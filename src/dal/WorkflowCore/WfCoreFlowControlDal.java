package dal.WorkflowCore;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Conn.dbconn;
import Model.LoginModel;

public class WfCoreFlowControlDal {
	private static dbconn conn = new dbconn();

	// 检测操作权限
	public boolean CheckCompetence(int tapr_id, String username) {
		try {
			CallableStatement c = conn
					.getcall("WfCoreCheckCompetence_P_lwj(?,?,?)");
			c.setInt(1, tapr_id);
			c.setString(2, username);
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			if (c.getInt(3) == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	// 检查流程是否正在执行
	public boolean CheckProcess(int tapr_id) {
		try {
			CallableStatement c = conn.getcall("WfCoreCheckProcess_P_lwj(?,?)");
			c.setInt(1, tapr_id);
			c.registerOutParameter(2, java.sql.Types.INTEGER);
			c.execute();
			if (c.getInt(2) == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	// 执行存储过程添加操作记录
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
			e.printStackTrace();
			return 0;
		}
	}

	// 执行存储过程通过并转下一步
	public int PassToNext(int tapr_id, int tapr_dataid, String username,
			String remark, int appointid, String appointcon) {
		try {
			CallableStatement c = conn
					.getcall("WfCorePassToNextnew_P_lwj(?,?,?,?,?,?,?)");
			c.setInt(1, tapr_id);
			c.setInt(2, tapr_dataid);
			c.setInt(3, appointid);
			c.setString(4, appointcon);
			c.setString(5, username);
			c.setString(6, remark);
			c.registerOutParameter(7, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(7);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 执行存储过程跳过并转下一步
	public int SkipToNext(int tapr_id, String username, String remark,
			int appointid, String appointcon) {
		try {
			CallableStatement c = conn
					.getcall("WfCoreSkipToNext_P_lwj(?,?,?,?,?,?)");
			c.setInt(1, tapr_id);
			c.setInt(2, appointid);
			c.setString(3, appointcon);
			c.setString(4, username);
			c.setString(5, remark);
			c.registerOutParameter(6, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(6);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 执行存储过程撤销到上一步
	public int revokeToPrev(int tapr_id, String username, String remark) {
		try {
			CallableStatement c = conn
					.getcall("WfCorerevokeToPrev_P_py(?,?,?,?,?)");
			c.setInt(1, tapr_id);
			c.setString(3, username);
			c.setString(4, remark);
			c.registerOutParameter(5, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(5);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 执行存储过程退回到上一步
	public int returnToPrev(int tapr_id, String username) {
		try {
			CallableStatement c = conn
					.getcall("WfCoreReturnToPrev_P_lwj(?,?,?)");
			c.setInt(1, tapr_id);
			c.setString(2, username);
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 执行存储过程退回到指定步骤
	public int returnToN(int tapr_id, int tostep, String username) {
		try {
			CallableStatement c = conn
					.getcall("WfCoreReturnToN_P_lwj(?,?,?,?)");
			c.setInt(1, tapr_id);
			c.setInt(2, tostep);
			c.setString(3, username);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 执行存储过程终止任务单
	public int StopTaskToDb(int tapr_id, String username, String remark) {
		try {
			CallableStatement c = conn.getcall("WfCoreStopTask_P_lwj(?,?,?,?)");
			c.setInt(1, tapr_id);
			c.setString(2, username);
			c.setString(3, remark);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 记录错误日志
	public void addErrLog(int tali_id, int tapr_id, int dataid,
			String wfel_con, String username) {
		try {
			CallableStatement c = conn.getcall("WfCoreErrLog_P_lwj(?,?,?,?,?)");
			c.setInt(1, tali_id);
			c.setInt(2, tapr_id);
			c.setInt(3, dataid);
			c.setString(4, wfel_con);
			c.setString(5, username);
			c.execute();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	// 通过当前流程
	public int PassProcess(int tapr_id, String username, String remark) {
		try {
			CallableStatement c = conn
					.getcall("WfCorePassProcess_P_lwj(?,?,?,?)");
			c.setInt(1, tapr_id);
			c.setString(2, username);
			c.setString(3, remark);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 跳转至指定步骤
	public int SkipToN(int tapr_id, int tapr_dataid, int tostep, int appointid,
			String appointcon, String username, String remark) {
		try {
			CallableStatement c = conn
					.getcall("WfCoreSkipToN_P_lwj(?,?,?,?,?,?,?,?)");
			c.setInt(1, tapr_id);
			c.setInt(2, tapr_dataid);
			c.setInt(3, tostep);
			c.setInt(4, appointid);
			c.setString(5, appointcon);
			c.setString(6, username);
			c.setString(7, remark);
			c.registerOutParameter(8, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(8);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 跳转至指定步骤
	public int moveToN(int tapr_id, int tostep, String username, String remark) {
		try {
			/*
			CallableStatement c = conn.getcall("");
			c.setInt(1, tapr_id);
			c.setInt(2, tostep);
			c.setString(3, username);
			c.setString(4, remark);
			c.registerOutParameter(5, java.sql.Types.INTEGER);
			c.execute();
			
			return c.getInt(5);
			*/
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 完结任务
	public int OverTask(int tapr_id, String username, String remark) {
		try {
			CallableStatement c = conn.getcall("WfCoreOverTask_P_lwj(?,?,?,?)");
			c.setInt(1, tapr_id);
			c.setString(2, username);
			c.setString(3, remark);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 更改流程状态
	public boolean upProcessState(int tapr_id, int openState, int endState) {
		boolean bool = false;
		try {
			CallableStatement c = conn
					.getcall("WfCoreUpProcessState_P_lwj(?,?,?,?)");
			c.setInt(1, tapr_id);
			c.setInt(2, openState);
			c.setInt(3, endState);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			if (c.getInt(4) > 0) {
				bool = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bool;
	}

	// 通过流程ID查询可操作人
	public List<Integer> searchUser(int tapr_id) {
		List<Integer> list = new ArrayList<Integer>();
		try {
			ResultSet rs = conn.GRS("exec WfCoreSearchUser_P_lwj " + tapr_id);
			while (rs.next()) {
				list.add(rs.getInt("log_id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 通过流程ID查询可操作人
	public List<LoginModel> searchUserName(int tapr_id) {
		List<LoginModel> list = new ArrayList<LoginModel>();
		LoginModel m;
		try {
			ResultSet rs = conn.GRS("exec WfCoreSearchUser_P_lwj " + tapr_id);
			while (rs.next()) {
				m = new LoginModel();
				m.setLog_id(rs.getInt("log_id"));
				m.setLog_name(rs.getString("log_name"));
				list.add(m);
			}
			Collections.sort(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询撤回至指定步骤的操作人
	public String selRevokeOpName(int tapr_id, int wfno_step) {
		String name = "";
		StringBuilder sql = new StringBuilder();
		sql.append("select top 1 tapr_endname from TaskProcess tp ");
		sql.append("inner join WfNode wn on tp.tapr_wfno_id=wn.wfno_id ");
		sql.append("where tp.tapr_tali_id=(select tapr_tali_id from TaskProcess where tapr_id=? and tapr_state=1) and wfno_step=?");
		try {
			PreparedStatement psmt = conn.getpre(sql.toString());
			psmt.setInt(1, tapr_id);
			psmt.setInt(2, wfno_step);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				name = rs.getString("tapr_endname");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return name;

	}
}
