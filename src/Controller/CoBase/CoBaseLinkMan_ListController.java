package Controller.CoBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import Model.CoAgencyLinkmanModel;
import bll.CoBase.CoBaseLinkMan_SelectBll;

;

public class CoBaseLinkMan_ListController {
	private final int cid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("cid").toString());
	private List<CoAgencyLinkmanModel> list;
	private CoBaseLinkMan_SelectBll bll;

	public CoBaseLinkMan_ListController() {
		bll = new CoBaseLinkMan_SelectBll();
		setList();
	}
	
	@Command("selLink")
	@NotifyChange("list")
	public void selLink(@BindingParam("cali_id") int cali_id){
		Map<String, String> map = new HashMap<String, String>();
		map.put("cali_id", String.valueOf(cali_id));
		Window window = (Window) Executions.createComponents(
				"CoBaseLinkMan_Sel.zul", null, map);
		window.doModal();
	}
	
	@Command("addLink")
	@NotifyChange("list")
	public void addLink(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("cid", String.valueOf(cid));
		Window window = (Window) Executions.createComponents(
				"CoBaseLinkMan_Add.zul", null, map);
		window.doModal();
		setList();
	}
	
	@Command("upLink")
	@NotifyChange("list")
	public void upLink(@BindingParam("cali_id") int cali_id){
		Map<String, String> map = new HashMap<String, String>();
		map.put("cali_id", String.valueOf(cali_id));
		Window window = (Window) Executions.createComponents(
				"CoBaseLinkMan_Update.zul", null, map);
		window.doModal();
		setList();
	}
	
	@Command("delLink")
	@NotifyChange("list")
	public void delLink(@BindingParam("cali_id") int cali_id){
		Map<String, String> map = new HashMap<String, String>();
		map.put("cali_id", String.valueOf(cali_id));
		map.put("cid", String.valueOf(cid));
		Window window = (Window) Executions.createComponents(
				"CoBaseLinkMan_Del.zul", null, map);
		window.doModal();
		setList();
	}

	public List<CoAgencyLinkmanModel> getList() {
		return list;
	}

	private void setList() {
		this.list = bll.getLinkmanByCid(cid);
	}

}
