package Controller.CoBase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoBaseSocialSecurityModel;
import Model.MaintenanceRecordModel;
import bll.CoBase.CoBaseSocial_SecurityBll;
import bll.MainRecord.MaintenanceRecordBll;

/**
 * 
 * @author suhongyuan
 * @create 2016-11-10
 */
public class CoBaseSocial_SecurityController {
	 private CoBaseSocialSecurityModel mm = new CoBaseSocialSecurityModel();
	 private List<CoBaseSocialSecurityModel> coBaseSocialList =new ArrayList<CoBaseSocialSecurityModel>();
	 private CoBaseSocial_SecurityBll bll= new CoBaseSocial_SecurityBll();
	 private List<String> listcoba_assistant=null;//客服列表
	 private String cid="";//公司编号
	 private String coba_company="";//所属公司
	 private String coba_client="";//客服
	 private String coba_assistant="";//员工
	 
	public CoBaseSocial_SecurityController() {
		try {
			listcoba_assistant=bll.getCoba_assistantList();//客服列表
		} catch (SQLException e) {
			e.printStackTrace();
		}
		serch();
	}
	//按条件查询当前用户提交的已审核维护记录
	public void serch(){
		StringBuilder strsql= new StringBuilder();
		if(!cid.equals("")){
			strsql.append(" and co.CID = '"+cid+"'" );
		}
		if(!coba_company.equals("")){
			strsql.append(" and co.coba_company like '%"+coba_company+"%'" );
		}
		if(!coba_client.equals("")){
			strsql.append(" and co.coba_client ='"+coba_client+"'" );
		}
		if(!coba_assistant.equals("")){
			strsql.append(" and co.coba_assistant like '%"+coba_assistant+"%'" );
		}
		strsql.append(" ");
			try {
				coBaseSocialList = bll.getCoBaseSocial_SecurityList(strsql.toString());//查询
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	 //查询按钮事件
	 @Command("findAuditCore")
	 @NotifyChange("coBaseSocialList")
	 public void findAuditCore(){
	 		serch();
	 }
	//修改结果
	@Command("editAssistant")
	public void maintain(@ContextParam(ContextType.VIEW) Component view,@BindingParam("m") CoBaseSocialSecurityModel m){
	    
		Map<String, CoBaseSocialSecurityModel> map = new HashMap<String, CoBaseSocialSecurityModel>();
		map.put("model", m);
		Window window = (Window) Executions.createComponents(
				"/CoBase/CoBaseSocial_SecurityUpdate.zul",view, map);
		window.doModal();
	  }
	 //子页面刷新
	 @Command("refreshAll")
     @NotifyChange("coBaseSocialList")
	 public void refreshParentWindow(){
		 serch();
	 }
	 
	public CoBaseSocialSecurityModel getMm() {
		return mm;
	}
	public void setMm(CoBaseSocialSecurityModel mm) {
		this.mm = mm;
	}
	public List<CoBaseSocialSecurityModel> getCoBaseSocialList() {
		return coBaseSocialList;
	}
	public void setCoBaseSocialList(List<CoBaseSocialSecurityModel> coBaseSocialList) {
		this.coBaseSocialList = coBaseSocialList;
	}
	
	
	public List<String> getListcoba_assistant() {
		return listcoba_assistant;
	}
	public void setListcoba_assistant(List<String> listcoba_assistant) {
		this.listcoba_assistant = listcoba_assistant;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCoba_company() {
		return coba_company;
	}
	public void setCoba_company(String coba_company) {
		this.coba_company = coba_company;
	}
	public String getCoba_client() {
		return coba_client;
	}
	public void setCoba_client(String coba_client) {
		this.coba_client = coba_client;
	}
	public String getCoba_assistant() {
		return coba_assistant;
	}
	public void setCoba_assistant(String coba_assistant) {
		this.coba_assistant = coba_assistant;
	}
	
	
	 
}
