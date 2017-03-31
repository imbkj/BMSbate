package Controller.EmCommissionOut;

import java.sql.SQLException;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import bll.EmCommissionOut.EmCommissionOutPayBll;

import Model.EmCommissionOutPayModel;

public class EmCommissionOutPayOverController {
	private ListModelList<EmCommissionOutPayModel> autpaysflist;// 显示帐单月份明细

	private ListModelList<EmCommissionOutPayModel> wt_paylist;// 显示帐单月份明细
	
	private ListModelList<EmCommissionOutPayModel> ownmonthlist;// 显示帐单所属月份
	
	private ListModelList<EmCommissionOutPayModel> citylist;// 委托城市
	
	private ListModelList<EmCommissionOutPayModel> depcompanylist;// 委托机构

	EmCommissionOutPayBll bll = new EmCommissionOutPayBll();

	// 生成实付帐单
	@Command("sf_zd")
	public void sf_zd(@BindingParam("wt_ownmonth") Combobox wt_ownmonth,
			@BindingParam("wt_depcompany") Combobox wt_depcompany)
			throws Exception {

		wt_paylist = new ListModelList<EmCommissionOutPayModel>(
				bll.getwt_sfpaylist(wt_ownmonth.getValue(),
						wt_depcompany.getValue()));// 显示帐单费用明细

		Messagebox.show("提交成功！", "操作提示",
				new Messagebox.Button[] { Messagebox.Button.OK },
				Messagebox.INFORMATION, null);
		Executions.sendRedirect("EmCommissionOutPay_AutOverList.zul");
	}

	// 审核搜索
	@Command("aut_search")
	@NotifyChange("autpaysflist")
	public void aut_search(@BindingParam("wt_ownmonth") Combobox wt_ownmonth,
			@BindingParam("wt_depcompany") Combobox wt_depcompany)
			throws SQLException {
	
		autpaysflist = new ListModelList<EmCommissionOutPayModel>(
				bll.getautpaysflist(wt_ownmonth.getValue(),
						wt_depcompany.getValue()));// 显示帐单月份明细
	}

	public ListModelList<EmCommissionOutPayModel> getAutpaysflist() {
		return autpaysflist;
	}

	public void setAutpaysflist(ListModelList<EmCommissionOutPayModel> autpaysflist) {
		this.autpaysflist = autpaysflist;
	}

	public ListModelList<EmCommissionOutPayModel> getWt_paylist() {
		return wt_paylist;
	}

	public void setWt_paylist(ListModelList<EmCommissionOutPayModel> wt_paylist) {
		this.wt_paylist = wt_paylist;
	}

	public ListModelList<EmCommissionOutPayModel> getOwnmonthlist() {
		return ownmonthlist;
	}

	public void setOwnmonthlist(ListModelList<EmCommissionOutPayModel> ownmonthlist) {
		this.ownmonthlist = ownmonthlist;
	}

	public ListModelList<EmCommissionOutPayModel> getCitylist() {
		return citylist;
	}

	public void setCitylist(ListModelList<EmCommissionOutPayModel> citylist) {
		this.citylist = citylist;
	}

	public ListModelList<EmCommissionOutPayModel> getDepcompanylist() {
		return depcompanylist;
	}

	public void setDepcompanylist(
			ListModelList<EmCommissionOutPayModel> depcompanylist) {
		this.depcompanylist = depcompanylist;
	}
	
	
}
