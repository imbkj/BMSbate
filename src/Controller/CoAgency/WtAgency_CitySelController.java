package Controller.CoAgency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Window;

import Model.CoAgencyBaseCityRelModel;
import bll.CoAgency.WtAgency_DisCitySelBll;

public class WtAgency_CitySelController {
	private List<String> regionlist;
	private List<String> provincelist;
	private List<String> citylist;
	private List<CoAgencyBaseCityRelModel> hzCityList;
	private List<CoAgencyBaseCityRelModel> winHzCityList;
	private WtAgency_DisCitySelBll bll = new WtAgency_DisCitySelBll();

	public WtAgency_CitySelController() throws Exception {
		regionlist = bll.getRegionName();
		provincelist = bll.getProvinceName();
		citylist = bll.getCityName();
		winHzCityList = hzCityList = bll.getCoAgencyBaseCityRelList();
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
	@NotifyChange("winHzCityList")
	public void search(@BindingParam("region") String region,
			@BindingParam("province") String province,
			@BindingParam("city") String city) throws Exception {
		winHzCityList = new ArrayList<CoAgencyBaseCityRelModel>();
		if (city != null && !"".equals(city)) {
			for (CoAgencyBaseCityRelModel m : hzCityList) {
				if (m.getCity().equals(city))
					winHzCityList.add(m);
			}
		} else if (province != null && !"".equals(province)) {
			for (CoAgencyBaseCityRelModel m : hzCityList) {
				if (m.getProvince().equals(province))
					winHzCityList.add(m);
			}
		} else if (region != null && !"".equals(region)) {
			for (CoAgencyBaseCityRelModel m : hzCityList) {
				if (m.getRegion().equals(region))
					winHzCityList.add(m);
			}
		} else {
			winHzCityList = hzCityList;
		}
		BindUtils.postNotifyChange(null, null, this, "winHzCityList");
	}
	
	//打开新增政府服务政策页面
	@Command
	public void openzul(@BindingParam("model") CoAgencyBaseCityRelModel model)
	{
		Map map=new HashMap();
		map.put("model", model);
		map.put("type", "政府政策");
		map.put("classname", model.getCity());
		Window window=(Window)Executions.createComponents("/CoServicePolicy/SePy_CityPolicyInfoAdd.zul", null, map);
		window.doModal();
	}
	
	//打开新增政府服务政策页面
	@Command
	public void openzulnote(@BindingParam("model") CoAgencyBaseCityRelModel model)
	{
		Map map=new HashMap();
		map.put("model", model);
		map.put("type", "政府通知");
		map.put("classname", model.getCity());
		Window window=(Window)Executions.createComponents("/CoPolicyNotice/PoNo_InfoAdd.zul", null, map);
		window.doModal();
	}

	public List<CoAgencyBaseCityRelModel> getwinHzCityList() {
		return winHzCityList;
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

}
