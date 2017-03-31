package bll.Workflow;

import java.util.List;

import org.zkoss.zul.ListModelList;

import Model.WfClassModel;
import Model.WfDefinationModel;
import dal.Workflow.WfClassDal;

public class WfClassBll {

	/**
	 * 获取任务类型名称
	 * @return
	 */
	public List<WfClassModel> getClassNameList() {
		List<WfClassModel> list = new ListModelList<WfClassModel>();
		WfClassDal dal = new WfClassDal();
		String sql = "select distinct wfcl_name from WfClass where wfcl_state=1";
		try {
			list = dal.getName(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 获取任务类型列表
	 * @return
	 */
	public List<WfClassModel> getClassList() {
		List<WfClassModel> list = new ListModelList<WfClassModel>();
		WfClassDal dal = new WfClassDal();
		String sql = "select * from WfClass where wfcl_state=1";
		try {
			list = dal.getClassModelsBySQL(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
