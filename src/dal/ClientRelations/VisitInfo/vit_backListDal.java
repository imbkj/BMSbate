package dal.ClientRelations.VisitInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.VisitFollowModel;
import Model.VisitInfoModel;

public class vit_backListDal {

	public static List<VisitInfoModel> getVisitList(String str) throws SQLException{
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<VisitInfoModel> list = new ArrayList<VisitInfoModel>();
		String sqlstr = "select * from View_Visitinfo where viin_state in(1,2,3,4,5)"
				+ str;

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				VisitInfoModel model = new VisitInfoModel();
				model.setCola_company(rs.getString("cola_company"));
				model.setViin_id(rs.getInt("viin_id"));
				model.setViin_month(rs.getInt("viin_month"));
				model.setViin_type(rs.getString("viin_type"));
				model.setViin_class(rs.getString("viin_class"));
				model.setViin_person(rs.getString("viin_person"));
				model.setViin_subordinate(rs.getString("viin_subordinate"));
				model.setViin_addname(rs.getString("viin_addname"));
				model.setViin_addtime(rs.getDate("viin_addtime"));
				model.setState(rs.getString("viin_stateStr"));
				model.setViin_iffolow(rs.getBoolean("viin_iffollow"));
				model.setViin_state(rs.getInt("viin_state"));
				//任务单ID获取
				model.setViin_tapr_id(rs.getInt("viin_tapr_id"));
				
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}
	
	public static List<String> getPersonList() throws SQLException{
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<String> list = new ArrayList<String>();
		String sqlstr = "select '' as viin_person union "
				+ "select distinct viin_person from visitinfo where viin_state in(1,2,3,4,5)";

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				list.add(rs.getString("viin_person"));
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}
	
	public static List<String> getSubordinateList() throws SQLException{
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<String> list = new ArrayList<String>();
		String sqlstr = "select '' as viin_subordinate union "
				+ "select distinct viin_subordinate from visitinfo where viin_state in(1,2,3,4,5)";

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				list.add(rs.getString("viin_subordinate"));
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}
	
	public static List<String> getAddnameList() throws SQLException{
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<String> list = new ArrayList<String>();
		String sqlstr = "select '' as viin_addname union "
				+ "select distinct viin_addname from visitinfo where viin_state in(1,2,3,4,5)";

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				list.add(rs.getString("viin_addname"));
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}
}
