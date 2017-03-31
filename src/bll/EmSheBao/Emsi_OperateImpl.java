package bll.EmSheBao;

import dal.EmSheBao.Emsi_OperateDal;
import service.WorkflowCore.WfBusinessService;

public class Emsi_OperateImpl implements WfBusinessService {

	@Override
	public String[] Operate(Object[] obj) {
		String[] message = new String[5];
		message[0] = "1";
		message[1] = obj[1] + "成功";
		message[2] = obj[0].toString();
		message[3] = "EmShebaoChange";
		message[4] = obj[1].toString();
		return message;
	}

	@Override
	public String[] ReturnOperate(Object[] obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] SkipOperate(Object[] obj) {
		String[] message = new String[5];
		message[0] = "1";
		message[1] = obj[1] + "成功";
		message[2] = obj[0].toString();
		message[3] = "EmShebaoChange";
		message[4] = obj[1].toString();
		return message;
	}

	@Override
	public String[] StopOperate(Object[] obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean UpdateTaprid(int dataId, int tapr_id, int state) {
		Emsi_OperateDal dal = new Emsi_OperateDal();
		return dal.upEmShebaoChangeTaprId(tapr_id, dataId);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

}
