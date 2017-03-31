package bll.EmCAFFeeInfo;

import dal.EmCAFFeeInfo.EmCAFFeeInfo_OperateDal;
import Model.EmCAFFeeInfoModel;

public class EmCAFFeeInfo_OperateBll {
	// 状态更新
	public String[] ecfiUpdateState(EmCAFFeeInfoModel m, int if_all) {
		EmCAFFeeInfo_OperateDal dal = new EmCAFFeeInfo_OperateDal();
		String[] message = new String[5];
		int result = 0;
		try {
			result = dal.ecfiUpdateState(m, if_all);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作状态更新成功!";
				message[2] = "";
				message[3] = "EmCAFFeeInfo";
				message[4] = "状态更新";
			} else {
				message[0] = "0";
				message[1] = "操作状态更新失败!";
			}
			return message;

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "状态更新，操作出错。";
		}
		return message;
	}

	// 退款
	public String[] ecfiRefundment(EmCAFFeeInfoModel m) {
		EmCAFFeeInfo_OperateDal dal = new EmCAFFeeInfo_OperateDal();
		String[] message = new String[5];
		int result = 0;
		try {
			result = dal.ecfiRefundment(m);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作退款成功!";
				message[2] = "";
				message[3] = "EmCAFFeeInfo";
				message[4] = "退款";
			} else {
				message[0] = "0";
				message[1] = "操作退款失败!";
			}
			return message;

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "退款，操作出错。";
		}
		return message;
	}

	// 新增数据
	public String[] ecfiAdd(EmCAFFeeInfoModel m) {
		EmCAFFeeInfo_OperateDal dal = new EmCAFFeeInfo_OperateDal();
		String[] message = new String[5];
		int result = 0;
		try {
			result = dal.ecfiAdd(m);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作新增成功!";
				message[2] = "";
				message[3] = "EmCAFFeeInfo";
				message[4] = "新增";
			} else {
				message[0] = "0";
				message[1] = "操作新增失败!";
			}
			return message;

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "新增，操作出错。";
		}
		return message;
	}
}
