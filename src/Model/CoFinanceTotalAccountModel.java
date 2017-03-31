package Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

public class CoFinanceTotalAccountModel {
	private final DecimalFormat df = new DecimalFormat("#.00");
	private String coba_client;
	private int cid;
	private String coba_company;
	private int cfta_id;
	private BigDecimal personnelReceivable = BigDecimal.ZERO;
	private BigDecimal financeReceivable = BigDecimal.ZERO;
	private BigDecimal personnelPaidIn = BigDecimal.ZERO;
	private BigDecimal financePaidIn = BigDecimal.ZERO;
	private BigDecimal totalReceivable = BigDecimal.ZERO;
	private BigDecimal totalPaidIn = BigDecimal.ZERO;
	private BigDecimal cfta_Balance = BigDecimal.ZERO;
	private BigDecimal loanBalance = BigDecimal.ZERO;
	private BigDecimal carryForwardEx = BigDecimal.ZERO;
	private BigDecimal imbalance = BigDecimal.ZERO;
	private Date cfta_addtime;
	private Date cfta_updatetime;
	private int cfta_state;

	// 应收统计
	public void sumReceivable() {
		totalReceivable = personnelReceivable.add(financeReceivable);
	}

	// 差额统计(实收合计-应收合计-结转金额-垫付金额)
	public void sumImbalance() {
		imbalance = totalPaidIn.subtract(totalReceivable)
				.subtract(loanBalance).subtract(carryForwardEx);
	}

	public CoFinanceTotalAccountModel() {
		super();
	}

	public String getCoba_client() {
		return coba_client;
	}

	public void setCoba_client(String coba_client) {
		this.coba_client = coba_client;
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

	public int getCfta_id() {
		return cfta_id;
	}

	public void setCfta_id(int cfta_id) {
		this.cfta_id = cfta_id;
	}

	public BigDecimal getTotalReceivable() {
		return totalReceivable;
	}

	public void setTotalReceivable(BigDecimal totalReceivable) {
		this.totalReceivable = new BigDecimal(df.format(totalReceivable));
	}

	public BigDecimal getCfta_Balance() {
		return cfta_Balance;
	}

	public void setCfta_Balance(BigDecimal cfta_Balance) {
		this.cfta_Balance = new BigDecimal(df.format(cfta_Balance));
	}

	public BigDecimal getPersonnelReceivable() {
		return personnelReceivable;
	}

	public void setPersonnelReceivable(BigDecimal personnelReceivable) {
		this.personnelReceivable = new BigDecimal(
				df.format(personnelReceivable));
	}

	public BigDecimal getFinanceReceivable() {
		return financeReceivable;
	}

	public void setFinanceReceivable(BigDecimal financeReceivable) {
		this.financeReceivable = new BigDecimal(df.format(financeReceivable));
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

	public BigDecimal getLoanBalance() {
		return loanBalance;
	}

	public void setLoanBalance(BigDecimal loanBalance) {
		this.loanBalance = new BigDecimal(df.format(loanBalance));
	}

	public BigDecimal getCarryForwardEx() {
		return carryForwardEx;
	}

	public void setCarryForwardEx(BigDecimal carryForwardEx) {
		this.carryForwardEx = new BigDecimal(df.format(carryForwardEx));
	}

	public BigDecimal getImbalance() {
		return imbalance;
	}

	public void setImbalance(BigDecimal imbalance) {
		this.imbalance = new BigDecimal(df.format(imbalance));
	}

	public Date getCfta_addtime() {
		return cfta_addtime;
	}

	public void setCfta_addtime(Date cfta_addtime) {
		this.cfta_addtime = cfta_addtime;
	}

	public Date getCfta_updatetime() {
		return cfta_updatetime;
	}

	public void setCfta_updatetime(Date cfta_updatetime) {
		this.cfta_updatetime = cfta_updatetime;
	}

	public int getCfta_state() {
		return cfta_state;
	}

	public void setCfta_state(int cfta_state) {
		this.cfta_state = cfta_state;
	}

	public DecimalFormat getDf() {
		return df;
	}

}
