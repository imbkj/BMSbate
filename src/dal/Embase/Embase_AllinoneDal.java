package dal.Embase;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.Allinone_ListModel;
import Model.EmBaseCompactListModel;

public class Embase_AllinoneDal {
	// 显示所有服务类型状态
	public List<Allinone_ListModel> getall_state(int gid) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<Allinone_ListModel> list = new ArrayList<Allinone_ListModel>();
		String sqlstr = "select 1,coli_pclass,e.name,coli_name,cgli_startdate,cgli_startdate2,cgli_stopdate from coglist a left join coofferlist b on b.coli_id=a.cgli_coli_id left join CoProduct c on c.Copr_id=b.coli_copr_id left join CoAgencyBaseCityRel d on d.cabc_id=c.copr_cabc_id left join PubProCity e on e.id=d.cabc_ppc_id where cgli_state=1 and gid="
				+ gid + "";
		try {
			rs = db.GRS(sqlstr);
			System.out.print(sqlstr);
			while (rs.next()) {
				Allinone_ListModel model = new Allinone_ListModel();
				model.setAll_st1(rs.getString(1));
				list.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取该员所选的服务项目
	public List<Allinone_ListModel> getcoglist_list(int gid)
			throws SQLException {
		dbconn db = new dbconn();
		java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
		ResultSet rs = null;
		List<Allinone_ListModel> list = new ArrayList<Allinone_ListModel>();
		String sqlstr = "select coli_pclass,e.name,coli_name,cgli_startdate,cgli_startdate2,cgli_stopdate,coli_fee,cgli_addname,coc.coco_compacttype,coc.coco_compactid "
				+ "from coglist a left join coofferlist b on b.coli_id=a.cgli_coli_id left join CoProduct c on c.Copr_id=b.coli_copr_id left join CoAgencyBaseCityRel d "
				+ "on d.cabc_id=c.copr_cabc_id left join PubProCity e on e.id=d.cabc_ppc_id LEFT JOIN CoCompact coc on coc.coco_id=b.coli_coco_id  "
				+ "where cgli_state=1"
				+ "and (coli_isfwf<>0 or coli_copr_id<>0)  and gid="
				+ gid
				+ " ";
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
				model.setAll_t7((df.format(rs.getBigDecimal(7))).toString());
				model.setAll_t8(rs.getString(8));
				model.setAll_t9(rs.getString(9));
				model.setAll_t10(rs.getString(10));
				list.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示社保数据
	public List<Allinone_ListModel> getsb_list(int gid) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<Allinone_ListModel> list = new ArrayList<Allinone_ListModel>();
		String sqlstr = "select isnull(Esiu_ComputerID,'/') as Esiu_ComputerID,Esiu_Radix,Esiu_hj,Esiu_TotalCP,Esiu_TotalOP,Esiu_YL,Esiu_YLType,Esiu_GS,Esiu_Sye,Esiu_Syu,state=(case when ec.emsc_change is not null then ec.emsc_change when esiu_ifstop=2 then '被调走' else '正常在册' end) from EmShebaoUpdate es left join EmShebaoChange ec on es.Ownmonth=ec.Ownmonth and es.GID=ec.GID where es.GID="
				+ gid + "";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				Allinone_ListModel model = new Allinone_ListModel();
				model.setAll_t1(rs.getString(1));
				model.setAll_t2(String.format("%.2f",
						Double.parseDouble(rs.getString(2))));
				model.setAll_t3(rs.getString(3));
				model.setAll_t4(String.format("%.2f",
						Double.parseDouble(rs.getString(4))));
				model.setAll_t5(String.format("%.2f",
						Double.parseDouble(rs.getString(5))));
				model.setAll_t6(rs.getString(6));
				model.setAll_t7(rs.getString(7));
				model.setAll_t8(rs.getString(8));
				model.setAll_t9(rs.getString(9));
				model.setAll_t10(rs.getString(10));
				model.setAll_t11(rs.getString(11));
				list.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示住房数据
	public List<Allinone_ListModel> gethouse_list(int gid) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<Allinone_ListModel> list = new ArrayList<Allinone_ListModel>();
		String sqlstr = "select emhu_name,emhu_companyid,emhu_houseid,emhu_radix,case emhu_single when 1 then '独立开户' else '中智开户' end+'-'+convert(varchar(10),emhu_cpp*100)+'%' remark,emhu_cp,emhu_op from emhouseupdate where GID="
				+ gid + "";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				Allinone_ListModel model = new Allinone_ListModel();
				model.setAll_t1(rs.getString(1));
				model.setAll_t2(rs.getString(2));
				model.setAll_t3(rs.getString(3));
				model.setAll_t4(String.format("%.2f",
						Double.parseDouble(rs.getString(4))));
				model.setAll_t5(rs.getString(5));
				model.setAll_t6(String.format("%.2f",
						Double.parseDouble(rs.getString(6))));
				model.setAll_t7(rs.getString(7));
				list.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示劳动合同数据
	public List<Allinone_ListModel> getemcompact_list(int gid)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<Allinone_ListModel> list = new ArrayList<Allinone_ListModel>();
		String sqlstr = "select convert(varchar(10),ebco_incept_date,120),convert(varchar(10),ebco_maturity_date,120),ebco_term,ebco_change,ebco_addtime,ebco_id from embasecompact where GID="
				+ gid + "";
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
			e.printStackTrace();
		} finally {
			db.Close();
		}
		return list;
	}

	// 根据GID获取员工社保在册表信息
	public List<EmBaseCompactListModel> getEmBaseCompact_Base(int gid) {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmBaseCompactListModel> list = new ArrayList<EmBaseCompactListModel>();
		String sqlstr = "select coba_shortname,emba_name,isnull(ebco_incept_date,'') ebco_incept_date,isnull(ebco_maturity_date,'') ebco_maturity_date,isnull(ebco_probation_incept,'') ebco_probation_incept,isnull(ebco_probation_mdate,'') ebco_probation_mdate,ebco_wage,ebco_wage_bt,ebco_probation_wage,ebco_probation_bt,ebco_wage_gz,ebco_probation_gz,ebco_payroll_date,ebco_payroll_mode,ebco_work_place,ebco_working_station,ebco_id,ebco_furlough_system,ebco_work_content,ebco_other_business,d.ebcc_tapr_id,d.ebcc_id,CONVERT(varchar(100), ebco_end_date, 23) ebco_end_date,ebco_compact_type from EmBaseCompact a left join embase b on b.gid=a.gid left join cobase c on c.cid=a.cid left join (select top 1 * from EmBaseCompactChange order by ebcc_id desc) d on d.gid=a.gid where a.gid="
				+ gid + " order by ebco_id desc";
		try {
			rs = db.GRS(sqlstr);
			if (rs.next()) {
				String in_date;
				String mo_date;
				String sin_date;
				String smo_date;
				if (!rs.getString("ebco_incept_date").equals(
						"1900-01-01 00:00:00.0")) {
					in_date = rs.getString("ebco_incept_date").substring(0, 10);
				} else {
					in_date = "";
				}

				if (!rs.getString("ebco_maturity_date").equals(
						"1900-01-01 00:00:00.0")) {
					mo_date = rs.getString("ebco_maturity_date").substring(0,
							10);
				} else {
					mo_date = "";
				}

				if (!rs.getString("ebco_probation_incept").equals(
						"1900-01-01 00:00:00.0")) {
					sin_date = rs.getString("ebco_probation_incept").substring(
							0, 10);
				} else {
					sin_date = "";
				}

				if (!rs.getString("ebco_probation_mdate").equals(
						"1900-01-01 00:00:00.0")) {
					smo_date = rs.getString("ebco_probation_mdate").substring(
							0, 10);
				} else {
					smo_date = "";
				}
				EmBaseCompactListModel model = new EmBaseCompactListModel();
				model.setCompany(rs.getString("coba_shortname"));
				model.setName(rs.getString("emba_name"));
				model.setEbco_incept_date(in_date);
				model.setEbco_maturity_date(mo_date);
				model.setEbco_probation_incept(sin_date);
				model.setEbco_probation_mdate(smo_date);
				model.setEbco_wage(rs.getString("ebco_wage"));
				model.setEbco_wage_bt(rs.getString("ebco_wage_bt"));
				model.setEbco_probation_wage(rs
						.getString("ebco_probation_wage"));
				model.setEbco_probation_bt(rs.getString("ebco_probation_bt"));
				model.setEbco_wage_gz(rs.getString("ebco_wage_gz"));
				model.setEbco_probation_gz(rs.getString("ebco_probation_gz"));
				model.setEbco_payroll_date(rs.getString("ebco_payroll_date"));
				model.setEbco_payroll_mode(rs.getString("ebco_payroll_mode"));
				model.setEbco_work_place(rs.getString("ebco_work_place"));
				model.setEbco_working_station(rs
						.getString("ebco_working_station"));
				model.setEbco_furlough_system(rs
						.getString("ebco_furlough_system"));
				model.setEbco_work_content(rs.getString("ebco_work_content"));
				model.setEbco_other_business(rs
						.getString("ebco_other_business"));
				model.setEbco_id(rs.getInt("ebcc_id"));
				model.setEbco_tapr_id(rs.getInt("ebcc_tapr_id"));
				model.setEbco_end_date(rs.getString("ebco_end_date"));
				model.setEbco_compact_type(rs.getString("ebco_compact_type"));
				list.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 显示委托出数据
	public List<Allinone_ListModel> getwt_list(int gid) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<Allinone_ListModel> list = new ArrayList<Allinone_ListModel>();
		String sqlstr = "select cast(sum(case eofd_name when '养老保险' then eofd_em_base else 0 end) as varchar(20)) as ecop_yl_sf,cast(sum(case eofd_name when '医疗保险' then eofd_em_base else 0 end) as varchar(20)) as ecop_jl_sf,cast(sum(case eofd_name when '工伤保险' then eofd_em_base else 0 end) as varchar(20)) as ecop_gs_sf,cast(sum(case eofd_name when '失业保险' then eofd_em_base else 0 end) as varchar(20)) as ecop_sye_sf,cast(sum(case eofd_name when '生育保险' then eofd_em_base else 0 end) as varchar(20)) as ecop_syu_sf,cast(sum(case eofd_name when '住房公积金' then eofd_em_base else 0 end) as varchar(20)) as ecop_house_sf,cast(sum(case eofd_name when '档案费' then eofd_em_base else 0 end) as varchar(20)) as ecop_file_sf,cast(sum(case eofd_name when '服务费' then eofd_em_base else 0 end) as varchar(20)) as ecop_fw_sf from EmCommissionOut a left join EmCommissionOutFeeDetail b on b.eofd_ecou_id=a.ecou_id where GID="
				+ gid + "";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				Allinone_ListModel model = new Allinone_ListModel();
				if (rs.getString(1) != null) {
					model.setAll_t1(String.format("%.2f",
							Double.parseDouble(rs.getString(1))));
				}

				if (rs.getString(2) != null) {
					model.setAll_t2(String.format("%.2f",
							Double.parseDouble(rs.getString(2))));
				}
				if (rs.getString(3) != null) {
					model.setAll_t3(String.format("%.2f",
							Double.parseDouble(rs.getString(3))));
				}
				if (rs.getString(4) != null) {
					model.setAll_t4(String.format("%.2f",
							Double.parseDouble(rs.getString(4))));
				}
				if (rs.getString(5) != null) {
					model.setAll_t5(String.format("%.2f",
							Double.parseDouble(rs.getString(5))));
				}
				if (rs.getString(6) != null) {
					model.setAll_t6(String.format("%.2f",
							Double.parseDouble(rs.getString(6))));
				}
				if (rs.getString(7) != null) {
					model.setAll_t7(String.format("%.2f",
							Double.parseDouble(rs.getString(7))));
				}
				if (rs.getString(8) != null) {
					model.setAll_t8(String.format("%.2f",
							Double.parseDouble(rs.getString(8))));
				}

				model.setAll_t9("");
				list.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示工资数据
	public List<Allinone_ListModel> getgz_list(int gid) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<Allinone_ListModel> list = new ArrayList<Allinone_ListModel>();
		String sqlstr = "select ownmonth,case when esda_usage_type=0 then '工资' else '' end+case when esda_usage_type=1 then"
				+ " '报销' else '' end+case when esda_usage_type=2 then '住房返还' else '' end,case when esda_data_type=0 then"
				+ " '正常' else '' end+case when esda_data_type=1 then '少发' else '' end+case when esda_data_type=2"
				+ " then '多发' else '' end+case when esda_data_type=3 then '补发' else '' end,esda_pay,case when"
				+ " esda_payment_state=0 then '未审核' else '' end+case when esda_payment_state=1 then '已审核' else '' "
				+ "end+case when esda_payment_state=2 then '已发放' else '' end+case when esda_payment_state=3 "
				+ "then '待确认' else '' end+case when esda_payment_state=4 then '待发放' else '' end,esda_payment_date,"
				+ "case when esda_email_state=0 then '未发' else '已发' end,esda_bank,esda_bank_account,esda_ba_name from EmSalaryData  where GID="
				+ gid + " order by   ownmonth desc";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				Allinone_ListModel model = new Allinone_ListModel();
				model.setAll_t1(rs.getString(1));
				model.setAll_t2(rs.getString(2));
				model.setAll_t3(rs.getString(3));
				model.setAll_t4(String.format("%.2f",
						Double.parseDouble(rs.getString(4))));
				model.setAll_t5(rs.getString(5));
				model.setAll_t6(rs.getString(6));
				model.setAll_t7(rs.getString(7));
				model.setAll_t8(rs.getString(8));
				model.setAll_t9(rs.getString(9));
				model.setAll_t10(rs.getString(10));
				list.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示个人支付数据
	public List<Allinone_ListModel> getpay_list(int gid) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<Allinone_ListModel> list = new ArrayList<Allinone_ListModel>();
		String sqlstr = "select cid,gid,empa_name,empa_class,ownmonth,ownmonthend,"
				+ "case empa_paytype when '报销' then 0 else empa_aftertax end,empa_tax,empa_fee,empa_payclass,empa_paytype,empa_paymenttype,"
				+ "case empa_state when 0 then '取消' when 1 then '待审核' when 2 then '经理已审核' "
				+ " when 3 then '待审批' when 4 then '总经理已审批' when 5 then '财务已审核' when 6 then '已发放'"
				+ " when 7 then '退回' when 8 then '经理退回' when 9 then '待审核' "
				+ " when 10 then '退回' when 11 then '退回' when 12 then '票据已审' end,convert(varchar(10),empa_addtime,120)empa_addtime"
				+ " from empay where gid=" + gid;
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				Allinone_ListModel model = new Allinone_ListModel();
				model.setAll_t1(rs.getString(3));
				model.setAll_t2(rs.getString(4));

				model.setAll_t3(rs.getString(5) + (rs.getString(6) == null ? ""
						: ("-" + rs.getString(6))));
				if (rs.getString(7) == null) {
					model.setAll_t4("0.00");
				} else {
					model.setAll_t4(String.format("%.2f",
							Double.parseDouble(rs.getString(7))));
				}
				if (rs.getString(8) == null) {
					model.setAll_t5("0.00");
				} else {
					model.setAll_t5(String.format("%.2f",
							Double.parseDouble(rs.getString(8))));
				}
				if (rs.getString(9) == null) {
					model.setAll_t6("0.00");
				} else {
					model.setAll_t6(String.format("%.2f",
							Double.parseDouble(rs.getString(9))));
				}

				model.setAll_t7(rs.getString(10));
				model.setAll_t8(rs.getString(11));
				model.setAll_t9(rs.getString(12));
				model.setAll_t10(rs.getString(13));
				model.setAll_t11(rs.getString(14));
				list.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示档案数据
	public List<Allinone_ListModel> getfile_list(int gid) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<Allinone_ListModel> list = new ArrayList<Allinone_ListModel>();
		String sqlstr = "select gid,emar_archivetype,isnull(emar_archivearea,'')+emar_fid emar_fid,emar_archivesource,emar_archiveplace,isnull(emar_continuedeadline,emar_firstdeadline)emar_continuedeadline,case emar_state when 1 then '在册' else '已调离' end state from emarchive  where GID="
				+ gid + "";
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
				list.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示体检数据
	public List<Allinone_ListModel> gettj_list(int gid) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<Allinone_ListModel> list = new ArrayList<Allinone_ListModel>();
		String sqlstr = "select ebcs_hospital,ebsa_address,case when len(ebcl_items)>4 then '自选项目' "
				+ "else ebcl_items end+'('+convert(varchar(50),ebcl_charge)+')' ebcl_items,"
				+ "convert(varchar(10),ebcl_plandate,120) ebcl_plandate,case ebcl_state when 0 then '已取消' when 1 then '待确认' when 2 then '待申报' when 3 then '体检中' when 4 then '已体检' when 5 then '已收报告' when 6 then '已报销' when 7 then '已退回' when 8 then '报销处理' when 9 then '重新预约' when 10 then '预约中' when 11 then '已结算' when 12 then '已签收报告' end embc_statebname "
				+ " from embodycheck a "
				+ " inner join embodychecklist b on a.embc_id=b.ebcl_embc_id"
				+ " inner join EmBcSetup d on b.ebcl_hospital=d.ebcs_id "
				+ " inner join EmBcSetupAddress e on b.ebcl_area=e.ebsa_id "
				+ " where GID=" + gid + "  and ebcl_flag=1 ";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				Allinone_ListModel model = new Allinone_ListModel();
				model.setAll_t1(rs.getString(1));
				model.setAll_t2(rs.getString(2));
				model.setAll_t3(rs.getString(3));
				model.setAll_t4(rs.getString(4));
				model.setAll_t5(rs.getString(5));
				list.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示社保卡数据
	public List<Allinone_ListModel> getcard_list(int gid) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<Allinone_ListModel> list = new ArrayList<Allinone_ListModel>();
		String sqlstr = "select sbcd_sbnumber,sbcd_companyname, sbcd_computerid, sbcd_photonum,sbcd_hj,sbcd_content, sbcd_addname,convert(varchar(10),sbcd_addtime,120) as sbcd_addtime,cdst_statename from EmShebaoCardInfo a,EmShebaoCardState b where a.sbcd_stateid=b.cdst_id and gid="
				+ gid + " order by cdst_id";
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
			e.printStackTrace();
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示户籍数据
	public List<Allinone_ListModel> gethj_list(int gid) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<Allinone_ListModel> list = new ArrayList<Allinone_ListModel>();
		String sqlstr = "select emhj_no,emhj_name,coba_shortname,emhj_idcard,emhj_place,emhj_in_time,emhj_in_class,case emhj_state when 1 then '已确认' when 2 then '已借卡' when 8 then '待确定' when 3 then '未还齐' when 4 then '退回' when 5 then '在册' when 6 then '迁出' when 7 then '已打印' else '' end states from EmCensus a inner join Cobase b on a.cid=b.cid where GID="
				+ gid + "";
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
			e.printStackTrace();
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示公积金卡制卡数据
	public List<Allinone_ListModel> getgzk_list(int gid) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<Allinone_ListModel> list = new ArrayList<Allinone_ListModel>();
		String sqlstr = "select ownmonth,username , gjj_no, gjj_cno, gjj_insertblank,gjj_case, gjj_addname,convert(varchar(10),addtime,120) as addtime,  coba_client, State_Name from EmHouseMakeCardInfo a inner join cobase c  on a.cid=c.cid,MakeOrTakeCardState b where a.Gjj_CartState=b.State_Id and b.State_type=1 and GID="
				+ gid + "";
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
			e.printStackTrace();
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示公积金卡领卡数据
	public List<Allinone_ListModel> getglk_list(int gid) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<Allinone_ListModel> list = new ArrayList<Allinone_ListModel>();
		String sqlstr = "select a.ownmonth, username, re_gjjno, re_cgjjno,re_cardid,re_accounttype,convert(varchar(10),re_apptime,120) as re_apptime, cohf_banklk,State_Name from EmHouseTakeCardInfo a inner join CoHousingFund d on a.re_cgjjno=d.cohf_houseid,MakeOrTakeCardState b , CoBase c  where Re_State=b.State_Id and b.state_type=2 and a.Cid=c.cid and GID="
				+ gid + "";
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
			e.printStackTrace();
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示公积金卡领卡数据
	public List<Allinone_ListModel> getsy_list(int gid) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<Allinone_ListModel> list = new ArrayList<Allinone_ListModel>();
		String sqlstr = "select ecin_insurant,ecin_insurer,ecin_castsort,convert(varchar(10),ecin_ef_date,120),convert(varchar(10),ecin_st_date,120),case when ecin_state2=0 then '未申报' else '已申报' end,ecin_idcard,ecin_birthday,ecin_buy_count,ecin_work_city,ecin_work_st from EmComInsurance where ecin_state=1 and GID="
				+ gid + "";
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
				list.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.Close();
		}
		return list;
	}
}
