package Controller.CoHousingFund;

import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import Model.CoHousingFundChangeModel;
import Model.CoHousingFundInforChangeModel;
import Model.PubStateRelModel;
import bll.CoHousingFund.CoHousingFund_ListBll;

public class CoHousingFund_RadixCppInfoController {
	Integer daid;
	private CoHousingFundChangeModel chfcModel = new CoHousingFundChangeModel();
	private CoHousingFundInforChangeModel cficModel = new CoHousingFundInforChangeModel();
	private List<PubStateRelModel> hosList = new ListModelList<>();

	public CoHousingFund_RadixCppInfoController() {
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
			try {
				setCficModel(bll
						.getInforChangeList(" and cfic_chfc_id=" + daid).get(0));
			} catch (Exception e) {
				// TODO: handle exception
			}
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

	public CoHousingFundInforChangeModel getCficModel() {
		return cficModel;
	}

	public void setCficModel(CoHousingFundInforChangeModel cficModel) {
		this.cficModel = cficModel;
	}
}
