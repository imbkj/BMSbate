package dal.EmBaseCompact;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.EmBaseCompactBModel;

public class EmBaseCompact_UpDal {
	private dbconn conn = new dbconn();

	// 获取该员工分配的商业类型
	public List<EmBaseCompactBModel> getcompact_base(String gid)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		String wage_state=null;
		List<EmBaseCompactBModel> list = new ArrayList<EmBaseCompactBModel>();
		String sqlstr = "select top 1 * from EmBaseCompact where ebco_change<>'结束' and gid="+gid+" order by ebco_id desc";
		System.out.println(sqlstr);
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				EmBaseCompactBModel model = new EmBaseCompactBModel();
				model.setEbco_incept_date(rs.getDate("ebco_incept_date"));
				model.setEbco_maturity_date(rs.getDate("ebco_maturity_date"));
				model.setEbco_term(rs.getString("ebco_term"));
				model.setEbco_probation_term(rs.getString("ebco_probation_term"));
				model.setEbco_probation_incept(rs.getDate("ebco_probation_incept"));
				model.setEbco_probation_mdate(rs.getDate("ebco_probation_mdate"));
				model.setEbco_wage(rs.getString("ebco_wage"));
				model.setEbco_wage_bt(rs.getString("ebco_wage_bt"));
				model.setEbco_probation_wage(rs.getString("ebco_probation_wage"));
				model.setEbco_probation_bt(rs.getString("ebco_probation_bt"));
				model.setEbco_wage_gz(rs.getString("ebco_wage_gz"));
				model.setEbco_probation_gz(rs.getString("ebco_probation_gz"));
				model.setEbco_payroll_date(rs.getString("ebco_payroll_date"));
				model.setEbco_payroll_mode(rs.getString("ebco_payroll_mode"));
				model.setEbco_work_place(rs.getString("ebco_work_place"));
				model.setEbco_working_station(rs.getString("ebco_working_station"));
				model.setEbco_teaching_hour(rs.getString("ebco_teaching_hour"));
				model.setEbco_furlough_system(rs.getString("ebco_furlough_system"));
				model.setEbco_work_content(rs.getString("ebco_work_content"));
				model.setEbco_other_business(rs.getString("ebco_other_business"));
				model.setEbco_compact_type(rs.getString("ebco_compact_type"));
				if(!rs.getString("ebco_wage").equals("")){
					wage_state="1";
				}
				if(!rs.getString("ebco_wage_gz").equals("")){
					wage_state="2";
				}
				model.setEbco_change(wage_state);
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}
	
	// 获取该员工分配的商业类型
		public List<EmBaseCompactBModel> getcompact_editbase(String ebcc_id)
				throws SQLException {
			dbconn db = new dbconn();
			ResultSet rs = null;
			String wage_state=null;
			List<EmBaseCompactBModel> list = new ArrayList<EmBaseCompactBModel>();
			String sqlstr = "select top 1 * from EmBaseCompactChange where ebcc_id="+ebcc_id;
			System.out.println(sqlstr);
			try {
				rs = db.GRS(sqlstr);
				while (rs.next()) {
					EmBaseCompactBModel model = new EmBaseCompactBModel();
					model.setEbco_incept_date(rs.getDate("ebcc_incept_date"));
					model.setEbco_maturity_date(rs.getDate("ebcc_maturity_date"));
					model.setEbco_term(rs.getString("ebcc_term"));
					model.setEbco_probation_term(rs.getString("ebcc_probation_term"));
					model.setEbco_probation_incept(rs.getDate("ebcc_probation_incept"));
					model.setEbco_probation_mdate(rs.getDate("ebcc_probation_mdate"));
					model.setEbco_wage(rs.getString("ebcc_wage"));
					model.setEbco_wage_bt(rs.getString("ebcc_wage_bt"));
					model.setEbco_probation_wage(rs.getString("ebcc_probation_wage"));
					model.setEbco_probation_bt(rs.getString("ebcc_probation_bt"));
					model.setEbco_wage_gz(rs.getString("ebcc_wage_gz"));
					model.setEbco_probation_gz(rs.getString("ebcc_probation_gz"));
					model.setEbco_payroll_date(rs.getString("ebcc_payroll_date"));
					model.setEbco_payroll_mode(rs.getString("ebcc_payroll_mode"));
					model.setEbco_work_place(rs.getString("ebcc_work_place"));
					model.setEbco_working_station(rs.getString("ebcc_working_station"));
					model.setEbco_teaching_hour(rs.getString("ebcc_teaching_hour"));
					model.setEbco_furlough_system(rs.getString("ebcc_furlough_system"));
					model.setEbco_work_content(rs.getString("ebcc_work_content"));
					model.setEbco_other_business(rs.getString("ebcc_other_business"));
					model.setEbco_compact_type(rs.getString("ebcc_compact_type"));
					if(!rs.getString("ebcc_wage").equals("")){
						wage_state="1";
					}
					if(!rs.getString("ebcc_wage_gz").equals("")){
						wage_state="2";
					}
					model.setEbco_change(wage_state);
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
