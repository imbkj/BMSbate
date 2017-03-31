package dal.EmBenefit;

import java.sql.CallableStatement;
import java.sql.SQLException;

import Conn.dbconn;
import Model.EmActySupProductInfoModel;

public class EmBenefitAndproductOperateDal {
	//项目关联产品
	public Integer EmBenefitAndproductAdd(EmActySupProductInfoModel m) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
			.getcall("[EmBenefitAndproduct_Add_p_cyj](?,?,?,?)");
			c.setInt(1, m.getEmbf_id());
			c.setInt(2, m.getProd_id());
			c.setString(3, m.getProd_addname());
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			return 0;
		}
	}
	
	//项目关联产品修改
	public Integer EmBenefitAndproductUpdate(EmActySupProductInfoModel m) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
			.getcall("[EmBenefitAndproduct_UPDATE_p_cyj](?,?,?,?)");
			c.setInt(1, m.getEmbf_id());
			c.setInt(2, m.getProd_id());
			c.setString(3, m.getProd_addname());
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			return 0;
		}
	}
}
