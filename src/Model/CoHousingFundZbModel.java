package Model;

import java.util.Date;

public class CoHousingFundZbModel {
	// chfz_id
	private Integer chfz_id;
	// chfz_cohf_id
	private Integer chfz_cohf_id;
	// chfz_chfc_id
	private Integer chfz_chfc_id;
	// ownmonth
	private Integer ownmonth;
	// 专办员姓名
	private String chfz_name;
	// 专办员号
	private String chfz_number;
	// 固定电话
	private String chfz_tel;
	// 移动电话
	private String chfz_mobile;
	// 电子邮箱
	private String chfz_mail;
	// chfz_addname
	private String chfz_addname;
	// chfz_addtime
	private Date chfz_addtime;
	private String chfz_addtimeString;
	// 1 使用 0 注销 2 申报中
	private Integer chfz_state;
	// 申报时间
	private Date chfz_completetime;
	private String chfz_completetimeString;
	// 注销原因
	private String chfz_stop_reason;
	// 注销时间
	private Date chfz_stop_time;
	private String chfz_stop_timeString;
	private String chfz_numberAndName;
	private String remark;
	private int cfzc_id;
	private boolean flag = false;

	public CoHousingFundZbModel() {

	}

	
	public boolean getFlag() {
		return flag;
	}


	public void setFlag(boolean flag) {
		this.flag = flag;
	}


	public int getCfzc_id() {
		return cfzc_id;
	}

	public void setCfzc_id(int cfzc_id) {
		this.cfzc_id = cfzc_id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getChfz_numberAndName() {
		return chfz_numberAndName;
	}

	public void setChfz_numberAndName(String chfz_numberAndName) {
		this.chfz_numberAndName = chfz_numberAndName;
	}

	public final String getChfz_stop_reason() {
		return chfz_stop_reason;
	}

	public final Date getChfz_stop_time() {
		return chfz_stop_time;
	}

	public final String getChfz_stop_timeString() {
		return chfz_stop_timeString;
	}

	public final void setChfz_stop_reason(String chfz_stop_reason) {
		this.chfz_stop_reason = chfz_stop_reason;
	}

	public final void setChfz_stop_time(Date chfz_stop_time) {
		this.chfz_stop_time = chfz_stop_time;
	}

	public final void setChfz_stop_timeString(String chfz_stop_timeString) {
		this.chfz_stop_timeString = chfz_stop_timeString;
	}

	public final Integer getChfz_id() {
		return chfz_id;
	}

	public final Integer getChfz_cohf_id() {
		return chfz_cohf_id;
	}

	public final Integer getChfz_chfc_id() {
		return chfz_chfc_id;
	}

	public final Integer getOwnmonth() {
		return ownmonth;
	}

	public final String getChfz_name() {
		return chfz_name;
	}

	public final String getChfz_number() {
		return chfz_number;
	}

	public final String getChfz_tel() {
		return chfz_tel;
	}

	public final String getChfz_mobile() {
		return chfz_mobile;
	}

	public final String getChfz_mail() {
		return chfz_mail;
	}

	public final String getChfz_addname() {
		return chfz_addname;
	}

	public final Date getChfz_addtime() {
		return chfz_addtime;
	}

	public final Integer getChfz_state() {
		return chfz_state;
	}

	public final void setChfz_id(Integer chfz_id) {
		this.chfz_id = chfz_id;
	}

	public final void setChfz_cohf_id(Integer chfz_cohf_id) {
		this.chfz_cohf_id = chfz_cohf_id;
	}

	public final void setChfz_chfc_id(Integer chfz_chfc_id) {
		this.chfz_chfc_id = chfz_chfc_id;
	}

	public final void setOwnmonth(Integer ownmonth) {
		this.ownmonth = ownmonth;
	}

	public final void setChfz_name(String chfz_name) {
		this.chfz_name = chfz_name;
	}

	public final void setChfz_number(String chfz_number) {
		this.chfz_number = chfz_number;
	}

	public final void setChfz_tel(String chfz_tel) {
		this.chfz_tel = chfz_tel;
	}

	public final void setChfz_mobile(String chfz_mobile) {
		this.chfz_mobile = chfz_mobile;
	}

	public final void setChfz_mail(String chfz_mail) {
		this.chfz_mail = chfz_mail;
	}

	public final void setChfz_addname(String chfz_addname) {
		this.chfz_addname = chfz_addname;
	}

	public final void setChfz_addtime(Date chfz_addtime) {
		this.chfz_addtime = chfz_addtime;
	}

	public final void setChfz_state(Integer chfz_state) {
		this.chfz_state = chfz_state;
	}

	public Date getChfz_completetime() {
		return chfz_completetime;
	}

	public void setChfz_completetime(Date chfz_completetime) {
		this.chfz_completetime = chfz_completetime;
	}

	public String getChfz_completetimeString() {
		return chfz_completetimeString;
	}

	public void setChfz_completetimeString(String chfz_completetimeString) {
		this.chfz_completetimeString = chfz_completetimeString;
	}

	public String getChfz_addtimeString() {
		return chfz_addtimeString;
	}

	public void setChfz_addtimeString(String chfz_addtimeString) {
		this.chfz_addtimeString = chfz_addtimeString;
	}
}
