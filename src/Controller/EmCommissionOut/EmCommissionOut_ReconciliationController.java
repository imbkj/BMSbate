package Controller.EmCommissionOut;

import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import bll.EmCommissionOut.EmCommissionOutListBll;

import Model.CoAgencyBaseCityRelViewModel;
import Model.EmCommissionOutHistoryModel;

public class EmCommissionOut_ReconciliationController {

	private List<CoAgencyBaseCityRelViewModel> cityList = new ListModelList<>();
	private CoAgencyBaseCityRelViewModel cityM = null;
	private List<CoAgencyBaseCityRelViewModel> coabList;
	private List<CoAgencyBaseCityRelViewModel> scoabList = new ListModelList<>();
	private CoAgencyBaseCityRelViewModel coabM = null;
	private List<String> ownmonthList = new ListModelList<>();
	private String ownmonth = "";

	private List<EmCommissionOutHistoryModel> echList = new ListModelList<>();

	public EmCommissionOut_ReconciliationController() {
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * 页面初始化
	 * 
	 */
	public void init() {
		EmCommissionOutListBll bll = new EmCommissionOutListBll();
		try {
			setCityList(bll.getEmOutHisPutCityList());
			cityList.add(0, null);
			setCoabList(bll.getEmOutHisPutCoabList());
			citySelect();
			setOwnmonthList(bll.getPayUpdateOwnmonthList());
			if (ownmonthList.size() > 0) {
				ownmonth = ownmonthList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询
	 * 
	 */
	@Command("search")
	@NotifyChange({ "echList" })
	public void search() {
		EmCommissionOutListBll bll = new EmCommissionOutListBll();
		String str = "";

		try {
			if (!ownmonth.isEmpty()) {
				if (cityM != null) {
					str += " and (his.cabc_ppc_id=" + cityM.getCabc_ppc_id()
							+ " or put.cabc_ppc_id=" + cityM.getCabc_ppc_id()
							+ ")";
				}
				if (coabM != null) {
					str += " and (his.cabc_id=" + coabM.getCabc_id()
							+ " or put.cabc_id=" + coabM.getCabc_id() + ")";
				}

				echList = bll.getEmOutCoabCompareList(ownmonth, str);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("查询出错:" + e.toString(), "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	@Command("citySelect")
	@NotifyChange({ "scoabList" })
	public void citySelect() {
		try {
			scoabList.clear();

			for (CoAgencyBaseCityRelViewModel m : coabList) {
				if (cityM != null) {
					if (cityM.getCabc_ppc_id() != m.getCabc_ppc_id()) {
						continue;
					}
				}

				scoabList.add(m);
			}
			scoabList.add(0, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public final List<CoAgencyBaseCityRelViewModel> getCityList() {
		return cityList;
	}

	public final CoAgencyBaseCityRelViewModel getCityM() {
		return cityM;
	}

	public final List<CoAgencyBaseCityRelViewModel> getCoabList() {
		return coabList;
	}

	public final List<CoAgencyBaseCityRelViewModel> getScoabList() {
		return scoabList;
	}

	public final CoAgencyBaseCityRelViewModel getCoabM() {
		return coabM;
	}

	public final List<String> getOwnmonthList() {
		return ownmonthList;
	}

	public final String getOwnmonth() {
		return ownmonth;
	}

	public final void setCityList(List<CoAgencyBaseCityRelViewModel> cityList) {
		this.cityList = cityList;
	}

	public final void setCityM(CoAgencyBaseCityRelViewModel cityM) {
		this.cityM = cityM;
	}

	public final void setCoabList(List<CoAgencyBaseCityRelViewModel> coabList) {
		this.coabList = coabList;
	}

	public final void setScoabList(List<CoAgencyBaseCityRelViewModel> scoabList) {
		this.scoabList = scoabList;
	}

	public final void setCoabM(CoAgencyBaseCityRelViewModel coabM) {
		this.coabM = coabM;
	}

	public final void setOwnmonthList(List<String> ownmonthList) {
		this.ownmonthList = ownmonthList;
	}

	public final void setOwnmonth(String ownmonth) {
		this.ownmonth = ownmonth;
	}

	public List<EmCommissionOutHistoryModel> getEchList() {
		return echList;
	}

	public void setEchList(List<EmCommissionOutHistoryModel> echList) {
		this.echList = echList;
	}

}
