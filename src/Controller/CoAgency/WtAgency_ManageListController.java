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

import bll.CoAgency.BaseInfo_SelBll;

import Model.CoAgencyBaseModel;
import Util.CheckString;

public class WtAgency_ManageListController {
	private List<CoAgencyBaseModel> cabaList;
	private List<CoAgencyBaseModel> wincabaList;
	private List<String> citylist;

	public WtAgency_ManageListController() {
		BaseInfo_SelBll bll = new BaseInfo_SelBll();
		wincabaList = cabaList = bll.getWtAgencyList();
		citylist = bll.getCityName();
	}

	// 机构检索
	@Command("searchAgency")
	@NotifyChange("wincabaList")
	public void searchAgency(@BindingParam("city") String city,
			@BindingParam("agency") String agency) {
		try {
			boolean cb = !"".equals(city) && city != null ? true : false;
			boolean ab = !"".equals(agency) && agency != null ? true : false;
			if (ab || cb) {
				List<CoAgencyBaseModel> list = new ArrayList<CoAgencyBaseModel>();
				for (CoAgencyBaseModel m : cabaList) {
					if (cb && !city.equals(m.getCoab_city())) {
						continue;
					}
					if (ab) {
						if (CheckString.isNum(agency)) {
							if (m.getCabc_id() != Integer.parseInt(agency))
								continue;
						} else if (CheckString.isLetter(agency)) {
							try {
								if (m.getCoab_namespell().contains(agency)
										|| m.getCoab_name().contains(agency)) {

								} else {
									continue;
								}
							} catch (Exception e) {
								continue;
							}
						} else {
							try {
								if (m.getCoab_name().contains(agency)
										|| m.getCoas_client().contains(agency)) {

								} else {
									continue;
								}
							} catch (Exception e) {
								continue;
							}
						}
					}
					list.add(m);
				}
				wincabaList = list;
			} else {
				wincabaList = cabaList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 机构管理
	@Command("manage")
	public void manage(@BindingParam("cabc_id") int cabc_id) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("cabc_id", cabc_id);
		Window window = (Window) Executions.createComponents(
				"WtAgency_Manage.zul", null, map);
		window.doModal();
	}

	// 基本信息管理
	@Command("upBaseInfo")
	public void upBaseInfo(@BindingParam("coab_id") int coab_id) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("coab_id", coab_id);
		Window window = (Window) Executions.createComponents(
				"WtAgency_BaseInfoUpdate.zul", null, map);
		window.doModal();
	}

	// 联系人管理
	@Command("upLinkman")
	public void upLinkman(@BindingParam("coab_id") int coab_id,
			@BindingParam("coab_name") String coab_name) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("coab_id", String.valueOf(coab_id));
		map.put("cabl_type", "1");
		map.put("coab_name", coab_name);
		Window window = (Window) Executions.createComponents(
				"LinkMan_Manage.zul", null, map);
		window.doModal();
	}

	public List<CoAgencyBaseModel> getWincabaList() {
		return wincabaList;
	}

	// 弹出新增委托合同页面
	@Command
	public void addcompact(@BindingParam("model") CoAgencyBaseModel model) {
		Map<String, CoAgencyBaseModel> map = new HashMap<String, CoAgencyBaseModel>();
		map.put("model", model);
		Window window = (Window) Executions.createComponents(
				"/CoAgencies/CoAg_WtCompactAdd.zul", null, map);
		window.doModal();
	}

	public List<String> getCitylist() {
		return citylist;
	}

}
