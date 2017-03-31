package bll.CoCompact;

import Model.CoCompactCppAduitModel;

import dal.CoCompact.CoCompactCppAduitDal;
import service.WorkflowCore.WfBusinessService;

public class Compact_houseCppImpl implements WfBusinessService {

	@Override
	public String[] Operate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] message = new String[5];
		if (obj[0].equals("新增")) {
			CoCompactCppAduitModel cm = new CoCompactCppAduitModel();
			cm = (CoCompactCppAduitModel) obj[1];
			Compact_houseCppBll bll = new Compact_houseCppBll();
			Integer i = bll.dataAdd(cm);

			if (i > 0) {
				message[0] = "1";
				message[1] = "提交成功!";
				message[2] = i.toString();
				message[3] = "CoCompactCppAduit";
				message[4] = "新增比例变更";
			} else {
				message[0] = "2";
				message[1] = "新增比例变更出错。";
			}
		} else if (obj[0].equals("审核")) {
			CoCompactCppAduitModel cm = new CoCompactCppAduitModel();
			cm = (CoCompactCppAduitModel) obj[1];
			Compact_houseCppBll bll = new Compact_houseCppBll();
			Integer i = bll.dataMod(cm);
			
			if (i > 0) {
				bll.updateCompact(cm);
				message[0] = "1";
				message[1] = "提交成功!";
				message[2] = cm.getCoca_id().toString();
				message[3] = "CoCompactCppAduit";
				message[4] = "审核比例变更";
			} else {
				message[0] = "2";
				message[1] = "审核比例变更出错。";
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
		if (obj[0].equals("跳过确认")) {
			CoCompactCppAduitModel cm = new CoCompactCppAduitModel();
			cm = (CoCompactCppAduitModel) obj[1];
			message[0] = "1";
			message[1] = "提交成功!";
			message[2] = cm.getCoca_id().toString();
			message[3] = "CoCompactCppAduit";
			message[4] = "新增比例变更";
		}
		return message;
	}

	@Override
	public String[] StopOperate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] message = new String[5];
		Compact_houseCppBll bll = new Compact_houseCppBll();
		CoCompactCppAduitModel cm = new CoCompactCppAduitModel();
		cm = (CoCompactCppAduitModel) obj[1];
		Integer i = bll.dataMod(cm);
		if (i>0) {
			bll.sendMail(cm);
			message[0] = "1";
			message[1] = "提交成功!";
			message[2] = cm.getCoca_id().toString();
			message[3] = "CoCompactCppAduit";
			message[4] = "终止任务";
		}else {
			message[0] = "2";
			message[1] = "更新失败。";
		}
		
		return message;
	}

	@Override
	public Boolean UpdateTaprid(int dataId, int tapr_id, int state) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Integer i = 0;
		CoCompactCppAduitDal dal = new CoCompactCppAduitDal();
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
