package Controller.EmCommissionOut.Standard;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoAgencyBaseCityRelViewModel;
import Model.EmCommissionOutStandardModel;
import Model.PubProCityModel;
import Util.RegexUtil;
import bll.EmCommissionOut.Standard.Standard_ListBll;

public class Standard_kfOperateListController {

	private List<EmCommissionOutStandardModel> stList;
	private List<EmCommissionOutStandardModel> sstList = new ListModelList<>();
	private List<EmCommissionOutStandardModel> stateList = new ListModelList<>();
	private List<PubProCityModel> cityList = new ListModelList<>();
	private List<CoAgencyBaseCityRelViewModel> coabList = new ListModelList<>();

	String wherestr = " and type=1 and state=1";

	// 检索条件
	private String cid = "";
	private String shortname = "";
	private String name = "";
	private PubProCityModel ppcModel = new PubProCityModel();
	private String coab_name = "";
	private String statename = "";
	private Date addtime;

	public Standard_kfOperateListController() {
		try {
			Standard_ListBll bll = new Standard_ListBll();

			setStList(new ListModelList<>(bll.getStandardList(wherestr)));
			setStateList(bll.getStateList(" and type=1"));
			stateList.add(0, new EmCommissionOutStandardModel());
			setCityList(bll.getCityList1());
			cityList.add(0, new PubProCityModel());

			search();

		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Command("search")
	@NotifyChange({ "sstList", "coabList" })
	public void search() {
		Standard_ListBll bll = new Standard_ListBll();

		sstList.clear();

		for (EmCommissionOutStandardModel m : stList) {
			if (!cid.isEmpty()) {
				if (!RegexUtil.isExists(cid, m.getCid() + "")) {
					continue;
				}
			}
			if (!shortname.isEmpty()) {
				if (!RegexUtil.isExists(shortname, m.getCoba_shortname())) {
					continue;
				}
			}
			if (!name.isEmpty()) {
				if (!RegexUtil.isExists(name, m.getEcos_name())) {
					continue;
				}
			}
			if (ppcModel.getName() != null && !ppcModel.getName().isEmpty()) {
				setCoabList(bll.getCoAgencyList(ppcModel.getId()));
				coabList.add(0, new CoAgencyBaseCityRelViewModel());
				if (!ppcModel.getName().equals(m.getCity())) {
					continue;
				}
			} else {
				coabList.clear();
			}
			if (!coab_name.isEmpty()) {
				if (!coab_name.equals(m.getCoab_name())) {
					continue;
				}
			}
			if (!statename.isEmpty()) {
				if (!statename.equals(m.getStatename())) {
					continue;
				}
			}
			if (addtime != null) {
				if (!new SimpleDateFormat("yyyy-MM-dd").format(addtime).equals(
						m.getEcos_addtime1())) {
					continue;
				}
			}

			sstList.add(m);
		}
	}

	@Command("openwin")
	@NotifyChange({ "sstList", "coabList" })
	public void openwin(@BindingParam("daid") Integer daid,
			@BindingParam("url") String url) {
		Standard_ListBll bll = new Standard_ListBll();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("daid", daid);
		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();

		setStList(new ListModelList<>(bll.getStandardList(wherestr)));
		search();
	}

	public List<EmCommissionOutStandardModel> getStList() {
		return stList;
	}

	public void setStList(List<EmCommissionOutStandardModel> stList) {
		this.stList = stList;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCoab_name() {
		return coab_name;
	}

	public void setCoab_name(String coab_name) {
		this.coab_name = coab_name;
	}

	public String getStatename() {
		return statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public List<EmCommissionOutStandardModel> getStateList() {
		return stateList;
	}

	public void setStateList(List<EmCommissionOutStandardModel> stateList) {
		this.stateList = stateList;
	}

	public List<PubProCityModel> getCityList() {
		return cityList;
	}

	public void setCityList(List<PubProCityModel> cityList) {
		this.cityList = cityList;
	}

	public List<EmCommissionOutStandardModel> getSstList() {
		return sstList;
	}

	public void setSstList(List<EmCommissionOutStandardModel> sstList) {
		this.sstList = sstList;
	}

	public List<CoAgencyBaseCityRelViewModel> getCoabList() {
		return coabList;
	}

	public void setCoabList(List<CoAgencyBaseCityRelViewModel> coabList) {
		this.coabList = coabList;
	}

	public PubProCityModel getPpcModel() {
		return ppcModel;
	}

	public void setPpcModel(PubProCityModel ppcModel) {
		this.ppcModel = ppcModel;
	}
}
