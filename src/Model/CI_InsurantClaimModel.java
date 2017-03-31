package Model;

public class CI_InsurantClaimModel {
	// / 自动编号
	private int eccl_id;
	// / 雇员编号
	private String gid;
	// / 公司编号
	private String cid;
	// / 公司名称
	private String eccl_company;
	// / 被保险人
	private String eccl_insurant;
	// / 主险人
	private String eccl_insurer;
	// / 投保类型
	private String eccl_castsort;
	// / 银行帐号
	private String eccl_bankacctid;
	// / 银行名称
	private String eccl_bankname;
	// / 开户名
	private String eccl_account;
	// / 联系方式
	private String eccl_mobile;
	// / 申赔金额
	private String eccl_pay_money;
	// / 索赔发票数量
	private String eccl_invoice_count;
	// / 收资料日期
	private String eccl_get_date;
	// / eccl_associate_name
	private String eccl_associate_name;
	// / 交福利组日期
	private String eccl_associate_date;
	// / 就诊次数
	private String eccl_visiting_count;
	// / 索赔次数
	private String eccl_claim_count;
	// / 索赔状态
	private String eccl_state;
	// / 索赔结果
	private String eccl_change;
	// / 索赔类型
	private String eccl_class;
	// / 审核金额
	private String eccl_auditing_money;
	// / 审核日期
	private String eccl_auditing_date;
	// / 保险公司签收人
	private String eccl_sign_name;
	// / 交保险公司时间
	private String eccl_sign_date;
	// / 保险公司名称
	private String eccl_insurance_company;
	// / 审核发票数量
	private String eccl_auditing_invoice;
	// / 审核人
	private String eccl_auditing_name;
	// / 赔付时间
	private String eccl_claim_date;
	// / eccl_fl_date
	private String eccl_fl_date;
	// / 实际赔付金额
	private String eccl_fact_money;
	// / 住院津贴
	private String eccl_bih_allowance;
	// / 赔付合计
	private String eccl_claim_total;
	// / 添加人
	private String eccl_addname;
	// / 添加时间
	private String eccl_addtime;
	// / eccl_remark
	private String eccl_remark;
	// / 发件人
	private String eccl_email_addname;
	// / 发件时间
	private String eccl_email_addtime;
	// / 是否已发邮件
	private String eccl_email_state;
	// / 发送状态，分为“发送”，“确认”
	private String eccl_email_change;
	// / 客服
	private String coba_client;
	// / 保险公司
	private String bcompany;
	// / 工作流id
	private String eccl_tapr_id;
	// / 索赔类型
	private String eccc_id;
	//统计字段
	private String d0_state;
	private String d1_state;
	private String d2_state;
	private String d3_state;
	private String d4_state;
	
	private String idcard;

	public int getEccl_id() {
		return eccl_id;
	}

	public void setEccl_id(int eccl_id) {
		this.eccl_id = eccl_id;
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

	public String getEccl_company() {
		return eccl_company;
	}

	public void setEccl_company(String eccl_company) {
		this.eccl_company = eccl_company;
	}

	public String getEccl_insurant() {
		return eccl_insurant;
	}

	public void setEccl_insurant(String eccl_insurant) {
		this.eccl_insurant = eccl_insurant;
	}

	public String getEccl_insurer() {
		return eccl_insurer;
	}

	public void setEccl_insurer(String eccl_insurer) {
		this.eccl_insurer = eccl_insurer;
	}

	public String getEccl_castsort() {
		return eccl_castsort;
	}

	public void setEccl_castsort(String eccl_castsort) {
		this.eccl_castsort = eccl_castsort;
	}

	public String getEccl_bankacctid() {
		return eccl_bankacctid;
	}

	public void setEccl_bankacctid(String eccl_bankacctid) {
		this.eccl_bankacctid = eccl_bankacctid;
	}

	public String getEccl_bankname() {
		return eccl_bankname;
	}

	public void setEccl_bankname(String eccl_bankname) {
		this.eccl_bankname = eccl_bankname;
	}

	public String getEccl_account() {
		return eccl_account;
	}

	public void setEccl_account(String eccl_account) {
		this.eccl_account = eccl_account;
	}

	public String getEccl_mobile() {
		return eccl_mobile;
	}

	public void setEccl_mobile(String eccl_mobile) {
		this.eccl_mobile = eccl_mobile;
	}

	public String getEccl_pay_money() {
		return eccl_pay_money;
	}

	public void setEccl_pay_money(String eccl_pay_money) {
		this.eccl_pay_money = eccl_pay_money;
	}

	public String getEccl_invoice_count() {
		return eccl_invoice_count;
	}

	public void setEccl_invoice_count(String eccl_invoice_count) {
		this.eccl_invoice_count = eccl_invoice_count;
	}

	public String getEccl_get_date() {
		return eccl_get_date;
	}

	public void setEccl_get_date(String eccl_get_date) {
		this.eccl_get_date = eccl_get_date;
	}

	public String getEccl_associate_name() {
		return eccl_associate_name;
	}

	public void setEccl_associate_name(String eccl_associate_name) {
		this.eccl_associate_name = eccl_associate_name;
	}

	public String getEccl_associate_date() {
		return eccl_associate_date;
	}

	public void setEccl_associate_date(String eccl_associate_date) {
		this.eccl_associate_date = eccl_associate_date;
	}

	public String getEccl_visiting_count() {
		return eccl_visiting_count;
	}

	public void setEccl_visiting_count(String eccl_visiting_count) {
		this.eccl_visiting_count = eccl_visiting_count;
	}

	public String getEccl_claim_count() {
		return eccl_claim_count;
	}

	public void setEccl_claim_count(String eccl_claim_count) {
		this.eccl_claim_count = eccl_claim_count;
	}

	public String getEccl_state() {
		return eccl_state;
	}

	public void setEccl_state(String eccl_state) {
		this.eccl_state = eccl_state;
	}

	public String getEccl_change() {
		return eccl_change;
	}

	public void setEccl_change(String eccl_change) {
		this.eccl_change = eccl_change;
	}

	public String getEccl_class() {
		return eccl_class;
	}

	public void setEccl_class(String eccl_class) {
		this.eccl_class = eccl_class;
	}

	public String getEccl_auditing_money() {
		return eccl_auditing_money;
	}

	public void setEccl_auditing_money(String eccl_auditing_money) {
		this.eccl_auditing_money = eccl_auditing_money;
	}

	public String getEccl_auditing_date() {
		return eccl_auditing_date;
	}

	public void setEccl_auditing_date(String eccl_auditing_date) {
		this.eccl_auditing_date = eccl_auditing_date;
	}

	public String getEccl_sign_name() {
		return eccl_sign_name;
	}

	public void setEccl_sign_name(String eccl_sign_name) {
		this.eccl_sign_name = eccl_sign_name;
	}

	public String getEccl_sign_date() {
		return eccl_sign_date;
	}

	public void setEccl_sign_date(String eccl_sign_date) {
		this.eccl_sign_date = eccl_sign_date;
	}

	public String getEccl_insurance_company() {
		return eccl_insurance_company;
	}

	public void setEccl_insurance_company(String eccl_insurance_company) {
		this.eccl_insurance_company = eccl_insurance_company;
	}

	public String getEccl_auditing_invoice() {
		return eccl_auditing_invoice;
	}

	public void setEccl_auditing_invoice(String eccl_auditing_invoice) {
		this.eccl_auditing_invoice = eccl_auditing_invoice;
	}

	public String getEccl_auditing_name() {
		return eccl_auditing_name;
	}

	public void setEccl_auditing_name(String eccl_auditing_name) {
		this.eccl_auditing_name = eccl_auditing_name;
	}

	public String getEccl_claim_date() {
		return eccl_claim_date;
	}

	public void setEccl_claim_date(String eccl_claim_date) {
		this.eccl_claim_date = eccl_claim_date;
	}

	public String getEccl_fl_date() {
		return eccl_fl_date;
	}

	public void setEccl_fl_date(String eccl_fl_date) {
		this.eccl_fl_date = eccl_fl_date;
	}

	public String getEccl_fact_money() {
		return eccl_fact_money;
	}

	public void setEccl_fact_money(String eccl_fact_money) {
		this.eccl_fact_money = eccl_fact_money;
	}

	public String getEccl_bih_allowance() {
		return eccl_bih_allowance;
	}

	public void setEccl_bih_allowance(String eccl_bih_allowance) {
		this.eccl_bih_allowance = eccl_bih_allowance;
	}

	public String getEccl_claim_total() {
		return eccl_claim_total;
	}

	public void setEccl_claim_total(String eccl_claim_total) {
		this.eccl_claim_total = eccl_claim_total;
	}

	public String getEccl_addname() {
		return eccl_addname;
	}

	public void setEccl_addname(String eccl_addname) {
		this.eccl_addname = eccl_addname;
	}

	public String getEccl_addtime() {
		return eccl_addtime;
	}

	public void setEccl_addtime(String eccl_addtime) {
		this.eccl_addtime = eccl_addtime;
	}

	public String getEccl_remark() {
		return eccl_remark;
	}

	public void setEccl_remark(String eccl_remark) {
		this.eccl_remark = eccl_remark;
	}

	public String getEccl_email_addname() {
		return eccl_email_addname;
	}

	public void setEccl_email_addname(String eccl_email_addname) {
		this.eccl_email_addname = eccl_email_addname;
	}

	public String getEccl_email_addtime() {
		return eccl_email_addtime;
	}

	public void setEccl_email_addtime(String eccl_email_addtime) {
		this.eccl_email_addtime = eccl_email_addtime;
	}

	public String getEccl_email_state() {
		return eccl_email_state;
	}

	public void setEccl_email_state(String eccl_email_state) {
		this.eccl_email_state = eccl_email_state;
	}

	public String getEccl_email_change() {
		return eccl_email_change;
	}

	public void setEccl_email_change(String eccl_email_change) {
		this.eccl_email_change = eccl_email_change;
	}

	public String getCoba_client() {
		return coba_client;
	}

	public void setCoba_client(String coba_client) {
		this.coba_client = coba_client;
	}

	public String getBcompany() {
		return bcompany;
	}

	public void setBcompany(String bcompany) {
		this.bcompany = bcompany;
	}

	public String getEccl_tapr_id() {
		return eccl_tapr_id;
	}

	public void setEccl_tapr_id(String eccl_tapr_id) {
		this.eccl_tapr_id = eccl_tapr_id;
	}

	public String getEccc_id() {
		return eccc_id;
	}

	public void setEccc_id(String eccc_id) {
		this.eccc_id = eccc_id;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getD1_state() {
		return d1_state;
	}

	public void setD1_state(String d1_state) {
		this.d1_state = d1_state;
	}

	public String getD2_state() {
		return d2_state;
	}

	public void setD2_state(String d2_state) {
		this.d2_state = d2_state;
	}

	public String getD3_state() {
		return d3_state;
	}

	public void setD3_state(String d3_state) {
		this.d3_state = d3_state;
	}

	public String getD4_state() {
		return d4_state;
	}

	public void setD4_state(String d4_state) {
		this.d4_state = d4_state;
	}

	public String getD0_state() {
		return d0_state;
	}

	public void setD0_state(String d0_state) {
		this.d0_state = d0_state;
	}

}
