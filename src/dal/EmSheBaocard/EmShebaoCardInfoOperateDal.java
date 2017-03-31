package dal.EmSheBaocard;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sun.org.apache.bcel.internal.generic.GETSTATIC;

import Conn.dbconn;
import Model.EmShebaoCardInfoModel;
import Util.UserInfo;

public class EmShebaoCardInfoOperateDal {
	// 社保卡新增
	public Integer EmShebaoCardInfoAdd(EmShebaoCardInfoModel m) {
		dbconn db = new dbconn();
		Integer i = 0;
		try {
			i = Integer
					.parseInt(db
							.callWithReturn(
									"{?=call EmShebaoCardInfo_Add_cyj(?,?,?,?,?,?,?,?,"
											+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, m.getGid(), m.getCid(),
									m.getSbcd_company(), m.getSbcd_name(),
									m.getSbcd_sex(), m.getSbcd_birthday(),
									m.getSbcd_folk(), m.getSbcd_mobile(),
									m.getSbcd_computerid(),
									m.getSbcd_idcardclass(),
									m.getSbcd_idcardstartdate(),
									m.getSbcd_idcardenddate(),
									m.getSbcd_province(), m.getSbcd_city(),
									m.getSbcd_address(), m.getSbcd_photonum(),
									m.getSbcd_hjprovince(), m.getSbcd_hjcity(),
									m.getSbcd_agencies(), m.getSbcd_job(),
									m.getSbcd_education(), m.getSbcd_marry(),
									m.getSbcd_hj(), m.getSbcd_remark(),
									m.getSbcd_stateid(), m.getSbcd_shortname(),
									m.getSbcd_addname(), m.getSbcd_content(),
									m.getSbcd_single(), m.getSbcd_idcard(),
									m.getOwnmonth(), m.getSbcd_upbankname(),
									m.getSbcd_bankdocid()).toString());
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return i;
	}

	// 社保卡重新提交
	public Integer EmShebaoCardInfoUpdate(EmShebaoCardInfoModel m) {
		dbconn db = new dbconn();
		Integer i = 0;
		try {
			i = Integer.parseInt(db.callWithReturn(
					"{?=call EmShebaoCardInfo_update_p_cyj(?,?,?,?,?,?,?,"
							+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
					Types.INTEGER, m.getSbcd_birthday(), m.getSbcd_folk(),
					m.getSbcd_mobile(), m.getSbcd_computerid(),
					m.getSbcd_idcardclass(), m.getSbcd_idcardstartdate(),
					m.getSbcd_idcardenddate(), m.getSbcd_province(),
					m.getSbcd_city(), m.getSbcd_address(),
					m.getSbcd_photonum(), m.getSbcd_hjprovince(),
					m.getSbcd_hjcity(), m.getSbcd_agencies(), m.getSbcd_job(),
					m.getSbcd_education(), m.getSbcd_marry(), m.getSbcd_hj(),
					m.getSbcd_remark(), m.getSbcd_stateid(),
					m.getSbcd_content(), m.getSbcd_single(),
					m.getSbcd_addname(), m.getOwnmonth(), m.getSbcd_id(),
					m.getSbcd_upbankname()).toString());
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return i;
	}

	// 福利核收信息更新
	public Integer updateCardInfo(String sqlstr, Integer id) {
		dbconn db = new dbconn();
		Integer k = 0;
		String sql = "update EmShebaoCardInfo set sbcd_modname='"
				+ UserInfo.getUsername() + "' " + ",sbcd_modtime='"
				+ getnowdate() + "'" + sqlstr + "where sbcd_id=" + id;
		try {
			k = db.execQuery(sql);
		} catch (Exception e) {
		}
		return k;
	}

	// 更新社保卡信息表任务单id
	public boolean updateShebaoCardTaprid(int taprid, int id) {
		dbconn db = new dbconn();
		PreparedStatement psmt = null;
		int row = 0;
		String sql = "update EmShebaoCardInfo set sbcd_tarpid=? where sbcd_id=?";
		try {
			psmt = db.getpre(sql);
			psmt.setInt(1, taprid);
			psmt.setInt(2, id);
			row = psmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return row > 0 ? true : false;
	}

	// 更新社保卡信息表取消原因
	public boolean CancelShebaoCardInfo(String cancelcase, int id) {
		dbconn db = new dbconn();
		PreparedStatement psmt = null;
		int row = 0;
		String sql = "update EmShebaoCardInfo set sbcd_stateid=19, sbcd_cancelcase=? where sbcd_id=?";
		try {
			psmt = db.getpre(sql);
			psmt.setString(1, cancelcase);
			psmt.setInt(2, id);
			row = psmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return row > 0 ? true : false;
	}

	// 更新社保卡信息表取消原因
	public boolean CancelShebaoCardInfo(String cancelcase, int id, int stateid) {
		dbconn db = new dbconn();
		PreparedStatement psmt = null;
		int row = 0;
		String sql = "update EmShebaoCardInfo set sbcd_stateid=?, " +
				"sbcd_cancelcase=? where sbcd_id=?";
		try {
			psmt = db.getpre(sql);
			psmt.setInt(1, stateid);
			psmt.setString(2, cancelcase);
			psmt.setInt(3, id);
			row = psmt.executeUpdate();
		} catch (Exception e) {
			
		}
		return row > 0 ? true : false;
	}

	/**
	 * 获取当前时间并转换成字符串
	 * 
	 * @param date
	 * @return str
	 */
	public static String getnowdate() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = "";
		if (date != null) {
			str = format.format(date);
		}
		return str;
	}
}
