package Model;

import java.util.Date;

public class CoHousingFundPwdChangeModel {
	// cfpc_id
	private Integer cfpc_id;
	// cfpc_chfp_id
	private Integer cfpc_chfp_id;
	// cfpc_cohf_id
	private Integer cfpc_cohf_id;
	// cfpc_chfc_id
	private Integer cfpc_chfc_id;
	// ownmonth
	private Integer ownmonth;
	// cfpc_addtype
	private String cfpc_addtype;
	// cfpc_zb_name
	private String cfpc_zb_name;
	// cfpc_zb_number
	private String cfpc_zb_number;
	// cfpc_yearlimit
	private Integer cfpc_yearlimit;
	// cfpc_startdate
	private String cfpc_startdate;
	private Date startdate;
	// cfpc_enddate
	private String cfpc_enddate;
	private Date enddate;
	// cfpc_addname
	private String cfpc_addname;
	// cfpc_addtime
	private Date cfpc_addtime;
	private String cpfc_addtimeString;
	// cfpc_state
	private Integer cfpc_state;
	// cfpc_laststate
	private Integer cfpc_laststate;
	// cfpc_tzlstate
	private Integer cfpc_tzlstate;
	// cfpc_tapr_id
	private Integer cfpc_tapr_id;
	// cfpc_remark
	private String cfpc_remark;
	// 申报时间
	private String cfpc_completetime;
	private Integer cfpc_chfz_id;
	private String cid;
	private String cfpc_addtimeString;
	private String cfzc_completetime;
	private String cfpc_cohf_houseid;
	private String cfpc_cohf_company;
	private String cfpc_statusString;
	private String mark; // 标记

	private String numberAndName;
	private String startDateString;
	private String endDateString;
	private String cohf_company;
	private String cohf_houseid;
	private int flag;
	private boolean bflag;
	private String tel;
	private String mobile;
	private String email;
	private int msg_a;
	private String backReason;
	private int cfpc_flag;
	
	
	
	
	

	public int getCfpc_flag() {
		return cfpc_flag;
	}

	public void setCfpc_flag(int cfpc_flag) {
		this.cfpc_flag = cfpc_flag;
	}

	public String getBackReason() {
		return backReason;
	}

	public void setBackReason(String backReason) {
		this.backReason = backReason;
	}

	public int getMsg_a() {
		return msg_a;
	}

	public void setMsg_a(int msg_a) {
		this.msg_a = msg_a;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean getBflag() {
		return bflag;
	}

	public void setBflag(boolean bflag) {
		this.bflag = bflag;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public Integer getCfpc_chfz_id() {
		return cfpc_chfz_id;
	}

	public void setCfpc_chfz_id(Integer cfpc_chfz_id) {
		this.cfpc_chfz_id = cfpc_chfz_id;
	}

	public String getNumberAndName() {
		return numberAndName;
	}

	public void setNumberAndName(String numberAndName) {
		this.numberAndName = numberAndName;
	}

	public String getCohf_company() {
		return cohf_company;
	}

	public void setCohf_company(String cohf_company) {
		this.cohf_company = cohf_company;
	}

	public String getCohf_houseid() {
		return cohf_houseid;
	}

	public void setCohf_houseid(String cohf_houseid) {
		this.cohf_houseid = cohf_houseid;
	}

	public String getStartDateString() {
		return startDateString;
	}

	public void setStartDateString(String startDateString) {
		this.startDateString = startDateString;
	}

	public String getEndDateString() {
		return endDateString;
	}

	public void setEndDateString(String endDateString) {
		this.endDateString = endDateString;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getCfpc_statusString() {
		return cfpc_statusString;
	}

	public void setCfpc_statusString(String cfpc_statusString) {
		this.cfpc_statusString = cfpc_statusString;
	}

	public String getCfpc_addtimeString() {
		return cfpc_addtimeString;
	}

	public void setCfpc_addtimeString(String cfpc_addtimeString) {
		this.cfpc_addtimeString = cfpc_addtimeString;
	}

	public String getCfzc_completetime() {
		return cfzc_completetime;
	}

	public void setCfzc_completetime(String cfzc_completetime) {
		this.cfzc_completetime = cfzc_completetime;
	}

	public String getCfpc_cohf_houseid() {
		return cfpc_cohf_houseid;
	}

	public void setCfpc_cohf_houseid(String cfpc_cohf_houseid) {
		this.cfpc_cohf_houseid = cfpc_cohf_houseid;
	}

	public String getCfpc_cohf_company() {
		return cfpc_cohf_company;
	}

	public void setCfpc_cohf_company(String cfpc_cohf_company) {
		this.cfpc_cohf_company = cfpc_cohf_company;
	}





	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public final Integer getCfpc_id() {
		return cfpc_id;
	}

	public final Integer getCfpc_chfp_id() {
		return cfpc_chfp_id;
	}

	public final Integer getCfpc_cohf_id() {
		return cfpc_cohf_id;
	}

	public final Integer getCfpc_chfc_id() {
		return cfpc_chfc_id;
	}

	public final Integer getOwnmonth() {
		return ownmonth;
	}

	public final String getCfpc_addtype() {
		return cfpc_addtype;
	}

	public final String getCfpc_zb_name() {
		return cfpc_zb_name;
	}

	public final String getCfpc_zb_number() {
		return cfpc_zb_number;
	}

	public final Integer getCfpc_yearlimit() {
		return cfpc_yearlimit;
	}

	public final String getCfpc_startdate() {
		return cfpc_startdate;
	}

	public final String getCfpc_enddate() {
		return cfpc_enddate;
	}

	public final String getCfpc_addname() {
		return cfpc_addname;
	}

	public final Date getCfpc_addtime() {
		return cfpc_addtime;
	}

	public final Integer getCfpc_state() {
		return cfpc_state;
	}

	public final Integer getCfpc_laststate() {
		return cfpc_laststate;
	}

	public final Integer getCfpc_tzlstate() {
		return cfpc_tzlstate;
	}

	public final Integer getCfpc_tapr_id() {
		return cfpc_tapr_id;
	}

	public final String getCfpc_remark() {
		return cfpc_remark;
	}

	public final void setCfpc_id(Integer cfpc_id) {
		this.cfpc_id = cfpc_id;
	}

	public final void setCfpc_chfp_id(Integer cfpc_chfp_id) {
		this.cfpc_chfp_id = cfpc_chfp_id;
	}

	public final void setCfpc_cohf_id(Integer cfpc_cohf_id) {
		this.cfpc_cohf_id = cfpc_cohf_id;
	}

	public final void setCfpc_chfc_id(Integer cfpc_chfc_id) {
		this.cfpc_chfc_id = cfpc_chfc_id;
	}

	public final void setOwnmonth(Integer ownmonth) {
		this.ownmonth = ownmonth;
	}

	public final void setCfpc_addtype(String cfpc_addtype) {
		this.cfpc_addtype = cfpc_addtype;
	}

	public final void setCfpc_zb_name(String cfpc_zb_name) {
		this.cfpc_zb_name = cfpc_zb_name;
	}

	public final void setCfpc_zb_number(String cfpc_zb_number) {
		this.cfpc_zb_number = cfpc_zb_number;
	}

	public final void setCfpc_yearlimit(Integer cfpc_yearlimit) {
		this.cfpc_yearlimit = cfpc_yearlimit;
	}

	public final void setCfpc_startdate(String cfpc_startdate) {
		this.cfpc_startdate = cfpc_startdate;
	}

	public final void setCfpc_enddate(String cfpc_enddate) {
		this.cfpc_enddate = cfpc_enddate;
	}

	public final void setCfpc_addname(String cfpc_addname) {
		this.cfpc_addname = cfpc_addname;
	}

	public final void setCfpc_addtime(Date cfpc_addtime) {
		this.cfpc_addtime = cfpc_addtime;
	}

	public final void setCfpc_state(Integer cfpc_state) {
		this.cfpc_state = cfpc_state;
	}

	public final void setCfpc_laststate(Integer cfpc_laststate) {
		this.cfpc_laststate = cfpc_laststate;
	}

	public final void setCfpc_tzlstate(Integer cfpc_tzlstate) {
		this.cfpc_tzlstate = cfpc_tzlstate;
	}

	public final void setCfpc_tapr_id(Integer cfpc_tapr_id) {
		this.cfpc_tapr_id = cfpc_tapr_id;
	}

	public final void setCfpc_remark(String cfpc_remark) {
		this.cfpc_remark = cfpc_remark;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public String getCfpc_completetime() {
		return cfpc_completetime;
	}

	public void setCfpc_completetime(String cfpc_completetime) {
		this.cfpc_completetime = cfpc_completetime;
	}

	public String getCpfc_addtimeString() {
		return cpfc_addtimeString;
	}

	public void setCpfc_addtimeString(String cpfc_addtimeString) {
		this.cpfc_addtimeString = cpfc_addtimeString;
	}
}
