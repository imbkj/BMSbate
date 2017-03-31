package Controller.EmCommercialInsurance;

import impl.WorkflowCore.WfOperateImpl;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import com.sun.java.swing.plaf.windows.resources.windows;
import com.sun.xml.internal.ws.api.model.CheckedException;

import dal.EmCommercialInsurance.CI_InsurantClaim_ListDal;

import service.WorkflowCore.WfOperateService;
import sun.org.mozilla.javascript.internal.ast.TryStatement;

import bll.EmCommercialInsurance.CI_InsurantClaim_ListBll;
import bll.EmCommercialInsurance.CI_InsurantClaim_OperateBll;
import bll.EmCommercialInsurance.CI_InsurantClaim_OperateImpl;
import Model.CI_InsurantClaimModel;
import Model.CI_InsurantClaimVistModel;
import Model.CI_Insurant_ListModel;
import Util.FileOperate;
import Util.UserInfo;

public class CI_InsurantClaim_Controller {

	CI_InsurantClaim_ListBll bll = new CI_InsurantClaim_ListBll();
	private CI_InsurantClaim_OperateBll ccsaBll = new CI_InsurantClaim_OperateBll();

	private String eccl_id;
	private String eccv_id;
	private String gid;
	String username = UserInfo.getUsername();

	public CI_InsurantClaim_Controller() {
		if (Executions.getCurrent().getArg().get("gid")!=null) {
			try {
				gid = Executions.getCurrent().getArg().get("gid").toString();
			} catch (Exception e) {
				// TODO: handle exception
				gid = "0";
			}
			
		}else{
			gid = "0";
		}
	}

	// 商保索赔跳转
	@Command("cs_url0")
	public void cs_url0() {
		Executions.sendRedirect("CI_InsurantClaim_AutOutOver.zul");
	}

	@Command("cs_url1")
	public void cs_url1() {
		Executions.sendRedirect("CI_InsurantClaim_AutOut.zul");
	}

	@Command("cs_url2")
	public void cs_url2() {
		Executions.sendRedirect("CI_InsurantClaim_WaitAut.zul");
	}

	@Command("cs_url3")
	public void cs_url3() {
		Executions.sendRedirect("CI_InsurantClaim_Auting.zul");
	}

	@Command("cs_url4")
	public void cs_url4() {
		Executions.sendRedirect("CI_InsurantClaim_AutOver.zul");
	}

	// 商保索赔新增
	@Command("ci_claim_add")
	public void ci_claim_add(@BindingParam("gid") Label gid,
			@BindingParam("cid") Label cid,
			@BindingParam("remark") Textbox remark,
			@BindingParam("ra1") Radio ra1, @BindingParam("ra2") Radio ra2,
			@BindingParam("ra3") Radio ra3,
			@BindingParam("eccl_castsort") Textbox eccl_castsort,
			@BindingParam("name") Textbox name,
			@BindingParam("pay_money") Textbox pay_money,
			@BindingParam("pay_count") Textbox pay_count,
			@BindingParam("t_money") Textbox t_money,
			@BindingParam("t_count") Textbox t_count,
			@BindingParam("fp_count") Textbox fp_count,
			@BindingParam("mobile") Textbox mobile,
			@BindingParam("email") Textbox email,
			@BindingParam("bankname") Textbox bankname,
			@BindingParam("hm") Textbox hm,
			@BindingParam("bankacctid") Textbox bankacctid,
			@BindingParam("ecin_iname") Label ecin_iname) throws Exception {

		String rra1 = "0";
		String rra2 = "0";
		String rra3 = "0";
		if (ra1.isChecked()) {
			rra1 = "1";
		}
		if (ra2.isChecked()) {
			rra2 = "1";
		}
		if (ra3.isChecked()) {
			rra3 = "1";
		}

		if (!eccl_castsort.getValue().equals("")
				&& eccl_castsort.getValue() != null) {
			if (name.getValue().equals("")) {
				Messagebox.show("请录入连带人姓名", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}

			if (pay_money.getValue().equals("")) {
				Messagebox.show("请录入符合金额", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}

			if (fp_count.getValue().equals("")) {
				Messagebox.show("请录入符合发票数量", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}

			if (remark.getValue().equals("")) {
				Messagebox.show("请录入备注信息", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}

			// 数据新增
			String[] message = ccsaBll.add_claim(gid.getValue(),
					cid.getValue(), eccl_castsort.getValue(), name.getValue(),
					pay_money.getValue(), pay_count.getValue(),
					fp_count.getValue(), email.getValue(), mobile.getValue(),
					t_money.getValue(), t_count.getValue(),
					bankname.getValue(), hm.getValue(), bankacctid.getValue(),
					remark.getValue(), rra1, rra2, rra3, ecin_iname.getValue());
			// 弹出提示并跳转页面
			if (message[0].equals("1")) {
				EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.OK.equals(event.getButton())) {

						}
					}
				};

			} else {
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
		Messagebox.show("操作成功！", "操作提示",
				new Messagebox.Button[] { Messagebox.Button.OK },
				Messagebox.INFORMATION, null);
		Executions.sendRedirect("CI_InsurantClaim_AddList.zul");
	}

	// 商保索赔修改
	@Command("ci_claim_edit")
	public void ci_claim_edit(@BindingParam("gid") Label gid,
			@BindingParam("cid") Label cid,
			@BindingParam("remark") Textbox remark,
			@BindingParam("ra1") Radio ra1, @BindingParam("ra2") Radio ra2,
			@BindingParam("ra3") Radio ra3,
			@BindingParam("eccl_castsort") Textbox eccl_castsort,
			@BindingParam("name") Textbox name,
			@BindingParam("pay_money") Textbox pay_money,
			@BindingParam("fp_count") Textbox fp_count,
			@BindingParam("mobile") Textbox mobile,
			@BindingParam("email") Textbox email,
			@BindingParam("bankname") Textbox bankname,
			@BindingParam("hm") Textbox hm,
			@BindingParam("bankacctid") Textbox bankacctid,
			@BindingParam("ecin_iname") Label ecin_iname,
			@BindingParam("eccl_id") Label id) throws Exception {

		String rra1 = "0";
		String rra2 = "0";
		String rra3 = "0";
		if (ra1.isChecked()) {
			rra1 = "1";
		}
		if (ra2.isChecked()) {
			rra2 = "0";
		}
		if (ra3.isChecked()) {
			rra3 = "1";
		}

		if (!eccl_castsort.getValue().equals("")
				&& eccl_castsort.getValue() != null) {
			if (name.getValue().equals("")) {
				Messagebox.show("请录入连带人姓名", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}

			if (pay_money.getValue().equals("")) {
				Messagebox.show("请录入符合金额", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}

			if (fp_count.getValue().equals("")) {
				Messagebox.show("请录入符合发票数量", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}

			if (remark.getValue().equals("")) {
				Messagebox.show("请录入备注信息", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}

			// 数据修改
			String[] message = ccsaBll.edit_claim(gid.getValue(),
					cid.getValue(), eccl_castsort.getValue(), name.getValue(),
					pay_money.getValue(), "", fp_count.getValue(),
					email.getValue(), mobile.getValue(), "", "",
					bankname.getValue(), hm.getValue(), bankacctid.getValue(),
					remark.getValue(), rra1, rra2, rra3, ecin_iname.getValue(),
					id.getValue());
			// 弹出提示并跳转页面
			if (message[0].equals("1")) {
				EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.OK.equals(event.getButton())) {
							// Executions.sendRedirect("Compact_SginList.zul");
							// //跳转页面
							// w1.detach();
							// 弹出提示

						}
					}
				};

			} else {
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
		Messagebox.show("操作成功！", "操作提示",
				new Messagebox.Button[] { Messagebox.Button.OK },
				Messagebox.INFORMATION, null);
		Executions.sendRedirect("CI_InsurantClaim_AutOut.zul");
	}

	// 商保索赔发票新增
	@Command("ci_pidup")
	public void ci_pidadd(@BindingParam("emco") CI_InsurantClaimVistModel emco,
			@BindingParam("eccl_id") Label eccl_id,
			@BindingParam("cl_castsort") Label cl_castsort,
			@BindingParam("w1") final Window w1) throws Exception {

		// 循环判断商保索赔新增
		Map arg = new HashMap();
		arg.put("eccv_id", emco.getEccv_id());
		arg.put("eccl_id", eccl_id.getValue());
		arg.put("eccl_castsort", cl_castsort.getValue());
		arg.put("w1", w1);
		Window wnd = (Window) Executions.createComponents(
				"CI_InsurantClaim_PIDAdd.zul", null, arg);
		w1.detach();
		wnd.doModal();

	}

	// 商保索赔发票新增
	@Command("ci_pidadd")
	public void ci_pidadd(@BindingParam("eccl_castsort") Label eccl_castsort,
			@BindingParam("eccl_id") Label eccl_id,
			@BindingParam("eccv_id") Label eccv_id,
			@BindingParam("em1") Textbox em1, @BindingParam("em2") Textbox em2,
			@BindingParam("em3") Textbox em3, @BindingParam("em4") Textbox em4,
			@BindingParam("em5") Textbox em5, @BindingParam("em6") Textbox em6,
			@BindingParam("em7") Textbox em7, @BindingParam("em8") Textbox em8,
			@BindingParam("em9") Textbox em9,
			@BindingParam("em10") Textbox em10,
			@BindingParam("em11") Textbox em11,
			@BindingParam("em12") Textbox em12,
			@BindingParam("em13") Textbox em13,
			@BindingParam("em14") Textbox em14,
			@BindingParam("em15") Textbox em15,
			@BindingParam("em16") Textbox em16,
			@BindingParam("em17") Textbox em17,

			@BindingParam("w2") final Window w2) throws Exception {

		if (!em1.getValue().equals("")) {
			// 备注新增
			String[] message = bll.pid_add(eccl_id.getValue(),
					eccv_id.getValue(), em1.getValue(), em2.getValue(),
					em3.getValue(), em4.getValue(), em5.getValue(),
					em6.getValue(), em7.getValue(), em8.getValue(),
					em9.getValue(), em10.getValue(), em11.getValue(),
					em12.getValue(), em13.getValue(), em14.getValue(),
					em15.getValue(), em16.getValue(), em17.getValue());

			if (message[0].equals("1")) {
				Messagebox
						.show("操作成功！", "操作提示", Messagebox.OK, Messagebox.NONE);

				w2.detach();
				Map arg = new HashMap();
				arg.put("daid", eccl_id.getValue());
				arg.put("eccl_castsort", eccl_castsort.getValue());
				Window wnd = (Window) Executions.createComponents(
						"CI_InsurantClaim_ECCVAdd.zul", null, arg);
				wnd.doModal();

			} else {
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox.show("发票号码不能为空！", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	private CI_InsurantClaimModel ci_base;// 获取商保基本信息

	private CI_InsurantClaimModel ci_state;// 获取商保理赔数据统计

	private ListModelList<CI_InsurantClaimModel> ci_castsort_inbase;// 获取商保在保类型

	private ListModelList<CI_InsurantClaimModel> ci_name;// 获取商保在保类型

	private ListModelList<CI_InsurantClaimModel> ci_claim_Wtlist;// 获取商保在保类型

	private ListModelList<CI_InsurantClaimModel> ci_claim_Wtoutlist;// 获取商保在保类型

	private ListModelList<CI_InsurantClaimModel> ci_claim_Wtoutoverlist;// 获取商保在保类型

	private ListModelList<CI_InsurantClaimModel> ci_claim_autlist;// 获取商保索赔处理中数据

	private ListModelList<CI_InsurantClaimModel> ci_claim_remarklist;// 获取商保索赔备注

	private ListModelList<CI_InsurantClaimModel> ci_castsort_class;// 获取商保索赔类型

	private ListModelList<CI_InsurantClaimModel> ci_class_l;// 获取商保索赔类型关联

	private ListModelList<CI_InsurantClaimVistModel> ci_claim_vistlist;// 获取商保索赔类型关联

	private ListModelList<CI_InsurantClaimVistModel> ci_claim_pidlist;// 获取商保索赔类型关联

	private ListModelList<CI_InsurantClaimModel> ci_claim_autoverlist;// 获取商保索赔已赔付数据

	private ListModelList<CI_InsurantClaimModel> pf_list;// 获取商保在保类型

	private ListModelList<CI_InsurantClaimModel> ci_claim_chlist;// 获取商保索赔查询

	private ListModelList<CI_InsurantClaimModel> ci_claim_down;// 获取商保索赔查询

	private ListModelList<CI_InsurantClaimModel> ci_insurant_claddlist;// 获取商保索赔新增列表

	// 根据所选类型列出索赔员工姓名
	@Command("ci_name")
	@NotifyChange("ci_name")
	public void ci_name(@BindingParam("gridco") Grid gridco,
			@BindingParam("gid") Label gid) throws Exception {
		for (int i = 0; i < gridco.getRows().getChildren().size(); i++) {
			Grid gd = (Grid) gridco.getCell(i, 0);
			Combobox em1 = (Combobox) gd.getCell(0, 1).getChildren().get(0);
			ci_name = new ListModelList<CI_InsurantClaimModel>(bll.ci_name(
					gid.getValue(), em1.getValue()));// 获取商保姓名
		}
	}

	// 获取商保索赔类型关联
	@Command("ci_class_classl")
	@NotifyChange("ci_class_l")
	public void ci_class_classl(@BindingParam("cl_class") Combobox cl_class)
			throws Exception {
		ci_class_l = new ListModelList<CI_InsurantClaimModel>(
				bll.ci_class_l(cl_class.getSelectedItem().getValue().toString()));// 获取商保索赔类型
	}

	// 待处理操作
	@Command("ci_claim_aut")
	public void ci_claim_aut(@BindingParam("gridco") Grid gridco)
			throws Exception {
		int j = 0;
		String chkall = "";
		String chk_taprall = "";
		for (int i = 0; i < gridco.getRows().getChildren().size(); i++) {
			Checkbox chk = (Checkbox) gridco.getCell(i, 11).getChildren()
					.get(0);
			Label tapr_chk = (Label) gridco.getCell(i, 12).getChildren().get(0);
			if (chk.isChecked()) {
				chkall = chkall + chk.getValue() + ",";
				chk_taprall = chk_taprall + tapr_chk.getValue() + ",";
			}
		}

		Map arg = new HashMap();
		arg.put("daid", chkall);
		arg.put("id", chk_taprall);
		Window wnd = (Window) Executions.createComponents(
				"CI_InsurantClaim_WaitUp.zul", null, arg);
		wnd.doModal();
	}

	// 删除操作
	@Command("ci_claim_del")
	public void ci_claim_del(@BindingParam("emco") CI_InsurantClaimModel emco)
			throws Exception {
		String[] message = new String[2];
		try {
			System.out.println("del");
			System.out.println(emco.getEccl_id());
			System.out.println(emco.getEccl_tapr_id());
			Object[] obj = { "6", (int) emco.getEccl_id() };
			// 执行工作流

			WfOperateService wf = new WfOperateImpl(
					new CI_InsurantClaim_OperateImpl());
			message = wf.StopTask(obj,
					Integer.parseInt(emco.getEccl_tapr_id()), username,
					"商业保险理赔删除");

		} catch (Exception e) {

		}

		Messagebox.show("删除成功！", "操作提示",
				new Messagebox.Button[] { Messagebox.Button.OK },
				Messagebox.INFORMATION, null);
		Executions.sendRedirect("CI_InsurantClaim_WaitAut.zul");
	}

	// 初审退回提交
	@Command("ci_claim_outaut")
	public void ci_claim_outaut(@BindingParam("gridco") Grid gridco)
			throws Exception {

		for (int i = 0; i < gridco.getRows().getChildren().size(); i++) {
			Checkbox chk = (Checkbox) gridco.getCell(i, 11).getChildren()
					.get(0);
			if (chk.isChecked()) {
				try {

					Object[] obj = { "4", (int) chk.getValue() };
					// 执行工作流

					WfOperateService wf = new WfOperateImpl(
							new CI_InsurantClaim_OperateImpl());
					wf.AddTaskToNext(obj, "商业保险", gid + "--商保理赔新增", 47,
							username, "", 0, "");

				} catch (Exception e) {

				}
			}
			// cl_dal.getci_outaut((int) chk.getValue());
		}
		Messagebox.show("操作成功！", "操作提示",
				new Messagebox.Button[] { Messagebox.Button.OK },
				Messagebox.INFORMATION, null);
		Executions.sendRedirect("CI_InsurantClaim_AutOut.zul");
	}

	// 处理中操作
	@Command("ci_claim_autup")
	public void ci_claim_autup(@BindingParam("gridco") Grid gridco)
			throws Exception {
		int j = 0;
		String chkall = "";
		String chk_taprall = "";
		for (int i = 0; i < gridco.getRows().getChildren().size(); i++) {
			Checkbox chk = (Checkbox) gridco.getCell(i, 14).getChildren()
					.get(0);
			Label tapr_chk = (Label) gridco.getCell(i, 15).getChildren().get(0);
			if (chk.isChecked()) {
				chkall = chkall + chk.getValue() + ",";
				chk_taprall = chk_taprall + tapr_chk.getValue() + ",";
			}
		}

		Map arg = new HashMap();
		arg.put("daid", chkall);
		arg.put("id", chk_taprall);
		Window wnd = (Window) Executions.createComponents(
				"CI_InsurantClaim_AutingUp.zul", null, arg);
		wnd.doModal();

	}

	// 初审退回提交
	@Command("ci_claim_outup")
	public void ci_claim_outup(@BindingParam("emco") CI_InsurantClaimModel emco)
			throws Exception {

		Map arg = new HashMap();
		arg.put("eccl_id", emco.getEccl_id());
		arg.put("gid", emco.getGid());
		arg.put("cid", emco.getCid());
		arg.put("eccl_castsort", emco.getEccl_castsort());
		arg.put("eccl_insurant", emco.getEccl_insurant());
		arg.put("eccl_pay_money", emco.getEccl_pay_money());
		arg.put("eccl_invoice_count", emco.getEccl_invoice_count());
		Window wnd = (Window) Executions.createComponents(
				"CI_InsurantClaim_OutUp.zul", null, arg);
		wnd.doModal();

	}

	@Command("ci_claim_waitup")
	public void ci_claim_waitup(@BindingParam("eccl_id") Label eccl_id,
			@BindingParam("eccl_tapr_id") Label eccl_tapr_id,
			@BindingParam("bx_date") Datebox bx_date,
			@BindingParam("bx_name") Textbox bx_name) throws Exception {
		String all_eccl = eccl_id.getValue();
		String[] a = all_eccl.split(",");
		String s = eccl_tapr_id.getValue();
		String[] ss = s.split(",");
		String bx_datein = "";

		if (bx_date.getValue().equals("")) {
			Messagebox.show("请录入交保险公司时间", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		if (bx_name.getValue().equals("")) {
			Messagebox.show("请录入保险公司签收人", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		if (bx_date.getValue() != null) {
			bx_datein = ccsaBll.DatetoSting(bx_date.getValue());
		}
		int i = -1;
		for (String t : a) {
			i = i + 1;
			if (!t.equals("") && t != null) {
				// 数据新增
				String[] message = ccsaBll.waitaut_claim(Integer.parseInt(t),
						Integer.parseInt(ss[i]), bx_datein, bx_name.getValue());
				// 弹出提示并跳转页面
				if (message[0].equals("1")) {
					EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
						public void onEvent(ClickEvent event) throws Exception {
							if (Messagebox.Button.OK.equals(event.getButton())) {
								// Executions.sendRedirect("Compact_SginList.zul");
								// //跳转页面
								// w1.detach();
							}
						}
					};

				} else {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		}
		// 弹出提示
		Messagebox.show("操作成功！", "操作提示",
				new Messagebox.Button[] { Messagebox.Button.OK },
				Messagebox.INFORMATION, null);
		Executions.sendRedirect("CI_InsurantClaim_WaitAut.zul");
	}

	@Command("ci_claim_auting")
	public void ci_claim_auting(@BindingParam("eccl_id") Label eccl_id,
			@BindingParam("eccl_tapr_id") Label eccl_tapr_id,
			@BindingParam("bx_date") Datebox bx_date,
			@BindingParam("fl_date") Datebox fl_date) throws Exception {
		String all_eccl = eccl_id.getValue();
		String[] a = all_eccl.split(",");
		String s = eccl_tapr_id.getValue();
		String[] ss = s.split(",");
		String bx_datein = "";
		if (bx_date.getValue() != null) {
			bx_datein = ccsaBll.DatetoSting(bx_date.getValue());
		}
		String fl_datein = "";
		if (fl_date.getValue() != null) {
			fl_datein = ccsaBll.DatetoSting(fl_date.getValue());
		}
		int i = -1;
		for (String t : a) {
			i = i + 1;
			if (!t.equals("") && t != null) {
				// 数据新增
				String[] message = ccsaBll.autup_claim(Integer.parseInt(t),
						Integer.parseInt(ss[i]), bx_datein, fl_datein);
				// 弹出提示并跳转页面
				if (message[0].equals("1")) {
					EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
						public void onEvent(ClickEvent event) throws Exception {
							if (Messagebox.Button.OK.equals(event.getButton())) {
								// Executions.sendRedirect("Compact_SginList.zul");
								// //跳转页面
								// w1.detach();
							}
						}
					};

				} else {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		}
		// 弹出提示
		Messagebox.show("操作成功！", "操作提示",
				new Messagebox.Button[] { Messagebox.Button.OK },
				Messagebox.INFORMATION, null);
		Executions.sendRedirect("CI_InsurantClaim_Auting.zul");
	}

	@Command("remark_add")
	public void remark_add(@BindingParam("eccl_id") Label eccl_id,
			@BindingParam("email") Textbox email,
			@BindingParam("mobile") Textbox mobile,
			@BindingParam("content") Textbox content,
			@BindingParam("a1") Checkbox a1, @BindingParam("a2") Checkbox a2,
			@BindingParam("a3") Checkbox a3, @BindingParam("w1") final Window w1)
			throws Exception {
		String aa1 = "0";
		String aa2 = "0";
		String aa3 = "0";
		if (a1.isChecked()) {
			aa1 = "1";
		}
		if (a2.isChecked()) {
			aa2 = "1";
		}
		if (a3.isChecked()) {
			aa3 = "1";
		}
		// 备注新增
		String[] message = bll.remark_add(eccl_id.getValue(), email.getValue(),
				mobile.getValue(), content.getValue(), aa1, aa2, aa3);

		if (message[0].equals("1")) {
			Messagebox.show("操作成功！", "操作提示", Messagebox.OK, Messagebox.NONE);
			w1.detach();
		} else {
			// 弹出提示
			Messagebox
					.show(message[1], "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Command("vist_add")
	public void vist_add(@BindingParam("eccl_id") Label eccl_id,
			@BindingParam("cl_castsort") Label cl_castsort,
			@BindingParam("jz_date") Datebox jz_date,
			@BindingParam("jz_host") Textbox jz_host,
			@BindingParam("jz_content") Textbox jz_content,
			@BindingParam("cl_class") Combobox cl_class,
			@BindingParam("cl_class_la") Combobox cl_class_la,
			@BindingParam("sk_remark") Textbox sk_remark,
			@BindingParam("w1") final Window w1) throws Exception {

		String jdd_date = "";
		if (jz_date.getValue() != null) {
			jdd_date = ccsaBll.DatetoSting(jz_date.getValue());
		}

		// 备注新增
		String[] message = bll.vist_add(eccl_id.getValue(),
				cl_castsort.getValue(), jdd_date, jz_host.getValue(),
				jz_content.getValue(), sk_remark.getValue(),
				cl_class_la.getValue(), cl_class.getSelectedItem().getValue()
						.toString());

		if (message[0].equals("1")) {
			Messagebox.show("操作成功！", "操作提示", Messagebox.OK, Messagebox.NONE);
			w1.detach();

			Map arg = new HashMap();
			arg.put("daid", eccl_id.getValue());
			arg.put("eccl_castsort", cl_castsort.getValue());
			Window wnd = (Window) Executions.createComponents(
					"CI_InsurantClaim_ECCVAdd.zul", null, arg);
			wnd.doModal();

		} else {
			// 弹出提示
			Messagebox
					.show(message[1], "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 商保理赔结果添加
	@Command("ci_pidoveradd")
	public void ci_pidoveradd(@BindingParam("g1") Button g1,
			@BindingParam("eccv_id") Label eccv_id,
			@BindingParam("eccl_id") Label eccl_id,
			@BindingParam("eccl_castsort") Label eccl_castsort,
			@BindingParam("emco") CI_InsurantClaimVistModel emco,
			@BindingParam("w1") final Window w1) throws Exception {
		Component com = g1.getParent().getParent();
		Row row = (Row) com;
		String[] message = bll.ci_overadd(((Label) row.getChildren().get(20)
				.getChildren().get(0)).getValue(), ((Textbox) row.getChildren()
				.get(1).getChildren().get(0)).getValue(), ((Textbox) row
				.getChildren().get(2).getChildren().get(0)).getValue(),
				((Combobox) row.getChildren().get(3).getChildren().get(0))
						.getValue(), ((Textbox) row.getChildren().get(4)
						.getChildren().get(0)).getValue(), ((Textbox) row
						.getChildren().get(5).getChildren().get(0)).getValue(),
				((Textbox) row.getChildren().get(6).getChildren().get(0))
						.getValue(), ((Textbox) row.getChildren().get(7)
						.getChildren().get(0)).getValue(), ((Textbox) row
						.getChildren().get(8).getChildren().get(0)).getValue(),
				((Textbox) row.getChildren().get(9).getChildren().get(0))
						.getValue(), ((Textbox) row.getChildren().get(10)
						.getChildren().get(0)).getValue(),
				((Textbox) row.getChildren().get(11).getChildren().get(0))
						.getValue(), ((Textbox) row.getChildren().get(12)
						.getChildren().get(0)).getValue(),
				((Textbox) row.getChildren().get(13).getChildren().get(0))
						.getValue(), ((Textbox) row.getChildren().get(14)
						.getChildren().get(0)).getValue(),
				((Textbox) row.getChildren().get(15).getChildren().get(0))
						.getValue(), ((Textbox) row.getChildren().get(16)
						.getChildren().get(0)).getValue(),
				((Textbox) row.getChildren().get(17).getChildren().get(0))
						.getValue(), ((Textbox) row.getChildren().get(18)
						.getChildren().get(0)).getValue(), eccl_id.getValue());

		if (message[0].equals("1")) {
			Messagebox.show("操作成功！", "操作提示", Messagebox.OK, Messagebox.NONE);
			w1.detach();

			Map arg = new HashMap();
			arg.put("daid", eccl_id.getValue());
			// arg.put("eccl_castsort", emco.getEccl_castsort());
			Window wnd = (Window) Executions.createComponents(
					"CI_InsurantClaim_AutOverUp.zul", null, arg);
			wnd.doModal();

		} else {
			// 弹出提示
			Messagebox
					.show(message[1], "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 商保审核查询
	@Command("ch_search")
	@NotifyChange("ci_claim_chlist")
	public void ch_search(@BindingParam("pf_date") Combobox pf_date,
			@BindingParam("state") Combobox state,
			@BindingParam("name") Textbox name,
			@BindingParam("pd_id") Textbox pd_id) throws Exception {

		ci_claim_chlist = new ListModelList<CI_InsurantClaimModel>(
				bll.ci_claim_chlist(pf_date.getValue(), state.getValue(),
						name.getValue(), pd_id.getValue()));// 显示查询信息
	}

	// 商保审核查询
	@Command("d2_search")
	@NotifyChange("ci_claim_Wtlist")
	public void d2_search(@BindingParam("company") Combobox company)
			throws Exception {

		ci_claim_Wtlist = new ListModelList<CI_InsurantClaimModel>(
				bll.ci_claim_Wtlist(company.getValue()));// 获取商保在保类型
	}

	@Init
	public void init() throws SQLException {
		try {
			eccl_id = String.valueOf(Executions.getCurrent().getArg()
					.get("daid").toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		ci_base = bll.ci_base(gid);// 获取商保基本信息
		// setCi_base((CI_InsurantClaimModel) bll.ci_base());

		ci_state = bll.ci_state();// 获取商保理赔数据统计

		ci_castsort_inbase = new ListModelList<CI_InsurantClaimModel>(
				bll.ci_castsort_inbase());// 获取商保在保类型

		ci_claim_Wtlist = new ListModelList<CI_InsurantClaimModel>(
				bll.ci_claim_Wtlist("全部"));// 获取商保在保类型

		ci_claim_Wtoutlist = new ListModelList<CI_InsurantClaimModel>(
				bll.ci_claim_Wtoutlist(""));// 获取商保理赔初审退回数据

		ci_claim_Wtoutoverlist = new ListModelList<CI_InsurantClaimModel>(
				bll.ci_claim_Wtoutoverlist(""));// 获取商保理赔终止退回数据

		ci_claim_autlist = new ListModelList<CI_InsurantClaimModel>(
				bll.ci_claim_autlist(""));// 获取商保索赔处理中数据

		ci_claim_remarklist = new ListModelList<CI_InsurantClaimModel>(
				bll.ci_claim_remarklist(eccl_id));// 获取商保索赔备注

		ci_castsort_class = new ListModelList<CI_InsurantClaimModel>(
				bll.ci_castsort_class(eccl_id));// 获取商保索赔类型

		ci_claim_vistlist = new ListModelList<CI_InsurantClaimVistModel>(
				bll.ci_claim_vistlist(eccl_id));// 获取商保索赔就诊列表

		ci_claim_autoverlist = new ListModelList<CI_InsurantClaimModel>(
				bll.ci_claim_autoverlist(""));// 获取商保索赔已理赔数据

		pf_list = new ListModelList<CI_InsurantClaimModel>(bll.pf_list());// 获取商保赔付时间

		ci_claim_chlist = new ListModelList<CI_InsurantClaimModel>(
				bll.ci_claim_chlist("", "", "", ""));// 获取商保索赔查询

		ci_insurant_claddlist = new ListModelList<CI_InsurantClaimModel>(
				bll.ci_insurant_claddlist("", ""));// 获取商保索赔就诊列表
	}

	// 理赔新增查询
	@Command("cladd_search")
	@NotifyChange("ci_insurant_claddlist")
	public void cladd_search(@BindingParam("name") Textbox name,
			@BindingParam("idcard") Textbox idcard) throws Exception {
		ci_insurant_claddlist = new ListModelList<CI_InsurantClaimModel>(
				bll.ci_insurant_claddlist(name.getValue(), idcard.getValue()));// 获取商保索赔就诊列表
	}

	// 理赔处理中查询
	@Command("claut_search")
	@NotifyChange("ci_claim_autlist")
	public void claut_search(@BindingParam("name") Textbox name)
			throws Exception {
		ci_claim_autlist = new ListModelList<CI_InsurantClaimModel>(
				bll.ci_claim_autlist(name.getValue()));// 获取商保索赔处理中数据
	}

	// 理赔已理赔查询
	@Command("clover_search")
	@NotifyChange("ci_claim_autoverlist")
	public void clover_search(@BindingParam("name") Textbox name)
			throws Exception {
		ci_claim_autoverlist = new ListModelList<CI_InsurantClaimModel>(
				bll.ci_claim_autoverlist(name.getValue()));// 获取商保索赔已理赔数据
	}

	// 理赔理赔待处理查询
	@Command("clwait_search")
	@NotifyChange("ci_claim_Wtlist")
	public void clwait_search(@BindingParam("name") Textbox name)
			throws Exception {
		ci_claim_Wtlist = new ListModelList<CI_InsurantClaimModel>(
				bll.ci_claim_Wtlist(name.getValue()));// 获取商保在保类型
	}

	// 理赔理赔初审退回查询
	@Command("clcs_search")
	@NotifyChange("ci_claim_Wtoutlist")
	public void clcs_search(@BindingParam("name") Textbox name)
			throws Exception {
		ci_claim_Wtoutlist = new ListModelList<CI_InsurantClaimModel>(
				bll.ci_claim_Wtoutlist(name.getValue()));// 获取商保理赔初审退回数据
	}

	// 添加备注
	@Command("ci_claim_remark")
	public void ci_claim_remark(@BindingParam("emco") CI_InsurantClaimModel emco)
			throws Exception {
		Map arg = new HashMap();
		arg.put("daid", emco.getEccl_id());
		Window wnd = (Window) Executions.createComponents(
				"CI_InsurantClaim_Remark.zul", null, arg);
		wnd.doModal();
	}

	// 待处理操作
	@Command("ci_claim_pidadd")
	public void ci_claim_pidadd(@BindingParam("emco") CI_InsurantClaimModel emco)
			throws Exception {
		Map arg = new HashMap();
		arg.put("daid", emco.getEccl_id());
		arg.put("eccl_castsort", emco.getEccl_castsort());
		Window wnd = (Window) Executions.createComponents(
				"CI_InsurantClaim_ECCVAdd.zul", null, arg);
		wnd.doModal();
	}

	// 处理发票明细
	@Command("ci_claim_pidup")
	public void ci_claim_pidup(@BindingParam("emco") CI_InsurantClaimModel emco)
			throws Exception {
		Map arg = new HashMap();
		arg.put("daid", emco.getEccl_id());
		arg.put("eccl_castsort", emco.getEccl_castsort());
		Window wnd = (Window) Executions.createComponents(
				"CI_InsurantClaim_AutOverUp.zul", null, arg);
		wnd.doModal();
	}

	// 新增理赔
	@Command("ci_insurant_cladd")
	public void ci_insurant_cladd(
			@BindingParam("emco") CI_InsurantClaimModel emco) throws InterruptedException {
		Map arg = new HashMap();
		arg.put("gid", emco.getGid());
		arg.put("cid", emco.getCid());
		arg.put("eccl_castsort", emco.getEccl_castsort());
		arg.put("eccl_insurant", emco.getEccl_insurant());
		arg.put("eccl_insurer", emco.getEccl_insurer());
		Window wnd = (Window) Executions.createComponents(
				"CI_InsurantClaim_Add.zul", null, arg);
		wnd.doModal();
	}

	@Command("checkall")
	public void checkall(@BindingParam("a") boolean ck,
			@BindingParam("b") Grid gd) {

		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			try {
				Checkbox ckb = (Checkbox) gd.getCell(i, 11).getChildren()
						.get(0);
				ckb.setChecked(ck);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

	@Command("checkautall")
	public void checkautall(@BindingParam("a") boolean ck,
			@BindingParam("b") Grid gd) {

		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			try {
				Checkbox ckb = (Checkbox) gd.getCell(i, 14).getChildren()
						.get(0);
				ckb.setChecked(ck);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

	// 导入理赔明细
	@Command("ci_insurant_up")
	public void ci_insurant_up(@BindingParam("gridco") Grid gridco)
			throws Exception {
		Map arg = new HashMap();
		// arg.put("daid", emco.getEbco_id());
		arg.put("cid", 1);
		arg.put("ownmonth", 1);
		arg.put("type", 1);
		Window wnd = (Window) Executions.createComponents(
				"CI_InsurantClaim_AutUpload.zul", null, arg);
		wnd.doModal();
	}

	// 导出报表
	@Command("ci_insurant_down")
	public void ci_insurant_down(@BindingParam("gridco") Grid gridco)
			throws Exception {

		String filename = ""; // 文件名
		String absolutePath; // 服务器地址
		String filetype = ".xls"; // 文件类型
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String nowtime = sdf.format(date);

		filename = nowtime + filetype;
		absolutePath = FileOperate.getAbsolutePath();

		// 创建exce
		// 读取Excel模板
		Workbook wb = Workbook.getWorkbook(new File(absolutePath
				+ "EmCommercialInsurance/File/Templet/ci_claims.xls"));
		// 使用模板创建
		WritableWorkbook wwb = Workbook.createWorkbook(new File(absolutePath
				+ "EmCommercialInsurance/File/Download/Em_cl/" + filename), wb);
		// 生成工作表
		WritableSheet sheet = wwb.getSheet(0);
		// Label label1 = null;
		int rowdata = 2;
		String pid = "";
		for (int i = 0; i < gridco.getRows().getChildren().size(); i++) {
			Checkbox chk = (Checkbox) gridco.getCell(i, 11).getChildren()
					.get(0);

			if (chk.isChecked()) {
				if (i < gridco.getRows().getChildren().size())
					ci_claim_down = new ListModelList<CI_InsurantClaimModel>(
							bll.ci_claim_down(chk.getValue().toString()));// 获取商保索赔查询
				rowdata = rowdata + 1;
				pid = String.valueOf(rowdata - 2);
				jxl.write.Label label0 = new jxl.write.Label(0, rowdata, pid);
				sheet.addCell(label0);

				jxl.write.Label label1 = new jxl.write.Label(1, rowdata,
						ci_claim_down.get(0).getEccl_insurant());
				sheet.addCell(label1);

				jxl.write.Label label2 = new jxl.write.Label(2, rowdata,
						ci_claim_down.get(0).getIdcard());
				sheet.addCell(label2);

				jxl.write.Label label3 = new jxl.write.Label(3, rowdata,
						ci_claim_down.get(0).getEccl_account());
				sheet.addCell(label3);

				jxl.write.Label label4 = new jxl.write.Label(4, rowdata,
						ci_claim_down.get(0).getEccl_bankacctid());
				sheet.addCell(label4);

				jxl.write.Label label5 = new jxl.write.Label(5, rowdata,
						ci_claim_down.get(0).getEccl_bankname());
				sheet.addCell(label5);

				jxl.write.Label label6 = new jxl.write.Label(6, rowdata,
						ci_claim_down.get(0).getEccl_insurer());
				sheet.addCell(label6);

				jxl.write.Label label7 = new jxl.write.Label(7, rowdata,
						ci_claim_down.get(0).getIdcard());
				sheet.addCell(label7);

				jxl.write.Label label8 = new jxl.write.Label(8, rowdata,
						ci_claim_down.get(0).getEccl_pay_money());
				sheet.addCell(label8);

				jxl.write.Label label9 = new jxl.write.Label(9, rowdata,
						ci_claim_down.get(0).getEccl_invoice_count());
				sheet.addCell(label9);

				jxl.write.Label label10 = new jxl.write.Label(10, rowdata, "");
				sheet.addCell(label10);

				jxl.write.Label label11 = new jxl.write.Label(11, rowdata,
						ci_claim_down.get(0).getGid());
				sheet.addCell(label11);

				jxl.write.Label label12 = new jxl.write.Label(12, rowdata,
						ci_claim_down.get(0).getEccl_email_change());
				sheet.addCell(label12);
			}
		}

		// 写入数据
		wwb.write();
		// 关闭文件
		wwb.close();

		FileOperate.download("EmCommercialInsurance/File/Download/Em_cl/"
				+ filename);
		// 弹出提示
		Messagebox.show("操作成功！", "操作提示",
				new Messagebox.Button[] { Messagebox.Button.OK },
				Messagebox.INFORMATION, null);
		Executions.sendRedirect("CI_InsurantClaim_WaitAut.zul");

	}

	public CI_InsurantClaimModel getCi_base() {
		return ci_base;
	}

	public void setCi_base(CI_InsurantClaimModel ci_base) {
		this.ci_base = ci_base;
	}

	public ListModelList<CI_InsurantClaimModel> getCi_castsort_inbase() {
		return ci_castsort_inbase;
	}

	public void setCi_castsort_inbase(
			ListModelList<CI_InsurantClaimModel> ci_castsort_inbase) {
		this.ci_castsort_inbase = ci_castsort_inbase;
	}

	public ListModelList<CI_InsurantClaimModel> getCi_name() {
		return ci_name;
	}

	public void setCi_name(ListModelList<CI_InsurantClaimModel> ci_name) {
		this.ci_name = ci_name;
	}

	public ListModelList<CI_InsurantClaimModel> getCi_claim_Wtlist() {
		return ci_claim_Wtlist;
	}

	public void setCi_claim_Wtlist(
			ListModelList<CI_InsurantClaimModel> ci_claim_Wtlist) {
		this.ci_claim_Wtlist = ci_claim_Wtlist;
	}

	public ListModelList<CI_InsurantClaimModel> getCi_claim_autlist() {
		return ci_claim_autlist;
	}

	public void setCi_claim_autlist(
			ListModelList<CI_InsurantClaimModel> ci_claim_autlist) {
		this.ci_claim_autlist = ci_claim_autlist;
	}

	public ListModelList<CI_InsurantClaimModel> getCi_claim_remarklist() {
		return ci_claim_remarklist;
	}

	public void setCi_claim_remarklist(
			ListModelList<CI_InsurantClaimModel> ci_claim_remarklist) {
		this.ci_claim_remarklist = ci_claim_remarklist;
	}

	public ListModelList<CI_InsurantClaimModel> getCi_castsort_class() {
		return ci_castsort_class;
	}

	public void setCi_castsort_class(
			ListModelList<CI_InsurantClaimModel> ci_castsort_class) {
		this.ci_castsort_class = ci_castsort_class;
	}

	public ListModelList<CI_InsurantClaimModel> getCi_class_l() {
		return ci_class_l;
	}

	public void setCi_class_l(ListModelList<CI_InsurantClaimModel> ci_class_l) {
		this.ci_class_l = ci_class_l;
	}

	public ListModelList<CI_InsurantClaimVistModel> getCi_claim_vistlist() {
		return ci_claim_vistlist;
	}

	public void setCi_claim_vistlist(
			ListModelList<CI_InsurantClaimVistModel> ci_claim_vistlist) {
		this.ci_claim_vistlist = ci_claim_vistlist;
	}

	public ListModelList<CI_InsurantClaimModel> getCi_claim_autoverlist() {
		return ci_claim_autoverlist;
	}

	public void setCi_claim_autoverlist(
			ListModelList<CI_InsurantClaimModel> ci_claim_autoverlist) {
		this.ci_claim_autoverlist = ci_claim_autoverlist;
	}

	public ListModelList<CI_InsurantClaimModel> getPf_list() {
		return pf_list;
	}

	public void setPf_list(ListModelList<CI_InsurantClaimModel> pf_list) {
		this.pf_list = pf_list;
	}

	public ListModelList<CI_InsurantClaimModel> getCi_claim_Wtoutlist() {
		return ci_claim_Wtoutlist;
	}

	public void setCi_claim_Wtoutlist(
			ListModelList<CI_InsurantClaimModel> ci_claim_Wtoutlist) {
		this.ci_claim_Wtoutlist = ci_claim_Wtoutlist;
	}

	public ListModelList<CI_InsurantClaimModel> getCi_insurant_claddlist() {
		return ci_insurant_claddlist;
	}

	public void setCi_insurant_claddlist(
			ListModelList<CI_InsurantClaimModel> ci_insurant_claddlist) {
		this.ci_insurant_claddlist = ci_insurant_claddlist;
	}

	public CI_InsurantClaimModel getCi_state() {
		return ci_state;
	}

	public void setCi_state(CI_InsurantClaimModel ci_state) {
		this.ci_state = ci_state;
	}

	public ListModelList<CI_InsurantClaimModel> getCi_claim_chlist() {
		return ci_claim_chlist;
	}

	public void setCi_claim_chlist(
			ListModelList<CI_InsurantClaimModel> ci_claim_chlist) {
		this.ci_claim_chlist = ci_claim_chlist;
	}

	public ListModelList<CI_InsurantClaimModel> getCi_claim_Wtoutoverlist() {
		return ci_claim_Wtoutoverlist;
	}

	public void setCi_claim_Wtoutoverlist(
			ListModelList<CI_InsurantClaimModel> ci_claim_Wtoutoverlist) {
		this.ci_claim_Wtoutoverlist = ci_claim_Wtoutoverlist;
	}

}
