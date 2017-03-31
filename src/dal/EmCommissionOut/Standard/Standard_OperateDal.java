package dal.EmCommissionOut.Standard;

import java.sql.PreparedStatement;
import java.sql.Types;
import java.text.SimpleDateFormat;

import Conn.dbconn;
import Model.EmCommissionOutStandardModel;

public class Standard_OperateDal {

	public Integer add(EmCommissionOutStandardModel m) {
		dbconn db = new dbconn();
		Integer id = 0;

		try {
			id = Integer
					.parseInt(db
							.callWithReturn(
									"{?=call EmCommissionOutStandardAdd_P_ply(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, m.getEcos_id(), m.getCid(),
									m.getOwnmonth(), m.getEcos_name(),
									m.getEcos_cabc_id(),
									m.getEcos_service_fee(),
									m.getEcos_archvie_fee(),
									m.getEcos_shebao_zhtype(),
									m.getEcos_shebao_feetype(),
									m.getEcos_gjj_zhtype(),
									m.getEcos_gjj_feetype(),
									m.getEcos_remark(), m.getEcos_laststate(),
									m.getEcos_state(), m.getEcos_usestate(),
									m.getEcos_history_id(),
									m.getEcos_addname(), m.getEcos_addtime(),
									m.getEcos_tapr_id(),
									m.getEcos_archvie_feetype()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	public Integer mod(EmCommissionOutStandardModel m) {
		dbconn db = new dbconn();
		Integer row = 0;

		try {
			row = Integer
					.parseInt(db
							.callWithReturn(
									"{?=call EmCommissionOutStandardMod_P_ply(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, m.getEcos_id(), m.getCid(),
									m.getOwnmonth(), m.getEcos_name(),
									m.getEcos_cabc_id(),
									m.getEcos_service_fee(),
									m.getEcos_archvie_fee(),
									m.getEcos_shebao_zhtype(),
									m.getEcos_shebao_feetype(),
									m.getEcos_gjj_zhtype(),
									m.getEcos_gjj_feetype(),
									m.getEcos_remark(), m.getEcos_laststate(),
									m.getEcos_state(), m.getEcos_usestate(),
									m.getEcos_history_id(),
									m.getEcos_addname(), m.getEcos_addtime(),
									m.getEcos_tapr_id(),
									m.getEcos_archvie_feetype()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public Integer UpdateState(EmCommissionOutStandardModel m) {
		dbconn db = new dbconn();
		Integer row = 0;

		try {
			row = Integer
					.parseInt(db
							.callWithReturn(
									"{?=call EmCommissionOutStandardUpdateState_P_ply(?,?,?,?)}",
									Types.INTEGER,
									m.getEcos_id(),
									m.getEcos_state(),
									m.getEosr_addname(),
									new SimpleDateFormat("yyyy-MM-dd").format(m
											.getEosr_statetime())).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public Integer CoOfferListReladd(EmCommissionOutStandardModel m) {
		dbconn db = new dbconn();
		Integer row = 0;

		try {
			row = Integer.parseInt(db.callWithReturn(
					"{?=call EmCommissionOutCoOfferListRelAdd_P_ply(?,?,?,?)}",
					Types.INTEGER, m.getEcos_id(), m.getEcop_ecos_id(),
					m.getEcop_coli_id(), m.getEcop_state()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public Integer DefaultCoAgency(EmCommissionOutStandardModel m) {
		dbconn db = new dbconn();
		Integer row = 0;

		try {
			row = Integer
					.parseInt(db
							.callWithReturn(
									"{?=call EmCommissionOutStandardDefaultCoAgency_P_ply(?,?)}",
									Types.INTEGER, m.getEcos_id(),
									m.getCabc_id()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public boolean UpdateTaprid(int daid, int taprid) {
		dbconn db = new dbconn();
		PreparedStatement psmt = null;
		int row = 0;
		String sql = "update EmCommissionOutStandard set ecos_tapr_id=? where ecos_id=?";

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

	public Integer DeleteCoOfferList(int daid) {
		dbconn db = new dbconn();
		PreparedStatement psmt = null;
		int row = 0;
		String sql = "delete from EmCommissionOutCoOfferListRel where ecop_ecos_id=?";

		try {
			psmt = db.getpre(sql);

			psmt.setInt(1, daid);
			row = psmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

}
