package Controller.EmPay;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import bll.EmPay.EmPay_ListBll;
import Model.EmPay_ListModel;

public class EmPay_Controller {
	EmPay_ListBll bll = new EmPay_ListBll();
	private ListModelList<EmPay_ListModel> empay_list;// 显示支付查询列表
	private ListModelList<EmPay_ListModel> empay_autlist;// 显示支付通知未审核数据
	private ListModelList<EmPay_ListModel> empay_mgautlist;// 显示支付通知已审核数据（总经理审核）
	private ListModelList<EmPay_ListModel> empay_autpaylist;// 显示支付通知已审核数据

	@Init
	public void init() throws SQLException {
		empay_autlist = new ListModelList<EmPay_ListModel>(
				bll.empay_autlist(""));// 显示未审核列表

		empay_autpaylist = new ListModelList<EmPay_ListModel>(
				bll.empay_autpaylist(""));// 显示已审核列表

		empay_mgautlist = new ListModelList<EmPay_ListModel>(
				bll.empay_mgautlist(""));// 显示已审核列表

		empay_list = new ListModelList<EmPay_ListModel>(bll.empay_list(""));// 显示已审核列表
	}

	// 审核支付通知
	@Command("empay_aut")
	public void empay_aut(@BindingParam("emco") EmPay_ListModel emco) {
		Map arg = new HashMap();
		// arg.put("daid", emco.getEbco_id());
		arg.put("id", emco.getEspa_id());
		arg.put("ownmonth", emco.getOwnmonth());
		arg.put("paynum", emco.getEspa_paynum());
		arg.put("stype", emco.getEspa_type());
		arg.put("fee", emco.getEspa_sf_fee());
		arg.put("espa_tapr_id", emco.getEspa_tapr_id());
		Window wnd = (Window) Executions.createComponents("EmPay_AutBase.zul",
				null, arg);
		wnd.doModal();
	}

	// 审核支付通知
	@Command("empay_mgaut")
	public void empay_mgaut(@BindingParam("emco") EmPay_ListModel emco) {
		Map arg = new HashMap();
		// arg.put("daid", emco.getEbco_id());
		arg.put("id", emco.getEspa_id());
		arg.put("ownmonth", emco.getOwnmonth());
		arg.put("paynum", emco.getEspa_paynum());
		arg.put("stype", emco.getEspa_type());
		arg.put("fee", emco.getEspa_sf_fee());
		arg.put("espa_tapr_id", emco.getEspa_tapr_id());
		Window wnd = (Window) Executions.createComponents("EmPay_MgBase.zul",
				null, arg);
		wnd.doModal();
	}

	// 审核支付通知
	@Command("empay_payok")
	public void empay_payok(@BindingParam("emco") EmPay_ListModel emco) {
		Map arg = new HashMap();
		// arg.put("daid", emco.getEbco_id());
		arg.put("id", emco.getEspa_id());
		arg.put("ownmonth", emco.getOwnmonth());
		arg.put("paynum", emco.getEspa_paynum());
		arg.put("stype", emco.getEspa_type());
		arg.put("fee", emco.getEspa_sf_fee());
		arg.put("espa_tapr_id", emco.getEspa_tapr_id());
		Window wnd = (Window) Executions.createComponents("EmPay_PayOk.zul",
				null, arg);
		wnd.doModal();
	}

	public ListModelList<EmPay_ListModel> getEmpay_autlist() {
		return empay_autlist;
	}

	public void setEmpay_autlist(ListModelList<EmPay_ListModel> empay_autlist) {
		this.empay_autlist = empay_autlist;
	}

	public ListModelList<EmPay_ListModel> getEmpay_autpaylist() {
		return empay_autpaylist;
	}

	public void setEmpay_autpaylist(
			ListModelList<EmPay_ListModel> empay_autpaylist) {
		this.empay_autpaylist = empay_autpaylist;
	}

	public ListModelList<EmPay_ListModel> getEmpay_mgautlist() {
		return empay_mgautlist;
	}

	public void setEmpay_mgautlist(
			ListModelList<EmPay_ListModel> empay_mgautlist) {
		this.empay_mgautlist = empay_mgautlist;
	}

	public ListModelList<EmPay_ListModel> getEmpay_list() {
		return empay_list;
	}

	public void setEmpay_list(
			ListModelList<EmPay_ListModel> empay_list) {
		this.empay_list = empay_list;
	}
}
