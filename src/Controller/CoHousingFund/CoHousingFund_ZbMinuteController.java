package Controller.CoHousingFund;

import org.zkoss.zk.ui.Executions;

import bll.CoHousingFund.CoHousingFundZbBll;

import Model.CoHousingFundModel;
import Model.CoHousingFundZbModel;
import Util.SingleBllFactory;

/**
 * 专办员详情
 * 
 * @author Administrator
 * 
 */
public class CoHousingFund_ZbMinuteController {

	private CoHousingFundModel chfm = (CoHousingFundModel) Executions
			.getCurrent().getArg().get("chfm");
	private CoHousingFundZbBll cfzb = SingleBllFactory.getInstance().getChzb();

	public CoHousingFund_ZbMinuteController() {
//		if (chfm.getCfzc_addtype().equals("修改专办员信息")) {
//			CoHousingFundZbModel m = cfzb.getZb(chfm.getChfz_id());
//			chfm.setCfzc_name(m.getChfz_name());
//			chfm.setCfzc_number(m.getChfz_number());
//		}
	}

	public CoHousingFundModel getChfm() {
		return chfm;
	}

	public void setChfm(CoHousingFundModel chfm) {
		this.chfm = chfm;
	}

}
