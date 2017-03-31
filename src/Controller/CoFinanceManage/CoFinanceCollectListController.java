package Controller.CoFinanceManage;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import bll.CoFinanceManage.Cfma_SelBll;

import Model.CoFinanceMonthlyBillModel;
import Model.CoFinanceTotalAccountModel;
import Util.SingleBllFactory;

public class CoFinanceCollectListController {

	int cid = (Integer) Executions.getCurrent().getArg().get("cid");
	private Cfma_SelBll csb = SingleBllFactory.getInstance().getCsb();
	private List<CoFinanceMonthlyBillModel> cfcList;
	private String ownmonth;
	private Cfma_SelBll bll;
	private CoFinanceTotalAccountModel totalModel;
	private List<CoFinanceMonthlyBillModel> billList;

	public CoFinanceCollectListController() {

		bll = new Cfma_SelBll();
		totalModel = bll.getCompanyTotalAccount(cid);
		billList = bll.getCompanyAllBill(totalModel.getCfta_id());
	}

	// 查看账单
	@Command("viewBill")
	public void viewBill(@BindingParam("billNo") String billNo) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("billNo", billNo);
		Window window = (Window) Executions.createComponents(
				"Cfma_MonthlyBillView.zul", null, map);
		window.doModal();

	}

//	@Command
//	public void check(@BindingParam("m") CoFinanceMonthlyBillModel m) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("cid", cid);
//		map.put("cfmb_number", m.getCfmb_number());
//		BindUtils.postGlobalCommand(null, null, "reflush", map);
//	}

	public List<CoFinanceMonthlyBillModel> getBillList() {
		return billList;
	}

	public void setBillList(List<CoFinanceMonthlyBillModel> billList) {
		this.billList = billList;
	}

	public List<CoFinanceMonthlyBillModel> getCfcList() {
		return cfcList;
	}

	public void setCfcList(List<CoFinanceMonthlyBillModel> cfcList) {
		this.cfcList = cfcList;
	}

}
