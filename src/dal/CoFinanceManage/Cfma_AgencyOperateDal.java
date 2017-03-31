package dal.CoFinanceManage;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.SQLException;

import Conn.dbconn;

public class Cfma_AgencyOperateDal {
	private static dbconn conn = new dbconn();

	// 添加机构收款
	public int addAgencyCollect(int coab_id, String cfab_number,
			BigDecimal paidin, String username, String remark) {
		try {
			CallableStatement c = conn
					.getcall("CoFinanceCollectToAgencyAdd_p_lwj(?,?,?,?,?,?)");
			c.setInt(1, coab_id);
			c.setString(2, cfab_number);
			c.setBigDecimal(3, paidin);
			c.setString(4, username);
			c.setString(5, remark);
			c.registerOutParameter(6, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(6);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 机构领款
	public int addAgencyDrawMoney(String cfab_number, int coab_id,
			BigDecimal drawEx, String username, String remark) {
		try {
			CallableStatement c = conn
					.getcall("CoFinanceAgencyDrawMoney_p_lwj(?,?,?,?,?,?)");
			c.setString(1, cfab_number);
			c.setInt(2, coab_id);
			c.setBigDecimal(3, drawEx);
			c.setString(4, username);
			c.setString(5, remark);
			c.registerOutParameter(6, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(6);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 账单核销
	public int agencyBillChargeOff(String cfab_number, String username,
			String reason) {
		try {
			CallableStatement c = conn
					.getcall("CoFinanceAgencyChargeOff_p_lwj(?,?,?,?)");
			c.setString(1, cfab_number);
			c.setString(2, username);
			c.setString(3, reason);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
