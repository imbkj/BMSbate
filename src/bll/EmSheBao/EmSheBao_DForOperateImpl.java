/*
 * 创建人：林少斌
 * 创建时间：2014-1-14
 * 用途：社保外籍人新参保任务单Impl
 */
package bll.EmSheBao;

import Model.EmShebaoChangeForeignerModel;
import dal.EmSheBao.EmSheBao_DOperateDal;
import dal.EmSheBao.Emsi_OperateDal;
import service.WorkflowCore.WfBusinessService;

public class EmSheBao_DForOperateImpl implements WfBusinessService {
	EmSheBao_DOperateDal dal = new EmSheBao_DOperateDal();

	@Override
	public String[] Operate(Object[] obj) {
		String[] message = new String[5];
		String i = obj[0].toString();

		if (i == "0") {
			message = declareForSingle(obj);
		} else if (i == "1") {
			message = declareForSingleSuccess(obj);
		} /*else if (i == "2") {
			message = declareForSingleExcel(obj);
		}*/

		return message;
	}

	private String[] declareForSingle(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		int id = Integer.parseInt(obj[1].toString());
		result = dal.declareForSingle((EmShebaoChangeForeignerModel) obj[2]);

		if (result == 0) {
			message[0] = "1";
			message[1] = "操作外籍人新参保数据为“申报中”成功!";
			message[2] = String.valueOf(id);
			message[3] = "EmShebaoChangeForeigner";
			message[4] = "社保外籍人新参保申报中";
		} else {
			message[0] = "0";
			message[1] = "操作外籍人新参保数据为“申报中”失败!";
		}
		return message;
	}

	/*private String[] declareForSingleExcel(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		int id = Integer.parseInt(obj[1].toString());
		result = dal
				.declareForSingleExcel((EmShebaoChangeForeignerModel) obj[2]);

		if (result == 0) {
			message[0] = "1";
			message[1] = "操作外籍人新参保数据为“申报中”成功!";
			message[2] = String.valueOf(id);
			message[3] = "EmShebaoChangeForeigner";
			message[4] = "社保外籍人新参保申报中";
		} else {
			message[0] = "0";
			message[1] = "操作外籍人新参保数据为“申报中”失败!";
		}
		return message;
	}*/

	private String[] declareForSingleSuccess(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		int id = Integer.parseInt(obj[1].toString());
		result = dal
				.declareForSingleSuccess((EmShebaoChangeForeignerModel) obj[2]);

		if (result == 0) {
			message[0] = "1";
			message[1] = "操作外籍人新参保数据为“已申报”成功!";
			message[2] = String.valueOf(id);
			message[3] = "EmShebaoChangeForeigner";
			message[4] = "社保外籍人新参保申报中";
		} else {
			message[0] = "0";
			message[1] = "操作外籍人新参保数据为“已申报”失败!";
		}
		return message;
	}

	private String[] declareForReturn(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		int id = Integer.parseInt(obj[1].toString());
		result = dal.declareForReturn((EmShebaoChangeForeignerModel) obj[2]);

		if (result == 0) {
			message[0] = "1";
			message[1] = "操作社保外籍人新参保数据为“退回”成功!";
			message[2] = String.valueOf(id);
			message[3] = "EmShebaoChangeForeigner";
			message[4] = "社保外籍人新参保退回";
		} else {
			message[0] = "0";
			message[1] = "操作社保外籍人新参保数据为“退回”失败!";
		}
		return message;
	}

	private String[] declareForFirstStep(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		int id = Integer.parseInt(obj[1].toString());
		result = dal.declareForFirstStep((EmShebaoChangeForeignerModel) obj[2]);

		if (result == 0) {
			message[0] = "1";
			message[1] = "操作社保外籍人新参保数据为“未申报”成功!";
			message[2] = String.valueOf(id);
			message[3] = "EmShebaoChangeForeigner";
			message[4] = "社保外籍人新参保未申报";
		} else {
			message[0] = "0";
			message[1] = "操作社保外籍人新参保数据为“未申报”失败!";
		}
		return message;
	}

	private String[] declareForCancel(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		int id = Integer.parseInt(obj[1].toString());
		result = dal.declareForCancel((EmShebaoChangeForeignerModel) obj[2]);

		if (result == 0) {
			message[0] = "1";
			message[1] = "操作社保外籍人新参保数据为“撤销申报”成功!";
			message[2] = String.valueOf(id);
			message[3] = "EmShebaoChangeForeigner";
			message[4] = "社保外籍人新参保撤销申报";
		} else {
			message[0] = "0";
			message[1] = "操作社保外籍人新参保数据为“撤销申报”失败!";
		}
		return message;
	}

	@Override
	public String[] ReturnOperate(Object[] obj) {
		String[] message = new String[5];
		String i = obj[0].toString();

		if (i == "3") {
			message = declareForReturn(obj);
		} else if (i == "4") {
			message = declareForFirstStep(obj);
		} else if (i == "5") {
			message = declareForCancel(obj);
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
		return dal.upEmShebaoChangeForeignerTaprId(tapr_id, dataId);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}
}
