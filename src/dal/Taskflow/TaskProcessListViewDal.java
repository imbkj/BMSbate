package dal.Taskflow;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.TaskProcessListModel;

public class TaskProcessListViewDal {
	/**
	 * @Title: getList
	 * @Description: TODO(查询任务单视图列表)
	 * @param logid
	 * @param depid
	 * @param rolid
	 * @param taclName
	 * @param taliName
	 * @return
	 * @throws SQLException
	 * @return List<TaskProcessListModel> 返回类型
	 * @throws
	 */
	public List<TaskProcessListModel> getList(String logid, String depid,
			String rolid, String userid, String taclName, String taliName)
			throws SQLException {
		List<TaskProcessListModel> list = new ListModelList<TaskProcessListModel>();
		dbconn db = new dbconn();
		String sql = "select no,wfde_id,tacl_id,tali_id,tacl_name,tali_name,tapr_id,wfno_id,wfno_name,tapr_starname,tapr_starttime,tapr_endname,tapr_endtime,tapr_lastid,tapr_statename from View_TaskProcessList where tali_id in (select tapr_tali_id from TaskProcess where tapr_starname in (select username from dbo.GetChildbase(?,6)a inner join View_loginrole b on a.UnitId=b.log_id where b.dep_id like ? and b.rol_id like ? and b.log_id like ?)) and tacl_id like ? and tali_name like ? order by wfde_id,tali_id,tapr_starttime desc";
		list = db.find(sql, TaskProcessListModel.class,
				dbconn.parseSmap(TaskProcessListModel.class), logid, depid,
				rolid, userid, taclName, taliName);

		return list;
	}
}
