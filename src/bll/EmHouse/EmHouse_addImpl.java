package bll.EmHouse;

import java.sql.SQLException;

import dal.Archives.EmArchiveDatumDal;
import dal.EmHouse.EmHouseChangeDal;
import service.WorkflowCore.WfBusinessService;

public class EmHouse_addImpl implements WfBusinessService {

	@Override
	public String[] Operate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] message = new String[5];
		Integer i = 0;
		EmHouseChangeDal dal = new EmHouseChangeDal();
		try {
			i = dal.addEmhouse(obj);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (i > 0) {
			message[0] = "1";
			message[1] = "提交成功!";
			message[2] = i.toString();
			message[3] = "EmHouseChange";
			message[4] = "新增公积金";
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
		String[] message = new String[5];
		Integer i = 0;
		EmHouseChangeDal dal = new EmHouseChangeDal();
		if (obj[0].toString().equals("5")) {
			try {
				i = dal.updateConfirmDate(Integer.valueOf(obj[1].toString()),
						Integer.valueOf(obj[2].toString()));
				if (i > 0) {

					message[0] = "1";
					message[1] = "提交成功!";
					message[2] = i.toString();
					message[3] = "EmHouseChange";
					message[4] = "跳过";
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return message;
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
