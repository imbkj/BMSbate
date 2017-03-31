package dal.Taskflow;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.TaskListModel;
import Model.TaskProcessModel;

public class TaskListDal {
	/**
	 * @Title: getDisList
	 * @Description: TODO(获取任务表生效的唯一名称列表)
	 * @return
	 * @throws SQLException
	 * @return List<TaskListModel> 返回类型
	 * @throws
	 */
	public List<TaskListModel> getDisList() {
		List<TaskListModel> list = new ListModelList<TaskListModel>();
		dbconn db = new dbconn();
		String sql = "select '全部' tali_name union all select distinct tali_name from TaskList where tali_state=1";
		try {
			list = db.find(sql, TaskListModel.class,
					dbconn.parseSmap(TaskListModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//查询节点信息
	public List<TaskProcessModel> getlist(Integer taprId){
		List<TaskProcessModel> list = new ListModelList<TaskProcessModel>();
		dbconn db = new dbconn();
		String sql="select tapr_id,tapr_appointid,tapr_starname,tapr_appointcon" +
				" from TaskProcess" +
				" where tapr_id=?";
		try {
			list=db.find(sql, TaskProcessModel.class, null, taprId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<TaskListModel> getListByTaprId(Integer id) {
		List<TaskListModel> list = new ListModelList<>();
		String sql = "select * from TaskList a " +
				"inner join TaskProcess b on a.tali_id=b.tapr_tali_id " +
				"where tapr_id=?";
		dbconn db = new dbconn();
		try {
			list = db.find(sql, TaskListModel.class, null, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
