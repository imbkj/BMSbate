package Controller.SocialInsurance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

import bll.SocialInsurance.Algorithm_ListBll;
import Model.CoAgencyBaseCityRelViewModel;
import Model.SocialInsuranceAlgorithmViewModel;
import Model.SocialInsuranceChangeModel;

public class Algorithm_EditListController {
	private Algorithm_ListBll bll;
	private List<CoAgencyBaseCityRelViewModel> baseCityList;
	private List<CoAgencyBaseCityRelViewModel> mainList;
	private List<String> cityList;
	private List<String> proList;
	private List<String> baseList;
	private List<String> maincityList;
	private List<String> mainbaseList;
	private List<String> setuptypeList;
	private List<SocialInsuranceAlgorithmViewModel> hisSiList;
	private List<SocialInsuranceAlgorithmViewModel> nowSiList;
	private int cabc_id;
	private String province;
	private String city;

	private String agname;
	private String setuptype;

	// 记录所选的城市与机构（用于判断是否为本地的算法）
	private String selCity;
	private String selCoabName;

	private boolean changeVis;
	private List<SocialInsuranceChangeModel> changeList;

	public Algorithm_EditListController() {
		bll = new Algorithm_ListBll();
		setBaseCityList();
		setMainList(getBaseCityList());
		setCityList();
		setMaincityList(getCityList());
		setProList();
		setBaseList();
		setMainbaseList(getBaseList());
		setSetuptypeList();
		changeVis = false;
	}

	// 检索
	@Command("search")
	@NotifyChange("mainList")
	public void search(@BindingParam("agid") Intbox agid) {
		int id;
		if (agid.getValue() == null)
			id = 0;
		else
			id = agid.getValue();
		setMainList(getList(id));
	}

	// 检索省份
	@Command("searchProvince")
	@NotifyChange({ "mainList", "maincityList", "mainbaseList", "city",
			"agname" })
	public void searchProvince(@BindingParam("agid") Intbox agid) {
		int id;
		if (agid.getValue() == null)
			id = 0;
		else
			id = agid.getValue();

		if (province == null || "".equals(province)) {
			setMaincityList(getCityList());
			setMainbaseList(getBaseList());
		} else {
			city = "";
			agname = "";
			setMaincityList(searchCityByPro(province));
			setMainbaseList(searchBaseByPro(province));
		}
		setMainList(getList(id));
	}

	// 检索城市
	@Command("searchCity")
	@NotifyChange({ "mainList", "mainbaseList", "agname" })
	public void searchCity(@BindingParam("agid") Intbox agid) {
		int id;
		if (agid.getValue() == null)
			id = 0;
		else
			id = agid.getValue();

		if (city == null || "".equals(city)) {
			setMainbaseList(getBaseList());
		} else {
			agname = "";
			setMainbaseList(searchBaseByCity(city));
		}
		setMainList(getList(id));
	}

	// 检索列表数据
	private List<CoAgencyBaseCityRelViewModel> getList(int agid) {
		List<CoAgencyBaseCityRelViewModel> list = new ArrayList<CoAgencyBaseCityRelViewModel>();
		for (CoAgencyBaseCityRelViewModel m : baseCityList) {
			if (province != null && !"".equals(province)) {
				if (!province.equals(m.getProvince()))
					continue;
			}
			if (city != null && !"".equals(city)) {
				if (!city.equals(m.getCity()))
					continue;
			}
			if (agid != 0) {
				if (m.getCabc_id() != agid)
					continue;
			}
			if (agname != null && !"".equals(agname)) {
				try {
					if (!m.getCoab_name().contains(agname))
						continue;
				} catch (Exception e) {
					continue;
				}
			}
			if (setuptype != null && !"".equals(setuptype)) {
				if (!setuptype.equals(m.getCoab_setuptype()))
					continue;
			}
			list.add(m);
		}
		return list;
	}

	// 根据省份找城市
	private List<String> searchCityByPro(String pro) {
		List<String> list = new ArrayList<String>();
		String c = null;
		for (CoAgencyBaseCityRelViewModel m : baseCityList) {
			if (m.getProvince().equals(pro))
				c = m.getCity();
			if (!list.contains(c))
				list.add(c);
		}
		return list;
	}

	// 根据省份找机构
	private List<String> searchBaseByPro(String pro) {
		List<String> list = new ArrayList<String>();
		String b = null;
		for (CoAgencyBaseCityRelViewModel m : baseCityList) {
			if (m.getProvince().equals(pro))
				b = m.getCoab_name();
			if (!list.contains(b))
				list.add(b);
		}
		return list;
	}

	// 根据城市找机构
	private List<String> searchBaseByCity(String city) {
		List<String> list = new ArrayList<String>();
		String b = null;
		for (CoAgencyBaseCityRelViewModel m : baseCityList) {
			if (m.getCity().equals(city))
				b = m.getCoab_name();
			if (!list.contains(b))
				list.add(b);
		}
		return list;
	}

	// 查看在册数据
	@Command("regData")
	public void regData(@BindingParam("sial_id") String sial_id) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("sial_id", sial_id);
		Window window;
		if ("深圳".equals(selCity) && "深圳中智经济技术合作有限公司".equals(selCoabName)) {
			window = (Window) Executions.createComponents(
					"Algorithm_LocalRegisteredData.zul", null, map);
		} else {
			window = (Window) Executions.createComponents(
					"Algorithm_RegisteredData.zul", null, map);
		}
		window.doModal();
	}

	// 新增算法
	@Command("addAlg")
	@NotifyChange({ "nowSiList", "hisSiList" })
	public void addAlg(@BindingParam("cabc_id") String cabc_idadd) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("cabc_id", cabc_idadd);
		Window window = (Window) Executions.createComponents(
				"Algorithm_Add.zul", null, map);
		window.doModal();
		refreshWin();
	}

	// 禁止算法
	@Command("stopAlg")
	@NotifyChange({ "nowSiList", "hisSiList" })
	public void stopAlg(@BindingParam("sial_id") String sial_id) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("sial_id", sial_id);
		Window window = (Window) Executions.createComponents(
				"Algorithm_Stop.zul", null, map);
		window.doModal();
		refreshWin();
	}

	// 更新算法
	@Command("upAlg")
	@NotifyChange({ "nowSiList", "hisSiList" })
	public void upAlg(@BindingParam("sial_id") String sial_id) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("sial_id", sial_id);
		Window window = (Window) Executions.createComponents(
				"Algorithm_Update.zul", null, map);
		window.doModal();
		refreshWin();
	}

	// 查看算法
	@Command("searAlg")
	public void searAlg(@BindingParam("sial_id") String sial_id) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("sial_id", sial_id);
		Window window = (Window) Executions.createComponents(
				"Algorithm_Search.zul", null, map);
		window.doModal();
	}

	// 根据城市及机构查找算法
	@Command("selectByBaseCity")
	@NotifyChange({ "nowSiList", "hisSiList", "changeList", "changeVis" })
	public void selectByBaseCity(@BindingParam("selectitem") Listitem selectitem) {
		try {
			cabc_id = ((CoAgencyBaseCityRelViewModel) selectitem.getValue())
					.getCabc_id();
			selCity = ((CoAgencyBaseCityRelViewModel) selectitem.getValue())
					.getCity();
			selCoabName = ((CoAgencyBaseCityRelViewModel) selectitem.getValue())
					.getCoab_name();
			changeList = bll.getSiAlgorithmChangeList(cabc_id);
			changeVis = changeList.size() > 0 ? true : false;
			nowSiList = bll.getSiAlgorithmSel(cabc_id, 1);
			hisSiList = bll.getSiAlgorithmSel(cabc_id, 2);
		} catch (Exception e) {

		}
	}

	// 查看待审核算法
	@Command("searAlgChange")
	public void searAlgChange(@BindingParam("sich_id") String sich_id) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("daid", sich_id);
		map.put("submitVis", "false");
		Window window = (Window) Executions.createComponents(
				"/SocialInsurance/Algorithm_Examined.zul", null, map);
		window.doModal();
	}

	// 刷新页面
	public void refreshWin() {
		nowSiList = bll.getSiAlgorithmSel(cabc_id, 1);
		hisSiList = bll.getSiAlgorithmSel(cabc_id, 2);
		changeList = bll.getSiAlgorithmChangeList(cabc_id);
		changeVis = changeList.size() > 0 ? true : false;
		BindUtils.postNotifyChange(null, null, this, "nowSiList");
		BindUtils.postNotifyChange(null, null, this, "hisSiList");
		BindUtils.postNotifyChange(null, null, this, "changeList");
		BindUtils.postNotifyChange(null, null, this, "changeVis");
	}

	public List<CoAgencyBaseCityRelViewModel> getBaseCityList() {
		return baseCityList;
	}

	private void setBaseCityList() {
		this.baseCityList = bll.getcoAgBaseCityRelList();
	}

	public List<String> getCityList() {
		return cityList;
	}

	private void setCityList() {
		this.cityList = bll.getCoAgCity();
	}

	public List<String> getProList() {
		return proList;
	}

	private void setProList() {
		this.proList = bll.getCoAgProvince();
	}

	public List<String> getBaseList() {
		return baseList;
	}

	private void setBaseList() {
		this.baseList = bll.getCoAgBase();
	}

	public List<String> getSetuptypeList() {
		return setuptypeList;
	}

	private void setSetuptypeList() {
		this.setuptypeList = bll.getSetuptype();
	}

	public List<SocialInsuranceAlgorithmViewModel> getHisSiList() {
		return hisSiList;
	}

	public List<SocialInsuranceAlgorithmViewModel> getNowSiList() {
		return nowSiList;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAgname() {
		return agname;
	}

	public void setAgname(String agname) {
		this.agname = agname;
	}

	public String getSetuptype() {
		return setuptype;
	}

	public void setSetuptype(String setuptype) {
		this.setuptype = setuptype;
	}

	public List<CoAgencyBaseCityRelViewModel> getMainList() {
		return mainList;
	}

	public void setMainList(List<CoAgencyBaseCityRelViewModel> mainList) {
		this.mainList = mainList;
	}

	public List<String> getMaincityList() {
		return maincityList;
	}

	public void setMaincityList(List<String> maincityList) {
		this.maincityList = maincityList;
	}

	public List<String> getMainbaseList() {
		return mainbaseList;
	}

	public void setMainbaseList(List<String> mainbaseList) {
		this.mainbaseList = mainbaseList;
	}

	public boolean isChangeVis() {
		return changeVis;
	}

	public List<SocialInsuranceChangeModel> getChangeList() {
		return changeList;
	}

}
