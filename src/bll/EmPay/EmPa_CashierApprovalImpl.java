package bll.EmPay;

import Model.EmPayModel;
import dal.EmPay.EmPa_OperateDal;

public class EmPa_CashierApprovalImpl implements EmPa_Approval {
	private EmPayModel model;
	public EmPa_CashierApprovalImpl(EmPayModel model)
	{
		this.model=model;
	}
	
	@Override
	public Integer Approval() {
		EmPa_OperateDal dal=new EmPa_OperateDal();
		Integer k=dal.CashierApproval(model);
		if (k>0) {
			log();
		}
		return k;
	}

	@Override
	public Integer log() {
		// TODO Auto-generated method stub
		EmPa_OperateDal dal=new EmPa_OperateDal();
		String opeatecontent = "更改状态，支付号为："
				+ model.getEmpa_number() + "，角色类型为:出纳";
		Integer k = dal.AddEmpayLog(model.getEmpa_number(), opeatecontent);
		return k;
	}
	@Override
	public Integer addremark() {
		// TODO Auto-generated method stub
		EmPa_OperateDal dal=new EmPa_OperateDal();
		Integer k =dal.AddRemark(model.getId(), model.getRemark());
		return k;
	}



}
