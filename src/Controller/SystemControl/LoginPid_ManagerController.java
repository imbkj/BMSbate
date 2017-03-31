package Controller.SystemControl;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import bll.LoginBll;
import bll.SystemControl.Login_ManagerBll;

import Model.DepartmentModel;
import Model.LoginModel;

public class LoginPid_ManagerController {
	private List<LoginModel> loginlist = new ArrayList<LoginModel>();
	private List<LoginModel> notlist = new ArrayList<LoginModel>();
	private List<LoginModel> selist = new ArrayList<LoginModel>();
	private LoginBll bll = new LoginBll();
	private List<DepartmentModel> deplist = new ArrayList<DepartmentModel>();
	private String department = "", log_name = "";

	public LoginPid_ManagerController() {
		loginlist = bll.loginList("");
		notlist = bll.loginList("");
		deplist = bll.getDepartment();
	}

	@Command
	@NotifyChange({ "notlist", "selist" })
	public void selectlog(@BindingParam("lb") Listbox lb,
			@BindingParam("flag") String flag) {
		if (lb.getSelectedItem() != null) {
			LoginModel model = lb.getSelectedItem().getValue();
			if (model != null) {
				if (flag.equals("1")) {
					selist.add(model);
					notlist.remove(model);
				} else if (flag.equals("2")) {
					notlist.add(model);
					selist.remove(model);
				}
			}
		}
	}

	// 上级的选择事件
	@Command
	@NotifyChange({ "notlist", "selist" })
	public void selects(@BindingParam("cb") Combobox cb) {
		if (cb.getValue() != null) {
			if (cb.getSelectedItem() != null) {
				LoginModel m = new LoginModel();
				m = cb.getSelectedItem().getValue();
				if (m != null) {
					String notsql = " and log_pid<>" + m.getLog_id()
							+ " and log_id<>" + m.getLog_id();
					if (department != null && !department.equals("")) {
						notsql = notsql
								+ " and dep_id in(select dep_id from department where dep_name='"
								+ department + "')";
					}
					selist = bll.loginList(" and log_pid=" + m.getLog_id());
					notlist = bll.loginList(notsql);
				}
			}
		}
	}

	@Command
	@NotifyChange({ "notlist", "selist" })
	public void submit(@BindingParam("cb") Combobox cb) {
		if (cb.getValue() != null && cb.getSelectedItem() != null) {
			LoginModel m = new LoginModel();
			m = cb.getSelectedItem().getValue();
			if (m != null && m.getLog_id() != 0) {
				Login_ManagerBll lbll = new Login_ManagerBll();
				lbll.UpdateLogins(m.getLog_id());
				for (LoginModel lm : selist) {
					lbll.UpdateLogin(lm.getLog_id(), m.getLog_id());
				}
				selist = bll.loginList(" and log_pid=" + m.getLog_id());
				String notsql = " and log_pid<>" + m.getLog_id();
				if (department != null && !department.equals("")) {
					notsql = notsql
							+ " and dep_id in(select dep_id from department where dep_name='"
							+ department + "')";
				}
				notlist = bll.loginList(notsql);
			} else {
				Messagebox.show("请选择上级", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请选择上级", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Command
	@NotifyChange("notlist")
	public void search(@BindingParam("txt") Textbox txt,
			@BindingParam("cb") Combobox cb) {
		String sql = "";
		if (txt.getValue() != null && !txt.getValue().equals("")) {
			sql = sql + " and log_name like '%" + txt.getValue() + "%'";
		}
		if (cb.getValue() != null && !cb.getValue().equals("")) {
			DepartmentModel m = cb.getSelectedItem().getValue();
			sql = sql + " and dep_id =" + m.getDep_id() + "";
		}
		notlist = bll.loginList(sql);
	}

	public List<LoginModel> getLoginlist() {
		return loginlist;
	}

	public void setLoginlist(List<LoginModel> loginlist) {
		this.loginlist = loginlist;
	}

	public List<LoginModel> getNotlist() {
		return notlist;
	}

	public void setNotlist(List<LoginModel> notlist) {
		this.notlist = notlist;
	}

	public List<LoginModel> getSelist() {
		return selist;
	}

	public void setSelist(List<LoginModel> selist) {
		this.selist = selist;
	}

	public List<DepartmentModel> getDeplist() {
		return deplist;
	}

	public void setDeplist(List<DepartmentModel> deplist) {
		this.deplist = deplist;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getLog_name() {
		return log_name;
	}

	public void setLog_name(String log_name) {
		this.log_name = log_name;
	}

}
