package dal.Taskflow;

import java.sql.SQLException;

import Conn.dbconn;

public class TaskBatchRelBusinessDal {

	public Integer add(Integer tabaId, Integer dataId) {
		Integer i=0;
		dbconn db = new dbconn();
		String sql = "insert into TaskBatchRelBusiness(tbrb_taba_id,tbrb_data_id,tbrb_state)" +
				"select ?,?,1";
		try {
			i = db.insertAndReturnKey(sql, tabaId,dataId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
	
	
}
