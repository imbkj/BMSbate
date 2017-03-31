package bll.Workflow;

import java.util.List;
import dal.Workflow.WfNode_ViewDal;

public class WfNode_ViewBll {
	// 获取部门数据
	public List<String> getNodeName(int wfno_id) {
		WfNode_ViewDal dal = new WfNode_ViewDal();
		List<String> nodeList = dal.getNodeName(wfno_id);
		return nodeList;
	}
}
