package Model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import bll.CoHousingFund.CoHousingFund_ListBll;

import Util.DateStringChange;

public class CoHousingFundChangeModel {
	// chfc_id
	private Integer chfc_id;
	// cid
	private Integer cid;
	private String chfc_company;
	// ownmonth
	private Integer ownmonth;
	// CoHousingFund主键
	private Integer chfc_cohf_id;
	// 公积金账号
	private String chfc_houseid;
	// 缴存登记/账户接管
	private String chfc_addtype;
	// 单位/个人缴存比例
	private Double chfc_cpp;
	// 客服
	private String chfc_client;
	// 托收银行
	private String chfc_bankts;
	// 托收银行账号
	private String chfc_banktsid;
	// 托收账户名称
	private String chfc_banktsacc;
	// 缴存银行(开户银行)
	private String chfc_bankjc;
	// 缴存银行账号
	private String chfc_bankjcid;
	// 归集银行
	private String chfc_bankgj;
	// 领卡银行
	private String chfc_banklk;
	// 托收日
	private Integer chfc_tsday;
	// chfc_addtime
	private Date chfc_addtime;
	private String chfc_addtimeStr;
	// chfc_addname
	private String chfc_addname;
	// chfc_laststate
	private Integer chfc_laststate;
	// chfc_state
	private Integer chfc_state;
	// chfc_tzlstate
	private Integer chfc_tzlstate;
	// chfc_remark
	private String chfc_remark;
	private String chfc_remark1;
	// 社保单位编号
	private String chfc_sorid;
	// 账户注销/管理终止
	private String chfc_stop_type;
	// 注销/终止原因
	private String chfc_stop_reason;
	// 退回原因
	private String chfc_return_reason;
	// 组织机构代码
	private String chfc_comid;
	// 资格类型
	private String chfc_zgtype;
	// 单位地址
	private String chfc_address;
	// 所属行政区域
	private String chfc_area;
	// 邮政编码
	private String chfc_pastal;
	// 性质分类
	private String chfc_nature;
	// 企业经济类型
	private String chfc_ecoclass;
	// 行业代码
	private String chfc_industry;
	// 隶属关系
	private String chfc_attached;
	// 法定代表人或负责人姓名
	private String chfc_corname;
	// 法定代表人或负责人证件类型
	private String chfc_coridtype;
	// 法定代表人或负责人证件号码
	private String chfc_coridcard;
	// 法定代表人或负责人联系电话
	private String chfc_cortel;
	// 上级主管部门名称
	private String chfc_department;
	// 上级主管部门联系电话
	private String chfc_departmenttel;
	// 单位成立时间
	private String chfc_createtime;
	// 工商注册号
	private String chfc_regid;
	// 纳税人识别号
	private String chfc_taxpayerid;
	// 业务经办部门
	private String chfc_jbdepartment;
	// 联系人姓名
	private String chfc_contactname;
	// 联系人固定电话
	private String chfc_contacttel;
	// 联系人电子邮件
	private String chfc_contactmail;
	// 联系人移动电话
	private String chfc_contactmobile;
	// 公积金首次托收月
	private Integer chfc_firmonth;
	// chfc_ispwd
	private Integer chfc_ispwd;
	// chfc_tapr_id
	private Integer chfc_tapr_id;
	// chfc_changestr
	private String chfc_changestr;
	// 材料id
	private Integer chfc_puzu_id;
	// 完成申报时间
	private Date chfc_completetime;
	private Boolean chfc_if_update_compact;
	// 中智管理状态
	private String chfc_manstate;
	// 降低比例/缓缴起始月份
	private Integer chfc_start_month;
	private Date chfc_start_monthDate;
	private Integer chfc_end_month;
	private Date chfc_end_monthDate;
	// 末次缴存人数
	private Integer chfc_lastemcount;
	// 末次缴存额
	private BigDecimal chfc_lastsum;
	// 末次缴存月份
	private Integer chfc_last_month;
	private Date chfc_last_monthDate;
	// 降低比例提前终止
	private Boolean chfc_ifstop_low;
	// 缓缴提前终止
	private Boolean chfc_ifstop_hj;
	// 修改时间
	private String chfc_modtime;
	// 修改人
	private String chfc_modname;
	// 退回原因
	private String chfc_backreason;

	private String chfc_file;
	private String chfc_recevicedate;

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
	private Date pbsr_addtime;
	// pbsr_addname
	private String pbsr_addname;
	// pbsr_statetime
	private Date pbsr_statetime;
	// pbsr_remark
	private String pbsr_remark;

	private String coba_client;

	private String coba_company;
	private String coba_shortname;
	private String company;
	private String ispwd;
	private boolean ispwd2;
	private String ismodzb;
	private String ismodzb1;
	private boolean ismodzb2;
	private Boolean vis_modzb;
	private boolean vis_modzb1;
	private Date firmonth;
	private Date ownmonthDate;
	private Integer cpp;
	private Boolean vis_bankjcid;
	private Integer zb_count;
	private List<CoHousingFundZbChangeModel> zbList;
	private List<CoHousingFundPwdChangeModel> pwdList;
	private List<CoHousingFundInforChangeModel> cficList;
	private String isaddpwd;
	private boolean vis_isaddpwd;
	private String ishavepwd;
	private String isaddzb1;

	/**
	 * 月份处理
	 * 
	 */
	public void month_handle() {
		try {
			// 操作月份
			if (ownmonthDate != null) {
				ownmonth = Integer.parseInt(DateStringChange.DatetoSting(
						ownmonthDate, "yyyyMM"));
			}
			// 首次托收月
			if (firmonth != null) {
				chfc_firmonth = Integer.parseInt(DateStringChange.DatetoSting(
						firmonth, "yyyyMM"));
			}
			// 降低比例/缓缴起始月份
			if (chfc_start_monthDate != null) {
				chfc_start_month = Integer.valueOf(DateStringChange
						.DatetoSting(chfc_start_monthDate, "yyyyMM"));
			}
			if (chfc_end_monthDate != null) {
				chfc_end_month = Integer.valueOf(DateStringChange.DatetoSting(
						chfc_end_monthDate, "yyyyMM"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 缴存银行处理(根据银行自动获取银行账号)
	 * 
	 */
	public void bankjc_handle() {
		try {
			if (chfc_bankjc != null && !chfc_bankjc.isEmpty()) {
				if (chfc_bankjc.equals("中国银行")) {
					chfc_bankjcid = "771857930564";
				} else if (chfc_bankjc.equals("建设银行")) {
					chfc_bankjcid = "44201501100052523456";
				} else if (chfc_bankjc.equals("招商银行")) {
					chfc_bankjcid = "755917515810666";
				}
				vis_bankjcid = true;
			} else {
				chfc_bankjcid = null;
				vis_bankjcid = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 信息变更-拼接变更项
	 * 
	 */
	public void changestr_handle() {
		try {
			if (cficList != null && cficList.size() > 0) {
				chfc_changestr = "";
				for (CoHousingFundInforChangeModel m : cficList) {
					chfc_changestr += "、" + m.getCfic_changestyle();
				}
				chfc_changestr = chfc_changestr.substring(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 比例处理
	 * 
	 */
	public void cpp_handle() {
		try {
			if (cpp != null) {
				chfc_cpp = Double.valueOf(cpp) / 100;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取末次缴存人数和缴存额
	 * 
	 */
	public void last_emcount_sum() {
		try {
			CoHousingFundChangeModel m = new CoHousingFundChangeModel();
			m = new CoHousingFund_ListBll().getLastMonth_EmCount_Sum("").get(0);
			chfc_lastemcount = m.getChfc_lastemcount();
			chfc_lastsum = m.getChfc_lastsum();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getIsaddzb1() {
		return isaddzb1;
	}

	public void setIsaddzb1(String isaddzb1) {
		this.isaddzb1 = isaddzb1;
	}

	public boolean isVis_modzb1() {
		return vis_modzb1;
	}

	public void setVis_modzb1(boolean vis_modzb1) {
		this.vis_modzb1 = vis_modzb1;
	}

	public String getIsmodzb1() {
		return ismodzb1;
	}

	public void setIsmodzb1(String ismodzb1) {
		this.ismodzb1 = ismodzb1;
	}

	public String getIshavepwd() {
		return ishavepwd;
	}

	public void setIshavepwd(String ishavepwd) {
		this.ishavepwd = ishavepwd;
	}

	public String getCoba_client() {
		return coba_client;
	}

	public void setCoba_client(String coba_client) {
		this.coba_client = coba_client;
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

	public Date getPbsr_addtime() {
		return pbsr_addtime;
	}

	public void setPbsr_addtime(Date pbsr_addtime) {
		this.pbsr_addtime = pbsr_addtime;
	}

	public String getPbsr_addname() {
		return pbsr_addname;
	}

	public void setPbsr_addname(String pbsr_addname) {
		this.pbsr_addname = pbsr_addname;
	}

	public Date getPbsr_statetime() {
		return pbsr_statetime;
	}

	public void setPbsr_statetime(Date pbsr_statetime) {
		this.pbsr_statetime = pbsr_statetime;
	}

	public String getPbsr_remark() {
		return pbsr_remark;
	}

	public void setPbsr_remark(String pbsr_remark) {
		this.pbsr_remark = pbsr_remark;
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

	public Integer getChfc_id() {
		return chfc_id;
	}

	public void setChfc_id(Integer chfc_id) {
		this.chfc_id = chfc_id;
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

	public Integer getChfc_cohf_id() {
		return chfc_cohf_id;
	}

	public void setChfc_cohf_id(Integer chfc_cohf_id) {
		this.chfc_cohf_id = chfc_cohf_id;
	}

	public String getChfc_houseid() {
		return chfc_houseid;
	}

	public void setChfc_houseid(String chfc_houseid) {
		this.chfc_houseid = chfc_houseid;
	}

	public String getChfc_addtype() {
		return chfc_addtype;
	}

	public void setChfc_addtype(String chfc_addtype) {
		this.chfc_addtype = chfc_addtype;
	}

	public Double getChfc_cpp() {
		return chfc_cpp;
	}

	public void setChfc_cpp(Double chfc_cpp) {
		this.chfc_cpp = chfc_cpp;
	}

	public String getChfc_client() {
		return chfc_client;
	}

	public void setChfc_client(String chfc_client) {
		this.chfc_client = chfc_client;
	}

	public String getChfc_bankts() {
		return chfc_bankts;
	}

	public void setChfc_bankts(String chfc_bankts) {
		this.chfc_bankts = chfc_bankts;
	}

	public String getChfc_banktsid() {
		return chfc_banktsid;
	}

	public void setChfc_banktsid(String chfc_banktsid) {
		this.chfc_banktsid = chfc_banktsid;
	}

	public String getChfc_banktsacc() {
		return chfc_banktsacc;
	}

	public void setChfc_banktsacc(String chfc_banktsacc) {
		this.chfc_banktsacc = chfc_banktsacc;
	}

	public String getChfc_bankjc() {
		return chfc_bankjc;
	}

	public void setChfc_bankjc(String chfc_bankjc) {
		this.chfc_bankjc = chfc_bankjc;
	}

	public String getChfc_bankjcid() {
		return chfc_bankjcid;
	}

	public void setChfc_bankjcid(String chfc_bankjcid) {
		this.chfc_bankjcid = chfc_bankjcid;
	}

	public String getChfc_bankgj() {
		return chfc_bankgj;
	}

	public void setChfc_bankgj(String chfc_bankgj) {
		this.chfc_bankgj = chfc_bankgj;
	}

	public String getChfc_banklk() {
		return chfc_banklk;
	}

	public void setChfc_banklk(String chfc_banklk) {
		this.chfc_banklk = chfc_banklk;
	}

	public Integer getChfc_tsday() {
		return chfc_tsday;
	}

	public void setChfc_tsday(Integer chfc_tsday) {
		this.chfc_tsday = chfc_tsday;
	}

	public Date getChfc_addtime() {
		return chfc_addtime;
	}

	public void setChfc_addtime(Date chfc_addtime) {
		this.chfc_addtime = chfc_addtime;
		if (chfc_addtime != null) {
			chfc_addtimeStr = DateStringChange.DatetoSting(chfc_addtime,
					"yyyy-MM-dd");
		}
	}

	public String getChfc_addname() {
		return chfc_addname;
	}

	public void setChfc_addname(String chfc_addname) {
		this.chfc_addname = chfc_addname;
	}

	public Integer getChfc_laststate() {
		return chfc_laststate;
	}

	public void setChfc_laststate(Integer chfc_laststate) {
		this.chfc_laststate = chfc_laststate;
	}

	public Integer getChfc_state() {
		return chfc_state;
	}

	public void setChfc_state(Integer chfc_state) {
		this.chfc_state = chfc_state;
	}

	public Integer getChfc_tzlstate() {
		return chfc_tzlstate;
	}

	public void setChfc_tzlstate(Integer chfc_tzlstate) {
		this.chfc_tzlstate = chfc_tzlstate;
	}

	public String getChfc_remark() {
		return chfc_remark;
	}

	public void setChfc_remark(String chfc_remark) {
		this.chfc_remark = chfc_remark;
	}

	public String getIsaddpwd() {
		return isaddpwd;
	}

	public void setIsaddpwd(String isaddpwd) {
		this.isaddpwd = isaddpwd;
	}

	public boolean isVis_isaddpwd() {
		return vis_isaddpwd;
	}

	public void setVis_isaddpwd(boolean vis_isaddpwd) {
		this.vis_isaddpwd = vis_isaddpwd;
	}

	public String getChfc_sorid() {
		return chfc_sorid;
	}

	public void setChfc_sorid(String chfc_sorid) {
		this.chfc_sorid = chfc_sorid;
	}

	public String getChfc_stop_type() {
		return chfc_stop_type;
	}

	public void setChfc_stop_type(String chfc_stop_type) {
		this.chfc_stop_type = chfc_stop_type;
	}

	public String getChfc_stop_reason() {
		return chfc_stop_reason;
	}

	public void setChfc_stop_reason(String chfc_stop_reason) {
		this.chfc_stop_reason = chfc_stop_reason;
	}

	public String getChfc_return_reason() {
		return chfc_return_reason;
	}

	public void setChfc_return_reason(String chfc_return_reason) {
		this.chfc_return_reason = chfc_return_reason;
	}

	public String getChfc_comid() {
		return chfc_comid;
	}

	public void setChfc_comid(String chfc_comid) {
		this.chfc_comid = chfc_comid;
	}

	public String getChfc_zgtype() {
		return chfc_zgtype;
	}

	public void setChfc_zgtype(String chfc_zgtype) {
		this.chfc_zgtype = chfc_zgtype;
	}

	public String getChfc_address() {
		return chfc_address;
	}

	public void setChfc_address(String chfc_address) {
		this.chfc_address = chfc_address;
	}

	public String getChfc_area() {
		return chfc_area;
	}

	public void setChfc_area(String chfc_area) {
		this.chfc_area = chfc_area;
	}

	public String getChfc_pastal() {
		return chfc_pastal;
	}

	public void setChfc_pastal(String chfc_pastal) {
		this.chfc_pastal = chfc_pastal;
	}

	public String getChfc_nature() {
		return chfc_nature;
	}

	public void setChfc_nature(String chfc_nature) {
		this.chfc_nature = chfc_nature;
	}

	public String getChfc_ecoclass() {
		return chfc_ecoclass;
	}

	public void setChfc_ecoclass(String chfc_ecoclass) {
		this.chfc_ecoclass = chfc_ecoclass;
	}

	public String getChfc_industry() {
		return chfc_industry;
	}

	public void setChfc_industry(String chfc_industry) {
		this.chfc_industry = chfc_industry;
	}

	public String getChfc_attached() {
		return chfc_attached;
	}

	public void setChfc_attached(String chfc_attached) {
		this.chfc_attached = chfc_attached;
	}

	public String getChfc_corname() {
		return chfc_corname;
	}

	public void setChfc_corname(String chfc_corname) {
		this.chfc_corname = chfc_corname;
	}

	public String getChfc_coridtype() {
		return chfc_coridtype;
	}

	public void setChfc_coridtype(String chfc_coridtype) {
		this.chfc_coridtype = chfc_coridtype;
	}

	public String getChfc_coridcard() {
		return chfc_coridcard;
	}

	public void setChfc_coridcard(String chfc_coridcard) {
		this.chfc_coridcard = chfc_coridcard;
	}

	public String getChfc_cortel() {
		return chfc_cortel;
	}

	public void setChfc_cortel(String chfc_cortel) {
		this.chfc_cortel = chfc_cortel;
	}

	public String getChfc_department() {
		return chfc_department;
	}

	public void setChfc_department(String chfc_department) {
		this.chfc_department = chfc_department;
	}

	public String getChfc_departmenttel() {
		return chfc_departmenttel;
	}

	public void setChfc_departmenttel(String chfc_departmenttel) {
		this.chfc_departmenttel = chfc_departmenttel;
	}

	public String getChfc_createtime() {
		return chfc_createtime;
	}

	public void setChfc_createtime(String chfc_createtime) {
		this.chfc_createtime = chfc_createtime;
	}

	public String getChfc_regid() {
		return chfc_regid;
	}

	public void setChfc_regid(String chfc_regid) {
		this.chfc_regid = chfc_regid;
	}

	public String getChfc_taxpayerid() {
		return chfc_taxpayerid;
	}

	public void setChfc_taxpayerid(String chfc_taxpayerid) {
		this.chfc_taxpayerid = chfc_taxpayerid;
	}

	public String getChfc_jbdepartment() {
		return chfc_jbdepartment;
	}

	public void setChfc_jbdepartment(String chfc_jbdepartment) {
		this.chfc_jbdepartment = chfc_jbdepartment;
	}

	public String getChfc_contactname() {
		return chfc_contactname;
	}

	public void setChfc_contactname(String chfc_contactname) {
		this.chfc_contactname = chfc_contactname;
	}

	public String getChfc_contacttel() {
		return chfc_contacttel;
	}

	public void setChfc_contacttel(String chfc_contacttel) {
		this.chfc_contacttel = chfc_contacttel;
	}

	public String getChfc_contactmail() {
		return chfc_contactmail;
	}

	public void setChfc_contactmail(String chfc_contactmail) {
		this.chfc_contactmail = chfc_contactmail;
	}

	public String getChfc_contactmobile() {
		return chfc_contactmobile;
	}

	public void setChfc_contactmobile(String chfc_contactmobile) {
		this.chfc_contactmobile = chfc_contactmobile;
	}

	public Integer getChfc_firmonth() {
		return chfc_firmonth;
	}

	public void setChfc_firmonth(Integer chfc_firmonth) {
		this.chfc_firmonth = chfc_firmonth;
	}

	public Integer getChfc_ispwd() {
		return chfc_ispwd;
	}

	public void setChfc_ispwd(Integer chfc_ispwd) {
		this.chfc_ispwd = chfc_ispwd;
	}

	public Integer getChfc_tapr_id() {
		return chfc_tapr_id;
	}

	public void setChfc_tapr_id(Integer chfc_tapr_id) {
		this.chfc_tapr_id = chfc_tapr_id;
	}

	public String getChfc_changestr() {
		return chfc_changestr;
	}

	public void setChfc_changestr(String chfc_changestr) {
		this.chfc_changestr = chfc_changestr;
	}

	public Integer getChfc_puzu_id() {
		return chfc_puzu_id;
	}

	public void setChfc_puzu_id(Integer chfc_puzu_id) {
		this.chfc_puzu_id = chfc_puzu_id;
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

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getIspwd() {
		return ispwd;
	}

	public void setIspwd(String ispwd) {
		this.ispwd = ispwd;
	}

	public String getIsmodzb() {
		return ismodzb;
	}

	public void setIsmodzb(String ismodzb) {
		this.ismodzb = ismodzb;
	}

	public Boolean getVis_modzb() {
		return vis_modzb;
	}

	public void setVis_modzb(Boolean vis_modzb) {
		this.vis_modzb = vis_modzb;
	}

	public Date getFirmonth() {
		return firmonth;
	}

	public void setFirmonth(Date firmonth) {
		this.firmonth = firmonth;
	}

	public Date getOwnmonthDate() {
		return ownmonthDate;
	}

	public void setOwnmonthDate(Date ownmonthDate) {
		this.ownmonthDate = ownmonthDate;
	}

	public Integer getCpp() {
		return cpp;
	}

	public void setCpp(Integer cpp) {
		this.cpp = cpp;
	}

	public Boolean getVis_bankjcid() {
		return vis_bankjcid;
	}

	public void setVis_bankjcid(Boolean vis_bankjcid) {
		this.vis_bankjcid = vis_bankjcid;
	}

	public Date getChfc_completetime() {
		return chfc_completetime;
	}

	public void setChfc_completetime(Date chfc_completetime) {
		this.chfc_completetime = chfc_completetime;
	}

	public String getChfc_addtimeStr() {
		return chfc_addtimeStr;
	}

	public void setChfc_addtimeStr(String chfc_addtimeStr) {
		this.chfc_addtimeStr = chfc_addtimeStr;
	}

	public Boolean getChfc_if_update_compact() {
		return chfc_if_update_compact;
	}

	public void setChfc_if_update_compact(Boolean chfc_if_update_compact) {
		this.chfc_if_update_compact = chfc_if_update_compact;
	}

	public String getChfc_manstate() {
		return chfc_manstate;
	}

	public void setChfc_manstate(String chfc_manstate) {
		this.chfc_manstate = chfc_manstate;
	}

	public Integer getZb_count() {
		return zb_count;
	}

	public void setZb_count(Integer zb_count) {
		this.zb_count = zb_count;
	}

	public List<CoHousingFundZbChangeModel> getZbList() {
		return zbList;
	}

	public void setZbList(List<CoHousingFundZbChangeModel> zbList) {
		this.zbList = zbList;
	}

	public List<CoHousingFundPwdChangeModel> getPwdList() {
		return pwdList;
	}

	public void setPwdList(List<CoHousingFundPwdChangeModel> pwdList) {
		this.pwdList = pwdList;
	}

	public String getChfc_company() {
		return chfc_company;
	}

	public void setChfc_company(String chfc_company) {
		this.chfc_company = chfc_company;
	}

	public List<CoHousingFundInforChangeModel> getCficList() {
		return cficList;
	}

	public void setCficList(List<CoHousingFundInforChangeModel> cficList) {
		this.cficList = cficList;
	}

	public Integer getChfc_start_month() {
		return chfc_start_month;
	}

	public void setChfc_start_month(Integer chfc_start_month) {
		this.chfc_start_month = chfc_start_month;
	}

	public Date getChfc_start_monthDate() {
		return chfc_start_monthDate;
	}

	public void setChfc_start_monthDate(Date chfc_start_monthDate) {
		this.chfc_start_monthDate = chfc_start_monthDate;
	}

	public Integer getChfc_end_month() {
		return chfc_end_month;
	}

	public void setChfc_end_month(Integer chfc_end_month) {
		this.chfc_end_month = chfc_end_month;
	}

	public Date getChfc_end_monthDate() {
		return chfc_end_monthDate;
	}

	public void setChfc_end_monthDate(Date chfc_end_monthDate) {
		this.chfc_end_monthDate = chfc_end_monthDate;
	}

	public BigDecimal getChfc_lastsum() {
		return chfc_lastsum;
	}

	public void setChfc_lastsum(BigDecimal chfc_lastsum) {
		this.chfc_lastsum = chfc_lastsum == null ? BigDecimal.ZERO
				: chfc_lastsum.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public Integer getChfc_lastemcount() {
		return chfc_lastemcount;
	}

	public void setChfc_lastemcount(Integer chfc_lastemcount) {
		this.chfc_lastemcount = chfc_lastemcount;
	}

	public Integer getChfc_last_month() {
		return chfc_last_month;
	}

	public void setChfc_last_month(Integer chfc_last_month) {
		this.chfc_last_month = chfc_last_month;
	}

	public Date getChfc_last_monthDate() {
		return chfc_last_monthDate;
	}

	public void setChfc_last_monthDate(Date chfc_last_monthDate) {
		this.chfc_last_monthDate = chfc_last_monthDate;
	}

	public String getChfc_remark1() {
		return chfc_remark1;
	}

	public void setChfc_remark1(String chfc_remark1) {
		this.chfc_remark1 = chfc_remark1;
	}

	public Boolean getChfc_ifstop_low() {
		return chfc_ifstop_low;
	}

	public void setChfc_ifstop_low(Boolean chfc_ifstop_low) {
		this.chfc_ifstop_low = chfc_ifstop_low;
	}

	public Boolean getChfc_ifstop_hj() {
		return chfc_ifstop_hj;
	}

	public void setChfc_ifstop_hj(Boolean chfc_ifstop_hj) {
		this.chfc_ifstop_hj = chfc_ifstop_hj;
	}

	public String getChfc_modtime() {
		return chfc_modtime;
	}

	public void setChfc_modtime(String chfc_modtime) {
		this.chfc_modtime = chfc_modtime;
	}

	public String getChfc_modname() {
		return chfc_modname;
	}

	public void setChfc_modname(String chfc_modname) {
		this.chfc_modname = chfc_modname;
	}

	public String getChfc_backreason() {
		return chfc_backreason;
	}

	public void setChfc_backreason(String chfc_backreason) {
		this.chfc_backreason = chfc_backreason;
	}

	public boolean isIspwd2() {
		return ispwd2;
	}

	public void setIspwd2(boolean ispwd2) {
		this.ispwd2 = ispwd2;
	}

	public boolean isIsmodzb2() {
		return ismodzb2;
	}

	public void setIsmodzb2(boolean ismodzb2) {
		this.ismodzb2 = ismodzb2;
	}

	public String getChfc_file() {
		return chfc_file;
	}

	public void setChfc_file(String chfc_file) {
		this.chfc_file = chfc_file;
	}

	public String getChfc_recevicedate() {
		return chfc_recevicedate;
	}

	public void setChfc_recevicedate(String chfc_recevicedate) {
		this.chfc_recevicedate = chfc_recevicedate;
	}

}
