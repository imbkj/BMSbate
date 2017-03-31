package Controller.Archives;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import bll.Archives.Archive_FeeManagerBll;

import Model.EmArchivePaymentModel;
import Model.EmArchiveRemarkModel;

public class Archive_addFeeEditController {
	private EmArchivePaymentModel epm = (EmArchivePaymentModel) Executions
			.getCurrent().getArg().get("epm");
	private List<EmArchiveRemarkModel> rmList = new ListModelList<>();

	private Archive_FeeManagerBll bll = new Archive_FeeManagerBll();
	
	private Window win;

	public Archive_addFeeEditController() {
		rmList = bll.remarklist(epm.getEmap_id());
	}

	@Command("winC")
	public void winC(@BindingParam("a") Window winD) {
		win = winD;
	}

	public EmArchivePaymentModel getEpm() {
		return epm;
	}

	public void setEpm(EmArchivePaymentModel epm) {
		this.epm = epm;
	}

	public List<EmArchiveRemarkModel> getRmList() {
		return rmList;
	}

	public void setRmList(List<EmArchiveRemarkModel> rmList) {
		this.rmList = rmList;
	}

}
