package Model;

import java.sql.Date;

public class CI_Insurant_ListModel {
	// / 自动编号
	private int ecin_id;
	// / 雇员编号
	private String gid;
	// / 公司编号
	private String cid;
	// / 合同编号
	private String ecin_compact_number;
	// / 被保险人
	private String ecin_insurant;
	// / 主险人
	private String ecin_insurer;
	// / 与主险人关系
	private String ecin_sconnection;
	// / 公司名称
	private String ecin_company;
	// / 身份证号码
	private String ecin_idcard;
	// / 性别
	private String ecin_sex;
	// / 出生日期
	private String ecin_birthday;
	// / 出生日期
	private Date ecin_birthdays;
	// / 出生日期
	private String ecin_birthdays1;
	// / 银行帐号
	private String ecin_bankacctid;
	// / 银行名称
	private String ecin_bankname;
	// / 开户名
	private String ecin_account;
	// / 工作城市
	private String ecin_work_city;
	// / 投保类型
	private String ecin_castsort;
	// / 购买份数
	private String ecin_buy_count;
	// / 生效日期
	private String ecin_ef_date;
	// / 生效日期
	private Date ecin_ef_date1;
	// / 投保日期
	private String ecin_in_date;
	// / 申报日期
	private String ecin_de_date;
	// / 停缴日期
	private String ecin_st_date;
	// / 停报日期
	private String ecin_st_dedate;
	// / 购买状态
	private String ecin_state;
	// / 申报状态
	private String ecin_state2;
	// / 费用审核状态(0-未审核 1-已审核 2-有问题数据)
	private String ecin_bstate;
	// / 添加人
	private String ecin_addname;
	// / 添加时间
	private String ecin_addtime;
	// / 所属月份
	private String ecin_ownmonth;
	// / 加保费用
	private String ecin_add_money;
	// / 减保费用
	private String ecin_reduce_money;
	// / 结算日期
	private String ecin_balance_date;
	// / 结算人
	private String ecin_balance_name;
	// / 费用结算状态(0--未结算,1--结算中,2--已结算)
	private String ecin_balance_state;
	// / 保费
	private String ecin_fee;
	// / 客服
	private String ecin_client;
	// / 备注
	private String ecin_remark;
	// / 工作职位
	private String ecin_work_st;
	// / 公司名称
	private String coba_company;
	// / 公司简称
	private String coba_shortname;
	// /客服
	private String coba_client;
	// / 任务单号
	private String ecin_tapr_id;

	// / 主险人身份证
	private String ecin_zidcard;
	// / 历史参保时间
	private String ecin_his_date;
	// / 有无理赔
	private String ecin_cl_count;

	public int getEcin_id() {
		return ecin_id;
	}

	public void setEcin_id(int ecin_id) {
		this.ecin_id = ecin_id;
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

	public String getEcin_compact_number() {
		return ecin_compact_number;
	}

	public void setEcin_compact_number(String ecin_compact_number) {
		this.ecin_compact_number = ecin_compact_number;
	}

	public String getEcin_insurant() {
		return ecin_insurant;
	}

	public void setEcin_insurant(String ecin_insurant) {
		this.ecin_insurant = ecin_insurant;
	}

	public String getEcin_insurer() {
		return ecin_insurer;
	}

	public void setEcin_insurer(String ecin_insurer) {
		this.ecin_insurer = ecin_insurer;
	}

	public String getEcin_sconnection() {
		return ecin_sconnection;
	}

	public void setEcin_sconnection(String ecin_sconnection) {
		this.ecin_sconnection = ecin_sconnection;
	}

	public String getEcin_company() {
		return ecin_company;
	}

	public void setEcin_company(String ecin_company) {
		this.ecin_company = ecin_company;
	}

	public String getEcin_idcard() {
		return ecin_idcard;
	}

	public void setEcin_idcard(String ecin_idcard) {
		this.ecin_idcard = ecin_idcard;
	}

	public String getEcin_sex() {
		return ecin_sex;
	}

	public void setEcin_sex(String ecin_sex) {
		this.ecin_sex = ecin_sex;
	}

	public String getEcin_birthday() {
		return ecin_birthday;
	}

	public void setEcin_birthday(String ecin_birthday) {
		this.ecin_birthday = ecin_birthday;
	}

	public Date getEcin_birthdays() {
		return ecin_birthdays;
	}

	public void setEcin_birthdays(Date ecin_birthdays) {
		this.ecin_birthdays = ecin_birthdays;
	}

	public String getEcin_bankacctid() {
		return ecin_bankacctid;
	}

	public void setEcin_bankacctid(String ecin_bankacctid) {
		this.ecin_bankacctid = ecin_bankacctid;
	}

	public String getEcin_bankname() {
		return ecin_bankname;
	}

	public void setEcin_bankname(String ecin_bankname) {
		this.ecin_bankname = ecin_bankname;
	}

	public String getEcin_account() {
		return ecin_account;
	}

	public void setEcin_account(String ecin_account) {
		this.ecin_account = ecin_account;
	}

	public String getEcin_work_city() {
		return ecin_work_city;
	}

	public void setEcin_work_city(String ecin_work_city) {
		this.ecin_work_city = ecin_work_city;
	}

	public String getEcin_castsort() {
		return ecin_castsort;
	}

	public void setEcin_castsort(String ecin_castsort) {
		this.ecin_castsort = ecin_castsort;
	}

	public String getEcin_buy_count() {
		return ecin_buy_count;
	}

	public void setEcin_buy_count(String ecin_buy_count) {
		this.ecin_buy_count = ecin_buy_count;
	}

	public String getEcin_ef_date() {
		return ecin_ef_date;
	}

	public void setEcin_ef_date(String ecin_ef_date) {
		this.ecin_ef_date = ecin_ef_date;
	}

	public String getEcin_in_date() {
		return ecin_in_date;
	}

	public void setEcin_in_date(String ecin_in_date) {
		this.ecin_in_date = ecin_in_date;
	}

	public String getEcin_de_date() {
		return ecin_de_date;
	}

	public void setEcin_de_date(String ecin_de_date) {
		this.ecin_de_date = ecin_de_date;
	}

	public String getEcin_st_date() {
		return ecin_st_date;
	}

	public void setEcin_st_date(String ecin_st_date) {
		this.ecin_st_date = ecin_st_date;
	}

	public String getEcin_st_dedate() {
		return ecin_st_dedate;
	}

	public void setEcin_st_dedate(String ecin_st_dedate) {
		this.ecin_st_dedate = ecin_st_dedate;
	}

	public String getEcin_state() {
		return ecin_state;
	}

	public void setEcin_state(String ecin_state) {
		this.ecin_state = ecin_state;
	}

	public String getEcin_state2() {
		return ecin_state2;
	}

	public void setEcin_state2(String ecin_state2) {
		this.ecin_state2 = ecin_state2;
	}

	public String getEcin_bstate() {
		return ecin_bstate;
	}

	public void setEcin_bstate(String ecin_bstate) {
		this.ecin_bstate = ecin_bstate;
	}

	public String getEcin_addname() {
		return ecin_addname;
	}

	public void setEcin_addname(String ecin_addname) {
		this.ecin_addname = ecin_addname;
	}

	public String getEcin_addtime() {
		return ecin_addtime;
	}

	public void setEcin_addtime(String ecin_addtime) {
		this.ecin_addtime = ecin_addtime;
	}

	public String getEcin_ownmonth() {
		return ecin_ownmonth;
	}

	public void setEcin_ownmonth(String ecin_ownmonth) {
		this.ecin_ownmonth = ecin_ownmonth;
	}

	public String getEcin_add_money() {
		return ecin_add_money;
	}

	public void setEcin_add_money(String ecin_add_money) {
		this.ecin_add_money = ecin_add_money;
	}

	public String getEcin_reduce_money() {
		return ecin_reduce_money;
	}

	public void setEcin_reduce_money(String ecin_reduce_money) {
		this.ecin_reduce_money = ecin_reduce_money;
	}

	public String getEcin_balance_date() {
		return ecin_balance_date;
	}

	public void setEcin_balance_date(String ecin_balance_date) {
		this.ecin_balance_date = ecin_balance_date;
	}

	public String getEcin_balance_name() {
		return ecin_balance_name;
	}

	public void setEcin_balance_name(String ecin_balance_name) {
		this.ecin_balance_name = ecin_balance_name;
	}

	public String getEcin_balance_state() {
		return ecin_balance_state;
	}

	public void setEcin_balance_state(String ecin_balance_state) {
		this.ecin_balance_state = ecin_balance_state;
	}

	public String getEcin_fee() {
		return ecin_fee;
	}

	public void setEcin_fee(String ecin_fee) {
		this.ecin_fee = ecin_fee;
	}

	public String getEcin_client() {
		return ecin_client;
	}

	public void setEcin_client(String ecin_client) {
		this.ecin_client = ecin_client;
	}

	public String getEcin_remark() {
		return ecin_remark;
	}

	public void setEcin_remark(String ecin_remark) {
		this.ecin_remark = ecin_remark;
	}

	public String getEcin_work_st() {
		return ecin_work_st;
	}

	public void setEcin_work_st(String ecin_work_st) {
		this.ecin_work_st = ecin_work_st;
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

	public String getEcin_tapr_id() {
		return ecin_tapr_id;
	}

	public void setEcin_tapr_id(String ecin_tapr_id) {
		this.ecin_tapr_id = ecin_tapr_id;
	}

	public String getEcin_zidcard() {
		return ecin_zidcard;
	}

	public void setEcin_zidcard(String ecin_zidcard) {
		this.ecin_zidcard = ecin_zidcard;
	}

	public String getEcin_his_date() {
		return ecin_his_date;
	}

	public void setEcin_his_date(String ecin_his_date) {
		this.ecin_his_date = ecin_his_date;
	}

	public Date getEcin_ef_date1() {
		return ecin_ef_date1;
	}

	public void setEcin_ef_date1(Date ecin_ef_date1) {
		this.ecin_ef_date1 = ecin_ef_date1;
	}

	public String getEcin_cl_count() {
		return ecin_cl_count;
	}

	public void setEcin_cl_count(String ecin_cl_count) {
		this.ecin_cl_count = ecin_cl_count;
	}

	public String getEcin_birthdays1() {
		return ecin_birthdays1;
	}

	public void setEcin_birthdays1(String ecin_birthdays1) {
		this.ecin_birthdays1 = ecin_birthdays1;
	}

}
