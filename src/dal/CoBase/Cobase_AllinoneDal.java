package dal.CoBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.Allinone_ListModel;

public class Cobase_AllinoneDal {

	// public List<Allinone_ListModel> getall_state(int cid) throws SQLException
	// {
	// dbconn db = new dbconn();
	// ResultSet rs = null;
	// List<Allinone_ListModel> list = new ArrayList<Allinone_ListModel>();
	// String sqlstr =
	// "select 1,coli_pclass,e.name,coli_name,cgli_startdate,cgli_startdate2,cgli_stopdate from coglist a left join coofferlist b "
	// +
	// "on b.coli_id=a.cgli_coli_id left join CoProduct c on c.Copr_id=b.coli_copr_id "
	// +
	// "left join CoAgencyBaseCityRel d on d.cabc_id=c.copr_cabc_id left join PubProCity "
	// +
	// "e on e.id=d.cabc_ppc_id where cgli_state=1 and gid="
	// + gid + "";
	// try {
	// rs = db.GRS(sqlstr);
	// System.out.print(sqlstr);
	// while (rs.next()) {
	// Allinone_ListModel model = new Allinone_ListModel();
	// model.setAll_st1(rs.getString(1));
	// list.add(model);
	// }
	// } catch (Exception e) {
	// System.out.print(e.toString());
	// } finally {
	// db.Close();
	// }
	// return list;
	// }
	//
	// // 获取该员所选的服务项目
	// public List<Allinone_ListModel> getcoglist_list(int cid)
	// throws SQLException {
	// dbconn db = new dbconn();
	// ResultSet rs = null;
	// List<Allinone_ListModel> list = new ArrayList<Allinone_ListModel>();
	// String sqlstr =
	// "select coli_pclass,e.name,coli_name,cgli_startdate,cgli_startdate2,cgli_stopdate from coglist a "
	// +
	// "left join coofferlist b on b.coli_id=a.cgli_coli_id " +
	// "left join CoProduct c on c.Copr_id=b.coli_copr_id " +
	// "left join CoAgencyBaseCityRel d on d.cabc_id=c.copr_cabc_id " +
	// "left join PubProCity e on e.id=d.cabc_ppc_id where cgli_state=1 and gid="
	// + gid + "";
	// try {
	// rs = db.GRS(sqlstr);
	// while (rs.next()) {
	// Allinone_ListModel model = new Allinone_ListModel();
	// model.setAll_t1(rs.getString(1));
	// model.setAll_t2(rs.getString(2));
	// model.setAll_t3(rs.getString(3));
	// model.setAll_t4(rs.getString(4));
	// model.setAll_t5(rs.getString(5));
	// model.setAll_t6(rs.getString(6));
	// list.add(model);
	// }
	// } catch (Exception e) {
	// System.out.print(e.toString());
	// } finally {
	// db.Close();
	// }
	// return list;
	// }

	// 显示社保数据
	public List<Allinone_ListModel> getsb_list(int cid) throws SQLException {
		dbconn db = new dbconn();

		ResultSet rs = null;
		List<Allinone_ListModel> list = new ArrayList<Allinone_ListModel>();
		String sqlstr = "select esiu_name,isnull(Esiu_ComputerID,'/') as Esiu_ComputerID,Esiu_Radix,Esiu_hj, Convert(decimal(18,2),Esiu_TotalCP),"
				+ "Convert(decimal(18,2),Esiu_TotalOP) ,Esiu_YL,Esiu_YLType,Esiu_GS,Esiu_Sye,Esiu_Syu,es.cid,"
				+ "state=(case when ec.emsc_change is not null then ec.emsc_change when efc.emsc_change is not null then efc.emsc_change when esiu_ifstop=2 then '被调走'  when esiu_ifstop=3 then '新增调入退回' when esiu_ifstop=1 then '不在册' else '正常在册' end)"
				+ " from EmShebaoUpdate es " +
				" left join (select * from emshebaochange a where exists (select id from (select gid,cid,MAX(id)as id from EmShebaoChange where emsc_ifdeclare<>3 and ownmonth=(select top 1 ownmonth from EmShebaoUpdate) group by GID,CID)b where a.ID=b.id)) ec on es.Ownmonth=ec.Ownmonth and es.GID=ec.GID " +
				" left join (select * from EmShebaoChangeForeigner a where exists (select id from (select gid,cid,MAX(id)as id from EmShebaoChangeForeigner where emsc_ifdeclare<>3 and ownmonth=(select top 1 ownmonth from EmShebaoUpdate) group by GID,CID)b where a.ID=b.id)) efc on es.Ownmonth=efc.Ownmonth and es.GID=efc.GID " +
				" where es.ownmonth=(select top 1 ownmonth from EmShebaoUpdate) and es.cid="
				+ cid + " order by esiu_name";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				Allinone_ListModel model = new Allinone_ListModel();
				model.setAll_t1(rs.getString(1));
				model.setAll_t2(rs.getString(2));
				model.setAll_t3(rs.getString(3));
				model.setAll_t4(rs.getString(4));
				model.setAll_t5(rs.getString(5));
				model.setAll_t6(rs.getString(6));
				model.setAll_t7(rs.getString(7));
				model.setAll_t8(rs.getString(8));
				model.setAll_t9(rs.getString(9));
				model.setAll_t10(rs.getString(10));
				model.setAll_t11(rs.getString(11));
				model.setAll_st12(rs.getString(13));

				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示社保数据
	public List<Allinone_ListModel> getsb_list(String strwhere)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<Allinone_ListModel> list = new ArrayList<Allinone_ListModel>();
		String sqlstr = "select esiu_name,isnull(Esiu_ComputerID,'/') as Esiu_ComputerID,Esiu_Radix,Esiu_hj, Convert(decimal(18,2),Esiu_TotalCP),"
				+ "Convert(decimal(18,2),Esiu_TotalOP) ,Esiu_YL,Esiu_YLType,Esiu_GS,Esiu_Sye,Esiu_Syu,es.cid,"
				+ "state=(case when ec.emsc_change is not null then ec.emsc_change when efc.emsc_change is not null then efc.emsc_change when esiu_ifstop=2 then '被调走' when esiu_ifstop=3 then '新增调入退回' when esiu_ifstop=1 then '不在册' else '正常在册' end)"
				+ " from EmShebaoUpdate es " +
				"left join (select * from emshebaochange a where exists (select id from (select gid,cid,MAX(id)as id from EmShebaoChange where emsc_ifdeclare<>3 and ownmonth=(select top 1 ownmonth from EmShebaoUpdate) group by GID,CID)b where a.ID=b.id)) ec on es.Ownmonth=ec.Ownmonth and es.GID=ec.GID " +
				"left join (select * from EmShebaoChangeForeigner a where exists (select id from (select gid,cid,MAX(id)as id from EmShebaoChangeForeigner where emsc_ifdeclare<>3 and ownmonth=(select top 1 ownmonth from EmShebaoUpdate) group by GID,CID)b where a.ID=b.id)) efc on es.Ownmonth=efc.Ownmonth and es.GID=efc.GID " +
				"where es.ownmonth=(select top 1 ownmonth from EmShebaoUpdate) "
				+ strwhere + " order by esiu_name";
		try {
			System.out.println(sqlstr);
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				Allinone_ListModel model = new Allinone_ListModel();
				model.setAll_t1(rs.getString(1));
				model.setAll_t2(rs.getString(2));
				model.setAll_t3(rs.getString(3));
				model.setAll_t4(rs.getString(4));
				model.setAll_t5(rs.getString(5));
				model.setAll_t6(rs.getString(6));
				model.setAll_t7(rs.getString(7));
				model.setAll_t8(rs.getString(8));
				model.setAll_t9(rs.getString(9));
				model.setAll_t10(rs.getString(10));
				model.setAll_t11(rs.getString(11));
				model.setAll_st12(rs.getString(13));

				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示住房统计
	public List<Allinone_ListModel> gethouse_count(int cid) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<Allinone_ListModel> list = new ArrayList<Allinone_ListModel>();
		String sqlstr = "select COUNT(*), Convert(decimal(18,2),sum(emhu_cp))  ,Convert(decimal(18,2),sum(emhu_op)) , Convert(decimal(18,2),sum(emhu_cp+emhu_op))   from emhouseupdate where ownmonth=(select top 1 ownmonth from EmHouseUpdate) and emhu_ifstop=0"
				+ " and cid=" + cid + "";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				Allinone_ListModel model = new Allinone_ListModel();
				model.setAll_t1(rs.getString(1));
				model.setAll_t2(rs.getString(2));
				model.setAll_t3(rs.getString(3));
				model.setAll_t4(rs.getString(4));

				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示住房统计
	public List<Allinone_ListModel> gethouse_count(String str)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<Allinone_ListModel> list = new ArrayList<Allinone_ListModel>();
		String sqlstr = "select COUNT(*), Convert(decimal(18,2),sum(emhu_cp))  ,Convert(decimal(18,2),sum(emhu_op)) , Convert(decimal(18,2),sum(emhu_cp+emhu_op))   from emhouseupdate eu where  ownmonth=(select top 1 ownmonth from EmHouseUpdate) and emhu_ifstop=0 "
				+ str + "";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				Allinone_ListModel model = new Allinone_ListModel();
				model.setAll_t1(rs.getString(1));
				model.setAll_t2(rs.getString(2));
				model.setAll_t3(rs.getString(3));
				model.setAll_t4(rs.getString(4));

				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示委托出数据
	public List<Allinone_ListModel> getwt_listcount(int cid)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<Allinone_ListModel> list = new ArrayList<Allinone_ListModel>();
		String sqlstr = "select a.cid, cast(sum(case eofd_name when '养老保险' then eofd_em_base else 0 end) as varchar(20)) as ecop_yl_sf, "
				+ "cast(sum(case eofd_name when '医疗保险' then eofd_em_base else 0 end) as varchar(20)) as ecop_jl_sf, "
				+ "cast(sum(case eofd_name when '工伤保险' then eofd_em_base else 0 end) as varchar(20)) as ecop_gs_sf, "
				+ "cast(sum(case eofd_name when '失业保险' then eofd_em_base else 0 end) as varchar(20)) as ecop_sye_sf, "
				+ "cast(sum(case eofd_name when '生育保险' then eofd_em_base else 0 end) as varchar(20)) as ecop_syu_sf, "
				+ "cast(sum(case eofd_name when '住房公积金' then eofd_em_base else 0 end) as varchar(20)) as ecop_house_sf, "
				+ "cast(sum(case eofd_name when '档案费' then eofd_em_base else 0 end) as varchar(20)) as ecop_file_sf, "
				+ "cast(sum(case eofd_name when '服务费' then eofd_em_base else 0 end) as varchar(20)) as ecop_fw_sf , count(a.gid) "
				+ "from EmCommissionOut a left join EmCommissionOutFeeDetail b on b.eofd_ecou_id=a.ecou_id "
				+ "left join (select coba_company,cid from cobase )c on c.CID=a.cid  "
				+ "where a.cid="
				+ cid
				+ "  and ecou_state=1 and ecou_addtype not in ('离职','补缴') "
				+ " group by a.cid ";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				Allinone_ListModel model = new Allinone_ListModel();
				model.setAll_t1(rs.getString(1));
				model.setAll_t2(rs.getString(2));
				model.setAll_t3(rs.getString(3));
				model.setAll_t4(rs.getString(4));
				model.setAll_t5(rs.getString(5));
				model.setAll_t6(rs.getString(6));
				model.setAll_t7(rs.getString(7));
				model.setAll_t8(rs.getString(8));
				model.setAll_t9(rs.getString(9));
				model.setAll_t10(rs.getString(10));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// select COUNT(*), sum(emhu_cp),sum(emhu_op) from emhouseupdate where
	// cid=1366 and ownmonth='' and emhu_ifstop=0
	//
	// select COUNT(*), sum(Esiu_TotalCP), SUM(Esiu_TotalOP) from EmShebaoUpdate
	// es left join EmShebaoChange ec on es.Ownmonth=ec.Ownmonth and
	// es.GID=ec.GID
	// where es.cid=1366 and Ownmonth=2014 and Esiu_IfStop=0

	// 显示社保统计
	public List<Allinone_ListModel> getshebao_count(int cid)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<Allinone_ListModel> list = new ArrayList<Allinone_ListModel>();
		String sqlstr = "select COUNT(*), Convert(decimal(18,2),sum(Esiu_TotalCP) ) , Convert(decimal(18,2),sum(Esiu_TotalOP) ),Convert(decimal(18,2),SUM(Esiu_TotalCP+Esiu_TotalOP) ) from"
				+ " EmShebaoUpdate es "
				+ " where es.ownmonth=(select top 1 ownmonth from emshebaoupdate) and es.esiu_ifstop=0 and es.cid="
				+ cid + "";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				Allinone_ListModel model = new Allinone_ListModel();
				model.setAll_t1(rs.getString(1));
				model.setAll_t2(rs.getString(2));
				model.setAll_t3(rs.getString(3));
				model.setAll_t4(rs.getString(4));

				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示社保统计
	public List<Allinone_ListModel> getshebao_count(String str)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<Allinone_ListModel> list = new ArrayList<Allinone_ListModel>();
		String sqlstr = "select COUNT(*), Convert(decimal(18,2),sum(Esiu_TotalCP) ) , Convert(decimal(18,2),sum(Esiu_TotalOP) ),Convert(decimal(18,2),SUM(Esiu_TotalCP+Esiu_TotalOP) ) from"
				+ " EmShebaoUpdate es "
				+ " where es.ownmonth=(select top 1 ownmonth from emshebaoupdate) and es.esiu_ifstop=0 "
				+ str + "";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				Allinone_ListModel model = new Allinone_ListModel();
				model.setAll_t1(rs.getString(1));
				model.setAll_t2(rs.getString(2));
				model.setAll_t3(rs.getString(3));
				model.setAll_t4(rs.getString(4));

				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示住房数据
	public List<Allinone_ListModel> gethouse_list(int cid) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<Allinone_ListModel> list = new ArrayList<Allinone_ListModel>();
		String sqlstr = "select emhu_name,eu.cid,emhu_companyid,emhu_houseid,emhu_radix,case emhu_single"
				+ " when 1 then '独立开户' else '中智开户' end+'-'+convert(varchar(10),emhu_cpp*100)+'%' remark,Convert(decimal(18,2),emhu_cp),Convert(decimal(18,2),emhu_op),"
				+ "state=(case when ec.emhc_change is not null then ec.emhc_change when emhu_ifstop=0 then '正常在册' when emhu_ifstop=1 then '终止' when emhu_ifstop=2 then '核查失败' end) "
				+ " from emhouseupdate eu left join (select * from emhousechange a where exists (select emhc_id from (select gid,cid,MAX(emhc_id)as emhc_id from emhousechange b where b.ownmonth=(select top 1 ownmonth from EmHouseUpdate) group by GID,CID)b where a.emhc_id=b.emhc_id))ec on eu.ownmonth=ec.ownmonth and eu.gid=ec.gid "
				+ " where eu.ownmonth=(select top 1 ownmonth from EmHouseUpdate) and eu.cid="
				+ cid + " order by emhu_ifstop,emhu_name";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				Allinone_ListModel model = new Allinone_ListModel();
				model.setAll_t1(rs.getString(1));
				model.setAll_t2(rs.getString(2));
				model.setAll_t3(rs.getString(3));
				model.setAll_t4(rs.getString(4));
				model.setAll_t5(rs.getString(5));
				model.setAll_t6(rs.getString(6));
				model.setAll_t7(rs.getString(7));
				model.setAll_t8(rs.getString(8));
				model.setAll_st12(rs.getString(9));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示住房数据
	public List<Allinone_ListModel> gethouse_list(String strwhere)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<Allinone_ListModel> list = new ArrayList<Allinone_ListModel>();
		String sqlstr = "select emhu_name,eu.cid,isnull(emhu_companyid,''),emhu_houseid,emhu_radix,case emhu_single"
				+ " when 1 then '独立开户' else '中智开户' end+'-'+convert(varchar(10),emhu_cpp*100)+'%' remark,Convert(decimal(18,2),emhu_cp),Convert(decimal(18,2),emhu_op),"
				+ "state=(case when ec.emhc_change is not null then ec.emhc_change when emhu_ifstop=0 then '正常在册' when emhu_ifstop=1 then '终止' when emhu_ifstop=2 then '核查失败' end) "
				+ " from emhouseupdate eu left join (select * from emhousechange a where exists (select emhc_id from (select gid,cid,MAX(emhc_id)as emhc_id from emhousechange b where b.ownmonth=(select top 1 ownmonth from EmHouseUpdate) group by GID,CID)b where a.emhc_id=b.emhc_id))ec on eu.ownmonth=ec.ownmonth and eu.gid=ec.gid and eu.cid=ec.cid where eu.ownmonth=(select top 1 ownmonth from EmHouseUpdate)  and isnull(emhu_remark,'')!='模拟数据' "
				+ strwhere + " order by emhu_ifstop,emhu_name";
		System.out.println(sqlstr);
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				Allinone_ListModel model = new Allinone_ListModel();
				model.setAll_t1(rs.getString(1));
				model.setAll_t2(rs.getString(2));
				model.setAll_t3(rs.getString(3));
				model.setAll_t4(rs.getString(4));
				model.setAll_t5(rs.getString(5));
				model.setAll_t6(rs.getString(6));
				model.setAll_t7(rs.getString(7));
				model.setAll_t8(rs.getString(8));
				model.setAll_st12(rs.getString(9));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示劳动合同数据
	public List<Allinone_ListModel> getemcompact_list(int cid)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<Allinone_ListModel> list = new ArrayList<Allinone_ListModel>();
		String sqlstr = "select emba_name,convert(varchar(10),ebco_incept_date,120),convert(varchar(10),ebco_maturity_date,120),"
				+ "ebco_term,convert(varchar(10),ebco_sign_date,120),convert(varchar(10),ebco_filing_date,120),cid,ebco_change,"
				+ "case when ebco_state=0 then '合同未制作' else '' end+case when ebco_state=1 then '合同未审核' else '' end+case"
				+ " when ebco_state=2 then '合同未打印' else '' end+case when ebco_state=3 then '合同未签回' else '' end+case "
				+ "when ebco_state=4 then '合同未归档' else '' end+case when ebco_state=5 then '合同已归档' else '' end+case"
				+ " when ebco_state=10 then '合同退回' else '' end from embasecompact a "
				+ "left join (select emba_name ,gid from embase) b on a.gid=b.gid "
				+ "where cid=" + cid + "";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				Allinone_ListModel model = new Allinone_ListModel();
				model.setAll_t1(rs.getString(1));
				model.setAll_t2(rs.getString(2));
				model.setAll_t3(rs.getString(3));
				model.setAll_t4(rs.getString(4));
				model.setAll_t5(rs.getString(5));
				model.setAll_t6(rs.getString(6));
				model.setAll_t7(rs.getString(7));
				model.setAll_t8(rs.getString(8));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示委托出数据
	public List<Allinone_ListModel> getwt_list(int cid) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<Allinone_ListModel> list = new ArrayList<Allinone_ListModel>();
		String sqlstr = "select emba_name, cast(sum(case eofd_name when '养老保险' then eofd_em_base else 0 end) as varchar(20)) as ecop_yl_sf,"
				+ "cast(sum(case eofd_name when '医疗保险' then eofd_em_base else 0 end) as varchar(20)) as ecop_jl_sf,"
				+ "cast(sum(case eofd_name when '工伤保险' then eofd_em_base else 0 end) as varchar(20)) as ecop_gs_sf,"
				+ "cast(sum(case eofd_name when '失业保险' then eofd_em_base else 0 end) as varchar(20)) as ecop_sye_sf,"
				+ "cast(sum(case eofd_name when '生育保险' then eofd_em_base else 0 end) as varchar(20)) as ecop_syu_sf,"
				+ "cast(sum(case eofd_name when '住房公积金' then eofd_em_base else 0 end) as varchar(20)) as ecop_house_sf,"
				+ "cast(sum(case eofd_name when '档案费' then eofd_month_sum else 0 end) as varchar(20)) as ecop_file_sf,"
				+ "cast(sum(case eofd_name when '服务费' then eofd_month_sum else 0 end) as varchar(20)) as ecop_fw_sf,case when ecou_state=1 then '正常' else case when ecou_state=0 then '离职' else '退回' end end "
				+ "from EmCommissionOut a left join EmCommissionOutFeeDetail b on b.eofd_ecou_id=a.ecou_id "
				+ "left join (select emba_name,gid from EmBase )c on c.gid=a.gid "
				+ " where a.cid=" + cid + " group by emba_name,ecou_state";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				Allinone_ListModel model = new Allinone_ListModel();
				model.setAll_t1(rs.getString(1));
				model.setAll_t2(rs.getString(2));
				model.setAll_t3(rs.getString(3));
				model.setAll_t4(rs.getString(4));
				model.setAll_t5(rs.getString(5));
				model.setAll_t6(rs.getString(6));
				model.setAll_t7(rs.getString(7));
				model.setAll_t8(rs.getString(8));
				model.setAll_t9(rs.getString(9));
				model.setAll_t10(rs.getString(10));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示工资数据
	public List<Allinone_ListModel> getgz_list(int cid) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<Allinone_ListModel> list = new ArrayList<Allinone_ListModel>();
		String sqlstr = "select esda_ba_name,ownmonth,case when esda_usage_type=0 then '工资' else '' end+case when esda_usage_type=1 "
				+ "then '报销' else '' end+case when esda_usage_type=2 then '住房返还' else '' end,case when esda_data_type=0 "
				+ "then '正常' else '' end+case when esda_data_type=1 then '少发' else '' end+case when esda_data_type=2 "
				+ "then '多发' else '' end+case when esda_data_type=3 then '补发' else '' end,esda_pay,case when esda_payment_state=0 "
				+ "then '未审核' else '' end+case when esda_payment_state=1 then '已审核' else '' end+case when esda_payment_state=2 "
				+ "then '已发放' else '' end+case when esda_payment_state=3 then '待确认' else '' end+case when esda_payment_state=4 "
				+ "then '待发放' else '' end,esda_payment_date,case when esda_email_state=0 then '未发' else '已发' end from EmSalaryData "
				+ " where cid=" + cid + " order by ownmonth desc,esda_ba_name";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				Allinone_ListModel model = new Allinone_ListModel();
				model.setAll_t1(rs.getString(1));
				model.setAll_t2(rs.getString(2));
				model.setAll_t3(rs.getString(3));
				model.setAll_t4(rs.getString(4));
				model.setAll_t5(rs.getString(5));
				model.setAll_t6(rs.getString(6));
				model.setAll_t7(rs.getString(7));
				model.setAll_t8(rs.getString(8));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示档案数据
	public List<Allinone_ListModel> getfile_list(int cid) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<Allinone_ListModel> list = new ArrayList<Allinone_ListModel>();
		String sqlstr = "select emar_name,gid,emar_archivetype,isnull(emar_archivearea,'')+emar_fid emar_fid,emar_archivesource,"
				+ "emar_archiveplace,isnull(emar_continuedeadline,emar_firstdeadline)emar_continuedeadline,case emar_state"
				+ " when 1 then '在册' else '已调离' end state from emarchive  where cid="
				+ cid + "";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				Allinone_ListModel model = new Allinone_ListModel();
				model.setAll_t1(rs.getString(1));
				model.setAll_t2(rs.getString(2));
				model.setAll_t3(rs.getString(3));
				model.setAll_t4(rs.getString(4));
				model.setAll_t5(rs.getString(5));
				model.setAll_t6(rs.getString(6));
				model.setAll_t7(rs.getString(7));
				model.setAll_t8(rs.getString(8));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示体检数据
	public List<Allinone_ListModel> gettj_list(int cid) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<Allinone_ListModel> list = new ArrayList<Allinone_ListModel>();
		String sqlstr = "select embc_name,cid,ebcs_hospital,ebsa_address,case when len(ebcl_items)>4 then '自选项目' else ebcl_items "
				+ "end+'('+convert(varchar(50),ebcl_charge)+')' ebcl_items,convert(varchar(10),ebcl_plandate,120) ebcl_plandate,"
				+ "case ebcl_state when 0 then '已取消' when 1 then '待确认' when 2 then '待申报' when 3 then '体检中' when 4 then '已体检'"
				+ " when 5 then '已收报告' when 6 then '已报销' when 7 then '已退回' when 8 then '报销处理' when 9 then '重新预约' "
				+ "when 10 then '预约中' when 11 then '已结算' when 12 then '已签收报告' end embc_statebname "
				+ "from embodycheck a "
				+ "inner join embodychecklist b on a.embc_id=b.ebcl_embc_id  "
				+ "inner join EmBcSetup d on b.ebcl_hospital=d.ebcs_id "
				+ "inner join EmBcSetupAddress e on b.ebcl_area=e.ebsa_id "
				+ "where a.cid=" + cid + "  and ebcl_flag=1 ";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				Allinone_ListModel model = new Allinone_ListModel();
				model.setAll_t1(rs.getString(1));
				model.setAll_t2(rs.getString(2));
				model.setAll_t3(rs.getString(3));
				model.setAll_t4(rs.getString(4));
				model.setAll_t5(rs.getString(5));
				model.setAll_t6(rs.getString(6));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示社保卡数据
	public List<Allinone_ListModel> getcard_list(int cid) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<Allinone_ListModel> list = new ArrayList<Allinone_ListModel>();
		String sqlstr = " select sbcd_name,sbcd_sbnumber,sbcd_companyname, sbcd_computerid, sbcd_photonum,sbcd_hj,sbcd_content,"
				+ " sbcd_addname,  convert(varchar(10),sbcd_addtime,120) as sbcd_addtime,cdst_statename from EmShebaoCardInfo a"
				+ ",EmShebaoCardState b   where a.sbcd_stateid=b.cdst_id and a.cid= "
				+ cid + " order by cdst_id";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				Allinone_ListModel model = new Allinone_ListModel();
				model.setAll_t1(rs.getString(1));
				model.setAll_t2(rs.getString(2));
				model.setAll_t3(rs.getString(3));
				model.setAll_t4(rs.getString(4));
				model.setAll_t5(rs.getString(5));
				model.setAll_t6(rs.getString(6));
				model.setAll_t7(rs.getString(7));
				model.setAll_t8(rs.getString(8));
				model.setAll_t9(rs.getString(9));
				model.setAll_t10(rs.getString(10));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示户籍数据
	public List<Allinone_ListModel> gethj_list(int cid) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<Allinone_ListModel> list = new ArrayList<Allinone_ListModel>();
		String sqlstr = "select emhj_no,emhj_name,coba_shortname,emhj_idcard,emhj_place,emhj_in_time,emhj_in_class,case emhj_state "
				+ "when 1 then '已确认' when 2 then '已借卡' when 8 then '待确定' when 3 then '未还齐' when 4 then '退回' when 5 then '在册' "
				+ "when 6 then '迁出' when 7 then '已打印' else '' end states from EmCensus a inner join Cobase b on a.cid=b.cid "
				+ "where a.cid=" + cid + "";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				Allinone_ListModel model = new Allinone_ListModel();
				model.setAll_t1(rs.getString(1));
				model.setAll_t2(rs.getString(2));
				model.setAll_t3(rs.getString(3));
				model.setAll_t4(rs.getString(4));
				model.setAll_t5(rs.getString(5));
				model.setAll_t6(rs.getString(6));
				model.setAll_t7(rs.getString(7));
				model.setAll_t8(rs.getString(8));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示公积金卡制卡数据
	public List<Allinone_ListModel> getgzk_list(int cid) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<Allinone_ListModel> list = new ArrayList<Allinone_ListModel>();
		String sqlstr = "select ownmonth,username ,a.cid, gjj_no, gjj_cno, gjj_insertblank,gjj_case, gjj_addname,"
				+ "convert(varchar(10),addtime,120) as addtime,  coba_client, State_Name from EmHouseMakeCardInfo "
				+ "a inner join cobase c  on a.cid=c.cid,MakeOrTakeCardState b where a.Gjj_CartState=b.State_Id and b.State_type=1 "
				+ "and a.cid=" + cid + "";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				Allinone_ListModel model = new Allinone_ListModel();
				model.setAll_t1(rs.getString(1));
				model.setAll_t2(rs.getString(2));
				model.setAll_t3(rs.getString(3));
				model.setAll_t4(rs.getString(4));
				model.setAll_t5(rs.getString(5));
				model.setAll_t6(rs.getString(6));
				model.setAll_t7(rs.getString(7));
				model.setAll_t8(rs.getString(8));
				model.setAll_t9(rs.getString(9));
				model.setAll_t10(rs.getString(10));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示公积金卡领卡数据
	public List<Allinone_ListModel> getglk_list(int cid) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<Allinone_ListModel> list = new ArrayList<Allinone_ListModel>();
		String sqlstr = "select a.ownmonth, username, re_gjjno,a.cid,re_cgjjno,re_cardid,re_accounttype,"
				+ "convert(varchar(10),re_apptime,120) as re_apptime, cohf_banklk,State_Name from EmHouseTakeCardInfo a "
				+ "inner join CoHousingFund d on a.re_cgjjno=d.cohf_houseid and cohf_state=1,MakeOrTakeCardState b , CoBase c  "
				+ "where Re_State=b.State_Id and b.state_type=2 and a.Cid=c.cid and a.cid="
				+ cid + "";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				Allinone_ListModel model = new Allinone_ListModel();
				model.setAll_t1(rs.getString(1));
				model.setAll_t2(rs.getString(2));
				model.setAll_t3(rs.getString(3));
				model.setAll_t4(rs.getString(4));
				model.setAll_t5(rs.getString(5));
				model.setAll_t6(rs.getString(6));
				model.setAll_t7(rs.getString(7));
				model.setAll_t8(rs.getString(8));
				model.setAll_t9(rs.getString(9));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示公积金卡领卡数据
	public List<Allinone_ListModel> getsy_list(int cid) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<Allinone_ListModel> list = new ArrayList<Allinone_ListModel>();
		String sqlstr = "select ecin_insurant,ecin_insurer,ecin_castsort,convert(varchar(10),ecin_ef_date,120),"
				+ "convert(varchar(10),ecin_st_date,120),case when ecin_state2=0 then '未申报' else '已申报' end from"
				+ " EmComInsurance where ecin_state=1 and cid=" + cid + "";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				Allinone_ListModel model = new Allinone_ListModel();
				model.setAll_t1(rs.getString(1));
				model.setAll_t2(rs.getString(2));
				model.setAll_t3(rs.getString(3));
				model.setAll_t4(rs.getString(4));
				model.setAll_t5(rs.getString(5));
				model.setAll_t6(rs.getString(6));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

}
