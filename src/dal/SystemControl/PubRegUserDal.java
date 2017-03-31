package dal.SystemControl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Conn.dbconn;
import Model.DocumentsInfoAddModel;
import Model.EmCensusModel;
import Model.LoginModel;

public class PubRegUserDal {
	private static dbconn conn = new dbconn();

	public int AddReg(LoginModel em) throws Exception {
		int row = 0;
		int pubtreepid = 0;
		int log_id = 0;
		int pubtreename;

		String sqlstr = "insert into login (log_name,log_pid,log_tel,log_sex,log_pws,log_email,log_mobile,dep_id,log_intime) values ('"
				+ em.getLog_name()
				+ "','"
				+ em.getLog_teamleader()
				+ "','"
				+ em.getLog_tel()
				+ "','"
				+ em.getLog_sex()
				+ "','"
				+ em.getLog_pws()
				+ "','"
				+ em.getLog_email()
				+ "','"
				+ em.getLog_mobile() + "','"

				+ em.getDep_id() + "','"

				+ em.getLog_intime() + "')";

		String sqlstrtree = "select log_id,name,id,log_name from login a left join (select * from PubTree) b on a.log_name=b.name  "
				+ "where a.log_id=" + em.getLog_teamleader() + " ";
		ResultSet rs = conn.GRS(sqlstrtree);

		while (rs.next()) {
			pubtreepid = rs.getInt("id");
		}

		String sqlstrrol = "select MAX(log_id)+1 id from login";
		ResultSet rs_rol = conn.GRS(sqlstrrol);

		while (rs_rol.next()) {
			log_id = rs_rol.getInt("id");
		}

		String sqlstrtreei = "insert into PubTree(PID,name) values("
				+ pubtreepid + ",'" + em.getLog_name() + "')";

		dbconn oper = new dbconn();

		String sqlstrrolei = "insert into logingroup(log_id,rol_id) values("
				+ log_id + ",'" + em.getRole_id() + "')";

		dbconn oper_rol = new dbconn();

		try {

			System.out.print(sqlstr);
			System.out.print(sqlstrtree);
			System.out.print(sqlstrtreei);
			System.out.print(sqlstrrolei);
			row = oper.execQuery(sqlstr);
			oper.execQuery(sqlstrtreei);
			oper_rol.execQuery(sqlstrrolei);

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			oper.Close();
		}
		return row;
	}

	// 检查重复名称
	public int UserCF(LoginModel em) throws Exception {
		int a = 0;
		ResultSet rs2 = null;
		String sqlstr2 = "select count(*) as copcount from login where log_name='"
				+ em.getLog_name() + "'";

		dbconn oper = new dbconn();
		rs2 = oper.GRS(sqlstr2);
		while (rs2.next()) {
			a = rs2.getInt("copcount");
		}
		return a;

	}

	// 查询系统用户信息
	public List<LoginModel> getLoginInfo(String str) {
		List<LoginModel> list = new ArrayList<LoginModel>();
		LoginModel m = null;
		String sql = "SELECT * FROM Login WHERE 1=1 " + str;

		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new LoginModel();
				m.setLog_id(rs.getInt("log_id"));
				m.setLog_name(rs.getString("log_name"));
				m.setLog_teamleader(rs.getInt("log_pid"));
				m.setLog_tel(rs.getString("log_tel"));
				m.setLog_sex(rs.getString("log_sex"));
				m.setLog_pws(rs.getString("log_pws"));
				m.setLog_email(rs.getString("log_email"));
				m.setLog_mobile(rs.getString("log_mobile"));
				m.setLog_intime(rs.getString("log_intime"));
				m.setDep_id(rs.getInt("dep_id"));
				m.setLog_spell(rs.getString("log_spell"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 修改用户信息
	public Integer User_Edit(LoginModel m) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db.getcall("User_Edit_cyj(?,?,?,?,?,?,?)");
			c.setInt(1, m.getLog_id());
			c.setString(2, m.getLog_name());
			c.setString(3, m.getLog_pws());
			c.setString(4, m.getLog_tel());
			c.setString(5, m.getLog_mobile());
			c.setString(6, m.getLog_email());
			c.registerOutParameter(7, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(7);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 修改用户信息并分配角色和部门
	public Integer User_Portion(LoginModel m) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
					.getcall("User_Portion_cyj(?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getLog_id());
			c.setString(2, m.getLog_name());
			c.setString(3, m.getLog_pws());
			c.setString(4, m.getLog_tel());
			c.setString(5, m.getLog_mobile());
			c.setString(6, m.getLog_email());
			c.setInt(7, m.getLog_inure());
			c.setInt(8, m.getDep_id());
			c.setInt(9, m.getLog_pid());
			c.setInt(10, m.getRole_id());
			c.registerOutParameter(11, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(11);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
