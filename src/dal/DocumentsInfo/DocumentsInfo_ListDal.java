package dal.DocumentsInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.DocumentsListModel;

public class DocumentsInfo_ListDal {
	//获取员工列表
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
					model.setDoin_state(rs.getString("doin_state"));
					list.add(model);
				}
			} catch (Exception e) {
				System.out.print(e.toString());
			} finally {
				db.Close();
			}
			return list;
		}
		
		//查找
				public List<DocumentsListModel> getdocumentslist(String role_name) throws SQLException {
					dbconn db = new dbconn();
					ResultSet rs = null;
					List<DocumentsListModel> list = new ArrayList<DocumentsListModel>();
					String sqlstr = "select doin_content,case when doin_type=1 then '员工材料' else '公司材料' end doin_type,doin_id,case when doin_state=1 then '生效' else '禁用' end doin_state from DocumentsInfo where doin_content='"+role_name+"' order by doin_content";

					try {
						rs = db.GRS(sqlstr);
						while (rs.next()) {
							DocumentsListModel model = new DocumentsListModel();
							model.setDoin_content(rs.getString("doin_content"));
							model.setDoin_type(rs.getString("doin_type"));
							model.setDoin_id(rs.getInt("doin_id"));
							model.setDoin_state(rs.getString("doin_state"));
							list.add(model);
						}
					} catch (Exception e) {
						System.out.print(e.toString());
					} finally {
						db.Close();
					}
					return list;
				}
}
