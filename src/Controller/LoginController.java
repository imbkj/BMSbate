/**
 * @Classname LoginController
 * @ClassInfo 登录页面控制类
 * @author 李文洁
 * @Date 2013-9-18
 */
package Controller;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import bll.LoginBll;

public class LoginController extends SelectorComposer<Component> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String version;
	@Wire
	private Textbox tbUsername;
	@Wire
	private Textbox tbPassword;

	public LoginController() {
	
		LoginBll bll = new LoginBll();
		
		Session session = Executions.getCurrent().getDesktop().getSession();
// 		String[] message = bll.checkLogin("赵敏捷", "1234567", session); 
 		setVersion("V3.6.28");
// 		Executions.sendRedirect("index.zul");
	}

	@Listen("onClick = #btnLogin")
	public void Login() {
		checkLogin();
	}

	@Listen("onOK = #tbPassword")
	public void passwordOnOk() {
		checkLogin();
	}

	@Listen("onClick = #btnReg")
	public void userReg() {
		Executions.sendRedirect("SystemControl/Pub_RegUser.zul");
	}

	private void checkLogin() {
		LoginBll bll = new LoginBll();
		String[] message = new String[2];
		String username = tbUsername.getValue();
		String password = tbPassword.getValue();
		Session session = Executions.getCurrent().getDesktop().getSession();
		message = bll.checkLogin(username, password, session);
		if (message[0].equals("0") || message[0].equals("2")) {
			Messagebox.show(message[1], "操作提示", Messagebox.OK, Messagebox.NONE);
		} else if (message[0].equals("2")) {
			Messagebox
					.show(message[1], "操作提示", Messagebox.OK, Messagebox.ERROR);
		} else if (message[0].equals("1")) {
			// SystemTrayDemo std = new SystemTrayDemo("测试");
			Executions.sendRedirect("index.zul");
		}
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
