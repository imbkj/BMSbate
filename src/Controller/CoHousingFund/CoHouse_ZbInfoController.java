package Controller.CoHousingFund;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.zk.ui.Executions;

import Model.CoHousingFundModel;
import Model.CoHousingFundPwdModel;
import Model.CoHousingFundZbModel;
import Util.SingleBllFactory;
import bll.CoHousingFund.CoHousingFundZbBll;

public class CoHouse_ZbInfoController {

	private CoHousingFundModel chfm = null;
	private List<CoHousingFundZbModel> chfzList = null;
	private List<CoHousingFundPwdModel> chfpList = null;
	private int cid;
	private int cohf_id;

	private CoHousingFundZbBll cfzb = SingleBllFactory.getInstance().getChzb();
	private Map<String, Integer> map = (Map<String, Integer>) Executions
			.getCurrent().getArg();
	Set<String> keySet = map.keySet();
	Iterator it = keySet.iterator();

	/**
	 * 加载信息
	 */
	public CoHouse_ZbInfoController() {
		String key = null;
		while (it.hasNext()) {
			key = (String) it.next();
			if (key.equals("cid")) {
				cid = map.get(key);
			} else if (key.equals("cohf_id")) {
				cohf_id = map.get(key);
			}
		}

		// 单位缴存信息
		chfm = cfzb.getChfzListById(cid);
		// 专办员信息
		chfzList = cfzb.getZbById(cohf_id);
		chfpList = cfzb.getPwdById(cohf_id);
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
