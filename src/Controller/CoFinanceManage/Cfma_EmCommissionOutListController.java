package Controller.CoFinanceManage;

import org.zkoss.zk.ui.Executions;

import Model.EmFinanceCommissionOutCityModel;

public class Cfma_EmCommissionOutListController {
	private final EmFinanceCommissionOutCityModel emCommissionOutModel = (EmFinanceCommissionOutCityModel) Executions
			.getCurrent().getArg().get("emCommissionOutModel");
	private String gridWidth;

	public Cfma_EmCommissionOutListController() {
		int width = 400 + emCommissionOutModel.getDetailItemList().size() * 200
				+ emCommissionOutModel.getProductItemList().size() * 100;
		
		gridWidth = width + "px";
	}

	public EmFinanceCommissionOutCityModel getEmCommissionOutModel() {
		return emCommissionOutModel;
	}

	public String getGridWidth() {
		return gridWidth;
	}

}
