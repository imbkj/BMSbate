package Controller.SysMessage;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmbaseModel;
import Util.UserInfo;
import bll.SmsMessage.SmsMessageManagerBll;

public class SysMessage_AddInfoController {
	int gid=(Integer) Executions.getCurrent().getArg().get("gid");
	private int messagenum=140;
	SmsMessageManagerBll bll=new SmsMessageManagerBll();
	List<EmbaseModel> emlist=bll.getembaList(" and gid="+gid);
	EmbaseModel emmodel=new EmbaseModel();
	
	public SysMessage_AddInfoController()
	{
		if(!emlist.isEmpty())
		{
			emmodel=emlist.get(0);
		}
	}
	
	@Command
	@NotifyChange("messagenum")
	public void changenum(@BindingParam("val") String val)
	{
		int k=val.length();
		messagenum=140-k;
	}
	
	@Command
	public void addSysmessage(@BindingParam("mobile") String mobile,@BindingParam("message") String message,
			@BindingParam("win") Window win)
	{
		if(mobile==null||mobile.equals("")||mobile=="")
		{
			Messagebox.show("该员工没有手机号码，请先录入手机号码", "提示", Messagebox.OK, Messagebox.ERROR);
		}
		else if(message==null||message.equals("")||message=="")
		{
			Messagebox.show("请填写短信内容", "提示", Messagebox.OK, Messagebox.ERROR);
		}
		else
		{
			int k=bll.addMessage(gid, mobile, message, Integer.parseInt(UserInfo.getUserid()), UserInfo.getUsername(), 2);
			if(k>0)
			{
				Messagebox.show("发送成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
				win.detach();
			}
			else
			{
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
}
