package dal.EmFinanceManage;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.SbGatherModel;

public class SbGatherDal {

	// 获取社保收款
	public List<SbGatherModel> getSbGatherList(String str, String ownmonth,
			String ifsingle) {
		List<SbGatherModel> list = new ArrayList<SbGatherModel>();
		String strsql = "", strown = "", strsql2 = "", strsql3 = "";
		if (ownmonth != null && !ownmonth.equals("")) {
			strown = strown + " and ownmonth=" + ownmonth;
		}
		if (ifsingle != null && !ifsingle.equals("")) {
			strsql = strsql + " and esiu_single=" + ifsingle
					+ " union all select cid,ownmonth from emshebaoszsibj "
					+ "where  essb_single=" + ifsingle;
			strsql2 = " and esiu_single=" + ifsingle;
			strsql3 = " and essb_single=" + ifsingle;
		}
		String sql = "select z.cid,isnull(total,0)+isnull(d.bj_total,0) sbktotal,c.coba_company,z.ownmonth,"
				+ " c.coba_client,isnull(coba_ufid2,'')coba_ufid2,isnull(coba_ufclass,'')coba_ufclass,"
				+ " isnull(coab_name,'')coab_name,isnull(d.bj_total,0) sbbjtotal "
				+ " from (select distinct(cid) cid,Ownmonth"
				+ " from (select cid,Ownmonth from EmShebao "
				+ " where 1=1 "
				+ strsql
				+ strown
				+ ")y)z "
				+ " left join (select cid, (sum(esiu_totalcp)+sum(esiu_totalop))total "
				+ " from EmShebao where 1=1 "
				+ strsql2
				+ strown
				+ " group by cid)a on a.cid=z.cid left join (select cid,(sum(essb_total))bj_total "
				+ " from emshebaoszsibj where 1=1 "
				+ strsql3
				+ strown
				+ " group by cid)d on z.cid=d.cid left join "
				+ " cobase c on z.cid=c.cid "
				+ " left join ( select distinct(cid),coab_name from CoCompact a inner join StAgencyBase_view c "
				+ "on a.cabc_id=c.coab_id where a.cabc_id is not null and a.coco_state!=9) coag on a.CID=coag.cid"
				+ " where 1=1" + str + " order by coba_ufid2";
		System.out.println(sql);
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				SbGatherModel m = new SbGatherModel();
				m.setCid(rs.getInt("cid"));
				m.setCoba_client(rs.getString("coba_client"));
				m.setCoba_company(rs.getString("coba_company"));
				m.setCoba_ufclass(rs.getString("coba_ufclass"));
				m.setCoba_ufid2(rs.getString("coba_ufid2"));
				m.setSbbjtotal(rs.getBigDecimal("sbbjtotal"));
				m.setSbktotal(rs.getBigDecimal("sbktotal"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setCoab_name(rs.getString("coab_name"));
				list.add(m);
			}
		} catch (Exception e) {

		}
		MatchUfclass(list);
		return list;
	}

	public List<SbGatherModel> getCfsa_total(int ownmonth) {
		List<SbGatherModel> list = new ArrayList<SbGatherModel>();
		String sql = "select cid,sum(cfsa_PaidIn) cfsa_total,ownmonth from CoFinanceMonthlyBillSortAccount a"
				+ " inner join CoFinanceMonthlyBill b  on a.cfsa_cfmb_number=b.cfmb_number"
				+ " inner join CoFinanceTotalAccount c on  b.cfmb_cfta_id=c.cfta_id "
				+ "where a.cfsa_cpac_name='社保费' and ownmonth="
				+ ownmonth
				+ " GROUP BY cid,ownmonth";
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				SbGatherModel m = new SbGatherModel();
				m.setCfsa_total(rs.getBigDecimal("cfsa_total"));
				m.setCid(rs.getInt("cid"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				list.add(m);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	private void MatchUfclass(List<SbGatherModel> list) {
		String ufclass = "";
		for (SbGatherModel m : list) {
			if (m.getCoba_ufclass() == null && m.getCoba_ufid2() != null
					&& !m.getCoba_ufid2().equals("")) {
				for (SbGatherModel m2 : list) {
					if (m2.getCoba_ufclass() != null
							&& !m2.getCoba_ufclass().equals("")
							&& m2.getCoba_ufid2() != null
							&& !m2.getCoba_ufid2().equals("")) {
						if (m.getCoba_ufid2().equals(m2.getCoba_ufid2())) {
							m.setCoba_ufclass(m2.getCoba_ufclass());
							break;
						}
					}
				}
			}
		}
	}

	// 获取社保中智户或者委托户总额
	public BigDecimal[] getSbTotalInfo(String ownmonth) {
		BigDecimal[] s = new BigDecimal[4];// s[0]表示中智，s[1]表示委托，s[2]表示外包，s[3]表示派遣
		BigDecimal zz = new BigDecimal(0.00);
		BigDecimal wt = new BigDecimal(0.00);
		BigDecimal wb = new BigDecimal(0.00);
		BigDecimal pq = new BigDecimal(0.00);
		String sql = "select ((select isnull(sum(esiu_totalcp+esiu_totalop),0) "
				+ " from emshebao where esiu_single=0 and ownmonth="
				+ ownmonth
				+ ")+(select "
				+ " isnull(sum(essb_total),0) from emshebaoszsibj where essb_single=0 "
				+ " and ownmonth="
				+ ownmonth
				+ "))zz,((select isnull(sum(esiu_totalcp+esiu_totalop),0) "
				+ " from emshebao where esiu_single=2 "
				+ " and ownmonth="
				+ ownmonth
				+ ")+(select isnull(sum(essb_total),0) from emshebaoszsibj"
				+ " where essb_single=2 and ownmonth="
				+ ownmonth
				+ "))wt,"
				+ "((select isnull(sum(esiu_totalcp+esiu_totalop),0) "
				+ " from emshebao where esiu_single=3 "
				+ " and ownmonth="
				+ ownmonth
				+ ")+(select isnull(sum(essb_total),0) from emshebaoszsibj"
				+ " where essb_single=3 and ownmonth="
				+ ownmonth
				+ "))wb,"
				+ "((select isnull(sum(esiu_totalcp+esiu_totalop),0) "
				+ " from emshebao where esiu_single=4 "
				+ " and ownmonth="
				+ ownmonth
				+ ")+(select isnull(sum(essb_total),0) from emshebaoszsibj"
				+ " where essb_single=4 and ownmonth=" + ownmonth + "))pq";
		dbconn db = new dbconn();
		try {

			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				zz = rs.getBigDecimal("zz");
				wt = rs.getBigDecimal("wt");
				wb = rs.getBigDecimal("wb");
				pq = rs.getBigDecimal("pq");
			}
		} catch (Exception e) {
			e.getMessage();
		}

		s[0] = zz;
		s[1] = wt;
		s[2] = wb;
		s[3] = pq;
		return s;
	}

	// 获取公积金收款
	public List<SbGatherModel> getHhouseGatherList(String str, String ownmonth,
			String ifsingle) {
		List<SbGatherModel> list = new ArrayList<SbGatherModel>();
		String strsql = "", strown = "", fice_owm = "";
		if (ownmonth != null && !ownmonth.equals("")) {
			strown = strown + " and ownmonth=" + ownmonth;
			fice_owm = fice_owm + " and emhu_finance_ownmonth=" + ownmonth;
		}
		if (ifsingle != null && !ifsingle.equals("")) {
			strsql = strsql + "  and emhu_single=" + ifsingle;
		}
		String sql = "select a.cid,isnull(total,0) total,c.coba_company,isnull(cfsa_total,0) cfsa_total,"
				+ "c.coba_client,coba_ufid2,coba_ufclass,a.ownmonth,coab_name from (select cid,emhu_finance_ownmonth ownmonth,(sum(emhu_total))total "
				+ "from EmHouse where 1=1 and emhu_single<>1 "
				+ fice_owm
				+ strsql
				+ " group by cid,emhu_finance_ownmonth)a "
				+ "left join (select cid,sum(cfsa_PaidIn) cfsa_total,ownmonth from "
				+ "CoFinanceMonthlyBillSortAccount a inner join CoFinanceMonthlyBill b "
				+ "on a.cfsa_cfmb_number=b.cfmb_number inner join CoFinanceTotalAccount c on "
				+ "b.cfmb_cfta_id=c.cfta_id where a.cfsa_cpac_name='住房公积金' "
				+ strown
				+ ""
				+ " GROUP BY cid,ownmonth)b "
				+ "on a.cid=b.cid left join cobase c on a.cid=c.cid "
				+ " left join ( select distinct a.cid,coab_name from CoCompact a inner join StAgencyBase_view c "
				+ "on a.cabc_id=c.coab_id where a.cabc_id is not null and coco_state!=9) coag on a.CID=coag.cid"
				+ " where 1=1 " + str + " order by coba_ufid2";
		System.out.println(sql);
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				SbGatherModel m = new SbGatherModel();
				m.setCfsa_total(rs.getBigDecimal("cfsa_total"));
				m.setCid(rs.getInt("cid"));
				m.setCoba_client(rs.getString("coba_client"));
				m.setCoba_company(rs.getString("coba_company"));
				m.setCoba_ufclass(rs.getString("coba_ufclass"));
				m.setCoba_ufid2(rs.getString("coba_ufid2"));
				m.setHstotal(rs.getBigDecimal("total"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setCoab_name(rs.getString("coab_name"));
				list.add(m);
			}
		} catch (Exception e) {

		}
		return list;
	}

	// 获取公积金中智户或者委托户总额
	public BigDecimal[] getHsTotalInfo(String ownmonth) {
		BigDecimal[] s = new BigDecimal[2];// s[0]表示中智，s[1]表示委托
		BigDecimal zz = new BigDecimal(0.00);
		BigDecimal wt = new BigDecimal(0.00);
		String sql = "select isnull(sum(emhu_total),0) zz from "
				+ "EmHouse where emhu_single=0 and emhu_finance_ownmonth="
				+ ownmonth;

		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				zz = rs.getBigDecimal("zz");

			}
		} catch (Exception e) {

		}
		s[0] = zz;

		return s;
	}

	// 获取公积金补缴收款
	public List<SbGatherModel> getHhouseBjGatherList(String str,
			String ownmonth, String ifsingle) {
		List<SbGatherModel> list = new ArrayList<SbGatherModel>();
		String strsql = "", strown = "", biowm = "";
		;
		if (ownmonth != null && !ownmonth.equals("")) {
			strown = strown + " and ownmonth=" + ownmonth;
			biowm = biowm + " and emhb_finance_ownmonth=" + ownmonth;
		}
		if (ifsingle != null && !ifsingle.equals("")) {
			strsql = strsql + "  and emhb_single=" + ifsingle;
		}
		String sql = "select a.cid,isnull(total,0) total,c.coba_company,isnull(cfsa_total,0) cfsa_total,"
				+ " c.coba_client,coba_ufid2,coba_ufclass,a.ownmonth,coab_name from (select cid,emhb_finance_ownmonth ownmonth,(sum(emhb_total))total "
				+ " from EmHouseBJ where 1=1 "
				+ biowm
				+ " and emhb_single<>1 and emhb_ifdeclare=1 "
				+ strsql
				+ " group by cid,emhb_finance_ownmonth)a left join (select cid,sum(cfsa_PaidIn) cfsa_total,ownmonth "
				+ " from CoFinanceMonthlyBillSortAccount a inner join CoFinanceMonthlyBill b "
				+ " on a.cfsa_cfmb_number=b.cfmb_number inner join CoFinanceTotalAccount c on "
				+ " b.cfmb_cfta_id=c.cfta_id where a.cfsa_cpac_name='住房公积金' "
				+ strown
				+ " GROUP BY cid,ownmonth)b on a.cid=b.cid left join cobase c "
				+ " on a.cid=c.cid "
				+ " left join ( select distinct a.cid,coab_name from CoCompact a inner join StAgencyBase_view c "
				+ " on a.cabc_id=c.coab_id where a.cabc_id is not null and coco_state!=9) coag on a.CID=coag.cid"
				+ " where 1=1 " + str + " order by coba_ufid2";
		dbconn db = new dbconn();
		System.out.print(sql);
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				SbGatherModel m = new SbGatherModel();
				m.setCfsa_total(rs.getBigDecimal("cfsa_total"));
				m.setCid(rs.getInt("cid"));
				m.setCoba_client(rs.getString("coba_client"));
				m.setCoba_company(rs.getString("coba_company"));
				m.setCoba_ufclass(rs.getString("coba_ufclass"));
				m.setCoba_ufid2(rs.getString("coba_ufid2"));
				m.setHsbjtotal(rs.getBigDecimal("total"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setCoab_name(rs.getString("coab_name"));
				list.add(m);
			}
		} catch (Exception e) {

		}
		return list;
	}

	// 获取公积金补缴中智户或者委托户总额
	public BigDecimal[] getHsBjTotalInfo(String ownmonth) {
		BigDecimal[] s = new BigDecimal[2];// s[0]表示中智，s[1]表示委托
		BigDecimal zz = new BigDecimal(0.00);
		BigDecimal wt = new BigDecimal(0.00);
		String sql = "select isnull(sum(emhb_total),0)zz from EmHouseBJ "
				+ "where emhb_single=0 and emhb_ifdeclare=1 and emhb_finance_ownmonth="
				+ ownmonth;
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				zz = rs.getBigDecimal("zz");
				// wt = rs.getBigDecimal("wt");
			}
		} catch (Exception e) {

		}
		s[0] = zz;
		// s[1] = wt;
		return s;
	}
}
