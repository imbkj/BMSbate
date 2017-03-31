package bll.EmCommissionOutNew;

import service.WorkflowCore.WfBusinessService;
import dal.EmCommissionOutNew.EmCommissionOut_OperateDal;

public class EmCommissionOutNewImpl implements WfBusinessService {
	private EmCommissionOut_OperateDal ccsaDal = new EmCommissionOut_OperateDal();

	@Override
	public String[] Operate(Object[] obj) {
		String[] message = new String[5];
		String i = obj[0].toString();

		if (i == "1") {
			message = WtOutAdd(obj);
		} else if (i == "2") {
			message = WtOutChange(obj);
		} else if (i == "3") {
			message = WtOutChangeRe(obj);
		}else if (i == "4") {
			message = WtOutAddRe(obj);
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

	// 委托外地新增
	public String[] WtOutAdd(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		EmCommissionOut_OperateDal dal = new EmCommissionOut_OperateDal();
		result = dal.Add_Insurant(obj[1].toString(), obj[2].toString(),
				obj[3].toString(), obj[4].toString(), obj[5].toString(),
				obj[6].toString(), obj[7].toString(), obj[8].toString(),
				obj[9].toString(), obj[10].toString(), obj[11].toString(),
				obj[12].toString(), obj[13].toString(), obj[14].toString(),
				obj[15].toString());
		if (result != 0) {
			message[0] = "1";
			message[1] = "委托外地新增成功!";
			message[2] = String.valueOf(result);
			message[3] = "ci_insurant";
			message[4] = "委托外地新增";
		} else {
			message[0] = "0";
			message[1] = "委托外地新增失败!";
		}
		return message;
	}

	// 委托外地调整
	public String[] WtOutChange(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		EmCommissionOut_OperateDal dal = new EmCommissionOut_OperateDal();
		result = dal.Change_WtOut(obj[1].toString(), obj[2].toString(),
				obj[3].toString(), obj[4].toString(), obj[5].toString(),
				obj[6].toString(), obj[7].toString(),obj[8].toString(),obj[9].toString());
		if (result != 0) {
			message[0] = "1";
			message[1] = "委托外地调整成功!";
			message[2] = String.valueOf(result);
			message[3] = "WtOut";
			message[4] = "委托外地调整";
		} else {
			message[0] = "0";
			message[1] = "委托外地调整失败!";
		}
		return message;
	}

	// 委托外地调整重新提交
	public String[] WtOutChangeRe(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		EmCommissionOut_OperateDal dal = new EmCommissionOut_OperateDal();
		result = dal.ChangeRe_WtOut(obj[1].toString(), obj[2].toString(),
				obj[3].toString(), obj[4].toString(), obj[5].toString(),
				obj[6].toString(), obj[7].toString(), obj[8].toString(), obj[9].toString(), obj[10].toString(), obj[11].toString());
		if (result != 0) {
			message[0] = "1";
			message[1] = "委托外地调整成功!";
			message[2] = String.valueOf(result);
			message[3] = "WtOut";
			message[4] = "委托外地调整";
		} else {
			message[0] = "0";
			message[1] = "委托外地调整失败!";
		}
		return message;
	}
	
	// 委托外地新增重新提交
		public String[] WtOutAddRe(Object[] obj) {
			int result = 0;
			String[] message = new String[5];
			EmCommissionOut_OperateDal dal = new EmCommissionOut_OperateDal();
			result = dal.AddRe_WtOut(obj[1].toString(), obj[2].toString(),
					obj[3].toString(), obj[4].toString(), obj[5].toString(),
					obj[6].toString(), obj[7].toString(), obj[8].toString(), obj[9].toString(), obj[10].toString(), obj[11].toString());
			if (result != 0) {
				message[0] = "1";
				message[1] = "委托外地新增成功!";
				message[2] = String.valueOf(result);
				message[3] = "WtOut";
				message[4] = "委托外地新增";
			} else {
				message[0] = "0";
				message[1] = "委托外地新增失败!";
			}
			return message;
		}
}
