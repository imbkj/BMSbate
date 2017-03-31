package Model;

import java.util.Date;

public class EmResidencePermitChangeModel {
	// erpc_id
	private Integer erpc_id;
	// gid
	private Integer gid;
	// cid
	private Integer cid;
	// ownmonth
	private String ownmonth;
	// erpc_idcard
	private String erpc_idcard;
	// erpc_laststate
	private Integer erpc_laststate;
	// erpc_state
	private Integer erpc_state;
	// erpc_return_people
	private String erpc_return_people;
	// erpc_return_date
	private Date erpc_return_date;
	// erpc_return_reason
	private String erpc_return_reason;
	// erpc_addname
	private String erpc_addname;
	// erpc_addtime
	private Date erpc_addtime;
	// erpc_tapr_id
	private Integer erpc_tapr_id;

	// epcr_id
	private Integer epcr_id;
	// epcr_erpc_id
	private Integer epcr_erpc_id;
	// epcr_stateid
	private Integer epcr_stateid;
	// epcr_addname
	private String epcr_addname;
	// epcr_addtime
	private Date epcr_addtime;
	// epcr_statetime
	private Date epcr_statetime;
	// epcr_remark
	private String epcr_remark;

	private String coba_shortname;
	private String coba_company;
	private String coba_client;
	private String emba_name;
	private String emba_idcard;
	// 账户类型(0:中智户 1:独立户)
	private Integer zhtype;
	private String statename;
	private String sname;
	private String erin_hjtype;
	
	private String erpc_csd_ac_date,erpc_csd_ac_name,erpc_wd_oc_date,erpc_wd_oc_name;

	public Integer getErpc_id() {
		return erpc_id;
	}

	public void setErpc_id(Integer erpc_id) {
		this.erpc_id = erpc_id;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(String ownmonth) {
		this.ownmonth = ownmonth;
	}

	public String getErpc_idcard() {
		return erpc_idcard;
	}

	public void setErpc_idcard(String erpc_idcard) {
		this.erpc_idcard = erpc_idcard;
	}

	public Integer getErpc_laststate() {
		return erpc_laststate;
	}

	public void setErpc_laststate(Integer erpc_laststate) {
		this.erpc_laststate = erpc_laststate;
	}

	public Integer getErpc_state() {
		return erpc_state;
	}

	public void setErpc_state(Integer erpc_state) {
		this.erpc_state = erpc_state;
	}

	public String getErpc_return_people() {
		return erpc_return_people;
	}

	public void setErpc_return_people(String erpc_return_people) {
		this.erpc_return_people = erpc_return_people;
	}

	public Date getErpc_return_date() {
		return erpc_return_date;
	}

	public void setErpc_return_date(Date erpc_return_date) {
		this.erpc_return_date = erpc_return_date;
	}

	public String getErpc_return_reason() {
		return erpc_return_reason;
	}

	public void setErpc_return_reason(String erpc_return_reason) {
		this.erpc_return_reason = erpc_return_reason;
	}

	public String getErpc_addname() {
		return erpc_addname;
	}

	public void setErpc_addname(String erpc_addname) {
		this.erpc_addname = erpc_addname;
	}

	public Date getErpc_addtime() {
		return erpc_addtime;
	}

	public void setErpc_addtime(Date erpc_addtime) {
		this.erpc_addtime = erpc_addtime;
	}

	public Integer getEpcr_id() {
		return epcr_id;
	}

	public void setEpcr_id(Integer epcr_id) {
		this.epcr_id = epcr_id;
	}

	public Integer getEpcr_erpc_id() {
		return epcr_erpc_id;
	}

	public void setEpcr_erpc_id(Integer epcr_erpc_id) {
		this.epcr_erpc_id = epcr_erpc_id;
	}

	public Integer getEpcr_stateid() {
		return epcr_stateid;
	}

	public void setEpcr_stateid(Integer epcr_stateid) {
		this.epcr_stateid = epcr_stateid;
	}

	public String getEpcr_addname() {
		return epcr_addname;
	}

	public void setEpcr_addname(String epcr_addname) {
		this.epcr_addname = epcr_addname;
	}

	public Date getEpcr_addtime() {
		return epcr_addtime;
	}

	public void setEpcr_addtime(Date epcr_addtime) {
		this.epcr_addtime = epcr_addtime;
	}

	public Date getEpcr_statetime() {
		return epcr_statetime;
	}

	public void setEpcr_statetime(Date epcr_statetime) {
		this.epcr_statetime = epcr_statetime;
	}

	public String getEpcr_remark() {
		return epcr_remark;
	}

	public void setEpcr_remark(String epcr_remark) {
		this.epcr_remark = epcr_remark;
	}

	public Integer getErpc_tapr_id() {
		return erpc_tapr_id;
	}

	public void setErpc_tapr_id(Integer erpc_tapr_id) {
		this.erpc_tapr_id = erpc_tapr_id;
	}

	public Integer getZhtype() {
		return zhtype;
	}

	public void setZhtype(Integer zhtype) {
		this.zhtype = zhtype;
	}

	public String getEmba_name() {
		return emba_name;
	}

	public void setEmba_name(String emba_name) {
		this.emba_name = emba_name;
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

	public String getStatename() {
		return statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}

	public String getEmba_idcard() {
		return emba_idcard;
	}

	public void setEmba_idcard(String emba_idcard) {
		this.emba_idcard = emba_idcard;
	}

	public String getCoba_client() {
		return coba_client;
	}

	public void setCoba_client(String coba_client) {
		this.coba_client = coba_client;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getErpc_csd_ac_date() {
		return erpc_csd_ac_date;
	}

	public void setErpc_csd_ac_date(String erpc_csd_ac_date) {
		this.erpc_csd_ac_date = erpc_csd_ac_date;
	}

	public String getErpc_csd_ac_name() {
		return erpc_csd_ac_name;
	}

	public void setErpc_csd_ac_name(String erpc_csd_ac_name) {
		this.erpc_csd_ac_name = erpc_csd_ac_name;
	}

	public String getErpc_wd_oc_date() {
		return erpc_wd_oc_date;
	}

	public void setErpc_wd_oc_date(String erpc_wd_oc_date) {
		this.erpc_wd_oc_date = erpc_wd_oc_date;
	}

	public String getErpc_wd_oc_name() {
		return erpc_wd_oc_name;
	}

	public void setErpc_wd_oc_name(String erpc_wd_oc_name) {
		this.erpc_wd_oc_name = erpc_wd_oc_name;
	}

	public String getErin_hjtype() {
		return erin_hjtype;
	}

	public void setErin_hjtype(String erin_hjtype) {
		this.erin_hjtype = erin_hjtype;
	}

	
	
}
