package dal.SystemControl;

import Conn.dbconn;

public class Login_Manager {
	public int UpdateLogin(int log_id, int log_pid) {
		int k = 0;
		String sql = "update login set log_pid=" + log_pid + " where log_id="
				+ log_id;
		dbconn db = new dbconn();
		k = db.execQuery(sql);
		return k;
	}
	
	public int UpdateLogins(int log_pid) {
		int k = 0;
		String sql = "update login set log_pid=0 where log_pid="
				+ log_pid;
		dbconn db = new dbconn();
		k = db.execQuery(sql);
		return k;
	}
}
