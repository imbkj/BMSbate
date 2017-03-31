package bll.EmCommercialInsurance;

import java.sql.SQLException;

import service.WorkflowCore.WfBusinessService;
import dal.EmCommercialInsurance.CI_InsurantClaim_ListDal;
import dal.EmCommercialInsurance.CI_InsurantClaim_OperateDal;
import dal.EmCommercialInsurance.CI_Insurant_OperateDal;

public class CI_InsurantClaim_OperateImpl implements WfBusinessService {
	private CI_InsurantClaim_OperateDal ccsaDal = new CI_InsurantClaim_OperateDal();

	@Override
	public String[] Operate(Object[] obj) {
		String[] message = new String[5];
		String i = obj[0].toString();

		if (i == "1") {
			message = AddInsurantClaim(obj);
		} else if (i == "2") {
			message = AutInsurantClaim(obj);
		} else if (i == "3") {
			message = AutUPInsurantClaim(obj);
		} else if (i == "4") {
			message = AutOutInsurantClaim(obj);
		} else if (i == "5") {
			//System.out.println("555555");
			message = EditInsurantClaim(obj);
		} else if (i == "6") {
			message = DelInsurantClaim(obj);
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
		int result = 0;
		String[] message = new String[5];
		CI_InsurantClaim_OperateDal dal = new CI_InsurantClaim_OperateDal();
		result = dal.Del_InsurantClaim(obj[1].toString());
		// System.out.println(obj[1].toString());
		if (result != 0) {
			message[0] = "1";
			message[1] = "商业保险删除成功!";
			message[2] = String.valueOf(result);
			message[3] = "emcompactup";
			message[4] = "商业保险删除";
		} else {
			message[0] = "0";
			message[1] = "商业保险删除失败!";
		}
		return message;
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

	// 商保理赔新增
	public String[] AddInsurantClaim(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		CI_InsurantClaim_OperateDal dal = new CI_InsurantClaim_OperateDal();
		result = dal.Add_InsurantClaim(obj[1].toString(), obj[2].toString(),
				obj[3].toString(), obj[4].toString(), obj[5].toString(),
				obj[6].toString(), obj[7].toString(), obj[8].toString(),
				obj[9].toString(), obj[10].toString(), obj[11].toString(),
				obj[12].toString(), obj[13].toString(), obj[14].toString(),
				obj[15].toString(), obj[16].toString(), obj[17].toString(),
				obj[18].toString(), obj[19].toString(), obj[20].toString());
		if (result != 0) {
			message[0] = "1";
			message[1] = "商保理赔新增成功!";
			message[2] = String.valueOf(result);
			message[3] = "ci_insurant";
			message[4] = "商保理赔新增";
		} else {
			message[0] = "0";
			message[1] = "商保理赔新增失败!";
		}
		return message;
	}

	// 商业保险新增审核
	public String[] AutInsurantClaim(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		CI_InsurantClaim_OperateDal dal = new CI_InsurantClaim_OperateDal();
		result = dal.WaitAut_Insurant(obj[1].toString(), obj[2].toString(),
				obj[3].toString());
		if (result != 0) {
			message[0] = "1";
			message[1] = "商保理赔审核成功!";
			message[2] = String.valueOf(result);
			message[3] = "emcompactup";
			message[4] = "商保理赔审核";
		} else {
			message[0] = "0";
			message[1] = "商保理赔审核失败!";
		}
		return message;
	}

	// 商保处理中
	public String[] AutUPInsurantClaim(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		CI_InsurantClaim_OperateDal dal = new CI_InsurantClaim_OperateDal();
		result = dal.AutUP_Insurant(obj[1].toString(), obj[2].toString(),
				obj[3].toString());
		// System.out.println(obj[1].toString());
		if (result != 0) {
			message[0] = "1";
			message[1] = "商保处理中申报成功!";
			message[2] = String.valueOf(result);
			message[3] = "emcompactup";
			message[4] = "商保处理中申报";
		} else {
			message[0] = "0";
			message[1] = "商保处理中申报失败!";
		}
		return message;
	}

	// 商保退回提交
	public String[] AutOutInsurantClaim(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		CI_InsurantClaim_ListDal cl_dal = new CI_InsurantClaim_ListDal();
		result = cl_dal.getci_outaut(Integer.parseInt(obj[1].toString()));
		// System.out.println(obj[1].toString());
		if (result != 0) {
			message[0] = "1";
			message[1] = "商保理赔提交成功!";
			message[2] = String.valueOf(result);
			message[3] = "emcompactup";
			message[4] = "商保理赔";
		} else {
			message[0] = "0";
			message[1] = "商保理赔提交失败!";
		}
		return message;
	}

	// 商保理赔新增
	public String[] EditInsurantClaim(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		CI_InsurantClaim_OperateDal dal = new CI_InsurantClaim_OperateDal();
		result = dal.Edit_InsurantClaim(obj[1].toString(), obj[2].toString(),
				obj[3].toString(), obj[4].toString(), obj[5].toString(),
				obj[6].toString(), obj[7].toString(), obj[8].toString(),
				obj[9].toString(), obj[10].toString(), obj[11].toString(),
				obj[12].toString(), obj[13].toString(), obj[14].toString(),
				obj[15].toString(), obj[16].toString(), obj[17].toString(),
				obj[18].toString(), obj[19].toString(), obj[20].toString(),
				obj[21].toString());
		if (result != 0) {
			message[0] = "1";
			message[1] = "商保理赔新增成功!";
			message[2] = String.valueOf(result);
			message[3] = "ci_insurant";
			message[4] = "商保理赔新增";
		} else {
			message[0] = "0";
			message[1] = "商保理赔新增失败!";
		}
		return message;
	}

	// 商保理赔新增
	public String[] DelInsurantClaim(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		CI_InsurantClaim_OperateDal dal = new CI_InsurantClaim_OperateDal();
		result = dal.Del_InsurantClaim(obj[1].toString());
		if (result != 0) {
			message[0] = "1";
			message[1] = "商保理赔删除成功!";
			message[2] = String.valueOf(result);
			message[3] = "ci_insurant";
			message[4] = "商保理赔删除";
		} else {
			message[0] = "0";
			message[1] = "商保理赔删除失败!";
		}
		return message;
	}
}
