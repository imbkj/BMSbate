/**
 * @Classname DocumentsInfo_ListBll
 * @ClassInfo 材料模块列表分配页面
 * @author 张志强
 * @Date 2013-10-31
 */
package bll.DocumentsInfo;

import java.sql.SQLException;
import java.util.List;

import Model.DocumentsListModel;
import Model.PubZulListModel;
import dal.DocumentsInfo.DocumentsInfo_ManangerDal;

public class DocumentsInfo_ManangerBll {
	// 获取材料
	public List<DocumentsListModel> getdocumentslist() throws SQLException {
		DocumentsInfo_ManangerDal dal = new DocumentsInfo_ManangerDal();
		List<DocumentsListModel> list = dal.getdocumentslist();
		return list;
	}

	// 获取类别
	public List<PubZulListModel> getzullist() throws SQLException {
		DocumentsInfo_ManangerDal dal = new DocumentsInfo_ManangerDal();
		List<PubZulListModel> list2 = dal.getzullist();
		return list2;
	}

	// 获取已选中的材料
	public List<DocumentsListModel> getdoclist(int doin_id) throws SQLException {
		DocumentsInfo_ManangerDal dal = new DocumentsInfo_ManangerDal();
		List<DocumentsListModel> list3 = dal.getdoclist(doin_id);
		return list3;
	}

	// 材料取消分配
	public String[] docDelete(Integer dire_id) {
		DocumentsInfo_ManangerDal dal = new DocumentsInfo_ManangerDal();
		String[] message = new String[5];
		try {
			
			int result=dal.docDelete(dire_id);
			
			if (result==1) {
				message[0] = "1";
				message[1] = "材料取消分配成功。";
			} else {
				message[0] = "0";
				message[1] = "材料取消分配失败。";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "材料取消分配出错。";
		}
		return message;
	}
}
