package Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class EmFinanceCommissionOutDetailItemModel {
	private final DecimalFormat df = new DecimalFormat("#.00");
	private String item;
	private BigDecimal base = new BigDecimal(df.format(BigDecimal.ZERO));
	private BigDecimal emSum = new BigDecimal(df.format(BigDecimal.ZERO));
	private BigDecimal coSum = new BigDecimal(df.format(BigDecimal.ZERO));
	private BigDecimal receivable = new BigDecimal(df.format(BigDecimal.ZERO));
	private String cpp;
	private String opp;

	public EmFinanceCommissionOutDetailItemModel() {
		super();
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public BigDecimal getBase() {
		return base;
	}

	public void setBase(BigDecimal base) {
		this.base = new BigDecimal(df.format(base));
	}

	public BigDecimal getEmSum() {
		return emSum;
	}

	public void setEmSum(BigDecimal emSum) {
		this.emSum = new BigDecimal(df.format(emSum));
	}

	public BigDecimal getCoSum() {
		return coSum;
	}

	public void setCoSum(BigDecimal coSum) {
		this.coSum = new BigDecimal(df.format(coSum));
	}

	public BigDecimal getReceivable() {
		return receivable;
	}

	public void setReceivable(BigDecimal receivable) {
		this.receivable = new BigDecimal(df.format(receivable));
	}

	public String getCpp() {
		return cpp;
	}

	public void setCpp(String cpp) {
		this.cpp = cpp;
	}

	public String getOpp() {
		return opp;
	}

	public void setOpp(String opp) {
		this.opp = opp;
	}

}
