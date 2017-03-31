package bll.EmHouse;

import Model.EmHouseChangeModel;
import Util.Log4jInit;

import dal.EmHouse.EmHouseChangeDal;
import service.WorkflowCore.WfBusinessService;

public class EmHouse_StopImpl implements WfBusinessService {

	@Override
	public String[] Operate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] message = new String[5];
		Integer i = 0;
		Log4jInit.toLog("公积金|停交|" + obj[0]);
		if (obj[0].equals("新增停交")) {
			EmHouseChangeModel em = new EmHouseChangeModel();
			em = (EmHouseChangeModel) obj[1];
			EmHouseChangeDal dal = new EmHouseChangeDal();
			Log4jInit.toLog("公积金|停交|ownmonth:" + em.getOwnmonth() + ",cid:"
					+ em.getCid() + ",gid:" + em.getGid() + ",addname:"
					+ em.getEmhc_addname() + ",ifdeclare:"
					+ em.getEmhc_ifdeclare() + "remark:"+em.getEmhc_remark());
			i = dal.stopEmhouse(em.getOwnmonth(), em.getCid(), em.getGid(),
					em.getEmhc_addname(), em.getEmhc_ifdeclare(),
					em.getEmhc_remark());
			Log4jInit.toLog("公积金|停交结果|" + i);
		} else if (obj[0].equals("跳过确认")) {
			EmHouseChangeDal dal = new EmHouseChangeDal();
			i = dal.auditData(Integer.valueOf(obj[1].toString()));
		}

		if (i > 0) {
			message[0] = "1";
			message[1] = "提交成功!";
			message[2] = i.toString();
			message[3] = "EmHouseChange";
			message[4] = "停交公积金";
		} else {
			message[0] = "0";
			message[1] = "修改失败!";

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
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Integer i = 0;
		EmHouseChangeDal dal = new EmHouseChangeDal();

		i = dal.updateTaprId(dataId, tapr_id);

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
