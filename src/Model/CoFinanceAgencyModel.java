package Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CoFinanceAgencyModel {
	private final DecimalFormat df = new DecimalFormat("#.00");
	private int cfat_id;
	private int cfat_coab_id;
	private String province;
	private String city;
	private String coab_name;
	private BigDecimal cfat_Balance;
	private BigDecimal personnelReceivable;
	private BigDecimal financeReceivable;
	private BigDecimal totalReceivable;
	private BigDecimal totalPaidIn;
	private BigDecimal chargeOff;
	private BigDecimal totalImbalance;
	private BigDecimal totalWriteOffEx;

	public CoFinanceAgencyModel() {
		super();
	}

	// 应收合计
	public void sumReceivable() {
		totalReceivable = personnelReceivable.add(financeReceivable);
	}

	// 计算差额(总实收+总核销+总冲销-总应收)
	public void sumImbalance() {
		totalImbalance = chargeOff.add(totalPaidIn).add(totalWriteOffEx)
				.subtract(totalReceivable);
	}

	public int getCfat_id() {
		return cfat_id;
	}

	public void setCfat_id(int cfat_id) {
		this.cfat_id = cfat_id;
	}

	public int getCfat_coab_id() {
		return cfat_coab_id;
	}

	public void setCfat_coab_id(int cfat_coab_id) {
		this.cfat_coab_id = cfat_coab_id;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCoab_name() {
		return coab_name;
	}

	public void setCoab_name(String coab_name) {
		this.coab_name = coab_name;
	}

	public BigDecimal getCfat_Balance() {
		return cfat_Balance;
	}

	public void setCfat_Balance(BigDecimal cfat_Balance) {
		this.cfat_Balance = new BigDecimal(df.format(cfat_Balance));
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

	public BigDecimal getTotalReceivable() {
		return totalReceivable;
	}

	public void setTotalReceivable(BigDecimal totalReceivable) {
		this.totalReceivable = new BigDecimal(df.format(totalReceivable));
	}

	public BigDecimal getTotalPaidIn() {
		return totalPaidIn;
	}

	public void setTotalPaidIn(BigDecimal totalPaidIn) {
		this.totalPaidIn = new BigDecimal(df.format(totalPaidIn));
	}

	public BigDecimal getChargeOff() {
		return chargeOff;
	}

	public void setChargeOff(BigDecimal chargeOff) {
		this.chargeOff = new BigDecimal(df.format(chargeOff));
	}

	public BigDecimal getTotalImbalance() {
		return totalImbalance;
	}

	public void setTotalImbalance(BigDecimal totalImbalance) {
		this.totalImbalance = new BigDecimal(df.format(totalImbalance));
	}

	public BigDecimal getTotalWriteOffEx() {
		return totalWriteOffEx;
	}

	public void setTotalWriteOffEx(BigDecimal totalWriteOffEx) {
		this.totalWriteOffEx = new BigDecimal(df.format(totalWriteOffEx));
	}

}
