package Model;

import java.util.Date;

public class CoShebaoModel {
	// cosb_id
	private Integer cosb_id;
	// cosb_csbc_id
	private Integer cosb_csbc_id;
	// CID
	private Integer cid;
	// Ownmonth
	private Integer ownmonth;
	// 申报类型(新开户、接管)
	private String cosb_addtype;
	// 企业联系电话座机
	private String cosb_cotel;
	// 镇
	private String cosb_town;
	// 街道
	private String cosb_street;
	// 企业注册地址
	private String cosb_regadd;
	// 工商注册号
	private String cosb_regid;
	// 社保分局名称
	private String cosb_sbadd;
	// 发工商执照日期
	private Date cosb_licdate;
	// 组织机构代码
	private String cosb_comid;
	// 组成形式
	private String cosb_forms;
	// 企业主管部门
	private String cosb_dep;
	// 经济类型
	private String cosb_ecoclass;
	// 行业类型一级
	private String cosb_iclassfirst;
	// 行业类型二级
	private String cosb_industryclass;
	// 法人姓名
	private String cosb_corname;
	// 法人身份证号
	private String cosb_coridcard;
	// 企业邮编
	private Integer cosb_pastcode;
	// 医疗机构(就近杜康点)
	private String cosb_heaname;
	// 银行全称(营业部全称)
	private String cosb_bankname;
	// 银行编码
	private String cosb_bankcode;
	// 银行账号
	private String cosb_bankacctid;
	// 企业所在辖区
	private String cosb_sorarea;
	// 单位社保编号
	private String cosb_sorid;
	// 社保业务主要提交方式
	private String cosb_submission;
	// 社保缴费方式
	private String cosb_paytype;
	// 收据送达方式
	private String cosb_payapply;
	// 经办人姓名
	private String cosb_attnname;
	// 经办人移动电话
	private String cosb_attnmobile;
	// 0、终止服务 1、服务中 2、申报中
	private Integer cosb_state;
	// 密码(6-10位长)
	private String cosb_pwd;
	// cosb_Remark
	private String cosb_remark;
	// cosb_Addtime
	private Date cosb_addtime;
	private String cosb_addtime1;
	// cosb_Addname
	private String cosb_addname;
	// 终止服务类型(账户注销、管理终止)
	private String cosb_stop_type;
	// 注销/终止原因
	private String cosb_stop_reason;
	// 失业比例
	private Double cosb_unemployment_per;
	// 工商比例
	private Double cosb_business_per;

	private String coba_company;
	private String coba_shortname;
	private String coba_client;
	private String statename;
	private String csbc_pdf;
	private String csbc_xls;
	private String csbc_image;

	private String cosb_cardbank;// 社保卡制卡银行大类（比如：中国银行，不具体到什么支行）
	private String cosb_branchbank;// 社保卡制卡银行大类（比如：中国银行，不具体到什么支行）
	private String cosb_ukey;//
	private String cosb_ukeytruetime;// ukey生效时间
	private String cosb_ukeyfailtime;// ukey失效时间
	private String cosb_comname;
	private Boolean inure;

	public Integer getCosb_id() {
		return cosb_id;
	}

	public void setCosb_id(Integer cosb_id) {
		this.cosb_id = cosb_id;
	}

	public Integer getCosb_csbc_id() {
		return cosb_csbc_id;
	}

	public void setCosb_csbc_id(Integer cosb_csbc_id) {
		this.cosb_csbc_id = cosb_csbc_id;
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

	public String getCosb_addtype() {
		return cosb_addtype;
	}

	public void setCosb_addtype(String cosb_addtype) {
		this.cosb_addtype = cosb_addtype;
	}

	public String getCosb_town() {
		return cosb_town;
	}

	public void setCosb_town(String cosb_town) {
		this.cosb_town = cosb_town;
	}

	public String getCosb_street() {
		return cosb_street;
	}

	public void setCosb_street(String cosb_street) {
		this.cosb_street = cosb_street;
	}

	public String getCosb_regadd() {
		return cosb_regadd;
	}

	public void setCosb_regadd(String cosb_regadd) {
		this.cosb_regadd = cosb_regadd;
	}

	public String getCosb_regid() {
		return cosb_regid;
	}

	public void setCosb_regid(String cosb_regid) {
		this.cosb_regid = cosb_regid;
	}

	public String getCosb_sbadd() {
		return cosb_sbadd;
	}

	public void setCosb_sbadd(String cosb_sbadd) {
		this.cosb_sbadd = cosb_sbadd;
	}

	public Date getCosb_licdate() {
		return cosb_licdate;
	}

	public void setCosb_licdate(Date cosb_licdate) {
		this.cosb_licdate = cosb_licdate;
	}

	public String getCosb_comid() {
		return cosb_comid;
	}

	public void setCosb_comid(String cosb_comid) {
		this.cosb_comid = cosb_comid;
	}

	public String getCosb_forms() {
		return cosb_forms;
	}

	public void setCosb_forms(String cosb_forms) {
		this.cosb_forms = cosb_forms;
	}

	public String getCosb_dep() {
		return cosb_dep;
	}

	public void setCosb_dep(String cosb_dep) {
		this.cosb_dep = cosb_dep;
	}

	public String getCosb_ecoclass() {
		return cosb_ecoclass;
	}

	public void setCosb_ecoclass(String cosb_ecoclass) {
		this.cosb_ecoclass = cosb_ecoclass;
	}

	public String getCosb_industryclass() {
		return cosb_industryclass;
	}

	public void setCosb_industryclass(String cosb_industryclass) {
		this.cosb_industryclass = cosb_industryclass;
	}

	public String getCosb_corname() {
		return cosb_corname;
	}

	public void setCosb_corname(String cosb_corname) {
		this.cosb_corname = cosb_corname;
	}

	public String getCosb_coridcard() {
		return cosb_coridcard;
	}

	public void setCosb_coridcard(String cosb_coridcard) {
		this.cosb_coridcard = cosb_coridcard;
	}

	public Integer getCosb_pastcode() {
		return cosb_pastcode;
	}

	public void setCosb_pastcode(Integer cosb_pastcode) {
		this.cosb_pastcode = cosb_pastcode;
	}

	public String getCosb_heaname() {
		return cosb_heaname;
	}

	public void setCosb_heaname(String cosb_heaname) {
		this.cosb_heaname = cosb_heaname;
	}

	public String getCosb_bankname() {
		return cosb_bankname;
	}

	public void setCosb_bankname(String cosb_bankname) {
		this.cosb_bankname = cosb_bankname;
	}

	public String getCosb_bankcode() {
		return cosb_bankcode;
	}

	public void setCosb_bankcode(String cosb_bankcode) {
		this.cosb_bankcode = cosb_bankcode;
	}

	public String getCosb_bankacctid() {
		return cosb_bankacctid;
	}

	public void setCosb_bankacctid(String cosb_bankacctid) {
		this.cosb_bankacctid = cosb_bankacctid;
	}

	public String getCosb_sorarea() {
		return cosb_sorarea;
	}

	public void setCosb_sorarea(String cosb_sorarea) {
		this.cosb_sorarea = cosb_sorarea;
	}

	public String getCosb_sorid() {
		return cosb_sorid;
	}

	public void setCosb_sorid(String cosb_sorid) {
		this.cosb_sorid = cosb_sorid;
	}

	public String getCosb_submission() {
		return cosb_submission;
	}

	public void setCosb_submission(String cosb_submission) {
		this.cosb_submission = cosb_submission;
	}

	public String getCosb_paytype() {
		return cosb_paytype;
	}

	public void setCosb_paytype(String cosb_paytype) {
		this.cosb_paytype = cosb_paytype;
	}

	public String getCosb_payapply() {
		return cosb_payapply;
	}

	public void setCosb_payapply(String cosb_payapply) {
		this.cosb_payapply = cosb_payapply;
	}

	public String getCosb_attnname() {
		return cosb_attnname;
	}

	public void setCosb_attnname(String cosb_attnname) {
		this.cosb_attnname = cosb_attnname;
	}

	public String getCosb_attnmobile() {
		return cosb_attnmobile;
	}

	public void setCosb_attnmobile(String cosb_attnmobile) {
		this.cosb_attnmobile = cosb_attnmobile;
	}

	public Integer getCosb_state() {
		return cosb_state;
	}

	public void setCosb_state(Integer cosb_state) {
		this.cosb_state = cosb_state;
	}

	public String getCosb_pwd() {
		return cosb_pwd;
	}

	public void setCosb_pwd(String cosb_pwd) {
		this.cosb_pwd = cosb_pwd;
	}

	public String getCosb_remark() {
		return cosb_remark;
	}

	public void setCosb_remark(String cosb_remark) {
		this.cosb_remark = cosb_remark;
	}

	public Date getCosb_addtime() {
		return cosb_addtime;
	}

	public void setCosb_addtime(Date cosb_addtime) {
		this.cosb_addtime = cosb_addtime;
	}

	public String getCosb_addname() {
		return cosb_addname;
	}

	public void setCosb_addname(String cosb_addname) {
		this.cosb_addname = cosb_addname;
	}

	public String getCosb_cotel() {
		return cosb_cotel;
	}

	public void setCosb_cotel(String cosb_cotel) {
		this.cosb_cotel = cosb_cotel;
	}

	public String getCosb_addtime1() {
		return cosb_addtime1;
	}

	public void setCosb_addtime1(String cosb_addtime1) {
		this.cosb_addtime1 = cosb_addtime1;
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

	public String getCoba_client() {
		return coba_client;
	}

	public void setCoba_client(String coba_client) {
		this.coba_client = coba_client;
	}

	public String getStatename() {
		return statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}

	public String getCsbc_pdf() {
		return csbc_pdf;
	}

	public void setCsbc_pdf(String csbc_pdf) {
		this.csbc_pdf = csbc_pdf;
	}

	public String getCsbc_xls() {
		return csbc_xls;
	}

	public void setCsbc_xls(String csbc_xls) {
		this.csbc_xls = csbc_xls;
	}

	public String getCsbc_image() {
		return csbc_image;
	}

	public void setCsbc_image(String csbc_image) {
		this.csbc_image = csbc_image;
	}

	public String getCosb_stop_type() {
		return cosb_stop_type;
	}

	public void setCosb_stop_type(String cosb_stop_type) {
		this.cosb_stop_type = cosb_stop_type;
	}

	public String getCosb_stop_reason() {
		return cosb_stop_reason;
	}

	public void setCosb_stop_reason(String cosb_stop_reason) {
		this.cosb_stop_reason = cosb_stop_reason;
	}

	public Double getCosb_unemployment_per() {
		return cosb_unemployment_per;
	}

	public void setCosb_unemployment_per(Double cosb_unemployment_per) {
		this.cosb_unemployment_per = (double) Math
				.round(cosb_unemployment_per * 1000) / 1000;
	}

	public Double getCosb_business_per() {
		return cosb_business_per;
	}

	public void setCosb_business_per(Double cosb_business_per) {
		this.cosb_business_per = (double) Math.round(cosb_business_per * 10000) / 10000;
	}

	public String getCosb_cardbank() {
		return cosb_cardbank;
	}

	public void setCosb_cardbank(String cosb_cardbank) {
		this.cosb_cardbank = cosb_cardbank;
	}

	public String getCosb_branchbank() {
		return cosb_branchbank;
	}

	public void setCosb_branchbank(String cosb_branchbank) {
		this.cosb_branchbank = cosb_branchbank;
	}

	public String getCosb_ukey() {
		return cosb_ukey;
	}

	public void setCosb_ukey(String cosb_ukey) {
		this.cosb_ukey = cosb_ukey;
	}

	public String getCosb_ukeytruetime() {
		return cosb_ukeytruetime;
	}

	public void setCosb_ukeytruetime(String cosb_ukeytruetime) {
		this.cosb_ukeytruetime = cosb_ukeytruetime;
	}

	public String getCosb_ukeyfailtime() {
		return cosb_ukeyfailtime;
	}

	public void setCosb_ukeyfailtime(String cosb_ukeyfailtime) {
		this.cosb_ukeyfailtime = cosb_ukeyfailtime;
	}

	public String getCosb_iclassfirst() {
		return cosb_iclassfirst;
	}

	public void setCosb_iclassfirst(String cosb_iclassfirst) {
		this.cosb_iclassfirst = cosb_iclassfirst;
	}

	public String getCosb_comname() {
		return cosb_comname;
	}

	public void setCosb_comname(String cosb_comname) {
		this.cosb_comname = cosb_comname;
	}

	public Boolean getInure() {
		return inure;
	}

	public void setInure(Boolean inure) {
		this.inure = inure;
	}

}
