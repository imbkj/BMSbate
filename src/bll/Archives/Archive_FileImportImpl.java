package bll.Archives;

import Model.EmArchiveDatumModel;
import Model.EmArchiveModel;
import dal.Archives.EmArchiveDatumDal;
import service.WorkflowCore.WfBusinessService;

public class Archive_FileImportImpl implements WfBusinessService {

	@Override
	public String[] Operate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] message = new String[5];
		Integer i = 0;
		if (obj[0].equals("新增调入")) {

			EmArchiveDatumDal dal = new EmArchiveDatumDal();
			Object[] o = { obj[1], obj[2], obj[3], obj[4], obj[5], obj[6],
					obj[7], obj[8], obj[9], obj[10], obj[11], obj[12],
					obj[13], obj[14] };
			try {
				i = dal.addfile(o);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (i > 0) {
				message[0] = "1";
				message[1] = "提交成功!";
				message[2] = i.toString();
				message[3] = "EmArchiveDatum";
				message[4] = "新增档案调入";
			} else {
				message[0] = "0";
				message[1] = "调入失败!";
			}
		}else if (obj[0].equals("重新发送")) {
			EmArchiveDatumDal dal = new EmArchiveDatumDal();
			EmArchiveDatumModel m = (EmArchiveDatumModel) obj[1];
			i =dal.updateData(m,m.getEada_id());
			if (i > 0) {
				message[0] = "1";
				message[1] = "提交成功!";
				message[2] = m.getEada_id().toString();
				message[3] = "EmArchiveDatum";
				message[4] = "确认档案调入数据";
			} else {
				message[0] = "0";
				message[1] = "确认失败!";
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
		String[] message = new String[5];
		Integer i = 0;
		if (obj[0].equals("跳过")) {
			message[0] = "1";
			message[1] = "提交成功!";
			message[2] = obj[0].toString();
			message[3] = "EmArchiveDatum";
			message[4] = "新增档案调入";
			
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
