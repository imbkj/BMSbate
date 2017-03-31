package Controller.Workflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.Workflow.WfNode_ListBll;

import Model.WfNodeModel;

public class WfNode_ListController {
	private final Object wfde_id = Executions.getCurrent().getArg().get("id");
	// private final Object wfde_id = "1";
	private List<String[]> nodeList;
	private String titleName;
	private String definationName;

	public WfNode_ListController() {
		setNodeList();
		setDefinationName();
		setTitleName();
	}

	// 新增节点
	@Command("addNode")
	@NotifyChange("nodeList")
	public void addNode() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", (String) wfde_id);
		map.put("name", getDefinationName() + "->新增节点");
		Window window = (Window) Executions.createComponents("WfNode_Add.zul",
				null, map);
		window.doModal();
		setNodeList();
	}

	// 修改节点
	@Command("updateNode")
	@NotifyChange("nodeList")
	public void updateNode(@BindingParam("id") String wfno_id,
			@BindingParam("name") String name) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", wfno_id);
		map.put("name", name + "->更新节点");
		Window window = (Window) Executions.createComponents(
				"WfNode_Update.zul", null, map);
		window.doModal();
		setNodeList();
	}

	// 分配操作人
	@Command("disUser")
	@NotifyChange("nodeList")
	public void disUser(@BindingParam("id") String wfno_id,
			@BindingParam("name") String name) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", wfno_id);
		map.put("name", name + "->分配操作人");
		Window window = (Window) Executions.createComponents(
				"WfNode_DisUser.zul", null, map);
		window.doModal();
		setNodeList();
	}

	// 检测流程节点(查看流程图)
	@Command("checkNode")
	public void checkNode() throws Exception {
		WfNode_ListBll bll = new WfNode_ListBll();
		if (bll.checkHaveNode(Integer.parseInt((String) wfde_id))) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", (String) wfde_id);
			map.put("name", getDefinationName() + "->流程示意图");
			Window window = (Window) Executions.createComponents(
					"WfNode_View.zul", null, map);
			window.doModal();
		} else {
			Messagebox.show("未检测到有效节点。", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	public List<String[]> getNodeList() {
		return nodeList;
	}

	private void setNodeList() {
		WfNode_ListBll bll = new WfNode_ListBll();
		this.nodeList = bll.getNodeBasebyDefinationId(Integer
				.parseInt((String) wfde_id));
	}

	private String getDefinationName() {
		return definationName;
	}

	private void setDefinationName() {
		WfNode_ListBll bll = new WfNode_ListBll();
		this.definationName = bll.getDefinationNamebyId(Integer
				.parseInt((String) wfde_id));
	}

	public String getTitleName() {
		return titleName;
	}

	private void setTitleName() {
		this.titleName = getDefinationName() + "->节点管理";
	}

}
