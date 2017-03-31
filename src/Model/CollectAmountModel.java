package Model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CollectAmountModel {

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
	private BigDecimal taxes; // 税金
	private BigDecimal accountfee; // 户口费
	private BigDecimal cfmb_TotalPaidIn;
	private Integer ownmonth;
	private String addtime;
	private String addname;
	private String modname;
	private String remark;
	private boolean flag;
	private String coba_company;
	private int cid;
	private String coba_client;
	private int cfss_id;

 
	public BigDecimal getTaxes() {
		return taxes;
	}
	

	public String getModname() {
		return modname;
	}


	public void setModname(String modname) {
		this.modname = modname;
	}


	public void setTaxes(BigDecimal taxes) {
		this.taxes = taxes;
	}

	public String getAddname() {
		return addname;
	}

	public String getRemark() {
		return remark;
	}

	public BigDecimal getAccountfee() {
		return accountfee;
	}

	public void setAccountfee(BigDecimal accountfee) {
		this.accountfee = accountfee == null ? BigDecimal.ZERO
				: accountfee.setScale(2, RoundingMode.HALF_UP);
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setAddname(String addname) {
		this.addname = addname;
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


 
	public String getCoba_client() {
		return coba_client;
	}

	public void setCoba_client(String coba_client) {
		this.coba_client = coba_client;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public BigDecimal getCfmb_TotalPaidIn() {
		return cfmb_TotalPaidIn;
	}

	public void setCfmb_TotalPaidIn(BigDecimal cfmb_TotalPaidIn) {
		this.cfmb_TotalPaidIn = cfmb_TotalPaidIn == null ? BigDecimal.ZERO
				: cfmb_TotalPaidIn.setScale(2, RoundingMode.HALF_UP);
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

	public void setOMoveFee(BigDecimal oMoveFee) {
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

	public BigDecimal getSheBaoFee() {
		return sheBaoFee;
	}

	public void setSheBaoFee(BigDecimal sheBaoFee) {
		this.sheBaoFee = sheBaoFee == null ? BigDecimal.ZERO : sheBaoFee
				.setScale(2, RoundingMode.HALF_UP);
	}

	public BigDecimal getHouseGjj() {
		return houseGjj;
	}

	public void setHouseGjj(BigDecimal houseGjj) {
		this.houseGjj = houseGjj == null ? BigDecimal.ZERO : houseGjj.setScale(
				2, RoundingMode.HALF_UP);
	}

	public Integer getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(Integer ownmonth) {
		this.ownmonth = ownmonth;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public void setoMoveFee(BigDecimal oMoveFee) {
		this.oMoveFee = oMoveFee == null ? BigDecimal.ZERO : oMoveFee.setScale(
				2, RoundingMode.HALF_UP);
	}


	public int getCfss_id() {
		return cfss_id;
	}


	public void setCfss_id(int cfss_id) {
		this.cfss_id = cfss_id;
	}
	
}
