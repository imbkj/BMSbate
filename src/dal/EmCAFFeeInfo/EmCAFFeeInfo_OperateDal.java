package dal.EmCAFFeeInfo;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;

import Conn.dbconn;
import Model.EmArchivePaymentModel;
import Model.EmCAFFeeInfoModel;
import Util.DateStringChange;
import Util.UserInfo;

public class EmCAFFeeInfo_OperateDal {
	private dbconn conn = new dbconn();

	// 状态更新
	public int ecfiUpdateState(EmCAFFeeInfoModel m, int if_all) {
		try {
			CallableStatement c = conn
					.getcall("ECFI_State_P_lsb(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getEcfi_id());
			c.setInt(2, if_all);
			c.setString(3, m.getEcfi_payment_state());
			c.setInt(4, m.getEcfi_if_sic());
			c.setString(5, m.getEcfi_wd_applicant());
			c.setString(6, m.getEcfi_csd_applicant());
			c.setString(7, m.getEcfi_wd_loan_date());
			c.setString(8, m.getEcfi_csd_loan_date());
			c.setString(9, m.getEcfi_ri_date());
			c.setString(10, m.getEcfi_csd_clearing_date());
			c.setString(11, m.getEcfi_remark());
			c.setInt(12, m.getEcfi_loanstate());
			c.setString(13, m.getRspay());
			c.setString(14, m.getEcfi_addname());
			c.registerOutParameter(15, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(15);
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
	}

	// 退款
	public int ecfiRefundment(EmCAFFeeInfoModel m) {
		try {
			CallableStatement c = conn
					.getcall("ECFI_Refundment_P_lsb(?,?,?,?)");
			c.setInt(1, m.getEcfi_id());
			c.setString(2, m.getEcfi_refundment_date());
			c.setString(3, m.getEcfi_refundment_people());
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
	}

	// 新增数据
	public int ecfiAdd(EmCAFFeeInfoModel m) {
		try {
			CallableStatement c = conn
					.getcall("ECFI_Add_P_lsb(?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getGid());
			c.setInt(2, m.getCid());
			c.setInt(3, m.getOwnmonth());
			c.setInt(4, m.getEcfi_caf_id());
			c.setString(5, m.getEcfi_class());
			c.setString(6, m.getEcfi_cl_number());
			c.setString(7, m.getEcfi_payment_kind());
			c.setString(8, m.getEcfi_payment_state());
			c.setInt(9, m.getEcfi_fee());
			c.setString(10, m.getEcfi_addname());
			c.registerOutParameter(11, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(11);
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
	}

	// 修改数据
	public Integer paymentmod(EmArchivePaymentModel em, Integer id, String type) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "update EmCAFFeeInfo set ecfi_modtime=getdate(),ecfi_modname='"
				+ UserInfo.getUsername() + "'";
		if (id > 0) {
			BigDecimal bd = BigDecimal.ZERO;
			if (em.getEmap_file() != null && !em.getEmap_file().equals("")) {
				bd = bd.add(em.getEmap_file());
			}
			if (em.getEmap_hj() != null && !em.getEmap_hj().equals("")) {
				bd = bd.add(em.getEmap_hj());
			}
			if (em.getEmap_op() != null && !em.getEmap_op().equals("")) {
				bd = bd.add(em.getEmap_op());
			}

			sql += ",ecfi_fee=" + bd;
			if (em.getEmap_sdate() != null && !em.getEmap_sdate().equals("")) {
				sql += ",ecfi_cddate='" + em.getEmap_sdate() + "'";
			}
			if (em.getEmap_cdate() != null && !em.getEmap_cdate().equals("")) {
				sql += ",ecfi_sdate='" + em.getEmap_cdate() + "'";
			}
			sql += " where ecfi_caf_id=? and ecfi_class=?";
			try {
				i = db.updatePrepareSQL(sql, id, type);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;
	}

	// 删除数据
	public Integer ecfiDelByTabId(Integer id, String className) {
		dbconn db = new dbconn();
		String sql = "delete from EmCAFFeeInfo where ecfi_caf_id=? and ecfi_class=?";
		Integer i = 0;
		try {
			i = db.updatePrepareSQL(sql, id, className);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
}
