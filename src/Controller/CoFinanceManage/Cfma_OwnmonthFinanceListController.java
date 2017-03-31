package Controller.CoFinanceManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Window;
import bll.CoFinanceManage.Cfma_SelBll;

import Model.CoFinanceMonthlyAccountModel;

public class Cfma_OwnmonthFinanceListController {
	private final String where = Executions.getCurrent().getArg().get("where")
			.toString();
	private List<CoFinanceMonthlyAccountModel> cfmaList;
	private Cfma_SelBll bll;
	List<String> message;

	public Cfma_OwnmonthFinanceListController() {
		bll = new Cfma_SelBll();
		cfmaList = bll.getMonthlyAccount(where);
	}

	// 提示
	@Command("suggest")
	public void suggest(@BindingParam("noBill") boolean noBill,
			@BindingParam("personnelConfirm") int personnelConfirm,
			@BindingParam("financeNoConfirm") int financeNoConfirm,
			@BindingParam("pop") Popup pop, @BindingParam("com") Component com) {
		message = new ArrayList<String>();
		if (noBill) {
			message.add("本月有未加账单的业务。");
		}
		if (personnelConfirm == 1) {
			message.add("本月有账单的人事应收未确认。");
		}
		if (financeNoConfirm == 1) {
			message.add("本月有账单的财务应收未确认。");
		}
		BindUtils.postNotifyChange(null, null, this, "message");
		pop.open(com);
	}

	// 账单管理
	@Command("mothlyBillManage")
	public void mothlyBillManage(@BindingParam("cfma_id") int cfma_id) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("cfma_id", cfma_id);
		Window window = (Window) Executions.createComponents(
				"Cfma_MonthlyBillManage.zul", null, map);
		window.doModal();
	}

	// 查看公司台账
	@Command("viewMonthlyCompany")
	public void viewMonthlyCompany(@BindingParam("cid") int cid,
			@BindingParam("ownmonth") int ownmonth) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("cid", cid);
		map.put("ownmonth", ownmonth);
		Window window = (Window) Executions.createComponents(
				"Cfma_MonthlyCompanyView.zul", null, map);
		window.doModal();
	}

	public List<CoFinanceMonthlyAccountModel> getCfmaList() {
		return cfmaList;
	}

	public List<String> getMessage() {
		return message;
	}

}
