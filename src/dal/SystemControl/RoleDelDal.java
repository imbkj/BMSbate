package dal.SystemControl;

import Conn.dbconn;
import Model.RoleModel;

public class RoleDelDal {
	public int delrol(RoleModel em) throws Exception {
		int row = 0;

		String sqlstr = "delete role where rol_id="+em.getRol_id();

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
}
