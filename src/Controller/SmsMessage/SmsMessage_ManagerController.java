package Controller.SmsMessage;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zul.Window;

import Util.UserInfo;
import bll.SmsMessage.SmsMessageManagerBll;

public class SmsMessage_ManagerController extends SelectorComposer<Component> {

	private Integer revSms;
	private Integer sendSms;
	private String newSms;
	private Integer userId = Integer.valueOf(UserInfo.getUserid());
	private SmsMessageManagerBll bll = new SmsMessageManagerBll();

	public SmsMessage_ManagerController() {
		userId = 198;
		setRevSms();
		setSendSms();
		setNewSms();
	}

	@Listen("onClick=#rec")
	public void linkReceive() {
		
		Window window = (Window) Executions.createComponents(
				"/smsMessage/SmsMessage_receive.zul", null, null);
		window.doModal();
	}
	
	@Listen("onClick=#sms")
	public void linkSms() {
		
		Window window = (Window) Executions.createComponents(
				"/Embase/SMS_Send.zul", null, null);
		window.doModal();
	}
	
	@Listen("onClick=#send")
	public void linkSend() {
		
		Window window = (Window) Executions.createComponents(
				"/smsMessage/SmsMessage_send.zul", null, null);
		window.doModal();
	}

	public Integer getRevSms() {
		return revSms;
	}

	public void setRevSms() {
		this.revSms = bll.getRecMessageCount(userId);
	}

	public Integer getSendSms() {
		return sendSms;
	}

	public void setSendSms() {
		this.sendSms = bll.getSendMessageCount(userId);
	}

	public String getNewSms() {
		return newSms;
	}

	public void setNewSms() {
		Integer i = 0;
		i = bll.getNewMessageCount(userId);
		this.newSms = i == 0 ? "提示:暂时没有新的短信" : "你有 " + i + " 条新短信";
	}

}
