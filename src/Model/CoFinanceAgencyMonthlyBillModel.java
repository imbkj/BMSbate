package Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CoFinanceAgencyMonthlyBillModel {
	private final DecimalFormat df = new DecimalFormat("#.00");
	private String cfab_number;
	private int cfab_coab_id;
	private int ownmonth;
	private BigDecimal cfab_PersonnelReceivable;
	private BigDecimal cfab_FinanceReceivable;
	private BigDecimal cfab_PaidIn;
	private BigDecimal cfab_WriteOffEx;
	private BigDecimal cfab_ChargeOff;
	private String cfab_ChargeOffName;
	private String cfab_ChargeOffTime;
	private String cfab_ChargeOffReason;
	private BigDecimal totalReceivable;
	private BigDecimal imbalance;
	private int writeOffs;
	private String writeOffsStr;
	private String province;
	private String city;
	private String coab_name;
	private BigDecimal cfat_Balance;
	private String cfab_addtime;
	private int comCou;

	public CoFinanceAgencyMonthlyBillModel() {
		super();
	}

	// 应收合计
	public void sumReceivable() {
		totalReceivable = cfab_PersonnelReceivable.add(cfab_FinanceReceivable);
	}

	// 计算差额(总实收+冲销金额-总应收)
	public void sumImbalance() {
		imbalance = cfab_PaidIn.add(cfab_WriteOffEx).subtract(totalReceivable);
		if (imbalance.compareTo(BigDecimal.ZERO) == 0) {
			writeOffs = 1;
			writeOffsStr = "已销账";
		} else if (imbalance.add(cfab_ChargeOff).compareTo(BigDecimal.ZERO) == 0) {
			writeOffs = 2;
			writeOffsStr = "已核销";
		} else {
			writeOffs = 0;
			writeOffsStr = "未销账";
		}
	}

	public String getCfab_number() {
		return cfab_number;
	}

	public void setCfab_number(String cfab_number) {
		this.cfab_number = cfab_number;
	}

	public int getCfab_coab_id() {
		return cfab_coab_id;
	}

	public void setCfab_coab_id(int cfab_coab_id) {
		this.cfab_coab_id = cfab_coab_id;
	}

	public int getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(int ownmonth) {
		this.ownmonth = ownmonth;
	}

	public BigDecimal getCfab_PersonnelReceivable() {
		return cfab_PersonnelReceivable;
	}

	public void setCfab_PersonnelReceivable(BigDecimal cfab_PersonnelReceivable) {
		this.cfab_PersonnelReceivable = new BigDecimal(
				df.format(cfab_PersonnelReceivable));
	}

	public BigDecimal getCfab_FinanceReceivable() {
		return cfab_FinanceReceivable;
	}

	public void setCfab_FinanceReceivable(BigDecimal cfab_FinanceReceivable) {
		this.cfab_FinanceReceivable = new BigDecimal(
				df.format(cfab_FinanceReceivable));
	}

	public BigDecimal getCfab_PaidIn() {
		return cfab_PaidIn;
	}

	public void setCfab_PaidIn(BigDecimal cfab_PaidIn) {
		this.cfab_PaidIn = new BigDecimal(df.format(cfab_PaidIn));
	}

	public BigDecimal getCfab_WriteOffEx() {
		return cfab_WriteOffEx;
	}

	public void setCfab_WriteOffEx(BigDecimal cfab_WriteOffEx) {
		this.cfab_WriteOffEx = new BigDecimal(df.format(cfab_WriteOffEx));
	}

	public BigDecimal getCfab_ChargeOff() {
		return cfab_ChargeOff;
	}

	public void setCfab_ChargeOff(BigDecimal cfab_ChargeOff) {
		this.cfab_ChargeOff = new BigDecimal(df.format(cfab_ChargeOff));
	}

	public String getCfab_ChargeOffName() {
		return cfab_ChargeOffName;
	}

	public void setCfab_ChargeOffName(String cfab_ChargeOffName) {
		this.cfab_ChargeOffName = cfab_ChargeOffName;
	}

	public String getCfab_ChargeOffTime() {
		return cfab_ChargeOffTime;
	}

	public void setCfab_ChargeOffTime(String cfab_ChargeOffTime) {
		this.cfab_ChargeOffTime = cfab_ChargeOffTime;
	}

	public String getCfab_ChargeOffReason() {
		return cfab_ChargeOffReason;
	}

	public void setCfab_ChargeOffReason(String cfab_ChargeOffReason) {
		this.cfab_ChargeOffReason = cfab_ChargeOffReason;
	}

	public BigDecimal getTotalReceivable() {
		return totalReceivable;
	}

	public void setTotalReceivable(BigDecimal totalReceivable) {
		this.totalReceivable = new BigDecimal(df.format(totalReceivable));
	}

	public BigDecimal getImbalance() {
		return imbalance;
	}

	public void setImbalance(BigDecimal imbalance) {
		this.imbalance = new BigDecimal(df.format(imbalance));
	}

	public int getWriteOffs() {
		return writeOffs;
	}

	public void setWriteOffs(int writeOffs) {
		this.writeOffs = writeOffs;
	}

	public String getWriteOffsStr() {
		return writeOffsStr;
	}

	public void setWriteOffsStr(String writeOffsStr) {
		this.writeOffsStr = writeOffsStr;
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

	public String getCfab_addtime() {
		return cfab_addtime;
	}

	public void setCfab_addtime(String cfab_addtime) {
		this.cfab_addtime = cfab_addtime;
	}

	public int getComCou() {
		return comCou;
	}

	public void setComCou(int comCou) {
		this.comCou = comCou;
	}

}
