package dal.EmBaseCompact;

import java.sql.CallableStatement;
import java.sql.SQLException;

import com.sun.mail.iap.Response;

import Conn.dbconn;
import Model.EmBaseCompactModel;
import Util.UserInfo;

public class EmBaseCompact_UploadDal {
	private static dbconn conn = new dbconn();
	String username = UserInfo.getUsername();

	// 文件上传
	public int DocFileUpload(int cid, String emcompact_temp, String url,
			String size, String addname, String compact_type) {
		try {
			CallableStatement c = conn
					.getcall("EmBaseCompactUpload_P_zzq(?,?,?,?,?,?,?)");
			c.setInt(1, cid);
			c.setString(2, emcompact_temp);
			c.setString(3, url);
			c.setString(4, size);
			c.setString(5, addname);
			c.setString(6, compact_type);
			c.registerOutParameter(7, java.sql.Types.INTEGER);
			c.execute();

			return c.getInt(7);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 劳动合同版本上传
	public int VerUpload(int cid, String emcompact_temp, String url,
			String size, String addname, String compact_type) {
		try {
			CallableStatement c = conn
					.getcall("CompactUploadVer_P_zzq(?,?,?,?,?,?,?)");
			c.setInt(1, 1);
			c.setString(2, emcompact_temp);
			c.setString(3, url);
			c.setString(4, size);
			c.setString(5, addname);
			c.setString(6, compact_type);
			c.registerOutParameter(7, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(7);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 公司合同版本上传
	public int CoVerUpload(int cid, String emcompact_temp, String url,
			String size, String addname, String coco_type) {
		try {
			CallableStatement c = conn
					.getcall("CompactUploadVer_P_zzq(?,?,?,?,?,?,?)");
			c.setInt(1, 2);
			c.setString(2, emcompact_temp);
			c.setString(3, url);
			c.setString(4, size);
			c.setString(5, addname);
			c.setString(6, coco_type);
			c.registerOutParameter(7, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(7);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 模板审核
	public int AutDocFileUpload(int ebct_id, String addname, int ebct_class) {
		try {
			CallableStatement c = conn
					.getcall("EmBaseCompactUploadAut_P_zzq(?,?,?,?)");
			c.setInt(1, ebct_id);
			c.setString(2, addname);
			c.setInt(3, ebct_class);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 劳动合同新签
	public int Add_Emcompact(EmBaseCompactModel em) {
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
					.getcall("EmBaseCompactAdd_P_zzq(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
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
			c.setString(25, em.getEbco_probation_term());
			c.setString(26, em.getEbco_compact_type());
			c.registerOutParameter(27, java.sql.Types.INTEGER);
			c.execute();

			return c.getInt(27);
		} catch (SQLException e) {
			return 0;
		}
	}

	// 劳动合同续签
	public int Ren_Emcompact(EmBaseCompactModel em) {
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
					.getcall("EmBaseCompactRen_P_zzq(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
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
			c.setString(25, em.getEbco_probation_term());
			c.setString(26, em.getEbco_compact_type());
			c.registerOutParameter(27, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(27);
		} catch (SQLException e) {
			return 0;
		}
	}

	// 劳动合同修改
	public String Edit_Emcompact(EmBaseCompactModel em) {
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
			System.out.println("0x0x0x0x0x1111");
			System.out.println(em.getGid());
			CallableStatement c = conn
					.getcall("EmBaseCompactEdit_P_zzq(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
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
			c.setString(25, em.getEbco_probation_term());
			c.setString(26, em.getEbco_compact_type());
			c.registerOutParameter(27, java.sql.Types.INTEGER);
			c.execute();
			return c.getString(27);
		} catch (SQLException e) {
			return "0";
		}
	}

	// 劳动合同制作
	public int Mak_Emcompact(int ebcc_id, String addname) {
		try {
			CallableStatement c = conn.getcall("EmBaseCompactMak_P_zzq(?,?,?)");
			c.setInt(1, ebcc_id);
			c.setString(2, addname);
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 劳动合同审核
	public int Aut_Emcompact(int ebcc_id, String addname) {
		try {
			CallableStatement c = conn.getcall("EmBaseCompactAut_P_zzq(?,?,?)");
			c.setInt(1, ebcc_id);
			c.setString(2, addname);
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 劳动合同打印
	public int Print_Emcompact(int ebcc_id, String addname) {
		try {
			CallableStatement c = conn
					.getcall("EmBaseCompactPrint_P_zzq(?,?,?)");
			c.setInt(1, ebcc_id);
			c.setString(2, addname);
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 劳动合同盖章
	public int GZ_Emcompact(int ebcc_id, String addname) {
		try {
			CallableStatement c = conn.getcall("EmBaseCompactGZ_P_zzq(?,?,?)");
			c.setInt(1, ebcc_id);
			c.setString(2, addname);
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 劳动合同签回
	public int Qd_Emcompact(int ebcc_id, String qd_type, String addname,String qd_date) {
		try {
			System.out.println("xxxx");
			System.out.println(ebcc_id);
			System.out.println(qd_type);
			System.out.println(qd_date);
			CallableStatement c = conn
					.getcall("EmBaseCompactQd_P_zzq(?,?,?,?)");
			c.setInt(1, ebcc_id);
			c.setString(2, qd_type);
			c.setString(3, qd_date);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 劳动合同签回
	public int Sign_Emcompact(int ebcc_id, String sign_date, String addname) {
		try {
			CallableStatement c = conn
					.getcall("EmBaseCompactSign_P_zzq(?,?,?,?)");
			c.setInt(1, ebcc_id);
			c.setString(2, sign_date);
			c.setString(3, addname);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 劳动合同签回
	public int Filing_Emcompact(int ebcc_id, String filing_date, String addname) {
		try {
			CallableStatement c = conn
					.getcall("EmBaseCompactFiling_P_zzq(?,?,?,?)");
			c.setInt(1, ebcc_id);
			c.setString(2, filing_date);
			c.setString(3, addname);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 劳动合同退回
	public int outEmCompact(int coco_id, String remark) {
		try {

			CallableStatement c = conn.getcall("EmBaseCompactOut_P_zzq(?,?,?)");
			c.setInt(1, coco_id);
			c.setString(2, remark);
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);
		} catch (SQLException e) {
			return 1;
		}
	}

	// 劳动合同变更
	public int Add_EmcompactSA(String ebco_id, String ebcu_indate,
			String ebcu_incept_date_F, String ebcu_incept_date_L,
			String ebcu_maturity_date_F, String ebcu_maturity_date_L,
			String ebcu_wage_F, String ebcu_wage_L, String ebcu_work_place_F,
			String ebcu_work_place_L, String ebcu_working_station_F,
			String ebcu_working_station_L, String ebcu_addname) {
		String incept;
		if (ebcu_incept_date_F.equals("")) {
			incept = null;
		} else {
			incept = ebcu_incept_date_F;
		}

		String maturity;
		if (ebcu_incept_date_L.equals("")) {
			maturity = null;
		} else {
			maturity = ebcu_incept_date_L;
		}

		String probation;
		if (ebcu_maturity_date_F.equals("")) {
			probation = null;
		} else {
			probation = ebcu_maturity_date_F;
		}

		String mdate;
		if (ebcu_maturity_date_L.equals("")) {
			mdate = null;
		} else {
			mdate = ebcu_maturity_date_L;
		}

		String incept1;
		if (ebcu_indate.equals("")) {
			incept1 = null;
		} else {
			incept1 = ebcu_indate;
		}

		System.out.println(ebco_id);
		System.out.println(incept);
		System.out.println(maturity);
		System.out.println(probation);
		System.out.println(mdate);
		System.out.println(incept1);
		System.out.println(ebcu_wage_F);
		System.out.println(ebcu_wage_L);
		System.out.println(ebcu_work_place_F);
		System.out.println(ebcu_work_place_L);
		System.out.println(ebcu_working_station_F);
		System.out.println(ebcu_working_station_L);
		System.out.println(ebcu_addname);

		try {
			CallableStatement c = conn
					.getcall("EmBaseCompactSAAdd_P_zzq(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setString(1, ebco_id);
			c.setString(2, incept1);
			c.setString(3, incept);
			c.setString(4, maturity);
			c.setString(5, probation);
			c.setString(6, mdate);
			c.setString(7, ebcu_wage_F);
			c.setString(8, ebcu_wage_L);
			c.setString(9, ebcu_work_place_F);
			c.setString(10, ebcu_work_place_L);
			c.setString(11, ebcu_working_station_F);
			c.setString(12, ebcu_working_station_L);
			c.setString(13, ebcu_addname);
			c.registerOutParameter(14, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(14);
		} catch (SQLException e) {
			return 0;
		}
	}

	// 劳动合同审核
	public int Aut_EmcompactSA(int ebcc_id, String addname) {
		try {
			CallableStatement c = conn
					.getcall("EmBaseCompactSAAut_P_zzq(?,?,?)");
			c.setInt(1, ebcc_id);
			c.setString(2, addname);
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 劳动合同打印
	public int Print_EmcompactSA(int ebcc_id, String addname) {
		try {
			CallableStatement c = conn
					.getcall("EmBaseCompactSAPrint_P_zzq(?,?,?)");
			c.setInt(1, ebcc_id);
			c.setString(2, addname);
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 劳动合同签回
	public int Sign_EmcompactSA(int ebcc_id, String sign_date, String addname) {
		try {
			CallableStatement c = conn
					.getcall("EmBaseCompactSASign_P_zzq(?,?,?,?)");
			c.setInt(1, ebcc_id);
			c.setString(2, sign_date);
			c.setString(3, addname);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 劳动合同归档
	public int Filing_EmcompactSA(int ebcc_id, String filing_date,
			String addname) {
		try {
			CallableStatement c = conn
					.getcall("EmBaseCompactSAFiling_P_zzq(?,?,?,?)");
			c.setInt(1, ebcc_id);
			c.setString(2, filing_date);
			c.setString(3, addname);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);

		} catch (SQLException e) {
			return 1;
		}
	}
}
