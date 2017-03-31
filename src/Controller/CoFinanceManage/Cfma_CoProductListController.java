package Controller.CoFinanceManage;

import java.util.List;

import org.zkoss.zk.ui.Executions;

import Model.CoFinanceProductModel;

public class Cfma_CoProductListController {
	private final List<CoFinanceProductModel> coProductList = (List<CoFinanceProductModel>) Executions
			.getCurrent().getArg().get("coProductList");

	public Cfma_CoProductListController() {
		
	}

	public List<CoFinanceProductModel> getCoProductList() {
		return coProductList;
	}

}
