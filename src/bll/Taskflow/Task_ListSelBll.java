package bll.Taskflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.LoginModel;
import Model.TaskListModel;
import dal.Taskflow.Task_ListSelDal;

public class Task_ListSelBll {
	private Task_ListSelDal dal;

	public Task_ListSelBll() {
		dal = new Task_ListSelDal();
	}

	// 初始化下拉列表
	public List<TaskListModel> wfClassList() {
		return dal.getFlowClass();
	}

	public List<TaskListModel> wfDeList() {
		return dal.getFlow();
	}

	// 查询初始化信息
	public List<TaskListModel> wfClassList(String str) {
		return dal.getMissionClass(str);

	}

	// 查询初始化信息
	public List<TaskListModel> wfClassList2(String str) {
		return dal.getMissionClass2(str);

	}

	public List<TaskListModel> allTaskList(String str, Integer taclId) {
		return dal.getMission(str, taclId);
	}

	public List<TaskListModel> allTaskList2(String str, Integer taclId) {
		return dal.getMission2(str, taclId);
	}

	public List<TaskListModel> allTaskList3(String cid, String gid,
			String ownmonth, String tali_id) {
		return dal.getMission3(cid, gid, ownmonth, tali_id);
	}

	// 我的任务单列表
	public List<TaskListModel> getMyTaskList(String username) {
		return dal.getMyTaskList(username);
	}

	// 查询所有任务单
	public List<TaskListModel> getAllTaskList() {
		return dal.getAllTaskList();
	}

	// 获取任务类型
	public List<TaskListModel> getTaskClassList(List<TaskListModel> infoList,
			List<TaskListModel> classList) {
		Map<Integer, Integer> classMap = new HashMap<Integer, Integer>();
		classList.clear();
		for (TaskListModel m : infoList) {
			if (!classMap.containsKey(m.getTacl_id())) {
				classMap.put(m.getTacl_id(), 1);
				classList.add(m);
			}
		}
		return classList;
	}

	// 获取任务类型,流程类型,流程名称列表
	public void getTaskList(List<TaskListModel> infoList,
			List<TaskListModel> classList, List<TaskListModel> wfClassList,
			List<TaskListModel> wfDeList) {
		Map<Integer, Integer> classMap = new HashMap<Integer, Integer>();
		Map<Integer, Integer> wfClassMap = new HashMap<Integer, Integer>();
		Map<Integer, Integer> wfDeMap = new HashMap<Integer, Integer>();
		for (TaskListModel m : infoList) {
			if (!classMap.containsKey(m.getTacl_id())) {
				classMap.put(m.getTacl_id(), 1);
				classList.add(m);
			}
			if (!wfClassMap.containsKey(m.getWfcl_id())) {
				wfClassMap.put(m.getWfcl_id(), 1);
				wfClassList.add(m);
			}
			if (!wfDeMap.containsKey(m.getWfde_id())) {
				wfDeMap.put(m.getWfde_id(), 1);
				wfDeList.add(m);
			}
		}
	}

	// 获取任务单操作人列表
	public List<LoginModel> getLogName() {
		return dal.getLogName();
	}

	// 根据操作人获取部门列表
	public List<LoginModel> getDeptList(List<LoginModel> logNameList) {
		List<LoginModel> list = new ArrayList<LoginModel>();
		Map<Integer, Integer> depMap = new HashMap<Integer, Integer>();
		try {
			for (LoginModel m : logNameList) {
				if (!depMap.containsKey(m.getDep_id())) {
					depMap.put(m.getDep_id(), 1);
					list.add(m);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据部门获取操作人列表
	public List<LoginModel> getLogNameList(List<LoginModel> logNameList,
			String dep_name) {
		List<LoginModel> list = new ArrayList<LoginModel>();
		try {
			for (LoginModel m : logNameList) {
				if (dep_name.equals(m.getDep_name())) {
					list.add(m);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据流程类型筛选流程名称
	public List<TaskListModel> getwfDeList(List<TaskListModel> wfDeList,
			String wfClass) {
		List<TaskListModel> list = new ArrayList<TaskListModel>();
		try {
			for (TaskListModel m : wfDeList) {
				if (wfClass.equals(m.getWfcl_name())) {
					list.add(m);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据部门查询任务单
	public List<TaskListModel> getTaskListByDept(String dep_name) {
		return dal.getTaskListByDept(dep_name);
	}

	// 根据操作人查询任务单
	public List<TaskListModel> getTaskListByUsername(String username) {
		return dal.getTaskListByUsername(username);
	}

}
