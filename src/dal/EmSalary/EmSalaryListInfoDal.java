package dal.EmSalary;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.CoBaseModel;
import Model.CoFinanceMonthlyBillSortAccountModel;
import Model.EmSalaryBaseAddItemModel;
import Model.EmSalaryDataModel;
import Model.EmSalaryInfoModel;
import Model.EmSalaryPayInfoModel;
import Model.LoginModel;

public class EmSalaryListInfoDal {

	private static dbconn conn = new dbconn();

	// 获取工资所属月份公司的员工列表

	private ResultSet getEmSalarylist(String str) throws Exception {
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT  * from  View_ESDa_EmBase_CoBase where 1=1");
		sql.append(str);
		sql.append(" order by esda_id ");
		// System.out.println(sql);
		rs = conn.GRS(sql.toString());
		return rs;
	}

	public List<EmSalaryInfoModel> getSalaryList(Integer cid, Integer gid) {
		dbconn db = new dbconn();
		String sql = "select cid,gid,esin_taxplace,esin_salaryplace,esin_hpro"
				+ " from EmSalaryInfo " + "where cid=? and gid=?";
		List<EmSalaryInfoModel> list = new ListModelList<>();
		try {
			list = db.find(sql, EmSalaryInfoModel.class, null, cid, gid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	private ResultSet getcoSalaryint(String str) throws Exception {
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT cid,csii_col,csii_item_name,csii_sequence from  View_CSII_CSIIDI where csii_fd_state=1");
		sql.append(str);
		sql.append(" order by csii_sequence ");
		// System.out.println(sql);
		rs = conn.GRS(sql.toString());
		return rs;
	}

	private ResultSet getEmSalaryinfolist(int str, int type) throws Exception {

		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		sql.append("exec EmSalaryinfo_p_zmj " + str + "," + type + "");
		// System.out.println(sql);
		rs = conn.GRS(sql.toString());
		return rs;
	}

	// 检查是否任务单执行下一步
	public int checkstate(int d_id, int state) {
		int i = 0;
		String sql = "select COUNT(*) as count from EmSalaryData where EmSalaryData.esda_id in(SELECT tbrb_data_id FROM TaskBatchRel_view WHERE tbrb_taba_id = "
				+ d_id
				+ ") and esda_oof_state=0 and esda_payment_state="
				+ state + "";
		// System.out.println(sql);
		try {
			ResultSet rs = null;
			rs = conn.GRS(sql.toString());

			while (rs.next()) {

				i = rs.getInt("count");

			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return i;

	}

	// 根据查询语句获取员工列表数据集
	public List<EmSalaryDataModel> getsalaryList(String str) {
		List<EmSalaryDataModel> embasalaryList = new ArrayList<EmSalaryDataModel>();
		// EmSalaryDataModel EmSModel =new EmSalaryDataModel();
		try {
			ResultSet rs = getEmSalarylist(str);
			while (rs.next()) {

				embasalaryList.add(new EmSalaryDataModel(rs.getString("name"),
						rs.getInt("esda_id"), rs.getInt("cid"), rs
								.getInt("gid"), rs.getInt("ownmonth"), rs
								.getInt("esda_if_bms"), rs
								.getString("esda_addname"), rs
								.getString("esda_addtime"), rs
								.getString("esda_remark"), rs
								.getString("esda_ba_name"), rs
								.getInt("esda_oof_state"), rs
								.getInt("esda_confirm_state"), rs
								.getString("esda_payment_date"), rs
								.getBigDecimal("esda_pay"), rs
								.getString("esda_usage_typestr"), rs
								.getString("esda_oof_statestr"), rs
								.getString("esda_if_bmsstr"), rs
								.getString("esda_remarkstr"), rs
								.getString("esda_payment_statestr")

				));
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return embasalaryList;
	}

	// d_id获取cid
	public int getcid(int d_id) {
		int i = 0;

		String sql = "select cid from EmSalaryData where esda_id=" + d_id;
		try {
			ResultSet rs = null;
			rs = conn.GRS(sql.toString());

			while (rs.next()) {

				i = rs.getInt("cid");

			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return i;
	}

	// 根据查询员工变量工资数据
	public List<EmSalaryInfoModel> geemsalaryinfo(int str, int type) {
		List<EmSalaryInfoModel> embasalaryinfoList = new ArrayList<EmSalaryInfoModel>();

		try {
			ResultSet rs = getEmSalaryinfolist(str, type);
			// System.out.println(rs);
			while (rs.next()) {
				// System.out.println(rs.getInt("gzje"));
				embasalaryinfoList.add(new EmSalaryInfoModel(rs
						.getInt("esda_id"), rs.getInt("cid"), rs
						.getString("name"), rs.getInt("ownmonth"), rs
						.getInt("csii_sequence"), rs.getString("gzdm"), rs
						.getString("gzmc"), rs.getBigDecimal("gzje")

				));
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return embasalaryinfoList;
	}

	// 根据Cid及所属月份获取项目信息type0财务 1工资
	public List<EmSalaryBaseAddItemModel> getItemInfoByCidMonth(int cid,
			int ownmonth, int type) {
		List<EmSalaryBaseAddItemModel> list = new ArrayList<EmSalaryBaseAddItemModel>();
		EmSalaryBaseAddItemModel m = null;
		String sql = "";
		if (type == 1) {
			sql = "select csii_col,csii_item_name from CSII_CFDa_V "
					+ "WHERE  cid=? AND ownmonth=? and csii_fd_state=1  ORDER BY csii_csd_state desc,csii_sequence";
		} else {
			sql = "select csii_col,csii_item_name from CSII_CFDa_V "
					+ "WHERE  cid=? AND ownmonth=? and csii_csd_state=1  ORDER BY csii_csd_state desc,csii_sequence";
		}
		// System.out.println(sql);
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, cid);
			psmt.setInt(2, ownmonth);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new EmSalaryBaseAddItemModel();
				m.setCsii_col(rs.getString("csii_col"));
				m.setCsii_item_name(rs.getString("csii_item_name"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据Cid及所属月份获取项目信息
	public List<EmSalaryBaseAddItemModel> getItemInfoByCidMonthforemail(
			int cid, int ownmonth) {
		List<EmSalaryBaseAddItemModel> list = new ArrayList<EmSalaryBaseAddItemModel>();
		EmSalaryBaseAddItemModel m = null;
		String sql = "select csii_col,csii_item_name from CSII_CFDa_V "
				+ "WHERE  cid=? AND ownmonth=? and csii_csd_state=1  ORDER BY csii_csd_state desc,csii_sequence";
		// System.out.println(sql);
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, cid);
			psmt.setInt(2, ownmonth);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new EmSalaryBaseAddItemModel();
				m.setCsii_col(rs.getString("csii_col"));
				m.setCsii_item_name(rs.getString("csii_item_name"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据Cid及所属月份获取项目信息
	public List<EmSalaryBaseAddItemModel> getItemInfoByCidMonth(int d_id)
			throws Exception {
		List<EmSalaryBaseAddItemModel> list = new ArrayList<EmSalaryBaseAddItemModel>();
		EmSalaryBaseAddItemModel m = null;
		String sql = "select csii_col,csii_item_name from CSII_CFDa_V wv, "
				+ "(select top 1* from EmSalaryData where EmSalaryData.esda_id in"
				+ "(SELECT tbrb_data_id FROM TaskBatchRel_view WHERE tbrb_taba_id ="
				+ d_id
				+ ")) ww "
				+ "where  wv.cid=ww.cid and wv.ownmonth=ww.ownmonth and  wv.csii_fd_state=1  ORDER BY csii_csd_state desc,csii_sequence";
		// System.out.println(sql);
		try {
			ResultSet rs = null;
			rs = conn.GRS(sql.toString());
			while (rs.next()) {
				m = new EmSalaryBaseAddItemModel();
				m.setCsii_col(rs.getString("csii_col"));
				m.setCsii_item_name(rs.getString("csii_item_name"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据Cid及所属月份获取工资信息
	public List<EmSalaryDataModel> getSalaryDataByCidMonth(int d_id)
			throws Exception {
		List<EmSalaryDataModel> list = new ArrayList<EmSalaryDataModel>();
		String sql = "select  eb.emba_name,ed.* from  (select * from EmSalaryData where EmSalaryData.esda_id in "
				+ "(SELECT tbrb_data_id FROM TaskBatchRel_view WHERE  tbrb_taba_id ="
				+ d_id + "))  ed " + "left join EmBase eb on ed.gid=eb.gid ";

		try {
			// System.out.println(sql);
			ResultSet rs = null;
			rs = conn.GRS(sql.toString());

			while (rs.next()) {
				list.add(new EmSalaryDataModel(rs.getInt("esda_id"), rs
						.getInt("cid"), rs.getInt("gid"),
						rs.getInt("ownmonth"), rs.getInt("esda_if_bms"), rs
								.getString("esda_addname"), rs
								.getString("esda_addtime"), rs
								.getString("esda_remark"), rs
								.getString("esda_fd_remark"), rs
								.getInt("cfin_id"),
						rs.getString("csii_itemid"), rs
								.getString("esda_o_bank"), rs
								.getString("esda_bank"), rs
								.getString("esda_ba_name"), rs
								.getString("esda_bank_account"), rs
								.getString("esda_nationality"), rs
								.getInt("esda_usage_type"), rs
								.getString("esda_email"), rs
								.getString("esda_bcc_email"), rs
								.getString("esda_email_sender"), rs
								.getInt("esda_email_state"), rs
								.getString("esda_email_date"), rs
								.getInt("esda_oof_state"), rs
								.getInt("esda_confirm_state"), rs
								.getInt("esda_payment_state"), rs
								.getString("esda_payment_date"), rs
								.getInt("esda_payment_batch"), rs
								.getInt("esda_data_type"), rs
								.getInt("esda_rp_reason"), rs
								.getInt("esda_history_state"), rs
								.getString("esda_history_date"), rs
								.getInt("esda_ttv_state"), rs
								.getString("esda_ttv_date"), rs
								.getString("esda_ttv_people"), rs
								.getBigDecimal("esda_base_radix"), rs
								.getBigDecimal("esda_a_base_radix"), rs
								.getBigDecimal("esda_a_workdays"), rs
								.getBigDecimal("esda_a_base_salary"), rs
								.getBigDecimal("esda_days"), rs
								.getBigDecimal("esda_cqdays"), rs
								.getBigDecimal("esda_b_cqdays"), rs
								.getBigDecimal("esda_qqdays"), rs
								.getBigDecimal("esda_b_qqdays"), rs
								.getBigDecimal("esda_aded"), rs
								.getBigDecimal("esda_a_aded"), rs
								.getBigDecimal("esda_base_salary"), rs
								.getBigDecimal("esda_siop"), rs
								.getBigDecimal("esda_hafop"), rs
								.getBigDecimal("esda_haf"), rs
								.getBigDecimal("esda_house_radix"), rs
								.getBigDecimal("esda_house_percent"), rs
								.getBigDecimal("esda_house_ntl"), rs
								.getBigDecimal("esda_total_pretax"), rs
								.getBigDecimal("esda_tax_base"), rs
								.getBigDecimal("esda_tax"), rs
								.getBigDecimal("esda_dc"), rs
								.getBigDecimal("esda_dc_tax"), rs
								.getBigDecimal("esda_db"), rs
								.getBigDecimal("esda_db_tax_base"), rs
								.getBigDecimal("esda_db_tax"), rs
								.getBigDecimal("esda_stock"), rs
								.getBigDecimal("esda_stock_tax"), rs
								.getBigDecimal("esda_other"), rs
								.getBigDecimal("esda_other_tax"), rs
								.getBigDecimal("esda_write_off"), rs
								.getBigDecimal("esda_house_bf"), rs
								.getBigDecimal("esda_pay"), rs
								.getBigDecimal("esda_col_1"), rs
								.getBigDecimal("esda_col_2"), rs
								.getBigDecimal("esda_col_3"), rs
								.getBigDecimal("esda_col_4"), rs
								.getBigDecimal("esda_col_5"), rs
								.getBigDecimal("esda_col_6"), rs
								.getBigDecimal("esda_col_7"), rs
								.getBigDecimal("esda_col_8"), rs
								.getBigDecimal("esda_col_9"), rs
								.getBigDecimal("esda_col_10"), rs
								.getBigDecimal("esda_col_11"), rs
								.getBigDecimal("esda_col_12"), rs
								.getBigDecimal("esda_col_13"), rs
								.getBigDecimal("esda_col_14"), rs
								.getBigDecimal("esda_col_15"), rs
								.getBigDecimal("esda_col_16"), rs
								.getBigDecimal("esda_col_17"), rs
								.getBigDecimal("esda_col_18"), rs
								.getBigDecimal("esda_col_19"), rs
								.getBigDecimal("esda_col_20"), rs
								.getBigDecimal("esda_col_21"), rs
								.getBigDecimal("esda_col_22"), rs
								.getBigDecimal("esda_col_23"), rs
								.getBigDecimal("esda_col_24"), rs
								.getBigDecimal("esda_col_25"), rs
								.getBigDecimal("esda_col_26"), rs
								.getBigDecimal("esda_col_27"), rs
								.getBigDecimal("esda_col_28"), rs
								.getBigDecimal("esda_col_29"), rs
								.getBigDecimal("esda_col_30"), rs
								.getBigDecimal("esda_col_31"), rs
								.getBigDecimal("esda_col_32"), rs
								.getBigDecimal("esda_col_33"), rs
								.getBigDecimal("esda_col_34"), rs
								.getBigDecimal("esda_col_35"), rs
								.getBigDecimal("esda_col_36"), rs
								.getBigDecimal("esda_col_37"), rs
								.getBigDecimal("esda_col_38"), rs
								.getBigDecimal("esda_col_39"), rs
								.getBigDecimal("esda_col_40"), rs
								.getBigDecimal("esda_col_41"), rs
								.getBigDecimal("esda_col_42"), rs
								.getBigDecimal("esda_col_43"), rs
								.getBigDecimal("esda_col_44"), rs
								.getBigDecimal("esda_col_45"), rs
								.getBigDecimal("esda_col_46"), rs
								.getBigDecimal("esda_col_47"), rs
								.getBigDecimal("esda_col_48"), rs
								.getBigDecimal("esda_col_49"), rs
								.getBigDecimal("esda_col_50"), rs
								.getBigDecimal("esda_col_51"), rs
								.getBigDecimal("esda_col_52"), rs
								.getBigDecimal("esda_col_53"), rs
								.getBigDecimal("esda_col_54"), rs
								.getBigDecimal("esda_col_55"), rs
								.getBigDecimal("esda_col_56"), rs
								.getBigDecimal("esda_col_57"), rs
								.getBigDecimal("esda_col_58"), rs
								.getBigDecimal("esda_col_59"), rs
								.getBigDecimal("esda_col_60"), rs
								.getBigDecimal("esda_col_61"), rs
								.getBigDecimal("esda_col_62"), rs
								.getBigDecimal("esda_col_63"), rs
								.getBigDecimal("esda_col_64"), rs
								.getBigDecimal("esda_col_65"), rs
								.getBigDecimal("esda_col_66"), rs
								.getBigDecimal("esda_col_67"), rs
								.getBigDecimal("esda_col_68"), rs
								.getBigDecimal("esda_col_69"), rs
								.getBigDecimal("esda_col_70"), rs
								.getBigDecimal("esda_col_71"), rs
								.getBigDecimal("esda_col_72"), rs
								.getBigDecimal("esda_col_73"), rs
								.getBigDecimal("esda_col_74"), rs
								.getBigDecimal("esda_col_75"), rs
								.getBigDecimal("esda_col_76"), rs
								.getBigDecimal("esda_col_77"), rs
								.getBigDecimal("esda_col_78"), rs
								.getBigDecimal("esda_col_79"), rs
								.getBigDecimal("esda_col_80"), rs
								.getString("emba_name"), rs
								.getInt("esda_ifhold")));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据Cid及所属月份获取工资信息
	public List<EmSalaryDataModel> getSalaryDataByCidMonth(int cid,
			int startownmonth, int endownmonth) throws Exception {
		List<EmSalaryDataModel> list = new ArrayList<EmSalaryDataModel>();
		String sql = "select eb.emba_name,ed.* from EmSalaryData ed left join EmBase eb on ed.gid=eb.gid "
				+ "where ed.cid="
				+ cid
				+ " and ed.ownmonth>="
				+ startownmonth
				+ "and ed.ownmonth<=" + endownmonth + " order by ownmonth desc";

		try {
			// System.out.println(sql);
			ResultSet rs = null;
			rs = conn.GRS(sql.toString());

			while (rs.next()) {
				list.add(new EmSalaryDataModel(rs.getInt("esda_id"), rs
						.getInt("cid"), rs.getInt("gid"),
						rs.getInt("ownmonth"), rs.getInt("esda_if_bms"), rs
								.getString("esda_addname"), rs
								.getString("esda_addtime"), rs
								.getString("esda_remark"), rs
								.getString("esda_fd_remark"), rs
								.getInt("cfin_id"),
						rs.getString("csii_itemid"), rs
								.getString("esda_o_bank"), rs
								.getString("esda_bank"), rs
								.getString("esda_ba_name"), rs
								.getString("esda_bank_account"), rs
								.getString("esda_nationality"), rs
								.getInt("esda_usage_type"), rs
								.getString("esda_email"), rs
								.getString("esda_bcc_email"), rs
								.getString("esda_email_sender"), rs
								.getInt("esda_email_state"), rs
								.getString("esda_email_date"), rs
								.getInt("esda_oof_state"), rs
								.getInt("esda_confirm_state"), rs
								.getInt("esda_payment_state"), rs
								.getString("esda_payment_date"), rs
								.getInt("esda_payment_batch"), rs
								.getInt("esda_data_type"), rs
								.getInt("esda_rp_reason"), rs
								.getInt("esda_history_state"), rs
								.getString("esda_history_date"), rs
								.getInt("esda_ttv_state"), rs
								.getString("esda_ttv_date"), rs
								.getString("esda_ttv_people"), rs
								.getBigDecimal("esda_base_radix"), rs
								.getBigDecimal("esda_a_base_radix"), rs
								.getBigDecimal("esda_a_workdays"), rs
								.getBigDecimal("esda_a_base_salary"), rs
								.getBigDecimal("esda_days"), rs
								.getBigDecimal("esda_cqdays"), rs
								.getBigDecimal("esda_b_cqdays"), rs
								.getBigDecimal("esda_qqdays"), rs
								.getBigDecimal("esda_b_qqdays"), rs
								.getBigDecimal("esda_aded"), rs
								.getBigDecimal("esda_a_aded"), rs
								.getBigDecimal("esda_base_salary"), rs
								.getBigDecimal("esda_siop"), rs
								.getBigDecimal("esda_hafop"), rs
								.getBigDecimal("esda_haf"), rs
								.getBigDecimal("esda_house_radix"), rs
								.getBigDecimal("esda_house_percent"), rs
								.getBigDecimal("esda_house_ntl"), rs
								.getBigDecimal("esda_total_pretax"), rs
								.getBigDecimal("esda_tax_base"), rs
								.getBigDecimal("esda_tax"), rs
								.getBigDecimal("esda_dc"), rs
								.getBigDecimal("esda_dc_tax"), rs
								.getBigDecimal("esda_db"), rs
								.getBigDecimal("esda_db_tax_base"), rs
								.getBigDecimal("esda_db_tax"), rs
								.getBigDecimal("esda_stock"), rs
								.getBigDecimal("esda_stock_tax"), rs
								.getBigDecimal("esda_other"), rs
								.getBigDecimal("esda_other_tax"), rs
								.getBigDecimal("esda_write_off"), rs
								.getBigDecimal("esda_house_bf"), rs
								.getBigDecimal("esda_pay"), rs
								.getBigDecimal("esda_col_1"), rs
								.getBigDecimal("esda_col_2"), rs
								.getBigDecimal("esda_col_3"), rs
								.getBigDecimal("esda_col_4"), rs
								.getBigDecimal("esda_col_5"), rs
								.getBigDecimal("esda_col_6"), rs
								.getBigDecimal("esda_col_7"), rs
								.getBigDecimal("esda_col_8"), rs
								.getBigDecimal("esda_col_9"), rs
								.getBigDecimal("esda_col_10"), rs
								.getBigDecimal("esda_col_11"), rs
								.getBigDecimal("esda_col_12"), rs
								.getBigDecimal("esda_col_13"), rs
								.getBigDecimal("esda_col_14"), rs
								.getBigDecimal("esda_col_15"), rs
								.getBigDecimal("esda_col_16"), rs
								.getBigDecimal("esda_col_17"), rs
								.getBigDecimal("esda_col_18"), rs
								.getBigDecimal("esda_col_19"), rs
								.getBigDecimal("esda_col_20"), rs
								.getBigDecimal("esda_col_21"), rs
								.getBigDecimal("esda_col_22"), rs
								.getBigDecimal("esda_col_23"), rs
								.getBigDecimal("esda_col_24"), rs
								.getBigDecimal("esda_col_25"), rs
								.getBigDecimal("esda_col_26"), rs
								.getBigDecimal("esda_col_27"), rs
								.getBigDecimal("esda_col_28"), rs
								.getBigDecimal("esda_col_29"), rs
								.getBigDecimal("esda_col_30"), rs
								.getBigDecimal("esda_col_31"), rs
								.getBigDecimal("esda_col_32"), rs
								.getBigDecimal("esda_col_33"), rs
								.getBigDecimal("esda_col_34"), rs
								.getBigDecimal("esda_col_35"), rs
								.getBigDecimal("esda_col_36"), rs
								.getBigDecimal("esda_col_37"), rs
								.getBigDecimal("esda_col_38"), rs
								.getBigDecimal("esda_col_39"), rs
								.getBigDecimal("esda_col_40"), rs
								.getBigDecimal("esda_col_41"), rs
								.getBigDecimal("esda_col_42"), rs
								.getBigDecimal("esda_col_43"), rs
								.getBigDecimal("esda_col_44"), rs
								.getBigDecimal("esda_col_45"), rs
								.getBigDecimal("esda_col_46"), rs
								.getBigDecimal("esda_col_47"), rs
								.getBigDecimal("esda_col_48"), rs
								.getBigDecimal("esda_col_49"), rs
								.getBigDecimal("esda_col_50"), rs
								.getBigDecimal("esda_col_51"), rs
								.getBigDecimal("esda_col_52"), rs
								.getBigDecimal("esda_col_53"), rs
								.getBigDecimal("esda_col_54"), rs
								.getBigDecimal("esda_col_55"), rs
								.getBigDecimal("esda_col_56"), rs
								.getBigDecimal("esda_col_57"), rs
								.getBigDecimal("esda_col_58"), rs
								.getBigDecimal("esda_col_59"), rs
								.getBigDecimal("esda_col_60"), rs
								.getBigDecimal("esda_col_61"), rs
								.getBigDecimal("esda_col_62"), rs
								.getBigDecimal("esda_col_63"), rs
								.getBigDecimal("esda_col_64"), rs
								.getBigDecimal("esda_col_65"), rs
								.getBigDecimal("esda_col_66"), rs
								.getBigDecimal("esda_col_67"), rs
								.getBigDecimal("esda_col_68"), rs
								.getBigDecimal("esda_col_69"), rs
								.getBigDecimal("esda_col_70"), rs
								.getBigDecimal("esda_col_71"), rs
								.getBigDecimal("esda_col_72"), rs
								.getBigDecimal("esda_col_73"), rs
								.getBigDecimal("esda_col_74"), rs
								.getBigDecimal("esda_col_75"), rs
								.getBigDecimal("esda_col_76"), rs
								.getBigDecimal("esda_col_77"), rs
								.getBigDecimal("esda_col_78"), rs
								.getBigDecimal("esda_col_79"), rs
								.getBigDecimal("esda_col_80"), rs
								.getString("emba_name"), rs
								.getInt("esda_ifhold")));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<EmSalaryDataModel> getSalaryDataByCidMonth(Integer cid,
			int ownmonth) throws SQLException {
		List<EmSalaryDataModel> list = new ListModelList<EmSalaryDataModel>();
		// dbconn db = new dbconn();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT  ISNULL(SUM(esda_siop),0) AS esda_siop,ISNULL(SUM(esda_hafop),0) AS esda_hafop,");
		sql.append("ISNULL(SUM(esda_haf),0) AS esda_haf,ISNULL(SUM(esda_total_pretax),0) AS esda_total_pretax,");
		sql.append("ISNULL(SUM(esda_tax_base),0) AS esda_tax_base,ISNULL(SUM(esda_tax),0) AS esda_tax,");
		sql.append(" ISNULL(SUM(esda_dc),0) AS esda_dc,ISNULL(SUM(esda_dc_tax),0) AS esda_dc_tax,ISNULL(SUM(esda_db),0) AS esda_db,");
		sql.append("ISNULL(SUM(esda_db_tax),0) AS esda_db_tax,ISNULL(SUM(esda_stock),0) AS esda_stock,ISNULL(SUM(esda_stock_tax),0) AS esda_stock_tax,");
		sql.append("ISNULL(SUM(esda_write_off),0) AS esda_write_off,ISNULL(SUM(esda_house_bf),0) AS esda_house_bf,ISNULL(SUM(esda_pay),0) AS esda_pay,");
		sql.append("ISNULL(SUM(esda_col_1),0) AS esda_col_1,ISNULL(SUM(esda_col_2),0) AS esda_col_2,ISNULL(SUM(esda_col_3),0) AS esda_col_3,");
		sql.append("ISNULL(SUM(esda_col_4),0) AS esda_col_4,ISNULL(SUM(esda_col_5),0) AS esda_col_5,ISNULL(SUM(esda_col_6),0) AS esda_col_6,");
		sql.append("ISNULL(SUM(esda_col_7),0) AS esda_col_7,ISNULL(SUM(esda_col_8),0) AS esda_col_8,ISNULL(SUM(esda_col_9),0) AS esda_col_9,");
		sql.append("ISNULL(SUM(esda_col_10),0) AS esda_col_10,ISNULL(SUM(esda_col_11),0) AS esda_col_11,ISNULL(SUM(esda_col_12),0) AS esda_col_12,");
		sql.append("ISNULL(SUM(esda_col_13),0) AS esda_col_13,ISNULL(SUM(esda_col_14),0) AS esda_col_14,ISNULL(SUM(esda_col_15),0) AS esda_col_15,");
		sql.append("ISNULL(SUM(esda_col_16),0) AS esda_col_16,ISNULL(SUM(esda_col_17),0) AS esda_col_17,ISNULL(SUM(esda_col_18),0) AS esda_col_18,");
		sql.append("ISNULL(SUM(esda_col_19),0) AS esda_col_19,ISNULL(SUM(esda_col_20),0) AS esda_col_20,ISNULL(SUM(esda_col_21),0) AS esda_col_21,");
		sql.append("ISNULL(SUM(esda_col_22),0) AS esda_col_22,ISNULL(SUM(esda_col_23),0) AS esda_col_23,ISNULL(SUM(esda_col_24),0) AS esda_col_24,");
		sql.append("ISNULL(SUM(esda_col_25),0) AS esda_col_25,ISNULL(SUM(esda_col_26),0) AS esda_col_26,ISNULL(SUM(esda_col_27),0) AS esda_col_27,");
		sql.append(" ISNULL(SUM(esda_col_28),0) AS esda_col_28,ISNULL(SUM(esda_col_29),0) AS esda_col_29,ISNULL(SUM(esda_col_30),0) AS esda_col_30,");
		sql.append("ISNULL(SUM(esda_col_31),0) AS esda_col_31,ISNULL(SUM(esda_col_32),0) AS esda_col_32,ISNULL(SUM(esda_col_33),0) AS esda_col_33,");
		sql.append(" ISNULL(SUM(esda_col_34),0) AS esda_col_34,ISNULL(SUM(esda_col_35),0) AS esda_col_35,ISNULL(SUM(esda_col_36),0) AS esda_col_36,");
		sql.append(" ISNULL(SUM(esda_col_37),0) AS esda_col_37,ISNULL(SUM(esda_col_38),0) AS esda_col_38,ISNULL(SUM(esda_col_39),0) AS esda_col_39,");
		sql.append("ISNULL(SUM(esda_col_40),0) AS esda_col_40,ISNULL(SUM(esda_col_41),0) AS esda_col_41,ISNULL(SUM(esda_col_42),0) AS esda_col_42,");
		sql.append("ISNULL(SUM(esda_col_43),0) AS esda_col_43,ISNULL(SUM(esda_col_44),0) AS esda_col_44,ISNULL(SUM(esda_col_45),0) AS esda_col_45,");
		sql.append("ISNULL(SUM(esda_col_46),0) AS esda_col_46,ISNULL(SUM(esda_col_47),0) AS esda_col_47,ISNULL(SUM(esda_col_48),0) AS esda_col_48,");
		sql.append("ISNULL(SUM(esda_col_49),0) AS esda_col_49,ISNULL(SUM(esda_col_50),0) AS esda_col_50,ISNULL(SUM(esda_col_51),0) AS esda_col_51,");
		sql.append("ISNULL(SUM(esda_col_52),0) AS esda_col_52,ISNULL(SUM(esda_col_53),0) AS esda_col_53,ISNULL(SUM(esda_col_54),0) AS esda_col_54,");
		sql.append("ISNULL(SUM(esda_col_55),0) AS esda_col_55,ISNULL(SUM(esda_col_56),0) AS esda_col_56,ISNULL(SUM(esda_col_57),0) AS esda_col_57,");
		sql.append("ISNULL(SUM(esda_col_58),0) AS esda_col_58,ISNULL(SUM(esda_col_59),0) AS esda_col_59,ISNULL(SUM(esda_col_60),0) AS esda_col_60,");
		sql.append("ISNULL(SUM(esda_col_61),0) AS esda_col_61,ISNULL(SUM(esda_col_62),0) AS esda_col_62,ISNULL(SUM(esda_col_63),0) AS esda_col_63,");
		sql.append("ISNULL(SUM(esda_col_64),0) AS esda_col_64,ISNULL(SUM(esda_col_65),0) AS esda_col_65,ISNULL(SUM(esda_col_66),0) AS esda_col_66,");
		sql.append("ISNULL(SUM(esda_col_67),0) AS esda_col_67,ISNULL(SUM(esda_col_68),0) AS esda_col_68,ISNULL(SUM(esda_col_69),0) AS esda_col_69,");
		sql.append("ISNULL(SUM(esda_col_70),0) AS esda_col_70,ISNULL(SUM(esda_col_71),0) AS esda_col_71,ISNULL(SUM(esda_col_72),0) AS esda_col_72,");
		sql.append("ISNULL(SUM(esda_col_73),0) AS esda_col_73,ISNULL(SUM(esda_col_74),0) AS esda_col_74,ISNULL(SUM(esda_col_75),0) AS esda_col_75,");
		sql.append("ISNULL(SUM(esda_col_76),0) AS esda_col_76,ISNULL(SUM(esda_col_77),0) AS esda_col_77,ISNULL(SUM(esda_col_78),0) AS esda_col_78,");
		sql.append("ISNULL(SUM(esda_col_79),0) AS esda_col_79,ISNULL(SUM(esda_col_80),0) AS esda_col_80");
		sql.append(" FROM EmSalaryData WHERE cid=? AND ownmonth=?");
		System.out.println(sql);
		list = conn.find(sql.toString(), EmSalaryDataModel.class,
				dbconn.parseSmap(EmSalaryDataModel.class), cid, ownmonth);
		return list;
	}

	// 根据Cid及所属月份获取工资信息（）
	public List<EmSalaryDataModel> getSalaryDataByCidMonth(int cid,
			int ownmonth, String wherestr) throws Exception {
		List<EmSalaryDataModel> list = new ArrayList<EmSalaryDataModel>();
		String sql = "select eb.emba_name,ed.* from EmSalaryData ed left join EmBase eb on ed.gid=eb.gid "
				+ "where ed.cid="
				+ cid
				+ " and ed.ownmonth="
				+ ownmonth
				+ wherestr;

		try {
			System.out.println(sql);
			ResultSet rs = null;
			rs = conn.GRS(sql.toString());

			while (rs.next()) {
				list.add(new EmSalaryDataModel(rs.getInt("esda_id"), rs
						.getInt("cid"), rs.getInt("gid"),
						rs.getInt("ownmonth"), rs.getInt("esda_if_bms"), rs
								.getString("esda_addname"), rs
								.getString("esda_addtime"), rs
								.getString("esda_remark"), rs
								.getString("esda_fd_remark"), rs
								.getInt("cfin_id"),
						rs.getString("csii_itemid"), rs
								.getString("esda_o_bank"), rs
								.getString("esda_bank"), rs
								.getString("esda_ba_name"), rs
								.getString("esda_bank_account"), rs
								.getString("esda_nationality"), rs
								.getInt("esda_usage_type"), rs
								.getString("esda_email"), rs
								.getString("esda_bcc_email"), rs
								.getString("esda_email_sender"), rs
								.getInt("esda_email_state"), rs
								.getString("esda_email_date"), rs
								.getInt("esda_oof_state"), rs
								.getInt("esda_confirm_state"), rs
								.getInt("esda_payment_state"), rs
								.getString("esda_payment_date"), rs
								.getInt("esda_payment_batch"), rs
								.getInt("esda_data_type"), rs
								.getInt("esda_rp_reason"), rs
								.getInt("esda_history_state"), rs
								.getString("esda_history_date"), rs
								.getInt("esda_ttv_state"), rs
								.getString("esda_ttv_date"), rs
								.getString("esda_ttv_people"), rs
								.getBigDecimal("esda_base_radix"), rs
								.getBigDecimal("esda_a_base_radix"), rs
								.getBigDecimal("esda_a_workdays"), rs
								.getBigDecimal("esda_a_base_salary"), rs
								.getBigDecimal("esda_days"), rs
								.getBigDecimal("esda_cqdays"), rs
								.getBigDecimal("esda_b_cqdays"), rs
								.getBigDecimal("esda_qqdays"), rs
								.getBigDecimal("esda_b_qqdays"), rs
								.getBigDecimal("esda_aded"), rs
								.getBigDecimal("esda_a_aded"), rs
								.getBigDecimal("esda_base_salary"), rs
								.getBigDecimal("esda_siop"), rs
								.getBigDecimal("esda_hafop"), rs
								.getBigDecimal("esda_haf"), rs
								.getBigDecimal("esda_house_radix"), rs
								.getBigDecimal("esda_house_percent"), rs
								.getBigDecimal("esda_house_ntl"), rs
								.getBigDecimal("esda_total_pretax"), rs
								.getBigDecimal("esda_tax_base"), rs
								.getBigDecimal("esda_tax"), rs
								.getBigDecimal("esda_dc"), rs
								.getBigDecimal("esda_dc_tax"), rs
								.getBigDecimal("esda_db"), rs
								.getBigDecimal("esda_db_tax_base"), rs
								.getBigDecimal("esda_db_tax"), rs
								.getBigDecimal("esda_stock"), rs
								.getBigDecimal("esda_stock_tax"), rs
								.getBigDecimal("esda_other"), rs
								.getBigDecimal("esda_other_tax"), rs
								.getBigDecimal("esda_write_off"), rs
								.getBigDecimal("esda_house_bf"), rs
								.getBigDecimal("esda_pay"), rs
								.getBigDecimal("esda_col_1"), rs
								.getBigDecimal("esda_col_2"), rs
								.getBigDecimal("esda_col_3"), rs
								.getBigDecimal("esda_col_4"), rs
								.getBigDecimal("esda_col_5"), rs
								.getBigDecimal("esda_col_6"), rs
								.getBigDecimal("esda_col_7"), rs
								.getBigDecimal("esda_col_8"), rs
								.getBigDecimal("esda_col_9"), rs
								.getBigDecimal("esda_col_10"), rs
								.getBigDecimal("esda_col_11"), rs
								.getBigDecimal("esda_col_12"), rs
								.getBigDecimal("esda_col_13"), rs
								.getBigDecimal("esda_col_14"), rs
								.getBigDecimal("esda_col_15"), rs
								.getBigDecimal("esda_col_16"), rs
								.getBigDecimal("esda_col_17"), rs
								.getBigDecimal("esda_col_18"), rs
								.getBigDecimal("esda_col_19"), rs
								.getBigDecimal("esda_col_20"), rs
								.getBigDecimal("esda_col_21"), rs
								.getBigDecimal("esda_col_22"), rs
								.getBigDecimal("esda_col_23"), rs
								.getBigDecimal("esda_col_24"), rs
								.getBigDecimal("esda_col_25"), rs
								.getBigDecimal("esda_col_26"), rs
								.getBigDecimal("esda_col_27"), rs
								.getBigDecimal("esda_col_28"), rs
								.getBigDecimal("esda_col_29"), rs
								.getBigDecimal("esda_col_30"), rs
								.getBigDecimal("esda_col_31"), rs
								.getBigDecimal("esda_col_32"), rs
								.getBigDecimal("esda_col_33"), rs
								.getBigDecimal("esda_col_34"), rs
								.getBigDecimal("esda_col_35"), rs
								.getBigDecimal("esda_col_36"), rs
								.getBigDecimal("esda_col_37"), rs
								.getBigDecimal("esda_col_38"), rs
								.getBigDecimal("esda_col_39"), rs
								.getBigDecimal("esda_col_40"), rs
								.getBigDecimal("esda_col_41"), rs
								.getBigDecimal("esda_col_42"), rs
								.getBigDecimal("esda_col_43"), rs
								.getBigDecimal("esda_col_44"), rs
								.getBigDecimal("esda_col_45"), rs
								.getBigDecimal("esda_col_46"), rs
								.getBigDecimal("esda_col_47"), rs
								.getBigDecimal("esda_col_48"), rs
								.getBigDecimal("esda_col_49"), rs
								.getBigDecimal("esda_col_50"), rs
								.getBigDecimal("esda_col_51"), rs
								.getBigDecimal("esda_col_52"), rs
								.getBigDecimal("esda_col_53"), rs
								.getBigDecimal("esda_col_54"), rs
								.getBigDecimal("esda_col_55"), rs
								.getBigDecimal("esda_col_56"), rs
								.getBigDecimal("esda_col_57"), rs
								.getBigDecimal("esda_col_58"), rs
								.getBigDecimal("esda_col_59"), rs
								.getBigDecimal("esda_col_60"), rs
								.getBigDecimal("esda_col_61"), rs
								.getBigDecimal("esda_col_62"), rs
								.getBigDecimal("esda_col_63"), rs
								.getBigDecimal("esda_col_64"), rs
								.getBigDecimal("esda_col_65"), rs
								.getBigDecimal("esda_col_66"), rs
								.getBigDecimal("esda_col_67"), rs
								.getBigDecimal("esda_col_68"), rs
								.getBigDecimal("esda_col_69"), rs
								.getBigDecimal("esda_col_70"), rs
								.getBigDecimal("esda_col_71"), rs
								.getBigDecimal("esda_col_72"), rs
								.getBigDecimal("esda_col_73"), rs
								.getBigDecimal("esda_col_74"), rs
								.getBigDecimal("esda_col_75"), rs
								.getBigDecimal("esda_col_76"), rs
								.getBigDecimal("esda_col_77"), rs
								.getBigDecimal("esda_col_78"), rs
								.getBigDecimal("esda_col_79"), rs
								.getBigDecimal("esda_col_80"), rs
								.getString("emba_name"), rs
								.getInt("esda_ifhold")));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据查询公司表头
	public List<EmSalaryInfoModel> gecobt(String str) {
		List<EmSalaryInfoModel> embasalaryinfoList = new ArrayList<EmSalaryInfoModel>();

		try {
			ResultSet rs = getcoSalaryint(str);
			// System.out.println(rs);
			while (rs.next()) {
				// System.out.println(rs.getInt("gzje"));
				embasalaryinfoList.add(new EmSalaryInfoModel(rs.getInt("cid"),
						rs.getInt("csii_sequence"), rs.getString("csii_col"),
						rs.getString("csii_item_name")

				));
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return embasalaryinfoList;
	}

	// 更新显示状态
	public int updatevisbel(int cid, String str, int state, int type)
			throws SQLException {
		int row = 0;
		String sql = "";
		if (type == 0) {
			sql = "UPDATE CoSalaryItemInfo SET csii_csd_state=" + state
					+ " WHERE cid=" + cid + " and csii_item_name='" + str + "'";
		} else {
			sql = "UPDATE CoSalaryItemInfo SET csii_fd_state=" + state
					+ " WHERE cid=" + cid + " and csii_item_name='" + str + "'";
		}

		// System.out.println(sql);

		dbconn update = new dbconn();

		try {

			row = update.execQuery(sql);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		update.Close();

		return row;

	}

	// 获取工资所属月份列表
	public ArrayList getownmonth(String str) throws SQLException {
		ArrayList row = new ArrayList();
		String sql = "SELECT  distinct(ownmonth) ownmonth from   View_ESDa_EmBase_CoBase where 1=1  "
				+ str + " order by ownmonth desc ";

		dbconn sel = new dbconn();

		try {

			ResultSet rs = sel.GRS(sql);

			while (rs.next()) {

				row.add(rs.getInt("ownmonth"));
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return row;

	}

	// 获取所属月份里公司列表
	public ArrayList getcobases(int ownmonth) throws SQLException {
		ArrayList row = new ArrayList();
		String sql = "SELECT  cid,namespell,coba_shortspell,shortname,ownmonth from  "
				+ "View_ESDa_EmBase_CoBase where shortname is not null and ownmonth="
				+ ownmonth
				+ " group by namespell,coba_shortspell,shortname,ownmonth,cid order by coba_shortspell";
		// System.out.println(sql);
		dbconn sel = new dbconn();

		try {

			ResultSet rs = sel.GRS(sql);

			while (rs.next()) {

				row.add(rs.getString("coba_shortspell") + "|"
						+ rs.getInt("cid") + "|" + rs.getString("shortname"));
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return row;

	}

	// 获取公司当月的财务收款
	public List<CoFinanceMonthlyBillSortAccountModel> getCwReceivable(
			int ownmonth, int cid) {
		List<CoFinanceMonthlyBillSortAccountModel> list = new ArrayList<CoFinanceMonthlyBillSortAccountModel>();
		CoFinanceMonthlyBillSortAccountModel m;
		StringBuilder sql = new StringBuilder();
		sql.append("select ct.cid,cfsa_cpac_name,cfsa_Receivable=ISNULL(SUM(cfsa_Receivable),0),cfsa_PaidIn=isnull(SUM(cfsa_PaidIn),0) from CoFinanceMonthlyBillSortAccount ca ");
		sql.append("inner join CoFinanceMonthlyBill cb on ca.cfsa_cfmb_number=cb.cfmb_number ");
		sql.append("inner join CoFinanceTotalAccount ct on ct.cfta_id=cb.cfmb_cfta_id ");
		sql.append("where cb.ownmonth=? and ct.cid=? and (cfsa_cpac_name='税后工资' or cfsa_cpac_name='个调税' or cfsa_cpac_name='财务服务费') group by ct.cid,cfsa_cpac_name order by cfsa_cpac_name desc");
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setInt(1, ownmonth);
			psmt.setInt(2, cid);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new CoFinanceMonthlyBillSortAccountModel();
				m.setCfsa_cpac_name(rs.getString("cfsa_cpac_name"));
				m.setCfsa_Receivable(rs.getBigDecimal("cfsa_Receivable"));
				m.setCfsa_PaidIn(rs.getBigDecimal("cfsa_PaidIn"));
				m.sumImbalance();
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取公司信息
	public CoBaseModel getCobaseByCid(int cid) {
		CoBaseModel m = new CoBaseModel();
		String sql = "select CID, coba_shortname,coba_client,coba_gzaddname from cobase where cid=?";
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setInt(1, cid);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				m.setCid(rs.getInt("cid"));
				m.setCoba_shortname(rs.getString("coba_shortname"));
				m.setCoba_client(rs.getString("coba_client"));
				m.setCoba_gzaddname(rs.getString("coba_gzaddname"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	public LoginModel getLoginByCid(Integer cid) {
		LoginModel m = new LoginModel();
		String sql = "select a.* from login a left join cobase b on a.log_name=b.coba_client where b.cid=?";
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setInt(1, cid);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				m.setLog_id(rs.getInt("log_id"));
				m.setLog_name(rs.getString("log_name"));
				m.setDep_id(rs.getInt("dep_id"));
				m.setDep_name(rs.getString("dept"));
				m.setLog_mobile(rs.getString("log_mobile"));
				m.setLog_email(rs.getString("log_email"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	// 获取工资数据和支付模块数据(重发页面使用)
	public List<EmSalaryDataModel> getRepayData(String str) {
		List<EmSalaryDataModel> list = new ArrayList<EmSalaryDataModel>();
		EmSalaryDataModel m;
		StringBuilder sql = new StringBuilder();
		sql.append("select a.*,b.coba_shortname from (");
		sql.append("select '工资' as d_type,esda_id,ownmonth,cid,gid,esda_bank,esda_bank_account,case esda_data_type when 0 then '正常' when 1 then '少发' when 2 then '多发' when 3 then '补发' end as esda_data_typestr,case esda_usage_type when 0 then '工资' when 1 then '报销' when 2 then '住房返还' when 3 then '奖金' when 4 then '离职补偿金' when 5 then '股票分红' end as esda_usage_typestr,esda_ba_name,esda_pay,esda_payment_state,esda_payment_date,esda_oof_state from EmSalaryData where esda_oof_state=0 and esda_rp_reason is null and esda_payment_state=2 ");
		sql.append("union all ");
		sql.append("select '支付' as d_type,id,ownmonth,cid,gid,empa_bank,empa_account,empa_paytype,empa_class,empa_ba_name,empa_fee,case empa_ifpay when 1 then 2 else 0 end as empa_ifpay,empa_cashier_checkdate,0 as esda_oof_state from empay where empa_payclass='银行支付' and empa_ifpay=1 and empa_state<>0 ");
		sql.append(")a left join cobase b on a.cid=b.cid where 1=1"+str);
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new EmSalaryDataModel();
				m.setEsda_id(rs.getInt("esda_id"));
				m.setCid(rs.getInt("cid"));
				m.setGid(rs.getInt("gid"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setEsda_bank(rs.getString("esda_bank"));
				m.setEsda_bank_account(rs.getString("esda_bank_account"));
				m.setEsda_data_typestr(rs.getString("esda_data_typestr"));
				m.setEsda_usage_typestr(rs.getString("esda_usage_typestr"));
				m.setEsda_ba_name(rs.getString("esda_ba_name"));
				m.setEsda_pay(rs.getBigDecimal("esda_pay"));
				m.setCoba_shortname(rs.getString("coba_shortname"));
				m.setEsda_payment_state(rs.getInt("esda_payment_state"));
				m.setEsda_payment_date(rs.getString("esda_payment_date"));
				m.setEsda_oof_state(rs.getInt("esda_oof_state"));
				m.setD_type(rs.getString("d_type"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
