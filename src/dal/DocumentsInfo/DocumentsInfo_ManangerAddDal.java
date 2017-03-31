package dal.DocumentsInfo;

import Conn.dbconn;
import Model.DocumentsInfoAddModel;
import Util.UserInfo;

public class DocumentsInfo_ManangerAddDal {
	public int Addmang(DocumentsInfoAddModel em) throws Exception {
		int row = 0;
		int row1 = 0;
		String username = UserInfo.getUsername();
		String sqlstr1="update DipzRelation set dire_state=0 where dire_doin_id="+em.getDoin_id()+" and dire_puzu_id="+em.getDoin_remark()+"";
		String sqlstr = "insert into DipzRelation (dire_doin_id,dire_puzu_id,dire_ifhaveto,dire_addname,dire_state) values ('"+ em.getDoin_id()+ "','"+em.getDoin_remark()+"','"+em.getDoin_state()+"','"+username+"',1)";
		dbconn oper = new dbconn();
		System.out.print(sqlstr1);
		try {

			System.out.print(sqlstr);
			row1 = oper.execQuery(sqlstr1);
			row = oper.execQuery(sqlstr);

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			oper.Close();
		}
		return row;
	}
}
