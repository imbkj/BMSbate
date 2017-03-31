package dal.EmSocialinPaper;

import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.EmSocialinPaperModel;

public class EmSocialinPaperOperateDal {

	public int add(EmSocialinPaperModel m) {
		Integer i = 0;
		dbconn db = new dbconn();
		try {
			i = Integer.valueOf(db.callWithReturn(
					"{?=call EmSocialinPaperAdd_P_ply(?,?,?,?,?,?,?,?,?,?)}",
					Types.INTEGER, m.getCid(), m.getGid(), m.getName(),
					m.getOwnmonth(), m.getEspa_type(), m.getEspa_idcard(),
					m.getEspa_feestate(), m.getEspa_feetype(),
					m.getEspa_addname(), m.getEspa_filetime()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return i;
	}

	public boolean updateTaprid(int daid, int taprid) {
		dbconn db = new dbconn();
		PreparedStatement psmt = null;
		int row = 0;
		String sql = "update EmSocialinPaper set espa_tapr_id=? where espa_id=?";

		try {
			psmt = db.getpre(sql);

			psmt.setInt(1, taprid);
			psmt.setInt(2, daid);
			row = psmt.executeUpdate();

		} catch (Exception e) {
			// TODO: handle exception
		}
		return row > 0 ? true : false;
	}

	public Integer Next(EmSocialinPaperModel m) {
		Integer i = 0;
		dbconn db = new dbconn();
		try {
			i = Integer.parseInt(db.callWithReturn(
					"{?=call EmSocialinPaperNext_P_ply(?,?,?,?,?)}",
					Types.INTEGER, m.getEspa_id(), m.getEspa_state(),
					m.getEspa_addname(), m.getEspa_finaltime(), m.getStr())
					.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return i;
	}

	public Integer Mod(EmSocialinPaperModel m) {
		Integer i = 0;
		dbconn db = new dbconn();
		try {
			i = Integer
					.parseInt(db.callWithReturn(
							"{?=call EmSocialinPaperMOD_P_ply(?,?,?)}",
							Types.INTEGER, m.getEspa_id(), m.getName(),
							m.getEspa_idcard()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return i;
	}

	public String getStatename(Object... objs) {
		List<EmSocialinPaperModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select statename from EmSocialinPaperState where stateid=? and type=1 and state=1";

		try {
			list = db.find(sql, EmSocialinPaperModel.class,
					dbconn.parseSmap(EmSocialinPaperModel.class), objs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list.get(0).getStatename();
	}

	public int Back(EmSocialinPaperModel m) {
		int i = 0;
		dbconn db = new dbconn();
		String sql = "update EmSocialinPaper set espa_state=?,espa_backreason=?,"
				+ "espa_backname=?,espa_isback=1 where espa_id=?";

		try {
			PreparedStatement psmt = db.getpre(sql);

			psmt.setInt(1, m.getEspa_state());
			psmt.setString(2, m.getEspa_backreason());
			psmt.setString(3, m.getEspa_backname());
			psmt.setInt(4, m.getEspa_id());

			i = psmt.executeUpdate();
			db.Close();
		} catch (Exception e) {

		}
		return i;
	}

	public void addLog(EmSocialinPaperModel m) {
		dbconn db = new dbconn();
		String sql = "INSERT INTO EmSocialinPaperStateRel(epsr_espa_id,epsr_stateid,"
				+ " epsr_statetime,epsr_addtime,epsr_addname)"
				+ " VALUES(?,11,GETDATE,GETDATE(),?)";
		try {
			PreparedStatement psmt = db.getpre(sql);

			psmt.setInt(1, m.getEspa_id());
			psmt.setString(2, m.getEspa_addname());

			psmt.executeUpdate();
			db.Close();
		} catch (Exception e) {

		}
	}
}
