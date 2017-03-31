package Controller.EmBenefit;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmBenefit.EmBenefit_ModBll;

import Model.EmActySupplierInfoModel;
import Model.EmBenefitModel;

public class EmActy_SupplyListController {
	private EmBenefitModel ebm = (EmBenefitModel) Executions.getCurrent()
			.getArg().get("ebm");

	private List<EmActySupplierInfoModel> supList = new ListModelList<>();
	
	private Window winC;

	private EmBenefit_ModBll bll = new EmBenefit_ModBll();

	public EmActy_SupplyListController() {
		if (ebm!=null && ebm.getEmbf_mold()!=null) {
			supList=bll.getSupplyList(ebm.getEmbf_mold());
		}
	}
	
	@Command
	public void winD(@BindingParam("a") Window w){
		winC=w;
	}
	
	@Command
	public void submit(){
		boolean b=false;
		for (EmActySupplierInfoModel m : supList) {
			if (m.isChecked()) {
				b=true;
			}
		}
		if (!b) {
			Messagebox.show("请选择供应商", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}
		if (!bll.updateLink(supList,ebm.getEmbf_id())) {
			Messagebox.show("操作失败", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}else {
			winC.detach();
		}
	}

	public List<EmActySupplierInfoModel> getSupList() {
		return supList;
	}

	public void setSupList(List<EmActySupplierInfoModel> supList) {
		this.supList = supList;
	}

}
