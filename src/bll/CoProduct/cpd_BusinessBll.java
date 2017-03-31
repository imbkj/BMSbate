package bll.CoProduct;

import java.util.List;

import org.zkoss.zul.ListModelList;

import dal.CoProduct.cpd_List1Dal;
import dal.SystemControl.EmBuCenter_SelectDal;

import Model.CoPclassModel;
import Model.CoProductModel;
import Model.EmbaseBusinessCenterModel;

public class cpd_BusinessBll {
	// 获取产品列表
	public List<CoProductModel> getProductList(String typeId, String name,
			String buId) {
		List<CoProductModel> list = new ListModelList<>();
		cpd_List1Dal dal = new cpd_List1Dal();
		typeId = typeId.equals("") ? "%" : typeId;
		name = name + "%";

		list = dal.getcoproductList(typeId, name, buId);
		return list;
	}

	// 获取产品类型列表
	public List<CoPclassModel> getProductClassList() {
		List<CoPclassModel> list = new ListModelList<>();
		cpd_List1Dal dal = new cpd_List1Dal();
		list = dal.getCoproductTypeList();
		return list;
	}

	// 获取业务列表
	public List<EmbaseBusinessCenterModel> getBusinessList() {
		List<EmbaseBusinessCenterModel> list = new ListModelList<>();
		EmBuCenter_SelectDal dal = new EmBuCenter_SelectDal();
		list = dal.getEmbaseBusinessCenterInfo("");
		return list;
	}

	public Integer updateList(Integer coprId, Integer emceId) {
		Integer i = 0;
		cpd_List1Dal dal = new cpd_List1Dal();
		i = dal.updateCopr(coprId, emceId);

		return i;
	}

}
