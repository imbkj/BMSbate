package bll.EmSheBao;

import dal.EmSheBao.Emsi_OperateDal;
import service.WorkflowCore.WfBusinessService;

public class Emsc_editChangeSZSIImpl implements WfBusinessService {

	@Override
	public String[] Operate(Object[] obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] ReturnOperate(Object[] obj) {
		String[] message = new String[5];
		try {
			if ("1".equals(obj[0].toString())) {
				Emsi_OperateBll bll = new Emsi_OperateBll();
				message = bll.revokeChangeReturnOp(
						Integer.parseInt(obj[1].toString()),
						Integer.parseInt(obj[2].toString()), obj[3].toString(),
						obj[4].toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}

	@Override
	public String[] SkipOperate(Object[] obj) {
		String[] message = new String[5];
		message[0] = "1";
		message[1] = obj[1] + "成功";
		message[2] = obj[0].toString();
		message[3] = obj[2].toString();
		message[4] = obj[1].toString();
		return message;
	}

	@Override
	public String[] StopOperate(Object[] obj) {
		String[] message = new String[5];
		try {
			if ("1".equals(obj[0].toString())) {
				Emsi_OperateBll bll = new Emsi_OperateBll();
				message = bll.DelChangeStopTaskOp(
						Integer.parseInt(obj[1].toString()),
						Integer.parseInt(obj[2].toString()), obj[3].toString(),
						obj[4].toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}

	@Override
	public Boolean UpdateTaprid(int dataId, int tapr_id, int state) {
		Emsi_OperateDal dal = new Emsi_OperateDal();
		return dal.upEmShebaoChangeSZSITaprId(tapr_id, dataId);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

}
