/*
 * 创建人：林少斌
 * 创建时间：2013-9-25
 * 用途：委托机构详细信息查询按地区显示Controller
 */
package Controller.CoAgency;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Window;

import bll.CoAgency.BaseInfo_CityDisBll;

public class BaseInfo_CitySelController{
	private List<String> regionlist;
	private List<String> provincelist;
	private List<String> citylist;
	private List<String[]> gridlist;
	private BaseInfo_CityDisBll bll = new BaseInfo_CityDisBll();

	public BaseInfo_CitySelController() throws Exception {
		regionlist = bll.getRegionName();
		provincelist = bll.getProvinceName();
		citylist = bll.getCityName();
		gridlist = bll.getCityBaseCount();
	}

	// 根据地区查询省份
	@Command("selProvince")
	@NotifyChange({ "provincelist", "citylist" })
	public void selProvince(@BindingParam("contact") String region,
			@BindingParam("com") Combobox province) throws Exception {
		province.setValue(null);
		setProvincelist(bll.searchProvinceName(region));
		setCitylist(bll.getCityName());
	}

	// 根据省份查询城市
	@Command("selCity")
	@NotifyChange("citylist")
	public void selCity(@BindingParam("contact") String province,
			@BindingParam("com") Combobox city) throws Exception {
		city.setValue(null);
		setCitylist(bll.searchCityName(province));
	}

	// 数据查询
	@Command("search")
	@NotifyChange("gridlist")
	public void search(@BindingParam("region") String region,
			@BindingParam("province") String province,
			@BindingParam("city") String city) throws Exception {
		setGridlist(bll.getCityBaseCount(region, province, city));
	}
	
	//打开新增服务政策页面
	@Command
	public void openzul(@BindingParam("model") String[] model)
	{
		Map map=new HashMap<>();
		map.put("type", "城市政策");
		map.put("name", model[2]);
		Window window=(Window)Executions.createComponents("/CoServicePolicy/SePy_CityPolicyAdd.zul", null, map);
		window.doModal();
	}

	public List<String> getRegionlist() {
		return regionlist;
	}

	public void setRegionlist(List<String> regionlist) {
		this.regionlist = regionlist;
	}

	public List<String> getProvincelist() {
		return provincelist;
	}

	public void setProvincelist(List<String> provincelist) {
		this.provincelist = provincelist;
	}

	public List<String> getCitylist() {
		return citylist;
	}

	public void setCitylist(List<String> citylist) {
		this.citylist = citylist;
	}

	public List<String[]> getGridlist() {
		return gridlist;
	}

	public void setGridlist(List<String[]> gridlist) {
		this.gridlist = gridlist;
	}
}
