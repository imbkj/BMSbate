package Controller.EmBenefit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import bll.EmBenefit.EmActy_compactAuditBll;
import Model.EmActyCompactModel;


public class EmActy_compactAuditController {

	private List<EmActyCompactModel> compactList = new ListModelList<>();
	private List<EmActyCompactModel> addnameList = new ListModelList<>();
	
	private EmActy_compactAuditBll bll = new EmActy_compactAuditBll();
	private Window win = (Window) Path.getComponent("/wincompactAudit");
	
	public EmActy_compactAuditController() {
		EmActyCompactModel eacm = new EmActyCompactModel();
		eacm.setEaco_state(3);
		setCompactList(eacm,true);
		setAddnameList("");
		
	}
	
	@Command("search")
	@NotifyChange("compactList")
	public void search(){
		Textbox tbName = (Textbox) win.getFellow("company");
		Textbox tbCompactId = (Textbox) win.getFellow("compactid");
		Combobox cbName = (Combobox) win.getFellow("addname");
		Combobox cbState = (Combobox) win.getFellow("compactState");
		EmActyCompactModel eacm = new EmActyCompactModel();
		
		if (tbName.getValue() != null && !tbName.getValue().equals("")) {
			eacm.setEaco_name(tbName.getValue());
		}
		if (tbCompactId.getValue() != null
				&& !tbCompactId.getValue().equals("")) {
			eacm.setEaco_compactid(tbCompactId.getValue());
		}
		if (cbName.getSelectedItem() != null
				&& !cbName.getSelectedItem().getLabel().equals("")) {
			eacm.setEaco_addname(cbName.getSelectedItem().getLabel());
		}
		if (cbState.getSelectedItem() != null
				&& !cbState.getSelectedItem().getLabel().equals("")) {
			eacm.setEaco_state(Integer.valueOf(cbState.getSelectedItem().getValue().toString()));
		}
		setCompactList(eacm,true);
	}
	
	@Command("audit")
	@NotifyChange("compactList")
	public void audit(@BindingParam("a") EmActyCompactModel eacm){
		
		Map map = new HashMap();
		map.put("id", eacm.getEaco_tapr_id());
		map.put("daid", eacm.getEaco_id());
		map.put("look", eacm.getEaco_state());
		map.put("audit", "1");
		Window window = (Window) Executions.createComponents(
				"../EmBenefit/EmActy_compactPrint.zul", null, map);
		window.doModal();
	}
	
	@Command
	@NotifyChange("compactList")
	public void back(@BindingParam("a") EmActyCompactModel eacm){
		
	}

	public List<EmActyCompactModel> getCompactList() {
		return compactList;
	}

	public void setCompactList(EmActyCompactModel eacm,Boolean desc) {
		this.compactList = bll.getCompactList(eacm,desc);
	}

	public List<EmActyCompactModel> getAddnameList() {
		return addnameList;
	}

	public void setAddnameList(String name) {
		this.addnameList = bll.getaddNameList(name);
	}
	
	

}
