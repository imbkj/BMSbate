package Controller.CoFinanceManage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import Model.CoFinanceMonthlyAccountModel;
import Model.CoFinanceMonthlyBillModel;
import Model.CoFinanceMonthlyBillSortAccountModel;
import bll.CoFinanceManage.Cfma_SelBll;

public class Cfma_MonthlyCompanyViewController {
	private final int cid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("cid").toString());
	private final int ownmonth = Integer.parseInt(Executions.getCurrent()
			.getArg().get("ownmonth").toString());
	private Cfma_SelBll bll;
	private CoFinanceMonthlyAccountModel macModel;
	private List<CoFinanceMonthlyBillSortAccountModel> sortList;
	private List<CoFinanceMonthlyBillModel> billList;
	private String listSrc;

	public Cfma_MonthlyCompanyViewController() {
		bll = new Cfma_SelBll();
		macModel = bll.getMonthlyCompanyView(cid, ownmonth);
		sortList = bll.getMonthlySortAccount(cid, ownmonth);
		billList = bll.getMonthlyBill(macModel.getCfma_cfta_id(), ownmonth);
		listSrc = "../CoFinanceManage/Cfma_BusinessAllList.zul?cid=" + cid + "&ownmonth="
				+ ownmonth;
	}

	// 查看账单
	@Command("viewBill")
	public void viewBill(@BindingParam("billNo") String billNo) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("billNo", billNo);
		Window window = (Window) Executions.createComponents(
				"../CoFinanceManage/Cfma_MonthlyBillView.zul", null, map);
		window.doModal();
	}

	public CoFinanceMonthlyAccountModel getMacModel() {
		return macModel;
	}

	public List<CoFinanceMonthlyBillSortAccountModel> getSortList() {
		return sortList;
	}

	public List<CoFinanceMonthlyBillModel> getBillList() {
		return billList;
	}

	public String getListSrc() {
		return listSrc;
	}

}
