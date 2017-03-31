package Controller.CoAgency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import Model.CoAgencyBaseModel;
import Util.CheckString;
import bll.CoAgency.BaseInfo_SelBll;

public class StAgency_ManageController {
	private List<CoAgencyBaseModel> cabaList;
	private List<CoAgencyBaseModel> wincabaList;

	public StAgency_ManageController() {
		BaseInfo_SelBll bll = new BaseInfo_SelBll();
		wincabaList = cabaList = bll.getStAgencyList();
	}

	// 机构检索
	@Command("searchAgency")
	@NotifyChange("wincabaList")
	public void searchAgency(@BindingParam("agency") String agency) {
		if ("".equals(agency) || agency == null) {
			wincabaList = cabaList;
		} else {
			wincabaList = searchCompany(agency);
		}

	}

	// 检索公司信息
	private List<CoAgencyBaseModel> searchCompany(String agency) {
		List<CoAgencyBaseModel> list = new ArrayList<CoAgencyBaseModel>();
		if (CheckString.isNum(agency)) {
			for (CoAgencyBaseModel m : cabaList) {
				if (m.getCoab_id() == Integer.parseInt(agency)) {
					list.add(m);
					return list;
				}
			}
		} else if (CheckString.isLetter(agency)) {
			for (CoAgencyBaseModel m : cabaList) {
				try {
					if (m.getCoab_namespell().contains(agency)
							|| m.getCoab_name().contains(agency)) {
						list.add(m);
					}
				} catch (Exception e) {

				}
			}
		} else {
			for (CoAgencyBaseModel m : cabaList) {
				try {
					if (m.getCoab_name().contains(agency)
							|| m.getCabsModel().getCoas_client()
									.contains(agency)) {
						list.add(m);
					}
				} catch (Exception e) {

				}
			}
		}
		return list;
	}

	// 基本信息管理
	@Command("upBaseInfo")
	public void upBaseInfo(@BindingParam("coab_id") int coab_id) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("coab_id", coab_id);
		Window window = (Window) Executions.createComponents(
				"StAgency_BaseInfoUpdate.zul", null, map);
		window.doModal();
	}

	// 联系人管理
	@Command("upLinkman")
	public void upLinkman(@BindingParam("coab_id") int coab_id,
			@BindingParam("coab_name") String coab_name) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("coab_id", String.valueOf(coab_id));
		map.put("cabl_type", "2");
		map.put("coab_name", coab_name);
		Window window = (Window) Executions.createComponents(
				"LinkMan_Manage.zul", null, map);
		window.doModal();
	}

	// 弹出新增受托合同页面
	@Command
	public void addcompact(@BindingParam("model") CoAgencyBaseModel model) {
		Map<String, CoAgencyBaseModel> map = new HashMap<String, CoAgencyBaseModel>();
		map.put("model", model);
		Window window = (Window) Executions.createComponents(
				"/CoAgencies/CoAg_StCompactAdd.zul", null, map);
		window.doModal();
	}

	public List<CoAgencyBaseModel> getWincabaList() {
		return wincabaList;
	}

}
