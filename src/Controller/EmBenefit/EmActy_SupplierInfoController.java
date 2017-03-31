package Controller.EmBenefit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import bll.EmBenefit.EmActy_SupplierSelectBll;

import Model.EmActySupContactManInfoModel;
import Model.EmActySupplierInfoModel;

public class EmActy_SupplierInfoController {
	
	private EmActy_SupplierSelectBll bll = new EmActy_SupplierSelectBll();
	private List<EmActySupplierInfoModel> list = bll.getEmActySupplierInfo("");
	private List<EmActySupContactManInfoModel> conlist=new ArrayList<EmActySupContactManInfoModel>();
	
	@Command
	@NotifyChange("list")
	public void seach(@BindingParam("supname") String supname,
			@BindingParam("supaddress") String supaddress,
			@BindingParam("website") String website) {
		String str = "";
		if (supname != null && !supname.equals("") && supname != "") {
			str = str + " and supp_name like '%" + supname + "%'";
		}
		if (supaddress != null && !supaddress.equals("") && supaddress != "") {
			str = str + " and supp_address like '%" + supaddress + "%'";
		}
		if (website != null && !website.equals("") && website != "") {
			str = str + " and supp_website like '%" + website + "%'";
		}
		list = bll.getEmActySupplierInfo(str);
	}

	@Command
	@NotifyChange("list")
	public void openZUL(@BindingParam("model") EmActySupplierInfoModel model,
			@BindingParam("url") String url) {
		Map map = new HashMap<>();
		map.put("model", model);
		map.put("sup_id", model.getSupp_id());
		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
		list = bll.getEmActySupplierInfo("");
	}

	public List<EmActySupplierInfoModel> getList() {
		return list;
	}

	public void setList(List<EmActySupplierInfoModel> list) {
		this.list = list;
	}

	public List<EmActySupContactManInfoModel> getConlist() {
		return conlist;
	}

	public void setConlist(List<EmActySupContactManInfoModel> conlist) {
		this.conlist = conlist;
	}
	
}
