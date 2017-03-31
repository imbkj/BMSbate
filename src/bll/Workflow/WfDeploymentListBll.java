package bll.Workflow;

import java.util.List;

import Model.WfBusinessModel;
import Model.WfDefinationModel;
import dal.Workflow.WfDeploymentListDal;

public class WfDeploymentListBll {
	private WfDeploymentListDal dal = new WfDeploymentListDal();

	// 获取所有业务
	public List<WfBusinessModel> getBusiness() {
		return dal.getBusiness();
	}
	
	//搜索业务信息
	public List<WfBusinessModel> getBusinessByName(String name) {
		return dal.getDefinationByName(name);
	}

	// 根据业务ID查找流程
	public List<WfDefinationModel> getDefinationByBid(int wfbu_id) {
		return dal.getDefinationByBid(wfbu_id);
	}

	// 获取流程列表排除已选
	public List<WfDefinationModel> getDefinationExBid(int wfbu_id) {
		return dal.getDefinationExBid(wfbu_id);
	}

	// 流程部署
	public int AddWfDeployment(int wfbu_id, int wfde_id, String wfde_remark,
			String addname) {
		return dal.AddWfDeployment(wfbu_id, wfde_id, wfde_remark, addname);
	}
}
