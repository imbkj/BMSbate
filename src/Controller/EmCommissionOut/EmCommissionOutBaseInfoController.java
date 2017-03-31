package Controller.EmCommissionOut;

import java.sql.SQLException;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;

import bll.EmCommissionOut.EmCommissionOutPayBll;

import Model.EmCommissionOutPayModel;

public class EmCommissionOutBaseInfoController {
	private ListModelList<EmCommissionOutPayModel> embasepaylistin;// 显示帐单费用明细
	EmCommissionOutPayBll bll = new EmCommissionOutPayBll();
	@Init
	public void init() throws Exception {
		String gid = "0";
		try {
			gid = Executions.getCurrent().getArg().get("gid")
					.toString();
		} catch (Exception e) {
			System.out.print(e.toString());
		}
		
		String ownmonth = "0";
		try {
			ownmonth = Executions.getCurrent().getArg().get("sownmonth")
					.toString();
		} catch (Exception e) {
			System.out.print(e.toString());
		}
		
		String cabc_idl = "0";
		try {
			cabc_idl = Executions.getCurrent().getArg().get("cabc_idl")
					.toString();
		} catch (Exception e) {
			System.out.print(e.toString());
		}
		
	embasepaylistin = new ListModelList<EmCommissionOutPayModel>(
			bll.getembasepaylistin(gid,ownmonth,cabc_idl));// 显示帐单费用明细
	}
	public ListModelList<EmCommissionOutPayModel> getEmbasepaylistin() {
		return embasepaylistin;
	}
	public void setEmbasepaylistin(
			ListModelList<EmCommissionOutPayModel> embasepaylistin) {
		this.embasepaylistin = embasepaylistin;
	}
	
	
}
