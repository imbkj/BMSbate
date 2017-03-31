package bll.EmCommissionOutNew;

import service.WorkflowCore.WfBusinessService;
import dal.EmCommissionOutNew.EmCommissionOut_AutOperateDal;

public class EmCommissionOut_AutOperateImpl implements WfBusinessService {
	private EmCommissionOut_AutOperateDal ccsaDal = new EmCommissionOut_AutOperateDal();

	@Override
	public String[] Operate(Object[] obj) {
		String[] message = new String[5];
		String i = obj[0].toString();
		if (i == "1") {
			message = Yc_Aut(obj);
		} else if (i == "2") {
			message = Ec_Aut(obj);
		}

		return message;
	}

	@Override
	public String[] ReturnOperate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] message = new String[5];
		String i = obj[0].toString();
		if (i == "3") {
			message = back(obj);
		}
		return message;
	}

	@Override
	public String[] SkipOperate(Object[] obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] StopOperate(Object[] obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean UpdateTaprid(int dataId, int tapr_id, int state) {
		try {
			ccsaDal.upoutid(dataId, tapr_id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

	// 一次确认
	public String[] Yc_Aut(Object[] obj) {
		int result = 0;
		System.out.println("aaaaa");
		System.out.println(obj[1].toString());
		String[] message = new String[5];
		EmCommissionOut_AutOperateDal dal = new EmCommissionOut_AutOperateDal();
		result = dal.Yc_Aut(obj[1].toString());
		if (result != 0) {
			message[0] = "1";
			message[1] = "委托单一次确认成功!";
			message[2] = String.valueOf(result);
			message[3] = "emcommissionout";
			message[4] = "委托单一次确认";
		} else {
			message[0] = "0";
			message[1] = "委托单一次确认失败!";
		}
		return message;
	}

	// 二次确认
	public String[] Ec_Aut(Object[] obj) {
		int result = 0;
		System.out.println("ec");
		System.out.println(obj[1].toString());
		String[] message = new String[5];
		EmCommissionOut_AutOperateDal dal = new EmCommissionOut_AutOperateDal();
		result = dal.Ec_Aut(obj[1].toString());
		if (result != 0) {
			message[0] = "1";
			message[1] = "委托单二次确认成功!";
			message[2] = String.valueOf(result);
			message[3] = "emcommissionout";
			message[4] = "委托单二次确认";
		} else {
			message[0] = "0";
			message[1] = "委托单二次确认失败!";
		}
		return message;
	}

	// 退回
	public String[] back(Object[] obj) {
		int result = 0;
		System.out.println("ec");
		System.out.println(obj[1].toString());
		String[] message = new String[5];
		EmCommissionOut_AutOperateDal dal = new EmCommissionOut_AutOperateDal();
		result = dal.back(obj[1].toString(),obj[2].toString());
		if (result != 0) {
			message[0] = "1";
			message[1] = "委托单退回成功!";
			message[2] = String.valueOf(result);
			message[3] = "emcommissionout";
			message[4] = "委托单退回";
		} else {
			message[0] = "0";
			message[1] = "委托单退回失败!";
		}
		return message;
	}
}
