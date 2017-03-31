package Controller.CoFinanceManage;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

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

import Model.CoFinanceCollectModel;
import Model.CoFinanceMonthlyBillModel;
import Model.CoFinanceMonthlyBillSortAccountModel;
import Util.UserInfo;
import bll.CoFinanceManage.Cfma_BillSelBll;
import bll.CoFinanceManage.Cfma_CollectOperateBll;

public class Cfma_CollectToBillController {
	private final String billNo = Executions.getCurrent().getArg()
			.get("billNo").toString();
	private CoFinanceMonthlyBillModel billModel;
	private List<CoFinanceMonthlyBillSortAccountModel> sortList;
	private String username;
	private BigDecimal amount;
	private BigDecimal sumEx;
	private boolean amountDisabled;
	private boolean autoSortDisabled;
	private boolean amountCheck;
	private String message = "";
	private List<CoFinanceCollectModel> billNoList;
	private DecimalFormat df = new DecimalFormat("#.00");
	Cfma_BillSelBll bll = new Cfma_BillSelBll();

	public Cfma_CollectToBillController() {
		billModel = bll.getBillModel(billNo);
		sortList = bll.getMonthlyBillSortAccount(billNo);
		username = UserInfo.getUsername();
		amount = BigDecimal.ZERO;
		sumEx = BigDecimal.ZERO;
		amountDisabled = false;
		autoSortDisabled = true;
		amountCheck = false;
	}

	// 提交收款
	@Command("SubmitCollectDis")
	public void SubmitCollectDis(@BindingParam("win") Window win,
			@BindingParam("remark") String remark,
			@ContextParam(ContextType.VIEW) Component view) {
		sumCollect();
		if (!amountCheck) {
			Messagebox.show("请先确认收款金额后，再提交。", "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
		} else if (amount.compareTo(BigDecimal.ZERO) == 0) {
			Messagebox.show("无需提交,您并未录入收款的金额。", "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
		} else if (sumEx.compareTo(amount) == 1) {
			Messagebox.show("无法提交,您分配科目的总额，已超出您录入的收款金额,请确认无误后提交。", "操作提示",
					Messagebox.OK, Messagebox.INFORMATION);
		} else if (sumEx.compareTo(amount) != 0) {
			if (Messagebox
					.show("您分配科目的总额与收款金额不一致，系统将把多出的收款录入该公司的余额当中，是否确认提交？",
							"操作提示", Messagebox.YES | Messagebox.NO,
							Messagebox.QUESTION) == Messagebox.YES) {
				AddCollectDis(win, remark, view);
			}
		} else {
			if (Messagebox.show("请核对收款金额：" + amount, "操作提示", Messagebox.YES
					| Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
				AddCollectDis(win, remark, view);
			}
		}
	}

	// 账单收款处理
	private void AddCollectDis(Window win, String remark, Component view) {
		try {
			if (remark == null) {
				remark = "";
			}
			Cfma_CollectOperateBll opbll = new Cfma_CollectOperateBll();
			String[] message = opbll.addCollectToBill(billModel.getCid(),
					amount, UserInfo.getUsername(), remark,
					billModel.getCfmb_number(), 0, sumEx, sortList);
			if (message[0].equals("1")) {
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.NONE);
				Binder bind = (Binder) view.getParent().getAttribute("binder");
				bind.postCommand("refreshWin", null);
				win.detach();
			} else {
				// 弹出提示
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
	@NotifyChange({ "amountDisabled", "autoSortDisabled", "amountCheck" })
	public void confirmEx(@BindingParam("pop") Popup pop,
			@BindingParam("com") Component com) {
		if (amountCheck) {
			if (checkAmount(pop, com)) {
				if (amount.compareTo(billModel.getImbalance().abs()) >= 0) {
					autoSortDisabled = false;
				}
				amountDisabled = true;
			} else {
				amountCheck = false;
			}
		} else {
			amountDisabled = false;
			autoSortDisabled = true;
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

					message = "您分配财务科目的总和，已超过收款的总额。";
					BindUtils.postNotifyChange(null, null, this, "message");
					pop.open(com);
				}
			}
			BindUtils.postNotifyChange(null, null, m, "collect");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 自动分配财务科目
	@Command("autoDisSort")
	@NotifyChange({ "sortList", "sumEx" })
	public void autoDisSort() {
		for (CoFinanceMonthlyBillSortAccountModel m : sortList) {
			m.autoDisCollect();
		}
		sumCollect();
	}

	// 检查财务科目分配的金额
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

				message = "您分配财务科目的总和，已超过收款的总额。";
				BindUtils.postNotifyChange(null, null, this, "message");
				pop.open(com);
			}
		}
	}

	// 格式化收款金额(只保留两位小数)
	@Command("formatAmount")
	@NotifyChange("amount")
	public void formatAmount() {
		amount = new BigDecimal(df.format(amount));
	}

	// 检查收款的总额是否合法
	private boolean checkAmount(Popup pop, Component com) {
		boolean bool = false;
		if (amount.compareTo(BigDecimal.ZERO) == 0) {
			message = "您并未领取金额，无需确认。";
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

	public List<CoFinanceCollectModel> getBillNoList() {
		return billNoList;
	}

	public void setBillNoList(List<CoFinanceCollectModel> billNoList) {
		this.billNoList = billNoList;
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
}
