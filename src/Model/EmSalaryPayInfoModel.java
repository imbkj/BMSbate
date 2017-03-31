package Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class EmSalaryPayInfoModel {
	private final DecimalFormat df = new DecimalFormat("#.00");
	private int cid;
	private int ownmonth;
	private String coba_company;
	private String coba_shortname;
	private String coba_namespell;
	private String coba_client;
	private String coba_gzaddname;
	private BigDecimal cfsa_PaidIn;
	private BigDecimal esda_pay;
	private int couWf;
	private int couSum;
	private String statistics;
	private String cfsa_addtime;
	private String remark;

	public EmSalaryPayInfoModel() {
		super();
	}

	public void mosaicStatistics() {
		this.statistics = couWf + "/" + couSum;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public int getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(int ownmonth) {
		this.ownmonth = ownmonth;
	}

	public String getCoba_company() {
		return coba_company;
	}

	public void setCoba_company(String coba_company) {
		this.coba_company = coba_company;
	}

	public String getCoba_shortname() {
		return coba_shortname;
	}

	public void setCoba_shortname(String coba_shortname) {
		this.coba_shortname = coba_shortname;
	}

	public String getCoba_namespell() {
		return coba_namespell;
	}

	public void setCoba_namespell(String coba_namespell) {
		this.coba_namespell = coba_namespell;
	}

	public String getCoba_client() {
		return coba_client;
	}

	public void setCoba_client(String coba_client) {
		this.coba_client = coba_client;
	}

	public String getCoba_gzaddname() {
		return coba_gzaddname;
	}

	public void setCoba_gzaddname(String coba_gzaddname) {
		this.coba_gzaddname = coba_gzaddname;
	}

	public BigDecimal getCfsa_PaidIn() {
		return cfsa_PaidIn;
	}

	public void setCfsa_PaidIn(BigDecimal cfsa_PaidIn) {
		this.cfsa_PaidIn = new BigDecimal(df.format(cfsa_PaidIn));
	}

	public BigDecimal getEsda_pay() {
		return esda_pay;
	}

	public void setEsda_pay(BigDecimal esda_pay) {
		this.esda_pay = new BigDecimal(df.format(esda_pay));
	}

	public int getCouWf() {
		return couWf;
	}

	public void setCouWf(int couWf) {
		this.couWf = couWf;
	}

	public int getCouSum() {
		return couSum;
	}

	public void setCouSum(int couSum) {
		this.couSum = couSum;
	}

	public String getStatistics() {
		return statistics;
	}

	public void setStatistics(String statistics) {
		this.statistics = statistics;
	}

	public String getCfsa_addtime() {
		return cfsa_addtime;
	}

	public void setCfsa_addtime(String cfsa_addtime) {
		this.cfsa_addtime = cfsa_addtime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
