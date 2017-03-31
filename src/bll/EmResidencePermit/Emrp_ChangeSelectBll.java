package bll.EmResidencePermit;

import java.util.List;

import Model.EmResidencePermitChangeModel;
import dal.EmResidencePermit.Emrp_ChangeSelectDal;

public class Emrp_ChangeSelectBll {
	private Emrp_ChangeSelectDal dal = new Emrp_ChangeSelectDal();

	// 查询居住证转换信息
	public List<EmResidencePermitChangeModel> getChangeList(String str) {
		return dal.getChangeList(str);
	}

}
