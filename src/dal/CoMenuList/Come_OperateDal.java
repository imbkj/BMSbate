package dal.CoMenuList;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import Util.UserInfo;
import Conn.dbconn;

public class Come_OperateDal {
	// 更新业务菜单权限
	public int updateCobaseMenuListRel(int role_id, int come_id) {
		int k = 0;
		ResultSet rs = null;
		dbconn db = new dbconn();
		String sqlstr = "select * from CobaseMenuListRel where reml_role_id="
				+ role_id + " and reml_come_id=" + come_id;
		try {
			rs = db.GRS(sqlstr);
			if (!rs.next()) {
				String username = UserInfo.getUsername();
				Date currentTime = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String meustr = "insert into CobaseMenuListRel(reml_role_id,reml_come_id,"
						+ "reml_addname,reml_addtime) values("
						+ role_id
						+ ","
						+ come_id
						+ ",'"
						+ username
						+ "','"
						+ formatter.format(currentTime) + "')";
				k = db.execQuery(meustr);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k;
	}

	// 删除菜单权限
	public int deleteCobaseMenuListRel(String str, int role_id) {
		int k = 0;
		dbconn db = new dbconn();
		String sqlstr = "";
		if (str == "all" || str.equals("all")) {
			sqlstr = "delete from CobaseMenuListRel where reml_role_id="
					+ role_id;
		} else {
			sqlstr = "delete from CobaseMenuListRel where reml_role_id="
					+ role_id + " and reml_come_id not in(" + str + ")";
		}
		k = db.execQuery(sqlstr);
		return k;
	}
}
