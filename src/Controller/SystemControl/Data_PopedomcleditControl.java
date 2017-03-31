package Controller.SystemControl;

import impl.UserInfoImpl;
import impl.SystemControl.Data_PopedomIpml;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;

import bll.CoLatencyClient.CoLatencyClient_AddBll;
import bll.SystemControl.Data_PopedomBll;
import bll.SystemControl.Data_PopedomChangeBll;

import service.DataPopedomService;
import service.UserInfoService;
import Model.CoLatencyClientModel;
import Model.DataPopedomModel;
import Model.LoginModel;
import Util.UserInfo;



public class Data_PopedomcleditControl extends SelectorComposer<Component>  {

	private static final long serialVersionUID = 1L;

	@Wire
	private Grid gridwin;
	@Wire
	private Checkbox selectAll;
	@Wire
	private Checkbox editAll;
	@Wire
	private Checkbox deleAll;
	@Wire
	private Combobox sqrcom;
	@Wire
	private Combobox bsqrcom;
	private String  sqrname="";
	private String  bsqrname="";
	private String cocid="";
	private String cola_id="";
	private String coshortname="";
	private String cola_company="";
	private String cola_follower="";
	public String getCola_company() {
		return cola_company;
	}

	public void setCola_company(String cola_company) {
		this.cola_company = cola_company;
	}

	public String getCola_follower() {
		return cola_follower;
	}

	public void setCola_follower(String cola_follower) {
		this.cola_follower = cola_follower;
	}
	//授权人list、被授权人list、以及客户list
	public List<LoginModel> sqrlist = new ArrayList<LoginModel>();
	public List<LoginModel> bsqrlist = new ArrayList<LoginModel>();
	public List<DataPopedomModel> cobaselist = new ArrayList<DataPopedomModel>();
	
	
	DataPopedomService d =new  Data_PopedomIpml("7","","","","","","");
	CoLatencyClientModel datapchangem =new CoLatencyClientModel();
	Data_PopedomChangeBll datapchangebll = new Data_PopedomChangeBll();
	
	
	CoLatencyClient_AddBll bll=new CoLatencyClient_AddBll();
	
	String username = UserInfo.getUsername();

	
	
	//构造函数 获取授权人和被授权人list
	public Data_PopedomcleditControl(){
		sqrlist=d.getLoginlist();
		bsqrlist=d.getLoginlist();

	}
	
	//监听查询权限全选按钮
	 @Listen("onCheck = #selectAll")
	   public void checksel(CheckEvent e){
			 List row = gridwin.getRows().getChildren();
			    for(Object obj:row){
			      Row comp = (Row) obj;
			      Checkbox ck = (Checkbox) comp.getChildren().get(4);
			      ck.setChecked(e.isChecked());
			      if(ck.isChecked()){
			    	  System.out.print(ck.getValue()+";");
			      }
			   }
		}
	//监听编辑权限全选按钮
	 @Listen("onCheck = #editAll")
	   public void checkedi(CheckEvent e){
			 List row = gridwin.getRows().getChildren();
			    for(Object obj:row){
			      Row comp = (Row) obj;
			      Checkbox ck = (Checkbox) comp.getChildren().get(5);
			      ck.setChecked(e.isChecked());
			      if(ck.isChecked()){
			    	  System.out.print(ck.getValue()+";");
			      }
			   }
		}
	//监听删除权限全选按钮
	 @Listen("onCheck = #deleAll")
	   public void checkdele(CheckEvent e){
			 List row = gridwin.getRows().getChildren();
			    for(Object obj:row){
			      Row comp = (Row) obj;
			      Checkbox ck = (Checkbox) comp.getChildren().get(6);
			      ck.setChecked(e.isChecked());
			      if(ck.isChecked()){
			    	  System.out.print(ck.getValue()+";");
			      }
			   }
		}
	 //提交数据事件
		@Listen("onClick = #addDatep")
		 public void adddatapedom(){
			 int i=0;
			try{
				if(((LoginModel) sqrcom.getSelectedItem().getValue()).getLog_id()!=0 & 
						((LoginModel) bsqrcom.getSelectedItem().getValue()).getLog_id()!=0){
     
				    //定义row 并将grid的行赋值到row	
						 List row = gridwin.getRows().getChildren();
						 //遍历row
						    for(Object obj:row){
						    //初始化3个boolean值
						      boolean selectid=false;
						      boolean editid=false;
						      boolean deleteid=false;
						      //定义comp 将row的一行赋值给它
						      Row comp = (Row) obj;
						      //定义3个checkbox获取当前遍历行的控件
						      Checkbox checksel = (Checkbox) comp.getChildren().get(4);
						      Checkbox checkedi = (Checkbox) comp.getChildren().get(5);
						      Checkbox checkdel = (Checkbox) comp.getChildren().get(6);
						      //逻辑检查checkbox是否被选中，并设定3个boolean变量的值

					      if(checksel.isChecked())
					      {
					    	  selectid=true;	
					    	  if(checkedi.isChecked())
						      {
					    		  editid=true;
					    		  if(checkdel.isChecked())
							      {
					    			  deleteid=true;
							      }
					    		  
	    	  			      }
					    	  else
					    	  {
					    		 
					    		  if(checkdel.isChecked())
							      {
					    			  deleteid=true;
							      }
					    	  }
					      }
					      else
					      {
					    	 
					    	  if(checkedi.isChecked())
						      {
					    		  editid=true;
					    		  if(checkdel.isChecked())
							      {
					    			  deleteid=true;
							      }
						      }
					    	  else
					    	  {
					    		 
					    		  if(checkdel.isChecked())
							      {
					    			  deleteid=true;
							      }
					    	  }
				      }
					      
					      datapchangem.setLog_id(((LoginModel) bsqrcom.getSelectedItem().getValue()).getLog_id());
				    	  datapchangem.setCola_id(Integer.parseInt(((Label)comp.getChildren().get(1)).getValue()));
				    	  datapchangem.setDat_selected(selectid);
				    	  datapchangem.setDat_edited(editid);
				    	  datapchangem.setDat_delete(deleteid);
				    	  datapchangem.setDat_addname(username);
				    	  
				    	  System.out.println(datapchangem.getCola_id());
				    	  
				    	  i=i+ datapchangebll.DataPopedomclchangeAdd(datapchangem);
				    }
				    
				    
				    if (i>0)
			    	   {
			    		   Messagebox.show("保存失败!", "ERROR", Messagebox.OK, Messagebox.ERROR);
			    	   }
			    	   else
			    	   {
			    		   Messagebox.show("保存成功!", "INFORMATION", Messagebox.OK, Messagebox.INFORMATION);
			    	   }
				}
				else
				{
					 Messagebox.show("请先选择授权人和被授权人！", "ERROR", Messagebox.OK, Messagebox.ERROR);
				}
				
			}
			catch(Exception e) {
				
			}
		}
//  权限编辑列表查询
	@Command
	@NotifyChange("cobaselist")
		 public void search() throws SQLException {
		if(!sqrname.isEmpty() || !bsqrname.isEmpty())
		{
			try{
				
					 
					 
//						String str="";
//						
//						if (cola_id!=""&&!cola_id.equals(""))
//							{
//								 str=str+" and cola_id like '%"+cola_id+"%'";
//							}
//						 
//						if(cola_company!=""&&!cola_company.equals(""))
//						{
//							str=str+" and cola_company like '%"+cola_company+"%'";
//						}
//					
//						
//						if(sqrname!=""&&!sqrname.equals(""))
//						{
//							str=str+" and cola_follower='"+sqrname+"'";
//						}
//						
//						if(bsqrname!=""&&!bsqrname.equals(""))
//						{
//							str=str+" and log_name='"+bsqrname+"'";
//						}
//						
//						 
//						System.out.println(str);
//						
//						DataPopedomService Datap =new Data_PopedomIpml("0",cocid,UserInfo.getUsername(),cola_company,cola_follower,null,null,null,"");
//						cobaselist=Datap.getPopedomCllist();
						
						
					 cobaselist=datapchangebll.getczlist(sqrname, bsqrname, cola_id, cola_company);
	
			} 
		catch (Exception e) {
				System.out.println(e.toString());
			}
		}
		else
		{
			Messagebox.show("请先选择授权人和被授权人！", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}

	 	}
	
	
	public String getCola_id() {
		return cola_id;
	}

	public void setCola_id(String cola_id) {
		this.cola_id = cola_id;
	}

	public String getSqrname() {
		return sqrname;
	}
	public void setSqrname(String sqrname) {
		this.sqrname = sqrname;
	}
	public String getBsqrname() {
		return bsqrname;
	}
	public void setBsqrname(String bsqrname) {
		this.bsqrname = bsqrname;
	}
	public List<LoginModel> getSqrlist() {
		return sqrlist;
	}
	public void setSqrlist(List<LoginModel> sqrlist) {
		this.sqrlist = sqrlist;
	}
	public List<LoginModel> getBsqrlist() {
		return bsqrlist;
	}
	public void setBsqrlist(List<LoginModel> bsqrlist) {
		this.bsqrlist = bsqrlist;
	}

	public List<DataPopedomModel> getCobaselist() {
		return cobaselist;
	}

	public void setCobaselist(List<DataPopedomModel> cobaselist) {
		this.cobaselist = cobaselist;
	}

	public String getCocid() {
		return cocid;
	}
	public void setCocid(String cocid) {
		this.cocid = cocid;
	}
	public String getCoshortname() {
		return coshortname;
	}
	public void setCoshortname(String coshortname) {
		this.coshortname = coshortname;
	}
}
