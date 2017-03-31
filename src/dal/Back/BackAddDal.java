package dal.Back;

import java.sql.CallableStatement;
import java.sql.SQLException;

import Conn.dbconn;
import Model.BackCauseInfoModel;
import Util.UserInfo;

public class BackAddDal {
	//新增退回信息
	public Integer back(BackCauseInfoModel m){
		dbconn conn = new dbconn();
		try {
			CallableStatement c = conn
					.getcall("BackCauseInfo_Add_cyj(?,?,?,?,?)");
			c.setInt(1, m.getBack_prof_id());
			c.setString(2, m.getBack_cause());
			c.setString(3, m.getBack_type());
			c.setString(4, UserInfo.getUsername());

			c.registerOutParameter(5, java.sql.Types.INTEGER);
			c.execute();

			return c.getInt(5);
		} catch (SQLException e) {
			return 1;
		}
	}

}
