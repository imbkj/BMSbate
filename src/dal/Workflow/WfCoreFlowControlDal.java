package dal.Workflow;

import java.sql.CallableStatement;
import java.sql.SQLException;

import Conn.dbconn;

public class WfCoreFlowControlDal {
	private static dbconn conn = new dbconn();

    //执行存储过程添加操作记录
	public int AddTaskLog(int tapr_id, String tapl_datatable,
			int tapl_datatableid, String tapl_content, String username,
			String remark) {
		try {
			CallableStatement c = conn
					.getcall("WfCoreAddTaskLog_P_lwj(?,?,?,?,?,?,?)");
			c.setInt(1, tapr_id);
			c.setString(2, tapl_datatable);
			c.setInt(3, tapl_datatableid);
			c.setString(4, tapl_content);
			c.setString(5, username);
			c.setString(6, remark);
			c.registerOutParameter(7, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(7);
		} catch (SQLException e) {
			return 0;
		}
	}
	
	   // 执行存储过程通过并转下一步
		public int PassToNext(int tapr_id, int tapr_dataid, String username,
				String remark, int appoint) {
			try {
				CallableStatement c = conn
						.getcall("WfCorePassToNext_P_lwj(?,?,?,?,?,?)");
				c.setInt(1, tapr_id);
				c.setInt(2, tapr_dataid);
				c.setInt(3, appoint);
				c.setString(4, username);
				c.setString(5, remark);
				c.registerOutParameter(6, java.sql.Types.INTEGER);
				c.execute();
				return c.getInt(6);
			} catch (SQLException e) {
				return 0;
			}
		}
		
		 // 执行存储过程跳过并转下一步
		public int SkipToNext(int tapr_id,String username,
				String remark, int appoint) {
			try {
				CallableStatement c = conn
						.getcall("WfCoreSkipToNext_P_lwj(?,?,?,?,?)");
				c.setInt(1, tapr_id);
				c.setInt(2, appoint);
				c.setString(3, username);
				c.setString(4, remark);
				c.registerOutParameter(5, java.sql.Types.INTEGER);
				c.execute();
				return c.getInt(5);
			} catch (SQLException e) {
				return 0;
			}
		}
		
		
		
		 // 执行存储过程撤销到上一步
		public int revokeToPrev(int tapr_id,String username,
				String remark) {
			try {
				CallableStatement c = conn
						.getcall("WfCorerevokeToPrev_P_py(?,?,?,?)");
				c.setInt(1, tapr_id);
				c.setString(2, username);
				c.setString(3, remark);
				c.registerOutParameter(4, java.sql.Types.INTEGER);
				c.execute();
				return c.getInt(4);
			} catch (SQLException e) {
				return 0;
			}
		}
		
}
