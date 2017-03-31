package Controller.EmCensus.EmDh;

import java.util.List;

import org.zkoss.zk.ui.Executions;

import bll.EmCensus.EmDh.EmDh_SelectBll;

import Model.EmArchiveRemarkModel;

public class emdh_remarkController {
	int emdh_id = (Integer) Executions.getCurrent().getArg().get("emdh_id");
	EmDh_SelectBll bll=new EmDh_SelectBll();
	List<EmArchiveRemarkModel> model=bll.getDhRemark(emdh_id);
	public List<EmArchiveRemarkModel> getModel() {
		return model;
	}
	public void setModel(List<EmArchiveRemarkModel> model) {
		this.model = model;
	}
	
	

}
