package Controller.CoFinanceManage;

import java.util.List;

import org.zkoss.zk.ui.Executions;

import Model.EmFinanceProductModel;
import Model.EmFinanceProductItemListModel;

public class Cfma_EmProductListController {
	private final List<EmFinanceProductModel> emProductList = (List<EmFinanceProductModel>) Executions
			.getCurrent().getArg().get("emProductList");
	private List<EmFinanceProductItemListModel> itemList;

	public Cfma_EmProductListController() {
		if (emProductList.size() > 0) {
			itemList = emProductList.get(0).getItemList();
		}
	}

	public List<EmFinanceProductModel> getEmProductList() {
		return emProductList;
	}

	public List<EmFinanceProductItemListModel> getItemList() {
		return itemList;
	}

}
