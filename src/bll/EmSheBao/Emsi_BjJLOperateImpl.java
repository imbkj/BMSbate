package bll.EmSheBao;

import service.WorkflowCore.WfBusinessService;
import dal.EmSheBao.Emsi_CheckOperateDal;
import dal.EmSheBao.Emsi_OperateDal;

public class Emsi_BjJLOperateImpl implements WfBusinessService {

	@Override
	public String[] Operate(Object[] obj) {
		String[] message = new String[5];
		try {
			// 雇员服务中心操作通过
			if ("0".equals(obj[0].toString())) {
				Emsi_CheckOperateDal dal = new Emsi_CheckOperateDal();
				int i = dal.upBjJLDeclare(Integer.parseInt(obj[1].toString()), 8,
						obj[2].toString(), obj[3].toString());
				if (i > 0) {
					message[0] = "1";
					message[1] = "操作成功。";
					message[2] = obj[1].toString();
					message[3] = "EmShebaoBJ";
					message[4] = "服务中心核收";
				} else {
					message[0] = "0";
					message[1] = "数据处理，失败。";
				}

			} else {
				message[0] = "1";
				message[1] = obj[1] + "成功";
				message[2] = obj[0].toString();
				message[3] = "EmShebaoBJJL";
				message[4] = obj[1].toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}

	@Override
	public String[] ReturnOperate(Object[] obj) {
		String[] message = new String[5];
		try {
			// 雇员服务中心操作退回
			if ("0".equals(obj[0].toString())) {
				Emsi_CheckOperateDal dal = new Emsi_CheckOperateDal();
				int i = dal.upBjJLDeclare(Integer.parseInt(obj[1].toString()), 3,
						obj[2].toString(), obj[3].toString());
				if (i > 0) {
					message[0] = "1";
					message[1] = "操作成功。";
					message[2] = obj[1].toString();
					message[3] = "EmShebaoBJJL";
					message[4] = "服务中心退回";
				} else {
					message[0] = "0";
					message[1] = "退回，数据处理时，失败。";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
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
		return null;
	}

	@Override
	public Boolean UpdateTaprid(int dataId, int tapr_id, int state) {
		Emsi_OperateDal dal = new Emsi_OperateDal();
		return dal.upEmShebaoBJJLTaprId(tapr_id, dataId);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}
}
