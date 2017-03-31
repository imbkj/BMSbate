package Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CoFinanceSortAccountssModel {
	private final DecimalFormat df = new DecimalFormat("#.00");
	private int cfss_id;
	private String cfss_cfso_id;
	private int cid;
	private int ownmonth;
	private String cfss_cpac_name;
	private BigDecimal cfss_Receivable;
	private BigDecimal cfss_PaidIn;
	private String cfss_addtime;
	private int cfss_fpstate;
	private Integer cfss_state;
	private int cfss_yystate;
	private BigDecimal collect = new BigDecimal(df.format(BigDecimal.ZERO));
	private boolean checked;
	private BigDecimal cfco_TotalPaidIn; // 收款总数
	private BigDecimal cfta_Balance; // 未录入收ArrayListprivate
										// CoFinanceSortAccountssModel
										// ssModel;private
										// CoFinanceSortAccountssModel
										// ssModel;private
										// CoFinanceSortAccountssModel
										// ssModel;private
										// CoFinanceSortAccountssModel
										// ssModel;款差额
	private String cfss_addname;
	private String remark;
	private String coba_company;
	private String coba_client;
	private String cfss_type;
	private Boolean cfss_fpfrist;
	private Boolean cfss_allin;

	public String getCfss_type() {
		return cfss_type;
	}

	public void setCfss_type(String cfss_type) {
		this.cfss_type = cfss_type;
	}

	public String getCoba_company() {
		return coba_company;
	}

	public void setCoba_company(String coba_company) {
		this.coba_company = coba_company;
	}

	public String getCoba_client() {
		return coba_client;
	}

	public void setCoba_client(String coba_client) {
		this.coba_client = coba_client;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public CoFinanceSortAccountssModel() {
		super();
	}

	// // 计算差额
	// public void sumImbalance() {
	// imbalance = cfsa_PaidIn.subtract(cfsa_Receivable);
	// }

	public Boolean getCfss_fpfrist() {
		return cfss_fpfrist;
	}

	public void setCfss_fpfrist(Boolean cfss_fpfrist) {
		this.cfss_fpfrist = cfss_fpfrist;
	}

	public BigDecimal getCfco_TotalPaidIn() {
		return cfco_TotalPaidIn;
	}

	public String getCfss_addname() {
		return cfss_addname;
	}

	public void setCfss_addname(String cfss_addname) {
		this.cfss_addname = cfss_addname;
	}

	public void setCfco_TotalPaidIn(BigDecimal cfco_TotalPaidIn) {
		this.cfco_TotalPaidIn = new BigDecimal(df.format(cfco_TotalPaidIn));

	}

	public BigDecimal getCfta_Balance() {
		return cfta_Balance;
	}

	public void setCfta_Balance(BigDecimal cfta_Balance) {
		this.cfta_Balance = new BigDecimal(df.format(cfta_Balance));
	}

	public int getCfss_id() {
		return cfss_id;
	}

	public void setCfss_id(int cfss_id) {
		this.cfss_id = cfss_id;
	}

	public String getCfss_cfso_id() {
		return cfss_cfso_id;
	}

	public void setCfss_cfso_id(String cfss_cfso_id) {
		this.cfss_cfso_id = cfss_cfso_id;
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

	public String getCfss_cpac_name() {
		return cfss_cpac_name;
	}

	public void setCfss_cpac_name(String cfss_cpac_name) {
		this.cfss_cpac_name = cfss_cpac_name;
	}

	public BigDecimal getCfss_Receivable() {
		return cfss_Receivable;
	}

	public void setCfss_Receivable(BigDecimal cfss_Receivable) {

		this.cfss_Receivable = new BigDecimal(df.format(cfss_Receivable));
	}

	public BigDecimal getCfss_PaidIn() {
		return cfss_PaidIn;
	}

	public void setCfss_PaidIn(BigDecimal cfss_PaidIn) {

		this.cfss_PaidIn = new BigDecimal(df.format(cfss_PaidIn));
	}

	public String getCfss_addtime() {
		return cfss_addtime;
	}

	public void setCfss_addtime(String cfss_addtime) {
		this.cfss_addtime = cfss_addtime;
	}

	public int getCfss_fpstate() {
		return cfss_fpstate;
	}

	public void setCfss_fpstate(int cfss_fpstate) {
		this.cfss_fpstate = cfss_fpstate;
	}

	public Integer getCfss_state() {
		return cfss_state;
	}

	public void setCfss_state(Integer cfss_state) {
		this.cfss_state = cfss_state;
	}

	public int getCfss_yystate() {
		return cfss_yystate;
	}

	public void setCfss_yystate(int cfss_yystate) {
		this.cfss_yystate = cfss_yystate;
	}

	public DecimalFormat getDf() {
		return df;
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

	public Boolean getCfss_allin() {
		return cfss_allin;
	}

	public void setCfss_allin(Boolean cfss_allin) {
		this.cfss_allin = cfss_allin;
	}

}
