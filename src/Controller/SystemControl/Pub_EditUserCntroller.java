package Controller.SystemControl;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import bll.SystemControl.Login_SelectBll;
import bll.SystemControl.PubRegUserBll;
import Model.LoginModel;
import Util.MD5;
import Util.UserInfo;

public class Pub_EditUserCntroller {
	private String username;
	private String userid;
	private LoginModel m = new LoginModel();
	private Login_SelectBll bll = new Login_SelectBll();
	private String newPassWord = "", rePassWord = "", oldPassWord = "";

	public Pub_EditUserCntroller() {
		username = UserInfo.getUsername();
		userid = UserInfo.getUserid();
		m = bll.getLoginInfoByUserId(userid);
	}

	@Command
	public void loadwin(@BindingParam("win") Window win) {
		if (UserInfo.getUsername() == null || UserInfo.getUsername().equals("")
				|| UserInfo.getUserid() == null
				|| UserInfo.getUserid().equals("")) {
			Messagebox.show("请先登录", "提示", Messagebox.OK, Messagebox.ERROR);
			Executions.sendRedirect("../index.zul");
		}
	}

	@Command
	public void summit(@BindingParam("newpassword") Textbox newpassword,
			@BindingParam("renewpassword") Textbox renewpassword) {
		String md5OldPassWord = MD5.GetMD5Code(oldPassWord);
		if (!md5OldPassWord.equals(m.getLog_pws())) {
			Messagebox.show("原密码不正确", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if (newPassWord == null || newPassWord.equals("")) {
			Clients.showNotification("新密码不能为空", "info", newpassword,
					"end_center", 2000);
			return;
		}
		if (newPassWord.length()<6) {
			Clients.showNotification("密码长度不能小于6", "info", newpassword,
					"end_center", 2000);
			return;
		}
		if (rePassWord == null || rePassWord.equals("")) {
			Clients.showNotification("确认密码不能为空", "info", newpassword,
					"end_center", 2000);
			return;
		}
		if (!rePassWord.equals(newPassWord)) {
			Clients.showNotification("新密码与确认密码不相符", "info", renewpassword,
					"end_center", 2000);
			return;
		}
		// 把新密码做MD5加密
		String md5PassWord = MD5.GetMD5Code(newPassWord);
		PubRegUserBll bll = new PubRegUserBll();
		m.setLog_pws(md5PassWord);
		Integer k = bll.User_Edit(m);
		if (k > 0) {
			Messagebox
					.show("修改成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
//			Session session = Executions.getCurrent().getDesktop().getSession();
//			session.setAttribute("userid", "");
//			session.setAttribute("username", "");
//			session.setAttribute("depid", "");
//			session.setAttribute("rolname", "");
			Executions.sendRedirect("../SystemControl/Pub_EditUser.zul");
		} else {
			Messagebox.show("修改失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public LoginModel getM() {
		return m;
	}

	public void setM(LoginModel m) {
		this.m = m;
	}

	public String getNewPassWord() {
		return newPassWord;
	}

	public void setNewPassWord(String newPassWord) {
		this.newPassWord = newPassWord;
	}

	public String getRePassWord() {
		return rePassWord;
	}

	public void setRePassWord(String rePassWord) {
		this.rePassWord = rePassWord;
	}

	public String getOldPassWord() {
		return oldPassWord;
	}

	public void setOldPassWord(String oldPassWord) {
		this.oldPassWord = oldPassWord;
	}

}
