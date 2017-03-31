package bll.EmResidencePermit;

import Model.EmCAFFeeInfoModel;
import dal.EmResidencePermit.Emrp_FeeOperateDal;

public class Emrp_FeeOperateBll {
	private Emrp_FeeOperateDal dal=new Emrp_FeeOperateDal();
	// 生成支付明细
	public Integer EmCAFFeeInfoAdd(EmCAFFeeInfoModel m) {
		return dal.EmCAFFeeInfoAdd(m);
	}

}
