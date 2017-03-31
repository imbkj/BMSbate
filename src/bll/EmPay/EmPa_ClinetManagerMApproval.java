package bll.EmPay;

import Model.EmPayModel;
import R.AL;
import dal.EmPay.EmPa_OperateDal;

//客服经理审核
public class EmPa_ClinetManagerMApproval implements EmPa_Approval {
	private EmPayModel model;
	public EmPa_ClinetManagerMApproval(EmPayModel model)
	{
		this.model=model;
	}
	@Override
	public Integer Approval() {
		EmPa_OperateDal dal=new EmPa_OperateDal();
		Integer k=dal.ClientMApproval(model);
		if (k>0) {
			log();
			if (model.getRemark()!=null) {
				addremark();
			}
			
		}
		
		return k;
	}

	@Override
	public Integer log() {
		// TODO Auto-generated method stub
		EmPa_OperateDal dal=new EmPa_OperateDal();
		String opeatecontent = "更改状态，支付号为："
				+ model.getEmpa_number() + "，角色类型为:客服经理";
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
