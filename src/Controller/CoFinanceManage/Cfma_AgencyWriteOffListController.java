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

import bll.CoFinanceManage.Cfma_AgencyWriteOffSelBll;

import Model.CoAgencyBaseModel;
import Util.CheckString;

public class Cfma_AgencyWriteOffListController {
	private List<CoAgencyBaseModel> agList;
	private List<CoAgencyBaseModel> winAgList;

	public Cfma_AgencyWriteOffListController() {
		Cfma_AgencyWriteOffSelBll bll = new Cfma_AgencyWriteOffSelBll();
		agList = winAgList = bll.getAgencyList();
	}

	// 打开机构冲销操作页面
	@Command("WriteOffMain")
	public void collectMain(@BindingParam("coab_id") int coab_id) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("coab_id", coab_id);
		Window window = (Window) Executions.createComponents(
				"Cfma_AgencyWriteOff.zul", null, map);
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
	private List<CoAgencyBaseModel> searchAgency(String search) {
		List<CoAgencyBaseModel> list = new ArrayList<CoAgencyBaseModel>();
		for (CoAgencyBaseModel m : agList) {
			if (CheckString.isNum(search)) {
				if (m.getCoab_id() == Integer.parseInt(search)) {
					list.add(m);
					return list;
				} else
					return list;
			} else if (m.getCoab_name().contains(search)
					|| search.equals(m.getCoab_city())
					|| search.equals(m.getCoab_province())) {
				list.add(m);
			}
		}
		return list;
	}

	public List<CoAgencyBaseModel> getWinAgList() {
		return winAgList;
	}
}
