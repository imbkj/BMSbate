package dal.WorkflowCore;

import java.sql.CallableStatement;
import java.sql.SQLException;

import Conn.dbconn;

public class WfCoreTaskOperateDal {
	private static dbconn conn = new dbconn();

	// 执行存储过程新建任务单
	public int AddTaskToDb(String tacl_name, String tali_name, int wfbu_id,
			int tapr_dataid, String username, String remark, String searchCon) {
		try {
			CallableStatement c = conn
					.getcall("WfCoreTaskAddnew_P_lwj(?,?,?,?,?,?,?,?)");
			c.setString(1, tacl_name);
			c.setString(2, tali_name);
			c.setInt(3, tapr_dataid);
			c.setInt(4, wfbu_id);
			c.setString(5, username);
			c.setString(6, remark);
			c.setString(7, searchCon);
			c.registerOutParameter(8, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(8);
		} catch (SQLException e) {
			return 0;
		}
	}

	// 执行存储过程新建子任务单
	public int AddSubTaskToDb(String tali_name, int tapr_id, int wfbu_id,
			int tapr_dataid, int nextappointid, String nextappointcon,
			String username, String remark, String searchCon) {
		try {
			CallableStatement c = conn
					.getcall("WfCoreSubTaskAdd_P_lwj(?,?,?,?,?,?,?,?,?,?)");
			c.setString(1, tali_name);
			c.setInt(2, tapr_id);
			c.setInt(3, wfbu_id);
			c.setInt(4, tapr_dataid);
			c.setInt(5, nextappointid);
			c.setString(6, nextappointcon);
			c.setString(7, username);
			c.setString(8, remark);
			c.setString(9, searchCon);
			c.registerOutParameter(10, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(10);
		} catch (SQLException e) {
			return 0;
		}
	}

}
