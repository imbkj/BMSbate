package Controller.CoFinanceManage;

import java.util.List;

import org.zkoss.zk.ui.Executions;

import Model.EmFinanceTaxModel;

public class Cfma_TaxListController {
	@SuppressWarnings("unchecked")
	private final List<EmFinanceTaxModel> taxList = (List<EmFinanceTaxModel>) Executions
			.getCurrent().getArg().get("taxList");

	public Cfma_TaxListController() {

	}

	public List<EmFinanceTaxModel> getTaxList() {
		return taxList;
	}

}
