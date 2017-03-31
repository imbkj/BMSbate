package Controller.EmBenefit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import bll.EmBenefit.EmBenefit_comitListBll;

import Model.EmActyWarehouseHistoryModel;
import Model.EmActyWarehouseModel;

public class EmActy_WarehouseHistoryController {
	private EmActyWarehouseModel m =(EmActyWarehouseModel) Executions.getCurrent().getArg()
			.get("wasemodel");
	private EmBenefit_comitListBll bll=new EmBenefit_comitListBll();
	private List<EmActyWarehouseHistoryModel> list=bll.getEmActyWarehouseHistory(" and hsry_wase_id="+m.getWase_id()+" and hsry_state=1");
	
	@Command
	public void edit(@BindingParam("model") EmActyWarehouseHistoryModel model)
	{
		Map map=new HashMap<>();
		map.put("model", model);
		map.put("m", m);
		Window window=(Window)Executions.createComponents("EmActy_WarehouseHistoryEdit.zul", null, map);
		window.doModal();
	}
	public List<EmActyWarehouseHistoryModel> getList() {
		return list;
	}
	public void setList(List<EmActyWarehouseHistoryModel> list) {
		this.list = list;
	}
	
}
