/**
 * @Classname DocumentsInfo_ListBll
 * @ClassInfo 材料模块列表显示页面
 * @author 张志强
 * @Date 2013-10-31
 */
package bll.DocumentsInfo;

import java.sql.SQLException;
import java.util.List;

import Model.DocumentsListModel;
import dal.DocumentsInfo.DocumentsInfo_ListDal;

public class DocumentsInfo_ListBll {
	//获取材料
	public List<DocumentsListModel> getdocumentslist() throws SQLException {
		DocumentsInfo_ListDal dal = new DocumentsInfo_ListDal();
		List<DocumentsListModel> list3 = dal.getdocumentslist();
		return list3;
	}
	
	//查询材料
		public List<DocumentsListModel> getdocumentslist(String role_name) throws SQLException {
			DocumentsInfo_ListDal dal = new DocumentsInfo_ListDal();
			List<DocumentsListModel> list3 = dal.getdocumentslist(role_name);
			return list3;
		}
}
