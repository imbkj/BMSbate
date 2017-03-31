package dal.CoFinanceManage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.CoFinanceCollectModel;
import Model.CoFinanceMonthlyBillModel;
import Model.CoFinanceMonthlyBillSortAccountModel;

public class Cfma_BillSelDal extends dbconn {
	private dbconn conn = new dbconn();

	// 根据cid查询账单号
	public List<CoFinanceCollectModel> getNumberByCid(int cid) {
		List<CoFinanceCollectModel> l = new ArrayList<CoFinanceCollectModel>();
		String sql = " select * from CoFinanceMonthlyBill where cfmb_number in (select cfco_cfmb_number from CoFinanceCollect where cid = ? and cfco_cfmb_number != '0') and (cfmb_FinanceConfirm != 0 or cfmb_WriteOffs != 0 or cfmb_PersonnelConfirm != 0)  and (cfmb_FinanceConfirm = 1 or cfmb_PersonnelConfirm = 1) and (cfmb_WriteOffs != 1 and cfmb_WriteOffs != 2) ";
		PreparedStatement pstmt = getpre(sql);
		try {
			pstmt.setInt(1, cid);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CoFinanceCollectModel m = new CoFinanceCollectModel();
				m.setCfco_cfmb_number(rs.getString("cfmb_number"));
				l.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return l;
	}

	// 根据账单号获取账单信息
	public CoFinanceMonthlyBillModel getBillModel(String billNo) {
		CoFinanceMonthlyBillModel m = new CoFinanceMonthlyBillModel();
		StringBuilder sql = new StringBuilder();
		sql.append("select cfmb.*,co.*,cfta_Balance=isnull(cfta.cfta_Balance,0),addtime=CONVERT(varchar(100), cfmb_addtime, 120) from CoFinanceMonthlyBill cfmb ");
		sql.append("inner join CoFinanceTotalAccount cfta on cfmb.cfmb_cfta_id=cfta.cfta_id ");
		sql.append("inner join (select CID,coba_company,coba_client from CoBase)co on cfta.cid=co.CID ");
		sql.append("where cfmb.cfmb_number=? ");
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setString(1, billNo);
			ResultSet rs = psmt.executeQuery();
			rs.next();
			m.setCfmb_cfta_id(rs.getInt("cfmb_cfta_id"));
			m.setCfmb_number(rs.getString("cfmb_number"));
			m.setCfmb_name(rs.getString("cfmb_name"));
			m.setOwnmonth(rs.getInt("ownmonth"));
			m.setCfmb_PersonnelReceivable(rs
					.getBigDecimal("cfmb_PersonnelReceivable"));
			m.setCfmb_FinanceReceivable(rs
					.getBigDecimal("cfmb_FinanceReceivable"));
			m.setCfmb_PersonnelPaidIn(rs.getBigDecimal("cfmb_PersonnelPaidIn"));
			m.setCfmb_FinancePaidIn(rs.getBigDecimal("cfmb_FinancePaidIn"));
			m.setCfmb_TotalPaidIn(rs.getBigDecimal("cfmb_TotalPaidIn"));
			m.setCfmb_LoanBalance(rs.getBigDecimal("cfmb_LoanBalance"));
			m.setCfmb_CarryForwardEx(rs.getBigDecimal("cfmb_CarryForwardEx"));
			m.setCfmb_PersonnelConfirm(rs.getInt("cfmb_PersonnelConfirm"));
			m.setCfmb_FinanceConfirm(rs.getInt("cfmb_FinanceConfirm"));
			m.setCfmb_Confirm(rs.getInt("cfmb_Confirm"));
			m.setCfmb_WriteOffs(rs.getInt("cfmb_WriteOffs"));
			m.setCid(rs.getInt("cid"));
			m.setCompany(rs.getString("coba_company"));
			m.setClient(rs.getString("coba_client"));
			m.setCfmb_addtime(rs.getString("addtime"));
			m.setCfmb_addname(rs.getString("cfmb_addname"));
			m.setCfmb_remark(rs.getString("cfmb_remark"));
			m.setCfta_Balance(rs.getBigDecimal("cfta_Balance"));
			m.sumTotalReceivable();
			m.sumImbalance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	// 根据账单号获取账单财务科目
	public List<CoFinanceMonthlyBillSortAccountModel> getMonthlyBillSortAccount(
			String billNo) {
		List<CoFinanceMonthlyBillSortAccountModel> list = new ArrayList<CoFinanceMonthlyBillSortAccountModel>();
		CoFinanceMonthlyBillSortAccountModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select cfsa_id,cfsa_cpac_name,cfsa_Receivable,cfsa_PaidIn,imbalance=ISNULL(cfsa_PaidIn,0)-ISNULL(cfsa_Receivable,0) from CoFinanceMonthlyBillSortAccount where cfsa_cfmb_number=?");
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setString(1, billNo);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new CoFinanceMonthlyBillSortAccountModel();
				m.setCfsa_id(rs.getInt("cfsa_id"));
				m.setCfsa_cpac_name(rs.getString("cfsa_cpac_name"));
				m.setCfsa_Receivable(rs.getBigDecimal("cfsa_Receivable"));
				m.setCfsa_PaidIn(rs.getBigDecimal("cfsa_PaidIn"));
				m.setImbalance(rs.getBigDecimal("imbalance"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询账单是否已生成付款通知
	public boolean existsBillReport(String billNo) {
		String sql = "select cfmb_ReportState from CoFinanceMonthlyBill where cfmb_number=?";
		PreparedStatement psmt = conn.getpre(sql.toString());
		boolean bool = false;
		try {
			psmt.setString(1, billNo);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				if (rs.getInt("cfmb_ReportState") == 1)
					bool = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}

	// 查询账单明细费用
	public List<CoFinanceMonthlyBillSortAccountModel> getlist(String num) {
		List<CoFinanceMonthlyBillSortAccountModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select cfsa_id, cfsa_cfmb_number, cfsa_cpac_name, cfsa_Receivable, cfsa_PaidIn, cfsa_addtime, cfsa_state"
				+ " from CoFinanceMonthlyBillSortAccount"
				+ " where cfsa_cfmb_number=?";
		try {
			list = db.find(sql, CoFinanceMonthlyBillSortAccountModel.class,
					null, num);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询账单列表(开票费用)
	public List<CoFinanceCollectModel> getInvoiceList(Integer cid) {
		List<CoFinanceCollectModel> list = new ListModelList<>();
		dbconn db = new dbconn();

		String sql = "select   a.cfco_id, a.cid, b.ownmonth, a.cfco_cfmb_number,"
				+ " a.cfco_TotalPaidIn, a.cfco_remark, a.cfco_addname, a.cfco_addtime, a.cfco_state"
				+ " from CoFinanceCollect a"
				+ " inner join CoFinanceMonthlyBill b on a.cfco_cfmb_number=b.cfmb_number"
				+ " where cid=? and cfco_state=1 order by cfco_id desc";

		System.out.println(sql);
		try {
			list = db.find(sql, CoFinanceCollectModel.class, null, cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询账单费用(开票计算费用)
	public List<CoFinanceMonthlyBillSortAccountModel> getInvoiceInfo(
			Integer cid, List<CoFinanceMonthlyBillSortAccountModel> aList) {
		List<CoFinanceMonthlyBillSortAccountModel> list = new ListModelList<>();
		dbconn db = new dbconn();

		String sql = "select cfsa_id,cfsa_cpac_name,cfsa_receivable,cfsa_PaidIn "
				+ "from CoFinanceMonthlyBillSortAccount cfsa "
				+ "inner join CoFinanceMonthlyBill cfmb on cfsa.cfsa_cfmb_number=cfmb.cfmb_number "
				+ "inner join CoFinanceTotalAccount cfta on cfmb.cfmb_cfta_id=cfta.cfta_id "
				+ "where cfta.cid=? and cfsa_state=1";
		String sql2 = "";
		boolean b = false;
		if (aList != null) {
			if (aList.size() > 0) {
				sql2 = sql2 + " and cfsa_cfmb_number in (";

				for (int i = 0; i < aList.size(); i++) {
					if (aList.get(i).isChecked()) {
						b = true;
						sql2 = sql2 + (i > 0 ? ",'" : "'") + aList.get(i) + "'";
					}
				}
				sql2 = sql2 + ")";
				if (b) {
					sql = sql + sql2;
				}
			}
		}

		System.out.println(sql);
		try {
			list = db.find(sql, CoFinanceMonthlyBillSortAccountModel.class,
					null, cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

}
