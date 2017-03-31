package Controller.EmSheBao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmSheBao.Emsi_OperateBll;
import bll.EmSheBao.Emsi_SelBll;

import Model.EmShebaoChangeMAModel;
import Model.EmShebaoUpdateModel;
import Util.DateStringChange;
import Util.DateUtil;
import Util.RedirectUtil;

public class Escm_ChangeAddController {
	private final int gid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("gid").toString());
	private Emsi_SelBll bll = new Emsi_SelBll();
	private boolean ifStop;
	private boolean existsShebao = false;
	private String existsMessage;
	private String[] ownmonthList;
	private EmShebaoUpdateModel sbModel;
	private String ownmonth;
	private List<String> birthsList = null;
	private List<String[]> dystociatypeList = null;
	private EmShebaoChangeMAModel escmM = null;
	private Date endoffpDate = null;
	private String bank = null;
	private String bankacc = null;
	private String ifagree = "是";// 是否同意
	private DateStringChange dsc = new DateStringChange();

	public Escm_ChangeAddController() {

		if (checkInit())
			return;

		sbModel = bll.getShebaoUpdateByGid(gid);
		if (sbModel == null) {
			existsShebao = true;
			existsMessage = "无此员工的社保信息，不能操作生育津贴申请!";
			return;
		}

		ifStop = bll.ifMAStop();
		// 判断是否停止当月操作社保
		if (ifStop) {
			ownmonthList = bll.getOwnmonthByNow(false);
		} else {
			ownmonthList = bll.getOwnmonthByNow(true);
		}

		birthsList = bll.getBriths();
		dystociatypeList = bll.getDystociatype();

		// 初始化modle
		escmM = new EmShebaoChangeMAModel();
		escmM.setGid(gid);
		escmM.setEscm_ifpay(1);
		escmM.setEscm_easylabourmb(0);
		escmM.setEscm_dystociamb(0);
		escmM.setEscm_dystociatype(0);
		escmM.setEscm_mobile(sbModel.getMobile());

		// 获取银行信息
		String[] bankInfo = bll.getShebaoBank(sbModel.getCid(),
				sbModel.getEsiu_single());
		bank = bankInfo[0];
		bankacc = bankInfo[1];
	}

	// 初始化检查
	private boolean checkInit() {
		if (!bll.existsCoOfferList(gid)) {
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
			escmM.setEscm_dystociatype(Integer.parseInt(type.getSelectedItem()
					.getValue().toString()));
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
				escmM.setEscm_easylabourmb(intMB);
			} else if ("d".equals(type)) {// 难产
				escmM.setEscm_dystociamb(intMB);
			}
		}
	}

	// 提交
	@Command("submit")
	public void submit(@BindingParam("win") Window win) {
		// try {
		// 如果没有勾选，则胞胎数清零
		if (escmM.getEscm_easylabour() == 0) {
			escmM.setEscm_easylabourmb(0);
		}
		if (escmM.getEscm_dystocia() == 0) {
			escmM.setEscm_dystociamb(0);
			escmM.setEscm_dystociatype(0);
		}

		if (checkPage()) {
			escmM.setCid(sbModel.getCid());
			escmM.setEscm_ifdeclare(0);
			escmM.setOwnmonth(Integer.parseInt(ownmonth));
			if (ifagree.equals("是")) {
				escmM.setEscm_ifagree(1);
			} else {
				escmM.setEscm_ifagree(0);
			}
			escmM.setEscm_endoffp(dsc.DatetoSting(endoffpDate, "yyyy-MM-dd"));

			// 调用方法
			Emsi_OperateBll opbll = new Emsi_OperateBll();
			String[] message = opbll.addEscmInfo(escmM);
			if ("1".equals(message[0])) {
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
			escmM.setEscm_endoffp(dsc.DatetoSting(endoffpDate, "yyyy-MM-dd"));
		}

		boolean b = true;
		if (ownmonth == null) {
			b = false;
			Messagebox.show("请选择“所属月份”!", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return b;
		}
		if (escmM.getEscm_easylabour() == 0 && escmM.getEscm_abortion_fm() == 0
				&& escmM.getEscm_abortion_nfm() == 0
				&& escmM.getEscm_dystocia() == 0 && escmM.getEscm_setiud() == 0
				&& escmM.getEscm_getiud() == 0
				&& escmM.getEscm_tuballigation() == 0
				&& escmM.getEscm_tubalreversal() == 0
				&& escmM.getEscm_vasoligation() == 0
				&& escmM.getEscm_vasostomy() == 0) {
			b = false;
			Messagebox.show("请选择“生育假期”或“计划生育手术产假”中的其中一项!", "操作提示",
					Messagebox.OK, Messagebox.ERROR);
			return b;
		}

		if (escmM.getEscm_easylabour() + escmM.getEscm_abortion_fm()
				+ escmM.getEscm_abortion_nfm() + escmM.getEscm_dystocia()
				+ escmM.getEscm_setiud() + escmM.getEscm_getiud()
				+ escmM.getEscm_tuballigation() + escmM.getEscm_tubalreversal()
				+ escmM.getEscm_vasoligation() + escmM.getEscm_vasostomy() > 1) {
			b = false;
			Messagebox.show("只能选择“生育假期”或“计划生育手术产假”中的其中一项!", "操作提示",
					Messagebox.OK, Messagebox.ERROR);
			return b;
		}

		if (escmM.getEscm_easylabour() == 1) {
			if (escmM.getEscm_easylabourmb() == 0) {
				b = false;
				Messagebox.show("请选择“顺产胞胎数据”!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return b;
			}
		}
		if (escmM.getEscm_dystocia() == 1) {
			if (escmM.getEscm_dystociatype() == 0) {
				b = false;
				Messagebox.show("请选择“难产类型”!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return b;
			} else if (escmM.getEscm_dystociamb() == 0) {
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
		if (endoffpDate != null && escmM.getEscm_easylabour() == 1) {
			if (dUtil.datediff(escmM.getEscm_endoffp(), nowtime, "d") < 98) {
				b = false;
				Messagebox.show("顺产必须出院98天后才能申请生育津贴!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return b;
			}
		}
		if (endoffpDate != null && escmM.getEscm_dystocia() == 1) {
			if (dUtil.datediff(escmM.getEscm_endoffp(), nowtime, "d") < 128) {
				b = false;
				Messagebox.show("难产必须出院128天后才能申请生育津贴!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return b;
			}
		}
		if (endoffpDate != null) {
			// 判断同一个妊娠/计划生育结束日期不能重复添加数据
			b = bll.chkEscmByEoD(sbModel.getEsiu_idcard(),
					dsc.DatetoSting(endoffpDate, "yyyy-MM-dd"));
			if (b == false) {
				Messagebox.show("该身份证号和妊娠/计划生育结束日期已申请过生育津贴，不能重复申请!", "操作提示",
						Messagebox.OK, Messagebox.ERROR);
			}
			return b;
		}

		return b;
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

	public int getGid() {
		return gid;
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

	public EmShebaoUpdateModel getSbModel() {
		return sbModel;
	}

	public void setSbModel(EmShebaoUpdateModel sbModel) {
		this.sbModel = sbModel;
	}

	public EmShebaoChangeMAModel getEscmM() {
		return escmM;
	}

	public void setEscmM(EmShebaoChangeMAModel escmM) {
		this.escmM = escmM;
	}

	public Date getEndoffpDate() {
		return endoffpDate;
	}

	public void setEndoffpDate(Date endoffpDate) {
		this.endoffpDate = endoffpDate;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBankacc() {
		return bankacc;
	}

	public void setBankacc(String bankacc) {
		this.bankacc = bankacc;
	}

	public String getIfagree() {
		return ifagree;
	}

	public void setIfagree(String ifagree) {
		this.ifagree = ifagree;
	}

}
