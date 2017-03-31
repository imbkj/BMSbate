package dal.DocumentsInfo;

import Conn.dbconn;
import Model.DocumentsInfoAddModel;

public class DocumentsInfo_EditDal {
	public int editrol(DocumentsInfoAddModel em) throws Exception {
		int row = 0;

		String sqlstr = "update DocumentsInfo set doin_content='"+ em.getDoin_content()+ "',doin_remark='"+em.getDoin_remark()+"',doin_type='"+em.getDoin_type()+"',doin_state="+em.getDoin_state()+" where doin_id="+em.getDoin_id();

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
}
