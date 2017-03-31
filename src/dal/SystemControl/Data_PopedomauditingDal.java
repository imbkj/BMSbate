package dal.SystemControl;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Conn.dbconn;
import Model.DataPopedomChangeModel;
public class Data_PopedomauditingDal {
	private dbconn conn = new dbconn();
	
	//数据权限申请数据集
	 public List<DataPopedomChangeModel> getlist(String str ,String str1) {
			List<DataPopedomChangeModel> DataPopedomChangem = new ArrayList<DataPopedomChangeModel>();
			try{
				if (!DataPopedomChangem.isEmpty())
					DataPopedomChangem.clear();
						ResultSet rs = null;
						String sqlstr = "select b.Dat_id, a.CID,coba_shortname,coba_client,Dat_selected,Dat_edited,Dat_delete,b.log_id,log_name,dat_addname,Dat_addtime,dat_auditingname,dat_auditingtime,dat_state" +
								" from CoBase a left join (select Dat_id,Dat_addtime,dat_addname,Dat_selected,Dat_edited,cid,log_id,Dat_delete,dat_auditingname,dat_auditingtime,dat_state from  DataPopedomChange" +
								" where dat_state=0  "+str1+" ) b  on a.CID=b.cid " +
								"left join (select log_id,log_name from login) c on c.log_id=b.log_id where 1=1 "+str+" order  by b.log_id desc";
		 			System.out.print(sqlstr);
						try {
							dbconn db = new dbconn();
							rs = db.GRS(sqlstr);
				
							while (rs.next()) {							
								DataPopedomChangem.add(new DataPopedomChangeModel(rs.getInt("dat_id"),rs.getInt("log_id"),
										rs.getInt("cid"),rs.getBoolean("Dat_selected"),rs.getBoolean("Dat_edited"),rs.getBoolean("dat_delete"),
										rs.getString("dat_addname"),rs.getDate("Dat_addtime"),rs.getString("log_name"),	
										rs.getString("coba_shortname"),rs.getString("coba_client"),rs.getString("dat_auditingname"),
										rs.getDate("dat_auditingtime"),rs.getInt("dat_state"))
										);
							}
						} catch (Exception e) {
							System.out.println(e.toString());
						}
				
				
			} catch (Exception e) {
			System.out.println(e.toString());
			}
			 
			return DataPopedomChangem;
		}
	 
	 	//数据权限审核
		public int DataPopedomchangeauditing(DataPopedomChangeModel m){
			try {
				CallableStatement c=conn.getcall("DataPopedomchangeAuditing_p_zmj(?,?,?,?,?,?,?)");
				c.setInt(1, m.getLog_id());
				c.setInt(2, m.getCid());
				c.setBoolean(3, m.isDat_selected());
				c.setBoolean(4, m.isDat_edited());
				c.setBoolean(5, m.isDat_delete());
				c.setString(6, m.getDat_addname());
				c.registerOutParameter(7, java.sql.Types.INTEGER);
				c.execute();
				return c.getInt(7);
	
			} catch (SQLException e) {
	           return 1;
			}
			
		}
}
