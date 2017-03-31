package Controller.SystemControl;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import Model.DepartmentModel;
import Model.LoginModel;
import Model.RoleModel;
import Util.MD5;
import bll.SystemControl.Login_SelectBll;
import bll.SystemControl.PubRegUserBll;

public class Pub_PortionUserController {
	private String username;
	private LoginModel m = new LoginModel();
	private Login_SelectBll bll = new Login_SelectBll();
	private String newPassWord = "";
	private String rePassWord = "";
	private List<LoginModel> loginlist = bll.getLoginList("");
	private List<LoginModel> leaderlist = new ArrayList<LoginModel>();
	private List<DepartmentModel> deplist = bll.getDepartmentList();
	private List<RoleModel> rolelist = bll.getRoleList();

	// 选择用户
	@Command
	@NotifyChange({ "m", "leaderlist" })
	public void selectLogin(@BindingParam("cb") Combobox cb) {
		if (cb.getValue() != null) {
			if (cb.getSelectedItem() != null) {
				LoginModel m = new LoginModel();
				m = cb.getSelectedItem().getValue();
				if (m != null) {
					this.m = bll.getLoginInfoByUserId(m.getLog_id() + "");
					LoginModel team = bll.getLoginInfoByUserId(m.getLog_pid()
							+ "");
					this.m.setLog_leader(team.getLog_name());
					leaderlist = bll.getLoginListByDepId(m.getDep_id());
				}
			}
		}
	}

	// 选择部门
	@Command
	@NotifyChange({ "m", "leaderlist" })
	public void selectdep(@BindingParam("cb") Combobox cb) {
		if (cb.getValue() != null) {
			if (cb.getSelectedItem() != null) {
				DepartmentModel m = new DepartmentModel();
				m = cb.getSelectedItem().getValue();
				leaderlist = bll.getLoginListByDepId(m.getDep_id());
				this.m.setLog_leader("");
				this.m.setDep_name(m.getDep_name());
				this.m.setDep_id(m.getDep_id());
				this.m.setLog_pid(0);
			}
		}
	}

	// 选择上级
	@Command
	@NotifyChange("m")
	public void selectleader(@BindingParam("cb") Combobox cb) {
		if (cb.getValue() != null) {
			if (cb.getSelectedItem() != null) {
				LoginModel m = new LoginModel();
				m = cb.getSelectedItem().getValue();
				this.m.setLog_leader(m.getLog_name());
				this.m.setLog_pid(m.getLog_id());
			}
		}
	}

	// 选择角色
	@Command
	@NotifyChange("m")
	public void selectrole(@BindingParam("cb") Combobox cb) {
		if (cb.getValue() != null) {
			if (cb.getSelectedItem() != null) {
				RoleModel m = new RoleModel();
				m = cb.getSelectedItem().getValue();
				this.m.setRol_name(m.getRol_name());
				this.m.setRole_id(m.getRol_id());
			}
		}
	}

	@Command
	public void summit(@BindingParam("newpassword") Textbox newpassword,
			@BindingParam("renewpassword") Textbox renewpassword,
			@BindingParam("win") Window win) {
		if (m.getLog_name() == null || m.getLog_name().equals("")) {
			Clients.showNotification("请选择用户", "info", win, "middle_center",
					2000);
			return;
		}

		if (newPassWord != null && !newPassWord.equals("")) {
			if (newPassWord.length() < 6) {
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
		}
		
		if (m.getLog_state() == null || m.getLog_state().equals("")) {
			Clients.showNotification("请选择在职状态", "info", win, "middle_center",
					2000);
			return;
		}
		if (m.getDep_name() == null || m.getDep_name().equals("")) {
			Clients.showNotification("请选择所属部门", "info", win, "middle_center",
					2000);
			return;
		}
		
		String md5PassWord = "";
		// 把新密码做MD5加密
		if (newPassWord != null && !newPassWord.equals("")) {
			md5PassWord = MD5.GetMD5Code(newPassWord);
		}
		
		PubRegUserBll bll = new PubRegUserBll();
		m.setLog_pws(md5PassWord);
		if(m.getLog_state()!=null)
		{
			if(m.getLog_state().equals("在职"))
			{
				m.setLog_inure(1);
			}
			else
			{
				m.setLog_inure(0);
			}
		}
		Integer k = bll.User_Portion(m);
		if (k > 0) {
			Messagebox
					.show("修改成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			// Session session =
			// Executions.getCurrent().getDesktop().getSession();
			// session.setAttribute("userid", "");
			// session.setAttribute("username", "");
			// session.setAttribute("depid", "");
			// session.setAttribute("rolname", "");
			Executions.sendRedirect("../SystemControl/Pub_PortionUser.zul");
		} else {
			Messagebox.show("修改失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public LoginModel getM() {
		return m;
	}

	public void setM(LoginModel m) {
		this.m = m;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public List<LoginModel> getLoginlist() {
		return loginlist;
	}

	public void setLoginlist(List<LoginModel> loginlist) {
		this.loginlist = loginlist;
	}

	public List<DepartmentModel> getDeplist() {
		return deplist;
	}

	public void setDeplist(List<DepartmentModel> deplist) {
		this.deplist = deplist;
	}

	public List<RoleModel> getRolelist() {
		return rolelist;
	}

	public void setRolelist(List<RoleModel> rolelist) {
		this.rolelist = rolelist;
	}

	public List<LoginModel> getLeaderlist() {
		return leaderlist;
	}

	public void setLeaderlist(List<LoginModel> leaderlist) {
		this.leaderlist = leaderlist;
	}

}
