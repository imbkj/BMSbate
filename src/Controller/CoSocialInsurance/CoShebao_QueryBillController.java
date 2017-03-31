package Controller.CoSocialInsurance;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

import Model.CoShebaoPayAmountModel;
import Util.RegexUtil;
import bll.CoSocialInsurance.CoShebao_QueryBillsBll;

public class CoShebao_QueryBillController {

	private List<CoShebaoPayAmountModel> QueryBillsList;
	private CoShebao_QueryBillsBll cqb = new CoShebao_QueryBillsBll();
	private List<CoShebaoPayAmountModel> sQueryBillsList = new ArrayList<CoShebaoPayAmountModel>();
	private String isAccount = "";
	private String StringMonth = "";
	private Date month;

	/**
	 * 构造器
	 */
	public CoShebao_QueryBillController() {
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
