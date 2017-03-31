package dal.EmCommissionOutNew;

import java.sql.CallableStatement;
import java.sql.SQLException;

import Util.UserInfo;

import Conn.dbconn;

public class EmCommissionOut_AutOperateDal {
	private dbconn conn = new dbconn();

	// 任务单编号更新
	public int upoutid(int ccsa_id, int tapr_id) throws Exception {
		int row = 0;
		String sqlstr = "update EmCommissionOutChange set ecoc_tapr_id="
				+ tapr_id + " where ecoc_id=" + ccsa_id + "";
		dbconn update = new dbconn();
		try {
			System.out.println(sqlstr);
			row = update.execQuery(sqlstr);

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			update.Close();
		}
		return row;
	}

	// 委托单一次确认
	public int Yc_Aut(String ecoc_id) {
		try {
			CallableStatement c = conn
					.getcall("EmCommissionOut_NewYAut_P_zzq(?,?,?)");

			c.setString(1, ecoc_id);
			c.setString(2, UserInfo.getUsername());
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 委托单二次确认
	public int Ec_Aut(String ecoc_id) {
		try {
			System.out.println("sb");
			System.out.println(ecoc_id);
			System.out.println(UserInfo.getUsername());
			CallableStatement c = conn
					.getcall("EmCommissionOut_NewEAut_P_zzq(?,?,?)");

			c.setString(1, ecoc_id);
			c.setString(2, UserInfo.getUsername());
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 委托单退回
	public int back(String ecoc_id,String remark) {
		try {
			CallableStatement c = conn
					.getcall("EmCommissionOut_NewBack_P_zzq(?,?,?,?)");

			c.setString(1, ecoc_id);
			c.setString(2, remark);
			c.setString(3, UserInfo.getUsername());
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);

		} catch (SQLException e) {
			return 1;
		}
	}
}
