package Controller.CoFinanceManage;

import java.util.List;

import org.zkoss.zk.ui.Executions;

import Model.EmFinanceSheBaoModel;

public class Cfma_SheBaoListController {
	private final List<EmFinanceSheBaoModel> shebaoList = (List<EmFinanceSheBaoModel>) Executions
			.getCurrent().getArg().get("shebaoList");

	public Cfma_SheBaoListController() {
		
	}

	public List<EmFinanceSheBaoModel> getShebaoList() {
		return shebaoList;
	}

}
