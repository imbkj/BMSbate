package Controller.CoProduct;

import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import bll.CoProduct.cpd_BusinessBll;

import Model.CoPclassModel;
import Model.CoProductModel;
import Model.EmbaseBusinessCenterModel;

public class cpd_BusinessRelController {
	private List<CoProductModel> productList = new ListModelList<>();
	private List<EmbaseBusinessCenterModel> businessList = new ListModelList<>();
	private List<CoPclassModel> pclassList = new ListModelList<>();
	private cpd_BusinessBll bll = new cpd_BusinessBll();

	private Window win = (Window) Path.getComponent("/winP");

	public cpd_BusinessRelController() {
		setProductList("", "", "");
		setPclassList();
		setBusinessList();
	}

	@Command("listFilter")
	@NotifyChange("productList")
	public void listFilter() {
		Combobox cbType = (Combobox) win.getFellow("proType");
		Textbox tbName = (Textbox) win.getFellow("proName");
		Combobox buName = (Combobox) win.getFellow("probn");
		String typeId = "";
		String name = "";
		String buId = "";

		if (cbType.getSelectedItem() != null) {
			typeId = cbType.getSelectedItem().getValue().toString();
		}

		if (tbName.getValue() != "") {
			name = tbName.getValue();
		}

		if (buName.getSelectedItem() != null) {
			buId = buName.getSelectedItem().getValue().toString();
		}
		setProductList(typeId, name, buId);

	}

	@Command("submit")
	public void submit(@BindingParam("a") CoProductModel cpm,
			@BindingParam("b") Combobox cb) {
		if (cb.getSelectedItem() != null) {

			cpm.setCopr_emce_id(Integer.valueOf(cb.getSelectedItem().getValue()
					.toString()));
		} else {
			cpm.setCopr_emce_id(0);
		}

		Integer i = bll.updateList(cpm.getCopr_id(), cpm.getCopr_emce_id());

		if (i == 0) {
			Messagebox.show("数据异常!", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
		//setProductList("", "", "");
	}
	
	@Command
	public void readinfo(@BindingParam("a") CoProductModel cm){
		if (cm.getBusinessList()==null) {
			cm.setBusinessList(businessList);
			BindUtils.postNotifyChange(null, null, cm, "businessList");
		}
	
	}

	public List<CoProductModel> getProductList() {
		return productList;
	}

	public void setProductList(String typeId, String name, String buId) {
		this.productList = bll.getProductList(typeId, name, buId);
	}

	public List<CoPclassModel> getPclassList() {
		return pclassList;
	}

	public void setPclassList() {
		this.pclassList = bll.getProductClassList();
	}

	public List<EmbaseBusinessCenterModel> getBusinessList() {
		return businessList;
	}

	public void setBusinessList() {
		this.businessList = bll.getBusinessList();
	}

}
