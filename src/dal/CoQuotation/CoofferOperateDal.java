package dal.CoQuotation;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;

import Conn.dbconn;
import Model.CoOfferModel;
import Util.UserInfo;

public class CoofferOperateDal {

	// 合同新增独立户触发生成社保公积金独立户模拟数据
	public Integer addData(Integer cid, Integer id, boolean sb, boolean house) {
		Integer i = 0;
		Integer j = 0;
		Integer k = 0;
		dbconn db = new dbconn();
		String sql = "insert into cooffer(coof_name,coof_cpct_id,coof_state,"
				+ "coof_addname,coof_addtime,coof_cola_id,cid,coof_coco_id)";

		if (sb || house) {
			sql += "select '独立户',0,0,'系统',getdate(),0,?,?";
		}
		try {
			i = db.insertAndReturnKey(sql, cid, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (i > 0) {
			sql = "insert into coofferlist(coli_coof_id,coli_copr_id,coli_name,"
					+ "coli_pclass,coli_addname,coli_addtime,coli_state,coli_fee,"
					+ "coli_cpfc_name,coli_coco_id,coli_parid,coli_isfwf,coli_group_id,coli_group_count)";
			if (sb) {
				String s1 = "select ?,7,'社会保险服务','人事服务','系统',getdate(),1,0,'元/月/人',?,0,0,0,1";
				try {
					j = db.updatePrepareSQL(sql + s1, i, id);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (house) {
				String s2 = "select ?,8,'住房公积金服务','人事服务','系统',getdate(),1,0,'元/月/人',?,0,0,0,1";
				try {
					k = db.updatePrepareSQL(sql + s2, i, id);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			i = j + k;
		}
		return i;
	}

	// 更新报价单的状态
	public Integer UpdateCooffer(CoOfferModel model) {
		Integer k = 0;
		dbconn db = new dbconn();
		String sql = "update CoOffer set coof_state=2,coof_auditing_name='"
				+ model.getCoof_auditing_name() + "'";
		sql = sql + ",coof_auditing_time='"
				+ convertToSqlDate(model.getCoof_auditing_time())
				+ "' where coof_id=" + model.getCoof_id();
		try {
			k = db.execQuery(sql);
		} catch (Exception e) {

		}
		return k;
	}

	// 更新报价单审核流程id
	public boolean updateCooferTaprid(int taprid, int id) {
		dbconn db = new dbconn();
		PreparedStatement psmt = null;
		int row = 0;
		String sql = "update CoOffer set coof_tarp_id=? where coof_id=?";
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

	// 更新报价单改为审核中
	public Integer updateCooferState(int id) {
		dbconn db = new dbconn();
		PreparedStatement psmt = null;
		int row = 0;
		String sql = "update CoOffer set coof_state=4," + "coof_auditaddname='"
				+ UserInfo.getUsername() + "'," + "coof_auditaddtime='"
				+ DateToStr() + "' where coof_id=?";
		try {
			psmt = db.getpre(sql);
			psmt.setInt(1, id);
			row = psmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return row;
	}

	public Integer delCoofferById(Integer id) {
		dbconn db = new dbconn();
		Integer i = 0;

		try {
			i = (Integer) db.callWithReturn("{?=call CoOfferDel_P_py(?,?)}",
					Types.INTEGER, id, UserInfo.getUsername());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date
	 */
	public static Date convertToSqlDate(String str) {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date now = new java.util.Date();
		Date sqlDate = null;
		try {
			if (str != null) {
				java.util.Date date = bartDateFormat.parse(str);
				sqlDate = new java.sql.Date(date.getTime());
			} else {
				sqlDate = new java.sql.Date(now.getTime());
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return sqlDate;
	}

	// 退回
	public Integer CooferBack(int id, String backcase) {
		dbconn db = new dbconn();
		int row = 0;
		String sql = "update CoOffer set coof_state=5,coof_backcase='"
				+ backcase + "'," + "coof_backname='" + UserInfo.getUsername()
				+ "',coof_backtime='" + DateToStr() + "' where coof_id=" + id;
		try {
			row = db.execQuery(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return row;
	}

	/**
	 * 日期转换成字符串
	 * 
	 * @param date
	 * @return str
	 */
	public static String DateToStr() {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = format.format(date);
		return str;
	}

	// 根据报价单id查询公司名称
	public String getCompany(Integer coof_id) {
		String company = "";
		String sql = "select * from CoOffer a inner join (select c.coco_id,c.cid,"
				+ "coba_shortname,coba_company from CoCompact c inner join CoBase d "
				+ "on c.cid=d.cid) b on a.coof_coco_id=b.coco_id where coof_id="
				+ coof_id;
		try {
			dbconn db = new dbconn();
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				company = rs.getString("coba_shortname");
			}
		} catch (Exception e) {

		}
		return company;
	}
}
