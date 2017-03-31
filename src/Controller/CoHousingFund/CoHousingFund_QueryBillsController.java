package Controller.CoHousingFund;

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

import Model.CoHousingFundPayAmountModel;
import Util.RegexUtil;
import bll.CoHousingFund.CoHousingFund_QueryBillsBll;

public class CoHousingFund_QueryBillsController {

	private CoHousingFund_QueryBillsBll bll = new CoHousingFund_QueryBillsBll();

	private String isAccount = "";
	private String isPayment = "";
	private String StringMonth = "";
	private Date month;
	private List<CoHousingFundPayAmountModel> queryBillsList;
	private List<CoHousingFundPayAmountModel> squeryBillsList = new ArrayList<CoHousingFundPayAmountModel>();

	public CoHousingFund_QueryBillsController() {
		queryBillsList = bll.getQueryBillsList();
		search(null);
	}

	@Command
	@NotifyChange("squeryBillsList")
	public void search(@BindingParam("own") Date own) {

		if (queryBillsList != null && queryBillsList.size() > 0) {
			squeryBillsList.clear();
			for (CoHousingFundPayAmountModel m : queryBillsList) {

				if (isAccount != null) {
					if (!RegexUtil.isExists(isAccount, m.getCqbc_isaccount())) {
						continue;
					}
				}

				if (isPayment != null) {
					if (!RegexUtil.isExists(isPayment, m.getCofp_isaccount())) {
						continue;
					}
				}
				if (own != null) {
					this.month = own;
					StringMonth = new SimpleDateFormat("yyyyMM").format(own);
				}
				if (StringMonth != null) {

					if (!RegexUtil.isExists(StringMonth, m.getOwnmonth())) {
						continue;
					}
				}
				squeryBillsList.add(m);
			}
		}
	}

	/**
	 * 补缴查询详情
	 * 
	 * @param m
	 */
	@Command
	public void paymentMinute(@BindingParam("m") CoHousingFundPayAmountModel m) {
		Map map = new HashMap<>();
		map.put("m", m);
		Window window = (Window) Executions.createComponents(
				"../CoHousingFund/CoHousingFund_PaymentBillsList.zul", null,
				map);
		window.doModal();
	}

	/**
	 * 补缴信息
	 */
	@Command
	@NotifyChange("squeryBillsList")
	public void paymentInfo(@BindingParam("m") CoHousingFundPayAmountModel m) {
		Map map = new HashMap<>();
		map.put("m", m);
		Window window = (Window) Executions.createComponents(
				"../CoHousingFund/CoHousingFund_PaymentInfo.zul", null, map);
		window.doModal();
		search(this.month);
	}

	/**
	 * 缴交信息
	 */
	@Command
	@NotifyChange("squeryBillsList")
	public void addPayInfo(@BindingParam("m") CoHousingFundPayAmountModel m) {
		Map map = new HashMap<>();
		map.put("m", m);
		Window window = (Window) Executions.createComponents(
				"../CoHousingFund/CoHousingFund_AddPayInfo.zul", null, map);
		window.doModal();
		search(this.month);
	}

	public List<CoHousingFundPayAmountModel> getSqueryBillsList() {
		return squeryBillsList;
	}

	public void setSqueryBillsList(
			List<CoHousingFundPayAmountModel> squeryBillsList) {
		this.squeryBillsList = squeryBillsList;
	}

	public String getIsAccount() {
		return isAccount;
	}

	public Date getMonth() {
		return month;
	}

	public void setMonth(Date month) {
		this.month = month;
	}

	public void setIsAccount(String isAccount) {
		this.isAccount = isAccount;
	}

	public String getIsPayment() {
		return isPayment;
	}

	public void setIsPayment(String isPayment) {
		this.isPayment = isPayment;
	}

}
