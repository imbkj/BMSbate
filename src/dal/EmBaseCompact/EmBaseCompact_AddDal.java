package dal.EmBaseCompact;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.CI_InsurantClaimModel;
import Model.EmBaseCompactModel;
import Util.UserInfo;

public class EmBaseCompact_AddDal {
	private static dbconn conn = new dbconn();
	String username = UserInfo.getUsername();

	public int add_emcompact(EmBaseCompactModel em) throws Exception {
		String incept;
		if (em.getEbco_incept_date().equals("")) {
			incept = null;
		} else {
			incept = em.getEbco_incept_date();
		}

		String maturity;
		if (em.getEbco_maturity_date().equals("")) {
			maturity = null;
		} else {
			maturity = em.getEbco_maturity_date();
		}

		String probation;
		if (em.getEbco_probation_incept().equals("")) {
			probation = null;
		} else {
			probation = em.getEbco_probation_incept();
		}

		String mdate;
		if (em.getEbco_probation_mdate().equals("")) {
			mdate = null;
		} else {
			mdate = em.getEbco_probation_mdate();
		}

		try {
			CallableStatement c = conn
					.getcall("EmBaseCompactAdd_P_zzq(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setString(1, em.getGid());
			c.setString(2, em.getCid());
			c.setString(3, incept);
			c.setString(4, em.getEbco_term());
			c.setString(5, maturity);
			c.setString(6, probation);
			c.setString(7, mdate);
			c.setString(8, em.getEbco_wage());
			c.setString(9, em.getEbco_wage_bt());
			c.setString(10, em.getEbco_probation_wage());
			c.setString(11, em.getEbco_probation_bt());
			c.setString(12, em.getEbco_wage_gz());
			c.setString(13, em.getEbco_probation_gz());
			c.setString(14, em.getEbco_payroll_date());
			c.setString(15, em.getEbco_payroll_mode());
			c.setString(16, em.getEbco_work_place());
			c.setString(17, em.getEbco_working_station());
			c.setString(18, em.getEbco_teaching_hour());
			c.setString(19, em.getEbco_furlough_system());
			c.setString(20, em.getEbco_work_content());
			c.setString(21, em.getEbco_other_business());
			c.setString(22, username);
			c.setString(23, em.getEbco_state());
			c.setString(24, em.getEbco_change());
			c.registerOutParameter(25, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(25);
		} catch (SQLException e) {
			return 0;
		}
	}

	public int sign_emcompact(EmBaseCompactModel em) throws Exception {
		String sign;
		if (em.getEbco_sign_date().equals("")) {
			sign = null;
		} else {
			sign = em.getEbco_sign_date();
		}

		try {
			CallableStatement c = conn
					.getcall("EmBaseCompactSign_P_zzq(?,?,?,?)");
			c.setInt(1, em.getEbco_id());
			c.setString(2, sign);
			c.setString(3, username);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			return 0;
		}
	}

	public int filing_emcompact(EmBaseCompactModel em) throws Exception {
		String filing;
		if (em.getEbco_sign_date().equals("")) {
			filing = null;
		} else {
			filing = em.getEbco_sign_date();
		}

		try {
			CallableStatement c = conn
					.getcall("EmBaseCompactFiling_P_zzq(?,?,?,?)");
			c.setInt(1, em.getEbco_id());
			c.setString(2, filing);
			c.setString(3, username);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			return 0;
		}
	}

	public int ren_emcompact(EmBaseCompactModel em) throws Exception {
		String incept;
		if (em.getEbco_incept_date().equals("")) {
			incept = null;
		} else {
			incept = em.getEbco_incept_date();
		}

		String maturity;
		if (em.getEbco_maturity_date().equals("")) {
			maturity = null;
		} else {
			maturity = em.getEbco_maturity_date();
		}

		String probation;
		if (em.getEbco_probation_incept().equals("")) {
			probation = null;
		} else {
			probation = em.getEbco_probation_incept();
		}

		String mdate;
		if (em.getEbco_probation_mdate().equals("")) {
			mdate = null;
		} else {
			mdate = em.getEbco_probation_mdate();
		}

		try {
			CallableStatement c = conn
					.getcall("EmBaseCompactRen_P_zzq(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setString(1, em.getGid());
			c.setString(2, em.getCid());
			c.setString(3, incept);
			c.setString(4, em.getEbco_term());
			c.setString(5, maturity);
			c.setString(6, probation);
			c.setString(7, mdate);
			c.setString(8, em.getEbco_wage());
			c.setString(9, em.getEbco_wage_bt());
			c.setString(10, em.getEbco_probation_wage());
			c.setString(11, em.getEbco_probation_bt());
			c.setString(12, em.getEbco_wage_gz());
			c.setString(13, em.getEbco_probation_gz());
			c.setString(14, em.getEbco_payroll_date());
			c.setString(15, em.getEbco_payroll_mode());
			c.setString(16, em.getEbco_work_place());
			c.setString(17, em.getEbco_working_station());
			c.setString(18, em.getEbco_teaching_hour());
			c.setString(19, em.getEbco_furlough_system());
			c.setString(20, em.getEbco_work_content());
			c.setString(21, em.getEbco_other_business());
			c.setString(22, username);
			c.setString(23, em.getEbco_state());
			c.setString(24, em.getEbco_change());
			c.registerOutParameter(25, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(25);
		} catch (SQLException e) {
			return 0;
		}
	}

	public int end_emcompact(EmBaseCompactModel em) throws Exception {
		String incept;
		if (em.getEbco_incept_date().equals("")) {
			incept = null;
		} else {
			incept = em.getEbco_incept_date();
		}

		try {

			CallableStatement c = conn
					.getcall("EmBaseCompactEnd_P_zzq(?,?,?,?,?)");
			c.setString(1, em.getGid());
			c.setString(2, incept);
			c.setString(3, em.getEbco_term());
			c.setString(4, em.getEbco_maturity_date());
			c.registerOutParameter(5, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(5);
		} catch (SQLException e) {
			return 0;
		}
	}

	// 查询该员工是否已有社保信息
	public boolean check_ciin(int gid) {
		String sql = "select count(*)count from EmBaseCompact where GID=? and ebco_change<>'终止' and ebco_change<>'解除'";
		PreparedStatement psmt = conn.getpre(sql);
		System.out.println(sql);
		boolean bool = false;
		try {
			psmt.setInt(1, gid);
			ResultSet rs = psmt.executeQuery();
			if (rs.next())
				if (rs.getInt("count") > 0)
					bool = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}

	// 查询该员工是否已有社保信息
	public boolean ch_xadd(int gid) {
		String sql = "select DATEDIFF(d,getdate(),ebco_maturity_date) count from EmBaseCompact where GID=? and ebco_maturity_date is not null and ebco_change<>'终止' and ebco_change<>'解除'";
		PreparedStatement psmt = conn.getpre(sql);
		boolean ch_st = false;
		try {
			psmt.setInt(1, gid);
			ResultSet rs = psmt.executeQuery();
			if (rs.next())
				if (rs.getInt("count") < 90)
					ch_st = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ch_st;
	}

	// 备注添加
	public int getremark_add(String eccl_id, String content) {
		dbconn conn = new dbconn();
		try {
			CallableStatement c = conn
					.getcall("EmBaseCompactRemark_P_zzq(?,?,?,?)");
			c.setString(1, eccl_id);
			c.setString(2, content);
			c.setString(3, username);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return 1;

		} catch (SQLException e) {
			return 1;
		}
	}

	// 获取商保索赔处理中数据
	public List<CI_InsurantClaimModel> embasecompact_remarklist(String eccl_id)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_InsurantClaimModel> list = new ArrayList<CI_InsurantClaimModel>();
		String sqlstr = "select eccr_content,eccr_addname,cast(year(eccr_addtime) as varchar(4))+'-'+cast(month(eccr_addtime) as varchar(4))+'-'+cast(day(eccr_addtime) as varchar(4)) eccr_addtime from EmBaseCompactZXRecord where eccr_eccl_id="
				+ eccl_id;
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CI_InsurantClaimModel model = new CI_InsurantClaimModel();
				model.setEccl_remark(rs.getString("eccr_content"));
				model.setEccl_addname(rs.getString("eccr_addname"));
				model.setEccl_addtime(rs.getString("eccr_addtime"));
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
