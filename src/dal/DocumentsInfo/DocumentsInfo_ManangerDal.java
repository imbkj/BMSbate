package dal.DocumentsInfo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.DocumentsListModel;
import Model.EmShebaoBJModel;
import Model.PubZulListModel;

public class DocumentsInfo_ManangerDal {
	// 获取材料
	public List<DocumentsListModel> getdocumentslist() throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<DocumentsListModel> list = new ArrayList<DocumentsListModel>();
		String sqlstr = "select doin_content,case when doin_type=1 then '员工材料' else '公司材料' end doin_type,doin_id,case when doin_state=1 then '生效' else '禁用' end doin_state from DocumentsInfo order by doin_content";

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				DocumentsListModel model = new DocumentsListModel();
				model.setDoin_content(rs.getString("doin_content"));
				model.setDoin_type(rs.getString("doin_type"));
				model.setDoin_id(rs.getInt("doin_id"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取类别
	public List<PubZulListModel> getzullist() throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs2 = null;
		List<PubZulListModel> list2 = new ArrayList<PubZulListModel>();
		String sqlstr2 = "SELECT puzu_id, puzu_page FROM PubZul";

		try {
			rs2 = db.GRS(sqlstr2);
			while (rs2.next()) {
				PubZulListModel model2 = new PubZulListModel();
				model2.setPuzu_pclass(rs2.getString("puzu_page"));
				model2.setPuzu_id(rs2.getInt("puzu_id"));
				list2.add(model2);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list2;
	}

	// 获取已选中的材料
	public List<DocumentsListModel> getdoclist(int doin_id) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs3 = null;
		List<DocumentsListModel> list3 = new ArrayList<DocumentsListModel>();
		String sqlstr3 = "select a.dire_id,doin_content,case when doin_type=1 then '员工材料' else '公司材料' end doin_type,doin_id,case when doin_state=1 then '生效' else '禁用' end doin_state,dire_ifhaveto from DipzRelation a left join DocumentsInfo b on b.doin_id=a.dire_doin_id where dire_state=1 and dire_puzu_id="
				+ doin_id + " order by doin_content";
		System.out.print(sqlstr3);
		try {
			rs3 = db.GRS(sqlstr3);
			while (rs3.next()) {
				DocumentsListModel model3 = new DocumentsListModel();
				model3.setDoin_content(rs3.getString("doin_content"));
				model3.setDoin_type(rs3.getString("doin_type"));
				model3.setDoin_id(rs3.getInt("doin_id"));
				model3.setDoin_state(rs3.getString("dire_ifhaveto"));
				model3.setDire_id(rs3.getInt("dire_id"));
				list3.add(model3);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list3;
	}

	// 材料取消分配
	public int docDelete(Integer dire_id) {
		String sql = "UPDATE DipzRelation SET dire_state=0 where dire_id="
				+ dire_id;
		System.out.println(sql);
		try {
			dbconn db = new dbconn();
			PreparedStatement psmt = db.getpre(sql);
			psmt.execute();
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
