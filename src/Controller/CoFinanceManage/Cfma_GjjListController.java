package Controller.CoFinanceManage;

import java.util.List;

import org.zkoss.zk.ui.Executions;

import Model.EmFinanceHouseGjjModel;

public class Cfma_GjjListController {
	private final List<EmFinanceHouseGjjModel> gjjList = (List<EmFinanceHouseGjjModel>) Executions
			.getCurrent().getArg().get("gjjList");

	public Cfma_GjjListController() {

	}

	public List<EmFinanceHouseGjjModel> getGjjList() {
		return gjjList;
	}

}
