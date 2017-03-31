package Controller.EmResidencePermit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import bll.EmResidencePermit.Emrp_FeeSelectBll;

import Model.EmCAFFeeInfoModel;

public class Emrp_FeeInfoListController {
	private Emrp_FeeSelectBll bll=new Emrp_FeeSelectBll();
	private List<EmCAFFeeInfoModel> list=bll.getFeeInfoList("");
	
	
	@Command
	@NotifyChange("list")
	public void search(@BindingParam("number") String number)
	{
		String str="";
		if(number!=null&&!number.equals(""))
		{
			str=" and ecfi_cl_number='"+number+"'";
		}
		list=bll.getFeeInfoList(str);
	}
	
	@Command
	public void detail(@BindingParam("model") EmCAFFeeInfoModel model)
	{
		Map map=new HashMap<>();
		map.put("model", model);
		Window window=(Window)Executions.createComponents("Emrp_FeeInfo.zul", null, map);
		window.doModal();
	}
	public List<EmCAFFeeInfoModel> getList() {
		return list;
	}
	public void setList(List<EmCAFFeeInfoModel> list) {
		this.list = list;
	}
	
	
}
