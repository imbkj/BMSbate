package Controller.ClientRelations.VisitInfo;

import impl.UserInfoImpl;
import impl.SystemControl.Data_PopedomIpml;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Composer;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.CheckEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;

import service.DataPopedomService;
import service.UserInfoService;
import Model.LoginModel;
import Model.VisitInfoModel;
import bll.ClientRelations.VisitInfo.VisitInfoBll;

public class visitinfo_auditingcontroller  extends SelectorComposer<Component>{

private static final long serialVersionUID = 1L;
	
	@Wire
	private Grid gridwin;  //获取前台grid
	@Wire
	private Checkbox selAll;   //全选查询选项框
	
	private String  cola_company="";   //潜在客户名称
	private String  viin_class="";     //拜访方式
	private String viin_person="";     //拜访人
	private String viin_month="";      //拜访计划月
	private String  viin_addname="";   //计划添加人
	
	
	public List<VisitInfoModel> visitInfolist = new ArrayList<VisitInfoModel>();
	VisitInfoBll visitinfob = new VisitInfoBll();
	
	DataPopedomService d =new  Data_PopedomIpml("7","","","","","","");
	public List<LoginModel> visipersonlist = new ArrayList<LoginModel>();
	
	Session session =  Executions.getCurrent().getDesktop().getSession();
	UserInfoService user = new UserInfoImpl(session);
	

	public String getCola_company() {
		return cola_company;
	}

	public void setCola_company(String cola_company) {
		this.cola_company = cola_company;
	}

	public String getViin_class() {
		return viin_class;
	}

	public void setViin_class(String viin_class) {
		this.viin_class = viin_class;
	}

	public String getViin_person() {
		return viin_person;
	}

	public void setViin_person(String viin_person) {
		this.viin_person = viin_person;
	}

	public String getViin_month() {
		return viin_month;
	}

	public void setViin_month(String viin_month) {
		this.viin_month = viin_month;
	}

	public String getViin_addname() {
		return viin_addname;
	}

	public void setViin_addname(String viin_addname) {
		this.viin_addname = viin_addname;
	}

	public List<VisitInfoModel> getVisitInfolist() {
		return visitInfolist;
	}

	public void setVisitInfolist(List<VisitInfoModel> visitInfolist) {
		this.visitInfolist = visitInfolist;
	}

	public List<LoginModel> getVisipersonlist() {
		return visipersonlist;
	}

	public void setVisipersonlist(List<LoginModel> visipersonlist) {
		this.visipersonlist = visipersonlist;
	}

	public visitinfo_auditingcontroller()
	{
		visipersonlist=d.getdepLoginlist();
	}
	
	 @Listen("onCheck = #selAll")
	   public void checksel(CheckEvent e){
			 List row = gridwin.getRows().getChildren();
			    for(Object obj:row){
			      Row comp = (Row) obj;
			      Checkbox ck = (Checkbox) comp.getChildren().get(8);
			      ck.setChecked(e.isChecked());
			      if(ck.isChecked()){
			    	  System.out.print(ck.getValue()+";");
			      }
			   }
		}

	 
	 //提交审核事件 
		@Listen("onClick = #autiding")
		 public void adddatapedom(){
			 int i=0;
			try{
				
				 List row = gridwin.getRows().getChildren();
				 //遍历row
				    for(Object obj:row){
				      //定义comp 将row的一行赋值给它
				      Row comp = (Row) obj;
				      //定义3个checkbox获取当前遍历行的控件
				      Checkbox checksel = (Checkbox) comp.getChildren().get(8);
				      Label lab =(Label) comp.getChildren().get(9);
				      
				      if(checksel.isChecked())
				      {
				    	  i=visitinfob.auditing(checksel.getValue().toString(),user.getUsername(),Integer.parseInt(lab.getValue().toString()));
				      }
				
		    
				  
			
				    }
				    if (i==0)
			    	   {
			    		   Messagebox.show("保存失败!", "ERROR", Messagebox.OK, Messagebox.ERROR);
			    	   }
			    	   else
			    	   {
			    		   Messagebox.show("保存成功!", "INFORMATION", Messagebox.OK, Messagebox.INFORMATION);
			    	   }
			}
			catch(Exception e) {
				
			}
		}

	//查询客服数据权限列表
		@Command
		@NotifyChange("visitInfolist")
		 public void search() throws SQLException {
			//System.out.print(log_name);
			if (!viin_person.isEmpty())
			{
			try{
				VisitInfoBll visitinfob = new VisitInfoBll();
				visitInfolist=visitinfob.getlist(viin_person, viin_month, cola_company, "", viin_addname,0);
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			}
			else
			{
				 Messagebox.show("请先选择拜访人!", "ERROR", Messagebox.OK, Messagebox.ERROR);
			}

	 	}
	
	
	
	
}
