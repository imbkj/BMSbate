package dal.EmCommercialInsurance;

import java.sql.CallableStatement;
import java.sql.SQLException;

import Conn.dbconn;

public class CI_InsurantClaim_OperateDal {
	private dbconn conn = new dbconn();

	// 任务单编号更新
	public int upinsurantid(int ccsa_id, int tapr_id) throws Exception {
		int row = 0;
		String sqlstr = "update EmCommercialClaim set eccl_tapr_id=" + tapr_id
				+ " where eccl_id=" + ccsa_id + "";
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
	public int Add_InsurantClaim(String gid, String cid, String em1,
			String em2, String em3, String em4, String em5, String em6,
			String em7, String em8, String em9, String em10, String em11,
			String em12, String username, String remark, String ra1,
			String ra2, String ra3, String ecin_iname) {
		try {
			CallableStatement c = conn
					.getcall("EmComInsuranceClaimAdd_P_zzq(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

			System.out.println(gid);
			System.out.println(cid);
			System.out.println(em1);
			System.out.println(em2);
			System.out.println(em3);
			System.out.println(em4);
			System.out.println(em5);
			System.out.println(em6);
			System.out.println(em7);
			System.out.println(em8);
			System.out.println(em9);
			System.out.println(em10);
			System.out.println(em11);
			System.out.println(em12);
			System.out.println(username);
			System.out.println(remark);
			System.out.println(ra1);
			System.out.println(ra2);
			System.out.println(ra3);
			System.out.println(ecin_iname);

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
			c.setString(13, em11);
			c.setString(14, em12);
			c.setString(15, username);
			c.setString(16, remark);
			c.setString(17, ra1);
			c.setString(18, ra2);
			c.setString(19, ra3);
			c.setString(20, ecin_iname);
			c.registerOutParameter(21, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(21);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 商业保险修改
	public int Edit_InsurantClaim(String gid, String cid, String em1,
			String em2, String em3, String em4, String em5, String em6,
			String em7, String em8, String em9, String em10, String em11,
			String em12, String username, String remark, String ra1,
			String ra2, String ra3, String ecin_iname, String eccl_id) {
		try {
			CallableStatement c = conn
					.getcall("EmComInsuranceClaimEdit_P_zzq(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			System.out.println("xxxxxxxxxxxxxxxxx");
			System.out.println(gid);
			System.out.println(cid);
			System.out.println(em1);
			System.out.println(em2);
			System.out.println(em3);
			System.out.println(em4);
			System.out.println(em5);
			System.out.println(em6);
			System.out.println(em7);
			System.out.println(em8);
			System.out.println(em9);
			System.out.println(em10);
			System.out.println(em11);
			System.out.println(em12);
			System.out.println(username);
			System.out.println(remark);
			System.out.println(ra1);
			System.out.println(ra2);
			System.out.println(ra3);
			System.out.println(ecin_iname);
			System.out.println(eccl_id);
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
			c.setString(13, em11);
			c.setString(14, em12);
			c.setString(15, username);
			c.setString(16, remark);
			c.setString(17, ra1);
			c.setString(18, ra2);
			c.setString(19, ra3);
			c.setString(20, ecin_iname);
			c.setString(21, eccl_id);
			c.registerOutParameter(22, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(22);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 商业保险删除
	public int Del_InsurantClaim(String eccl_id) {
		try {
			CallableStatement c = conn
					.getcall("EmComInsuranceClaimDel_P_zzq(?,?)");
			c.setString(1, eccl_id);
			c.registerOutParameter(2, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(2);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 商业保险理赔待处理审核
	public int WaitAut_Insurant(String eccl_id, String bx_date, String bx_name) {
		try {
			CallableStatement c = conn
					.getcall("EmComInsuranceClaimWaitAut_P_zzq(?,?,?,?)");
			c.setString(1, eccl_id);
			c.setString(2, bx_date);
			c.setString(3, bx_name);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 商业保险申报
	public int AutUP_Insurant(String eccl_id, String bx_date, String fl_date) {
		try {
			CallableStatement c = conn
					.getcall("EmComInsuranceClaimAutUp_P_zzq(?,?,?,?)");
			c.setString(1, eccl_id);
			c.setString(2, bx_date);
			c.setString(3, fl_date);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);

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
}
