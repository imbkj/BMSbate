package Controller.CoAgency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Window;

import Model.CoAgencyBaseCityRelModel;
import bll.CoAgency.WtAgency_DisCitySelBll;

public class WtAgency_CityDisListController {
	private List<String> regionlist;
	private List<String> provincelist;
	private List<String> citylist;
	private List<CoAgencyBaseCityRelModel> disCityList;
	private List<CoAgencyBaseCityRelModel> winDisCityList;
	private WtAgency_DisCitySelBll bll = new WtAgency_DisCitySelBll();

	public WtAgency_CityDisListController() throws Exception {
		regionlist = bll.getRegionName();
		provincelist = bll.getProvinceName();
		citylist = bll.getCityName();
		winDisCityList = disCityList = bll.getCoAgencyBaseCityRelList();
	}

	// 根据地区查询省份
	@Command("selProvince")
	@NotifyChange({ "provincelist", "citylist" })
	public void selProvince(@BindingParam("contact") String region,
			@BindingParam("com") Combobox province) throws Exception {
		province.setValue(null);
		provincelist = bll.searchProvinceName(region);
		citylist = bll.getCityName();
	}

	// 根据省份查询城市
	@Command("selCity")
	@NotifyChange("citylist")
	public void selCity(@BindingParam("contact") String province,
			@BindingParam("com") Combobox city) throws Exception {
		city.setValue(null);
		citylist = bll.searchCityName(province);
	}

	// 数据查询
	@Command("search")
	public void search(@BindingParam("region") String region,
			@BindingParam("province") String province,
			@BindingParam("city") String city) throws Exception {
		winDisCityList = new ArrayList<CoAgencyBaseCityRelModel>();
		if (city != null && !"".equals(city)) {
			for (CoAgencyBaseCityRelModel m : disCityList) {
				if (m.getCity().equals(city))
					winDisCityList.add(m);
			}
		} else if (province != null && !"".equals(province)) {
			for (CoAgencyBaseCityRelModel m : disCityList) {
				if (m.getProvince().equals(province))
					winDisCityList.add(m);
			}
		} else if (region != null && !"".equals(region)) {
			for (CoAgencyBaseCityRelModel m : disCityList) {
				if (m.getRegion().equals(region))
					winDisCityList.add(m);
			}
		} else {
			winDisCityList = disCityList;
		}
		BindUtils.postNotifyChange(null, null, this, "winDisCityList");
	}

	// 分配城市
	@Command("disCity")
	public void disCity(@BindingParam("city") String city,
			@ContextParam(ContextType.VIEW) Component view) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("city", city);
		Window window = (Window) Executions.createComponents(
				"WtAgency_DisOperate.zul", view, map);
		window.doModal();
	}

	// 刷新列表
	@Command("refreshWin")
	@NotifyChange("winDisCityList")
	public void refreshWin() {
		winDisCityList = disCityList = bll.getCoAgencyBaseCityRelList();
	}

	public List<String> getRegionlist() {
		return regionlist;
	}

	public List<String> getProvincelist() {
		return provincelist;
	}

	public List<String> getCitylist() {
		return citylist;
	}

	public List<CoAgencyBaseCityRelModel> getWinDisCityList() {
		return winDisCityList;
	}

}
