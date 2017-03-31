package Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CoFinanceMonthlyAccountModel {
	private final DecimalFormat df = new DecimalFormat("#.00");
	private int cfma_id;
	private int cfma_cfta_id;
	private int ownmonth;
	private BigDecimal cfma_PersonnelReceivable;
	private BigDecimal cfma_FinanceReceivable;
	private BigDecimal personnelPaidIn;
	private BigDecimal financePaidIn;
	private BigDecimal totalReceivable;
	private BigDecimal totalPaidIn;
	private BigDecimal totalLoanBalance;
	private BigDecimal totalCarryForwardEx;
	private BigDecimal totalImbalance;
	private String cfma_remark;
	private int cfma_state;
	private int cid;
	private String coba_company;
	private BigDecimal perimbalance;
	private BigDecimal finimbalance;
	private String coba_client;
	private BigDecimal billTotalReceivable;
	private int existsPersonnelConfirm;
	private int existsFinanceNoConfirm;
	private boolean existsNoBill;

	public CoFinanceMonthlyAccountModel() {
		super();
	}

	// 应收合计
	public void sumReceivable() {
		totalReceivable = cfma_PersonnelReceivable.add(cfma_FinanceReceivable);
	}

	// 计算差额(总实收+总垫款+总结转-总应收)
	public void sumImbalance() {
		totalImbalance = totalLoanBalance.add(totalPaidIn)
				.add(totalCarryForwardEx).subtract(totalReceivable);
	}

	// 判断该公司总应收与账单总应收是否一致（以此判断是否有未加账单的业务,用于页面的提示。）
	public void existsNoBill() {
		if (billTotalReceivable.compareTo(totalReceivable) == -1) {
			existsNoBill = true;
		} else {
			existsNoBill = false;
		}
	}

	public int getCfma_id() {
		return cfma_id;
	}

	public void setCfma_id(int cfma_id) {
		this.cfma_id = cfma_id;
	}

	public int getCfma_cfta_id() {
		return cfma_cfta_id;
	}

	public void setCfma_cfta_id(int cfma_cfta_id) {
		this.cfma_cfta_id = cfma_cfta_id;
	}

	public int getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(int ownmonth) {
		this.ownmonth = ownmonth;
	}

	public BigDecimal getCfma_PersonnelReceivable() {
		return cfma_PersonnelReceivable;
	}

	public void setCfma_PersonnelReceivable(BigDecimal cfma_PersonnelReceivable) {
		this.cfma_PersonnelReceivable = new BigDecimal(
				df.format(cfma_PersonnelReceivable));
	}

	public BigDecimal getCfma_FinanceReceivable() {
		return cfma_FinanceReceivable;
	}

	public void setCfma_FinanceReceivable(BigDecimal cfma_FinanceReceivable) {
		this.cfma_FinanceReceivable = new BigDecimal(
				df.format(cfma_FinanceReceivable));
	}

	public BigDecimal getPersonnelPaidIn() {
		return personnelPaidIn;
	}

	public void setPersonnelPaidIn(BigDecimal personnelPaidIn) {
		this.personnelPaidIn = new BigDecimal(df.format(personnelPaidIn));
	}

	public BigDecimal getFinancePaidIn() {
		return financePaidIn;
	}

	public void setFinancePaidIn(BigDecimal financePaidIn) {
		this.financePaidIn = new BigDecimal(df.format(financePaidIn));
	}

	public BigDecimal getTotalPaidIn() {
		return totalPaidIn;
	}

	public void setTotalPaidIn(BigDecimal totalPaidIn) {
		this.totalPaidIn = new BigDecimal(df.format(totalPaidIn));
	}

	public String getCfma_remark() {
		return cfma_remark;
	}

	public void setCfma_remark(String cfma_remark) {
		this.cfma_remark = cfma_remark;
	}

	public int getCfma_state() {
		return cfma_state;
	}

	public void setCfma_state(int cfma_state) {
		this.cfma_state = cfma_state;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getCoba_company() {
		return coba_company;
	}

	public void setCoba_company(String coba_company) {
		this.coba_company = coba_company;
	}

	public BigDecimal getPerimbalance() {
		return perimbalance;
	}

	public void setPerimbalance(BigDecimal perimbalance) {
		this.perimbalance = new BigDecimal(df.format(perimbalance));
	}

	public BigDecimal getFinimbalance() {
		return finimbalance;
	}

	public void setFinimbalance(BigDecimal finimbalance) {
		this.finimbalance = new BigDecimal(df.format(finimbalance));
	}

	public String getCoba_client() {
		return coba_client;
	}

	public void setCoba_client(String coba_client) {
		this.coba_client = coba_client;
	}

	public BigDecimal getTotalReceivable() {
		return totalReceivable;
	}

	public void setTotalReceivable(BigDecimal totalReceivable) {
		this.totalReceivable = new BigDecimal(df.format(totalReceivable));
	}

	public BigDecimal getTotalLoanBalance() {
		return totalLoanBalance;
	}

	public void setTotalLoanBalance(BigDecimal totalLoanBalance) {
		this.totalLoanBalance = new BigDecimal(df.format(totalLoanBalance));
	}

	public BigDecimal getTotalImbalance() {
		return totalImbalance;
	}

	public void setTotalImbalance(BigDecimal totalImbalance) {
		this.totalImbalance = new BigDecimal(df.format(totalImbalance));
	}

	public BigDecimal getTotalCarryForwardEx() {
		return totalCarryForwardEx;
	}

	public void setTotalCarryForwardEx(BigDecimal totalCarryForwardEx) {
		this.totalCarryForwardEx = new BigDecimal(
				df.format(totalCarryForwardEx));
	}

	public boolean isExistsNoBill() {
		return existsNoBill;
	}

	public void setExistsNoBill(boolean existsNoBill) {
		this.existsNoBill = existsNoBill;
	}

	public int getExistsPersonnelConfirm() {
		return existsPersonnelConfirm;
	}

	public void setExistsPersonnelConfirm(int existsPersonnelConfirm) {
		this.existsPersonnelConfirm = existsPersonnelConfirm;
	}

	public int getExistsFinanceNoConfirm() {
		return existsFinanceNoConfirm;
	}

	public void setExistsFinanceNoConfirm(int existsFinanceNoConfirm) {
		this.existsFinanceNoConfirm = existsFinanceNoConfirm;
	}

	public BigDecimal getBillTotalReceivable() {
		return billTotalReceivable;
	}

	public void setBillTotalReceivable(BigDecimal billTotalReceivable) {
		this.billTotalReceivable =  new BigDecimal(df.format(billTotalReceivable));
	}

}
