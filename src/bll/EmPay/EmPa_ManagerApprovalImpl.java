package bll.EmPay;

import java.util.ArrayList;
import java.util.List;

import Model.EmPayModel;
import dal.EmPay.EmPa_OperateDal;
import dal.EmTax.EmTax_OperateDal;

public class EmPa_ManagerApprovalImpl implements EmPa_Approval {

	private EmPayModel model;
	public EmPa_ManagerApprovalImpl(EmPayModel model)
	{
		this.model=model;
	}
	@Override
	public Integer Approval() {
		EmPa_OperateDal dal=new EmPa_OperateDal();
		int k=dal.ManagerApproval(model);
		if (k>0) {
			// 获取支付号下所有数据
			List<EmPayModel> list = new ArrayList<EmPayModel>();
			EmPa_SelectBll epbll = new EmPa_SelectBll();
			list = epbll.getEmPayList(" and a.empa_number='"
					+ model.getEmpa_number() + "' and empa_state>0 and empa_state!=9");

			if (list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					EmTax_OperateDal eto_dal = new EmTax_OperateDal();
					eto_dal.createEmPayTax(list.get(i).getId());
				}
			}
			if (k>0) {
				log();
			}
		}
		return k;
	}
	@Override
	public Integer log() {
		// TODO Auto-generated method stub
		EmPa_OperateDal dal=new EmPa_OperateDal();
		String opeatecontent = "更改状态，支付号为："
				+ model.getEmpa_number() + "，角色类型为:总经理";
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
