package bll.Taskflow;

import impl.WorkflowCore.Core.WfFlowControlImpl;

import java.util.List;

import Model.TaskProcessModel;
import Model.WfNodeModel;
import dal.Taskflow.Task_controlDal;

public class Task_controlBll {
	private Task_controlDal dal = new Task_controlDal();

	// 根据任务单编号获取任务单的节点信息
	public List<WfNodeModel> getNode(int tali_id) {
		return dal.getNode(tali_id);
	}

	// 根据任务单编号获取流程信息
	public TaskProcessModel getNowProcess(int tali_id) {
		return dal.getNowProcess(tali_id);
	}

	// 流程调整
	public int ctrlNode(int tali_id, WfNodeModel m, int appointid,
			String appointcon, String remark, String username) {
		int i = 0;
		try {
			TaskProcessModel nowModel = getNowProcess(tali_id);
			WfFlowControlImpl impl = new WfFlowControlImpl();
			if (m.getWfno_step() > nowModel.getWfno_step()) {
				if (nowModel.getTapr_appointname() >= 0 && appointid == 0) {
					appointid = nowModel.getTapr_appointname();
				}
				int id = impl.SkipToN(nowModel.getTapr_id(),
						nowModel.getTapr_dataid(), m.getWfno_step(), appointid,
						appointcon, username, remark);
				if (id > 0) {
					impl.AddTaskLog(nowModel.getTapr_id(), null,
							nowModel.getTapr_dataid(), "流程调整", username, remark);
					i = 1;
				}
				
			} else if (m.getWfno_step() < nowModel.getWfno_step()) {
				int id = impl.returnToN(nowModel.getTapr_id(),
						m.getWfno_step(), username);
				if (id > 0) {
					impl.AddTaskLog(nowModel.getTapr_id(), null,
							nowModel.getTapr_dataid(), "流程调整", username, remark);
					i = 1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	// 终止任务单
	public int stopTask(int tali_id, String remark, String username) {
		int i = 0;
		try {
			WfFlowControlImpl impl = new WfFlowControlImpl();
			TaskProcessModel nowModel = getNowProcess(tali_id);
			i = impl.StopTask(nowModel.getTapr_id(), username, remark);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	// 设置操作人
	public int setOpName(int tapr_id, String name) {
		return dal.setOpName(tapr_id, name);
	}

	// 节点暂缓处理
	public int dalayOp(int tapr_id, String remark, String username) {
		return dal.dalayOp(tapr_id, remark, username);
	}

	// 节点催促处理
	public int urgeOp(int tapr_id, String remark, String username) {
		return dal.urgeOp(tapr_id, remark, username);
	}
}
