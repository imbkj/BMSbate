package dal.EmResidencePermit;

import java.sql.CallableStatement;
import java.sql.SQLException;

import Conn.dbconn;
import Model.EmCAFFeeInfoModel;

public class Emrp_FeeOperateDal {
	// 生成支付明细
	public Integer EmCAFFeeInfoAdd(EmCAFFeeInfoModel m) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
					.getcall("EmCAFFeeInfoAdd_p_cyj(?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getGid());
			c.setInt(2, m.getCid());
			c.setInt(3, m.getOwnmonth());
			c.setInt(4, m.getEcfi_caf_id());
			c.setString(5, m.getEcfi_cl_number());
			c.setString(6, m.getEcfi_payment_kind());
			c.setString(7, m.getEcfi_payment_state());
			c.setInt(8, m.getEcfi_fee());
			c.setString(9, m.getEcfi_addname());
			c.registerOutParameter(10, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(10);
		} catch (SQLException e) {
			System.out.println("数据库操作错误：" + e.getMessage());
			return 0;
		}
	}
}
