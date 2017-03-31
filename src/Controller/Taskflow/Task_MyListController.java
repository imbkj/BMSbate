package Controller.Taskflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import bll.Taskflow.Task_ListSelBll;

import Model.TaskListModel;
import Util.UserInfo;

public class Task_MyListController {
	private List<TaskListModel> myList;
	private Task_ListSelBll bll;

	public Task_MyListController() {
		bll = new Task_ListSelBll();
		myList = bll.getMyTaskList(UserInfo.getUsername());
	}

	// 打开任务单详细操作内容
	@Command("openView")
	public void openView(@BindingParam("id") int id) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("id", id);
		Window window = (Window) Executions.createComponents(
				"TaskProcess_View.zul", null, map);
		window.doModal();
	}

	public List<TaskListModel> getMyList() {
		return myList;
	}

}
