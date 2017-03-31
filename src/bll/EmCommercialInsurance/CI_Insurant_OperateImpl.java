package bll.EmCommercialInsurance;

import service.WorkflowCore.WfBusinessService;
import dal.EmCommercialInsurance.CI_Insurant_OperateDal;

public class CI_Insurant_OperateImpl implements WfBusinessService {
	private CI_Insurant_OperateDal ccsaDal = new CI_Insurant_OperateDal();

	@Override
	public String[] Operate(Object[] obj) {
		String[] message = new String[5];
		String i = obj[0].toString();

		if (i == "1") {
			message = AddInsurant(obj);
		} else if (i == "2") {
			message = AutInsurant(obj);
		} else if (i == "3") {
			message = AutUPInsurant(obj);
		} else if (i == "4") {
			message = OutInsurant(obj);
		} else if (i == "5") {
			message = ChangeInsurant(obj);
		} else if (i == "6") {
			message = ApplyInsurant(obj);
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

	// 商业保险新增
	public String[] AddInsurant(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		CI_Insurant_OperateDal dal = new CI_Insurant_OperateDal();
		result = dal.Add_Insurant(obj[1].toString(), obj[2].toString(),
				obj[3].toString(), obj[4].toString(), obj[5].toString(),
				obj[6].toString(), obj[7].toString(), obj[8].toString(),
				obj[9].toString(), obj[10].toString(), obj[11].toString(),
				obj[12].toString(),obj[13].toString(),obj[14].toString(),obj[15].toString());
		if (result != 0) {
			message[0] = "1";
			message[1] = "商业保险新增成功!";
			message[2] = String.valueOf(result);
			message[3] = "ci_insurant";
			message[4] = "商业保险新增";
		} else {
			message[0] = "0";
			message[1] = "商业保险新增失败!";
		}
		return message;
	}
	
	// 商业保险信息变更
		public String[] ChangeInsurant(Object[] obj) {
			int result = 0;
			String[] message = new String[5];
			CI_Insurant_OperateDal dal = new CI_Insurant_OperateDal();
			result = dal.Change_Insurant(obj[1].toString(), obj[2].toString(),
					obj[3].toString(), obj[4].toString(), obj[5].toString(),
					obj[6].toString(), obj[7].toString(), obj[8].toString());
			if (result != 0) {
				message[0] = "1";
				message[1] = "商业保险信息变更成功!";
				message[2] = String.valueOf(result);
				message[3] = "ci_insurant";
				message[4] = "商业保险信息变更";
			} else {
				message[0] = "0";
				message[1] = "商业保险信息变更失败!";
			}
			return message;
		}
		
		// 商业保险信息变更
				public String[] ApplyInsurant(Object[] obj) {
					int result = 0;
					String[] message = new String[5];
					CI_Insurant_OperateDal dal = new CI_Insurant_OperateDal();
					result = dal.Apply_Insurant(obj[1].toString(), obj[2].toString(),
							obj[3].toString(),obj[4].toString());
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

	// 商业保险新增审核
	public String[] AutInsurant(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		CI_Insurant_OperateDal dal = new CI_Insurant_OperateDal();

		result = dal.Aut_Insurant(obj[1].toString());
		if (result != 0) {
			message[0] = "1";
			message[1] = "商业保险新增审核成功!";
			message[2] = String.valueOf(result);
			message[3] = "emcompactup";
			message[4] = "商业保险新增审核";
		} else {
			message[0] = "0";
			message[1] = "商业保险新增审核失败!";
		}
		return message;
	}

	// 商业保险新增申报
	public String[] AutUPInsurant(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		CI_Insurant_OperateDal dal = new CI_Insurant_OperateDal();
		result = dal.AutUP_Insurant(obj[1].toString());
		// System.out.println(obj[1].toString());
		if (result != 0) {
			message[0] = "1";
			message[1] = "商业保险新增申报成功!";
			message[2] = String.valueOf(result);
			message[3] = "emcompactup";
			message[4] = "商业保险新增申报";
		} else {
			message[0] = "0";
			message[1] = "商业保险新增申报失败!";
		}
		return message;
	}

	// 商业保险停缴
	public String[] OutInsurant(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		CI_Insurant_OperateDal dal = new CI_Insurant_OperateDal();
		result = dal.Out_Insurant(obj[1].toString(), obj[2].toString(),
				obj[3].toString());
		if (result != 0) {
			message[0] = "1";
			message[1] = "商业保险停缴成功!";
			message[2] = String.valueOf(result);
			message[3] = "ci_insurant";
			message[4] = "商业保险停缴";
		} else {
			message[0] = "0";
			message[1] = "商业保险新增失败!";
		}
		return message;
	}
}
