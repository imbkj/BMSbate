package Controller.EmCommissionOut.Standard;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmCommissionOut.Standard.Standard_ListBll;
import bll.EmCommissionOut.Standard.Standard_OperateBll;

import Model.CoAgencyBaseCityRelViewModel;
import Model.CoOfferListModel;
import Model.EmCommissionOutStandardModel;
import Model.PubProCityModel;
import Util.UserInfo;

public class Standard_ReSubmitController {

	private EmCommissionOutStandardModel m = new EmCommissionOutStandardModel();
	private boolean ltbmu = true;
	Integer daid = 0;

	// 社保、公积金
	private List<String> sbzhtypeList = new ListModelList<>();
	private List<String> sbfeetypeList = new ListModelList<>();
	private List<String> gjjzhtypeList = new ListModelList<>();
	private List<String> gjjfeetypeList = new ListModelList<>();

	// 城市
	private ListModelList<PubProCityModel> cityList = new ListModelList<>();
	private PubProCityModel ppcModel = new PubProCityModel();

	// 委托机构
	private List<CoAgencyBaseCityRelViewModel> caList = new ListModelList<>();
	private CoAgencyBaseCityRelViewModel caM = new CoAgencyBaseCityRelViewModel();

	// 福利产品
	private List<CoOfferListModel> colList = new ListModelList<>();
	private List<CoOfferListModel> scolList = new ListModelList<>();

	public Standard_ReSubmitController() {
		try {
			Standard_ListBll bll = new Standard_ListBll();

			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());

			setM(bll.getStandardInfo1(daid));

			// 城市
			setCityList(bll.getCityList());
			for (PubProCityModel m1 : cityList) {
				if (m1.getId() == m.getPpc_id()) {
					setPpcModel(m1);
					break;
				}
			}

			// 委托机构
			setCaList(bll.getCoAgencyList1(m.getEcos_cabc_id()));
			if (caList.size() > 0) {
				for (CoAgencyBaseCityRelViewModel m1 : caList) {
					if (m1.getCabc_id() == m.getEcos_cabc_id()) {
						setCaM(m1);
						break;
					}
				}
			}

			// 福利产品
			setColList(bll.getCoOfferList(m.getCid(), caM.getCabc_id(), 0));
			setScolList(bll.getCoOfferListRel(m.getEcos_id()));

			// 社保、公积金
			sbzhtypeList.add("");
			sbzhtypeList.add("受托方账户");
			sbzhtypeList.add("独立账户");

			sbfeetypeList.add("");
			sbfeetypeList.add("委托对账");
			sbfeetypeList.add("单独支付");

			gjjzhtypeList.add("");
			gjjzhtypeList.add("受托方账户");
			gjjzhtypeList.add("独立账户");

			gjjfeetypeList.add("");
			gjjfeetypeList.add("委托对账");
			gjjfeetypeList.add("单独支付");

		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Command("CoOfferListInit")
	public void CoOfferListInit(@BindingParam("listitem") Listitem lt) {
		CoOfferListModel m1 = lt.getValue();
		for (CoOfferListModel m2 : scolList) {
			if (m1.getColi_id().toString().trim()
					.equals(m2.getColi_id().toString().trim())) {
				lt.setSelected(true);
				break;
			}
		}
	}

	@Command("cityOnChange")
	@NotifyChange({ "caM", "colList", "caList" })
	public void cityOnChange() {
		Standard_ListBll bll = new Standard_ListBll();

		if (ppcModel != null) {

			// 获取机构列表
			setCaList(bll.getCoAgencyList(ppcModel.getId()));

			if (caList.size() > 0) {
				setCaM(bll.getDefaultCoAgency(ppcModel.getId()));
				if (caM.getCoab_name() == null || caM.getCoab_name().isEmpty()) {
					CoAgencyBaseCityRelViewModel m1 = new CoAgencyBaseCityRelViewModel();
					m1.setCoab_name("无");
					caList.add(0, m1);
					setCaM(caList.get(0));
				} else {
					for (CoAgencyBaseCityRelViewModel m1 : caList) {
						if (caM.getCabc_id() == m1.getCabc_id()) {
							setCaM(m1);
							break;
						}
					}
				}
				if (caM.getCoab_name().equals("无")) {
					setColList(bll.getCoOfferList(m.getCid(), 0,
							ppcModel.getId()));
				} else {
					setColList(bll.getCoOfferList(m.getCid(), caM.getCabc_id(),
							0));
				}
			} else {
				CoAgencyBaseCityRelViewModel m1 = new CoAgencyBaseCityRelViewModel();
				m1.setCoab_name("无");
				caList.add(0, m1);
				setCaM(caList.get(0));
			}
		}
	}

	@Command("CoAgencyOnChange")
	@NotifyChange({ "colList", "m", "feeVis1", "feeVis2" })
	public void CoAgencyOnChange() {
		Standard_ListBll bll = new Standard_ListBll();

		setColList(bll.getCoOfferList(m.getCid(), caM.getCabc_id(), 0));

		m.setEcos_cabc_id(caM.getCabc_id());
		setM(bll.getServiceCost(m));
	}

	@Command("submit")
	public void submit(@BindingParam("win") Window win,
			@BindingParam("set") Set<Listitem> set) {
		try {
			if (caM.getCoab_name().equals("无")) {
				m.setCabc_id(ppcModel.getId());
				m.setEcos_state(2);
			} else {

				m.setCabc_id(caM.getCabc_id());

				m.setEcos_state(m.getEcos_laststate() + 1);

				m.setEosr_addname(UserInfo.getUsername());
				m.setEosr_statetime(new Date());
				if (m.getEcos_state() == 4) {
					m.setEcos_usestate(1);
				}

				String[] str = new Standard_OperateBll().UpdateState(m);
				new Standard_OperateBll().CoOfferListMod(m.getEcos_id(), set);

				if (str[0].equals("1")) {
					Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show(str[1], "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("提交出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public EmCommissionOutStandardModel getM() {
		return m;
	}

	public void setM(EmCommissionOutStandardModel m) {
		this.m = m;
	}

	public boolean isLtbmu() {
		return ltbmu;
	}

	public void setLtbmu(boolean ltbmu) {
		this.ltbmu = ltbmu;
	}

	public List<String> getSbzhtypeList() {
		return sbzhtypeList;
	}

	public void setSbzhtypeList(List<String> sbzhtypeList) {
		this.sbzhtypeList = sbzhtypeList;
	}

	public List<String> getSbfeetypeList() {
		return sbfeetypeList;
	}

	public void setSbfeetypeList(List<String> sbfeetypeList) {
		this.sbfeetypeList = sbfeetypeList;
	}

	public List<String> getGjjzhtypeList() {
		return gjjzhtypeList;
	}

	public void setGjjzhtypeList(List<String> gjjzhtypeList) {
		this.gjjzhtypeList = gjjzhtypeList;
	}

	public List<String> getGjjfeetypeList() {
		return gjjfeetypeList;
	}

	public void setGjjfeetypeList(List<String> gjjfeetypeList) {
		this.gjjfeetypeList = gjjfeetypeList;
	}

	public ListModelList<PubProCityModel> getCityList() {
		return cityList;
	}

	public void setCityList(ListModelList<PubProCityModel> cityList) {
		this.cityList = cityList;
	}

	public PubProCityModel getPpcModel() {
		return ppcModel;
	}

	public void setPpcModel(PubProCityModel ppcModel) {
		this.ppcModel = ppcModel;
	}

	public List<CoAgencyBaseCityRelViewModel> getCaList() {
		return caList;
	}

	public void setCaList(List<CoAgencyBaseCityRelViewModel> caList) {
		this.caList = caList;
	}

	public CoAgencyBaseCityRelViewModel getCaM() {
		return caM;
	}

	public void setCaM(CoAgencyBaseCityRelViewModel caM) {
		this.caM = caM;
	}

	public List<CoOfferListModel> getColList() {
		return colList;
	}

	public void setColList(List<CoOfferListModel> colList) {
		this.colList = colList;
	}

	public List<CoOfferListModel> getScolList() {
		return scolList;
	}

	public void setScolList(List<CoOfferListModel> scolList) {
		this.scolList = scolList;
	}
}
