package Controller.Canlendar;

import java.util.Date;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkex.zul.Colorbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import bll.Canlendar.Calendar_InfoBll;

import Model.CalendarsModel;
import Util.UserInfo;

public class Calendar_EditorController extends SelectorComposer<Component>{
	@Wire
	private Window win;
	@Wire
	private Textbox cont;
	@Wire
	private Textbox tittle;
	@Wire
	private Datebox begindate;
	@Wire
	private Datebox enddate;
	@Wire
	private Colorbox borthcolor;
	@Wire
	private Colorbox concolor;
	private int id=0;
	private String idstr="";
	CalendarsModel model = (CalendarsModel)Executions.getCurrent().getArg().get("model");
	String username = UserInfo.getUsername();
	// 重写组件初始化方法
		public void doAfterCompose(Component comp) throws Exception {
			// TODO Auto-generated method stub
			super.doAfterCompose(comp);
			
			if(model.getCale_content()!="")
			{
				String s = new String(model.getCale_content());   
				String con[] = s.split("/",2);
				idstr=con[0];
				cont.setValue(con[1]+"");
			}
			else
			{
				win.setTitle("新增事件");
			}
		}
	
	 @Listen("onClick = #okbtn")
	 public void addcanlendar() {
		String str=isEmploy();
		if(str=="")
		{
			Calendar_InfoBll bll=new Calendar_InfoBll();
			CalendarsModel models=new CalendarsModel();
			models.setCale_begindate(begindate.getValue());
			models.setCale_enddate(enddate.getValue());
			models.setCale_contentcolor(concolor.getValue());
			models.setCale_content(cont.getValue());
			models.setCale_headcolor(borthcolor.getValue());
			models.setCale_tittle(tittle.getValue());
			models.setCale_addname(username);
			if(idstr!=""&&!idstr.equals("")&&idstr!=null)
			{
				id=Integer.parseInt(idstr);
				models.setId(id);
				int k=bll.updateCalendarsInfo(models);
				if(k>0)
				{
					Messagebox.show("修改成功","提示",Messagebox.OK, Messagebox.INFORMATION);
					win.detach();
				}
				else
				{
					Messagebox.show("修改失败","提示",Messagebox.OK, Messagebox.INFORMATION);
				}
			}
			else
			{
				int k=bll.addCalendarsInfo(models);
				if(k>0)
				{
					Messagebox.show("添加成功","提示",Messagebox.OK, Messagebox.INFORMATION);
					win.detach();
				}
				else
				{
					Messagebox.show("添加失败","提示",Messagebox.OK, Messagebox.INFORMATION);
				}
			}
		}
	 }
	 
	 @Listen("onClick = #cancelbtn")
	 public void cancelcanlendar() {
		 win.detach();
	 }
	 
	 @Listen("onClick = #deletebtn")
	 public void delcanlendar() {
		if(idstr!=""&&!idstr.equals("")&&idstr!=null)
		{
			id=Integer.parseInt(idstr);
			Calendar_InfoBll bll=new Calendar_InfoBll();
			int k=bll.deleteCalendarsInfo(id);
			if(k>0)
			{
				Messagebox.show("删除成功","提示",Messagebox.OK, Messagebox.INFORMATION);
				win.detach();
			}
			else
			{
				Messagebox.show("删除失败","提示",Messagebox.OK, Messagebox.INFORMATION);
			}
		}
	 }
	 
	 public String isEmploy()
	 {
		 String str="";
		 if(begindate.getValue()==null)
		 {
			 str="开始时间不能为空";
			 Clients.showNotification(str,"info",begindate,"end_center",3000);
		 }
		 else if(enddate.getValue()==null)
		 {
			 str="结束时间不能为空";
			 Clients.showNotification(str,"info",enddate,"end_center",3000);
		 }
		 else if(begindate.getValue()==enddate.getValue()||begindate.getValue().equals(enddate.getValue()))
		 {
			 str="开始时间和结束时间不能相同";
			 Clients.showNotification(str,"info",enddate,"end_center",3000);
		 }
		 else if(tittle.getValue()==""||tittle.getValue().equals("")||tittle.getValue()==null)
		 {
			 str="标题不能为空";
			 Clients.showNotification(str,"info",tittle,"end_center",3000);
		 }
		 else if(compare_date(begindate.getValue(),enddate.getValue())==1)
		 {
			 str="开始时间不能大于结束时间";
			 Clients.showNotification(str,"info",begindate,"end_center",3000);
		 }
		 return str;
	 }
	 
	 public static int compare_date(Date DATE1, Date DATE2) {
		 int k=0;
	     try {
	          if (DATE1.getTime() > DATE2.getTime()) {
	              k=1;
	           } else if (DATE1.getTime() < DATE2.getTime()) {
	        	  k=2;
	          }
	        } catch (Exception exception) {
	            exception.printStackTrace();
	      }
	      return k;
	 }
}
