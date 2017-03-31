package dal.Batchprocessing;

import java.sql.CallableStatement;
import java.sql.SQLException;

import Conn.dbconn;
import Model.EmHouseChangeModel;
import Util.UserInfo;

public class EmHouse_BathOperateDal {
	// 公积金新增
	public int EmHouseAdd(EmHouseChangeModel m) {
		dbconn conn = new dbconn();
		try {
			CallableStatement c = conn
					.getcall("EmHouse_BatpAdd_P_cyj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getOwnmonth());
			c.setInt(2, m.getGid());
			c.setInt(3, m.getCid());
			c.setString(4, m.getEmhc_companyid());
			c.setString(5, m.getEmhc_hj());
			c.setDouble(6, m.getEmhc_radix());
			c.setDouble(7, m.getEmhc_trueradix());
			c.setDouble(8, m.getEmhc_cpp());
			c.setDouble(9, m.getEmhc_opp());
			c.setString(10, m.getEmhc_title());
			c.setString(11, m.getEmhc_degree());
			c.setString(12, m.getEmhc_wifename());
			c.setString(13, m.getEmhc_wifeidcard());
			c.setString(14, UserInfo.getUsername());
			c.setString(15, m.getEmhc_change());
			c.setString(16, m.getEmhc_mobile());
			c.setInt(17, m.getEmhc_single());
			c.setString(18, "系统导入");
			c.setInt(19, m.getEmhc_ifprogress());
			c.registerOutParameter(20, java.sql.Types.INTEGER);
			c.execute();
			int k = c.getInt(20);
			return k;
		} catch (SQLException e) {
			return -2;
		}
	}

	// 公积金调入
	public int EmHouseMoveIn(EmHouseChangeModel m) {
		dbconn conn = new dbconn();
		try {
			CallableStatement c = conn
					.getcall("[EmHouse_BatpMovein_P_cyj](?,?,?,?)");
			c.setInt(1, m.getHsup_id());
			c.setInt(2, m.getGid());
			c.setString(3, UserInfo.getUsername());
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			int k = c.getInt(4);
			return k;
		} catch (SQLException e) {
			return -2;
		}
	}

	// 公积金接管
	public int EmHouseTakeOver(EmHouseChangeModel m) {
		dbconn conn = new dbconn();
		try {
			CallableStatement c = conn
					.getcall("[EmHouse_BatpTakeOver_P_cyj](?,?,?,?)");
			c.setInt(1, m.getHsup_id());
			c.setInt(2, m.getGid());
			c.setString(3, UserInfo.getUsername());
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			int k = c.getInt(4);
			return k;
		} catch (SQLException e) {
			return -2;
		}
	}

	// 公积金数据导进临时表
	public int EmHouseUpload(EmHouseChangeModel m) {
		dbconn conn = new dbconn();
		try {
			CallableStatement c = conn
					.getcall("EmHouse_Upload_P_cyj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getOwnmonth());
			c.setInt(2, m.getGid());
			if (m.getCid() == null) {
				m.setCid(0);
			}
			c.setInt(3, m.getCid());
			c.setString(4, m.getEmhc_name());
			c.setString(5, m.getEmhc_idcard());
			c.setString(6, m.getEmhc_companyid());
			c.setString(7, m.getEmhc_hj());
			if (m.getEmhc_radix() == null) {
				m.setEmhc_radix(0.0);
			}
			c.setDouble(8, m.getEmhc_radix());
			if (m.getEmhc_trueradix() == null) {
				m.setEmhc_trueradix(0.0);
			}
			c.setDouble(9, m.getEmhc_trueradix());
			if (m.getEmhc_cpp() == null) {
				m.setEmhc_cpp(0.0);
			}
			c.setDouble(10, m.getEmhc_cpp());
			if (m.getEmhc_opp() == null) {
				m.setEmhc_opp(0.0);
			}
			c.setDouble(11, m.getEmhc_opp());
			c.setString(12, m.getEmhc_title());
			c.setString(13, m.getEmhc_degree());
			c.setString(14, m.getEmhc_wifename());
			c.setString(15, m.getEmhc_wifeidcard());
			c.setString(16, UserInfo.getUsername());
			c.setString(17, m.getEmhc_change());
			c.setString(18, m.getEmhc_mobile());
			if (m.getEmhc_single() == null) {
				m.setEmhc_single(-1);
			}
			
			c.setInt(19, m.getEmhc_single());
			if (m.getEmhc_type().equals(4)) {
				c.setString(20, "系统导入:独立户接管");
			}else {
				c.setString(20, "系统导入");
			}
			
			c.setInt(21, m.getEmhc_ifprogress());
			c.setInt(22, m.getEmhc_type());
			c.setString(23, m.getEmhc_houseid());
			c.setString(24, m.getErrorMsg());
			c.setString(25, UserInfo.getUsername());
			c.setString(26, m.getEmhc_excompanyid());
			c.setString(27, m.getEmhc_excompany());
			c.setString(28, m.getHsup_batchnumber());
			c.setString(29, m.getEmhc_name());
			c.registerOutParameter(30, java.sql.Types.INTEGER);
			c.execute();
			int k = c.getInt(30);
			return k;
		} catch (SQLException e) {
			e.printStackTrace();
			return -2;
		}
	}

	public Integer delEmHouseUpload(Integer hsup_id) {
		String sql = "delete from EmHouseUpload where hsup_id=" + hsup_id;
		dbconn db = new dbconn();
		Integer k = db.execQuery(sql);

		return k;
	}

	public Integer UpdateEmHouseUpload(Integer hsup_id) {
		String sql = "update EmHouseUpload set hsup_state=1 where hsup_id="
				+ hsup_id;
		dbconn db = new dbconn();
		Integer k = db.execQuery(sql);

		return k;
	}
}
