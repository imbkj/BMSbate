package Controller.EmHouseCard;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.EmExpressContactInfoModel;
import Model.EmExpressInfoModel;
import Model.EmHouseCardBackInfoModel;
import Model.EmHouseTakeCardInfoModel;
import Util.UserInfo;
import bll.EmExpress.EmExpressInfoSelectBll;
import bll.EmHouseCard.EmHouse_MakeCardInfoSelectBll;
import bll.EmHouseCard.EmHouse_TakeCardInfoOperateBll;
import bll.EmHouseCard.EmHouse_TakeCardInfoSelectBll;

public class HuCard_ClientUpDataInfoController {
	private String id = (String) Executions.getCurrent().getArg().get("daid");
	//private String tperid = (String) Executions.getCurrent().getArg().get("id");
	private EmHouse_TakeCardInfoSelectBll bll = new EmHouse_TakeCardInfoSelectBll();
	private List<EmHouseTakeCardInfoModel> list = new ArrayList<EmHouseTakeCardInfoModel>();
	private EmHouseTakeCardInfoModel model = new EmHouseTakeCardInfoModel();
	private String takebank = "";
	private Integer dataId;// 材料ID
	private String timename;
	private List<String> banklist = bll.getBankInfo();

	private EmExpressInfoSelectBll bllc = new EmExpressInfoSelectBll();
	private List<EmExpressContactInfoModel> listc = new ArrayList<EmExpressContactInfoModel>();
	private EmExpressContactInfoModel m = new EmExpressContactInfoModel();
	private EmExpressInfoModel modele = new EmExpressInfoModel();
	private boolean visable = false;
	private String zhtype = "";
	private EmHouseCardBackInfoModel bm = new EmHouseCardBackInfoModel();

	public HuCard_ClientUpDataInfoController() {
		list = bll.getEmhouseTakeCardInfo(" and re_id=" + id);
		if (list.size() > 0) {
			model = list.get(0);
			takebank = model.getCohf_banklk();
			if (takebank == null || takebank == "") {
				takebank = model.getRe_band();
			}

			getType(takebank);
			if (model.getRe_state().equals("8") || model.getRe_state() == 8) {
				timename = "福利核收资料时间";
			}
			// 已交银行
			else if (model.getRe_state().equals("9")
					|| model.getRe_state() == 9) {
				timename = "递交银行时间";
			}
			// 福利领卡
			else if (model.getRe_state().equals("10")
					|| model.getRe_state() == 10) {
				timename = "福利领卡时间";
			}
			// 客服助理签收
			else if (model.getRe_state().equals("11")
					|| model.getRe_state() == 11) {
				timename = "服务中心领卡时间";
			}
			// 客服签收
			else if (model.getRe_state().equals("12")
					|| model.getRe_state() == 12) {
				timename = "客服签收时间";
			}
			// 客服签收
			else if (model.getRe_state().equals("25")
					|| model.getRe_state() == 25) {
				timename = "客服签收时间";
			}
			// 员工领卡
			else if (model.getRe_state().equals("13")
					|| model.getRe_state() == 13) {
				timename = "员工领卡时间";
			}

			listc = bllc.getEmExpressContactInfoList(" and gid="
					+ model.getGid());
		} else {
			Messagebox
					.show("找不到该员工基本信息", "提示", Messagebox.OK, Messagebox.ERROR);
		}
		EmHouse_MakeCardInfoSelectBll mbll = new EmHouse_MakeCardInfoSelectBll();
		zhtype = mbll.getZhType(model.getGid());
		if (model.getState_Name() != null && model.getState_Name().equals("退回")) {
			bm = bll.getBackCase(id);
		}
	}

	@Command
	public void oreatewin(@BindingParam("win") Window win) {
		if (list.size() <= 0) {
			win.detach();
		}
	}

	// 领卡信息更新(客服核收)
	@Command
	public void summit(@BindingParam("win") final Window win,
			@BindingParam("gd") final Grid gd,
			@BindingParam("clientuptime") Date clientuptime) {
		EmHouseTakeCardInfoModel m = new EmHouseTakeCardInfoModel();
		EmHouse_TakeCardInfoOperateBll obll = new EmHouse_TakeCardInfoOperateBll();
		m.setRe_id(model.getRe_id());
		m.setRe_taprid(model.getRe_taprid());
		m.setState_Name(model.getState_Name());
		m.setRe_docId(dataId);
		m.setCid(model.getCid());
		if (model.getRe_state().equals("22") || model.getRe_state() == 22) {
			m.setState_Name("客服重新提交资料");
		}
		if (clientuptime == null || clientuptime.equals("")) {
			Messagebox.show("请输入提交资料时间", "提示", Messagebox.OK, Messagebox.ERROR);
		} else {
			// Re_State=23表示是客服核收
			// String
			// sqlstr=",Pla_clientTime='"+datechange(clientuptime)+"',Re_Client='"+UserInfo.getUsername()+"',Re_State=23";
			String sqlstr = ",pla_clienttime='" + datechange(clientuptime)
					+ "',Re_State=8";
			String[] str = new String[5];
			DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
			try {
				str = obll.updateTakeCardInfo(m, sqlstr, 0);
				// 调用内联页方法chkHaveTo(Grid gd)
				if (str[0] == "1") {
					// String[] message = docOC.AddchkHaveTo(gd);
					// if (message[0].equals("1")) {
					docOC.UpsubmitDoc(gd, id);
					// }
					Messagebox.show("提交成功", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show(str[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} catch (Exception e) {
				System.out.println("错误：" + e.getMessage());
				Messagebox.show("操作失败。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	// 领卡信息更新(客服助理核收_服务中心核收)
	@Command
	public void summitzl(@BindingParam("win") final Window win,
			@BindingParam("gd") final Grid gd,
			@BindingParam("clientuptime") Date clientuptime) {
		EmHouseTakeCardInfoModel m = new EmHouseTakeCardInfoModel();
		EmHouse_TakeCardInfoOperateBll obll = new EmHouse_TakeCardInfoOperateBll();
		m.setRe_id(model.getRe_id());
		m.setRe_taprid(model.getRe_taprid());
		m.setState_Name(model.getState_Name());
		m.setRe_docId(dataId);
		m.setCid(model.getCid());
		if (model.getRe_state().equals("22") || model.getRe_state() == 22) {
			m.setState_Name("助理重新提交资料");
		}
		if (clientuptime == null || clientuptime.equals("")) {
			Messagebox.show("请输入提交资料时间", "提示", Messagebox.OK, Messagebox.ERROR);
		} else {
			// Re_State=8表示是客服助理核收
			String sqlstr = ",Pla_ClientAssistantTime='"
					+ datechange(clientuptime) + "',Pla_ClientAssistant='"
					+ UserInfo.getUsername() + "',Re_State=8";
			String[] str = new String[5];
			DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
			try {
				str = obll.updateTakeCardInfo(m, sqlstr, 0);
				// 调用内联页方法chkHaveTo(Grid gd)
				if (str[0] == "1") {
					docOC.UpsubmitDoc(gd, id);
					Messagebox.show("提交成功", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show("提交失败", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} catch (Exception e) {
				System.out.println("错误：" + e.getMessage());
				Messagebox.show("操作失败。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	// 领卡信息更新(福利助理核收)
	@Command
	public void summitfl(@BindingParam("win") final Window win,
			@BindingParam("gd") final Grid gd,
			@BindingParam("clientuptime") Date clientuptime,
			@BindingParam("bank") String bank) {
		EmHouseTakeCardInfoModel m = new EmHouseTakeCardInfoModel();
		EmHouse_TakeCardInfoOperateBll obll = new EmHouse_TakeCardInfoOperateBll();
		m.setRe_id(model.getRe_id());
		m.setRe_taprid(model.getRe_taprid());
		m.setState_Name(model.getState_Name());
		m.setRe_docId(dataId);
		m.setCid(model.getCid());
		if (clientuptime == null || clientuptime.equals("")) {
			Messagebox.show("请输入" + timename, "提示", Messagebox.OK,
					Messagebox.ERROR);
		} else {
			String sqlstr = "";
			// Re_State=9表示是福利助理核收
			if (model.getRe_state().equals("8") || model.getRe_state() == 8) {
				sqlstr = ",Gjj_WelfreAssistantTime='"
						+ datechange(clientuptime) + "',pla_welfreassistant='"
						+ UserInfo.getUsername() + "',Re_State=9";
			}
			// 已交银行
			else if (model.getRe_state().equals("9")
					|| model.getRe_state() == 9) {
				sqlstr = ",Pla_ToBankTime='" + datechange(clientuptime)
						+ "',Pla_bankpeople='" + UserInfo.getUsername()
						+ "',upbank='" + bank + "',Re_State=10";
			}
			// 福利领卡
			else if (model.getRe_state().equals("10")
					|| model.getRe_state() == 10) {
				sqlstr = ",Pla_flTime='" + datechange(clientuptime)
						+ "',Pla_fl='" + UserInfo.getUsername()
						+ "',Re_State=11";
			}
			String[] str = new String[5];
			try {
				str = obll.updateTakeCardInfo(m, sqlstr, 1);
				// 调用内联页方法chkHaveTo(Grid gd)
				if (str[0] == "1") {
					Messagebox.show("提交成功", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show("提交失败", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} catch (Exception e) {
				System.out.println("错误：" + e.getMessage());
				Messagebox.show("操作失败。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	// 领卡信息更新(签收)
	@Command
	public void summitqian(@BindingParam("win") final Window win,
			@BindingParam("clientuptime") Date clientuptime,
			@BindingParam("takecardname") String takecardname,
			@BindingParam("taketype") String taketype) {
		EmHouseTakeCardInfoModel m = new EmHouseTakeCardInfoModel();
		EmHouse_TakeCardInfoOperateBll obll = new EmHouse_TakeCardInfoOperateBll();
		m.setRe_id(model.getRe_id());
		m.setRe_taprid(model.getRe_taprid());
		m.setState_Name(model.getState_Name());
		m.setRe_docId(dataId);
		m.setCid(model.getCid());
		if (clientuptime == null || clientuptime.equals("")) {
			Messagebox.show("请输入提交资料时间", "提示", Messagebox.OK, Messagebox.ERROR);
		} else {
			String sqlstr = "";
			// 助理签收
			if (model.getRe_state().equals("11") || model.getRe_state() == 11) {
				sqlstr = ",Pla_ReceiveTime='" + datechange(clientuptime)
						+ "',Pla_ReceiveName='" + UserInfo.getUsername()
						+ "',Re_State=12";
			}
			// 客服签收(Re_state=25表示此时状态是已交客服)
			else if (model.getRe_state().equals("25")
					|| model.getRe_state() == 25) {
				sqlstr = ",Pla_ClientTTime='" + datechange(clientuptime)
						+ "',Pla_ReceliveClient='" + UserInfo.getUsername()
						+ "',Re_State=13";
			}
			// 员工领卡
			else if (model.getRe_state().equals("13")
					|| model.getRe_state() == 13) {
				sqlstr = ",Re_name='" + takecardname + "',Re_time='"
						+ datechange(clientuptime) + "',pla_taketype='"
						+ taketype + "',send_client='" + UserInfo.getUsername()
						+ "',Re_State=14";
			}
			String[] str = new String[5];
			try {
				str = obll.updateTakeCardInfo(m, sqlstr, 1);
				// 调用内联页方法chkHaveTo(Grid gd)
				if (str[0] == "1") {
					Messagebox.show("提交成功", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show("提交失败", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} catch (Exception e) {
				System.out.println("错误：" + e.getMessage());
				Messagebox.show("操作失败。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	// 退回
	@Command
	public void summitback(@BindingParam("win") Window win) {
		Map map = new HashMap<>();
		map.put("model", model);
		map.put("daid", model.getRe_id() + "");
		map.put("id", model.getRe_taprid() + "");
		Window window = (Window) Executions.createComponents(
				"../EmHouseCard/HuCard_TakeCardSendBack.zul", null, map);
		window.doModal();
		win.detach();
	}

	// 判断领卡类型
	private void getType(String info) {
		if (info != null) {
			if (info.indexOf("建行") >= 0 || info.indexOf("建设") >= 0) {
				dataId = 41;
			} else if (info.indexOf("招行") >= 0 || info.indexOf("招行") >= 0) {
				dataId = 42;
			}
			// 否则就是默认为中国银行
			else {
				dataId = 43;
			}
		} else {
			dataId = 43;
		}
	}

	// 发卡方式
	@Command
	@NotifyChange("visable")
	public void sendtype(@BindingParam("val") String val) {
		if (val != null) {
			if (val.equals("到付邮寄员工") || val.equals("统一邮寄联系人")) {
				visable = true;
			} else {
				visable = false;
			}
		}
	}

	public Integer getDataId() {
		return dataId;
	}

	public void setDataId(Integer dataId) {
		this.dataId = dataId;
	}

	public EmHouseTakeCardInfoModel getModel() {
		return model;
	}

	public void setModel(EmHouseTakeCardInfoModel model) {
		this.model = model;
	}

	public String getTimename() {
		return timename;
	}

	public void setTimename(String timename) {
		this.timename = timename;
	}

	public List<String> getBanklist() {
		return banklist;
	}

	public void setBanklist(List<String> banklist) {
		this.banklist = banklist;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<EmExpressContactInfoModel> getListc() {
		return listc;
	}

	public void setListc(List<EmExpressContactInfoModel> listc) {
		this.listc = listc;
	}

	public EmExpressContactInfoModel getM() {
		return m;
	}

	public void setM(EmExpressContactInfoModel m) {
		this.m = m;
	}

	public EmExpressInfoModel getModele() {
		return modele;
	}

	public void setModele(EmExpressInfoModel modele) {
		this.modele = modele;
	}

	public boolean isVisable() {
		return visable;
	}

	public void setVisable(boolean visable) {
		this.visable = visable;
	}

	public String getZhtype() {
		return zhtype;
	}

	public void setZhtype(String zhtype) {
		this.zhtype = zhtype;
	}

	public EmHouseCardBackInfoModel getBm() {
		return bm;
	}

	public void setBm(EmHouseCardBackInfoModel bm) {
		this.bm = bm;
	}
	
	
	public String getTakebank() {
		return takebank;
	}

	public void setTakebank(String takebank) {
		this.takebank = takebank;
	}

	private String datechange(Date d) {
		String date = "";
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		if (d != null && !d.equals("")) {
			date = time.format(d);
		}
		return date;
	}

}
