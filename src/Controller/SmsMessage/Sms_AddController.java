package Controller.SmsMessage;

import java.sql.SQLException;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.SmsMessage.Sms_GroupSendBll;

import Model.SMSModel;
import Util.UserInfo;

public class Sms_AddController {
	private String content;
	private String emba_name;
	private String mobile;
	private String username = UserInfo.getUsername();
	
	@Command
	@NotifyChange("content")
	public void send(@BindingParam("win") Window win)
	{
		if(mobile==null||mobile.equals(""))
		{
			Messagebox.show("手机号码不能为空", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if(content==null||content.equals(""))
		{
			Messagebox.show("短信内容不能为空", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		SMSModel model = new SMSModel();
		model.setSendname(username);
		model.setMobile(mobile);
		model.setContent(content + "【深圳中智】");
		Sms_GroupSendBll bll = new Sms_GroupSendBll();
		try {
			int k=bll.SmsSend(model);
			if(k>0)
			{
				content = "";
				Clients.showNotification("发送成功", "info", win, "middle_center",
						3000);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getEmba_name() {
		return emba_name;
	}
	public void setEmba_name(String emba_name) {
		this.emba_name = emba_name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	
	
	
	
}
