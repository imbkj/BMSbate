package Model;

import java.util.Date;

public class EmArchiveDatumModel {
	private Integer eada_id;
	private Integer cid;
	private Integer gid;
	private Integer ownmonth;
	private String eada_name;
	private String eada_filetype;
	private String eada_filearea;
	private String eada_fid;
	private String eada_idcard;
	private String eada_type;
	private String eada_fileplace;
	private String eada_rspaykind;
	private String eada_rsinvoice;
	private String eada_hjpaykind;
	private String eada_hjinvoice;
	private String eada_hj;
	private Integer eada_colhj;
	private String eada_colhjName;
	private String eada_deadline;
	private String eada_wtmode;
	private Integer eada_sdh;
	private Date eada_sdhdate;
	private String eada_orderdata;
	private String eada_archivename;
	private String eada_lendcause;
	private Date eada_lenddate;
	private String eada_lendpeople;
	private Date eada_returnarchivedate;
	private String eada_prove;
	private String eada_cause;
	private Date eada_drawprovedate;
	private String eada_drawprovepeople;
	private String eada_file;
	private Date eada_filedate;
	private Integer eada_zg;
	private Integer eada_bf;
	private Integer eada_dms;
	private String eada_rsetup;
	private Date eada_rdate;
	private Date eada_drawformdate;
	private String eada_drawformpeople;
	private Date eada_returnformdate;
	private Date eada_transactdate;
	private String eada_charge;
	private Date eada_chargedate;
	private Integer eada_deputy;
	private Date eada_checkoutdate;
	private String eada_checkoutmode;
	private String eada_checkoutreason;
	private String eada_checkoutsetup;
	private Date eada_paydate;
	private String eada_arrearageinfo;
	private String eada_fileplacefull;
	private String eada_client;
	private String eada_final;
	private String eada_finaldate;
	private Integer eada_state;
	private String eada_addtime;
	private String eada_addname;
	private Date eada_modtime;
	private String eada_modname;
	private Integer eada_msg;
	private String coba_company;
	private String coba_shortname;
	private String emba_name;
	private String emba_sex;
	private String emba_hjadd;
	private String emba_party;
	private String emba_degree;
	private String emba_school;
	private String emba_specialty;
	private String emba_marital;
	private String emba_graduation;
	private String emba_mobile;
	private String emba_email;
	private String emba_native;
	private String coba_client;
	private Integer num;
	private Date emba_outdate;
	private Date emba_indate;
	private String indate;
	private String emar_idcard;
	private Date emar_continuedeadline;
	private Date emar_prodate;
	private String emar_archiveplace;
	private String emar_address;
	private String opertext;// 操作显示字段
	private String remark;
	private Integer eada_doc;
	private Integer eada_tapr_id;
	private String eada_remark;
	private Date eada_senddate;
	private Date eada_backdate;
	private Date eada_accountdate;// 财务结算时间
	private String eada_accountinfo;// 财务结算情况
	private Integer eada_ifArrears;// 是否欠费，0表示欠费，1表示没有欠费
	private Integer cgli_startdate, cgli_stopdate;
	private String emba_idcard;
	private String eada_starddate, eada_stopdate;
	private String eada_savefiledate;
	private Date savefiledate;
	private String eada_backcase;
	private String eada_finadate;
	private String eada_finaname;
	private String eada_dnfee;
	private String eada_hkfee;
	private String eada_jyfee;
	private String eada_promisefee;
	private String eada_feeenddate;
	private Integer readstate;// 短信状态
	private Integer eada_iffee;
	private Integer symr_readstate;
	private boolean message;
	private boolean readState;
	private String eada_issdh;

	private Integer emba_id;
	
	private String eada_returndate,eada_returnname;

	public String getEmba_mobile() {
		return emba_mobile;
	}

	public void setEmba_mobile(String emba_mobile) {
		this.emba_mobile = emba_mobile;
	}

	public String getEmba_party() {
		return emba_party;
	}

	public void setEmba_party(String emba_party) {
		this.emba_party = emba_party;
	}

	public String getEmba_degree() {
		return emba_degree;
	}

	public void setEmba_degree(String emba_degree) {
		this.emba_degree = emba_degree;
	}

	public String getEmba_school() {
		return emba_school;
	}

	public void setEmba_school(String emba_school) {
		this.emba_school = emba_school;
	}

	public String getEmba_specialty() {
		return emba_specialty;
	}

	public void setEmba_specialty(String emba_specialty) {
		this.emba_specialty = emba_specialty;
	}

	public String getEmba_marital() {
		return emba_marital;
	}

	public void setEmba_marital(String emba_marital) {
		this.emba_marital = emba_marital;
	}

	public String getEmba_graduation() {
		return emba_graduation;
	}

	public void setEmba_graduation(String emba_graduation) {
		this.emba_graduation = emba_graduation;
	}

	public String getEmba_hjadd() {
		return emba_hjadd;
	}

	public void setEmba_hjadd(String emba_hjadd) {
		this.emba_hjadd = emba_hjadd;
	}

	public String getEmba_sex() {
		return emba_sex;
	}

	public void setEmba_sex(String emba_sex) {
		this.emba_sex = emba_sex;
	}

	public String getEada_colhjName() {
		return eada_colhjName;
	}

	public void setEada_colhjName(String eada_colhjName) {
		this.eada_colhjName = eada_colhjName;
	}

	public String getEmar_idcard() {
		return emar_idcard;
	}

	public void setEmar_idcard(String emar_idcard) {
		this.emar_idcard = emar_idcard;
	}

	public Date getEmar_prodate() {
		return emar_prodate;
	}

	public void setEmar_prodate(Date emar_prodate) {
		this.emar_prodate = emar_prodate;
	}

	public Integer getEada_id() {
		return eada_id;
	}

	public void setEada_id(Integer eada_id) {
		this.eada_id = eada_id;
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

	public Integer getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(Integer ownmonth) {
		this.ownmonth = ownmonth;
	}

	public String getEada_name() {
		return eada_name;
	}

	public void setEada_name(String eada_name) {
		this.eada_name = eada_name;
	}

	public String getEada_filetype() {
		return eada_filetype;
	}

	public void setEada_filetype(String eada_filetype) {
		this.eada_filetype = eada_filetype;
	}

	public String getEada_filearea() {
		return eada_filearea;
	}

	public void setEada_filearea(String eada_filearea) {
		this.eada_filearea = eada_filearea;
	}

	public String getEada_fid() {
		return eada_fid;
	}

	public void setEada_fid(String eada_fid) {
		this.eada_fid = eada_fid;
	}

	public String getEada_idcard() {
		return eada_idcard;
	}

	public void setEada_idcard(String eada_idcard) {
		this.eada_idcard = eada_idcard;
	}

	public String getEada_fileplace() {
		return eada_fileplace;
	}

	public void setEada_fileplace(String eada_fileplace) {
		this.eada_fileplace = eada_fileplace;
	}

	public String getEada_rspaykind() {
		return eada_rspaykind;
	}

	public void setEada_rspaykind(String eada_rspaykind) {
		this.eada_rspaykind = eada_rspaykind;
	}

	public String getEada_rsinvoice() {
		return eada_rsinvoice;
	}

	public void setEada_rsinvoice(String eada_rsinvoice) {
		this.eada_rsinvoice = eada_rsinvoice;
	}

	public String getEada_hjpaykind() {
		return eada_hjpaykind;
	}

	public void setEada_hjpaykind(String eada_hjpaykind) {
		this.eada_hjpaykind = eada_hjpaykind;
	}

	public String getEada_hjinvoice() {
		return eada_hjinvoice;
	}

	public void setEada_hjinvoice(String eada_hjinvoice) {
		this.eada_hjinvoice = eada_hjinvoice;
	}

	public Integer getEada_colhj() {
		return eada_colhj;
	}

	public void setEada_colhj(Integer eada_colhj) {
		this.eada_colhj = eada_colhj;
	}

	public String getEada_deadline() {
		return eada_deadline;
	}

	public void setEada_deadline(String eada_deadline) {
		this.eada_deadline = eada_deadline;
	}

	public String getEada_wtmode() {
		return eada_wtmode;
	}

	public void setEada_wtmode(String eada_wtmode) {
		this.eada_wtmode = eada_wtmode;
	}

	public Integer getEada_sdh() {
		return eada_sdh;
	}

	public void setEada_sdh(Integer eada_sdh) {
		this.eada_sdh = eada_sdh;
	}

	public Date getEada_sdhdate() {
		return eada_sdhdate;
	}

	public void setEada_sdhdate(Date eada_sdhdate) {
		this.eada_sdhdate = eada_sdhdate;
	}

	public String getEada_orderdata() {
		return eada_orderdata;
	}

	public void setEada_orderdata(String eada_orderdata) {
		this.eada_orderdata = eada_orderdata;
	}

	public String getEada_archivename() {
		return eada_archivename;
	}

	public void setEada_archivename(String eada_archivename) {
		this.eada_archivename = eada_archivename;
	}

	public String getEada_lendcause() {
		return eada_lendcause;
	}

	public void setEada_lendcause(String eada_lendcause) {
		this.eada_lendcause = eada_lendcause;
	}

	public Date getEada_lenddate() {
		return eada_lenddate;
	}

	public void setEada_lenddate(Date eada_lenddate) {
		this.eada_lenddate = eada_lenddate;
	}

	public String getEada_lendpeople() {
		return eada_lendpeople;
	}

	public void setEada_lendpeople(String eada_lendpeople) {
		this.eada_lendpeople = eada_lendpeople;
	}

	public Date getEada_returnarchivedate() {
		return eada_returnarchivedate;
	}

	public void setEada_returnarchivedate(Date eada_returnarchivedate) {
		this.eada_returnarchivedate = eada_returnarchivedate;
	}

	public String getEada_prove() {
		return eada_prove;
	}

	public void setEada_prove(String eada_prove) {
		this.eada_prove = eada_prove;
	}

	public String getEada_cause() {
		return eada_cause;
	}

	public void setEada_cause(String eada_cause) {
		this.eada_cause = eada_cause;
	}

	public Date getEada_drawprovedate() {
		return eada_drawprovedate;
	}

	public void setEada_drawprovedate(Date eada_drawprovedate) {
		this.eada_drawprovedate = eada_drawprovedate;
	}

	public String getEada_drawprovepeople() {
		return eada_drawprovepeople;
	}

	public void setEada_drawprovepeople(String eada_drawprovepeople) {
		this.eada_drawprovepeople = eada_drawprovepeople;
	}

	public String getEada_file() {
		return eada_file;
	}

	public void setEada_file(String eada_file) {
		this.eada_file = eada_file;
	}

	public Date getEada_filedate() {
		return eada_filedate;
	}

	public void setEada_filedate(Date eada_filedate) {
		this.eada_filedate = eada_filedate;
	}

	public Integer getEada_zg() {
		return eada_zg;
	}

	public void setEada_zg(Integer eada_zg) {
		this.eada_zg = eada_zg;
	}

	public Integer getEada_bf() {
		return eada_bf;
	}

	public void setEada_bf(Integer eada_bf) {
		this.eada_bf = eada_bf;
	}

	public Integer getEada_dms() {
		return eada_dms;
	}

	public void setEada_dms(Integer eada_dms) {
		this.eada_dms = eada_dms;
	}

	public String getEada_rsetup() {
		return eada_rsetup;
	}

	public void setEada_rsetup(String eada_rsetup) {
		this.eada_rsetup = eada_rsetup;
	}

	public Date getEada_rdate() {
		return eada_rdate;
	}

	public void setEada_rdate(Date eada_rdate) {
		this.eada_rdate = eada_rdate;
	}

	public Date getEada_drawformdate() {
		return eada_drawformdate;
	}

	public void setEada_drawformdate(Date eada_drawformdate) {
		this.eada_drawformdate = eada_drawformdate;
	}

	public String getEada_drawformpeople() {
		return eada_drawformpeople;
	}

	public void setEada_drawformpeople(String eada_drawformpeople) {
		this.eada_drawformpeople = eada_drawformpeople;
	}

	public Date getEada_returnformdate() {
		return eada_returnformdate;
	}

	public void setEada_returnformdate(Date eada_returnformdate) {
		this.eada_returnformdate = eada_returnformdate;
	}

	public Date getEada_transactdate() {
		return eada_transactdate;
	}

	public void setEada_transactdate(Date eada_transactdate) {
		this.eada_transactdate = eada_transactdate;
	}

	public String getEada_charge() {
		return eada_charge;
	}

	public void setEada_charge(String eada_charge) {
		this.eada_charge = eada_charge;
	}

	public Date getEada_chargedate() {
		return eada_chargedate;
	}

	public void setEada_chargedate(Date eada_chargedate) {
		this.eada_chargedate = eada_chargedate;
	}

	public Integer getEada_deputy() {
		return eada_deputy;
	}

	public void setEada_deputy(Integer eada_deputy) {
		this.eada_deputy = eada_deputy;
	}

	public Date getEada_checkoutdate() {
		return eada_checkoutdate;
	}

	public void setEada_checkoutdate(Date eada_checkoutdate) {
		this.eada_checkoutdate = eada_checkoutdate;
	}

	public String getEada_checkoutmode() {
		return eada_checkoutmode;
	}

	public void setEada_checkoutmode(String eada_checkoutmode) {
		this.eada_checkoutmode = eada_checkoutmode;
	}

	public String getEada_checkoutreason() {
		return eada_checkoutreason;
	}

	public void setEada_checkoutreason(String eada_checkoutreason) {
		this.eada_checkoutreason = eada_checkoutreason;
	}

	public String getEada_checkoutsetup() {
		return eada_checkoutsetup;
	}

	public void setEada_checkoutsetup(String eada_checkoutsetup) {
		this.eada_checkoutsetup = eada_checkoutsetup;
	}

	public Date getEada_paydate() {
		return eada_paydate;
	}

	public void setEada_paydate(Date eada_paydate) {
		this.eada_paydate = eada_paydate;
	}

	public String getEada_arrearageinfo() {
		return eada_arrearageinfo;
	}

	public void setEada_arrearageinfo(String eada_arrearageinfo) {
		this.eada_arrearageinfo = eada_arrearageinfo;
	}

	public String getEada_fileplacefull() {
		return eada_fileplacefull;
	}

	public void setEada_fileplacefull(String eada_fileplacefull) {
		this.eada_fileplacefull = eada_fileplacefull;
	}

	public String getEada_client() {
		return eada_client;
	}

	public void setEada_client(String eada_client) {
		this.eada_client = eada_client;
	}

	public String getEada_finaldate() {
		return eada_finaldate;
	}

	public void setEada_finaldate(String eada_finaldate) {
		this.eada_finaldate = eada_finaldate;
	}

	public Integer getEada_state() {
		return eada_state;
	}

	public void setEada_state(Integer eada_state) {
		this.eada_state = eada_state;
	}

	public String getEada_addtime() {
		return eada_addtime;
	}

	public void setEada_addtime(String eada_addtime) {
		this.eada_addtime = eada_addtime;
	}

	public String getEada_addname() {
		return eada_addname;
	}

	public void setEada_addname(String eada_addname) {
		this.eada_addname = eada_addname;
	}

	public Date getEada_modtime() {
		return eada_modtime;
	}

	public void setEada_modtime(Date eada_modtime) {
		this.eada_modtime = eada_modtime;
	}

	public String getEada_modname() {
		return eada_modname;
	}

	public void setEada_modname(String eada_modname) {
		this.eada_modname = eada_modname;
	}

	public Integer getEada_msg() {
		return eada_msg;
	}

	public void setEada_msg(Integer eada_msg) {
		this.eada_msg = eada_msg;
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

	public String getEmba_name() {
		return emba_name;
	}

	public void setEmba_name(String emba_name) {
		this.emba_name = emba_name;
	}

	public String getCoba_client() {
		return coba_client;
	}

	public void setCoba_client(String coba_client) {
		this.coba_client = coba_client;
	}

	public String getEada_type() {
		return eada_type;
	}

	public void setEada_type(String eada_type) {
		this.eada_type = eada_type;
	}

	public String getEada_final() {
		return eada_final;
	}

	public void setEada_final(String eada_final) {
		this.eada_final = eada_final;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Date getEmba_outdate() {
		return emba_outdate;
	}

	public void setEmba_outdate(Date emba_outdate) {
		this.emba_outdate = emba_outdate;
	}

	public Date getEmba_indate() {
		return emba_indate;
	}

	public void setEmba_indate(Date emba_indate) {
		this.emba_indate = emba_indate;
	}

	public Date getEmar_continuedeadline() {
		return emar_continuedeadline;
	}

	public void setEmar_continuedeadline(Date emar_continuedeadline) {
		this.emar_continuedeadline = emar_continuedeadline;
	}

	public String getEmar_archiveplace() {
		return emar_archiveplace;
	}

	public void setEmar_archiveplace(String emar_archiveplace) {
		this.emar_archiveplace = emar_archiveplace;
	}

	public String getEmar_address() {
		return emar_address;
	}

	public void setEmar_address(String emar_address) {
		this.emar_address = emar_address;
	}

	public String getOpertext() {
		return opertext;
	}

	public void setOpertext(String opertext) {
		this.opertext = opertext;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getEada_doc() {
		return eada_doc;
	}

	public void setEada_doc(Integer eada_doc) {
		this.eada_doc = eada_doc;
	}

	public Integer getEada_tapr_id() {
		return eada_tapr_id;
	}

	public void setEada_tapr_id(Integer eada_tapr_id) {
		this.eada_tapr_id = eada_tapr_id;
	}

	public String getEada_remark() {
		return eada_remark;
	}

	public void setEada_remark(String eada_remark) {
		this.eada_remark = eada_remark;
	}

	public Date getEada_senddate() {
		return eada_senddate;
	}

	public void setEada_senddate(Date eada_senddate) {
		this.eada_senddate = eada_senddate;
	}

	public Date getEada_backdate() {
		return eada_backdate;
	}

	public void setEada_backdate(Date eada_backdate) {
		this.eada_backdate = eada_backdate;
	}

	public Date getEada_accountdate() {
		return eada_accountdate;
	}

	public void setEada_accountdate(Date eada_accountdate) {
		this.eada_accountdate = eada_accountdate;
	}

	public String getEada_accountinfo() {
		return eada_accountinfo;
	}

	public void setEada_accountinfo(String eada_accountinfo) {
		this.eada_accountinfo = eada_accountinfo;
	}

	public Integer getEada_ifArrears() {
		return eada_ifArrears;
	}

	public void setEada_ifArrears(Integer eada_ifArrears) {
		this.eada_ifArrears = eada_ifArrears;
	}

	public String getEmba_native() {
		return emba_native;
	}

	public void setEmba_native(String emba_native) {
		this.emba_native = emba_native;
	}

	public Integer getCgli_startdate() {
		return cgli_startdate;
	}

	public void setCgli_startdate(Integer cgli_startdate) {
		this.cgli_startdate = cgli_startdate;
	}

	public Integer getCgli_stopdate() {
		return cgli_stopdate;
	}

	public void setCgli_stopdate(Integer cgli_stopdate) {
		this.cgli_stopdate = cgli_stopdate;
	}

	public String getEmba_idcard() {
		return emba_idcard;
	}

	public void setEmba_idcard(String emba_idcard) {
		this.emba_idcard = emba_idcard;
	}

	public String getEada_starddate() {
		return eada_starddate;
	}

	public void setEada_starddate(String eada_starddate) {
		this.eada_starddate = eada_starddate;
	}

	public String getEada_stopdate() {
		return eada_stopdate;
	}

	public void setEada_stopdate(String eada_stopdate) {
		this.eada_stopdate = eada_stopdate;
	}

	public String getEada_savefiledate() {
		return eada_savefiledate;
	}

	public void setEada_savefiledate(String eada_savefiledate) {
		this.eada_savefiledate = eada_savefiledate;
	}

	public String getEada_backcase() {
		return eada_backcase;
	}

	public void setEada_backcase(String eada_backcase) {
		this.eada_backcase = eada_backcase;
	}

	public String getEada_finadate() {
		return eada_finadate;
	}

	public void setEada_finadate(String eada_finadate) {
		this.eada_finadate = eada_finadate;
	}

	public String getEada_finaname() {
		return eada_finaname;
	}

	public void setEada_finaname(String eada_finaname) {
		this.eada_finaname = eada_finaname;
	}

	public String getEada_dnfee() {
		return eada_dnfee;
	}

	public void setEada_dnfee(String eada_dnfee) {
		this.eada_dnfee = eada_dnfee;
	}

	public String getEada_hkfee() {
		return eada_hkfee;
	}

	public void setEada_hkfee(String eada_hkfee) {
		this.eada_hkfee = eada_hkfee;
	}

	public String getEada_jyfee() {
		return eada_jyfee;
	}

	public void setEada_jyfee(String eada_jyfee) {
		this.eada_jyfee = eada_jyfee;
	}

	public String getEada_feeenddate() {
		return eada_feeenddate;
	}

	public void setEada_feeenddate(String eada_feeenddate) {
		this.eada_feeenddate = eada_feeenddate;
	}

	public Integer getReadstate() {
		return readstate;
	}

	public void setReadstate(Integer readstate) {
		this.readstate = readstate;
	}

	public Date getSavefiledate() {
		return savefiledate;
	}

	public void setSavefiledate(Date savefiledate) {
		this.savefiledate = savefiledate;
	}

	public Integer getEada_iffee() {
		return eada_iffee;
	}

	public void setEada_iffee(Integer eada_iffee) {
		this.eada_iffee = eada_iffee;
	}

	public Integer getSymr_readstate() {
		return symr_readstate;
	}

	public void setSymr_readstate(Integer symr_readstate) {
		this.symr_readstate = symr_readstate;
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

	public Integer getEmba_id() {
		return emba_id;
	}

	public void setEmba_id(Integer emba_id) {
		this.emba_id = emba_id;
	}

	public String getEada_hj() {
		return eada_hj;
	}

	public void setEada_hj(String eada_hj) {
		this.eada_hj = eada_hj;
	}

	public String getIndate() {
		return indate;
	}

	public void setIndate(String indate) {
		this.indate = indate;
	}

	public String getEmba_email() {
		return emba_email;
	}

	public void setEmba_email(String emba_email) {
		this.emba_email = emba_email;
	}

	public String getEada_promisefee() {
		return eada_promisefee;
	}

	public void setEada_promisefee(String eada_promisefee) {
		this.eada_promisefee = eada_promisefee;
	}

	public String getEada_returndate() {
		return eada_returndate;
	}

	public void setEada_returndate(String eada_returndate) {
		this.eada_returndate = eada_returndate;
	}

	public String getEada_returnname() {
		return eada_returnname;
	}

	public void setEada_returnname(String eada_returnname) {
		this.eada_returnname = eada_returnname;
	}

	public String getEada_issdh() {
		return eada_issdh;
	}

	public void setEada_issdh(String eada_issdh) {
		this.eada_issdh = eada_issdh;
	}
	
}
