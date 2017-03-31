package Model;

import java.math.BigDecimal;
import java.util.Date;

public class EmDhModel {
	// / id
	private Integer id;
	// / cid
	private Integer cid;
	// / gid
	private Integer gid;
	// / emdh_zhtype
	private String emdh_zhtype;
	// / emdh_name
	private String emdh_name;
	// / emdh_company
	private String emdh_company;
	// / emdh_tel
	private String emdh_tel;
	// / emdh_mail
	private String emdh_mail;
	// / emdh_dhtype
	private String emdh_dhtype;
	// / emdh_client
	private String emdh_client;
	// / emdh_govefee
	private BigDecimal emdh_govefee;
	// / emdh_govetar
	private String emdh_govetar;
	// / emdh_servefee
	private BigDecimal emdh_servefee;
	// / emdh_servetar
	private String emdh_servetar;
	// / emdh_servetype
	private String emdh_servetype;
	// / emdh_isbackfee
	private Integer emdh_isbackfee;
	// / emdh_backreason
	private String emdh_backreason;
	// / emdh_remark
	private String emdh_remark;
	// / emdh_state
	private Integer emdh_state;
	// / emdh_addtime
	private Date emdh_addtime;
	// / emdh_addname
	private String emdh_addname;
	// / emdh_time1
	private Date emdh_time1;
	// / emdh_time2
	private Date emdh_time2;
	// / emdh_time3
	private Date emdh_time3;
	// / emdh_time4
	private Date emdh_time4;
	// / emdh_time5
	private Date emdh_time5;
	// / emdh_time6
	private Date emdh_time6;
	// / emdh_time7
	private Date emdh_time7;
	// / emdh_time8
	private Date emdh_time8;
	// / emdh_time9
	private Date emdh_time9;
	// / emdh_time10
	private Date emdh_time10;
	// / emdh_comtime
	private Date emdh_comtime;
	// / emdh_back
	private String emdh_back;
	// / emdh_backtime
	private Date emdh_backtime;
	// / emdh_endreason
	private String emdh_endreason;
	// / emdh_isnote
	private Integer emdh_isnote;
	// / emdh_state1
	private Integer emdh_state1;
	// / emdh_endname
	private String emdh_endname;
	// / emdh_endtime
	private Date emdh_endtime;
	// / emdh_lasttime
	private Date emdh_lasttime;
	// / emdh_idcard
	private String emdh_idcard;
	// / emdh_marital
	private String emdh_marital;
	// / emdh_education
	private String emdh_education;
	// / emdh_getfee
	private BigDecimal emdh_getfee;
	// / emdh_school
	private String emdh_school;
	// / emdh_time11
	private Date emdh_time11;
	// / emdh_time12
	private Date emdh_time12;
	private String emdh_ifdn;
	private String emdh_ifhk;
	private String states;
	private Integer emdh_taprid;
	private Integer emdh_doc;
	private Integer num;
	private Date emdh_linktime;
	private String emdh_linktype;
	private String emdh_linkname;
	private String emdh_linkcontent;
	private String emdh_goveservetype, emdh_newhj;
	private Integer emdh_hjtaprid;
	private BigDecimal emdh_fee = BigDecimal.ZERO;
	private BigDecimal emdh_totalfee = BigDecimal.ZERO;
	private String coba_shortname;
	private String emdh_fistfeetype;
	private String emdh_secondfeetype;
	private String emdh_inhjtype;
	private String emdh_proxytime,emdh_proxyname;
	private String emdh_shebaotype;
	private String emdh_feetime;
	private String emdh_feegeter;
	private String emdh_feeer;
	private String emdh_ifcompact;
	private Integer emdh_feestep;
	private Integer esiu_single;
	private Date proxytime;
	private BigDecimal nowfee=BigDecimal.ZERO;
	private String nowfeetype;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getEmdh_zhtype() {
		return emdh_zhtype;
	}

	public void setEmdh_zhtype(String emdh_zhtype) {
		this.emdh_zhtype = emdh_zhtype;
	}

	public String getEmdh_name() {
		return emdh_name;
	}

	public void setEmdh_name(String emdh_name) {
		this.emdh_name = emdh_name;
	}

	public String getEmdh_company() {
		return emdh_company;
	}

	public void setEmdh_company(String emdh_company) {
		this.emdh_company = emdh_company;
	}

	public String getEmdh_tel() {
		return emdh_tel;
	}

	public void setEmdh_tel(String emdh_tel) {
		this.emdh_tel = emdh_tel;
	}

	public String getEmdh_mail() {
		return emdh_mail;
	}

	public void setEmdh_mail(String emdh_mail) {
		this.emdh_mail = emdh_mail;
	}

	public String getEmdh_dhtype() {
		return emdh_dhtype;
	}

	public void setEmdh_dhtype(String emdh_dhtype) {
		this.emdh_dhtype = emdh_dhtype;
	}

	public String getEmdh_client() {
		return emdh_client;
	}

	public void setEmdh_client(String emdh_client) {
		this.emdh_client = emdh_client;
	}

	public String getEmdh_govetar() {
		return emdh_govetar;
	}

	public void setEmdh_govetar(String emdh_govetar) {
		this.emdh_govetar = emdh_govetar;
	}

	public String getEmdh_servetar() {
		return emdh_servetar;
	}

	public void setEmdh_servetar(String emdh_servetar) {
		this.emdh_servetar = emdh_servetar;
	}

	public String getEmdh_servetype() {
		return emdh_servetype;
	}

	public void setEmdh_servetype(String emdh_servetype) {
		this.emdh_servetype = emdh_servetype;
	}

	public Integer getEmdh_isbackfee() {
		return emdh_isbackfee;
	}

	public void setEmdh_isbackfee(Integer emdh_isbackfee) {
		this.emdh_isbackfee = emdh_isbackfee;
	}

	public String getEmdh_backreason() {
		return emdh_backreason;
	}

	public void setEmdh_backreason(String emdh_backreason) {
		this.emdh_backreason = emdh_backreason;
	}

	public String getEmdh_remark() {
		return emdh_remark;
	}

	public void setEmdh_remark(String emdh_remark) {
		this.emdh_remark = emdh_remark;
	}

	public Integer getEmdh_state() {
		return emdh_state;
	}

	public void setEmdh_state(Integer emdh_state) {
		this.emdh_state = emdh_state;
	}

	public Date getEmdh_addtime() {
		return emdh_addtime;
	}

	public void setEmdh_addtime(Date emdh_addtime) {
		this.emdh_addtime = emdh_addtime;
	}

	public String getEmdh_addname() {
		return emdh_addname;
	}

	public void setEmdh_addname(String emdh_addname) {
		this.emdh_addname = emdh_addname;
	}

	public Date getEmdh_time1() {
		return emdh_time1;
	}

	public void setEmdh_time1(Date emdh_time1) {
		this.emdh_time1 = emdh_time1;
	}

	public Date getEmdh_time2() {
		return emdh_time2;
	}

	public void setEmdh_time2(Date emdh_time2) {
		this.emdh_time2 = emdh_time2;
	}

	public Date getEmdh_time3() {
		return emdh_time3;
	}

	public void setEmdh_time3(Date emdh_time3) {
		this.emdh_time3 = emdh_time3;
	}

	public Date getEmdh_time4() {
		return emdh_time4;
	}

	public void setEmdh_time4(Date emdh_time4) {
		this.emdh_time4 = emdh_time4;
	}

	public Date getEmdh_time5() {
		return emdh_time5;
	}

	public void setEmdh_time5(Date emdh_time5) {
		this.emdh_time5 = emdh_time5;
	}

	public Date getEmdh_time6() {
		return emdh_time6;
	}

	public void setEmdh_time6(Date emdh_time6) {
		this.emdh_time6 = emdh_time6;
	}

	public Date getEmdh_time7() {
		return emdh_time7;
	}

	public void setEmdh_time7(Date emdh_time7) {
		this.emdh_time7 = emdh_time7;
	}

	public Date getEmdh_time8() {
		return emdh_time8;
	}

	public void setEmdh_time8(Date emdh_time8) {
		this.emdh_time8 = emdh_time8;
	}

	public Date getEmdh_time9() {
		return emdh_time9;
	}

	public void setEmdh_time9(Date emdh_time9) {
		this.emdh_time9 = emdh_time9;
	}

	public Date getEmdh_time10() {
		return emdh_time10;
	}

	public void setEmdh_time10(Date emdh_time10) {
		this.emdh_time10 = emdh_time10;
	}

	public Date getEmdh_comtime() {
		return emdh_comtime;
	}

	public void setEmdh_comtime(Date emdh_comtime) {
		this.emdh_comtime = emdh_comtime;
	}

	public String getEmdh_back() {
		return emdh_back;
	}

	public void setEmdh_back(String emdh_back) {
		this.emdh_back = emdh_back;
	}

	public Date getEmdh_backtime() {
		return emdh_backtime;
	}

	public void setEmdh_backtime(Date emdh_backtime) {
		this.emdh_backtime = emdh_backtime;
	}

	public String getEmdh_endreason() {
		return emdh_endreason;
	}

	public void setEmdh_endreason(String emdh_endreason) {
		this.emdh_endreason = emdh_endreason;
	}

	public Integer getEmdh_isnote() {
		return emdh_isnote;
	}

	public void setEmdh_isnote(Integer emdh_isnote) {
		this.emdh_isnote = emdh_isnote;
	}

	public Integer getEmdh_state1() {
		return emdh_state1;
	}

	public void setEmdh_state1(Integer emdh_state1) {
		this.emdh_state1 = emdh_state1;
	}

	public String getEmdh_endname() {
		return emdh_endname;
	}

	public void setEmdh_endname(String emdh_endname) {
		this.emdh_endname = emdh_endname;
	}

	public Date getEmdh_endtime() {
		return emdh_endtime;
	}

	public void setEmdh_endtime(Date emdh_endtime) {
		this.emdh_endtime = emdh_endtime;
	}

	public Date getEmdh_lasttime() {
		return emdh_lasttime;
	}

	public void setEmdh_lasttime(Date emdh_lasttime) {
		this.emdh_lasttime = emdh_lasttime;
	}

	public String getEmdh_idcard() {
		return emdh_idcard;
	}

	public void setEmdh_idcard(String emdh_idcard) {
		this.emdh_idcard = emdh_idcard;
	}

	public String getEmdh_marital() {
		return emdh_marital;
	}

	public void setEmdh_marital(String emdh_marital) {
		this.emdh_marital = emdh_marital;
	}

	public String getEmdh_education() {
		return emdh_education;
	}

	public void setEmdh_education(String emdh_education) {
		this.emdh_education = emdh_education;
	}

	public String getEmdh_school() {
		return emdh_school;
	}

	public void setEmdh_school(String emdh_school) {
		this.emdh_school = emdh_school;
	}

	public Date getEmdh_time11() {
		return emdh_time11;
	}

	public void setEmdh_time11(Date emdh_time11) {
		this.emdh_time11 = emdh_time11;
	}

	public Date getEmdh_time12() {
		return emdh_time12;
	}

	public void setEmdh_time12(Date emdh_time12) {
		this.emdh_time12 = emdh_time12;
	}

	public String getEmdh_ifdn() {
		return emdh_ifdn;
	}

	public void setEmdh_ifdn(String emdh_ifdn) {
		this.emdh_ifdn = emdh_ifdn;
	}

	public String getEmdh_ifhk() {
		return emdh_ifhk;
	}

	public void setEmdh_ifhk(String emdh_ifhk) {
		this.emdh_ifhk = emdh_ifhk;
	}

	public void setStates(String states) {
		this.states = states;
	}

	public String getStates() {
		return states;
	}

	public Integer getEmdh_taprid() {
		return emdh_taprid;
	}

	public void setEmdh_taprid(Integer emdh_taprid) {
		this.emdh_taprid = emdh_taprid;
	}

	public Integer getEmdh_doc() {
		return emdh_doc;
	}

	public void setEmdh_doc(Integer emdh_doc) {
		this.emdh_doc = emdh_doc;
	}

	public BigDecimal getEmdh_govefee() {
		return emdh_govefee;
	}

	public void setEmdh_govefee(BigDecimal emdh_govefee) {
		this.emdh_govefee = emdh_govefee;
	}

	public BigDecimal getEmdh_servefee() {
		return emdh_servefee;
	}

	public void setEmdh_servefee(BigDecimal emdh_servefee) {
		this.emdh_servefee = emdh_servefee;
	}

	public BigDecimal getEmdh_getfee() {
		return emdh_getfee;
	}

	public void setEmdh_getfee(BigDecimal emdh_getfee) {
		this.emdh_getfee = emdh_getfee;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Date getEmdh_linktime() {
		return emdh_linktime;
	}

	public void setEmdh_linktime(Date emdh_linktime) {
		this.emdh_linktime = emdh_linktime;
	}

	public String getEmdh_linktype() {
		return emdh_linktype;
	}

	public void setEmdh_linktype(String emdh_linktype) {
		this.emdh_linktype = emdh_linktype;
	}

	public String getEmdh_linkname() {
		return emdh_linkname;
	}

	public void setEmdh_linkname(String emdh_linkname) {
		this.emdh_linkname = emdh_linkname;
	}

	public String getEmdh_linkcontent() {
		return emdh_linkcontent;
	}

	public void setEmdh_linkcontent(String emdh_linkcontent) {
		this.emdh_linkcontent = emdh_linkcontent;
	}

	public String getEmdh_goveservetype() {
		return emdh_goveservetype;
	}

	public void setEmdh_goveservetype(String emdh_goveservetype) {
		this.emdh_goveservetype = emdh_goveservetype;
	}

	public Integer getEmdh_hjtaprid() {
		return emdh_hjtaprid;
	}

	public void setEmdh_hjtaprid(Integer emdh_hjtaprid) {
		this.emdh_hjtaprid = emdh_hjtaprid;
	}

	public String getEmdh_newhj() {
		return emdh_newhj;
	}

	public void setEmdh_newhj(String emdh_newhj) {
		this.emdh_newhj = emdh_newhj;
	}

	public BigDecimal getEmdh_fee() {
		return emdh_fee;
	}

	public void setEmdh_fee(BigDecimal emdh_fee) {
		this.emdh_fee = emdh_fee;
	}

	public BigDecimal getEmdh_totalfee() {
		return emdh_totalfee;
	}

	public void setEmdh_totalfee(BigDecimal emdh_totalfee) {
		this.emdh_totalfee = emdh_totalfee;
	}

	public String getCoba_shortname() {
		return coba_shortname;
	}

	public void setCoba_shortname(String coba_shortname) {
		this.coba_shortname = coba_shortname;
	}

	public String getEmdh_fistfeetype() {
		return emdh_fistfeetype;
	}

	public void setEmdh_fistfeetype(String emdh_fistfeetype) {
		this.emdh_fistfeetype = emdh_fistfeetype;
	}

	public String getEmdh_secondfeetype() {
		return emdh_secondfeetype;
	}

	public void setEmdh_secondfeetype(String emdh_secondfeetype) {
		this.emdh_secondfeetype = emdh_secondfeetype;
	}

	public String getEmdh_inhjtype() {
		return emdh_inhjtype;
	}

	public void setEmdh_inhjtype(String emdh_inhjtype) {
		this.emdh_inhjtype = emdh_inhjtype;
	}

	public String getEmdh_proxytime() {
		return emdh_proxytime;
	}

	public void setEmdh_proxytime(String emdh_proxytime) {
		this.emdh_proxytime = emdh_proxytime;
	}

	public String getEmdh_proxyname() {
		return emdh_proxyname;
	}

	public void setEmdh_proxyname(String emdh_proxyname) {
		this.emdh_proxyname = emdh_proxyname;
	}

	public String getEmdh_shebaotype() {
		return emdh_shebaotype;
	}

	public void setEmdh_shebaotype(String emdh_shebaotype) {
		this.emdh_shebaotype = emdh_shebaotype;
	}

	public String getEmdh_feetime() {
		return emdh_feetime;
	}

	public void setEmdh_feetime(String emdh_feetime) {
		this.emdh_feetime = emdh_feetime;
	}

	public String getEmdh_feegeter() {
		return emdh_feegeter;
	}

	public void setEmdh_feegeter(String emdh_feegeter) {
		this.emdh_feegeter = emdh_feegeter;
	}

	public String getEmdh_feeer() {
		return emdh_feeer;
	}

	public void setEmdh_feeer(String emdh_feeer) {
		this.emdh_feeer = emdh_feeer;
	}

	public String getEmdh_ifcompact() {
		return emdh_ifcompact;
	}

	public void setEmdh_ifcompact(String emdh_ifcompact) {
		this.emdh_ifcompact = emdh_ifcompact;
	}

	public Integer getEmdh_feestep() {
		return emdh_feestep;
	}

	public void setEmdh_feestep(Integer emdh_feestep) {
		this.emdh_feestep = emdh_feestep;
	}

	public Integer getEsiu_single() {
		return esiu_single;
	}

	public void setEsiu_single(Integer esiu_single) {
		this.esiu_single = esiu_single;
	}

	public Date getProxytime() {
		return proxytime;
	}

	public void setProxytime(Date proxytime) {
		this.proxytime = proxytime;
	}

	public BigDecimal getNowfee() {
		return nowfee;
	}

	public void setNowfee(BigDecimal nowfee) {
		this.nowfee = nowfee;
	}

	public String getNowfeetype() {
		return nowfeetype;
	}

	public void setNowfeetype(String nowfeetype) {
		this.nowfeetype = nowfeetype;
	}
}
