package Controller.EmBenefit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import Model.CoBaseModel;
import bll.EmBenefit.EmActy_SurveyInfoSelectBll;

public class EmActy_SurveyEmbaseListController {
	private EmActy_SurveyInfoSelectBll bll=new EmActy_SurveyInfoSelectBll();
	private List<CoBaseModel> list=bll.getSurveyCobaseInfo("");
	
	@Command
	public void openzul(@BindingParam("model") CoBaseModel model,@BindingParam("url") String url)
	{
		Map map=new HashMap<>();
		map.put("model", model);
		Window window=(Window)Executions.createComponents(url, null, map);
		window.doModal();
	}
	public List<CoBaseModel> getList() {
		return list;
	}
	public void setList(List<CoBaseModel> list) {
		this.list = list;
	}
	
	
}
