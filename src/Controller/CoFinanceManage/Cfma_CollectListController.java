package Controller.CoFinanceManage;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import bll.CoFinanceManage.Cfma_SelBll;

import Model.CoFinanceTotalAccountModel;
import Util.CheckString;
import Util.RegexUtil;

public class Cfma_CollectListController {
	private List<CoFinanceTotalAccountModel> totalAccountList;
	private List<CoFinanceTotalAccountModel> winCompanyList;

	public Cfma_CollectListController() {
		Cfma_SelBll bll = new Cfma_SelBll();
		totalAccountList = bll.getTotalAccount("");
		winCompanyList = totalAccountList;
	}

	// 查看公司收款主页
	@Command("collectMain")
	public void collectMain(@BindingParam("cid") int cid) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("cid", cid);
		Window window = (Window) Executions.createComponents(
				"Cfma_CollectMain.zul", null, map);
		window.doModal();
	}

	// 公司检索
	@Command("selCompany")
	@NotifyChange("winCompanyList")
	public void selCompany(@BindingParam("company") String company) {
		if ("".equals(company) || company == null) {
			winCompanyList = totalAccountList;
		} else {
			winCompanyList = searchCompany(company);
		}

	}

	// 检索公司信息
	private List<CoFinanceTotalAccountModel> searchCompany(String company) {
		List<CoFinanceTotalAccountModel> list = new LinkedList<CoFinanceTotalAccountModel>();
		if (CheckString.isNum(company)) {
			for (CoFinanceTotalAccountModel m : totalAccountList) {
				if (m.getCid() == Integer.parseInt(company)) {
					list.add(m);
					break;
				}
			}
		} else {
			for (CoFinanceTotalAccountModel m : totalAccountList) {
				if (RegexUtil.isExists(company.toUpperCase(), m.getCoba_company().toUpperCase())) {
					list.add(m);
				}
			}
		}
		return list;
	}

	public List<CoFinanceTotalAccountModel> getWinCompanyList() {
		return winCompanyList;
	}

}
