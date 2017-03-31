package bll.EmBenefit;

import java.util.List;

import dal.EmBenefit.EmActy_SupplierSelectDal;

import Model.EmActySupContactManInfoModel;
import Model.EmActySupProductInfoModel;
import Model.EmActySupplierInfoModel;

public class EmActy_SupplierSelectBll {
	private EmActy_SupplierSelectDal dal=new EmActy_SupplierSelectDal();
	// 查询员工活动——供应商信息
	public List<EmActySupplierInfoModel> getEmActySupplierInfo(String str){
		return dal.getEmActySupplierInfo(str);
	}
	
	
	// 查询员工活动——供应商联系人信息
	public List<EmActySupContactManInfoModel> getEmActySupContactManInfo(String str){
		return dal.getEmActySupContactManInfo(str);
	}
	
	// 查询员工活动——供应商产品信息
	public List<EmActySupProductInfoModel> getEmActySupProductInfo(String str){
		return dal.getEmActySupProductInfo(str);
	}
	
	// 查询员工活动——查询已经关联了合同的产品
	public List<EmActySupProductInfoModel> getEmProductInfoList(String str,String embf_name) {
		return dal.getEmProductInfoList(str,embf_name);
	}
	
	// 查询员工活动——根据系那个亩名称选择已经被关联了的产品
	public List<EmActySupProductInfoModel> getEmProductInfo(String embf_name) {
		return dal.getEmProductInfo(embf_name);
	}
}
	
	
