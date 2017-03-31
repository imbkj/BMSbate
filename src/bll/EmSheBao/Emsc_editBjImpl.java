package bll.EmSheBao;

import dal.EmSheBao.Emsi_OperateDal;
import service.WorkflowCore.WfBusinessService;

public class Emsc_editBjImpl implements WfBusinessService {

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
				Emsi_OperateDal dal = new Emsi_OperateDal();
				int i = dal.revokeBj(Integer.parseInt(obj[1].toString()),
						obj[2].toString());
				if (i == 1) {
					message[0] = "1";
					message[1] = "撤回社保补缴，操作成功。";
					message[2] = obj[1].toString();
					message[3] = "EmShebaoBJ";
					message[4] = "撤回社保补缴。";
				} else if (i == -1) {
					message[0] = "0";
					message[1] = "当前状态，无法撤回。";
				} else {
					message[0] = "0";
					message[1] = "撤回社保变更，操作失败。";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "撤回社保补缴，出错！";
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
		String[] message = new String[5];
		try {
			if ("1".equals(obj[0].toString())) {
				Emsi_OperateDal dal = new Emsi_OperateDal();
				int i = dal.DelBj(Integer.parseInt(obj[1].toString()));
				if (i == 1) {
					message[0] = "1";
					message[1] = "删除社保补缴，操作成功。";
					message[2] = obj[1].toString();
					message[3] = "EmShebaoBJ";
					message[4] = "删除社保补缴。";
				}else {
					message[0] = "0";
					message[1] = "删除社保变更，操作失败。";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "删除社保补缴，出错！";
		}
		return message;
	}

	@Override
	public Boolean UpdateTaprid(int dataId, int tapr_id, int state) {
		Emsi_OperateDal dal = new Emsi_OperateDal();
		return dal.upEmShebaoBJTaprId(tapr_id, dataId);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

}
