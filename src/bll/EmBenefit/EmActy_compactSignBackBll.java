package bll.EmBenefit;

import impl.WorkflowCore.WfOperateImpl;

import java.util.List;

import org.zkoss.zul.ListModelList;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

import dal.EmBenefit.EmActyCompactDal;

import Model.EmActyCompactModel;

public class EmActy_compactSignBackBll {
	public List<EmActyCompactModel> getListInfo(Integer id) {
		List<EmActyCompactModel> list = new ListModelList<>();
		EmActyCompactDal dal = new EmActyCompactDal();
		list = dal.getListById(id);
		return list;
	}

	public Integer sign(EmActyCompactModel eacm) {
		Integer i = 0;

		WfOperateService wfs = new WfOperateImpl(new EmActy_compactAddImpl());
		Object[] obj = { 4, eacm };

		String[] str = wfs.PassToNext(obj, eacm.getEaco_tapr_id(),
				eacm.getEaco_modname(), "", 0, "");
		if (str[0].equals("1")) {
			return 1;
		}else {
			return 0;
		}
	}
}
