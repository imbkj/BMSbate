package Controller.EmBenefit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import Model.EmActyWarehouseModel;
import bll.EmBenefit.EmBenefit_comitListBll;

public class EmActy_WarehouseListController {
	private EmBenefit_comitListBll bll = new EmBenefit_comitListBll();
	private List<EmActyWarehouseModel> list = bll.getEmActyWarehouse("");

	// 打开记录
	@Command
	public void openzul(@BindingParam("model") EmActyWarehouseModel model) {
		Map map = new HashMap<>();
		map.put("model", model);
		Window window = (Window) Executions.createComponents(
				"EmActy_WarehouseNotes.zul", null, map);
		window.doModal();
	}

	// 打开记录
	@Command
	@NotifyChange("list")
	public void edit(@BindingParam("model") EmActyWarehouseModel model) {
		Map map = new HashMap<>();
		map.put("model", model);
		Window window = (Window) Executions.createComponents(
				"EmActy_WarehouseEdit.zul", null, map);
		window.doModal();
		list = bll.getEmActyWarehouse("");
	}

	public EmBenefit_comitListBll getBll() {
		return bll;
	}

	public void setBll(EmBenefit_comitListBll bll) {
		this.bll = bll;
	}

	public List<EmActyWarehouseModel> getList() {
		return list;
	}

	public void setList(List<EmActyWarehouseModel> list) {
		this.list = list;
	}

}
