package bll.EmBenefit;

import impl.WorkflowCore.WfOperateImpl;

import java.util.List;

import org.zkoss.zul.ListModelList;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

import dal.EmBenefit.EmActyCompactDal;
import dal.EmBenefit.EmActy_SupplierSelectDal;

import Model.EmActyCompactModel;
import Model.EmActySupplierInfoModel;

public class EmActy_compactAddBll {
	public Integer add(EmActyCompactModel eacm) {
		/*
		WfOperateService wfs = new WfOperateImpl(new EmActy_compactAddImpl());
		eacm.setEaco_state(2);
		Object[] obj ={1,eacm};
		String[] str = wfs.AddTaskToNext(obj, "供应商合同新增",
				eacm.getEaco_name() + "("+eacm.getEaco_compactid()+")合同新增", 76,
				eacm.getEaco_addname(), "", 0, "");
				
		if (str[0].equals("1")) {
			return 1;
		}else {
			return 0;
		}
		*/
		EmActyCompactDal dal = new EmActyCompactDal();
		eacm.setEaco_state(2);
		Integer i = dal.add(eacm);
		return i;
	}

	// 获取合同号列表
	public List<EmActyCompactModel> getCompactList(String name, Boolean type) {
		List<EmActyCompactModel> list = new ListModelList<>();
		EmActyCompactDal dal = new EmActyCompactDal();
		list = dal.getCompactList(name, type);
		return list;

	}
	
	//获取供应商列表
	public List<EmActySupplierInfoModel> getNameList(String name,boolean type){
		List<EmActySupplierInfoModel> list = new ListModelList<>();
		EmActy_SupplierSelectDal dal = new EmActy_SupplierSelectDal();
		list = dal.getNameList(name, type);
		return list;
	}
}
