package Controller.EmSheBao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.EmShebaoChangeMAModel;
import Util.DateStringChange;
import Util.DateUtil;
import Util.RedirectUtil;
import bll.EmSheBao.Emsc_DeclareSelBll;
import bll.EmSheBao.Emsi_OperateBll;
import bll.EmSheBao.Emsi_SelBll;

public class Escm_ChangeReSendController {
	private final int id = Integer.parseInt(Executions.getCurrent().getArg()
			.get("daid").toString());
	private Emsi_SelBll bll = new Emsi_SelBll();
	private boolean ifStop;
	private boolean existsShebao = false;
	private String existsMessage;
	private String[] ownmonthList;
	private String ownmonth;
	private List<String> birthsList = null;
	private List<String[]> dystociatypeList = null;
	private Date endoffpDate = null;
	private String ifagree = "是";// 是否同意
	private DateStringChange dsc = new DateStringChange();

	private EmShebaoChangeMAModel maModel = new EmShebaoChangeMAModel();
	private Emsc_DeclareSelBll selbll = new Emsc_DeclareSelBll();

	public Escm_ChangeReSendController() {
		maModel = selbll.getMAChangeById(id);
		ownmonth = String.valueOf(maModel.getOwnmonth());

		if (checkInit())
			return;

		ifStop = bll.ifMAStop();
		// 判断是否停止当月操作社保
		if (ifStop) {
			ownmonthList = bll.getOwnmonthByNow(false);
		} else {
			ownmonthList = bll.getOwnmonthByNow(true);
		}

		birthsList = bll.getBriths();
		dystociatypeList = bll.getDystociatype();

		endoffpDate = dsc.StringtoDate(maModel.getEscm_endoffp(), "yyyy-MM-dd");
	}

	// 初始化检查
	private boolean checkInit() {
		if (!bll.existsCoOfferList(maModel.getGid())) {
			existsShebao = true;
			existsMessage = "该员工未分配“社会保险服务”的报价单项目，请分配该项目后再操作此步骤！";
			return existsShebao;
		}
		return false;
	}

	// 赋值难产类型
	@Command
	public void changeDystociatype(@BindingParam("type") Combobox type) {
		if (type.getSelectedItem() != null) {
			maModel.setEscm_dystociatype(Integer.parseInt(type
					.getSelectedItem().getValue().toString()));
		}
	}

	// 赋值胎数
	@Command
	public void changeMB(@BindingParam("mb") Combobox mb,
			@BindingParam("type") String type) {
		if (mb.getSelectedItem() != null) {
			Integer intMB = 0;
			if ("单".equals(mb.getSelectedItem().getLabel())) {
				intMB = 1;
			} else if ("双".equals(mb.getSelectedItem().getLabel())) {
				intMB = 2;
			} else {
				intMB = Integer.parseInt(mb.getSelectedItem().getLabel());
			}

			if ("e".equals(type)) {// 顺产
				maModel.setEscm_easylabourmb(intMB);
			} else if ("d".equals(type)) {// 难产
				maModel.setEscm_dystociamb(intMB);
			}
		}
	}

	// 提交
	@Command("submit")
	public void submit(@BindingParam("win") Window win,
			@BindingParam("gd") Grid gd) throws Exception {
		// try {
		// 如果没有勾选，则胞胎数清零
		if (maModel.getEscm_easylabour() == 0) {
			maModel.setEscm_easylabourmb(0);
		}
		if (maModel.getEscm_dystocia() == 0) {
			maModel.setEscm_dystociamb(0);
			maModel.setEscm_dystociatype(0);
		}

		if (checkPage()) {
			maModel.setEscm_ifdeclare(0);
			maModel.setOwnmonth(Integer.parseInt(ownmonth));
			if (ifagree.equals("是")) {
				maModel.setEscm_ifagree(1);
			} else {
				maModel.setEscm_ifagree(0);
			}
			maModel.setEscm_endoffp(dsc.DatetoSting(endoffpDate, "yyyy-MM-dd"));

			// 调用方法
			Emsi_OperateBll opbll = new Emsi_OperateBll();
			String[] message = opbll.reSendEscmInfo(maModel);
			if ("1".equals(message[0])) {
				// 新增材料
				DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
				docOC.AddsubmitDoc(gd, String.valueOf(id));

				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.NONE);
				win.detach();
				RedirectUtil util = new RedirectUtil();
				util.refreshEmUrl("/EmSheBao/Emsi_index.zul");// url为跳转页面连接

			} else {
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
		/*
		 * } catch (Exception e) { Messagebox.show("添加生育津贴申请出错。", "操作提示",
		 * Messagebox.OK, Messagebox.ERROR); }
		 */
	}

	// 检测表单
	private boolean checkPage() {
		DateUtil dUtil = new DateUtil();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String nowtime = sdf.format(date);
		if (endoffpDate != null) {
			maModel.setEscm_endoffp(dsc.DatetoSting(endoffpDate, "yyyy-MM-dd"));
		}

		boolean b = true;
		if (ownmonth == null) {
			b = false;
			Messagebox.show("请选择“所属月份”!", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return b;
		}
		if (maModel.getEscm_easylabour() == 0
				&& maModel.getEscm_abortion_fm() == 0
				&& maModel.getEscm_abortion_nfm() == 0
				&& maModel.getEscm_dystocia() == 0
				&& maModel.getEscm_setiud() == 0
				&& maModel.getEscm_getiud() == 0
				&& maModel.getEscm_tuballigation() == 0
				&& maModel.getEscm_tubalreversal() == 0
				&& maModel.getEscm_vasoligation() == 0
				&& maModel.getEscm_vasostomy() == 0) {
			b = false;
			Messagebox.show("请选择“生育假期”或“计划生育手术产假”中的其中一项!", "操作提示",
					Messagebox.OK, Messagebox.ERROR);
			return b;
		}

		if (maModel.getEscm_easylabour() + maModel.getEscm_abortion_fm()
				+ maModel.getEscm_abortion_nfm() + maModel.getEscm_dystocia()
				+ maModel.getEscm_setiud() + maModel.getEscm_getiud()
				+ maModel.getEscm_tuballigation()
				+ maModel.getEscm_tubalreversal()
				+ maModel.getEscm_vasoligation() + maModel.getEscm_vasostomy() > 1) {
			b = false;
			Messagebox.show("只能选择“生育假期”或“计划生育手术产假”中的其中一项!", "操作提示",
					Messagebox.OK, Messagebox.ERROR);
			return b;
		}
		if (maModel.getEscm_easylabour() == 1) {
			if (maModel.getEscm_easylabourmb() == 0) {
				b = false;
				Messagebox.show("请选择“顺产胞胎数据”!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return b;
			}
		}
		if (maModel.getEscm_dystocia() == 1) {
			if (maModel.getEscm_dystociatype() == 0) {
				b = false;
				Messagebox.show("请选择“难产类型”!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return b;
			} else if (maModel.getEscm_dystociamb() == 0) {
				b = false;
				Messagebox.show("请选择“难产胞胎数据”!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return b;
			}
		}
		if (endoffpDate == null) {
			b = false;
			Messagebox.show("请选择“妊娠/计划生育结束日期”!", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return b;
		}
		if (endoffpDate != null && maModel.getEscm_easylabour() == 1) {
			if (dUtil.datediff(maModel.getEscm_endoffp(), nowtime, "d") < 98) {
				b = false;
				Messagebox.show("顺产必须出院98天后才能申请生育津贴!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return b;
			}
		}
		if (endoffpDate != null && maModel.getEscm_dystocia() == 1) {
			if (dUtil.datediff(maModel.getEscm_endoffp(), nowtime, "d") < 128) {
				b = false;
				Messagebox.show("难产必须出院128天后才能申请生育津贴!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return b;
			}
		}
		return b;
	}

	public EmShebaoChangeMAModel getMaModel() {
		return maModel;
	}

	public void setMaModel(EmShebaoChangeMAModel maModel) {
		this.maModel = maModel;
	}

	public boolean isIfStop() {
		return ifStop;
	}

	public void setIfStop(boolean ifStop) {
		this.ifStop = ifStop;
	}

	public boolean isExistsShebao() {
		return existsShebao;
	}

	public void setExistsShebao(boolean existsShebao) {
		this.existsShebao = existsShebao;
	}

	public String getExistsMessage() {
		return existsMessage;
	}

	public void setExistsMessage(String existsMessage) {
		this.existsMessage = existsMessage;
	}

	public String[] getOwnmonthList() {
		return ownmonthList;
	}

	public void setOwnmonthList(String[] ownmonthList) {
		this.ownmonthList = ownmonthList;
	}

	public String getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(String ownmonth) {
		this.ownmonth = ownmonth;
	}

	public List<String> getBirthsList() {
		return birthsList;
	}

	public void setBirthsList(List<String> birthsList) {
		this.birthsList = birthsList;
	}

	public List<String[]> getDystociatypeList() {
		return dystociatypeList;
	}

	public void setDystociatypeList(List<String[]> dystociatypeList) {
		this.dystociatypeList = dystociatypeList;
	}

	public Date getEndoffpDate() {
		return endoffpDate;
	}

	public void setEndoffpDate(Date endoffpDate) {
		this.endoffpDate = endoffpDate;
	}

	public String getIfagree() {
		return ifagree;
	}

	public void setIfagree(String ifagree) {
		this.ifagree = ifagree;
	}

}
