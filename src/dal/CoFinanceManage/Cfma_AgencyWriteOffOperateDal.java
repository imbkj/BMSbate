package dal.CoFinanceManage;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.SQLException;

import Conn.dbconn;

public class Cfma_AgencyWriteOffOperateDal {
	private static dbconn conn = new dbconn();

	// 机构冲销
	public int CoFinanceAgencyWriteOff(int coab_id, String wtNumber,
			String stNumber, BigDecimal writeOffEx, String username) {
		try {
			CallableStatement c = conn
					.getcall("CoFinanceAgencyWriteOff_p_lwj(?,?,?,?,?,?)");
			c.setInt(1, coab_id);
			c.setString(2, wtNumber);
			c.setString(3, stNumber);
			c.setBigDecimal(4, writeOffEx);
			c.setString(5, username);
			c.registerOutParameter(6, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(6);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
