/*
 * 创建人：林少斌
 * 创建时间：2013-9-22
 * 用途：委托机构基本修改查询页面Controller
 */

package Controller.CoAgency;

import impl.UserInfoImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Window;

import service.UserInfoService;


import Model.CoAgencyBaseModel;
import bll.CoAgency.CoAgencyBaseListBll;


public class BaseInfo_UpdateListController{

	private String coab_province;
	private String coab_city;
	private String coab_name;
	private List coagBaseList;	//机构信息
	private List agProvinceList;	//省份
	private List agCityList;	//城市
	private CoAgencyBaseListBll cablBll = new CoAgencyBaseListBll();
	//获取用户名
	Session session =  Executions.getCurrent().getDesktop().getSession();
	UserInfoService user = new UserInfoImpl(session);
	String username = user.getUsername();
	
	public BaseInfo_UpdateListController(){
		//首页加载显示所有数据
		coagBaseList = cablBll.getCoAgencyBaseAll();
		//查询中省份下拉框
		agProvinceList = cablBll.getAgProvince();
	}
	
	//根据省份查询城市
	@Command("selAgCity")
	@NotifyChange("agCityList")
	public void selAgCity(@BindingParam("contact") String province,@BindingParam("cb") Combobox cb){
		coab_city="";
		cb.setValue("");
		setAgCityList(cablBll.getAgCity(province));
	}
	
	//查询委托机构基本信息
	@Command("search")
	@NotifyChange("coagBaseList")
	public void search() {
		coagBaseList = cablBll.searchCoAgencyBase(coab_province, coab_city, coab_name);
	}
	
	//弹出修改基本信息页面
	@Command("update")
	@NotifyChange("coagBaseList")
	public void update(@BindingParam("coabM") CoAgencyBaseModel coabM){
		//专递Model
		Map coabMap=new HashMap();
		coabMap.put("coabM",coabM);
		Window window = (Window)Executions.createComponents("BaseInfo_Update.zul",null, coabMap);
		window.doModal();
		coagBaseList = cablBll.getCoAgencyBaseAll();
	}
	
	//弹出新增联系人页面
	@Command("openLinkMan_Add")
	public void openLinkMan_Add(@BindingParam("coab_id") int coab_id){
		//专递参数coab_id
		Map map = new HashMap();
		map.put("coab_id",coab_id);
		Window window = (Window)Executions.createComponents("LinkMan_Add.zul",null, map);
	    window.doModal();
	    
	}
		
	public String getCoab_province() {
		return coab_province;
	}

	public void setCoab_province(String coab_province) {
		this.coab_province = coab_province;
	}

	public String getCoab_city() {
		return coab_city;
	}

	public void setCoab_city(String coab_city) {
		this.coab_city = coab_city;
	}

	public String getCoab_name() {
		return coab_name;
	}

	public void setCoab_name(String coab_name) {
		this.coab_name = coab_name;
	}

	public List<CoAgencyBaseListBll> getCoagBaseList() {
		return coagBaseList;
	}
	public void setCoagBaseList(List<CoAgencyBaseListBll> coagBaseList) {
		this.coagBaseList = coagBaseList;
	}

	public List getAgProvinceList() {
		return agProvinceList;
	}

	public void setAgProvinceList(List agProvinceList) {
		this.agProvinceList = agProvinceList;
	}

	public List getAgCityList() {
		return agCityList;
	}

	public void setAgCityList(List agCityList) {
		this.agCityList = agCityList;
	}
}
