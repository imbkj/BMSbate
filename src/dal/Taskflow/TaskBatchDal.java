package dal.Taskflow;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.TaskBatchModel;

public class TaskBatchDal {
	// 查询批量任务
	public List<TaskBatchModel> getListByid(Integer id) throws SQLException {
		List<TaskBatchModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select taba_id,taba_tapr_id,taba_class,taba_remark,taba_addname,convert(varchar(19),taba_addtime,120)taba_addtime,taba_state "
				+ "from TaskBatch where taba_state=1 and taba_id=?";
		list = db.find(sql, TaskBatchModel.class, null, id);
		return list;
	}

	// 添加批量任务
	public Integer add(String name, String remark, String addname, Integer gid)
			throws SQLException {
		Integer i = 0;
		dbconn db = new dbconn();
		// 返回插入行ID
		i = Integer.valueOf(db.callWithReturn(
				"{?=call EmOnBoard_TaskBatch_P_py(?,?,?,?)}", Types.INTEGER,
				name, remark, addname, gid).toString());
		return i;
	}

	public Integer add(String type, String remark, String addname) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "insert into TaskBatch(taba_class,taba_remark,taba_addname,taba_addtime,taba_state)"
				+ "values(?,?,?,getdate(),?)";
		try {
			i = db.insertAndReturnKey(sql, type, remark, addname, 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	public int UpdateTaprid(int daid, int taprid) throws SQLException {
		dbconn db = new dbconn();

		int row = 0;
		String sql = "update TaskBatch set taba_tapr_id=? where taba_id=?";
		row = db.updatePrepareSQL(sql, taprid, daid);

		return row;
	}

}
