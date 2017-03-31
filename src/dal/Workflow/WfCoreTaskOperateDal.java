package dal.Workflow;

import java.sql.CallableStatement;
import java.sql.SQLException;

import Conn.dbconn;

public class WfCoreTaskOperateDal {
	private static dbconn conn = new dbconn();

	// 执行存储过程新建任务单
	public int AddTaskToDb(String tacl_name, String tali_name, int wfbu_id,
			String username, String remark) {
		try {
			CallableStatement c = conn
					.getcall("WfCoreTaskAdd_P_lwj(?,?,?,?,?,?)");
			c.setString(1, tacl_name);
			c.setString(2, tali_name);
			c.setInt(3, wfbu_id);
			c.setString(4, username);
			c.setString(5, remark);
			c.registerOutParameter(6, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(6);
		} catch (SQLException e) {
			return 0;
		}
	}

	// 执行存储过程新建子任务单
	public int AddSubTaskToDb(String tali_name,int tapr_id, int wfbu_id,int tapr_dataid,
			String username, String remark) {
		try {
			CallableStatement c = conn
					.getcall("WfCoreSubTaskAdd_P_lwj(?,?,?,?,?,?,?)");
			c.setString(1, tali_name);
			c.setInt(2, tapr_id);
			c.setInt(3, wfbu_id);
			c.setInt(4, tapr_dataid);
			c.setString(5, username);
			c.setString(6, remark);
			c.registerOutParameter(7, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(7);
		} catch (SQLException e) {
			return 0;
		}
	}

}
