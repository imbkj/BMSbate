package dal.ClientRelations.CreditRating;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import Conn.dbconn;
import Model.CreditContentInfoModel;
import Model.CreditCriterionModel;

public class CreditContent_OpraDal {
	//对信誉评定内容表CreditContentInfo插入一条数据，并返回一个Int类型的数
	public int addCreditContentInfo(CreditContentInfoModel model,int id){
		int k=0;
		try {
			dbconn db = new dbconn();
			Timestamp date= new Timestamp(System.currentTimeMillis());
			String sql = "insert into CreditContentInfo(crst_crcr_id,crst_name,crst_addname,crst_addtime)";
				sql=sql+" values("+id+",'"+model.getCrst_name()+"','"+model.getCrst_addname()+"','"+date+"')";
				k=db.execQuery(sql);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return k;
	}
	
	//对信誉评定标准信息表CreditCriterion插入一条数据，并返回一个Int类型的数
	public int addCreditCriterion(CreditCriterionModel model){
		dbconn conn = new dbconn();
		try {
			Timestamp date= new Timestamp(System.currentTimeMillis());
			CallableStatement c=conn.getcall("[CreditCriterion_Add_P_cyj](?,?,?,?,?)");
			c.setString(1, model.getCrcr_type());
			c.setString(2, model.getCrcr_content());
			c.setString(3, model.getCrcr_addname());
			c.setTimestamp(4, date);
			c.registerOutParameter(5, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(5);
		} catch (SQLException e) {
			return -1;
	  }
	}
}
