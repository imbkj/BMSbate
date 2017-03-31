package Controller.CoHousingFund;

import java.util.List;

import org.zkoss.zk.ui.Executions;

import Model.CoHousingFundPayAmountModel;
import Model.CoHousingFundPaymentModel;
import bll.CoHousingFund.CoHousingFund_QueryBillsBll;

public class CoHousingFund_PaymentBillsListController {

	private CoHousingFundPayAmountModel m = (CoHousingFundPayAmountModel) Executions
			.getCurrent().getArg().get("m");

	private CoHousingFund_QueryBillsBll bll = new CoHousingFund_QueryBillsBll();
	private List<CoHousingFundPaymentModel> billsList;

	public CoHousingFund_PaymentBillsListController() {
		if (m.getCfpa_id() != 0) {
			billsList = bll.getPaymentBillsList(m.getCfpa_id());
		}
	}

	public List<CoHousingFundPaymentModel> getBillsList() {
		return billsList;
	}

	public void setBillsList(List<CoHousingFundPaymentModel> billsList) {
		this.billsList = billsList;
	}
}
