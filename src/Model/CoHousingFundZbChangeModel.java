package Model;

import java.util.Date;

public class CoHousingFundZbChangeModel {
	// cfzc_id
	private Integer cfzc_id;
	// cfzc_chfz_id
	private Integer cfzc_chfz_id;
	// cfzc_cohf_id
	private Integer cfzc_cohf_id;
	// cfzc_chfc_id
	private Integer cfzc_chfc_id;
	// ownmonth
	private Integer ownmonth;
	// cfzc_addtype
	private String cfzc_addtype;
	// cfzc_name
	private String cfzc_name;
	// cfzc_number
	private String cfzc_number;
	// cfzc_tel
	private String cfzc_tel;
	// cfzc_mobile
	private String cfzc_mobile;
	// cfzc_mail
	private String cfzc_mail;
	// cfzc_addname
	private String cfzc_addname;
	// cfzc_addtime
	private Date cfzc_addtime;
	private String cfzc_addtimeString;
	// cfzc_state 1是未申报，2申报中 ， 3已申报，0退回
	private Integer cfzc_state;
	// cfzc_laststate
	private Integer cfzc_laststate;
	// cfzc_tzlstate
	private Integer cfzc_tzlstate;
	// cfzc_tapr_id
	private Integer cfzc_tapr_id;
	// cfzc_remark
	private String cfzc_remark;
	// 申报时间
	private String cfzc_completetime;
	private String cid;
	private String cfzc_cohf_company;
	private String cfzc_cohf_houseid;
	private String cfzc_statusString;
	private int msg_a;
	private int flag;
	private String backReason;
	private int cfzc_cfpc_id;
	
	
	
	public int getCfzc_cfpc_id() {
		return cfzc_cfpc_id;
	}



	public void setCfzc_cfpc_id(int cfzc_cfpc_id) {
		this.cfzc_cfpc_id = cfzc_cfpc_id;
	}



	public String getBackReason() {
		return backReason;
	}



	public void setBackReason(String backReason) {
		this.backReason = backReason;
	}



	public int getFlag() {
		return flag;
	}



	public void setFlag(int flag) {
		this.flag = flag;
	}



	public int getMsg_a() {
		return msg_a;
	}



	public void setMsg_a(int msg_a) {
		this.msg_a = msg_a;
	}



	public CoHousingFundZbChangeModel() {

	}

	
	
	public String getCfzc_statusString() {
		return cfzc_statusString;
	}



	public void setCfzc_statusString(String cfzc_statusString) {
		this.cfzc_statusString = cfzc_statusString;
	}



	public String getCfzc_cohf_houseid() {
		return cfzc_cohf_houseid;
	}


	public void setCfzc_cohf_houseid(String cfzc_cohf_houseid) {
		this.cfzc_cohf_houseid = cfzc_cohf_houseid;
	}


	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCfzc_cohf_company() {
		return cfzc_cohf_company;
	}

	public void setCfzc_cohf_company(String cfzc_cohf_company) {
		this.cfzc_cohf_company = cfzc_cohf_company;
	}

	public final Integer getCfzc_id() {
		return cfzc_id;
	}

	public final Integer getCfzc_chfz_id() {
		return cfzc_chfz_id;
	}

	public final Integer getCfzc_cohf_id() {
		return cfzc_cohf_id;
	}

	public final Integer getCfzc_chfc_id() {
		return cfzc_chfc_id;
	}

	public final Integer getOwnmonth() {
		return ownmonth;
	}

	public final String getCfzc_addtype() {
		return cfzc_addtype;
	}

	public final String getCfzc_name() {
		return cfzc_name;
	}

	public final String getCfzc_number() {
		return cfzc_number;
	}

	public final String getCfzc_tel() {
		return cfzc_tel;
	}

	public final String getCfzc_mobile() {
		return cfzc_mobile;
	}

	public final String getCfzc_mail() {
		return cfzc_mail;
	}

	public final String getCfzc_addname() {
		return cfzc_addname;
	}

	public final Date getCfzc_addtime() {
		return cfzc_addtime;
	}

	public final Integer getCfzc_state() {
		return cfzc_state;
	}

	public final Integer getCfzc_laststate() {
		return cfzc_laststate;
	}

	public final Integer getCfzc_tzlstate() {
		return cfzc_tzlstate;
	}

	public final Integer getCfzc_tapr_id() {
		return cfzc_tapr_id;
	}

	public final String getCfzc_remark() {
		return cfzc_remark;
	}

	public final void setCfzc_id(Integer cfzc_id) {
		this.cfzc_id = cfzc_id;
	}

	public final void setCfzc_chfz_id(Integer cfzc_chfz_id) {
		this.cfzc_chfz_id = cfzc_chfz_id;
	}

	public final void setCfzc_cohf_id(Integer cfzc_cohf_id) {
		this.cfzc_cohf_id = cfzc_cohf_id;
	}

	public final void setCfzc_chfc_id(Integer cfzc_chfc_id) {
		this.cfzc_chfc_id = cfzc_chfc_id;
	}

	public final void setOwnmonth(Integer ownmonth) {
		this.ownmonth = ownmonth;
	}

	public final void setCfzc_addtype(String cfzc_addtype) {
		this.cfzc_addtype = cfzc_addtype;
	}

	public final void setCfzc_name(String cfzc_name) {
		this.cfzc_name = cfzc_name;
	}

	public final void setCfzc_number(String cfzc_number) {
		this.cfzc_number = cfzc_number;
	}

	public final void setCfzc_tel(String cfzc_tel) {
		this.cfzc_tel = cfzc_tel;
	}

	public final void setCfzc_mobile(String cfzc_mobile) {
		this.cfzc_mobile = cfzc_mobile;
	}

	public final void setCfzc_mail(String cfzc_mail) {
		this.cfzc_mail = cfzc_mail;
	}

	public final void setCfzc_addname(String cfzc_addname) {
		this.cfzc_addname = cfzc_addname;
	}

	public final void setCfzc_addtime(Date cfzc_addtime) {
		this.cfzc_addtime = cfzc_addtime;
	}

	public final void setCfzc_state(Integer cfzc_state) {
		this.cfzc_state = cfzc_state;
	}

	public final void setCfzc_laststate(Integer cfzc_laststate) {
		this.cfzc_laststate = cfzc_laststate;
	}

	public final void setCfzc_tzlstate(Integer cfzc_tzlstate) {
		this.cfzc_tzlstate = cfzc_tzlstate;
	}

	public final void setCfzc_tapr_id(Integer cfzc_tapr_id) {
		this.cfzc_tapr_id = cfzc_tapr_id;
	}

	public final void setCfzc_remark(String cfzc_remark) {
		this.cfzc_remark = cfzc_remark;
	}

	public String getCfzc_completetime() {
		return cfzc_completetime;
	}

	public void setCfzc_completetime(String cfzc_completetime) {
		this.cfzc_completetime = cfzc_completetime;
	}

	public String getCfzc_addtimeString() {
		return cfzc_addtimeString;
	}

	public void setCfzc_addtimeString(String cfzc_addtimeString) {
		this.cfzc_addtimeString = cfzc_addtimeString;
	}
}
