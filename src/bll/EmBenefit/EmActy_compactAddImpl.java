package bll.EmBenefit;

import java.text.SimpleDateFormat;
import java.util.Date;

import Model.EmActyCompactModel;

import dal.EmBenefit.EmActyCompactDal;

import service.WorkflowCore.WfBusinessService;

public class EmActy_compactAddImpl implements WfBusinessService {

	@Override
	public String[] Operate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] message = new String[5];
		Integer i = 0;
		EmActyCompactDal dal = new EmActyCompactDal();

		if (obj[0].toString().equals("1")) {
			EmActyCompactModel eacm = (EmActyCompactModel) obj[1];
			i = dal.add(eacm);
			message[4] = "新增合同";
		} else if (obj[0].toString().equals("2")) {
			EmActyCompactModel eacm = (EmActyCompactModel) obj[1];

			i = dal.mod(eacm);
			message[4] = "制作合同";

		} else if (obj[0].toString().equals("3")) {

			EmActyCompactModel eacm = (EmActyCompactModel) obj[1];
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			eacm.setEaco_auditdate(sdf.format(d));
			i = dal.mod(eacm);
			message[4] = "审核合同";
		} else if (obj[0].toString().equals("4")) {
			EmActyCompactModel eacm = (EmActyCompactModel) obj[1];
			i = dal.mod(eacm);
			message[4] = "签回合同";
		} else if (obj[0].toString().equals("5")) {
			EmActyCompactModel eacm = (EmActyCompactModel) obj[1];
			i = dal.mod(eacm);
			message[4] = "合同归档";
		}

		if (i > 0) {
			message[0] = "1";
			message[1] = "提交成功!";
			message[2] = i.toString();
			message[3] = "EmActyCompact";

		} else {
			message[0] = "0";
			message[1] = "修改失败!";

		}
		return message;
	}

	@Override
	public String[] ReturnOperate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] message = new String[5];
		Integer i = 0;
		EmActyCompactDal dal = new EmActyCompactDal();
		if (obj[0].equals(4)) {
			EmActyCompactModel eacm = (EmActyCompactModel) obj[1];

			i = dal.mod(eacm);
			if (i > 0) {

				message[0] = "1";
				message[1] = "操作成功!";
				message[2] = i.toString();
				message[3] = "EmActyCompact";
				message[4] = "退回合同";
			} else {
				message[0] = "0";
				message[1] = "修改失败!";
			}
		} else if (obj[0].equals(5)) {
			EmActyCompactModel em = (EmActyCompactModel) obj[1];
			i = dal.mod(em);
			if (i > 0) {
				message[0] = "1";
				message[1] = "操作成功!";
				message[2] = i.toString();
				message[3] = "EmActyCompact";
				message[4] = "撤回合同";
			} else {
				message[0] = "0";
				message[1] = "修改失败!";
			}
		} else if (obj[0].equals(6)) {
			EmActyCompactModel em = (EmActyCompactModel) obj[1];
			i = dal.mod(em);
			if (i > 0) {
				message[0] = "1";
				message[1] = "操作成功!";
				message[2] = i.toString();
				message[3] = "EmActyCompact";
				message[4] = "退回合同";
			} else {
				message[0] = "0";
				message[1] = "修改失败!";
			}
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
		String[] message = new String[5];
		Integer i = 0;
		EmActyCompactDal dal = new EmActyCompactDal();
		if (obj[0].equals(1)) {
			EmActyCompactModel eacm = (EmActyCompactModel) obj[1];
			i = dal.del(eacm.getEaco_id());
			if (i > 0) {
				message[0] = "1";
				message[1] = "操作成功!";
				message[2] = eacm.getEaco_id().toString();
				message[3] = "EmActyCompact";
				message[4] = "删除合同";
			} else {
				message[0] = "0";
				message[1] = "操作失败!";
			}
		}
		return message;
	}

	@Override
	public Boolean UpdateTaprid(int dataId, int tapr_id, int state) {
		// TODO Auto-generated method stub
		Integer i = 0;
		EmActyCompactDal dal = new EmActyCompactDal();
		try {
			i = dal.updateTaprId(dataId, tapr_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

}
