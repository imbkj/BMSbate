package Controller.CoFinanceManage;

import java.util.List;

import org.zkoss.zk.ui.Executions;

import Model.EmFinanceSalaryModel;
import Model.EmSalaryBaseAddItemModel;

public class Cfma_SalaryListController {
	@SuppressWarnings("unchecked")
	private final List<EmFinanceSalaryModel> salaryList = (List<EmFinanceSalaryModel>) Executions
			.getCurrent().getArg().get("salaryList");
	private List<EmSalaryBaseAddItemModel> itemList;

	public Cfma_SalaryListController() {
		if (salaryList.size() > 0) {
			itemList = salaryList.get(0).getEmsdModel().getItemList();
		}
	}

	public List<EmFinanceSalaryModel> getSalaryList() {
		return salaryList;
	}

	public List<EmSalaryBaseAddItemModel> getItemList() {
		return itemList;
	}

}
