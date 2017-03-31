package dal.Workflow;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Model.LoginModel;

import Conn.dbconn;

public class WfNodeDisUserDal {
	private static dbconn conn = new dbconn();

	// 获取部门数据集
	private ResultSet getDepartmentRs() throws Exception {
		ResultSet rs = null;
		String sql = "select distinct dep_name from department";
		rs = conn.GRS(sql);
		return rs;
	}

	// 获取部门数据
	public ArrayList<String> getDepartment() {
		ArrayList<String> depList = new ArrayList<String>();
		try {
			ResultSet rs = getDepartmentRs();
			while (rs.next()) {
				depList.add(rs.getString("dep_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return depList;
	}

	// 根据节点ID获取已选部门的数据集
	private ResultSet getDepartmentRs(int wfno_id) throws Exception {
		ResultSet rs = null;
		String sql = "select distinct dep_name from department where dep_id in (select wnar_role from WfNodeAndRole where wnar_type=1 and wnar_state=1 and wnar_wfno_id=?)";
		PreparedStatement psmt = conn.getpre(sql);
		psmt.setInt(1, wfno_id);
		rs = psmt.executeQuery();
		return rs;
	}

	// 根据节点ID获取已选部门的数据
	public ArrayList<String> getDepartment(int wfno_id) {
		ArrayList<String> depList = new ArrayList<String>();
		try {
			ResultSet rs = getDepartmentRs(wfno_id);
			while (rs.next()) {
				depList.add(rs.getString("dep_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return depList;
	}

	// 获取角色数据集
	private ResultSet getRoleRs() throws Exception {
		ResultSet rs = null;
		String sql = "select distinct rol_name from role";
		rs = conn.GRS(sql);
		return rs;
	}

	// 获取角色数据
	public ArrayList<String> getRole() {
		ArrayList<String> roleList = new ArrayList<String>();
		try {
			ResultSet rs = getRoleRs();
			while (rs.next()) {
				roleList.add(rs.getString("rol_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roleList;
	}

	// 根据节点ID获取已选角色的数据集
	private ResultSet getRoleRs(int wfno_id) throws Exception {
		ResultSet rs = null;
		String sql = "select distinct rol_name from role where rol_id in (select wnar_role from WfNodeAndRole where wnar_type=2 and wnar_state=1 and wnar_wfno_id=?)";
		PreparedStatement psmt = conn.getpre(sql);
		psmt.setInt(1, wfno_id);
		rs = psmt.executeQuery();
		return rs;
	}

	// 根据节点ID获取已选角色的数据
	public ArrayList<String> getRole(int wfno_id) {
		ArrayList<String> roleList = new ArrayList<String>();
		try {
			ResultSet rs = getRoleRs(wfno_id);
			while (rs.next()) {
				roleList.add(rs.getString("rol_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roleList;
	}

	// 获取用户数据集
	private ResultSet getUserRs() throws Exception {
		ResultSet rs = null;
		String sql = "select distinct log_name from login where log_inure=1";
		rs = conn.GRS(sql);
		return rs;
	}

	// 获取用户数据
	public ArrayList<String> getUser() {
		ArrayList<String> userList = new ArrayList<String>();
		try {
			ResultSet rs = getUserRs();
			while (rs.next()) {
				userList.add(rs.getString("log_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userList;
	}

	// 根据节点ID获取已选用户的数据集
	private ResultSet getUserRs(int wfno_id) throws Exception {
		ResultSet rs = null;
		String sql = "select distinct log_name from Login where log_inure=1 and log_id in (select wnar_role from WfNodeAndRole where wnar_type=3 and wnar_state=1 and wnar_wfno_id=?)";
		PreparedStatement psmt = conn.getpre(sql);
		psmt.setInt(1, wfno_id);
		rs = psmt.executeQuery();
		return rs;
	}

	// 根据节点ID获取已选用户的数据
	public ArrayList<String> getUser(int wfno_id) {
		ArrayList<String> userList = new ArrayList<String>();
		try {
			ResultSet rs = getUserRs(wfno_id);
			while (rs.next()) {
				userList.add(rs.getString("log_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userList;
	}

	// 初始获取所选的用户信息数据集
	private ResultSet getUserListRs(int wfno_id) throws Exception {
		ResultSet rs = null;
		String sql = "select distinct log_id,log_name"
				+ " from Login"
				+ " where dep_id in (select wnar_role from WfNodeAndRole where wnar_type=1 and wnar_state=1 and wnar_wfno_id=?) or log_id in (select log_id from logingroup where rol_id in (select wnar_role from WfNodeAndRole where wnar_type=2 and wnar_state=1 and wnar_wfno_id=?)) or log_id in (select wnar_role from WfNodeAndRole where wnar_type=3 and wnar_state=1 and wnar_wfno_id=?)"
				+ " order by log_name";
		PreparedStatement psmt = conn.getpre(sql);
		psmt.setInt(1, wfno_id);
		psmt.setInt(2, wfno_id);
		psmt.setInt(3, wfno_id);
		rs = psmt.executeQuery();
		return rs;
	}

	// 初始获取所选的用户信息数据
	public ArrayList<LoginModel> getUserList(int wfno_id) {
		ArrayList<LoginModel> userList = new ArrayList<LoginModel>();
		try {
			ResultSet rs = getUserListRs(wfno_id);
			while (rs.next()) {
				userList.add(new LoginModel(rs.getInt("log_id"), rs
						.getString("log_name")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userList;
	}

	// 根据所选内容获取用户的列表信息数据集
	private ResultSet getUserListRs(String depStr, String roleStr,
			String userStr) throws Exception {
		ResultSet rs = null;
		String sql = "select distinct log_id,log_name from Login where dep_id in (select dep_id from department where dep_name in ("
				+ depStr
				+ ")) or log_id in (select log_id from logingroup where rol_id in (select rol_id from role where rol_name in ("
				+ roleStr + "))) or log_name in (" + userStr + ")";
		rs = conn.GRS(sql);
		return rs;
	}

	// 根据所选内容获取用户的列表信息数据
	public ArrayList<LoginModel> getUserList(String depStr, String roleStr,
			String userStr) {
		ArrayList<LoginModel> userList = new ArrayList<LoginModel>();
		try {
			ResultSet rs = getUserListRs(depStr, roleStr, userStr);
			while (rs.next()) {
				userList.add(new LoginModel(rs.getInt("log_id"), rs
						.getString("log_name")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userList;
	}

	// 删除分配结果
	public int DelNodeUser(int wfno_id) {
		try {
			CallableStatement c = conn.getcall("WfNodeUserDel_P_lwj(?,?)");
			c.setInt(1, wfno_id);
			c.registerOutParameter(2, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(2);
		} catch (SQLException e) {
			return 0;
		}
	}

	// 提交分配结果
	public int UpdateNodeUser(int wfno_id, int type, String con, String addname) {
		try {
			CallableStatement c = conn
					.getcall("WfNodeUserUpdate_P_lwj(?,?,?,?,?)");
			c.setInt(1, wfno_id);
			c.setInt(2, type);
			c.setString(3, con);
			c.setString(4, addname);
			c.registerOutParameter(5, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(5);
		} catch (SQLException e) {
			return 0;
		}
	}
}
