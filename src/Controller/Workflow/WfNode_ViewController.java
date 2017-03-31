package Controller.Workflow;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import bll.Workflow.WfNode_ViewBll;
public class WfNode_ViewController extends SelectorComposer<Component>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int wfno_wfde_id = Integer.parseInt(Executions.getCurrent().getArg()
			.get("id").toString());
	private List<String> nodeList;

	public WfNode_ViewController() {
		setNodeList();
	}
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
	}
	public List<String> getNodeList() {
		return nodeList;
	}
	private void setNodeList() {
		WfNode_ViewBll bll = new WfNode_ViewBll();
		this.nodeList = bll.getNodeName(wfno_wfde_id);
	}


}
