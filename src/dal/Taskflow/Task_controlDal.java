package dal.Taskflow;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.TaskProcessModel;
import Model.WfNodeModel;

public class Task_controlDal {
	private static dbconn conn = new dbconn();

	// 根据任务单编号获取任务单的节点信息
	public List<WfNodeModel> getNode(int tali_id) {
		List<WfNodeModel> list = new ArrayList<WfNodeModel>();
		WfNodeModel m;
		StringBuilder sql = new StringBuilder();
		sql.append("select wn.wfno_id,wn.wfno_name,wn.wfno_step,nowNode=(case when tp.tapr_id is not null then tp.tapr_id else 0 end) from WfNode wn ");
		sql.append("inner join TaskList tl on wn.wfno_wfde_id=tl.tali_wfde_id ");
		sql.append("left join TaskProcess tp on tp.tapr_tali_id=tl.tali_id and tp.tapr_state=1 and tp.tapr_wfno_id=wn.wfno_id ");
		sql.append("where wfno_state=1 and tl.tali_id=? ");
		sql.append("order by wfno_step");
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setInt(1, tali_id);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new WfNodeModel();
				m.setWfno_id(rs.getInt("wfno_id"));
				m.setWfno_name(rs.getString("wfno_name"));
				m.setWfno_step(rs.getInt("wfno_step"));
				m.setNowNode(rs.getInt("nowNode"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据任务单编号获取流程信息
	public TaskProcessModel getNowProcess(int tali_id) {
		TaskProcessModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select tapr_id,wn.wfno_step,tapr_appointid,tapr_dataid,wn.wfno_id from v_TaskProcess tp ");
		sql.append("inner join WfNode wn on tp.tapr_wfno_id=wn.wfno_id ");
		sql.append(" where tapr_tali_id=? and tapr_state=1");
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setInt(1, tali_id);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				m = new TaskProcessModel();
				m.setTapr_id(rs.getInt("tapr_id"));
				m.setWfno_step(rs.getInt("wfno_step"));
				m.setTapr_appointname(rs.getInt("tapr_appointid"));
				m.setTapr_dataid(rs.getInt("tapr_dataid"));
				m.setTapr_wfno_id(rs.getInt("wfno_id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	// 设置操作人
	public int setOpName(int tapr_id, String name) {
		try {
			CallableStatement c = conn.getcall("WfCoreSetOpName_P_lwj(?,?,?)");
			c.setInt(1, tapr_id);
			c.setString(2, name);
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 节点暂缓处理
	public int dalayOp(int tapr_id, String remark, String username) {
		try {
			CallableStatement c = conn.getcall("WfCoreProDalay_P_lwj(?,?,?,?)");
			c.setInt(1, tapr_id);
			c.setString(2, remark);
			c.setString(3, username);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 节点催促处理
	public int urgeOp(int tapr_id, String remark, String username) {
		try {
			CallableStatement c = conn
					.getcall("WfCoreTaskListUrge_P_lwj(?,?,?,?)");
			c.setInt(1, tapr_id);
			c.setString(2, remark);
			c.setString(3, username);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 节点权限更新
	public int updateAppointid(int tapr_id) {
		dbconn db = new dbconn();
		Integer i = 0;
		String sql = "update taskprocess set tapr_appointcon=tapr_starname,tapr_state=1 where tapr_id=?";
		try {
			i = db.updatePrepareSQL(sql, tapr_id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	// 节点权限更新
	public int resumedState(int tapr_id) {
		dbconn db = new dbconn();
		Integer i = 0;
		String sql = "update TaskList set tali_state=1 where tali_id in (select tapr_tali_id from taskprocess where tapr_id=?)";
		try {
			i = db.updatePrepareSQL(sql, tapr_id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
}
