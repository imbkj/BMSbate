package bll.EmBenefit;

import java.util.List;

import org.zkoss.zul.ListModelList;

import dal.EmBenefit.EmActyCompactDal;
import dal.EmBenefit.EmActy_SupplierOperateDal;
import dal.EmBenefit.EmActy_SupplierSelectDal;

import Model.EmActyCompactModel;
import Model.EmActySupProductInfoModel;

public class EmActy_compactModBll {
	public List<EmActyCompactModel> getlist(Integer id) {
		List<EmActyCompactModel> list = new ListModelList<>();
		EmActyCompactDal dal = new EmActyCompactDal();
		list = dal.getListById(id);

		return list;
	}

	// 获取合同号列表
	public List<EmActyCompactModel> getCompactList(String name, Boolean type) {
		List<EmActyCompactModel> list = new ListModelList<>();
		EmActyCompactDal dal = new EmActyCompactDal();
		list = dal.getCompactList(name, type);
		return list;

	}

	public List<EmActySupProductInfoModel> getProdList(
			EmActySupProductInfoModel easm) {
		List<EmActySupProductInfoModel> list = new ListModelList<>();
		EmActy_SupplierSelectDal dal = new EmActy_SupplierSelectDal();

		list = dal.getProductList(easm);
		return list;
	}

	public Integer mod(EmActyCompactModel eacm) {
		Integer i = 0;
		EmActyCompactDal dal = new EmActyCompactDal();
		i = dal.mod(eacm);

		return i;
	}

	public Integer modProd(EmActySupProductInfoModel easm) {
		Integer i = 0;
		EmActy_SupplierOperateDal dal = new EmActy_SupplierOperateDal();
		i = dal.modProd(easm);

		return i;
	}
}
