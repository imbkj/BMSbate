package Controller.EmZYT;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmHouse.EmHouseChangeGjjBll;
import bll.EmSheBao.Emsi_OperateBll;
import bll.EmSheBao.Emsi_SelBll;
import bll.EmZYT.EmZYT_OperateBll;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.EmHouseChangeGJJModel;
import Model.EmHouseUpdateModel;
import Model.EmShebaoChangeSZSIModel;
import Model.EmShebaoUpdateModel;
import Model.EmZYTModel;
import Model.PubCodeConversionModel;
import Util.DateStringChange;
import Util.UserInfo;

public class EmZYT_ChangeHJController {
	private EmZYTModel emztM = (EmZYTModel) Executions.getCurrent().getArg()
			.get("emztM");
	private EmZYT_OperateBll oBll = new EmZYT_OperateBll();

	private Emsi_SelBll sbSbll = new Emsi_SelBll();
	private String sbchangecon;
	private String sbcon;
	private String[] sbownmonthList;// 社保所属月份下拉框
	private String sbownmonth;
	private boolean ifStop;
	private EmShebaoUpdateModel sbModel;
	private boolean existsShebao = false;
	private String existsMessage;
	private boolean ifDoshebao = true;

	// 公积金
	private List<String> gjjownmonthList = new ArrayList<>();// 公积金所属月份下拉框
	private EmHouseChangeGjjBll gjjBll = new EmHouseChangeGjjBll();
	private List<EmHouseUpdateModel> euList = new ListModelList<>();
	private EmHouseChangeGJJModel ehg = new EmHouseChangeGJJModel();
	private List<PubCodeConversionModel> pmList = new ListModelList<>();
	private String gjjHJ;
	private String gjjownmonth;
	private boolean existsGjj = false;
	private String existsGjjMessage;
	private boolean ifDogjj = true;
	private String changeVal;

	private String username = UserInfo.getUsername();
	private String userid = UserInfo.getUserid();

	public EmZYT_ChangeHJController() {
		// 社保数据初始化
		sbModel = sbSbll.getShebaoUpdateByGid(emztM.getGid());
		if (sbModel == null) {
			existsShebao = true;
			existsMessage = "无此员工的社保信息，不能作变更!";
		} else {
			ifStop = sbSbll.ifStop();
			// 判断是否停止当月操作社保
			if (ifStop) {
				sbownmonthList = sbSbll.getOwnmonthByNow(false);
			} else {
				sbownmonthList = sbSbll.getOwnmonthByNow(true);
			}

			if ("市内城镇".equals(sbModel.getEsiu_hj())
					|| "市内农村".equals(sbModel.getEsiu_hj())) {
				sbchangecon = "市外城镇";
				sbcon = "户籍由“" + sbModel.getEsiu_hj() + "”改为“市外城镇”";
			} else {
				sbchangecon = "市内城镇";
				sbcon = "户籍由“" + sbModel.getEsiu_hj() + "”改为“市内城镇”";
			}
		}

		// 公积金数据初始化
		euList = gjjBll.getEmhouseupdateInfoByGid(emztM.getGid());
		if (euList.size() > 0) {
			ehg.setCid(euList.get(0).getCid());
			ehg.setGid(euList.get(0).getGid());
			ehg.setEhcg_company(euList.get(0).getCoba_shortname());
			ehg.setEhcg_name(euList.get(0).getEmhu_name());
			ehg.setEhcg_addname(username);
			ehg.setEhcg_ifdeclare(0);
			setGjjownmonthList();
			gjjHJ = euList.get(0).getEmhu_hj();
			setPmList(35, "户籍类型");
		} else {
			existsGjj = true;
			existsGjjMessage = "无此员工的住房公积金信息，不能作变更!";
		}

		chkzytOwnmonth();
	}

	// 检查委托单委托缴费月是否可操作
	public void chkzytOwnmonth() {
		DateStringChange dsc = new DateStringChange();
		String zytOwnmonth;

		try {
			zytOwnmonth = dsc.DatetoSting(
					dsc.StringtoDate(emztM.getEmzt_ylstart(), "yyyy-MM-dd"),
					"yyyyMM");
		} catch (Exception e) {
			zytOwnmonth = "";
		}

		if (sbownmonthList != null) {
			if (sbownmonthList.length > 0) {
				for (String om : sbownmonthList) {// 遍历社保所属月份下拉框
					if (om.equals(zytOwnmonth)) {// 判断委托单委托缴费月是否包含在所属月份下拉框里
						sbownmonth = zytOwnmonth;
						break;
					}
				}
			}
		}

		if (gjjownmonthList.size() > 0) {
			for (String om : gjjownmonthList) {// 遍历公积金所属月份下拉框
				if (om.equals(zytOwnmonth)) {// 判断委托单委托缴费月是否包含在所属月份下拉框里
					gjjownmonth = zytOwnmonth;
					break;
				}
			}
		}
	}

	// 是否操作社保或公积金户籍变更
	@Command("ifDo")
	@NotifyChange({ "ifDogjj", "ifDoshebao" })
	public void ifDo(@BindingParam("type") int type,
			@BindingParam("val") int val) {
		if (type == 1) {// 社保
			if (val == 1) {
				ifDoshebao = true;
			} else {
				ifDoshebao = false;
			}
		} else if (type == 2) {
			if (val == 1) {
				ifDogjj = true;
			} else {
				ifDogjj = false;
			}
		}
	}

	@Command("submit")
	public void submit(@BindingParam("winC") Window winC) throws Exception {
		if (chkPage()) {
			int chk = 0;
			String mes = "";

			// 社保
			if (ifDoshebao) {
				EmShebaoChangeSZSIModel ecModel = new EmShebaoChangeSZSIModel();
				ecModel.setGid(emztM.getGid());
				ecModel.setOwnmonth(Integer.parseInt(sbownmonth));
				ecModel.setEscs_change("变更户籍");
				ecModel.setEscs_content(sbcon);
				ecModel.setEscs_addname(username);
				ecModel.setEscs_name(sbModel.getEsiu_name());
				ecModel.setEscs_changehj(sbchangecon);
				// 调用新增方法
				Emsi_OperateBll opbll = new Emsi_OperateBll();
				String[] message = opbll.changeSZSI(ecModel);
				if (!"1".equals(message[0])) {// 不成功
					chk = chk + 1;
					mes = "社保变更户籍数据添加失败；";
				}
			}

			// 公积金
			if (ifDogjj) {
				Grid gd = (Grid) winC.getFellow("docGrid");
				Integer tid = 0;

				ehg.setEhcg_tid(0);
				ehg.setOwnmonth(Integer.parseInt(gjjownmonth));
				ehg.setEhcg_change("变更户籍");
				ehg.setEhcg_content("户籍由\"" + gjjHJ + "\"改为\"" + changeVal
						+ "\"");
				ehg.setEhcg_changeValue(changeVal);

				tid = gjjBll.addData(ehg);

				if (tid > 0) {
					// 调用内联页方法submitDoc(Grid gd)
					DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
					docOC.AddsubmitDoc(gd, tid.toString());
					ehg.setEhcg_tid(tid);
				} else {// 不成功
					chk = chk + 1;
					mes = mes + "公积金变更户籍数据添加失败；";
				}

			}

			// 智翼通接口
			if (chk == 0) {
				emztM.setEmzt_state(1);
				String[] message = oBll.upEmZYTState(emztM);
				if (!message[0].equals("1")) {
					chk = chk + 1;
					mes = mes + "智翼通接口数据状态更新失败；";
				}
			} else {
				chk = chk + 1;
				mes = mes + "智翼通接口数据状态更新失败；";
			}

			if (chk == 0) {
				Window win;
				win = (Window) Path.getComponent("/winCA");
				// 弹出提示
				Messagebox
						.show("操作成功！", "操作提示", Messagebox.OK, Messagebox.NONE);
				win.detach();
			} else {
				// 弹出提示
				Messagebox.show("操作失败：" + mes, "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}

		}

	}

	// 页面数据完整性检查
	public boolean chkPage() {
		boolean chk = true;
		if (ifDoshebao) {
			if (sbownmonth == null || "".equals(sbownmonth)) {
				chk = false;
				Messagebox.show("请选择“社保户籍变更”所属月份!", "操作提示", Messagebox.OK,
						Messagebox.NONE);
			}
		}
		if (ifDogjj) {
			if (gjjownmonth == null || "".equals(gjjownmonth)) {
				chk = false;
				Messagebox.show("请选择“公积金户籍变更”所属月份!", "操作提示", Messagebox.OK,
						Messagebox.NONE);
			} else if (changeVal == null || "".equals(changeVal)) {
				chk = false;
				Messagebox.show("请选择“公积金”变更内容!", "操作提示", Messagebox.OK,
						Messagebox.NONE);
			}
		}

		return chk;
	}

	public String getSbchangecon() {
		return sbchangecon;
	}

	public void setSbchangecon(String sbchangecon) {
		this.sbchangecon = sbchangecon;
	}

	public String[] getSbownmonthList() {
		return sbownmonthList;
	}

	public void setSbownmonthList(String[] sbownmonthList) {
		this.sbownmonthList = sbownmonthList;
	}

	public boolean isIfStop() {
		return ifStop;
	}

	public void setIfStop(boolean ifStop) {
		this.ifStop = ifStop;
	}

	public List<String> getGjjownmonthList() {
		return gjjownmonthList;
	}

	public void setGjjownmonthList() {
		this.gjjownmonthList = gjjBll.getOwnmonthList();
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

	public List<PubCodeConversionModel> getPmList() {
		return pmList;
	}

	public void setPmList(Integer id, String name) {
		this.pmList = gjjBll.getchangeModel(id, name);
	}

	public String getGjjHJ() {
		return gjjHJ;
	}

	public void setGjjHJ(String gjjHJ) {
		this.gjjHJ = gjjHJ;
	}

	public String getSbownmonth() {
		return sbownmonth;
	}

	public void setSbownmonth(String sbownmonth) {
		this.sbownmonth = sbownmonth;
	}

	public String getGjjownmonth() {
		return gjjownmonth;
	}

	public void setGjjownmonth(String gjjownmonth) {
		this.gjjownmonth = gjjownmonth;
	}

	public EmZYTModel getEmztM() {
		return emztM;
	}

	public void setEmztM(EmZYTModel emztM) {
		this.emztM = emztM;
	}

	public EmShebaoUpdateModel getSbModel() {
		return sbModel;
	}

	public void setSbModel(EmShebaoUpdateModel sbModel) {
		this.sbModel = sbModel;
	}

	public boolean isExistsGjj() {
		return existsGjj;
	}

	public void setExistsGjj(boolean existsGjj) {
		this.existsGjj = existsGjj;
	}

	public String getExistsGjjMessage() {
		return existsGjjMessage;
	}

	public void setExistsGjjMessage(String existsGjjMessage) {
		this.existsGjjMessage = existsGjjMessage;
	}

	public boolean isIfDoshebao() {
		return ifDoshebao;
	}

	public void setIfDoshebao(boolean ifDoshebao) {
		this.ifDoshebao = ifDoshebao;
	}

	public boolean isIfDogjj() {
		return ifDogjj;
	}

	public void setIfDogjj(boolean ifDogjj) {
		this.ifDogjj = ifDogjj;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getChangeVal() {
		return changeVal;
	}

	public void setChangeVal(String changeVal) {
		this.changeVal = changeVal;
	}

}
