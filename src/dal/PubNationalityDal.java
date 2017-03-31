package dal;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.PubNationalityModel;

public class PubNationalityDal {
	private static dbconn conn = new dbconn();

	// 查询工资项目信息
	public List<PubNationalityModel> getPubNationalityList(String str) {
		ResultSet rs = null;
		List<PubNationalityModel> list = new ArrayList<PubNationalityModel>();
		if (!list.isEmpty())
			list.clear();

		try {
			String sql = "select * from PubNationality WHERE 1=1 " + str
					+ " order by hid";

			rs = conn.GRS(sql);
			while (rs.next()) {
				PubNationalityModel model = new PubNationalityModel();
				model.setId(rs.getInt("id"));
				model.setPuna_name(rs.getString("puna_name"));
				model.setHid(rs.getInt("hid"));
				list.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
