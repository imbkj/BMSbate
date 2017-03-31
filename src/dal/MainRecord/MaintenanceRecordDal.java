package dal.MainRecord;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.MaintenanceRecordModel;
import Util.UserInfo;

/**
 * 
 * @author suhongyuan
 * @create 2016-11-10
 * 维护记录表数据库操作过程
 */
public class MaintenanceRecordDal {
   //添加维护记录
	public int addMainRecord(MaintenanceRecordModel em) throws SQLException{
		int row = 0;
		String username = UserInfo.getUsername();
		String strsql="insert into maintenancerecord(proposer,proposereson,proposerdate,sql,status) "
           +"values ('"+ username+ "','"+ em.getProposereson()+ "',GETDATE(),'"+ em.getSql()+ "',0) ";
		dbconn oper = new dbconn();
		try {

			System.out.print(strsql);
			row = oper.execQuery(strsql);
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			oper.Close();
		}
		return row;
	}
	//查询未审核的维护记录
	public List<MaintenanceRecordModel> getNotAuditMainRecordList() throws SQLException{
		List<MaintenanceRecordModel> list = new ListModelList<MaintenanceRecordModel>();
		dbconn con = new  dbconn();
		String username = UserInfo.getUsername();
		String strsql ="SELECT [id],[proposereson],[proposerdate],[sql],[mainresult] FROM MaintenanceRecord where status=0 and proposer='"+username+"'";
		try {
			ResultSet rs= con.GRS(strsql);
			while(rs.next()){
				MaintenanceRecordModel mm =new MaintenanceRecordModel();
				mm.setId(rs.getInt("id"));
				mm.setProposereson(rs.getString("proposereson"));
				mm.setProposerdate(rs.getDate("proposerdate"));
				mm.setSql(rs.getString("sql"));
				mm.setMainresult(rs.getString("mainresult"));
				list.add(mm);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			con.Close();
		}
		return list;
	}
	//根据id删除未审核维护记录
	public int deleteMainRecord(int id){
		dbconn con = new  dbconn();
		String strsql="DELETE FROM MaintenanceRecord WHERE status=0 and id='"+id+"'";
		int a=con.execQuery(strsql);
		return a;
	}
	//更新未审核维护记录
	public int updateMainRecord(MaintenanceRecordModel mr){
		int i= 0;
		dbconn con = new  dbconn();
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE MaintenanceRecord  SET ");
		sql.append(" proposereson =?, ");
		sql.append(" sql =? ");
		sql.append(" where id=?");
		try {
			 i= con.updatePrepareSQL(sql.toString(),mr.getProposereson(),mr.getSql(),mr.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
		
	}
	//当前技术员多条件查询维护记录
	public List<MaintenanceRecordModel> getMainRecordList(String strsql) throws SQLException{
		String username = UserInfo.getUsername();//当前操作的用户
		List<MaintenanceRecordModel> list = new ListModelList<MaintenanceRecordModel>();
		dbconn con = new  dbconn();
		StringBuilder sql = new StringBuilder();
		sql.append(" select * from MaintenanceRecord where (ISNULL(approver,'')='' or approver='"+username+"') ");
		sql.append(strsql);
		try {
			System.out.println(sql.toString());
			ResultSet rs= con.GRS(sql.toString());
			while(rs.next()){
				MaintenanceRecordModel mm =new MaintenanceRecordModel();
				mm.setId(rs.getInt("id"));
				mm.setProposer(rs.getString("proposer"));
				mm.setProposereson(rs.getString("proposereson"));
				mm.setProposerdate(rs.getDate("proposerdate"));
				mm.setSql(rs.getString("sql"));
				mm.setStatus(rs.getInt("status"));
				mm.setApprover(rs.getString("approver"));
				mm.setAudittime(rs.getDate("audittime"));
				mm.setMainresult(rs.getString("mainresult"));
				mm.setIsbackup(rs.getInt("isbackup"));
				list.add(mm);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			con.Close();
		}
		return list;
	}
	//获取维护记录申请时间
	public List<String> getDayList(){
		String sql="select distinct CONVERT(varchar(100), proposerdate, 112) from MaintenanceRecord";
		List<String> list =new ArrayList<>();
		list.add("");
		dbconn con = new  dbconn();
		ResultSet rs;
		try {
			rs = con.GRS(sql);
			while(rs.next()){
               list.add(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	//获取维护记录审核时间
	public List<String> getAduitDayList(){
			String sql="select  distinct CONVERT(varchar(100), isnull(audittime,GETDATE()), 112) from MaintenanceRecord ";
			List<String> list =new ArrayList<>();
			list.add("");
			dbconn con = new  dbconn();
			ResultSet rs;
			try {
				rs = con.GRS(sql);
				while(rs.next()){
	               list.add(rs.getString(1));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return list;
		}
	 //修改维护记录审核状态
	 public int aduitMainRecord(MaintenanceRecordModel mr){
			int i= 0;
			String username = UserInfo.getUsername();//当前操作的用户
			dbconn con = new  dbconn();
			StringBuilder sql = new StringBuilder();
			sql.append(" UPDATE MaintenanceRecord  SET ");
			sql.append(" status =?, ");
			sql.append(" approver =?, ");
			sql.append(" audittime =GETDATE() ");
			sql.append(" where id=?");
			System.out.println(sql.toString());
			try {
				 i= con.updatePrepareSQL(sql.toString(),mr.getStatus(),username,mr.getId());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return i;
			
		}
		
	  //修改维护记录备份状态
	  public int backUpMainRecord(MaintenanceRecordModel mr){
			int i= 0;
			dbconn con = new  dbconn();
			String username = UserInfo.getUsername();//当前操作的用户
			StringBuilder sql = new StringBuilder();
			sql.append(" UPDATE MaintenanceRecord  SET ");
			sql.append(" isbackup =?, ");
			sql.append(" approver =? ");
			sql.append(" where id=?");
			System.out.println(sql.toString());
			try {
				 i= con.updatePrepareSQL(sql.toString(),mr.getIsbackup(),username,mr.getId());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return i;
			
		}
	  
	//当前用户多条件查询已审核的申请的维护记录
	public List<MaintenanceRecordModel> getAduitMainRecordList(String strsql) throws SQLException{
		String username = UserInfo.getUsername();//当前操作的用户
		List<MaintenanceRecordModel> list = new ListModelList<MaintenanceRecordModel>();
		dbconn con = new  dbconn();
		StringBuilder sql = new StringBuilder();
		sql.append(" select * from MaintenanceRecord where  status=1 ");
		sql.append(strsql);
		try {
			System.out.println(sql.toString());
			ResultSet rs= con.GRS(sql.toString());
			while(rs.next()){
				MaintenanceRecordModel mm =new MaintenanceRecordModel();
				mm.setId(rs.getInt("id"));
				mm.setProposer(rs.getString("proposer"));
				mm.setProposereson(rs.getString("proposereson"));
				mm.setProposerdate(rs.getDate("proposerdate"));
				mm.setSql(rs.getString("sql"));
				mm.setStatus(rs.getInt("status"));
				mm.setApprover(rs.getString("approver"));
				mm.setAudittime(rs.getDate("audittime"));
				mm.setMainresult(rs.getString("mainresult"));
				mm.setIsbackup(rs.getInt("isbackup"));
				list.add(mm);
			  }
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				con.Close();
			}
			return list;
		}
	
	   //更新审核维护记录维护结果
		public int updateAduitMainRecord(MaintenanceRecordModel mr){
			int i= 0;
			dbconn con = new  dbconn();
			StringBuilder sql = new StringBuilder();
			sql.append(" UPDATE MaintenanceRecord  SET ");
			sql.append(" mainresult =? ");
			sql.append(" where id=? ");
			try {
				 i= con.updatePrepareSQL(sql.toString(),mr.getMainresult(),mr.getId());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return i;
			
		}
}
