package Model;

import java.util.List;

import org.zkoss.zul.ListModelList;

public class EmHouseTakeCardInfoModel {
	// / Re_Id
	private Integer re_id;
	// / Gid
	private Integer gid;
	// / UserName
	private String username;
	// / Cid
	private Integer cid;
	// / CName
	private String cname;
	// / Re_Client
	private String re_client;
	// / Re_Ctime
	private String re_ctime;
	// / Re_AppTime
	private String re_apptime;
	// / IdCard
	private String idcard;
	// / Re_AccountType
	private String re_accounttype;
	// / Re_Type
	private String re_type;
	// / Re_GjjNo
	private String re_gjjno;
	// / Re_CardId
	private String re_cardid;
	// / Re_name
	private String re_name;
	// / Re_time
	private String re_time;
	// / Re_IfMessage
	private Integer re_ifmessage;
	// / Re_State
	private Integer re_state;
	// / Re_applyCase
	private String re_applycase;
	// / Re_CardState
	private String re_cardstate;
	// / Re_Band
	private String re_band;
	// / Re_CGjjNo
	private String re_cgjjno;
	// / Re_failCase
	private String re_failcase;
	// / Re_month
	private String re_month;
	// / Pla_client
	private String pla_client;
	// / Pla_clientTime
	private String pla_clienttime;
	// / Pla_ClientAssistant
	private String pla_clientassistant;
	// / Pla_ClientAssistantTime
	private String pla_clientassistanttime;
	// / Pla_WelfreAssistant
	private String pla_welfreassistant;
	// / Gjj_WelfreAssistantTime
	private String gjj_welfreassistanttime;
	// / Pla_bankpeople
	private String pla_bankpeople;
	// / Pla_ToBankTime
	private String pla_tobanktime;
	// / Pla_fl
	private String pla_fl;
	// / Pla_flTime
	private String pla_fltime;
	// / Pla_ReceiveName
	private String pla_receivename;
	// / Pla_ReceiveTime
	private String pla_receivetime;
	// / Pla_ReceliveClient
	private String pla_receliveclient;
	// / Pla_ClientTTime
	private String pla_clientttime;
	// / Pla_TakeType
	private String pla_taketype;
	// / otherData
	private String otherdata;
	// / allNo
	private String allno;
	// / upbank
	private String upbank;
	// / company
	private String company;
	// / shortname
	private String shortname;
	// / backtype
	private Integer backtype;
	// / typefl
	private Integer typefl;
	// / dept
	private String dept;
	// / inserttime
	private String addtime;
	// / ownmonth
	private String ownmonth;
	// / send_client
	private String send_client;
	private String State_Name;
	private Integer State_Id;
	private String addname;
	private Integer Re_taprid;
	private String coba_client;
	private String coba_company;
	private String coba_shortname;
	private String cohf_banklk;
	private Integer re_docId;
	private Integer num, gidnum, picnum;
	private String re_flsendname;
	private String esiu_computerid;
	private String emhu_idcardclass, emhu_idcard, emhu_degree, emhu_title,
			emhu_mobile, emhu_hj, emba_marital, emhu_wifename, emhu_wifeidcard;
	private Double emhu_radix;
	private String cosp_card_caliname, cosp_bsal_caliname;
	private String emba_idcard, emba_mobile, emhu_houseid;
	private Boolean cosp = false, bs_cosp = false;
	private String emba_sex;
	private String emba_email;
	private String re_contactsate;
	private String cosp_card_data_caliname;
	private String cohf_bankjc;

	private List<EmHouseCardBackInfoModel> list = new ListModelList<>();

	public Integer getRe_id() {
		return re_id;
	}

	public void setRe_id(Integer re_id) {
		this.re_id = re_id;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getRe_client() {
		return re_client;
	}

	public void setRe_client(String re_client) {
		this.re_client = re_client;
	}

	public String getRe_ctime() {
		return re_ctime;
	}

	public void setRe_ctime(String re_ctime) {
		this.re_ctime = re_ctime;
	}

	public String getRe_apptime() {
		return re_apptime;
	}

	public void setRe_apptime(String re_apptime) {
		this.re_apptime = re_apptime;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getRe_accounttype() {
		return re_accounttype;
	}

	public void setRe_accounttype(String re_accounttype) {
		this.re_accounttype = re_accounttype;
	}

	public String getRe_type() {
		return re_type;
	}

	public void setRe_type(String re_type) {
		this.re_type = re_type;
	}

	public String getRe_gjjno() {
		return re_gjjno;
	}

	public void setRe_gjjno(String re_gjjno) {
		this.re_gjjno = re_gjjno;
	}

	public String getRe_cardid() {
		return re_cardid;
	}

	public void setRe_cardid(String re_cardid) {
		this.re_cardid = re_cardid;
	}

	public String getRe_name() {
		return re_name;
	}

	public void setRe_name(String re_name) {
		this.re_name = re_name;
	}

	public String getRe_time() {
		return re_time;
	}

	public void setRe_time(String re_time) {
		this.re_time = re_time;
	}

	public Integer getRe_ifmessage() {
		return re_ifmessage;
	}

	public void setRe_ifmessage(Integer re_ifmessage) {
		this.re_ifmessage = re_ifmessage;
	}

	public Integer getRe_state() {
		return re_state;
	}

	public void setRe_state(Integer re_state) {
		this.re_state = re_state;
	}

	public String getRe_applycase() {
		return re_applycase;
	}

	public void setRe_applycase(String re_applycase) {
		this.re_applycase = re_applycase;
	}

	public String getRe_cardstate() {
		return re_cardstate;
	}

	public void setRe_cardstate(String re_cardstate) {
		this.re_cardstate = re_cardstate;
	}

	public String getRe_band() {
		return re_band;
	}

	public void setRe_band(String re_band) {
		this.re_band = re_band;
	}

	public String getRe_cgjjno() {
		return re_cgjjno;
	}

	public void setRe_cgjjno(String re_cgjjno) {
		this.re_cgjjno = re_cgjjno;
	}

	public String getRe_failcase() {
		return re_failcase;
	}

	public void setRe_failcase(String re_failcase) {
		this.re_failcase = re_failcase;
	}

	public String getRe_month() {
		return re_month;
	}

	public void setRe_month(String re_month) {
		this.re_month = re_month;
	}

	public String getPla_client() {
		return pla_client;
	}

	public void setPla_client(String pla_client) {
		this.pla_client = pla_client;
	}

	public String getPla_clienttime() {
		return pla_clienttime;
	}

	public void setPla_clienttime(String pla_clienttime) {
		this.pla_clienttime = pla_clienttime;
	}

	public String getPla_clientassistant() {
		return pla_clientassistant;
	}

	public void setPla_clientassistant(String pla_clientassistant) {
		this.pla_clientassistant = pla_clientassistant;
	}

	public String getPla_clientassistanttime() {
		return pla_clientassistanttime;
	}

	public void setPla_clientassistanttime(String pla_clientassistanttime) {
		this.pla_clientassistanttime = pla_clientassistanttime;
	}

	public String getPla_welfreassistant() {
		return pla_welfreassistant;
	}

	public void setPla_welfreassistant(String pla_welfreassistant) {
		this.pla_welfreassistant = pla_welfreassistant;
	}

	public String getGjj_welfreassistanttime() {
		return gjj_welfreassistanttime;
	}

	public void setGjj_welfreassistanttime(String gjj_welfreassistanttime) {
		this.gjj_welfreassistanttime = gjj_welfreassistanttime;
	}

	public String getPla_bankpeople() {
		return pla_bankpeople;
	}

	public void setPla_bankpeople(String pla_bankpeople) {
		this.pla_bankpeople = pla_bankpeople;
	}

	public String getPla_tobanktime() {
		return pla_tobanktime;
	}

	public void setPla_tobanktime(String pla_tobanktime) {
		this.pla_tobanktime = pla_tobanktime;
	}

	public String getPla_fl() {
		return pla_fl;
	}

	public void setPla_fl(String pla_fl) {
		this.pla_fl = pla_fl;
	}

	public String getPla_fltime() {
		return pla_fltime;
	}

	public void setPla_fltime(String pla_fltime) {
		this.pla_fltime = pla_fltime;
	}

	public String getPla_receivename() {
		return pla_receivename;
	}

	public void setPla_receivename(String pla_receivename) {
		this.pla_receivename = pla_receivename;
	}

	public String getPla_receivetime() {
		return pla_receivetime;
	}

	public void setPla_receivetime(String pla_receivetime) {
		this.pla_receivetime = pla_receivetime;
	}

	public String getPla_receliveclient() {
		return pla_receliveclient;
	}

	public void setPla_receliveclient(String pla_receliveclient) {
		this.pla_receliveclient = pla_receliveclient;
	}

	public String getPla_clientttime() {
		return pla_clientttime;
	}

	public void setPla_clientttime(String pla_clientttime) {
		this.pla_clientttime = pla_clientttime;
	}

	public String getPla_taketype() {
		return pla_taketype;
	}

	public void setPla_taketype(String pla_taketype) {
		this.pla_taketype = pla_taketype;
	}

	public String getOtherdata() {
		return otherdata;
	}

	public void setOtherdata(String otherdata) {
		this.otherdata = otherdata;
	}

	public String getAllno() {
		return allno;
	}

	public void setAllno(String allno) {
		this.allno = allno;
	}

	public String getUpbank() {
		return upbank;
	}

	public void setUpbank(String upbank) {
		this.upbank = upbank;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public Integer getBacktype() {
		return backtype;
	}

	public void setBacktype(Integer backtype) {
		this.backtype = backtype;
	}

	public Integer getTypefl() {
		return typefl;
	}

	public void setTypefl(Integer typefl) {
		this.typefl = typefl;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(String ownmonth) {
		this.ownmonth = ownmonth;
	}

	public String getSend_client() {
		return send_client;
	}

	public void setSend_client(String send_client) {
		this.send_client = send_client;
	}

	public String getState_Name() {
		return State_Name;
	}

	public void setState_Name(String state_Name) {
		State_Name = state_Name;
	}

	public Integer getState_Id() {
		return State_Id;
	}

	public void setState_Id(Integer state_Id) {
		State_Id = state_Id;
	}

	public String getAddname() {
		return addname;
	}

	public void setAddname(String addname) {
		this.addname = addname;
	}

	public Integer getRe_taprid() {
		return Re_taprid;
	}

	public void setRe_taprid(Integer re_taprid) {
		Re_taprid = re_taprid;
	}

	public String getCoba_client() {
		return coba_client;
	}

	public void setCoba_client(String coba_client) {
		this.coba_client = coba_client;
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

	public String getCohf_banklk() {
		return cohf_banklk;
	}

	public void setCohf_banklk(String cohf_banklk) {
		this.cohf_banklk = cohf_banklk;
	}

	public Integer getRe_docId() {
		return re_docId;
	}

	public void setRe_docId(Integer re_docId) {
		this.re_docId = re_docId;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getGidnum() {
		return gidnum;
	}

	public void setGidnum(Integer gidnum) {
		this.gidnum = gidnum;
	}

	public Integer getPicnum() {
		return picnum;
	}

	public void setPicnum(Integer picnum) {
		this.picnum = picnum;
	}

	public String getRe_flsendname() {
		return re_flsendname;
	}

	public void setRe_flsendname(String re_flsendname) {
		this.re_flsendname = re_flsendname;
	}

	public String getEsiu_computerid() {
		return esiu_computerid;
	}

	public void setEsiu_computerid(String esiu_computerid) {
		this.esiu_computerid = esiu_computerid;
	}

	public String getEmhu_idcardclass() {
		return emhu_idcardclass;
	}

	public void setEmhu_idcardclass(String emhu_idcardclass) {
		this.emhu_idcardclass = emhu_idcardclass;
	}

	public String getEmhu_idcard() {
		return emhu_idcard;
	}

	public void setEmhu_idcard(String emhu_idcard) {
		this.emhu_idcard = emhu_idcard;
	}

	public String getEmhu_degree() {
		return emhu_degree;
	}

	public void setEmhu_degree(String emhu_degree) {
		this.emhu_degree = emhu_degree;
	}

	public String getEmhu_title() {
		return emhu_title;
	}

	public void setEmhu_title(String emhu_title) {
		this.emhu_title = emhu_title;
	}

	public String getEmhu_mobile() {
		return emhu_mobile;
	}

	public void setEmhu_mobile(String emhu_mobile) {
		this.emhu_mobile = emhu_mobile;
	}

	public String getEmhu_hj() {
		return emhu_hj;
	}

	public void setEmhu_hj(String emhu_hj) {
		this.emhu_hj = emhu_hj;
	}

	public String getEmba_marital() {
		return emba_marital;
	}

	public void setEmba_marital(String emba_marital) {
		this.emba_marital = emba_marital;
	}

	public String getEmhu_wifename() {
		return emhu_wifename;
	}

	public void setEmhu_wifename(String emhu_wifename) {
		this.emhu_wifename = emhu_wifename;
	}

	public String getEmhu_wifeidcard() {
		return emhu_wifeidcard;
	}

	public void setEmhu_wifeidcard(String emhu_wifeidcard) {
		this.emhu_wifeidcard = emhu_wifeidcard;
	}

	public Double getEmhu_radix() {
		return emhu_radix;
	}

	public void setEmhu_radix(Double emhu_radix) {
		this.emhu_radix = emhu_radix;
	}

	public String getCosp_card_caliname() {
		return cosp_card_caliname;
	}

	public void setCosp_card_caliname(String cosp_card_caliname) {
		if (cosp_card_caliname != null && cosp_card_caliname.contains("指定联系人")) {
			setCosp(true);
		} else {
			setCosp(false);
		}
		this.cosp_card_caliname = cosp_card_caliname;
	}

	public String getCosp_bsal_caliname() {
		return cosp_bsal_caliname;
	}

	public void setCosp_bsal_caliname(String cosp_bsal_caliname) {
		this.cosp_bsal_caliname = cosp_bsal_caliname;
	}

	public String getEmba_idcard() {
		return emba_idcard;
	}

	public void setEmba_idcard(String emba_idcard) {
		this.emba_idcard = emba_idcard;
	}

	public String getEmba_mobile() {
		return emba_mobile;
	}

	public void setEmba_mobile(String emba_mobile) {
		this.emba_mobile = emba_mobile;
	}

	public String getEmhu_houseid() {
		return emhu_houseid;
	}

	public void setEmhu_houseid(String emhu_houseid) {
		this.emhu_houseid = emhu_houseid;
	}

	public Boolean getCosp() {
		return cosp;
	}

	public void setCosp(Boolean cosp) {
		this.cosp = cosp;
	}

	public String getEmba_sex() {
		return emba_sex;
	}

	public void setEmba_sex(String emba_sex) {
		this.emba_sex = emba_sex;
	}

	public String getEmba_email() {
		return emba_email;
	}

	public void setEmba_email(String emba_email) {
		this.emba_email = emba_email;
	}

	public String getRe_contactsate() {
		return re_contactsate;
	}

	public void setRe_contactsate(String re_contactsate) {
		this.re_contactsate = re_contactsate;
	}

	public String getCosp_card_data_caliname() {
		return cosp_card_data_caliname;
	}

	public void setCosp_card_data_caliname(String cosp_card_data_caliname) {
		if (cosp_card_data_caliname != null
				&& cosp_card_data_caliname.contains("指定联系人")) {
			setBs_cosp(true);
		} else {
			setBs_cosp(false);
		}
		this.cosp_card_data_caliname = cosp_card_data_caliname;
	}

	public Boolean getBs_cosp() {
		return bs_cosp;
	}

	public void setBs_cosp(Boolean bs_cosp) {
		this.bs_cosp = bs_cosp;
	}

	public String getCohf_bankjc() {
		return cohf_bankjc;
	}

	public void setCohf_bankjc(String cohf_bankjc) {
		this.cohf_bankjc = cohf_bankjc;
	}

	public List<EmHouseCardBackInfoModel> getList() {
		return list;
	}

	public void setList(List<EmHouseCardBackInfoModel> list) {
		this.list = list;
	}

}
