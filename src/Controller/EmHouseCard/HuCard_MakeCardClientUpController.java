package Controller.EmHouseCard;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmHouseCard.EmHouse_MakeCardInfoOperateBll;
import bll.EmHouseCard.EmHouse_MakeCardInfoSelectBll;
import bll.EmHouseCard.EmHouse_TakeCardInfoSelectBll;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.EmHouseMakeCardInfoModel;
import Util.UserInfo;

public class HuCard_MakeCardClientUpController {
	private String id = (String) Executions.getCurrent().getArg().get("daid");
	private String tperid = (String) Executions.getCurrent().getArg().get("id");
	private EmHouse_MakeCardInfoSelectBll bll = new EmHouse_MakeCardInfoSelectBll();
	private EmHouseMakeCardInfoModel model = bll.getMakeCardInfo(id);
	private String timename = "";
	private EmHouse_TakeCardInfoSelectBll bankbll = new EmHouse_TakeCardInfoSelectBll();
	private List<String> banklist = bankbll.getBankInfo();
	private String zhtype = "";

	public HuCard_MakeCardClientUpController() {
		if (model.getGjj_cartstate() != null) {
			if (model.getGjj_cartstate().equals("1")
					|| model.getGjj_cartstate() == 1
					|| model.getGjj_cartstate().equals("2")
					|| model.getGjj_cartstate() == 2) {
				timename = "核收资料时间";
			} else if (model.getGjj_cartstate().equals("4")
					|| model.getGjj_cartstate() == 4) {
				timename = "制卡时间";
			}
			zhtype = bll.getZhType(model.getGid());
		}
	}

	// 客服助理提交资料
	@Command
	public void summit(@BindingParam("win") final Window win,
			@BindingParam("gd") final Grid gd,
			@BindingParam("clientuptime") Date clientuptime) {
		EmHouseMakeCardInfoModel m = new EmHouseMakeCardInfoModel();
		m.setId(Integer.parseInt(id));
		m.setTarpid(model.getTarpid());
		m.setState_Name(model.getState_Name());
		String[] message = new String[5];
		DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
		try {
			message = docOC.UpchkHaveTo(gd);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (message[0].equals("1")) {
			if (clientuptime == null || clientuptime.equals("")) {
				Messagebox.show("请输入核收资料时间", "提示", Messagebox.OK,
						Messagebox.ERROR);
			} else {
				String sqlstr = ",Gjj_ClientAssistantTime='"
						+ datechange(clientuptime) + "',Gjj_ClientAssistant='"
						+ UserInfo.getUsername() + "',Gjj_CartState=2";
				EmHouse_MakeCardInfoOperateBll obll = new EmHouse_MakeCardInfoOperateBll();
				String[] str = obll.EmHuMakeCardUpdate(m, sqlstr);
				if (str[0] == "1") {
					try {
						message = docOC.UpsubmitDoc(gd, id);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Messagebox.show("提交成功", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show("提交失败", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		}
	}

	// 福利提交
	@Command
	public void summitfl(@BindingParam("win") final Window win,
			@BindingParam("gd") final Grid gd,
			@BindingParam("clientuptime") Date clientuptime,
			@BindingParam("bank") String bank) {
		EmHouseMakeCardInfoModel m = new EmHouseMakeCardInfoModel();
		m.setId(Integer.parseInt(id));
		m.setTarpid(model.getTarpid());
		m.setState_Name(model.getState_Name());
		if (model.getGjj_cartstate() != 5
				&& (clientuptime == null || clientuptime.equals(""))) {
			if (model.getGjj_cartstate() == 4) {
				Messagebox.show("请选择银行制卡时间", "提示", Messagebox.OK,
						Messagebox.ERROR);
			} else {
				Messagebox.show("请选择核收资料时间", "提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			if (model.getGjj_cartstate() == 5
					&& (bank == null || bank.equals(""))) {
				Messagebox.show("请选择制卡银行", "提示", Messagebox.OK,
						Messagebox.ERROR);
			} else {
				String sqlstr = "";
				if (model.getGjj_cartstate().equals("2")
						|| model.getGjj_cartstate() == 2) {
					// 状态改为福利核收
					sqlstr = ",Gjj_WelfreAssistantTime='"
							+ datechange(clientuptime)
							+ "',Gjj_WelfreAssistant='"
							+ UserInfo.getUsername() + "',Gjj_CartState=4";
				} else if (model.getGjj_cartstate().equals("4")
						|| model.getGjj_cartstate() == 4) {
					// 状态改为正在制卡
					sqlstr = ",Gjj_ToBankTime='" + datechange(clientuptime)
							+ "',Gjj_maker='" + UserInfo.getUsername()
							+ "',Gjj_CartState=5";
				} else if (model.getGjj_cartstate().equals("5")
						|| model.getGjj_cartstate() == 5) {
					// 状态改为制卡完成
					sqlstr = ",Gjj_makebank='" + bank + "',Gjj_CartState=6";
				}
				EmHouse_MakeCardInfoOperateBll obll = new EmHouse_MakeCardInfoOperateBll();
				String[] str = obll.EmHuMakeCardUpdate(m, sqlstr);
				if (str[0] == "1") {
					Messagebox.show("提交成功", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show("提交失败", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		}
	}

	public EmHouseMakeCardInfoModel getModel() {
		return model;
	}

	public void setModel(EmHouseMakeCardInfoModel model) {
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

	public String getZhtype() {
		return zhtype;
	}

	public void setZhtype(String zhtype) {
		this.zhtype = zhtype;
	}

	private String datechange(Date d) {
		String date = "";
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		date = time.format(d);
		return date;
	}

}
