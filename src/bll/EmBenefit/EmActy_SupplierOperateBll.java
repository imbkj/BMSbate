package bll.EmBenefit;

import dal.EmBenefit.EmActy_SupplierOperateDal;
import Model.EmActyProduceModel;
import Model.EmActySupContactManInfoModel;
import Model.EmActySupProductInfoModel;
import Model.EmActySupplierInfoModel;

public class EmActy_SupplierOperateBll {
	private EmActy_SupplierOperateDal dal = new EmActy_SupplierOperateDal();

	// 供应商信息新增
	public Integer EmActy_SupplierAdd(EmActySupplierInfoModel m) {
		return dal.EmActy_SupplierAdd(m);
	}

	// 供应商产品信息新增
	public Integer EmActySupProductInfoAdd(EmActySupProductInfoModel m) {
		return dal.EmActySupProductInfoAdd(m);
	}

	// 供应商联系人信息新增
	public Integer EmActySupContactManInfoAdd(EmActySupContactManInfoModel m) {
		return dal.EmActySupContactManInfoAdd(m);
	}

	// 修改供应商基本信息
	public int updateEmActySupplierInfo(EmActySupplierInfoModel model) {
		return dal.updateEmActySupplierInfo(model);
	}

	// 修改供应商联系人信息
	public int updateEmActySupContactManInfo(EmActySupContactManInfoModel model) {
		return dal.updateEmActySupContactManInfo(model);
	}

	// 修改供应商产品信息
	public int updateEmActySupProductInfo(EmActySupProductInfoModel model) {
		return dal.updateEmActySupProductInfo(model);
	}

	// 修改产品信息
	public Integer EmActyProduceEdit(EmActyProduceModel m) {
		return dal.EmActyProduceEdit(m);
	}

	// 删除联系人信息
	public Integer deletecoct(Integer id) {
		return dal.deletecoct(id);
	}

	// 删除报价信息
	public Integer deleteprod(Integer id) {
		return dal.deleteprod(id);
	}

}
