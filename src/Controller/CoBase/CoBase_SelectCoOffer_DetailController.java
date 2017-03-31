package Controller.CoBase;

import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import bll.CoBase.CoBase_SelectBll;

import Model.CoOfferModel;

public class CoBase_SelectCoOffer_DetailController {

	Integer daid;

	private List<CoOfferModel> embaseList = new ListModelList<>();

	public CoBase_SelectCoOffer_DetailController() {
		try {
			daid = Integer.valueOf(Executions.getCurrent().getArg().get("daid")
					.toString());

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
		CoBase_SelectBll bll = new CoBase_SelectBll();
		try {
			setEmbaseList(bll.getEmbaseList(" and coof_id=" + daid+" order by emba_name"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<CoOfferModel> getEmbaseList() {
		return embaseList;
	}

	public void setEmbaseList(List<CoOfferModel> embaseList) {
		this.embaseList = embaseList;
	}
}
