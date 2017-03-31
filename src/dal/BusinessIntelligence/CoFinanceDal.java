package dal.BusinessIntelligence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bll.BusinessIntelligence.CoFinanceBll;

import Conn.dbconn;
import Model.CoFinanceMonthlyBillModel;
import Model.CoFinanceMonthlyBillSortAccountModel;

public class CoFinanceDal {
	private dbconn conn = new dbconn();

	// 获取台账财务科目统计
	public List<CoFinanceMonthlyBillSortAccountModel> getMonthlySortAccount(
			int ownmonth) {
		List<CoFinanceMonthlyBillSortAccountModel> list = new ArrayList<CoFinanceMonthlyBillSortAccountModel>();
		CoFinanceMonthlyBillSortAccountModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select cfsa.cfsa_cpac_name,cfsa_Receivable=SUM(cfsa_Receivable),PaidIn=isnull(sum(s.PaidIn),0) ");
		sql.append("from CoFinanceSortAccount cfsa ");
		sql.append("left join (select cfta.cid,cfmb.ownmonth,cfsa.cfsa_cpac_name,PaidIn=ISNULL(sum(cfsa.cfsa_PaidIn),0) from CoFinanceMonthlyBillSortAccount cfsa ");
		sql.append("inner join CoFinanceMonthlyBill cfmb on cfsa.cfsa_cfmb_number=cfmb.cfmb_number ");
		sql.append("inner join CoFinanceTotalAccount cfta on cfmb_cfta_id=cfta.cfta_id ");
		sql.append("group by cfta.cid,cfmb.ownmonth,cfsa.cfsa_cpac_name) s on cfsa.cid=s.cid and cfsa.ownmonth=s.ownmonth and cfsa.cfsa_cpac_name=s.cfsa_cpac_name ");
		sql.append("where cfsa.ownmonth=? ");
		sql.append("group by cfsa.cfsa_cpac_name");
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setInt(1, ownmonth);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new CoFinanceMonthlyBillSortAccountModel();
				m.setCfsa_cpac_name(rs.getString("cfsa_cpac_name"));
				m.setCfsa_Receivable(rs.getBigDecimal("cfsa_Receivable"));
				m.setCfsa_PaidIn(rs.getBigDecimal("PaidIn"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取每月的应收实收数据
	public List<CoFinanceMonthlyBillModel> getMonthBillTotle() {
		List<CoFinanceMonthlyBillModel> list = new ArrayList<CoFinanceMonthlyBillModel>();
		CoFinanceMonthlyBillModel m = null;
		String sql = "select ownmonth,Receivable=SUM(cfmb_PersonnelReceivable)+SUM(cfmb_FinanceReceivable),cfmb_TotalPaidIn=SUM(cfmb_TotalPaidIn) from CoFinanceMonthlyBill group by ownmonth";
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new CoFinanceMonthlyBillModel();
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setCfmb_FinanceReceivable(rs
						.getBigDecimal("Receivable"));
				m.setCfmb_FinancePaidIn(rs.getBigDecimal("cfmb_TotalPaidIn"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
