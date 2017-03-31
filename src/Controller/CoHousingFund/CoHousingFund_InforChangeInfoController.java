package Controller.CoHousingFund;

import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import Model.CoHousingFundChangeModel;
import Model.CoHousingFundInforChangeModel;
import Model.PubStateRelModel;
import bll.CoHousingFund.CoHousingFund_ListBll;

public class CoHousingFund_InforChangeInfoController {
	Integer daid;
	private CoHousingFundChangeModel chfcModel = new CoHousingFundChangeModel();
	private List<PubStateRelModel> hosList = new ListModelList<>();
	private List<CoHousingFundInforChangeModel> cficList = new ListModelList<>();

	public CoHousingFund_InforChangeInfoController() {
		try {
			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());

			init();
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * 初始化
	 * 
	 */
	public void init() {
		CoHousingFund_ListBll bll = new CoHousingFund_ListBll();
		try {
			chfcModel = bll.getCoHoChangeList(" and chfc_id=" + daid).get(0);
			setHosList(bll.getHosList(daid, " and pbsr_type='cogjj'"));
			setCficList(bll.getInforChangeList(" and cfic_chfc_id=" + daid));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public final CoHousingFundChangeModel getChfcModel() {
		return chfcModel;
	}

	public final void setChfcModel(CoHousingFundChangeModel chfcModel) {
		this.chfcModel = chfcModel;
	}

	public List<PubStateRelModel> getHosList() {
		return hosList;
	}

	public void setHosList(List<PubStateRelModel> hosList) {
		this.hosList = hosList;
	}

	public List<CoHousingFundInforChangeModel> getCficList() {
		return cficList;
	}

	public void setCficList(List<CoHousingFundInforChangeModel> cficList) {
		this.cficList = cficList;
	}
}
