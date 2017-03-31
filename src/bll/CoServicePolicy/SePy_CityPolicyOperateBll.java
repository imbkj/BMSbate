package bll.CoServicePolicy;

import Model.CoServicePolicyFileModel;
import Model.CoServicePolicyModel;
import Model.CoServicePolicyTitleModel;
import dal.CoServicePolicy.SePy_CityPolicyOperateDal;

public class SePy_CityPolicyOperateBll {
	private SePy_CityPolicyOperateDal dal = new SePy_CityPolicyOperateDal();

	// 新增服务政策
	public Integer CoServicePolicyAdd(CoServicePolicyModel model) {
		return dal.CoServicePolicyAdd(model);
	}

	// 修改服务政策
	public Integer CoServicePolicyUpdate(CoServicePolicyModel model) {
		return dal.CoServicePolicyUpdate(model);
	}

	// 新增服务政策文件
	public Integer CoServicePolicyFileAdd(CoServicePolicyFileModel model) {
		return dal.CoServicePolicyFileAdd(model);
	}

	// 修改服务政策文件
	public Integer CoServicePolicyFileUpdate(CoServicePolicyFileModel model) {
		return dal.CoServicePolicyFileUpdate(model);
	}

	// 根据Id把CoServicePolicy表的状态改为0
	public Integer updateCoServicePolicyState(Integer id) {
		return dal.updateCoServicePolicyState(id);
	}

	// 根据Id把CoServicePolicyFile表的状态改为0
	public Integer updateCoServicePolicyFileState(Integer id) {
		return dal.updateCoServicePolicyFileState(id);
	}

	// 服务政策标题新增
	public Integer CoServicePolicyTitleAdd(CoServicePolicyTitleModel model) {
		return dal.CoServicePolicyTitleAdd(model);
	}

	public Integer updateCoServicePolicyType(String note_type,
			Integer note_order, Integer note_id) {
		return dal.updateCoServicePolicyType(note_type, note_order, note_id);
	}

	// 根据Id把CoServicePolicyTitle表的状态改为0
	public Integer updateCoServicePolicyTitleState(Integer id) {
		return dal.updateCoServicePolicyTitleState(id);
	}
}
