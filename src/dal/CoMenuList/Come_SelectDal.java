package dal.CoMenuList;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.CobaseMenulistModel;
import Model.MenuListModel;

public class Come_SelectDal {
	// 获取公司菜单名称
	public List<CobaseMenulistModel> getEmbaseMenuListInfo(String str) {
		List<CobaseMenulistModel> list = new ArrayList<CobaseMenulistModel>();
		dbconn db = new dbconn();
		String sql = "select * from CobaseMenuList where 1=1 and come_state=1 "
				+ str+" order by come_order";
		try {
			list = db.find(sql, CobaseMenulistModel.class,
					dbconn.parseSmap(CobaseMenulistModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询菜单与角色关系表
	public List<CobaseMenulistModel> getCobaseMenuListRel(String str) {
		ResultSet rs = null;
		List<CobaseMenulistModel> list = new ArrayList<CobaseMenulistModel>();
		if (!list.isEmpty())
			list.clear();
		try {
			dbconn db = new dbconn();
			String sqlstr = "select * from CobaseMenuListRel where 1=1 " + str;
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CobaseMenulistModel menumodel = new CobaseMenulistModel();
				menumodel.setCome_id(rs.getInt("reml_come_id"));
				list.add(menumodel);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
}
