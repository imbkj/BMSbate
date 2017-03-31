package Model;

import java.util.Comparator;
import java.util.Date;

public class CoHousingFundModel implements Comparator<CoHousingFundModel> {
	// cohf_id
	private Integer cohf_id;
	// cid
	private Integer cid;
	private String cohf_company;
	// ownmonth
	private Integer ownmonth;
	// 公积金账号
	private String cohf_houseid;
	// 缴存登记/账户接管
	private String cohf_addtype;
	// 单位/个人缴存比例
	private Double cohf_cpp;
	private Integer cpp;
	// 客服
	private String cohf_client;
	// 托收银行
	private String cohf_bankts;
	// 托收银行账号
	private String cohf_banktsid;
	// 托收账户名称
	private String cohf_banktsacc;
	// 缴存银行(开户银行)
	private String cohf_bankjc;
	// 缴存银行账号
	private String cohf_bankjcid;
	// 归集银行
	private String cohf_bankgj;
	// 领卡银行
	private String cohf_banklk;
	// 托收日
	private Integer cohf_tsday;
	private Integer cohf_lastday;
	private Integer cohf_fwday;
	// 截单日
	private String lastdate;
	// cohf_addtime
	private Date cohf_addtime;
	private String cohf_addtimeString;
	// cohf_addname
	private String cohf_addname;
	// 0.终止服务 1.服务中 2.申报中
	private Integer cohf_state;
	// 社保单位编号
	private String cohf_sorid;
	// 账户注销/管理终止
	private String cohf_stop_type;
	// 注销/终止原因
	private String cohf_stop_reason;
	// 组织机构代码
	private String cohf_comid;
	// 资格类型
	private String cohf_zgtype;
	// 单位地址
	private String cohf_address;
	// 所属行政区域
	private String cohf_area;
	// 邮政编码
	private String cohf_pastal;
	// 性质分类
	private String cohf_nature;
	// 企业经济类型
	private String cohf_ecoclass;
	// 行业代码
	private String cohf_industry;
	// 隶属关系
	private String cohf_attached;
	// 法定代表人或负责人姓名
	private String cohf_corname;
	// 法定代表人或负责人证件类型
	private String cohf_coridtype;
	// 法定代表人或负责人证件号码
	private String cohf_coridcard;
	// 法定代表人或负责人联系电话
	private String cohf_cortel;
	// 上级主管部门名称
	private String cohf_department;
	// 上级主管部门联系电话
	private String cohf_departmenttel;
	// 单位成立时间
	private String cohf_createtime;
	// 工商注册号
	private String cohf_regid;
	// 纳税人识别号
	private String cohf_taxpayerid;
	// 业务经办部门
	private String cohf_jbdepartment;
	// 联系人姓名
	private String cohf_contactname;
	// 联系人固定电话
	private String cohf_contacttel;
	// 联系人电子邮件
	private String cohf_contactmail;
	// 联系人移动电话
	private String cohf_contactmobile;
	// 公积金首次托收月
	private Integer cohf_firmonth;
	// cohf_ispwd
	private Integer cohf_ispwd;
	private String ispwd;
	// 完成申报时间
	private Date cohf_completetime;
	private String cohf_completetimeString;
	// 中智管理状态
	private String cohf_manstate;
	// 员工是否可以提交
	private Boolean cohf_if_edit;
	// 员工是否可以提交补缴
	private Boolean cohf_if_bj;
	// 降低比例起始月份
	private Integer cohf_start_month;
	private Date cohf_start_monthDate;
	private Integer cohf_end_month;
	private Date cohf_end_monthDate;
	// 缓缴起始月份
	private Integer cohf_hjstart_month;
	private Date cohf_hjstart_monthDate;
	private Integer cohf_hjend_month;
	private Date cohf_hjend_monthDate;
	// 是否降低比例
	private Boolean cohf_if_low;
	// 是否缓缴
	private Boolean cohf_if_hj;
	// 末次缴存月份
	private Integer last_month;
	// 终止服务时间
	private Date cohf_stoptime;
	private String cohf_stoptimeString;
	private String statename;
	// 员工公积金未停交数
	private Integer em_notstop_count;

	private String coba_company;
	private String coba_shortname;
	private String coba_client;

	private Integer cohf_single;

	private Integer chfz_id;

	private String cfzc_name;
	private String cfzc_number;
	private String cfzc_addname;
	private String cfzc_addtime;
	private String chfz_addtime;
	private String cfzc_addtype;
	private int cfzc_id;
	private int cfzc_state;

	private String tel;
	private String email;
	private String mobile;
	private String remark;
	private String backReason;

	private Boolean cppChange;

	private Boolean inure;

	public String getBackReason() {
		return backReason;
	}

	public void setBackReason(String backReason) {
		this.backReason = backReason;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getCfzc_id() {
		return cfzc_id;
	}

	public void setCfzc_id(int cfzc_id) {
		this.cfzc_id = cfzc_id;
	}

	public String getCfzc_name() {
		return cfzc_name;
	}

	public void setCfzc_name(String cfzc_name) {
		this.cfzc_name = cfzc_name;
	}

	public String getCfzc_number() {
		return cfzc_number;
	}

	public void setCfzc_number(String cfzc_number) {
		this.cfzc_number = cfzc_number;
	}

	public String getCfzc_addname() {
		return cfzc_addname;
	}

	public void setCfzc_addname(String cfzc_addname) {
		this.cfzc_addname = cfzc_addname;
	}

	public String getCfzc_addtime() {
		return cfzc_addtime;
	}

	public void setCfzc_addtime(String cfzc_addtime) {
		this.cfzc_addtime = cfzc_addtime;
	}

	public String getChfz_addtime() {
		return chfz_addtime;
	}

	public void setChfz_addtime(String chfz_addtime) {
		this.chfz_addtime = chfz_addtime;
	}

	public String getCfzc_addtype() {
		return cfzc_addtype;
	}

	public void setCfzc_addtype(String cfzc_addtype) {
		this.cfzc_addtype = cfzc_addtype;
	}

	public CoHousingFundModel() {

	}

	public int getCfzc_state() {
		return cfzc_state;
	}

	public void setCfzc_state(int cfzc_state) {
		this.cfzc_state = cfzc_state;
	}

	public Integer getCohf_id() {
		return cohf_id;
	}

	public void setCohf_id(Integer cohf_id) {
		this.cohf_id = cohf_id;
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

	public String getCohf_houseid() {
		return cohf_houseid;
	}

	public void setCohf_houseid(String cohf_houseid) {
		this.cohf_houseid = cohf_houseid;
	}

	public String getCohf_addtype() {
		return cohf_addtype;
	}

	public void setCohf_addtype(String cohf_addtype) {
		this.cohf_addtype = cohf_addtype;
	}

	public Double getCohf_cpp() {
		return cohf_cpp;
	}

	public void setCohf_cpp(Double cohf_cpp) {
		if (cohf_cpp != null) {
			cohf_cpp = (double) Math.round(cohf_cpp * 1000) / 1000;
		}
		this.cohf_cpp = cohf_cpp;
	}

	public String getCohf_client() {
		return cohf_client;
	}

	public void setCohf_client(String cohf_client) {
		this.cohf_client = cohf_client;
	}

	public String getCohf_bankts() {
		return cohf_bankts;
	}

	public void setCohf_bankts(String cohf_bankts) {
		this.cohf_bankts = cohf_bankts;
	}

	public String getCohf_banktsid() {
		return cohf_banktsid;
	}

	public void setCohf_banktsid(String cohf_banktsid) {
		this.cohf_banktsid = cohf_banktsid;
	}

	public String getCohf_banktsacc() {
		return cohf_banktsacc;
	}

	public void setCohf_banktsacc(String cohf_banktsacc) {
		this.cohf_banktsacc = cohf_banktsacc;
	}

	public String getCohf_bankjc() {
		return cohf_bankjc;
	}

	public void setCohf_bankjc(String cohf_bankjc) {
		this.cohf_bankjc = cohf_bankjc;
	}

	public String getCohf_bankjcid() {
		return cohf_bankjcid;
	}

	public void setCohf_bankjcid(String cohf_bankjcid) {
		this.cohf_bankjcid = cohf_bankjcid;
	}

	public String getCohf_bankgj() {
		return cohf_bankgj;
	}

	public void setCohf_bankgj(String cohf_bankgj) {
		this.cohf_bankgj = cohf_bankgj;
	}

	public String getCohf_banklk() {
		return cohf_banklk;
	}

	public void setCohf_banklk(String cohf_banklk) {
		this.cohf_banklk = cohf_banklk;
	}

	public Integer getCohf_tsday() {
		return cohf_tsday;
	}

	public void setCohf_tsday(Integer cohf_tsday) {
		this.cohf_tsday = cohf_tsday;
	}

	public Date getCohf_addtime() {
		return cohf_addtime;
	}

	public void setCohf_addtime(Date cohf_addtime) {
		this.cohf_addtime = cohf_addtime;
	}

	public String getCohf_addname() {
		return cohf_addname;
	}

	public void setCohf_addname(String cohf_addname) {
		this.cohf_addname = cohf_addname;
	}

	public Integer getCohf_state() {
		return cohf_state;
	}

	public void setCohf_state(Integer cohf_state) {
		this.cohf_state = cohf_state;
	}

	public String getCohf_sorid() {
		return cohf_sorid;
	}

	public void setCohf_sorid(String cohf_sorid) {
		this.cohf_sorid = cohf_sorid;
	}

	public String getCohf_stop_type() {
		return cohf_stop_type;
	}

	public void setCohf_stop_type(String cohf_stop_type) {
		this.cohf_stop_type = cohf_stop_type;
	}

	public String getCohf_stop_reason() {
		return cohf_stop_reason;
	}

	public void setCohf_stop_reason(String cohf_stop_reason) {
		this.cohf_stop_reason = cohf_stop_reason;
	}

	public String getCohf_comid() {
		return cohf_comid;
	}

	public void setCohf_comid(String cohf_comid) {
		this.cohf_comid = cohf_comid;
	}

	public String getCohf_zgtype() {
		return cohf_zgtype;
	}

	public void setCohf_zgtype(String cohf_zgtype) {
		this.cohf_zgtype = cohf_zgtype;
	}

	public String getCohf_address() {
		return cohf_address;
	}

	public void setCohf_address(String cohf_address) {
		this.cohf_address = cohf_address;
	}

	public String getCohf_area() {
		return cohf_area;
	}

	public void setCohf_area(String cohf_area) {
		this.cohf_area = cohf_area;
	}

	public String getCohf_pastal() {
		return cohf_pastal;
	}

	public void setCohf_pastal(String cohf_pastal) {
		this.cohf_pastal = cohf_pastal;
	}

	public String getCohf_nature() {
		return cohf_nature;
	}

	public void setCohf_nature(String cohf_nature) {
		this.cohf_nature = cohf_nature;
	}

	public String getCohf_ecoclass() {
		return cohf_ecoclass;
	}

	public void setCohf_ecoclass(String cohf_ecoclass) {
		this.cohf_ecoclass = cohf_ecoclass;
	}

	public String getCohf_industry() {
		return cohf_industry;
	}

	public void setCohf_industry(String cohf_industry) {
		this.cohf_industry = cohf_industry;
	}

	public String getCohf_attached() {
		return cohf_attached;
	}

	public void setCohf_attached(String cohf_attached) {
		this.cohf_attached = cohf_attached;
	}

	public String getCohf_corname() {
		return cohf_corname;
	}

	public void setCohf_corname(String cohf_corname) {
		this.cohf_corname = cohf_corname;
	}

	public String getCohf_coridtype() {
		return cohf_coridtype;
	}

	public void setCohf_coridtype(String cohf_coridtype) {
		this.cohf_coridtype = cohf_coridtype;
	}

	public String getCohf_coridcard() {
		return cohf_coridcard;
	}

	public void setCohf_coridcard(String cohf_coridcard) {
		this.cohf_coridcard = cohf_coridcard;
	}

	public String getCohf_cortel() {
		return cohf_cortel;
	}

	public void setCohf_cortel(String cohf_cortel) {
		this.cohf_cortel = cohf_cortel;
	}

	public String getCohf_department() {
		return cohf_department;
	}

	public void setCohf_department(String cohf_department) {
		this.cohf_department = cohf_department;
	}

	public String getCohf_departmenttel() {
		return cohf_departmenttel;
	}

	public void setCohf_departmenttel(String cohf_departmenttel) {
		this.cohf_departmenttel = cohf_departmenttel;
	}

	public String getCohf_createtime() {
		return cohf_createtime;
	}

	public void setCohf_createtime(String cohf_createtime) {
		this.cohf_createtime = cohf_createtime;
	}

	public String getCohf_regid() {
		return cohf_regid;
	}

	public void setCohf_regid(String cohf_regid) {
		this.cohf_regid = cohf_regid;
	}

	public String getCohf_taxpayerid() {
		return cohf_taxpayerid;
	}

	public void setCohf_taxpayerid(String cohf_taxpayerid) {
		this.cohf_taxpayerid = cohf_taxpayerid;
	}

	public String getCohf_jbdepartment() {
		return cohf_jbdepartment;
	}

	public void setCohf_jbdepartment(String cohf_jbdepartment) {
		this.cohf_jbdepartment = cohf_jbdepartment;
	}

	public String getCohf_contactname() {
		return cohf_contactname;
	}

	public void setCohf_contactname(String cohf_contactname) {
		this.cohf_contactname = cohf_contactname;
	}

	public String getCohf_contacttel() {
		return cohf_contacttel;
	}

	public void setCohf_contacttel(String cohf_contacttel) {
		this.cohf_contacttel = cohf_contacttel;
	}

	public String getCohf_contactmail() {
		return cohf_contactmail;
	}

	public void setCohf_contactmail(String cohf_contactmail) {
		this.cohf_contactmail = cohf_contactmail;
	}

	public String getCohf_contactmobile() {
		return cohf_contactmobile;
	}

	public void setCohf_contactmobile(String cohf_contactmobile) {
		this.cohf_contactmobile = cohf_contactmobile;
	}

	public Integer getCohf_firmonth() {
		return cohf_firmonth;
	}

	public void setCohf_firmonth(Integer cohf_firmonth) {
		this.cohf_firmonth = cohf_firmonth;
	}

	public Integer getCohf_ispwd() {
		return cohf_ispwd;
	}

	public void setCohf_ispwd(Integer cohf_ispwd) {
		this.cohf_ispwd = cohf_ispwd;
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

	public Integer getCohf_single() {
		return cohf_single;
	}

	public void setCohf_single(Integer cohf_single) {
		this.cohf_single = cohf_single;
	}

	public Date getCohf_completetime() {
		return cohf_completetime;
	}

	public void setCohf_completetime(Date cohf_completetime) {
		this.cohf_completetime = cohf_completetime;
	}

	public String getCohf_manstate() {
		return cohf_manstate;
	}

	public void setCohf_manstate(String cohf_manstate) {
		this.cohf_manstate = cohf_manstate;
	}

	public Boolean getCohf_if_edit() {
		return cohf_if_edit;
	}

	public void setCohf_if_edit(Boolean cohf_if_edit) {
		this.cohf_if_edit = cohf_if_edit;
	}

	public String getCohf_completetimeString() {
		return cohf_completetimeString;
	}

	public void setCohf_completetimeString(String cohf_completetimeString) {
		this.cohf_completetimeString = cohf_completetimeString;
	}

	public String getCohf_addtimeString() {
		return cohf_addtimeString;
	}

	public void setCohf_addtimeString(String cohf_addtimeString) {
		this.cohf_addtimeString = cohf_addtimeString;
	}

	public String getCoba_client() {
		return coba_client;
	}

	public void setCoba_client(String coba_client) {
		this.coba_client = coba_client;
	}

	public String getIspwd() {
		return ispwd;
	}

	public void setIspwd(String ispwd) {
		this.ispwd = ispwd;
	}

	public Integer getChfz_id() {
		return chfz_id;
	}

	public void setChfz_id(Integer chfz_id) {
		this.chfz_id = chfz_id;
	}

	public Integer getCohf_lastday() {
		return cohf_lastday;
	}

	public void setCohf_lastday(Integer cohf_lastday) {
		this.cohf_lastday = cohf_lastday;
	}

	public String getCohf_company() {
		return cohf_company;
	}

	public void setCohf_company(String cohf_company) {
		this.cohf_company = cohf_company;
	}

	public Integer getCpp() {
		return cpp;
	}

	public void setCpp(Integer cpp) {
		this.cpp = cpp;
	}

	public Integer getCohf_start_month() {
		return cohf_start_month;
	}

	public void setCohf_start_month(Integer cohf_start_month) {
		this.cohf_start_month = cohf_start_month;
	}

	public Date getCohf_start_monthDate() {
		return cohf_start_monthDate;
	}

	public void setCohf_start_monthDate(Date cohf_start_monthDate) {
		this.cohf_start_monthDate = cohf_start_monthDate;
	}

	public Integer getCohf_end_month() {
		return cohf_end_month;
	}

	public void setCohf_end_month(Integer cohf_end_month) {
		this.cohf_end_month = cohf_end_month;
	}

	public Date getCohf_end_monthDate() {
		return cohf_end_monthDate;
	}

	public void setCohf_end_monthDate(Date cohf_end_monthDate) {
		this.cohf_end_monthDate = cohf_end_monthDate;
	}

	public Boolean getCohf_if_low() {
		return cohf_if_low;
	}

	public void setCohf_if_low(Boolean cohf_if_low) {
		this.cohf_if_low = cohf_if_low;
	}

	public Boolean getCohf_if_hj() {
		return cohf_if_hj;
	}

	public void setCohf_if_hj(Boolean cohf_if_hj) {
		this.cohf_if_hj = cohf_if_hj;
	}

	public Integer getLast_month() {
		return last_month;
	}

	public void setLast_month(Integer last_month) {
		this.last_month = last_month;
	}

	public Integer getCohf_hjstart_month() {
		return cohf_hjstart_month;
	}

	public void setCohf_hjstart_month(Integer cohf_hjstart_month) {
		this.cohf_hjstart_month = cohf_hjstart_month;
	}

	public Date getCohf_hjstart_monthDate() {
		return cohf_hjstart_monthDate;
	}

	public void setCohf_hjstart_monthDate(Date cohf_hjstart_monthDate) {
		this.cohf_hjstart_monthDate = cohf_hjstart_monthDate;
	}

	public Integer getCohf_hjend_month() {
		return cohf_hjend_month;
	}

	public void setCohf_hjend_month(Integer cohf_hjend_month) {
		this.cohf_hjend_month = cohf_hjend_month;
	}

	public Date getCohf_hjend_monthDate() {
		return cohf_hjend_monthDate;
	}

	public void setCohf_hjend_monthDate(Date cohf_hjend_monthDate) {
		this.cohf_hjend_monthDate = cohf_hjend_monthDate;
	}

	public Date getCohf_stoptime() {
		return cohf_stoptime;
	}

	public void setCohf_stoptime(Date cohf_stoptime) {
		this.cohf_stoptime = cohf_stoptime;
	}

	public String getCohf_stoptimeString() {
		return cohf_stoptimeString;
	}

	public void setCohf_stoptimeString(String cohf_stoptimeString) {
		this.cohf_stoptimeString = cohf_stoptimeString;
	}

	public String getStatename() {
		return statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}

	public Integer getEm_notstop_count() {
		return em_notstop_count;
	}

	public void setEm_notstop_count(Integer em_notstop_count) {
		this.em_notstop_count = em_notstop_count;
	}

	public Boolean getCohf_if_bj() {
		return cohf_if_bj;
	}

	public void setCohf_if_bj(Boolean cohf_if_bj) {
		this.cohf_if_bj = cohf_if_bj;
	}

	public String getLastdate() {
		return lastdate;
	}

	public void setLastdate(String lastdate) {
		this.lastdate = lastdate;
	}

	public Boolean getCppChange() {
		return cppChange;
	}

	public void setCppChange(Boolean cppChange) {
		this.cppChange = cppChange;
	}

	public Boolean getInure() {
		return inure;
	}

	public void setInure(Boolean inure) {
		this.inure = inure;
	}

	public Integer getCohf_fwday() {
		return cohf_fwday;
	}

	public void setCohf_fwday(Integer cohf_fwday) {
		this.cohf_fwday = cohf_fwday;
	}

	@Override
	public int compare(CoHousingFundModel arg0, CoHousingFundModel arg1) {
		// TODO Auto-generated method stub
		Integer i = arg0.getCohf_company().compareTo(arg1.getCohf_company());
		if (i.equals(0)) {
			Integer i2 = arg0.getCohf_tsday().compareTo(arg1.getCohf_tsday());
			i = i2;
		}
		return i;
	}

}
