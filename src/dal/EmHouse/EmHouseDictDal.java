package dal.EmHouse;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.EmHouseDictModel;

public class EmHouseDictDal {
	public List<EmHouseDictModel> listInfo(EmHouseDictModel em,String order){
		List<EmHouseDictModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select id,ownmonth,sp,spup,spdown from EmHouseDict ";
		if (order!=null) {
			if (!order.equals("")) {
				sql=sql+order;
			}
		}
		try {
			list = db.find(sql, EmHouseDictModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
