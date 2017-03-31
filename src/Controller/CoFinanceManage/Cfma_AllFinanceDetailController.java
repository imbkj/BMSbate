package Controller.CoFinanceManage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import Model.CoFinanceMonthlyAccountModel;
import bll.CoFinanceManage.Cfma_SelBll;

public class Cfma_AllFinanceDetailController {
	private final int cid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("cid").toString());
	private List<CoFinanceMonthlyAccountModel> cfmaList;

	public Cfma_AllFinanceDetailController() {
		Cfma_SelBll bll = new Cfma_SelBll();
		cfmaList = bll.getMonthlyCompanyFinance(cid);
	}

	// 查看公司台账
	@Command("viewMonthlyCompany")
	public void viewMonthlyCompany(@BindingParam("ownmonth") int ownmonth) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("cid", cid);
		map.put("ownmonth", ownmonth);
		Window window = (Window) Executions.createComponents(
				"../CoFinanceManage/Cfma_MonthlyCompanyView.zul", null, map);
		window.doModal();
	}

	public List<CoFinanceMonthlyAccountModel> getCfmaList() {
		return cfmaList;
	}
}
