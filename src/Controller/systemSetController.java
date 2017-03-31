package Controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zul.Messagebox;

import Model.LoginModel;
import Util.LoginInfoStatic;
import Util.PublishWindow;
import Util.PublishWindow.openWindow;

public class systemSetController {
	// window属性
	private String title;
	private String height;
	private String width;
	private String position;
	private String closable;
	private String openMethod;
	private List<LoginModel> logList;
	private String sysMessage;
	private String loginSet;
	private String loginMessage;

	public systemSetController() {
		title = "系统提示";
		height = "100px";
		width = "300px";
		position = "center";
		closable = "无";
		loginSet = "开放登录";
		openMethod = "doOverlapped";
		sysMessage = "";
		loginMessage = "";

		if ("".equals(LoginInfoStatic.getLoginMessage())
				|| LoginInfoStatic.getLoginMessage() == null) {
			loginSet = "开放登录";
		} else {
			loginSet = "禁止登录";
		}
		// 获取登录信息
		getLoginList();
		
		
		Boolean loginMes = LoginInfoStatic.getSetupdatestate();
		
		if (loginMes)
		{
			Messagebox.show("字典库更新中，请稍后重启。", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
		
	}

	@Command("sendMessage")
	public void sendMessage(@BindingParam("sendName") Integer logid) {
		try {
			if (sysMessage == null || "".equals(sysMessage)) {
				Messagebox.show("请填写通知信息内容。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
			if (logid == null) {
				Messagebox.show("请选择接收人。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			} else {
				PublishWindow pw = new PublishWindow();
				pw.setTitle(title);
				pw.setHeight(height);
				pw.setWidth(width);
				pw.setPosition(position);

				if ("有".equals(closable)) {
					pw.setClosable(true);
				} else {
					pw.setClosable(false);
				}
				switch (openMethod) {
				case "doModal":
					pw.setOpenMethod(openWindow.doModal);
					break;
				case "doPopup":
					pw.setOpenMethod(openWindow.doPopup);
					break;
				case "doOverlapped":
					pw.setOpenMethod(openWindow.doOverlapped);
					break;
				default:
					pw.setOpenMethod(openWindow.doModal);
					break;
				}
				if (logid == 0) {
					pw.publish("all", sysMessage, null, null);
				} else {
					pw.publish("Uid" + logid, sysMessage, null, null);
				}
			}
		} catch (Exception e) {
			Messagebox.show("操作出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	@Command("setLogin")
	public void setLogin() {
		try {
			if ("禁止登录".equals(loginSet)) {
				if ("".equals(loginMessage) || loginMessage == null) {
					Messagebox.show("禁止登录需填写提示信息。", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
					return;
				} else {
					LoginInfoStatic.setLoginMessage(loginMessage);
				}
			} else {
				LoginInfoStatic.setLoginMessage("");
			}
			Messagebox.show("操作成功，已" + loginSet + "。", "操作提示", Messagebox.OK,
					Messagebox.NONE);
		} catch (Exception e) {
			Messagebox.show("操作出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	// 获取登录信息
	private void getLoginList() {
		Map<String, LoginModel> loginMap = LoginInfoStatic.getLoginMap();
		Map<Integer, String> userMap = new HashMap<Integer, String>();
		logList = new ArrayList<LoginModel>();
	
		// 添加一个所有人选项
		LoginModel all = new LoginModel();
		all.setLog_id(0);
		all.setLog_name("所有人");
		logList.add(all);

		// Map转List
		Collection<LoginModel> collection = loginMap.values();
		for (LoginModel m : collection) {
			if (!userMap.containsKey(m.getLog_id())) {
				logList.add(m);
				userMap.put(m.getLog_id(), "");
			}
		}
		
		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getClosable() {
		return closable;
	}

	public void setClosable(String closable) {
		this.closable = closable;
	}

	public String getOpenMethod() {
		return openMethod;
	}

	public void setOpenMethod(String openMethod) {
		this.openMethod = openMethod;
	}

	public List<LoginModel> getLogList() {
		return logList;
	}

	public void setLogList(List<LoginModel> logList) {
		this.logList = logList;
	}

	public String getSysMessage() {
		return sysMessage;
	}

	public void setSysMessage(String sysMessage) {
		this.sysMessage = sysMessage;
	}

	public String getLoginSet() {
		return loginSet;
	}

	public void setLoginSet(String loginSet) {
		this.loginSet = loginSet;
	}

	public String getLoginMessage() {
		return loginMessage;
	}

	public void setLoginMessage(String loginMessage) {
		this.loginMessage = loginMessage;
	}

}
