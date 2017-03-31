package Controller.EmSheBao;

import java.util.ArrayList;
import java.util.List;

import impl.MessageImpl;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import dal.LoginDal;

import service.MessageService;

import Model.EmSheBaoChangeModel;
import Model.LoginModel;
import Model.SysMessageModel;
import Util.UserInfo;
import bll.EmSheBao.EmSheBao_DOperateBll;
import bll.EmSheBao.EmSheBao_DSelectBll;
import bll.SysMessage.Message_SelectBll;
import bll.SysMessage.SysMessage_AddBll;

public class Emsc_DetailController {
	private EmSheBao_DSelectBll dsbll = new EmSheBao_DSelectBll();
	private EmSheBao_DOperateBll dobll = new EmSheBao_DOperateBll();
	private EmSheBaoChangeModel sbData;
	private String id = Executions.getCurrent().getArg().get("daid").toString();

	// 获取用户名
	String username = UserInfo.getUsername();
	String userid = UserInfo.getUserid();

	// 系统短信
	private SysMessage_AddBll smbll = new SysMessage_AddBll();
	private Message_SelectBll sbll = new Message_SelectBll();
	private List<LoginModel> loginList = smbll.getLoginList(" and log_id<>"
			+ UserInfo.getUserid());
	private List<LoginModel> deptlist = new ArrayList<LoginModel>();
	private String selectedname = "";
	private List<LoginModel> selectedlist = new ArrayList<LoginModel>();
	private MessageService msgservice;
	private LoginModel ml = new LoginModel();
	private String msgname = "";
	private List<SysMessageModel> list = new ArrayList<SysMessageModel>();
	private List<SysMessageModel> temList = sbll
			.gettemList(" and pmte_class='社会保险'");
	private LoginDal d = new LoginDal();
	private String reason = "";
	private String old_ifdeclare = "";

	public Emsc_DetailController() {
		// 获取页面数据
		sbData = dsbll.getEmSCData(" AND a.id=" + id, "").get(0);
		// 旧状态
		old_ifdeclare = sbData.getEmsc_ifdeclare();

		// 系统短信
		List<LoginModel> llist = new ArrayList<LoginModel>();
		LoginModel m = new LoginModel();
		m.setLog_name(sbData.getEmsc_addname());
		m.setLog_id(d.getUserIDByname(sbData.getEmsc_addname()));
		llist.add(m);

		if (llist.size() > 0) {
			for (LoginModel lm : llist) {
				if (lm.getLog_name() != null) {
					ml = lm;
					selectedlist.add(ml);
					getSelectname();
					if (msgname == "") {
						msgname = "'" + lm.getLog_name() + "'";
					} else {
						msgname = msgname + ",'" + lm.getLog_name() + "'";
					}
				}
			}
		}
		msgservice = new MessageImpl("EmShebaoChange", Integer.parseInt(id));
		deptlist = sbll.getLoginList(msgname);
		list = msgservice.Select();
		// 把登陆人的该业务ID的未读信息改为已读
		msgservice.Read();
	}

	@Command("changeState")
	public void changeState(
			@BindingParam("winDetailData") final Window winDetailData,
			@BindingParam("flag") Textbox flag,
			@BindingParam("rg") Radiogroup rg,
			@BindingParam("epms_content") Textbox epms_content,
			@BindingParam("cb") Checkbox cb) {

		if (!rg.getSelectedItem().getValue().equals(old_ifdeclare)) {

			if (rg.getSelectedItem().getValue().equals("0")
					|| rg.getSelectedItem().getValue().equals("2")
					|| ((rg.getSelectedItem().getValue().equals("3") || rg
							.getSelectedItem().getValue().equals("13")) && !""
							.equals(epms_content.getValue()))
					|| (rg.getSelectedItem().getValue().equals("1") && !""
							.equals(flag.getValue()))) {

				String[] message;
				int emsc_tapr_id;
				emsc_tapr_id = sbData.getEmsc_tapr_id();

				if (rg.getSelectedItem().getValue().equals("13")) {// 判断是否退回并终止任务单
					sbData.setEmsc_remark(epms_content.getValue());
					message = dobll.returnStopChange(sbData);
				} else {
					int cid = sbData.getCid();
					message = dobll.declareChangeState(Integer.parseInt(id),
							emsc_tapr_id, rg.getSelectedItem().getValue()
									.toString(), flag.getValue(), username,
							cid, epms_content.getValue());
				}

				// 判断是否成功
				if (message[0].equals("1")) {
					// 插入退回小信封
					if (rg.getSelectedItem().getValue().equals("3")
							|| rg.getSelectedItem().getValue().equals("13")) {
						for (LoginModel m : selectedlist) {
							SysMessageModel model = new SysMessageModel();
							model.setSyme_content(epms_content.getValue());// 短信内容
							model.setSyme_log_id(Integer.parseInt(userid));// 发件人id
							model.setSymr_name(m.getLog_name());// 收件人姓名
							model.setSymr_log_id(m.getLog_id());
							if (cb.isChecked()) {
								model.setEmail(1);// 1表示同时发邮件
							} else {
								model.setEmail(0);
							}
							model.setSyme_title(sbData.getEmsc_company()
									+ " 的 " + sbData.getEmsc_name()
									+ " 社保变更数据被退回");
							// 调用方法
							msgservice.Add(model);
						}
					}

					EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
						public void onEvent(ClickEvent event) throws Exception {
							if (Messagebox.Button.OK.equals(event.getButton())) {
								winDetailData.detach();
							}
						}
					};
					// 弹出提示
					Messagebox.show(message[1], "操作提示",
							new Messagebox.Button[] { Messagebox.Button.OK },
							Messagebox.INFORMATION, clickListener);
				} else {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}

			} else {
				if (rg.getSelectedItem().getValue().equals("1")
						&& "".equals(flag.getValue())) {
					// 弹出提示
					Messagebox.show("修改状态为已申报，必须输入备忘信息!", "操作提示",
							Messagebox.OK, Messagebox.ERROR);
				} else if (rg.getSelectedItem().getValue().equals("3")
						&& selectedlist.size() <= 0) {
					// 弹出提示
					Messagebox.show("请选择退回短信收件人!", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				} else if ((rg.getSelectedItem().getValue().equals("3") || rg
						.getSelectedItem().getValue().equals("13"))
						&& "".equals(epms_content.getValue())) {
					// 弹出提示
					Messagebox.show("退回数据必须输入退回原因!", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				} else {
					// 弹出提示
					Messagebox.show("请选择“申报状态”", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}

		} else {
			// 弹出提示
			Messagebox.show("状态未操作变更，请选择状态！", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}

	}

	@Command("addFlag")
	public void addFlag(
			@BindingParam("winDetailData") final Window winDetailData,
			@BindingParam("flag") Textbox flag) {
		if (!"".equals(flag.getValue())) {
			String[] message;

			message = dobll.declareAddFlag(Integer.parseInt(id),
					flag.getValue(), username);

			// 判断是否成功
			if (message[0].equals("1")) {
				EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.OK.equals(event.getButton())) {
							winDetailData.detach();
						}
					}
				};
				// 弹出提示
				Messagebox.show(message[1], "操作提示",
						new Messagebox.Button[] { Messagebox.Button.OK },
						Messagebox.INFORMATION, clickListener);
			} else {
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			// 弹出提示
			Messagebox.show("请输入“备忘”内容", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 选择收件人
	@Command
	@NotifyChange("selectedname")
	public void checkname(@BindingParam("bd") Bandbox bx,
			@BindingParam("model") LoginModel model,
			@BindingParam("lbitem") Listitem item) {
		if (item.isSelected()) {
			selectedlist.add(model);
		} else {
			selectedlist.remove(model);
		}
		getSelectname();
	}

	// 把已选择的收件人串起来
	private void getSelectname() {
		selectedname = "";
		for (LoginModel m : selectedlist) {
			if (selectedname == "") {
				selectedname = m.getLog_name();
			} else {
				selectedname = selectedname + ";" + m.getLog_name();
			}
		}
	}

	// 选择模板
	@Command("temselect")
	@NotifyChange("reason")
	public void temselect(@BindingParam("temcb") Combobox temcb) {
		if (temcb.getValue() != null || !temcb.getValue().equals("")) {
			reason = "";
			SysMessageModel m = temcb.getSelectedItem().getValue();
			reason = m.getPmte_content();
		}
	}

	public EmSheBaoChangeModel getSbData() {
		return sbData;
	}

	public void setSbData(EmSheBaoChangeModel sbData) {
		this.sbData = sbData;
	}

	public List<LoginModel> getLoginList() {
		return loginList;
	}

	public void setLoginList(List<LoginModel> loginList) {
		this.loginList = loginList;
	}

	public List<LoginModel> getDeptlist() {
		return deptlist;
	}

	public void setDeptlist(List<LoginModel> deptlist) {
		this.deptlist = deptlist;
	}

	public String getSelectedname() {
		return selectedname;
	}

	public void setSelectedname(String selectedname) {
		this.selectedname = selectedname;
	}

	public List<SysMessageModel> getList() {
		return list;
	}

	public void setList(List<SysMessageModel> list) {
		this.list = list;
	}

	public List<SysMessageModel> getTemList() {
		return temList;
	}

	public void setTemList(List<SysMessageModel> temList) {
		this.temList = temList;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
