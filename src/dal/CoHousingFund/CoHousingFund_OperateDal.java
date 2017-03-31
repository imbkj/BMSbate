package dal.CoHousingFund;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import Conn.dbconn;
import Model.CoHousingFundChangeModel;
import Model.CoHousingFundInforChangeModel;
import Model.CoHousingFundPwdChangeModel;
import Model.CoHousingFundZbChangeModel;
import Util.UserInfo;

public class CoHousingFund_OperateDal {
	// 修改截单日
	public Integer mod(Integer lastday, Integer id) {
		Integer i = 0;
		if (id != null && id > 0) {

			dbconn db = new dbconn();
			String sql = "update CoHousingFund set cohf_lastday=? where cohf_id=?";
			try {
				i = db.updatePrepareSQL(sql, lastday, id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return i;
	}

	// 更新打单文件
	public Integer uploadfile(Integer id, String filename) {
		dbconn db = new dbconn();
		String sql = "update CoHousingFundChange set chfc_file=?,chfc_state=6"
				+ " where chfc_id=?";
		Integer i=0;
		try {
			i = db.updatePrepareSQL(sql, filename, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	public Integer cohouseadd(CoHousingFundChangeModel m) {
		Integer id = 0;
		dbconn db = new dbconn();
		try {
			id = Integer
					.parseInt(db
							.callWithReturn(
									"{?=call CoHousingFundAdd_P_ply(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, m.getChfc_id(), m.getCid(),
									m.getOwnmonth(), m.getChfc_cohf_id(),
									m.getChfc_houseid(), m.getChfc_addtype(),
									m.getChfc_cpp(), m.getChfc_client(),
									m.getChfc_bankts(), m.getChfc_banktsid(),
									m.getChfc_banktsacc(), m.getChfc_bankjc(),
									m.getChfc_bankjcid(), m.getChfc_bankgj(),
									m.getChfc_banklk(), m.getChfc_tsday(),
									m.getChfc_addtime(), m.getChfc_addname(),
									m.getChfc_laststate(), m.getChfc_state(),
									m.getChfc_tzlstate(), m.getChfc_remark(),
									m.getChfc_sorid(), m.getChfc_stop_type(),
									m.getChfc_stop_reason(),
									m.getChfc_return_reason(),
									m.getChfc_comid(), m.getChfc_zgtype(),
									m.getChfc_address(), m.getChfc_area(),
									m.getChfc_pastal(), m.getChfc_nature(),
									m.getChfc_ecoclass(), m.getChfc_industry(),
									m.getChfc_attached(), m.getChfc_corname(),
									m.getChfc_coridtype(),
									m.getChfc_coridcard(), m.getChfc_cortel(),
									m.getChfc_department(),
									m.getChfc_departmenttel(),
									m.getChfc_createtime(), m.getChfc_regid(),
									m.getChfc_taxpayerid(),
									m.getChfc_jbdepartment(),
									m.getChfc_contactname(),
									m.getChfc_contacttel(),
									m.getChfc_contactmail(),
									m.getChfc_contactmobile(),
									m.getChfc_firmonth(), m.getChfc_ispwd(),
									m.getChfc_tapr_id(), m.getChfc_changestr(),
									m.getChfc_puzu_id(), m.getChfc_company(),
									m.getChfc_lastemcount(),
									m.getChfc_lastsum(),
									m.getChfc_start_month(),
									m.getChfc_end_month(),
									m.getChfc_last_month(),
									m.getChfc_ifstop_low(),
									m.getChfc_ifstop_hj()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	public Integer cohousemod(CoHousingFundChangeModel m) {
		Integer row = 0;

		try {
			row = Integer
					.parseInt(new dbconn()
							.callWithReturn(
									"{?=call CoHousingFundMod_P_ply(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, m.getChfc_id(), m.getCid(),
									m.getOwnmonth(), m.getChfc_cohf_id(),
									m.getChfc_houseid(), m.getChfc_addtype(),
									m.getChfc_cpp(), m.getChfc_client(),
									m.getChfc_bankts(), m.getChfc_banktsid(),
									m.getChfc_banktsacc(), m.getChfc_bankjc(),
									m.getChfc_bankjcid(), m.getChfc_bankgj(),
									m.getChfc_banklk(), m.getChfc_tsday(),
									m.getChfc_addtime(), m.getChfc_addname(),
									m.getChfc_laststate(), m.getChfc_state(),
									m.getChfc_tzlstate(), m.getChfc_remark(),
									m.getChfc_sorid(), m.getChfc_stop_type(),
									m.getChfc_stop_reason(),
									m.getChfc_return_reason(),
									m.getChfc_comid(), m.getChfc_zgtype(),
									m.getChfc_address(), m.getChfc_area(),
									m.getChfc_pastal(), m.getChfc_nature(),
									m.getChfc_ecoclass(), m.getChfc_industry(),
									m.getChfc_attached(), m.getChfc_corname(),
									m.getChfc_coridtype(),
									m.getChfc_coridcard(), m.getChfc_cortel(),
									m.getChfc_department(),
									m.getChfc_departmenttel(),
									m.getChfc_createtime(), m.getChfc_regid(),
									m.getChfc_taxpayerid(),
									m.getChfc_jbdepartment(),
									m.getChfc_contactname(),
									m.getChfc_contacttel(),
									m.getChfc_contactmail(),
									m.getChfc_contactmobile(),
									m.getChfc_firmonth(), m.getChfc_ispwd(),
									m.getChfc_tapr_id(), m.getChfc_changestr(),
									m.getChfc_puzu_id(),
									m.getChfc_completetime(),
									m.getChfc_if_update_compact(),
									m.getChfc_manstate(), m.getChfc_company())
							.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public Integer cohousechange(CoHousingFundChangeModel cm, Integer id) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "update CoHousingFundChange set chfc_modname=?,chfc_modtime=getdate()";
		if (id != null && id > 0) {

			if (cm.getChfc_state() != null && !cm.getChfc_state().equals("")) {
				sql += ",chfc_state=" + cm.getChfc_state();
			}
			if (cm.getChfc_backreason() != null
					&& !cm.getChfc_backreason().equals("")) {
				sql += ",chfc_backreason='" + cm.getChfc_backreason() + "'";
			}

			sql += " where chfc_id=?";

			try {
				i = db.updatePrepareSQL(sql, UserInfo.getUsername(), id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;
	}

	public Integer zbadd(CoHousingFundZbChangeModel m) {
		Integer id = 0;
		dbconn db = new dbconn();
		try {
			id = Integer
					.parseInt(db
							.callWithReturn(
									"{?=call CoHousingFundZbAdd_P_ply(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, m.getCfzc_id(),
									m.getCfzc_chfz_id(), m.getCfzc_cohf_id(),
									m.getCfzc_chfc_id(), m.getOwnmonth(),
									m.getCfzc_addtype(), m.getCfzc_name(),
									m.getCfzc_number(), m.getCfzc_tel(),
									m.getCfzc_mobile(), m.getCfzc_mail(),
									m.getCfzc_addname(), m.getCfzc_addtime(),
									m.getCfzc_state(), m.getCfzc_laststate(),
									m.getCfzc_tzlstate(), m.getCfzc_tapr_id(),
									m.getCfzc_remark()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	public Integer zbmod(CoHousingFundZbChangeModel m) {
		Integer row = 0;

		try {
			row = Integer
					.parseInt(new dbconn()
							.callWithReturn(
									"{?=call CoHousingFundZbMod_P_ply(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, m.getCfzc_id(),
									m.getCfzc_chfz_id(), m.getCfzc_cohf_id(),
									m.getCfzc_chfc_id(), m.getOwnmonth(),
									m.getCfzc_addtype(), m.getCfzc_name(),
									m.getCfzc_number(), m.getCfzc_tel(),
									m.getCfzc_mobile(), m.getCfzc_mail(),
									m.getCfzc_addname(), m.getCfzc_addtime(),
									m.getCfzc_state(), m.getCfzc_laststate(),
									m.getCfzc_tzlstate(), m.getCfzc_tapr_id(),
									m.getCfzc_remark()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public Integer pwdadd(CoHousingFundPwdChangeModel m) {
		Integer id = 0;
		dbconn db = new dbconn();
		try {
			id = Integer
					.parseInt(db
							.callWithReturn(
									"{?=call CoHousingFundPwdAdd_P_ply(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, m.getCfpc_id(),
									m.getCfpc_chfp_id(), m.getCfpc_cohf_id(),
									m.getCfpc_chfc_id(), m.getOwnmonth(),
									m.getCfpc_addtype(), m.getCfpc_zb_name(),
									m.getCfpc_zb_number(),
									m.getCfpc_yearlimit(),
									m.getCfpc_startdate(), m.getCfpc_enddate(),
									m.getCfpc_addname(), m.getCfpc_addtime(),
									m.getCfpc_state(), m.getCfpc_laststate(),
									m.getCfpc_tzlstate(), m.getCfpc_tapr_id(),
									m.getCfpc_remark()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	public Integer pwdmod(CoHousingFundPwdChangeModel m) {
		Integer row = 0;

		try {
			row = Integer
					.parseInt(new dbconn()
							.callWithReturn(
									"{?=call CoHousingFundPwdMod_P_ply(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, m.getCfpc_id(),
									m.getCfpc_chfp_id(), m.getCfpc_cohf_id(),
									m.getCfpc_chfc_id(), m.getOwnmonth(),
									m.getCfpc_addtype(), m.getCfpc_zb_name(),
									m.getCfpc_zb_number(),
									m.getCfpc_yearlimit(),
									m.getCfpc_startdate(), m.getCfpc_enddate(),
									m.getCfpc_addname(), m.getCfpc_addtime(),
									m.getCfpc_state(), m.getCfpc_laststate(),
									m.getCfpc_tzlstate(), m.getCfpc_tapr_id(),
									m.getCfpc_remark()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public Integer inforchangeadd(CoHousingFundInforChangeModel m) {
		Integer id = 0;
		dbconn db = new dbconn();
		try {
			id = Integer
					.parseInt(db
							.callWithReturn(
									"{?=call CoHousingFundInforChangeAdd_P_ply(?,?,?,?,?,?,?,?)}",
									Types.INTEGER, m.getCfic_id(),
									m.getCfic_chfc_id(),
									m.getCfic_changestyle(),
									m.getCfic_changeold(),
									m.getCfic_changenew(), m.getCfic_state(),
									m.getCfic_addtime(), m.getCfic_changetype())
							.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	public boolean UpdateTaprid(Integer daid, Integer tapr_id) {
		dbconn db = new dbconn();
		Integer row = 0;
		String sql = "update CoHousingFundChange set chfc_tapr_id=? where chfc_id=?";

		try {
			PreparedStatement psmt = db.getpre(sql);

			psmt.setInt(1, tapr_id);
			psmt.setInt(2, daid);

			row = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row == 1 ? true : false;
	}

	public Integer UpdateState(CoHousingFundChangeModel m) {
		Integer row = 0;

		try {
			row = Integer.parseInt(new dbconn().callWithReturn(
					"{?=call CoHousingFundUpdateState_P_ply(?,?,?,?)}",
					Types.INTEGER, m.getChfc_id(), m.getChfc_state(),
					m.getChfc_addname(), m.getChfc_remark1()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public Integer ZbUpdateState(CoHousingFundZbChangeModel m) {
		Integer row = 0;

		try {
			row = Integer.parseInt(new dbconn().callWithReturn(
					"{?=call CoHousingFundZbUpdateState_P_ply(?,?,?,?)}",
					Types.INTEGER, m.getCfzc_id(), m.getCfzc_state(),
					m.getCfzc_addname(), m.getCfzc_remark()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public Integer PwdUpdateState(CoHousingFundPwdChangeModel m) {
		Integer row = 0;

		try {
			row = Integer.parseInt(new dbconn().callWithReturn(
					"{?=call CoHousingFundPwdUpdateState_P_ply(?,?,?,?)}",
					Types.INTEGER, m.getCfpc_id(), m.getCfpc_state(),
					m.getCfpc_addname(), m.getCfpc_remark()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public Integer Complete(CoHousingFundChangeModel m) {
		Integer row = 0;

		try {
			row = Integer.parseInt(new dbconn().callWithReturn(
					"{?=call CoHousingFundComplete_P_ply(?)}", Types.INTEGER,
					m.getChfc_id()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public Integer ZbComplete(CoHousingFundZbChangeModel m) {
		Integer row = 0;

		try {
			row = Integer.parseInt(new dbconn().callWithReturn(
					"{?=call CoHousingFundZbComplete_P_ply(?)}", Types.INTEGER,
					m.getCfzc_id()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public Integer PwdComplete(CoHousingFundPwdChangeModel m) {
		Integer row = 0;

		try {
			row = Integer.parseInt(new dbconn().callWithReturn(
					"{?=call CoHousingFundPwdComplete_P_ply(?)}",
					Types.INTEGER, m.getCfpc_id()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public Integer DelZbTem(Integer chfc_id) {
		Integer row = 0;

		try {
			row = Integer.parseInt(new dbconn().callWithReturn(
					"{?=call CoHousingFundZbDelTem_P_ply(?)}", Types.INTEGER,
					chfc_id).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public Integer DelPwdTem(Integer chfc_id) {
		Integer row = 0;

		try {
			row = Integer.parseInt(new dbconn().callWithReturn(
					"{?=call CoHousingFundPwdDelTem_P_ply(?)}", Types.INTEGER,
					chfc_id).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public Integer CoHoDocBack(CoHousingFundChangeModel m) {
		Integer row = 0;

		try {
			row = Integer.parseInt(new dbconn().callWithReturn(
					"{?=call CoHousingFundDocBack_P_ply(?,?,?)}",
					Types.INTEGER, m.getChfc_id(), m.getChfc_addname(),
					m.getChfc_remark()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public Integer InforChangeComplete(CoHousingFundChangeModel m) {
		Integer row = 0;

		try {
			row = Integer.parseInt(new dbconn().callWithReturn(
					"{?=call CoHousingFundInforChangeComplete_P_ply(?)}",
					Types.INTEGER, m.getChfc_id()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}
}
