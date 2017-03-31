package Controller.CoProduct;

import java.util.List;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zul.ListModelList;

import bll.CoProduct.cpd_ListBll;

import Model.CoProductModel;

public class cpd_FeeRelationListController extends SelectorComposer<Component> {

	cpd_ListBll bll = new cpd_ListBll();
	int copr_id = Integer.parseInt(Executions.getCurrent().getArg()
			.get("copr_id").toString());
	private List<CoProductModel> list;

	@Init
	public void init() throws Exception {

		setList(new ListModelList<CoProductModel>(
				bll.getEclassBycoprid(copr_id)));
	}

	public List<CoProductModel> getList() {
		return list;
	}

	public void setList(List<CoProductModel> list) {
		this.list = list;
	}
}
