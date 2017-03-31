package Model;

public class EmBaseCompactModel {
	// / 自动编号
	private int ebco_id;
	// / 雇员编号
	private String gid;
	// / 公司编号
	private String cid;
	// / 合同起始日
	private String ebco_incept_date;
	// / 合同到期日
	private String ebco_maturity_date;
	// / 合同期限
	private String ebco_term;
	// / 试用期期限
	private String ebco_probation_term;
	// / 试用期起始日
	private String ebco_probation_incept;
	// / 试用到期时间
	private String ebco_probation_mdate;
	// / 月工资
	private String ebco_wage;
	// / 试用期工资
	private String ebco_probation_wage;
	// / 工作地点
	private String ebco_work_place;
	// / 工作岗位
	private String ebco_working_station;
	// / 工作时间
	private String ebco_working_hours;
	// / 休假制度
	private String ebco_furlough_system;
	// / 工作内容
	private String ebco_work_content;
	// / 其它事项
	private String ebco_other_business;
	// / 签回时间
	private String ebco_sign_date;
	// / 归档时间
	private String ebco_filing_date;
	// / 签收人
	private String ebco_sign_name;
	// / 归档人
	private String ebco_filing_name;
	// / 工时制度
	private String ebco_teaching_hour;
	// / 劳动报酬
	private String ebco_remuneration;
	// / 工资发放日
	private String ebco_payroll_date;
	// / 工资发放方式
	private String ebco_payroll_mode;
	// / 审核人
	private String ebco_auditing_name;
	// / 审核时间
	private String ebco_auditing_time;
	// / 解除时间
	private String ebco_release_date;
	// / 解除原因
	private String ebco_release_cause;
	// / 处理结果
	private String ebco_result;
	// / 终止时间
	private String ebco_end_date;
	// / 合同状态(0-制作合同 1-等审核 2-已审核 3-已打印 4-已归档 5-退回)
	private String ebco_state;
	// / 打印状态
	private String ebco_print;
	// / 聘用确认书
	private String ebco_eprint;
	// / 合同变更状态
	private String ebco_change;
	// / 添加人
	private String ebco_addname;
	// / 添加时间
	private String ebco_addtime;
	// / 基本工资
	private String ebco_base;
	// / 标准工资
	private String ebco_bz_base;
	// / 奖金
	private String ebco_bonus;
	// / 其它津贴
	private String ebco_allowance;
	// / 月工资补贴
	private String ebco_wage_bt;
	// / 月工资含加班
	private String ebco_wage_gz;
	// / 试用期补贴
	private String ebco_probation_bt;
	// / 试用期含加班
	private String ebco_probation_gz;
	// / 通知时间
	private String ebco_remind_date;
	// / 是否通知(0--未通知,1--已通知)
	private String ebco_remind_state;
	// / 合同交审时间
	private String ebco_gdate;
	// / 是否盖章
	private String ebco_gz_state;
	// / 盖章人
	private String ebco_gz_name;
	// / 盖章时间
	private String ebco_gz_date;
	// / 是否在本地保管劳动合同（0-否，1-是）
	private String ebco_save_state;
	//合同类型
	private String ebco_compact_type;

	public EmBaseCompactModel()
	{
		
	}
	public EmBaseCompactModel(String gid, String cid, String ebco_incept_date,
			String ebco_term, String ebco_maturity_date,
			String ebco_probation_incept, String ebco_probation_mdate,
			String ebco_wage, String ebco_wage_bt, String ebco_probation_wage,
			String ebco_probation_bt, String ebco_wage_gz,
			String ebco_probation_gz, String ebco_payroll_date,
			String ebco_payroll_mode, String ebco_work_place,
			String ebco_working_station, String ebco_teaching_hour,
			String ebco_furlough_system, String ebco_work_content,
			String ebco_other_business, String ebco_addname, String ebco_state,
			String ebco_change,String ebco_probation_term,String compact_type) {
		super();
		this.gid = gid;
		this.cid = cid;
		this.ebco_incept_date = ebco_incept_date;
		this.ebco_maturity_date = ebco_maturity_date;
		this.ebco_term = ebco_term;
		this.ebco_probation_incept = ebco_probation_incept;
		this.ebco_probation_mdate = ebco_probation_mdate;
		this.ebco_wage = ebco_wage;
		this.ebco_probation_wage = ebco_probation_wage;
		this.ebco_work_place = ebco_work_place;
		this.ebco_working_station = ebco_working_station;
		this.ebco_furlough_system = ebco_furlough_system;
		this.ebco_work_content = ebco_work_content;
		this.ebco_other_business = ebco_other_business;
		this.ebco_teaching_hour = ebco_teaching_hour;
		this.ebco_payroll_date = ebco_payroll_date;
		this.ebco_payroll_mode = ebco_payroll_mode;
		this.ebco_wage_bt = ebco_wage_bt;
		this.ebco_wage_gz = ebco_wage_gz;
		this.ebco_probation_bt = ebco_probation_bt;
		this.ebco_probation_gz = ebco_probation_gz;
		this.ebco_addname = ebco_addname;
		this.ebco_state = ebco_state;
		this.ebco_change = ebco_change;
		this.ebco_probation_term = ebco_probation_term;
		this.ebco_compact_type = compact_type;
	}

	public EmBaseCompactModel(int ebco_id, String ebco_sign_date) {
		super();
		this.ebco_id = ebco_id;
		this.ebco_sign_date = ebco_sign_date;
	}

	public int getEbco_id() {
		return ebco_id;
	}

	public void setEbco_id(int ebco_id) {
		this.ebco_id = ebco_id;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getEbco_incept_date() {
		return ebco_incept_date;
	}

	public void setEbco_incept_date(String ebco_incept_date) {
		this.ebco_incept_date = ebco_incept_date;
	}

	public String getEbco_maturity_date() {
		return ebco_maturity_date;
	}

	public void setEbco_maturity_date(String ebco_maturity_date) {
		this.ebco_maturity_date = ebco_maturity_date;
	}

	public String getEbco_term() {
		return ebco_term;
	}

	public void setEbco_term(String ebco_term) {
		this.ebco_term = ebco_term;
	}

	public String getEbco_probation_term() {
		return ebco_probation_term;
	}

	public void setEbco_probation_term(String ebco_probation_term) {
		this.ebco_probation_term = ebco_probation_term;
	}

	public String getEbco_probation_incept() {
		return ebco_probation_incept;
	}

	public void setEbco_probation_incept(String ebco_probation_incept) {
		this.ebco_probation_incept = ebco_probation_incept;
	}

	public String getEbco_probation_mdate() {
		return ebco_probation_mdate;
	}

	public void setEbco_probation_mdate(String ebco_probation_mdate) {
		this.ebco_probation_mdate = ebco_probation_mdate;
	}

	public String getEbco_wage() {
		return ebco_wage;
	}

	public void setEbco_wage(String ebco_wage) {
		this.ebco_wage = ebco_wage;
	}

	public String getEbco_probation_wage() {
		return ebco_probation_wage;
	}

	public void setEbco_probation_wage(String ebco_probation_wage) {
		this.ebco_probation_wage = ebco_probation_wage;
	}

	public String getEbco_work_place() {
		return ebco_work_place;
	}

	public void setEbco_work_place(String ebco_work_place) {
		this.ebco_work_place = ebco_work_place;
	}

	public String getEbco_working_station() {
		return ebco_working_station;
	}

	public void setEbco_working_station(String ebco_working_station) {
		this.ebco_working_station = ebco_working_station;
	}

	public String getEbco_working_hours() {
		return ebco_working_hours;
	}

	public void setEbco_working_hours(String ebco_working_hours) {
		this.ebco_working_hours = ebco_working_hours;
	}

	public String getEbco_furlough_system() {
		return ebco_furlough_system;
	}

	public void setEbco_furlough_system(String ebco_furlough_system) {
		this.ebco_furlough_system = ebco_furlough_system;
	}

	public String getEbco_work_content() {
		return ebco_work_content;
	}

	public void setEbco_work_content(String ebco_work_content) {
		this.ebco_work_content = ebco_work_content;
	}

	public String getEbco_other_business() {
		return ebco_other_business;
	}

	public void setEbco_other_business(String ebco_other_business) {
		this.ebco_other_business = ebco_other_business;
	}

	public String getEbco_sign_date() {
		return ebco_sign_date;
	}

	public void setEbco_sign_date(String ebco_sign_date) {
		this.ebco_sign_date = ebco_sign_date;
	}

	public String getEbco_filing_date() {
		return ebco_filing_date;
	}

	public void setEbco_filing_date(String ebco_filing_date) {
		this.ebco_filing_date = ebco_filing_date;
	}

	public String getEbco_sign_name() {
		return ebco_sign_name;
	}

	public void setEbco_sign_name(String ebco_sign_name) {
		this.ebco_sign_name = ebco_sign_name;
	}

	public String getEbco_filing_name() {
		return ebco_filing_name;
	}

	public void setEbco_filing_name(String ebco_filing_name) {
		this.ebco_filing_name = ebco_filing_name;
	}

	public String getEbco_teaching_hour() {
		return ebco_teaching_hour;
	}

	public void setEbco_teaching_hour(String ebco_teaching_hour) {
		this.ebco_teaching_hour = ebco_teaching_hour;
	}

	public String getEbco_remuneration() {
		return ebco_remuneration;
	}

	public void setEbco_remuneration(String ebco_remuneration) {
		this.ebco_remuneration = ebco_remuneration;
	}

	public String getEbco_payroll_date() {
		return ebco_payroll_date;
	}

	public void setEbco_payroll_date(String ebco_payroll_date) {
		this.ebco_payroll_date = ebco_payroll_date;
	}

	public String getEbco_payroll_mode() {
		return ebco_payroll_mode;
	}

	public void setEbco_payroll_mode(String ebco_payroll_mode) {
		this.ebco_payroll_mode = ebco_payroll_mode;
	}

	public String getEbco_auditing_name() {
		return ebco_auditing_name;
	}

	public void setEbco_auditing_name(String ebco_auditing_name) {
		this.ebco_auditing_name = ebco_auditing_name;
	}

	public String getEbco_auditing_time() {
		return ebco_auditing_time;
	}

	public void setEbco_auditing_time(String ebco_auditing_time) {
		this.ebco_auditing_time = ebco_auditing_time;
	}

	public String getEbco_release_date() {
		return ebco_release_date;
	}

	public void setEbco_release_date(String ebco_release_date) {
		this.ebco_release_date = ebco_release_date;
	}

	public String getEbco_release_cause() {
		return ebco_release_cause;
	}

	public void setEbco_release_cause(String ebco_release_cause) {
		this.ebco_release_cause = ebco_release_cause;
	}

	public String getEbco_result() {
		return ebco_result;
	}

	public void setEbco_result(String ebco_result) {
		this.ebco_result = ebco_result;
	}

	public String getEbco_end_date() {
		return ebco_end_date;
	}

	public void setEbco_end_date(String ebco_end_date) {
		this.ebco_end_date = ebco_end_date;
	}

	public String getEbco_state() {
		return ebco_state;
	}

	public void setEbco_state(String ebco_state) {
		this.ebco_state = ebco_state;
	}

	public String getEbco_print() {
		return ebco_print;
	}

	public void setEbco_print(String ebco_print) {
		this.ebco_print = ebco_print;
	}

	public String getEbco_eprint() {
		return ebco_eprint;
	}

	public void setEbco_eprint(String ebco_eprint) {
		this.ebco_eprint = ebco_eprint;
	}

	public String getEbco_change() {
		return ebco_change;
	}

	public void setEbco_change(String ebco_change) {
		this.ebco_change = ebco_change;
	}

	public String getEbco_addname() {
		return ebco_addname;
	}

	public void setEbco_addname(String ebco_addname) {
		this.ebco_addname = ebco_addname;
	}

	public String getEbco_addtime() {
		return ebco_addtime;
	}

	public void setEbco_addtime(String ebco_addtime) {
		this.ebco_addtime = ebco_addtime;
	}

	public String getEbco_base() {
		return ebco_base;
	}

	public void setEbco_base(String ebco_base) {
		this.ebco_base = ebco_base;
	}

	public String getEbco_bz_base() {
		return ebco_bz_base;
	}

	public void setEbco_bz_base(String ebco_bz_base) {
		this.ebco_bz_base = ebco_bz_base;
	}

	public String getEbco_bonus() {
		return ebco_bonus;
	}

	public void setEbco_bonus(String ebco_bonus) {
		this.ebco_bonus = ebco_bonus;
	}

	public String getEbco_allowance() {
		return ebco_allowance;
	}

	public void setEbco_allowance(String ebco_allowance) {
		this.ebco_allowance = ebco_allowance;
	}

	public String getEbco_wage_bt() {
		return ebco_wage_bt;
	}

	public void setEbco_wage_bt(String ebco_wage_bt) {
		this.ebco_wage_bt = ebco_wage_bt;
	}

	public String getEbco_wage_gz() {
		return ebco_wage_gz;
	}

	public void setEbco_wage_gz(String ebco_wage_gz) {
		this.ebco_wage_gz = ebco_wage_gz;
	}

	public String getEbco_probation_bt() {
		return ebco_probation_bt;
	}

	public void setEbco_probation_bt(String ebco_probation_bt) {
		this.ebco_probation_bt = ebco_probation_bt;
	}

	public String getEbco_probation_gz() {
		return ebco_probation_gz;
	}

	public void setEbco_probation_gz(String ebco_probation_gz) {
		this.ebco_probation_gz = ebco_probation_gz;
	}

	public String getEbco_remind_date() {
		return ebco_remind_date;
	}

	public void setEbco_remind_date(String ebco_remind_date) {
		this.ebco_remind_date = ebco_remind_date;
	}

	public String getEbco_remind_state() {
		return ebco_remind_state;
	}

	public void setEbco_remind_state(String ebco_remind_state) {
		this.ebco_remind_state = ebco_remind_state;
	}

	public String getEbco_gdate() {
		return ebco_gdate;
	}

	public void setEbco_gdate(String ebco_gdate) {
		this.ebco_gdate = ebco_gdate;
	}

	public String getEbco_gz_state() {
		return ebco_gz_state;
	}

	public void setEbco_gz_state(String ebco_gz_state) {
		this.ebco_gz_state = ebco_gz_state;
	}

	public String getEbco_gz_name() {
		return ebco_gz_name;
	}

	public void setEbco_gz_name(String ebco_gz_name) {
		this.ebco_gz_name = ebco_gz_name;
	}

	public String getEbco_gz_date() {
		return ebco_gz_date;
	}

	public void setEbco_gz_date(String ebco_gz_date) {
		this.ebco_gz_date = ebco_gz_date;
	}

	public String getEbco_save_state() {
		return ebco_save_state;
	}

	public void setEbco_save_state(String ebco_save_state) {
		this.ebco_save_state = ebco_save_state;
	}
	public String getEbco_compact_type() {
		return ebco_compact_type;
	}
	public void setEbco_compact_type(String ebco_compact_type) {
		this.ebco_compact_type = ebco_compact_type;
	}

}
