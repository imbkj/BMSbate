package bll.EmBenefit;

import java.util.List;

import org.zkoss.zul.ListModelList;

import dal.EmBenefit.EmActyCompactDal;
import dal.EmBenefit.EmActy_SupplierOperateDal;
import dal.EmBenefit.EmActy_SupplierSelectDal;

import Model.EmActyCompactModel;
import Model.EmActySupProductInfoModel;

public class EmActy_addProductBll {
	//查询产品合同
	public List<EmActyCompactModel> getcompactList(Integer id){
		List<EmActyCompactModel> list = new ListModelList<>();
		EmActyCompactDal dal = new EmActyCompactDal();
		list = dal.getListById(id);
		return list;
	}
	
	//查询产品列表
	public List<EmActySupProductInfoModel> getList(
			EmActySupProductInfoModel easm) {
		List<EmActySupProductInfoModel> list = new ListModelList<>();
		EmActy_SupplierSelectDal dal = new EmActy_SupplierSelectDal();

		list = dal.getProductList(easm);

		return list;
	}

	public Integer add(EmActySupProductInfoModel easm) {
		Integer i = 0;
		EmActy_SupplierOperateDal dal = new EmActy_SupplierOperateDal();
		i = dal.modProd(easm);

		return i;
	}
}
