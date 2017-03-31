package Controller.Workflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import bll.Workflow.WfDeploymentListBll;

import Model.WfBusinessModel;
import Model.WfDefinationModel;

public class WfDeployment_ListController {
	private List<WfBusinessModel> buList;
	private List<WfDefinationModel> deList;
	private WfDeploymentListBll bll = new WfDeploymentListBll();
	private Window win = (Window) Path.getComponent("/flowWin");

	public WfDeployment_ListController() {
		buList = bll.getBusiness();
	}

	@Command("search")
	@NotifyChange("buList")
	public void search() {
		Textbox tb = (Textbox) win.getFellow("buName");
		buList = bll.getBusinessByName(tb.getValue());
	}

	@Command("selDe")
	@NotifyChange("deList")
	public void selDe(@BindingParam("selectitem") Listitem selectitem) {
		try {
			int wfbu_id = ((WfBusinessModel) selectitem.getValue())
					.getWfbu_id();
			deList = bll.getDefinationByBid(wfbu_id);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command("add")
	public void add(@BindingParam("id") String id,
			@BindingParam("name") String name) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("wfbu_id", id);
		map.put("wfbu_name", name);
		Window window = (Window) Executions.createComponents(
				"WfDeployment_Add.zul", null, map);
		window.doModal();
	}

	public List<WfBusinessModel> getBuList() {
		return buList;
	}

	public List<WfDefinationModel> getDeList() {
		return deList;
	}

	

}
