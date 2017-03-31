package bll.SystemControl;

import dal.SystemControl.Login_Manager;

public class Login_ManagerBll {
	private Login_Manager dal=new Login_Manager();
	
	public int UpdateLogin(int log_id, int log_pid) {
		return dal.UpdateLogin(log_id, log_pid);
	}
	
	public int UpdateLogins(int log_pid) {
		return dal.UpdateLogins(log_pid);
	}
}
