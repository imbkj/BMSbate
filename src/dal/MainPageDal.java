package dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.PubCallModel;

public class MainPageDal {

	public List<PubCallModel> getpubcallList(String username) throws SQLException {
		ResultSet rs = null;
		dbconn db = new dbconn();
		List<PubCallModel> list = new ArrayList<PubCallModel>();
		String sqlstr = "select top 20 * from pubcall where client<>'林少斌' order by addtime desc";
		/*String sqlstr = "select * from pubcall where client='" + username + "' and state=0 " +
				"order by addtime desc";*/
		//System.out.println(sqlstr);
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				PubCallModel model = new PubCallModel();
				model.setId(rs.getInt("id"));
				model.setGid(rs.getInt("gid"));
				model.setContent(rs.getString("content"));
				model.setClient(rs.getString("client"));
				model.setAddtime(rs.getDate("addtime"));
				model.setState(rs.getInt("state"));
				model.setPc_class(rs.getInt("class"));
				model.setRemindtime(rs.getDate("remindtime"));
				model.setReadname(rs.getString("readname"));
				model.setReadtime(rs.getDate("readtime"));
				list.add(model);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			db.Close();
		}
		return list;
	}
}
