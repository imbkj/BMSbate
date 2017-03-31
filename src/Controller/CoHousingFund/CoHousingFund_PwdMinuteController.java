package Controller.CoHousingFund;

import org.zkoss.zk.ui.Executions;

import Model.CoHousingFundPwdChangeModel;


public class CoHousingFund_PwdMinuteController {

	private CoHousingFundPwdChangeModel cfpm = (CoHousingFundPwdChangeModel) Executions
			.getCurrent().getArg().get("cfpm");

	public CoHousingFundPwdChangeModel getCfpm() {
		return cfpm;
	}

	public void setCfpm(CoHousingFundPwdChangeModel cfpm) {
		this.cfpm = cfpm;
	}
	
}
