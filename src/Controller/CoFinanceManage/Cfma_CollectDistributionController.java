package Controller.CoFinanceManage;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Window;

import Model.CoFinanceMonthlyBillModel;
import Model.CoFinanceMonthlyBillSortAccountModel;
import Util.UserInfo;
import bll.CoFinanceManage.Cfma_BillSelBll;
import bll.CoFinanceManage.Cfma_CollectOperateBll;

public class Cfma_CollectDistributionController {
	private final String billNo = Executions.getCurrent().getArg()
			.get("billNo").toString();
	private final String coba_client = Executions.getCurrent().getArg()
			.get("coba_client").toString();
	private final String owmno = Executions.getCurrent().getArg().get("owmno").toString();
	private CoFinanceMonthlyBillModel billModel;
	private List<CoFinanceMonthlyBillSortAccountModel> sortList;
	private String username;
	private BigDecimal amount;
	private BigDecimal sumEx;
	private boolean amountDisabled;
	private boolean autoSortDisabled;
	private boolean amountCheck;
	private String message = "";

	public Cfma_CollectDistributionController() {
		Cfma_BillSelBll bll = new Cfma_BillSelBll();
		billModel = bll.getBillModel(billNo);
		sortList = bll.getMonthlyBillSortAccount(billNo);
		username = UserInfo.getUsername();
		amount = BigDecimal.ZERO;
		sumEx = BigDecimal.ZERO;
		amountDisabled = false;
		autoSortDisabled = true;
		amountCheck = false;
	}
	// 结转子页面刷新父页面billModel 以及 sortList
		@Command("refreshW")
		@NotifyChange({ "billModel", "sortList" })
		public void refreshW() {
			Cfma_BillSelBll bll = new Cfma_BillSelBll();
			billModel = bll.getBillModel(billNo);
			sortList = bll.getMonthlyBillSortAccount(billNo);
			username = UserInfo.getUsername();
			amount = BigDecimal.ZERO;
			sumEx = BigDecimal.ZERO;
			amountDisabled = false;
			autoSortDisabled = true;
			amountCheck = false;
		}
	// 提交领款
	@Command("SubmitCollectDis")
	public void SubmitCollectDis(@BindingParam("win") Window win,
			@BindingParam("remark") String remark,
			@ContextParam(ContextType.VIEW) Component view) {
		DecimalFormat df = new DecimalFormat("#.00");
		amount = new BigDecimal(df.format(amount));
		sumCollect();
		if (sumEx.compareTo(BigDecimal.ZERO) == 0) {
			Messagebox.show("无需提交,您并未分配科目的金额。", "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
		} else if (sumEx.compareTo(billModel.getCfta_Balance()) == 1) {
			Messagebox.show("无法提交,您分配科目的总额，已超出可领金额,请确认无误后提交。", "操作提示",
					Messagebox.OK, Messagebox.INFORMATION);
		} else if (sumEx.compareTo(amount) != 0) {
			if (Messagebox
					.show("您分配科目的总额与领取总额不一致，系统将以分配科目的总额为实际领款金额，是否确认提交？",
							"操作提示", Messagebox.YES | Messagebox.NO,
							Messagebox.QUESTION) == Messagebox.YES) {
				AddCollectDis(win, remark, view);
			}
		} else {
			AddCollectDis(win, remark, view);
		}
	}

	// 复制财务科目余额
	@Command("sameImbalance")
	public void sameImbalance(
			@BindingParam("m") CoFinanceMonthlyBillSortAccountModel m,
			@BindingParam("pop") Popup pop, @BindingParam("com") Component com) {
		try {
			m.setCollect(m.getImbalance().abs());
			sumCollect();
			if (amountCheck) {
				if (sumEx.compareTo(amount) == 1) {
					sumEx = sumEx.subtract(m.getCollect());
					m.setCollect(BigDecimal.ZERO);
					BindUtils.postNotifyChange(null, null, this, "sumEx");

					message = "您分配科目的总和，已超过领取总额。";
					BindUtils.postNotifyChange(null, null, this, "message");
					pop.open(com);
				}
			} else {
				amount = sumEx;
				BindUtils.postNotifyChange(null, null, this, "amount");
			}
			BindUtils.postNotifyChange(null, null, m, "collect");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 账单领款
	private void AddCollectDis(Window win, String remark, Component view) {
		try {
			if (remark == null) {
				remark = "";
			}
			Cfma_CollectOperateBll opbll = new Cfma_CollectOperateBll();
			String[] message = opbll.drawMoneyToBill(
					billModel.getCfmb_number(), billModel.getCid(), sumEx,
					UserInfo.getUsername(), remark, sortList);
			if (message[0].equals("1")) {
				Binder bind = (Binder) view.getParent().getAttribute("binder");
				bind.postCommand("refreshWin", null);
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.NONE);
				win.detach();
			} else {
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("操作出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 确认领款总额
	@Command("confirmEx")
	@NotifyChange({ "amountDisabled", "autoSortDisabled", "amountCheck",
			"amount" })
	public void confirmEx(@BindingParam("pop") Popup pop,
			@BindingParam("com") Component com) {
		if (amountCheck) {
			if (checkAmount(pop, com)) {
				if (amount.compareTo(billModel.getImbalance().abs()) == 0) {
					autoSortDisabled = false;
				}
				amountDisabled = true;
			} else {
				amountCheck = false;
			}
		} else {
			amount = sumEx;
			amountDisabled = false;
			autoSortDisabled = true;
		}
	}

	// 自动填写领款总额(填写账单余额)
	@Command("autoImbalance")
	@NotifyChange("amount")
	public void autoImbalance() {
		amount = billModel.getImbalance().abs();
	}

	// 自动填写领款总额(填写可领金额)
	@Command("autoBalance")
	@NotifyChange("amount")
	public void autoBalance() {
		amount = billModel.getCfta_Balance();
	}

	// 自动分配财务科目
	@Command("autoDisSort")
	@NotifyChange({ "sortList", "sumEx" })
	public void autoDisSort() {
		for (CoFinanceMonthlyBillSortAccountModel m : sortList) {
			m.autoDisCollect();
		}
		sumEx = amount;
	}

	// 检查领款的金额
	@Command("checkEx")
	public void checkEx(
			@BindingParam("m") CoFinanceMonthlyBillSortAccountModel m,
			@BindingParam("pop") Popup pop, @BindingParam("com") Component com) {
		if (m.getCollect().compareTo(m.getImbalance().abs()) == 1) {
			m.setCollect(m.getImbalance().abs());
		}
		sumCollect();
		if (amountCheck) {
			if (sumEx.compareTo(amount) == 1) {
				sumEx = sumEx.subtract(m.getCollect());
				m.setCollect(BigDecimal.ZERO);
				BindUtils.postNotifyChange(null, null, this, "sumEx");

				message = "您分配科目的总和，已超过领取总额。";
				BindUtils.postNotifyChange(null, null, this, "message");
				pop.open(com);
			}
		} else {
			amount = sumEx;
			BindUtils.postNotifyChange(null, null, this, "amount");
		}
	}

	// 检查领取的总额是否合法
	private boolean checkAmount(Popup pop, Component com) {
		boolean bool = false;
		if (amount.compareTo(BigDecimal.ZERO) == 0) {
			message = "您并未领取金额，无需确认。";
			BindUtils.postNotifyChange(null, null, this, "message");
			pop.open(com);
		} else if (amount.compareTo(billModel.getCfta_Balance()) == 1) {
			amount = BigDecimal.ZERO;
			BindUtils.postNotifyChange(null, null, this, "amount");
			message = "您需领取的金额，已超出可领金额，请重新填写需领取的金额。";
			BindUtils.postNotifyChange(null, null, this, "message");
			pop.open(com);
		} else if (amount.compareTo(billModel.getImbalance().abs()) == 1) {
			amount = billModel.getImbalance().abs();
			BindUtils.postNotifyChange(null, null, this, "amount");
			message = "您需领取的金额，已超出账单的差额，系统已帮您校正，请重新确认。";
			BindUtils.postNotifyChange(null, null, this, "message");
			pop.open(com);
		} else {
			bool = true;
		}
		return bool;
	}

	// 合计已分配的金额
	private void sumCollect() {
		sumEx = BigDecimal.ZERO;
		for (CoFinanceMonthlyBillSortAccountModel m : sortList) {
			sumEx = sumEx.add(m.getCollect());
		}
		BindUtils.postNotifyChange(null, null, this, "sumEx");
	}
    //结转
	@Command("carrybutton")
	public void carrybutton(@ContextParam(ContextType.VIEW) Component view,@BindingParam("m") CoFinanceMonthlyBillSortAccountModel m){
		System.out.println(m.getImbalance());
		System.out.println(Math.abs(m.getImbalance().doubleValue()));
		Map<String, String> map = new HashMap<String, String>();
		map.put("Cfmb_number", billModel.getCfmb_number());
		map.put("Company", billModel.getCompany());
		map.put("Cfsa_cpac_name", m.getCfsa_cpac_name());
		//map.put("Collect", m.getCollect().toString());
		map.put("coba_client", coba_client);
		map.put("owmno", owmno);
		map.put("billNo", billNo);
		Window window1 = (Window) Executions.createComponents(
				"Cfma_CollectCarry.zul", view, map);
		window1.doModal();
	}
	
	public CoFinanceMonthlyBillModel getBillModel() {
		return billModel;
	}

	public List<CoFinanceMonthlyBillSortAccountModel> getSortList() {
		return sortList;
	}

	public String getUsername() {
		return username;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public boolean isAmountDisabled() {
		return amountDisabled;
	}

	public boolean isAutoSortDisabled() {
		return autoSortDisabled;
	}

	public BigDecimal getSumEx() {
		return sumEx;
	}

	public boolean isAmountCheck() {
		return amountCheck;
	}

	public void setAmountCheck(boolean amountCheck) {
		this.amountCheck = amountCheck;
	}

	public String getMessage() {
		return message;
	}

	public String getCoba_client() {
		return coba_client;
	}

	public String getOwmno() {
		return owmno;
	}
	
	

}
