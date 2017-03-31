package Controller.SmsMessage;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.SmsMessage.SmsMessageManagerBll;

import Model.AlarmClassModel;
import Model.PubMobileIdModel;
import Model.SmsSendRecModel;
import Util.UserInfo;

public class SmsMessage_addController extends SelectorComposer<Component> {
	private List<SmsSendRecModel> smsList;
	private List<PubMobileIdModel> mobileList;

	private String phoneNum;
	private String phoneAddress;
	private String city;

	private SmsMessageManagerBll bll = new SmsMessageManagerBll();
	private Integer gid = 13709;// Integer.parseInt(Executions.getCurrent().getArg().get("gid").toString());

	public SmsMessage_addController() {
		setSmsList(gid);
		setMobileList(smsList.get(0).getPhoneNumber());
		setPhoneNum();
		setPhoneAddress();
		setCity();
	}

	@Command("submit")
	@NotifyChange("smsList")
	public void sendMessage(@BindingParam("a") final Textbox tb) {
		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub
						if (Messagebox.Button.OK.equals(event.getButton())) {
							Integer i = bll.addMessage(gid, phoneNum,
									tb.getValue(),
									Integer.valueOf(UserInfo.getUserid()),
									UserInfo.getUsername(), 1);
							if (i>0) {
								Messagebox.show("操作成功", "提示", Messagebox.OK,
										Messagebox.INFORMATION);
							}else {
								Messagebox.show("操作失败", "提示", Messagebox.OK,
										Messagebox.ERROR);
							}
						}
					}
				});
	}

	public List<SmsSendRecModel> getSmsList() {
		return smsList;
	}

	public void setSmsList(Integer gid) {
		this.smsList = bll.getSendrecList(gid);
	}

	public List<PubMobileIdModel> getMobileList() {
		return mobileList;
	}

	/**
	 * @Title: setMobileList
	 * @Description: TODO(临时从短信表获取号码,待雇员表设计好进行替换)
	 * @return void 返回类型
	 * @throws
	 */
	public void setMobileList(String num) {
		this.mobileList = bll.getPhoneInfo(num);
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum() {
		this.phoneNum = smsList.get(0).getPhoneNumber().toString();
	}

	public String getPhoneAddress() {
		return phoneAddress;
	}

	public void setPhoneAddress() {
		this.phoneAddress = mobileList.get(0).getPmid_cardtype();
	}

	public String getCity() {
		return city;
	}

	public void setCity() {
		this.city = mobileList.get(0).getPmid_city();
	}

}
