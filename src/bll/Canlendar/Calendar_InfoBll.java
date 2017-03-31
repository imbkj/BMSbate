package bll.Canlendar;

import java.util.LinkedList;
import java.util.List;

import org.zkoss.calendar.api.CalendarEvent;

import dal.Canlendar.CalendarTest_InfoDal;

import Model.CalendarsModel;
import Util.CalendarEventUtil;
import Util.UserInfo;

public class Calendar_InfoBll {
	CalendarTest_InfoDal dal=new CalendarTest_InfoDal();
	String username = UserInfo.getUsername();
	//查询日历信息
	public List<CalendarsModel> getCalendarsInfo()
	{
		return dal.getCalendarsInfo(username);
	}
	
	//对日历事件安排表calendars插入一条数据，并返回一个Int类型的数
	public int addCalendarsInfo(CalendarsModel model){
		return dal.addCalendarsInfo(model);
	}
	//根据id更新日历事件安排表calendars，并返回一个Int类型的数
	public int updateCalendarsInfo(CalendarsModel model){
		return dal.updateCalendarsInfo(model);
	}
	
	//根据id更新日历事件安排表calendars，并返回一个Int类型的数
		public int deleteCalendarsInfo(int id){
			return dal.deleteCalendarsInfo(id);
		}
	
	//把查询查来的日历表的数据添加到日历的构造其中
	public List<CalendarEvent> bindCalendarEventUtil()
	{
		List<CalendarEvent> calendarEvents = new LinkedList<CalendarEvent>();
        List<CalendarsModel> calendinfo=this.getCalendarsInfo();
        if(!calendinfo.isEmpty())
        {
        	for(int i=0;i<calendinfo.size();i++)
        	{
        		
        		//区分拜访人和次执行人的颜色
        		if (username.equals(calendinfo.get(i).getViin_person().toString()))
        		{
        			
        		  			calendarEvents.add(new CalendarEventUtil(calendinfo.get(i).getCale_begindate(),calendinfo.get(i).getCale_enddate(), 
        	        		calendinfo.get(i).getCale_headcolor(), calendinfo.get(i).getCale_contentcolor(),calendinfo.get(i).getCale_tittle(),
        	        		calendinfo.get(i).getId()+"/"+calendinfo.get(i).getCale_content()));
        		}
        		else
        		{
        			calendarEvents.add(new CalendarEventUtil(calendinfo.get(i).getCale_begindate(),calendinfo.get(i).getCale_enddate(), 
        	        		calendinfo.get(i).getCale_headcolor1(), calendinfo.get(i).getCale_contentcolor1(),calendinfo.get(i).getCale_tittle(),
        	        		calendinfo.get(i).getId()+"/"+calendinfo.get(i).getCale_content()));
        		}
        	
        	}
        }
        return calendarEvents;
	}
}
