package dal.SysMessage;

import Conn.dbconn;
import Model.SysMessageModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

public class SysMessageTem_ManageDal {

	public ListModelList<SysMessageModel> gettemList(String str) {
		ResultSet rs = null;
		dbconn db = new dbconn();
		ListModelList<SysMessageModel> list = new ListModelList<SysMessageModel>();
		String sql = "select *,CONVERT(nvarchar(16),pmte_addtime,120) addtime "
				+ "from PubMessageTemplet where pmte_state=1"+str;

		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				SysMessageModel model = new SysMessageModel();
				model.setPmte_id(rs.getInt("pmte_id"));
				model.setPmte_model(rs.getString("pmte_model"));
				model.setPmte_class(rs.getString("pmte_class"));
				model.setPmte_content(rs.getString("pmte_content"));
				model.setPmte_addname(rs.getString("pmte_addname"));
				model.setPmte_addtime(rs.getString("addtime"));
				model.setPmte_state(rs.getInt("pmte_state"));
				model.setPmte_type(rs.getString("pmte_type"));
				list.add(model);
			}
			db.Close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}

		return list;
	}
	
	public ListModelList<SysMessageModel> gettemListByClass(String name) {
		ResultSet rs = null;
		dbconn db = new dbconn();
		ListModelList<SysMessageModel> list = new ListModelList<SysMessageModel>();
		String sql = "select *,CONVERT(nvarchar(16),pmte_addtime,120) addtime "
				+ "from PubMessageTemplet where pmte_state=1 and pmte_class='"+name+"'";

		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				SysMessageModel model = new SysMessageModel();
				model.setPmte_id(rs.getInt("pmte_id"));
				model.setPmte_model(rs.getString("pmte_model"));
				model.setPmte_class(rs.getString("pmte_class"));
				model.setPmte_content(rs.getString("pmte_content"));
				model.setPmte_addname(rs.getString("pmte_addname"));
				model.setPmte_addtime(rs.getString("addtime"));
				model.setPmte_state(rs.getInt("pmte_state"));
				model.setPmte_type(rs.getString("pmte_type"));
				list.add(model);
			}
			db.Close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}

		return list;
	}

	public int updateTem(SysMessageModel model) {
		int row = 0;
		PreparedStatement psmt = null;
		dbconn db = new dbconn();
		String sql = "update PubMessageTemplet set pmte_model=?,pmte_class=?,pmte_content=?,pmte_type=? "
				+ "where pmte_id=?";
		try {
			psmt = db.getpre(sql);
			psmt.setString(1, model.getPmte_model());
			psmt.setString(2, model.getPmte_class());
			psmt.setString(3, model.getPmte_content());
			psmt.setString(4, model.getPmte_type());
			psmt.setInt(5, model.getPmte_id());

			row = psmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return row;
	}
	
	public int deleteTem(int pmte_id) {
		int row = 0;
		PreparedStatement psmt = null;
		dbconn db = new dbconn();
		String sql = "update PubMessageTemplet set pmte_state=0 where pmte_id=?";
		try {
			psmt = db.getpre(sql);
			psmt.setInt(1, pmte_id);

			row = psmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return row;
	}
}
