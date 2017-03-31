package Controller.EmBenefit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import bll.EmBenefit.EmWelfare_ListBll;

import Model.EmBenefitModel;
import Model.EmWelfareModel;

public class EmWelfare_ListController {
	private List<EmWelfareModel> list = new ListModelList<>();
	private List<EmBenefitModel> itemList = new ListModelList<>();
	private EmWelfareModel ewmo = new EmWelfareModel();
	private EmWelfare_ListBll bll = new EmWelfare_ListBll();
	private Window win = (Window) Path.getComponent("/winEmp");

	public EmWelfare_ListController() {
		setList(ewmo);
		setItemList();
	}

	@Command("mod")
	@NotifyChange("list")
	public void mod(@BindingParam("a") EmWelfareModel em) {

	}

	@Command("look")
	public void look(@BindingParam("a") EmWelfareModel em) {
		Map map = new HashMap();
		map.put("daid", em.getEmwf_embf_id());
		if (em.getEmwf_name() != null) {
			map.put("name", em.getEmwf_name());
		} else {
			map.put("name", "");
		}
	
		if (em.getEmwf_sortid() != null) {
			map.put("sortid", em.getEmwf_sortid());
		} else {
			map.put("sortid", "");
		}
		
		System.out.println(em.getEmwf_embf_id());
		Window window = (Window) Executions.createComponents(
				"EmWelfare_Detail.zul", null, map);
		window.doModal();

	}

	@Command("Search")
	@NotifyChange("list")
	public void Search() {
		EmWelfareModel ewm = new EmWelfareModel();
		Textbox tbcompany = (Textbox) win.getFellow("company");

		Combobox cbitem = (Combobox) win.getFellow("item");

		if (tbcompany.getValue() != null && !tbcompany.getValue().equals("")) {
			ewm.setEmwf_company(tbcompany.getValue());
		}

	}

	public List<EmWelfareModel> getList() {
		return list;
	}

	public void setList(EmWelfareModel ewm) {
		this.list = bll.getList(ewm);
	}

	public List<EmBenefitModel> getItemList() {
		return itemList;
	}

	public void setItemList() {
		this.itemList = bll.getitemList();
	}

}
