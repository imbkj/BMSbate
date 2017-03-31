package bll.EmCommercialInsurance;

import service.WorkflowCore.WfBusinessService;
import dal.EmCommercialInsurance.CI_Insurant_ApplyOperateDal;

public class CI_Insurant_ApplyOperateImpl implements WfBusinessService {
	private CI_Insurant_ApplyOperateDal ccsaDal = new CI_Insurant_ApplyOperateDal();

	@Override
	public String[] Operate(Object[] obj) {
		String[] message = new String[5];
		String i = obj[0].toString();

		if (i == "6") {
			message = ApplyInsurant(obj);
		}else if(i == "1"){
			message = ApplyInsurantUp(obj);
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
			ccsaDal.upinsurantid(dataId, tapr_id);
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

	// 商业保险信息申请
	public String[] ApplyInsurant(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		CI_Insurant_ApplyOperateDal dal = new CI_Insurant_ApplyOperateDal();
		result = dal.Apply_Insurant(obj[1].toString(), obj[2].toString(),
				obj[3].toString(), obj[4].toString(), obj[5].toString(), obj[6].toString());
		if (result != 0) {
			message[0] = "1";
			message[1] = "商业保险连带人申请成功!";
			message[2] = String.valueOf(result);
			message[3] = "ci_insurant";
			message[4] = "商业保险连带人申请";
		} else {
			message[0] = "0";
			message[1] = "商业保险连带人申请失败!";
		}
		return message;
	}
	
	// 商业保险信息申请
		public String[] ApplyInsurantUp(Object[] obj) {
			int result = 0;
			String[] message = new String[5];
			CI_Insurant_ApplyOperateDal dal = new CI_Insurant_ApplyOperateDal();
			result = dal.Apply_InsurantUp(obj[1].toString());
			if (result != 0) {
				message[0] = "1";
				message[1] = "商业保险连带人申请成功!";
				message[2] = String.valueOf(result);
				message[3] = "ci_insurant";
				message[4] = "商业保险连带人申请";
			} else {
				message[0] = "0";
				message[1] = "商业保险连带人申请失败!";
			}
			return message;
		}

}
