package bll.EmBenefit;

import java.util.List;

import org.zkoss.zul.ListModelList;

import dal.EmBenefit.EmBenefitDal;

import Model.EmBenefitModel;

public class EmBenefit_addBll {
	public List<EmBenefitModel> getNameList(String name) {
		List<EmBenefitModel> list = new ListModelList<>();
		EmBenefitDal dal = new EmBenefitDal();
		list = dal.getNamelist(name);

		return list;
	}

	public Integer add(EmBenefitModel ebfm) {
		Integer i = 0;
		EmBenefitDal dal = new EmBenefitDal();
		i = dal.add(ebfm.getEmbf_name(), ebfm.getEmbf_start(),
				ebfm.getEmbf_type(), ebfm.getEmbf_mold(), ebfm.getEmbf_cycle(),
				ebfm.getEmbf_unit(), ebfm.getEmbf_month(),
				ebfm.getEmbf_field(), ebfm.getEmbf_addname());
		return i;
	}
}
