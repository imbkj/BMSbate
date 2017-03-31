package bll.SysMessage;

import java.util.ArrayList;
import java.util.List;

import dal.SysMessage.SysMessageTem_AddDal;

import Model.SysMessageModel;
import Model.WfClassModel;

public class SysMessageTem_AddBll {

	// 模板新增
	public SysMessageModel PubMessageTemAdd(SysMessageModel model)
			throws Exception {
		SysMessageModel model1 = new SysMessageModel();
		SysMessageTem_AddDal dal = new SysMessageTem_AddDal();
		model1 = dal.PubMessageTemAdd(model);
		return model1;
	}

	// 获取业务类型列表
	public List<WfClassModel> getwfclassList() {
		List<WfClassModel> list = new ArrayList<WfClassModel>();
		SysMessageTem_AddDal dal = new SysMessageTem_AddDal();
		list = dal.getwfclassList();
		return list;
	}
}
