package dal.CoReg;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.Types;

import Conn.dbconn;
import Model.CoOnlineRegisterInfoModel;
import Model.DocumentsInfoModel;
import Util.UserInfo;

public class CoReg_OperateDal {

	public Integer CoRegAdd(CoOnlineRegisterInfoModel m) {
		Integer id = 0;
		dbconn db = new dbconn();

		try {
			id = Integer
					.valueOf(db
							.callWithReturn(
									"{?=call CoOnlineRegisterInfoAdd_P_ply(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, m.getCid(), m.getOwnmonth(),
									m.getCori_reg_type(),
									m.getCori_reg_transact_type(),
									m.getCori_reg_account(),
									m.getCori_reg_pwd(),
									m.getCori_industry_type(),
									m.getCori_reg_fund(),
									m.getCori_belong_unit(),
									m.getCori_legal_person(),
									m.getCori_lp_tel(), m.getCori_fax(),
									m.getCori_unti_principal(),
									m.getCori_up_tel(), m.getCori_postcode(),
									m.getCori_open_date(),
									m.getCori_operate_s_date(),
									m.getCori_ss_code(), m.getCori_salary(),
									m.getCori_stock_unit1(),
									m.getCori_stock_unit2(),
									m.getCori_stock_unit3(),
									m.getCori_stock_unit4(),
									m.getCori_if_build(), m.getCori_if_pass(),
									m.getCori_if_tell(), m.getCori_all_p(),
									m.getCori_sz_p(), m.getCori_foreign_p(),
									m.getCori_sign_contract_p(),
									m.getCori_if_give(),
									m.getCori_if_build_sign_in(),
									m.getCori_wtime_type(),
									m.getCori_hour_per_day(),
									m.getCori_hour_per_week(),
									m.getCori_day_per_week(),
									m.getCori_if_ot(), m.getCori_i_work(),
									m.getCori_sc_man_hour(),
									m.getCori_salary_date(),
									m.getCori_if_arrear(),
									m.getCori_if_lowest(),
									m.getCori_if_ar_ot(),
									m.getCori_if_join_ss(), m.getCori_join_p(),
									m.getCori_if_happen_ld(),
									m.getCori_if_kid(), m.getCori_if_union(),
									m.getCwin_id(), m.getCori_reg_date(),
									m.getCori_oi_code(), m.getCori_bl_code(),
									m.getCori_oaa_place(), m.getCori_ws_name(),
									m.getCori_ws_tel(),
									m.getCori_rp_t_address(),
									m.getCori_rp_t_tel(), m.getCori_address(),
									m.getCori_state(), m.getCori_backname(),
									m.getCori_backtime(),
									m.getCori_backreason(), m.getCori_remark(),
									m.getCori_addname(),
									m.getCori_if_responsebook(),
									m.getCori_if_sign_responsebook(),
									m.getCori_web_password(),
									m.getCori_reg_state()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	public Integer CoRegMod(CoOnlineRegisterInfoModel m) {
		Integer id = 0;
		dbconn db = new dbconn();

		try {
			id = Integer
					.valueOf(db
							.callWithReturn(
									"{?=call CoOnlineRegisterInfoMod_P_ply(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, m.getCori_id(), m.getCid(),
									m.getOwnmonth(), m.getCori_reg_type(),
									m.getCori_reg_transact_type(),
									m.getCori_reg_account(),
									m.getCori_reg_pwd(),
									m.getCori_industry_type(),
									m.getCori_reg_fund(),
									m.getCori_belong_unit(),
									m.getCori_legal_person(),
									m.getCori_lp_tel(), m.getCori_fax(),
									m.getCori_unti_principal(),
									m.getCori_up_tel(), m.getCori_postcode(),
									m.getCori_open_date(),
									m.getCori_operate_s_date(),
									m.getCori_ss_code(), m.getCori_salary(),
									m.getCori_stock_unit1(),
									m.getCori_stock_unit2(),
									m.getCori_stock_unit3(),
									m.getCori_stock_unit4(),
									m.getCori_if_build(), m.getCori_if_pass(),
									m.getCori_if_tell(), m.getCori_all_p(),
									m.getCori_sz_p(), m.getCori_foreign_p(),
									m.getCori_sign_contract_p(),
									m.getCori_if_give(),
									m.getCori_if_build_sign_in(),
									m.getCori_wtime_type(),
									m.getCori_hour_per_day(),
									m.getCori_hour_per_week(),
									m.getCori_day_per_week(),
									m.getCori_if_ot(), m.getCori_i_work(),
									m.getCori_sc_man_hour(),
									m.getCori_salary_date(),
									m.getCori_if_arrear(),
									m.getCori_if_lowest(),
									m.getCori_if_ar_ot(),
									m.getCori_if_join_ss(), m.getCori_join_p(),
									m.getCori_if_happen_ld(),
									m.getCori_if_kid(), m.getCori_if_union(),
									m.getCwin_id(), m.getCori_reg_date(),
									m.getCori_oi_code(), m.getCori_bl_code(),
									m.getCori_oaa_place(), m.getCori_ws_name(),
									m.getCori_ws_tel(),
									m.getCori_rp_t_address(),
									m.getCori_rp_t_tel(), m.getCori_address(),
									m.getCori_state(), m.getCori_backname(),
									m.getCori_backtime(),
									m.getCori_backreason(), m.getCori_remark(),
									m.getCori_addname(),
									m.getCori_if_responsebook(),
									m.getCori_if_sign_responsebook(),
									m.getCori_web_password(),
									m.getCori_reg_state()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	public boolean UpdateTaprid(int daid, int taprid) {
		dbconn db = new dbconn();
		PreparedStatement psmt = null;
		int row = 0;
		String sql = "update CoOnlineRegisterInfo set cori_tapr_id=? where cori_id=?";

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

	public Integer UpdateState(int daid, int stateid) {
		dbconn db = new dbconn();
		PreparedStatement psmt = null;
		int row = 0;
		String sql = "update CoOnlineRegisterInfo set cori_state=? where cori_id=?";

		try {
			psmt = db.getpre(sql);

			psmt.setInt(1, stateid);
			psmt.setInt(2, daid);
			row = psmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public Integer UpdateState(CoOnlineRegisterInfoModel m) {
		dbconn db = new dbconn();
		Integer row = 0;

		try {
			row = Integer.valueOf(db.callWithReturn(
					"{?=call CoOnlineRegisterInfoUpdateState_P_ply(?,?,?,?)}",
					Types.INTEGER, m.getCori_id(), m.getCori_state(),
					UserInfo.getUsername(), m.getCrsr_statetime()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}
	
	//接管完成
	public Integer UpdateStateEnd(CoOnlineRegisterInfoModel m) {
		dbconn db = new dbconn();
		Integer row = 0;

		try {
			row = Integer.valueOf(db.callWithReturn(
					"{?=call CoOnlineRegisterInfoUpdateState_P_ply(?,?,?,?)}",
					Types.INTEGER, m.getCori_id(), m.getCori_state(),
					UserInfo.getUsername(), m.getCrsr_statetime()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public Integer DocBack(int cori_id) {
		dbconn db = new dbconn();
		Integer row = 0;
		String sql = "update CoOnlineRegisterInfo set cori_tzl_state=1 where cori_id=?";

		try {
			PreparedStatement psmt = db.getpre(sql);

			psmt.setInt(1, cori_id);

			row = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public Integer Back(CoOnlineRegisterInfoModel m) {
		Integer row = 0;
		dbconn db = new dbconn();
		String sql = "update CoOnlineRegisterInfo set cori_backname=?,cori_backreason=?,"
				+ " cori_state=?,cori_backtime=getdate(),cori_isback=1 where cori_id=?";

		try {
			PreparedStatement psmt = db.getpre(sql);

			psmt.setString(1, m.getCori_backname());
			psmt.setString(2, m.getCori_backreason());
			psmt.setString(3, m.getCori_state());
			psmt.setInt(4, m.getCori_id());

			row = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public Integer NeedDoc(CoOnlineRegisterInfoModel m) {
		Integer row = 0;
		dbconn db = new dbconn();
		String sql = "update CoOnlineRegisterInfo set cori_need_doc=? where cori_id=?";

		try {
			PreparedStatement psmt = db.getpre(sql);

			psmt.setString(1, m.getCori_need_doc());
			psmt.setInt(2, m.getCori_id());

			row = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public void EmRegDocAdd(Integer cori_id, String addname) {
		dbconn db = new dbconn();

		try {
			CallableStatement csmt = db
					.getcall("CoOnlineRegisterInfoDocAdd_P_ply(?,?)");

			csmt.setInt(1, cori_id);
			csmt.setString(2, addname);

			csmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Integer EmRegDocUpdateState(DocumentsInfoModel m) {
		Integer row = 0;
		dbconn db = new dbconn();
		String sql = "update DipzRelation set dire_state=? where dire_id=?";

		try {
			PreparedStatement psmt = db.getpre(sql);

			psmt.setString(1, m.getDire_state());
			psmt.setInt(2, Integer.parseInt(m.getDire_id()));

			row = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	// 计划生育责任书新增
	public Integer CoBookAdd(Integer cid, String booktype) {
		try {
			dbconn db = new dbconn();
			CallableStatement csmt = db
					.getcall("ResponsibilityBookAdd_p_cyj(?,?,?,?)");

			csmt.setInt(1, cid);
			csmt.setString(2, booktype);
			csmt.setString(3, UserInfo.getUsername());
			csmt.registerOutParameter(4, java.sql.Types.INTEGER);
			csmt.execute();
			return csmt.getInt(4);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	// 更新计划生育责任书信息
	public Integer updateresponebook(String str, Integer rsbk_cori_id) {
		Integer k = 0;
		String sql = "update ResponsibilityBook set rebk_modname='"
				+ UserInfo.getUsername() + "',rebk_modtime=getdate()" + str
				+ " where rebk_state<>6 and rebk_cori_id=" + rsbk_cori_id;
		dbconn db = new dbconn();
		k = db.execQuery(sql);
		return k;
	}

	// 更新计划生育责任书信息
	public Integer updateCoRenInfo(String cori_key, Integer cori_id) {
		Integer k = 0;
		String sql = "update CoOnlineRegisterInfo set cori_key='"
				+cori_key+ "' where cori_id=" + cori_id;
		dbconn db = new dbconn();
		k = db.execQuery(sql);
		return k;
	}
}
