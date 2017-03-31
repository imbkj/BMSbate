package dal.Workflow;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.WfBusinessModel;
import Model.WfDefinationModel;

public class WfDeploymentListDal {
	private dbconn conn = new dbconn();

	// 获取所有业务
	public List<WfBusinessModel> getBusiness() {
		List<WfBusinessModel> list = new ArrayList<WfBusinessModel>();
		String sql = "select wfbu_id,wfbu_name,wfbu_remark,wfbu_addname,CONVERT(varchar(100), wfbu_addtime, 20) as wfbu_addtime,wfbu_state from WfBusiness";
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				list.add(new WfBusinessModel(rs.getInt("wfbu_id"), rs
						.getString("wfbu_name"), rs.getString("wfbu_remark"),
						rs.getString("wfbu_addname"), rs
								.getString("wfbu_addtime"), rs
								.getInt("wfbu_state")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//根据业务名称查找流程
	public List<WfBusinessModel> getDefinationByName(String name) {
		List<WfBusinessModel> list = new ArrayList<WfBusinessModel>();
		name = name.replace("'", "");
		String sql = "select wfbu_id,wfbu_name,wfbu_remark,wfbu_addname,CONVERT(varchar(100), wfbu_addtime, 20) as wfbu_addtime,wfbu_state " +
				"from WfBusiness " +
				"where wfbu_name like '%"+name+"%'";
		
		dbconn db = new dbconn();
		System.out.println(sql);
		try {
			list = db.find(sql, WfBusinessModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	// 根据业务ID查找流程
	public List<WfDefinationModel> getDefinationByBid(int wfbu_id) {
		List<WfDefinationModel> list = new ArrayList<WfDefinationModel>();
		WfDefinationModel m = null;
		String sql = "select wt.wfde_id,wfde_name,wm.wfde_remark,wt.wfde_addname,wm.wfde_state from WfDeployment wm left join WfDefination wt on wt.wfde_id=wm.wfde_wfde_id where wm.wfde_wfbu_id=? order by wt.wfde_state desc";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, wfbu_id);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new WfDefinationModel();
				m.setWfde_id(rs.getInt("wfde_id"));
				m.setWfde_name(rs.getString("wfde_name"));
				m.setWfde_remark(rs.getString("wfde_remark"));
				m.setWfde_addname(rs.getString("wfde_addname"));
				m.setWfde_state(rs.getInt("wfde_state"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取流程列表排除已选
	public List<WfDefinationModel> getDefinationExBid(int wfbu_id) {
		List<WfDefinationModel> list = new ArrayList<WfDefinationModel>();
		WfDefinationModel m = null;
		String sql = "select wfde_id,wfde_name from WfDefination where wfde_state=1 and wfde_id not in (select wfde_wfde_id from WfDeployment where wfde_wfbu_id=?)";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, wfbu_id);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new WfDefinationModel();
				m.setWfde_id(rs.getInt("wfde_id"));
				m.setWfde_name(rs.getString("wfde_name"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 流程部署
	public int AddWfDeployment(int wfbu_id, int wfde_id, String wfde_remark,
			String addname) {
		try {
			CallableStatement c = conn
					.getcall("WfDeploymentAdd_P_lwj(?,?,?,?,?)");
			c.setInt(1, wfbu_id);
			c.setInt(2, wfde_id);
			c.setString(3, wfde_remark);
			c.setString(4, addname);
			c.registerOutParameter(5, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(5);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

}
