package dal.EmCensus.EmDh;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import Conn.dbconn;
import Model.EmActyGiftBackInfoModel;
import Model.EmDhModel;
import Model.EmSheBaoChangeHjModel;
import Util.UserInfo;

public class EmDh_OperateDal {
	// 调户申请新增
	public Integer EmDhAdd(EmDhModel model) {
		Integer i = 0;
		dbconn db = new dbconn();
		try {
			i = Integer
					.parseInt(db
							.callWithReturn(
									"{?=call EmDH_Add_cyj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
									"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, model.getCid(),
									model.getGid(), model.getEmdh_zhtype(),
									model.getEmdh_name(),
									model.getEmdh_company(),
									model.getEmdh_tel(), model.getEmdh_mail(),
									model.getEmdh_dhtype(),
									model.getEmdh_client(),
									model.getEmdh_govefee(),
									model.getEmdh_govetar(),
									model.getEmdh_servefee(),
									model.getEmdh_servetar(),
									model.getEmdh_servetype(),
									model.getEmdh_isbackfee(),
									model.getEmdh_backreason(),
									model.getEmdh_remark(),
									model.getEmdh_state(),
									timechange(model.getEmdh_addtime()),
									model.getEmdh_addname(),
									timechange(model.getEmdh_time1()),
									model.getEmdh_isnote(),
									model.getEmdh_state1(),
									model.getEmdh_idcard(),
									model.getEmdh_marital(),
									model.getEmdh_education(),
									model.getEmdh_school(),
									model.getEmdh_ifdn(), model.getEmdh_ifhk(),
									model.getEmdh_goveservetype(),model.getEmdh_shebaotype(),
									model.getEmdh_feeer(),model.getEmdh_feetime(),model.getEmdh_feegeter(),
									model.getEmdh_ifcompact(),model.getEmdh_feestep()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	// 调户申请新增(未入职员工)
	public Integer EmDhInfoAdd(EmDhModel model) {
		Integer i = 0;
		dbconn db = new dbconn();
		try {
			i = Integer
					.parseInt(db
							.callWithReturn(
									"{?=call EmDHInfo_Add_cyj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, model.getEmdh_zhtype(),
									model.getEmdh_name(),
									model.getEmdh_company(),
									model.getEmdh_tel(), model.getEmdh_mail(),
									model.getEmdh_dhtype(),
									model.getEmdh_client(),
									model.getEmdh_govefee(),
									model.getEmdh_govetar(),
									model.getEmdh_servefee(),
									model.getEmdh_servetar(),
									model.getEmdh_servetype(),
									model.getEmdh_isbackfee(),
									model.getEmdh_backreason(),
									model.getEmdh_remark(),
									model.getEmdh_state(),
									timechange(model.getEmdh_addtime()),
									model.getEmdh_addname(),
									timechange(model.getEmdh_time1()),
									model.getEmdh_isnote(),
									model.getEmdh_state1(),
									model.getEmdh_idcard(),
									model.getEmdh_marital(),
									model.getEmdh_education(),
									model.getEmdh_school(),
									model.getEmdh_ifdn(), model.getEmdh_ifhk(),
									model.getEmdh_goveservetype()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	// 更新调户申请信息
	public int UpdateEmdhInfo(EmDhModel model, String str) {
		int k = 0;
		try {
			dbconn db = new dbconn();
			java.util.Date now = new java.util.Date();
			String sql = "update emdh set emdh_lasttime='" + timechange(now)
					+ "'";
			sql = sql + str;
			sql = sql + " where id=" + model.getId();
			k = db.execQuery(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k;
	}

	// /更新档案业务表的任务单id
	public boolean updateTaprid(int id, int taprid) {
		dbconn db = new dbconn();
		PreparedStatement psmt = null;
		int row = 0;
		String sql = "update emdh set emdh_taprid=? where id=?";
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

	// /更新档案业务表的户籍变更任务单id
	public boolean updateemdh_hjtaprid(int id, int taprid) {
		dbconn db = new dbconn();
		PreparedStatement psmt = null;
		int row = 0;
		String sql = "update emdh set emdh_hjtaprid=? where id=?";
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

	// 添加备注
	public int addramark(String id, String content) {
		int k = 0;
		try {
			dbconn db = new dbconn();
			java.util.Date now = new java.util.Date();
			String sql = "insert into EmDHRemark(emdh_id,[content],addname) "
					+ "values(" + id + ",'" + content + "','"
					+ UserInfo.getUsername() + "')";
			k = db.execQuery(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k;
	}

	// 插入材料
	public void DocInfoAdd_(Integer id, Integer docid, String addname) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db.getcall("DocInfoAdd_p_cyj(?,?,?)");
			c.setInt(1, id);
			c.setInt(2, docid);
			c.setString(3, addname);
			c.execute();
		} catch (SQLException e) {

		}
	}

	// 新增社保户籍变更信息
	public Integer EmsheBaoChange_Add(EmSheBaoChangeHjModel model) {
		Integer id = 0;
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
					.getcall("EmShebaoChangeHj_Add_p_CYJ(?,?,?,?,?,?,?,?,?)");
			c.setInt(1, model.getGid());
			c.setInt(2, model.getCid());
			c.setInt(3, model.getOwnmonth());
			c.setString(4, model.getSbci_name());
			c.setString(5, model.getSbci_change());
			c.setString(6, model.getSbci_content());
			c.setString(7, model.getSbci_remark());
			c.setString(8, model.getSbci_addname());
			c.registerOutParameter(9, java.sql.Types.INTEGER);
			c.execute();
			id = c.getInt(9);
		} catch (SQLException e) {

		}
		return id;
	}

	// /更新档案业务表的任务单id
	public boolean updateHjTaprid(int id, int taprid) {
		dbconn db = new dbconn();
		PreparedStatement psmt = null;
		int row = 0;
		String sql = "update EmSheBaoChangeHj set sbci_tarpid=? where sbci_id=?";
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

	// 时间格式转换
	private Date timechange(java.util.Date d) {
		Date da = null;
		if (d != null && !d.equals("")) {
			java.sql.Date date = new java.sql.Date(d.getTime());
			da = date;
		}
		return da;
	}
}
