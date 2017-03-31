package Model;

import java.math.BigDecimal;

public class EmHouseBJModel {
	// / id
	private Integer emhb_id;
	// / cid
	private Integer cid;
	// / gid
	private Integer gid;
	// / emhb_name
	private String emhb_name;
	// / emhb_company
	private String emhb_company;
	// / ownmonth
	private Integer ownmonth;
	// / emhb_Feemonth
	private Integer emhb_feemonth;
	// / emhb_startmonth
	private Integer emhb_startmonth;
	// / emhb_stopmonth
	private Integer emhb_stopmonth;
	// / emhb_houseid
	private String emhb_houseid;
	// / emhb_companyid
	private String emhb_companyid;
	// / emhb_idcard
	private String emhb_idcard;
	// / emhb_radix
	private BigDecimal emhb_radix;
	// / emhb_reason
	private String emhb_reason;
	// / emhb_total
	private BigDecimal emhb_total;
	// / emhb_cpp
	private Double emhb_cpp;
	// / emhb_opp
	private Double emhb_opp;
	// / emhb_addname
	private String emhb_addname;
	// / emhb_addtime
	private String emhb_addtime;
	// / emhb_declarename
	private String emhb_declarename;
	// / emhb_declaretime
	private String emhb_declaretime;
	// / emhb_ifdeclare
	private Integer emhb_ifdeclare;
	// / emhb_single
	private Integer emhb_single;
	// / emhb_flag
	private String emhb_flag;
	// / emhb_flagname
	private String emhb_flagname;
	// / emhb_Excelfile
	private String emhb_excelfile;
	// / emhb_remark
	private String emhb_remark;
	private Integer emhb_tapr_id;
	private String states;
	private String emhb_backreason;
	private String emhb_modtime;
	private String client;
	private boolean audit;
	private Integer dataState;// 数据状态:1:未申报/待确认,2:未申报/待确认/退回,3:未申报/申报中
	private boolean confirm;
	private boolean checked;
	private Integer num;
	private String dept;
	private String ifdeclare;
	private boolean message;
	private boolean readState;
	private Integer smwr_tid;
	private Integer symr_readstate;

	private Integer nowmonth;// 入职时判断是否有存在NOWMONTH月份以后的数据
	private Boolean sameInfo;
	private Boolean self; // 是否排除自身数据
	private Boolean onboard; // 入职数据
	private String addname;
	private Integer n;

	public Integer getEmhb_id() {
		return emhb_id;
	}

	public void setEmhb_id(Integer emhb_id) {
		this.emhb_id = emhb_id;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public String getEmhb_name() {
		return emhb_name;
	}

	public void setEmhb_name(String emhb_name) {
		this.emhb_name = emhb_name;
	}

	public String getEmhb_company() {
		return emhb_company;
	}

	public void setEmhb_company(String emhb_company) {
		this.emhb_company = emhb_company;
	}

	public Integer getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(Integer ownmonth) {
		this.ownmonth = ownmonth;
	}

	public Integer getEmhb_feemonth() {
		return emhb_feemonth;
	}

	public void setEmhb_feemonth(Integer emhb_feemonth) {
		this.emhb_feemonth = emhb_feemonth;
	}

	public Integer getEmhb_startmonth() {
		return emhb_startmonth;
	}

	public void setEmhb_startmonth(Integer emhb_startmonth) {
		this.emhb_startmonth = emhb_startmonth;
	}

	public Integer getEmhb_stopmonth() {
		return emhb_stopmonth;
	}

	public void setEmhb_stopmonth(Integer emhb_stopmonth) {
		this.emhb_stopmonth = emhb_stopmonth;
	}

	public String getEmhb_houseid() {
		return emhb_houseid;
	}

	public void setEmhb_houseid(String emhb_houseid) {
		this.emhb_houseid = emhb_houseid;
	}

	public String getEmhb_companyid() {
		return emhb_companyid;
	}

	public void setEmhb_companyid(String emhb_companyid) {
		this.emhb_companyid = emhb_companyid;
	}

	public String getEmhb_idcard() {
		return emhb_idcard;
	}

	public void setEmhb_idcard(String emhb_idcard) {
		this.emhb_idcard = emhb_idcard;
	}

	public BigDecimal getEmhb_radix() {
		return emhb_radix;
	}

	public void setEmhb_radix(BigDecimal emhb_radix) {
		this.emhb_radix = emhb_radix;
	}

	public String getEmhb_reason() {
		return emhb_reason;
	}

	public void setEmhb_reason(String emhb_reason) {
		this.emhb_reason = emhb_reason;
	}

	public BigDecimal getEmhb_total() {
		return emhb_total;
	}

	public void setEmhb_total(BigDecimal emhb_total) {

		emhb_total = emhb_total.setScale(2, BigDecimal.ROUND_HALF_UP);
		this.emhb_total = emhb_total;
	}

	public Double getEmhb_cpp() {
		return emhb_cpp;
	}

	public void setEmhb_cpp(Double emhb_cpp) {
		this.emhb_cpp = emhb_cpp;
	}

	public double getEmhb_opp() {
		return emhb_opp;
	}

	public void setEmhb_opp(Double emhb_opp) {
		this.emhb_opp = emhb_opp;
	}

	public String getEmhb_addname() {
		return emhb_addname;
	}

	public void setEmhb_addname(String emhb_addname) {
		this.emhb_addname = emhb_addname;
	}

	public String getEmhb_addtime() {
		return emhb_addtime;
	}

	public void setEmhb_addtime(String emhb_addtime) {
		this.emhb_addtime = emhb_addtime;
	}

	public String getEmhb_declarename() {
		return emhb_declarename;
	}

	public void setEmhb_declarename(String emhb_declarename) {
		this.emhb_declarename = emhb_declarename;
	}

	public String getEmhb_declaretime() {
		return emhb_declaretime;
	}

	public void setEmhb_declaretime(String emhb_declaretime) {
		this.emhb_declaretime = emhb_declaretime;
	}

	public Integer getEmhb_ifdeclare() {
		return emhb_ifdeclare;
	}

	public void setEmhb_ifdeclare(Integer emhb_ifdeclare) {
		this.emhb_ifdeclare = emhb_ifdeclare;
	}

	public Integer getEmhb_single() {
		return emhb_single;
	}

	public void setEmhb_single(Integer emhb_single) {
		this.emhb_single = emhb_single;
	}

	public String getEmhb_flag() {
		return emhb_flag;
	}

	public void setEmhb_flag(String emhb_flag) {
		this.emhb_flag = emhb_flag;
	}

	public String getEmhb_flagname() {
		return emhb_flagname;
	}

	public void setEmhb_flagname(String emhb_flagname) {
		this.emhb_flagname = emhb_flagname;
	}

	public String getEmhb_excelfile() {
		return emhb_excelfile;
	}

	public void setEmhb_excelfile(String emhb_excelfile) {
		this.emhb_excelfile = emhb_excelfile;
	}

	public String getEmhb_remark() {
		return emhb_remark;
	}

	public void setEmhb_remark(String emhb_remark) {
		this.emhb_remark = emhb_remark;
	}

	public String getStates() {
		return states;
	}

	public void setStates(String states) {
		this.states = states;
	}

	public String getEmhb_backreason() {
		return emhb_backreason;
	}

	public void setEmhb_backreason(String emhb_backreason) {
		this.emhb_backreason = emhb_backreason;
	}

	public String getEmhb_modtime() {
		return emhb_modtime;
	}

	public void setEmhb_modtime(String emhb_modtime) {
		this.emhb_modtime = emhb_modtime;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public boolean isAudit() {
		return audit;
	}

	public void setAudit(boolean audit) {
		this.audit = audit;
	}

	public Integer getEmhb_tapr_id() {
		return emhb_tapr_id;
	}

	public void setEmhb_tapr_id(Integer emhb_tapr_id) {
		this.emhb_tapr_id = emhb_tapr_id;
	}

	public Integer getDataState() {
		return dataState;
	}

	public void setDataState(Integer dataState) {
		this.dataState = dataState;
	}

	public boolean isConfirm() {
		return confirm;
	}

	public void setConfirm(boolean confirm) {
		this.confirm = confirm;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getIfdeclare() {
		return ifdeclare;
	}

	public void setIfdeclare(String ifdeclare) {
		this.ifdeclare = ifdeclare;
	}

	public boolean isMessage() {
		return message;
	}

	public void setMessage(boolean message) {
		this.message = message;
	}

	public boolean isReadState() {
		return readState;
	}

	public void setReadState(boolean readState) {
		this.readState = readState;
	}

	public Integer getNowmonth() {
		return nowmonth;
	}

	public void setNowmonth(Integer nowmonth) {
		this.nowmonth = nowmonth;
	}

	public Boolean getSameInfo() {
		return sameInfo;
	}

	public void setSameInfo(Boolean sameInfo) {
		this.sameInfo = sameInfo;
	}

	public Boolean getSelf() {
		return self;
	}

	public void setSelf(Boolean self) {
		this.self = self;
	}

	public Boolean getOnboard() {
		return onboard;
	}

	public void setOnboard(Boolean onboard) {
		this.onboard = onboard;
	}

	public Integer getSmwr_tid() {
		return smwr_tid;
	}

	public void setSmwr_tid(Integer smwr_tid) {
		this.smwr_tid = smwr_tid;
	}

	public Integer getSymr_readstate() {
		return symr_readstate;
	}

	public void setSymr_readstate(Integer symr_readstate) {
		this.symr_readstate = symr_readstate;
	}

	public String getAddname() {
		return addname;
	}

	public void setAddname(String addname) {
		this.addname = addname;
	}

	public Integer getN() {
		return n;
	}

	public void setN(Integer n) {
		this.n = n;
	}

}
