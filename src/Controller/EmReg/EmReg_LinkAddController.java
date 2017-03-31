package Controller.EmReg;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmDhModel;
import Model.EmbaseModel;
import Model.SmsSendRecModel;
import Util.UserInfo;
import bll.EmCensus.EmDh.EmDh_SelectBll;
import bll.SmsMessage.SmsMessageManagerBll;

public class EmReg_LinkAddController {
	int gid = (Integer) Executions.getCurrent().getArg().get("gid");
	private int messagenum = 140;
	private SmsMessageManagerBll bll = new SmsMessageManagerBll();
	private List<EmbaseModel> emlist = bll.getembaList(" and gid=" + gid);
	private EmbaseModel emmodel = new EmbaseModel();

	private SmsMessageManagerBll smsbll = new SmsMessageManagerBll();

	private EmDh_SelectBll bl = new EmDh_SelectBll();

	private List<SmsSendRecModel> list = new ArrayList<SmsSendRecModel>();

	public EmReg_LinkAddController() {
		if (!emlist.isEmpty()) {
			emmodel = emlist.get(0);
		}
		list= smsbll.getSendrecList(gid);
	}

	@Command
	@NotifyChange("messagenum")
	public void changenum(@BindingParam("val") String val) {
		int k = val.length();
		messagenum = 140 - k;
	}

	@Command
	@NotifyChange("list")
	public void addSysmessage(@BindingParam("mobile") String mobile,
			@BindingParam("message") String message,
			@BindingParam("win") Window win) {
		if (mobile == null || mobile.equals("") || mobile == "") {
			Messagebox.show("该员工没有手机号码，请先录入手机号码", "提示", Messagebox.OK,
					Messagebox.ERROR);
		} else if (message == null || message.equals("") || message == "") {
			Messagebox.show("请填写短信内容", "提示", Messagebox.OK, Messagebox.ERROR);
		} else {
			int k = bll.addMessage(gid, mobile, message,
					Integer.parseInt(UserInfo.getUserid()),
					UserInfo.getUsername(), 2);
			if (k > 0) {
				Clients.showNotification("发送成功",
						"info", win, "middle_center",
						3000);
				list= smsbll.getSendrecList(gid);
			} else {
				Messagebox.show("发送失败", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}

	public EmbaseModel getEmmodel() {
		return emmodel;
	}

	public void setEmmodel(EmbaseModel emmodel) {
		this.emmodel = emmodel;
	}

	public int getMessagenum() {
		return messagenum;
	}

	public void setMessagenum(int messagenum) {
		this.messagenum = messagenum;
	}

	public List<SmsSendRecModel> getList() {
		return list;
	}

	public void setList(List<SmsSendRecModel> list) {
		this.list = list;
	}
	
}
