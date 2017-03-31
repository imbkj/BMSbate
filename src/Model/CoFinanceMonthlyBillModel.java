package Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CoFinanceMonthlyBillModel {
	private final DecimalFormat df = new DecimalFormat("#.00");
	private String cfmb_number;
	private int cfmb_cfta_id;
	private int ownmonth;
	private String cfmb_name;
	private BigDecimal cfmb_PersonnelReceivable;
	private BigDecimal cfmb_FinanceReceivable;
	private BigDecimal cfmb_PersonnelPaidIn;
	private BigDecimal cfmb_FinancePaidIn;
	private BigDecimal cfmb_TotalPaidIn;
	private BigDecimal totalReceivable;
	private BigDecimal cfmb_LoanBalance;
	private BigDecimal cfmb_CarryForwardEx;
	private int cfmb_PersonnelConfirm;
	private String cfmb_PersonnelConfirmName;
	private String cfmb_PersonnelConfirmTime;
	private int cfmb_FinanceConfirm;
	private String cfmb_FinanceConfirmName;
	private String cfmb_FinanceConfirmTime;
	private int cfmb_Confirm;
	private int cfmb_WriteOffs;
	private String cfmb_WriteOffsName;
	private String cfmb_WriteOffsTime;
	private String cfmb_addname;
	private String cfmb_addtime;
	private String cfmb_remark;
	private int cfmb_state;
	private String cfmb_PersonnelConfirmStr;
	private String cfmb_FinanceConfirmStr;
	private String cfmb_ConfirmStr;
	private String cfmb_WriteOffsStr;
	private BigDecimal imbalance;
	private boolean check = false;
	private int cid;
	private String company;
	private String client;
	private BigDecimal cfta_Balance;
	private String cfco_remark;
	private BigDecimal cfco_TotalPaidIn;
	private String cfco_addname;

	public CoFinanceMonthlyBillModel() {
		super();
	}

	// 应收合计
	public void sumTotalReceivable() {
		if (cfmb_PersonnelReceivable != null && cfmb_FinanceReceivable != null) {
			this.totalReceivable = cfmb_PersonnelReceivable
					.add(cfmb_FinanceReceivable);
		}
	}

	// 统计差额(实收合计+结转金额+垫付金额-应收合计)
	public void sumImbalance() {
		if (cfmb_TotalPaidIn != null && cfmb_LoanBalance != null) {
			imbalance = cfmb_TotalPaidIn.add(cfmb_LoanBalance)
					.add(cfmb_CarryForwardEx).subtract(totalReceivable);
			if (imbalance.compareTo(BigDecimal.ZERO) == 0) {
				cfmb_WriteOffs = 1;
				cfmb_WriteOffsStr = "已销账";
			} else {
				cfmb_WriteOffs = 0;
				cfmb_WriteOffsStr = "未销账";
			}
		}
	}

	public String getCfco_remark() {
		return cfco_remark;
	}

	public void setCfco_remark(String cfco_remark) {
		this.cfco_remark = cfco_remark;
	}

	public BigDecimal getCfco_TotalPaidIn() {
		return cfco_TotalPaidIn;
	}

	public void setCfco_TotalPaidIn(BigDecimal cfco_TotalPaidIn) {
		if (cfco_TotalPaidIn != null) {
			this.cfco_TotalPaidIn = new BigDecimal(
					df.format(cfco_TotalPaidIn));
		}
		
	}

	public String getCfco_addname() {
		return cfco_addname;
	}

	public void setCfco_addname(String cfco_addname) {
		this.cfco_addname = cfco_addname;
	}

	public String getCfmb_number() {
		return cfmb_number;
	}

	public void setCfmb_number(String cfmb_number) {
		this.cfmb_number = cfmb_number;
	}

	public String getCfmb_name() {
		return cfmb_name;
	}

	public void setCfmb_name(String cfmb_name) {
		this.cfmb_name = cfmb_name;
	}

	public int getCfmb_cfta_id() {
		return cfmb_cfta_id;
	}

	public void setCfmb_cfta_id(int cfmb_cfta_id) {
		this.cfmb_cfta_id = cfmb_cfta_id;
	}

	public int getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(int ownmonth) {
		this.ownmonth = ownmonth;
	}

	public BigDecimal getCfmb_PersonnelReceivable() {
		return cfmb_PersonnelReceivable;
	}

	public void setCfmb_PersonnelReceivable(BigDecimal cfmb_PersonnelReceivable) {
		if (cfmb_PersonnelReceivable != null) {
			this.cfmb_PersonnelReceivable = new BigDecimal(
					df.format(cfmb_PersonnelReceivable));
		}
	}

	public BigDecimal getCfmb_FinanceReceivable() {
		return cfmb_FinanceReceivable;
	}

	public void setCfmb_FinanceReceivable(BigDecimal cfmb_FinanceReceivable) {
		if (cfmb_FinanceReceivable != null) {
			this.cfmb_FinanceReceivable = new BigDecimal(
					df.format(cfmb_FinanceReceivable));
		}
	}

	public BigDecimal getCfmb_PersonnelPaidIn() {
		return cfmb_PersonnelPaidIn;
	}

	public void setCfmb_PersonnelPaidIn(BigDecimal cfmb_PersonnelPaidIn) {
		if (cfmb_PersonnelPaidIn != null) {
			this.cfmb_PersonnelPaidIn = new BigDecimal(
					df.format(cfmb_PersonnelPaidIn));
		}
	}

	public BigDecimal getCfmb_FinancePaidIn() {
		return cfmb_FinancePaidIn;
	}

	public void setCfmb_FinancePaidIn(BigDecimal cfmb_FinancePaidIn) {
		if (cfmb_FinancePaidIn != null) {
			this.cfmb_FinancePaidIn = new BigDecimal(
					df.format(cfmb_FinancePaidIn));
		}
	}

	public BigDecimal getCfmb_TotalPaidIn() {
		return cfmb_TotalPaidIn;
	}

	public BigDecimal getTotalReceivable() {
		return totalReceivable;
	}

	public void setTotalReceivable(BigDecimal totalReceivable) {
		if (totalReceivable != null) {
			this.totalReceivable = new BigDecimal(df.format(totalReceivable));
		}
	}

	public BigDecimal getImbalance() {
		return imbalance;
	}

	public void setImbalance(BigDecimal imbalance) {
		if (imbalance != null) {
			this.imbalance = new BigDecimal(df.format(imbalance));
		}
	}

	public void setCfmb_TotalPaidIn(BigDecimal cfmb_TotalPaidIn) {
		if (cfmb_TotalPaidIn != null) {
			this.cfmb_TotalPaidIn = new BigDecimal(df.format(cfmb_TotalPaidIn));
		}
	}

	public BigDecimal getCfmb_LoanBalance() {
		return cfmb_LoanBalance;
	}

	public void setCfmb_LoanBalance(BigDecimal cfmb_LoanBalance) {
		if (cfmb_LoanBalance != null) {
			this.cfmb_LoanBalance = new BigDecimal(df.format(cfmb_LoanBalance));
		}
	}

	public BigDecimal getCfmb_CarryForwardEx() {
		return cfmb_CarryForwardEx;
	}

	public void setCfmb_CarryForwardEx(BigDecimal cfmb_CarryForwardEx) {
		if (cfmb_CarryForwardEx != null) {
			this.cfmb_CarryForwardEx = new BigDecimal(
					df.format(cfmb_CarryForwardEx));
		}
	}

	public int getCfmb_PersonnelConfirm() {
		return cfmb_PersonnelConfirm;
	}

	public void setCfmb_PersonnelConfirm(int cfmb_PersonnelConfirm) {
		this.cfmb_PersonnelConfirm = cfmb_PersonnelConfirm;
		switch (cfmb_PersonnelConfirm) {
		case 0:
			cfmb_PersonnelConfirmStr = "未确认";
			break;
		case 1:
			cfmb_PersonnelConfirmStr = "已确认";
			break;
		}
	}

	public String getCfmb_PersonnelConfirmName() {
		return cfmb_PersonnelConfirmName;
	}

	public void setCfmb_PersonnelConfirmName(String cfmb_PersonnelConfirmName) {
		this.cfmb_PersonnelConfirmName = cfmb_PersonnelConfirmName;
	}

	public String getCfmb_PersonnelConfirmTime() {
		return cfmb_PersonnelConfirmTime;
	}

	public void setCfmb_PersonnelConfirmTime(String cfmb_PersonnelConfirmTime) {
		this.cfmb_PersonnelConfirmTime = cfmb_PersonnelConfirmTime;
	}

	public int getCfmb_FinanceConfirm() {
		return cfmb_FinanceConfirm;
	}

	public void setCfmb_FinanceConfirm(int cfmb_FinanceConfirm) {
		this.cfmb_FinanceConfirm = cfmb_FinanceConfirm;
		switch (cfmb_FinanceConfirm) {
		case 0:
			cfmb_FinanceConfirmStr = "未确认";
			break;
		case 1:
			cfmb_FinanceConfirmStr = "已确认";
			break;
		}
	}

	public String getCfmb_FinanceConfirmName() {
		return cfmb_FinanceConfirmName;
	}

	public void setCfmb_FinanceConfirmName(String cfmb_FinanceConfirmName) {
		this.cfmb_FinanceConfirmName = cfmb_FinanceConfirmName;
	}

	public String getCfmb_FinanceConfirmTime() {
		return cfmb_FinanceConfirmTime;
	}

	public void setCfmb_FinanceConfirmTime(String cfmb_FinanceConfirmTime) {
		this.cfmb_FinanceConfirmTime = cfmb_FinanceConfirmTime;
	}

	public int getCfmb_Confirm() {
		return cfmb_Confirm;
	}

	public void setCfmb_Confirm(int cfmb_Confirm) {
		this.cfmb_Confirm = cfmb_Confirm;
		switch (cfmb_Confirm) {
		case 0:
			cfmb_ConfirmStr = "未确认";
			break;
		case 1:
			cfmb_ConfirmStr = "已确认";
			break;
		}
	}

	public int getCfmb_WriteOffs() {
		return cfmb_WriteOffs;
	}

	public void setCfmb_WriteOffs(int cfmb_WriteOffs) {
		this.cfmb_WriteOffs = cfmb_WriteOffs;
		switch (cfmb_WriteOffs) {
		case 0:
			cfmb_WriteOffsStr = "未销账";
			break;
		case 1:
			cfmb_WriteOffsStr = "已销账";
			break;
		case 2:
			cfmb_WriteOffsStr = "已结转";
			break;
		}
	}

	public String getCfmb_WriteOffsName() {
		return cfmb_WriteOffsName;
	}

	public void setCfmb_WriteOffsName(String cfmb_WriteOffsName) {
		this.cfmb_WriteOffsName = cfmb_WriteOffsName;
	}

	public String getCfmb_WriteOffsTime() {
		return cfmb_WriteOffsTime;
	}

	public void setCfmb_WriteOffsTime(String cfmb_WriteOffsTime) {
		this.cfmb_WriteOffsTime = cfmb_WriteOffsTime;
	}

	public String getCfmb_addtime() {
		return cfmb_addtime;
	}

	public void setCfmb_addtime(String cfmb_addtime) {
		this.cfmb_addtime = cfmb_addtime;
	}

	public String getCfmb_remark() {
		return cfmb_remark;
	}

	public void setCfmb_remark(String cfmb_remark) {
		this.cfmb_remark = cfmb_remark;
	}

	public int getCfmb_state() {
		return cfmb_state;
	}

	public void setCfmb_state(int cfmb_state) {
		this.cfmb_state = cfmb_state;
	}

	public String getCfmb_PersonnelConfirmStr() {
		return cfmb_PersonnelConfirmStr;
	}

	public void setCfmb_PersonnelConfirmStr(String cfmb_PersonnelConfirmStr) {
		this.cfmb_PersonnelConfirmStr = cfmb_PersonnelConfirmStr;
	}

	public String getCfmb_FinanceConfirmStr() {
		return cfmb_FinanceConfirmStr;
	}

	public void setCfmb_FinanceConfirmStr(String cfmb_FinanceConfirmStr) {
		this.cfmb_FinanceConfirmStr = cfmb_FinanceConfirmStr;
	}

	public String getCfmb_ConfirmStr() {
		return cfmb_ConfirmStr;
	}

	public void setCfmb_ConfirmStr(String cfmb_ConfirmStr) {
		this.cfmb_ConfirmStr = cfmb_ConfirmStr;
	}

	public String getCfmb_WriteOffsStr() {
		return cfmb_WriteOffsStr;
	}

	public void setCfmb_WriteOffsStr(String cfmb_WriteOffsStr) {
		this.cfmb_WriteOffsStr = cfmb_WriteOffsStr;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getCfmb_addname() {
		return cfmb_addname;
	}

	public void setCfmb_addname(String cfmb_addname) {
		this.cfmb_addname = cfmb_addname;
	}

	public BigDecimal getCfta_Balance() {
		return cfta_Balance;
	}

	public void setCfta_Balance(BigDecimal cfta_Balance) {
		this.cfta_Balance = new BigDecimal(df.format(cfta_Balance));
	}

}
