package dal.EmSalary;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.EmPayModel;
import Model.EmSalaryDataModel;
import Model.EmSalaryHistoryDataModel;

public class EmSalary_HisSelDal {
	private static dbconn conn = new dbconn();

	// 查询工资历史数据
	public Map<Integer, EmSalaryHistoryDataModel> getHisData(String date,
			int batch) {
		Map<Integer, EmSalaryHistoryDataModel> map = new TreeMap<Integer, EmSalaryHistoryDataModel>();
		List<EmSalaryDataModel> esdaList;
		EmSalaryHistoryDataModel m;
		EmSalaryDataModel esdaM;
		String batch_str = "";
		if (batch > 0) {// 批次
			batch_str = " and eshd_payment_batch=" + String.valueOf(batch);
		}
		int cid;
		String ownmonth;
		Integer key;
		StringBuilder sql = new StringBuilder();
		sql.append("select es.cid,co.coba_shortname,eshd_id,es.gid,es.ownmonth,em.emba_name,eshd_usage_type,ISNULL(eshd_fd_remark,'') as eshd_fd_remark,eshd_rp_reason,ei.esin_taxplace,");
		sql.append("eshd_siop,eshd_hafop,eshd_total_pretax,eshd_tax_base,eshd_tax,eshd_dc,eshd_dc_tax,eshd_db,eshd_db_tax,eshd_stock,eshd_stock_tax,eshd_write_off,eshd_house_bf,eshd_pay,eshd_lw_tax_base,eshd_lw_tax ");
		sql.append("from EmSalaryHistoryData es ");
		sql.append("inner join (select gid,emba_name from EmBase) em on em.gid=es.gid ");
		sql.append("inner join (select cid,coba_shortname from CoBase) co on co.CID=es.cid ");
		sql.append("inner join EmSalaryInfo ei on es.gid=ei.gid and es.cid=ei.cid ");
		sql.append("where DATEDIFF(d,eshd_history_date,?)=0 " + batch_str);
		sql.append(" order by es.cid,es.ownmonth ");
		// System.out.println(sql);
		try {
			PreparedStatement psmt = conn.getpre(sql.toString());
			psmt.setString(1, date);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				cid = rs.getInt("cid");
				ownmonth =String.valueOf(rs.getInt("ownmonth")).substring(4);
				key=Integer.parseInt(String.valueOf(cid)+ownmonth);
				esdaM = new EmSalaryDataModel();
				esdaM.setCid(cid);
				esdaM.setCoba_shortname(rs.getString("coba_shortname"));
				esdaM.setEsda_id(rs.getInt("eshd_id"));
				esdaM.setGid(rs.getInt("gid"));
				esdaM.setOwnmonth(rs.getInt("ownmonth"));
				esdaM.setName(rs.getString("emba_name"));
				esdaM.setEsda_usage_type(rs.getInt("eshd_usage_type"));
				String fd_remark = "";
				if (!"".equals(rs.getString("esin_taxplace")) && !"深圳".equals(rs.getString("esin_taxplace"))
						&& rs.getString("esin_taxplace") != null
						&& !"NULL".equals(rs.getString("esin_taxplace"))) {
					fd_remark = "   个税申报地：" + rs.getString("esin_taxplace");
				}
				esdaM.setEsda_fd_remark(rs.getString("eshd_fd_remark")
						+ fd_remark);

				esdaM.setEsda_rp_reason(rs.getInt("eshd_rp_reason"));
				esdaM.setEsda_taxplace(rs.getString("esin_taxplace"));
				esdaM.setEsda_siop(rs.getBigDecimal("eshd_siop"));
				esdaM.setEsda_hafop(rs.getBigDecimal("eshd_hafop"));
				esdaM.setEsda_total_pretax(rs
						.getBigDecimal("eshd_total_pretax"));
				esdaM.setEsda_tax_base(rs.getBigDecimal("eshd_tax_base"));
				esdaM.setEsda_tax(rs.getBigDecimal("eshd_tax"));
				esdaM.setEsda_lw_tax_base(rs.getBigDecimal("eshd_lw_tax_base"));
				esdaM.setEsda_lw_tax(rs.getBigDecimal("eshd_lw_tax"));
				esdaM.setEsda_dc(rs.getBigDecimal("eshd_dc"));
				esdaM.setEsda_dc_tax(rs.getBigDecimal("eshd_dc_tax"));
				esdaM.setEsda_db(rs.getBigDecimal("eshd_db"));
				esdaM.setEsda_db_tax(rs.getBigDecimal("eshd_db_tax"));
				esdaM.setEsda_stock(rs.getBigDecimal("eshd_stock"));
				esdaM.setEsda_stock_tax(rs.getBigDecimal("eshd_stock_tax"));
				esdaM.setEsda_write_off(rs.getBigDecimal("eshd_write_off"));
				esdaM.setEsda_house_bf(rs.getBigDecimal("eshd_house_bf"));
				esdaM.setEsda_pay(rs.getBigDecimal("eshd_pay"));
				if (!map.containsKey(key)) {
					m = new EmSalaryHistoryDataModel();
					esdaList = new ArrayList<EmSalaryDataModel>();
					esdaList.add(esdaM);
					m.setEsdaList(esdaList);
					m.setCid(cid);
					m.setCoba_shortname(esdaM.getCoba_shortname());
					m.setOwnmonth(esdaM.getOwnmonth());
					map.put(key, m);
				} else {
					m = map.get(key);
					m.getEsdaList().add(esdaM);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	// 工资历史数据按公司统计
	public void getHisDataTotal(Map<Integer, EmSalaryHistoryDataModel> map,
			String date, int batch) {
		EmSalaryDataModel sumModel;
		EmSalaryHistoryDataModel m;
		String batch_str = "";
		if (batch > 0) {// 批次
			batch_str = " and eshd_payment_batch=" + String.valueOf(batch);
		}
		int cid;
		String ownmonth;
		Integer key;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT es.*,coco.gsfee,isnull(core.csre_csd_remark,'') as csre_csd_remark,isnull(core.csre_fd_remark,'') as csre_fd_remark from ");

		sql.append("(");
		sql.append("select es.cid,es.ownmonth,ISNULL(SUM(eshd_siop),0) AS eshd_siop,ISNULL(SUM(eshd_hafop),0) AS eshd_hafop,ISNULL(SUM(eshd_total_pretax),0) AS eshd_total_pretax,");
		sql.append("ISNULL(SUM(eshd_tax_base),0) AS eshd_tax_base,ISNULL(SUM(eshd_tax),0) AS eshd_tax,ISNULL(SUM(eshd_dc),0) AS eshd_dc,ISNULL(SUM(eshd_dc_tax),0) AS eshd_dc_tax,");
		sql.append("ISNULL(SUM(eshd_db),0) AS eshd_db,ISNULL(SUM(eshd_db_tax),0) AS eshd_db_tax,ISNULL(SUM(eshd_stock),0) AS eshd_stock,ISNULL(SUM(eshd_stock_tax),0) AS eshd_stock_tax,");
		sql.append("ISNULL(SUM(eshd_write_off),0) AS eshd_write_off,ISNULL(SUM(eshd_house_bf),0) AS eshd_house_bf,ISNULL(SUM(eshd_pay),0) AS eshd_pay,ISNULL(SUM(eshd_lw_tax_base),0) as eshd_lw_tax_base,ISNULL(SUM(eshd_lw_tax),0) as eshd_lw_tax ");
		sql.append("from EmSalaryHistoryData es ");
		sql.append("where DATEDIFF(d,eshd_history_date,?)=0 " + batch_str
				+ " group BY cid,ownmonth");
		sql.append(")es ");

		sql.append("left join (");
		sql.append("select a.cid,case coco_gsfee when 1 then '中智扣缴' when 2 then '客户扣缴' end as gsfee ");
		sql.append("from CoCompact a ");
		sql.append("where exists( ");
		sql.append("select * from ");
		sql.append("(select max(coco_id)as coco_id,cid from CoCompact where LEN(isnull(coco_gs,''))>1 group by cid)b ");
		sql.append("where a.coco_id=b.coco_id) ");
		sql.append(" )coco on es.cid=coco.cid ");

		sql.append("left join (");
		sql.append("SELECT * FROM CoSalaryRemark");
		sql.append(" )core on es.cid=core.cid ");
		try {
			PreparedStatement psmt = conn.getpre(sql.toString());
			psmt.setString(1, date);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				cid = rs.getInt("cid");
				ownmonth =String.valueOf(rs.getInt("ownmonth")).substring(4);
				key=Integer.parseInt(String.valueOf(cid)+ownmonth);
				if (map.containsKey(key)) {
					sumModel = new EmSalaryDataModel();
					sumModel.setCid(cid);
					sumModel.setEsda_siop(rs.getBigDecimal("eshd_siop"));
					sumModel.setEsda_hafop(rs.getBigDecimal("eshd_hafop"));
					sumModel.setEsda_total_pretax(rs
							.getBigDecimal("eshd_total_pretax"));
					sumModel.setEsda_tax_base(rs.getBigDecimal("eshd_tax_base"));
					sumModel.setEsda_tax(rs.getBigDecimal("eshd_tax"));
					sumModel.setEsda_lw_tax_base(rs.getBigDecimal("eshd_lw_tax_base"));
					sumModel.setEsda_lw_tax(rs.getBigDecimal("eshd_lw_tax"));
					sumModel.setEsda_dc(rs.getBigDecimal("eshd_dc"));
					sumModel.setEsda_dc_tax(rs.getBigDecimal("eshd_dc_tax"));
					sumModel.setEsda_db(rs.getBigDecimal("eshd_db"));
					sumModel.setEsda_db_tax(rs.getBigDecimal("eshd_db_tax"));
					sumModel.setEsda_stock(rs.getBigDecimal("eshd_stock"));
					sumModel.setEsda_stock_tax(rs
							.getBigDecimal("eshd_stock_tax"));
					sumModel.setEsda_write_off(rs
							.getBigDecimal("eshd_write_off"));
					sumModel.setEsda_house_bf(rs.getBigDecimal("eshd_house_bf"));
					sumModel.setEsda_pay(rs.getBigDecimal("eshd_pay"));
					sumModel.setEsda_remark(rs.getString("csre_csd_remark"));
					sumModel.setEsda_fd_remark(rs.getString("csre_fd_remark")
							+ "   " + rs.getString("gsfee"));
					m = map.get(key);
					m.setSumModel(sumModel);
					m.setEsdaListSize();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 查询电汇审核工资数据
	public Map<Integer, EmSalaryHistoryDataModel> getTTVData(String date,
			String str) {
		Map<Integer, EmSalaryHistoryDataModel> map = new TreeMap<Integer, EmSalaryHistoryDataModel>();
		List<EmSalaryDataModel> esdaList;
		EmSalaryHistoryDataModel m;
		EmSalaryDataModel esdaM;
		int cid;
		String ownmonth;
		Integer key;
		StringBuilder sql = new StringBuilder();
		sql.append("select es.cid,co.coba_shortname,esda_id,es.gid,es.ownmonth,em.emba_name,esda_usage_type,esda_fd_remark,isnull(esda_rp_reason,0) as esda_rp_reason,");
		sql.append("esda_siop,esda_hafop,esda_total_pretax,esda_tax_base,esda_tax,esda_dc,esda_dc_tax,esda_db,esda_db_tax,esda_stock,esda_stock_tax,esda_write_off,esda_house_bf,esda_pay ");
		sql.append("from EmSalaryData es ");
		sql.append("inner join (select gid,emba_name from EmBase) em on em.gid=es.gid ");
		sql.append("inner join (select SUM(esda_ttv_state)ttv_state,COUNT(*)cou,a.cid,a.ownmonth,coba_shortname,coba_gzaddname from EmSalaryData a left join cobase b on a.cid=b.CID where datediff(d,esda_payment_date,?)=0 group by a.cid,a.ownmonth,coba_shortname,coba_gzaddname ) co on co.CID=es.cid and co.ownmonth=es.ownmonth ");
		sql.append("where DATEDIFF(d,esda_payment_date,?)=0 " + str);
		//System.out.println(sql);
		try {
			PreparedStatement psmt = conn.getpre(sql.toString());
			psmt.setString(1, date);
			psmt.setString(2, date);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				cid = rs.getInt("cid");
				ownmonth =String.valueOf(rs.getInt("ownmonth")).substring(4);
				key=Integer.parseInt(String.valueOf(cid)+ownmonth);
				esdaM = new EmSalaryDataModel();
				esdaM.setCid(cid);
				esdaM.setCoba_shortname(rs.getString("coba_shortname"));
				esdaM.setEsda_id(rs.getInt("esda_id"));
				esdaM.setGid(rs.getInt("gid"));
				esdaM.setOwnmonth(rs.getInt("ownmonth"));
				esdaM.setName(rs.getString("emba_name"));
				esdaM.setEsda_usage_type(rs.getInt("esda_usage_type"));
				esdaM.setEsda_fd_remark(rs.getString("esda_fd_remark"));
				esdaM.setEsda_rp_reason(rs.getInt("esda_rp_reason"));
				esdaM.setEsda_siop(rs.getBigDecimal("esda_siop"));
				esdaM.setEsda_hafop(rs.getBigDecimal("esda_hafop"));
				esdaM.setEsda_total_pretax(rs
						.getBigDecimal("esda_total_pretax"));
				esdaM.setEsda_tax_base(rs.getBigDecimal("esda_tax_base"));
				esdaM.setEsda_tax(rs.getBigDecimal("esda_tax"));
				esdaM.setEsda_dc(rs.getBigDecimal("esda_dc"));
				esdaM.setEsda_dc_tax(rs.getBigDecimal("esda_dc_tax"));
				esdaM.setEsda_db(rs.getBigDecimal("esda_db"));
				esdaM.setEsda_db_tax(rs.getBigDecimal("esda_db_tax"));
				esdaM.setEsda_stock(rs.getBigDecimal("esda_stock"));
				esdaM.setEsda_stock_tax(rs.getBigDecimal("esda_stock_tax"));
				esdaM.setEsda_write_off(rs.getBigDecimal("esda_write_off"));
				esdaM.setEsda_house_bf(rs.getBigDecimal("esda_house_bf"));
				esdaM.setEsda_pay(rs.getBigDecimal("esda_pay"));
				if (!map.containsKey(key)) {
					m = new EmSalaryHistoryDataModel();
					esdaList = new ArrayList<EmSalaryDataModel>();
					esdaList.add(esdaM);
					m.setEsdaList(esdaList);
					m.setCid(cid);
					m.setCoba_shortname(esdaM.getCoba_shortname());
					m.setOwnmonth(esdaM.getOwnmonth());
					map.put(key, m);
				} else {
					m = map.get(key);
					m.getEsdaList().add(esdaM);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	// 工资电汇审核数据按公司统计
	public void getTTVDataTotal(Map<Integer, EmSalaryHistoryDataModel> map,
			String date, String str) {
		EmSalaryDataModel sumModel;
		EmSalaryHistoryDataModel m;
		int cid;
		String ownmonth;
		Integer key;
		StringBuilder sql = new StringBuilder();
		sql.append("select es.*,isnull(cg1.cfsa_PaidIn,0) as gz_paidin,isnull(cg2.cfsa_PaidIn,0) as gs_paidin,isnull(cg3.cfsa_PaidIn,0) as csfee_paidin,coco.coco_gzmonth from ");
		// 工资数据合计
		sql.append("(");
		sql.append("select es.ownmonth,es.cid,ISNULL(SUM(esda_siop),0) AS esda_siop,ISNULL(SUM(esda_hafop),0) AS esda_hafop,ISNULL(SUM(esda_total_pretax),0) AS esda_total_pretax,");
		sql.append("ISNULL(SUM(esda_tax_base),0) AS esda_tax_base,ISNULL(SUM(esda_tax),0) AS esda_tax,ISNULL(SUM(esda_dc),0) AS esda_dc,ISNULL(SUM(esda_dc_tax),0) AS esda_dc_tax,");
		sql.append("ISNULL(SUM(esda_db),0) AS esda_db,ISNULL(SUM(esda_db_tax),0) AS esda_db_tax,ISNULL(SUM(esda_stock),0) AS esda_stock,ISNULL(SUM(esda_stock_tax),0) AS esda_stock_tax,");
		sql.append("ISNULL(SUM(esda_write_off),0) AS esda_write_off,ISNULL(SUM(esda_house_bf),0) AS esda_house_bf,ISNULL(SUM(esda_pay),0) AS esda_pay,ISNULL(co.ttv_state,0) as ttv_state,ISNULL(co.cou,0) as cou ");
		sql.append("from EmSalaryData es ");
		sql.append("left join (select SUM(esda_ttv_state)ttv_state,COUNT(*)cou,a.cid,a.ownmonth,coba_shortname,coba_gzaddname from EmSalaryData a left join cobase b on a.cid=b.CID where datediff(d,esda_payment_date,?)=0 group by a.cid,a.ownmonth,coba_shortname,coba_gzaddname) co on co.CID=es.cid and co.ownmonth=es.ownmonth ");
		sql.append("where DATEDIFF(d,esda_payment_date,?)=0 " + str
				+ " group BY es.ownmonth,es.cid,co.ttv_state,co.cou");
		sql.append(" )es ");
		// 公司收款
		/*sql.append("left join (");
		sql.append("select ct.cid,cb.ownmonth,cfsa_PaidIn=isnull(SUM(cfsa_PaidIn),0) from CoFinanceMonthlyBillSortAccount ca ");
		sql.append("inner join CoFinanceMonthlyBill cb on ca.cfsa_cfmb_number=cb.cfmb_number ");
		sql.append("inner join CoFinanceTotalAccount ct on ct.cfta_id=cb.cfmb_cfta_id ");
		sql.append("where cfsa_cpac_name='税后工资' group by ct.cid,cb.ownmonth");
		sql.append(" )cg1 on es.cid=cg1.cid and es.ownmonth=cg1.ownmonth ");
		
		sql.append("left join (");
		sql.append("select ct.cid,cb.ownmonth,cfsa_PaidIn=isnull(SUM(cfsa_PaidIn),0) from CoFinanceMonthlyBillSortAccount ca ");
		sql.append("inner join CoFinanceMonthlyBill cb on ca.cfsa_cfmb_number=cb.cfmb_number ");
		sql.append("inner join CoFinanceTotalAccount ct on ct.cfta_id=cb.cfmb_cfta_id ");
		sql.append("where cfsa_cpac_name='个调税' group by ct.cid,cb.ownmonth");
		sql.append(" )cg2 on es.cid=cg2.cid and es.ownmonth=cg2.ownmonth ");

		sql.append("left join (");
		sql.append("select ct.cid,cb.ownmonth,cfsa_PaidIn=isnull(SUM(cfsa_PaidIn),0) from CoFinanceMonthlyBillSortAccount ca ");
		sql.append("inner join CoFinanceMonthlyBill cb on ca.cfsa_cfmb_number=cb.cfmb_number ");
		sql.append("inner join CoFinanceTotalAccount ct on ct.cfta_id=cb.cfmb_cfta_id ");
		sql.append("where cfsa_cpac_name='财务服务费' group by ct.cid,cb.ownmonth");
		sql.append(" )cg3 on es.cid=cg3.cid and es.ownmonth=cg3.ownmonth ");*/

		//临时sql语句，上面才是正式语句
		sql.append("left join (select cid,ownmonth,sum(cfss_Receivable)cfsa_PaidIn from CoFinanceSortAccountss where cfss_Receivable!=0 and cfss_cpac_name='税后工资'	group by cid,ownmonth )cg1 on es.cid=cg1.cid and es.ownmonth=cg1.ownmonth ");
		sql.append("left join (select cid,ownmonth,sum(cfss_Receivable)cfsa_PaidIn from CoFinanceSortAccountss where cfss_Receivable!=0 and cfss_cpac_name='个调税'	group by cid,ownmonth)cg2 on es.cid=cg2.cid and es.ownmonth=cg2.ownmonth ");
		sql.append("left join (select cid,ownmonth,sum(cfss_Receivable)cfsa_PaidIn from CoFinanceSortAccountss where cfss_Receivable!=0 and cfss_cpac_name='财务服务费'	group by cid,ownmonth)cg3 on es.cid=cg3.cid and es.ownmonth=cg3.ownmonth ");
		
		
		sql.append("left join (");
		sql.append("select a.cid,a.coco_gzmonth ");
		sql.append("from CoCompact a ");
		sql.append("where exists( ");
		sql.append("select * from ");
		sql.append("(select max(coco_id)as coco_id,cid from CoCompact where isnull(coco_gzperfee,'0')>0 group by cid)b ");
		sql.append("where a.coco_id=b.coco_id) ");
		sql.append(" )coco on es.cid=coco.cid ");
		//System.out.println(sql);
		try {
			PreparedStatement psmt = conn.getpre(sql.toString());
			psmt.setString(1, date);
			psmt.setString(2, date);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				cid = rs.getInt("cid");
				ownmonth =String.valueOf(rs.getInt("ownmonth")).substring(4);
				key=Integer.parseInt(String.valueOf(cid)+ownmonth);
				if (map.containsKey(key)) {
					sumModel = new EmSalaryDataModel();
					sumModel.setCid(cid);
					sumModel.setEsda_siop(rs.getBigDecimal("esda_siop"));
					sumModel.setEsda_hafop(rs.getBigDecimal("esda_hafop"));
					sumModel.setEsda_total_pretax(rs
							.getBigDecimal("esda_total_pretax"));
					sumModel.setEsda_tax_base(rs.getBigDecimal("esda_tax_base"));
					sumModel.setEsda_tax(rs.getBigDecimal("esda_tax"));
					sumModel.setEsda_dc(rs.getBigDecimal("esda_dc"));
					sumModel.setEsda_dc_tax(rs.getBigDecimal("esda_dc_tax"));
					sumModel.setEsda_db(rs.getBigDecimal("esda_db"));
					sumModel.setEsda_db_tax(rs.getBigDecimal("esda_db_tax"));
					sumModel.setEsda_stock(rs.getBigDecimal("esda_stock"));
					sumModel.setEsda_stock_tax(rs
							.getBigDecimal("esda_stock_tax"));
					sumModel.setEsda_write_off(rs
							.getBigDecimal("esda_write_off"));
					sumModel.setEsda_house_bf(rs.getBigDecimal("esda_house_bf"));
					sumModel.setEsda_pay(rs.getBigDecimal("esda_pay"));
					sumModel.setCsfee_paidin(rs.getBigDecimal("csfee_paidin"));
					sumModel.setGz_paidin(rs.getBigDecimal("gz_paidin"));
					sumModel.setGs_paidin(rs.getBigDecimal("gs_paidin"));
					sumModel.setCou(rs.getInt("cou"));
					sumModel.setTtv_state(rs.getInt("ttv_state"));
					sumModel.setEsda_fd_remark(rs.getString("coco_gzmonth"));
					m = map.get(key);
					m.setSumModel(sumModel);
					m.setEsdaListSize();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 个人支付木块模块数据
	public List<EmPayModel> getEmPayList(String date) {
		List<EmPayModel> list = new ListModelList<EmPayModel>();
		EmPayModel m;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT a.cid,a.gid,a.ownmonth,a.empa_fee,a.empa_account,a.empa_bank,a.empa_ifpay,a.empa_remark,empa_name,b.coba_shortname,empa_class,empa_ba_name,isnull(b.coba_ufclass,'') as coba_ufclass,isnull(b.coba_ufid,'') as coba_ufid ");
		sql.append("FROM EmPay a LEFT JOIN cobase b ON a.cid=b.cid ");
		sql.append("WHERE a.empa_payclass='银行支付' AND DATEDIFF(d,a.empa_cashier_checkdate,?)=0 AND a.empa_state<>0 order by b.coba_shortname,empa_name");
		try {
			PreparedStatement psmt = conn.getpre(sql.toString());
			psmt.setString(1, date);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new EmPayModel();
				m.setCid(rs.getInt("cid"));
				m.setGid(rs.getInt("gid"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setEmpa_fee(rs.getBigDecimal("empa_fee"));
				m.setEmpa_account(rs.getString("empa_account"));
				m.setEmpa_bank(rs.getString("empa_bank"));
				m.setEmpa_ifpay(rs.getInt("empa_ifpay"));
				m.setEmpa_remark(rs.getString("empa_remark"));
				m.setEmba_name(rs.getString("empa_name"));
				m.setCoba_shortname(rs.getString("coba_shortname"));
				m.setEmpa_class(rs.getString("empa_class"));
				m.setEmpa_ba_name(rs.getString("empa_ba_name"));
				m.setCoba_ufclass(rs.getString("coba_ufclass"));
				m.setCoba_ufid(rs.getString("coba_ufid"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 个人支付木块模块总计
	public EmPayModel getEmPayTotal(String date) {
		EmPayModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT isnull(sum(empa_fee),0) as empa_fee ");
		sql.append("FROM EmPay ");
		sql.append("WHERE empa_payclass='银行支付' AND DATEDIFF(d,empa_cashier_checkdate,?)=0");
		try {
			PreparedStatement psmt = conn.getpre(sql.toString());
			psmt.setString(1, date);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new EmPayModel();
				m.setEmpa_fee(rs.getBigDecimal("empa_fee"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	// 更新支付模块数据
	public int upEmPay(String date) {
		String sql = "UPDATE EmPay SET empa_ifpay=1 WHERE empa_ifpay=0 and empa_state<>0 AND empa_payclass='银行支付' AND DATEDIFF(d,empa_cashier_checkdate,'"
				+ date + "')=0";
		try {
			PreparedStatement psmt = conn.getpre(sql);
			psmt.execute();
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
	}

	// 历史记录工资加支付模块金额总计
	public EmPayModel getEmGZandPayTotal(String date) {
		EmPayModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select SUM(pay) as pay from( ");
		sql.append("SELECT ISNULL(SUM(eshd_pay),0) AS pay FROM EmSalaryHistoryData WHERE DATEDIFF(d,eshd_history_date,?)=0 ");
		sql.append("union all  ");
		sql.append("SELECT ISNUll(SUM(empa_fee),0) AS pay FROM EmPay WHERE empa_payclass='银行支付' AND DATEDIFF(d,empa_cashier_checkdate,?)=0  ");
		sql.append(")a");
		try {
			PreparedStatement psmt = conn.getpre(sql.toString());
			psmt.setString(1, date);
			psmt.setString(2, date);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new EmPayModel();
				m.setEmpa_fee(rs.getBigDecimal("pay"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	// 导出历史用友工资+个税金额数据
	public List<EmSalaryDataModel> getHistoryGZUftxt(String date) {
		List<EmSalaryDataModel> list = new ArrayList<EmSalaryDataModel>();
		EmSalaryDataModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT convert(bigint,convert(varchar(6),a.cid)+convert(varchar(6),a.ownmonth)) as cokey,c.coba_shortname,isnull(c.coba_ufid,'') as coba_ufid,isnull(c.coba_ufclass,'') as coba_ufclass,a.ownmonth,a.cid,chk_cgli,case chk_cgli when 1 then ISNULL(SUM(eshd_tax+eshd_db_tax+eshd_dc_tax+eshd_stock_tax+eshd_lw_tax),0)+ISNULL(SUM(eshd_pay),0) else ISNULL(SUM(eshd_pay),0) end as total ");
		sql.append("FROM EmSalaryHistoryData a ");
		sql.append("left join (  ");
		sql.append("select 1 as chk_cgli,cogl.gid from CoGList cogl ");
		sql.append("left join CoOfferList coli on cogl.cgli_coli_id=coli.coli_id left join CoOffer coof on coli.coli_coof_id=coof.coof_id ");
		sql.append("left join CoCompact coco on coof.coof_coco_id=coco.coco_id ");
		sql.append("where cgli_state=1 and coco.coco_gs='中智开户' and coli.coli_name in(select copr_name from CoProduct where copr_name like '%税%') ");
		sql.append("group by cogl.gid ");
		sql.append(")b ");
		sql.append("on a.gid=b.gid ");
		sql.append("left join cobase c on a.cid=c.CID ");
		sql.append("WHERE DATEDIFF(d,eshd_history_date,?)=0 ");
		sql.append("GROUP BY c.coba_shortname,c.coba_ufid,c.coba_ufclass,a.cid,a.ownmonth,chk_cgli ");
		sql.append("order BY cokey");
		// System.out.println(sql);
		try {
			PreparedStatement psmt = conn.getpre(sql.toString());
			psmt.setString(1, date);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new EmSalaryDataModel();
				m.setCoba_shortname(rs.getString("coba_shortname"));
				m.setCoba_ufid(rs.getString("coba_ufid"));
				m.setCoba_ufclass(rs.getString("coba_ufclass"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setEsda_pay(rs.getBigDecimal("total"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 导出历史用户个税数据
	public List<EmSalaryDataModel> getHistoryTaxUftxt(String date) {
		List<EmSalaryDataModel> list = new ArrayList<EmSalaryDataModel>();
		EmSalaryDataModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT a.ownmonth,ISNULL(SUM(eshd_tax+eshd_db_tax+eshd_dc_tax+eshd_stock_tax+eshd_lw_tax),0) AS eshd_tax ");
		sql.append("FROM EmSalaryHistoryData a inner join ( ");
		sql.append("select 1 as chk_cgli,cogl.gid from CoGList cogl ");
		sql.append("left join CoOfferList coli on cogl.cgli_coli_id=coli.coli_id left join CoOffer coof on coli.coli_coof_id=coof.coof_id ");
		sql.append("left join CoCompact coco on coof.coof_coco_id=coco.coco_id ");
		sql.append("where cgli_state=1 and coco.coco_gs='中智开户' and coli.coli_name in(select copr_name from CoProduct where copr_name like '%税%') ");
		sql.append("group by cogl.gid ");
		sql.append(")b ");
		sql.append("on a.gid=b.gid ");
		sql.append("WHERE DATEDIFF(d,eshd_history_date,?)=0 ");
		sql.append("GROUP BY a.ownmonth");
		// System.out.println(sql);
		try {
			PreparedStatement psmt = conn.getpre(sql.toString());
			psmt.setString(1, date);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new EmSalaryDataModel();
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setEsda_tax(rs.getBigDecimal("eshd_tax"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
