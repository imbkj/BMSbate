package dal.EmCommercialInsurance;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.CI_Insurant_ListModel;

public class CI_Insurant_ApplyOperateDal {
	private dbconn conn = new dbconn();

	// 任务单编号更新
	public int upinsurantid(int ccsa_id, int tapr_id) throws Exception {
		int row = 0;
		String sqlstr = "update EmComInsuranceApply set ecia_tapr_id="
				+ tapr_id + " where ecia_id=" + ccsa_id + "";
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

	// 任务单编号更新
	public int upinsurantchangeid(int ccsa_id, int tapr_id) throws Exception {
		int row = 0;
		String sqlstr = "update EmComInsuranceBChange set ecib_tapr_id="
				+ tapr_id + " where ecib_id=" + ccsa_id + "";
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

	// 商业保险新增
	public int Add_Insurant(String gid, String cid, String em1, String em2,
			String em3, String em4, String em5, String em6, String em7,
			String em8, String em9, String em10, String em11, String getld) {
		try {
			CallableStatement c = conn
					.getcall("EmComInsuranceAdd_P_zzq(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

			c.setString(1, gid);
			c.setString(2, cid);
			c.setString(3, em1);
			c.setString(4, em2);
			c.setString(5, em3);
			c.setString(6, em4);
			c.setString(7, em5);
			c.setString(8, em6);
			c.setString(9, em7);
			c.setString(10, em8);
			c.setString(11, em9);
			c.setString(12, em10);
			c.setString(13, getld);
			c.registerOutParameter(14, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(14);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 商业保险信息变更
	public int Change_Insurant(String gid, String cid, String name,
			String idcard, String sex, String birthday, String lname,
			String username) {
		try {
			CallableStatement c = conn
					.getcall("EmComInsuranceChange_P_zzq(?,?,?,?,?,?,?,?,?)");

			c.setString(1, gid);
			c.setString(2, cid);
			c.setString(3, name);
			c.setString(4, idcard);
			c.setString(5, sex);
			c.setString(6, birthday);
			c.setString(7, lname);
			c.setString(8, username);
			c.registerOutParameter(9, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(9);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 商业保险信息变更
	public int Apply_Insurant(String gid, String cid, String getld,
			String username,String f_date,String castsort) {
		try {
			CallableStatement c = conn
					.getcall("EmComInsuranceApply_P_zzq(?,?,?,?,?,?,?)");

			c.setString(1, gid);
			c.setString(2, cid);
			c.setString(3, getld);
			c.setString(4, username);
			c.setString(5, f_date);
			c.setString(6, castsort);
			c.registerOutParameter(7, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(7);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 商业保险信息变更
	public int Apply_InsurantUp(String gid) {
		try {
			CallableStatement c = conn
					.getcall("EmComInsuranceApplyUp_P_zzq(?,?)");
			System.out.println("sql");
			System.out.println(gid);
			c.setString(1, gid);
			c.registerOutParameter(2, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(2);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 商业保险信息变更审核
	public int AutChange_Insurant(String ecin_id) {
		try {
			CallableStatement c = conn
					.getcall("EmComInsuranceChangeAut_P_zzq(?,?)");
			c.setString(1, ecin_id);
			c.registerOutParameter(2, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(2);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 商业保险审核
	public int Aut_Insurant(String em1) {
		try {
			CallableStatement c = conn.getcall("EmComInsuranceAut_P_zzq(?,?)");

			c.setString(1, em1);
			c.registerOutParameter(2, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(2);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 商业保险申报
	public int AutUP_Insurant(String em1) {
		try {
			CallableStatement c = conn
					.getcall("EmComInsuranceAutUp_P_zzq(?,?)");
			c.setString(1, em1);
			c.registerOutParameter(2, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(2);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 商业保险停缴
	public int Out_Insurant(String ecin_id, String em1, String em2) {
		try {
			CallableStatement c = conn
					.getcall("EmComInsuranceOut_P_zzq(?,?,?,?)");
			c.setString(1, ecin_id);
			c.setString(2, em1);
			c.setString(3, em2);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 获取商保在保删除
	public int ecin_del(String id) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		List<CI_Insurant_ListModel> list = new ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "select ecic_inid from EmComInsuranceChange where ecic_id="
				+ id + "";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				String sqlstr1 = "delete EmComInsuranceChange where ecic_id="
						+ id + "";
				String sqlstr2 = "delete EmComInsurance where ecin_id="
						+ rs.getString("ecic_inid") + "";

				rs1 = db.GRS(sqlstr1);
				rs2 = db.GRS(sqlstr2);
			}
			return 1;
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return 1;
	}
}
