package Controller.CoHousingFund;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;

import Model.CoHousingFundChangeModel;
import bll.CoHousingFund.CoHousingFund_ListBll;

public class CoHousingFund_DocInfoController {
	Integer daid = 0;

	private CoHousingFundChangeModel m = new CoHousingFundChangeModel();

	public CoHousingFund_DocInfoController() {
		try {
			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());

			m = new CoHousingFund_ListBll().getCoHoChangeList(
					" and chfc_id=" + daid).get(0);

		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public final CoHousingFundChangeModel getM() {
		return m;
	}

	public final void setM(CoHousingFundChangeModel m) {
		this.m = m;
	}
}
