package Controller.EmCommissionOut;

import java.sql.SQLException;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;

import bll.EmCommissionOut.EmCommissionOutPayBll;

import Model.EmCommissionOutPayModel;

public class EmCommissionOutPay_ErrController {
	private ListModelList<EmCommissionOutPayModel> pay_yflist;// 显示帐单月份明细
	EmCommissionOutPayBll bll = new EmCommissionOutPayBll();
	@Init
	public void init() throws SQLException {
		String ownmonth = "";
		try {
			ownmonth = Executions.getCurrent().getArg().get("ownmonth")
					.toString();
		} catch (Exception e) {
			System.out.print(e.toString());
		}

		String cabc_id = "";
		try {
			cabc_id = Executions.getCurrent().getArg()
					.get("cabc_id").toString();
		} catch (Exception e) {
			System.out.print(e.toString());
		}
		
		String er_st = "";
		try {
			er_st = Executions.getCurrent().getArg()
					.get("er_st").toString();
		} catch (Exception e) {
			System.out.print(e.toString());
		}
		
		pay_yflist = new ListModelList<EmCommissionOutPayModel>(
				bll.getpay_yflist(cabc_id,ownmonth,er_st));// 显示帐单月份明细
	}
	public ListModelList<EmCommissionOutPayModel> getPay_yflist() {
		return pay_yflist;
	}
	public void setPay_yflist(ListModelList<EmCommissionOutPayModel> pay_yflist) {
		this.pay_yflist = pay_yflist;
	}
	
	
}
