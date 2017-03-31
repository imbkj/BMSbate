package Controller.ClientRelations.VisitInfo;

import impl.SystemControl.Data_PopedomIpml;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import service.DataPopedomService;

import Model.LoginModel;
import Model.VisitInfoModel;
import bll.ClientRelations.VisitInfo.VisitInfoBll;


public class VisitInfo_selectController  extends SelectorComposer<Component>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String  cola_company="";   //潜在客户名称
	private String  viin_class="";     //拜访方式
	private String viin_person="";     //拜访人
	private String viin_month="";      //拜访计划月
	private String  viin_addname="";   //计划添加人
	private String  viin_state="";   //计划添加人
	
	
	
	
	public String getViin_state() {
		return viin_state;
	}

	public void setViin_state(String viin_state) {
		this.viin_state = viin_state;
	}

	public List<VisitInfoModel> visitInfolist = new ArrayList<VisitInfoModel>();
	VisitInfoBll visitinfob = new VisitInfoBll();
	
	DataPopedomService d =new  Data_PopedomIpml("7","","","","","","");
	public List<LoginModel> visipersonlist = new ArrayList<LoginModel>();
	

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

	public VisitInfo_selectController()
	{
		visipersonlist=d.getdepLoginlist();
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
				
				visitInfolist=visitinfob.getlist(viin_person, viin_month, cola_company, "", viin_addname,1);
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			}
			else
			{
				 Messagebox.show("请先选择拜访人!", "ERROR", Messagebox.OK, Messagebox.ERROR);
			}

	 	}
	
	//详情弹窗
	@Command("detail")
	@NotifyChange("visitInfolist")
	public void detail(@BindingParam("model") VisitInfoModel model) throws Exception{
		Map map = new HashMap();
		//map.put("vim", model);
		
		map.put("id",model.getViin_tapr_id());
		map.put("daid",model.getViin_id());
		
		Window window = (Window) Executions.createComponents(
				"/ClientRelations/VisitInfo/visitinfo_detail.zul", null, map);
		window.doModal();
		search();
	}
	
}
