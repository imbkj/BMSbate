package bll.Workflow;

import java.util.List;
import dal.Workflow.WfNodeListDal;

public class WfNode_ListBll {
	// 根据流程ID获取节点信息
	public List<String[]> getNodeBasebyDefinationId(int id) {
		WfNodeListDal dal = new WfNodeListDal();
		List<String[]> nodeList = dal.getNodeBasebyDefination(id);
		return nodeList;
	}

	// 根据流程ID获取流程名称信息
	public String getDefinationNamebyId(int id) {
		WfNodeListDal dal = new WfNodeListDal();
		String DefinationName = dal.getDefinationNamebyId(id);
		return DefinationName;
	}
	// 检测是否存在有效节点
	public boolean checkHaveNode(int wfde_id) throws Exception{
		WfNodeListDal dal = new WfNodeListDal();
		return dal.checkHaveNode(wfde_id);
	}
}
