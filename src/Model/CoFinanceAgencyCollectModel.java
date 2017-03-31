package Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CoFinanceAgencyCollectModel {
	private final DecimalFormat df = new DecimalFormat("#.00");
	private int cfac_id;
	private int cfac_coab_id;
	private int ownmonth;
	private String cfac_cfab_number;
	private BigDecimal cfac_TotalPaidIn;
	private String cfac_remark;
	private String cfac_addname;
	private String cfac_addtime;
	private int cfac_state;

	public CoFinanceAgencyCollectModel() {
		super();
	}

	public int getCfac_id() {
		return cfac_id;
	}

	public void setCfac_id(int cfac_id) {
		this.cfac_id = cfac_id;
	}

	public int getCfac_coab_id() {
		return cfac_coab_id;
	}

	public void setCfac_coab_id(int cfac_coab_id) {
		this.cfac_coab_id = cfac_coab_id;
	}

	public int getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(int ownmonth) {
		this.ownmonth = ownmonth;
	}

	public String getCfac_cfab_number() {
		return cfac_cfab_number;
	}

	public void setCfac_cfab_number(String cfac_cfab_number) {
		this.cfac_cfab_number = cfac_cfab_number;
	}

	public BigDecimal getCfac_TotalPaidIn() {
		return cfac_TotalPaidIn;
	}

	public void setCfac_TotalPaidIn(BigDecimal cfac_TotalPaidIn) {
		this.cfac_TotalPaidIn = new BigDecimal(df.format(cfac_TotalPaidIn));
	}

	public String getCfac_remark() {
		return cfac_remark;
	}

	public void setCfac_remark(String cfac_remark) {
		this.cfac_remark = cfac_remark;
	}

	public String getCfac_addname() {
		return cfac_addname;
	}

	public void setCfac_addname(String cfac_addname) {
		this.cfac_addname = cfac_addname;
	}

	public String getCfac_addtime() {
		return cfac_addtime;
	}

	public void setCfac_addtime(String cfac_addtime) {
		this.cfac_addtime = cfac_addtime;
	}

	public int getCfac_state() {
		return cfac_state;
	}

	public void setCfac_state(int cfac_state) {
		this.cfac_state = cfac_state;
	}

	public DecimalFormat getDf() {
		return df;
	}

}
