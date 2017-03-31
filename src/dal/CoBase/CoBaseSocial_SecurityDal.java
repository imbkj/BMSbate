package dal.CoBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.CoBaseSocialSecurityModel;
import Model.MaintenanceRecordModel;

/**
 * 
 * @author suhongyuan 
 * @create 2017-01-04
 * 商业保险-体记录查询操作过程
 */
public class CoBaseSocial_SecurityDal {
	//当前用户多条件查询商业保险-体记录
	public List<CoBaseSocialSecurityModel> getCoBaseSocial_SecurityList(String strsql) throws SQLException{
			List<CoBaseSocialSecurityModel> list = new ListModelList<CoBaseSocialSecurityModel>();
			dbconn con = new  dbconn();
			StringBuilder sql = new StringBuilder();
			sql.append(" select  DISTINCT co.CID,co.coba_company,co.coba_client,co.coba_assistant,co.coba_id from ( "
                 +" select  * from CoBase   where coba_client in (SELECT log_name FROM Login where dep_id IN (2,6)) "
                 +" ) co LEFT JOIN ( "
                 +" select * from CoCompact where coco_state in(4,5) "
                 +" ) coc ON co.CID=coc.cid  "
                
                 +" where  co.coba_servicestate=1 ");
			sql.append(strsql);
			try {
				System.out.println(sql.toString());
				ResultSet rs= con.GRS(sql.toString());
				while(rs.next()){
					CoBaseSocialSecurityModel mm =new CoBaseSocialSecurityModel();
					mm.setCid(String.valueOf(rs.getInt("CID")));
					mm.setCoba_id(String.valueOf(rs.getInt("coba_id")));
					mm.setCoba_company(rs.getString("coba_company"));
					mm.setCoba_client(rs.getString("coba_client"));
					mm.setCoba_assistant(rs.getString("coba_assistant"));
					list.add(mm);
				  }
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
					con.Close();
				}
				return list;
		}
	//获取kefu下拉列表数据
	public List<String> getCoba_assistantList() throws SQLException{
		List<String> list = new ArrayList<String>();
		list.add("");
		dbconn con = new  dbconn();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT  log_name FROM Login where dep_id=2 AND log_inure=1 ");
		try {
			System.out.println(sql.toString());
			ResultSet rs= con.GRS(sql.toString());
			while(rs.next()){
				list.add(rs.getString("log_name"));
			  }
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				con.Close();
			}
			return list;
	}
	
	//根据coba_id 修改coba_assistant字段员工米名
	public int updateCoBase(CoBaseSocialSecurityModel mr){
		int i= 0;
		dbconn con = new  dbconn();
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE CoBase   SET ");
		sql.append(" coba_assistant =? ");
		sql.append(" where CID=? ");
		try {
				 i= con.updatePrepareSQL(sql.toString(),mr.getCoba_assistant(),mr.getCid());
			} catch (SQLException e) {
				e.printStackTrace();
		}
		return i;
			
	}
	
	//获取员服下拉列表数据
	public List<String> getCobaList() throws SQLException{
		List<String> list = new ArrayList<String>();
		list.add("");
		dbconn con = new  dbconn();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT  log_name from Login where dep_id=16  AND log_inure=1 ");
		try {
			System.out.println(sql.toString());
			ResultSet rs= con.GRS(sql.toString());
			while(rs.next()){
				list.add(rs.getString("log_name"));
			  }
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				con.Close();
			}
			return list;
	}
}
