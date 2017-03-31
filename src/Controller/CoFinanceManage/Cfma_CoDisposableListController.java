package Controller.CoFinanceManage;

import java.util.List;

import org.zkoss.zk.ui.Executions;

import Model.CoFinanceDisposableModel;

public class Cfma_CoDisposableListController {
	@SuppressWarnings("unchecked")
	private final List<CoFinanceDisposableModel> coDisposableList = (List<CoFinanceDisposableModel>) Executions
			.getCurrent().getArg().get("coDisposableList");

	public Cfma_CoDisposableListController() {

	}

	public List<CoFinanceDisposableModel> getCoDisposableList() {
		return coDisposableList;
	}

}
