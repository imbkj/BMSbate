package Controller.CoFinanceManage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import Model.CoFinanceMonthlyBillModel;
import bll.CoFinanceManage.Cfma_SelBll;

public class Cfma_AllBillListController {
	private final String where = Executions.getCurrent().getArg().get("where")
			.toString();
	private Cfma_SelBll bll;
	private List<CoFinanceMonthlyBillModel> mbList;

	public Cfma_AllBillListController() {
		bll = new Cfma_SelBll();
		mbList = bll.getAllBill(where);
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

	public List<CoFinanceMonthlyBillModel> getMbList() {
		return mbList;
	}

}
