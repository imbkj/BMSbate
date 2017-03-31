package bll.Archives;

import Model.EmArchiveDatumModel;
import dal.Archives.EmArchiveDatumDal;
import service.WorkflowCore.WfBusinessService;

public class Archive_ConfirmImpl implements WfBusinessService {

	@Override
	public String[] Operate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] message = new String[5];
		Integer i = 0;
		EmArchiveDatumDal dal = new EmArchiveDatumDal();
		try {
			EmArchiveDatumModel em = (EmArchiveDatumModel) obj[0];
			i = dal.ConfirmData(em);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (i > 0) {
			message[0] = "1";
			message[1] = "提交成功!";
			message[2] = i.toString();
			message[3] = "EmArchiveDatum";
			message[4] = "确认档案信息";
		} else {
			message[0] = "0";
			message[1] = "确认失败!";
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
