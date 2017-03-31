package Controller.CoHousingFund;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.zk.ui.Executions;

import Model.CoHousingFundModel;
import Model.CoHousingFundPwdModel;
import Model.CoHousingFundZbModel;
import bll.CoHousingFund.CoHousingFundZbBll;

public class CoHousingFund_InfoController {
	private Integer id = Integer.valueOf(Executions.getCurrent().getArg().get("daid").toString());
	private Integer cid = Integer.valueOf(Executions.getCurrent().getArg().get("cid").toString());
	private CoHousingFundModel chfm;
	private List<CoHousingFundZbModel> chfzList = null;
	private List<CoHousingFundPwdModel> chfpList = null;

	private CoHousingFundZbBll cfzb = new CoHousingFundZbBll();


	/**
	 * 加载信息
	 */
	public CoHousingFund_InfoController() {
		
		// 单位缴存信息
		chfm = cfzb.getChfzListById(cid);
		// 专办员信息
		chfzList = cfzb.getZbById(id);
		chfpList = cfzb.getPwdById(id);
		// 密钥信息
		for (int i = 0; i < chfpList.size(); i++) {
			for (int j = 0; j < chfzList.size(); j++) {
				if (chfpList.get(i).getChfp_chfz_id() == chfzList.get(j)
						.getChfz_id()) {
					chfzList.get(j).setFlag(true);
				}
			}
		}
	}

	public List<CoHousingFundZbModel> getChfzList() {
		return chfzList;
	}

	public void setChfzList(List<CoHousingFundZbModel> chfzList) {
		this.chfzList = chfzList;
	}

	public List<CoHousingFundPwdModel> getChfpList() {
		return chfpList;
	}

	public void setChfpList(List<CoHousingFundPwdModel> chfpList) {
		this.chfpList = chfpList;
	}

	public CoHousingFundModel getChfm() {
		return chfm;
	}

	public void setChfm(CoHousingFundModel chfm) {
		this.chfm = chfm;
	}

}
