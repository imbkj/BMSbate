package bll.CoQuotation;

import java.sql.SQLException;

import Model.CoCompactModel;
import Util.UserInfo;
import dal.CoQuotation.CoofferlistDal;
import dal.Taskflow.TaskBatchDal;
import service.WorkflowCore.WfBusinessService;

//只适用在合同新增触发独立户
public class CoofferlistImpl implements WfBusinessService {

	@Override
	public String[] Operate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] message = new String[5];
		if (obj[0].equals("启动社保开户")) {
			CoCompactModel cm = (CoCompactModel) obj[1];
			// CoCompact_OperateBll bll = new CoCompact_OperateBll();
			// Integer id = bll.getcoofferListId(cm.getCoco_id(), "社会保险服务");
			TaskBatchDal dal = new TaskBatchDal();
			Integer id = dal.add("TaskBatch",
					cm.getCid() + "," + cm.getCompany() + "社保独立开户",
					UserInfo.getUsername());
			message[0] = "1";
			message[1] = "社保开户";
			message[2] = id.toString();
			message[3] = "TaskBatch";
			message[4] = "启动任务";
		} else if (obj[0].equals("启动公积金开户")) {
			CoCompactModel cm = (CoCompactModel) obj[1];
			// CoCompact_OperateBll bll = new CoCompact_OperateBll();
			// Integer id = bll.getcoofferListId(cm.getCoco_id(), "住房公积金服务");
			TaskBatchDal dal = new TaskBatchDal();
			Integer id = dal.add("TaskBatch",
					cm.getCid() + "," + cm.getCompany() + "公积金独立开户",
					UserInfo.getUsername());
			message[0] = "1";
			message[1] = "公积金开户";
			message[2] = id.toString();
			message[3] = "TaskBatch";
			message[4] = "启动任务";
		} else if (obj[0].equals("任务结束")) {
			message[0] = "1";
			message[1] = "结束任务";
			message[2] = obj[1].toString();
			message[3] = "TaskBatch";
			message[4] = "结束";

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
		TaskBatchDal dal = new TaskBatchDal();
		Integer i = 0;
		try {
			i = dal.UpdateTaprid(dataId, tapr_id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
