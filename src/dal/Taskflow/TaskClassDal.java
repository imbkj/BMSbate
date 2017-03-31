package dal.Taskflow;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.TaskClassModel;
import Model.TaskListModel;

public class TaskClassDal {
	/** 
	* @Title: getDisList 
	* @Description: TODO(查询生效任务类型唯一列表) 
	* @return
	* @throws SQLException
	* @return List<TaskClassModel>    返回类型 
	* @throws 
	*/
	public List<TaskClassModel> getDisList() throws SQLException {
		List<TaskClassModel> list = new ListModelList<TaskClassModel>();
		dbconn db = new dbconn();
		String sql = "select 0 tacl_id ,'全部' tacl_name union all select distinct tacl_id,tacl_name from TaskClass where tacl_state=1";
		list = db.find(sql, TaskClassModel.class,
				dbconn.parseSmap(TaskClassModel.class));
		return list;
	}
}
