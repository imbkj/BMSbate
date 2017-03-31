package dal.CoSocialInsurance;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import Conn.dbconn;
import Model.CoShebaoChangeModel;
import Model.CoShebaoModel;

public class CoSocialInsurance_OperateDal {

	public Integer add(CoShebaoChangeModel m) {
		dbconn db = new dbconn();
		Integer id = 0;

		try {
			id = Integer
					.parseInt(db
							.callWithReturn(
									"{?=call CoShebaoAdd_P_ply(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, m.getCsbc_id(), m.getCid(),
									m.getOwnmonth(), m.getCsbc_addtype(),
									m.getCsbc_cotel(), m.getCsbc_town(),
									m.getCsbc_street(), m.getCsbc_regadd(),
									m.getCsbc_regid(), m.getCsbc_sbadd(),
									m.getCsbc_licdate(), m.getCsbc_comid(),
									m.getCsbc_forms(), m.getCsbc_dep(),
									m.getCsbc_ecoclass(),
									m.getCsbc_industryclass(),
									m.getCsbc_corname(), m.getCsbc_coridcard(),
									m.getCsbc_pastcode(), m.getCsbc_heaname(),
									m.getCsbc_bankname(), m.getCsbc_bankcode(),
									m.getCsbc_bankacctid(),
									m.getCsbc_sorarea(), m.getCsbc_sorid(),
									m.getCsbc_pwd(), m.getCsbc_submission(),
									m.getCsbc_paytype(), m.getCsbc_payapply(),
									m.getCsbc_attnname(),
									m.getCsbc_attnmobile(),
									m.getCsbc_laststate(), m.getCsbc_state(),
									m.getCsbc_remark(), m.getCsbc_addtime(),
									m.getCsbc_addname(), m.getCsbc_tzlstate(),
									m.getCosb_cardbank(),
									m.getCosb_branchbank(), m.getCosb_ukey(),
									m.getCosb_ukeytruetime(),
									m.getCosb_ukeyfailtime(),
									m.getCsbc_iclassfirst()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	public Integer mod(CoShebaoChangeModel m) {
		dbconn db = new dbconn();
		Integer row = 0;

		/*
		 * System.out.println(m.getCsbc_id()); System.out.println(m.getCid());
		 * System.out.println(m.getOwnmonth());
		 * System.out.println(m.getCsbc_addtype());
		 * System.out.println(m.getCsbc_cotel());
		 * System.out.println(m.getCsbc_town());
		 * System.out.println(m.getCsbc_street());
		 * System.out.println(m.getCsbc_regadd());
		 * System.out.println(m.getCsbc_regid());
		 * System.out.println(m.getCsbc_sbadd());
		 * System.out.println(m.getCsbc_licdate());
		 * System.out.println(m.getCsbc_comid());
		 * System.out.println(m.getCsbc_forms());
		 * System.out.println(m.getCsbc_dep());
		 * System.out.println(m.getCsbc_ecoclass());
		 * System.out.println(m.getCsbc_industryclass());
		 * System.out.println(m.getCsbc_corname());
		 * System.out.println(m.getCsbc_coridcard());
		 * System.out.println(m.getCsbc_pastcode());
		 * System.out.println(m.getCsbc_heaname());
		 * System.out.println(m.getCsbc_bankname());
		 * System.out.println(m.getCsbc_bankcode());
		 * System.out.println(m.getCsbc_bankacctid());
		 * System.out.println(m.getCsbc_sorarea());
		 * System.out.println(m.getCsbc_sorid());
		 * System.out.println(m.getCsbc_pwd());
		 * System.out.println(m.getCsbc_submission());
		 * System.out.println(m.getCsbc_paytype());
		 * System.out.println(m.getCsbc_payapply());
		 * System.out.println(m.getCsbc_attnname());
		 * System.out.println(m.getCsbc_attnmobile());
		 * System.out.println(m.getCsbc_laststate());
		 * System.out.println(m.getCsbc_state());
		 * System.out.println(m.getCsbc_tzlstate());
		 * System.out.println(m.getCsbc_remark());
		 * System.out.println(m.getCsbc_addtime());
		 * System.out.println(m.getCsbc_addname());
		 * System.out.println(m.getCsbc_tapr_id());
		 * System.out.println(m.getCsbc_pdf());
		 * System.out.println(m.getCsbc_image());
		 * System.out.println(m.getCsbc_xls());
		 * System.out.println(m.getCsbc_unemployment_per());
		 * System.out.println(m.getCsbc_business_per());
		 * System.out.println(m.getCsbc_stop_reason());
		 * System.out.println(m.getCsbc_iclassfirst());
		 */

		try {
			row = Integer
					.parseInt(db
							.callWithReturn(
									"{?=call CoShebaoMod_P_ply(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, m.getCsbc_id(), m.getCid(),
									m.getOwnmonth(), m.getCsbc_addtype(),
									m.getCsbc_cotel(), m.getCsbc_town(),
									m.getCsbc_street(), m.getCsbc_regadd(),
									m.getCsbc_regid(), m.getCsbc_sbadd(),
									m.getCsbc_licdate(), m.getCsbc_comid(),
									m.getCsbc_forms(), m.getCsbc_dep(),
									m.getCsbc_ecoclass(),
									m.getCsbc_industryclass(),
									m.getCsbc_corname(), m.getCsbc_coridcard(),
									m.getCsbc_pastcode(), m.getCsbc_heaname(),
									m.getCsbc_bankname(), m.getCsbc_bankcode(),
									m.getCsbc_bankacctid(),
									m.getCsbc_sorarea(), m.getCsbc_sorid(),
									m.getCsbc_pwd(), m.getCsbc_submission(),
									m.getCsbc_paytype(), m.getCsbc_payapply(),
									m.getCsbc_attnname(),
									m.getCsbc_attnmobile(),
									m.getCsbc_laststate(), m.getCsbc_state(),
									m.getCsbc_tzlstate(), m.getCsbc_remark(),
									m.getCsbc_addtime(), m.getCsbc_addname(),
									m.getCsbc_tapr_id(), m.getCsbc_pdf(),
									m.getCsbc_image(), m.getCsbc_xls(),
									m.getCsbc_unemployment_per(),
									m.getCsbc_business_per(),
									m.getCsbc_stop_reason(),
									m.getCsbc_iclassfirst()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public Integer changeadd(CoShebaoChangeModel m) {
		dbconn db = new dbconn();
		Integer id = 0;

		try {
			id = Integer
					.parseInt(db
							.callWithReturn(
									"{?=call CoShebaoInforChangeAdd_P_ply(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, m.getCsbc_id(), m.getCid(),
									m.getCsbc_cosb_id(), m.getOwnmonth(),
									m.getCsbc_addtype(), m.getCsbc_cotel(),
									m.getCsbc_town(), m.getCsbc_street(),
									m.getCsbc_regadd(), m.getCsbc_regid(),
									m.getCsbc_sbadd(), m.getCsbc_licdate(),
									m.getCsbc_comid(), m.getCsbc_forms(),
									m.getCsbc_dep(), m.getCsbc_ecoclass(),
									m.getCsbc_industryclass(),
									m.getCsbc_corname(), m.getCsbc_coridcard(),
									m.getCsbc_pastcode(), m.getCsbc_heaname(),
									m.getCsbc_bankname(), m.getCsbc_bankcode(),
									m.getCsbc_bankacctid(),
									m.getCsbc_sorarea(), m.getCsbc_sorid(),
									m.getCsbc_pwd(), m.getCsbc_submission(),
									m.getCsbc_paytype(), m.getCsbc_payapply(),
									m.getCsbc_attnname(),
									m.getCsbc_attnmobile(),
									m.getCsbc_laststate(), m.getCsbc_state(),
									m.getCsbc_tzlstate(), m.getCsbc_remark(),
									m.getCsbc_addtime(), m.getCsbc_addname(),
									m.getCsbc_tapr_id(), m.getCsbc_pdf(),
									m.getCsbc_image(), m.getCsbc_xls(),
									m.getCsbc_changestr(),
									m.getCsbc_iclassfirst(),
									m.getCoba_company()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	public Integer changecomplete(CoShebaoChangeModel m) {
		dbconn db = new dbconn();
		Integer row = 0;

		try {
			row = Integer
					.parseInt(db
							.callWithReturn(
									"{?=call CoShebaoInforChangeComplete_P_ply(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, m.getCsbc_id(), m.getCid(),
									m.getCsbc_cosb_id(), m.getOwnmonth(),
									m.getCsbc_addtype(), m.getCsbc_cotel(),
									m.getCsbc_town(), m.getCsbc_street(),
									m.getCsbc_regadd(), m.getCsbc_regid(),
									m.getCsbc_sbadd(), m.getCsbc_licdate(),
									m.getCsbc_comid(), m.getCsbc_forms(),
									m.getCsbc_dep(), m.getCsbc_ecoclass(),
									m.getCsbc_industryclass(),
									m.getCsbc_corname(), m.getCsbc_coridcard(),
									m.getCsbc_pastcode(), m.getCsbc_heaname(),
									m.getCsbc_bankname(), m.getCsbc_bankcode(),
									m.getCsbc_bankacctid(),
									m.getCsbc_sorarea(), m.getCsbc_sorid(),
									m.getCsbc_pwd(), m.getCsbc_submission(),
									m.getCsbc_paytype(), m.getCsbc_payapply(),
									m.getCsbc_attnname(),
									m.getCsbc_attnmobile(),
									m.getCsbc_laststate(), m.getCsbc_state(),
									m.getCsbc_tzlstate(), m.getCsbc_remark(),
									m.getCsbc_addtime(), m.getCsbc_addname(),
									m.getCsbc_tapr_id(), m.getCsbc_pdf(),
									m.getCsbc_image(), m.getCsbc_xls(),
									m.getCsbc_changestr()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public Integer changereadd(CoShebaoChangeModel m) {
		dbconn db = new dbconn();
		Integer id = 0;

		try {
			id = Integer
					.parseInt(db
							.callWithReturn(
									"{?=call CoShebaoReInforChange_P_ply(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, m.getCsbc_id(), m.getCid(),
									m.getCsbc_cosb_id(), m.getOwnmonth(),
									m.getCsbc_addtype(), m.getCsbc_cotel(),
									m.getCsbc_town(), m.getCsbc_street(),
									m.getCsbc_regadd(), m.getCsbc_regid(),
									m.getCsbc_sbadd(), m.getCsbc_licdate(),
									m.getCsbc_comid(), m.getCsbc_forms(),
									m.getCsbc_dep(), m.getCsbc_ecoclass(),
									m.getCsbc_industryclass(),
									m.getCsbc_corname(), m.getCsbc_coridcard(),
									m.getCsbc_pastcode(), m.getCsbc_heaname(),
									m.getCsbc_bankname(), m.getCsbc_bankcode(),
									m.getCsbc_bankacctid(),
									m.getCsbc_sorarea(), m.getCsbc_sorid(),
									m.getCsbc_pwd(), m.getCsbc_submission(),
									m.getCsbc_paytype(), m.getCsbc_payapply(),
									m.getCsbc_attnname(),
									m.getCsbc_attnmobile(),
									m.getCsbc_laststate(), m.getCsbc_state(),
									m.getCsbc_tzlstate(), m.getCsbc_remark(),
									m.getCsbc_addtime(), m.getCsbc_addname(),
									m.getCsbc_tapr_id(), m.getCsbc_pdf(),
									m.getCsbc_image(), m.getCsbc_xls(),
									m.getCsbc_changestr(),
									m.getCsbc_iclassfirst()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	public Integer cancellationadd(CoShebaoChangeModel m) {
		dbconn db = new dbconn();
		Integer id = 0;

		try {
			id = Integer
					.parseInt(db
							.callWithReturn(
									"{?=call CoShebaoCancellation_P_ply(?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, m.getCid(), m.getOwnmonth(),
									m.getCsbc_addtype(), m.getCsbc_state(),
									m.getCsbc_laststate(),
									m.getCsbc_tzlstate(),
									m.getCsbc_stop_reason(),
									m.getCsbc_cosb_id(), m.getCsbc_remark(),
									m.getCsbc_addname(), m.getType())
							.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	public Integer cancellationcomplete(CoShebaoChangeModel m) {
		dbconn db = new dbconn();
		Integer row = 0;

		try {
			row = Integer.parseInt(db.callWithReturn(
					"{?=call update_coshebao_p_cyj(?,?,?,?)}", Types.INTEGER,
					m.getCsbc_cosb_id(), m.getCsbc_sorid(), m.getCsbc_pwd(),
					m.getCosb_ukey()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public Integer UpdateInfo(CoShebaoChangeModel m) {
		Integer k = 0;
		try {
			dbconn conn = new dbconn();
			CallableStatement c = conn
					.getcall("Coshebao_update_p_cyj(?,?,?,?,?,?)");
			c.setInt(1, m.getCsbc_cosb_id());
			c.setString(2, m.getCsbc_attnname());
			c.setString(3, m.getCsbc_attnmobile());
			c.setString(4, m.getCosb_ukeytruetime());
			c.setString(5, m.getCosb_ukeyfailtime());
			c.registerOutParameter(6, java.sql.Types.INTEGER);
			c.execute();

			return c.getInt(6);

		} catch (SQLException e) {
			return 1;
		}
	}

	public Integer addcomplete(CoShebaoChangeModel m) {
		dbconn db = new dbconn();
		Integer row = 0;

		try {
			row = Integer.parseInt(db.callWithReturn(
					"{?=call CoShebaoAddComplete_P_ply(?,?,?,?,?,?,?)}",
					Types.INTEGER, m.getCid(), m.getCsbc_id(),
					m.getCsbc_sbadd(), m.getCsbc_sorid(), m.getCsbc_pwd(),
					m.getCsbc_unemployment_per(), m.getCsbc_business_per())
					.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public Integer updatestate(CoShebaoChangeModel m) {
		dbconn db = new dbconn();
		Integer row = 0;
		try {
/*
			System.out.println(m.getCsbc_id());
			System.out.println(m.getCsbc_state());
			System.out.println(m.getCsbc_addname());
			System.out.println(m.getType());
			System.out.println(m.getCsbc_remark());
			System.out.println(m.getCsbc_pdf());
			System.out.println(m.getCsbc_image());
			System.out.println(m.getCsbc_xls());
			System.out.println(m.getCid());
			System.out.println(m.getCsbc_unemployment_per());
			System.out.println(m.getCsbc_business_per());
			System.out.println(m.getCsbc_bankname());
			System.out.println(m.getCsbc_bankacctid());
			System.out.println(m.getCsbc_sorid());
*/
			row = Integer
					.parseInt(db
							.callWithReturn(
									"{?=call CoShebaoUpdateState_P_ply(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, m.getCsbc_id(),
									m.getCsbc_state(), m.getCsbc_addname(),
									m.getType(), m.getCsbc_remark(),
									m.getCsbc_pdf(), m.getCsbc_image(),
									m.getCsbc_xls(), m.getCid(),
									m.getCsbc_unemployment_per(),
									m.getCsbc_business_per(),
									m.getCsbc_bankname(),
									m.getCsbc_bankacctid(), m.getCsbc_sorid())
							.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public Integer back(CoShebaoChangeModel m) {
		dbconn db = new dbconn();
		Integer row = 0;

		try {
			row = Integer.parseInt(db.callWithReturn(
					"{?=call CoShebaoBack_P_ply(?,?,?,?)}", Types.INTEGER,
					m.getCsbc_id(), m.getPbsr_remark(), m.getPbsr_addname(),
					m.getType()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public boolean UpdateTaprid(Integer daid, Integer tapr_id) {
		dbconn db = new dbconn();
		Integer row = 0;
		String sql = "update coshebaochange set csbc_tapr_id=? where csbc_id=?";

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

	public boolean DocBack(Integer daid) {
		dbconn db = new dbconn();
		Integer row = 0;
		String sql = "update coshebaochange set csbc_tzlstate=1 where csbc_id=?";

		try {
			PreparedStatement psmt = db.getpre(sql);

			psmt.setInt(1, daid);

			row = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row == 1 ? true : false;
	}

	public Integer InsertLog(CoShebaoChangeModel m) {
		dbconn db = new dbconn();
		Integer row = 0;
		String sql = "INSERT INTO PubStateRel(pbsr_daid,pbsr_statename,pbsr_content,pbsr_type,"
				+ " pbsr_addtime,pbsr_addname,pbsr_statetime,pbsr_remark)"
				+ " VALUES(?,?,?,?,getdate(),?,getdate(),?)";

		try {
			PreparedStatement psmt = db.getpre(sql);

			psmt.setInt(1, m.getCsbc_id());
			psmt.setString(2, m.getPbsr_statename());
			psmt.setString(3, m.getPbsr_content());
			psmt.setString(4, m.getPbsr_type());
			psmt.setString(5, m.getPbsr_addname());
			psmt.setString(6, m.getPbsr_remark());

			row = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public Integer TerminationPwd(CoShebaoChangeModel m) {
		dbconn db = new dbconn();
		Integer row = 0;
		String sql = "UPDATE coshebaochange set csbc_pwd=? where csbc_id=?";

		try {
			PreparedStatement psmt = db.getpre(sql);

			psmt.setString(1, m.getCsbc_pwd());
			psmt.setInt(2, m.getCsbc_id());

			row = psmt.executeUpdate();

			// 更新在册表密码
			CoShebaoModel m1 = new CoShebaoModel();
			m1.setCosb_pwd(m.getCsbc_pwd());
			m1.setCosb_id(m.getCsbc_cosb_id());
			row += UpdatePwd(m1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public Integer UpdatePwd(CoShebaoModel m) {
		dbconn db = new dbconn();
		Integer row = 0;
		String sql = "UPDATE coshebao set cosb_pwd=? where cosb_id=?";

		try {
			PreparedStatement psmt = db.getpre(sql);

			psmt.setString(1, m.getCosb_pwd());
			psmt.setInt(2, m.getCosb_id());

			row = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	// 修改合同表
	public Integer UpdatePer(CoShebaoModel m) {
		dbconn db = new dbconn();
		Integer row = 0;

		try {
			row = Integer.parseInt(db.callWithReturn(
					"{?=call CoShebaoUpdateCompact_p_zmj(?,?,?,?,?,?,?)}",
					Types.INTEGER, m.getCid(), m.getCosb_unemployment_per(),
					m.getCosb_business_per(), m.getCosb_id(),m.getCosb_ukey(),
					m.getCosb_ukeytruetime(),
					m.getCosb_ukeyfailtime()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public Integer UpdateCompact(CoShebaoChangeModel m) {
		dbconn db = new dbconn();
		Integer row = 0;

		try {
			row = Integer.parseInt(db.callWithReturn(
					"{?=call CoShebaoUpdateCompact_p_ply(?,?)}", Types.INTEGER,
					m.getCoco_id(), m.getCsbc_id()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}
}
