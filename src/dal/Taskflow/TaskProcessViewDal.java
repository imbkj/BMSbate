package dal.Taskflow;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.TaskProcessViewModel;

public class TaskProcessViewDal {
	public List<TaskProcessViewModel> getView_WfNode_TaskProcessDalsByTaliId(
			Integer id) throws SQLException {
		List<TaskProcessViewModel> list = new ListModelList<TaskProcessViewModel>();
		dbconn db = new dbconn();
		String sql = "select wfno_id, wfde_id,wfde_name, wfno_name, wfno_url,wfno_step, tacl_id, tali_id,tacl_name, tali_name,tali_wfde_id, tapr_id,tapr_wfno_id, tapr_dataid, tapr_starname,tapr_starttime, tapr_endname, tapr_endtime,tapr_state, tapr_statename,twi"
				+ " from View_WfNode_TaskProcess"
				+ " where wfno_state=1 and tali_id=?"
				+ " and ( (tapr_state!=0 or (tapr_state=3 and tapr_endtime is null)) or tapr_state is null)"
				+ " and (tapr_id is null or tapr_id in ("
				+ "	select MAX(tapr_id) from View_WfNode_TaskProcess where tali_id=? group by wfno_id,wfde_id,tapr_dataid)) "
				+ "order by wfno_step";
		
		try {
			list = db.find(sql, TaskProcessViewModel.class, null, id,id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<TaskProcessViewModel> getLastId(Integer id) throws SQLException {
		List<TaskProcessViewModel> list = new ListModelList<TaskProcessViewModel>();
		dbconn db = new dbconn();
		String sql = "select top 1 wfno_id from View_WfNode_TaskProcess where wfno_state=1 and tali_id=? order by wfno_step desc";
		try {
			list = db.find(sql, TaskProcessViewModel.class,
					dbconn.parseSmap(TaskProcessViewModel.class), id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

}
