package dal.EmBodyCheck;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import Conn.dbconn;
import Model.EmBodyCheckCommitModel;
import Model.EmBodyCheckListModel;
import Model.EmBodyCheckModel;
import Model.EmBodycheckCancelModel;
import Model.embodycheckoperlogModel;
import Util.UserInfo;

public class EmBcInfo_OperatetDal {
	// 体检信息新增
	public Integer EmBodyCheckAdd(EmBodyCheckModel m) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
					.getcall("EmBodyCheck_Add_cyj(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getCid());
			c.setInt(2, m.getGid());
			c.setString(3, m.getEmbc_company());
			c.setString(4, m.getEmbc_shortname());
			c.setString(5, m.getEmbc_name());
			c.setString(6, m.getEmbc_idcard());
			c.setString(7, m.getEmbc_sex());
			c.setString(8, m.getEmbc_mobile());
			c.setString(9, m.getEmbc_client());
			if (m.getEmbc_age() == null) {
				m.setEmbc_age(0);
			}
			c.setInt(10, m.getEmbc_age());
			c.setString(11, m.getEmbc_addname());
			c.setString(12, m.getEmbc_remark());
			c.registerOutParameter(13, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(13);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 体检信息新增
	public Integer EmBodyCheckListAdd(EmBodyCheckListModel m) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
					.getcall("EmBodyCheckList_Add_cyj(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getEbcl_embc_id());
			c.setInt(2, m.getEbcl_hospital());
			if (m.getEbcl_area() == null) {
				m.setEbcl_area(0);
			}
			c.setInt(3, m.getEbcl_area());
			c.setString(4, m.getEbcl_items());
			c.setString(5, m.getEbcl_itemnums());
			c.setString(6, m.getEbcl_itemgroup());
			c.setDate(7, changedate(m.getEbcl_bookdate()));
			c.setInt(8, m.getEbcl_type());
			c.setInt(9, m.getEbcl_bookmode());
			c.setString(10, m.getEbcl_addname());
			c.setBigDecimal(11, m.getEbcl_charge());
			c.setBigDecimal(12, m.getEbcl_discount());
			c.registerOutParameter(13, java.sql.Types.INTEGER);
			c.execute();
			int k = c.getInt(13);
			System.out.println("k=" + k);
			return k;
		} catch (SQLException e) {
			System.out.print("错误：" + e.getMessage());
			return 0;
		}
	}

	// 年度体检新增体检信息
	public Integer embodycheckAddYear(EmBodyCheckCommitModel em) {
		Integer i = 0;
		dbconn db = new dbconn();
		try {
			i = (Integer) db.callWithReturn("{?=call EmBc_Add_cyj(?,?)}",
					Types.INTEGER, em.getEbcc_id(), UserInfo.getUsername());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	// 修改年度体检信息
	public Integer embodycheckModYear(EmBodyCheckCommitModel em) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "update EmBodyCheckCommit set ebcc_modtime=getdate(),ebcc_modname='"
				+ UserInfo.getUsername() + "'";

		if (em.getEbcc_hospital() != null) {
			if (!em.getEbcc_hospital().equals(0)) {
				sql = sql + ",ebcc_hospital=" + em.getEbcc_hospital();
			} else {
				sql = sql + ",ebcc_hospital=null";
			}

		}
		if (em.getEbcc_address() != null) {
			if (!em.getEbcc_address().equals(0)) {
				sql = sql + ",ebcc_address=" + em.getEbcc_address();
			} else {
				sql = sql + ",ebcc_address=null";
			}

		}
		if (em.getEbcc_bookdate() != null) {
			if (!em.getEbcc_bookdate().equals("")) {
				sql = sql + ",ebcc_bookdate='" + em.getEbcc_bookdate() + "'";
			} else {
				sql = sql + ",ebcc_bookdate=null";
			}

		}
		if (em.getEbcc_groupid() != null) {
			if (!em.getEbcc_groupid().equals(0)) {
				sql = sql + ",ebcc_groupid=" + em.getEbcc_groupid();
			} else {
				sql = sql + ",ebcc_groupid=null";
			}

		}

		if (em.getEbcc_itemid() != null) {
			if (!em.getEbcc_itemid().equals(0)) {
				sql = sql + ",ebcc_itemid=" + em.getEbcc_itemid();
			} else {
				sql = sql + ",ebcc_itemid=null";
			}

		}

		if (em.getEbcc_deleteState() != null) {
			if (!em.getEbcc_deleteState().equals("")) {
				sql += ",ebcc_deleteState=" + em.getEbcc_deleteState();
			}
		}

		sql = sql + " where 1=1";

		if (em.getEbcc_id() != null) {
			sql = sql + " and ebcc_id=" + em.getEbcc_id();
		}

		try {
			i = db.updatePrepareSQL(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	// 更新体检信息任务单id
	public boolean updateEmbodyCheckTaprid(int id, int taprid) {
		dbconn db = new dbconn();
		PreparedStatement psmt = null;
		int row = 0;
		String sql = "update EmBodyCheck set embc_tapr_id=? where embc_id=?";
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

	// 更新体检信息任务单id
	public Integer updateEmbodyCheckInfo(int id, String sqlstr) {
		dbconn db = new dbconn();
		int row = 0;
		String sql = "update EmBodyCheck set embc_modname='"
				+ UserInfo.getUsername() + "',embc_modtime=getdate() " + sqlstr
				+ " where embc_id=" + id;
		try {
			row = db.execQuery(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return row;
	}

	// 更新体检信息任务单id
	public Integer updateEmbodyCheckInfo(String idstr, String sqlstr) {
		dbconn db = new dbconn();
		int row = 0;
		String sql = "update EmBodyCheck set embc_modname='"
				+ UserInfo.getUsername() + "',embc_modtime=getdate() " + sqlstr
				+ " where embc_id in(" + idstr + ")";
		try {
			row = db.execQuery(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return row;
	}

	// 更新EmbodyChecklist表信息
	public int updateEmbodyChecklist(int id, String str) {
		dbconn db = new dbconn();
		int row = 0;
		
		String sql = "update embodychecklist set ebcl_modtime=getdate()"
				+ ",ebcl_modname='" + UserInfo.getUsername() + "'";
		sql = sql + str;
		sql = sql + " where ebcl_id=" + id;
		try {
			row = db.execQuery(sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return row;
	}

	// 更新EmbodyChecklist表信息
	public int EditEmbodyChecklist(String idstr, String str) {
		dbconn db = new dbconn();
		int row = 0;
		java.util.Date d = new java.util.Date();
		String sql = "update embodychecklist set ebcl_modtime=getdate()"
				+ ",ebcl_modname='" + UserInfo.getUsername() + "'";
		sql = sql + str;
		sql = sql + " where ebcl_id in(" + idstr + ")";
		try {
			row = db.execQuery(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return row;
	}

	// 把字符串转换成时间
	public Date changedate(String s) {
		Date da = null;
		if (s != null && !s.equals("") && s != "") {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 小写的mm表示的是分钟
			java.util.Date date = null;
			try {
				date = sdf.parse(s);
				if (date != null && !date.equals("")) {
					java.sql.Date reda = new java.sql.Date(date.getTime());
					da = reda;
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return da;
	}

	// 取消体检信息
	public Integer EmBodyCheckCancel(EmBodycheckCancelModel m) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
					.getcall("EmBodyCheck_Cancel_p_cyj(?,?,?,?,?,?,?)");
			c.setInt(1, m.getEmca_embc_id());
			c.setInt(2, m.getEbcl_id());
			c.setString(3, m.getEmca_remark());
			c.setString(4, m.getEmca_addname());
			c.setString(5, m.getEmca_hospital());
			c.setString(6, m.getEmca_content());
			c.registerOutParameter(7, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(7);
		} catch (SQLException e) {
			System.out.print("错误：" + e.getMessage());
			return 0;
		}
	}

	// 更新embase表的emba_email
	public int updateEmabseEmail(String emba_email, int gid) {
		dbconn db = new dbconn();
		int row = 0;
		java.util.Date d = new java.util.Date();
		String sql = "update embase set emba_email='" + emba_email + "'";
		sql = sql + " where gid=" + gid;
		try {
			row = db.execQuery(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return row;
	}

	// 更新embase表的emba_email
	public int updateEmabseMarital(String emba_marital, int gid) {
		dbconn db = new dbconn();
		int row = 0;
		java.util.Date d = new java.util.Date();
		String sql = "update embase set emba_marital='" + emba_marital + "'";
		sql = sql + " where gid=" + gid;
		try {
			row = db.execQuery(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return row;
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

	public int insertLog(embodycheckoperlogModel m) {
		int k = 0;
		String sql = "insert into embodycheckoperlog(bclg_ebcl_id,bclg_content,bclg_addname)";
		sql = sql + " values(" + m.getBclg_ebcl_id() + ",'"
				+ m.getBclg_content() + "','" + m.getBclg_addname() + "')";
		System.out.print(sql);
		dbconn db = new dbconn();
		k = db.execQuery(sql);
		return k;
	}
}
