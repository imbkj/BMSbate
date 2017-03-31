package bll.EmSheBao;

import Model.EmShebaoUpdateModel;
import dal.EmSheBao.Emsi_OperateDal;
import service.WorkflowCore.WfBusinessService;

public class Emsc_editChangeImpl implements WfBusinessService {

	@Override
	public String[] Operate(Object[] obj) {
		String[] message = new String[5];
		String con = obj[1].toString();
		try {
			Emsi_OperateDal dal = new Emsi_OperateDal();
			int newChangeid = 0;
			if ("社保新增".equals(con)) {
				newChangeid = dal.newin((EmShebaoUpdateModel) obj[2],
						Integer.parseInt(obj[0].toString()));
			} else if ("社保调入".equals(con)) {
				newChangeid = dal.movein((EmShebaoUpdateModel) obj[2],
						Integer.parseInt(obj[0].toString()));
			}else if ("修改工资".equals(con)) {
				newChangeid = dal.upSalary((EmShebaoUpdateModel) obj[2],
						Integer.parseInt(obj[0].toString()));
			} else if ("社保停交".equals(obj[1].toString())) {
				newChangeid = dal.stop((EmShebaoUpdateModel) obj[2],
						Integer.parseInt(obj[0].toString()));
			}
			if (newChangeid > 0) {
				message[0] = "1";
				message[1] = con + ",成功";
				message[2] = obj[0].toString();
				message[3] = "EmShebaoChange";
				message[4] = con;
			} else if (newChangeid == -1) {
				message[0] = "0";
				message[1] = "已有该员工的" + con + "变更，无法提交！";
			} else {
				message[0] = "0";
				message[1] = con + ",失败！";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = con + ",出错！";
		}
		return message;
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
		message[3] = "EmShebaoChange";
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
		return dal.upEmShebaoChangeTaprId(tapr_id, dataId);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

}
