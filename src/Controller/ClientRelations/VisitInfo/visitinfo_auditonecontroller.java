package Controller.ClientRelations.VisitInfo;

import impl.UserInfoImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.CheckEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;

import service.DataPopedomService;
import service.UserInfoService;
import Model.CoCompactModel;
import Model.LoginModel;
import Model.VisitInfoModel;
import bll.ClientRelations.VisitInfo.VisitInfoBll;


public class visitinfo_auditonecontroller   extends SelectorComposer<Component> {

private static final long serialVersionUID = 1L;
	
	@Wire
	private Grid gridwin;  //获取前台grid
	
	String viin_id;
	String table_id;
	


	

	
	public List<VisitInfoModel> visitInfolist = new ArrayList<VisitInfoModel>();
	VisitInfoBll visitinfob = new VisitInfoBll();
	
	Session session =  Executions.getCurrent().getDesktop().getSession();
	UserInfoService user = new UserInfoImpl(session);
	

	 

	
	public visitinfo_auditonecontroller()
	{
		//节点ID
		   viin_id=Executions.getCurrent().getArg().get("id").toString();

		//表ID
		 table_id=Executions.getCurrent().getArg().get("daid").toString();
		 
		System.out.println(table_id);
		
		search();
		
	
		
		
	}
	
	
	

	public List<VisitInfoModel> getVisitInfolist() {
		return visitInfolist;
	}

	public void setVisitInfolist(List<VisitInfoModel> visitInfolist) {
		this.visitInfolist = visitInfolist;
	}

	
	
		public void search()
		{
			try{
				VisitInfoBll visitinfob = new VisitInfoBll();
				visitInfolist=visitinfob.getlist(table_id);
				
				
				
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	 
	 
	 //提交审核事件 
		@Command("auditing")
		@NotifyChange("visitInfolist")
		 public void auditing(){
			 int i=0;
			try{
				
	
				
			 i=visitinfob.auditing(table_id,user.getUsername(),Integer.parseInt(viin_id));
				 
			   // i=visitinfob.auditing(viin_id);
				 
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
			search();
		}
		
		
		 //提交审核事件 
			@Command
			@Listen("onClick = #untread")
			@NotifyChange("visitInfolist")
			 public void untread(){
				 int i=0;
				try{
					
				 
				  //  i=visitinfob.untread(viin_id);

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
				search();
			}

	
}
