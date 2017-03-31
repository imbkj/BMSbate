package Model;

import java.sql.Timestamp;

public class CoShebaoChangeModel {
	// csbc_id
	private Integer csbc_id;
	// CID
	private Integer cid;
	// Ownmonth
	private Integer ownmonth;
	// 申报类型(新开户、接管)
	private String csbc_addtype;
	// 企业联系电话座机
	private String csbc_cotel;
	// 镇
	private String csbc_town;
	// 街道
	private String csbc_street;
	// 企业注册地址
	private String csbc_regadd;
	// 工商注册号
	private String csbc_regid;
	// 社保分局名称
	private String csbc_sbadd;
	// 发工商执照日期
	private Timestamp csbc_licdate;
	// 组织机构代码
	private String csbc_comid;
	// 组成形式
	private String csbc_forms;
	// 企业主管部门
	private String csbc_dep;
	// 经济类型
	private String csbc_ecoclass;
	// 行业一级
	private String csbc_iclassfirst;
	// 行业二级
	private String csbc_industryclass;
	// 法人姓名
	private String csbc_corname;
	// 法人身份证号
	private String csbc_coridcard;
	// 企业邮编
	private Integer csbc_pastcode;
	// 医疗机构(就近杜康点)
	private String csbc_heaname;
	// 银行全称(营业部全称)
	private String csbc_bankname;
	// 银行编码
	private String csbc_bankcode;
	// 银行账号
	private String csbc_bankacctid;
	// 企业所在辖区
	private String csbc_sorarea;
	// 单位社保编号
	private String csbc_sorid;
	// 社保业务主要提交方式
	private String csbc_submission;
	// 社保缴费方式
	private String csbc_paytype;
	// 收据送达方式
	private String csbc_payapply;
	// 经办人姓名
	private String csbc_attnname;
	// 经办人移动电话
	private String csbc_attnmobile;
	// 记录上一个状态
	private Integer csbc_laststate;
	// 状态
	private Integer csbc_state;
	// 密码(6-10位长)
	private String csbc_pwd;
	// csbc_Remark
	private String csbc_remark;
	// csbc_Addtime
	private Timestamp csbc_addtime;
	private String csbc_addtime1;
	// csbc_Addname
	private String csbc_addname;
	// csbc_tapr_id
	private Integer csbc_tapr_id;
	// csbc_tzlstate
	private Integer csbc_tzlstate;
	// 打单文件名
	private String csbc_pdf;
	// 公司信息截图
	private String csbc_image;
	// 在册人员名单
	private String csbc_xls;
	private Integer csbc_cosb_id;
	// 信息变更项目
	private String csbc_changestr;
	// 注销/终止原因
	private String csbc_stop_reason;
	// 失业比例
	private double csbc_unemployment_per;
	// 工商比例
	private double csbc_business_per;
	// 退回原因
	private String csbc_return_reason;
	// 合同主键
	private Integer coco_id;
	// 合同号
	private String coco_compactid;
	// 是否更新过合同
	private Boolean csbc_if_update_compact;

	// id
	private Integer id;
	// stateid
	private Integer stateid;
	// statename
	private String statename;
	// operate
	private String operate;
	// type
	private String type;
	// typename
	private String typename;
	// orderid
	private Integer orderid;
	// state
	private Integer state;

	// pbsr_id
	private Integer pbsr_id;
	// pbsr_daid
	private Integer pbsr_daid;
	// pbsr_statename
	private String pbsr_statename;
	// pbsr_content
	private String pbsr_content;
	// pbsr_type
	private String pbsr_type;
	// pbsr_addtime
	private Timestamp pbsr_addtime;
	// pbsr_addname
	private String pbsr_addname;
	// pbsr_statetime
	private Timestamp pbsr_statetime;
	// pbsr_remark
	private String pbsr_remark;

	private String coba_company;
	private String coba_shortname;
	private String coba_client;
	private Boolean if_submit;
	private String cosb_cardbank;//社保卡制卡银行大类（比如：中国银行，不具体到什么支行）
	private String cosb_branchbank;//社保卡制卡银行大类（比如：中国银行，不具体到什么支行）
	private String cosb_ukey;//
	private String cosb_ukeytruetime;//ukey生效时间
	private String cosb_ukeyfailtime;//ukey失效时间
	private String cosb_attnname,cosb_attnmobile;
	private String cosb_sorid;

	public Integer getCsbc_id() {
		return csbc_id;
	}

	public void setCsbc_id(Integer csbc_id) {
		this.csbc_id = csbc_id;
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

	public String getCsbc_addtype() {
		return csbc_addtype;
	}

	public void setCsbc_addtype(String csbc_addtype) {
		this.csbc_addtype = csbc_addtype;
	}

	public String getCsbc_town() {
		return csbc_town;
	}

	public void setCsbc_town(String csbc_town) {
		this.csbc_town = csbc_town;
	}

	public String getCsbc_street() {
		return csbc_street;
	}

	public void setCsbc_street(String csbc_street) {
		this.csbc_street = csbc_street;
	}

	public String getCsbc_regadd() {
		return csbc_regadd;
	}

	public void setCsbc_regadd(String csbc_regadd) {
		this.csbc_regadd = csbc_regadd;
	}

	public String getCsbc_regid() {
		return csbc_regid;
	}

	public void setCsbc_regid(String csbc_regid) {
		this.csbc_regid = csbc_regid;
	}

	public String getCsbc_sbadd() {
		return csbc_sbadd;
	}

	public void setCsbc_sbadd(String csbc_sbadd) {
		this.csbc_sbadd = csbc_sbadd;
	}

	public Timestamp getCsbc_licdate() {
		return csbc_licdate;
	}

	public void setCsbc_licdate(Timestamp csbc_licdate) {
		this.csbc_licdate = csbc_licdate;
	}

	public String getCsbc_comid() {
		return csbc_comid;
	}

	public void setCsbc_comid(String csbc_comid) {
		this.csbc_comid = csbc_comid;
	}

	public String getCsbc_forms() {
		return csbc_forms;
	}

	public void setCsbc_forms(String csbc_forms) {
		this.csbc_forms = csbc_forms;
	}

	public String getCsbc_dep() {
		return csbc_dep;
	}

	public void setCsbc_dep(String csbc_dep) {
		this.csbc_dep = csbc_dep;
	}

	public String getCsbc_ecoclass() {
		return csbc_ecoclass;
	}

	public void setCsbc_ecoclass(String csbc_ecoclass) {
		this.csbc_ecoclass = csbc_ecoclass;
	}

	public String getCsbc_industryclass() {
		return csbc_industryclass;
	}

	public void setCsbc_industryclass(String csbc_industryclass) {
		this.csbc_industryclass = csbc_industryclass;
	}

	public String getCsbc_corname() {
		return csbc_corname;
	}

	public void setCsbc_corname(String csbc_corname) {
		this.csbc_corname = csbc_corname;
	}

	public String getCsbc_coridcard() {
		return csbc_coridcard;
	}

	public void setCsbc_coridcard(String csbc_coridcard) {
		this.csbc_coridcard = csbc_coridcard;
	}

	public Integer getCsbc_pastcode() {
		return csbc_pastcode;
	}

	public void setCsbc_pastcode(Integer csbc_pastcode) {
		this.csbc_pastcode = csbc_pastcode;
	}

	public String getCsbc_heaname() {
		return csbc_heaname;
	}

	public void setCsbc_heaname(String csbc_heaname) {
		this.csbc_heaname = csbc_heaname;
	}

	public String getCsbc_bankname() {
		return csbc_bankname;
	}

	public void setCsbc_bankname(String csbc_bankname) {
		this.csbc_bankname = csbc_bankname;
	}

	public String getCsbc_bankcode() {
		return csbc_bankcode;
	}

	public void setCsbc_bankcode(String csbc_bankcode) {
		this.csbc_bankcode = csbc_bankcode;
	}

	public String getCsbc_bankacctid() {
		return csbc_bankacctid;
	}

	public void setCsbc_bankacctid(String csbc_bankacctid) {
		this.csbc_bankacctid = csbc_bankacctid;
	}

	public String getCsbc_sorarea() {
		return csbc_sorarea;
	}

	public void setCsbc_sorarea(String csbc_sorarea) {
		this.csbc_sorarea = csbc_sorarea;
	}

	public String getCsbc_sorid() {
		return csbc_sorid;
	}

	public void setCsbc_sorid(String csbc_sorid) {
		this.csbc_sorid = csbc_sorid;
	}

	public String getCsbc_submission() {
		return csbc_submission;
	}

	public void setCsbc_submission(String csbc_submission) {
		this.csbc_submission = csbc_submission;
	}

	public String getCsbc_paytype() {
		return csbc_paytype;
	}

	public void setCsbc_paytype(String csbc_paytype) {
		this.csbc_paytype = csbc_paytype;
	}

	public String getCsbc_payapply() {
		return csbc_payapply;
	}

	public void setCsbc_payapply(String csbc_payapply) {
		this.csbc_payapply = csbc_payapply;
	}

	public String getCsbc_attnname() {
		return csbc_attnname;
	}

	public void setCsbc_attnname(String csbc_attnname) {
		this.csbc_attnname = csbc_attnname;
	}

	public String getCsbc_attnmobile() {
		return csbc_attnmobile;
	}

	public void setCsbc_attnmobile(String csbc_attnmobile) {
		this.csbc_attnmobile = csbc_attnmobile;
	}

	public Integer getCsbc_state() {
		return csbc_state;
	}

	public void setCsbc_state(Integer csbc_state) {
		this.csbc_state = csbc_state;
	}

	public String getCsbc_pwd() {
		return csbc_pwd;
	}

	public void setCsbc_pwd(String csbc_pwd) {
		this.csbc_pwd = csbc_pwd;
	}

	public String getCsbc_remark() {
		return csbc_remark;
	}

	public void setCsbc_remark(String csbc_remark) {
		this.csbc_remark = csbc_remark;
	}

	public Timestamp getCsbc_addtime() {
		return csbc_addtime;
	}

	public void setCsbc_addtime(Timestamp csbc_addtime) {
		this.csbc_addtime = csbc_addtime;
	}

	public String getCsbc_addname() {
		return csbc_addname;
	}

	public void setCsbc_addname(String csbc_addname) {
		this.csbc_addname = csbc_addname;
	}

	public String getCsbc_cotel() {
		return csbc_cotel;
	}

	public void setCsbc_cotel(String csbc_cotel) {
		this.csbc_cotel = csbc_cotel;
	}

	public Integer getCsbc_laststate() {
		return csbc_laststate;
	}

	public void setCsbc_laststate(Integer csbc_laststate) {
		this.csbc_laststate = csbc_laststate;
	}

	public Integer getCsbc_tapr_id() {
		return csbc_tapr_id;
	}

	public void setCsbc_tapr_id(Integer csbc_tapr_id) {
		this.csbc_tapr_id = csbc_tapr_id;
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

	public Integer getCsbc_tzlstate() {
		return csbc_tzlstate;
	}

	public void setCsbc_tzlstate(Integer csbc_tzlstate) {
		this.csbc_tzlstate = csbc_tzlstate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStateid() {
		return stateid;
	}

	public void setStateid(Integer stateid) {
		this.stateid = stateid;
	}

	public String getStatename() {
		return statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getPbsr_id() {
		return pbsr_id;
	}

	public void setPbsr_id(Integer pbsr_id) {
		this.pbsr_id = pbsr_id;
	}

	public Integer getPbsr_daid() {
		return pbsr_daid;
	}

	public void setPbsr_daid(Integer pbsr_daid) {
		this.pbsr_daid = pbsr_daid;
	}

	public String getPbsr_statename() {
		return pbsr_statename;
	}

	public void setPbsr_statename(String pbsr_statename) {
		this.pbsr_statename = pbsr_statename;
	}

	public String getPbsr_content() {
		return pbsr_content;
	}

	public void setPbsr_content(String pbsr_content) {
		this.pbsr_content = pbsr_content;
	}

	public String getPbsr_type() {
		return pbsr_type;
	}

	public void setPbsr_type(String pbsr_type) {
		this.pbsr_type = pbsr_type;
	}

	public Timestamp getPbsr_addtime() {
		return pbsr_addtime;
	}

	public void setPbsr_addtime(Timestamp pbsr_addtime) {
		this.pbsr_addtime = pbsr_addtime;
	}

	public String getPbsr_addname() {
		return pbsr_addname;
	}

	public void setPbsr_addname(String pbsr_addname) {
		this.pbsr_addname = pbsr_addname;
	}

	public Timestamp getPbsr_statetime() {
		return pbsr_statetime;
	}

	public void setPbsr_statetime(Timestamp pbsr_statetime) {
		this.pbsr_statetime = pbsr_statetime;
	}

	public String getPbsr_remark() {
		return pbsr_remark;
	}

	public void setPbsr_remark(String pbsr_remark) {
		this.pbsr_remark = pbsr_remark;
	}

	public String getCsbc_addtime1() {
		return csbc_addtime1;
	}

	public void setCsbc_addtime1(String csbc_addtime1) {
		this.csbc_addtime1 = csbc_addtime1;
	}

	public String getCsbc_pdf() {
		return csbc_pdf;
	}

	public void setCsbc_pdf(String csbc_pdf) {
		this.csbc_pdf = csbc_pdf;
	}

	public String getCsbc_image() {
		return csbc_image;
	}

	public void setCsbc_image(String csbc_image) {
		this.csbc_image = csbc_image;
	}

	public String getCsbc_xls() {
		return csbc_xls;
	}

	public void setCsbc_xls(String csbc_xls) {
		this.csbc_xls = csbc_xls;
	}

	public Integer getCsbc_cosb_id() {
		return csbc_cosb_id;
	}

	public void setCsbc_cosb_id(Integer csbc_cosb_id) {
		this.csbc_cosb_id = csbc_cosb_id;
	}

	public String getCsbc_changestr() {
		return csbc_changestr;
	}

	public void setCsbc_changestr(String csbc_changestr) {
		this.csbc_changestr = csbc_changestr;
	}

	public String getCsbc_stop_reason() {
		return csbc_stop_reason;
	}

	public void setCsbc_stop_reason(String csbc_stop_reason) {
		this.csbc_stop_reason = csbc_stop_reason;
	}

	public double getCsbc_unemployment_per() {
		return csbc_unemployment_per;
	}

	public void setCsbc_unemployment_per(double csbc_unemployment_per) {
		this.csbc_unemployment_per = csbc_unemployment_per;
	}

	public double getCsbc_business_per() {
		return csbc_business_per;
	}

	public void setCsbc_business_per(double csbc_business_per) {
		this.csbc_business_per = csbc_business_per;
	}

	public String getCsbc_return_reason() {
		return csbc_return_reason;
	}

	public void setCsbc_return_reason(String csbc_return_reason) {
		this.csbc_return_reason = csbc_return_reason;
	}

	public Boolean getCsbc_if_update_compact() {
		return csbc_if_update_compact;
	}

	public void setCsbc_if_update_compact(Boolean csbc_if_update_compact) {
		this.csbc_if_update_compact = csbc_if_update_compact;
	}

	public Integer getCoco_id() {
		return coco_id;
	}

	public void setCoco_id(Integer coco_id) {
		this.coco_id = coco_id;
	}

	public String getCoco_compactid() {
		return coco_compactid;
	}

	public void setCoco_compactid(String coco_compactid) {
		this.coco_compactid = coco_compactid;
	}

	public Boolean getIf_submit() {
		return if_submit;
	}

	public void setIf_submit(Boolean if_submit) {
		this.if_submit = if_submit;
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

	public String getCsbc_iclassfirst() {
		return csbc_iclassfirst;
	}

	public void setCsbc_iclassfirst(String csbc_iclassfirst) {
		this.csbc_iclassfirst = csbc_iclassfirst;
	}

	public String getCosb_sorid() {
		return cosb_sorid;
	}

	public void setCosb_sorid(String cosb_sorid) {
		this.cosb_sorid = cosb_sorid;
	}
	
}
