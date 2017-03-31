package dal.CoCompact;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.CoCompactModel;
import Model.RoleListModel;
import Util.pinyin4jUtil;

public class CoCompact_CompanyAddDal {
	public Integer AddReg(CoCompactModel em) {
		Integer row = 0;
		String indate;
		String inuredate;
		String signdate;

		if ("".equals(em.getCoco_indate())) {
			indate = null;
		} else {
			indate = em.getCoco_indate();
		}

		if ("".equals(em.getCoco_inuredate())) {
			inuredate = null;
		} else {
			inuredate = em.getCoco_inuredate();
		}

		if ("".equals(em.getCoco_signdate())) {
			signdate = null;
		} else {
			signdate = em.getCoco_signdate();
		}

		dbconn oper = new dbconn();
		try {

			CallableStatement c = oper
					.getcall("CoCompact_wtAdd_P_lsb(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, Integer.parseInt(em.getCid()));
			c.setInt(2, em.getCoco_wttype());
			c.setString(3, em.getCoco_compacttype());
			c.setInt(4, Integer.parseInt(em.getCoco_class()));
			c.setString(5, inuredate);
			c.setString(6, indate);
			c.setString(7, em.getCoco_delay());
			c.setString(8, em.getCoco_money());
			c.setString(9, em.getCoco_invoice());
			c.setString(10, signdate);
			c.setString(11, em.getCoco_compactid());
			c.setString(12, em.getCity());
			c.setString(13, em.getAgency());
			c.setString(14, em.getCoco_remark());
			c.setString(15, em.getCoco_shebao());
			c.setInt(16, em.getCoco_sbfee());
			c.setString(17, em.getCoco_gzmonth());
			c.setString(18, em.getCoco_gsmonth());
			c.setInt(19, em.getCoco_gzpay());
			c.setString(20, em.getCoco_house());
			c.setString(21, em.getCoco_cpp());
			c.setString(22, em.getCoco_cpp());
			c.setInt(23, em.getCoco_housefee());
			c.setInt(24, em.getCoco_sbperfee());
			c.setInt(25, em.getCoco_gsfee());
			c.setString(26, em.getCoco_fileid());
			c.setInt(27, 1);
			c.setString(28, em.getCoco_addname());
			c.registerOutParameter(29, java.sql.Types.INTEGER);
			c.execute();
			row = c.getInt(29);
			return row;
		} catch (Exception e) {
			System.out.println(e.toString());
			return -1;
		}
	}

	// 获取委托机构
	public List<RoleListModel> deplist(String city_name) throws SQLException {
		pinyin4jUtil pyu = new pinyin4jUtil();
		dbconn db = new dbconn();
		ResultSet rs1 = null;
		List<RoleListModel> list1 = new ArrayList<RoleListModel>();
		String sqlstr1 = "select coab_id,coab_name from  StAgencyBase_view where coab_city='"
				+ city_name + "'";
		// System.out.println(sqlstr1);

		try {
			rs1 = db.GRS(sqlstr1);
			while (rs1.next()) {
				RoleListModel model1 = new RoleListModel();
				model1.setRol_id(rs1.getInt("coab_id"));
				model1.setRol_name(rs1.getString("coab_name"));
				model1.setRol_index(pyu.getHeadCharPinYin(rs1
						.getString("coab_name")));
				list1.add(model1);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list1;
	}

	// 判断是否已存在合同号
	public int checkCompactID(String compactID) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs1 = null;
		int chk = 1;

		String sqlstr1 = "select count(*)cou from coCompact WHERE coco_compactid='"
				+ compactID + "'";

		try {
			rs1 = db.GRS(sqlstr1);
			rs1.next();
			chk = rs1.getInt("cou");
		} catch (Exception e) {
			System.out.println(e.toString());
			chk = 1;
		} finally {
			db.Close();
		}
		return chk;
	}
}
