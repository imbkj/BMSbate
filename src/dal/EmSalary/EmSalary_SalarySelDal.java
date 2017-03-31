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
import Model.EmSalaryBaseAddItemModel;
import Model.EmSalaryBaseAdd_viewModel;
import Model.EmSalaryBaseSel_viewModel;
import Model.EmSalaryDataModel;
import Model.EmSalaryInfoModel;
import Model.EmSalaryPayInfoModel;
import Model.EmbaseModel;
import Model.PubCodeConversionModel;
import Util.UserInfo;

public class EmSalary_SalarySelDal {
	private static dbconn conn = new dbconn();

	// 获取工资用途的数据列表
	public List<PubCodeConversionModel> getCodeConversion() {
		List<PubCodeConversionModel> list = new ArrayList<PubCodeConversionModel>();
		PubCodeConversionModel m = null;
		String sql = "SELECT pcco_cn,pcco_code FROM PubCodeConversion WHERE pucl_id=5 AND pcco_name='用途'";
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new PubCodeConversionModel();
				m.setPcco_cn(rs.getString("pcco_cn"));
				m.setPcco_code(rs.getString("pcco_code"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取员工信息
	public EmSalaryBaseAdd_viewModel getEmSalaryDateAdd(int gid, int cid) {
		EmSalaryBaseAdd_viewModel m = new EmSalaryBaseAdd_viewModel();
		String sql = "select * from EmSalaryBaseAdd_view where gid=? and cid=?";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, gid);
			psmt.setInt(2, cid);
			ResultSet rs = psmt.executeQuery();
			rs.next();
			m.setCoba_shortname(rs.getString("coba_shortname"));
			m.setCid(rs.getInt("cid"));
			m.setEmba_name(rs.getString("emba_name"));
			m.setGid(rs.getInt("gid"));
			m.setEmba_Nationality(rs.getString("emba_Nationality"));
			m.setEmba_gz_account(rs.getString("emba_gz_account"));
			m.setEmba_gz_bank(rs.getString("emba_gz_bank"));
			m.setEmba_house_account(rs.getString("emba_house_account"));
			m.setEmba_house_bank(rs.getString("emba_house_bank"));
			m.setEmba_writeoff_account(rs.getString("emba_writeoff_account"));
			m.setEmba_writeoff_bank(rs.getString("emba_writeoff_bank"));
			m.setEsda_ba_name(rs.getString("esda_ba_name"));
			m.setCfin_name(rs.getString("cfin_name"));
			m.setCfin_id(rs.getInt("cfin_id"));
			m.setCsii_itemid(rs.getString("csii_itemid"));
			m.setEsda_cou(rs.getInt("esda_cou"));
			m.setEsin_hpro(rs.getString("esin_hpro"));
			m.setEsin_taxplace(rs.getString("esin_taxplace"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return m;
	}

	// 获取可批量新增工资的员工信息(既无工资数据并已分配算法及报价单的员工)
	public List<EmSalaryDataModel> getEmSalaryDateBase(int cid) {
		List<EmSalaryDataModel> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("select emba_name,gid,cid,cfin_id,csii_itemid,emba_gz_bank,emba_gz_account,esda_ba_name,Emba_nationality,emba_state from EmSalaryBaseAdd_view ev ");
		sql.append("where cid=? and esda_cou=0 and emba_state in (0,1,2,5) and cfin_id is not null and cfin_id!=0 ");
		sql.append("and (select COUNT(*)c from CoGList cogl ");
		sql.append(" inner join CoOfferList coli on cogl.cgli_coli_id=coli.coli_id ");
		sql.append(" where coli.coli_pclass='财税服务' ");
		sql.append(" and gid=ev.gid)>0");
		sql.append(" order by emba_state,emba_name desc");
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setInt(1, cid);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				EmSalaryDataModel m = new EmSalaryDataModel();
				m.setGid(rs.getInt("gid"));
				m.setCid(rs.getInt("cid"));
				m.setEsda_if_bms(1);
				m.setEsda_oof_state(0);
				m.setEsda_data_type(0);
				m.setEsda_usage_type(0);
				m.setEsda_payment_state(3);
				m.setCfin_id(rs.getInt("cfin_id"));
				m.setCsii_itemid(rs.getString("csii_itemid"));
				m.setEsda_bank(rs.getString("emba_gz_bank"));
				m.setEsda_bank_account(rs.getString("emba_gz_account"));
				m.setEsda_ba_name(rs.getString("esda_ba_name"));
				m.setEsda_nationality(rs.getString("Emba_nationality"));
				m.setName(rs.getString("emba_name"));
				m.setEmba_state(rs.getInt("emba_state"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取可批量导入工资的员工信息
	public List<EmSalaryDataModel> getEmSalaryBase(int cid) {
		List<EmSalaryDataModel> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("select emba_name,gid,cid,cfin_id,csii_itemid,emba_gz_bank,emba_gz_account,esda_ba_name,Emba_nationality from EmSalaryBaseAdd_view ev ");
		sql.append("where cid=? and emba_state in(1,2,5) and cfin_id is not null and cfin_id!=0 ");
		sql.append("and (select COUNT(*)c from CoGList cogl ");
		sql.append(" inner join CoOfferList coli on cogl.cgli_coli_id=coli.coli_id ");
		sql.append(" where coli.coli_pclass='财税服务' ");
		sql.append(" and gid=ev.gid)>0");
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setInt(1, cid);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				EmSalaryDataModel m = new EmSalaryDataModel();
				m.setGid(rs.getInt("gid"));
				m.setCid(rs.getInt("cid"));
				m.setEsda_if_bms(1);
				m.setEsda_oof_state(0);
				m.setEsda_data_type(0);
				m.setEsda_usage_type(0);
				m.setEsda_payment_state(3);
				m.setCfin_id(rs.getInt("cfin_id"));
				m.setCsii_itemid(rs.getString("csii_itemid"));
				m.setEsda_bank(rs.getString("emba_gz_bank"));
				m.setEsda_bank_account(rs.getString("emba_gz_account"));
				m.setEsda_ba_name(rs.getString("esda_ba_name"));
				m.setEsda_nationality(rs.getString("Emba_nationality"));
				m.setName(rs.getString("emba_name"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取所属月份列表
	public List<String> getOwnmonth(int cid, int gid, String esin_stopmonth) {
		List<String> list = new ArrayList<String>();
		PreparedStatement psmt = null;
		if (!"".equals(esin_stopmonth) && esin_stopmonth != null) {
			String sql = "SELECT DISTINCT TOP 5 ownmonth FROM CoSalaryItemIDInfo WHERE cid=? and ownmonth<=(SELECT TOP 1 esin_stopmonth FROM EmSalaryInfo WHERE gid=? ORDER BY esin_id DESC) ORDER BY ownmonth DESC";
			psmt = conn.getpre(sql);
			try {
				psmt.setInt(1, cid);
				psmt.setInt(2, gid);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			String sql = "SELECT DISTINCT TOP 5 ownmonth FROM CoSalaryItemIDInfo WHERE cid=? ORDER BY ownmonth DESC";
			psmt = conn.getpre(sql);
			try {
				psmt.setInt(1, cid);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		try {
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				list.add(rs.getString("ownmonth"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 检测是否存在项目
	public boolean existItem(int cid, int ownmonth) {
		PreparedStatement psmt = null;
		String sql = "SELECT count(csii_id) FROM CoSalaryItemIDInfo WHERE cid=? AND ownmonth=?";
		try {
			psmt = conn.getpre(sql);
			psmt.setInt(1, cid);
			psmt.setInt(2, ownmonth);
			ResultSet rs = psmt.executeQuery();
			rs.next();
			if (rs.getInt("count") > 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			return false;
		}
	}

	// 获取项目信息(csii_ifzero: -1获取所有项目;0获取不清零字段；1获取清零字段;2暂时清零字段)
	public List<EmSalaryBaseAddItemModel> getCSIIInfo(int cid, int ownmonth,
			int csii_ifzero) {
		List<EmSalaryBaseAddItemModel> list = new ArrayList<EmSalaryBaseAddItemModel>();
		PreparedStatement psmt = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT a.csii_id,csii_item_name,ciin_id,csii_col,b.csii_id AS cfda,c.csgi_content,CASE WHEN c.csgi_content IS NULL THEN 0 ELSE 1 END AS 'if_gd'");
			sql.append(" FROM (SELECT csii_id,csii_col,csii_sequence,csii_item_name,ciin_id,csii_ifzero FROM View_CSII_CSIIDI WHERE csii_csd_state<>2 AND cid=? AND ownmonth=? and csii_fd_state!=2)a");
			sql.append(" LEFT JOIN (SELECT csii_id FROM CoFormulaData d LEFT JOIN CoFormulaInfo e ON d.cfin_id=e.cfin_id WHERE cfin_delete=0 GROUP BY csii_id)b ON a.csii_id=b.csii_id");
			sql.append(" LEFT JOIN CoSalaryGDItem c ON a.csii_col=c.csgi_col");
			if (csii_ifzero != -1) {
				sql.append(" where csii_ifzero=");
				sql.append(csii_ifzero);
			}
			sql.append(" ORDER BY csii_sequence,csii_col");
			psmt = conn.getpre(sql.toString());
			psmt.setInt(1, cid);
			psmt.setInt(2, ownmonth);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				list.add(new EmSalaryBaseAddItemModel(rs.getInt("csii_id"), rs
						.getString("csii_item_name"), rs.getInt("ciin_id"), rs
						.getString("csii_col"), rs.getString("cfda"), rs
						.getString("csgi_content"), rs.getInt("if_gd")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取工资非清零字段修改列表（根据taba_id获取工资信息，批量任务单）
	public List<EmSalaryDataModel> getSalaryDataByTabaIdToNonZero(int taba_id) {
		List<EmSalaryDataModel> list = new ArrayList<EmSalaryDataModel>();
		StringBuilder sql = new StringBuilder();
		EmSalaryDataModel m;
		sql.append("select tb.tbrb_id,ed.cid,ed.gid,ed.ownmonth,ed.cfin_id,ed.esda_usage_type,ed.esda_payment_state,ed.esda_remark,eb.emba_name,cb.coba_shortname,eb.emba_name,esdt.* ");
		sql.append(" from TaskBatchRelBusiness tb ");
		sql.append(" left join EmSalaryDataTemp esdt on tb.tbrb_data_id=esdt.esdt_id  ");
		sql.append(" left join (select esda_id,cid,gid,ownmonth,cfin_id,esda_usage_type,esda_payment_state,esda_remark from EmSalaryData)ed on esdt.esda_id=ed.esda_id ");
		sql.append(" left join (select gid,emba_name from EmBase) eb on ed.gid=eb.gid  ");
		sql.append(" left join (select CID,coba_shortname from CoBase)cb on cb.CID=ed.cid ");
		sql.append(" where tb.tbrb_taba_id=? and tb.tbrb_state=1 and ed.esda_id is not null ");
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setInt(1, taba_id);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new EmSalaryDataModel();
				m.setEsda_id(rs.getInt("esda_id"));
				m.setCid(rs.getInt("cid"));
				m.setGid(rs.getInt("gid"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setEsda_remark(rs.getString("esda_remark"));
				m.setCfin_id(rs.getInt("cfin_id"));
				m.setEsda_usage_type(rs.getInt("esda_usage_type"));
				m.setEsda_payment_state(rs.getInt("esda_payment_state"));
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
				m.setCoba_shortname(rs.getString("coba_shortname"));
				m.setName(rs.getString("emba_name"));
				m.setTbrb_id(rs.getInt("tbrb_id"));
				m.setEsdt_id(rs.getInt("esdt_id"));
				m.setEsda_total_adjtax(rs.getBigDecimal("esda_total_adjtax"));
				m.setEsda_total_afttax(rs.getBigDecimal("esda_total_afttax"));
				m.setEsda_hafcp(rs.getBigDecimal("esda_hafcp"));
				m.setEsda_house_cradix(rs.getBigDecimal("esda_house_cradix"));
				m.setEsda_house_cpercent(rs
						.getBigDecimal("esda_house_cpercent"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据taba_id获取工资信息(批量任务单)
	public List<EmSalaryDataModel> getSalaryDataByTabaIdFormEsdt(int taba_id) {
		List<EmSalaryDataModel> list = new ArrayList<EmSalaryDataModel>();
		EmSalaryDataModel m;
		String sql = "select ed.* from TaskBatchRelBusiness tb left join EmSalaryDataTemp et on tb.tbrb_data_id=et.esdt_id left join EmSalaryData ed on ed.esda_id=et.esda_id where tb.tbrb_taba_id=? and tb.tbrb_state=1 and ed.esda_id is not null and esdt_state=0";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, taba_id);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new EmSalaryDataModel();
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
				m.setEsda_hafcp(rs.getBigDecimal("esda_hafcp"));
				m.setEsda_house_cradix(rs.getBigDecimal("esda_house_cradix"));
				m.setEsda_house_cpercent(rs
						.getBigDecimal("esda_house_cpercent"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取工资合计
	public List<EmSalaryDataModel> getSalaryDataSUM(String str, Integer taba_id) {
		List<EmSalaryDataModel> list = new ListModelList<EmSalaryDataModel>();
		EmSalaryDataModel m;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT  ISNULL(SUM(esda_siop),0) AS esda_siop,ISNULL(SUM(esda_hafop),0) AS esda_hafop,");
		sql.append("ISNULL(SUM(esda_base_radix),0) AS esda_base_radix,ISNULL(SUM(esda_a_base_radix),0) AS esda_a_base_radix,");
		sql.append("ISNULL(SUM(esda_a_base_salary),0) AS esda_a_base_salary,ISNULL(SUM(esda_a_workdays),0) AS esda_a_workdays,");
		sql.append("ISNULL(SUM(esda_days),0) AS esda_days,ISNULL(SUM(esda_cqdays),0) AS esda_cqdays,");
		sql.append("ISNULL(SUM(esda_b_cqdays),0) AS esda_b_cqdays,ISNULL(SUM(esda_qqdays),0) AS esda_qqdays,");
		sql.append("ISNULL(SUM(esda_b_qqdays),0) AS esda_b_qqdays,ISNULL(SUM(esda_aded),0) AS esda_aded,");
		sql.append("ISNULL(SUM(esda_a_aded),0) AS esda_a_aded,ISNULL(SUM(esda_base_salary),0) AS esda_base_salary,");
		sql.append("ISNULL(SUM(esda_house_radix),0) AS esda_house_radix,ISNULL(SUM(esda_house_percent),0) AS esda_house_percent,");
		sql.append("ISNULL(SUM(esda_house_ntl),0) AS esda_house_ntl,ISNULL(SUM(esda_db_tax_base),0) AS esda_db_tax_base,");
		sql.append("ISNULL(SUM(esda_lw_tax_base),0) AS esda_lw_tax_base,ISNULL(SUM(esda_lw_tax),0) AS esda_lw_tax,");
		sql.append("ISNULL(SUM(esda_other),0) AS esda_other,ISNULL(SUM(esda_other_tax),0) AS esda_other_tax,");
		sql.append("ISNULL(SUM(esda_total_adjtax),0) AS esda_total_adjtax,ISNULL(SUM(esda_total_afttax),0) AS esda_total_afttax,");
		sql.append("ISNULL(SUM(esda_hafcp),0) AS esda_hafcp,ISNULL(SUM(esda_house_cradix),0) AS esda_house_cradix,");
		sql.append("ISNULL(SUM(esda_house_cpercent),0) AS esda_house_cpercent,");
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
		if (!"".equals(taba_id) && taba_id != null) {
			sql.append(" FROM TaskBatchRelBusiness tb left join EmSalaryData ed on tb.tbrb_data_id=ed.esda_id WHERE tb.tbrb_taba_id="
					+ taba_id
					+ " and tb.tbrb_state=1 and ed.esda_id is not null ");
		} else {
			sql.append(" FROM EmSalaryData ed WHERE 1=1" + str);
		}
		// System.out.println(sql);
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new EmSalaryDataModel();
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
				m.setEsda_hafcp(rs.getBigDecimal("esda_hafcp"));
				m.setEsda_house_cradix(rs.getBigDecimal("esda_house_cradix"));
				m.setEsda_house_cpercent(rs
						.getBigDecimal("esda_house_cpercent"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据taba_id获取工资信息(批量任务单)
	public List<EmSalaryDataModel> getSalaryDataByTabaId(int taba_id) {
		List<EmSalaryDataModel> list = new ArrayList<EmSalaryDataModel>();
		EmSalaryDataModel m;
		String sql = "select tb.tbrb_id,tb.tbrb_customInt,eb.emba_name,ed.* from TaskBatchRelBusiness tb left join EmSalaryData ed on tb.tbrb_data_id=ed.esda_id left join (select gid,emba_name from EmBase) eb on ed.gid=eb.gid where tb.tbrb_taba_id=? and tb.tbrb_state=1 and ed.esda_id is not null order by CHARINDEX(','+CONVERT(varchar(10),ed.esda_payment_state)+',',',3,0,1,4,2,'),tbrb_customInt";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, taba_id);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new EmSalaryDataModel();
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
				m.setName(rs.getString("emba_name"));
				m.setTbrb_id(rs.getInt("tbrb_id"));
				m.setTbrb_customInt(rs.getInt("tbrb_customInt"));
				m.setEsda_total_adjtax(rs.getBigDecimal("esda_total_adjtax"));
				m.setEsda_total_afttax(rs.getBigDecimal("esda_total_afttax"));
				m.setEsda_hpro(rs.getString("esda_hpro"));
				m.setEsda_taxplace(rs.getString("esda_taxplace"));
				m.setEsda_hafcp(rs.getBigDecimal("esda_hafcp"));
				m.setEsda_house_cradix(rs.getBigDecimal("esda_house_cradix"));
				m.setEsda_house_cpercent(rs
						.getBigDecimal("esda_house_cpercent"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据业务esda_id获取taba_id等信息
	public EmSalaryDataModel getEmSalaryTaskInfo(int esda_id) {
		EmSalaryDataModel m = null;
		String sql = "select taba_id,taba_tapr_id,tbrb_id,tbrb_customInt from TaskBatchRelBusiness tr inner join TaskBatch tb on tr.tbrb_taba_id=tb.taba_id where tb.taba_class='EmSalaryData' and tbrb_state=1 and tr.tbrb_data_id=?";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, esda_id);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				m = new EmSalaryDataModel();
				m.setTaba_id(rs.getInt("taba_id"));
				m.setTapr_id(rs.getInt("taba_tapr_id"));
				m.setTbrb_id(rs.getInt("tbrb_id"));
				m.setTbrb_customInt(rs.getInt("tbrb_customInt"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return m;
	}

	// 根据taba_id获取工资信息(批量任务单待发放)
	public List<EmSalaryDataModel> getSalaryDataByTabaIdToPay(int taba_id) {
		List<EmSalaryDataModel> list = new ArrayList<EmSalaryDataModel>();
		EmSalaryDataModel m;
		StringBuilder sql = new StringBuilder();
		// sql.append("select tb.tbrb_id,eb.emba_name,efsa_ReceivableConfirm=isnull(ef.cfmb_FinanceConfirm,0),ed.* ");
		sql.append("select tb.tbrb_id,eb.emba_name,case when esda_oof_state=1 then 1 else isnull(ef.cfmb_FinanceConfirm,0) end as efsa_ReceivableConfirm,ed.* ");
		sql.append(" from TaskBatchRelBusiness tb ");
		sql.append(" left join EmSalaryData ed on tb.tbrb_data_id=ed.esda_id ");
		sql.append(" left join (select gid,emba_name from EmBase) eb on ed.gid=eb.gid ");
		sql.append(" left join (select efsa_esda_id,cfmb_FinanceConfirm from EmFinanceSalary efsa left join CoFinanceMonthlyBill cfmb on efsa.efsa_cfmb_number=cfmb.cfmb_number where efsa_state=1)ef  ");
		sql.append(" on ef.efsa_esda_id=ed.esda_id");
		sql.append(" where tb.tbrb_taba_id=? and tb.tbrb_state=1 and ed.esda_id is not null ");
		sql.append(" order by esda_payment_state desc,efsa_ReceivableConfirm");
		PreparedStatement psmt = conn.getpre(sql.toString());
		// System.out.println(taba_id);
		// System.out.println(sql);
		try {
			psmt.setInt(1, taba_id);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new EmSalaryDataModel();
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
				m.setName(rs.getString("emba_name"));
				m.setTbrb_id(rs.getInt("tbrb_id"));
				m.setTbrb_customInt(rs.getInt("efsa_ReceivableConfirm"));
				m.setEsda_total_adjtax(rs.getBigDecimal("esda_total_adjtax"));
				m.setEsda_total_afttax(rs.getBigDecimal("esda_total_afttax"));
				m.setEsda_hpro(rs.getString("esda_hpro"));
				m.setEsda_taxplace(rs.getString("esda_taxplace"));
				m.setEsda_hafcp(rs.getBigDecimal("esda_hafcp"));
				m.setEsda_house_cradix(rs.getBigDecimal("esda_house_cradix"));
				m.setEsda_house_cpercent(rs
						.getBigDecimal("esda_house_cpercent"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据Cid及所属月份获取工资信息
	public List<EmSalaryDataModel> getSalaryDataByCidMonth(int cid, int ownmonth) {
		List<EmSalaryDataModel> list = new ArrayList<EmSalaryDataModel>();
		EmSalaryDataModel m;
		String sql = "select eb.emba_name,ed.* from EmSalaryData ed left join EmBase eb on ed.gid=eb.gid where ed.cid=? and ed.ownmonth=? order by CHARINDEX(','+CONVERT(varchar(10),esda_payment_state)+',',',3,0,1,4,2,'),eb.emba_name";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, cid);
			psmt.setInt(2, ownmonth);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new EmSalaryDataModel();
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
				m.setName(rs.getString("emba_name"));
				m.setEsda_total_adjtax(rs.getBigDecimal("esda_total_adjtax"));
				m.setEsda_total_afttax(rs.getBigDecimal("esda_total_afttax"));
				m.setEsda_hpro(rs.getString("esda_hpro"));
				m.setEsda_taxplace(rs.getString("esda_taxplace"));
				m.setEsda_hafcp(rs.getBigDecimal("esda_hafcp"));
				m.setEsda_house_cradix(rs.getBigDecimal("esda_house_cradix"));
				m.setEsda_house_cpercent(rs
						.getBigDecimal("esda_house_cpercent"));
				m.checkIfBank();
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据Cid及所属月份获取工资信息
	public List<EmSalaryDataModel> getSalaryDataByCidMonth(int cid,
			int ownmonth, int esda_payment_state) {
		List<EmSalaryDataModel> list = new ArrayList<EmSalaryDataModel>();
		EmSalaryDataModel m;
		String sql = "select eb.emba_name,ed.* from EmSalaryData ed left join EmBase eb on ed.gid=eb.gid where ed.cid=? and ed.ownmonth=? and esda_payment_state=? order by eb.emba_name";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, cid);
			psmt.setInt(2, ownmonth);
			psmt.setInt(3, esda_payment_state);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new EmSalaryDataModel();
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
				m.setName(rs.getString("emba_name"));
				m.setEsda_total_adjtax(rs.getBigDecimal("esda_total_adjtax"));
				m.setEsda_total_afttax(rs.getBigDecimal("esda_total_afttax"));
				m.setEsda_hpro(rs.getString("esda_hpro"));
				m.setEsda_taxplace(rs.getString("esda_taxplace"));
				m.setEsda_hafcp(rs.getBigDecimal("esda_hafcp"));
				m.setEsda_house_cradix(rs.getBigDecimal("esda_house_cradix"));
				m.setEsda_house_cpercent(rs
						.getBigDecimal("esda_house_cpercent"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据主键获取工资信息
	public List<EmSalaryDataModel> getSalaryDataByIdList(String esbaId) {
		List<EmSalaryDataModel> list = new ArrayList<EmSalaryDataModel>();
		EmSalaryDataModel m;
		String sql = "select ei.cfin_id as cfinid,ed.*,em.emba_gz_account,em.emba_gz_bank,em.emba_writeoff_account,em.emba_writeoff_bank from EmSalaryData ed left join EmSalaryInfo ei on ed.gid=ei.gid and ed.cid=ei.cid left join EmBase em on em.gid=ed.gid where esda_id in ("
				+ esbaId + ")";
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new EmSalaryDataModel();
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
				m.setEsda_ba_name(rs.getString("esda_ba_name"));
				if (rs.getInt("esda_usage_type") == 1) {
					if (!"".equals(rs.getString("emba_writeoff_bank"))
							&& rs.getString("emba_writeoff_bank") != null
							&& !"NULL".equals(rs
									.getString("emba_writeoff_bank"))) {
						m.setEsda_bank(rs.getString("emba_writeoff_bank"));
						m.setEsda_bank_account(rs
								.getString("emba_writeoff_account"));
					} else {
						m.setEsda_bank(rs.getString("emba_gz_bank"));
						m.setEsda_bank_account(rs.getString("emba_gz_account"));
					}
				} else {
					m.setEsda_bank(rs.getString("emba_gz_bank"));
					m.setEsda_bank_account(rs.getString("emba_gz_account"));
				}
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
				m.setEsda_hafcp(rs.getBigDecimal("esda_hafcp"));
				m.setEsda_house_cradix(rs.getBigDecimal("esda_house_cradix"));
				m.setEsda_house_cpercent(rs
						.getBigDecimal("esda_house_cpercent"));
				list.add(m);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<EmSalaryDataModel> getSalaryDataIfhold() {
		List<EmSalaryDataModel> list = new ArrayList<EmSalaryDataModel>();
		EmSalaryDataModel m = null;
		String sql = "select co.CID,co.coba_shortname,co.coba_namespell,em.gid,em.emba_name,em.emba_spell,ed.esda_id,ed.ownmonth,ed.esda_usage_type,ed.esda_ifhold,esda_total_pretax,esda_tax,esda_db,esda_db_tax,esda_dc,esda_dc_tax,esda_stock,esda_stock_tax,esda_pay from EmSalaryData ed left join EmBase em on ed.gid = em.gid left join CoBase co on ed.cid=co.CID where ed.esda_ifhold=1";
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new EmSalaryDataModel();
				m.setCid(rs.getInt("cid"));
				m.setCoba_shortname(rs.getString("coba_shortname"));
				m.setCoba_namespell(rs.getString("coba_namespell"));
				m.setGid(rs.getInt("gid"));
				m.setName(rs.getString("emba_name"));
				m.setEmba_spell(rs.getString("emba_spell"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setEsda_usage_type(rs.getInt("esda_usage_type"));
				m.setEsda_ifhold(rs.getInt("esda_ifhold"));
				m.setEsda_total_pretax(rs.getBigDecimal("esda_total_pretax"));
				m.setEsda_tax(rs.getBigDecimal("esda_tax"));
				m.setEsda_db(rs.getBigDecimal("esda_db"));
				m.setEsda_db_tax(rs.getBigDecimal("esda_db_tax"));
				m.setEsda_dc(rs.getBigDecimal("esda_dc"));
				m.setEsda_dc_tax(rs.getBigDecimal("esda_dc_tax"));
				m.setEsda_stock(rs.getBigDecimal("esda_stock"));
				m.setEsda_stock_tax(rs.getBigDecimal("esda_stock_tax"));
				m.setEsda_pay(rs.getBigDecimal("esda_pay"));
				m.setEsda_id(rs.getInt("esda_id"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据Cid及所属月份获取项目信息
	public List<EmSalaryBaseAddItemModel> getItemInfoByCidMonth(int cid,
			int ownmonth) {
		List<EmSalaryBaseAddItemModel> list = new ArrayList<EmSalaryBaseAddItemModel>();
		EmSalaryBaseAddItemModel m = null;
		String sql = "select csii_col,csii_item_name,csii_fd_state,chk_cfd from CSII_CFDa_V WHERE  cid=? AND ownmonth=? and csii_fd_state!=2  ORDER BY csii_csd_state desc,csii_sequence";
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

	// 根据Cid及所属月份获取项目信息
	public List<EmSalaryBaseAddItemModel> getItemInfoByCidMonthNoPay(int cid,
			int ownmonth) {
		List<EmSalaryBaseAddItemModel> list = new ArrayList<EmSalaryBaseAddItemModel>();
		EmSalaryBaseAddItemModel m = null;
		String sql = "select csii_col,csii_item_name,csii_fd_state,chk_cfd from CSII_CFDa_V WHERE  cid=? AND ownmonth=? and csii_fd_state!=2 and csii_col!='esda_pay'  ORDER BY csii_csd_state desc,csii_sequence";
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

	// 根据Cid及所属月份获取项目信息(获取不需自动计算以及清零的数据)
	public List<EmSalaryBaseAddItemModel> getItemInfoByCidMonthCanEdit(int cid,
			int ownmonth) {
		List<EmSalaryBaseAddItemModel> list = new ArrayList<EmSalaryBaseAddItemModel>();
		EmSalaryBaseAddItemModel m = null;
		String sql = "select csii_col,csii_item_name from CSII_CFDa_V WHERE  cid=? AND ownmonth=? and csii_fd_state!=2 and chk_cfd is null and csii_ifzero=1 ORDER BY csii_csd_state desc,csii_sequence";
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

	// 根据Cid及所属月份获取项目信息(获取非清零的数据)
	public List<EmSalaryBaseAddItemModel> getItemInfoByCidMonthNoZero(int cid,
			int ownmonth) {
		List<EmSalaryBaseAddItemModel> list = new ArrayList<EmSalaryBaseAddItemModel>();
		EmSalaryBaseAddItemModel m = null;
		String sql = "select csii_col,csii_item_name from CSII_CFDa_V WHERE  cid=? AND ownmonth=? and csii_fd_state!=2 and csii_ifzero=0 ORDER BY csii_csd_state desc,csii_sequence";
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

	// 获取工资所属月份列表
	public List<String> getOwnmonth() {
		List<String> list = new ArrayList<String>();
		String sql = "select distinct ownmonth from EmSalaryData order by ownmonth desc";
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				list.add(rs.getString("ownmonth"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据Cid获取工资所属月份列表
	public List<String> getOwnmonth(int cid) {
		List<String> list = new ArrayList<String>();
		String sql = "select distinct ownmonth from EmSalaryData where cid=? order by ownmonth desc";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, cid);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				list.add(rs.getString("ownmonth"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据所属月份获取工资信息的公司简称及CID
	public List<String[]> getCompany(int ownmonth) {
		List<String[]> list = new ArrayList<String[]>();
		String sql = "select distinct ed.cid,co.coba_shortname from EmSalaryData ed left join CoBase co on ed.cid=co.CID where ed.ownmonth=? order by coba_shortname";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, ownmonth);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				list.add(new String[] { rs.getString("cid"),
						rs.getString("coba_shortname") });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;

	}

	// 根据工资数据ID获取员工工资信息
	public EmSalaryBaseSel_viewModel getSalaryEmBase(int esda_id) {
		EmSalaryBaseSel_viewModel m = null;
		String sql = "select * from EmSalaryBaseSel_view where esda_id=?";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, esda_id);
			ResultSet rs = psmt.executeQuery();
			rs.next();
			m = new EmSalaryBaseSel_viewModel(rs.getInt("esda_id"),
					rs.getInt("ownmonth"), rs.getInt("cid"),
					rs.getString("coba_shortname"), rs.getString("emba_name"),
					rs.getInt("gid"), rs.getString("esda_nationality"),
					rs.getString("esda_bank_account"),
					rs.getString("esda_bank"), rs.getString("esda_ba_name"),
					rs.getInt("esda_usage_type"), rs.getInt("cfin_id"),
					rs.getString("csii_itemid"), rs.getString("cfin_name"),
					rs.getString("esda_remark"),
					rs.getString("esda_fd_remark"),
					rs.getInt("esda_payment_state"),
					rs.getString("esda_rp_reason"),
					rs.getInt("esda_history_state"), rs.getInt("esda_tapr_id"));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return m;
	}

	// 根据Cid获取公司简称
	public String getCoShortName(int cid) {
		String name = "";
		String sql = "select coba_shortname from CoBase where CID=?";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, cid);
			ResultSet rs = psmt.executeQuery();
			rs.next();
			name = rs.getString("coba_shortname");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return name;
	}

	// 查询服务中的所有公司基本信息
	public List<CoBaseModel> getCoBaseAll() {
		List<CoBaseModel> list = new ArrayList<CoBaseModel>();
		CoBaseModel m = null;
		String sql = "select CID,coba_shortname,coba_namespell,coba_gzaddname from CoBase where coba_servicestate=1 order by CID";
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new CoBaseModel();
				m.setCid(rs.getInt("CID"));
				m.setCoba_shortname(rs.getString("coba_shortname"));
				m.setCoba_namespell(rs.getString("coba_namespell"));
				m.setCoba_gzaddname(rs.getString("coba_gzaddname"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取所有公司基本信息
	public List<CoBaseModel> getCoBase() {
		List<CoBaseModel> list = new ArrayList<CoBaseModel>();
		CoBaseModel m = null;
		String sql = "select CID,coba_shortname,coba_namespell,coba_client,coba_gzaddname,coba_gzaudname,coba_servicestate from CoBase order by coba_servicestate desc,CID";
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new CoBaseModel();
				m.setCid(rs.getInt("CID"));
				m.setCoba_shortname(rs.getString("coba_shortname"));
				m.setCoba_namespell(rs.getString("coba_namespell"));
				m.setCoba_gzaddname(rs.getString("coba_gzaddname"));
				m.setCoba_gzaudname(rs.getString("coba_gzaudname"));
				m.setCoba_client(rs.getString("coba_client"));
				m.setCoba_servicestate(rs.getInt("coba_servicestate"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取所有公司基本信息
	public List<CoBaseModel> getCoBase(String top, String str) {
		List<CoBaseModel> list = new ArrayList<CoBaseModel>();
		CoBaseModel m = null;
		String sql = "select "
				+ top
				+ " CID,coba_shortname,coba_namespell,coba_client,coba_gzaddname,coba_gzaudname,coba_servicestate from CoBase where 1=1 "
				+ str + " order by coba_servicestate desc,CID";
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new CoBaseModel();
				m.setCid(rs.getInt("CID"));
				m.setCoba_shortname(rs.getString("coba_shortname"));
				m.setCoba_namespell(rs.getString("coba_namespell"));
				m.setCoba_gzaddname(rs.getString("coba_gzaddname"));
				m.setCoba_gzaudname(rs.getString("coba_gzaudname"));
				m.setCoba_client(rs.getString("coba_client"));
				m.setCoba_servicestate(rs.getInt("coba_servicestate"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据所属月份查询工资表的公司信息
	public List<CoBaseModel> getCoBaseAll(int ownmonth) {
		List<CoBaseModel> list = new ArrayList<CoBaseModel>();
		CoBaseModel m = null;
		String sql = "select distinct co.CID,co.coba_shortname,co.coba_namespell,co.coba_gzaddname,isnull(ed.cou,0)cou from CoBase co left join (select distinct cid,ownmonth from EmSalaryData) esda on esda.cid=co.CID left join (select cid,ownmonth,cou=COUNT(esda_id) from EmSalaryData where esda_payment_state=3 group by cid,ownmonth)ed on ed.cid=esda.CID and ed.ownmonth=esda.ownmonth where coba_servicestate=1 and esda.ownmonth=? order by cou desc,CID";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, ownmonth);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new CoBaseModel();
				m.setCid(rs.getInt("CID"));
				m.setCoba_shortname(rs.getString("coba_shortname"));
				m.setCoba_namespell(rs.getString("coba_namespell"));
				m.setCoba_gzaddname(rs.getString("coba_gzaddname"));
				m.setCou(rs.getInt("cou"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据Cid获取员工信息
	public List<EmbaseModel> getEmbaseByCid(int cid) {
		List<EmbaseModel> list = new ArrayList<EmbaseModel>();
		EmbaseModel m = null;
		String sql = "select gid,emba_name,emba_spell,emba_idcard,(case emba_state when 0 then '离职' when 1 then '在职' else '入职中' end) as state  from EmBase where cid=? and emba_state in(0,1,2,5) order by emba_state desc,emba_name";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, cid);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new EmbaseModel();
				m.setGid(rs.getInt("gid"));
				m.setEmba_name(rs.getString("emba_name"));
				m.setEmba_spell(rs.getString("emba_spell"));
				m.setEmba_idcard(rs.getString("emba_idcard"));
				m.setStatename(rs.getString("state"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据CID及所属月份获取工资表的员工姓名及用途
	public List<EmSalaryBaseSel_viewModel> getEmbaseByCidOwnmonth(int cid,
			int ownmonth, int createmonth) {
		List<EmSalaryBaseSel_viewModel> list = new ArrayList<EmSalaryBaseSel_viewModel>();
		String sql = "";
		sql = "select esda_id,emba_name,esda_usage_type from EmSalaryBaseSel_view where cid=? and ownmonth=? "
				+ "	and gid in(select a.gid from "
				+ "	(select gid,COUNT(*)o_cou from EmSalaryData where esda_usage_type=0 and cid=? and ownmonth=? group by gid)a "
				+ "left join 	(select gid,COUNT(*)n_cou from EmSalaryData where esda_usage_type=0 and cid=? and ownmonth=? group by gid)b "
				+ "on a.gid=b.gid where 1=1 ";
		if (ownmonth != createmonth) {
			sql = sql + " and a.o_cou>isnull(n_cou,0)) order by emba_name";
		} else {
			sql = sql + " ) order by emba_name";
		}

		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, cid);
			psmt.setInt(2, ownmonth);
			psmt.setInt(3, cid);
			psmt.setInt(4, ownmonth);
			psmt.setInt(5, cid);
			psmt.setInt(6, createmonth);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				list.add(new EmSalaryBaseSel_viewModel(rs.getInt("esda_id"), rs
						.getString("emba_name"), rs.getInt("esda_usage_type")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取需要清零的字段
	public List<String> getIfZero(int cid, int ownmonth) {
		List<String> list = new ArrayList<String>();
		String sql = "select csii_col from CoSalaryItemInfo where csii_itemid=(select csii_itemid from CoSalaryItemIDInfo where cid=? and ownmonth=?) and cid=? and csii_ifzero=1 order by csii_sequence";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, cid);
			psmt.setInt(2, ownmonth);
			psmt.setInt(3, cid);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				list.add(rs.getString("csii_col"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据GID查CID
	public int getCidByGid(int gid) {
		String sql = "select cid from embase where gid=?";
		PreparedStatement psmt = conn.getpre(sql);
		int cid = 0;
		try {
			psmt.setInt(1, gid);
			ResultSet rs = psmt.executeQuery();
			rs.next();
			cid = rs.getInt("cid");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cid;
	}

	// 获取财务部用户
	public List<String> getCwUser() {
		List<String> list = new ArrayList<String>();
		String sql = "select log_name from Login l left join department d on l.dep_id=d.dep_id where log_inure=1 and d.dep_name='财务部' ";
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				list.add(rs.getString("log_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取薪酬负责人列表
	public List<String> getGzUser() {
		List<String> list = new ArrayList<String>();
		String sql = "select distinct coba_gzaddname from CoBase where coba_gzaddname is not null";
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				list.add(rs.getString("coba_gzaddname"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 判断员工是否分配算法
	public boolean existCfin(int cid, int gid) {
		boolean check = false;
		String sql = "select cou=COUNT(*) from EmSalaryInfo es  LEFT OUTER JOIN CoFormulaInfo cf on es.cfin_id=cf.cfin_id where cfin_state=3  and cfin_delete=0 AND es.cid=? AND es.gid=?";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, cid);
			psmt.setInt(2, gid);
			ResultSet rs = psmt.executeQuery();
			rs.next();
			if (rs.getInt("cou") > 0) {
				check = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return check;
	}

	// 获取员工薪酬信息
	public List<EmSalaryInfoModel> getEmSalaryInfo(String str) {
		ResultSet rs = null;
		List<EmSalaryInfoModel> list = new ArrayList<EmSalaryInfoModel>();
		if (!list.isEmpty())
			list.clear();

		try {
			String sql = "SELECT case when len(esin_nexttaxplace)>0 then esin_nexttaxplace else esin_taxplace end as taxplace,a.*,b.emba_nationality,b.emba_name,b.emba_email,c.coba_company,b.emba_gz_account,b.emba_gz_bank,b.emba_writeoff_bank,b.emba_writeoff_account,b.emba_gz_email,b.emba_gz_cemail FROM EmSalaryInfo a right join embase b on a.gid=b.gid and a.cid=b.cid left join cobase c on a.cid=c.cid WHERE 1=1"
					+ str;

			rs = conn.GRS(sql);
			while (rs.next()) {
				EmSalaryInfoModel model = new EmSalaryInfoModel();

				model.setEsin_id(rs.getInt("esin_id"));
				model.setCid(rs.getInt("cid"));
				model.setGid(rs.getInt("gid"));
				model.setCfin_id(rs.getInt("cfin_id"));
				model.setEsda_ba_name(rs.getString("esda_ba_name"));
				model.setEsda_bcc_email(rs.getString("esda_bcc_email"));
				model.setEsin_taxplace(rs.getString("taxplace"));
				model.setEsin_salaryplace(rs.getString("esin_salaryplace"));
				model.setEmba_nationality(rs.getString("emba_nationality"));
				model.setEsin_hpro(rs.getString("esin_hpro"));
				model.setEsin_tapr_id(rs.getInt("esin_tapr_id"));
				model.setName(rs.getString("emba_name"));
				model.setCompany(rs.getString("coba_company"));
				model.setEmba_gz_account(rs.getString("emba_gz_account"));
				model.setEmba_gz_bank(rs.getString("emba_gz_bank"));
				model.setEmba_writeoff_bank(rs.getString("emba_writeoff_bank"));
				model.setEmba_writeoff_account(rs
						.getString("emba_writeoff_account"));
				model.setEmba_gz_email(rs.getString("emba_gz_email"));
				model.setEmba_gz_cemail(rs.getString("emba_gz_cemail"));
				model.setEmba_email(rs.getString("emba_email"));
				model.setEsin_nexttaxplace_smonth(rs
						.getInt("esin_nexttaxplace_smonth"));
				list.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取员工薪酬报价单是否含有工资或个税
	public Integer[] getEmSalaryCoGlist(String str) {
		ResultSet rs = null;
		Integer[] list = new Integer[2];
		try {
			String sql = "select * from 	(select COUNT(*)gz_cou from coglist a left join CoOfferList b on a.cgli_coli_id=b.coli_id where b.coli_name like '%工资%' and a.cgli_state=1 "
					+ str
					+ ")gz_cou,"
					+ "(select COUNT(*)gs_cou from coglist a left join CoOfferList b on a.cgli_coli_id=b.coli_id where b.coli_name like '%税%' and a.cgli_state=1 "
					+ str + ")gs_cou";

			rs = conn.GRS(sql);
			while (rs.next()) {
				list[0] = rs.getInt("gz_cou");
				list[1] = rs.getInt("gs_cou");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据所属月份获取工资发放情况
	public List<EmSalaryPayInfoModel> getEmSalaryPayInfo(int ownmonth) {
		List<EmSalaryPayInfoModel> list = new ArrayList<EmSalaryPayInfoModel>();
		EmSalaryPayInfoModel m;
		StringBuilder sql = new StringBuilder();
		// 临时sq语句，上面的才是正式sql语句
		sql.append("select coba.CID,coba.coba_company,coba.coba_shortname,coba.coba_namespell,coba.coba_client,coba.coba_gzaddname,esda_pay=ISNULL(esda_pay,0),cfsa_PaidIn=isnull(cb.cfsa_PaidIn,0),couWf=isnull(c1.couWf,0),couSum=isnull(c2.couSum,0),isnull(convert(varchar(16),cfsa_addtime,120),'') as cfsa_addtime from CoBase coba ");
		sql.append("left join (select cid,esda_pay=isnull(SUM(esda_pay),0) from EmSalaryData es where ownmonth=? group by cid) es on coba.CID=es.cid ");
		sql.append("left join (select cid,couWf=COUNT(*) from EmSalaryData es where ownmonth=? and ((esda_payment_state!=2 and esda_oof_state=0) or (esda_payment_state in(3,0) and esda_oof_state=1)) group by cid) c1 on coba.CID=c1.cid ");
		sql.append("left join (select cid,couSum=COUNT(*) from EmSalaryData es where ownmonth=? group by cid) c2 on coba.CID=c2.cid ");
		// 上月工资的公司，将收款月份改为上一个月
		sql.append("left join (select cid,cfsa_PaidIn from ("
				+ "select a.cid,cfsa_PaidIn,case b.coco_gzmonth when '上月' then dbo.GetLastOwnmonth(a.ownmonth) "
				+ " else a.ownmonth end as ownmonth from  "
				+ " (select cid,sum(cfss_Receivable)cfsa_PaidIn,ownmonth from CoFinanceSortAccountss  "
				+ " where cfss_Receivable!=0 and cfss_fpfrist!=1 and cfss_cpac_name='税后工资'	group by cid,ownmonth)a "
				+ " left join  "
				+ " (select cid,case coco_gzmonth when '' then '当月' else coco_gzmonth end as coco_gzmonth "
				+ " from CoCompact where coco_compacttype='cs' and coco_stoptype is null and cid is not null)b on a.cid=b.cid"
				+ ")cfsa where ownmonth=?) cb on cb.cid=coba.CID ");
		// 用来排序，最新录的收款排最前
		sql.append("left join (select cid,cfsa_addtime from ("
				+ " select a.cid,cfsa_addtime,case b.coco_gzmonth when '上月' then dbo.GetLastOwnmonth(a.ownmonth) "
				+ " else a.ownmonth end as ownmonth from  "
				+ " (select cid,ownmonth,max(case when cfss_modtime is not null then cfss_modtime else cfss_addtime end)cfsa_addtime from CoFinanceSortAccountss  "
				+ " where cfss_Receivable!=0 and cfss_fpfrist!=1 and cfss_cpac_name='税后工资' group by cid,ownmonth)a "
				+ " left join  "
				+ " (select cid,case coco_gzmonth when '' then '当月' else coco_gzmonth end as coco_gzmonth "
				+ " from CoCompact where coco_compacttype='cs' and coco_stoptype is null and cid is not null)b "
				+ " on a.cid=b.cid"
				+ ")cfsa where ownmonth=? ) cb2 on cb2.cid=coba.CID ");
		sql.append("group by coba.CID,coba.coba_company,coba.coba_shortname,coba.coba_namespell,coba.coba_client,coba.coba_gzaddname,esda_pay,cb.cfsa_PaidIn,couWf,c2.couSum,cfsa_addtime ");
		sql.append("order by cfsa_addtime desc,couWf desc");
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setInt(1, ownmonth);
			psmt.setInt(2, ownmonth);
			psmt.setInt(3, ownmonth);
			psmt.setInt(4, ownmonth);
			psmt.setInt(5, ownmonth);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new EmSalaryPayInfoModel();
				m.setOwnmonth(ownmonth);
				m.setCid(rs.getInt("cid"));
				m.setCoba_company(rs.getString("coba_company"));
				m.setCoba_shortname(rs.getString("coba_shortname"));
				m.setCoba_namespell(rs.getString("coba_namespell"));
				m.setCoba_client(rs.getString("coba_client"));
				m.setCoba_gzaddname(rs.getString("coba_gzaddname"));
				m.setCfsa_PaidIn(rs.getBigDecimal("cfsa_PaidIn"));
				m.setEsda_pay(rs.getBigDecimal("esda_pay"));
				m.setCouWf(rs.getInt("couWf"));
				m.setCouSum(rs.getInt("couSum"));
				m.mosaicStatistics();
				m.setCfsa_addtime(rs.getString("cfsa_addtime"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据公司编号获取工资款收款情况
	public List<EmSalaryPayInfoModel> getEmSalaryGatheringInfo(int cid) {
		List<EmSalaryPayInfoModel> list = new ArrayList<EmSalaryPayInfoModel>();
		EmSalaryPayInfoModel m;
		StringBuilder sql = new StringBuilder();

		sql.append("select coba.CID,cb.ownmonth,coba.coba_company,coba.coba_shortname,coba.coba_namespell,coba.coba_client,coba.coba_gzaddname,cfss_remark,cfsa_PaidIn=isnull(cb.cfsa_PaidIn,0),isnull(convert(varchar(16),cfsa_addtime,120),'') as cfsa_addtime from  (select ownmonth,cid,cfss_Receivable as cfsa_PaidIn,cfss_remark,cfss_addtime as cfsa_addtime from CoFinanceSortAccountss where cfss_Receivable!=0 and cid=? and cfss_cpac_name='税后工资') cb ");
		sql.append("left join CoBase coba on cb.cid=coba.CID ");
		sql.append("order by cfsa_addtime desc");
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setInt(1, cid);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new EmSalaryPayInfoModel();
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setCid(rs.getInt("cid"));
				m.setCoba_company(rs.getString("coba_company"));
				m.setCoba_shortname(rs.getString("coba_shortname"));
				m.setCoba_namespell(rs.getString("coba_namespell"));
				m.setCoba_client(rs.getString("coba_client"));
				m.setCoba_gzaddname(rs.getString("coba_gzaddname"));
				m.setCfsa_PaidIn(rs.getBigDecimal("cfsa_PaidIn"));
				m.setCfsa_addtime(rs.getString("cfsa_addtime"));
				m.setRemark(rs.getString("cfss_remark"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查找台账应收的cfma_id
	public int getCfmaId(int cid, int gid, int ownmonth) {
		try {
			CallableStatement c = conn
					.getcall("EmSalaryData_SelCfma_p_lwj(?,?,?,?)");
			c.setInt(1, cid);
			c.setInt(2, gid);
			c.setInt(3, ownmonth);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
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

	// 判断员工是否分配工资或个税项的报价单
	public boolean checkCoOfferList(int gid) {
		StringBuilder sql = new StringBuilder();
		sql.append("select COUNT(*)c from CoGList cogl ");
		sql.append("inner join CoOfferList coli on cogl.cgli_coli_id=coli.coli_id ");
		sql.append("where coli.coli_pclass='财税服务' ");
		sql.append("and gid=?");
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setInt(1, gid);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				if (rs.getInt("c") > 0)
					return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// 员工工资数据查询
	public List<EmSalaryDataModel> getSalaryDataCoEm(String str) {
		List<EmSalaryDataModel> list = new ArrayList<EmSalaryDataModel>();
		EmSalaryDataModel m = null;
		String sql = "select co.CID,co.coba_shortname,co.coba_namespell,em.gid,em.emba_name,em.emba_spell,ed.esda_id,ed.ownmonth,ed.esda_usage_type"
				+ ",ed.esda_tax_base,esda_total_pretax,esda_tax,esda_db,esda_db_tax_base,esda_db_tax,esda_dc,esda_dc_tax,esda_stock,esda_stock_tax,esda_pay,esda_siop,esda_hafop,"
				+ "esda_write_off,esda_lw_tax,esda_lw_tax_base,esda_payment_state "
				+ "from EmSalaryData ed left join EmBase em on ed.gid = em.gid left join CoBase co on ed.cid=co.CID where co.CID in ( select cid from DataPopedom where log_id="
				+ UserInfo.getUserid()
				+ "  and dat_selected=1 )"
				+ str
				+ " order by co.coba_shortname,ed.ownmonth desc,em.emba_name";
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new EmSalaryDataModel();
				m.setCid(rs.getInt("cid"));
				m.setCoba_shortname(rs.getString("coba_shortname"));
				m.setCoba_namespell(rs.getString("coba_namespell"));
				m.setGid(rs.getInt("gid"));
				m.setName(rs.getString("emba_name"));
				m.setEmba_spell(rs.getString("emba_spell"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setEsda_usage_type(rs.getInt("esda_usage_type"));
				m.setEsda_total_pretax(rs.getBigDecimal("esda_total_pretax"));
				m.setEsda_siop(rs.getBigDecimal("esda_siop"));
				m.setEsda_hafop(rs.getBigDecimal("esda_hafop"));
				m.setEsda_tax_base(rs.getBigDecimal("esda_tax_base"));
				m.setEsda_tax(rs.getBigDecimal("esda_tax"));
				m.setEsda_db(rs.getBigDecimal("esda_db"));
				m.setEsda_db_tax_base(rs.getBigDecimal("esda_db_tax_base"));
				m.setEsda_db_tax(rs.getBigDecimal("esda_db_tax"));
				m.setEsda_dc(rs.getBigDecimal("esda_dc"));
				m.setEsda_dc_tax(rs.getBigDecimal("esda_dc_tax"));
				m.setEsda_stock(rs.getBigDecimal("esda_stock"));
				m.setEsda_stock_tax(rs.getBigDecimal("esda_stock_tax"));
				m.setEsda_write_off(rs.getBigDecimal("esda_write_off"));
				m.setEsda_lw_tax(rs.getBigDecimal("esda_lw_tax"));
				m.setEsda_lw_tax_base(rs.getBigDecimal("esda_lw_tax_base"));
				m.setEsda_pay(rs.getBigDecimal("esda_pay"));
				m.setEsda_id(rs.getInt("esda_id"));
				m.setEsda_payment_state(rs.getInt("esda_payment_state"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取个税报价单项目城市
	public ArrayList<String> getTaxCity(Integer gid) throws Exception {
		ArrayList<String> city = new ArrayList<String>();
		String sql = "select distinct b.coli_city from coglist a left join CoOfferList b on a.cgli_coli_id=b.coli_id "
				+ "where a.cgli_state=1 and b.coli_name like '%税%' and a.gid="
				+ gid + " order by b.coli_city";
		ResultSet rs = conn.GRS(sql);
		try {
			while (rs.next()) {
				city.add(rs.getString("coli_city"));
			}
		} catch (Exception e) {

		}
		return city;
	}

	// 获取个税报价单项目城市
	public ArrayList<String> getTaxCityByCid(Integer cid) throws Exception {
		ArrayList<String> city = new ArrayList<String>();
		String sql = "select distinct b.coli_city from coglist a left join CoOfferList b on a.cgli_coli_id=b.coli_id "
				+ "where a.cgli_state=1 and b.coli_name like '%税%' and a.cid="
				+ cid + " order by b.coli_city";
		ResultSet rs = conn.GRS(sql);
		try {
			while (rs.next()) {
				city.add(rs.getString("coli_city"));
			}
		} catch (Exception e) {

		}
		return city;
	}

	// 获取所有个税报价单项目城市
	public ArrayList<String> getTaxCityAll() throws Exception {
		ArrayList<String> city = new ArrayList<String>();
		String sql = "select distinct name from View_CoProduct where copr_name like '%税%' order by name";
		ResultSet rs = conn.GRS(sql);
		try {
			while (rs.next()) {
				city.add(rs.getString("name"));
			}
		} catch (Exception e) {

		}
		return city;
	}

	// 根据月份获取个税申报地
	public String getTaxPlace(Integer gid, Integer cid, Integer ownmonth)
			throws Exception {
		String taxplace = "";
		String sql = "select case when len(esin_nexttaxplace)>0 then case when "
				+ ownmonth
				+ ">=esin_nexttaxplace_smonth then esin_nexttaxplace else esin_taxplace end else esin_taxplace end as esin_taxplace "
				+ "from EmSalaryInfo where gid=" + gid + " and cid=" + cid;
		ResultSet rs = conn.GRS(sql);
		try {
			while (rs.next()) {
				taxplace = rs.getString("esin_taxplace");
			}
		} catch (Exception e) {

		}
		return taxplace;
	}
}
