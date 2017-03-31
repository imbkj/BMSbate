package Controller.CoQuotation;

import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import bll.CoQuotation.CoQuotation_AddBll;

import Model.CoOfferListModel;

public class CoQuotation_Add_SpanController {

	Integer coli_id = 935;
	private List<CoOfferListModel> spList;
	private boolean ltbmu = true;

	public CoQuotation_Add_SpanController() {
		try {
//			coli_id = Integer.parseInt(Executions.getCurrent().getArg()
//					.get("coli_id").toString());
			setSpList(new ListModelList<>(
					new CoQuotation_AddBll().getSpanList(coli_id)));
		} catch (Exception e) {
			Messagebox.show("加载页面失败,请联系IT部门!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
			System.out.println(e.toString());
		}
	}

	public List<CoOfferListModel> getSpList() {
		return spList;
	}

	public void setSpList(List<CoOfferListModel> spList) {
		this.spList = spList;
	}

	public boolean isLtbmu() {
		return ltbmu;
	}

	public void setLtbmu(boolean ltbmu) {
		this.ltbmu = ltbmu;
	}
}
