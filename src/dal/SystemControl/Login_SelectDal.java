package dal.SystemControl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.DepartmentModel;
import Model.LoginModel;
import Model.RoleModel;

public class Login_SelectDal {
	// 通过log_id查询用户信息
	public LoginModel getLoginInfoByUserId(String userId) {
		LoginModel m = new LoginModel();
		String sql = "select case log_inure when 1 then '在职' else '离职' end log_state,* from login a inner join department b on a.dep_id=b.dep_id "
				+ "left join (select rol_name,lp.rol_id,rol_index,lgp_id,log_id from logingroup lp "
				+ "inner join role rl on lp.rol_id=rl.rol_id) lprl on a.log_id=lprl.log_id "
				+ "where a.log_id=" + userId;
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				m.setDep_id(rs.getInt("dep_id"));
				m.setDep_name(rs.getString("dep_name"));
				m.setLog_email(rs.getString("log_email"));
				m.setLog_id(rs.getInt("log_id"));
				m.setLog_mobile(rs.getString("log_mobile"));
				m.setLog_name(rs.getString("log_name"));
				m.setLog_pid(rs.getInt("log_pid"));
				m.setLog_pws(rs.getString("log_pws"));
				m.setLog_sex(rs.getString("log_sex"));
				m.setLog_teamleader(rs.getShort("log_teamleader"));
				m.setLog_tel(rs.getString("log_tel"));
				m.setRol_name(rs.getString("rol_name"));
				m.setLog_state(rs.getString("log_state"));
				m.setRole_id(rs.getInt("rol_id"));
			}
		} catch (Exception e) {

		}
		return m;
	}

	// 查询用户信息
	public List<LoginModel> getLoginList(String str) {
		List<LoginModel> list = new ArrayList<LoginModel>();
		String sql = "select * from Login where log_inure=1 "+str+" order by log_name";
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				LoginModel m = new LoginModel();
				m.setLog_email(rs.getString("log_email"));
				m.setLog_id(rs.getInt("log_id"));
				m.setLog_mobile(rs.getString("log_mobile"));
				m.setLog_name(rs.getString("log_name"));
				m.setLog_pid(rs.getInt("log_pid"));
				m.setLog_pws(rs.getString("log_pws"));
				m.setLog_sex(rs.getString("log_sex"));
				m.setLog_teamleader(rs.getShort("log_teamleader"));
				m.setLog_tel(rs.getString("log_tel"));
				m.setDep_id(rs.getInt("dep_id"));
				list.add(m);
			}
		} catch (Exception e) {

		}

		return list;
	}

	// 查询部门信息
	public List<DepartmentModel> getDepartmentList() {
		List<DepartmentModel> list = new ArrayList<DepartmentModel>();
		String sql = "select * from department where 1=1";
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				DepartmentModel m = new DepartmentModel();
				m.setDep_id(rs.getInt("dep_id"));
				m.setDep_name(rs.getString("dep_name"));
				list.add(m);
			}
		} catch (Exception e) {

		}

		return list;
	}

	// 查询角色信息
	public List<RoleModel> getRoleList() {
		List<RoleModel> list = new ArrayList<RoleModel>();
		String sql = "select * from role where 1=1";
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				RoleModel m = new RoleModel();
				m.setRol_id(rs.getShort("rol_id"));
				m.setRol_name(rs.getString("rol_name"));
				m.setRol_index(rs.getString("rol_index"));
				list.add(m);
			}
		} catch (Exception e) {

		}

		return list;
	}
}
