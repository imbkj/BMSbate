package dal.SystemControl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import Conn.dbconn;
import Model.LoginModel;
import Model.RoleModel;

public class RoleAddDal {
	public int AddReg(RoleModel em) throws Exception {
		int row = 0;

		String sqlstr = "insert into role (rol_name,rol_index) values ('"+ em.getRol_name()+ "','"+em.getRol_index()+"')";

		dbconn oper = new dbconn();
		try {

			System.out.print(sqlstr);
			row = oper.execQuery(sqlstr);

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			oper.Close();
		}
		return row;
	}
	
	//检查重复名称
			public int RoleCF(RoleModel em) throws Exception {
				int a = 0;
				ResultSet rs2 = null;
				String sqlstr2 = "select count(*) as copcount from role where rol_name='"+ em.getRol_name()+"'";

				dbconn oper = new dbconn();
				rs2 = oper.GRS(sqlstr2);
				while (rs2.next()) {
					a = rs2.getInt("copcount");
				}
				return a;
				
			}
}
