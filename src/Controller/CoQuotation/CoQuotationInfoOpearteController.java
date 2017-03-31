package Controller.CoQuotation;

import java.util.List;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;

import Model.CoOfferListModel;
import bll.CoQuotation.CoQuotationInfoBll;

public class CoQuotationInfoOpearteController {
	
	private List<CoOfferListModel> coofferinfoList;
	private int coof_id = Integer.parseInt(Executions.getCurrent().getArg()
			.get("coofid").toString());

	@Init
	public void init() throws Exception {
		CoQuotationInfoBll bll = new CoQuotationInfoBll();
		setCoofferinfoList(new ListModelList<CoOfferListModel>(
				bll.getCoOfferlistList(coof_id)));
	}

	public List<CoOfferListModel> getCoofferinfoList() {
		return coofferinfoList;
	}

	public void setCoofferinfoList(List<CoOfferListModel> coofferinfoList) {
		this.coofferinfoList = coofferinfoList;
	}
}
