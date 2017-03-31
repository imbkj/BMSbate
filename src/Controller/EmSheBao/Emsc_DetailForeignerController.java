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
import org.zkoss.zul.Grid;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import dal.LoginDal;

import service.MessageService;

import bll.EmSheBao.EmSheBao_DSelectBll;
import bll.EmSheBao.Emsc_DeclareOperateBll;
import bll.EmSheBao.Emsc_DeclareSelBll;
import bll.SysMessage.Message_SelectBll;
import bll.SysMessage.SysMessage_AddBll;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.EmShebaoChangeForeignerModel;
import Model.LoginModel;
import Model.SysMessageModel;
import Util.UserInfo;

public class Emsc_DetailForeignerController {
	private DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
	private final int id = Integer.parseInt(Executions.getCurrent().getArg()
			.get("daid").toString());
	private EmShebaoChangeForeignerModel efModel;
	private Emsc_DeclareSelBll selbll;
	// 页面值：原因
	private String reason;

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
	private String old_ifdeclare = "";

	public Emsc_DetailForeignerController() {
		selbll = new Emsc_DeclareSelBll();
		efModel = selbll.getForeignerChangeById(id);
		// 原数据状态
		old_ifdeclare = efModel.getEmsc_ifdeclare();

		// 系统短信
		List<LoginModel> llist = new ArrayList<LoginModel>();
		LoginModel m = new LoginModel();
		m.setLog_name(efModel.getEmsc_addname());
		m.setLog_id(d.getUserIDByname(efModel.getEmsc_addname()));
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
		msgservice = new MessageImpl("EmShebaoChangeForeigner", id);
		deptlist = sbll.getLoginList(msgname);
		list = msgservice.Select();
		// 把登陆人的该业务ID的未读信息改为已读
		msgservice.Read();
	}

	// 添加备忘
	@Command("addFlag")
	public void addFlag(@BindingParam("flag") Textbox txtFlag) {
		if (!"".equals(txtFlag.getValue())) {
			if (!txtFlag.getValue().equals(efModel.getEmsc_flag())) {
				Emsc_DeclareOperateBll bll = new Emsc_DeclareOperateBll();
				efModel.setEmsc_flag(txtFlag.getValue());
				int i = bll.UpForeignerFlag(efModel, UserInfo.getUsername());
				if (i == 1) {
					Messagebox.show("备忘添加成功。", "操作提示", Messagebox.OK,
							Messagebox.NONE);
				} else {
					Messagebox.show("备忘添加失败。", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				Messagebox.show("您并未修改备忘信息，无需添加。", "操作提示", Messagebox.OK,
						Messagebox.NONE);
			}
		} else {
			Messagebox
					.show("请输入备忘信息。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 修改状态
	@Command("upState")
	public void upState(@BindingParam("state") Radiogroup rgState,
			@BindingParam("win") Window win, @BindingParam("cb") Checkbox cb) {
		try {
			String state = rgState.getSelectedItem().getValue().toString();

			if (!old_ifdeclare.equals(state)) {

				// 退回时录入原因
				if ("3".equals(state)) {
					if (selectedlist.size() <= 0) {
						Messagebox.show("请选择退回短信收件人。", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
						return;
					} else if (reason == null || "".equals(reason)) {
						Messagebox.show("请填写退回原因。", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
						return;
					} else {
						efModel.setEmsc_reason(reason);
					}
				}
				efModel.setEmsc_ifdeclare(state);
				Emsc_DeclareOperateBll opbll = new Emsc_DeclareOperateBll();
				String[] mes = opbll.ForeignerDeclareUpState(efModel,
						UserInfo.getUsername());
				if ("1".equals(mes[0])) {
					// 退回系统短信
					if ("3".equals(state)) {
						EmSheBao_DSelectBll dsbll = new EmSheBao_DSelectBll();
						for (LoginModel m : selectedlist) {
							SysMessageModel model = new SysMessageModel();
							model.setSyme_content(reason);// 短信内容
							model.setSyme_log_id(Integer.parseInt(UserInfo
									.getUserid()));// 发件人id
							model.setSymr_name(m.getLog_name());// 收件人姓名
							model.setSymr_log_id(m.getLog_id());
							model.setEmail(1);// 1表示同时发邮件
							model.setSyme_title(efModel.getEmsc_company()
									+ " 的 " + efModel.getEmsc_name()
									+ " 社保外籍人新参保数据被退回");
							// 调用方法
							msgservice.Add(model);
						}
					}

					// 成功提示
					Messagebox.show(mes[1], "操作提示", Messagebox.OK,
							Messagebox.NONE);
					win.detach();
				} else {
					// 错误提示
					Messagebox.show(mes[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}

			} else {
				// 弹出提示
				Messagebox.show("状态未操作变更，请选择状态！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}

		} catch (Exception e) {
			Messagebox.show("操作出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 更新材料
	@Command("upDocData")
	public void upDocData(@BindingParam("gd") Grid gd,
			@BindingParam("win") final Window win) throws Exception {
		try {
			// 调用内联页方法
			String[] message = docOC.UpsubmitDocHO(gd, String.valueOf(id));

			if (message[0].equals("1")) {
				EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.OK.equals(event.getButton())) {
							win.detach();
						}
					}
				};
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
			} else {
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			Messagebox.show("操作失败。", "操作提示", Messagebox.OK, Messagebox.ERROR);
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

	public EmShebaoChangeForeignerModel getEfModel() {
		return efModel;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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
}
