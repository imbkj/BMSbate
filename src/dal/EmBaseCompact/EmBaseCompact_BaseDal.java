package dal.EmBaseCompact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.Allinone_ListModel;
import Model.EmBaseCompactListModel;

public class EmBaseCompact_BaseDal {
	private dbconn conn = new dbconn();

	// 根据GID获取员工社保在册表信息
	public List<EmBaseCompactListModel> getEmBase_Base(int gid) {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmBaseCompactListModel> list = new ArrayList<EmBaseCompactListModel>();
		String sqlstr = "select emba_name,coba_shortname from embase a left join cobase b on b.cid=a.cid where gid="
				+ gid;
		try {
			rs = db.GRS(sqlstr);
			if (rs.next()) {
				EmBaseCompactListModel model = new EmBaseCompactListModel();
				model.setName(rs.getString("emba_name"));
				model.setCompany(rs.getString("coba_shortname"));
				list.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据tapr_id获取员工cid
	public List<EmBaseCompactListModel> getEmBase_cid(int tapr_id) {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmBaseCompactListModel> list = new ArrayList<EmBaseCompactListModel>();
		String sqlstr = "select cid from embasecompactchange where ebcc_tapr_id="
				+ tapr_id;
		try {
			rs = db.GRS(sqlstr);
			if (rs.next()) {
				EmBaseCompactListModel model = new EmBaseCompactListModel();
				model.setCid(rs.getString("cid"));
				list.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
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

	// 显示劳动合同数据
	public List<Allinone_ListModel> getemcompact_list(int gid)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<Allinone_ListModel> list = new ArrayList<Allinone_ListModel>();
		String sqlstr = "select convert(varchar(10),ebcc_incept_date,120),convert(varchar(10),ebcc_maturity_date,120),ebcc_term,convert(varchar(10),ebcc_sign_date,120),convert(varchar(10),ebcc_filing_date,120),ebcc_change,case when ebcc_state=0 then '合同未制作' else '' end+case when ebcc_state=1 then '合同未审核' else '' end+case when ebcc_state=2 then '合同未打印' else '' end+case when ebcc_state=3 then '合同未盖章' else '' end+case when ebcc_state=4 then '合同待签回' else '' end+case when ebcc_state=5 then '合同未签回' else '' end+case when ebcc_state=6 then '合同未归档' else '' end+case when ebcc_state=7 then '合同已归档' else '' end+case when ebcc_state=10 then '合同退回' else '' end+case when ebcc_state=11 then '合同历史数据' else '' end from embasecompactchange where GID="
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

	// 显示劳动合同数据
	public List<Allinone_ListModel> getemcompact_chlist(String ebco_id)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<Allinone_ListModel> list = new ArrayList<Allinone_ListModel>();
		String sqlstr = "select convert(varchar(10),ebcc_incept_date,120),convert(varchar(10),ebcc_maturity_date,120),ebcc_term,convert(varchar(10),ebcc_sign_date,120),convert(varchar(10),ebcc_filing_date,120),ebcc_change,case when ebcc_state=0 then '合同未制作' else '' end+case when ebcc_state=1 then '合同未审核' else '' end+case when ebcc_state=2 then '合同未打印' else '' end+case when ebcc_state=3 then '合同未盖章' else '' end+case when ebcc_state=4 then '合同待签回' else '' end+case when ebcc_state=5 then '合同未签回' else '' end+case when ebcc_state=6 then '合同未归档' else '' end+case when ebcc_state=7 then '合同已归档' else '' end+case when ebcc_state=10 then '合同退回' else '' end+case when ebcc_state=11 then '合同历史数据' else '' end,ebcc_id,convert(varchar(10),ebcc_addtime,120) from embasecompactchange where ebcc_ebco_id="
				+ ebco_id + " order by ebcc_id desc";
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

	// 显示劳动合同数据
	public List<Allinone_ListModel> getemcompact_baselist(int gid)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<Allinone_ListModel> list = new ArrayList<Allinone_ListModel>();
		String sqlstr = "select convert(varchar(10),ebco_incept_date,120),convert(varchar(10),ebco_maturity_date,120),ebco_term,ebco_change,ebco_addtime,ebco_id from embasecompact where GID="
				+ gid + "";
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
				list.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.Close();
		}
		return list;
	}

	// 查询员工是否存在在册数据
	public boolean getupdateInfo(Integer gid) {
		boolean b = false;
		List<EmBaseCompactListModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select CONVERT(VARCHAR(10),gid)GID from EmBaseCompact where gid=?";
		try {
			list = db.find(sql, EmBaseCompactListModel.class, null, gid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (list.size() > 0) {
			b = true;
		}
		return b;
	}
}
