package Controller.Taskflow;

import impl.TaskListComparatorImpl;
import java.sql.SQLException;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Group;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Tree;

import bll.SystemControl.MenuListBll;
import bll.Taskflow.Task_ListBll;

import Model.MenuListModel;
import Model.TaskListGroupModel;
import Model.TaskListModel;
import Model.WfTaskListInfoModel;

public class Task_ListController {
	MenuListBll mbll = new MenuListBll();
	Task_ListBll bll = new Task_ListBll();
	// int copr_id = (Integer) Executions.getCurrent().getArg().get("copr_id");
	private boolean showGroup = true;
	private List<WfTaskListInfoModel> list;

	// 初始化
	@Init
	public void init() throws SQLException {
		setList(new ListModelList<TaskListModel>(bll.getTaskBycoprid(0)));
	}

	// 调用分组model
	public TaskListGroupModel getFeeRelationModel() {
		return new TaskListGroupModel(list, new TaskListComparatorImpl(),
				this.showGroup);
	}

	public List<TaskListModel> getList() {
		return list;
	}

	public void setList(List<TaskListModel> list) {
		this.list = list;
	}

	// 定义生成tab标签的方法

}
