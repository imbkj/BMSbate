package Controller.Taskflow;

import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;

import bll.Taskflow.TaskProcess_ViewBll;

import Model.TaskProcessLogViewModel;

public class Task_historyController {
	private Integer tali_id = (Integer) Executions.getCurrent().getArg().get("tali_id");
	private List<TaskProcessLogViewModel> tplList = new ListModelList<TaskProcessLogViewModel>();
	private TaskProcess_ViewBll bll = new TaskProcess_ViewBll();
	

	public Task_historyController() {
		
		setTplList();
	}


	public List<TaskProcessLogViewModel> getTplList() {
		return tplList;
	}


	public void setTplList() {
		this.tplList = bll.geTaskProcessLogViewByTaliId(tali_id);
	}
	
	
}
