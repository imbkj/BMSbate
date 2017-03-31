package dal.EmPay;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import Conn.dbconn;
import Model.EmPayBackLogModel;
import Model.EmPayModel;
import Util.UserInfo;

public class EmPa_OperateDal {
	// 支付新增
	public int EmPayAdd(EmPayModel m) {
		try {
			dbconn conn = new dbconn();
			CallableStatement c = conn
					.getcall("empay_add_p_cyj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getGid());
			c.setInt(2, m.getCid());
			c.setInt(3, m.getOwnmonth());
			c.setBigDecimal(4, m.getEmpa_fee());
			c.setString(5, m.getEmpa_class());
			c.setString(6, m.getEmpa_account());
			c.setString(7, m.getEmpa_bank());
			c.setString(8, m.getEmpa_addname());
			c.setString(9, m.getEmpa_remark());
			c.setString(10, m.getEmpa_paytype());
			c.setString(11, m.getEmpa_payclass());
			c.setString(12, m.getEmpa_name());
			c.setString(13, m.getEmpa_ba_name());
			c.setBigDecimal(14, m.getEmpa_tax());
			c.setBigDecimal(15, m.getEmpa_aftertax());
			c.setString(16, m.getEmpa_number());
			c.setInt(17, m.getEmpa_state());
			c.setInt(18, m.getEmpa_ifclientManager());
			c.setString(19, m.getEmpa_paymenttype());
			c.setInt(20, m.getOwnmonthend());
			c.registerOutParameter(21, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(21);
		} catch (SQLException e) {
			return 0;
		}
	}

	// 客户经理审核
	public int ClientMApproval(EmPayModel model) {
		int k = 0;
		dbconn conn = new dbconn();
		String sql = "update empay set empa_clientmanager_checkname=?,empa_clientmanager_checkdate=getdate(),"
				+ "empa_state=2 where empa_number=? and empa_state=1";
		try {
			k = conn.updatePrepareSQL(sql, UserInfo.getUsername(),
					model.getEmpa_number());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k;
	}

	// 部门经理审核
	public int DeptMApproval(EmPayModel model) {
		int k = 0;
		dbconn conn = new dbconn();
		String sql = "update empay set empa_depmanager_checkname=?,empa_depmanager_checkdate=getdate(),"
				+ "empa_state=? where empa_number=? and empa_state=2";
		try {
			k = conn.updatePrepareSQL(sql, UserInfo.getUsername(),
					model.getCheckState(), model.getEmpa_number());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k;
	}

	// 总经理助理审核
	public int assMApproval(EmPayModel model) {
		int k = 0;
		dbconn conn = new dbconn();
		String sql = "update empay set empa_assistant_chengname=?,empa_assistant_chengdate=getdate(),"
				+ "empa_state=? where empa_number=? and empa_state=3";
		try {
			k = conn.updatePrepareSQL(sql, UserInfo.getUsername(),
					model.getCheckState(),model.getEmpa_number());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k;
	}

	// 票据审核
	public int InvoiceApproval(EmPayModel model) {
		int k = 0;
		dbconn conn = new dbconn();
		String sql = "update empay set empa_invoiceauditname=?,empa_invoiceaudittime=getdate(),"
				+ "empa_state=5 where empa_number=? and empa_state in(41,42)";
		try {
			k = conn.updatePrepareSQL(sql, UserInfo.getUsername(),
					model.getEmpa_number());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k;
	}

	// 财务审核
	public int FinanceApproval(int state_id, EmPayModel model) {
		int k = 0;
		dbconn conn = new dbconn();
		String sql = "update empay set empa_finance_checkname=?,"
				+ "empa_finance_checkdate=getdate(),empa_state=?"
				+ " where empa_number=? and empa_state in (5,41,42)";
		try {
			k = conn.updatePrepareSQL(sql, UserInfo.getUsername(),state_id,
					model.getEmpa_number());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k;
	}

	// 总经理审核
	public int ManagerApproval(EmPayModel model) {
		int k = 0;
		dbconn conn = new dbconn();
		String sql = "update empay set empa_manager_checkname=?,empa_manager_checkdate=getdate(),"
				+ "empa_state=8 where empa_number=? and empa_state=7";
		try {
			k = conn.updatePrepareSQL(sql, UserInfo.getUsername(),
					model.getEmpa_number());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k;
	}

	// 出纳操作
	public int CashierApproval(EmPayModel model) {
		int k = 0;
		dbconn conn = new dbconn();
		String sql = "update empay set empa_cashier_checkname=?,"
				+ "empa_cashier_checkdate=getdate(),empa_state=6"
				+ " where empa_number=? and empa_state=8";
		try {
			k = conn.updatePrepareSQL(sql, UserInfo.getUsername(),
					model.getEmpa_number());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k;
	}

	// 终止任务
	public Integer stop(String number) {
		Integer k = 0;
		if (number != null && !number.equals("")) {

			dbconn db = new dbconn();
			String sql = "update empay set empa_state=0"
					+ " where empa_number=?";
			try {
				k = db.updatePrepareSQL(sql, number);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return k;
	}

	// 批量提交退回数据
	// 看下要不要修改?
	public Integer mod(EmPayModel model) {
		Integer k = 0;
		if (model.getId() != null && !model.getId().equals("")) {
			dbconn db = new dbconn();
			String sql = "update empay set empa_State=1,ownmonth=?,ownmonthend=?"
					+ ",empa_bank=?,empa_account=?,empa_ba_name=?,empa_fee=?"
					+ ",empa_class=?,empa_payclass=?,empa_paytype=?,empa_paymenttype=?"
					+ " where id=?";
			try {
				k = db.updatePrepareSQL(sql, model.getOwnmonth(),
						model.getOwnmonthend(), model.getEmpa_bank(),
						model.getEmpa_account(), model.getEmpa_ba_name(),
						model.getEmpa_fee(), model.getEmpa_class(),
						model.getEmpa_payclass(), model.getEmpa_paytype(),
						model.getEmpa_paymenttype(), model.getId());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return k;
	}

	// 插入备注
	public int AddRemark(int empa_id, String content) {
		String sql = "insert into EmPayRemark(empa_id, content, addname) values(?,?,?)";
		int k = 0;
		dbconn conn = new dbconn();
		try {
			k = conn.updatePrepareSQL(sql, empa_id, content,
					UserInfo.getUsername());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return k;
	}

	// 插入操作记录
	public int AddEmpayLog(String log_empa_number, String opeatecontent) {
		String sql = "insert into EmPaylog(log_empa_number, log_opeatecontent, log_addname) values(?,?,?)";
		int k = 0;
		dbconn conn = new dbconn();
		try {
			k = conn.updatePrepareSQL(sql, log_empa_number, opeatecontent,
					UserInfo.getUsername());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return k;
	}

	// 插入处理步骤
	public int AddEmpayTask(int task_step, String task_name, String task_number) {
		String sql = "insert into EmPayTask(task_step,task_name,task_number) values(?,?,?)";
		int k = 0;
		dbconn conn = new dbconn();
		try {
			k = conn.updatePrepareSQL(sql, task_step, task_name, task_number);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return k;
	}

	// 插入退回信息记录
	public int AddEmpayBackLog(EmPayBackLogModel m) {
		try {
			dbconn conn = new dbconn();
			CallableStatement c = conn.getcall("Empay_Back_P_cyj(?,?,?,?,?)");
			c.setString(1, m.getBack_empa_number());
			c.setString(2, m.getBack_case());
			c.setString(3, m.getBack_addname());
			c.setInt(4, m.getEmpa_state());
			c.registerOutParameter(5, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(5);
		} catch (SQLException e) {
			return 0;
		}
	}

	//
	public int AddTaskList(String patk_number) {
		try {
			dbconn conn = new dbconn();
			CallableStatement c = conn.getcall("empayList_add_p_cyj(?,?,?)");
			c.setString(1, patk_number);
			c.setString(2, UserInfo.getUsername());
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);
		} catch (SQLException e) {
			return 0;
		}
	}

	// 更新社保卡信息表任务单id
	public boolean updateTaprid(int taprid, int id) {
		dbconn db = new dbconn();
		PreparedStatement psmt = null;
		int row = 0;
		String sql = "update EmPayTaskList set patk_tapr_id=? where patk_id=?";
		try {
			psmt = db.getpre(sql);
			psmt.setInt(1, taprid);
			psmt.setInt(2, id);
			row = psmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return row > 0 ? true : false;
	}

	// 删除员工个人支付信息
	public Integer del(Integer id) {
		dbconn db = new dbconn();
		String sql = "delete from empay where id=?";
		Integer i = 0;
		try {
			i = db.updatePrepareSQL(sql, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
}
