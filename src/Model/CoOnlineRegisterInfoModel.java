package Model;

public class CoOnlineRegisterInfoModel {
	// cori_id
	private Integer cori_id;
	// cid
	private Integer cid;
	// ownmonth
	private String ownmonth;
	// 新开户/接管
	private String cori_reg_type;
	// 开户办理类型
	private String cori_reg_transact_type;
	// cori_reg_account
	private String cori_reg_account;
	// cori_reg_pwd
	private String cori_reg_pwd;
	// 行业类型
	private String cori_industry_type;
	// 注册资金
	private String cori_reg_fund;
	// 隶属单位
	private String cori_belong_unit;
	// 法定代表人
	private String cori_legal_person;
	// 法定代表人联系电话
	private String cori_lp_tel;
	// 公司传真
	private String cori_fax;
	// 单位负责人
	private String cori_unti_principal;
	// 单位负责人联系电话
	private String cori_up_tel;
	// 邮政编码
	private String cori_postcode;
	// 开业时间
	private String cori_open_date;
	// 经营起始日期
	private String cori_operate_s_date;
	// 社保编号
	private String cori_ss_code;
	// 月平均工资
	private String cori_salary;
	// 股东单位1
	private String cori_stock_unit1;
	// 股东单位2
	private String cori_stock_unit2;
	// 股东单位3
	private String cori_stock_unit3;
	// 股东单位4
	private String cori_stock_unit4;
	// 是否依法建立和完善
	private Integer cori_if_build;
	// 是否通过平等协商确定
	private Integer cori_if_pass;
	// 是否公示或告知员工
	private Integer cori_if_tell;
	// 总人数
	private Integer cori_all_p;
	// 深圳户籍人数
	private Integer cori_sz_p;
	// 外籍港澳台人数
	private Integer cori_foreign_p;
	// 已签订劳动合同人数
	private Integer cori_sign_contract_p;
	// 合同文本是否交付劳动者
	private Integer cori_if_give;
	// 是否建立劳动合同签收备案制度
	private Integer cori_if_build_sign_in;
	// 工作时间
	private String cori_wtime_type;
	// 每天工作小时数
	private Double cori_hour_per_day;
	// 每周工作小时数
	private Double cori_hour_per_week;
	// 每周工作天数
	private Double cori_day_per_week;
	// 是否存在超时加班加点情况
	private Integer cori_if_ot;
	// 不定时工作制
	private String cori_i_work;
	// 综合计算工时制
	private String cori_sc_man_hour;
	// 每月工资发放时间
	private String cori_salary_date;
	// 是否存在拖欠员工工资情况
	private Integer cori_if_arrear;
	// 是否低于最低工资标准
	private Integer cori_if_lowest;
	// 是否按规定支付加班加点工资
	private Integer cori_if_ar_ot;
	// 是否参加社会保险
	private Integer cori_if_join_ss;
	// 参加人数
	private Integer cori_join_p;
	// 是否发生30人以上群体性劳资纠纷
	private Integer cori_if_happen_ld;
	// 是否招用童工
	private Integer cori_if_kid;
	// 是否成立工会组织
	private Integer cori_if_union;
	// 是否签订了计生责任书
	private Integer cori_if_responsebook;
	// 社区工作站编号
	private Integer cwin_id;
	// 网上立户时间
	private String cori_reg_date;
	// 组织机构代码
	private String cori_oi_code;
	// 营业执照注册号
	private String cori_bl_code;
	// 立户地点
	private String cori_oaa_place;
	// 街道办及社区工作站(临时字段)
	private String cori_ws_name;
	// 街道办及社区工作站联系电话(临时字段)
	private String cori_ws_tel;
	// 居住证办理地点
	private String cori_rp_t_address;
	// 居住证办理地点联系电话
	private String cori_rp_t_tel;
	// 公司地址(临时字段)
	private String cori_address;
	// 状态
	private String cori_state;
	// 立户状态
	private Integer cori_reg_state;
	// 退回人
	private String cori_backname;
	// 退回时间
	private String cori_backtime;
	// cori_backreason
	private String cori_backreason;
	// cori_remark
	private String cori_remark;
	// cori_addname
	private String cori_addname;
	// cori_addtime
	private String cori_addtime;
	// cori_tapr_id
	private Integer cori_tapr_id;
	// 是否签订计生责任书
	private Integer cori_if_sign_responsebook;
	private String cori_tzl_state;
	private Integer cori_isrevoke;
	private Integer cori_isback;
	// 人事反馈所需材料
	private String cori_need_doc;
	// 网上登录密码
	private String cori_web_password;
	// 深户材料id
	private Integer cori_sz_puzu_id;
	// 非深户材料id
	private Integer cori_wd_puzu_id;
	// 账户类型(0:中智户 1:独立户)
	private Integer zhtype;

	// stateid
	private Integer stateid;
	// statename
	private String statename;
	// operate
	private String operate;
	// type
	private Integer type;
	// typename
	private String typename;
	// state
	private Integer state;

	// crsr_id
	private Integer crsr_id;
	// crsr_stateid
	private Integer crsr_stateid;
	// crsr_cori_id
	private Integer crsr_cori_id;
	// crsr_addname
	private String crsr_addname;
	// crsr_addtime
	private String crsr_addtime;
	// crsr_statetime
	private String crsr_statetime;

	private String coba_shortname;
	private String coba_company;
	private Integer gid;
	private String emba_name;
	private Integer erin_id;
	private String emba_idcard;
	private String erin_hjtype;
	private Integer erpi_state;
	private String erpi_t_kind;

	private int reStatus;
	
	private String cori_tackovertime;

	private Integer cori_restate;

	public int getReStatus() {
		return reStatus;
	}

	public void setReStatus(int reStatus) {
		this.reStatus = reStatus;
	}

	public Integer getCori_id() {
		return cori_id;
	}

	public void setCori_id(Integer cori_id) {
		this.cori_id = cori_id;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(String ownmonth) {
		this.ownmonth = ownmonth;
	}

	public String getCori_reg_type() {
		return cori_reg_type;
	}

	public void setCori_reg_type(String cori_reg_type) {
		this.cori_reg_type = cori_reg_type;
	}

	public String getCori_reg_transact_type() {
		return cori_reg_transact_type;
	}

	public void setCori_reg_transact_type(String cori_reg_transact_type) {
		this.cori_reg_transact_type = cori_reg_transact_type;
	}

	public String getCori_reg_account() {
		return cori_reg_account;
	}

	public void setCori_reg_account(String cori_reg_account) {
		this.cori_reg_account = cori_reg_account;
	}

	public String getCori_reg_pwd() {
		return cori_reg_pwd;
	}

	public void setCori_reg_pwd(String cori_reg_pwd) {
		this.cori_reg_pwd = cori_reg_pwd;
	}

	public String getCori_industry_type() {
		return cori_industry_type;
	}

	public void setCori_industry_type(String cori_industry_type) {
		this.cori_industry_type = cori_industry_type;
	}

	public String getCori_reg_fund() {
		return cori_reg_fund;
	}

	public void setCori_reg_fund(String cori_reg_fund) {
		this.cori_reg_fund = cori_reg_fund;
	}

	public String getCori_belong_unit() {
		return cori_belong_unit;
	}

	public void setCori_belong_unit(String cori_belong_unit) {
		this.cori_belong_unit = cori_belong_unit;
	}

	public String getCori_legal_person() {
		return cori_legal_person;
	}

	public void setCori_legal_person(String cori_legal_person) {
		this.cori_legal_person = cori_legal_person;
	}

	public String getCori_lp_tel() {
		return cori_lp_tel;
	}

	public void setCori_lp_tel(String cori_lp_tel) {
		this.cori_lp_tel = cori_lp_tel;
	}

	public String getCori_fax() {
		return cori_fax;
	}

	public void setCori_fax(String cori_fax) {
		this.cori_fax = cori_fax;
	}

	public String getCori_unti_principal() {
		return cori_unti_principal;
	}

	public void setCori_unti_principal(String cori_unti_principal) {
		this.cori_unti_principal = cori_unti_principal;
	}

	public String getCori_up_tel() {
		return cori_up_tel;
	}

	public void setCori_up_tel(String cori_up_tel) {
		this.cori_up_tel = cori_up_tel;
	}

	public String getCori_postcode() {
		return cori_postcode;
	}

	public void setCori_postcode(String cori_postcode) {
		this.cori_postcode = cori_postcode;
	}

	public String getCori_open_date() {
		return cori_open_date;
	}

	public void setCori_open_date(String cori_open_date) {
		this.cori_open_date = cori_open_date;
	}

	public String getCori_operate_s_date() {
		return cori_operate_s_date;
	}

	public void setCori_operate_s_date(String cori_operate_s_date) {
		this.cori_operate_s_date = cori_operate_s_date;
	}

	public String getCori_ss_code() {
		return cori_ss_code;
	}

	public void setCori_ss_code(String cori_ss_code) {
		this.cori_ss_code = cori_ss_code;
	}

	public String getCori_salary() {
		return cori_salary;
	}

	public void setCori_salary(String cori_salary) {
		this.cori_salary = cori_salary;
	}

	public String getCori_stock_unit1() {
		return cori_stock_unit1;
	}

	public void setCori_stock_unit1(String cori_stock_unit1) {
		this.cori_stock_unit1 = cori_stock_unit1;
	}

	public String getCori_stock_unit2() {
		return cori_stock_unit2;
	}

	public void setCori_stock_unit2(String cori_stock_unit2) {
		this.cori_stock_unit2 = cori_stock_unit2;
	}

	public String getCori_stock_unit3() {
		return cori_stock_unit3;
	}

	public void setCori_stock_unit3(String cori_stock_unit3) {
		this.cori_stock_unit3 = cori_stock_unit3;
	}

	public String getCori_stock_unit4() {
		return cori_stock_unit4;
	}

	public void setCori_stock_unit4(String cori_stock_unit4) {
		this.cori_stock_unit4 = cori_stock_unit4;
	}

	public Integer getCori_if_build() {
		return cori_if_build == null ? 0 : cori_if_build;
	}

	public void setCori_if_build(Integer cori_if_build) {
		this.cori_if_build = cori_if_build;
	}

	public Integer getCori_if_pass() {
		return cori_if_pass == null ? 0 : cori_if_pass;
	}

	public void setCori_if_pass(Integer cori_if_pass) {
		this.cori_if_pass = cori_if_pass;
	}

	public Integer getCori_if_tell() {
		return cori_if_tell == null ? 0 : cori_if_tell;
	}

	public void setCori_if_tell(Integer cori_if_tell) {
		this.cori_if_tell = cori_if_tell;
	}

	public Integer getCori_all_p() {
		return cori_all_p;
	}

	public void setCori_all_p(Integer cori_all_p) {
		this.cori_all_p = cori_all_p;
	}

	public Integer getCori_sz_p() {
		return cori_sz_p;
	}

	public void setCori_sz_p(Integer cori_sz_p) {
		this.cori_sz_p = cori_sz_p;
	}

	public Integer getCori_foreign_p() {
		return cori_foreign_p;
	}

	public void setCori_foreign_p(Integer cori_foreign_p) {
		this.cori_foreign_p = cori_foreign_p;
	}

	public Integer getCori_sign_contract_p() {
		return cori_sign_contract_p;
	}

	public void setCori_sign_contract_p(Integer cori_sign_contract_p) {
		this.cori_sign_contract_p = cori_sign_contract_p;
	}

	public Integer getCori_if_give() {
		return cori_if_give == null ? 0 : cori_if_give;
	}

	public void setCori_if_give(Integer cori_if_give) {
		this.cori_if_give = cori_if_give;
	}

	public Integer getCori_if_build_sign_in() {
		return cori_if_build_sign_in == null ? 0 : cori_if_build_sign_in;
	}

	public void setCori_if_build_sign_in(Integer cori_if_build_sign_in) {
		this.cori_if_build_sign_in = cori_if_build_sign_in;
	}

	public Double getCori_hour_per_day() {
		return cori_hour_per_day;
	}

	public void setCori_hour_per_day(Double cori_hour_per_day) {
		this.cori_hour_per_day = cori_hour_per_day;
	}

	public Double getCori_hour_per_week() {
		return cori_hour_per_week;
	}

	public void setCori_hour_per_week(Double cori_hour_per_week) {
		this.cori_hour_per_week = cori_hour_per_week;
	}

	public Double getCori_day_per_week() {
		return cori_day_per_week;
	}

	public void setCori_day_per_week(Double cori_day_per_week) {
		this.cori_day_per_week = cori_day_per_week;
	}

	public Integer getCori_if_ot() {
		return cori_if_ot == null ? 0 : cori_if_ot;
	}

	public void setCori_if_ot(Integer cori_if_ot) {
		this.cori_if_ot = cori_if_ot;
	}

	public String getCori_i_work() {
		return cori_i_work;
	}

	public void setCori_i_work(String cori_i_work) {
		this.cori_i_work = cori_i_work;
	}

	public String getCori_sc_man_hour() {
		return cori_sc_man_hour;
	}

	public void setCori_sc_man_hour(String cori_sc_man_hour) {
		this.cori_sc_man_hour = cori_sc_man_hour;
	}

	public String getCori_salary_date() {
		return cori_salary_date;
	}

	public void setCori_salary_date(String cori_salary_date) {
		this.cori_salary_date = cori_salary_date;
	}

	public Integer getCori_if_arrear() {
		return cori_if_arrear == null ? 0 : cori_if_arrear;
	}

	public void setCori_if_arrear(Integer cori_if_arrear) {
		this.cori_if_arrear = cori_if_arrear;
	}

	public Integer getCori_if_lowest() {
		return cori_if_lowest == null ? 0 : cori_if_lowest;
	}

	public void setCori_if_lowest(Integer cori_if_lowest) {
		this.cori_if_lowest = cori_if_lowest;
	}

	public Integer getCori_if_ar_ot() {
		return cori_if_ar_ot == null ? 0 : cori_if_ar_ot;
	}

	public void setCori_if_ar_ot(Integer cori_if_ar_ot) {
		this.cori_if_ar_ot = cori_if_ar_ot;
	}

	public Integer getCori_if_join_ss() {
		return cori_if_join_ss == null ? 0 : cori_if_join_ss;
	}

	public void setCori_if_join_ss(Integer cori_if_join_ss) {
		this.cori_if_join_ss = cori_if_join_ss;
	}

	public Integer getCori_join_p() {
		return cori_join_p;
	}

	public void setCori_join_p(Integer cori_join_p) {
		this.cori_join_p = cori_join_p;
	}

	public Integer getCori_if_happen_ld() {
		return cori_if_happen_ld == null ? 0 : cori_if_happen_ld;
	}

	public void setCori_if_happen_ld(Integer cori_if_happen_ld) {
		this.cori_if_happen_ld = cori_if_happen_ld;
	}

	public Integer getCori_if_kid() {
		return cori_if_kid == null ? 0 : cori_if_kid;
	}

	public void setCori_if_kid(Integer cori_if_kid) {
		this.cori_if_kid = cori_if_kid;
	}

	public Integer getCori_if_union() {
		return cori_if_union == null ? 0 : cori_if_union;
	}

	public void setCori_if_union(Integer cori_if_union) {
		this.cori_if_union = cori_if_union;
	}

	public Integer getCwin_id() {
		return cwin_id;
	}

	public void setCwin_id(Integer cwin_id) {
		this.cwin_id = cwin_id;
	}

	public String getCori_reg_date() {
		return cori_reg_date;
	}

	public void setCori_reg_date(String cori_reg_date) {
		this.cori_reg_date = cori_reg_date;
	}

	public String getCori_oi_code() {
		return cori_oi_code;
	}

	public void setCori_oi_code(String cori_oi_code) {
		this.cori_oi_code = cori_oi_code;
	}

	public String getCori_bl_code() {
		return cori_bl_code;
	}

	public void setCori_bl_code(String cori_bl_code) {
		this.cori_bl_code = cori_bl_code;
	}

	public String getCori_oaa_place() {
		return cori_oaa_place;
	}

	public void setCori_oaa_place(String cori_oaa_place) {
		this.cori_oaa_place = cori_oaa_place;
	}

	public String getCori_ws_name() {
		return cori_ws_name;
	}

	public void setCori_ws_name(String cori_ws_name) {
		this.cori_ws_name = cori_ws_name;
	}

	public String getCori_ws_tel() {
		return cori_ws_tel;
	}

	public void setCori_ws_tel(String cori_ws_tel) {
		this.cori_ws_tel = cori_ws_tel;
	}

	public String getCori_rp_t_address() {
		return cori_rp_t_address;
	}

	public void setCori_rp_t_address(String cori_rp_t_address) {
		this.cori_rp_t_address = cori_rp_t_address;
	}

	public String getCori_rp_t_tel() {
		return cori_rp_t_tel;
	}

	public void setCori_rp_t_tel(String cori_rp_t_tel) {
		this.cori_rp_t_tel = cori_rp_t_tel;
	}

	public String getCori_address() {
		return cori_address;
	}

	public void setCori_address(String cori_address) {
		this.cori_address = cori_address;
	}

	public String getCori_state() {
		return cori_state;
	}

	public void setCori_state(String cori_state) {
		this.cori_state = cori_state;
	}

	public String getCori_backname() {
		return cori_backname;
	}

	public void setCori_backname(String cori_backname) {
		this.cori_backname = cori_backname;
	}

	public String getCori_backtime() {
		return cori_backtime;
	}

	public void setCori_backtime(String cori_backtime) {
		this.cori_backtime = cori_backtime;
	}

	public String getCori_backreason() {
		return cori_backreason;
	}

	public void setCori_backreason(String cori_backreason) {
		this.cori_backreason = cori_backreason;
	}

	public String getCori_remark() {
		return cori_remark;
	}

	public void setCori_remark(String cori_remark) {
		this.cori_remark = cori_remark;
	}

	public String getCori_addname() {
		return cori_addname;
	}

	public void setCori_addname(String cori_addname) {
		this.cori_addname = cori_addname;
	}

	public String getCori_addtime() {
		return cori_addtime;
	}

	public void setCori_addtime(String cori_addtime) {
		this.cori_addtime = cori_addtime;
	}

	public String getCori_wtime_type() {
		return cori_wtime_type;
	}

	public void setCori_wtime_type(String cori_wtime_type) {
		this.cori_wtime_type = cori_wtime_type;
	}

	public Integer getCori_if_responsebook() {
		return cori_if_responsebook == null ? 0 : cori_if_responsebook;
	}

	public void setCori_if_responsebook(Integer cori_if_responsebook) {
		this.cori_if_responsebook = cori_if_responsebook;
	}

	public Integer getCori_tapr_id() {
		return cori_tapr_id;
	}

	public void setCori_tapr_id(Integer cori_tapr_id) {
		this.cori_tapr_id = cori_tapr_id;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getCoba_shortname() {
		return coba_shortname;
	}

	public void setCoba_shortname(String coba_shortname) {
		this.coba_shortname = coba_shortname;
	}

	public String getCoba_company() {
		return coba_company;
	}

	public void setCoba_company(String coba_company) {
		this.coba_company = coba_company;
	}

	public Integer getCrsr_id() {
		return crsr_id;
	}

	public void setCrsr_id(Integer crsr_id) {
		this.crsr_id = crsr_id;
	}

	public Integer getCrsr_stateid() {
		return crsr_stateid;
	}

	public void setCrsr_stateid(Integer crsr_stateid) {
		this.crsr_stateid = crsr_stateid;
	}

	public Integer getCrsr_cori_id() {
		return crsr_cori_id;
	}

	public void setCrsr_cori_id(Integer crsr_cori_id) {
		this.crsr_cori_id = crsr_cori_id;
	}

	public String getCrsr_addname() {
		return crsr_addname;
	}

	public void setCrsr_addname(String crsr_addname) {
		this.crsr_addname = crsr_addname;
	}

	public String getCrsr_addtime() {
		return crsr_addtime;
	}

	public void setCrsr_addtime(String crsr_addtime) {
		this.crsr_addtime = crsr_addtime;
	}

	public String getCrsr_statetime() {
		return crsr_statetime;
	}

	public void setCrsr_statetime(String crsr_statetime) {
		this.crsr_statetime = crsr_statetime;
	}

	public String getCori_tzl_state() {
		return cori_tzl_state;
	}

	public void setCori_tzl_state(String cori_tzl_state) {
		this.cori_tzl_state = cori_tzl_state;
	}

	public Integer getCori_if_sign_responsebook() {
		return cori_if_sign_responsebook == null ? 0
				: cori_if_sign_responsebook;
	}

	public void setCori_if_sign_responsebook(Integer cori_if_sign_responsebook) {
		this.cori_if_sign_responsebook = cori_if_sign_responsebook;
	}

	public Integer getCori_isrevoke() {
		return cori_isrevoke;
	}

	public void setCori_isrevoke(Integer cori_isrevoke) {
		this.cori_isrevoke = cori_isrevoke;
	}

	public Integer getCori_isback() {
		return cori_isback;
	}

	public void setCori_isback(Integer cori_isback) {
		this.cori_isback = cori_isback;
	}

	public String getCori_need_doc() {
		return cori_need_doc;
	}

	public void setCori_need_doc(String cori_need_doc) {
		this.cori_need_doc = cori_need_doc;
	}

	public String getCori_web_password() {
		return cori_web_password;
	}

	public void setCori_web_password(String cori_web_password) {
		this.cori_web_password = cori_web_password;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public String getEmba_name() {
		return emba_name;
	}

	public void setEmba_name(String emba_name) {
		this.emba_name = emba_name;
	}

	public Integer getCori_sz_puzu_id() {
		return cori_sz_puzu_id;
	}

	public void setCori_sz_puzu_id(Integer cori_sz_puzu_id) {
		this.cori_sz_puzu_id = cori_sz_puzu_id;
	}

	public Integer getCori_wd_puzu_id() {
		return cori_wd_puzu_id;
	}

	public void setCori_wd_puzu_id(Integer cori_wd_puzu_id) {
		this.cori_wd_puzu_id = cori_wd_puzu_id;
	}

	public Integer getZhtype() {
		return zhtype;
	}

	public void setZhtype(Integer zhtype) {
		this.zhtype = zhtype;
	}

	public Integer getErin_id() {
		return erin_id;
	}

	public void setErin_id(Integer erin_id) {
		this.erin_id = erin_id;
	}

	public String getEmba_idcard() {
		return emba_idcard;
	}

	public void setEmba_idcard(String emba_idcard) {
		this.emba_idcard = emba_idcard;
	}

	public String getErin_hjtype() {
		return erin_hjtype;
	}

	public void setErin_hjtype(String erin_hjtype) {
		this.erin_hjtype = erin_hjtype;
	}

	public Integer getErpi_state() {
		return erpi_state;
	}

	public void setErpi_state(Integer erpi_state) {
		this.erpi_state = erpi_state;
	}

	public String getErpi_t_kind() {
		return erpi_t_kind;
	}

	public void setErpi_t_kind(String erpi_t_kind) {
		this.erpi_t_kind = erpi_t_kind;
	}

	public Integer getCori_reg_state() {
		return cori_reg_state;
	}

	public void setCori_reg_state(Integer cori_reg_state) {
		this.cori_reg_state = cori_reg_state;
	}

	public Integer getCori_restate() {
		return cori_restate;
	}

	public void setCori_restate(Integer cori_restate) {
		this.cori_restate = cori_restate;
	}
	
	public String getCori_tackovertime() {
		return cori_tackovertime;
	}

	public void setCori_tackovertime(String cori_tackovertime) {
		this.cori_tackovertime = cori_tackovertime;
	}
	
}
