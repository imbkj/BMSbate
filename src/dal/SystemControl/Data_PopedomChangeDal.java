package dal.SystemControl;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Conn.dbconn;
import Model.CoAgencyBaseModel;
import Model.CoLatencyClientModel;
import Model.DataPopedomChangeModel;
import Model.DataPopedomModel;

public class Data_PopedomChangeDal {
	private dbconn conn = new dbconn();
	
	public Data_PopedomChangeDal(){
		
	}
	
	//数据权限调整申请数据集
	 public List<DataPopedomChangeModel> getlist(String str) {
		 List<DataPopedomChangeModel> datapopedomcm = new ArrayList<DataPopedomChangeModel>();
			try{
				if (!datapopedomcm.isEmpty())
					datapopedomcm.clear();
						ResultSet rs = null;
						String sqlstr = "SELECT dat_id, log_id, cid, Dat_selected, Dat_edited, Dat_delete, Dat_state, Dat_addname, Dat_addtime, Dat_inuretime, Dat_auditingname, Dat_auditingtime,  Expr1, log_name, Expr2, coba_shortname, coba_client FROM   View_DataPopedomChage where 1=1  "+str+" order by coba_client ";
		 			//System.out.print(sqlstr);
						try {
							dbconn db = new dbconn();
							rs = db.GRS(sqlstr);
							while (rs.next()) {
								datapopedomcm.add(new DataPopedomChangeModel(rs.getInt("dat_id"),rs.getInt("log_id"),
										rs.getInt("cid"),rs.getBoolean("Dat_selected"),rs.getBoolean("Dat_edited"),rs.getBoolean("dat_delete"),
										rs.getString("dat_addname"),rs.getDate("Dat_addtime"),rs.getString("log_name"),	
										rs.getString("coba_shortname"),rs.getString("coba_client"),rs.getString("Dat_auditingname"),rs.getDate("Dat_auditingtime"),rs.getInt("Dat_state"))
										);
							}
						} catch (Exception e) {
							System.out.println(e.toString());
						}
				
				
			} catch (Exception e) {
			System.out.println(e.toString());
			}
			 
			return datapopedomcm;
		 
	 }
	 
	 //数据权限数据集
	 public List<DataPopedomModel> getlist(String str ,String str1) {
			List<DataPopedomModel> DataPopedomChangem = new ArrayList<DataPopedomModel>();
			try{
				if (!DataPopedomChangem.isEmpty())
					DataPopedomChangem.clear();
						ResultSet rs = null;
						String sqlstr = "select b.Dat_id, a.CID,coba_shortname,coba_client,Dat_selected,Dat_edited,Dat_delete,b.log_id,log_name,dat_addname,Dat_addtime" +
								" from CoBase a left join (select Dat_id,Dat_addtime,dat_addname,Dat_selected,Dat_edited,cid,log_id,Dat_delete from  DataPopedom" +
								" where 1=1 "+str1+" ) b  on a.CID=b.cid " +
								"left join (select log_id,log_name from login) c on c.log_id=b.log_id where 1=1 "+str+" order  by b.log_id desc";
		 			System.out.print(sqlstr);
						try {
							dbconn db = new dbconn();
							rs = db.GRS(sqlstr);
				
							while (rs.next()) {							
								DataPopedomChangem.add(new DataPopedomModel(rs.getInt("dat_id"),rs.getInt("log_id"),
										rs.getInt("cid"),rs.getBoolean("Dat_selected"),rs.getBoolean("Dat_edited"),rs.getBoolean("dat_delete"),
										rs.getString("dat_addname"),rs.getDate("Dat_addtime"),rs.getString("log_name"),	
										rs.getString("coba_shortname"),rs.getString("coba_client"))
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
	 
	 
	 //潜在数据权限数据集
	 public List<DataPopedomModel> getczlist(String str ,String str1) {
			List<DataPopedomModel> DataPopedomChangem = new ArrayList<DataPopedomModel>();
			try{
				if (!DataPopedomChangem.isEmpty())
					DataPopedomChangem.clear();
						ResultSet rs = null;
						String sqlstr = "select b.Dat_id, a.cid,a.cola_id,cola_company,cola_follower,Dat_selected,Dat_edited,Dat_delete,b.log_id,log_name,dat_addname,Dat_addtime" +
								" from CoLatencyClient a left join (select cola_id,Dat_id,Dat_addtime,dat_addname,Dat_selected,Dat_edited,cid,log_id,Dat_delete from  DataPopedom" +
								" where 1=1 "+str1+" ) b  on a.cola_id=b.cola_id " +
								"left join (select log_id,log_name from login) c on c.log_id=b.log_id where 1=1 "+str+" order  by b.log_id desc";
		 			System.out.print(sqlstr);
						try {
							dbconn db = new dbconn();
							rs = db.GRS(sqlstr);
				
							while (rs.next()) {							
								DataPopedomChangem.add(new DataPopedomModel(rs.getInt("dat_id"),rs.getInt("log_id"),rs.getInt("cid"),
										rs.getInt("cola_id"),rs.getBoolean("Dat_selected"),rs.getBoolean("Dat_edited"),rs.getBoolean("dat_delete"),
										rs.getString("dat_addname"),rs.getDate("Dat_addtime"),rs.getString("log_name"),	
										rs.getString("cola_company"),rs.getString("cola_follower"))
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
	 
	 
 
	 	//添加一条数据权限申请记录已签
		public int DataPopedomchangeAdd_p_zmj(DataPopedomModel m){
			try {
				CallableStatement c=conn.getcall("DataPopedomchangeAdd_p_zmj(?,?,?,?,?,?,?)");
				c.setInt(1, m.getLog_id());
				c.setInt(2, m.getCid());
				c.setBoolean(3, m.getDat_selected());
				c.setBoolean(4, m.getDat_edited());
				c.setBoolean(5, m.getDat_delete());
				c.setString(6, m.getDat_addname());
				c.registerOutParameter(7, java.sql.Types.INTEGER);
				c.execute();
				return c.getInt(7);
	
			} catch (SQLException e) {
	           return 0;
			}
			
		}
		
		//添加一条数据权限申请记录潜在客户
				public int DataPopedomclchangeAdd_p_zmj(CoLatencyClientModel m){
					try {
						CallableStatement c=conn.getcall("DataPopedomclAuditing_p_zmj(?,?,?,?,?,?,?)");
						c.setInt(1, m.getLog_id());
						c.setInt(2, m.getCola_id());
						c.setBoolean(3, m.getDat_selected());
						c.setBoolean(4, m.getDat_edited());
						c.setBoolean(5, m.getDat_delete());
						c.setString(6, m.getDat_addname());
						c.registerOutParameter(7, java.sql.Types.INTEGER);
						c.execute();
						return c.getInt(7);
			
					} catch (SQLException e) {
			           return 1;
					}
					
				}
				
	//
				
				
	
}
