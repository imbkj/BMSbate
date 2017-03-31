package dal.EmFinanceManage;

import java.sql.CallableStatement;
import java.sql.SQLException;

import Conn.dbconn;
import Model.EmGatheringModel;

public class emgt_OperateDal {
	//个人收款新增
	public Integer EmGatheringAdd(EmGatheringModel m) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
				.getcall("EmGathering_Add_cyj(?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getGid());
			c.setInt(2, m.getCid());
			c.setInt(3, m.getOwnmonth());
			c.setString(4, m.getEmgt_type());
			c.setString(5, m.getEmgt_paytype());
			c.setBigDecimal(6, m.getEmgt_fee());
			c.setString(7, m.getEmgt_remark());
			c.setString(8, m.getEmgt_addname());
			c.registerOutParameter(9, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(9);
		} catch (SQLException e) {
			System.out.println("数据库操作错误："+e.getMessage());
			return 0;
		}
	}
	
	//个人收款修改
	public Integer EmGatheringUpdate(EmGatheringModel m) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
				.getcall("EmGathering_update_cyj(?,?,?,?,?,?,?)");
			c.setInt(1, m.getEmgt_id());
			c.setInt(2, m.getOwnmonth());
			c.setString(3, m.getEmgt_type());
			c.setString(4, m.getEmgt_paytype());
			c.setBigDecimal(5, m.getEmgt_fee());
			c.setString(6, m.getEmgt_remark());
			c.registerOutParameter(7, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(7);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
