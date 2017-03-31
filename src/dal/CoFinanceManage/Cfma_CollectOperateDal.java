package dal.CoFinanceManage;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.SQLException;

import Conn.dbconn;
import Model.CoFinanceSortAccountssModel;
import Util.UserInfo;

public class Cfma_CollectOperateDal {
	private static dbconn conn = new dbconn();

	// 添加公司收款
	public int addCollectToCompany(int cid, String cfmb_number,
			BigDecimal paidin, String username, String remark,int ownmonth,Boolean kpfrist) {
		try {
			CallableStatement c = conn
					.getcall("CoFinanceCollectToCompanyAdd_p_gxj(?,?,?,?,?,?,?,?)");
			c.setInt(1, cid);
			c.setString(2, cfmb_number);
			c.setBigDecimal(3, paidin);
			c.setString(4, username);
			c.setString(5, remark);
			c.setInt(6, ownmonth);
			c.setBoolean(7, kpfrist);
			c.registerOutParameter(8, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(8);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		

	}
	
	// 修改公司收款
		public int updateCollectToCompany(int cfco_id,int cid, String cfmb_number,
				BigDecimal paidin, String username, String remark,int ownmonth,Boolean f) {
			try {
				CallableStatement c = conn
						.getcall("CoFinanceCollectToCompanyupdate_p_zmj(?,?,?,?,?,?,?,?)");
				c.setInt(1, cfco_id);
				c.setInt(2, cid);
				//c.setString(3, cfmb_number);
				c.setBigDecimal(3, paidin);
				c.setString(4, username);
				c.setString(5, remark);
				c.setInt(6, ownmonth);
				c.setBoolean(7, f);
				c.registerOutParameter(8, java.sql.Types.INTEGER);
				c.execute();
				return c.getInt(8);
			} catch (SQLException e) {
				e.printStackTrace();
				return 0;
			}
	
		}
	
	public int addCollectToCompanys(int cid, String cfmb_number,
			BigDecimal paidin, String username, String remark,int ownmonth) {
		try {
			CallableStatement c = conn
					.getcall("CoFinanceCollectToCompanyAdd_p_gxj(?,?,?,?,?,?,?)");
			c.setInt(1, cid);
			c.setString(2, cfmb_number);
			c.setBigDecimal(3, paidin);
			c.setString(4, username);
			c.setString(5, remark);
			c.setInt(6, ownmonth);
			c.registerOutParameter(7, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(7);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	//添加实收款
	public int addcogathering(CoFinanceSortAccountssModel m,int cfco_id) {
		try {
			CallableStatement c = conn
					.getcall("CoFinanceSortAccountssadd_p_zmj(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getCid());
			
			c.setInt(2, m.getOwnmonth());
			c.setString(3, m.getCfss_cpac_name());
			c.setBigDecimal(4, m.getCfss_Receivable());
			c.setInt(5, m.getCfss_state());
			c.setInt(6, m.getCfss_fpstate());
			c.setInt(7, m.getCfss_yystate());
			c.setString(8, m.getCfss_addname());
			c.setString(9, m.getRemark());
			c.setInt(10, cfco_id);
			c.setString(11, m.getCfss_type());
			c.setBoolean(12, m.getCfss_fpfrist());
			c.setBoolean(13, m.getCfss_allin());
			c.registerOutParameter(14, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(14);
			
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	//修改实收款
		public int modcogathering(CoFinanceSortAccountssModel m) {
			try {
				CallableStatement c = conn
						.getcall("CoFinanceSortAccountssupdate_p_zmj(?,?,?,?,?,?,?,?,?,?,?,?,?)");
				c.setString(1, m.getCfss_cfso_id());
				c.setString(2, m.getCfss_cpac_name());
				 
				c.setBigDecimal(3, m.getCfss_Receivable());
				c.setInt(4, m.getCfss_state());
				c.setInt(5, m.getCfss_fpstate());
				c.setInt(6, m.getCfss_yystate());
				c.setString(7, m.getRemark());
				c.setString(8, UserInfo.getUsername());
				c.setString(9, m.getCfss_type());
				c.setInt(10, m.getOwnmonth());
				c.setBoolean(11, m.getCfss_fpfrist());
				c.setBoolean(12, m.getCfss_allin());
				c.registerOutParameter(13, java.sql.Types.INTEGER);
				c.execute();
				return c.getInt(13);
			} catch (SQLException e) {
				e.printStackTrace();
				return 0;
			}
		}

	// 添加领款
	public int addDrawMoneyToBill(String cfmb_number, int cid,
			BigDecimal drawEx, String username, String remark) {
		try {
			CallableStatement c = conn
					.getcall("CoFinanceDrawMoneyToBill_p_lwj(?,?,?,?,?,?)");
			c.setString(1, cfmb_number);
			c.setInt(2, cid);
			c.setBigDecimal(3, drawEx);
			c.setString(4, username);
			c.setString(5, remark);
			c.registerOutParameter(6, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(6);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 分配款项至账单财务科目
	public int disMoneyToBillSort(int cfdm_id, int cfsa_id, BigDecimal collect) {
		try {
			CallableStatement c = conn
					.getcall("CoFinanceDisMoneyToBillSort_p_lwj(?,?,?,?)");
			c.setInt(1, cfdm_id);
			c.setInt(2, cfsa_id);
			c.setBigDecimal(3, collect);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 更新账单实收
	public void upBillPaidin(String cfmb_number) {
		try {
			CallableStatement c = conn
					.getcall("CoFinanceBillPaidinUp_p_lwj(?)");
			c.setString(1, cfmb_number);
			c.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 销账
	public int BillWriteOffs(String cfmb_number, String username) {
		try {
			CallableStatement c = conn
					.getcall("CoFinanceBillWriteOffs_p_lwj(?,?,?)");
			c.setString(1, cfmb_number);
			c.setString(2, username);
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 结转
	public int BillCarryForward(String cfmb_number, int cid, int ownmonth,
			String remark, String username) {
		try {
			CallableStatement c = conn
					.getcall("CoFinanceBillCarryForward_p_lwj(?,?,?,?,?,?)");
			c.setString(1, cfmb_number);
			c.setInt(2, cid);
			c.setInt(3, ownmonth);
			c.setString(4, remark);
			c.setString(5, username);
			c.registerOutParameter(6, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(6);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
