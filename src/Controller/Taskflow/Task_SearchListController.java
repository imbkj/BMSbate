package Controller.Taskflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import Model.LoginModel;
import Model.TaskListModel;
import bll.Taskflow.Task_ListSelBll;

public class Task_SearchListController {
	private Task_ListSelBll bll;
	private List<TaskListModel> allClassList;
	private List<TaskListModel> allTaskList;
	private List<TaskListModel> searTaskList;
	private List<TaskListModel> thisTaskList;
	private List<TaskListModel> winclassList;
	private List<TaskListModel> wininfoList;
	private List<TaskListModel> wfClassList;
	private List<TaskListModel> wfDeList;
	private List<TaskListModel> winwfDeList;
	private List<LoginModel> deptList;
	private List<LoginModel> loginList;
	private List<LoginModel> winloginList;
	private String tcSearch;
	private String tcState;
	private String deptName;
	private String userName;
	private String wfClass;
	private String wfDeName;
	private String searchKey;
	private String searchValue;

	public Task_SearchListController() {
		bll = new Task_ListSelBll();

		// 获取任务类型,流程类型,流程名称列表
		allClassList = new ArrayList<TaskListModel>();
		wfClassList = new ArrayList<TaskListModel>();
		wfDeList = new ArrayList<TaskListModel>();

		winclassList = bll.wfClassList("");
		wfClassList = bll.wfClassList();
		wfDeList = bll.wfDeList();
		winwfDeList = wfDeList;

		// 获取任务单操作人、部门列表
		winloginList = loginList = bll.getLogName();
		deptList = bll.getDeptList(loginList);
		tcState = "全部";
		deptName = "全部";
	}

	// 选择任务类型事件
	@Command
	@NotifyChange("wininfoList")
	public void selectname(@BindingParam("model") TaskListModel model) {
		List<TaskListModel> list = new ArrayList<TaskListModel>();
		if (tcSearch == null) {
			allTaskList = bll.allTaskList("", model.getTacl_id());
		} else {
			allTaskList = bll.allTaskList(tcSearch, model.getTacl_id());
		}

		searTaskList = thisTaskList = allTaskList;

		for (TaskListModel m : thisTaskList) {
			if (m.getTacl_id().equals(model.getTacl_id())) {
				list.add(m);
			}
		}
		wininfoList = list;
	}

	// 点击任务名称弹出页面
	@Command
	public void taskinfo(@BindingParam("model") TaskListModel model) {
		String url = "Task_Info.zul";
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("id", model.getTali_id());
		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
	}

	// 按部门检索
	@Command
	@NotifyChange({ "userName", "winloginList" })
	public void searchByDept() {
		try {
			userName = "";
			// 获取数据源
			if (!"全部".equals(deptName)) {
				searTaskList = bll.getTaskListByDept(deptName);
				winloginList = bll.getLogNameList(loginList, deptName);
			} else {
				searTaskList = allTaskList;
				winloginList = loginList;
			}
			searchTask();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 按操作人检索
	@Command
	public void searchByUser() {
		try {
			// 获取数据源
			if (!"".equals(userName) && userName != null) {
				searTaskList = bll.getTaskListByUsername(userName);
			} else if (!"全部".equals(deptName)) {
				searTaskList = bll.getTaskListByDept(deptName);
			} else {
				searTaskList = allTaskList;
			}
			searchTask();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 按流程类型检索
	@Command
	@NotifyChange({ "winwfDeList", "wfDeName" })
	public void searchBywfClass() {
		try {
			wfDeName = "";
			if (!"".equals(wfClass) && wfClass != null) {
				winwfDeList = bll.getwfDeList(wfDeList, wfClass);
			} else {
				winwfDeList = wfDeList;
			}
			searchTask();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 刷新数据
	@Command
	public void search() {

		if ((tcSearch != null && !tcSearch.equals(""))) {
			winclassList = bll.wfClassList(tcSearch);
			allTaskList = bll.allTaskList(tcSearch, null);

			searTaskList = thisTaskList = allTaskList;
			searchTask();
		} else {
			winclassList = bll.wfClassList("");
			searTaskList = thisTaskList = allTaskList = null;
			BindUtils.postNotifyChange(null, null, this, "winclassList");
		}

	}

	// 检索
	@Command
	public void searchTask() {
		if (((searchValue != null && !searchValue.equals(""))
				|| (tcSearch != null && !tcSearch.equals(""))
				|| (tcState != null && !tcState.equals("全部"))
				|| (deptName != null && !deptName.equals("全部"))
				|| (wfClass != null && !wfClass.equals(""))
				|| (wfDeName != null && !wfDeName.equals("")) || (userName != null && !userName
				.equals("")))) {
			if (allTaskList != null && allTaskList.size() > 0) {
				try {
					List<TaskListModel> list = new ArrayList<TaskListModel>();
					// 遍历检索
					for (TaskListModel m : searTaskList) {
						// 状态
						if (!"全部".equals(tcState)) {
							if (!tcState.equals(m.getState()))
								continue;
						}
						// 任务单
						if (!"".equals(tcSearch) && tcSearch != null) {
							if (m.getTacl_name().contains(tcSearch)
									|| m.getTali_name().contains(tcSearch)) {

							} else
								continue;

						}
						// 流程
						if (!"".equals(wfDeName) && wfDeName != null) {
							if (!wfDeName.equals(m.getWfde_name()))
								continue;
						} else if (!"".equals(wfClass) && wfClass != null) {
							if (!wfClass.equals(m.getWfcl_name()))
								continue;
						}
						// 业务
						if (!"".equals(searchValue) && searchValue != null) {
							if (!"".equals(searchKey) && searchKey != null) {
								if ("任务单编号".equals(searchKey)) {
									if (searchValue.equals(String.valueOf(m
											.getTali_id()))) {
										list.add(m);
										break;
									} else
										continue;
								} else if (m.getSearchConMap() == null
										|| !searchValue.equals(m
												.getSearchConMap().get(
														changeKey(searchKey))))
									continue;
							}
						}
						list.add(m);
					}
					wininfoList = thisTaskList = list;
					// 重新设置任务单类型
					bll.getTaskClassList(wininfoList, winclassList);
					BindUtils.postNotifyChange(null, null, this, "wininfoList");
					BindUtils
							.postNotifyChange(null, null, this, "winclassList");
					BindUtils.postNotifyChange(null, null, this, "wfClassList");
					BindUtils.postNotifyChange(null, null, this, "winwfDeList");
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (allTaskList == null) {

			}
		}
	}

	// 转换业务搜索条件
	private String changeKey(String con) {
		String key = "";
		switch (con) {
		case "公司编号":
			key = "cid";
			break;
		case "员工编号":
			key = "gid";
			break;
		case "所属月份":
			key = "ownmonth";
			break;
		}
		return key;
	}

	public List<TaskListModel> getWinclassList() {
		return winclassList;
	}

	public List<TaskListModel> getWininfoList() {
		return wininfoList;
	}

	public List<TaskListModel> getWfClassList() {
		return wfClassList;
	}

	public List<TaskListModel> getWfDeList() {
		return wfDeList;
	}

	public List<LoginModel> getDeptList() {
		return deptList;
	}

	public List<LoginModel> getWinloginList() {
		return winloginList;
	}

	public String getTcSearch() {
		return tcSearch;
	}

	public void setTcSearch(String tcSearch) {
		this.tcSearch = tcSearch;
	}

	public String getTcState() {
		return tcState;
	}

	public void setTcState(String tcState) {
		this.tcState = tcState;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getWfClass() {
		return wfClass;
	}

	public void setWfClass(String wfClass) {
		this.wfClass = wfClass;
	}

	public String getWfDeName() {
		return wfDeName;
	}

	public void setWfDeName(String wfDeName) {
		this.wfDeName = wfDeName;
	}

	public List<TaskListModel> getWinwfDeList() {
		return winwfDeList;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

}
