package Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * 公积金缴交信息实体
 * 
 * @author Administrator
 * 
 */
public class CoHousingFundPayAmountModel {
	private final DecimalFormat df = new DecimalFormat("#.00");
	private int cfpa_id;
	private int bodyCount; // 人数
	private BigDecimal amount; // 缴交金额
	private BigDecimal payment; // 补缴金额
	private String ownmonth;
	private String addname;
	private Date addtime;
	private String companyname;
	private String cohf_houseid;
	private String cilent;
	private double cohf_cpp;
	private String chfz_number;
	private int lastDay; // 缴存日
	private Date cofp_queryDate; // 补缴查询日期
	private Date cqbc_queryDate; // 缴交查询日期
	private String cofp_queryDateString;
	private String cofp_isaccount; // 补缴到账状态
	private String cqbc_isaccount; // 缴交到账状态
	private int cohf_id;
	private int chfz_id;
	private int count;

	public Date getCqbc_queryDate() {
		return cqbc_queryDate;
	}

	public void setCqbc_queryDate(Date cqbc_queryDate) {
		this.cqbc_queryDate = cqbc_queryDate;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getCofp_queryDateString() {
		return cofp_queryDateString;
	}

	public void setCofp_queryDateString(String cofp_queryDateString) {
		this.cofp_queryDateString = cofp_queryDateString;
	}

	public int getChfz_id() {
		return chfz_id;
	}

	public void setChfz_id(int chfz_id) {
		this.chfz_id = chfz_id;
	}

	public int getCohf_id() {
		return cohf_id;
	}

	public void setCohf_id(int cohf_id) {
		this.cohf_id = cohf_id;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}


	public String getCohf_houseid() {
		return cohf_houseid;
	}

	public void setCohf_houseid(String cohf_houseid) {
		this.cohf_houseid = cohf_houseid;
	}

	public String getCilent() {
		return cilent;
	}

	public void setCilent(String cilent) {
		this.cilent = cilent;
	}

	public double getCohf_cpp() {
		return cohf_cpp;
	}

	public void setCohf_cpp(double cohf_cpp) {
		this.cohf_cpp = cohf_cpp;
	}

	public String getChfz_number() {
		return chfz_number;
	}

	public void setChfz_number(String chfz_number) {
		this.chfz_number = chfz_number;
	}

	public int getLastDay() {
		return lastDay;
	}

	public void setLastDay(int lastDay) {
		this.lastDay = lastDay;
	}

	public Date getCofp_queryDate() {
		return cofp_queryDate;
	}

	public void setCofp_queryDate(Date cofp_queryDate) {
		this.cofp_queryDate = cofp_queryDate;
	}

	public String getCofp_isaccount() {
		return cofp_isaccount;
	}

	public void setCofp_isaccount(String cofp_isaccount) {
		this.cofp_isaccount = cofp_isaccount;
	}

	public String getCqbc_isaccount() {
		return cqbc_isaccount;
	}

	public void setCqbc_isaccount(String cqbc_isaccount) {
		this.cqbc_isaccount = cqbc_isaccount;
	}

	public int getCfpa_id() {
		return cfpa_id;
	}

	public void setCfpa_id(int cfpa_id) {
		this.cfpa_id = cfpa_id;
	}

	public int getBodyCount() {
		return bodyCount;
	}

	public void setBodyCount(int bodyCount) {
		this.bodyCount = bodyCount;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		if (amount != null) {
			this.amount = new BigDecimal(df.format(amount));
		}
	}

	public BigDecimal getPayment() {
		return payment;
	}

	public void setPayment(BigDecimal payment) {
		if (payment != null) {
			this.payment = new BigDecimal(df.format(payment));
		}
	}

	public String getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(String ownmonth) {
		this.ownmonth = ownmonth;
	}

	public String getAddname() {
		return addname;
	}

	public void setAddname(String addname) {
		this.addname = addname;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

}
