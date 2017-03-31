package Controller.CoLatencyClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import Model.CoAgencyLinkmanModel;
import Model.CoCompactModel;
import Model.MenuListModel;
import bll.CoLatencyClient.CoLatencyClient_AddBll;

public class ColaClientLinkManList_Controller {
	int cola_id = (Integer) Executions.getCurrent().getArg().get("cola_id");
	CoLatencyClient_AddBll bll=new CoLatencyClient_AddBll();
	List<CoAgencyLinkmanModel> linkmodel=bll.getLinkmanForAg(cola_id);
	public List<CoAgencyLinkmanModel> getLinkmodel() {
		return linkmodel;
	}
	
	List<CoCompactModel> compactmodel=bll.getCompact(cola_id);
	public List<CoCompactModel> getCompactmodel() {
		return compactmodel;
	}
	
	//弹出更新联系人信息页面
	@Command
	@NotifyChange("linkmodel")
	public void linkUpdate(@BindingParam("linkn") final CoAgencyLinkmanModel model)
	{
		Map map=new HashMap();
		map.put("linkmodel",model);
		Window window = (Window)Executions.createComponents("ColaClientLinkManUpdate.zul",null, map);
		window.doModal();
		linkmodel=bll.getLinkmanForAg(cola_id);
	}
	
	//弹出联系人详细信息页面
	@Command
	public void linkinfo(@BindingParam("linkn") final CoAgencyLinkmanModel model)
	{
		Map map=new HashMap();
		map.put("linkmodel",model);
		Window window = (Window)Executions.createComponents("ColaClientLinkManInfo.zul",null, map);
		window.doModal();
	}
}
