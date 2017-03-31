package dal.CoFinanceManage;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.CoFinanceCollectImportErrModel;
import Model.CoFinanceCollectImportModel;

public class Cfma_CollectImportDal {
	private dbconn conn = new dbconn();

	// 查询导入的收款纪录
	public List<CoFinanceCollectImportModel> getCollectImportList() {
		List<CoFinanceCollectImportModel> list = new ArrayList<CoFinanceCollectImportModel>();
		CoFinanceCollectImportModel m = null;
		String sql = "select cfci_id,cfci_type,cid,cfci_coab_id,cfci_transactionNo,cfci_company,cfci_account,cfci_amount,cfci_usage,cfci_remark, cfci_transactionTime=CONVERT(varchar(100),cfci_transactionTime, 23),cfci_addname,cfci_addtime= CONVERT(varchar(100), cfci_addtime, 120),cfci_state from CoFinanceCollectImport order by cfci_id desc";
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new CoFinanceCollectImportModel();
				m.setCfci_id(rs.getInt("cfci_id"));
				m.setCid(rs.getInt("cid"));
				m.setCfci_coab_id(rs.getInt("cfci_coab_id"));
				m.setCfci_type(rs.getInt("cfci_type"));
				m.setCfci_transactionNo(rs.getString("cfci_transactionNo"));
				m.setCfci_company(rs.getString("cfci_company"));
				m.setCfci_account(rs.getString("cfci_account"));
				m.setCfci_amount(rs.getBigDecimal("cfci_amount"));
				m.setCfci_usage(rs.getString("cfci_usage"));
				m.setCfci_remark(rs.getString("cfci_remark"));
				m.setCfci_transactionTime(rs.getString("cfci_transactionTime"));
				m.setCfci_addname(rs.getString("cfci_addname"));
				m.setCfci_addtime(rs.getString("cfci_addtime"));
				m.setCfci_state(rs.getInt("cfci_state"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询导入的收款纪录
	public CoFinanceCollectImportModel getCollectImportModel(int cfci_id) {
		CoFinanceCollectImportModel m = null;
		String sql = "select cfci_id,cfci_type,cid,cfci_coab_id,cfci_transactionNo,cfci_company,cfci_account,cfci_amount,cfci_usage,cfci_remark, cfci_transactionTime=CONVERT(varchar(100),cfci_transactionTime, 23),cfci_addname,cfci_addtime= CONVERT(varchar(100), cfci_addtime, 120),cfci_state from CoFinanceCollectImport where cfci_id=?";
		try {
			PreparedStatement psmt = conn.getpre(sql.toString());
			psmt.setInt(1, cfci_id);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				m = new CoFinanceCollectImportModel();
				m.setCfci_id(rs.getInt("cfci_id"));
				m.setCid(rs.getInt("cid"));
				m.setCfci_coab_id(rs.getInt("cfci_coab_id"));
				m.setCfci_type(rs.getInt("cfci_type"));
				m.setCfci_transactionNo(rs.getString("cfci_transactionNo"));
				m.setCfci_company(rs.getString("cfci_company"));
				m.setCfci_account(rs.getString("cfci_account"));
				m.setCfci_amount(rs.getBigDecimal("cfci_amount"));
				m.setCfci_usage(rs.getString("cfci_usage"));
				m.setCfci_remark(rs.getString("cfci_remark"));
				m.setCfci_transactionTime(rs.getString("cfci_transactionTime"));
				m.setCfci_addname(rs.getString("cfci_addname"));
				m.setCfci_addtime(rs.getString("cfci_addtime"));
				m.setCfci_state(rs.getInt("cfci_state"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	// 导入收款纪录至DB
	public int addImportToCompany(CoFinanceCollectImportModel m, String username) {
		try {
			CallableStatement c = conn
					.getcall("CoFinanceCollectImportToCompanyAdd_p_lwj(?,?,?,?,?,?,?,?,?)");
			c.setString(1, m.getCfci_transactionNo());
			c.setString(2, m.getCfci_company());
			c.setString(3, m.getCfci_account());
			c.setBigDecimal(4, m.getCfci_amount());
			c.setString(5, m.getCfci_usage());
			c.setString(6, m.getCfci_remark());
			c.setString(7, m.getCfci_transactionTime());
			c.setString(8, username);
			c.registerOutParameter(9, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(9);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 写入导入收款出错情况至DB
	public void addImportErr(String username, String errCon) {
		try {
			CallableStatement c = conn
					.getcall("CoFinanceCollectImportErr_p_lwj(?,?)");
			c.setString(1, username);
			c.setString(2, errCon);
			c.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 导入收款数据入账
	public int addImportRecorded(CoFinanceCollectImportModel m, String username) {
		try {
			CallableStatement c = conn
					.getcall("CoFinanceCollectImportRecorded_p_lwj(?,?,?,?,?,?)");
			c.setInt(1, m.getCfci_id());
			c.setInt(2, m.getCfci_type());
			c.setString(3, m.getCfci_company());
			c.setString(4, m.getCfci_usage());
			c.setString(5, username);
			c.registerOutParameter(6, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(6);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 查询导入收款的出错情况
	public List<CoFinanceCollectImportErrModel> getErrList() {
		List<CoFinanceCollectImportErrModel> list = new ArrayList<CoFinanceCollectImportErrModel>();
		CoFinanceCollectImportErrModel m = null;
		String sql = "select ccie_id,ccie_errCon,ccie_addname, CONVERT(varchar(100), ccie_addtime, 20)ccie_addtime from CoFinanceCollectImportErr order by ccie_addtime desc";
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new CoFinanceCollectImportErrModel();
				m.setCcie_id(rs.getInt("ccie_id"));
				m.setCcie_addname(rs.getString("ccie_addname"));
				m.setCcie_addtime(rs.getString("ccie_addtime"));
				m.setCcie_errCon(rs.getString("ccie_errCon"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
