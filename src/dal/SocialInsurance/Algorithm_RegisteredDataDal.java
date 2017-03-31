package dal.SocialInsurance;

import java.sql.CallableStatement;
import java.sql.SQLException;

import Conn.dbconn;
import Model.EmCommissionOutFeeDetailChangeModel;
import Model.EmShebaoUpdateModel;

public class Algorithm_RegisteredDataDal {
	private static dbconn conn = new dbconn();

	// 更新在册明细数据
	public void upRegData(EmCommissionOutFeeDetailChangeModel m) {
		try {
			CallableStatement c = conn
					.getcall("upRegDataFeeDetail_p_lwj(?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getEofc_id());
			c.setBigDecimal(2, m.getEofc_em_base());
			c.setBigDecimal(3, m.getEofc_co_base());
			c.setString(4, m.getEofc_cp());
			c.setString(5, m.getEofc_op());
			c.setBigDecimal(6, m.getEofc_em_sum());
			c.setBigDecimal(7, m.getEofc_co_sum());
			c.setBigDecimal(8, m.getEofc_month_sum());
			c.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	// 插入变更表
		public void insertchangeDate(int ecou_id) {
			try {
				CallableStatement c = conn
						.getcall("EmCommissionOutchangeAddbzgx_P_zmj(?)");
				c.setInt(1,ecou_id);
			
				c.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}


	// 更新变更表明细数据
	public void upRegDataChange(EmCommissionOutFeeDetailChangeModel m) {
		try {
			CallableStatement c = conn
					.getcall("upRegDataFeeDetailChange_p_lwj(?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getEofc_id());
			c.setBigDecimal(2, m.getEofc_em_base());
			c.setBigDecimal(3, m.getEofc_co_base());
			c.setString(4, m.getEofc_cp());
			c.setString(5, m.getEofc_op());
			c.setBigDecimal(6, m.getEofc_em_sum());
			c.setBigDecimal(7, m.getEofc_co_sum());
			c.setBigDecimal(8, m.getEofc_month_sum());
			c.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 更新本地社保在册数据
	public void upLocalRegData(EmShebaoUpdateModel m) {
		try {
			CallableStatement c = conn
					.getcall("upLocalRegData_p_lwj(?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getId());
			c.setBigDecimal(2, m.getEsiu_ylcp());
			c.setBigDecimal(3, m.getEsiu_ylop());
			c.setBigDecimal(4, m.getEsiu_jlcp());
			c.setBigDecimal(5, m.getEsiu_jlop());
			c.setBigDecimal(6, m.getEsiu_syucp());
			c.setBigDecimal(7, m.getEsiu_syuop());
			c.setBigDecimal(8, m.getEsiu_syecp());
			c.setBigDecimal(9, m.getEsiu_syeop());
			c.setBigDecimal(10, m.getEsiu_gscp());
			c.setBigDecimal(11, m.getEsiu_gsop());
			c.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
