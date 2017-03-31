package Controller.CoFinanceManage;

import java.util.List;

import org.zkoss.zk.ui.Executions;

import Model.CoFinanceCollectModel;
import Model.CoFinanceMonthlyBillSortAccountModel;
import Util.SingleBllFactory;
import bll.CoFinanceManage.Cfma_SelBll;

public class CoFinanceMonthlyBillListController {
	String cfmb_number = (String) Executions.getCurrent().getArg()
			.get("cfmb_number");
	private Cfma_SelBll csb = SingleBllFactory.getInstance().getCsb();
	private List<CoFinanceMonthlyBillSortAccountModel> cfmbList;

	
	
	public CoFinanceMonthlyBillListController() {
		cfmbList = csb.getCfmbList(cfmb_number);
	}

	public List<CoFinanceMonthlyBillSortAccountModel> getCfmbList() {
		return cfmbList;
	}

	public void setCfmbList(List<CoFinanceMonthlyBillSortAccountModel> cfmbList) {
		this.cfmbList = cfmbList;
	}

}
