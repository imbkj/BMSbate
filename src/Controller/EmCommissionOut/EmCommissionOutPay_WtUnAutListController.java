package Controller.EmCommissionOut;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import bll.EmCommissionOut.EmCommissionOutPay_UnfinishedBll;

import Model.EmCommissionOutPayModel;
import Util.UserInfo;

public class EmCommissionOutPay_WtUnAutListController {
	private ListModelList<EmCommissionOutPayModel> wt_wtunflist;// 显示后道差额成列表

	private ListModelList<EmCommissionOutPayModel> clientlist;// 显示客服

	private EmCommissionOutPay_UnfinishedBll bll = new EmCommissionOutPay_UnfinishedBll();

	String cabc_id = "";
	String ownmonth = "";

	@Init
	public void init() throws SQLException {

		try {
			cabc_id = Executions.getCurrent().getArg().get("cabc_id")
					.toString();
		} catch (Exception e) {
			System.out.print(e.toString());
		}

		try {
			ownmonth = Executions.getCurrent().getArg().get("ownmonth")
					.toString();
		} catch (Exception e) {
			System.out.print(e.toString());
		}

		wt_wtunflist = new ListModelList<EmCommissionOutPayModel>(
				bll.getwt_wtunflist("", "", "", cabc_id, ownmonth));// 显示差额列表数据

		clientlist = new ListModelList<EmCommissionOutPayModel>(
				bll.getclientlist());// 显示未完成列表数据
	}

	@Command("wtaut_search")
	@NotifyChange("wt_wtunflist")
	public void wtaut_search(@BindingParam("client") Combobox client,
			@BindingParam("name") Textbox name,
			@BindingParam("gid") Textbox gid,
			@BindingParam("company") Textbox company) throws SQLException {

		wt_wtunflist = new ListModelList<EmCommissionOutPayModel>(
				bll.getwt_wtunflist(gid.getValue(), name.getValue(),
						company.getValue(), cabc_id, ownmonth));// 显示未完成列表数据
	}

	// 委托二次确认页面
	@Command("ec_aut")
	public void ec_aut(@BindingParam("emco") EmCommissionOutPayModel emco) {

		Map arg = new HashMap();
		arg.put("gid", emco.getGid());
		arg.put("zd_url", 1);
		Window wnd = (Window) Executions.createComponents(
				"/EmCommissionOutNew/EmCommissionOut_OveAut.zul", null, arg);
		wnd.doModal();
	}

	// 费用添加
	@Command("wt_sfeeadd")
	@NotifyChange("embasepaylist")
	public void wt_sfeeadd(@BindingParam("emco") EmCommissionOutPayModel emco) throws SQLException {
		Map arg = new HashMap();
		arg.put("emco", emco);
		arg.put("cabc_id", emco.getEcop_cabc_id());
		arg.put("scid", emco.getCid());
		arg.put("ownmonth",ownmonth);
		Window wnd = (Window) Executions.createComponents(
				"EmCommissionOutPay_SFeeAdd.zul", null, arg);
		wnd.doModal();

		wt_wtunflist = new ListModelList<EmCommissionOutPayModel>(
				bll.getwt_wtunflist("", "", "", cabc_id, ownmonth));// 显示差额列表数据
	}

	public ListModelList<EmCommissionOutPayModel> getWt_wtunflist() {
		return wt_wtunflist;
	}

	public void setWt_wtunflist(
			ListModelList<EmCommissionOutPayModel> wt_wtunflist) {
		this.wt_wtunflist = wt_wtunflist;
	}

	public ListModelList<EmCommissionOutPayModel> getClientlist() {
		return clientlist;
	}

	public void setClientlist(ListModelList<EmCommissionOutPayModel> clientlist) {
		this.clientlist = clientlist;
	}

}
