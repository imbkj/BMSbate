package bll.EmBaseCompact;

import service.WorkflowCore.WfBusinessService;
import Model.EmBaseCompactModel;
import dal.EmBaseCompact.EmBaseCompact_OperateDal;
import dal.EmBaseCompact.EmBaseCompact_UploadDal;

public class EmBaseCompactSA_OperateImpl implements WfBusinessService {
	private EmBaseCompact_OperateDal ccsaDal = new EmBaseCompact_OperateDal();
	private EmBaseCompactModel reg;

	@Override
	public String[] Operate(Object[] obj) {
		String[] message = new String[5];
		String i = obj[0].toString();

		if (i == "1") {
			message = AddEmCompact(obj);
		} else if (i == "2") {
			message = AutEmCompact(obj);
		} else if (i == "3") {
			message = PrintEmCompact(obj);
		} else if (i == "4") {
			message = SignEmCompact(obj);
		} else if (i == "5") {
			message = FilingEmCompact(obj);
		} else if (i == "6") {
			message = GZEmCompact(obj);
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
			ccsaDal.addemcompactsaid(dataId, tapr_id);
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

	// 劳动合同变更
	public String[] AddEmCompact(Object[] obj) {
		String incept1;
		if (obj[2].toString().equals("")) {
			incept1 = "";
		} else {
			incept1 = obj[2].toString();
		}
		
		String incept2;
		if (obj[3].toString().equals("")) {
			incept2 = "";
		} else {
			incept2 = obj[3].toString();
		}
		
		String incept3;
		if (obj[4].toString().equals("")) {
			incept3 = "";
		} else {
			incept3 = obj[4].toString();
		}
		
		String incept4;
		if (obj[5].toString().equals("")) {
			incept4 = "";
		} else {
			incept4 = obj[5].toString();
		}
		
		String incept5;
		if (obj[6].toString().equals("")) {
			incept5 = "";
		} else {
			incept5 = obj[6].toString();
		}
			
		int result = 0;
		String[] message = new String[5];
		EmBaseCompact_UploadDal dal = new EmBaseCompact_UploadDal();
		result = dal.Add_EmcompactSA(obj[1].toString(), incept1,
				incept2, incept3, incept4, incept5,
				obj[7].toString(), obj[8].toString(),
				obj[9].toString(), obj[10].toString(), obj[11].toString(), obj[12].toString(),
				obj[13].toString());
		if (result != 0) {
			message[0] = "1";
			message[1] = "劳动合同变更成功!";
			message[2] = String.valueOf(result);
			message[3] = "emcompactadd";
			message[4] = "劳动合同变更";
		} else {
			message[0] = "0";
			message[1] = "劳动合同变更失败!";
		}
		return message;
	}

	// 劳动合同变更审核
	public String[] AutEmCompact(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		EmBaseCompact_UploadDal dal = new EmBaseCompact_UploadDal();
		result = dal.Aut_EmcompactSA(Integer.parseInt(obj[1].toString()),
				obj[2].toString());
		if (result != 0) {
			message[0] = "1";
			message[1] = "劳动合同变更审核成功!";
			message[2] = String.valueOf(result);
			message[3] = "emcompactup";
			message[4] = "劳动合同变更审核";
		} else {
			message[0] = "0";
			message[1] = "劳动合同变更审核失败!";
		}
		return message;
	}

	// 劳动合同变更打印
	public String[] PrintEmCompact(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		EmBaseCompact_UploadDal dal = new EmBaseCompact_UploadDal();
		result = dal.Print_EmcompactSA(Integer.parseInt(obj[1].toString()),
				obj[2].toString());
		if (result != 0) {
			message[0] = "1";
			message[1] = "劳动合同变更打印成功!";
			message[2] = String.valueOf(result);
			message[3] = "emcompactup";
			message[4] = "劳动合同变更打印";
		} else {
			message[0] = "0";
			message[1] = "劳动合同变更打印失败!";
		}
		return message;
	}
	
	// 劳动合同变更盖章
		public String[] GZEmCompact(Object[] obj) {
			int result = 0;
			String[] message = new String[5];
			EmBaseCompact_UploadDal dal = new EmBaseCompact_UploadDal();
			result = dal.Print_EmcompactSA(Integer.parseInt(obj[1].toString()),
					obj[2].toString());
			if (result != 0) {
				message[0] = "1";
				message[1] = "劳动合同变更盖章成功!";
				message[2] = String.valueOf(result);
				message[3] = "emcompactup";
				message[4] = "劳动合同变更盖章";
			} else {
				message[0] = "0";
				message[1] = "劳动合同变更盖章失败!";
			}
			return message;
		}
	

	// 劳动合同变更签回
	public String[] SignEmCompact(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		EmBaseCompact_UploadDal dal = new EmBaseCompact_UploadDal();
		result = dal.Sign_EmcompactSA(Integer.parseInt(obj[1].toString()),
				obj[2].toString(), obj[3].toString());
		if (result != 0) {
			message[0] = "1";
			message[1] = "劳动合同变更签回成功!";
			message[2] = String.valueOf(result);
			message[3] = "emcompactup";
			message[4] = "劳动合同变更签回";
		} else {
			message[0] = "0";
			message[1] = "劳动合同变更签回失败!";
		}
		return message;
	}

	// 劳动合同变更归档
	public String[] FilingEmCompact(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		EmBaseCompact_UploadDal dal = new EmBaseCompact_UploadDal();
		result = dal.Filing_EmcompactSA(Integer.parseInt(obj[1].toString()),
				obj[2].toString(), obj[3].toString());
		if (result != 0) {
			message[0] = "1";
			message[1] = "劳动合同变更归档成功!";
			message[2] = String.valueOf(result);
			message[3] = "emcompactup";
			message[4] = "劳动合同变更归档";
		} else {
			message[0] = "0";
			message[1] = "劳动合同变更归档失败!";
		}
		return message;
	}
}
