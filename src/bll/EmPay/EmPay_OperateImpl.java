package bll.EmPay;

import service.WorkflowCore.WfBusinessService;
import dal.EmPay.EmPay_OperateDal;

public class EmPay_OperateImpl implements WfBusinessService {
	private EmPay_OperateDal ccsaDal = new EmPay_OperateDal();

	@Override
	public String[] Operate(Object[] obj) {
		String[] message = new String[5];
		String i = obj[0].toString();

		if (i == "1") {
			message = AddPay(obj);
		} else if (i == "2") {
			message = AutPay(obj);
		} else if (i == "3") {
			message = MgAutPay(obj);
		} else if (i == "4") {
			message = AutPayOk(obj);
		}

		return message;
	}

	@Override
	public String[] ReturnOperate(Object[] obj) {
		// TODO Auto-generated method stub
		return null;
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
			ccsaDal.uppayid(dataId, tapr_id);
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

	// 支付新增
	public String[] AddPay(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		EmPay_OperateDal dal = new EmPay_OperateDal();
		result = dal.Up_Pay(obj[1].toString(), obj[2].toString(),
				obj[3].toString(), obj[4].toString());
		if (result != 0) {
			message[0] = "1";
			message[1] = obj[4].toString() + "支付新增成功!";
			message[2] = String.valueOf(result);
			message[3] = "em_pay";
			message[4] = obj[4].toString() + "支付";
		} else {
			message[0] = "0";
			message[1] = obj[4].toString() + "支付失败!";
		}
		return message;
	}

	// 支付财务审核
	public String[] AutPay(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		EmPay_OperateDal dal = new EmPay_OperateDal();
		result = dal.Aut_Pay(Integer.parseInt(obj[1].toString()),
				obj[2].toString());
		if (result != 0) {
			message[0] = "1";
			message[1] = "支付财务审核成功!";
			message[2] = String.valueOf(result);
			message[3] = "em_pay";
			message[4] = "支付财务审核";
		} else {
			message[0] = "0";
			message[1] = "支付财务审核失败!";
		}
		return message;
	}

	// 支付总经理审核
	public String[] MgAutPay(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		EmPay_OperateDal dal = new EmPay_OperateDal();
		result = dal.MgAut_Pay(Integer.parseInt(obj[1].toString()),
				obj[2].toString());
		if (result != 0) {
			message[0] = "1";
			message[1] = "支付总经理审核成功!";
			message[2] = String.valueOf(result);
			message[3] = "em_pay";
			message[4] = "支付总经理审核";
		} else {
			message[0] = "0";
			message[1] = "支付总经理审核失败!";
		}
		return message;
	}

	// 财务支付
	public String[] AutPayOk(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		EmPay_OperateDal dal = new EmPay_OperateDal();
		result = dal.Aut_PayOk(Integer.parseInt(obj[1].toString()),
				obj[2].toString(), obj[3].toString(), obj[4].toString(),
				obj[5].toString(), obj[6].toString(), obj[7].toString(),
				obj[8].toString(), obj[9].toString(), obj[10].toString(), obj[11].toString());
		if (result != 0) {
			message[0] = "1";
			message[1] = "支付通知支付成功!";
			message[2] = String.valueOf(result);
			message[3] = "em_pay";
			message[4] = "支付通知支付";
		} else {
			message[0] = "0";
			message[1] = "支付通知支付失败!";
		}
		return message;
	}
}
