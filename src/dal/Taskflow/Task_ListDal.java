package dal.Taskflow;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.TaskListModel;

public class Task_ListDal {

	// 获取任务单集合
	public List<TaskListModel> getlist(int login_id) {
		List<TaskListModel> TaskM = new ArrayList<TaskListModel>();
		try {
			if (!TaskM.isEmpty())
				TaskM.clear();
			ResultSet rs = null;
			String sqlstr = "select * from View_Tasklist ";
			// System.out.print(sqlstr);
			try {
				dbconn db = new dbconn();
				rs = db.GRS(sqlstr);
				while (rs.next()) {
					TaskM.add(new TaskListModel(rs.getString("tacl_name"), rs
							.getString("tacl_state"),
							rs.getString("tali_name"), rs.getInt("tali_id")));
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return TaskM;
	}

	// 查询当前任务是否正在执行或已完成
	public boolean taskState(String name) {
		boolean b = false;
		dbconn db = new dbconn();
		String sql = "select 1 from tasklist where tali_name =? and tali_state in(1,2)";
		List<TaskListModel> list = new ListModelList<>();
		try {
			list = db.find(sql, TaskListModel.class, null, name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (list.size() > 0) {
			b = true;
		}
		return b;
	}

}
