package bll.EmPay;

import impl.WorkflowCore.WfOperateImpl;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import Util.UserInfo;

public class EmPa_TaskService {

	// 新增任务单
	public String[] addTask(String type, String number, int task_id) {
		Object[] obj = { "1", number };
		WfBusinessService wfbs = new EmPa_TaskServiceImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = wfs.AddTaskToNext(obj, "个人支付", number, task_id,
				UserInfo.getUsername(), "",Integer.parseInt(UserInfo.getDepID()), "", null);
		return str;
	}

	// 任务单转下一步
	public String[] taskPass(int id, int tapr_id) {
		Object[] obj = { "2", id + "" };
		WfBusinessService wfbs = new EmPa_TaskServiceImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = wfs.PassToNext(obj, tapr_id, UserInfo.getUsername(), "",
				Integer.parseInt(UserInfo.getDepID()), "");
		return str;
	}

	// 任务单跳过下一步
	public String[] taskSkip(int id, int tapr_id) {
		Object[] obj = { "2", id + "" };
		WfBusinessService wfbs = new EmPa_TaskServiceImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = wfs.SkipToNext(obj, tapr_id, "系统", "", Integer.parseInt(UserInfo.getDepID()), "");
		return str;
	}

	// 任务单跳过下一步
	public String[] taskSkip(int id, int tapr_id, int step) {
		Object[] obj = { "2", id + "" };
		WfBusinessService wfbs = new EmPa_TaskServiceImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = wfs.SkipToN(obj, tapr_id, step, "系统", "", Integer.parseInt(UserInfo.getDepID()), "");
		return str;
	}

	// 任务单退回上一步 ReturnToN
	public String[] taskBack(int id, int tapr_id) {
		Object[] obj = { "2", id + "" };
		WfBusinessService wfbs = new EmPa_TaskServiceImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = wfs.ReturnToPrev(obj, tapr_id, "系统", "");
		return str;
	}

	// 任务单退回到指定步骤
	public String[] taskReturnToN(int id, int tapr_id, int tostep) {
		Object[] obj = { "2", id + "" };
		WfBusinessService wfbs = new EmPa_TaskServiceImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = wfs.ReturnToN(obj, tapr_id, tostep, "系统");
		return str;
	}
}
