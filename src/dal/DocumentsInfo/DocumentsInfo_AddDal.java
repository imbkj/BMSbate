package dal.DocumentsInfo;

import java.sql.ResultSet;
import Conn.dbconn;
import Model.DocumentsInfoAddModel;
import Util.UserInfo;

public class DocumentsInfo_AddDal {
	//新增
	public int AddDocuments(DocumentsInfoAddModel em) throws Exception {
		int row = 0;
		String username = UserInfo.getUsername();
		String sqlstr = "insert into DocumentsInfo (doin_type,doin_content,doin_remark,doin_addname) values ('"+ em.getDoin_type()+ "','"+em.getDoin_content()+"','"+em.getDoin_remark()+"','"+username+"')";

		dbconn oper = new dbconn();
		try {

			System.out.print(sqlstr);
			row = oper.execQuery(sqlstr);

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			oper.Close();
		}
		return row;
	}
	
	//检查重复材料
	public int AddDocumentsCF(DocumentsInfoAddModel em) throws Exception {
		int a = 0;
		ResultSet rs = null;
		String sqlstr = "select count(*) as copcount from DocumentsInfo where doin_type='"+em.getDoin_type()+"' and doin_content='"+em.getDoin_content()+"'";

		dbconn oper = new dbconn();
		rs = oper.GRS(sqlstr);
		while (rs.next()) {
			a = rs.getInt("copcount");
		}
		return a;
		
	}
}
