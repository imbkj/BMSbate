package Controller.Embase;

import java.util.List;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import bll.Embase.EmbaseNotinListBll;
import Model.EmbaseNotInModel;
import bll.Embase.EmbaseNotinListBll;
 

public class EmbaseNotin_ListController {
	
	private String strwhere="";
	public List<EmbaseNotInModel> embasenotinlist = new ArrayList<EmbaseNotInModel>();
	private EmbaseNotinListBll embanotinbll =new EmbaseNotinListBll();

	public  EmbaseNotin_ListController()
	{
		embasenotinlist=embanotinbll.searchembaselist(strwhere);
	}


	//查询客服数据权限列表
	@Command
	@NotifyChange("embasenotinlist")
	 public void search() throws SQLException {
		//System.out.print(log_name);
		try{
		  if(strwhere.indexOf("|")!=-1){
			  
			  embasenotinlist=embanotinbll.searchembaselist(strwhere.split("\\|")[1]);
			  setStrwhere(strwhere.split("\\|")[1]);
		  }
		  else
		  {
			  embasenotinlist=embanotinbll.searchembaselist(strwhere);
		  }
		
		  
		} 
		catch (Exception e) {
			System.out.println(e.toString());
		}
 	}
	
	@Command
	@NotifyChange("embasenotinlist")
	 public void addem(@BindingParam("id") int id,@BindingParam("cid") int cid,@BindingParam("embaid") Integer embaid) throws SQLException {
		try{
			if (embaid==0)
						
			{
				embaid=null;
			}
			Map emMap =new HashMap();
			emMap.put("embanotin_id", id);
			emMap.put("cid", cid);
			emMap.put("embaId", embaid);
			Window window = (Window) Executions.createComponents("../EmBase/EmBase_Add.zul", null, emMap);
			window.doModal();
			search();
			
		}catch(Exception e)
		{
			
		}
		
	}
	
	
	@Command
	@NotifyChange("embasenotinlist")
	 public void onboard(@BindingParam("emid") int id) throws SQLException {
		try{
			Map emMap =new HashMap();
			emMap.put("daid", id);
			System.out.print(id);
			
			Window window = (Window) Executions.createComponents("Embase_OnBoard.zul", null, emMap);
			window.doModal();
			search();
			
		}catch(Exception e)
		{
			
		}
		
	}


	
	
	public String getStrwhere() {
		return strwhere;
	}

	public void setStrwhere(String strwhere) {
		this.strwhere = strwhere;
	}


	public List<EmbaseNotInModel> getEmbasenotinlist() {
		return embasenotinlist;
	}


	public void setEmbasenotinlist(List<EmbaseNotInModel> embasenotinlist) {
		this.embasenotinlist = embasenotinlist;
	}
	
	

}
