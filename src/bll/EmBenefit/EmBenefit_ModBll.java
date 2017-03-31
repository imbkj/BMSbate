package bll.EmBenefit;

import java.util.List;

import org.zkoss.zul.ListModelList;

import dal.EmBenefit.EmActy_SupplierSelectDal;
import dal.EmBenefit.EmBenefitDal;
import dal.EmBenefit.EmBenefitRelationDal;

import Model.EmActySupplierInfoModel;
import Model.EmBenefitModel;
import Model.EmBenefitRelationModel;

public class EmBenefit_ModBll {

	public List<EmBenefitModel> getList(Integer id) {
		List<EmBenefitModel> list = new ListModelList<>();
		EmBenefitDal dal = new EmBenefitDal();
		list = dal.getlistInfo(id);

		return list;
	}

	public List<EmBenefitModel> getNameList(String name, String name2) {
		List<EmBenefitModel> list = new ListModelList<>();
		EmBenefitDal dal = new EmBenefitDal();
		list = dal.getNamelistMod(name, name2);
		return list;
	}

	public Integer mod(EmBenefitModel ebfm) {
		Integer i = 0;
		EmBenefitDal dal = new EmBenefitDal();
		i = dal.mod(ebfm.getEmbf_id(), ebfm.getEmbf_name(),
				ebfm.getEmbf_start(), ebfm.getEmbf_notice(),
				ebfm.getEmbf_type(), ebfm.getEmbf_mold(), ebfm.getEmbf_cycle(),
				ebfm.getEmbf_unit(), ebfm.getEmbf_month(),
				ebfm.getEmbf_field(), ebfm.getEmbf_modname());
		return i;
	}

	// 查询产品对应供应商列表
	public List<EmBenefitRelationModel> getSupply(Integer id) {
		EmActy_SupplierSelectDal dal = new EmActy_SupplierSelectDal();
		List<EmBenefitRelationModel> list = new ListModelList<>();
		list = dal.getSupplyListById(id);

		return list;
	}

	// 查询对应服务类型的供应商列表
	public List<EmActySupplierInfoModel> getSupplyList(String s) {
		EmActy_SupplierSelectDal dal = new EmActy_SupplierSelectDal();
		List<EmActySupplierInfoModel> list = new ListModelList<>();
		EmActySupplierInfoModel em = new EmActySupplierInfoModel();
		em.setSupp_state(1);
		em.setTypes(s);
		list = dal.getSIList(em);
		return list;

	}

	// 更新产品与供应商关联
	public boolean updateLink(List<EmActySupplierInfoModel> list, Integer id) {
		boolean b = false;
		EmBenefitRelationDal dal = new EmBenefitRelationDal();
		for (EmActySupplierInfoModel m : list) {
			if (m.isChecked()) {
				Integer i1 = dal.mod(id, m.getSupp_id(), 1);
				Integer i2 = dal.add(id, m.getSupp_id());
				b = (i1 + i2) > 0 ? true : false;
				if (!b) {
					return false;
				}
			}
		}
		return b;
	}

	// 取消产品与供应商关联
	public boolean updateRelation(Integer id) {
		boolean b = false;
		EmBenefitRelationDal dal = new EmBenefitRelationDal();
		Integer i = dal.mod(id, 0);
		b = i > 0 ? true : false;
		return b;
	}

}
