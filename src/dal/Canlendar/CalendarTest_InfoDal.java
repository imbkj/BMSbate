package dal.Canlendar;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.CalendarsModel;
import Model.CreditContentInfoModel;


public class CalendarTest_InfoDal {
	//查询日历信息
	public List<CalendarsModel> getCalendarsInfo(String username )
	{
		ResultSet rs = null;
		List<CalendarsModel> calendarsinfo = new ArrayList<CalendarsModel>();
		if (!calendarsinfo.isEmpty())
			calendarsinfo.clear();
		try {
			dbconn db = new dbconn();
			String sqlstr = "SELECT viin_id, viin_month, viin_type, viin_person, viin_class, viin_aim, viin_cost, viin_costremark, viin_summary, viin_feedback, viin_iffollow, viin_state," +
					" viin_truetime, viin_addname, viin_addtime, viin_auditingname, viin_auditingtime, viin_subordinate, viin_personed, viin_job, viin_tapr_id, viin_starttime, viin_endtime," +
					"  viin_remark,viin_headcolor,viin_headcolor1,viin_contentcolor,viin_contentcolor1" +
					" FROM         VisitInfo where viin_person='"+ username +"' union SELECT  viin_id, viin_month, viin_type, viin_person, viin_class, viin_aim, viin_cost, viin_costremark, viin_summary, viin_feedback, viin_iffollow, viin_state, " +
					"viin_truetime, viin_addname, viin_addtime, viin_auditingname, viin_auditingtime, viin_subordinate, viin_personed, viin_job, viin_tapr_id, viin_starttime, viin_endtime," +
					"viin_remark,viin_headcolor,viin_headcolor1,viin_contentcolor,viin_contentcolor1" +
					" FROM         VisitInfo where CHARINDEX('"+ username +"', viin_subordinate)>0 ";
			rs = db.GRS(sqlstr);
			//System.out.println(sqlstr);
			while (rs.next()) {
			
				calendarsinfo.add(new CalendarsModel(rs.getInt("viin_id"),rs.getString("viin_person"),rs.getTimestamp("viin_starttime"),rs.getTimestamp("viin_endtime"),
					rs.getString("viin_headcolor"),rs.getString("viin_contentcolor"),rs.getString("viin_headcolor1"),rs.getString("viin_contentcolor1"),rs.getString("viin_remark"),
					rs.getDate("viin_addtime"),rs.getString("viin_addname"),rs.getString("viin_remark")));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return calendarsinfo;
	}
	
	
	public List<CalendarsModel> getCalendarsInfo()
	{
		ResultSet rs = null;
		List<CalendarsModel> calendarsinfo = new ArrayList<CalendarsModel>();
		if (!calendarsinfo.isEmpty())
			calendarsinfo.clear();
		try {
			dbconn db = new dbconn();
			String sqlstr = "SELECT  * from calendars";
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				calendarsinfo.add(new CalendarsModel(rs.getInt("id"),rs.getTimestamp("cale_begindate"),rs.getTimestamp("cale_enddate"),
					rs.getString("cale_headcolor"),rs.getString("cale_contentcolor"),rs.getString("cale_content"),
					rs.getDate("cale_addtime"),rs.getString("cale_addname"),rs.getString("cale_tittle")));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return calendarsinfo;
	}
	
	
	//对日历事件安排表calendars插入一条数据，并返回一个Int类型的数
	public int addCalendarsInfo(CalendarsModel model){
		int k=0;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String begindate = formatter.format(model.getCale_begindate());
			String enddate = formatter.format(model.getCale_enddate());
			Timestamp begindates = Timestamp.valueOf(begindate);
			Timestamp enddates = Timestamp.valueOf(enddate);
			dbconn db = new dbconn();
			Timestamp date= new Timestamp(System.currentTimeMillis());
			String sql = "insert into calendars(cale_begindate,cale_enddate,cale_headcolor,cale_contentcolor,cale_content";
				sql=sql+",cale_addtime,cale_addname,cale_tittle)";
				sql=sql+" values('"+begindates+"','"+enddates+"','"+model.getCale_headcolor();
				sql=sql+"','"+model.getCale_contentcolor()+"','"+model.getCale_content()+"','"+date+"'";
				sql=sql+",'"+model.getCale_addname()+"','"+model.getCale_tittle()+"')";
				k=db.execQuery(sql);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return k;
	}
	
	//根据id更新日历事件安排表calendars，并返回一个Int类型的数
	public int updateCalendarsInfo(CalendarsModel model){
		int k=0;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String begindate = formatter.format(model.getCale_begindate());
			String enddate = formatter.format(model.getCale_enddate());
			Timestamp begindates = Timestamp.valueOf(begindate);
			Timestamp enddates = Timestamp.valueOf(enddate);
			dbconn db = new dbconn();
			Timestamp date= new Timestamp(System.currentTimeMillis());
			String sql = "update calendars set cale_begindate='"+begindates+"',cale_enddate='"+enddates+"'";
				sql=sql+",cale_tittle='"+model.getCale_tittle()+"',cale_headcolor='"+model.getCale_headcolor()+"'";
				sql=sql+",cale_contentcolor='"+model.getCale_contentcolor()+"',cale_content='"+model.getCale_content()+"'";
				sql=sql+" where id="+model.getId();
				k=db.execQuery(sql);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return k;
		}
	
	//根据id更新日历事件安排表calendars，并返回一个Int类型的数
	public int deleteCalendarsInfo(int id){
		int k=0;
		try {
			dbconn db = new dbconn();
			String sql = "delete from calendars where id="+id;
				k=db.execQuery(sql);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return k;
	}
}
