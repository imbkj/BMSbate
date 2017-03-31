package Controller.EmExpress;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import bll.EmExpress.EmExpressInfoSelectBll;

import Model.EmExpressContactInfoModel;

public class EmExpressContactInfoListController {
	private Integer gid = (Integer) Executions.getCurrent().getArg().get("gid");
	private EmExpressInfoSelectBll bll=new EmExpressInfoSelectBll();
	private List<EmExpressContactInfoModel> list=bll.getEmExpressContactInfoList(" and gid="+gid);
	
	
	//修改
	@Command
	@NotifyChange("list")
	public void openzul(@BindingParam("model") EmExpressContactInfoModel model)
	{
		Map map=new HashMap<>();
		map.put("model", model);
		Window window=(Window)Executions.createComponents("ExpressAddressInfoUpdate.zul", null, map);
		window.doModal();
		list=bll.getEmExpressContactInfoList(" and gid="+gid);
	}
	public List<EmExpressContactInfoModel> getList() {
		return list;
	}
	public void setList(List<EmExpressContactInfoModel> list) {
		this.list = list;
	}
	
	

}
