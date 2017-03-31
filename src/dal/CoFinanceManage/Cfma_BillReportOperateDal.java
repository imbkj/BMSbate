package dal.CoFinanceManage;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Conn.dbconn;
import Model.CoFinanceCompanyClientModel;

public class Cfma_BillReportOperateDal {
	private dbconn conn = new dbconn();

	// 获取账单客服信息
	public CoFinanceCompanyClientModel getCompanyClientByBillNo(String billNo) {
		CoFinanceCompanyClientModel m = new CoFinanceCompanyClientModel();
		StringBuilder sql = new StringBuilder();
		sql.append("select coba.CID,cfmb.ownmonth,coba.coba_company,coba.coba_shortname,coba.coba_client,coba.coba_clientmanager,l.log_tel,l.log_email,lm.log_tel as lm_tel,lm.log_email as lm_email,receivable=isnull(cfmb.cfmb_PersonnelReceivable,0)+ISNULL(cfmb.cfmb_FinanceReceivable,0)  from CoFinanceMonthlyBill cfmb ");
		sql.append("left join CoFinanceTotalAccount cfta on cfmb.cfmb_cfta_id=cfta.cfta_id ");
		sql.append("left join (select CID,coba_company,coba_shortname,coba_client,coba_clientmanager from CoBase) coba on cfta.cid = coba.CID ");
		sql.append("left join (select log_name,log_tel,log_email from Login) l on coba.coba_client=l.log_name ");
		sql.append("left join (select log_name,log_tel,log_email from Login) lm on coba.coba_clientmanager=lm.log_name ");
		sql.append("where cfmb.cfmb_number=?");
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setString(1, billNo);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				m.setCid(rs.getInt("cid"));
				m.setCoba_company(rs.getString("coba_company"));
				m.setCoba_shortname(rs.getString("coba_shortname"));
				m.setCoba_client(rs.getString("coba_client"));
				m.setLog_tel(rs.getString("log_tel"));
				m.setLog_email(rs.getString("log_email"));
				m.setCoba_clientmanager(rs.getString("coba_clientmanager"));
				m.setLm_tel(rs.getString("lm_tel"));
				m.setLm_email(rs.getString("lm_email"));
				m.setOwnmonth(rs.getString("ownmonth"));
				m.setReceivable(rs.getBigDecimal("receivable"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	// 修改账单付款通知状态
	public void upReportState(String billNo, String username) {
		try {
			CallableStatement c = conn
					.getcall("CoFinanceBillReportStateUp_p_lwj(?,?)");
			c.setString(1, billNo);
			c.setString(2, username);
			c.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
