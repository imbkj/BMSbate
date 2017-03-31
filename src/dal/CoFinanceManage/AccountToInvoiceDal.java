package dal.CoFinanceManage;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.AccountToInvoiceModel;

public class AccountToInvoiceDal {
	private AccountToInvoiceDal() {
	}

	private static final AccountToInvoiceDal single = new AccountToInvoiceDal();

	// 静态工厂方法
	public static AccountToInvoiceDal getInstance() {
		return single;
	}

	public List<AccountToInvoiceModel> getlist(String type) {
		List<AccountToInvoiceModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select atin_id,atin_type,atin_account,atin_km,atin_km2,atin_kind,atin_slv,atin_spbm,atin_single,atin_sort"
				+ " from AccountToInvoice" + " where atin_state=1";
		if (type != null) {
			sql += " and atin_type='" + type+"'";
		}
		try {
			list = db.find(sql, AccountToInvoiceModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
