package Controller.Embase;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Window;

import Model.EmbaseModel;
import Model.SMSModel;
import Util.UserInfo;
import bll.Embase.EmbaseListBll;
import bll.Embase.SMS_CheckBll;

public class SMS_AddController {
	private List<SMSModel> smslist = new ListModelList<SMSModel>();
	private List<SMSModel> smsclasslist = new ListModelList<SMSModel>();
	private List<SMSModel> smsbase = new ListModelList<SMSModel>();
	SMS_CheckBll bll = new SMS_CheckBll();
	String mobile = "";
	String sms_a_content = "";
	String gid = null;
	
	private EmbaseListBll mbll=new EmbaseListBll();
	private EmbaseModel model= new EmbaseModel();

	// 获取用户名
	String username = UserInfo.getUsername();

	public SMS_AddController() throws SQLException {
		 
//		if (Executions.getCurrent().getArg().get("mobile") != null) {
//			mobile = Executions.getCurrent().getArg().get("mobile").toString();
//			//System.out.println(mobile);
//		}
		
		if (Executions.getCurrent().getArg().get("gid") != null) {
			gid = Executions.getCurrent().getArg().get("gid").toString();
			
			System.out.println(gid);
			
			model=mbll.getEmbaseInfo("  and a.gid="+gid);
			mobile=model.getEmba_mobile();
		}
		if (Executions.getCurrent().getArg().get("mobile") != null) {
			mobile=Executions.getCurrent().getArg().get("mobile").toString();
			model.setEmba_mobile(mobile);
		}
		smslist = new ListModelList<SMSModel>(bll.getsmslist(gid));// 获取短信内容

		smsclasslist = new ListModelList<SMSModel>(bll.getsmsclasslist("",""));// 获取短信模板内容
		
		//smsbase = new ListModelList<SMSModel>(bll.getsmsbase(username));// 获取短信模板内容

		setSms_a_content("");
	}

	@Command("smsadd")
	@NotifyChange("smslist")
	public void smsadd(@BindingParam("a_mobile") Textbox a_mobile,
			@BindingParam("a_content") Textbox a_content,
			@BindingParam("SMSwin") Window win) throws Exception {
		
		if (a_content.getValue().length()>180) {
			Messagebox.show("短信内容超出字数限制，无法发送短信。", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}
		
		if (!"".equals(mobile))
		{

			String message = bll.sms_add(a_mobile.getValue(), a_content.getValue(),
					username);
			if (message.equals("1")) {
				EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.OK.equals(event.getButton())) {
							// Executions.sendRedirect("Compact_SginList.zul");
							// //跳转页面
							// w1.detach();
							smslist = new ListModelList<SMSModel>(bll.getsmslist(mobile));// 获取短信内容
						}
					}
				};
				Messagebox.show("发送成功！", "操作提示",
						new Messagebox.Button[] { Messagebox.Button.OK },
						Messagebox.INFORMATION, clickListener);
//				if (gid != null && gid.equals("gid")) {
//					//win.detach();
//				} else {
//					// 弹出提示
//					//Executions.sendRedirect("../EmBase/Embase_editlist.zul");
//				}
			} else {
				// 弹出提示
				Messagebox.show("发送失败！", "操作提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
		else
		{
			Messagebox.show("请先将员工手机号码填入员工基本信息！", "操作提示", Messagebox.OK, Messagebox.INFORMATION);
			
		}

	}
	
	@Command("smssend")
	@NotifyChange("smslist")
	public void smssend(@BindingParam("a_mobile") Textbox a_mobile,
			@BindingParam("a_content") Textbox a_content,
			@BindingParam("SMSwin") final Window win) throws Exception {
		
		if (a_content.getValue().length()>180) {
			Messagebox.show("短信内容超出字数限制，无法发送短信。", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}
		
		if (!a_mobile.getValue().equals(""))
		{

			String message = bll.sms_add(a_mobile.getValue(), a_content.getValue(),
					username);
			if (message.equals("1")) {
				EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.OK.equals(event.getButton())) {
							// Executions.sendRedirect("Compact_SginList.zul");
							// //跳转页面
							win.detach();
							//smslist = new ListModelList<SMSModel>(bll.getsmslist(a_mobile.getValue().toString()));// 获取短信内容
						}
					}
				};
				Messagebox.show("发送成功！", "操作提示",
						new Messagebox.Button[] { Messagebox.Button.OK },
						Messagebox.INFORMATION, clickListener);
//				if (gid != null && gid.equals("gid")) {
//					//win.detach();
//				} else {
//					// 弹出提示
//					//Executions.sendRedirect("../EmBase/Embase_editlist.zul");
//				}
			} else {
				// 弹出提示
				Messagebox.show("发送失败！", "操作提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
		else
		{
			Messagebox.show("请先将员工手机号码填入员工基本信息！", "操作提示", Messagebox.OK, Messagebox.INFORMATION);
			
		}

	}

	@Command("smstype")
	@NotifyChange("sms_a_content")
	public void smstype(@BindingParam("a_type") Textbox a_type,@BindingParam("a_mobile") Textbox a_mobile)
			throws Exception {
		smsclasslist = new ListModelList<SMSModel>(bll.getsmsclasslistcon(a_type
				.getValue(),a_mobile.getValue()));// 获取短信模板内容
		setSms_a_content(smsclasslist.get(0).getSms_content());
	}
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public List<SMSModel> getSmslist() {
		return smslist;
	}

	public void setSmslist(List<SMSModel> smslist) {
		this.smslist = smslist;
	}

	public List<SMSModel> getSmsclasslist() {
		return smsclasslist;
	}

	public void setSmsclasslist(List<SMSModel> smsclasslist) {
		this.smsclasslist = smsclasslist;
	}

	public String getSms_a_content() {
		return sms_a_content;
	}

	public void setSms_a_content(String sms_a_content) {
		this.sms_a_content = sms_a_content;
	}

}
