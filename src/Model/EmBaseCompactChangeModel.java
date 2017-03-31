package Model;

public class EmBaseCompactChangeModel {
    /// 自动编号
    private Integer  ebcc_id;
             /// ebcc_ebco_id
    private Integer  ebcc_ebco_id;
             /// 雇员编号
    private Integer  gid;
             /// 公司编号
    private Integer  cid;
             /// 合同起始日
    private String  ebcc_incept_date;
             /// 合同到期日
    private String  ebcc_maturity_date;
             /// 合同期限
    private String  ebcc_term;
             /// 试用期期限
    private String  ebcc_probation_term;
             /// 试用期起始日
    private String  ebcc_probation_incept;
             /// 试用到期时间
    private String  ebcc_probation_mdate;
             /// 月工资
    private String  ebcc_wage;
             /// 试用期工资
    private String  ebcc_probation_wage;
             /// 工作地点
    private String  ebcc_work_place;
             /// 工作岗位
    private String  ebcc_working_station;
             /// 工作时间
    private String  ebcc_working_hours;
             /// 休假制度
    private String  ebcc_furlough_system;
             /// 工作内容
    private String  ebcc_work_content;
             /// 其它事项
    private String  ebcc_other_business;
             /// 签回时间
    private String  ebcc_sign_date;
             /// 归档时间
    private String  ebcc_filing_date;
             /// ebcc_sign_name
    private String  ebcc_sign_name;
             /// ebcc_filing_name
    private String  ebcc_filing_name;
             /// 工时制度
    private String  ebcc_teaching_hour;
             /// 劳动报酬
    private String  ebcc_remuneration;
             /// 工资发放日
    private String  ebcc_payroll_date;
             /// 工资发放方式
    private String  ebcc_payroll_mode;
             /// 审核人
    private String  ebcc_auditing_name;
             /// 审核时间
    private String  ebcc_auditing_time;
             /// 解除时间
    private String  ebcc_release_date;
             /// 解除原因
    private String  ebcc_release_cause;
             /// 处理结果
    private String  ebcc_result;
             /// 终止时间
    private String  ebcc_end_date;
             /// 合同状态(0-制作合同 1-等审核 2-已审核 3-已打印 4-已归档 5-退回)
    private Integer  ebcc_state;
             /// 打印状态
    private Integer  ebcc_print;
             /// 聘用确认书
    private Integer  ebcc_eprint;
             /// 合同变更状态
    private String  ebcc_change;
             /// 添加人
    private String  ebcc_addname;
             /// 添加时间
    private String  ebcc_addtime;
             /// 基本工资
    private String  ebcc_base;
             /// 标准工资
    private String  ebcc_bz_base;
             /// 奖金
    private String  ebcc_bonus;
             /// 其它津贴
    private String  ebcc_allowance;
             /// 月工资补贴
    private String  ebcc_wage_bt;
             /// 月工资含加班
    private String  ebcc_wage_gz;
             /// 试用期补贴
    private String  ebcc_probation_bt;
             /// 试用期含加班
    private String  ebcc_probation_gz;
             /// 通知时间
    private String  ebcc_remind_date;
             /// 是否通知(0--未通知,1--已通知)
    private Integer  ebcc_remind_state;
             /// 合同交审时间
    private String  ebcc_gdate;
             /// 是否盖章
    private Integer  ebcc_gz_state;
             /// 盖章人
    private String  ebcc_gz_name;
             /// 盖章时间
    private String  ebcc_gz_date;
             /// 是否在本地保管劳动合同（0-否，1-是）
    private Integer  ebcc_sava_state;
             /// ebcc_tapr_id
    private Integer  ebcc_tapr_id;
    private Integer readstate;//短信状态
	public Integer getEbcc_id() {
		return ebcc_id;
	}
	public void setEbcc_id(Integer ebcc_id) {
		this.ebcc_id = ebcc_id;
	}
	public Integer getEbcc_ebco_id() {
		return ebcc_ebco_id;
	}
	public void setEbcc_ebco_id(Integer ebcc_ebco_id) {
		this.ebcc_ebco_id = ebcc_ebco_id;
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
	public String getEbcc_incept_date() {
		return ebcc_incept_date;
	}
	public void setEbcc_incept_date(String ebcc_incept_date) {
		this.ebcc_incept_date = ebcc_incept_date;
	}
	public String getEbcc_maturity_date() {
		return ebcc_maturity_date;
	}
	public void setEbcc_maturity_date(String ebcc_maturity_date) {
		this.ebcc_maturity_date = ebcc_maturity_date;
	}
	public String getEbcc_term() {
		return ebcc_term;
	}
	public void setEbcc_term(String ebcc_term) {
		this.ebcc_term = ebcc_term;
	}
	public String getEbcc_probation_term() {
		return ebcc_probation_term;
	}
	public void setEbcc_probation_term(String ebcc_probation_term) {
		this.ebcc_probation_term = ebcc_probation_term;
	}
	public String getEbcc_probation_incept() {
		return ebcc_probation_incept;
	}
	public void setEbcc_probation_incept(String ebcc_probation_incept) {
		this.ebcc_probation_incept = ebcc_probation_incept;
	}
	public String getEbcc_probation_mdate() {
		return ebcc_probation_mdate;
	}
	public void setEbcc_probation_mdate(String ebcc_probation_mdate) {
		this.ebcc_probation_mdate = ebcc_probation_mdate;
	}
	public String getEbcc_wage() {
		return ebcc_wage;
	}
	public void setEbcc_wage(String ebcc_wage) {
		this.ebcc_wage = ebcc_wage;
	}
	public String getEbcc_probation_wage() {
		return ebcc_probation_wage;
	}
	public void setEbcc_probation_wage(String ebcc_probation_wage) {
		this.ebcc_probation_wage = ebcc_probation_wage;
	}
	public String getEbcc_work_place() {
		return ebcc_work_place;
	}
	public void setEbcc_work_place(String ebcc_work_place) {
		this.ebcc_work_place = ebcc_work_place;
	}
	public String getEbcc_working_station() {
		return ebcc_working_station;
	}
	public void setEbcc_working_station(String ebcc_working_station) {
		this.ebcc_working_station = ebcc_working_station;
	}
	public String getEbcc_working_hours() {
		return ebcc_working_hours;
	}
	public void setEbcc_working_hours(String ebcc_working_hours) {
		this.ebcc_working_hours = ebcc_working_hours;
	}
	public String getEbcc_furlough_system() {
		return ebcc_furlough_system;
	}
	public void setEbcc_furlough_system(String ebcc_furlough_system) {
		this.ebcc_furlough_system = ebcc_furlough_system;
	}
	public String getEbcc_work_content() {
		return ebcc_work_content;
	}
	public void setEbcc_work_content(String ebcc_work_content) {
		this.ebcc_work_content = ebcc_work_content;
	}
	public String getEbcc_other_business() {
		return ebcc_other_business;
	}
	public void setEbcc_other_business(String ebcc_other_business) {
		this.ebcc_other_business = ebcc_other_business;
	}
	public String getEbcc_sign_date() {
		return ebcc_sign_date;
	}
	public void setEbcc_sign_date(String ebcc_sign_date) {
		this.ebcc_sign_date = ebcc_sign_date;
	}
	public String getEbcc_filing_date() {
		return ebcc_filing_date;
	}
	public void setEbcc_filing_date(String ebcc_filing_date) {
		this.ebcc_filing_date = ebcc_filing_date;
	}
	public String getEbcc_sign_name() {
		return ebcc_sign_name;
	}
	public void setEbcc_sign_name(String ebcc_sign_name) {
		this.ebcc_sign_name = ebcc_sign_name;
	}
	public String getEbcc_filing_name() {
		return ebcc_filing_name;
	}
	public void setEbcc_filing_name(String ebcc_filing_name) {
		this.ebcc_filing_name = ebcc_filing_name;
	}
	public String getEbcc_teaching_hour() {
		return ebcc_teaching_hour;
	}
	public void setEbcc_teaching_hour(String ebcc_teaching_hour) {
		this.ebcc_teaching_hour = ebcc_teaching_hour;
	}
	public String getEbcc_remuneration() {
		return ebcc_remuneration;
	}
	public void setEbcc_remuneration(String ebcc_remuneration) {
		this.ebcc_remuneration = ebcc_remuneration;
	}
	public String getEbcc_payroll_date() {
		return ebcc_payroll_date;
	}
	public void setEbcc_payroll_date(String ebcc_payroll_date) {
		this.ebcc_payroll_date = ebcc_payroll_date;
	}
	public String getEbcc_payroll_mode() {
		return ebcc_payroll_mode;
	}
	public void setEbcc_payroll_mode(String ebcc_payroll_mode) {
		this.ebcc_payroll_mode = ebcc_payroll_mode;
	}
	public String getEbcc_auditing_name() {
		return ebcc_auditing_name;
	}
	public void setEbcc_auditing_name(String ebcc_auditing_name) {
		this.ebcc_auditing_name = ebcc_auditing_name;
	}
	public String getEbcc_auditing_time() {
		return ebcc_auditing_time;
	}
	public void setEbcc_auditing_time(String ebcc_auditing_time) {
		this.ebcc_auditing_time = ebcc_auditing_time;
	}
	public String getEbcc_release_date() {
		return ebcc_release_date;
	}
	public void setEbcc_release_date(String ebcc_release_date) {
		this.ebcc_release_date = ebcc_release_date;
	}
	public String getEbcc_release_cause() {
		return ebcc_release_cause;
	}
	public void setEbcc_release_cause(String ebcc_release_cause) {
		this.ebcc_release_cause = ebcc_release_cause;
	}
	public String getEbcc_result() {
		return ebcc_result;
	}
	public void setEbcc_result(String ebcc_result) {
		this.ebcc_result = ebcc_result;
	}
	public String getEbcc_end_date() {
		return ebcc_end_date;
	}
	public void setEbcc_end_date(String ebcc_end_date) {
		this.ebcc_end_date = ebcc_end_date;
	}
	public Integer getEbcc_state() {
		return ebcc_state;
	}
	public void setEbcc_state(Integer ebcc_state) {
		this.ebcc_state = ebcc_state;
	}
	public Integer getEbcc_print() {
		return ebcc_print;
	}
	public void setEbcc_print(Integer ebcc_print) {
		this.ebcc_print = ebcc_print;
	}
	public Integer getEbcc_eprint() {
		return ebcc_eprint;
	}
	public void setEbcc_eprint(Integer ebcc_eprint) {
		this.ebcc_eprint = ebcc_eprint;
	}
	public String getEbcc_change() {
		return ebcc_change;
	}
	public void setEbcc_change(String ebcc_change) {
		this.ebcc_change = ebcc_change;
	}
	public String getEbcc_addname() {
		return ebcc_addname;
	}
	public void setEbcc_addname(String ebcc_addname) {
		this.ebcc_addname = ebcc_addname;
	}
	public String getEbcc_addtime() {
		return ebcc_addtime;
	}
	public void setEbcc_addtime(String ebcc_addtime) {
		this.ebcc_addtime = ebcc_addtime;
	}
	public String getEbcc_base() {
		return ebcc_base;
	}
	public void setEbcc_base(String ebcc_base) {
		this.ebcc_base = ebcc_base;
	}
	public String getEbcc_bz_base() {
		return ebcc_bz_base;
	}
	public void setEbcc_bz_base(String ebcc_bz_base) {
		this.ebcc_bz_base = ebcc_bz_base;
	}
	public String getEbcc_bonus() {
		return ebcc_bonus;
	}
	public void setEbcc_bonus(String ebcc_bonus) {
		this.ebcc_bonus = ebcc_bonus;
	}
	public String getEbcc_allowance() {
		return ebcc_allowance;
	}
	public void setEbcc_allowance(String ebcc_allowance) {
		this.ebcc_allowance = ebcc_allowance;
	}
	public String getEbcc_wage_bt() {
		return ebcc_wage_bt;
	}
	public void setEbcc_wage_bt(String ebcc_wage_bt) {
		this.ebcc_wage_bt = ebcc_wage_bt;
	}
	public String getEbcc_wage_gz() {
		return ebcc_wage_gz;
	}
	public void setEbcc_wage_gz(String ebcc_wage_gz) {
		this.ebcc_wage_gz = ebcc_wage_gz;
	}
	public String getEbcc_probation_bt() {
		return ebcc_probation_bt;
	}
	public void setEbcc_probation_bt(String ebcc_probation_bt) {
		this.ebcc_probation_bt = ebcc_probation_bt;
	}
	public String getEbcc_probation_gz() {
		return ebcc_probation_gz;
	}
	public void setEbcc_probation_gz(String ebcc_probation_gz) {
		this.ebcc_probation_gz = ebcc_probation_gz;
	}
	public String getEbcc_remind_date() {
		return ebcc_remind_date;
	}
	public void setEbcc_remind_date(String ebcc_remind_date) {
		this.ebcc_remind_date = ebcc_remind_date;
	}
	public Integer getEbcc_remind_state() {
		return ebcc_remind_state;
	}
	public void setEbcc_remind_state(Integer ebcc_remind_state) {
		this.ebcc_remind_state = ebcc_remind_state;
	}
	public String getEbcc_gdate() {
		return ebcc_gdate;
	}
	public void setEbcc_gdate(String ebcc_gdate) {
		this.ebcc_gdate = ebcc_gdate;
	}
	public Integer getEbcc_gz_state() {
		return ebcc_gz_state;
	}
	public void setEbcc_gz_state(Integer ebcc_gz_state) {
		this.ebcc_gz_state = ebcc_gz_state;
	}
	public String getEbcc_gz_name() {
		return ebcc_gz_name;
	}
	public void setEbcc_gz_name(String ebcc_gz_name) {
		this.ebcc_gz_name = ebcc_gz_name;
	}
	public String getEbcc_gz_date() {
		return ebcc_gz_date;
	}
	public void setEbcc_gz_date(String ebcc_gz_date) {
		this.ebcc_gz_date = ebcc_gz_date;
	}
	public Integer getEbcc_sava_state() {
		return ebcc_sava_state;
	}
	public void setEbcc_sava_state(Integer ebcc_sava_state) {
		this.ebcc_sava_state = ebcc_sava_state;
	}
	public Integer getEbcc_tapr_id() {
		return ebcc_tapr_id;
	}
	public void setEbcc_tapr_id(Integer ebcc_tapr_id) {
		this.ebcc_tapr_id = ebcc_tapr_id;
	}
	public Integer getReadstate() {
		return readstate;
	}
	public void setReadstate(Integer readstate) {
		this.readstate = readstate;
	}
    
}
