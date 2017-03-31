package bll.EmBenefit;

import Model.EmBenefitModel;
import Util.DateStringChange;

import impl.WorkflowCore.WfOperateImpl;

import java.util.Date;
import java.util.List;

import org.zkoss.zul.ListModelList;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import bll.EmHouse.EmHouse_addImpl;

import dal.CoProduct.cpd_List1Dal;
import dal.EmBenefit.EmBenefitDal;

public class EmBenefit_ManagerBll {
	// 查询列表
	public List<EmBenefitModel> getList(String name) {
		List<EmBenefitModel> list = new ListModelList<>();
		EmBenefitDal dal = new EmBenefitDal();
		list = dal.getlist(name);
		return list;
	}

	public Integer startMission(EmBenefitModel ebfm,String username) {
		Integer i = 0;
		EmBenefitDal dal = new EmBenefitDal();
		i = dal.start(ebfm.getEmbf_id(),username);

		return i;
	}

	// 删除项目
	public Integer delEmBenefit(Integer id, String name) {
		Integer i = 0;
		EmBenefitDal dal = new EmBenefitDal();
		i = dal.del(id, name);
		return i;
	}

	// 统计未关联的福利产品
	public Integer sumTotal() {
		Integer i = 0;
		cpd_List1Dal dal = new cpd_List1Dal();
		i = dal.getCoprTotalByEmbfId();

		return i;
	}
}
