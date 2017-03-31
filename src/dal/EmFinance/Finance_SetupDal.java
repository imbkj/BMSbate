package dal.EmFinance;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.CoFinanceWtModel;
import Model.EmFinanceZYTModel;

public class Finance_SetupDal {
	// 查询委托进数据信息
	public List<EmFinanceZYTModel> getEmFinanceList(String str) {
		dbconn db = new dbconn();
		List<EmFinanceZYTModel> list = new ArrayList<EmFinanceZYTModel>();
		String sql = "select ownmonth,cabc_id,name,coab_name,"
				+ "SUM(emfz_total) AS total from EmFinanceZYT a left join (select gid,cid,"
				+ "p.cabc_id,name,coab_name,coco_id from (select distinct(j.gid) gid,"
				+ "coco_id,b.cid,b.cabc_id from CoCompact b left join (select "
				+ "max(cgli_id) cgli_id,gid,a.cid,coli_coco_id from CoGList a "
				+ "left join CoOfferList b on a.cgli_coli_id=b.coli_id left join "
				+ "CoCompact c on b.coli_coco_id=c.coco_id where "
				+ "isnull(cgli_stopdate,220023)>=cgli_startdate GROUP BY cgli_id,"
				+ "gid,a.cid,coli_coco_id) j on b.coco_id=j.coli_coco_id) p inner "
				+ "join (select distinct(coab_name) coab_name,p.cabc_id,name from "
				+ "CoCompact p inner join(select cabc_id,name,coab_name from "
				+ "CoAgencyBase e,CoAgencyBaseCityRel f,PubProCity g "
				+ "where e.coab_id=f.cabc_coab_id and g.id=f.cabc_ppc_id "
				+ "and name<>'深圳') o on p.cabc_id=o.cabc_id) mj "
				+ "on p.cabc_id=mj.cabc_id) m on a.cid=m.cid and a.gid=m.gid "
				+ " where 1=1 and cabc_id is not null "
				+ str
				+ "GROUP BY ownmonth,cabc_id,name,coab_name order by ownmonth desc";
		try {
			list = db.find(sql, EmFinanceZYTModel.class,
					dbconn.parseSmap(EmFinanceZYTModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询委托进数据信息
	public List<EmFinanceZYTModel> getEmFinanceLists(String str) {
		dbconn db = new dbconn();
		List<EmFinanceZYTModel> list = new ArrayList<EmFinanceZYTModel>();
		String sql = "select distinct(cabc_id) cabc_id,ownmonth,name,coab_name "
				+ "from (select ownmonth,cabc_id,name,coab_name "
				+ "from EmFinanceZYT a left join (select distinct(j.gid) gid,"
				+ "coco_id,b.cid,b.cabc_id,name,coab_name from CoCompact b "
				+ "left join (select max(cgli_id) cgli_id,gid,a.cid,"
				+ "coli_coco_id from CoGList a left join CoOfferList b "
				+ "on a.cgli_coli_id=b.coli_id left join CoCompact c "
				+ "on b.coli_coco_id=c.coco_id where "
				+ "isnull(cgli_stopdate,220023)>=cgli_startdate  GROUP BY "
				+ "cgli_id,gid,a.cid,coli_coco_id) j on "
				+ "b.coco_id=j.coli_coco_id inner join (select cabc_id,name,"
				+ "coab_name from CoAgencyBase e,CoAgencyBaseCityRel f,"
				+ "PubProCity g where e.coab_id=f.cabc_coab_id "
				+ "and g.id=f.cabc_ppc_id and  name<>'深圳' and name is not null) h "
				+ "on b.cabc_id=h.cabc_id and b.cabc_id is not null and "
				+ "name is not null  GROUP BY j.gid,coco_id,b.cid,b.cabc_id,"
				+ "name,coab_name) b on a.cid=b.cid and a.gid=b.gid GROUP BY "
				+ "ownmonth,emfz_company,cabc_id,name,coab_name union all "
				+ "select o.ownmonth,cabc_id,name,coab_name from (select "
				+ "distinct(c.cid) cid,coco_id,b.cabc_id,name,coab_name,"
				+ "ownmonth from CoCompact b inner join (select cabc_id,"
				+ "name,coab_name from CoAgencyBase e,CoAgencyBaseCityRel f,"
				+ "PubProCity g where e.coab_id=f.cabc_coab_id and "
				+ "g.id=f.cabc_ppc_id and name<>'深圳' and name is not null) h "
				+ " on b.cabc_id=h.cabc_id and b.cabc_id is not null and "
				+ "name is not null inner join EmFinanceBase c on b.cid=c.cid) o "
				+ "GROUP BY o.ownmonth,cabc_id,name,coab_name) hg "
				+ "where name is not null" + str + " order by ownmonth desc";
		try {
			list = db.find(sql, EmFinanceZYTModel.class,
					dbconn.parseSmap(EmFinanceZYTModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据所属月份查询委托地区
	public List<String> getWtAreaList(String str) {
		String sql = "select distinct(name) name  from EmFinanceZYT l, (select distinct(name) name,gid,j.cid "
				+ "from CoCompact b left join (select max(cgli_id) cgli_id,coli_coco_id,a.gid,a.cid "
				+ "from CoGList a left join CoOfferList b on a.cgli_coli_id=b.coli_id left join CoCompact c "
				+ "on b.coli_coco_id=c.coco_id where isnull(cgli_stopdate,220023)>=cgli_startdate "
				+ " and gid is not null "
				+ "GROUP BY cgli_id,gid,a.cid,coli_coco_id,a.gid,a.cid ) j on b.coco_id=j.coli_coco_id "
				+ "inner join (select cabc_id,name from CoAgencyBase e,CoAgencyBaseCityRel f,PubProCity g "
				+ "where e.coab_id=f.cabc_coab_id and g.id=f.cabc_ppc_id) h "
				+ "on b.cabc_id=h.cabc_id and b.cabc_id is not null and name is not null and gid is not null and name<>'深圳' "
				+ "GROUP BY name,gid,j.cid) k where  l.cid=k.cid and l.gid=k.gid "
				+ str;
		List<String> list = new ArrayList<String>();
		dbconn db = new dbconn();
		ResultSet rs = null;
		try {
			rs = db.GRS(sql);
			list.add("");
			while (rs.next()) {
				list.add(rs.getString("name"));
			}

		} catch (Exception e) {

		}

		return list;
	}

	// 根据委托地区和所属月份查找委托机构
	public List<String> getSetupList(String str) {
		String sql = "select distinct(coab_name) coab_name from EmFinanceZYT l, (select distinct(coab_name) coab_name,name,gid,j.cid "
				+ "from CoCompact b left join (select max(cgli_id) cgli_id,coli_coco_id,a.gid,a.cid "
				+ "from CoGList a left join CoOfferList b on a.cgli_coli_id=b.coli_id left join CoCompact c "
				+ "on b.coli_coco_id=c.coco_id where isnull(cgli_stopdate,220023)>=cgli_startdate "
				+ " and gid is not null "
				+ "GROUP BY cgli_id,gid,a.cid,coli_coco_id,a.gid,a.cid ) j on b.coco_id=j.coli_coco_id "
				+ "inner join (select cabc_id,coab_name,name from CoAgencyBase e,CoAgencyBaseCityRel f,PubProCity g "
				+ "where e.coab_id=f.cabc_coab_id and g.id=f.cabc_ppc_id ) h "
				+ "on b.cabc_id=h.cabc_id and b.cabc_id is not null and coab_name is not null  "
				+ "GROUP BY coab_name,gid,j.cid,name) k where  l.cid=k.cid and l.gid=k.gid "
				+ str;
		List<String> list = new ArrayList<String>();
		dbconn db = new dbconn();
		ResultSet rs = null;
		try {
			rs = db.GRS(sql);
			list.add("");
			while (rs.next()) {
				list.add(rs.getString("coab_name"));
			}

		} catch (Exception e) {

		}

		return list;
	}

	public List<EmFinanceZYTModel> getCoabList(String str) {
		List<EmFinanceZYTModel> list = new ArrayList<EmFinanceZYTModel>();
		String sql = "select distinct(a.cabc_id) cabc_id, name,coab_name from CoCompact a "
				+ "inner join (select cabc_id,name,coab_name from CoAgencyBase e,CoAgencyBaseCityRel f,"
				+ "PubProCity g where e.coab_id=f.cabc_coab_id and g.id=f.cabc_ppc_id ) b "
				+ "on a.cabc_id=b.cabc_id  where 1=1"
				+ str
				+ "group by a.cabc_id, name,coab_name";
		dbconn db = new dbconn();
		try {
			list = db.find(sql, EmFinanceZYTModel.class,
					dbconn.parseSmap(EmFinanceZYTModel.class));
		} catch (Exception e) {

		}
		return list;
	}

	public List<Integer> getCidList(String str) {
		List<Integer> list = new ArrayList<Integer>();
		String sql = "select cid from CoCompact where 1=1 " + str;
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				list.add(rs.getInt("cid"));
			}
		} catch (Exception e) {

		}
		return list;
	}

	// 根据所属月份查询机构信息
	public List<EmFinanceZYTModel> getEmFinanceSetupList(Integer ownmonth) {
		List<EmFinanceZYTModel> list = new ArrayList<EmFinanceZYTModel>();
		String sql = "select distinct(coab_id) cabc_id,coab_city name,coab_name "
				+ "from StAgencyBase_view a inner join cocompact b on a.coab_id=b.cabc_id "
				+ "where coab_state=1 and coco_state>3 and (cid in("
				+ " select distinct(cid) from EmFinanceZYT where ownmonth= "
				+ ownmonth
				+ " union "
				+ " select distinct(a.cid)  from CoGList a left join CoOfferList b on a.cgli_coli_id=b.coli_id "
				+ "left join CoCompact c on b.coli_coco_id=c.coco_id where isnull(cgli_stopdate,220023)>=cgli_startdate "
				+ "and "
				+ ownmonth
				+ " between cgli_startdate and isnull(cgli_stopdate,220023)))";
		dbconn db = new dbconn();
		try {
			list = db.find(sql, EmFinanceZYTModel.class,
					dbconn.parseSmap(EmFinanceZYTModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据所属月份查询机构信息
	public List<EmFinanceZYTModel> getEmFinanceSetupListn(Integer ownmonth) {
		List<EmFinanceZYTModel> list = new ArrayList<EmFinanceZYTModel>();
		String sql = "select setup.cabc_id,name,coab_name,isnull(total,0) total,isnull(emfi_total,0)emfi_total " +
				"from(select distinct(coab_id) cabc_id,coab_city name,coab_name " +
				"from StAgencyBase_view a inner join cocompact b on a.coab_id=b.cabc_id " +
				"where coab_state=1 and coco_state>3 and (cid in(select distinct(cid) from EmFinanceZYT " +
				"where ownmonth= " +ownmonth+
				"union select distinct(a.cid)  from CoGList a left join CoOfferList b on a.cgli_coli_id=b.coli_id  " +
				"left join CoCompact c on b.coli_coco_id=c.coco_id where isnull(cgli_stopdate,220023)>=cgli_startdate" +
				" and "+ownmonth+" between cgli_startdate and isnull(cgli_stopdate,220023))))setup left join " +
				"(select isnull(sum(emfz_total),0) total,cabc_id from EmFinanceZYT a inner join CoCompact b " +
				"on a.cid=b.cid and coco_state>3 and ownmonth= "+ownmonth+" group by cabc_id)emfz " +
				"on setup.cabc_id=emfz.cabc_id left join (select SUM(Receivable)emfi_total,cabc_id " +
				"from (select ISNULL(efsb_Receivable,0) as Receivable,cabc_id from EmFinanceSheBao a " +
				"inner join CoCompact co on a.efsb_coco_id=co.coco_id where ownmonth= "+ownmonth+" and efsb_state=1 " +
				"union all select ISNULL(efhg_Receivable,0),cabc_id from EmFinanceHouseGjj a inner join CoCompact co " +
				"on a.efhg_coco_id=co.coco_id where ownmonth= "+ownmonth+" and efhg_state=1 " +
				"union all select ISNULL(efpr_Receivable,0),cabc_id from EmFinanceProduct a " +
				"inner join CoCompact co on a.efpr_coco_id=co.coco_id where ownmonth= "+ownmonth+" and efpr_state=1 " +
				"and efpr_cpac_name not in('税后工资','个调税','财务服务费') " +
				"union all select ISNULL(efdi_Receivable,0),cabc_id from EmFinanceDisposable a " +
				"inner join CoCompact co on a.efdi_coco_id=co.coco_id where ownmonth= "+ownmonth+" and efdi_state=1 " +
				"union all select ISNULL(cfpr_Receivable,0),cabc_id from CoFinanceProduct a inner join CoCompact co " +
				"on a.cfpr_coco_id=co.coco_id  where ownmonth= "+ownmonth+" and cfpr_state=1 " +
				"union all select ISNULL(cfdi_Receivable,0),cabc_id from CoFinanceDisposable a inner join CoCompact co " +
				"on a.cfdi_coco_id=co.coco_id where ownmonth= "+ownmonth+" and cfdi_state=1)b  group by cabc_id) emfi " +
						" on setup.cabc_id=emfi.cabc_id order by setup.cabc_id";
		dbconn db = new dbconn();
		try {
			list = db.find(sql, EmFinanceZYTModel.class,
					dbconn.parseSmap(EmFinanceZYTModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据所属月份和机构id计算智翼通的总额
	public BigDecimal getZytTotal(Integer ownmonth, Integer coab_id) {
		BigDecimal total = BigDecimal.ZERO;
		String sql = " select isnull(sum(emfz_total),0) emfz_total from EmFinanceZYT a "
				+ "inner join CoCompact b on a.cid=b.cid and coco_state>3 and ownmonth="
				+ ownmonth + " and cabc_id=" + coab_id;
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				total = rs.getBigDecimal("emfz_total");
			}
		} catch (Exception e) {

		}
		return total;
	}

	// 根据所属月份和机构id查询合同编号
	public int[] getCocoId(Integer ownmonth, Integer cabc_id) {
		int cocoid[] = null;
		String sql = " select distinct(coco_id) coco_id from cocompact "
				+ "where coco_state>3 and (cid in(select distinct(cid) from EmFinanceZYT "
				+ "where ownmonth="
				+ ownmonth
				+ ") or cid in(select distinct(a.cid) from CoGList a "
				+ "left join CoOfferList b on a.cgli_coli_id=b.coli_id left join "
				+ "CoCompact c on b.coli_coco_id=c.coco_id "
				+ "where isnull(cgli_stopdate,220023)>=cgli_startdate "
				+ "and " + ownmonth + " between cgli_startdate "
				+ "and isnull(cgli_stopdate,220023))) and cabc_id=" + cabc_id;
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			rs.last();
			Integer j = rs.getRow();
			cocoid = new int[j];
			int i = 0;
			rs.first();
			if (j > 0) {
				do {
					cocoid[i] = rs.getInt("coco_id");
					i++;
				} while (rs.next());
			}
		} catch (Exception e) {

		}
		return cocoid;
	}

	// 根据月份获取城市
	public List<String> getCoabCity(Integer ownmonth) {
		List<String> list = new ArrayList<String>();
		list.add("");
		try {
			String sql = "  select distinct(coab_city) coab_city from StAgencyBase_view a inner join "
					+ "(select * from cocompact where (cid in(select distinct(a.cid)"
					+ " from CoGList a left join CoOfferList b on a.cgli_coli_id=b.coli_id "
					+ "left join CoCompact c on b.coli_coco_id=c.coco_id where "
					+ "isnull(cgli_stopdate,220023)>=cgli_startdate"
					+ " and "
					+ ownmonth
					+ " between cgli_startdate and isnull(cgli_stopdate,220023)) "
					+ " or cid in(select distinct(cid) cid from EmFinanceZYT where ownmonth= "
					+ ownmonth
					+ "))) cb"
					+ " on a.coab_id=cb.cabc_id where coab_state=1 and coco_state>3";
			dbconn db = new dbconn();
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				list.add(rs.getString("coab_city"));

			}
		} catch (Exception e) {

		}
		return list;
	}

	// 根据月份和城市获取机构
	public List<String> getCoabSetup(Integer ownmonth, String city) {
		List<String> list = new ArrayList<String>();
		list.add("");
		try {
			String sql = "select distinct(coab_name )coab_name from StAgencyBase_view a inner join "
					+ "(select * from cocompact where (cid in(select distinct(a.cid)"
					+ " from CoGList a left join CoOfferList b on a.cgli_coli_id=b.coli_id "
					+ "left join CoCompact c on b.coli_coco_id=c.coco_id where "
					+ "isnull(cgli_stopdate,220023)>=cgli_startdate"
					+ " and "
					+ ownmonth
					+ " between cgli_startdate and isnull(cgli_stopdate,220023)) "
					+ " or cid in(select distinct(cid) cid from EmFinanceZYT where ownmonth= "
					+ ownmonth
					+ "))) cb"
					+ " on a.coab_id=cb.cabc_id where coab_state=1 and coco_state>3 "
					+ " and coab_city='" + city + "'";
			dbconn db = new dbconn();
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				list.add(rs.getString("coab_name"));

			}
		} catch (Exception e) {

		}
		return list;
	}
}
