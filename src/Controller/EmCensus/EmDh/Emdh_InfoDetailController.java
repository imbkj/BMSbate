package Controller.EmCensus.EmDh;

import java.util.List;

import org.zkoss.zk.ui.Executions;

import bll.EmCensus.EmDh.EmDh_SelectBll;

import Model.EmArchiveRemarkModel;
import Model.EmDhModel;

public class Emdh_InfoDetailController {
	EmDhModel dhmodel = (EmDhModel)Executions.getCurrent().getArg().get("model");
	EmDh_SelectBll bll=new EmDh_SelectBll();
	List<EmArchiveRemarkModel> model=bll.getDhRemark(dhmodel.getId());
	public List<EmArchiveRemarkModel> getModel() {
		return model;
	}
	public void setModel(List<EmArchiveRemarkModel> model) {
		this.model = model;
	}
}
