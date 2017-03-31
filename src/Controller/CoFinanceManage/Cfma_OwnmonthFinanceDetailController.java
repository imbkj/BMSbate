package Controller.CoFinanceManage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import bll.CoFinanceManage.Cfma_SelBll;

import Model.CoFinanceMonthlyBillModel;

public class Cfma_OwnmonthFinanceDetailController {
	private final int cfta_id = Integer.parseInt(Executions.getCurrent()
			.getArg().get("cfta_id").toString());
	private final int ownmonth = Integer.parseInt(Executions.getCurrent()
			.getArg().get("ownmonth").toString());

	private List<CoFinanceMonthlyBillModel> mbList;

	public Cfma_OwnmonthFinanceDetailController() {
		Cfma_SelBll bll = new Cfma_SelBll();
		mbList = bll.getMonthlyBill(cfta_id, ownmonth);
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
