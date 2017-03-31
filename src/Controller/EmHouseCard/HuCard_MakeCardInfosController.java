package Controller.EmHouseCard;

import org.zkoss.zk.ui.Executions;

import Model.EmHouseMakeCardInfoModel;

public class HuCard_MakeCardInfosController {
	private EmHouseMakeCardInfoModel model = (EmHouseMakeCardInfoModel)Executions.getCurrent().getArg().get("model");

	public EmHouseMakeCardInfoModel getModel() {
		return model;
	}

	public void setModel(EmHouseMakeCardInfoModel model) {
		this.model = model;
	}

	
	

	

}
