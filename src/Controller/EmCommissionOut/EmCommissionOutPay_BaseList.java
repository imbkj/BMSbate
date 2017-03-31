package Controller.EmCommissionOut;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmCommissionOutPayModel;
import Model.LoginModel;
import bll.EmCommissionOut.EmCommissionOutPayBll;

public class EmCommissionOutPay_BaseList {
	EmCommissionOutPayBll bll = new EmCommissionOutPayBll();

	private ListModelList<EmCommissionOutPayModel> embasepaylist;// 显示帐单费用明细

	@Init
	public void init() throws SQLException {

		String cabc_id = "";
		try {
			cabc_id = Executions.getCurrent().getArg().get("cabc_id")
					.toString();
		} catch (Exception e) {
			System.out.print(e.toString());
		}

		String ownmonth = "";
		try {
			ownmonth = Executions.getCurrent().getArg().get("ownmonth")
					.toString();
		} catch (Exception e) {
			System.out.print(e.toString());
		}

		String cid = "";
		try {
			cid = Executions.getCurrent().getArg().get("cid").toString();
		} catch (Exception e) {
			System.out.print(e.toString());
		}

		embasepaylist = new ListModelList<EmCommissionOutPayModel>(
				bll.getembasepaylist(ownmonth, cabc_id, cid, ""));// 显示帐单费用明细

		// yfembasepaylist = new ListModelList<EmCommissionOutPayModel>(
		// bll.getyfembasepaylist(ownmonth, ecop_dep_company));// 显示系统应付帐单费用明细
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

	// 完成匹配
	@Command("autpay_ok")
	@NotifyChange("embasepaylist")
	public void autpay_ok(@BindingParam("ownmonth") Label ownmonth,
			@BindingParam("dep_company") Label dep_company,
			@BindingParam("company") Label company,
			@BindingParam("p1") Button p1) throws SQLException {
		embasepaylist = new ListModelList<EmCommissionOutPayModel>(
				bll.getembasepaylist(ownmonth.getValue(),
						dep_company.getValue(), company.getValue(), p1.getId()));// 显示帐单月份明细
	}

	// 部份匹配
	@Command("autpay_ok2")
	@NotifyChange("embasepaylist")
	public void autpay_ok2(@BindingParam("ownmonth") Label ownmonth,
			@BindingParam("dep_company") Label dep_company,
			@BindingParam("company") Label company,
			@BindingParam("p2") Button p2) throws SQLException {
		embasepaylist = new ListModelList<EmCommissionOutPayModel>(
				bll.getembasepaylist(ownmonth.getValue(),
						dep_company.getValue(), company.getValue(), p2.getId()));// 显示帐单月份明细
	}

	// 完全不匹配
	@Command("autpay_ok3")
	@NotifyChange("embasepaylist")
	public void autpay_ok3(@BindingParam("ownmonth") Label ownmonth,
			@BindingParam("dep_company") Label dep_company,
			@BindingParam("company") Label company,
			@BindingParam("p3") Button p3) throws SQLException {
		embasepaylist = new ListModelList<EmCommissionOutPayModel>(
				bll.getembasepaylist(ownmonth.getValue(),
						dep_company.getValue(), company.getValue(), p3.getId()));// 显示帐单月份明细
	}

	// 委托确认
	@Command("wt_ok")
	@NotifyChange("embasepaylist")
	public void wt_ok(@BindingParam("gridco") Grid gridco,
			@BindingParam("cabc_id") Label cabc_id,
			@BindingParam("scid") Label scid,
			@BindingParam("ownmonth") Label ownmonth) throws Exception {
		String message = "0";
		String message1 = "0";

		for (int i = 0; i < gridco.getRows().getChildren().size(); i++) {
			try {
				Checkbox chk = (Checkbox) gridco.getCell(i, 17).getChildren()
						.get(0);

				if (chk.isChecked()) {
					message = bll.wt_ok((String) chk.getValue());
				}

			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.toString());
			}

		}
		Messagebox.show("确认成功！", "操作提示",
				new Messagebox.Button[] { Messagebox.Button.OK },
				Messagebox.INFORMATION, null);
		embasepaylist = new ListModelList<EmCommissionOutPayModel>(
				bll.getembasepaylist(ownmonth.getValue(), cabc_id.getValue(),
						scid.getValue(), ""));// 显示帐单费用明细z

	}

	// 费用添加
	@Command("wt_sfeeadd")
	@NotifyChange("embasepaylist")
	public void wt_sfeeadd(@BindingParam("emco") EmCommissionOutPayModel emco,
			@BindingParam("cabc_id") Label cabc_id,
			@BindingParam("scid") Label scid,
			@BindingParam("ownmonth") Label ownmonth) throws SQLException {
		Map arg = new HashMap();
		arg.put("emco", emco);
		arg.put("cabc_id", cabc_id.getValue());
		arg.put("scid", scid.getValue());
		arg.put("ownmonth", ownmonth.getValue());
		Window wnd = (Window) Executions.createComponents(
				"EmCommissionOutPay_SFeeAdd.zul", null, arg);
		wnd.doModal();

		embasepaylist = new ListModelList<EmCommissionOutPayModel>(
				bll.getembasepaylist(ownmonth.getValue(), cabc_id.getValue(),
						scid.getValue(), ""));// 显示帐单费用明细z
	}

	// 费用添加
	@Command("wt_spayadd")
	public void wt_spayadd(@BindingParam("emco") EmCommissionOutPayModel emco,
			@BindingParam("cabc_id") Label cabc_id,
			@BindingParam("scid") Label scid,
			@BindingParam("ownmonth") Label ownmonth) {
		Map arg = new HashMap();
		arg.put("emco", emco);
		arg.put("cabc_id", cabc_id.getValue());
		arg.put("scid", scid.getValue());
		arg.put("ownmonth", ownmonth.getValue());
		Window wnd = (Window) Executions.createComponents(
				"EmCommissionOutPay_SFeeAdd.zul", null, arg);
		wnd.doModal();
	}
	
	@Command("checkall")
	public void checkall(@BindingParam("a") boolean ck,
			@BindingParam("b") Grid gd) {
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			try {
				Checkbox ckb = (Checkbox) gd.getCell(i, 17).getChildren()
						.get(0);
				ckb.setChecked(ck);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	@Command("remark")
	public void remark(@BindingParam("emco") EmCommissionOutPayModel emco) {
		Map map = new HashMap<>();
		map.put("type", "委托对帐");// 业务类型:来自WfClass的wfcl_name
		map.put("id", Integer.parseInt(emco.getEcop_id()));// 业务表id
		map.put("tablename", "EmCommissionOutPay");// 业务表名

		List<LoginModel> mlist = new ArrayList<LoginModel>();
		LoginModel m = new LoginModel();
		m.setLog_name("");
		m.setLog_id(0);
		// 收件人姓名和收件人id至少要填一个
		mlist.add(m);
		map.put("list", mlist);// 默认收件人list
		Window window = (Window) Executions.createComponents(
				"../SysMessage/Message_Add.zul", null, map);
		window.doModal();

	}

	public ListModelList<EmCommissionOutPayModel> getEmbasepaylist() {
		return embasepaylist;
	}

	public void setEmbasepaylist(
			ListModelList<EmCommissionOutPayModel> embasepaylist) {
		this.embasepaylist = embasepaylist;
	}

}
