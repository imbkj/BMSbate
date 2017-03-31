package dal.CoFinanceManage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.CoFinanceDisposableModel;
import Model.CoFinanceProductModel;
import Model.EmFinanceCommissionOutDetailItemModel;
import Model.EmFinanceDisposableModel;
import Model.EmFinanceHouseGjjModel;
import Model.EmFinanceProductItemListModel;
import Model.EmFinanceProductModel;
import Model.EmFinanceSalaryModel;
import Model.EmFinanceSheBaoModel;
import Model.EmFinanceTaxModel;
import Model.EmSalaryBaseAddItemModel;
import Model.EmSalaryDataModel;
import Model.SocialInsuranceClassInfoViewModel;

public class Cfma_BussinessSelAllDal {
	private dbconn conn = new dbconn();

	// 获取社保费用
	public List<EmFinanceSheBaoModel> getShebaoList(int cid, int ownmonth) {
		List<EmFinanceSheBaoModel> list = new ArrayList<EmFinanceSheBaoModel>();
		EmFinanceSheBaoModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select efba.gid,efba.efba_emba_name,ef.* from EmFinanceSheBao ef inner join (select efba_id,gid,cid,efba_emba_name from EmFinanceBase) efba on ef.efsb_efba_id=efba.efba_id where efba.cid=? and ef.ownmonth=? and ef.efsb_state=1");
		sql.append(" union all ");
		sql.append("select efba.gid,efba.efba_emba_name,ef.* from EmFinanceSheBaoHistory ef inner join (select efba_id,gid,cid,efba_emba_name from EmFinanceBase) efba on ef.efsb_efba_id=efba.efba_id where efba.cid=? and ef.ownmonth=? and ef.efsb_state=1");
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setInt(1, cid);
			psmt.setInt(2, ownmonth);
			psmt.setInt(3, cid);
			psmt.setInt(4, ownmonth);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new EmFinanceSheBaoModel();
				m.setGid(rs.getInt("gid"));
				m.setEmba_name(rs.getString("efba_emba_name"));
				m.setEfsb_id(rs.getInt("efsb_id"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setEfsb_esiu_radix(rs.getBigDecimal("efsb_esiu_radix"));
				m.setEfsb_esiu_hj(rs.getString("efsb_esiu_hj"));
				m.setEfsb_esiu_ylcp(rs.getBigDecimal("efsb_esiu_ylcp"));
				m.setEfsb_esiu_ylop(rs.getBigDecimal("efsb_esiu_ylop"));
				m.setEfsb_esiu_jlcp(rs.getBigDecimal("efsb_esiu_jlcp"));
				m.setEfsb_esiu_jlop(rs.getBigDecimal("efsb_esiu_jlop"));
				m.setEfsb_esiu_syucp(rs.getBigDecimal("efsb_esiu_syucp"));
				m.setEfsb_esiu_syuop(rs.getBigDecimal("efsb_esiu_syuop"));
				m.setEfsb_esiu_syecp(rs.getBigDecimal("efsb_esiu_syecp"));
				m.setEfsb_esiu_syeop(rs.getBigDecimal("efsb_esiu_syeop"));
				m.setEfsb_esiu_gscp(rs.getBigDecimal("efsb_esiu_gscp"));
				m.setEfsb_esiu_gsop(rs.getBigDecimal("efsb_esiu_gsop"));
				m.setEfsb_esiu_totalcp(rs.getBigDecimal("efsb_esiu_totalcp"));
				m.setEfsb_esiu_totalop(rs.getBigDecimal("efsb_esiu_totalop"));
				m.setEfsb_totalsum(rs.getBigDecimal("efsb_totalsum"));
				m.setEfsb_receivable(rs.getBigDecimal("efsb_receivable"));
				m.setEfsb_paidin(rs.getBigDecimal("efsb_paidin"));
				m.setEfsb_payout(rs.getBigDecimal("efsb_payout"));
				m.setEfsb_iffirstpaidin(rs.getInt("efsb_iffirstpaidin"));
				m.setEfsb_state(rs.getInt("efsb_state"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取公积金费用
	public List<EmFinanceHouseGjjModel> getGjjList(int cid, int ownmonth) {
		List<EmFinanceHouseGjjModel> list = new ArrayList<EmFinanceHouseGjjModel>();
		EmFinanceHouseGjjModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select efba.gid,efba.efba_emba_name,ef.* from EmFinanceHouseGjj ef inner join (select efba_id,gid,cid,efba_emba_name from EmFinanceBase) efba on ef.efhg_efba_id=efba.efba_id where efba.cid=? and ef.ownmonth=? and ef.efhg_state=1");
		sql.append(" union all ");
		sql.append("select efba.gid,efba.efba_emba_name,ef.* from EmFinanceHouseGjjHistory ef inner join (select efba_id,gid,cid,efba_emba_name from EmFinanceBase) efba on ef.efhg_efba_id=efba.efba_id where efba.cid=? and ef.ownmonth=? and ef.efhg_state=1");
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setInt(1, cid);
			psmt.setInt(2, ownmonth);
			psmt.setInt(3, cid);
			psmt.setInt(4, ownmonth);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new EmFinanceHouseGjjModel();
				m.setGid(rs.getInt("gid"));
				m.setEmba_name(rs.getString("efba_emba_name"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setEfhg_id(rs.getInt("efhg_id"));
				m.setEfhg_efba_id(rs.getInt("efhg_efba_id"));
				m.setEfhg_coco_id(rs.getInt("efhg_coco_id"));
				m.setEfhg_cfmb_number(rs.getString("efhg_cfmb_number"));
				m.setEfhg_emhu_hj(rs.getString("efhg_emhu_hj"));
				m.setEfhg_emhu_radix(rs.getBigDecimal("efhg_emhu_radix"));
				m.setEfhg_emhu_cpp(rs.getBigDecimal("efhg_emhu_cpp"));
				m.setEfhg_emhu_opp(rs.getBigDecimal("efhg_emhu_opp"));
				m.setEfhg_emhu_cp(rs.getBigDecimal("efhg_emhu_cp"));
				m.setEfhg_emhu_op(rs.getBigDecimal("efhg_emhu_op"));
				m.setEfhg_emhu_total(rs.getBigDecimal("efhg_emhu_total"));
				m.setEfhg_receivable(rs.getBigDecimal("efhg_receivable"));
				m.setEfhg_paidin(rs.getBigDecimal("efhg_paidin"));
				m.setEfhg_payout(rs.getBigDecimal("efhg_payout"));
				m.setEfhg_addtime(rs.getString("efhg_addtime"));
				m.setEfhg_state(rs.getInt("efhg_state"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 按账单号获取个税费用
	public List<EmFinanceTaxModel> getTaxList(int cid, int ownmonth) {
		List<EmFinanceTaxModel> list = new ArrayList<EmFinanceTaxModel>();
		EmFinanceTaxModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select ef.*,efba.efba_emba_name from EmFinanceTax ef inner join (select efba_id,gid,cid,efba_emba_name from EmFinanceBase) efba on ef.efta_efba_id=efba.efba_id where efba.cid=? and ef.ownmonth=? and ef.efta_state=1");
		sql.append(" union all ");
		sql.append("select ef.*,efba.efba_emba_name from EmFinanceTaxHistory ef inner join (select efba_id,gid,cid,efba_emba_name from EmFinanceBase) efba on ef.efta_efba_id=efba.efba_id where efba.cid=? and ef.ownmonth=? and ef.efta_state=1");
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setInt(1, cid);
			psmt.setInt(2, ownmonth);
			psmt.setInt(3, cid);
			psmt.setInt(4, ownmonth);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new EmFinanceTaxModel();
				m.setGid(rs.getInt("gid"));
				m.setEmba_name(rs.getString("efba_emba_name"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setEfta_coco_id(rs.getInt("efta_coco_id"));
				m.setEfta_cfmb_number(rs.getString("efta_cfmb_number"));
				m.setEfta_id(rs.getInt("efta_id"));
				m.setEfta_receivable(rs.getBigDecimal("efta_receivable"));
				m.setEfta_tax(rs.getBigDecimal("efta_tax"));
				m.setEfta_tax_base(rs.getBigDecimal("efta_tax_base"));
				m.setEfta_tax_class(rs.getInt("efta_tax_class"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取工资费用
	public List<EmFinanceSalaryModel> getSalaryList(int cid, int ownmonth) {
		List<EmFinanceSalaryModel> list = new ArrayList<EmFinanceSalaryModel>();
		EmFinanceSalaryModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select efba.gid,efba.efba_emba_name,ef.* from EmFinanceSalary ef inner join (select efba_id,gid,cid,efba_emba_name from EmFinanceBase) efba on ef.efsa_efba_id=efba.efba_id where efba.cid=? and ef.ownmonth=? and ef.efsa_state=1");
		sql.append(" union all ");
		sql.append("select efba.gid,efba.efba_emba_name,ef.* from EmFinanceSalaryHistory ef inner join (select efba_id,gid,cid,efba_emba_name from EmFinanceBase) efba on ef.efsa_efba_id=efba.efba_id where efba.cid=? and ef.ownmonth=? and ef.efsa_state=1");
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setInt(1, cid);
			psmt.setInt(2, ownmonth);
			psmt.setInt(3, cid);
			psmt.setInt(4, ownmonth);
			ResultSet rs = psmt.executeQuery();
			List<EmSalaryBaseAddItemModel> itemList = getItemInfoByCidMonth(
					cid, ownmonth);
			while (rs.next()) {
				m = new EmFinanceSalaryModel();
				m.setGid(rs.getInt("gid"));
				m.setEmba_name(rs.getString("efba_emba_name"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setEfsa_id(rs.getInt("efsa_id"));
				m.setEfsa_efba_id(rs.getInt("efsa_efba_id"));
				m.setEfsa_coco_id(rs.getInt("efsa_coco_id"));
				m.setEfsa_cfmb_number(rs.getString("efsa_cfmb_number"));
				m.setEfsa_esda_id(rs.getInt("efsa_esda_id"));
				m.setEfsa_esda_pay(rs.getBigDecimal("efsa_esda_pay"));
				m.setEfsa_esda_tax(rs.getBigDecimal("efsa_esda_tax"));
				m.setEfsa_esda_db(rs.getBigDecimal("efsa_esda_db"));
				m.setEfsa_esda_db_tax(rs.getBigDecimal("efsa_esda_db_tax"));
				m.setEfsa_receivable(rs.getBigDecimal("efsa_receivable"));
				m.setEfsa_paidin(rs.getBigDecimal("efsa_paidin"));
				m.setEfsa_payout(rs.getBigDecimal("efsa_payout"));
				m.setEfsa_iffirstpaidin(rs.getInt("efsa_iffirstpaidin"));
				m.setEfsa_addtime(rs.getString("efsa_addtime"));
				m.setEfsa_finalcheck(rs.getInt("efsa_finalcheck"));
				m.setEfsa_finalchecktime(rs.getString("efsa_finalchecktime"));
				m.setEfsa_state(rs.getInt("efsa_state"));
				m.setEmsdModel(getSalaryDataByIdList(m.getEfsa_esda_id()));
				m.getEmsdModel().setItemList(itemList);
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据Cid及所属月份获取工资项目信息
	private List<EmSalaryBaseAddItemModel> getItemInfoByCidMonth(int cid,
			int ownmonth) {
		List<EmSalaryBaseAddItemModel> list = new ArrayList<EmSalaryBaseAddItemModel>();
		EmSalaryBaseAddItemModel m = null;
		String sql = "select csii_col,csii_item_name,csii_fd_state,chk_cfd from CSII_CFDa_V WHERE  cid=? AND ownmonth=? and csii_fd_state=1  ORDER BY csii_csd_state desc,csii_sequence";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, cid);
			psmt.setInt(2, ownmonth);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new EmSalaryBaseAddItemModel();
				m.setCsii_col(rs.getString("csii_col"));
				m.setCsii_item_name(rs.getString("csii_item_name"));
				m.setCsii_fd_state(rs.getInt("csii_fd_state"));
				m.setCfda_id(rs.getInt("chk_cfd"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据主键获取工资信息
	private EmSalaryDataModel getSalaryDataByIdList(int esbaId) {
		EmSalaryDataModel m = new EmSalaryDataModel();
		String sql = "select ei.cfin_id as cfinid,ed.* from EmSalaryData ed left join EmSalaryInfo ei on ed.gid=ei.gid and ed.cid=ei.cid  where esda_id=?";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, esbaId);
			ResultSet rs = psmt.executeQuery();
			rs.next();
			m.setEsda_id(rs.getInt("esda_id"));
			m.setCid(rs.getInt("cid"));
			m.setGid(rs.getInt("gid"));
			m.setOwnmonth(rs.getInt("ownmonth"));
			m.setEsda_if_bms(rs.getInt("esda_if_bms"));
			m.setEsda_addname(rs.getString("esda_addname"));
			m.setEsda_addtime(rs.getString("esda_addtime"));
			m.setEsda_remark(rs.getString("esda_remark"));
			m.setEsda_fd_remark(rs.getString("esda_fd_remark"));
			m.setCfin_id(rs.getInt("cfin_id"));
			m.setCsii_itemid(rs.getString("csii_itemid"));
			m.setEsda_o_bank(rs.getString("esda_o_bank"));
			m.setEsda_bank(rs.getString("esda_bank"));
			m.setEsda_ba_name(rs.getString("esda_ba_name"));
			m.setEsda_bank_account(rs.getString("esda_bank_account"));
			m.setEsda_nationality(rs.getString("esda_nationality"));
			m.setEsda_usage_type(rs.getInt("esda_usage_type"));
			m.setEsda_email(rs.getString("esda_email"));
			m.setEsda_bcc_email(rs.getString("esda_bcc_email"));
			m.setEsda_email_sender(rs.getString("esda_email_sender"));
			m.setEsda_email_state(rs.getInt("esda_email_state"));
			m.setEsda_email_date(rs.getString("esda_email_date"));
			m.setEsda_oof_state(rs.getInt("esda_oof_state"));
			m.setEsda_confirm_state(rs.getInt("esda_confirm_state"));
			m.setEsda_ifhold(rs.getInt("esda_ifhold"));
			m.setEsda_payment_state(rs.getInt("esda_payment_state"));
			m.setEsda_payment_date(rs.getString("esda_payment_date"));
			m.setEsda_payment_batch(rs.getInt("esda_payment_batch"));
			m.setEsda_data_type(rs.getInt("esda_data_type"));
			m.setEsda_rp_reason(rs.getInt("esda_rp_reason"));
			m.setEsda_history_state(rs.getInt("esda_history_state"));
			m.setEsda_history_date(rs.getString("esda_history_date"));
			m.setEsda_ttv_state(rs.getInt("esda_ttv_state"));
			m.setEsda_ttv_date(rs.getString("esda_ttv_date"));
			m.setEsda_ttv_people(rs.getString("esda_ttv_people"));
			m.setEsda_base_radix(rs.getBigDecimal("esda_base_radix"));
			m.setEsda_a_base_radix(rs.getBigDecimal("esda_a_base_radix"));
			m.setEsda_a_workdays(rs.getBigDecimal("esda_a_workdays"));
			m.setEsda_a_base_salary(rs.getBigDecimal("esda_a_base_salary"));
			m.setEsda_days(rs.getBigDecimal("esda_days"));
			m.setEsda_cqdays(rs.getBigDecimal("esda_cqdays"));
			m.setEsda_b_cqdays(rs.getBigDecimal("esda_b_cqdays"));
			m.setEsda_qqdays(rs.getBigDecimal("esda_qqdays"));
			m.setEsda_b_qqdays(rs.getBigDecimal("esda_b_qqdays"));
			m.setEsda_aded(rs.getBigDecimal("esda_aded"));
			m.setEsda_a_aded(rs.getBigDecimal("esda_a_aded"));
			m.setEsda_base_salary(rs.getBigDecimal("esda_base_salary"));
			m.setEsda_siop(rs.getBigDecimal("esda_siop"));
			m.setEsda_hafop(rs.getBigDecimal("esda_hafop"));
			m.setEsda_haf(rs.getBigDecimal("esda_haf"));
			m.setEsda_house_radix(rs.getBigDecimal("esda_house_radix"));
			m.setEsda_house_percent(rs.getBigDecimal("esda_house_percent"));
			m.setEsda_house_ntl(rs.getBigDecimal("esda_house_ntl"));
			m.setEsda_total_pretax(rs.getBigDecimal("esda_total_pretax"));
			m.setEsda_tax_base(rs.getBigDecimal("esda_tax_base"));
			m.setEsda_tax(rs.getBigDecimal("esda_tax"));
			m.setEsda_lw_tax_base(rs.getBigDecimal("esda_lw_tax_base"));
			m.setEsda_lw_tax(rs.getBigDecimal("esda_lw_tax"));
			m.setEsda_dc(rs.getBigDecimal("esda_dc"));
			m.setEsda_dc_tax(rs.getBigDecimal("esda_dc_tax"));
			m.setEsda_db(rs.getBigDecimal("esda_db"));
			m.setEsda_db_tax_base(rs.getBigDecimal("esda_db_tax_base"));
			m.setEsda_db_tax(rs.getBigDecimal("esda_db_tax"));
			m.setEsda_stock(rs.getBigDecimal("esda_stock"));
			m.setEsda_stock_tax(rs.getBigDecimal("esda_stock_tax"));
			m.setEsda_other(rs.getBigDecimal("esda_other"));
			m.setEsda_other_tax(rs.getBigDecimal("esda_other_tax"));
			m.setEsda_write_off(rs.getBigDecimal("esda_write_off"));
			m.setEsda_house_bf(rs.getBigDecimal("esda_house_bf"));
			m.setEsda_pay(rs.getBigDecimal("esda_pay"));
			m.setEsda_col_1(rs.getBigDecimal("esda_col_1"));
			m.setEsda_col_2(rs.getBigDecimal("esda_col_2"));
			m.setEsda_col_3(rs.getBigDecimal("esda_col_3"));
			m.setEsda_col_4(rs.getBigDecimal("esda_col_4"));
			m.setEsda_col_5(rs.getBigDecimal("esda_col_5"));
			m.setEsda_col_6(rs.getBigDecimal("esda_col_6"));
			m.setEsda_col_7(rs.getBigDecimal("esda_col_7"));
			m.setEsda_col_8(rs.getBigDecimal("esda_col_8"));
			m.setEsda_col_9(rs.getBigDecimal("esda_col_9"));
			m.setEsda_col_10(rs.getBigDecimal("esda_col_10"));
			m.setEsda_col_11(rs.getBigDecimal("esda_col_11"));
			m.setEsda_col_12(rs.getBigDecimal("esda_col_12"));
			m.setEsda_col_13(rs.getBigDecimal("esda_col_13"));
			m.setEsda_col_14(rs.getBigDecimal("esda_col_14"));
			m.setEsda_col_15(rs.getBigDecimal("esda_col_15"));
			m.setEsda_col_16(rs.getBigDecimal("esda_col_16"));
			m.setEsda_col_17(rs.getBigDecimal("esda_col_17"));
			m.setEsda_col_18(rs.getBigDecimal("esda_col_18"));
			m.setEsda_col_19(rs.getBigDecimal("esda_col_19"));
			m.setEsda_col_20(rs.getBigDecimal("esda_col_20"));
			m.setEsda_col_21(rs.getBigDecimal("esda_col_21"));
			m.setEsda_col_22(rs.getBigDecimal("esda_col_22"));
			m.setEsda_col_23(rs.getBigDecimal("esda_col_23"));
			m.setEsda_col_24(rs.getBigDecimal("esda_col_24"));
			m.setEsda_col_25(rs.getBigDecimal("esda_col_25"));
			m.setEsda_col_26(rs.getBigDecimal("esda_col_26"));
			m.setEsda_col_27(rs.getBigDecimal("esda_col_27"));
			m.setEsda_col_28(rs.getBigDecimal("esda_col_28"));
			m.setEsda_col_29(rs.getBigDecimal("esda_col_29"));
			m.setEsda_col_30(rs.getBigDecimal("esda_col_30"));
			m.setEsda_col_31(rs.getBigDecimal("esda_col_31"));
			m.setEsda_col_32(rs.getBigDecimal("esda_col_32"));
			m.setEsda_col_33(rs.getBigDecimal("esda_col_33"));
			m.setEsda_col_34(rs.getBigDecimal("esda_col_34"));
			m.setEsda_col_35(rs.getBigDecimal("esda_col_35"));
			m.setEsda_col_36(rs.getBigDecimal("esda_col_36"));
			m.setEsda_col_37(rs.getBigDecimal("esda_col_37"));
			m.setEsda_col_38(rs.getBigDecimal("esda_col_38"));
			m.setEsda_col_39(rs.getBigDecimal("esda_col_39"));
			m.setEsda_col_40(rs.getBigDecimal("esda_col_40"));
			m.setEsda_col_41(rs.getBigDecimal("esda_col_41"));
			m.setEsda_col_42(rs.getBigDecimal("esda_col_42"));
			m.setEsda_col_43(rs.getBigDecimal("esda_col_43"));
			m.setEsda_col_44(rs.getBigDecimal("esda_col_44"));
			m.setEsda_col_45(rs.getBigDecimal("esda_col_45"));
			m.setEsda_col_46(rs.getBigDecimal("esda_col_46"));
			m.setEsda_col_47(rs.getBigDecimal("esda_col_47"));
			m.setEsda_col_48(rs.getBigDecimal("esda_col_48"));
			m.setEsda_col_49(rs.getBigDecimal("esda_col_49"));
			m.setEsda_col_50(rs.getBigDecimal("esda_col_50"));
			m.setEsda_col_51(rs.getBigDecimal("esda_col_51"));
			m.setEsda_col_52(rs.getBigDecimal("esda_col_52"));
			m.setEsda_col_53(rs.getBigDecimal("esda_col_53"));
			m.setEsda_col_54(rs.getBigDecimal("esda_col_54"));
			m.setEsda_col_55(rs.getBigDecimal("esda_col_55"));
			m.setEsda_col_56(rs.getBigDecimal("esda_col_56"));
			m.setEsda_col_57(rs.getBigDecimal("esda_col_57"));
			m.setEsda_col_58(rs.getBigDecimal("esda_col_58"));
			m.setEsda_col_59(rs.getBigDecimal("esda_col_59"));
			m.setEsda_col_60(rs.getBigDecimal("esda_col_60"));
			m.setEsda_col_61(rs.getBigDecimal("esda_col_61"));
			m.setEsda_col_62(rs.getBigDecimal("esda_col_62"));
			m.setEsda_col_63(rs.getBigDecimal("esda_col_63"));
			m.setEsda_col_64(rs.getBigDecimal("esda_col_64"));
			m.setEsda_col_65(rs.getBigDecimal("esda_col_65"));
			m.setEsda_col_66(rs.getBigDecimal("esda_col_66"));
			m.setEsda_col_67(rs.getBigDecimal("esda_col_67"));
			m.setEsda_col_68(rs.getBigDecimal("esda_col_68"));
			m.setEsda_col_69(rs.getBigDecimal("esda_col_69"));
			m.setEsda_col_70(rs.getBigDecimal("esda_col_70"));
			m.setEsda_col_71(rs.getBigDecimal("esda_col_71"));
			m.setEsda_col_72(rs.getBigDecimal("esda_col_72"));
			m.setEsda_col_73(rs.getBigDecimal("esda_col_73"));
			m.setEsda_col_74(rs.getBigDecimal("esda_col_74"));
			m.setEsda_col_75(rs.getBigDecimal("esda_col_75"));
			m.setEsda_col_76(rs.getBigDecimal("esda_col_76"));
			m.setEsda_col_77(rs.getBigDecimal("esda_col_77"));
			m.setEsda_col_78(rs.getBigDecimal("esda_col_78"));
			m.setEsda_col_79(rs.getBigDecimal("esda_col_79"));
			m.setEsda_col_80(rs.getBigDecimal("esda_col_80"));
			m.setEsda_total_adjtax(rs.getBigDecimal("esda_total_adjtax"));
			m.setEsda_total_afttax(rs.getBigDecimal("esda_total_afttax"));
			m.setEsda_hpro(rs.getString("esda_hpro"));
			m.setEsda_taxplace(rs.getString("esda_taxplace"));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	// 获取公司福利费用
	public List<CoFinanceProductModel> getCoProductList(int cid, int ownmonth) {
		List<CoFinanceProductModel> list = new ArrayList<CoFinanceProductModel>();
		CoFinanceProductModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select cfpr_copr_name,cfpr_Receivable from CoFinanceProduct where cfpr_state=1 and cid=? and ownmonth=?");
		sql.append(" union all ");
		sql.append("select cfpr_copr_name,cfpr_Receivable from CoFinanceProductHistory where cfpr_state=1 and cid=? and ownmonth=?");
		sql.append(" order by cfpr_copr_name");
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setInt(1, cid);
			psmt.setInt(2, ownmonth);
			psmt.setInt(3, cid);
			psmt.setInt(4, ownmonth);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new CoFinanceProductModel();
				m.setCfpr_copr_name(rs.getString("cfpr_copr_name"));
				m.setCfpr_Receivable(rs.getBigDecimal("cfpr_Receivable"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取员工福利费用
	public List<EmFinanceProductModel> getEmProductList(int cid, int ownmonth) {
		List<EmFinanceProductModel> list = new ArrayList<EmFinanceProductModel>();
		EmFinanceProductModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select ef.efpr_id,efba.gid,efba.efba_emba_name,ef.efpr_cpac_name,ef.efpr_copr_name,ef.efpr_Receivable from EmFinanceProduct ef inner join (select efba_id,gid,cid,efba_emba_name from EmFinanceBase) efba on ef.efpr_efba_id=efba.efba_id where efba.cid=? and ef.ownmonth=? and ef.efpr_state=1");
		sql.append(" union all ");
		sql.append("select ef.efpr_id,efba.gid,efba.efba_emba_name,ef.efpr_cpac_name,ef.efpr_copr_name,ef.efpr_Receivable from EmFinanceProductHistory ef inner join (select efba_id,gid,cid,efba_emba_name from EmFinanceBase) efba on ef.efpr_efba_id=efba.efba_id where efba.cid=? and ef.ownmonth=? and ef.efpr_state=1");
		sql.append(" order by efba.gid");
		PreparedStatement psmt = conn.getpre(sql.toString());
		List<EmFinanceProductItemListModel> itemList = getEmProductItemList(
				cid, ownmonth);
		try {
			psmt.setInt(1, cid);
			psmt.setInt(2, ownmonth);
			psmt.setInt(3, cid);
			psmt.setInt(4, ownmonth);
			ResultSet rs = psmt.executeQuery();
			int gid = 0;
			while (rs.next()) {
				if (rs.getInt("gid") != gid) {
					m = new EmFinanceProductModel();
					m.setGid(rs.getInt("gid"));
					m.setEmba_name(rs.getString("efba_emba_name"));
					m.insertItemList(itemList);
					m.insertReceivableToItemList(
							rs.getString("efpr_cpac_name"),
							rs.getString("efpr_copr_name"),
							rs.getBigDecimal("efpr_Receivable"));
					m.setSumReceivable(m.getSumReceivable().add(
							rs.getBigDecimal("efpr_Receivable")));
					gid = rs.getInt("gid");
					list.add(m);
				} else {
					m.insertReceivableToItemList(
							rs.getString("efpr_cpac_name"),
							rs.getString("efpr_copr_name"),
							rs.getBigDecimal("efpr_Receivable"));
					m.setSumReceivable(m.getSumReceivable().add(
							rs.getBigDecimal("efpr_Receivable")));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取员工福利项目
	public List<EmFinanceProductItemListModel> getEmProductItemList(int cid,
			int ownmonth) {
		List<EmFinanceProductItemListModel> list = new ArrayList<EmFinanceProductItemListModel>();
		EmFinanceProductItemListModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct efpr_cpac_name,efpr_copr_name from EmFinanceProduct ef  inner join (select efba_id,cid from EmFinanceBase) efba on ef.efpr_efba_id=efba.efba_id where  efba.cid=? and ef.ownmonth=? and ef.efpr_state=1");
		sql.append(" union all ");
		sql.append("select distinct efpr_cpac_name,efpr_copr_name from EmFinanceProductHistory ef  inner join (select efba_id,cid from EmFinanceBase) efba on ef.efpr_efba_id=efba.efba_id where  efba.cid=? and ef.ownmonth=? and ef.efpr_state=1");
		sql.append(" order by efpr_cpac_name,efpr_copr_name");
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setInt(1, cid);
			psmt.setInt(2, ownmonth);
			psmt.setInt(3, cid);
			psmt.setInt(4, ownmonth);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new EmFinanceProductItemListModel();
				m.setClassName(rs.getString("efpr_cpac_name"));
				m.setName(rs.getString("efpr_copr_name"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取委托出费用
	public ResultSet getEmFinanceCommissionOutRs(int cid, int ownmonth) {
		try {
			ResultSet rs = conn.GRS("exec EmFinanceCommissionOutPageSel_p_lwj "
					+ cid + "," + ownmonth + "," + "'-1'");
			return rs;
		} catch (Exception e) {
			return null;
		}
	}

	// 获取委托出明细项目
	public List<EmFinanceCommissionOutDetailItemModel> getEmCommissionOutDetailItem(
			int cid, int ownmonth, String city) {
		List<EmFinanceCommissionOutDetailItemModel> list = new ArrayList<EmFinanceCommissionOutDetailItemModel>();
		EmFinanceCommissionOutDetailItemModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct efcd.efcd_eofd_name,efcd_eofd_sicl_id from EmFinanceCommissionOutDetail efcd ");
		sql.append(" left join EmFinanceCommissionOut ef on efcd.efcd_efco_id=ef.efco_id and ef.efco_state=1");
		sql.append(" left join EmFinanceBase efba on ef.efco_efba_id=efba.efba_id");
		sql.append(" left join CoAgencyBaseCityRel cabc on ef.efco_cabc_id=cabc.cabc_id");
		sql.append(" left join PubProCity ppc on cabc.cabc_ppc_id=ppc.id");
		sql.append(" where efcd.efco_state=1 and efba.cid=? and ef.ownmonth=? and ppc.name=?");
		sql.append(" union all ");
		sql.append("select distinct efcd.efcd_eofd_name,efcd_eofd_sicl_id from EmFinanceCommissionOutDetailHistory efcd ");
		sql.append(" left join EmFinanceCommissionOutHistory ef on efcd.efcd_efco_id=ef.efco_id and ef.efco_state=1");
		sql.append(" left join EmFinanceBase efba on ef.efco_efba_id=efba.efba_id");
		sql.append(" left join CoAgencyBaseCityRel cabc on ef.efco_cabc_id=cabc.cabc_id");
		sql.append(" left join PubProCity ppc on cabc.cabc_ppc_id=ppc.id");
		sql.append(" where efcd.efco_state=1 and efba.cid=? and ef.ownmonth=? and ppc.name=?");
		sql.append(" order by efcd.efcd_eofd_sicl_id");
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setInt(1, cid);
			psmt.setInt(2, ownmonth);
			psmt.setString(3, city);
			psmt.setInt(4, cid);
			psmt.setInt(5, ownmonth);
			psmt.setString(6, city);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new EmFinanceCommissionOutDetailItemModel();
				m.setItem(rs.getString("efcd_eofd_name"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取委托出福利项目
	public List<EmFinanceCommissionOutDetailItemModel> getEmCommissionOutProductItem(
			int cid, int ownmonth, String city) {
		List<EmFinanceCommissionOutDetailItemModel> list = new ArrayList<EmFinanceCommissionOutDetailItemModel>();
		EmFinanceCommissionOutDetailItemModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct efcp.efcp_copr_name from EmFinanceCommissionOutProduct efcp ");
		sql.append(" left join EmFinanceCommissionOut ef on efcp.efcp_efco_id=ef.efco_id and ef.efco_state=1");
		sql.append(" left join EmFinanceBase efba on ef.efco_efba_id=efba.efba_id");
		sql.append(" left join CoAgencyBaseCityRel cabc on ef.efco_cabc_id=cabc.cabc_id");
		sql.append(" left join PubProCity ppc on cabc.cabc_ppc_id=ppc.id");
		sql.append(" where efcp.efcp_state=1 and efba.cid=? and ef.ownmonth=? and ppc.name=?");
		sql.append(" union all ");
		sql.append("select distinct efcp.efcp_copr_name from EmFinanceCommissionOutProductHistory efcp ");
		sql.append(" left join EmFinanceCommissionOutHistory ef on efcp.efcp_efco_id=ef.efco_id and ef.efco_state=1");
		sql.append(" left join EmFinanceBase efba on ef.efco_efba_id=efba.efba_id");
		sql.append(" left join CoAgencyBaseCityRel cabc on ef.efco_cabc_id=cabc.cabc_id");
		sql.append(" left join PubProCity ppc on cabc.cabc_ppc_id=ppc.id");
		sql.append(" where efcp.efcp_state=1 and efba.cid=? and ef.ownmonth=? and ppc.name=?");
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setInt(1, cid);
			psmt.setInt(2, ownmonth);
			psmt.setString(3, city);
			psmt.setInt(4, cid);
			psmt.setInt(5, ownmonth);
			psmt.setString(6, city);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new EmFinanceCommissionOutDetailItemModel();
				m.setItem(rs.getString("efcp_copr_name"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取委托出数据的社保标准
	public List<SocialInsuranceClassInfoViewModel> getEmCommissionOutSocialInsurance(
			int cid, int ownmonth) {
		List<SocialInsuranceClassInfoViewModel> list = new ArrayList<SocialInsuranceClassInfoViewModel>();
		SocialInsuranceClassInfoViewModel m = null;
		try {
			ResultSet rs = conn
					.GRS("exec EmFinanceCommissionOutSocialInsurance_p_lwj "
							+ "'-1'," + cid + "," + ownmonth);

			while (rs.next()) {
				m = new SocialInsuranceClassInfoViewModel();
				m.setSoin_id(rs.getInt("soin_id"));
				m.setSoin_title(rs.getString("soin_title"));
				m.setCity(rs.getString("city"));
				m.setSicl_name(rs.getString("sicl_name"));
				m.setSicl_payunit(rs.getString("sicl_payunit"));
				m.setSiai_proportion(rs.getString("siai_proportion"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取员工非标
	public List<EmFinanceDisposableModel> getEmDisposableList(int cid,
			int ownmonth) {
		List<EmFinanceDisposableModel> list = new ArrayList<EmFinanceDisposableModel>();
		EmFinanceDisposableModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select efba.gid,efba.efba_emba_name,ef.efdi_cfmb_number,ef.efdi_Reason,ef.efdi_Receivable from EmFinanceDisposable ef inner join (select efba_id,gid,cid,efba_emba_name from EmFinanceBase) efba on ef.efdi_efba_id=efba.efba_id where efba.cid=? and ef.ownmonth=? and ef.efdi_state=1");
		sql.append(" union all ");
		sql.append("select efba.gid,efba.efba_emba_name,ef.efdi_cfmb_number,ef.efdi_Reason,ef.efdi_Receivable from EmFinanceDisposableHistory ef inner join (select efba_id,gid,cid,efba_emba_name from EmFinanceBase) efba on ef.efdi_efba_id=efba.efba_id where efba.cid=? and ef.ownmonth=? and ef.efdi_state=1");
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setInt(1, cid);
			psmt.setInt(2, ownmonth);
			psmt.setInt(3, cid);
			psmt.setInt(4, ownmonth);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new EmFinanceDisposableModel();
				m.setGid(rs.getInt("gid"));
				m.setEmba_name(rs.getString("efba_emba_name"));
				m.setEfdi_Reason(rs.getString("efdi_Reason"));
				m.setEfdi_Receivable(rs.getBigDecimal("efdi_Receivable"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取公司非标
	public List<CoFinanceDisposableModel> getCoDisposableList(int cid,
			int ownmonth) {
		List<CoFinanceDisposableModel> list = new ArrayList<CoFinanceDisposableModel>();
		CoFinanceDisposableModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select cfdi_copr_name,cfdi_Reason,cfdi_Receivable from CoFinanceDisposable where cfdi_state=1 and cid=? and ownmonth=?");
		sql.append(" union all ");
		sql.append("select cfdi_copr_name,cfdi_Reason,cfdi_Receivable from CoFinanceDisposableHistory where cfdi_state=1 and cid=? and ownmonth=?");
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setInt(1, cid);
			psmt.setInt(2, ownmonth);
			psmt.setInt(3, cid);
			psmt.setInt(4, ownmonth);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new CoFinanceDisposableModel();
				m.setCfdi_copr_name(rs.getString("cfdi_copr_name"));
				m.setCfdi_Reason(rs.getString("cfdi_Reason"));
				m.setCfdi_Receivable(rs.getBigDecimal("cfdi_Receivable"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
