package Controller.CoHousingFund;

import java.util.List;

import org.zkoss.zk.ui.Executions;

import bll.CoHousingFund.CoHousingFund_QueryBillsBll;

import Model.CoHousingFundPaymentModel;

public class CoHousingFund_BillsListController {
	int cfpaid = (Integer) Executions.getCurrent().getArg().get("cfpaid");

	private CoHousingFund_QueryBillsBll bll = new CoHousingFund_QueryBillsBll();
	private List<CoHousingFundPaymentModel> billsList;

	public CoHousingFund_BillsListController() {
		if (cfpaid != 0) {
			billsList = bll.getBillsList(cfpaid);
		}
	}

	
	public List<CoHousingFundPaymentModel> getBillsList() {
		return billsList;
	}

	public void setBillsList(List<CoHousingFundPaymentModel> billsList) {
		this.billsList = billsList;
	}
	
}
