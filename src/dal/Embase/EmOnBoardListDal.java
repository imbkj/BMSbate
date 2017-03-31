package dal.Embase;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.EmOnBoardListModel;
import Model.TaskBatchModel;
import Model.TaskListModel;

public class EmOnBoardListDal {
	public List<EmOnBoardListModel> getInfoById(Integer id) throws SQLException {
		List<EmOnBoardListModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select cid,gid,ownmonth,embo_class,embo_state,embo_pid,embo_addtime,embo_addname "
				+ "from EmOnBoardList where embo_id=?";

		list = db.find(sql, EmOnBoardListModel.class, null, id);

		return list;
	}

	public List<EmOnBoardListModel> getInfoByGid(Integer gid) {
		List<EmOnBoardListModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select a.cid,a.gid,a.ownmonth,embo_class,embo_state,embo_pid,"
				+ "convert(varchar(10),embo_addtime,120)embo_addtime,embo_addname,"
				+ "emba_sb_place,emba_house_place,emba_sb_radix,emba_house_radix,"
				+ "emba_emsb_m1,emba_emhb_startdate,emba_emsb_ownmonth,emba_emhb_ownmonth,"
				+ "emba_formula,emba_house_cpp,emba_emsb_foreigner,copr_copc_id,copr_cpac_id,copr_name,emba_nationality "
				+ "from EmOnBoardList a "
				+ "inner join EmBase b on a.gid=b.gid "
				+ "inner join coproduct c on a.embo_class=c.copr_name "
				+ "where embo_state=0 and a.gid=?";

		try {
			list = db.find(sql, EmOnBoardListModel.class, null, gid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// 判断入职任务列表
	public List<EmOnBoardListModel> judgecs(Integer gid, Integer ownmonth) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "select gid,embo_state,embo_pid,ownmonth"
				+ " from EmOnBoardList" + " where gid=? and ownmonth>=?";
		List<EmOnBoardListModel> list = new ListModelList<>();
		try {
			list = db.find(sql, EmOnBoardListModel.class, null, gid, ownmonth);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public Integer add(Integer cid, Integer gid, Integer ownmonth, String id,
			String addname) throws SQLException {
		Integer i = 0;
		dbconn db = new dbconn();

		i = Integer.valueOf(db.callWithReturn(
				"{?=call EmOnBoardList_Add_P_py(?,?,?,?,?)}", Types.INTEGER,
				cid, gid, ownmonth, id, addname).toString());
		return i;
	}

	// 缤纷服务方法
	public Integer getbffwforgid(Integer gid) throws SQLException {
		Integer i = 0;
		dbconn db = new dbconn();

		i = Integer.valueOf(db.callWithReturn(
				"{?=call EmBase_bffwforone_p_zmj(?)}", Types.INTEGER, gid)
				.toString());
		return i;
	}

	// 查询是否属于入职任务
	public boolean getTask(Integer taprId) {
		boolean b = false;
		List<TaskListModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select tali_id from TaskList a "
				+ "inner join TaskProcess b on a.tali_id=b.tapr_tali_id "
				+ "where b.tapr_id=? "
				+ "and tali_wfbu_id in (select id from dbo.getBusinessCenterPid(tali_wfbu_id))";
		try {
			list = db.find(sql, TaskListModel.class, null, taprId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (list.size() > 0) {
			b = true;
		}
		return b;
	}

	// 查询员工入职任务是否已全部完成
	public Integer getFinish(Integer id) {
		List<EmOnBoardListModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select count(*) from EmOnBoardList "
				+ "where gid in (select gid from EmOnBoardList where embo_id =?) and embo_state=0";
		try {
			list = db.find(sql, EmOnBoardListModel.class, null, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list.size();
	}

	// 查询入职任务流程taprID
	public List<TaskBatchModel> getTaprId(Integer id) {
		List<TaskBatchModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select taba_id,taba_tapr_id from TaskBatch a "
				+ "inner join TaskBatchRelBusiness b on a.taba_id=b.tbrb_taba_id "
				+ "inner join EmOnBoardList c on b.tbrb_data_id=c.embo_id";
		try {
			list = db.find(sql, TaskBatchModel.class, null, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 更新入职任务状态
	public Integer completeFlow(Integer dataid, Integer taprId, String tbName) {
		Integer i = 0;
		dbconn db = new dbconn();

		try {
			i = Integer.valueOf(db.callWithReturn(
					"{?=call EmOnBoard_TaskBatchComplete_P_py(?,?,?)}",
					Types.INTEGER, dataid, taprId, tbName).toString());
		} catch (NumberFormatException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	public Integer updateFlow(Integer gid, Integer ownmonth, Integer pid) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "update EmOnBoardList set embo_state=1"
				+ " where gid=? and ownmonth=?";
		if (pid != null && !pid.equals("")) {
			sql += " and embo_pid=" + pid;
		}
		try {
			i = db.updatePrepareSQL(sql, gid, ownmonth);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;

	}
}
