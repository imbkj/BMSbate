package bll.EmFinanceManage;

import dal.EmFinanceManage.emgt_OperateDal;
import Model.EmGatheringModel;

public class emgt_OperateBll {
	private emgt_OperateDal dal=new emgt_OperateDal();
	//个人收款新增
	public Integer EmGatheringAdd(EmGatheringModel m) {
		return dal.EmGatheringAdd(m);
	}
	
	//个人收款修改
	public Integer EmGatheringUpdate(EmGatheringModel m) {
		return dal.EmGatheringUpdate(m);
	}
}
