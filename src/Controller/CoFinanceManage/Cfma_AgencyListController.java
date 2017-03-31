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

import Model.CoFinanceAgencyModel;
import Util.CheckString;

public class Cfma_AgencyListController {
	private List<CoFinanceAgencyModel> agList;
	private List<CoFinanceAgencyModel> winAgList;

	public Cfma_AgencyListController() {
		Cfma_AgencySelBll bll = new Cfma_AgencySelBll();
		agList = winAgList = bll.getTotalAccount();
	}

	// 查看机构收款主页
	@Command("collectMain")
	public void collectMain(@BindingParam("cfat_id") int cfat_id) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("cfat_id", cfat_id);
		Window window = (Window) Executions.createComponents(
				"Cfma_AgencyMain.zul", null, map);
		window.doModal();
	}

	// 机构检索
	@Command("selAgency")
	@NotifyChange("winAgList")
	public void selAgency(@BindingParam("search") String search) {
		if ("".equals(search) || search == null) {
			winAgList = agList;
		} else {
			winAgList = searchAgency(search);
		}

	}

	// 检索机构信息
	private List<CoFinanceAgencyModel> searchAgency(String search) {
		List<CoFinanceAgencyModel> list = new ArrayList<CoFinanceAgencyModel>();
		for (CoFinanceAgencyModel m : agList) {
			if (CheckString.isNum(search)) {
				if (m.getCfat_coab_id() == Integer.parseInt(search)) {
					list.add(m);
					return list;
				} else
					return list;
			} else if (m.getCoab_name().contains(search)
					|| search.equals(m.getCity())
					|| search.equals(m.getProvince())) {
				list.add(m);
			}
		}
		return list;
	}

	public List<CoFinanceAgencyModel> getWinAgList() {
		return winAgList;
	}

}
