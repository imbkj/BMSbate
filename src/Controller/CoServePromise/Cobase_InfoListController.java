package Controller.CoServePromise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import bll.CoBase.CoBase_SelectBll;

import Model.CoBaseModel;

public class Cobase_InfoListController {
	private CoBase_SelectBll bll = new CoBase_SelectBll();
	private List<CoBaseModel> list=new ArrayList<CoBaseModel>();
	
	public Cobase_InfoListController()
	{
		list=bll.getCobaseinfo(" and a.cid not in(select cid from CoBaseServePromise)");
	}
	
	@Command
	@NotifyChange("list")
	public void addpromise(@BindingParam("model") CoBaseModel model)
	{
		Map map=new HashMap<>();
		map.put("cid", model.getCid());
		Window window=(Window)Executions.createComponents("CoPromise_Add.zul", null,map);
		window.doModal();
		list=bll.getCobaseinfo(" and a.cid not in(select cid from CoBaseServePromise)");
	}

	public List<CoBaseModel> getList() {
		return list;
	}

	public void setList(List<CoBaseModel> list) {
		this.list = list;
	}
	
	
}
