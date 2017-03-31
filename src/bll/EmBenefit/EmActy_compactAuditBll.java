package bll.EmBenefit;

import java.util.List;

import org.zkoss.zul.ListModelList;

import dal.EmBenefit.EmActyCompactDal;
import dal.Taskflow.TaskListDal;

import Model.EmActyCompactModel;
import Model.TaskListModel;

public class EmActy_compactAuditBll {
	public List<EmActyCompactModel> getCompactList(EmActyCompactModel eacm,
			Boolean desc) {
		List<EmActyCompactModel> list = new ListModelList<>();
		EmActyCompactDal dal = new EmActyCompactDal();
		list = dal.getList(eacm, desc);
		return list;
	}

	public List<EmActyCompactModel> getaddNameList(String name) {
		List<EmActyCompactModel> list = new ListModelList<>();
		EmActyCompactDal dal = new EmActyCompactDal();
		list = dal.getAddNameList(name);
		return list;
	}

	public List<TaskListModel> gettaclId(Integer id) {
		List<TaskListModel> list = new ListModelList<>();
		TaskListDal dal = new TaskListDal();
		list = dal.getListByTaprId(id);
		return list;
	}

	public Integer modReason(Integer id, String remark) {
		Integer i = 0;
		EmActyCompactDal dal = new EmActyCompactDal();
		EmActyCompactModel em = new EmActyCompactModel();
		em.setEaco_id(id);
		em.setEaco_backreason(remark);
		i = dal.mod(em);
		return i;
	}

	public Integer mod(EmActyCompactModel em) {
		Integer i = 0;
		EmActyCompactDal dal = new EmActyCompactDal();
		i = dal.mod(em);
		return i;
	}
}
