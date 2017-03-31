package dal.EmFinance;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.EmFinanceZYTModel;

public class EmFinance_SelectDal {

	// 根据cid和机构查询合同id
	public int[] getCocoID(String str) {
		int[] ints = null;
		String sql = " select coco_id from CoCompact b inner join StAgencyBase_view h on b.cabc_id=h.coab_id "
				+ "where coab_city!='深圳' and coco_state>3  AND coco_compacttype not like '%CS%' "
				+ str;
		dbconn db = new dbconn();
		ResultSet rs = null;
		try {
			rs = db.GRS(sql);
			rs.last();
			Integer j = rs.getRow();
			ints = new int[j];
			int i = 0;
			rs.first();
			if (j > 0) {
				do {
					ints[i] = rs.getInt("coco_id");
					i++;
				} while (rs.next());
			}
		} catch (Exception e) {

		}
		return ints;
	}

	// 根据城市和机构
	public int[] getCocoID(String city, String coab_name) {
		int[] ints = null;
		String sql = " select coco_id from CoCompact b inner join "
				+ " StAgencyBase_view h on b.cabc_id=h.coab_id "
				+ "where coco_state>3 and coab_city='" + city
				+ "' AND coco_compacttype not like '%CS%'  and coab_name='"
				+ coab_name + "'";
		dbconn db = new dbconn();
		ResultSet rs = null;
		try {
			rs = db.GRS(sql);
			rs.last();
			Integer j = rs.getRow();
			ints = new int[j];
			int i = 0;
			rs.first();
			if (j > 0) {
				do {
					ints[i] = rs.getInt("coco_id");
					i++;
				} while (rs.next());
			}

		} catch (Exception e) {

		}
		return ints;
	}

	// 根据所属月份查询委托地区（根据文洁最新增加的受托）
	public List<String> getWtAreaLists(Integer ownmonth) {
		String sql = "select distinct(coab_city) coab_city"
				+ " from CoCompact b "
				+ "inner join ("
				+ "	select max(cgli_id) cgli_id,coof_coco_id,a.gid,a.cid "
				+ "	from CoGList a"
				+ " inner join CoOfferList b on a.cgli_coli_id=b.coli_id "
				+ " inner join CoOffer coof on b.coli_coof_id=coof.coof_id "
				+ "	inner join CoCompact c on coof.coof_coco_id=c.coco_id AND coco_compacttype not like '%CS%' and coco_state>3"
				+ "	where cgli_startdate<=isnull(cgli_stopdate,220023) and gid is not null"
				+ " GROUP BY cgli_id,gid,a.cid,coof_coco_id,a.gid,a.cid"
				+ ") j on b.coco_id=j.coof_coco_id and coco_state>3 "
				+ "inner join (select coab_id,coab_city from StAgencyBase_view ) h on b.cabc_id=h.coab_id and b.cabc_id is not null and coab_city is not null "
				+ " and gid is not null and coab_city<>'深圳'"
				+ " GROUP BY coab_city" + " order by coab_city";
		List<String> list = new ArrayList<String>();
		dbconn db = new dbconn();
		ResultSet rs = null;
		try {
			rs = db.GRS(sql);
			list.add("");
			while (rs.next()) {
				list.add(rs.getString("coab_city"));
			}

		} catch (Exception e) {

		}

		return list;
	}

	// 根据委托地区和所属月份查找委托机构（根据文洁最新增加的受托）
	public List<String> getSetupLists(String city) {
		String sql = " select * from StAgencyBase_view where coab_city='"
				+ city + "'";
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

	// 获取委托地区列表
	public List<String> getCity() {
		List<String> list = new ArrayList<String>();
		String sql = "select spell+name as name from PubProCity order by spell";
		dbconn db = new dbconn();
		ResultSet rs = null;
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				list.add(rs.getString("name"));
			}
		} catch (Exception e) {

		}
		return list;
	}

	// 根据委托地区和cid查询智翼通员工数
	public Integer getEmbaseCount(String city, Integer cid, Integer ownmonth,
			String coab_name) {
		Integer num = 0;
		String sql = "select count(distinct(a.gid)) num from EmFinanceZYT a"
				+ "  inner join (select distinct(gid) gid,cid,coco_id,cabc_id "
				+ " from ( select max(cgli_id) cgli_id, gid,a.cid,coco_id,cabc_id"
				+ " from CoGList a left join CoOfferList b  on a.cgli_coli_id=b.coli_id"
				+ " inner join CoOffer coof on b.coli_coof_id=coof.coof_id "
				+ " left join CoCompact c on coof.coof_coco_id=c.coco_id AND coco_compacttype not like '%CS%'  "
				+ " where isnull(cgli_stopdate,220023)>=cgli_startdate GROUP BY gid,a.cid,coco_id,cabc_id)fhf)j on "
				+ " a.gid=j.gid and a.cid=j.cid"
				+ " inner join StAgencyBase_view b "
				+ "on a.scity=b.coab_city where a.cid=" + cid + " and scity='"
				+ city + "' and a.ownmonth=" + ownmonth + "and coab_name='"
				+ coab_name + "'";
		dbconn db = new dbconn();
		ResultSet rs = null;
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				num = rs.getInt("num");
			}
		} catch (Exception e) {

		}
		return num;
	}

	// 根据合同编号和所属月份查询智翼通的员工数据
	public List<EmFinanceZYTModel> getEmFinanceList2(Integer ownmonth,
			String cocoid) {
		dbconn db = new dbconn();
		List<EmFinanceZYTModel> list = new ArrayList<EmFinanceZYTModel>();
		String sql = "select distinct(a.gid) gid,(emfz_other-isnull(emfz_fee2,0)) emfz_other,ISNULL(emfz_servertotal2,0)emfz_servertotal,"
				+ "a.cid,emfz_sbtotal,emfz_yltotal,"
				+ "emfz_total,ownmonth,emfz_company,emfz_housetotal,isnull(emfz_fee,0)emfz_feex,"
				+ " (emfz_fee+isnull(emfz_fee2,0)) as emfz_fee,emfz_filefee,isnull(emfz_fee2,0)emfz_fee2,"
				+ "(emfz_servertotal+emfz_elsefee+isnull(emfz_servertotal2,0)) as emfz_elsefee"
				+ ",emfz_name as emba_name,"
				+ "emfz_idcard as emba_idcard from (select emfz_servertotal2="
				+ " (select SUM(emfz_servertotal) from EmFinanceZYT where gid=a.gid "
				+ " and emfz_sbchange<>'正常收费' and ownmonth="
				+ ownmonth
				+ " and cid in(select cid "
				+ " from CoCompact where coco_compacttype not like '%CS%' AND coco_id in("
				+ cocoid
				+ "))),"
				+ " emfz_fee2=(select SUM(emfz_fee) from EmFinanceZYT where gid=a.gid "
				+ " and emfz_sbchange<>'正常收费' and ownmonth="
				+ ownmonth
				+ " and cid in(select cid "
				+ " from CoCompact where coco_compacttype not like '%CS%' AND coco_id in("
				+ cocoid
				+ "))),"
				+ " *,emfz_other=(select SUM(emfz_total) "
				+ "from EmFinanceZYT where gid=a.gid and emfz_sbchange<>'正常收费' "
				+ " and ownmonth="
				+ ownmonth
				+ " and cid in(select cid from CoCompact "
				+ "where coco_compacttype not like '%CS%' AND coco_id in("
				+ cocoid
				+ "))) from (SELECT id, zid, gid, cid, scompany, sname, scity, "
				+ "rname, rcity, rcompany, ownmonth, emfz_company, emfz_name, emfz_idcard, "
				+ "emfz_yltotal, emfz_syetotal, emfz_gstotal, emfz_syutotal, emfz_jltotal,"
				+ " emfz_housetotal, emfz_zhtotal, emfz_bjtotal, emfz_sbtotal, emfz_elsefee, "
				+ "emfz_sbchange, emfz_serverid, emfz_servername, emfz_serverfee, emfz_servertotal, "
				+ "emfz_serverchange, emfz_fee, emfz_filefee, emfz_total, emfz_remark, emfz_ifinure,"
				+ " emfz_addtime, emfz_ifconfirm,emfz_confirmtime, emfz_confirmname, emfz_city, "
				+ "emfz_sbstand, emfz_sbstanename, emfz_ylcp, emfz_ylop, emfz_jlcp, emfz_jlop, "
				+ "emfz_gscp, emfz_gsop, emfz_syecp, emfz_syeop, emfz_syucp, emfz_syuop, emfz_housecp, "
				+ "emfz_houseop, emfz_zhcp, emfz_zhop, emfz_bjcp, emfz_bjop, emfz_ylradix, emfz_syeradix, "
				+ "emfz_gsradix, emfz_syuradix, emfz_jlradix, emfz_houseradix, emfz_zhradix, emfz_bjradix,"
				+ " emfz_fpfee, emfz_fptime, emfz_fpid, emfz_filename, emfz_f_confirm, emfz_f_confirmtime, "
				+ "emfz_f_confirmname, emfz_zgid FROM EmFinanceZYT where emfz_sbchange='正常收费' "
				+ "and ownmonth="
				+ ownmonth
				+ " and cid in(select cid from CoCompact where coco_compacttype "
				+ "not like '%CS%' AND coco_id in("
				+ cocoid
				+ ")) union all SELECT id, zid, gid, cid, scompany,"
				+ " sname, scity, rname, rcity, rcompany, ownmonth, emfz_company, emfz_name, emfz_idcard,"
				+ "0, 0, 0, 0, 0, 0, 0, 0, 0, 0, emfz_sbchange, emfz_serverid, emfz_servername,"
				+ " 0, 0, emfz_serverchange, 0, 0, 0, emfz_remark, emfz_ifinure, emfz_addtime, "
				+ "emfz_ifconfirm, emfz_confirmtime, emfz_confirmname, emfz_city, emfz_sbstand,"
				+ " emfz_sbstanename, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,"
				+ " 0, 0, emfz_fptime, emfz_fpid, emfz_filename, emfz_f_confirm, emfz_f_confirmtime,"
				+ " emfz_f_confirmname, emfz_zgid FROM EmFinanceZYT where emfz_sbchange<>'正常收费' "
				+ "and ownmonth="
				+ ownmonth
				+ " and cid in(select cid from CoCompact where coco_compacttype "
				+ "not like '%CS%' AND coco_id in("
				+ cocoid
				+ ")) and gid not in(select gid from EmFinanceZYT "
				+ "where emfz_sbchange='正常收费' and ownmonth="
				+ ownmonth
				+ " and cid in(select cid "
				+ "from CoCompact where coco_compacttype not like '%CS%' AND coco_id in("
				+ cocoid
				+ ")))) a "
				+ "where cid in(select cid from CoCompact "
				+ "where coco_compacttype not like '%CS%' AND coco_id in("
				+ cocoid
				+ ")) and ownmonth="
				+ ownmonth
				+ ") a  inner join (select distinct(gid) gid,cid,"
				+ "coco_id,cabc_id from ( select max(cgli_id) cgli_id,gid,a.cid,coco_id,cabc_id "
				+ "from CoGList a  left join CoOfferList b  on a.cgli_coli_id=b.coli_id "
				+ " inner join CoOffer coof on b.coli_coof_id=coof.coof_id "
				+ "left join CoCompact c on coof.coof_coco_id=c.coco_id AND coco_compacttype not like '%CS%'  "
				+ "where (isnull(cgli_stopdate,220023)>=cgli_startdate2 "
				+ " or isnull(cgli_stopdate,220023)>=cgli_startdate) "
				+ " GROUP BY gid,a.cid,coco_id,cabc_id)fhf)j "
				+ "on a.gid=j.gid and a.cid=j.cid where a.cid in(select cid "
				+ "from CoCompact where coco_compacttype not like '%CS%' AND coco_id in("
				+ cocoid + ")) and ownmonth=" + ownmonth;
		System.out.println(sql);
		try {
			list = db.find(sql, EmFinanceZYTModel.class,
					dbconn.parseSmap(EmFinanceZYTModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据合同编号和所属月份查询智翼通的员工数据
	public List<EmFinanceZYTModel> getEmFinanceListOut(Integer ownmonth,// 导出专用
			int cid) {
		dbconn db = new dbconn();
		List<EmFinanceZYTModel> list = new ArrayList<EmFinanceZYTModel>();
		String sql = "select distinct(a.gid) gid,(emfz_other-isnull(emfz_fee2,0)) emfz_other,"
				+ "emfz_fee2=(select SUM(emfz_fee) from EmFinanceZYT where gid=a.gid "
				+ "and emfz_sbchange<>'正常收费' and ownmonth="
				+ ownmonth
				+ " and cid="
				+ cid
				+ "),"
				+ "a.cid,emfz_sbtotal,"
				+ " (emfz_yltotal+isnull(emfz_yltotal2,0)) emfz_yltotal,"
				+ "emfz_total,ownmonth,emfz_company,emfz_housetotal,"
				+ "(emfz_fee+isnull(emfz_fee2,0)) as emfz_fee,emfz_filefee,"
				+ " (emfz_jltotal+isnull(emfz_jltotal2,0)) emfz_jltotal,"
				+ "(emfz_syutotal+isnull(emfz_syutotal2,0)) emfz_syutotal,"
				+ " (emfz_syetotal+isnull(emfz_syetotal2,0)) emfz_syetotal,"
				+ "(emfz_gstotal+isnull(emfz_gstotal2,0)) emfz_gstotal,"
				+ "(emfz_servertotal+emfz_elsefee+isnull(emfz_servertotal2,0)) as emfz_elsefee,"
				+ " emfz_name as emba_name,"
				+ "emfz_idcard as emba_idcard from (select emfz_servertotal2="
				+ " (select SUM(emfz_servertotal) from EmFinanceZYT where gid=a.gid "
				+ " and emfz_sbchange<>'正常收费' and ownmonth="
				+ ownmonth
				+ " and cid="
				+ cid
				+ "),"
				+ " emfz_fee2=(select SUM(emfz_fee) from EmFinanceZYT where gid=a.gid "
				+ " and emfz_sbchange<>'正常收费' and ownmonth="
				+ ownmonth
				+ " and cid= "
				+ cid
				+ "),"
				+ " emfz_syetotal2=(select SUM(emfz_syetotal) "
				+ " from EmFinanceZYT where gid=a.gid and emfz_sbchange<>'正常收费' and ownmonth="
				+ ownmonth
				+ " and cid= "
				+ cid
				+ "),emfz_gstotal2=(select SUM(emfz_gstotal) from EmFinanceZYT "
				+ " where gid=a.gid and emfz_sbchange<>'正常收费' and ownmonth="
				+ ownmonth
				+ " and cid="
				+ cid
				+ "),"
				+ " emfz_syutotal2=(select SUM(emfz_syutotal) from EmFinanceZYT where gid=a.gid and "
				+ " emfz_sbchange<>'正常收费' and ownmonth="
				+ ownmonth
				+ " and cid="
				+ cid
				+ "),emfz_jltotal2=(select "
				+ " SUM(emfz_jltotal) from EmFinanceZYT where gid=a.gid and emfz_sbchange<>'正常收费' "
				+ " and ownmonth="
				+ ownmonth
				+ " and cid="
				+ cid
				+ "),emfz_yltotal2=(select SUM(emfz_yltotal) "
				+ " from EmFinanceZYT where gid=a.gid and emfz_sbchange<>'正常收费' and ownmonth="
				+ ownmonth
				+ " "
				+ " and cid="
				+ cid
				+ "),*,emfz_other=(select SUM(emfz_total) "
				+ "from EmFinanceZYT where gid=a.gid and emfz_sbchange<>'正常收费' "
				+ " and ownmonth="
				+ ownmonth
				+ " and cid="
				+ cid
				+ ") from (SELECT id, zid, gid, cid, scompany, sname, scity, "
				+ "rname, rcity, rcompany, ownmonth, emfz_company, emfz_name, emfz_idcard, "
				+ "emfz_yltotal, emfz_syetotal, emfz_gstotal, emfz_syutotal, emfz_jltotal,"
				+ " emfz_housetotal, emfz_zhtotal, emfz_bjtotal, emfz_sbtotal, emfz_elsefee, "
				+ "emfz_sbchange, emfz_serverid, emfz_servername, emfz_serverfee, emfz_servertotal, "
				+ "emfz_serverchange, emfz_fee, emfz_filefee, emfz_total, emfz_remark, emfz_ifinure,"
				+ " emfz_addtime, emfz_ifconfirm,emfz_confirmtime, emfz_confirmname, emfz_city, "
				+ "emfz_sbstand, emfz_sbstanename, emfz_ylcp, emfz_ylop, emfz_jlcp, emfz_jlop, "
				+ "emfz_gscp, emfz_gsop, emfz_syecp, emfz_syeop, emfz_syucp, emfz_syuop, emfz_housecp, "
				+ "emfz_houseop, emfz_zhcp, emfz_zhop, emfz_bjcp, emfz_bjop, emfz_ylradix, emfz_syeradix, "
				+ "emfz_gsradix, emfz_syuradix, emfz_jlradix, emfz_houseradix, emfz_zhradix, emfz_bjradix,"
				+ " emfz_fpfee, emfz_fptime, emfz_fpid, emfz_filename, emfz_f_confirm, emfz_f_confirmtime, "
				+ "emfz_f_confirmname, emfz_zgid FROM EmFinanceZYT where emfz_sbchange='正常收费' "
				+ "and ownmonth="
				+ ownmonth
				+ " and cid="
				+ cid
				+ " union all SELECT id, zid, gid, cid, scompany,"
				+ " sname, scity, rname, rcity, rcompany, ownmonth, emfz_company, emfz_name, emfz_idcard,"
				+ "0, 0, 0, 0, 0, 0, 0, 0, 0, 0, emfz_sbchange, emfz_serverid, emfz_servername,"
				+ " 0, 0, emfz_serverchange, 0, 0, 0, emfz_remark, emfz_ifinure, emfz_addtime, "
				+ "emfz_ifconfirm, emfz_confirmtime, emfz_confirmname, emfz_city, emfz_sbstand,"
				+ " emfz_sbstanename, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,"
				+ " 0, 0, emfz_fptime, emfz_fpid, emfz_filename, emfz_f_confirm, emfz_f_confirmtime,"
				+ " emfz_f_confirmname, emfz_zgid FROM EmFinanceZYT where emfz_sbchange<>'正常收费' "
				+ "and ownmonth="
				+ ownmonth
				+ " and cid="
				+ cid
				+ " and gid not in(select gid from EmFinanceZYT "
				+ "where emfz_sbchange='正常收费' and ownmonth="
				+ ownmonth
				+ " and cid="
				+ cid
				+ ")) a "
				+ " where cid="
				+ cid
				+ " and ownmonth="
				+ ownmonth
				+ ") a inner join (select distinct(gid) gid,cid,"
				+ "coco_id,cabc_id from ( select max(cgli_id) cgli_id,gid,a.cid,coco_id,cabc_id "
				+ "from CoGList a  left join CoOfferList b  on a.cgli_coli_id=b.coli_id "
				+ "  inner join CoOffer coof on b.coli_coof_id=coof.coof_id"
				+ " left join CoCompact c on coof.coof_coco_id=c.coco_id AND coco_compacttype not like '%CS%'  "
				+ " where (isnull(cgli_stopdate,220023)>=cgli_startdate2 "
				+ " or isnull(cgli_stopdate,220023)>=cgli_startdate) "
				+ " and a.cid="
				+ cid
				+ " GROUP BY gid,a.cid,coco_id,cabc_id)fhf)j "
				+ " on a.gid=j.gid and a.cid=j.cid where a.cid="
				+ cid
				+ " and ownmonth=" + ownmonth;

		System.out.println(sql);
		try {
			list = db.find(sql, EmFinanceZYTModel.class,
					dbconn.parseSmap(EmFinanceZYTModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询台帐表的所有的所属月份
	public List<Integer> getOwnmonth() {
		List<Integer> list = new ArrayList<Integer>();
		String sql = "select distinct(ownmonth) ownmonth from EmFinanceBase";
		dbconn db = new dbconn();
		ResultSet rs = null;
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				list.add(rs.getInt("ownmonth"));
			}
		} catch (Exception e) {

		}
		return list;
	}

	// 根据cid和所属月份获取智翼通的数据
	public List<EmFinanceZYTModel> getEmFinanceInfoList(Integer ownmonth,
			Integer cid) {
		List<EmFinanceZYTModel> list = new ArrayList<EmFinanceZYTModel>();
		String sql = " select a.gid,a.cid,emfz_sbtotal,emfz_yltotal,emfz_total,ownmonth,emfz_company,"
				+ "emfz_housetotal,emfz_fee,emfz_filefee,emfz_servertotal+emfz_elsefee as emfz_elsefee,"
				+ "emfz_name as emba_name,emfz_idcard as emba_idcard from EmFinanceZYT a "
				+ " where a.cid=" + cid + " and ownmonth=" + ownmonth;
		dbconn db = new dbconn();
		try {
			list = db.find(sql, EmFinanceZYTModel.class,
					dbconn.parseSmap(EmFinanceZYTModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询客服
	public List<String> getLoginlist(String city) {
		List<String> list = new ArrayList<String>();
		dbconn db = new dbconn();
		ResultSet rs = null;
		String str = "";
		String sql = "";
		if (city != null && !city.equals("")) {
			sql = " select distinct(coba_client) log_name from CoBase where "
					+ "cid in(select distinct(cid) from CoCompact b inner join "
					+ "StAgencyBase_view h on b.cabc_id=h.coab_id and coco_state>3"
					+ " AND coco_compacttype not like '%CS%'  where coab_city!='深圳' "
					+ "and coab_city='" + city + "')";
		} else {
			sql = "select LEFT(log_spell,1)+log_name as log_name from Login a,department b where a.dep_id=b.dep_id and "
					+ "(b.dep_name='客户服务部' or b.dep_name='全国项目部') and log_inure=1 and log_name is not null and log_spell is not"
					+ " null " + str + " order by log_spell";
		}
		try {
			System.out.println(sql);
			rs = db.GRS(sql);
			list.add("");
			while (rs.next()) {
				list.add(rs.getString("log_name"));
			}
		} catch (Exception e) {

		}
		return list;
	}

	// 根据合同编号获取cid
	public Integer getCidByCocoId(String str) {
		Integer cid = 0;
		String sql = "select * from cocompact where coco_state>3 AND coco_compacttype not like '%CS%' "
				+ str;
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				cid = rs.getInt("cid");
			}
		} catch (Exception e) {
		}

		return cid;
	}

	public List<Integer> getCidByClient(String client) {
		List<Integer> list = new ArrayList<Integer>();
		String sql = "select cid from cobase where coba_client='" + client
				+ "'";
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

	// 根据账单号获取账单备注
	public String getBillRemark(String billno) {
		String remark = "";
		String sql = "select cfmb_remark,cfmb_number,* from CoFinanceMonthlyBill where cfmb_number='"
				+ billno + "'";
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				remark = rs.getString("cfmb_remark");
			}
		} catch (Exception e) {

		}
		return remark;
	}
}
