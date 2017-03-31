package dal.EmBodyCheck;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import Conn.dbconn;
import Model.EmBcSetupAddressModel;
import Model.EmBcSetupModel;

public class EmBcSetup_OperateDal {
	// 体检机构新增
	public Integer EmBcSetupAdd(EmBcSetupModel m) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
					.getcall("EmBc_SetupAdd_cyj(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setString(1, m.getEbcs_hospital());
			c.setString(2, m.getEbsa_address());
			c.setString(3, m.getEbcs_name());
			c.setString(4, m.getEbcs_phone());
			c.setDate(5, changedate(m.getEbcs_inuredate()));
			c.setDate(6, changedate(m.getEbcs_indate()));
			c.setString(7, m.getEbcs_flow());
			c.setString(8, m.getEbcs_balance());
			c.setString(9, m.getEbcs_remark());
			c.setString(10, m.getEbcs_addname());
			c.setBigDecimal(11, m.getEbcs_limit());
			c.setInt(12, m.getEbcs_pstate());
			c.registerOutParameter(13, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(13);
		} catch (SQLException e) {
			return 0;
		}
	}

	// 体检机构新增
	public Integer EmBcSetupUpdate(EmBcSetupModel m) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
					.getcall("EmBc_Setuppdate_cyj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getEbcs_id());
			c.setString(2, m.getEbcs_hospital());
			c.setString(3, m.getEbsa_address());
			c.setString(4, m.getEbcs_name());
			c.setString(5, m.getEbcs_phone());
			c.setDate(6, changedate(m.getEbcs_inuredate()));
			c.setDate(7, changedate(m.getEbcs_indate()));
			c.setString(8, m.getEbcs_flow());
			c.setString(9, m.getEbcs_balance());
			c.setString(10, m.getEbcs_remark());
			c.setString(11, m.getEbcs_addname());
			c.setBigDecimal(12, m.getEbcs_limit());
			c.setInt(13, m.getEbcs_pstate());
			c.setInt(14, m.getEbcs_state());
			c.registerOutParameter(15, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(15);
		} catch (SQLException e) {
			return 0;
		}
	}

	// 更新机构地址
	public int UpdateEmBcSetupAddress(EmBcSetupAddressModel m) {
		int k = 0;
		try {
			dbconn db = new dbconn();
			String sql = "update EmBcSetupAddress set ebsa_address='"
					+ m.getEbsa_address() + "'," + "ebsa_modname='"
					+ m.getEbsa_modname() + "',ebsa_modtime='"
					+ changedate(m.getEbsa_modtime()) + "'," + "ebsa_state="
					+ m.getEbsa_state() + ",ebsa_ystate=" + m.getEbsa_ystate()
					+ ",ebsa_istate=" + m.getEbsa_istate() + ",ebsa_w1="
					+ m.getEbsa_w1() + ",ebsa_w2=" + m.getEbsa_w2()
					+ ",ebsa_w3=" + m.getEbsa_w3() + ",ebsa_w4="
					+ m.getEbsa_w4() + ",ebsa_w5=" + m.getEbsa_w5()
					+ ",ebsa_w6=" + m.getEbsa_w6() + ",ebsa_w7="
					+ m.getEbsa_w7() + " where ebsa_id=" + m.getEbsa_id();
			k = db.execQuery(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k;
	}

	// 新增体检机构地址
	public int AddEmBcSetupAddress(EmBcSetupAddressModel m) {
		int k = 0;
		try {
			dbconn db = new dbconn();
			String sql = "INSERT INTO EmBcSetupAddress(ebsa_ebcs_id,ebsa_address,ebsa_addname)"
					+ " VALUES("
					+ m.getEbsa_ebcs_id()
					+ ",'"
					+ m.getEbsa_address() + "','" + m.getEbsa_addname() + "')";
			k = db.execQuery(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k;
	}

	// 把字符串转换成时间
	public Date changedate(String s) {
		Date da = null;
		if (s != null && !s.equals("") && s != "") {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 小写的mm表示的是分钟
			java.util.Date date = null;
			try {
				date = sdf.parse(s);
				if (date != null && !date.equals("")) {
					java.sql.Date reda = new java.sql.Date(date.getTime());
					da = reda;
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return da;
	}

	// 更新机构介绍文件
	public int UpdateEmBcSetupAddressFile(String ebsa_doc, Integer ebsa_id) {
		int k = 0;
		try {
			dbconn db = new dbconn();
			String sql = "update EmBcSetupAddress set ebsa_doc='" + ebsa_doc
					+ "' where ebsa_id=" + ebsa_id;
			k = db.execQuery(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k;
	}
}
