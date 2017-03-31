package Controller.EmPay;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import Controller.systemWindowController;
import Model.EmPay_BaseListModel;
import Model.EmPay_ListModel;
import bll.EmBenefit.EmActy_GiftInfoOperateBll;
import bll.EmPay.EmPay_BaseListBll;
import bll.EmPay.EmPay_OperateBll;

public class EmPay_BaseController {
	EmPay_BaseListBll bll = new EmPay_BaseListBll();
	private EmPay_OperateBll payBll = new EmPay_OperateBll();
	private EmActy_GiftInfoOperateBll cbll= new EmActy_GiftInfoOperateBll();
	private ListModelList<EmPay_BaseListModel> empay_baselist;// 显示员工列表

	private ListModelList<EmPay_BaseListModel> copay_baselist;// 显示公司列表

	private ListModelList<EmPay_BaseListModel> empay_feelist;// 显示员工列表

	private ListModelList<EmPay_BaseListModel> copay_feelist;// 显示公司列表

	@Init
	public void init() throws SQLException {
		try {
			empay_baselist = new ListModelList<EmPay_BaseListModel>(
					bll.empay_baselist(Executions.getCurrent().getArg()
							.get("id").toString()));// 显示员工列表
		} catch (Exception e) {
			empay_baselist = null;
		}

		try {
			copay_baselist = new ListModelList<EmPay_BaseListModel>(
					bll.copay_baselist(Executions.getCurrent().getArg()
							.get("ownmonth").toString(),Executions.getCurrent().getArg()
							.get("paynum").toString(),Executions.getCurrent().getArg()
							.get("stype").toString()));// 显示公司列表
		} catch (Exception e) {
			copay_baselist = null;
		}

		try {
			empay_feelist = new ListModelList<EmPay_BaseListModel>(
					bll.empay_feelist("", "", "", ""));// 显示员工列表
		} catch (Exception e) {
			empay_feelist = null;
		}

		try {
			copay_feelist = new ListModelList<EmPay_BaseListModel>(
					bll.copay_feelist("", "", "", ""));// 显示公司列表
		} catch (Exception e) {
			copay_feelist = null;
		}
	}

	// 显示员工费用明细
	@Command("feelist")
	@NotifyChange("empay_feelist")
	public void empay_feelist(@BindingParam("emco") EmPay_BaseListModel emco)
			throws Exception {
		empay_feelist = new ListModelList<EmPay_BaseListModel>(
				bll.empay_feelist(emco.getEmsp_id(), emco.getGid(),
						emco.getOwnmonth(), emco.getEspa_type()));// 显示员工列表

	}
	
	// 显示公司费用明细
		@Command("cfeelist")
		@NotifyChange("copay_feelist")
		public void copay_feelist(@BindingParam("emco") EmPay_BaseListModel emco)
				throws Exception {
			copay_feelist = new ListModelList<EmPay_BaseListModel>(
					bll.copay_feelist(emco.getCid(),emco.getOwnmonth(), emco.getEspa_paynum(),emco.getEspa_type()));// 显示公司明细

		}

	// 支付财务审核
	@Command("aut_ok")
	public void aut_ok(@BindingParam("espa_id") Label espa_id,
			@BindingParam("espa_tapr_id") Label espa_tapr_id) throws Exception {
		// bll.empay_autok(ownmonth.getValue(), paynum.getValue());// 审核支付通知
		String[] message = payBll.aut_pay(Integer.parseInt(espa_id.getValue()),
				Integer.parseInt(espa_tapr_id.getValue()));
		// 弹出提示
		Messagebox.show("操作成功！", "操作提示",
				new Messagebox.Button[] { Messagebox.Button.OK },
				Messagebox.INFORMATION, null);
		Executions.sendRedirect("/EmPay/EmPay_Aut.zul");
	}
	
	 

	// 支付总经理审核
	@Command("mgaut_ok")
	public void mgaut_ok(@BindingParam("espa_id") Label espa_id,
			@BindingParam("espa_tapr_id") Label espa_tapr_id,
			@BindingParam("embase2") Label embase2,
			@BindingParam("embase3") Label embase3) throws Exception {
		// bll.empay_mgautok(espa_id.getValue(), espa_tapr_id.getValue());//
		// 审核支付通知
		String[] message = payBll.mgaut_pay(
				Integer.parseInt(espa_id.getValue()),
				Integer.parseInt(espa_tapr_id.getValue()));
		
		if(embase3.getValue().equals("福利费")){
		String[]  str=cbll.passaudit(embase2.getValue());  // paynum为支付单号，返回String[]类型
		}
		// 弹出提示
		Messagebox.show("操作成功！", "操作提示",
				new Messagebox.Button[] { Messagebox.Button.OK },
				Messagebox.INFORMATION, null);
		Executions.sendRedirect("/EmPay/EmPay_MgAut.zul");
	}

	// 支付审核
	@Command("pay_ok")
	public void pay_ok(@BindingParam("em1") Combobox em1,
			@BindingParam("em2") Textbox em2, @BindingParam("em3") Textbox em3,
			@BindingParam("em4") Datebox em4, @BindingParam("em5") Textbox em5,
			@BindingParam("em6") Combobox em6,
			@BindingParam("em7") Textbox em7,
			@BindingParam("ownmonth") Label ownmonth,
			@BindingParam("paynum") Label paynum,
			@BindingParam("espa_id") Label espa_id,
			@BindingParam("espa_tapr_id") Label espa_tapr_id) throws Exception {
		String ems4 = "";
		if (em4.getValue() != null) {
			ems4 = bll.DatetoSting(em4.getValue());
		}

		// bll.empay_payok(em1.getValue(), em2.getValue(), em3.getValue(), ems4,
		// em5.getValue(), em6.getValue(), em7.getValue(),
		// ownmonth.getValue(), paynum.getValue());// 支付

		String[] message = payBll.aut_payok(em1.getValue(), em2.getValue(),
				em3.getValue(), ems4, em5.getValue(), em6.getValue(),
				em7.getValue(), ownmonth.getValue(), paynum.getValue(),
				Integer.parseInt(espa_id.getValue()),
				Integer.parseInt(espa_tapr_id.getValue()));

		// 弹出提示
		Messagebox.show("操作成功！", "操作提示",
				new Messagebox.Button[] { Messagebox.Button.OK },
				Messagebox.INFORMATION, null);
		Executions.sendRedirect("/EmPay/EmPay_AutPay.zul");
	}

	// 公司页面跳转
	@Command("cbase_index")
	public void cbase_index(@BindingParam("winadd") Window winadd,@BindingParam("em1") Label em1,
			@BindingParam("em2") Label em2, @BindingParam("em3") Label em3,
			@BindingParam("em4") Label em4,
			@BindingParam("espa_id") Label espa_id,
			@BindingParam("espa_tapr_id") Label espa_tapr_id) {
		winadd.detach();
		// Executions.sendRedirect("/EmPay/EmPay_AutCBase.zul?ownmonth="+em1.getValue()+"&paynum="+em2.getValue()+"&stype="+em3.getValue()+"&fee="+em4.getValue());
		Map arg = new HashMap();
		// arg.put("daid", emco.getEbco_id());
		arg.put("id", espa_id.getValue());
		arg.put("ownmonth", em1.getValue());
		arg.put("paynum", em2.getValue());
		arg.put("stype", em3.getValue());
		arg.put("fee", em4.getValue());
		arg.put("espa_tapr_id", espa_tapr_id.getValue());
		Window wnd = (Window) Executions.createComponents("EmPay_AutCBase.zul",
				null, arg);
		wnd.doModal();
	}

	// 员工页面跳转
	@Command("ebase_index")
	public void ebase_index(@BindingParam("winadd") Window winadd,@BindingParam("em1") Label em1,
			@BindingParam("em2") Label em2, @BindingParam("em3") Label em3,
			@BindingParam("em4") Label em4,
			@BindingParam("espa_id") Label espa_id,
			@BindingParam("espa_tapr_id") Label espa_tapr_id) {
		winadd.detach();
		// Executions.sendRedirect("/EmPay/EmPay_AutCBase.zul?ownmonth="+em1.getValue()+"&paynum="+em2.getValue()+"&stype="+em3.getValue()+"&fee="+em4.getValue());
		Map arg = new HashMap();
		// arg.put("daid", emco.getEbco_id());
		arg.put("id", espa_id.getValue());
		arg.put("ownmonth", em1.getValue());
		arg.put("paynum", em2.getValue());
		arg.put("stype", em3.getValue());
		arg.put("fee", em4.getValue());
		arg.put("espa_tapr_id", espa_tapr_id.getValue());
		Window wnd = (Window) Executions.createComponents("EmPay_AutBase.zul",
				null, arg);
		wnd.doModal();
	}

	public ListModelList<EmPay_BaseListModel> getEmpay_baselist() {
		return empay_baselist;
	}

	public void setEmpay_baselist(
			ListModelList<EmPay_BaseListModel> empay_baselist) {
		this.empay_baselist = empay_baselist;
	}

	public ListModelList<EmPay_BaseListModel> getEmpay_feelist() {
		return empay_feelist;
	}

	public void setEmpay_feelist(
			ListModelList<EmPay_BaseListModel> empay_feelist) {
		this.empay_feelist = empay_feelist;
	}

	public ListModelList<EmPay_BaseListModel> getCopay_baselist() {
		return copay_baselist;
	}

	public void setCopay_baselist(
			ListModelList<EmPay_BaseListModel> copay_baselist) {
		this.copay_baselist = copay_baselist;
	}

	public ListModelList<EmPay_BaseListModel> getCopay_feelist() {
		return copay_feelist;
	}

	public void setCopay_feelist(ListModelList<EmPay_BaseListModel> copay_feelist) {
		this.copay_feelist = copay_feelist;
	}

}
