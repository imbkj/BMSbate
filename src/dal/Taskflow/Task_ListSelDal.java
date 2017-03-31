package dal.Taskflow;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.LoginModel;
import Model.TaskListModel;
import Util.MapStringChange;

public class Task_ListSelDal {
	private static dbconn conn = new dbconn();

	// 查询流程类型
	public List<TaskListModel> getFlowClass() {
		List<TaskListModel> list = new ArrayList<TaskListModel>();
		String sql = "select distinct wfcl_name from WfClass where wfcl_state=1 order by wfcl_name";
		try {
			list = conn.find(sql, TaskListModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询流程
	public List<TaskListModel> getFlow() {
		List<TaskListModel> list = new ArrayList<TaskListModel>();
		String sql = "select distinct wfde_name,wfcl_name from WfDefination a inner join WfClass b on a.wfde_wfcl_id=b.wfcl_id"
				+ " where wfde_state=1" + " order by wfde_name";
		try {
			list = conn.find(sql, TaskListModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询任务单类型
	public List<TaskListModel> getMissionClass(String str) {
		List<TaskListModel> list = new ArrayList<TaskListModel>();
		String sql = "select distinct tacl_id,tacl_name from"
				+ " (select * from TaskList union all select * from tasklist2) a"
				+ " inner join TaskClass b on a.tali_tacl_id=b.tacl_id"
				+ " where tacl_name like '%'+?+'%' or tali_name like '%'+?+'%'"
				+ " order by tacl_name";

		try {
			list = conn.find(sql, TaskListModel.class, null, str, str);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询任务单类型
	public List<TaskListModel> getMissionClass2(String str) {
		List<TaskListModel> list = new ArrayList<TaskListModel>();
		String sql = "select distinct tacl_id,tacl_name" + " from WfClass a "
				+ " inner join WfDefination b on a.wfcl_id=b.wfde_wfcl_id"
				+ " inner join v_tasklist c on b.wfde_id=c.tali_wfde_id"
				+ " inner join TaskClass d on c.tali_tacl_id=d.tacl_id"
				+ " where wfcl_name like '%'+?+'%'"
				+ " group by tacl_id,tacl_name" + " order by tacl_name";
		try {
			list = conn.find(sql, TaskListModel.class, null, str);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询任务
	public List<TaskListModel> getMission(String str, Integer taclId) {
		List<TaskListModel> list = new ArrayList<TaskListModel>();
		String sql = "select a.tali_id,a.tali_name,convert(varchar(19),tali_addtime,120) tali_addtimeStr,tacl_id,b.tacl_name,c.tapr_id,"
				+ "case a.tali_state when 0 then '取消' when 2 then '已完成' when 3 then '已终止' else d.wfno_name end state,tali_state,tali_searchCon searchConMap,"
				+ " wfde_name,wfcl_name"
				+ " from v_tasklist a"
				+ " inner join TaskClass b on a.tali_tacl_id=b.tacl_id"
				+ " inner join WfDefination e on a.tali_wfde_id=e.wfde_id"
				+ " inner join WfClass f on f.wfcl_id=e.wfde_wfcl_id"
				+ " left join v_TaskProcess c on a.tali_id=c.tapr_tali_id and tapr_state=1"
				+ " left join WfNode d on c.tapr_wfno_id=d.wfno_id"
				+ " where tali_name like '%'+?+'%'";
		if (taclId != null && !taclId.equals("")) {
			sql += " and tacl_id=" + taclId;
		}
		if (str == null) {
			str = "";
		}
		sql += " order by tali_addtime desc";

		try {
			list = conn.find(sql, TaskListModel.class, null, str);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询任务
	public List<TaskListModel> getMission2(String str, Integer taclId) {
		List<TaskListModel> list = new ArrayList<TaskListModel>();
		String sql = "select a.tali_id,a.tali_name,convert(varchar(19),tali_addtime,120) tali_addtimeStr,tacl_id,b.tacl_name,c.tapr_id,"
				+ "case a.tali_state when 0 then '取消' when 2 then '已完成' when 3 then '已终止' else d.wfno_name end state,tali_state,tali_searchCon searchConMap,"
				+ " wfde_name,wfcl_name"
				+ " from v_tasklist a"
				+ " inner join TaskClass b on a.tali_tacl_id=b.tacl_id"
				+ " inner join WfDefination e on a.tali_wfde_id=e.wfde_id"
				+ " inner join WfClass f on f.wfcl_id=e.wfde_wfcl_id"
				+ " left join v_TaskProcess c on a.tali_id=c.tapr_tali_id and tapr_state=1"
				+ " left join WfNode d on c.tapr_wfno_id=d.wfno_id"
				+ " where wfcl_name like '%'+?+'%'";
		if (taclId != null && !taclId.equals("")) {
			sql += " and tacl_id=" + taclId;
		}
		sql += " order by tali_addtime desc";

		try {
			list = conn.find(sql, TaskListModel.class, null, str);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询任务
	public List<TaskListModel> getMission3(String cid, String gid,
			String ownmonth, String tali_id) {
		List<TaskListModel> list = new ArrayList<TaskListModel>();
		String sql = "select a.tali_id,a.tali_name,convert(varchar(19),tali_addtime,120) tali_addtimeStr,tacl_id,b.tacl_name,c.tapr_id,"
				+ "case a.tali_state when 0 then '取消' when 2 then '已完成' when 3 then '已终止' else d.wfno_name end state,tali_state,tali_searchCon searchConMap,"
				+ " wfde_name,wfcl_name"
				+ " from v_tasklist a"
				+ " inner join TaskClass b on a.tali_tacl_id=b.tacl_id"
				+ " inner join WfDefination e on a.tali_wfde_id=e.wfde_id"
				+ " inner join WfClass f on f.wfcl_id=e.wfde_wfcl_id"
				+ " left join v_TaskProcess c on a.tali_id=c.tapr_tali_id and tapr_state=1"
				+ " left join WfNode d on c.tapr_wfno_id=d.wfno_id"
				+ " where 1=1";
		if (cid != null && !cid.equals("")) {
			sql += " and tali_searchCon like '%cid=" + cid + "%'";
		}
		if (gid != null && !gid.equals("")) {
			sql += " and tali_searchCon like '%gid=" + gid + "%'";
		}
		if (ownmonth != null && !ownmonth.equals("")) {
			sql += " and tali_searchCon like '%ownmonth=" + ownmonth + "%'";
		}
		if (tali_id != null && !tali_id.equals("")) {
			sql += " and tali_id=" + tali_id;
		}
		sql += " order by tali_addtime desc";
		System.out.println(sql);
		try {
			list = conn.find(sql, TaskListModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 我的任务单列表
	public List<TaskListModel> getMyTaskList(String username) {
		List<TaskListModel> list = new ArrayList<TaskListModel>();
		TaskListModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select tl.tali_id,tl.tali_name,CONVERT(varchar(30), tl.tali_addtime, 20) as tali_addtime,tc.tacl_name,tp.tapr_id,");
		sql.append("(case tl.tali_state when 0 then '取消' when 2 then '已完成' when 3 then '已终止' else wn.wfno_name end) as state,tali_searchCon"
				+ " from (select * from TaskList union all select * from tasklist2) tl ");
		sql.append("left join (select tacl_id,tacl_name from TaskClass) tc on tl.tali_tacl_id=tc.tacl_id ");
		sql.append("left join (select tapr_id,tapr_tali_id,tapr_wfno_id from (select * from TaskProcess union all select * from TaskProcess2) where tapr_state=1) tp on tl.tali_id=tp.tapr_tali_id ");
		sql.append("left join (select wfno_id,wfno_name from WfNode) wn on tp.tapr_wfno_id=wn.wfno_id ");
		sql.append("where tl.tali_addname=? ");
		sql.append("order by tl.tali_addtime desc");
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setString(1, username);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new TaskListModel();
				m.setTali_id(rs.getInt("tali_id"));
				m.setTacl_name(rs.getString("tacl_name"));
				m.setTali_name(rs.getString("tali_name"));
				m.setTali_addtimeStr(rs.getString("tali_addtime"));
				m.setTapr_id(rs.getInt("tapr_id"));
				m.setState(rs.getString("state"));
				m.setSearchConMap(MapStringChange.StringToMap(rs
						.getString("tali_searchCon")));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询所有任务单
	public List<TaskListModel> getAllTaskList() {
		List<TaskListModel> list = new ArrayList<TaskListModel>();
		TaskListModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select wc.wfcl_id,wc.wfcl_name,wd.wfde_id,wd.wfde_name,tc.tacl_id,tc.tacl_name,");
		sql.append("tl.tali_id,tl.tali_name,CONVERT(varchar(30), tl.tali_addtime, 20) as tali_addtime,tl.tali_addname,tali_state,tali_searchCon"
				+ " from (select * from TaskList union all select * from tasklist2) tl ");
		sql.append("inner join (select tacl_id,tacl_name from TaskClass) tc on tl.tali_tacl_id=tc.tacl_id ");
		sql.append("inner join (select wfde_wfcl_id,wfde_id,wfde_name from WfDefination) wd on tl.tali_wfde_id=wd.wfde_id ");
		sql.append("inner join (select wfcl_id,wfcl_name from WfClass) wc on wc.wfcl_id=wd.wfde_wfcl_id ");
		sql.append("order by tali_addtime desc");
		try {
			ResultSet rs = conn.GRS(sql.toString());
			while (rs.next()) {
				m = new TaskListModel();
				m.setWfcl_id(rs.getInt("wfcl_id"));
				m.setWfcl_name(rs.getString("wfcl_name"));
				m.setWfde_id(rs.getInt("wfde_id"));
				m.setWfde_name(rs.getString("wfde_name"));
				m.setTacl_id(rs.getInt("tacl_id"));
				m.setTacl_name(rs.getString("tacl_name"));
				m.setTali_id(rs.getInt("tali_id"));
				m.setTali_name(rs.getString("tali_name"));
				m.setTali_addtimeStr(rs.getString("tali_addtime"));
				m.setTali_addname(rs.getString("tali_addname"));
				m.setTali_state(rs.getInt("tali_state"));
				m.setSearchConMap(MapStringChange.StringToMap(rs
						.getString("tali_searchCon")));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据部门查询任务单
	public List<TaskListModel> getTaskListByDept(String dep_name) {
		List<TaskListModel> list = new ArrayList<TaskListModel>();
		TaskListModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select wc.wfcl_id,wc.wfcl_name,wd.wfde_id,wd.wfde_name,tc.tacl_id,tc.tacl_name,");
		sql.append("tl.tali_id,tl.tali_name,CONVERT(varchar(30), tl.tali_addtime, 20) as tali_addtime,tl.tali_addname,tali_state,tali_searchCon"
				+ " from (select * from TaskList union all select * from tasklist2) tl ");
		sql.append("inner join (select tacl_id,tacl_name from TaskClass) tc on tl.tali_tacl_id=tc.tacl_id ");
		sql.append("inner join (select wfde_wfcl_id,wfde_id,wfde_name from WfDefination) wd on tl.tali_wfde_id=wd.wfde_id ");
		sql.append("inner join (select wfcl_id,wfcl_name from WfClass) wc on wc.wfcl_id=wd.wfde_wfcl_id ");
		sql.append("where exists (select tapr_tali_id from (select * from TaskProcess union all select * from TaskProcess2) tp inner join Login l on tp.tapr_endname=l.log_name inner join department dep on l.dep_id=dep.dep_id where tapr_tali_id=tl.tali_id and dep.dep_name=?) ");
		sql.append("order by tali_addtime desc");
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setString(1, dep_name);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new TaskListModel();
				m.setWfcl_id(rs.getInt("wfcl_id"));
				m.setWfcl_name(rs.getString("wfcl_name"));
				m.setWfde_id(rs.getInt("wfde_id"));
				m.setWfde_name(rs.getString("wfde_name"));
				m.setTacl_id(rs.getInt("tacl_id"));
				m.setTacl_name(rs.getString("tacl_name"));
				m.setTali_id(rs.getInt("tali_id"));
				m.setTali_name(rs.getString("tali_name"));
				m.setTali_addtimeStr(rs.getString("tali_addtime"));
				m.setTali_addname(rs.getString("tali_addname"));
				m.setTali_state(rs.getInt("tali_state"));
				m.setSearchConMap(MapStringChange.StringToMap(rs
						.getString("tali_searchCon")));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据操作人查询任务单
	public List<TaskListModel> getTaskListByUsername(String username) {
		List<TaskListModel> list = new ArrayList<TaskListModel>();
		TaskListModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select wc.wfcl_id,wc.wfcl_name,wd.wfde_id,wd.wfde_name,tc.tacl_id,tc.tacl_name,");
		sql.append("tl.tali_id,tl.tali_name,CONVERT(varchar(30), tl.tali_addtime, 20) as tali_addtime,tl.tali_addname,tali_state,tali_searchCon"
				+ " from (select * from TaskList union all select * from tasklist2) tl ");
		sql.append("inner join (select tacl_id,tacl_name from TaskClass) tc on tl.tali_tacl_id=tc.tacl_id ");
		sql.append("inner join (select wfde_wfcl_id,wfde_id,wfde_name from WfDefination) wd on tl.tali_wfde_id=wd.wfde_id ");
		sql.append("inner join (select wfcl_id,wfcl_name from WfClass) wc on wc.wfcl_id=wd.wfde_wfcl_id ");
		sql.append("where exists (select tapr_tali_id from (select * from TaskProcess union all select * from TaskProcess2) where tapr_endname=? and tapr_tali_id=tl.tali_id) ");
		sql.append("order by tali_addtime desc");
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setString(1, username);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new TaskListModel();
				m.setWfcl_id(rs.getInt("wfcl_id"));
				m.setWfcl_name(rs.getString("wfcl_name"));
				m.setWfde_id(rs.getInt("wfde_id"));
				m.setWfde_name(rs.getString("wfde_name"));
				m.setTacl_id(rs.getInt("tacl_id"));
				m.setTacl_name(rs.getString("tacl_name"));
				m.setTali_id(rs.getInt("tali_id"));
				m.setTali_name(rs.getString("tali_name"));
				m.setTali_addtimeStr(rs.getString("tali_addtime"));
				m.setTali_addname(rs.getString("tali_addname"));
				m.setTali_state(rs.getInt("tali_state"));
				m.setSearchConMap(MapStringChange.StringToMap(rs
						.getString("tali_searchCon")));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取任务单操作人列表
	public List<LoginModel> getLogName() {
		List<LoginModel> list = new ArrayList<LoginModel>();
		LoginModel m = null;
		try {
			ResultSet rs = conn.GRS("exec TaskProcessLogUser_P_lwj");
			while (rs.next()) {
				m = new LoginModel();
				m.setLog_id(rs.getInt("log_id"));
				m.setLog_name(rs.getString("log_name"));
				m.setDep_id(rs.getInt("dep_id"));
				m.setDep_name(rs.getString("dep_name"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
