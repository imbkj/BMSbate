package Controller.Canlendar;

import java.util.HashMap;
import java.util.Map;
import org.zkoss.calendar.Calendars;
import org.zkoss.calendar.event.CalendarsEvent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Window;

import bll.Canlendar.Calendar_InfoBll;

import Model.CalendarsModel;
import Util.CalendarEventUtil;
import Util.CalendarModel;
import Util.UserInfo;

public class CalendarTest_Controller extends SelectorComposer<Component>{
	private CalendarsEvent calendarsEvent = null;
	@Wire
    private Calendars calendars;
	@Wire
	private Datebox selectdate;
     
    private CalendarModel calendarModel;
    Calendar_InfoBll bll=new Calendar_InfoBll();
    String username = UserInfo.getUsername();	
 
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        //对日历绑定数据
        calendarModel = new CalendarModel(bll.bindCalendarEventUtil());
        calendars.setModel(this.calendarModel);
        selectdate.setValue(calendars.getCurrentDate());
    }
    
    //下一页
    @Listen("onClick = #next")
    public void gotoNext(){
        calendars.nextPage();
               
      }
    //上一页
    @Listen("onClick = #prev")
    public void gotoPrev(){
        calendars.previousPage();
    }
    
    //选择日期
    @Listen("onChange = #selectdate")
    public void selectdate(){
    	if(selectdate.getValue()!=null)
    	{
    		calendars.setCurrentDate(selectdate.getValue());
    	}
    }
     
    //页面按天显示
    @Listen("onClick = #pageDay")
    public void changeToDay(){
        calendars.setMold("default");
        calendars.setDays(1);
    }
    //页面按州显示
    @Listen("onClick = #pageWeek")
    public void changeToWeek(){
        calendars.setMold("default");
        calendars.setDays(7);
    }
    
    //页面按月显示
    @Listen("onClick = #pageMonth")
    public void changeToMonth(){
        calendars.setMold("month");
    }
       
    //监听创建事件和编辑事件
	@Listen("onEventCreate = #calendars; onEventEdit = #calendars")
	public void createEvent(CalendarsEvent event) {
	       calendarsEvent = event;
	         
	        //当处于编辑状态时添加一层蒙版
	        calendarsEvent.stopClearGhost();
	        
	        
	        CalendarEventUtil data = (CalendarEventUtil)event.getCalendarEvent();
	        String mold=calendars.getMold();
	        //当data是null的时候就是新建了一个新的计划,data不是null的时候就是编辑原有的计划
	        if(data == null) {
	            data = new CalendarEventUtil();
	            CalendarsModel model=new CalendarsModel();
		        model.setCale_addname(username);
		        model.setCale_begindate(event.getBeginDate());
		        model.setCale_tittle("");
		        model.setCale_contentcolor("#3366ff");
		        model.setCale_headcolor("#6699ff");
		        model.setCale_enddate(event.getEndDate());
		        model.setCale_content("");
		        Map map=new HashMap();
		    	map.put("model",model);
		    	map.put("mold", mold);
		    	Window window = (Window)Executions.createComponents("/ClientRelations/VisitInfo/LatencyClientVisit_Add.zul",null, map);
		    	window.doModal();
		    	calendarModel = new CalendarModel(bll.bindCalendarEventUtil());
		        calendars.setModel(this.calendarModel);
	        } else {
	            data = (CalendarEventUtil) event.getCalendarEvent();
	            CalendarsModel model=new CalendarsModel();
	            model.setCale_addname(username);
	            model.setCale_begindate(data.getBeginDate());
	            model.setCale_tittle(data.getContent());
	            model.setCale_contentcolor(data.getContentColor());
	            model.setCale_headcolor(data.getHeaderColor());
	            model.setCale_enddate(data.getEndDate());
	            model.setCale_content(data.getTitle());
	            Map map=new HashMap();
	    		map.put("model",model);
	    		
	    		map.put("id","0");
	    		map.put("daid",model.getCale_content().split("/",2)[0]);
	    		
    		
	    		Window window = (Window)Executions.createComponents("/ClientRelations/VisitInfo/visitinfo_selectone.zul",null, map);
	    		window.doModal();
	    		calendarModel = new CalendarModel(bll.bindCalendarEventUtil());
	            calendars.setModel(this.calendarModel);
	        }
	    }
	 
	  @Listen("onEventUpdate = #calendars")
	  public void updateEvent(CalendarsEvent event) {
		  if(event.getCalendarEvent().getTitle()!=""&&!event.getCalendarEvent().getTitle()
				  .equals("")&&event.getCalendarEvent().getTitle()!=null)
		  {
			  String con[] = event.getCalendarEvent().getTitle().split("/",2);
			  String idstr=con[0];
			  
			  Calendar_InfoBll bll=new Calendar_InfoBll();
			  CalendarsModel models=new CalendarsModel();
			  models.setCale_begindate(event.getBeginDate());
			  models.setCale_enddate(event.getEndDate());
			  models.setCale_contentcolor(event.getCalendarEvent().getContentColor());
			  models.setCale_content(con[1]);
			  models.setCale_headcolor(event.getCalendarEvent().getHeaderColor());
			  models.setCale_tittle(event.getCalendarEvent().getContent());
			  if(idstr!=""&&!idstr.equals("")&&idstr!=null)
			  {
				  models.setId(Integer.parseInt(idstr));
				  bll.updateCalendarsInfo(models);
				  calendarModel = new CalendarModel(bll.bindCalendarEventUtil());
			      calendars.setModel(this.calendarModel);
			  }
		  }
	 }
}
