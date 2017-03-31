package dal.CoBase;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.DepartmentModel;
import Model.LoginModel;
import Model.pubTreeModel;

public class PubTree_OperateDal {
	// 获取pubTree表的第一级数据
	public List<pubTreeModel> getPubTreeParentInfo() {
		ResultSet rs = null;
		List<pubTreeModel> pubtreelist = new ArrayList<pubTreeModel>();
		if (!pubtreelist.isEmpty())
			pubtreelist.clear();
		try {
			dbconn db = new dbconn();
			String sqlstr = "SELECT  * from PubTree where pid=0";
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				pubtreelist.add(new pubTreeModel(rs.getInt("id"), rs
						.getInt("pid"), rs.getString("name"), rs
						.getString("url"), rs.getString("financeurl"), rs
						.getInt("level"), rs.getString("target")));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pubtreelist;
	}

	// 根据pid获取pubTree表每一级数据
	public List<pubTreeModel> getPubTreeChildrenInfo(int pid) {
		ResultSet rs = null;
		List<pubTreeModel> pubtreelist = new ArrayList<pubTreeModel>();
		if (!pubtreelist.isEmpty())
			pubtreelist.clear();
		try {
			dbconn db = new dbconn();
			String sqlstr = "SELECT  * from PubTree where pid=" + pid;
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				pubtreelist.add(new pubTreeModel(rs.getInt("id"), rs
						.getInt("pid"), rs.getString("name"), rs
						.getString("url"), rs.getString("financeurl"), rs
						.getInt("level"), rs.getString("target")));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pubtreelist;
	}

	// 获取pubTree表所有数据
	public List<pubTreeModel> getPubTreeAllInfo() {
		ResultSet rs = null;
		List<pubTreeModel> pubtreelist = new ArrayList<pubTreeModel>();
		if (!pubtreelist.isEmpty())
			pubtreelist.clear();
		try {
			dbconn db = new dbconn();
			String sqlstr = "SELECT  * from PubTree";
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				pubtreelist.add(new pubTreeModel(rs.getInt("id"), rs
						.getInt("pid"), rs.getString("name"), rs
						.getString("url"), rs.getString("financeurl"), rs
						.getInt("level"), rs.getString("target")));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pubtreelist;
	}

	public List<DepartmentModel> getDepartmentInfo() {
		ResultSet rs = null;
		List<DepartmentModel> list = new ArrayList<DepartmentModel>();
		if (!list.isEmpty())
			list.clear();
		try {
			dbconn db = new dbconn();
			String sqlstr = " SELECT * FROM department order by dep_order";
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				DepartmentModel m = new DepartmentModel();
				m.setDep_id(rs.getInt("dep_id"));
				m.setDep_name(rs.getString("dep_name"));
				m.setDep_pid(rs.getInt("dep_pid"));
				list.add(m);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public List<LoginModel> getLoginInfo() {
		ResultSet rs = null;
		List<LoginModel> list = new ArrayList<LoginModel>();
		if (!list.isEmpty())
			list.clear();
		try {
			dbconn db = new dbconn();
			String sqlstr = " SELECT log_pid,* FROM Login WHERE log_inure=1";
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				LoginModel m = new LoginModel();
				m.setDep_id(rs.getInt("dep_id"));
				m.setLog_pid(rs.getInt("log_pid"));
				m.setLog_id(rs.getInt("log_id"));
				m.setLog_name(rs.getString("log_name"));
				list.add(m);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
