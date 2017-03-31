package bll.Taskflow;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.zul.ListModelList;

import dal.Taskflow.TaskProcessLogViewDal;
import dal.Taskflow.TaskProcessViewDal;

import Model.TaskProcessLogViewModel;
import Model.TaskProcessViewModel;

public class TaskProcess_ViewBll {
	/**
	 * @Title: getViewList
	 * @Description: TODO(获取任务单图形列表)
	 * @param id
	 * @return
	 * @return List<TaskProcessViewModel> 返回类型
	 * @throws
	 */
	public List<TaskProcessViewModel> getViewList(Integer id) {
		Integer j = 0;
		List<TaskProcessViewModel> vList = new ListModelList<TaskProcessViewModel>();
		List<TaskProcessViewModel> list = getTaskProcessViewList(id);
		Integer state = 0;
		String str[];

		for (Integer i = 0; i < list.size(); i++) {
			if (list.get(i).getTapr_state() != null) {
				state = list.get(i).getTapr_state();
			} else {

				if (list.get(i).getTwi() >= list.get(i).getWfno_step()) {
					state = 6;
				} else {
					state = 3;
				}

			}
			str = getNodeInfo(state);

			if (i <= list.size() && i > 0) {
				TaskProcessViewModel tpModel = new TaskProcessViewModel();
				tpModel.setProcessname(str[1]);
				vList.add(tpModel);
				tpModel = null;
			}

			list.get(i).setProcessname(str[0]);
			vList.add(list.get(i));
			j++;
		}

		return vList;
	}

	/**
	 * @Title: getNodeInfo
	 * @Description: TODO(生成图形STYLE)
	 * @param state
	 * @return
	 * @return String[] 返回类型
	 * @throws
	 */
	public String[] getNodeInfo(Integer state) {
		String[] s = { "", "" };
		String nodeName = "";
		String proceName = "";
		if (state == 0) {
			nodeName = "space";
			proceName = "";
		} else if (state == 3 || state == 4) {
			nodeName = "node wait2";
			proceName = "proce wait";
		} else if (state == 1) {
			nodeName = "node wait2";
			proceName = "proce doing";
		} else if (state == 1 || state == 2) {
			nodeName = "node ready2";
			proceName = "proce ready";
		} else if (state == 5) {
			nodeName = "node singular";
			proceName = "";
		} else if (state == 6) {
			nodeName = "node singular";
			proceName = "proce ready";
		}
		s[0] = nodeName;
		s[1] = proceName;
		return s;
	}

	public Integer getLastId(Integer id) throws SQLException {
		Integer i;
		List<TaskProcessViewModel> list = new ListModelList<TaskProcessViewModel>();
		TaskProcessViewDal tpvd = new TaskProcessViewDal();
		list = tpvd.getLastId(id);
		i = list.get(0).getWfno_id();
		return i;
	}

	/**
	 * @Title: getTaskProcessViewList
	 * @Description: TODO(获取节点列表)
	 * @param id
	 * @return
	 * @return List<TaskProcessViewModel> 返回类型
	 * @throws
	 */
	public List<TaskProcessViewModel> getTaskProcessViewList(Integer id) {
		List<TaskProcessViewModel> list = new ListModelList<TaskProcessViewModel>();
		TaskProcessViewDal dal = new TaskProcessViewDal();
		try {
			list = dal.getView_WfNode_TaskProcessDalsByTaliId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @Title: geTaskProcessLogViewByTaliId
	 * @Description: TODO(查询任务单列表)
	 * @param id
	 * @return
	 * @return List<TaskProcessLogViewModel> 返回类型
	 * @throws
	 */
	public List<TaskProcessLogViewModel> geTaskProcessLogViewByTaliId(Integer id) {
		List<TaskProcessLogViewModel> list = null;
		TaskProcessLogViewDal dal = new TaskProcessLogViewDal();
		try {
			list = dal.geTaskProcessLogViewListById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @Title: geTaskProcessLogById
	 * @Description: TODO(获取任务单操作记录)
	 * @param id
	 * @return
	 * @return List<TaskProcessLogViewModel> 返回类型
	 * @throws
	 */
	public List<TaskProcessLogViewModel> geTaskProcessLogById(Integer id) {
		List<TaskProcessLogViewModel> list = null;
		TaskProcessLogViewDal dal = new TaskProcessLogViewDal();
		try {
			list = dal.geTaskProcessLogById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
