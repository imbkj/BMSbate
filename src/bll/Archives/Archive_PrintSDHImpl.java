package bll.Archives;

import dal.Archives.EmArchiveDatumDal;
import service.WorkflowCore.WfBusinessService;

public class Archive_PrintSDHImpl implements WfBusinessService {

	@Override
	public String[] Operate(Object[] obj) {
		// TODO Auto-generated method stub
		Integer i = 0;
		String message[] = new String[5];
		EmArchiveDatumDal dal = new EmArchiveDatumDal();
		try {
			i = dal.updateSDH(obj[0], obj[1]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (i > 0) {
			message[0] = "1";
			message[1] = "提交成功!";
			message[2] = i.toString();
			message[3] = "EmArchiveDatum";
			message[4] = "打印商调函";
		} else {
			message[0] = "0";
			message[1] = "打印失败!";
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
