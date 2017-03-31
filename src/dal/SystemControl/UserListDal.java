package dal.SystemControl;

import Model.LoginUserModel;
import Model.DepartmentListModel;
import Model.RoleListModel;
import Model.loginroleModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Conn.dbconn;

import org.zkoss.zhtml.Em;
import org.zkoss.zul.ListModelList;
import Util.pinyin4jUtil;

public class UserListDal {
	// 获取员工列表
	public List<LoginUserModel> getuserlist() throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<LoginUserModel> list = new ArrayList<LoginUserModel>();
		String sqlstr = "select log_id,log_name from Login a where log_inure=1 order by log_name";

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				LoginUserModel model = new LoginUserModel();
				model.setLog_id(rs.getInt("log_id"));
				model.setLog_name(rs.getString("log_name"));
				model.setPyzm("（"
						+ pinyin4jUtil.getPinYinHeadChar(rs.getString(
								"log_name").substring(0, 1)) + "）");
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 根据部门获取员工列表
	public List<LoginUserModel> getuserlistfordid(int dep_id)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<LoginUserModel> list = new ArrayList<LoginUserModel>();
		String sqlstr = "select log_id,log_name from Login  where log_inure=1 and  dep_id="
				+ dep_id + "  " + "order by log_name";
		System.out.println(sqlstr);
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				LoginUserModel model = new LoginUserModel();
				model.setLog_id(rs.getInt("log_id"));
				model.setLog_name(rs.getString("log_name"));
				model.setPyzm("（"
						+ pinyin4jUtil.getPinYinHeadChar(rs.getString(
								"log_name").substring(0, 1)) + "）");
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 根据部门获取员工列表
	public List<LoginUserModel> getuserlistfordid(String dep_id)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<LoginUserModel> list = new ArrayList<LoginUserModel>();
		String sqlstr = "select log_id,log_name,dep_name from Login a  inner join department b on "
				+ "a.dep_id=b.dep_id  where log_inure=1 and  b.dep_name='"
				+ dep_id + "'  " + "order by log_name";
		System.out.println(sqlstr);
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				LoginUserModel model = new LoginUserModel();
				model.setLog_id(rs.getInt("log_id"));
				model.setLog_name(rs.getString("log_name"));
				model.setPyzm("（"
						+ pinyin4jUtil.getPinYinHeadChar(rs.getString(
								"log_name").substring(0, 1)) + "）");
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取部门列表
	public List<DepartmentListModel> getdeplist() throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs2 = null;
		List<DepartmentListModel> list2 = new ArrayList<DepartmentListModel>();
		String sqlstr2 = "select dep_id,dep_name from department";

		try {
			rs2 = db.GRS(sqlstr2);
			while (rs2.next()) {
				DepartmentListModel model2 = new DepartmentListModel();
				model2.setDep_id(rs2.getInt("dep_id"));
				model2.setDep_name(rs2.getString("dep_name"));
				list2.add(model2);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list2;
	}

	// 获取部门列表(可全选)
	public List<DepartmentListModel> getdepAlllist() throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs2 = null;
		List<DepartmentListModel> list2 = new ArrayList<DepartmentListModel>();
		String sqlstr2 = "select 0 dep_id,'全部' dep_name union all select dep_id,dep_name from department";

		try {
			rs2 = db.GRS(sqlstr2);
			while (rs2.next()) {
				DepartmentListModel model2 = new DepartmentListModel();
				model2.setDep_id(rs2.getInt("dep_id"));
				model2.setDep_name(rs2.getString("dep_name"));
				list2.add(model2);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list2;
	}

	// 获取角色
	public List<RoleListModel> getrolelist() throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs3 = null;
		List<RoleListModel> list3 = new ArrayList<RoleListModel>();
		String sqlstr3 = "select rol_id,rol_name,rol_index from role";

		try {
			rs3 = db.GRS(sqlstr3);
			while (rs3.next()) {
				RoleListModel model3 = new RoleListModel();
				model3.setRol_id(rs3.getInt("rol_id"));
				model3.setRol_name(rs3.getString("rol_name"));
				model3.setRol_index(rs3.getString("rol_index"));
				list3.add(model3);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list3;
	}

	// 通过部门获取角色
	public List<loginroleModel> getloginroleByDeptId(Integer id)
			throws SQLException {
		List<loginroleModel> list = new ListModelList<loginroleModel>();
		dbconn db = new dbconn();
		String sql = "select distinct rol_id,rol_name from View_loginrole where log_inure=1 and dep_id like ? order by rol_name";
		String d_i = id == 0 ? "%" : id.toString() + "%";
		list = db.find(sql, loginroleModel.class,
				dbconn.parseSmap(loginroleModel.class), d_i);
		return list;
	}

	// 获取已选中的角色
	public List<LoginUserModel> getroleclist(int role_id) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs1 = null;
		List<LoginUserModel> list1 = new ArrayList<LoginUserModel>();
		String sqlstr1 = "select a.log_id,log_name from logingroup a left join login b on b.log_id=a.log_id where rol_id="
				+ role_id;

		try {
			rs1 = db.GRS(sqlstr1);
			while (rs1.next()) {
				LoginUserModel model1 = new LoginUserModel();
				model1.setLog_id(rs1.getInt("log_id"));
				model1.setLog_name(rs1.getString("log_name"));
				model1.setPyzm("（"
						+ pinyin4jUtil.getPinYinHeadChar(rs1.getString(
								"log_name").substring(0, 1)) + "）");
				list1.add(model1);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list1;
	}

	// 查询角色
	public List<RoleListModel> getrolelistc(String role_name)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs5 = null;
		List<RoleListModel> list5 = new ArrayList<RoleListModel>();
		String sqlstr5 = "select rol_id,rol_name,rol_index from role where rol_name like '%"
				+ role_name + "%'";

		try {
			rs5 = db.GRS(sqlstr5);
			while (rs5.next()) {
				RoleListModel model5 = new RoleListModel();
				model5.setRol_id(rs5.getInt("rol_id"));
				model5.setRol_name(rs5.getString("rol_name"));
				model5.setRol_index(rs5.getString("rol_index"));
				list5.add(model5);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list5;
	}

	// 拼音检索用户所属权限用户列表
	public List<loginroleModel> getUserList(Integer userid, String depid,
			String rolid, String username) throws SQLException {
		List<loginroleModel> list = new ListModelList<loginroleModel>();
		String sql = "select 0 log_id,'全部' log_name,0 sort union all select distinct log_id,log_name,1 from View_loginrole where dep_id like ? and rol_id like ? and (dbo.GetPYM(log_name,0) like ? or log_name like ?) and log_id in (select UnitId from dbo.GetChildbase(?,6)) order by sort,log_name";
		dbconn db = new dbconn();
		list = db.find(sql, loginroleModel.class,
				dbconn.parseSmap(loginroleModel.class), depid, rolid, username,
				username, userid);

		return list;
	}

	// 拼音检索用户所属权限部门列表
	public List<loginroleModel> getDepList(Integer userid, String depname)
			throws SQLException {
		List<loginroleModel> list = new ListModelList<loginroleModel>();
		String sql = "select 0 dep_id,'全部' dep_name,0 sort union all select distinct dep_id,dep_name,1 from View_loginrole where (dbo.GetPYM(dep_name,0) like ? or dep_name like ?) and log_id in (select UnitId from dbo.GetChildbase(?,6)) order by sort,dep_name";
		dbconn db = new dbconn();
		list = db.find(sql, loginroleModel.class,
				dbconn.parseSmap(loginroleModel.class), depname, depname,
				userid);

		return list;
	}

	// 拼音检索用户所属权限角色列表
	public List<loginroleModel> getRolList(Integer userid, String depid,
			String rolname) throws SQLException {
		List<loginroleModel> list = new ListModelList<loginroleModel>();
		String sql = "select 0 rol_id,'全部' rol_name,0 sort union all select distinct rol_id,rol_name,1 from View_loginrole where dep_id like ? and (dbo.GetPYM(rol_name,0) like ? or rol_name like ?) and log_id in (select UnitId from dbo.GetChildbase(?,6)) order by sort,rol_name";
		dbconn db = new dbconn();
		list = db.find(sql, loginroleModel.class,
				dbconn.parseSmap(loginroleModel.class), depid, rolname,
				rolname, userid);

		return list;
	}

	public String getDept(String name) throws SQLException {
		List<loginroleModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select dep_name from View_loginrole where log_name=?";
		list = db.find(sql, loginroleModel.class,
				dbconn.parseSmap(loginroleModel.class), name);
		String str = "";
		if (list.size() > 0) {
			str = list.get(0).getDep_name();
		}

		return str;
	}

	public List<loginroleModel> getdistinctColumn(String name, String sort,
			String dept) throws SQLException {
		List<loginroleModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String dept_sql = dept.equals("") ? " and dep_name in ('客户服务部','全国项目部')"
				: " and dep_name ='" + dept + "'";
		String sql = "select distinct " + name
				+ " from View_loginrole where log_inure=1 " + dept_sql
				+ " order by " + name + " " + sort;
		list = db.find(sql, loginroleModel.class,
				dbconn.parseSmap(loginroleModel.class));
		return list;
	}

	// 查询在册客服(体检)
	public List<loginroleModel> getClientList(loginroleModel lm) {
		List<loginroleModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select distinct log_id,log_name "
				+ "from View_loginrole a "
				+ "inner join cobase b on a.log_name=b.coba_client "
				+ "inner join ("
				+ " select distinct a.cid from cocompact a"
				+ " inner join cooffer b on a.coco_id=b.coof_coco_id"
				+ " inner join coofferlist c on b.coof_id=c.coli_coof_id"
				+ " where (coli_name like '%体检%' or coli_name like '中智_类') and coco_state>3 and coof_state=3 and coli_stoptime is null"
				+ ")c on b.cid=c.cid" + " where 1=1";

		if (lm.getType() != null && lm.getType().equals("1")) {
			sql = sql + " and dep_id in (2,6,9,8,10,13)";
		}

		if (lm.getLog_inure() != null) {
			if (!lm.getLog_inure().equals("")) {
				sql = sql + " and log_inure=" + lm.getLog_inure();
			}
		}

		sql = sql + " order by log_name";
		System.out.print(sql);
		try {
			list = db.find(sql, loginroleModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
