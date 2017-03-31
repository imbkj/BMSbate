package dal.EmReg;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.Types;

import Conn.dbconn;
import Model.EmRegContactModel;
import Model.EmRegistrationInfoModel;
import Model.EmResidencePermitInfoModel;
import Util.UserInfo;

public class EmReg_OperateDal {

	public Integer add(EmRegistrationInfoModel m) {
		Integer id = 0;
		dbconn db = new dbconn();

		try {
			id = Integer
					.parseInt(db
							.callWithReturn(
									"{?=call EmRegistrationInfoAdd_P_ply(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, m.getGid(), m.getCid(),
									m.getOwnmonth(), m.getErin_t_kind(),
									m.getErin_idcard(), m.getErin_folk(),
									m.getErin_educationcode(),
									m.getErin_education(), m.getErin_marital(),
									m.getErin_party(), m.getErin_hjadd(),
									m.getErin_nowadd(), m.getErin_title(),
									m.getErin_titlecode(),
									m.getErin_computerid(),
									m.getErin_worktime(),
									m.getErin_if_resident_con(),
									m.getErin_handover_people(),
									m.getErin_former_name(),
									m.getErin_photo_number(),
									m.getErin_hjtypecode(), m.getErin_hjtype(),
									m.getErin_wcin_code(), m.getErin_oe_type(),
									m.getErin_compact_kind(),
									m.getErin_if_unlimited(),
									m.getErin_compact_ylimit(),
									m.getErin_compact_s_date(),
									m.getErin_compact_e_date(),
									m.getErin_hj_in_sz_way(),
									m.getErin_in_sz_remark(),
									m.getErin_salary(),
									m.getErin_is_parttime(),
									m.getErin_idcard_address(),
									m.getErin_address_number(),
									m.getErin_come_sz_date(),
									m.getErin_come_sz_reason(),
									m.getErin_house_class(),
									m.getErin_s_place(),
									m.getErin_house_mode(), m.getErin_r_date(),
									m.getErin_file_place(),
									m.getErin_in_file_people(),
									m.getErin_get_people(),
									m.getErin_stop_state(),
									m.getErin_stop_reason(),
									m.getErin_stop_date(),
									m.getErin_stop_people(),
									m.getErin_back_people(),
									m.getErin_back_date(),
									m.getErin_back_reason(),
									m.getErin_laststate(), m.getErin_state(),
									m.getErin_tzl_state(), m.getErin_remark(),
									m.getErin_addname(), m.getErin_addtime(),
									m.getErin_folkcode(),
									m.getErin_maritalcode(),
									m.getErin_partycode(),
									m.getErin_skilllevelcode(),
									m.getErin_unit_b_date(), m.getErin_phone(),
									m.getErin_mobile(), m.getErin_epname(),
									m.getErin_epphone(), m.getErin_epmobile(),
									m.getErin_birthcontrol(),
									m.getErin_skilllevel(), m.getErin_wcin(),
									m.getErin_in_sz_date(),m.getErin_datakeeptype()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	public Integer mod(EmRegistrationInfoModel m) {
		Integer row = 0;
		dbconn db = new dbconn();

		try {
			row = Integer
					.parseInt(db
							.callWithReturn(
									"{?=call EmRegistrationInfoMod_P_ply(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, m.getErin_id(), m.getGid(),
									m.getCid(), m.getOwnmonth(),
									m.getErin_t_kind(), m.getErin_idcard(),
									m.getErin_folkcode(), m.getErin_folk(),
									m.getErin_educationcode(),
									m.getErin_education(),
									m.getErin_maritalcode(),
									m.getErin_marital(), m.getErin_partycode(),
									m.getErin_party(), m.getErin_hjadd(),
									m.getErin_nowadd(), m.getErin_title(),
									m.getErin_titlecode(),
									m.getErin_skilllevelcode(),
									m.getErin_skilllevel(),
									m.getErin_computerid(),
									m.getErin_worktime(),
									m.getErin_if_resident_con(),
									m.getErin_handover_people(),
									m.getErin_former_name(),
									m.getErin_photo_number(),
									m.getErin_hjtypecode(), m.getErin_hjtype(),
									m.getErin_wcin(), m.getErin_wcin_code(),
									m.getErin_oe_type(),
									m.getErin_compact_kind(),
									m.getErin_if_unlimited(),
									m.getErin_unit_b_date(),
									m.getErin_compact_ylimit(),
									m.getErin_compact_s_date(),
									m.getErin_compact_e_date(),
									m.getErin_hj_in_sz_way(),
									m.getErin_in_sz_remark(),
									m.getErin_salary(),
									m.getErin_is_parttime(),
									m.getErin_idcard_address(),
									m.getErin_address_number(),
									m.getErin_come_sz_date(),
									m.getErin_come_sz_reason(),
									m.getErin_house_class(),
									m.getErin_s_place(),
									m.getErin_house_mode(), m.getErin_r_date(),
									m.getErin_file_place(),
									m.getErin_in_file_people(),
									m.getErin_get_people(),
									m.getErin_stop_state(),
									m.getErin_stop_reason(),
									m.getErin_stop_date(),
									m.getErin_stop_people(),
									m.getErin_back_people(),
									m.getErin_back_date(),
									m.getErin_back_reason(),
									m.getErin_laststate(), m.getErin_state(),
									m.getErin_tzl_state(), m.getErin_remark(),
									m.getErin_addname(), m.getErin_addtime(),
									m.getErin_reg_state(), m.getErin_tapr_id(),
									m.getErin_phone(), m.getErin_mobile(),
									m.getErin_epname(), m.getErin_epphone(),
									m.getErin_epmobile(),
									m.getErin_birthcontrol(),
									m.getErin_in_sz_date(),
									m.getErin_getdata_date(),
									m.getErin_dw_entering(),m.getErin_datakeeptype()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public boolean UpdateTaprid(int daid, int taprid) {
		dbconn db = new dbconn();
		PreparedStatement psmt = null;
		int row = 0;
		String sql = "update EmRegistrationInfo set erin_tapr_id=? where erin_id=?";

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

	public Integer UpdateState(EmRegistrationInfoModel m) {
		Integer row = 0;
		dbconn db = new dbconn();

		try {
			row = Integer
					.parseInt(db
							.callWithReturn(
									"{?=call EmRegistrationInfoUpdateState_P_ply(?,?,?,?,?,?)}",
									Types.INTEGER, m.getErin_id(),
									m.getErin_state(), m.getErin_reg_state(),
									m.getErsr_addname(), m.getErsr_statetime(),
									m.getErsr_remark()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public Integer DocBack(Integer erin_id) {
		dbconn db = new dbconn();
		Integer row = 0;
		String sql = "update EmRegistrationInfo set erin_tzl_state=1 where erin_id=?";

		try {
			PreparedStatement psmt = db.getpre(sql);

			psmt.setInt(1, erin_id);

			row = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	// 终止申请
	public Integer erinStopApp(EmRegistrationInfoModel m) {
		Integer row = 0;
		dbconn db = new dbconn();

		try {

			CallableStatement c = db
					.getcall("EmRegistrationInfoStopApp_P_lsb(?,?,?,?,?)");
			c.setInt(1, m.getGid());
			c.setString(2, m.getErin_stop_reason());
			c.setString(3, m.getErin_stop_date());
			c.setString(4, m.getErin_stop_people());
			c.registerOutParameter(5, java.sql.Types.INTEGER);
			c.execute();
			row = c.getInt(5);
		} catch (Exception e) {
			row = 0;
		}
		return row;
	}

	// 终止申报
	public Integer erinStop(EmRegistrationInfoModel m) {
		Integer row = 0;
		dbconn db = new dbconn();

		try {
			CallableStatement c = db
					.getcall("EmRegistrationInfoStop_P_lsb(?,?)");
			c.setInt(1, m.getGid());
			c.registerOutParameter(2, java.sql.Types.INTEGER);
			c.execute();

			row = c.getInt(2);

		} catch (Exception e) {
			row = 0;
		}
		return row;
	}

	public Integer UpdateTime(EmRegistrationInfoModel m) {
		Integer row = 0;
		dbconn db = new dbconn();

		try {
			row = Integer.parseInt(db.callWithReturn(
					"{?=call EmRegistrationInfoUpdateState_P_cyj(?,?,?,?,?)}",
					Types.INTEGER, m.getErin_id(), m.getErin_state(),
					m.getErsr_addname(), m.getErsr_statetime(),
					m.getErsr_remark()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public Integer updateerin(Integer erin_id, Integer erin_ifbackdata) {
		Integer k = 0;
		String sql = "update EmRegistrationInfo set erin_ifbackdata="
				+ erin_ifbackdata + " where erin_id=" + erin_id;
		dbconn db = new dbconn();
		k = db.execQuery(sql);
		return k;
	}

	public Integer docinfoback(Integer erin_id, String str) {
		Integer k = 0;
		String sql = "update EmRegistrationInfo set erin_ifbackdata=2" + str
				+ " where erin_id=" + erin_id;
		dbconn db = new dbconn();
		k = db.execQuery(sql);
		return k;
	}

	// 添加居住证
	public Integer EmResidencePermitInfoAdd(EmResidencePermitInfoModel m) {
		Integer row = 0;
		dbconn db = new dbconn();

		try {
			CallableStatement c = db
					.getcall("EmResidencePermitInfoAdd_P_cyj(?,?,?,?,?,?)");
			c.setInt(1, m.getGid());
			c.setInt(2, m.getCid());
			c.setString(3, m.getOwnmonth());
			c.setInt(4, m.getErin_id());
			c.setString(5, UserInfo.getUsername());
			c.registerOutParameter(6, java.sql.Types.INTEGER);
			c.execute();

			row = c.getInt(6);

		} catch (Exception e) {
			row = 0;
		}
		return row;
	}

	// 添加联系记录
	public Integer EmRegContactAdd(EmRegContactModel model) {
		Integer k = 0;
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
					.getcall("EmRegContact_Add_p_CYJ(?,?,?,?,?,?,?)");
			c.setInt(1, model.getCont_erin_id());
			c.setString(2, model.getCont_type());
			c.setString(3, model.getCont_content());
			c.setString(4, model.getCont_addname());
			c.setString(5, model.getCont_remark());
			c.setString(6, model.getCont_tablename());
			c.registerOutParameter(7, java.sql.Types.INTEGER);
			c.execute();
			k = c.getInt(7);

		} catch (Exception e) {
			k = 0;
		}
		return k;
	}
}
