package Controller.EmHouseCard;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

import bll.EmHouseCard.EmHouse_TakeCardInfoOperateBll;
import bll.EmHouseCard.EmHouse_TakeCardInfoSelectBll;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.EmHouseTakeCardInfoModel;
import Util.UserInfo;

public class HuCard_ClientDealAllController {
	private List<EmHouseTakeCardInfoModel> list = (List<EmHouseTakeCardInfoModel>) Executions
			.getCurrent().getArg().get("list");
	private String sid = Executions
			.getCurrent().getArg().get("sid").toString();
	private Integer dataId;
	private String timename;
	private EmHouseTakeCardInfoModel model = new EmHouseTakeCardInfoModel();
	private EmHouse_TakeCardInfoSelectBll bankbll = new EmHouse_TakeCardInfoSelectBll();
	private List<String> banklist = bankbll.getBankInfo();
	private boolean ifvis = false, takeinfovis = false;

	public HuCard_ClientDealAllController() {
		if (list.size() > 0) {
			dataId = getType(list.get(0).getCohf_banklk());
			gettimenames(list.get(0).getRe_state());
			model = list.get(0);
		}
	}

	// 领卡信息更新(客服批量核收)
	@Command
	public void summit(@BindingParam("win") final Window win,
			@BindingParam("gd") final Grid gd,
			@BindingParam("clientuptime") Date clientuptime) {
		EmHouse_TakeCardInfoOperateBll obll = new EmHouse_TakeCardInfoOperateBll();
		boolean flag = false;
		if (clientuptime == null || clientuptime.equals("")) {
			Messagebox.show("请输入提交资料时间", "提示", Messagebox.OK, Messagebox.ERROR);
		} else {
			for (int i = 0; i < list.size(); i++) {
				EmHouseTakeCardInfoModel m = list.get(i);
				m.setRe_docId(dataId);
				String sqlstr = ",Pla_ClientAssistantTime='"
						+ datechange(clientuptime) + "',"
						+ "Pla_ClientAssistant='" + UserInfo.getUsername()
						+ "',Re_State=8";
				String[] str = new String[5];
				DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
				try {
					if (m.getRe_taprid() != null) {
						str = obll.updateTakeCardInfo(m, sqlstr, 0);
					} else {
						int k = obll.updateTakeCardInfo(m.getRe_id(), sqlstr);
						if (k > 0) {
							flag = true;
						}
					}

					// 调用内联页方法chkHaveTo(Grid gd)
					if (str[0] == "1") {
						String[] message = docOC.AddchkHaveTo(gd);
						if (message[0].equals("1")) {
							message = docOC.AddsubmitDoc(gd, m.getRe_id() + "");
						}
						flag = true;
					}
				} catch (Exception e) {
					System.out.println("错误：" + e.getMessage());
					Messagebox.show("操作失败。", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
			if (flag) {
				Messagebox.show("提交成功", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox
						.show("提交失败", "操作提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}

	// 领卡信息更新(福利批量核收)
	@Command
	public void summitfl(@BindingParam("win") final Window win,
			@BindingParam("gd") final Grid gd,
			@BindingParam("clientuptime") Date clientuptime,
			@BindingParam("bank") String bank) {
		EmHouse_TakeCardInfoOperateBll obll = new EmHouse_TakeCardInfoOperateBll();
		boolean flag = false;
		if (clientuptime == null || clientuptime.equals("")) {
			Messagebox.show("请输入提交资料时间", "提示", Messagebox.OK, Messagebox.ERROR);
		} else {
			for (int i = 0; i < list.size(); i++) {
				EmHouseTakeCardInfoModel m = list.get(i);
				m.setRe_docId(dataId);
				String sqlstr = "";
				// Re_State=9表示是福利助理核收
				if (m.getRe_state().equals("8") || m.getRe_state() == 8) {
					sqlstr = ",Gjj_WelfreAssistantTime='"
							+ datechange(clientuptime)
							+ "',pla_welfreassistant='"
							+ UserInfo.getUsername() + "',Re_State=9";
				}
				// 已交银行
				else if (m.getRe_state().equals("9") || m.getRe_state() == 9) {
					sqlstr = ",Pla_ToBankTime='" + datechange(clientuptime)
							+ "',Pla_bankpeople='" + UserInfo.getUsername()
							+ "',upbank='" + bank + "',Re_State=10";
				}
				// 福利领卡
				else if (m.getRe_state().equals("10") || m.getRe_state() == 10) {
					sqlstr = ",Pla_flTime='" + datechange(clientuptime)
							+ "',Pla_fl='" + UserInfo.getUsername()
							+ "',Re_State=11";
				}

				String[] str = new String[5];
				try {
					if (m.getRe_taprid() != null) {
						str = obll.updateTakeCardInfo(m, sqlstr, 0);
					} else {
						int k = obll.updateTakeCardInfo(m.getRe_id(), sqlstr);
						if (k > 0) {
							flag = true;
						}
					}
					// 调用内联页方法chkHaveTo(Grid gd)
					if (str[0] == "1") {
						flag = true;

					}
				} catch (Exception e) {
					System.out.println("错误：" + e.getMessage());
					Messagebox.show("操作失败。", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
			if (flag) {
				Messagebox.show("提交成功", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox
						.show("提交失败", "操作提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}

	// 领卡信息更新(签收)
	@Command
	public void summitqian(@BindingParam("win") final Window win,
			@BindingParam("clientuptime") Date clientuptime,
			@BindingParam("takecardname") String takecardname,
			@BindingParam("taketype") String taketype,
			@BindingParam("statename") String statename,
			@BindingParam("takename") String takename,
			@BindingParam("sendtime") Date sendtime,
			@BindingParam("sendtype") String sendtype) {
		// Window wins = (Window) Path.getComponent("/windata");
		Cell dt = (Cell) win.getFellow("clval");
		// types==1表示下一步，types==2表示跳转到最后并结束流程
		Integer types = 1;
		EmHouse_TakeCardInfoOperateBll obll = new EmHouse_TakeCardInfoOperateBll();
		boolean flag = false;
		if (dt.isVisible() && (clientuptime == null || clientuptime.equals(""))) {
			Messagebox.show("请输入提交资料时间", "提示", Messagebox.OK, Messagebox.ERROR);
		} else {
			String sqlstr = "";
			String[] str = new String[5];
			for (int i = 0; i < list.size(); i++) {
				EmHouseTakeCardInfoModel m = list.get(i);
				m.setRe_docId(dataId);
				// 助理签收
				if (m.getRe_state().equals("11") || m.getRe_state() == 11) {
					sqlstr = ",Pla_ReceiveTime='" + datechange(clientuptime)
							+ "',Pla_ReceiveName='" + UserInfo.getUsername()
							+ "',Re_State=12";
				}
				// 已交客服
				else if (m.getRe_state().equals("12") || m.getRe_state() == 12) {
					if (statename != null) {
						if (statename.equals("1") || statename == "1") {
							// 状态变为已交客服
							sqlstr = ",Re_State=25";
						} else {
							// 状态变为员工领卡
							types = 2;
							sqlstr = ",Re_name='" + takename + "',Re_time='"
									+ datechange(sendtime) + "',pla_taketype='"
									+ sendtype + "',send_client='"
									+ UserInfo.getUsername() + "',Re_State=14";
						}
					}
				}
				// 客服签收
				else if (m.getRe_state().equals("25") || m.getRe_state() == 25) {
					sqlstr = ",Pla_ClientTTime='" + datechange(clientuptime)
							+ "',Pla_ReceliveClient='" + UserInfo.getUsername()
							+ "',Re_State=13";
				}
				// 员工领卡
				else if (m.getRe_state().equals("13") || m.getRe_state() == 13) {
					sqlstr = ",Re_name='" + takecardname + "',Re_time='"
							+ datechange(clientuptime) + "',pla_taketype='"
							+ taketype + "',send_client='"
							+ UserInfo.getUsername() + "',Re_State=14";
				}
				try {
					if (types == 1) {
						if (m.getRe_taprid() != null) {
							str = obll.updateTakeCardInfo(m, sqlstr, 1);
						} else {
							int k = obll.updateTakeCardInfo(m.getRe_id(),
									sqlstr);
							if (k > 0) {
								flag = true;
							}
						}
					} else if (types == 2) {
						if (m.getRe_taprid() != null) {
							str = obll.TakeCardInfoEdit(m, "2", sqlstr);
						} else {
							int k = obll.updateTakeCardInfo(m.getRe_id(),
									sqlstr);
							if (k > 0) {
								flag = true;
							}
						}
					}
					// 调用内联页方法chkHaveTo(Grid gd)
					if (str[0] == "1") {
						flag = true;
					}
				} catch (Exception e) {
					System.out.println("错误：" + e.getMessage());
					Messagebox.show("操作失败。", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
			if (flag) {
				Messagebox.show("提交成功", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox
						.show(str[1], "操作提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}

	// 判断领卡类型
	private Integer getType(String info) {
		Integer docid = 0;
		if (info != null) {
			if (info.indexOf("建行") >= 0 || info.indexOf("建设") >= 0) {
				docid = 41;
			} else if (info.indexOf("招行") >= 0 || info.indexOf("招行") >= 0) {
				docid = 42;
			}
			// 否则就是默认为中国银行
			else {
				docid = 43;
			}
		} else {
			docid = 43;
		}
		return docid;
	}

	public Integer getDataId() {
		return dataId;
	}

	public void setDataId(Integer dataId) {
		this.dataId = dataId;
	}

	public String getTimename() {
		return timename;
	}

	public void setTimename(String timename) {
		this.timename = timename;
	}

	private String datechange(Date d) {
		String date = "";
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		if (d != null && !d.equals("")) {
			date = time.format(d);
		}
		return date;
	}

	public EmHouseTakeCardInfoModel getModel() {
		return model;
	}

	public void setModel(EmHouseTakeCardInfoModel model) {
		this.model = model;
	}

	// 获取要填写的时间的名称
	private void gettimenames(Integer name) {
		if (name.equals("8") || name == 8) {
			timename = "福利核收资料时间";
		}
		// 已交银行
		else if (name.equals("9") || name == 9) {
			timename = "递交银行时间";
		}
		// 福利领卡
		else if (name.equals("10") || name == 10) {
			timename = "福利领卡时间";
		}
		// 客服助理签收
		else if (name.equals("11") || name == 11) {
			timename = "客服助理签收时间";
		}
		// 客服签收
		else if (name.equals("12") || name == 12) {
			timename = "客服签收时间";
			ifvis = true;
		}
		// 员工领卡
		else if (name.equals("13") || name == 13) {
			timename = "员工领卡时间";
		}

		// 已交客服
		else if (name.equals("25") || name == 25) {
			timename = "客服签收时间";
		}
	}

	@Command
	@NotifyChange("takeinfovis")
	public void infovisible(@BindingParam("cel") Cell cell,
			@BindingParam("takename") Cell takename,
			@BindingParam("takeinfo") Row takeinfo,
			@BindingParam("val") String val) {
		if (val != null && !val.equals("") && val != "") {
			if (val.equals("2") || val == "2") {
				takeinfovis = true;
			} else {
				takeinfovis = false;
			}
		}
	}

	public boolean isIfvis() {
		return ifvis;
	}

	public void setIfvis(boolean ifvis) {
		this.ifvis = ifvis;
	}

	public boolean isTakeinfovis() {
		return takeinfovis;
	}

	public void setTakeinfovis(boolean takeinfovis) {
		this.takeinfovis = takeinfovis;
	}

	public List<String> getBanklist() {
		return banklist;
	}

	public void setBanklist(List<String> banklist) {
		this.banklist = banklist;
	}

}
