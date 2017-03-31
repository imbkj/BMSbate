package dal.EmTax;

import java.sql.CallableStatement;
import java.sql.SQLException;

import Conn.dbconn;
import Model.EmSalaryInfoModel;
import Util.UserInfo;

public class EmTax_OperateDal {
	private dbconn conn = new dbconn();

	// 分配个税申报地
	public int assginTaxPlace(EmSalaryInfoModel m) {
		try {
			CallableStatement c = conn
					.getcall("EmTaxPlaceAssgin_P_lsb(?,?,?,?,?)");
			c.setInt(1, m.getGid());
			c.setInt(2, m.getCid());
			c.setString(3, m.getEsin_taxplace());
			c.setInt(4, m.getEsin_nexttaxplace_smonth());
			c.registerOutParameter(5, java.sql.Types.INTEGER);
			c.execute();

			return c.getInt(5);
		} catch (SQLException e) {
			return 1;
		}
	}
	
	//生成支付模块个税数据
	public int createEmPayTax(Integer id) {
		try {
			CallableStatement c = conn
					.getcall("EmTaxInfo_EmPay_P_lsb(?,?,?)");
			c.setInt(1, id);
			c.setString(2, UserInfo.getUsername());
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();

			return c.getInt(3);
		} catch (SQLException e) {
			return 1;
		}
	}
	
}
