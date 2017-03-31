package Controller.CoFinanceManage;

import java.util.List;

import org.zkoss.zk.ui.Executions;

import Model.EmFinanceDisposableModel;

public class Cfma_EmDisposableListController {
	@SuppressWarnings("unchecked")
	private final List<EmFinanceDisposableModel> emDisposableList = (List<EmFinanceDisposableModel>) Executions
			.getCurrent().getArg().get("emDisposableList");

	public Cfma_EmDisposableListController() {

	}

	public List<EmFinanceDisposableModel> getEmDisposableList() {
		return emDisposableList;
	}

}
