package Model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
/**
 * 
 * @author suhongyaun
 * 2016-05-20
 */
public class CoFinanceCollectNewModel {
	private final DecimalFormat df = new DecimalFormat("#.00");
	private int cfco_id;
	private int cid;
	private String companyName; // 公司名称
	private String shortname;
	private String shortspell;
	private int ownmonth;
	private String cfco_cfmb_number;
	private BigDecimal cfco_TotalPaidIn;
	private String cfco_remark;
	private String cfco_addname;
	private String cfco_addtime;
	private int cfco_state;
	private String coba_company;
	private String types;
	private Integer coin_id;
	private BigDecimal total;
	private BigDecimal finanServiceFee; // 财务服务费
	private BigDecimal deformityFee; // 残保金
	private BigDecimal fileManageFee; // 档案管理费
	private BigDecimal serviceFee; // 服务费
	private BigDecimal oMoveFee; // 个调费
	private BigDecimal activityFee; // 活动费
	private BigDecimal residencePermitFee; // 居住证费
	private BigDecimal lasscFee; // 劳动保障卡
	private BigDecimal other; // 其他
	private BigDecimal businessProtectFee; // 商保费
	private BigDecimal businessServiceFee; // 商务服务费
	private BigDecimal bookFee; // 书报费
	private BigDecimal salaryOfAfterTax; // 税后工资
	private BigDecimal bodyTestFee; // 体检费
	private BigDecimal recruitServiceFee; // 招聘服务费
	private BigDecimal houseRestore; // 住房返还
	private BigDecimal sheBaoFee; // 社保费
	private BigDecimal houseGjj; // 公积金
	private String cfsa_cpac_name; // 费用名称
	private BigDecimal cfsa_PaidIn; // 具体费用
	private String msg_a;
	private String coba_client;
	private String cfmb_WriteOffs;
	private String cfmb_PersonnelConfim;
	private String cfmb_FinanceConfim;
	private boolean flag = true; // 是否可选
	private BigDecimal cfmb_PersonnelReceivable;
	private BigDecimal cfmb_FinanceReceivable;
	private BigDecimal cfmb_LoanBalance;
	private BigDecimal cfmb_CarryForwardEx;
	private BigDecimal cfmb_TotalPaidIn;
	private BigDecimal totalReceivable;
	private BigDecimal imbalance;
	private BigDecimal balance;
	private Integer cfsa_id;
	private String cfmb_remark;
	private String cfta_addtime;
	private String cfta_addname;
	private String cfta_remark;
	private String cfss_cfso_id;
	private String cfdm_addtime;

	private boolean check;
	private Integer month;
	private String cfta_updatetime;

	public String getCfss_cfso_id() {
		return cfss_cfso_id;
	}
	
	

	public String getCfta_updatetime() {
		return cfta_updatetime;
	}



	public void setCfta_updatetime(String cfta_updatetime) {
		this.cfta_updatetime = cfta_updatetime;
	}



	public void setCfss_cfso_id(String cfss_cfso_id) {
		this.cfss_cfso_id = cfss_cfso_id;
	}

	public BigDecimal getTotalReceivable() {
		return totalReceivable;
	}

	public String getCfta_addname() {
		return cfta_addname;
	}

	public String getCfta_remark() {
		return cfta_remark;
	}

	public void setCfta_remark(String cfta_remark) {
		this.cfta_remark = cfta_remark;
	}

	public void setCfta_addname(String cfta_addname) {
		this.cfta_addname = cfta_addname;
	}

	public void setTotalReceivable(BigDecimal totalReceivable) {
		this.totalReceivable = totalReceivable;
	}

	public BigDecimal getImbalance() {
		return imbalance;
	}
	
	

	public String getCfdm_addtime() {
		return cfdm_addtime;
	}



	public void setCfdm_addtime(String cfdm_addtime) {
		this.cfdm_addtime = cfdm_addtime;
	}



	public void setImbalance(BigDecimal imbalance) {
		
		this.imbalance = imbalance == null ? BigDecimal.ZERO : imbalance
				.setScale(2, RoundingMode.HALF_UP);
	 
	}

	public BigDecimal getBalance() {
		return balance;
	}



	public void setBalance(BigDecimal balance) {
		
		this.balance = balance == null ? BigDecimal.ZERO : imbalance
				.setScale(2, RoundingMode.HALF_UP);
		 
	}


	public BigDecimal getCfmb_PersonnelReceivable() {
		return cfmb_PersonnelReceivable;
	}

	public void setCfmb_PersonnelReceivable(BigDecimal cfmb_PersonnelReceivable) {
		this.cfmb_PersonnelReceivable = cfmb_PersonnelReceivable;
	}

	public BigDecimal getCfmb_FinanceReceivable() {
		return cfmb_FinanceReceivable;
	}

	public void setCfmb_FinanceReceivable(BigDecimal cfmb_FinanceReceivable) {
		this.cfmb_FinanceReceivable = cfmb_FinanceReceivable;
	}

	public BigDecimal getCfmb_LoanBalance() {
		return cfmb_LoanBalance;
	}

	public void setCfmb_LoanBalance(BigDecimal cfmb_LoanBalance) {
		this.cfmb_LoanBalance = cfmb_LoanBalance;
	}

	public BigDecimal getCfmb_CarryForwardEx() {
		return cfmb_CarryForwardEx;
	}

	public void setCfmb_CarryForwardEx(BigDecimal cfmb_CarryForwardEx) {
		this.cfmb_CarryForwardEx = cfmb_CarryForwardEx;
	}

	public BigDecimal getCfmb_TotalPaidIn() {
		return cfmb_TotalPaidIn;
	}

	public void setCfmb_TotalPaidIn(BigDecimal cfmb_TotalPaidIn) {
		this.cfmb_TotalPaidIn = cfmb_TotalPaidIn;
	}

	public String getCfmb_WriteOffs() {
		return cfmb_WriteOffs;
	}

	public void setCfmb_WriteOffs(String cfmb_WriteOffs) {
		this.cfmb_WriteOffs = cfmb_WriteOffs;
	}

	public String getCfmb_PersonnelConfim() {
		return cfmb_PersonnelConfim;
	}

	public void setCfmb_PersonnelConfim(String cfmb_PersonnelConfim) {
		this.cfmb_PersonnelConfim = cfmb_PersonnelConfim;
	}

	public String getCfmb_FinanceConfim() {
		return cfmb_FinanceConfim;
	}

	public void setCfmb_FinanceConfim(String cfmb_FinanceConfim) {
		this.cfmb_FinanceConfim = cfmb_FinanceConfim;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String getShortspell() {
		return shortspell;
	}

	public void setShortspell(String shortspell) {
		this.shortspell = shortspell;
	}

	public String getCoba_client() {
		return coba_client;
	}

	public void setCoba_client(String coba_client) {
		this.coba_client = coba_client;
	}

	public String getMsg_a() {
		return msg_a;
	}

	public void setMsg_a(String msg_a) {
		this.msg_a = msg_a;
	}

	public BigDecimal getSheBaoFee() {
		return sheBaoFee;
	}

	public void setSheBaoFee(BigDecimal sheBaoFee) {
		this.sheBaoFee = sheBaoFee == null ? BigDecimal.ZERO : sheBaoFee
				.setScale(2, RoundingMode.HALF_UP);
	}

	public BigDecimal getCfsa_PaidIn() {
		return cfsa_PaidIn;
	}

	public void setCfsa_PaidIn(BigDecimal cfsa_PaidIn) {
		this.cfsa_PaidIn = cfsa_PaidIn == null ? BigDecimal.ZERO : cfsa_PaidIn
				.setScale(2, RoundingMode.HALF_UP);
	}

	public String getCfsa_cpac_name() {
		return cfsa_cpac_name;
	}

	public void setCfsa_cpac_name(String cfsa_cpac_name) {
		this.cfsa_cpac_name = cfsa_cpac_name;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public BigDecimal getFinanServiceFee() {
		return finanServiceFee;
	}

	public void setFinanServiceFee(BigDecimal finanServiceFee) {
		this.finanServiceFee = finanServiceFee == null ? BigDecimal.ZERO
				: finanServiceFee.setScale(2, RoundingMode.HALF_UP);
	}

	public BigDecimal getDeformityFee() {
		return deformityFee;
	}

	public void setDeformityFee(BigDecimal deformityFee) {
		this.deformityFee = deformityFee == null ? BigDecimal.ZERO
				: deformityFee.setScale(2, RoundingMode.HALF_UP);
	}

	public BigDecimal getFileManageFee() {
		return fileManageFee;
	}

	public void setFileManageFee(BigDecimal fileManageFee) {
		this.fileManageFee = fileManageFee == null ? BigDecimal.ZERO
				: fileManageFee.setScale(2, RoundingMode.HALF_UP);
	}

	public BigDecimal getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee == null ? BigDecimal.ZERO : serviceFee
				.setScale(2, RoundingMode.HALF_UP);
	}

	public BigDecimal getoMoveFee() {
		return oMoveFee;
	}

	public void setoMoveFee(BigDecimal oMoveFee) {
		this.oMoveFee = oMoveFee == null ? BigDecimal.ZERO : oMoveFee.setScale(
				2, RoundingMode.HALF_UP);
	}

	public BigDecimal getActivityFee() {
		return activityFee;
	}

	public void setActivityFee(BigDecimal activityFee) {
		this.activityFee = activityFee == null ? BigDecimal.ZERO : activityFee
				.setScale(2, RoundingMode.HALF_UP);
	}

	public BigDecimal getResidencePermitFee() {
		return residencePermitFee;
	}

	public void setResidencePermitFee(BigDecimal residencePermitFee) {
		this.residencePermitFee = residencePermitFee == null ? BigDecimal.ZERO
				: residencePermitFee.setScale(2, RoundingMode.HALF_UP);
	}

	public BigDecimal getLasscFee() {
		return lasscFee;
	}

	public void setLasscFee(BigDecimal lasscFee) {
		this.lasscFee = lasscFee == null ? BigDecimal.ZERO : lasscFee.setScale(
				2, RoundingMode.HALF_UP);
	}

	public BigDecimal getOther() {
		return other;
	}

	public void setOther(BigDecimal other) {
		this.other = other == null ? BigDecimal.ZERO : other.setScale(2,
				RoundingMode.HALF_UP);
	}

	public BigDecimal getBusinessProtectFee() {
		return businessProtectFee;
	}

	public void setBusinessProtectFee(BigDecimal businessProtectFee) {
		this.businessProtectFee = businessProtectFee == null ? BigDecimal.ZERO
				: businessProtectFee.setScale(2, RoundingMode.HALF_UP);
	}

	public BigDecimal getBusinessServiceFee() {
		return businessServiceFee;
	}

	public void setBusinessServiceFee(BigDecimal businessServiceFee) {
		this.businessServiceFee = businessServiceFee == null ? BigDecimal.ZERO
				: businessServiceFee.setScale(2, RoundingMode.HALF_UP);
	}

	public BigDecimal getBookFee() {
		return bookFee;
	}

	public void setBookFee(BigDecimal bookFee) {
		this.bookFee = bookFee == null ? BigDecimal.ZERO : bookFee.setScale(2,
				RoundingMode.HALF_UP);
	}

	public BigDecimal getSalaryOfAfterTax() {
		return salaryOfAfterTax;
	}

	public void setSalaryOfAfterTax(BigDecimal salaryOfAfterTax) {
		this.salaryOfAfterTax = salaryOfAfterTax == null ? BigDecimal.ZERO
				: salaryOfAfterTax.setScale(2, RoundingMode.HALF_UP);
	}

	public BigDecimal getBodyTestFee() {
		return bodyTestFee;
	}

	public void setBodyTestFee(BigDecimal bodyTestFee) {
		this.bodyTestFee = bodyTestFee == null ? BigDecimal.ZERO : bodyTestFee
				.setScale(2, RoundingMode.HALF_UP);
	}

	public BigDecimal getRecruitServiceFee() {
		return recruitServiceFee;
	}

	public void setRecruitServiceFee(BigDecimal recruitServiceFee) {
		this.recruitServiceFee = recruitServiceFee == null ? BigDecimal.ZERO
				: recruitServiceFee.setScale(2, RoundingMode.HALF_UP);
	}

	public BigDecimal getHouseRestore() {
		return houseRestore;
	}

	public void setHouseRestore(BigDecimal houseRestore) {
		this.houseRestore = houseRestore == null ? BigDecimal.ZERO
				: houseRestore.setScale(2, RoundingMode.HALF_UP);
	}

	public BigDecimal getHouseGjj() {
		return houseGjj;
	}

	public void setHouseGjj(BigDecimal houseGjj) {
		this.houseGjj = houseGjj == null ? BigDecimal.ZERO : houseGjj.setScale(
				2, RoundingMode.HALF_UP);
	}

	public DecimalFormat getDf() {
		return df;
	}

	public CoFinanceCollectNewModel() {
		super();
	}

	public int getCfco_id() {
		return cfco_id;
	}

	public void setCfco_id(int cfco_id) {
		this.cfco_id = cfco_id;
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

	public String getCfco_cfmb_number() {
		return cfco_cfmb_number;
	}

	public void setCfco_cfmb_number(String cfco_cfmb_number) {
		this.cfco_cfmb_number = cfco_cfmb_number;
	}

	public BigDecimal getCfco_TotalPaidIn() {
		return cfco_TotalPaidIn;
	}

	public void setCfco_TotalPaidIn(BigDecimal cfco_TotalPaidIn) {
		// this.cfco_TotalPaidIn = new BigDecimal(df.format(cfco_TotalPaidIn));
		this.cfco_TotalPaidIn = cfco_TotalPaidIn == null ? BigDecimal.ZERO
				: cfco_TotalPaidIn.setScale(2, RoundingMode.HALF_UP);
	}

	public String getCfco_remark() {
		return cfco_remark;
	}

	public void setCfco_remark(String cfco_remark) {
		this.cfco_remark = cfco_remark;
	}

	public String getCfco_addname() {
		return cfco_addname;
	}

	public void setCfco_addname(String cfco_addname) {
		this.cfco_addname = cfco_addname;
	}

	public String getCfco_addtime() {
		return cfco_addtime;
	}

	public void setCfco_addtime(String cfco_addtime) {
		this.cfco_addtime = cfco_addtime;
	}

	public int getCfco_state() {
		return cfco_state;
	}

	public void setCfco_state(int cfco_state) {
		this.cfco_state = cfco_state;
	}

	public String getCoba_company() {
		return coba_company;
	}

	public void setCoba_company(String coba_company) {
		this.coba_company = coba_company;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public Integer getCoin_id() {
		return coin_id;
	}

	public void setCoin_id(Integer coin_id) {
		this.coin_id = coin_id;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public Integer getCfsa_id() {
		return cfsa_id;
	}

	public void setCfsa_id(Integer cfsa_id) {
		this.cfsa_id = cfsa_id;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public String getCfmb_remark() {
		return cfmb_remark;
	}

	public void setCfmb_remark(String cfmb_remark) {
		this.cfmb_remark = cfmb_remark;
	}

	public String getCfta_addtime() {
		return cfta_addtime;
	}

	public void setCfta_addtime(String cfta_addtime) {
		this.cfta_addtime = cfta_addtime;
	}
	
}
