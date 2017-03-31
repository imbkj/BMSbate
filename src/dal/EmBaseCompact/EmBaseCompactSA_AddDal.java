package dal.EmBaseCompact;

import java.sql.CallableStatement;
import java.sql.SQLException;

import Conn.dbconn;
import Model.EmBaseCompactModel;
import Util.UserInfo;

public class EmBaseCompactSA_AddDal {
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
			maturity =  em.getEbco_maturity_date() ;
		}
		
		String probation;
		if (em.getEbco_probation_incept().equals("")) {
			probation = null;
		} else {
			probation =em.getEbco_probation_incept();
		}
		
		String mdate;
		if (em.getEbco_probation_mdate().equals("")) {
			mdate = null;
		} else {
			mdate =em.getEbco_probation_mdate();
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
					.getcall("EmBaseCompactSASign_P_zzq(?,?,?,?)");
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
					.getcall("EmBaseCompactSAFiling_P_zzq(?,?,?,?)");
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
}
