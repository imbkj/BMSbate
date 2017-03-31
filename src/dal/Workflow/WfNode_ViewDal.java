package dal.Workflow;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import Conn.dbconn;

public class WfNode_ViewDal {
	private static dbconn conn = new dbconn();

	// 获取节点名称数据集
	private ResultSet getNodeNameRs(int wfno_id) throws Exception {
		ResultSet rs = null;
		String sql = "select wfno_name from WfNode where wfno_state=1 and wfno_wfde_id=? order by wfno_step";
		PreparedStatement psmt = conn.getpre(sql);
		psmt.setInt(1, wfno_id);
		rs = psmt.executeQuery();
		return rs;
	}

	// 获取部门数据
	public ArrayList<String> getNodeName(int wfno_id) {
		ArrayList<String> nodeList = new ArrayList<String>();
		try {
			ResultSet rs = getNodeNameRs(wfno_id);
			while (rs.next()) {
				nodeList.add(rs.getString("wfno_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nodeList;
	}
}
