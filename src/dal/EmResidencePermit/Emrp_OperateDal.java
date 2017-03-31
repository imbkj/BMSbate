package dal.EmResidencePermit;

import java.sql.PreparedStatement;
import java.sql.Types;

import Conn.dbconn;
import Model.EmResidencePermitChangeModel;
import Model.EmResidencePermitInfoModel;
import Util.DateStringChange;
import Util.UserInfo;

public class Emrp_OperateDal {
	public Integer add(EmResidencePermitInfoModel m1) {
		Integer id = 0;
		dbconn db = new dbconn();

		try {
			id = Integer
					.parseInt(db
							.callWithReturn(
									"{?=call EmResidencePermitInfoAdd_P_ply(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, m1.getGid(), m1.getCid(),
									m1.getOwnmonth(), m1.getErin_id(),
									m1.isErpi_w_photo_number(),
									m1.isErpi_w_skill_level(),
									m1.isErpi_w_a_sz_date(),
									m1.isErpi_w_r_date(),
									m1.isErpi_w_house_class(),
									m1.isErpi_w_r_mode(), m1.getErpi_t_kind(),
									m1.getErpi_handover_people(),
									m1.getErpi_dw_entering(),
									m1.getErpi_payment_kind(),
									m1.getErpi_payment_state(),
									m1.getErpi_fee(), m1.getErpi_fee_state(),
									m1.getErpi_if_invoice(),
									m1.getErpi_app_type(),
									m1.getErpi_app_con(),
									m1.getErpi_send_target(),
									m1.getErpi_invoice_date(),
									m1.getErpi_cl_number(),
									m1.getErpi_wd_applicant(),
									m1.getErpi_wd_loan_date(),
									m1.getErpi_ri_date(),
									m1.getErpi_csd_applicant(),
									m1.getErpi_csd_loan_date(),
									m1.getErpi_csd_clearing_date(),
									m1.getErpi_return_people(),
									m1.getErpi_return_date(),
									m1.getErpi_return_reason(),
									m1.getErpi_laststate(), m1.getErpi_state(),
									m1.getErpi_remark(), m1.getErpi_addname(),
									m1.getErpi_reg_state(),
									m1.getErpi_tzl_state(), m1.getErpi_ifedit())
							.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	public Integer mod(EmResidencePermitInfoModel m) {
		Integer row = 0;
		dbconn db = new dbconn();

		try {
			row = Integer
					.parseInt(db
							.callWithReturn(
									"{?=call EmResidencePermitInfoMod_P_ply(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, m.getErpi_id(), m.getGid(),
									m.getCid(), m.getOwnmonth(),
									m.getErin_id(), m.isErpi_w_photo_number(),
									m.isErpi_w_skill_level(),
									m.isErpi_w_a_sz_date(),
									m.isErpi_w_r_date(),
									m.isErpi_w_house_class(),
									m.isErpi_w_r_mode(), m.getErpi_t_kind(),
									m.getErpi_handover_people(),
									m.getErpi_dw_entering(),
									m.getErpi_payment_kind(),
									m.getErpi_payment_state(), m.getErpi_fee(),
									m.getErpi_fee_state(),
									m.getErpi_if_invoice(),
									m.getErpi_app_type(), m.getErpi_app_con(),
									m.getErpi_send_target(),
									m.getErpi_invoice_date(),
									m.getErpi_cl_number(),
									m.getErpi_wd_applicant(),
									m.getErpi_wd_loan_date(),
									m.getErpi_ri_date(),
									m.getErpi_csd_applicant(),
									m.getErpi_csd_loan_date(),
									m.getErpi_csd_clearing_date(),
									m.getErpi_return_people(),
									m.getErpi_return_date(),
									m.getErpi_return_reason(),
									m.getErpi_laststate(), m.getErpi_state(),
									m.getErpi_remark(), m.getErpi_addname(),
									m.getErpi_addtime(), m.getErpi_reg_state(),
									m.getErpi_tzl_state()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public boolean UpdateTaprid(int daid, int taprid) {
		dbconn db = new dbconn();
		PreparedStatement psmt = null;
		int row = 0;
		String sql = "update EmResidencePermitInfo set erpi_tapr_id=? where erpi_id=?";

		try {
			psmt = db.getpre(sql);

			psmt.setInt(1, taprid);
			psmt.setInt(2, daid);
			row = psmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return row > 0 ? true : false;
	}

	public boolean UpdateTaprid1(int daid, int taprid) {
		dbconn db = new dbconn();
		PreparedStatement psmt = null;
		int row = 0;
		String sql = "update EmResidencePermitChange set erpc_tapr_id=? where erpc_id=?";

		try {
			psmt = db.getpre(sql);

			psmt.setInt(1, taprid);
			psmt.setInt(2, daid);
			row = psmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return row > 0 ? true : false;
	}

	public Integer UpdateState(EmResidencePermitInfoModel m) {
		Integer row = 0;
		dbconn db = new dbconn();

		try {
			row = Integer
					.parseInt(db
							.callWithReturn(
									"{?=call EmResidencePermitInfoUpdateState_P_ply(?,?,?,?,?,?)}",
									Types.INTEGER, m.getErpi_id(),
									m.getErpi_state(), m.getErpi_reg_state(),
									m.getEpsr_addname(), m.getEpsr_statetime(),
									m.getEpsr_remark()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public Integer back(EmResidencePermitInfoModel m) {
		Integer row = 0;
		dbconn db = new dbconn();

		try {
			row = Integer.parseInt(db.callWithReturn(
					"{?=call EmResidencePermitInfoBack_P_ply(?,?,?,?)}",
					Types.INTEGER, m.getErpi_id(), m.getErpi_state(),
					m.getErpi_return_reason(), m.getErpi_return_people())
					.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public Integer DocBack(Integer erpi_id) {
		dbconn db = new dbconn();
		Integer row = 0;
		String sql = "update EmResidencePermitInfo set erpi_tzl_state=1 where erpi_id=?";

		try {
			PreparedStatement psmt = db.getpre(sql);

			psmt.setInt(1, erpi_id);

			row = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public Integer ChangeBack(Integer erpi_id, String erpc_return_reason) {
		dbconn db = new dbconn();
		Integer row = 0;
		String sql = "update EmResidencePermitChange set erpc_return_reason=? where erpc_id=?";
		try {
			PreparedStatement psmt = db.getpre(sql);
			psmt.setString(1, erpc_return_reason);
			psmt.setInt(2, erpi_id);

			row = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public Integer changeadd(EmResidencePermitChangeModel m) {
		Integer id = 0;
		dbconn db = new dbconn();

		try {
			id = Integer
					.parseInt(db
							.callWithReturn(
									"{?=call EmResidencePermitChangeAdd_P_ply(?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, m.getGid(), m.getCid(),
									m.getOwnmonth(), m.getErpc_idcard(),
									m.getErpc_laststate(), m.getErpc_state(),
									m.getErpc_return_people(),
									m.getErpc_return_date(),
									m.getErpc_return_reason(),
									m.getErpc_addname(), m.getErpc_addtime())
							.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	public Integer changeUpdateState(EmResidencePermitChangeModel m) {
		Integer row = 0;
		dbconn db = new dbconn();

		try {
			row = Integer
					.parseInt(db
							.callWithReturn(
									"{?=call EmResidencePermitChangeUpdateState_P_ply(?,?,?,?,?)}",
									Types.INTEGER,
									m.getErpc_id(),
									m.getErpc_state(),
									m.getEpcr_addname(),
									DateStringChange.DatetoSting(
											m.getEpcr_statetime(), "yyyy-MM-dd"),
									m.getEpcr_remark()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}
}
