package dal.CoFinanceManage;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Conn.dbconn;

public class cfma_OperateDal {
	private static dbconn conn = new dbconn();

	// 同步台账
	public void synchronousFinance(int cid, int ownmonth) {
		
		try {
			CallableStatement c = conn
					.getcall("EmFinanceReceivableByCid_p_lwj(?,?)");
			c.setInt(1, cid);
			c.setInt(2, ownmonth);
			c.execute();
			if(c!=null)
			{
				c.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	// 生成账单
	public String createBill(String prefix, int tid, String tname,
			String cfmb_name, int cfta_id, int cid, int ownmonth,
			String username, String remark) {
		try {
			CallableStatement c = conn
					.getcall("CoFinanceMonthlyBillCreate_p_lwj(?,?,?,?,?,?,?,?,?,?)");
			c.setString(1, prefix);
			c.setInt(2, tid);
			c.setString(3, tname);
			c.setString(4, cfmb_name);
			c.setInt(5, cfta_id);
			c.setInt(6, cid);
			c.setInt(7, ownmonth);
			c.setString(8, username);
			c.setString(9, remark);
			c.registerOutParameter(10, java.sql.Types.VARCHAR);
			c.execute();
			return c.getString(10);
		} catch (SQLException e) {
			e.printStackTrace();
			return "0";
		}
	}

	// 删除账单
	public int delBill(String cfmb_number) {
		try {
			CallableStatement c = conn
					.getcall("CoFinanceMonthlyBillDel_p_lwj(?,?)");
			c.setString(1, cfmb_number);
			c.registerOutParameter(2, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(2);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	// 账单应收确认
	public int confirmBill(String cfmb_number, int type, String username) {
		try {
			CallableStatement c = conn
					.getcall("CoFinanceMonthlyBillConfirm_p_lwj(?,?,?,?)");
			c.setString(1, cfmb_number);
			c.setInt(2, type);
			c.setString(3, username);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
	}

	// 新建账单(返回账单号)
	public String AddBill(String prefix, int cfta_id, int cid, int ownmonth,
			String username) {
		try {
			CallableStatement c = conn
					.getcall("CoFinanceMonthlyBillAdd_p_lwj(?,?,?,?,?,?)");
			c.setString(1, prefix);
			c.setInt(2, cfta_id);
			c.setInt(3, cid);
			c.setInt(4, ownmonth);
			c.setString(5, username);
			c.registerOutParameter(6, java.sql.Types.VARCHAR);
			c.execute();
			return c.getString(6);
		} catch (SQLException e) {
			e.printStackTrace();
			return "0";
		}
	}

	// 更改台账数据的账单号
	public void UpBillNumber(String cfmb_number, String cfmb_numberold) {
		try {
			CallableStatement c = conn
					.getcall("CoFinanceMonthlyBillUpNumber_p_lwj(?,?)");
			c.setString(1, cfmb_number);
			c.setString(2, cfmb_numberold);
			c.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 账单分类账表数据更新
	public void UpBillSortAccount(String cfmb_number) {
		try {
			CallableStatement c = conn
					.getcall("CoFinanceMonthlyBillSortAccountAdd_p_lwj(?)");
			c.setString(1, cfmb_number);
			c.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 检测账单确认情况
	public int[] checkBillConfirm(String cfmb_number) {
		int[] i = new int[4];
		String sql = " select cfmb_PersonnelConfirm,cfmb_FinanceConfirm,cfmb_Confirm, case cfmb_TotalPaidIn WHEN 0 THEN 0  when null then 0 ELSE 1 END  cfmb_TotalPaidIn from CoFinanceMonthlyBill  where cfmb_number=?";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setString(1, cfmb_number);
			ResultSet rs = psmt.executeQuery();
			rs.next();
			i[0] = rs.getInt("cfmb_PersonnelConfirm");
			i[1] = rs.getInt("cfmb_FinanceConfirm");
			i[2] = rs.getInt("cfmb_Confirm");
			i[3] = rs.getInt("cfmb_TotalPaidIn");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	// 检测是否有未加账单的业务
	public int existsNoBill(int cid, int ownmonth) {
		try {
			CallableStatement c = conn
					.getcall("CoFinanceExistsNoBill_p_lwj(?,?,?)");
			c.setInt(1, cid);
			c.setInt(2, ownmonth);
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 检测是否有未加账单的业务(根据合同查询)
	public int existsNoBillByCp(int cid, int ownmonth, int coco_id) {
		try {
			CallableStatement c = conn
					.getcall("CoFinanceExistsNoBillByCp_p_lwj(?,?,?,?)");
			c.setInt(1, cid);
			c.setInt(2, ownmonth);
			c.setInt(3, coco_id);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 获取用户部门名称
	public String getUserDept(String username) {
		String sql = "select dep_name from Login l inner join department d on l.dep_id=d.dep_id where log_name=?";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setString(1, username);
			ResultSet rs = psmt.executeQuery();
			if (rs.next())
				return rs.getString("dep_name").trim();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}

	// 根据账单号对账单备注
	public Integer billRemarkAdd(String billno, String remark) {
		Integer k = 0;
		String sql = "update CoFinanceMonthlyBill set cfmb_remark='" + remark
				+ "' where cfmb_number='" + billno + "'";
		dbconn db = new dbconn();
		k = db.execQuery(sql);
		return k;
	}
}
