package Model;

import java.math.BigDecimal;
import java.util.Date;

public class EmBcSetupModel {
	// ebcs_id
	private Integer ebcs_id;
	// ebcs_hospital
	private String ebcs_hospital;
	// ebcs_name
	private String ebcs_name;
	// ebcs_phone
	private String ebcs_phone;
	// ebcs_inuredate
	private String ebcs_inuredate;
	private Date inuredate;
	// ebcs_indate
	private String ebcs_indate;
	private Date indate;
	// ebcs_flow
	private String ebcs_flow;
	// ebcs_balance
	private String ebcs_balance;

	private String ebcs_info;
	// ebcs_remark
	private String ebcs_remark;
	// ebcs_formula
	private String ebcs_formula;
	// ebcs_limit
	private BigDecimal ebcs_limit;
	// ebcs_tips
	private String ebcs_tips;
	// ebcs_state
	private Integer ebcs_state;
	private String state;
	private String stateName;
	// ebcs_pstate
	private Integer ebcs_pstate;
	private String pstate;
	// ebcs_addtime
	private String ebcs_addtime;
	// ebcs_addname
	private String ebcs_addname;
	private Integer ebsa_id;// 地址id
	private String ebsa_address;// 机构地址
	private Boolean checkName;

	public Integer getEbcs_id() {
		return ebcs_id;
	}

	public void setEbcs_id(Integer ebcs_id) {
		this.ebcs_id = ebcs_id;
	}

	public String getEbcs_hospital() {
		return ebcs_hospital;
	}

	public void setEbcs_hospital(String ebcs_hospital) {
		this.ebcs_hospital = ebcs_hospital;
	}

	public String getEbcs_name() {
		return ebcs_name;
	}

	public void setEbcs_name(String ebcs_name) {
		this.ebcs_name = ebcs_name;
	}

	public String getEbcs_phone() {
		return ebcs_phone;
	}

	public void setEbcs_phone(String ebcs_phone) {
		this.ebcs_phone = ebcs_phone;
	}

	public String getEbcs_inuredate() {
		return ebcs_inuredate;
	}

	public void setEbcs_inuredate(String ebcs_inuredate) {
		this.ebcs_inuredate = ebcs_inuredate;
	}

	public String getEbcs_indate() {
		return ebcs_indate;
	}

	public void setEbcs_indate(String ebcs_indate) {
		this.ebcs_indate = ebcs_indate;
	}

	public String getEbcs_flow() {
		return ebcs_flow;
	}

	public void setEbcs_flow(String ebcs_flow) {
		this.ebcs_flow = ebcs_flow;
	}

	public String getEbcs_balance() {
		return ebcs_balance;
	}

	public void setEbcs_balance(String ebcs_balance) {
		this.ebcs_balance = ebcs_balance;
	}

	public String getEbcs_remark() {
		return ebcs_remark;
	}

	public void setEbcs_remark(String ebcs_remark) {
		this.ebcs_remark = ebcs_remark;
	}

	public String getEbcs_formula() {
		return ebcs_formula;
	}

	public void setEbcs_formula(String ebcs_formula) {
		this.ebcs_formula = ebcs_formula;
	}

	public BigDecimal getEbcs_limit() {
		if (ebcs_limit != null) {
			ebcs_limit = ebcs_limit.setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		return ebcs_limit;
	}

	public void setEbcs_limit(BigDecimal ebcs_limit) {
		this.ebcs_limit = ebcs_limit;
	}

	public String getEbcs_tips() {
		return ebcs_tips;
	}

	public void setEbcs_tips(String ebcs_tips) {
		this.ebcs_tips = ebcs_tips;
	}

	public Integer getEbcs_state() {
		return ebcs_state;
	}

	public void setEbcs_state(Integer ebcs_state) {
		this.ebcs_state = ebcs_state;
	}

	public Integer getEbcs_pstate() {
		return ebcs_pstate;
	}

	public void setEbcs_pstate(Integer ebcs_pstate) {
		this.ebcs_pstate = ebcs_pstate;
	}

	public String getEbcs_addtime() {
		return ebcs_addtime;
	}

	public void setEbcs_addtime(String ebcs_addtime) {
		this.ebcs_addtime = ebcs_addtime;
	}

	public String getEbcs_addname() {
		return ebcs_addname;
	}

	public void setEbcs_addname(String ebcs_addname) {
		this.ebcs_addname = ebcs_addname;
	}

	public String getEbsa_address() {
		return ebsa_address;
	}

	public void setEbsa_address(String ebsa_address) {
		this.ebsa_address = ebsa_address;
	}

	public Integer getEbsa_id() {
		return ebsa_id;
	}

	public void setEbsa_id(Integer ebsa_id) {
		this.ebsa_id = ebsa_id;
	}

	public String getEbcs_info() {
		return ebcs_info;
	}

	public void setEbcs_info(String ebcs_info) {
		this.ebcs_info = ebcs_info;
	}

	public Boolean getCheckName() {
		return checkName;
	}

	public void setCheckName(Boolean checkName) {
		this.checkName = checkName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public Date getInuredate() {
		return inuredate;
	}

	public void setInuredate(Date inuredate) {
		this.inuredate = inuredate;
	}

	public Date getIndate() {
		return indate;
	}

	public void setIndate(Date indate) {
		this.indate = indate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPstate() {
		return pstate;
	}

	public void setPstate(String pstate) {
		this.pstate = pstate;
	}

}
