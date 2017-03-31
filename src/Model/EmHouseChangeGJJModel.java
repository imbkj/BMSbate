package Model;

public class EmHouseChangeGJJModel {
	private Integer ehcg_id;
	private Integer gid;
	private Integer cid;
	private Integer ownmonth;
	private String ehcg_company;
	private String ehcg_name;
	private String ehcg_change;
	private String ehcg_content;
	private String ehcg_changeValue;
	private String ehcg_addtime;
	private String ehcg_addname;
	private Integer ehcg_ifdeclare;
	private String ehcg_declareTime;
	private String ehcg_declareName;
	private Integer ehcg_single;
	private String ehcg_shebaoID;
	private String ehcg_remark;
	private Integer ehcg_ifbase;
	private Integer ehcg_ifsb;
	private Integer ehcg_tapr_id;
	private Integer ehcg_tid;
	private String ehcg_modname;
	private String ehcg_modtime;
	private Boolean sameInfo;

	private Integer smwr_tid;
	private Integer symr_readstate;
	private String states;

	private String ehcg_name_p, ehcg_name_n, ehcg_idcardclass_p,
			ehcg_idcardclass_n, ehcg_idcard_p, ehcg_idcard_n, ehcg_sex_p,
			ehcg_sex_n, ehcg_hj_p, ehcg_hj_n, ehcg_sbid_p, ehcg_sbid_n,
			ehcg_marry_p, ehcg_marry_n, ehcg_wifename_p, ehcg_wifename_n,
			ehcg_wifeidcard_p, ehcg_wifeidcard_n, ehcg_title_p, ehcg_title_n,
			ehcg_degree_p, ehcg_degree_n, ehcg_email_p, ehcg_email_n,
			ehcg_phone_p, ehcg_phone_n;

	private String coba_shortname;
	private String coba_client;
	private String statename;
	private String emhu_companyid;
	private String emhu_houseid;
	private String emhu_idcard;
	private boolean checked;
	private Integer state; // 1:ifdeclare in ( 0,3,4);2:ifdeclare in
							// (0,1,2);3:ifdelcare in (1,2)
	private boolean message;
	private boolean readState;
	private Integer nowmonth;// 入职时判断是否有存在NOWMONTH月份以后的数据
	private String idList;

	private String addname;

	public Integer getEhcg_id() {
		return ehcg_id;
	}

	public void setEhcg_id(Integer ehcg_id) {
		this.ehcg_id = ehcg_id;
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

	public Integer getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(Integer ownmonth) {
		this.ownmonth = ownmonth;
	}

	public String getEhcg_company() {
		return ehcg_company;
	}

	public void setEhcg_company(String ehcg_company) {
		this.ehcg_company = ehcg_company;
	}

	public String getEhcg_name() {
		return ehcg_name;
	}

	public void setEhcg_name(String ehcg_name) {
		this.ehcg_name = ehcg_name;
	}

	public String getEhcg_change() {
		return ehcg_change;
	}

	public void setEhcg_change(String ehcg_change) {
		this.ehcg_change = ehcg_change;
	}

	public String getEhcg_content() {
		return ehcg_content;
	}

	public void setEhcg_content(String ehcg_content) {
		this.ehcg_content = ehcg_content;
	}

	public String getEhcg_changeValue() {
		return ehcg_changeValue;
	}

	public void setEhcg_changeValue(String ehcg_changeValue) {
		this.ehcg_changeValue = ehcg_changeValue;
	}

	public String getEhcg_addtime() {
		return ehcg_addtime;
	}

	public void setEhcg_addtime(String ehcg_addtime) {
		this.ehcg_addtime = ehcg_addtime;
	}

	public String getEhcg_addname() {
		return ehcg_addname;
	}

	public void setEhcg_addname(String ehcg_addname) {
		this.ehcg_addname = ehcg_addname;
	}

	public Integer getEhcg_ifdeclare() {
		return ehcg_ifdeclare;
	}

	public void setEhcg_ifdeclare(Integer ehcg_ifdeclare) {
		this.ehcg_ifdeclare = ehcg_ifdeclare;
	}

	public String getEhcg_declareTime() {
		return ehcg_declareTime;
	}

	public void setEhcg_declareTime(String ehcg_declareTime) {
		this.ehcg_declareTime = ehcg_declareTime;
	}

	public String getEhcg_declareName() {
		return ehcg_declareName;
	}

	public void setEhcg_declareName(String ehcg_declareName) {
		this.ehcg_declareName = ehcg_declareName;
	}

	public Integer getEhcg_single() {
		return ehcg_single;
	}

	public void setEhcg_single(Integer ehcg_single) {
		this.ehcg_single = ehcg_single;
	}

	public String getEhcg_shebaoID() {
		return ehcg_shebaoID;
	}

	public void setEhcg_shebaoID(String ehcg_shebaoID) {
		this.ehcg_shebaoID = ehcg_shebaoID;
	}

	public String getEhcg_remark() {
		return ehcg_remark;
	}

	public void setEhcg_remark(String ehcg_remark) {
		this.ehcg_remark = ehcg_remark;
	}

	public Integer getEhcg_ifbase() {
		return ehcg_ifbase;
	}

	public void setEhcg_ifbase(Integer ehcg_ifbase) {
		this.ehcg_ifbase = ehcg_ifbase;
	}

	public Integer getEhcg_ifsb() {
		return ehcg_ifsb;
	}

	public void setEhcg_ifsb(Integer ehcg_ifsb) {
		this.ehcg_ifsb = ehcg_ifsb;
	}

	public Integer getEhcg_tapr_id() {
		return ehcg_tapr_id;
	}

	public void setEhcg_tapr_id(Integer ehcg_tapr_id) {
		this.ehcg_tapr_id = ehcg_tapr_id;
	}

	public Integer getEhcg_tid() {
		return ehcg_tid;
	}

	public void setEhcg_tid(Integer ehcg_tid) {
		this.ehcg_tid = ehcg_tid;
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

	public String getEmhu_houseid() {
		return emhu_houseid;
	}

	public void setEmhu_houseid(String emhu_houseid) {
		this.emhu_houseid = emhu_houseid;
	}

	public String getEmhu_idcard() {
		return emhu_idcard;
	}

	public void setEmhu_idcard(String emhu_idcard) {
		this.emhu_idcard = emhu_idcard;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getEhcg_modname() {
		return ehcg_modname;
	}

	public void setEhcg_modname(String ehcg_modname) {
		this.ehcg_modname = ehcg_modname;
	}

	public String getEhcg_modtime() {
		return ehcg_modtime;
	}

	public void setEhcg_modtime(String ehcg_modtime) {
		this.ehcg_modtime = ehcg_modtime;
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

	public String getEmhu_companyid() {
		return emhu_companyid;
	}

	public void setEmhu_companyid(String emhu_companyid) {
		this.emhu_companyid = emhu_companyid;
	}

	public String getEhcg_name_p() {
		return ehcg_name_p;
	}

	public void setEhcg_name_p(String ehcg_name_p) {
		this.ehcg_name_p = ehcg_name_p;
	}

	public String getEhcg_name_n() {
		return ehcg_name_n;
	}

	public void setEhcg_name_n(String ehcg_name_n) {
		this.ehcg_name_n = ehcg_name_n;
	}

	public String getEhcg_idcardclass_p() {
		return ehcg_idcardclass_p;
	}

	public void setEhcg_idcardclass_p(String ehcg_idcardclass_p) {
		this.ehcg_idcardclass_p = ehcg_idcardclass_p;
	}

	public String getEhcg_idcardclass_n() {
		return ehcg_idcardclass_n;
	}

	public void setEhcg_idcardclass_n(String ehcg_idcardclass_n) {
		this.ehcg_idcardclass_n = ehcg_idcardclass_n;
	}

	public String getEhcg_idcard_p() {
		return ehcg_idcard_p;
	}

	public void setEhcg_idcard_p(String ehcg_idcard_p) {
		this.ehcg_idcard_p = ehcg_idcard_p;
	}

	public String getEhcg_idcard_n() {
		return ehcg_idcard_n;
	}

	public void setEhcg_idcard_n(String ehcg_idcard_n) {
		this.ehcg_idcard_n = ehcg_idcard_n;
	}

	public String getEhcg_sex_p() {
		return ehcg_sex_p;
	}

	public void setEhcg_sex_p(String ehcg_sex_p) {
		this.ehcg_sex_p = ehcg_sex_p;
	}

	public String getEhcg_sex_n() {
		return ehcg_sex_n;
	}

	public void setEhcg_sex_n(String ehcg_sex_n) {
		this.ehcg_sex_n = ehcg_sex_n;
	}

	public String getEhcg_hj_p() {
		return ehcg_hj_p;
	}

	public void setEhcg_hj_p(String ehcg_hj_p) {
		this.ehcg_hj_p = ehcg_hj_p;
	}

	public String getEhcg_hj_n() {
		return ehcg_hj_n;
	}

	public void setEhcg_hj_n(String ehcg_hj_n) {
		this.ehcg_hj_n = ehcg_hj_n;
	}

	public String getEhcg_sbid_p() {
		return ehcg_sbid_p;
	}

	public void setEhcg_sbid_p(String ehcg_sbid_p) {
		this.ehcg_sbid_p = ehcg_sbid_p;
	}

	public String getEhcg_sbid_n() {
		return ehcg_sbid_n;
	}

	public void setEhcg_sbid_n(String ehcg_sbid_n) {
		this.ehcg_sbid_n = ehcg_sbid_n;
	}

	public String getEhcg_marry_p() {
		return ehcg_marry_p;
	}

	public void setEhcg_marry_p(String ehcg_marry_p) {
		this.ehcg_marry_p = ehcg_marry_p;
	}

	public String getEhcg_marry_n() {
		return ehcg_marry_n;
	}

	public void setEhcg_marry_n(String ehcg_marry_n) {
		this.ehcg_marry_n = ehcg_marry_n;
	}

	public String getEhcg_wifename_p() {
		return ehcg_wifename_p;
	}

	public void setEhcg_wifename_p(String ehcg_wifename_p) {
		this.ehcg_wifename_p = ehcg_wifename_p;
	}

	public String getEhcg_wifename_n() {
		return ehcg_wifename_n;
	}

	public void setEhcg_wifename_n(String ehcg_wifename_n) {
		this.ehcg_wifename_n = ehcg_wifename_n;
	}

	public String getEhcg_wifeidcard_p() {
		return ehcg_wifeidcard_p;
	}

	public void setEhcg_wifeidcard_p(String ehcg_wifeidcard_p) {
		this.ehcg_wifeidcard_p = ehcg_wifeidcard_p;
	}

	public String getEhcg_wifeidcard_n() {
		return ehcg_wifeidcard_n;
	}

	public void setEhcg_wifeidcard_n(String ehcg_wifeidcard_n) {
		this.ehcg_wifeidcard_n = ehcg_wifeidcard_n;
	}

	public String getEhcg_title_p() {
		return ehcg_title_p;
	}

	public void setEhcg_title_p(String ehcg_title_p) {
		this.ehcg_title_p = ehcg_title_p;
	}

	public String getEhcg_title_n() {
		return ehcg_title_n;
	}

	public void setEhcg_title_n(String ehcg_title_n) {
		this.ehcg_title_n = ehcg_title_n;
	}

	public String getEhcg_degree_p() {
		return ehcg_degree_p;
	}

	public void setEhcg_degree_p(String ehcg_degree_p) {
		this.ehcg_degree_p = ehcg_degree_p;
	}

	public String getEhcg_degree_n() {
		return ehcg_degree_n;
	}

	public void setEhcg_degree_n(String ehcg_degree_n) {
		this.ehcg_degree_n = ehcg_degree_n;
	}

	public String getEhcg_email_p() {
		return ehcg_email_p;
	}

	public void setEhcg_email_p(String ehcg_email_p) {
		this.ehcg_email_p = ehcg_email_p;
	}

	public String getEhcg_email_n() {
		return ehcg_email_n;
	}

	public void setEhcg_email_n(String ehcg_email_n) {
		this.ehcg_email_n = ehcg_email_n;
	}

	public String getEhcg_phone_p() {
		return ehcg_phone_p;
	}

	public void setEhcg_phone_p(String ehcg_phone_p) {
		this.ehcg_phone_p = ehcg_phone_p;
	}

	public String getEhcg_phone_n() {
		return ehcg_phone_n;
	}

	public void setEhcg_phone_n(String ehcg_phone_n) {
		this.ehcg_phone_n = ehcg_phone_n;
	}

	public String getCoba_client() {
		return coba_client;
	}

	public void setCoba_client(String coba_client) {
		this.coba_client = coba_client;
	}

	public Integer getNowmonth() {
		return nowmonth;
	}

	public void setNowmonth(Integer nowmonth) {
		this.nowmonth = nowmonth;
	}

	public String getIdList() {
		return idList;
	}

	public void setIdList(String idList) {
		this.idList = idList;
	}

	public Boolean getSameInfo() {
		return sameInfo;
	}

	public void setSameInfo(Boolean sameInfo) {
		this.sameInfo = sameInfo;
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

	public String getStates() {
		return states;
	}

	public void setStates(String states) {
		this.states = states;
	}

	public String getAddname() {
		return addname;
	}

	public void setAddname(String addname) {
		this.addname = addname;
	}

}
