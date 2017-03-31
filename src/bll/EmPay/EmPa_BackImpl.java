package bll.EmPay;

import dal.EmPay.EmPa_OperateDal;
import Model.EmPayBackLogModel;

//部门经理退回
public class EmPa_BackImpl implements EmPa_back {
	
	@Override
	public int empayBack(EmPayBackLogModel m) {
		EmPa_OperateDal dal=new EmPa_OperateDal();
		return dal.AddEmpayBackLog(m);
	}

}
