package Controller.CoSocialInsurance;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import Model.CoHousingFundPwdChangeModel;
import Model.CoShebaoPayAmountModel;
import Util.RegexUtil;
import bll.CoSocialInsurance.CoShebao_QueryBillsBll;

public class CoShebao_QueryBillsController {

	private List<CoShebaoPayAmountModel> QueryBillsList;
	private CoShebao_QueryBillsBll cqb = new CoShebao_QueryBillsBll();
	private List<CoShebaoPayAmountModel> sQueryBillsList = new ArrayList<CoShebaoPayAmountModel>();
	private String isAccount = "";
	private String StringMonth = "";
	private Date month;

	/**
	 * 构造器
	 */
	public CoShebao_QueryBillsController() {
		QueryBillsList = cqb.getQueryBillsList();
		search(null);
	}

	@Command
	@NotifyChange("sQueryBillsList")
	public void search(@BindingParam("own") Date month) {

		if (QueryBillsList != null && QueryBillsList.size() > 0) {
			sQueryBillsList.clear();
			for (CoShebaoPayAmountModel m : QueryBillsList) {
				if (isAccount != null) {
					if (!RegexUtil.isExists(isAccount, m.getIsaccount())) {
						continue;
					}
				}
				if (month != null) {
					this.month = month;
					StringMonth = new SimpleDateFormat("yyyyMM").format(month);
				}
				if (StringMonth != null) {
					if (!RegexUtil.isExists(StringMonth, m.getOwnmonth())) {
						continue;
					}
				}
				sQueryBillsList.add(m);
			}
		}
	}

	/**
	 * 添加缴交信息
	 */
	@Command
	@NotifyChange("sQueryBillsList")
	public void addPayInfo(@BindingParam("m") CoShebaoPayAmountModel m) {
		Map map = new HashMap<>();
		map.put("m", m);
		Window window = (Window) Executions.createComponents(
				"../CoSocialInsurance/CoShebao_AddPayInfo.zul", null, map);
		window.doModal();
		search(this.month);
	}

	
	public String getIsAccount() {
		return isAccount;
	}

	public void setIsAccount(String isAccount) {
		this.isAccount = isAccount;
	}

	public List<CoShebaoPayAmountModel> getsQueryBillsList() {
		return sQueryBillsList;
	}

	public void setsQueryBillsList(List<CoShebaoPayAmountModel> sQueryBillsList) {
		this.sQueryBillsList = sQueryBillsList;
	}

}
