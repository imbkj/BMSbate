package dal.CoFinanceManage;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.EmbaseModel;

/**
 * 
 * @author suhongyuan
 *
 */
public class Cfma_CollectCarryDal {
private  dbconn db= new dbconn();
//根据当前公司的id获取公司的员工信息
public List<EmbaseModel> getEmbaseList(String str){
	List<EmbaseModel> list = new ArrayList<EmbaseModel>();
	String sql= " select * from EmBase where 1=1 "+str;
	ResultSet rs;
	try {
		rs = db.GRS(sql);
	   while(rs.next()){
		EmbaseModel e=new EmbaseModel();
		e.setGid(rs.getInt("gid"));
		e.setEmba_name(rs.getString("emba_name"));
		list.add(e);
	   }
	   } catch (Exception e1) {
			e1.printStackTrace();
		}
	return list;
}
//结转业务操作
public int addCfma_CollectCarry(String company_id ,String cfsa_cpac_name,String cfmb_number,String coba_client,String owmno,
		   String ownmonth,String remak,BigDecimal recexpense,String gid,String caoname){
	Integer a=0;
	CallableStatement c=db.getcall("Cfma_CollectCarry_add_shy(?,?,?,?,?,?,?,?,?,?,?)");
	      try {
		        c.setInt(1, Integer.parseInt(company_id));
	            c.setString(2, cfsa_cpac_name);
	            c.setString(3, cfmb_number);
	            c.setString(4, coba_client);
	            c.setInt(5, Integer.parseInt(owmno));
	            c.setInt(6, Integer.parseInt(ownmonth));
	            c.setString(7, remak);
	            c.setBigDecimal(8, recexpense);
	            c.setInt(9, Integer.parseInt(gid));
				c.setString(10, caoname);
				c.registerOutParameter(11, java.sql.Types.INTEGER);
				c.execute();
				a=c.getInt(11);
				return a;
				} catch (SQLException e) {
					e.printStackTrace();
					return a;
				}
}
}
