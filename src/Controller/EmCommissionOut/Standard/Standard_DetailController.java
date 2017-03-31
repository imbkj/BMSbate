package Controller.EmCommissionOut.Standard;

import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import bll.EmCommissionOut.Standard.Standard_ListBll;

import Model.CoOfferListModel;
import Model.EmCommissionOutStandardModel;

public class Standard_DetailController {
	private EmCommissionOutStandardModel m = new EmCommissionOutStandardModel();
	private List<CoOfferListModel> scolList = new ListModelList<>();
	Integer daid = 0;

	public Standard_DetailController() {
		try {
			Standard_ListBll bll = new Standard_ListBll();

			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());

			setM(bll.getStandardInfo1(daid));
			setScolList(bll.getCoOfferListRel(daid));

		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public EmCommissionOutStandardModel getM() {
		return m;
	}

	public void setM(EmCommissionOutStandardModel m) {
		this.m = m;
	}

	public List<CoOfferListModel> getScolList() {
		return scolList;
	}

	public void setScolList(List<CoOfferListModel> scolList) {
		this.scolList = scolList;
	}
}
