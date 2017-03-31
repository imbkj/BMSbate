package Controller.EmCommissionOut;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.omg.CORBA.Request;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.sun.java.swing.plaf.windows.resources.windows;

import bll.EmCommissionOut.EmCommissionOutPayBll;
import bll.EmPay.EmPay_OperateBll;

import Model.EmCommissionOutPayModel;
import Model.LoginModel;

public class EmCommissionOutPayController {
	EmCommissionOutPayBll bll = new EmCommissionOutPayBll();

	private ListModelList<EmCommissionOutPayModel> ownmonthlist;// 显示帐单所属月份

	private ListModelList<EmCommissionOutPayModel> empaylist;// 显示所有帐单数

	private ListModelList<EmCommissionOutPayModel> emmonthpaylist;// 显示帐单月份明细

	private ListModelList<EmCommissionOutPayModel> autpaylist;// 显示帐单月份明细
	
	private ListModelList<EmCommissionOutPayModel> autpaysflist;// 显示帐单月份明细

	private ListModelList<EmCommissionOutPayModel> autpaycolist;// 显示公司帐单月份明细

	private ListModelList<EmCommissionOutPayModel> embasepaylist;// 显示帐单费用明细

	private ListModelList<EmCommissionOutPayModel> yfembasepaylist;// 显示系统应付帐单费用明细

	private ListModelList<EmCommissionOutPayModel> embasepaylistin;// 显示帐单费用明细

	private ListModelList<EmCommissionOutPayModel> citylist;// 委托城市

	private ListModelList<EmCommissionOutPayModel> yfcitylist;// 应付委托城市

	private ListModelList<EmCommissionOutPayModel> depcompanylist;// 委托机构

	private ListModelList<EmCommissionOutPayModel> yfdepcompanylist;// 应付委托机构

	private ListModelList<EmCommissionOutPayModel> yfemmonthpaylist;// 显示帐单月份明细

	private EmPay_OperateBll payBll = new EmPay_OperateBll();

	private ListModelList<EmCommissionOutPayModel> wt_sftotal;// 显示帐单月份明细

	private ListModelList<EmCommissionOutPayModel> wt_paylist;// 显示帐单月份明细

	@Init
	public void init() throws SQLException {
		String ecop_id = "0";
		try {
			ecop_id = Executions.getCurrent().getArg().get("ecop_id")
					.toString();
		} catch (Exception e) {
			System.out.print(e.toString());
		}

		String ecop_dep_company = "";
		try {
			ecop_dep_company = Executions.getCurrent().getArg()
					.get("ecop_dep_company").toString();
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

		String company = "";
		try {
			company = Executions.getCurrent().getArg().get("company")
					.toString();
		} catch (Exception e) {
			System.out.print(e.toString());
		}

		ownmonthlist = new ListModelList<EmCommissionOutPayModel>(
				bll.getownmonthlist());// 显示帐单月份

		empaylist = new ListModelList<EmCommissionOutPayModel>(
				bll.getempaylist("", ""));// 显示所有帐单数

		emmonthpaylist = new ListModelList<EmCommissionOutPayModel>(
				bll.getemmonthpaylist(ecop_dep_company, ""));// 显示帐单月份明细

		autpaylist = new ListModelList<EmCommissionOutPayModel>(
				bll.getautpaylist("", "",""));// 显示帐单月份明细
		
		autpaysflist = new ListModelList<EmCommissionOutPayModel>(
				bll.getautpaysflist("", ""));// 显示帐单月份明细

		autpaycolist = new ListModelList<EmCommissionOutPayModel>(
				bll.getautpaycolist(ownmonth, ecop_dep_company));// 显示公司帐单月份明细

		embasepaylist = new ListModelList<EmCommissionOutPayModel>(
				bll.getembasepaylist(ownmonth, ecop_dep_company, company, ""));// 显示帐单费用明细

		yfembasepaylist = new ListModelList<EmCommissionOutPayModel>(
				bll.getyfembasepaylist(ownmonth, ecop_dep_company));// 显示系统应付帐单费用明细

		try {
			embasepaylistin = new ListModelList<EmCommissionOutPayModel>(
					bll.getembasepaylistin(ecop_id,"",""));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 显示帐单费用明细

		citylist = new ListModelList<EmCommissionOutPayModel>(bll.getcitylist());// 显示实付委托城市

		yfcitylist = new ListModelList<EmCommissionOutPayModel>(
				bll.getyfcitylist());// 显示应付委托城市

		// depcompanylist = new ListModelList<EmCommissionOutPayModel>(
		// bll.getdepcompanylist(""));// 显示委托机构

		yfdepcompanylist = new ListModelList<EmCommissionOutPayModel>(
				bll.getdepcompanylist(""));// 显示应付委托机构

		yfemmonthpaylist = new ListModelList<EmCommissionOutPayModel>(
				bll.getyfemmonthpaylist("", ""));// 显示系统帐单月份明细
	}

	// 委托实付帐单列表
	@Command("empay_month")
	public void empay_month(@BindingParam("emco") EmCommissionOutPayModel emco) {

		Map arg = new HashMap();
		arg.put("ecop_dep_company", emco.getEcop_dep_company());
		Window wnd = (Window) Executions.createComponents(
				"EmCommissionOutPay_MonthList.zul", null, arg);
		wnd.doModal();
	}

	// 委托公司确认
	@Command("wtco_ok")
	public void wtco_ok(@BindingParam("gridco") Grid gridco,
			@BindingParam("emco") EmCommissionOutPayModel emco)
			throws Exception {
		String message = "0";
		String message1 = "0";
		try {

			for (int i = 0; i < gridco.getRows().getChildren().size(); i++) {
				Checkbox chk = (Checkbox) gridco.getCell(i, 8).getChildren()
						.get(0);

				Label ownmonth = (Label) gridco.getCell(i, 1).getChildren()
						.get(0);
				Label company = (Label) gridco.getCell(i, 3).getChildren()
						.get(0);

				if (chk.isChecked()) {
					message = bll.wtco_ok(ownmonth.getValue(),
							company.getValue(),"");

				}
			}

			/*
			 * if (desum.equals("0")) { // 更新支付费用 String[] message1 =
			 * payBll.up_pay(filename, ownmonth.getValue(), decom); }
			 */

			Messagebox.show("确认成功！", "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.INFORMATION, null);
			Executions.sendRedirect("EmCommissionOutPay_AutList.zul");
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	// 提交帐单
	@Command("zd_ok")
	public void zd_ok(@BindingParam("emco") EmCommissionOutPayModel emco)
			throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String nowtime = sdf.format(date);

		String filename = "WT" + nowtime;
		String decom = "";
		String desum = "";

		wt_paylist = new ListModelList<EmCommissionOutPayModel>(
				bll.getwt_paylist(emco.getEcop_cabc_id(), emco.getOwnmonth(),
						filename));// 显示帐单费用明细

		// for (int i = 0; i < wt_paylist.getSelection().size(); i++) {
		// int add_message = payBll .add_pay(wt_paylist.get(i).getGid(),
		// wt_paylist.get(i).getCid(), filename, emco.getOwnmonth(),
		// wt_paylist.get(i).getEcop_company() .toString(),
		// wt_paylist.get(i) .getEcop_sf_fee().toString(),
		// wt_paylist.get(i).getEcop_id());
		// }

		payBll.up_pay(filename, emco.getOwnmonth(), emco.getEcop_dep_company());

		Messagebox.show("提交成功！", "操作提示",
				new Messagebox.Button[] { Messagebox.Button.OK },
				Messagebox.INFORMATION, null);
		Executions.sendRedirect("EmCommissionOutPay_AutList.zul");
	}

	// 提交帐单
	@Command("zd_pass")
	public void zd_pass(@BindingParam("emco") EmCommissionOutPayModel emco)
			throws Exception {

		wt_paylist = new ListModelList<EmCommissionOutPayModel>(
				bll.getwt_paypass(emco.getEcop_gs_yf(), emco.getOwnmonth()));// 显示帐单费用明细

		Messagebox.show("提交成功！", "操作提示",
				new Messagebox.Button[] { Messagebox.Button.OK },
				Messagebox.INFORMATION, null);
		Executions.sendRedirect("EmCommissionOutPay_AutList.zul");
	}

	// 生成实付帐单
	@Command("sf_zd")
	public void sf_zd(@BindingParam("wt_ownmonth") Combobox wt_ownmonth,@BindingParam("wt_depcompany") Combobox wt_depcompany)
			throws Exception {

		wt_paylist = new ListModelList<EmCommissionOutPayModel>(
				bll.getwt_sfpaylist(wt_ownmonth.getValue(),wt_depcompany.getValue()));// 显示帐单费用明细

		Messagebox.show("提交成功！", "操作提示",
				new Messagebox.Button[] { Messagebox.Button.OK },
				Messagebox.INFORMATION, null);
		Executions.sendRedirect("EmCommissionOutPay_AutOverList.zul");
	}

	// 委托转下月
	@Command("wt_up")
	public void wt_up(@BindingParam("gridco") Grid gridco,
			@BindingParam("emco") EmCommissionOutPayModel emco)
			throws Exception {
		String message = "0";
		Label sownmonth = (Label) gridco.getCell(0, 2).getChildren().get(0);
		Label scabc_id = (Label) gridco.getCell(0, 18).getChildren().get(0);
		for (int i = 0; i < gridco.getRows().getChildren().size(); i++) {
			Label ownmonth = (Label) gridco.getCell(i, 2).getChildren().get(0);
			Label company = (Label) gridco.getCell(i, 3).getChildren().get(0);
			Label name = (Label) gridco.getCell(i, 4).getChildren().get(0);
			Label yl = (Label) gridco.getCell(i, 5).getChildren().get(0);
			Label sye = (Label) gridco.getCell(i, 6).getChildren().get(0);
			Label jl = (Label) gridco.getCell(i, 7).getChildren().get(0);
			Label gs = (Label) gridco.getCell(i, 8).getChildren().get(0);
			Label syu = (Label) gridco.getCell(i, 9).getChildren().get(0);
			Label house = (Label) gridco.getCell(i, 10).getChildren().get(0);
			Label other = (Label) gridco.getCell(i, 11).getChildren().get(0);
			Label file = (Label) gridco.getCell(i, 12).getChildren().get(0);
			Label fw = (Label) gridco.getCell(i, 13).getChildren().get(0);
			Label total = (Label) gridco.getCell(i, 14).getChildren().get(0);
			Label gid = (Label) gridco.getCell(i, 16).getChildren().get(0);
			Label cid = (Label) gridco.getCell(i, 17).getChildren().get(0);
			Label cabc_id = (Label) gridco.getCell(i, 18).getChildren().get(0);
			Label state = (Label) gridco.getCell(i, 19).getChildren().get(0);
			if (state.getValue().equals("1")) {
				Checkbox chk = (Checkbox) gridco.getCell(i, 15).getChildren()
						.get(0);
				if (chk.isChecked()) {
					System.out.println(chk.getValue());
					message = bll.wt_up((String) chk.getValue(),
							company.getValue(), name.getValue(), yl.getValue(),
							sye.getValue(), jl.getValue(), gs.getValue(),
							syu.getValue(), house.getValue(), total.getValue(),
							file.getValue(), fw.getValue(), total.getValue(),
							gid.getValue(), cid.getValue(), cabc_id.getValue(),
							ownmonth.getValue());
				}
			}
		}

		if (message.equals("1")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date();
			String nowtime = sdf.format(date);

			String filename = "WT" + nowtime;

			wt_sftotal = new ListModelList<EmCommissionOutPayModel>(
					bll.getwt_sftotal(scabc_id.getValue(), sownmonth.getValue()));// 显示帐单费用明细

			if (wt_sftotal.get(0).getEcop_state().toString().equals("0")) {
				int add_message = payBll.add_pay("0", "0", filename,
						sownmonth.getValue(), wt_sftotal.get(0)
								.getEcop_company().toString(), wt_sftotal
								.get(0).getEcop_paytotal().toString(), "0");

				// 更新支付费用
				String[] message1 = payBll.up_pay(filename,
						sownmonth.getValue(), wt_sftotal.get(0)
								.getEcop_company().toString());
			}
			Messagebox.show("确认成功！", "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.INFORMATION, null);
			Executions.sendRedirect("EmCommissionOutPay_AutList.zul");

		} else {
			// 弹出提示
			Messagebox.show("确认失败！", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
	}

	/**
	 * 弹出导入窗口
	 * 
	 */
	@Command("importExcel")
	@NotifyChange({ "fieldList", "spuList", "cityList", "coabList" })
	public void importExcel() {
		Window window = (Window) Executions.createComponents(
				"/EmCommissionOut/EmCommissionOutPayUpdate_ImportExcel.zul",
				null, null);
		window.doModal();
	}

	// 二次确认
	@Command("wt_autall")
	public void wt_autall(@BindingParam("gridco") Grid gridco,
			@BindingParam("emco") EmCommissionOutPayModel emco,
			@BindingParam("all_id") Label all_id,
			@BindingParam("yl_cp") Datebox yl_cp,
			@BindingParam("yl_op") Datebox yl_op,
			@BindingParam("jl_cp") Datebox jl_cp,
			@BindingParam("jl_op") Datebox jl_op,
			@BindingParam("djl_cp") Datebox djl_cp,
			@BindingParam("djl_op") Datebox djl_op,
			@BindingParam("sye_cp") Datebox sye_cp,
			@BindingParam("sye_op") Datebox sye_op,
			@BindingParam("syu_cp") Datebox syu_cp,
			@BindingParam("syu_op") Datebox syu_op,
			@BindingParam("gs_cp") Datebox gs_cp,
			@BindingParam("gs_op") Datebox gs_op,
			@BindingParam("house_cp") Datebox house_cp,
			@BindingParam("house_op") Datebox house_op,
			@BindingParam("fw_cp") Datebox fw_cp,
			@BindingParam("fw_op") Datebox fw_op,
			@BindingParam("file_cp") Datebox file_cp,
			@BindingParam("file_op") Datebox file_op,
			@BindingParam("fl_cp") Datebox fl_cp,
			@BindingParam("fl_op") Datebox fl_op,
			@BindingParam("yl_di") Label yl_di,
			@BindingParam("jl_di") Label jl_di,
			@BindingParam("djl_di") Label djl_di,
			@BindingParam("syu_di") Label syu_di,
			@BindingParam("gs_di") Label gs_di,
			@BindingParam("sye_di") Label sye_di,
			@BindingParam("house_di") Label house_di,
			@BindingParam("fw_di") Label fw_di,
			@BindingParam("file_di") Label file_di,
			@BindingParam("other_di") Label other_di,
			@BindingParam("ownmonth") Label ownmonth,
			@BindingParam("gid") Label gid, @BindingParam("cid") Label cid,
			@BindingParam("company") Label company,
			@BindingParam("cabc_id") Label cabc_id,
			@BindingParam("name") Label name) throws Exception {
		String message = "0";

		String message1 = "0";

		String message2 = "0";

		String yl_fee = "0";
		int di_state = 0;
		if (yl_cp.getValue() == null && !yl_di.getValue().equals("0.0")) {
			yl_fee = yl_di.getValue();
			di_state = di_state + 1;
		}

		String jl_fee = "0";
		if (jl_cp.getValue() == null && !jl_di.getValue().equals("0.0")) {
			jl_fee = jl_di.getValue();
			di_state = di_state + 1;
		}

		String djl_fee = "0";
		if (djl_cp.getValue() == null && !djl_di.getValue().equals("0.0")) {
			djl_fee = djl_di.getValue();
			di_state = di_state + 1;
		}

		String syu_fee = "0";
		if (syu_cp.getValue() == null && !syu_di.getValue().equals("0.0")) {
			syu_fee = syu_di.getValue();
			di_state = di_state + 1;
		}

		String gs_fee = "0";
		if (gs_cp.getValue() == null && !gs_di.getValue().equals("0.0")) {
			gs_fee = gs_di.getValue();
			di_state = di_state + 1;
		}

		String sye_fee = "0";
		if (sye_cp.getValue() == null && !sye_di.getValue().equals("0.0")) {
			sye_fee = sye_di.getValue();
			di_state = di_state + 1;
		}

		String house_fee = "0";
		if (house_cp.getValue() == null && !house_di.getValue().equals("0.0")) {
			house_fee = house_di.getValue();
			di_state = di_state + 1;
		}

		String fw_fee = "0";
		if (fw_cp.getValue() == null && !fw_di.getValue().equals("0.0")) {
			fw_fee = fw_di.getValue();
			di_state = di_state + 1;
		}

		String file_fee = "0";
		if (file_cp.getValue() == null && !file_di.getValue().equals("0.0")) {
			file_fee = file_di.getValue();
			di_state = di_state + 1;
		}

		String other_fee = "0";
		if (fl_cp.getValue() == null && !other_di.getValue().equals("0.0")) {
			other_fee = other_di.getValue();
			di_state = di_state + 1;
		}

		// if (di_state != 0) {
		// message = bll.wt_allup(all_id.getValue(), company.getValue(),
		// name.getValue(), yl_fee, sye_fee, jl_fee, gs_fee, syu_fee,
		// house_fee, other_fee, file_fee, fw_fee, "0",
		// gid.getValue(), cid.getValue(), cabc_id.getValue(),
		// ownmonth.getValue());
		// }

		String yl_cpdate = "";
		if (yl_cp.getValue() != null) {
			yl_cpdate = bll.DatetoSting(yl_cp.getValue());
		}

		String yl_opdate = "";
		if (yl_op.getValue() != null) {
			yl_opdate = bll.DatetoSting(yl_op.getValue());
		}

		String jl_cpdate = "";
		if (jl_cp.getValue() != null) {
			jl_cpdate = bll.DatetoSting(jl_cp.getValue());
		}

		String jl_opdate = "";
		if (jl_op.getValue() != null) {
			jl_opdate = bll.DatetoSting(jl_op.getValue());
		}

		String gs_cpdate = "";
		if (gs_cp.getValue() != null) {
			gs_cpdate = bll.DatetoSting(gs_cp.getValue());
		}

		String gs_opdate = "";
		if (gs_op.getValue() != null) {
			gs_opdate = bll.DatetoSting(gs_op.getValue());
		}

		String syu_cpdate = "";
		if (syu_cp.getValue() != null) {
			syu_cpdate = bll.DatetoSting(syu_cp.getValue());
		}

		String syu_opdate = "";
		if (syu_op.getValue() != null) {
			syu_opdate = bll.DatetoSting(syu_op.getValue());
		}

		String sye_cpdate = "";
		if (sye_cp.getValue() != null) {
			sye_cpdate = bll.DatetoSting(sye_cp.getValue());
		}

		String sye_opdate = "";
		if (sye_op.getValue() != null) {
			sye_opdate = bll.DatetoSting(sye_op.getValue());
		}

		String house_cpdate = "";
		if (house_cp.getValue() != null) {
			house_cpdate = bll.DatetoSting(house_cp.getValue());
		}

		String house_opdate = "";
		if (house_op.getValue() != null) {
			house_opdate = bll.DatetoSting(house_op.getValue());
		}

		String fw_date = "";
		if (fw_cp.getValue() != null) {
			fw_date = bll.DatetoSting(fw_cp.getValue());
		}

		String file_date = "";
		if (file_cp.getValue() != null) {
			file_date = bll.DatetoSting(file_cp.getValue());
		}

		String other_date = "";
		if (fl_cp.getValue() != null) {
			other_date = bll.DatetoSting(fl_cp.getValue());
		}

		// message1 = bll.wt_ok(all_id.getValue());

		message1 = bll.wtdate_ok(gid.getValue(), yl_cpdate, yl_opdate,
				jl_cpdate, jl_opdate, gs_cpdate, gs_opdate, syu_cpdate,
				syu_opdate, sye_cpdate, sye_opdate, house_cpdate, house_opdate,
				fw_date, file_date, other_date);

		if (message1.equals("1")) {
			Messagebox.show("确认成功！", "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.INFORMATION, null);
			Executions.sendRedirect("EmCommissionOutPay_AutList.zul");

		} else {
			// 弹出提示
			Messagebox.show("二次确认失败！", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
	}

	// 委托机构查询
	@Command("cityChange")
	@NotifyChange("depcompanylist")
	public void cityChange(@BindingParam("wt_city") Combobox wt_city)
			throws SQLException {
		depcompanylist = new ListModelList<EmCommissionOutPayModel>(
				bll.getdepcompanylist(wt_city.getValue()));// 显示所有帐单数
	}

	// 应付委托机构查询
	@Command("yfcityChange")
	@NotifyChange("yfdepcompanylist")
	public void yfcityChange(@BindingParam("wt_city") Combobox wt_city)
			throws SQLException {
		yfdepcompanylist = new ListModelList<EmCommissionOutPayModel>(
				bll.getyfdepcompanylist(wt_city.getValue()));// 显示所有帐单数
	}

	// 完成匹配
	@Command("pay_ok")
	@NotifyChange("emmonthpaylist")
	public void pay_ok(@BindingParam("dep_company") Label dep_company,
			@BindingParam("p1") Button p1) throws SQLException {
		emmonthpaylist = new ListModelList<EmCommissionOutPayModel>(
				bll.getemmonthpaylist(dep_company.getValue(), p1.getId()));// 显示帐单月份明细
	}

	// 实付搜索
	@Command("sf_search")
	@NotifyChange("empaylist")
	public void sf_search(@BindingParam("wt_city") Combobox wt_city,
			@BindingParam("wt_depcompany") Combobox wt_depcompany)
			throws SQLException {
		empaylist = new ListModelList<EmCommissionOutPayModel>(
				bll.getempaylist(wt_city.getValue(), wt_depcompany.getValue()));// 显示所有帐单数
	}

	// 应付搜索
	@Command("yf_search")
	@NotifyChange("yfemmonthpaylist")
	public void yf_search(@BindingParam("wt_city") Combobox wt_city,
			@BindingParam("wt_depcompany") Combobox wt_depcompany)
			throws SQLException {
		yfemmonthpaylist = new ListModelList<EmCommissionOutPayModel>(
				bll.getyfemmonthpaylist(wt_city.getValue(),
						wt_depcompany.getValue()));// 显示所有帐单数
	}

	// 审核搜索
	@Command("aut_search")
	@NotifyChange("autpaylist")
	public void aut_search(@BindingParam("wt_ownmonth") Combobox wt_ownmonth,
			@BindingParam("wt_depcompany") Combobox wt_depcompany)
			throws SQLException {
		autpaylist = new ListModelList<EmCommissionOutPayModel>(
				bll.getautpaylist(wt_ownmonth.getValue(),
						wt_depcompany.getValue(),""));// 显示所有帐单数
	}

	// 添加费用
	@Command("co_feeadd")
	public void co_feeadd(@BindingParam("gid") Label gid,
			@BindingParam("scid") Label scid,
			@BindingParam("ownmonth") Label ownmonth,
			@BindingParam("cabc_id") Label cabc_id,
			@BindingParam("name") Label name,
			@BindingParam("yl") Textbox yl, @BindingParam("jl") Textbox jl,
			@BindingParam("syu") Textbox syu, @BindingParam("gs") Textbox gs,
			@BindingParam("sye") Textbox sye,
			@BindingParam("house") Textbox house,
			@BindingParam("file") Textbox file, @BindingParam("fw") Textbox fw,
			@BindingParam("other") Textbox other,
			@BindingParam("remark") Textbox remark,
			@BindingParam("win") Window win) throws SQLException {
		String message = "0";
		message = bll.feeadd(gid.getValue(), scid.getValue(),
				ownmonth.getValue(), cabc_id.getValue(), name.getValue(),
				yl.getValue(), jl.getValue(),
				syu.getValue(), gs.getValue(), sye.getValue(),
				house.getValue(), file.getValue(), fw.getValue(),
				other.getValue(), remark.getValue());

		if (message.equals("1")) {

			Messagebox.show("添加成功！", "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.INFORMATION, null);
			//Executions.sendRedirect("EmCommissionOutPay_AutList.zul");
			win.detach();
		} else {
			// 弹出提示
			Messagebox.show("添加失败！", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
	}

	// 部份匹配
	@Command("pay_ok2")
	@NotifyChange("emmonthpaylist")
	public void pay_ok2(@BindingParam("dep_company") Label dep_company,
			@BindingParam("p2") Button p2) throws SQLException {
		emmonthpaylist = new ListModelList<EmCommissionOutPayModel>(
				bll.getemmonthpaylist(dep_company.getValue(), p2.getId()));// 显示帐单月份明细
	}

	// 完全不匹配
	@Command("pay_ok3")
	@NotifyChange("emmonthpaylist")
	public void pay_ok3(@BindingParam("dep_company") Label dep_company,
			@BindingParam("p3") Button p3) throws SQLException {
		emmonthpaylist = new ListModelList<EmCommissionOutPayModel>(
				bll.getemmonthpaylist(dep_company.getValue(), p3.getId()));// 显示帐单月份明细
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

	// 委托实付明细
	@Command("empay_base")
	public void empay_base(@BindingParam("emco") EmCommissionOutPayModel emco) {

		Map arg = new HashMap();
		arg.put("ecop_dep_company", emco.getEcop_cabc_id());
		arg.put("ownmonth", emco.getOwnmonth());
		arg.put("company", emco.getCid());
		Window wnd = (Window) Executions.createComponents(
				"EmCommissionOutPay_BaseList1.zul", null, arg);
		wnd.doModal();
	}

	// 委托公司实付明细
	@Command("copay_base")
	public void copay_base(@BindingParam("emco") EmCommissionOutPayModel emco) {

		Map arg = new HashMap();
		arg.put("ecop_dep_company", emco.getEcop_dep_company());
		arg.put("ownmonth", emco.getOwnmonth());
		Window wnd = (Window) Executions.createComponents(
				"EmCommissionOutPay_CoBaseList.zul", null, arg);
		wnd.doModal();
	}

	// 委托应付明细
	@Command("yfempay_base")
	public void yfempay_base(@BindingParam("emco") EmCommissionOutPayModel emco) {

		Map arg = new HashMap();
		arg.put("ecop_dep_company", emco.getEcop_dep_company());
		arg.put("ownmonth", emco.getOwnmonth());
		Window wnd = (Window) Executions.createComponents(
				"EmCommissionOutPay_YFBaseList1.zul", null, arg);
		wnd.doModal();
	}

	// 委托应付明细
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

	// 二次确认
	@Command("wt_autok")
	public void wt_autok(@BindingParam("gridco") Grid gridco,
			@BindingParam("emco") EmCommissionOutPayModel emco) {
		Map arg = new HashMap();
		arg.put("emco", emco);
		Window wnd = (Window) Executions.createComponents(
				"EmCommissionOutPay_AutOk.zul", null, arg);
		wnd.doModal();
	}
	
	// 二次确认
		@Command("ec_aut")
		public void ec_aut(@BindingParam("emco") EmCommissionOutPayModel emco) {
			Map arg = new HashMap();
			arg.put("gid", emco.getGid());
			Window wnd = (Window) Executions.createComponents(
					"EmCommissionOut_OveAut.zul", null, arg);
			wnd.doModal();
		}

	// 费用添加
	@Command("wt_sfeeadd")
	public void wt_sfeeadd(@BindingParam("emco") EmCommissionOutPayModel emco,
			@BindingParam("dep_company") Label dep_company,
			@BindingParam("company") Label company,
			@BindingParam("scid") Label scid,
			@BindingParam("ownmonth") Label ownmonth) {
		Map arg = new HashMap();
		arg.put("emco", emco);
		arg.put("dep_company", dep_company.getValue());
		arg.put("company", company.getValue());
		arg.put("scid", scid.getValue());
		arg.put("ownmonth", ownmonth.getValue());
		Window wnd = (Window) Executions.createComponents(
				"EmCommissionOutPay_SFeeAdd.zul", null, arg);
		wnd.doModal();
	}

	// 费用添加
	@Command("wt_spayadd")
	public void wt_spayadd(@BindingParam("emco") EmCommissionOutPayModel emco,
			@BindingParam("dep_company") Label dep_company,
			@BindingParam("company") Label company,
			@BindingParam("scid") Label scid,
			@BindingParam("ownmonth") Label ownmonth) {
		Map arg = new HashMap();
		arg.put("emco", emco);
		arg.put("dep_company", dep_company.getValue());
		arg.put("company", company.getValue());
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
			Label state = (Label) gd.getCell(i, 20).getChildren().get(0);

			if (state.getValue().equals("1")) {
				Checkbox ckb = (Checkbox) gd.getCell(i, 16).getChildren()
						.get(0);
				ckb.setChecked(ck);
			}
		}
	}

	@Command("cocheckall")
	public void cocheckall(@BindingParam("a") boolean ck,
			@BindingParam("b") Grid gd) {
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {

			Label dis = (Label) gd.getCell(i, 6).getChildren().get(0);

			Label state = (Label) gd.getCell(i, 7).getChildren().get(0);

			if (dis.equals("0") && state.equals("未确认")) {
				Checkbox ckb = (Checkbox) gd.getCell(i, 8).getChildren().get(0);
				ckb.setChecked(ck);
			}
		}
	}

	public ListModelList<EmCommissionOutPayModel> getEmpaylist() {
		return empaylist;
	}

	public void setEmpaylist(ListModelList<EmCommissionOutPayModel> empaylist) {
		this.empaylist = empaylist;
	}

	public ListModelList<EmCommissionOutPayModel> getEmmonthpaylist() {
		return emmonthpaylist;
	}

	public void setEmmonthpaylist(
			ListModelList<EmCommissionOutPayModel> emmonthpaylist) {
		this.emmonthpaylist = emmonthpaylist;
	}

	public ListModelList<EmCommissionOutPayModel> getEmbasepaylist() {
		return embasepaylist;
	}

	public void setEmbasepaylist(
			ListModelList<EmCommissionOutPayModel> embasepaylist) {
		this.embasepaylist = embasepaylist;
	}

	public ListModelList<EmCommissionOutPayModel> getEmbasepaylistin() {
		return embasepaylistin;
	}

	public void setEmbasepaylistin(
			ListModelList<EmCommissionOutPayModel> embasepaylistin) {
		this.embasepaylistin = embasepaylistin;
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

	public ListModelList<EmCommissionOutPayModel> getYfemmonthpaylist() {
		return yfemmonthpaylist;
	}

	public void setYfemmonthpaylist(
			ListModelList<EmCommissionOutPayModel> yfemmonthpaylist) {
		this.yfemmonthpaylist = yfemmonthpaylist;
	}

	public ListModelList<EmCommissionOutPayModel> getYfembasepaylist() {
		return yfembasepaylist;
	}

	public void setYfembasepaylist(
			ListModelList<EmCommissionOutPayModel> yfembasepaylist) {
		this.yfembasepaylist = yfembasepaylist;
	}

	public ListModelList<EmCommissionOutPayModel> getYfcitylist() {
		return yfcitylist;
	}

	public void setYfcitylist(ListModelList<EmCommissionOutPayModel> yfcitylist) {
		this.yfcitylist = yfcitylist;
	}

	public ListModelList<EmCommissionOutPayModel> getYfdepcompanylist() {
		return yfdepcompanylist;
	}

	public void setYfdepcompanylist(
			ListModelList<EmCommissionOutPayModel> yfdepcompanylist) {
		this.yfdepcompanylist = yfdepcompanylist;
	}

	public ListModelList<EmCommissionOutPayModel> getOwnmonthlist() {
		return ownmonthlist;
	}

	public void setOwnmonthlist(
			ListModelList<EmCommissionOutPayModel> ownmonthlist) {
		this.ownmonthlist = ownmonthlist;
	}

	public ListModelList<EmCommissionOutPayModel> getAutpaylist() {
		return autpaylist;
	}

	public void setAutpaylist(ListModelList<EmCommissionOutPayModel> autpaylist) {
		this.autpaylist = autpaylist;
	}

	public ListModelList<EmCommissionOutPayModel> getAutpaycolist() {
		return autpaycolist;
	}

	public void setAutpaycolist(
			ListModelList<EmCommissionOutPayModel> autpaycolist) {
		this.autpaycolist = autpaycolist;
	}

	public ListModelList<EmCommissionOutPayModel> getAutpaysflist() {
		return autpaysflist;
	}

	public void setAutpaysflist(ListModelList<EmCommissionOutPayModel> autpaysflist) {
		this.autpaysflist = autpaysflist;
	}

	
}
