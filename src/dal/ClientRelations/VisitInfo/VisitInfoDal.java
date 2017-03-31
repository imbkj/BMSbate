package dal.ClientRelations.VisitInfo;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import Conn.dbconn;
import Model.VisitInfoModel;

public class VisitInfoDal {
	
	public VisitInfoDal() {

	}

	// 获取拜访计划列表集合
	public List<VisitInfoModel> getlist(String str) {
		List<VisitInfoModel> VisitInfoM = new ArrayList<VisitInfoModel>();
		try {
			if (!VisitInfoM.isEmpty())
				VisitInfoM.clear();
			ResultSet rs = null;
		
			String sqlstr = "SELECT      cola_id, covi_cola_id, covi_viin_id, cola_company, cid, cola_spell,"
					+ " cola_address, cola_clientarea, cola_state, cola_companytype, viin_id, viin_month, viin_type,"
					+ " viin_person, viin_class, viin_aim, viin_cost, viin_costremark, viin_summary, viin_feedback, "
					+ "viin_iffollow, viin_state, viin_truetime, viin_addname,viin_addtime, viin_auditingname, "
					+ "viin_auditingtime, viin_subordinate, viin_personed, viin_job,viin_stateStr,viin_tapr_id,viin_starttime,viin_endtime,viin_remark FROM   View_Visitinfo where 1=1 "
					+ str + " " + "order by viin_addname,viin_addtime ";
			System.out.print(sqlstr);
			try {
				dbconn db = new dbconn();
				rs = db.GRS(sqlstr);
				while (rs.next()) {
					VisitInfoM.add(new VisitInfoModel(rs.getInt("viin_id"), rs
							.getInt("viin_month"), rs.getString("viin_type"),
							rs.getString("viin_person"), rs
									.getString("viin_class"), rs
									.getInt("viin_state"), rs
									.getString("viin_addname"), rs
									.getDate("viin_addtime"), rs
									.getString("viin_auditingname"), rs
									.getDate("viin_auditingtime"), rs
									.getInt("covi_cola_id"), rs.getInt("cid"),
							rs.getInt("cola_id"), rs.getString("cola_company"),
							rs.getString("cola_spell"), rs
									.getString("cola_companytype"), rs
									.getString("cola_address"), rs
									.getString("cola_clientarea"), rs
									.getInt("cola_state"), rs
									.getString("viin_stateStr"),rs.getString("viin_subordinate"),
									rs.getInt("viin_tapr_id"),
									rs.getTimestamp("viin_starttime").toString().replace("00:00:00.0", ""),rs.getTimestamp("viin_endtime").toString().replace("00:00:00.0", ""),
									rs.getString("viin_remark")
									));
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return VisitInfoM;
	}

	// 审核拜访计划
	public int auditing(int viin_id,String auditname) throws Exception {
		int row = 0;
		
		java.util.Date datetime=new java.util.Date();
		java.sql.Timestamp time=new java.sql.Timestamp(datetime.getTime());

		String sqlstr = "update VisitInfo  set viin_state=1," +
				"viin_contentcolor='#008A00',viin_contentcolor1='#FA6800',viin_auditingname='"+auditname+"',viin_auditingtime='"+time+"' where viin_id="
				+ viin_id + "";

		dbconn auditing = new dbconn();
		try {
			 System.out.print(sqlstr);
			row = auditing.execQuery(sqlstr);
			

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			auditing.Close();
			
			
		}
		
		if (row==1)
		{
			return viin_id;
		}
		else
		{
			return row;
		}
	}
	
	
	// 退回计划
	public int untread(int viin_id) throws Exception {
		int row = 0;

		String sqlstr = "update VisitInfo  set viin_state=9 where viin_id="
				+ viin_id + "";

		dbconn untread = new dbconn();
		try {
			 System.out.print(sqlstr);
			row = untread.execQuery(sqlstr);

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			untread.Close();
		}
		if (row==1)
		{
			return viin_id;
		}
		else
		{
			return row;
		}
	}


	// 删除拜访计划
	public int deleteing(int viin_id) throws Exception {
		int row = 0;
		String sqlstr = "update VisitInfo  set viin_state=9 where viin_id="
				+viin_id+ "";
		dbconn deleteing = new dbconn();
		try {
			System.out.print(sqlstr);
			row = deleteing.execQuery(sqlstr);

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			deleteing.Close();
		}
		if (row==1)
		{
			return viin_id;
		}
		else
		{
			return row;
		}
	}
	
	// 任务单编号更新
	public int updatetaskid(int viin_id,int tapr_id) throws Exception {
		int row = 0;
		String sqlstr = "update VisitInfo  set viin_tapr_id="+tapr_id+" where viin_id="
				+viin_id+ "";
		dbconn update = new dbconn();
		try {
			
			row = update.execQuery(sqlstr);
			
			
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			update.Close();
		}
		if (row==1)
		{
			return viin_id;
		}
		else
		{
			return row;
		}
	}


	// 新增拜访计划
	public int addVisitInfo(String viin_person,String viin_subordinate,String viin_type,String viin_class,String viin_month, String cola_id,String viin_addname,String viin_state
			,Date viin_starttime,Date viin_endtime,String viin_remark) {
		
		dbconn conn = new dbconn();
		try {
			
			CallableStatement c=conn.getcall("VisitInfoAdd_P_lsb(?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setString(1, viin_person);
			c.setString(2, viin_subordinate);
			c.setString(3, viin_type);
			c.setString(4, viin_class);
			c.setString(5, viin_month);
			c.setString(6, cola_id);
			c.setString(7, viin_addname);
			c.setString(8, viin_state);
			c.setDate(9, new java.sql.Date(viin_starttime.getTime()));
			c.setDate(10,  new java.sql.Date(viin_endtime.getTime()));
			c.setString(11, viin_remark);
			c.registerOutParameter(12, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(12);
		} 
		catch (Exception e) {
			return 0;
		}
	}
	
	// 更新拜访计划
	public int updateVisitInfo(int viin_id,String viin_person,String viin_subordinate,String viin_type,String viin_class,String viin_month) {
		dbconn conn = new dbconn();
		try {
			CallableStatement c=conn.getcall("VisitInfoUpdate_P_lsb(?,?,?,?,?,?,?)");
			c.setInt(1, viin_id);
			c.setString(2, viin_person);
			c.setString(3, viin_subordinate);
			c.setString(4, viin_type);
			c.setString(5, viin_class);
			c.setString(6, viin_month);
			c.registerOutParameter(7, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(7);
		} 
		catch (Exception e) {
			return 1;
		}
	}
}
