package Controller.EmSalary;

import java.util.List;

import org.zkoss.zk.ui.Executions;

import Model.EmSalaryBaseAddItemModel;

public class EmSalary_ConfirmDetailController {
	@SuppressWarnings("unchecked")
	private final List<EmSalaryBaseAddItemModel> itemList = (List<EmSalaryBaseAddItemModel>) Executions
			.getCurrent().getArg().get("item");

	public List<EmSalaryBaseAddItemModel> getItemList() {
		return itemList;
	}
	
}
