package dal.EmCommissionOutNew;

import java.sql.CallableStatement;
import java.sql.SQLException;

import Conn.dbconn;

public class EmCommissionOut_OperateDal {
	private dbconn conn = new dbconn();

	// 任务单编号更新
	public int upinsurantid(int ccsa_id, int tapr_id) throws Exception {
		int row = 0;
		String sqlstr = "update EmCommissionOutChange set ecoc_tapr_id="
				+ tapr_id + " where ecoc_id=" + ccsa_id + "";
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

	// 委托外地新增
	public int Add_Insurant(String gid, String cid, String em1, String em2,
			String em3, String em4, String em5, String em6, String em7,
			String em8, String em9, String em10, String em11, String getld,
			String compact_qd) {
		try {
			CallableStatement c = conn
					.getcall("EmComInsuranceAdd_P_zzq(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

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
			c.setString(14, compact_qd);
			c.registerOutParameter(15, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(15);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 委托外地调整
	public int Change_WtOut(String ecou_id, String sb_base, String house_base,
			String ecoc_salary, String remark, String addname, String soin_id,
			String wtss_lab,String wt_fee_content) {
		try {
			CallableStatement c = conn
					.getcall("EmCommissionOut_NewChange_P_zzq(?,?,?,?,?,?,?,?,?,?)");
			
			c.setString(1, ecou_id);
			c.setString(2, sb_base);
			c.setString(3, house_base);
			c.setString(4, ecoc_salary);
			c.setString(5, remark);
			c.setString(6, addname);
			c.setString(7, soin_id);
			c.setString(8, wtss_lab);
			c.setString(9, wt_fee_content);
			c.registerOutParameter(10, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(10);

		} catch (SQLException e) {
			return 0;
		}
	}
	
	// 委托外地修改
	public int Edit_WtOut(String ecou_id, String sb_base, String house_base,
			String ecoc_salary, String remark, String addname, String soin_id,
			String wtss_lab) {
		try {
			CallableStatement c = conn
					.getcall("EmCommissionOut_NewEdit_P_zzq(?,?,?,?,?,?,?,?,?)");

			c.setString(1, ecou_id);
			c.setString(2, sb_base);
			c.setString(3, house_base);
			c.setString(4, ecoc_salary);
			c.setString(5, remark);
			c.setString(6, addname);
			c.setString(7, soin_id);
			c.setString(8, wtss_lab);
			c.registerOutParameter(9, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(9);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 委托外地新增重新提交
	public int AddRe_WtOut(String ecou_id, String sb_base, String house_base,
			String ecoc_salary, String remark, String addname, String soin_id,
			String wtss_la,String wt_fee_content,String compact_f,String compact_l) {
		try {
			CallableStatement c = conn
					.getcall("EmCommissionOut_NewAddRe_P_zzq(?,?,?,?,?,?,?,?,?,?,?,?)");

			c.setString(1, ecou_id);
			c.setString(2, sb_base);
			c.setString(3, house_base);
			c.setString(4, ecoc_salary);
			c.setString(5, remark);
			c.setString(6, addname);
			c.setString(7, soin_id);
			c.setString(8, wtss_la);
			c.setString(9, wt_fee_content);
			c.setString(10, compact_f);
			c.setString(11, compact_l);
			c.registerOutParameter(12, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(12);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 委托外地调整重新提交
	public int ChangeRe_WtOut(String ecou_id, String sb_base,
			String house_base, String ecoc_salary, String remark,
			String addname, String soin_id, String wtss_la,String compact_f,String compact_l,String wt_content_fee) {
		try {
			CallableStatement c = conn
					.getcall("EmCommissionOut_NewChangeRe_P_zzq(?,?,?,?,?,?,?,?,?,?)");

			c.setString(1, ecou_id);
			c.setString(2, sb_base);
			c.setString(3, house_base);
			c.setString(4, ecoc_salary);
			c.setString(5, remark);
			c.setString(6, addname);
			c.setString(7, soin_id);
			c.setString(8, wtss_la);
			c.setString(9, wt_content_fee);
			c.registerOutParameter(10, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(10);

		} catch (SQLException e) {
			return 1;
		}
	}
}
