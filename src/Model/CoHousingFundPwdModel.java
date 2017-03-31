package Model;

import java.util.Date;

public class CoHousingFundPwdModel {
	// chfp_id
	private Integer chfp_id;
	// chfp_cohf_id
	private Integer chfp_cohf_id;
	// chfp_chfc_id
	private Integer chfp_chfc_id;
	// ownmonth
	private String ownmonth;
	// 专办员姓名
	private String chfp_zb_name;
	// 专办员号
	private String chfp_zb_number;
	// 年限
	private Integer chfp_yearlimit;
	// 开始日期
	private Date chfp_startdate;
	private String startDateString;
	// 到期日期
	private Date chfp_enddate;
	private String endDateString;

	// chfp_addname
	private String chfp_addname;
	// chfp_addtime
	private Date chfp_addtime;
	private String chfp_addtimeString;
	// 1 使用 0 不使用
	private Integer chfp_state;
	// 申报日期
	private String chfp_completetime;
	private String chfp_completetimeString;

	private int chfp_chfz_id;
	private String chfp_numberAndName;
	private int flag;
	private String chfp_pwd;

	
	public String getChfp_pwd() {
		return chfp_pwd;
	}

	public void setChfp_pwd(String chfp_pwd) {
		this.chfp_pwd = chfp_pwd;
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

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getChfp_numberAndName() {
		return chfp_numberAndName;
	}

	public void setChfp_numberAndName(String chfp_numberAndName) {
		this.chfp_numberAndName = chfp_numberAndName;
	}

	public CoHousingFundPwdModel() {

	}

	public int getChfp_chfz_id() {
		return chfp_chfz_id;
	}

	public void setChfp_chfz_id(int chfp_chfz_id) {
		this.chfp_chfz_id = chfp_chfz_id;
	}

	public final Integer getChfp_id() {
		return chfp_id;
	}

	public final Integer getChfp_cohf_id() {
		return chfp_cohf_id;
	}

	public final Integer getChfp_chfc_id() {
		return chfp_chfc_id;
	}

	public final String getChfp_zb_name() {
		return chfp_zb_name;
	}

	public final String getChfp_zb_number() {
		return chfp_zb_number;
	}

	public final Integer getChfp_yearlimit() {
		return chfp_yearlimit;
	}

	public final String getChfp_addname() {
		return chfp_addname;
	}

	public final Date getChfp_addtime() {
		return chfp_addtime;
	}

	public final Integer getChfp_state() {
		return chfp_state;
	}

	public final void setChfp_id(Integer chfp_id) {
		this.chfp_id = chfp_id;
	}

	public final void setChfp_cohf_id(Integer chfp_cohf_id) {
		this.chfp_cohf_id = chfp_cohf_id;
	}

	public final void setChfp_chfc_id(Integer chfp_chfc_id) {
		this.chfp_chfc_id = chfp_chfc_id;
	}

	public String getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(String ownmonth) {
		this.ownmonth = ownmonth;
	}

	public final void setChfp_zb_name(String chfp_zb_name) {
		this.chfp_zb_name = chfp_zb_name;
	}

	public final void setChfp_zb_number(String chfp_zb_number) {
		this.chfp_zb_number = chfp_zb_number;
	}

	public final void setChfp_yearlimit(Integer chfp_yearlimit) {
		this.chfp_yearlimit = chfp_yearlimit;
	}

	public Date getChfp_startdate() {
		return chfp_startdate;
	}

	public void setChfp_startdate(Date chfp_startdate) {
		this.chfp_startdate = chfp_startdate;
	}

	public Date getChfp_enddate() {
		return chfp_enddate;
	}

	public void setChfp_enddate(Date chfp_enddate) {
		this.chfp_enddate = chfp_enddate;
	}

	public final void setChfp_addname(String chfp_addname) {
		this.chfp_addname = chfp_addname;
	}

	public final void setChfp_addtime(Date chfp_addtime) {
		this.chfp_addtime = chfp_addtime;
	}

	public final void setChfp_state(Integer chfp_state) {
		this.chfp_state = chfp_state;
	}

	public String getChfp_completetime() {
		return chfp_completetime;
	}

	public void setChfp_completetime(String chfp_completetime) {
		this.chfp_completetime = chfp_completetime;
	}

	public String getChfp_addtimeString() {
		return chfp_addtimeString;
	}

	public void setChfp_addtimeString(String chfp_addtimeString) {
		this.chfp_addtimeString = chfp_addtimeString;
	}

	public String getChfp_completetimeString() {
		return chfp_completetimeString;
	}

	public void setChfp_completetimeString(String chfp_completetimeString) {
		this.chfp_completetimeString = chfp_completetimeString;
	}
}
