package bll.Archives;

import dal.Archives.EmArchiveDatumDal;
import service.WorkflowCore.WfBusinessService;

public class Archive_CommitChargeImpl implements WfBusinessService {

	@Override
	public String[] Operate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] message = new String[5];
		Integer i = 0;
		EmArchiveDatumDal dal = new EmArchiveDatumDal();
		
		if (Integer.valueOf(obj[0].toString())>0) {//判断是否需要更新数据库
			try {
				i = dal.updateCharge(obj[2],obj[3],obj[4]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (i>0) {
				message[0] = "1";
				message[1] = "提交成功!";
				message[2] = obj[4].toString();
				message[3] = "EmArchiveDatum";
				message[4] = "确认档案信息";
			} else {
				message[0] = "0";
				message[1] = "确认失败!";
			}
		}else {
			if (obj[1].equals("否")) {
				message[0] = "1";
				message[1] = "提交成功!";
				message[2] = obj[2].toString(); //业务ID
				message[3] = "EmArchiveDatum";
				message[4] = "跳过";
			}
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
		EmArchiveDatumDal dal = new EmArchiveDatumDal();
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
