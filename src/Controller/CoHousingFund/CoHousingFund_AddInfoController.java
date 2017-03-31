package Controller.CoHousingFund;

import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import Model.CoHousingFundChangeModel;
import Model.CoHousingFundPwdChangeModel;
import Model.CoHousingFundZbChangeModel;
import Model.PubStateRelModel;
import bll.CoHousingFund.CoHousingFund_ListBll;

public class CoHousingFund_AddInfoController {
	Integer daid;
	private CoHousingFundChangeModel chfcModel = new CoHousingFundChangeModel();
	private List<CoHousingFundZbChangeModel> zbList = new ListModelList<>();
	private List<CoHousingFundPwdChangeModel> pwdList = new ListModelList<>();
	private List<PubStateRelModel> hosList = new ListModelList<>();
	private String title = "";

	public CoHousingFund_AddInfoController() {
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
			
			setZbList(bll.getZbChangeList(" and cfzc_chfc_id=" + daid));
			setPwdList(bll.getPwdChangeList(" and cfpc_chfc_id=" + daid));
			setHosList(bll.getHosList(daid, " and pbsr_type='cogjj'"));
			setTitle(chfcModel.getChfc_addtype() + " - 申报详情");
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

	public final List<CoHousingFundZbChangeModel> getZbList() {
		return zbList;
	}

	public final List<CoHousingFundPwdChangeModel> getPwdList() {
		return pwdList;
	}

	public final void setZbList(List<CoHousingFundZbChangeModel> zbList) {
		this.zbList = zbList;
	}

	public final void setPwdList(List<CoHousingFundPwdChangeModel> pwdList) {
		this.pwdList = pwdList;
	}

	public List<PubStateRelModel> getHosList() {
		return hosList;
	}

	public void setHosList(List<PubStateRelModel> hosList) {
		this.hosList = hosList;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
