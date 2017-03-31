package bll.Archives;

import Model.EmArchiveDatumModel;
import dal.Archives.EmArchiveDatumDal;
import service.WorkflowCore.WfBusinessService;

public class Archive_classifyImpl implements WfBusinessService {

	@Override
	public String[] Operate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] message = new String[5];
		String[] i = new String[5];
		EmArchiveDatumDal dal = new EmArchiveDatumDal();
		if (obj[0].toString().equals("1")) {

			try {
				if (obj[1].equals("中智保管")) {
					i = dal.classifyFile(obj[1], obj[3]);
				} else {
					i = dal.classifyFile(obj[1], obj[2], obj[3]);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (i[0] != null && i[0] != "") {
				message[0] = "1";
				message[1] = "提交成功!";
				message[2] = i[0];
				message[3] = "EmArchiveDatum";
				message[4] = "档案类型确定为:" + i[1];
			} else {
				message[0] = "0";
				message[1] = "修改失败!";

			}
		} else if (obj[0].toString().equals("2")) {
			message[0] = "1";
			message[1] = "提交成功!";
			message[2] = obj[1].toString();
			message[3] = "EmArchiveDatum";
			message[4] = "进入子流程";
		} else if (obj[0].toString().equals("3")) {
			message[0] = "1";
			message[1] = "提交成功!";
			message[2] = obj[1].toString();
			message[3] = "EmArchiveDatum";
			message[4] = "转入中智户流程";
		} else if (obj[0].toString().equals("4")) {
			message[0] = "1";
			message[1] = "提交成功!";
			message[2] = obj[1].toString();
			message[3] = "EmArchiveDatum";
			message[4] = "转入市内人才流程";

		} else if (obj[0].toString().equals("5")) {
			message[0] = "1";
			message[1] = "提交成功!";
			message[2] = obj[1].toString();
			message[3] = "EmArchiveDatum";
			message[4] = "转入市外人才流程";
		}
		return message;
	}

	@Override
	public String[] ReturnOperate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] message = new String[5];
		
		if (obj[0].equals("人事退回数据")) {
			EmArchiveDatumModel m = (EmArchiveDatumModel) obj[1];
			EmArchiveDatumDal dal = new EmArchiveDatumDal();
			Integer i =dal.updateData(m, m.getEada_id());
			if (i>0) {
				message[0] = "1";
				message[1] = "操作成功!";
				message[2] = m.getEada_id().toString();
				message[3] = "EmArchiveDatum";
				message[4] = "人事退回数据";
			}else {
				message[0] = "0";
				message[1] = "操作失败!";

			}
		}

		return message;
	}

	@Override
	public String[] SkipOperate(Object[] obj) {
		String[] message = new String[5];
		message[0] = "1";
		message[1] = "操作成功!";
		message[2] = obj[1]+"";
		message[3] = "EmArchiveDatum";
		message[4] = "跳过";
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
