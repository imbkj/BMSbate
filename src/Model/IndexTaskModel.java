package Model;

import java.util.ArrayList;
import java.util.List;

public class IndexTaskModel {
	private List<TaskListModel> tasklist = new ArrayList<TaskListModel>();
	
	public IndexTaskModel(){
	/*	tasklist.add(new TaskListModel("合同新增", "待审核"));
		tasklist.add(new TaskListModel("合同新增", "审核通过"));
		tasklist.add(new TaskListModel("单位社保调入", "未申报"));
		tasklist.add(new TaskListModel("社保个人工资变更", "已申报"));*/
	}

	public List<TaskListModel> getTasklist() {
		return tasklist;
	}

	public void setTasklist(List<TaskListModel> tasklist) {
		this.tasklist = tasklist;
	}
}
