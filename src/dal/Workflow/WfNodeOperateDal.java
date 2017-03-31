package dal.Workflow;

import java.sql.CallableStatement;
import java.sql.SQLException;

import Conn.dbconn;
import Model.WfNodeModel;

;

public class WfNodeOperateDal {
	private dbconn conn = new dbconn();

	// 任务流程管理新增节点
	public int AddNode(WfNodeModel m) {
		try {
			CallableStatement c = conn
					.getcall("WfNodeAdd_P_lwj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getWfno_wfde_id());
			c.setString(2, m.getWfno_name());
			c.setInt(3, m.getWfno_step());
			c.setInt(4, m.getWfno_maxday());
			c.setString(5, m.getWfno_selectuser());
			c.setInt(6, m.getWfno_runtype());
			c.setString(7, m.getWfno_runprocedure());
			c.setString(8, m.getWfno_url());
			c.setInt(9, m.getWfno_ifview());
			c.setInt(10, m.getWfno_ifskip());
			c.setInt(11, m.getWfno_ifreturn());
			c.setInt(12, m.getWfno_ifstop());
			c.setInt(13, m.getWfno_ifrevoke());
			c.setInt(14, m.getWfno_ifhavechild());
			c.setString(15, m.getWfno_remark());
			c.setString(16, m.getWfno_addname());
			c.setInt(17, m.getWfno_state());
			c.registerOutParameter(18, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(18);
		} catch (SQLException e) {
			return 1;
		}

	}

	// 任务流程管理修改节点
	public int UpdateNode(WfNodeModel m) {
		try {
			CallableStatement c = conn
					.getcall("WfNodeUpdate_P_lwj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getWfno_id());
			c.setString(2, m.getWfno_name());
			c.setInt(3, m.getWfno_step());
			c.setInt(4, m.getWfno_maxday());
			c.setString(5, m.getWfno_selectuser());
			c.setInt(6, m.getWfno_runtype());
			c.setString(7, m.getWfno_runprocedure());
			c.setString(8, m.getWfno_url());
			c.setInt(9, m.getWfno_ifview());
			c.setInt(10, m.getWfno_ifskip());
			c.setInt(11, m.getWfno_ifreturn());
			c.setInt(12, m.getWfno_ifstop());
			c.setInt(13, m.getWfno_ifrevoke());
			c.setInt(14, m.getWfno_ifhavechild());
			c.setString(15, m.getWfno_remark());
			c.setString(16, m.getWfno_addname());
			c.setInt(17, m.getWfno_state());
			c.registerOutParameter(18, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(18);
		} catch (SQLException e) {
			return 1;
		}

	}

	// 新增业务
	public int AddBusiness(String name, String remark, String addname) {
		try {
			CallableStatement c = conn.getcall("WfBusinessAdd_P_lwj(?,?,?,?)");
			c.setString(1, name);
			c.setString(2, remark);
			c.setString(3, addname);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			return 0;
		}
	}
}
