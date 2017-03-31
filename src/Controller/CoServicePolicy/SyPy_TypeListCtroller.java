package Controller.CoServicePolicy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import Model.CoServicePolicyTypeModel;
import bll.CoServicePolicy.SePy_CityPolicySelectBll;

public class SyPy_TypeListCtroller {
	private SePy_CityPolicySelectBll bll = new SePy_CityPolicySelectBll();
	private List<CoServicePolicyTypeModel> typelist = bll
			.getCoServicePolicyType("");

	// 打开新增标题页面
	@Command
	public void titleAdd(@BindingParam("model") CoServicePolicyTypeModel model) {
		Map map = new HashMap<>();
		map.put("model", model);
		Window window = (Window) Executions.createComponents(
				"SyPo_TitleAdd.zul", null, map);
		window.doModal();
	}

	// 打开修改类型页面
	@Command
	@NotifyChange("typelist")
	public void typeupdate(@BindingParam("model") CoServicePolicyTypeModel model) {
		Map map = new HashMap<>();
		map.put("model", model);
		Window window = (Window) Executions.createComponents(
				"SyPy_TypeUpdate.zul", null, map);
		window.doModal();
		typelist = bll
				.getCoServicePolicyType("");
	}

	public List<CoServicePolicyTypeModel> getTypelist() {
		return typelist;
	}

	public void setTypelist(List<CoServicePolicyTypeModel> typelist) {
		this.typelist = typelist;
	}

}
