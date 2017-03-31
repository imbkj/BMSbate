package Controller.CoFinanceManage;

import java.util.List;

import org.zkoss.zk.ui.Executions;

import Model.EmFinanceValueAddTaxModel;

public class Cfma_ValueAddTaxController {

	@SuppressWarnings("unchecked")
	private final List<EmFinanceValueAddTaxModel> vatList = (List<EmFinanceValueAddTaxModel>) Executions
			.getCurrent().getArg().get("vatList");

	public Cfma_ValueAddTaxController() {

	}

	public List<EmFinanceValueAddTaxModel> getVatList() {
		return vatList;
	}

}
