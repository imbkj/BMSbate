/*
 * 创建人：林少斌
 * 创建时间：2014-1-2
 * 用途：社保变更数据任务单Impl
 */
package bll.EmSheBao;

import service.WorkflowCore.WfBusinessService;
import Model.EmSheBaoChangeModel;
import Model.EmShebaoDeclareOKModel;
import dal.EmSheBao.EmSheBao_DOperateDal;
import dal.EmSheBao.Emsi_OperateDal;

public class EmSheBao_DOperateImpl implements WfBusinessService {
	EmSheBao_DOperateDal dal = new EmSheBao_DOperateDal();

	@Override
	public String[] Operate(Object[] obj) {
		String[] message = new String[5];
		String i = obj[0].toString();

		if (i == "0") {
			message = declareSingle(obj);
		} else if (i == "1") {
			message = declareSingleExcel(obj);
		} else if (i == "5") {
			message = declareChangeState(obj);
		} else if (i == "7") {
			message = declareSuccess(obj);
		}

		return message;
	}

	private String[] declareSuccess(Object[] obj) {
		int[] result;
		String[] message = new String[5];
		int id = Integer.parseInt(obj[1].toString());

		result = dal.declareSuccess((EmShebaoDeclareOKModel) obj[2]);
		if (result[0] != 1) {
			message[0] = "1";
			message[1] = "操作变更数据为“已申报”成功!";
			message[2] = String.valueOf(result[1]);
			message[3] = "EmShebaoChange";
			message[4] = "社保已申报";
		} else {
			message[0] = "0";
			message[1] = "操作变更数据为“已申报”失败!";
		}
		return message;
	}

	private String[] declareSingle(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		int id = Integer.parseInt(obj[1].toString());
		result = dal.declareSingle((EmSheBaoChangeModel) obj[2]);

		if (result == 0) {
			message[0] = "1";
			message[1] = "操作变更数据为“申报中”成功!";
			message[2] = String.valueOf(id);
			message[3] = "EmShebaoChange";
			message[4] = "社保申报中";
		} else {
			message[0] = "0";
			message[1] = "操作变更数据为“申报中”失败!";
		}
		return message;
	}

	private String[] declareSingleExcel(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		int id = Integer.parseInt(obj[1].toString());
		result = dal.declareSingleExcel((EmSheBaoChangeModel) obj[2]);

		if (result == 0) {
			message[0] = "1";
			message[1] = "操作变更数据为“申报中”成功!";
			message[2] = String.valueOf(id);
			message[3] = "EmShebaoChange";
			message[4] = "社保申报中";
		} else {
			message[0] = "0";
			message[1] = "操作变更数据为“申报中”失败!";
		}
		return message;
	}

	private String[] declareCancel(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		int id = Integer.parseInt(obj[1].toString());
		result = dal.declareCancel((EmSheBaoChangeModel) obj[2]);

		if (result == 0) {
			message[0] = "1";
			message[1] = "操作变更数据为“撤销申报”成功!";
			message[2] = String.valueOf(id);
			message[3] = "EmShebaoChange";
			message[4] = "社保撤销申报";
		} else {
			message[0] = "0";
			message[1] = "操作变更数据为“撤销申报”失败!";
		}
		return message;
	}

	private String[] declareReturn(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		int id = Integer.parseInt(obj[1].toString());
		result = dal.declareReturn((EmSheBaoChangeModel) obj[2]);

		if (result == 0) {
			message[0] = "1";
			message[1] = "操作变更数据为“退回”成功!";
			message[2] = String.valueOf(id);
			message[3] = "EmShebaoChange";
			message[4] = "社保申报退回";
		} else {
			message[0] = "0";
			message[1] = "操作变更数据为“退回”失败!";
		}
		return message;
	}

	private String[] declareFirstStep(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		int id = Integer.parseInt(obj[1].toString());
		result = dal.declareFirstStep((EmSheBaoChangeModel) obj[2]);

		if (result == 0) {
			message[0] = "1";
			message[1] = "操作变更数据为“未申报”成功!";
			message[2] = String.valueOf(id);
			message[3] = "EmShebaoChange";
			message[4] = "社保未申报";
		} else {
			message[0] = "0";
			message[1] = "操作变更数据为“未申报”失败!";
		}
		return message;
	}

	private String[] declareChangeState(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		int id = Integer.parseInt(obj[1].toString());
		result = dal.declareChangeState((EmSheBaoChangeModel) obj[2]);

		if (result == 0) {
			message[0] = "1";
			message[1] = "操作变更数据修改状态成功!";
			message[2] = String.valueOf(id);
			message[3] = "EmShebaoChange";
			message[4] = "社保未申报";
		} else {
			message[0] = "0";
			message[1] = "操作变更数据修改状态失败!";
		}
		return message;
	}

	@Override
	public String[] ReturnOperate(Object[] obj) {
		String[] message = new String[5];
		String i = obj[0].toString();
		if (i == "2") {
			message = declareCancel(obj);
		} else if (i == "3") {
			message = declareReturn(obj);
		} else if (i == "4") {
			message = declareFirstStep(obj);
		} else if (i == "6") {
			message = declareChangeState(obj);
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
		return dal.upEmShebaoChangeTaprId(tapr_id, dataId);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}
}
