package dal.EmSheBao;

import java.sql.CallableStatement;
import java.sql.SQLException;

import Conn.dbconn;

public class Emsi_CheckOperateDal {
	private static dbconn conn = new dbconn();

	// 更新社保补缴表状态
	public int upBjDeclare(int id, int ifdeclare, String reason, String username) {
		try {
			CallableStatement c = conn
					.getcall("EMSI_BjDeclare_p_lwj(?,?,?,?,?)");
			c.setInt(1, id);
			c.setInt(2, ifdeclare);
			c.setString(3, reason);
			c.setString(4, username);
			c.registerOutParameter(5, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(5);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 更新社保补缴表状态(医疗)
	public int upBjJLDeclare(int id, int ifdeclare, String reason,
			String username) {
		try {
			CallableStatement c = conn
					.getcall("EMSI_BjJLDeclare_p_lsb(?,?,?,?,?)");
			c.setInt(1, id);
			c.setInt(2, ifdeclare);
			c.setString(3, reason);
			c.setString(4, username);
			c.registerOutParameter(5, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(5);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 更新外籍人变更表状态
	public int upForeignerDeclare(int id, int ifdeclare, String reason,
			String username) {
		try {
			CallableStatement c = conn
					.getcall("EMSI_ForeignerDeclare_p_lwj(?,?,?,?,?)");
			c.setInt(1, id);
			c.setInt(2, ifdeclare);
			c.setString(3, reason);
			c.setString(4, username);
			c.registerOutParameter(5, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(5);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

}
