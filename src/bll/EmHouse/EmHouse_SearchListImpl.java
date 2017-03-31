package bll.EmHouse;

import java.sql.SQLException;

import dal.EmHouse.EmHouseChangeDal;
import service.WorkflowCore.WfBusinessService;

public class EmHouse_SearchListImpl implements WfBusinessService {

	@Override
	public String[] Operate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] message = new String[5];
		Integer i = 0;
		EmHouseChangeDal dal = new EmHouseChangeDal();

		try {
			i = dal.declareData(obj[0],obj[1],obj[2]);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (i > 0) {
			message[0] = "1";
			message[1] = "提交成功!";
			message[2] = i.toString(); // DATAID
			message[3] = "EmHouseChange";

			switch (obj[3].toString()) {
			case "11":
				if (obj[4].toString().equals("0")) {
					message[4] = "申报公积金";
				} else {
					message[4] = "新增成功";
				}

			case "21":
				if (obj[4].toString().equals("0")) {
					message[4] = "申报公积金";
				} else {
					message[4] = "转移公积金";
				}
				break;
			case "22":
				if (obj[4].toString().equals("2")) {
					message[4] = "启封公积金";
				}
				break;
			case "31":
				if (obj[4].toString().equals("0")) {
					message[4] = "申报公积金";
				} else {
					message[4] = "停交成功";
				}
			case "41":
				if (obj[4].toString().equals("0")) {
					message[4] = "申报公积金";
				} else {
					message[4] = "调整成功";
				}
				break;
			}

		} else {
			message[0] = "0";
			message[1] = "编号:"+obj[0]+"数据异常,请反馈编号到计算机信息部!";

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
		Integer i = 0;
		EmHouseChangeDal dal = new EmHouseChangeDal();
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
