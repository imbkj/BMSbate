package dal.Taskflow;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.TaskProcessLogViewModel;

public class TaskProcessLogViewDal {

	// 获取任务单流程明细
	public List<TaskProcessLogViewModel> geTaskProcessLogViewListById(Integer id)
			throws SQLException {
		List<TaskProcessLogViewModel> list = new ListModelList<TaskProcessLogViewModel>();
		dbconn db = new dbconn();
		StringBuilder sql = new StringBuilder();
		sql.append("select tl.tali_name,tl.tali_urgentState,wn.wfno_name,tp.tapr_id,tp.tapr_urgentState,tapr_remark,tapr_endname,tapr_endtime=CONVERT(varchar(19), tp.tapr_endtime, 120),tapr_state  from v_TaskProcess tp ");
		sql.append("inner join WfNode wn on tp.tapr_wfno_id=wn.wfno_id ");
		sql.append("inner join v_tasklist tl on tl.tali_id=tp.tapr_tali_id ");
		sql.append("where tp.tapr_tali_id=? and (tapr_state=1 or tapr_state=2 or tapr_state=5)");
		sql.append("order by tp.tapr_id desc");
		try {
			list = db.find(sql.toString(), TaskProcessLogViewModel.class,
					dbconn.parseSmap(TaskProcessLogViewModel.class), id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取任务单操作记录
	public List<TaskProcessLogViewModel> geTaskProcessLogById(Integer id)
			throws SQLException {
		List<TaskProcessLogViewModel> list = new ListModelList<TaskProcessLogViewModel>();
		dbconn db = new dbconn();
		StringBuilder sql = new StringBuilder();
		sql.append("select tpl.tapl_id,wn.wfno_name,tapl_username,tapl_addtime=CONVERT(varchar(19), tapl_addtime, 120),tapl_content,tapl_remark" +
				" from TaskProcessLog tpl ");
		sql.append("inner join v_TaskProcess tp on tpl.tapl_tapr_id=tp.tapr_id ");
		sql.append("inner join WfNode wn on tp.tapr_wfno_id=wn.wfno_id ");
		sql.append("where tp.tapr_tali_id=? ");
		sql.append("order by tapl_id desc");
		try {
			list = db.find(sql.toString(), TaskProcessLogViewModel.class,
					dbconn.parseSmap(TaskProcessLogViewModel.class), id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
