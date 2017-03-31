package dal.SystemControl;

import Conn.dbconn;
import Model.RoleModel;

public class RoleEditDal {
	public int editrol(RoleModel em) throws Exception {
		int row = 0;

		String sqlstr = "update role set rol_name='"+ em.getRol_name()+ "',rol_index='"+em.getRol_index()+"' where rol_id="+em.getRol_id();

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
