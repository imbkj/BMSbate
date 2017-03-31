package Controller.EmCommissionOut;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import bll.CoProduct.cpd_addBll;
import bll.EmCommissionOut.EmCommissionOutPay_UnfinishedBll;

import Model.CoAgencyBaseCityRelViewModel;
import Model.CoPclassModel;
import Model.CoProductModel;
import Model.EmCommissionOutPayModel;
import Model.LoginModel;
import Model.PubProCityModel;
import Util.RedirectUtil;
import Util.UserInfo;

public class EmCommissionOutPay_UnfinishedController {
	private ListModelList<EmCommissionOutPayModel> wt_unflist;// 显示未完成列表
	private ListModelList<EmCommissionOutPayModel> wt_kfunflist;// 显示未完成列表
	private ListModelList<EmCommissionOutPayModel> wt_wtunflist;// 显示后道差额成列表
	private ListModelList<EmCommissionOutPayModel> ownmonthlist;// 显示所属月份
	private ListModelList<EmCommissionOutPayModel> clientlist;// 显示客服
	private EmCommissionOutPay_UnfinishedBll bll = new EmCommissionOutPay_UnfinishedBll();
	private ListModelList<CoPclassModel> classlist;
	private List<CoAgencyBaseCityRelViewModel> scoagList = new ListModelList<>();
	private List<CoAgencyBaseCityRelViewModel> coagList;
	private PubProCityModel cityModel = new PubProCityModel();
	private List<PubProCityModel> cityList;
	private ListModelList<CoProductModel> eclasslist;
	cpd_addBll citybll = new cpd_addBll();
	private CoAgencyBaseCityRelViewModel coagModel = new CoAgencyBaseCityRelViewModel();

	@Init
	public void init() throws SQLException {
		wt_unflist = new ListModelList<EmCommissionOutPayModel>(
				bll.getwt_unflist("", "", ""));// 显示未完成列表数据

		wt_kfunflist = new ListModelList<EmCommissionOutPayModel>(
				bll.getwt_kfunflist("","","",UserInfo.getUsername()));// 显示未完成列表数据
		
		ownmonthlist = new ListModelList<EmCommissionOutPayModel>(
				bll.getownmonthlist());// 显示未完成列表数据
		
		clientlist = new ListModelList<EmCommissionOutPayModel>(
				bll.getclientlist());// 显示未完成列表数据

		classlist = new ListModelList<CoPclassModel>(citybll.getclass());
		this.setEclasslist(new ListModelList<CoProductModel>(citybll
				.geteclass()));
		setCityList(citybll.getcity());
		setCoagList(citybll.getCoagList());
		if (cityList.size() > 0) {
			for (PubProCityModel m1 : cityList) {
				if (m1.getName().equals("")) {
					setCityModel(m1);
					break;
				}
			}
			city_change();
		}
	}

	/**
	 * 城市变更
	 * 
	 */
	@Command("city_change")
	@NotifyChange({ "scoagList", "coagModel" })
	public void city_change() {
		try {
			scoagList.clear();

			for (CoAgencyBaseCityRelViewModel m1 : coagList) {
				if (m1.getCabc_ppc_id() == cityModel.getId()) {
					scoagList.add(m1);
				}
			}
			if (scoagList.size() > 0) {
				setCoagModel(scoagList.get(0));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command("search")
	@NotifyChange("wt_unflist")
	public void search(@BindingParam("ownmonth") Combobox ownmonth,
			@BindingParam("city") Combobox city,
			@BindingParam("company") Combobox company) throws SQLException {

		wt_unflist = new ListModelList<EmCommissionOutPayModel>(
				bll.getwt_unflist(ownmonth.getValue(), city.getValue(),
						company.getValue()));// 显示未完成列表数据
	}
	
	@Command("kfaut_search")
	@NotifyChange("wt_kfunflist")
	public void kfaut_search(@BindingParam("client") Combobox client,
			@BindingParam("name") Textbox name,
			@BindingParam("gid") Textbox gid,
			@BindingParam("company") Textbox company) throws SQLException {

		wt_kfunflist = new ListModelList<EmCommissionOutPayModel>(
				bll.getwt_kfunflist(gid.getValue(),name.getValue(),company.getValue(),client.getValue()));// 显示未完成列表数据
	}
	
	@Command("remark")
	public void remark(@BindingParam("emco") EmCommissionOutPayModel emco) {
		Map map = new HashMap<>();
		map.put("type", "委托对帐");// 业务类型:来自WfClass的wfcl_name
		map.put("id", Integer.parseInt(emco.getEcop_id()));// 业务表id
		map.put("tablename", "EmCommissionOutPay");// 业务表名

		List<LoginModel> mlist = new ArrayList<LoginModel>();
		LoginModel m = new LoginModel();
		m.setLog_name("");
		m.setLog_id(0);
		// 收件人姓名和收件人id至少要填一个
		mlist.add(m);
		map.put("list", mlist);// 默认收件人list
		Window window = (Window) Executions.createComponents(
				"../SysMessage/Message_Add.zul", null, map);
		window.doModal();

	}
	
	@Command("sfee_check")
	public void sfee_check(@BindingParam("emco") EmCommissionOutPayModel emco) {
		Map map = new HashMap<>();
		map.put("ecop_id", emco.getEcop_id());// 业务表id
		Window window = (Window) Executions.createComponents(
				"EmCommissionOutPay_SFeeCheck.zul", null, map);
		window.doModal();

	}
	
	@Command("sfee_addzd")
	@NotifyChange("wt_kfunflist")
	public void sfee_addzd(@BindingParam("emco") EmCommissionOutPayModel emco) throws SQLException {
		Map map = new HashMap<>();
		map.put("ecop_id", emco.getEcop_id());// 业务表id
		Window window = (Window) Executions.createComponents(
				"EmCommissionOutPay_SFeeAddZd.zul", null, map);
		window.doModal();
		wt_kfunflist = new ListModelList<EmCommissionOutPayModel>(
				bll.getwt_kfunflist("","","",""));// 显示未完成列表数据
	}

	public ListModelList<EmCommissionOutPayModel> getWt_unflist() {
		return wt_unflist;
	}

	public void setWt_unflist(ListModelList<EmCommissionOutPayModel> wt_unflist) {
		this.wt_unflist = wt_unflist;
	}

	public ListModelList<EmCommissionOutPayModel> getWt_kfunflist() {
		return wt_kfunflist;
	}

	public void setWt_kfunflist(
			ListModelList<EmCommissionOutPayModel> wt_kfunflist) {
		this.wt_kfunflist = wt_kfunflist;
	}

	public ListModelList<CoPclassModel> getClasslist() {
		return classlist;
	}

	public void setClasslist(ListModelList<CoPclassModel> classlist) {
		this.classlist = classlist;
	}

	public List<CoAgencyBaseCityRelViewModel> getScoagList() {
		return scoagList;
	}

	public void setScoagList(List<CoAgencyBaseCityRelViewModel> scoagList) {
		this.scoagList = scoagList;
	}

	public List<CoAgencyBaseCityRelViewModel> getCoagList() {
		return coagList;
	}

	public void setCoagList(List<CoAgencyBaseCityRelViewModel> coagList) {
		this.coagList = coagList;
	}

	public PubProCityModel getCityModel() {
		return cityModel;
	}

	public void setCityModel(PubProCityModel cityModel) {
		this.cityModel = cityModel;
	}

	public cpd_addBll getCitybll() {
		return citybll;
	}

	public void setCitybll(cpd_addBll citybll) {
		this.citybll = citybll;
	}

	public CoAgencyBaseCityRelViewModel getCoagModel() {
		return coagModel;
	}

	public void setCoagModel(CoAgencyBaseCityRelViewModel coagModel) {
		this.coagModel = coagModel;
	}

	public List<PubProCityModel> getCityList() {
		return cityList;
	}

	public void setCityList(List<PubProCityModel> cityList) {
		this.cityList = cityList;
	}

	public ListModelList<CoProductModel> getEclasslist() {
		return eclasslist;
	}

	public void setEclasslist(ListModelList<CoProductModel> eclasslist) {
		this.eclasslist = eclasslist;
	}

	public ListModelList<EmCommissionOutPayModel> getOwnmonthlist() {
		return ownmonthlist;
	}

	public void setOwnmonthlist(
			ListModelList<EmCommissionOutPayModel> ownmonthlist) {
		this.ownmonthlist = ownmonthlist;
	}

	public ListModelList<EmCommissionOutPayModel> getClientlist() {
		return clientlist;
	}

	public void setClientlist(ListModelList<EmCommissionOutPayModel> clientlist) {
		this.clientlist = clientlist;
	}

}
