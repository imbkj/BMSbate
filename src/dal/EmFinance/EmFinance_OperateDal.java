package dal.EmFinance;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import Conn.dbconn;
import Model.EmFinanceZYTModel;

public class EmFinance_OperateDal {
	// 新增智翼通台帐数据
	public Integer EmFinanceZYTAdd(EmFinanceZYTModel model) {
		Integer i = 0;
		dbconn db = new dbconn();
		try {
			i = Integer
					.parseInt(db
							.callWithReturn(
									"{?=call EmFinanceZYT_Add_CYJ(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, model.getScompany(),
									model.getScity(), model.getRcity(),
									model.getRcompany(),
									model.getEmfz_company(),
									model.getEmfz_zgid(), model.getEmfz_name(),
									model.getEmfz_idcard(),
									model.getOwnmonth(),
									model.getEmfz_yltotal(),
									model.getEmfz_syetotal(),
									model.getEmfz_gstotal(),
									model.getEmfz_syutotal(),
									model.getEmfz_jltotal(),
									model.getEmfz_housetotal(),
									model.getEmfz_bjtotal(),
									model.getEmfz_sbtotal(),
									model.getEmfz_elsefee(),
									model.getEmfz_sbchange(),
									model.getEmfz_servertotal(),
									model.getEmfz_fee(),
									model.getEmfz_filefee(),
									model.getEmfz_total(),
									model.getEmfz_remark(),
									model.getEmfz_ylcp(), model.getEmfz_ylop(),
									model.getEmfz_jlcp(), model.getEmfz_jlop(),
									model.getEmfz_gscp(), model.getEmfz_gsop(),
									model.getEmfz_syecp(),
									model.getEmfz_syeop(),
									model.getEmfz_syucp(),
									model.getEmfz_syuop(),
									model.getEmfz_housecp(),
									model.getEmfz_houseop(),
									model.getEmfz_bjcp(), model.getEmfz_bjop(),
									model.getEmfz_ylradix(),
									model.getEmfz_jlradix(),
									model.getEmfz_gsradix(),
									model.getEmfz_syeradix(),
									model.getEmfz_syuradix(),
									model.getEmfz_houseradix(),
									model.getEmfz_bjradix(),
									model.getEmfz_filename()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	// 修改智翼通台帐信息
	public void EmFinanceZYTEdit(Integer ownmonth, String filename) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db.getcall("EmFinanceZYT_UpdateGID_CYJ(?,?)");
			c.setInt(1, ownmonth);
			c.setString(2, filename);
			c.execute();
		} catch (SQLException e) {
		}
	}

	// 根据所属月份和委托地区删除EmFinanceZYT表数据
	public Integer delEmFinanceZYT(Integer ownmonth, String city,
			String filename) {
		Integer k = 0;
		String sql = "DELETE FROM EmFinanceZYT WHERE ownmonth=" + ownmonth
				+ " AND scity='" + city + "'" + " and emfz_filename<>'"
				+ filename + "'";
		System.out.println(sql);
		dbconn db = new dbconn();
		try {
			k = db.execQuery(sql);
		} catch (Exception e) {

		}
		return k;
	}

	// 调整报价单项目月份
	public Integer changeCoGlist(String cgli_id, String startdate,
			String startdate2, String stopdate, String username) {
		Integer k = 0;
		String str = "";

		if (!"".equals(startdate) && startdate != null
				&& !"null".equals(startdate)) {
			str = str + ",CGLI_STARTDATE=" + startdate;
		}
		if (!"".equals(startdate2) && startdate2 != null
				&& !"null".equals(startdate2)) {
			str = str + ",CGLI_STARTDATE2=" + startdate2;
		}
		if (!"".equals(stopdate) && stopdate != null
				&& !"null".equals(stopdate)) {
			str = str + ",CGLI_STOPDATE=" + stopdate;
		}

		String sql = "UPDATE COGLIST SET CGLI_MODTIME=GETDATE(),CGLI_MODNAME='"
				+ username + "'" + str + " WHERE CGLI_ID=" + cgli_id;
		dbconn db = new dbconn();
		try {
			k = db.execQuery(sql);
		} catch (Exception e) {
			k = 0;
		}
		return k;
	}

	public Integer changeSBBJFeemonth(String id, String feemonth,
			String username) {
		Integer k = 0;

		String sql = "UPDATE EmShebaoBJ SET emsb_feeownmonth=" + feemonth
				+ " WHERE ID=" + id;
		dbconn db = new dbconn();
		try {
			k = db.execQuery(sql);
		} catch (Exception e) {
			k = 0;
		}
		return k;
	}

	public Integer changeSBBJJLFeemonth(String id, String feemonth,
			String username) {
		Integer k = 0;

		String sql = "UPDATE EmShebaoBJJL SET esbj_feeownmonth=" + feemonth
				+ " WHERE ID=" + id;
		dbconn db = new dbconn();
		try {
			k = db.execQuery(sql);
		} catch (Exception e) {
			k = 0;
		}
		return k;
	}

	public Integer changeGJJBJFeemonth(String id, String feemonth,
			String username) {
		Integer k = 0;

		String sql = "UPDATE EmHouseBJ SET emhb_feemonth=" + feemonth
				+ " WHERE emhb_id=" + id;
		dbconn db = new dbconn();
		try {
			k = db.execQuery(sql);
		} catch (Exception e) {
			k = 0;
		}
		return k;
	}

	// 获取公司最新已确认台账月份
	public Integer getFinanceOwnmonth(Integer cid) {
		Integer fm = 0;
		String sql = "select isnull(a.ownmonth,0)ownmonth from CoFinanceMonthlyBill a left join CoFinanceTotalAccount b on a.cfmb_cfta_id=b.cfta_id where a.cfmb_PersonnelConfirm=1 and b.cid="
				+ cid + " order by a.ownmonth desc";
		dbconn conn = new dbconn();
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				fm = rs.getInt("ownmonth");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fm;
	}
}
