package bll.Taskflow;

import java.util.List;

import dal.Taskflow.EmBaseMenulistSelectDal;

import Model.CobasedepartmentModel;
import Model.EmbaseModel;
import Model.cobasepositionModel;

public class EmBaseMenulistSelectBll {
	private EmBaseMenulistSelectDal dal = new EmBaseMenulistSelectDal();

	public List<EmbaseModel> getEmbaseLoginInfo(Integer gid) {
		return dal.getEmbaseLoginInfo(gid);
	}

	public List<Integer> getEmbaseMenuList(Integer gid) {
		return dal.getEmbaseMenuList(gid);
	}

	public Integer getEmbaId(Integer gid) {
		return dal.getEmbaId(gid);
	}

	// 职位表
	public List<cobasepositionModel> getcsidList(String str) {
		return dal.getcsidList(str);
	}

	// 部门表
	public List<CobasedepartmentModel> getcpidList(String str) {
		return dal.getcpidList(str);
	}

}
