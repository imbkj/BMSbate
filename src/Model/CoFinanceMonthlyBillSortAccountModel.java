package Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CoFinanceMonthlyBillSortAccountModel {
	private final DecimalFormat df = new DecimalFormat("#.00");
	private int cfsa_id;
	private String cfsa_cfmb_number;
	private String cfsa_cpac_name;
	private BigDecimal cfsa_Receivable;
	private BigDecimal cfsa_PaidIn;
	private BigDecimal imbalance;
	private String cfsa_addtime;
	private int cfsa_state;
	private Integer cfsa_yyoutstate;
	private BigDecimal collect = new BigDecimal(df.format(BigDecimal.ZERO));
	private boolean checked;

	public CoFinanceMonthlyBillSortAccountModel() {
		super();
	}

	// 计算差额
	public void sumImbalance() {
		imbalance = cfsa_PaidIn.subtract(cfsa_Receivable);
	}

	// 自动填写领款金额
	public void autoDisCollect() {
		collect = imbalance.abs();
	}

	public int getCfsa_id() {
		return cfsa_id;
	}

	public void setCfsa_id(int cfsa_id) {
		this.cfsa_id = cfsa_id;
	}

	public String getCfsa_cfmb_number() {
		return cfsa_cfmb_number;
	}

	public void setCfsa_cfmb_number(String cfsa_cfmb_number) {
		this.cfsa_cfmb_number = cfsa_cfmb_number;
	}

	public String getCfsa_cpac_name() {
		return cfsa_cpac_name;
	}

	public void setCfsa_cpac_name(String cfsa_cpac_name) {
		this.cfsa_cpac_name = cfsa_cpac_name;
	}

	public BigDecimal getCfsa_Receivable() {
		return cfsa_Receivable;
	}

	public void setCfsa_Receivable(BigDecimal cfsa_Receivable) {
		this.cfsa_Receivable = new BigDecimal(df.format(cfsa_Receivable));
	}

	public BigDecimal getCfsa_PaidIn() {
		return cfsa_PaidIn;
	}

	public void setCfsa_PaidIn(BigDecimal cfsa_PaidIn) {
		this.cfsa_PaidIn = new BigDecimal(df.format(cfsa_PaidIn));
	}

	public BigDecimal getImbalance() {
		return imbalance;
	}

	public void setImbalance(BigDecimal imbalance) {
		this.imbalance = new BigDecimal(df.format(imbalance));
	}

	public String getCfsa_addtime() {
		return cfsa_addtime;
	}

	public void setCfsa_addtime(String cfsa_addtime) {
		this.cfsa_addtime = cfsa_addtime;
	}

	public int getCfsa_state() {
		return cfsa_state;
	}

	public void setCfsa_state(int cfsa_state) {
		this.cfsa_state = cfsa_state;
	}

	public BigDecimal getCollect() {
		return collect;
	}

	public void setCollect(BigDecimal collect) {
		this.collect = new BigDecimal(df.format(collect));
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Integer getCfsa_yyoutstate() {
		return cfsa_yyoutstate;
	}

	public void setCfsa_yyoutstate(Integer cfsa_yyoutstate) {
		this.cfsa_yyoutstate = cfsa_yyoutstate;
	}
	
}
