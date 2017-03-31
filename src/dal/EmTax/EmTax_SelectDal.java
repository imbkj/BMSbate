package dal.EmTax;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.CoBaseModel;
import Model.EmTaxInfoModel;
import Util.UserInfo;

public class EmTax_SelectDal {
	private dbconn conn = new dbconn();

	// 获取公司信息
	public List<CoBaseModel> getCoBase() {
		List<CoBaseModel> list = new ArrayList<CoBaseModel>();
		CoBaseModel m = null;
		String sql = "SELECT cid,coba_shortname,LEFT(coba_namespell,1) AS shortspell FROM ETIn_EmBase_CoBase_V WHERE csqe_taxde_date IS NOT NULL GROUP BY cid,coba_shortname,coba_namespell ORDER BY coba_shortname";
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new CoBaseModel();
				m.setCid(rs.getInt("CID"));
				m.setCoba_shortname(rs.getString("coba_shortname"));
				m.setCoba_shortspell(rs.getString("shortspell"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 员工明细时个税数据
	public List<EmTaxInfoModel> getEmTaxList(String str,String ownmonth) {
		List<EmTaxInfoModel> list = new ArrayList<EmTaxInfoModel>();
		EmTaxInfoModel m = null;
		String sql="";
		sql=sql+"SELECT a.ownmonth,a.cid,a.gid,b.emba_name,b.emba_idcardclass,b.emba_idcard," +
				"CASE a.etin_tax_class WHEN 1 THEN '正常工资薪金' WHEN 2 THEN '年终一次性奖金' WHEN 3 THEN '股票期权个人所得税' WHEN 4 THEN '离职补偿金个人所得税' WHEN 5 THEN '劳务报酬个人所得税' WHEN 6 THEN '正常工资薪金' END AS etin_tax_class," +
				"a.etin_tax_class AS etin_tax_class_num,a.etin_tax_base,a.etin_tax,ISNULL(d.cou,0) as cou,b.emba_nationality,b.emba_birth,b.emba_mobile," +
				"b.emba_sex,case etin_tax_class when 6 then '中智开户' else f.coco_gs end as coco_gs,c.coba_company,c.coba_manageraddress,e.esin_taxplace,f.chk_cgli,g.coab_shortname," +
				//"case h.Csqe_taxde_date when 2 then dbo.GetLastOwnmonth(convert(varchar(6),ownmonth)) else convert(varchar(6),ownmonth) end as gz_ownmonth";
				"a.gz_ownmonth";
		//sql=sql+" FROM EmTaxInfo a ";
		sql=sql+" FROM (SELECT case b.Csqe_taxde_date when 2 then dbo.GetNextOwnmonth(convert(varchar(6),a.ownmonth)) else convert(varchar(6),a.ownmonth) end as ownmonth,a.cid,a.gid,a.etin_tax_class,a.etin_tax_base,a.etin_tax,a.etin_state,b.Csqe_taxde_date,a.ownmonth as gz_ownmonth from EmTaxInfo a left join CoServiceRequest b on a.cid=b.cid)a ";
		sql=sql+" LEFT JOIN EmBase b ON a.gid=b.gid";
		sql=sql+" LEFT JOIN CoBase c ON a.cid=c.cid ";
		sql=sql+" LEFT JOIN (SELECT cid,gid,COUNT(gid) AS cou FROM EmSalaryData WHERE esda_payment_state=2 GROUP BY cid,gid)d ON a.gid=d.gid AND a.cid=d.cid ";
		sql=sql+" left join (select a.cid,a.gid,case when len(esin_nexttaxplace)>0 then case when esin_nexttaxplace_smonth<="+ownmonth+" then esin_nexttaxplace else esin_taxplace end else esin_taxplace end as esin_taxplace from emsalaryinfo a left join CoServiceRequest b on a.cid=b.cid where esin_taxplace is not null and esin_taxplace<>'' group by a.cid,a.gid,esin_taxplace,esin_nexttaxplace,esin_nexttaxplace_smonth,Csqe_taxde_date)e on a.gid=e.gid and a.cid=e.cid ";
		sql=sql+" left join (select 1 as chk_cgli,cogl.gid,cogl.cid,isnull(coco.coco_gs,'') as coco_gs	from CoGList cogl left join CoOfferList coli on cogl.cgli_coli_id=coli.coli_id left join CoOffer coof on coli.coli_coof_id=coof.coof_id left join CoCompact coco on coof.coof_coco_id=coco.coco_id where cgli_state=1 and cogl.cgli_startdate<="+ownmonth+" and coli.coli_name in(select copr_name from CoProduct where copr_name like '%税%') group by cogl.gid,coco.coco_gs,cogl.cid)f on a.gid=f.gid and a.cid=f.cid";
		sql=sql+" left join (select a.cid,isnull(b.coab_shortname,'')coab_shortname,MAX(coco_id)coco_id from CoCompact a left join StAgencyBase_view b on a.cabc_id=b.coab_id where coco_state in(4,5) and LEN(cabc_id)>0 and a.cid is not null group by a.cid,b.coab_shortname)g on a.cid=g.cid";
		//sql=sql+" left join (select Csqe_taxde_date,cid from CoServiceRequest)h on a.cid=h.cid";
		//sql=sql+" WHERE 1=1 and f.chk_cgli=1 "+ str + " ORDER BY e.esin_taxplace,a.cid,b.emba_name";
		sql=sql+" WHERE 1=1 "+ str + " ORDER BY e.esin_taxplace,a.cid,b.emba_name";
		//System.out.println(sql);
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new EmTaxInfoModel();
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setEsin_taxplace(rs.getString("esin_taxplace"));
				m.setEtin_name(rs.getString("emba_name"));
				m.setIdcardclass(rs.getString("emba_idcardclass"));
				m.setEtin_idcard(rs.getString("emba_idcard"));
				m.setTax_class(rs.getString("etin_tax_class"));
				m.setNationality(rs.getString("emba_nationality"));
				m.setEtin_tax_base(rs.getBigDecimal("etin_tax_base"));
				m.setEtin_tax_class(rs.getInt("etin_tax_class_num"));
				m.setEtin_tax(rs.getBigDecimal("etin_tax"));
				m.setIfNew(rs.getInt("cou"));
				m.setGid(rs.getInt("gid"));
				m.setCid(rs.getInt("cid"));
				m.setBirth(rs.getString("emba_birth"));
				m.setSex(rs.getString("emba_sex"));
				m.setGtstate(rs.getString("coco_gs"));
				m.setCompany(rs.getString("coba_company"));
				m.setManageraddress(rs.getString("coba_manageraddress"));
				if ("1".equals(rs.getString("chk_cgli"))) {
					m.setChk_cgli("有");
				}else {
					m.setChk_cgli("无");
				}
				m.setCoab_shortname(rs.getString("coab_shortname"));
				m.setGz_ownmonth(rs.getInt("gz_ownmonth"));
				m.setMobile(rs.getString("emba_mobile"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 公司汇总时个税数据
	public List<EmTaxInfoModel> getCoTaxList(String str, int ifLast,String ownmonth) {
		List<EmTaxInfoModel> list = new ArrayList<EmTaxInfoModel>();
		EmTaxInfoModel m = null;
		String emtaxinfo="(SELECT case b.Csqe_taxde_date when 2 then dbo.GetNextOwnmonth(convert(varchar(6),a.ownmonth)) else convert(varchar(6),a.ownmonth) end as ownmonth,a.etin_id,a.cid,a.gid,a.etin_tax_class,a.etin_tax_base,a.etin_tax,a.etin_state,b.Csqe_taxde_date,a.ownmonth as gz_ownmonth from EmTaxInfo a left join CoServiceRequest b on a.cid=b.cid)";
		String l_str = " AND ownmonth="+ownmonth;
		if (ifLast == 1) {
			l_str =l_str+ " AND etin_state=0";
		}
		String sql = "";
		sql=sql+"SELECT ct.cid,isnull(j.s_cou,0)s_cou,f.coba_company,h.ownmonth,h.esin_taxplace,i.coab_shortname,ISNULL(h.etin_tax1,0) AS etin_tax1,ISNULL(h.etin_tax2,0) AS etin_tax2,ISNULL(h.etin_tax3,0) AS etin_tax3,ISNULL(h.etin_tax4,0) AS etin_tax4,ISNULL(h.etin_tax5,0) AS etin_tax5,ISNULL(h.etin_tax6,0) AS etin_tax6,ISNULL(e.coga_t6,0) AS coga_t6,(ISNULL(h.etin_tax1,0)+ISNULL(h.etin_tax2,0)+ISNULL(h.etin_tax3,0)+ISNULL(h.etin_tax4,0)+ISNULL(h.etin_tax5,0)+ISNULL(h.etin_tax6,0)-ISNULL(coga_t6,0)) AS balance";
				sql=sql+" FROM (select cid from (select a.cid from (select cid,coco_id from CoCompact where coco_state in(4,5) and cid is not null)a left join CoOffer b on a.coco_id=b.coof_coco_id left join (select distinct coli_coof_id from CoOfferList where coli_state=1 and (coli_name like '%税%'))c on b.coof_id=c.coli_coof_id where c.coli_coof_id is not null)e group by cid)ct " ;
				sql=sql+" LEFT JOIN";
				sql=sql+" (SELECT cid,ownmonth,esin_taxplace,coco_gs,etin_state,SUM(etin_tax1)etin_tax1,SUM(etin_tax2)etin_tax2,SUM(etin_tax3)etin_tax3,SUM(etin_tax4)etin_tax4,SUM(etin_tax5)etin_tax5,SUM(etin_tax6)etin_tax6";
				sql=sql+" FROM (SELECT z.etin_state,tt.coco_gs,z.cid,z.gid,z.ownmonth,ISNULL(a.etin_tax,0)etin_tax1,ISNULL(b.etin_tax,0)etin_tax2,ISNULL(c.etin_tax,0)etin_tax3,ISNULL(d.etin_tax,0)etin_tax4,ISNULL(lw.etin_tax,0)etin_tax5,ISNULL(zf.etin_tax,0)etin_tax6,esin_taxplace";
					sql=sql+" FROM";
						sql=sql+" (SELECT cid,gid,ownmonth,etin_state FROM "+emtaxinfo+"EmTaxInfo WHERE 1=1"+ l_str+ " GROUP BY cid,gid,ownmonth,etin_state)z";
								
								sql=sql+" LEFT JOIN (SELECT cid,gid,ownmonth,ISNULL(SUM(etin_tax),0) AS etin_tax FROM "+emtaxinfo+"EmTaxInfo WHERE etin_tax_class=1"+ l_str+ " GROUP BY cid,gid,ownmonth)a ON z.gid=a.gid AND z.cid=a.cid AND z.ownmonth=a.ownmonth";
								sql=sql+" LEFT JOIN (SELECT cid,gid,ownmonth,ISNULL(SUM(etin_tax),0) AS etin_tax FROM "+emtaxinfo+"EmTaxInfo WHERE etin_tax_class=2"+ l_str+ " GROUP BY cid,gid,ownmonth)b ON z.gid=b.gid AND z.cid=b.cid AND z.ownmonth=b.ownmonth";
								sql=sql+" LEFT JOIN (SELECT cid,gid,ownmonth,ISNULL(SUM(etin_tax),0) AS etin_tax FROM "+emtaxinfo+"EmTaxInfo WHERE etin_tax_class=3"+ l_str+ " GROUP BY cid,gid,ownmonth)c ON z.gid=c.gid AND z.cid=c.cid AND z.ownmonth=c.ownmonth";
								sql=sql+" LEFT JOIN (SELECT cid,gid,ownmonth,ISNULL(SUM(etin_tax),0) AS etin_tax FROM "+emtaxinfo+"EmTaxInfo WHERE etin_tax_class=4"+ l_str+ " GROUP BY cid,gid,ownmonth)d ON z.gid=d.gid AND z.cid=d.cid AND z.ownmonth=d.ownmonth";
								sql=sql+" LEFT JOIN (SELECT cid,gid,ownmonth,ISNULL(SUM(etin_tax),0) AS etin_tax FROM "+emtaxinfo+"EmTaxInfo WHERE etin_tax_class=5"+ l_str+ " GROUP BY cid,gid,ownmonth)lw ON z.gid=lw.gid AND z.cid=lw.cid AND z.ownmonth=lw.ownmonth";
								sql=sql+" LEFT JOIN (SELECT cid,gid,ownmonth,ISNULL(SUM(etin_tax),0) AS etin_tax FROM "+emtaxinfo+"EmTaxInfo WHERE etin_tax_class=6"+ l_str+ " GROUP BY cid,gid,ownmonth)zf ON z.gid=zf.gid AND z.cid=zf.cid AND z.ownmonth=zf.ownmonth";
								sql=sql+" LEFT JOIN (select cid,gid,case when len(esin_nexttaxplace)>0 then case when "+ownmonth+">=esin_nexttaxplace_smonth then esin_nexttaxplace else esin_taxplace end else esin_taxplace end as esin_taxplace from emsalaryinfo where esin_taxplace is not null and esin_taxplace<>'' group by cid,gid,esin_taxplace,esin_nexttaxplace,esin_nexttaxplace_smonth)g ON z.gid=g.gid AND z.cid=g.cid";
								
								//sql=sql+" left join (select 1 as chk_cgli,cogl.gid,isnull(coco.coco_gs,'') as coco_gs	from CoGList cogl left join CoOfferList coli on cogl.cgli_coli_id=coli.coli_id	left join CoOffer coof on coli.coli_coof_id=coof.coof_id left join CoCompact coco on coof.coof_coco_id=coco.coco_id where cgli_state=1 and coli.coli_name in(select copr_name from CoProduct where copr_name like '%税%'))tt on z.gid=tt.gid 	where chk_cgli=1";
								sql=sql+" left join (select 1 as chk_cgli,cogl.gid,isnull(coco.coco_gs,'') as coco_gs	from CoGList cogl left join CoOfferList coli on cogl.cgli_coli_id=coli.coli_id	left join CoOffer coof on coli.coli_coof_id=coof.coof_id left join CoCompact coco on coof.coof_coco_id=coco.coco_id where cgli_state=1 and cogl.cgli_startdate<="+ownmonth+" and coli.coli_name in(select copr_name from CoProduct where copr_name like '%税%') group by cogl.gid,coco.coco_gs)tt on z.gid=tt.gid 	where 1=1";
							sql=sql+" )g GROUP BY cid,ownmonth,esin_taxplace,etin_state,coco_gs)h on ct.cid=h.cid";
							sql=sql+" LEFT JOIN";
							sql=sql+" (select ti.cid,ti.ownmonth,sum(isnull(et.efta_Receivable,0))coga_t6";
								sql=sql+" from EmFinanceTax et " ;
								sql=sql+"left join CoFinanceMonthlyBillSortAccount cb on et.efta_cfmb_number=cb.cfsa_cfmb_number and cfsa_cpac_name='个调税' " ;
								sql=sql+"left join (select * from "+emtaxinfo+"EmTaxInfo) ti on et.efta_etin_id=ti.etin_id where ISNULL(cfsa_PaidIn,0)-ISNULL(cfsa_Receivable,0)=0 and et.efta_Receivable<>0 group by ti.cid,ti.ownmonth)e ON h.cid=e.cid AND h.ownmonth=e.ownmonth " ;
							sql=sql+"LEFT JOIN CoBase f ON ct.cid=f.cid " ;
							sql=sql+" LEFT JOIN";
							sql=sql+"(select a.cid,isnull(b.coab_shortname,'')coab_shortname,MAX(coco_id)coco_id from CoCompact a left join StAgencyBase_view b on a.cabc_id=b.coab_id where coco_state in(4,5) and LEN(cabc_id)>0 and a.cid is not null group by a.cid,b.coab_shortname)i on ct.cid=i.cid ";
							sql=sql+" LEFT JOIN";
							sql=sql+" (select cid,COUNT(*)s_cou from "+emtaxinfo+"EmTaxInfo where ownmonth="+ownmonth+" group by cid)j on ct.cid=j.cid ";
						sql=sql+" WHERE 1=1"+ str + " ORDER BY f.cid,f.coba_company";//要按公司编号排序才不会影响应收税额合计
		//System.out.println(sql);
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new EmTaxInfoModel();
				m.setOwnmonth(Integer.parseInt(ownmonth));
				m.setEsin_taxplace(rs.getString("esin_taxplace"));
				m.setCid(rs.getInt("cid"));
				m.setCompany(rs.getString("coba_company"));
				m.setEtin_tax1(rs.getBigDecimal("etin_tax1"));
				m.setEtin_tax2(rs.getBigDecimal("etin_tax2"));
				m.setEtin_tax3(rs.getBigDecimal("etin_tax3"));
				m.setEtin_tax4(rs.getBigDecimal("etin_tax4"));
				m.setEtin_tax5(rs.getBigDecimal("etin_tax5"));
				m.setEtin_tax6(rs.getBigDecimal("etin_tax6"));
				m.setCoga_t6(rs.getBigDecimal("coga_t6"));
				m.setBalance(rs.getBigDecimal("balance"));
				m.setCoab_shortname(rs.getString("coab_shortname"));
				m.setS_cou(rs.getInt("s_cou"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 导出员工明细时个税数据更新
	public int upEmTax(String str,String ownmonth,String filename) {
		String sql = "";
		sql=sql+"UPDATE EmTaxInfo SET etin_state=2,etin_declare_date=getdate(),etin_declare_name='"+UserInfo.getUsername()+"',etin_file_num='"+filename+"' WHERE etin_id IN(";
		sql=sql+"SELECT etin_id ";
		//sql=sql+" FROM (SELECT etin_id,cid,gid,ownmonth FROM EmTaxInfo WHERE etin_state=0)a";
		sql=sql+" FROM (SELECT a.etin_tax_class,a.etin_id,case b.Csqe_taxde_date when 2 then dbo.GetNextOwnmonth(convert(varchar(6),a.ownmonth)) else convert(varchar(6),a.ownmonth) end as ownmonth,a.cid,a.gid from EmTaxInfo a left join CoServiceRequest b on a.cid=b.cid)a";
		sql=sql+" LEFT JOIN (select cid,gid,case when len(esin_nexttaxplace)>0 then case when "+ownmonth+">=esin_nexttaxplace_smonth then esin_nexttaxplace else esin_taxplace end else esin_taxplace end as esin_taxplace from emsalaryinfo where esin_taxplace is not null and esin_taxplace<>'' group by cid,gid,esin_taxplace,esin_nexttaxplace,esin_nexttaxplace_smonth)b on a.gid=b.gid AND a.cid=b.cid";
		sql=sql+" LEFT JOIN CoBase c ON a.cid=c.cid";
		sql=sql+" left join (select 1 as chk_cgli,cogl.gid,isnull(coco.coco_gs,'') as coco_gs	from CoGList cogl left join CoOfferList coli on cogl.cgli_coli_id=coli.coli_id left join CoOffer coof on coli.coli_coof_id=coof.coof_id left join CoCompact coco on coof.coof_coco_id=coco.coco_id where cgli_state=1 and cogl.cgli_startdate<="+ownmonth+" and coli.coli_name in(select copr_name from CoProduct where copr_name like '%税%'))f on a.gid=f.gid";
		sql=sql+" WHERE " + str + " and  etin_state=0)";

		try {
			if (!"".equals(str)) {

				ResultSet rs = conn.GRS(sql);
				return 0;

			} else {
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
	}

	// 导出公司汇总时个税数据更新
	public int upCoTax(String str) {
		String sql="";
		sql=sql+ "UPDATE EmTaxInfo SET etin_state=2 WHERE etin_id IN(" ;
		sql=sql+" SELECT etin_id " ;
		//sql=sql+" FROM (SELECT etin_id,cid,gid,ownmonth FROM EmTaxInfo WHERE etin_state=0)a " ;
		sql=sql+" FROM (SELECT a.etin_id,case b.Csqe_taxde_date when 2 then dbo.GetNextOwnmonth(convert(varchar(6),a.ownmonth)) else convert(varchar(6),a.ownmonth) end as ownmonth,a.cid,a.gid from EmTaxInfo a left join CoServiceRequest b on a.cid=b.cid)a " ;
		sql=sql+" LEFT JOIN (SELECT cid,gid,esin_taxplace FROM EmSalaryInfo GROUP BY cid,gid,esin_taxplace)b on a.gid=b.gid AND a.cid=b.cid " ;
		sql=sql+" left join (select 1 as chk_cgli,cogl.gid,isnull(coco.coco_gs,'') as coco_gs	from CoGList cogl left join CoOfferList coli on cogl.cgli_coli_id=coli.coli_id left join CoOffer coof on coli.coli_coof_id=coof.coof_id left join CoCompact coco on coof.coof_coco_id=coco.coco_id where cgli_state=1 and coli.coli_name in(select copr_name from CoProduct where copr_name like '%税%'))f on a.gid=f.gid";
		sql=sql+" LEFT JOIN CoBase c ON a.cid=c.cid ";
		sql=sql+" WHERE " + str + ")";

		try {
			if (!"".equals(str)) {

				ResultSet rs = conn.GRS(sql);
				return 0;

			} else {
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
	}

	// 公司个税申报地分配情况
	public List<EmTaxInfoModel> getCoTaxPlaceList(String str) {
		List<EmTaxInfoModel> list = new ArrayList<EmTaxInfoModel>();
		EmTaxInfoModel m = null;
		String sql = "select * from (select e.coba_company,e.coba_shortname,e.coba_client,c.*,isnull(d.s,0)s from (select a.cid,count(*)y from coglist a inner join coofferlist b on a.cgli_coli_id=b.coli_id where a.cgli_state=1 and b.coli_city<>'深圳' and (b.coli_name like '%个税%' or b.coli_name like '%个人所得税%') group by a.cid)c left join (select cid,count(*)s from emsalaryinfo where esin_taxplace is not null and esin_taxplace<>'' group by cid)d on c.cid=d.cid left join cobase e on c.cid=e.cid)f where 1=1 "
				+ str + " order by coba_shortname";
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new EmTaxInfoModel();
				m.setCid(rs.getInt("cid"));
				m.setCompany(rs.getString("coba_shortname"));
				m.setClient(rs.getString("coba_client"));
				m.setY_cou(rs.getInt("y"));
				m.setS_cou(rs.getInt("s"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 所有个税申报城市
	public List<String> getCity() {
		List<String> list = new ArrayList<String>();
		String sql = "select * from PubProCity order by name";
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				list.add(rs.getString("name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取需分配个税申报地的员工列表
	public List<EmTaxInfoModel> getTPEmList(String str) {
		List<EmTaxInfoModel> list = new ArrayList<EmTaxInfoModel>();
		EmTaxInfoModel m = null;
		String sql = "select * from (select a.cid,a.gid,a.emba_name,a.emba_state,d.esin_taxplace,d.esin_nexttaxplace_smonth " +
				"from (select cid,gid,emba_name,emba_state from embase where emba_state in(1,2,5) or datediff(m,emba_outdate,getdate())<=3)a " +
				"left join (select cid,gid,case when len(esin_nexttaxplace)>0 then esin_nexttaxplace else esin_taxplace end as esin_taxplace,esin_nexttaxplace_smonth " +
				"from emsalaryinfo group by cid,gid,esin_taxplace,esin_nexttaxplace_smonth,esin_nexttaxplace)d on a.gid=d.gid and a.cid=d.cid)f where 1=1 "
				+ str + " order by esin_taxplace,emba_name";
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new EmTaxInfoModel();
				m.setCid(rs.getInt("cid"));
				m.setGid(rs.getInt("gid"));
				m.setEtin_name(rs.getString("emba_name"));
				m.setEsin_taxplace(rs.getString("esin_taxplace"));
				m.setEsin_nexttaxplace_smonth(rs.getInt("esin_nexttaxplace_smonth"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
