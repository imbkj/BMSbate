package dal.SystemControl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.LoginUserModel;
import Model.RoleModel;
import Util.pinyin4jUtil;

public class PubRoleManangerDal {
	public int AddReg(RoleModel em) throws Exception {
		int row = 0;
		String sqlstr = "insert into logingroup (log_id,rol_id) select log_id,"+em.getRol_id()+" from login where log_name='"+em.getRol_name()+"'";

		dbconn oper = new dbconn();
		try {
			row = oper.execQuery(sqlstr);
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			oper.Close();
		}
		return row;
	}
	
	public int DetReg(RoleModel em) throws Exception {
		int row = 0;
		String sqlstr1 = "delete logingroup where rol_id="+em.getRol_id();
		dbconn oper = new dbconn();
		try {
			System.out.println(sqlstr1);
			row = oper.execQuery(sqlstr1);
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			oper.Close();
		}
		return row;
	}
}
