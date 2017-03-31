package dal.Workflow;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Conn.dbconn;
import Model.WfNodeModel;

public class WfNodeListDal {
	private static dbconn conn = new dbconn();

	// 根据流程ID获取节点数据集
	private ResultSet getNodeBasebyDefinationIdRs(int id) throws Exception {
		ResultSet rs = null;
		String sql = "select wfno_id,wfno_step,wfno_name,wfno_maxday,usercount=(select COUNT(distinct log_id) from Login where dep_id in(select wnar_role from WfNodeAndRole where wnar_type=1 and wnar_state=1 and wnar_wfno_id=wn.wfno_id) or log_id in (select log_id from logingroup where rol_id in(select wnar_role from WfNodeAndRole where wnar_type=2 and wnar_state=1 and wnar_wfno_id=wn.wfno_id)) or log_id in (select wnar_role from WfNodeAndRole where wnar_type=3 and wnar_state=1 and wnar_wfno_id=wn.wfno_id)),(case wfno_runtype when 1 then '人为决策' when 2 then '系统处理' end) as wfno_runtype,wfno_remark,wfno_addname,wfno_addtime,(case wfno_state when 0 then '禁用' when 1 then '启用' end) as state from WfNode wn where wfno_wfde_id=? order by wfno_state desc,wfno_step";
		PreparedStatement psmt = conn.getpre(sql);
		psmt.setInt(1, id);
		rs = psmt.executeQuery();
		return rs;
	}

	// 根据流程ID获取节点数据
	public ArrayList<String[]> getNodeBasebyDefination(int id) {
		ArrayList<String[]> nodeList = new ArrayList<String[]>();
		try {
			ResultSet rs = getNodeBasebyDefinationIdRs(id);
			while (rs.next()) {
				nodeList.add(new String[] { rs.getString("wfno_id"),
						rs.getString("wfno_step"), rs.getString("wfno_name"),
						rs.getString("wfno_runtype"),
						rs.getString("wfno_maxday"), rs.getString("usercount"),
						rs.getString("wfno_addname"),
						rs.getString("wfno_addtime"),
						rs.getString("wfno_remark"), rs.getString("state") });
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nodeList;
	}

	// 根据流程ID获取流程名称数据集
	private ResultSet getDefinationNamebyIdRs(int id) throws Exception {
		ResultSet rs = null;
		String sql = "select wfde_name from WfDefination where wfde_id=?";
		PreparedStatement psmt = conn.getpre(sql);
		psmt.setInt(1, id);
		rs = psmt.executeQuery();
		return rs;
	}

	// 根据流程ID获取流程名称数据
	public String getDefinationNamebyId(int id) {
		String DefinationName = null;
		try {
			ResultSet rs = getDefinationNamebyIdRs(id);
			while (rs.next()) {
				DefinationName = rs.getString("wfde_name");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return DefinationName;
	}

	// 根据节点ID获取节点数据集
	private ResultSet getNodeBasebyIdRs(int id) throws Exception {
		ResultSet rs = null;
		String sql = "select * from WfNode where wfno_id=?";
		PreparedStatement psmt = conn.getpre(sql);
		psmt.setInt(1, id);
		rs = psmt.executeQuery();
		return rs;
	}

	// 根据节点ID获取节点表数据
	public ArrayList<WfNodeModel> getNodeBasebyId(int id) {
		ArrayList<WfNodeModel> nodeList = new ArrayList<WfNodeModel>();
		try {
			ResultSet rs = getNodeBasebyIdRs(id);
			while (rs.next()) {
				nodeList.add(new WfNodeModel(rs.getInt("wfno_id"), rs
						.getInt("wfno_wfde_id"), rs.getString("wfno_name"), rs
						.getInt("wfno_step"), rs.getInt("wfno_maxday"), rs
						.getString("wfno_selectuser"), rs
						.getInt("wfno_runtype"), rs
						.getString("wfno_runprocedure"), rs
						.getString("wfno_url"), rs.getInt("wfno_ifview"), rs
						.getInt("wfno_ifskip"), rs.getInt("wfno_ifreturn"), rs
						.getInt("wfno_ifstop"), rs.getInt("wfno_ifrevoke"), rs
						.getInt("wfno_ifhavechild"), rs
						.getString("wfno_remark"),
						rs.getString("wfno_addname"), rs
								.getString("wfno_addtime"), rs
								.getString("wfno_updatename"), rs
								.getString("wfno_updatetime"), rs
								.getInt("wfno_state")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nodeList;
	}
	
	//是否存在有效节点
	private ResultSet checkHaveNodeRs(int wfde_id) throws Exception {
		ResultSet rs = null;
		String sql = "select COUNT(wfno_id) as count from WfNode where wfno_wfde_id=? and wfno_state=1";
		PreparedStatement psmt = conn.getpre(sql);
		psmt.setInt(1, wfde_id);
		rs = psmt.executeQuery();
		return rs;
	} 
	public boolean checkHaveNode(int wfde_id) throws Exception{
		try {
			ResultSet rs = checkHaveNodeRs(wfde_id);
			rs.next();
			if(rs.getInt("count")>0){
				return true;
			}else{
				return false;
			}
		} catch (SQLException e) {
			return false;
		}
	}
}
