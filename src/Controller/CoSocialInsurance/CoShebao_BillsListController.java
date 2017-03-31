package Controller.CoSocialInsurance;

import java.util.List;

import org.zkoss.zk.ui.Executions;

import Model.CoShebaoQueryBillsModel;
import bll.CoSocialInsurance.CoShebao_QueryBillsBll;

public class CoShebao_BillsListController {

	int cspaid = (Integer) Executions.getCurrent().getArg().get("cspaid");

	private CoShebao_QueryBillsBll cqb = new CoShebao_QueryBillsBll();
	private List<CoShebaoQueryBillsModel> cqbl;

	public CoShebao_BillsListController() {
		if (cspaid != 0) {
			cqbl = cqb.getQueryBillsList(cspaid);
		}
	}

	public List<CoShebaoQueryBillsModel> getCqbl() {
		return cqbl;
	}

	public void setCqbl(List<CoShebaoQueryBillsModel> cqbl) {
		this.cqbl = cqbl;
	}



}
