package dal.ClientRelations.VisitInfo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.VisitFollowModel;
import Model.VisitInfoModel;

public class vit_backmodDal {

	public static VisitInfoModel getvisitbackDetail(int viin_id)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		VisitInfoModel model = new VisitInfoModel();
		String sqlstr = "select * from visitinfo where viin_id=" + viin_id;

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				model.setViin_cost(rs.getString("viin_cost"));
				model.setViin_costremark(rs.getString("viin_costremark"));
				model.setViin_personed(rs.getString("viin_personed"));
				model.setViin_job(rs.getString("viin_job"));
				model.setViin_aim(rs.getString("viin_aim"));
				model.setViin_truetime(rs.getDate("viin_truetime"));
				model.setViin_summary(rs.getString("viin_summary"));
				model.setViin_feedback(rs.getString("viin_feedback"));
				model.setViin_iffolow(rs.getBoolean("viin_iffollow"));
				model.setViin_state(rs.getInt("viin_state"));
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return model;
	}

	public static List<VisitFollowModel> getvisitfollows(int viin_id)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<VisitFollowModel> list = new ArrayList<VisitFollowModel>();
		String sqlstr = "select vifo_id,vifo_content,vifo_dep_id,dep_name,vifo_name,"
				+ "vifo_disposetime,vifo_disposecontent,vifo_addtime,vifo_order,"
				+ "vifo_addname from visitinfo a,VisitFollow b left outer join department d "
				+ "on b.vifo_dep_id=d.dep_id,ViviRelation c where a.viin_id=c.vire_viin_id "
				+ "and b.vifo_id=c.vire_vifo_id and vifo_state=1 and viin_id="
				+ viin_id + "order by vifo_order";

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				VisitFollowModel model = new VisitFollowModel();
				model.setVifo_id(rs.getInt("vifo_id"));
				model.setVifo_content(rs.getString("vifo_content"));
				model.setVifo_dept_id(rs.getInt("vifo_dep_id"));
				model.setDeptname(rs.getString("dep_name"));
				model.setVifo_name(rs.getString("vifo_name"));
				model.setVifo_disposetime(rs.getDate("vifo_disposetime"));
				model.setVifo_disposecontent(rs.getString("vifo_disposecontent"));
				model.setVifo_addtime(rs.getDate("vifo_addtime"));
				model.setVifo_addname(rs.getString("vifo_addname"));
				model.setVifo_order(rs.getInt("vifo_order"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}
	
	public static int followsDel(int viin_id) throws SQLException{
		int row = 0;
		dbconn db = new dbconn();
		String sqlstr = "update VisitFollow set vifo_state=0 where vifo_id in(" +
				"select vifo_id from VisitFollow a,VisitInfo b,ViviRelation c " +
				"where a.vifo_id=c.vire_vifo_id and b.viin_id=c.vire_viin_id and viin_id=?)";
		PreparedStatement psmt = db.getpre(sqlstr);

		try {
			psmt.setInt(1, viin_id);

			row = psmt.executeUpdate();
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return row;
	}
}
