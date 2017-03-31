package dal.EmPay;

import java.sql.CallableStatement;
import java.sql.SQLException;

import Util.UserInfo;

import Conn.dbconn;

public class EmPay_OperateDal {
	private dbconn conn = new dbconn();
	String username = UserInfo.getUsername();

	// 任务单编号更新
	public int uppayid(int ccsa_id, int tapr_id) throws Exception {
		int row = 0;
		String sqlstr = "update EmShouldPayAut set espa_tapr_id=" + tapr_id
				+ " where espa_id=" + ccsa_id + "";
		dbconn update = new dbconn();
		try {

			row = update.execQuery(sqlstr);

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			update.Close();
		}
		return row;
	}

	// 支付更新
	public int Up_Pay(String paynum, String ownmonth, String username1,
			String type) {
		try {
			CallableStatement c = conn
					.getcall("ESPA_AutUpdate_P_zzq(?,?,?,?,?)");
			c.setString(1, type);
			c.setString(2, ownmonth);
			c.setString(3, paynum);
			c.setString(4, username);
			c.registerOutParameter(5, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(5);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 支付明细添加
	public int add_pay(String gid, String cid, String paynum, String ownmonth,
			String type, String fee, String tid) {
		try {
			CallableStatement c = conn
					.getcall("ESPA_Add_P_zzq(?,?,?,?,?,?,?,?,?,?)");
			c.setString(1, gid);
			c.setString(2, cid);
			c.setString(3, type);
			c.setString(4, fee);
			c.setString(5, "");
			c.setString(6, username);
			c.setString(7, ownmonth);
			c.setString(8, tid);
			c.setString(9, paynum);
			c.registerOutParameter(10, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(10);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 支付财务审核
	public int Aut_Pay(int espa_id, String str1) {
		try {

			CallableStatement c = conn.getcall("ESPA_Aut_P_zzq(?,?,?)");
			c.setInt(1, espa_id);
			c.setString(2, str1);
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 支付总经理审核
	public int MgAut_Pay(int espa_id, String str1) {
		try {

			CallableStatement c = conn.getcall("ESPA_MgAut_P_zzq(?,?,?)");
			c.setInt(1, espa_id);
			c.setString(2, str1);
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 支付
	public int Aut_PayOk(int espa_id,String em1, String em2,
			String em3, String em4, String em5, String em6, String em7,
			String ownmonth, String paynum,String username) {
		try {
			CallableStatement c = conn.getcall("ESPA_PayOk_P_zzq(?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, espa_id);
			c.setString(2, ownmonth);
			c.setString(3, paynum);
			c.setString(4, em2);
			c.setString(5, em3);
			c.setString(6, em5);
			c.setString(7, username);
			c.setString(8, em4);
			c.setString(9, em7);
			c.setString(10, em6);
			c.registerOutParameter(11, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(11);

		} catch (SQLException e) {
			return 1;
		}
	}
}
