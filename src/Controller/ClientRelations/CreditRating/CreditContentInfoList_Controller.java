package Controller.ClientRelations.CreditRating;

import java.util.List;

import org.zkoss.zk.ui.Executions;

import bll.CoLatencyClient.CoLatencyClient_AddBll;

import Model.CreditContentInfoModel;

public class CreditContentInfoList_Controller {
	CoLatencyClient_AddBll bll=new CoLatencyClient_AddBll();
	int id = (Integer) Executions.getCurrent().getArg().get("crcr_id");
	List<CreditContentInfoModel> commodel=bll.getCreditContentInfo(id);
	public List<CreditContentInfoModel> getCommodel() {
		return commodel;
	}
	public void setCommodel(List<CreditContentInfoModel> commodel) {
		this.commodel = commodel;
	}
	
}
