package Controller.SystemControl;

import impl.UserInfoImpl;
import impl.SystemControl.Data_PopedomIpml;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.CheckEvent;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Textbox;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Executions;

import dal.LoginDal;
import dal.SystemControl.Data_PopedomChangeDal;


import bll.SystemControl.Data_PopedomAuditingBll;
import bll.SystemControl.Data_PopedomChangeBll;


import service.DataPopedomService;
import service.UserInfoService;


import Model.CoPclassModel;
import Model.CoProductModel;
import Model.DataPopedomChangeModel;
import Model.DataPopedomModel;
import Model.LoginModel;
 

public class Data_PopedomEditController extends SelectorComposer<Component> {

	/**
	 * 
	 */
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
	private String coshortname="";


	//授权人list、被授权人list、以及客户list
	public List<LoginModel> sqrlist = new ArrayList<LoginModel>();
	public List<LoginModel> bsqrlist = new ArrayList<LoginModel>();
	public List<DataPopedomModel> cobaselist = new ArrayList<DataPopedomModel>();
	
	DataPopedomService d =new  Data_PopedomIpml("3","");
	DataPopedomModel datapchangem =new DataPopedomModel();
	Data_PopedomChangeBll datapchangebll = new Data_PopedomChangeBll();
	//session 获取
	Session session =  Executions.getCurrent().getDesktop().getSession();
	UserInfoService user = new UserInfoImpl(session);

	
	//构造函数 获取授权人和被授权人list
	public Data_PopedomEditController(){
		sqrlist=d.getLoginlist();
		//bsqrlist=d.getpidLoginlist();
		
		bsqrlist=d.getLoginlist();

	}

	 
	 @Command("selectall")
	 @NotifyChange({ "cobaselist" })
	 public void selectall(@BindingParam("ifchecked") Boolean checked){
		 
		 for (DataPopedomModel dM : cobaselist) {
				dM.setDat_selected(checked);
				//copM.setIfChecked(checked);
			}
		 
	 }

	 @Command("editAll")
	 @NotifyChange({ "cobaselist" })
	 public void editAll(@BindingParam("ifchecked") Boolean checked){
		 
		 for (DataPopedomModel dM : cobaselist) {
				dM.setDat_edited(checked);
				//copM.setIfChecked(checked);
			}
		 
	 }
	 
	 
	 @Command("deleAll")
	 @NotifyChange({ "cobaselist" })
	 public void deleAll(@BindingParam("ifchecked") Boolean checked){
		 
		 for (DataPopedomModel dM : cobaselist)
		 {
				dM.setDat_delete(checked);
				//copM.setIfChecked(checked);
			}
		 
	 }

		@Command("addDatep")
		 @NotifyChange({ "cobaselist" })
		 public void addDatep(@BindingParam("sqrcomstr") String sqrcomstr,@BindingParam("bsqrcomstr") String bsqrcom){
			 int i=0;
			 LoginDal d =new LoginDal();
			try{
				if(d.getUserIDByname(sqrcomstr)!=0 & 
						d.getUserIDByname(bsqrcom)!=0){
       
					 for (DataPopedomModel datapchangem : cobaselist) {
	
							 datapchangem.setDat_addname(user.getUsername().toString());
							 i=i+ datapchangebll.DataPopedomchangeAdd(datapchangem);
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
			
				cobaselist=datapchangebll.getcobaselist(sqrname,bsqrname,cocid,coshortname);
	
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
