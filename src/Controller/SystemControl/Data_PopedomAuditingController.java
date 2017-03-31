package Controller.SystemControl;

import impl.UserInfoImpl;
import impl.SystemControl.Data_PopedomIpml;

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
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;

import service.DataPopedomService;
import service.UserInfoService;
import Model.DataPopedomChangeModel;
import Model.LoginModel;
import bll.SystemControl.Data_PopedomAuditingBll;

public class Data_PopedomAuditingController  extends SelectorComposer<Component>  {

	
	private static final long serialVersionUID = 1L;

	@Wire
	private Grid gridwin;
	@Wire
	private Checkbox selectAll;   //全选查询选项框
	@Wire
	private Checkbox editAll;	  //全选修改选项框
	@Wire
	private Checkbox deleAll;		//全选删除选项框
	@Wire
	private Combobox sqrcom;       //授权人
	@Wire
	private Combobox bsqrcom;		//被授权人
	
	private String  sqrname="";
	private String  bsqrname="";
	private String cocid="";
	private String coshortname="";
	private String addname="";


	
	public String getAddname() {
		return addname;
	}

	public void setAddname(String addname) {
		this.addname = addname;
	}
	//授权人list、被授权人list、以及客户list
	public List<LoginModel> sqrlist = new ArrayList<LoginModel>();
	public List<LoginModel> bsqrlist = new ArrayList<LoginModel>();
	public List<DataPopedomChangeModel> cobaselist = new ArrayList<DataPopedomChangeModel>();
	
	DataPopedomService d =new  Data_PopedomIpml("3","");
	DataPopedomChangeModel datapchangem =new DataPopedomChangeModel();
	Data_PopedomAuditingBll datapchangebll = new Data_PopedomAuditingBll();
	//session 获取
	Session session =  Executions.getCurrent().getDesktop().getSession();
	UserInfoService user = new UserInfoImpl(session);
	
	//构造函数 获取授权人和被授权人list
	public Data_PopedomAuditingController() {
		sqrlist=d.getLoginlist();
		//bsqrlist=d.getpidLoginlist();
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
	 //提交审核事件 
		@Listen("onClick = #autidingDatep")
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
					      //实例一个model
					      datapchangem.setLog_id(((LoginModel) bsqrcom.getSelectedItem().getValue()).getLog_id());
				    	  datapchangem.setCid(Integer.parseInt(((Label)comp.getChildren().get(1)).getValue()));
				    	  datapchangem.setDat_selected(selectid);
				    	  datapchangem.setDat_edited(editid);
				    	  datapchangem.setDat_delete(deleteid);
				    	  datapchangem.setDat_addname(user.getUsername().toString());
				    	  //执行审核的存储过程
				    	  i=i+ datapchangebll.DataPopedomchangeauditing(datapchangem);
				     
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

	@Command
	@NotifyChange("cobaselist")
		 public void search() throws SQLException {
		if(!sqrname.isEmpty() || !bsqrname.isEmpty())
		{
			try{
			//监听查询事件
				cobaselist=datapchangebll.getcobaselist(sqrname,bsqrname,cocid,coshortname,addname);
	
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
		else
		{
			Messagebox.show("请先选择授权人和被授权人！", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
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
	public List<DataPopedomChangeModel> getCobaselist() {
		return cobaselist;
	}
	public void setCobaselist(List<DataPopedomChangeModel> cobaselist) {
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
