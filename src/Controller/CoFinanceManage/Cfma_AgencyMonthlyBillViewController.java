package Controller.CoFinanceManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import bll.CoFinanceManage.Cfma_AgencySelBll;

import Model.CoFinanceAgencyMonthlyBillModel;
import Model.CoFinanceMonthlyBillModel;
import Util.RegexUtil;

public class Cfma_AgencyMonthlyBillViewController {
	private final String cfab_number = Executions.getCurrent().getArg()
			.get("cfab_number").toString();
	private CoFinanceAgencyMonthlyBillModel billModel;
	private List<CoFinanceMonthlyBillModel> billList;
	private List<CoFinanceMonthlyBillModel> winbillList;
	private String billNo;

	public Cfma_AgencyMonthlyBillViewController() {
		Cfma_AgencySelBll selBll = new Cfma_AgencySelBll();
		billModel = selBll.getAgencyMonthlyBillModel(cfab_number);
		winbillList = billList = selBll.getCoFinanceMonthlyBillList(
				billModel.getCfab_coab_id(), billModel.getOwnmonth());
	}

	// 账单查询
	@Command("selBill")
	@NotifyChange("winbillList")
	public void selBill() {
		if ("".equals(billNo) || billNo == null)
			winbillList = billList;
		else
			searchBill();
	}

	// 账单检索
	private void searchBill() {
		List<CoFinanceMonthlyBillModel> list = new ArrayList<CoFinanceMonthlyBillModel>();
		for (CoFinanceMonthlyBillModel m : billList) {
			if (RegexUtil.isExists(billNo, m.getCfmb_number())) {
				list.add(m);
			}
		}
		winbillList = list;
	}

	// 查看公司账单
	@Command("viewBill")
	public void viewBill(@BindingParam("billNo") String billNo) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("billNo", billNo);
		Window window = (Window) Executions.createComponents(
				"Cfma_MonthlyBillView.zul", null, map);
		window.doModal();
	}

	public CoFinanceAgencyMonthlyBillModel getBillModel() {
		return billModel;
	}

	public List<CoFinanceMonthlyBillModel> getWinbillList() {
		return winbillList;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

}
