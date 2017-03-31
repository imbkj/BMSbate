package Controller.CoFinanceManage;

import java.util.List;

import org.zkoss.zk.ui.Executions;

import Model.CoFinanceTotalAccountModel;
import bll.CoFinanceManage.Cfma_SelBll;

public class Cfma_AllFinanceListController {
	private final String where = Executions.getCurrent().getArg().get("where")
			.toString();

	private List<CoFinanceTotalAccountModel> totalAccountList;

	public Cfma_AllFinanceListController() {
		Cfma_SelBll bll = new Cfma_SelBll();
		totalAccountList = bll.getTotalAccount(where);
	}

	public List<CoFinanceTotalAccountModel> getTotalAccountList() {
		return totalAccountList;
	}

}
