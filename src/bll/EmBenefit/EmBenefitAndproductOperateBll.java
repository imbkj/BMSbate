package bll.EmBenefit;

import Model.EmActySupProductInfoModel;
import dal.EmBenefit.EmBenefitAndproductOperateDal;

public class EmBenefitAndproductOperateBll {
	private EmBenefitAndproductOperateDal dal=new EmBenefitAndproductOperateDal();
	
	//项目关联产品
	public Integer EmBenefitAndproductAdd(EmActySupProductInfoModel m) {
		return dal.EmBenefitAndproductAdd(m);
	}
	
	//项目关联产品修改
	public Integer EmBenefitAndproductUpdate(EmActySupProductInfoModel m) {
		return dal.EmBenefitAndproductUpdate(m);
	}
}
