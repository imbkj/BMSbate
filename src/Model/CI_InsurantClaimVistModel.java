package Model;

import java.util.List;

public class CI_InsurantClaimVistModel {
	// / 自动编号
	private int eccv_id;
	// / 绑定在册表编号
	private String eccv_clid;
	// / 雇员编号
	private String gid;
	// / 投保类型
	private String eccv_castsort;
	// / 添加人
	private String eccv_addname;
	// / 添加时间
	private String eccv_addtime;
	// / 申赔金额
	private String eccv_pay_money;
	// / 索赔发票数量
	private String eccv_invoice_count;
	// / 索赔状态
	private String eccv_state;
	// / 索赔结果
	private String eccv_change;
	// / 索赔类型
	private String eccv_class;
	// / 就诊日期
	private String eccv_visiting_date;
	// / 就诊医院
	private String eccv_visiting_hospital;
	// / 就诊原因
	private String eccv_visiting_cause;
	// / 索赔分类
	private String eccv_claim_class;
	// / 拒赔合计
	private String eccv_rejected_total;
	// / 延赔合计
	private String eccv_delay_total;
	// / 赔付合计
	private String eccv_claim_total;
	// / 住院津贴
	private String eccv_bih_allowance;
	// / 备注
	private String eccv_remark;
	// / 拒延赔原因
	private String eccv_rejected_case;
	// / 是否发送EMAIL(0-发送 1-不发送)
	private String eccv_email_state;
	// / 索赔类型
	private String eccv_claim_bclass;

	private List<CI_InsurantClaimVistModel> pid_list;

	// / 自动编号
	private int ecci_id;
	// / 绑定visits表ID
	private int ecci_viid;
	// / 发票编号
	private String ecci_pid;
	// / 添加人
	private String ecci_addname;
	// / 添加时间
	private String ecci_addtime;
	// / 发票金额
	private String ecci_invoice_money;
	// / 西药
	private String ecci_west_medicine;
	// / 中成药
	private String ecci_chinese_medicine;
	// / 中草药
	private String ecci_herbal_medicine;
	// / 诊查费
	private String ecci_exmine_fee;
	// / 床位费
	private String ecci_bed_fee;
	// / 检查费
	private String ecci_check_fee;
	// / ct
	private String ecci_ct_fee;
	// / 检验费
	private String ecci_prove_fee;
	// / 治疗费
	private String ecci_cure_fee;
	// / 手术费
	private String ecci_ops_fee;
	// / 其它
	private String ecci_other_fee;
	// / 服务费
	private String ecci_service_fee;
	// / 材料费
	private String ecci_stuff_fee;
	// / 挂号费
	private String ecci_register_fee;
	// / 护理费
	private String ecci_nurse_fee;
	// / 赔付金额
	private String ecci_claim_total;
	// / 索赔状态
	private String ecci_state;
	// / 索赔结果
	private String ecci_change;
	// / 审核金额
	private String ecci_auditing_money;
	// / 拒赔-除外责任
	private String ecci_rejected_duty;
	// / 拒赔-社保统筹已付
	private String ecci_rejected_pdpaid;
	// / 拒赔-自费药
	private String ecci_rejected_medicine;
	// / 拒赔-自费材料
	private String ecci_rejected_stuff;
	// / 拒赔-自费检查
	private String ecci_rejected_check;
	// / 拒赔-非指定医院
	private String ecci_rejected_hospital;
	// / 拒赔-其它费用
	private String ecci_rejected_other_fee;
	// / 免赔
	private String ecci_rejected_no_fee;
	// / 超限额
	private String ecci_rejected_up_fee;
	// / 延赔-缺病历
	private String ecci_delay_case_history;
	// / 延赔-非正规发票
	private String ecci_delay_invoice;
	// / 延赔-缺药品清单
	private String ecci_delay_leechdom_list;
	// / 延赔-缺证件
	private String ecci_delay_paper;
	// / 延赔-缺检查报告
	private String ecci_delay_check_report;
	// / 延赔-缺其它资料
	private String ecci_delay_other;
	// / 拒延赔原因
	private String ecci_rejected_case;
	// / 核对时间
	private String ecci_autime;

	public int getEccv_id() {
		return eccv_id;
	}

	public void setEccv_id(int eccv_id) {
		this.eccv_id = eccv_id;
	}

	public String getEccv_clid() {
		return eccv_clid;
	}

	public void setEccv_clid(String eccv_clid) {
		this.eccv_clid = eccv_clid;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getEccv_castsort() {
		return eccv_castsort;
	}

	public void setEccv_castsort(String eccv_castsort) {
		this.eccv_castsort = eccv_castsort;
	}

	public String getEccv_addname() {
		return eccv_addname;
	}

	public void setEccv_addname(String eccv_addname) {
		this.eccv_addname = eccv_addname;
	}

	public String getEccv_addtime() {
		return eccv_addtime;
	}

	public void setEccv_addtime(String eccv_addtime) {
		this.eccv_addtime = eccv_addtime;
	}

	public String getEccv_pay_money() {
		return eccv_pay_money;
	}

	public void setEccv_pay_money(String eccv_pay_money) {
		this.eccv_pay_money = eccv_pay_money;
	}

	public String getEccv_invoice_count() {
		return eccv_invoice_count;
	}

	public void setEccv_invoice_count(String eccv_invoice_count) {
		this.eccv_invoice_count = eccv_invoice_count;
	}

	public String getEccv_state() {
		return eccv_state;
	}

	public void setEccv_state(String eccv_state) {
		this.eccv_state = eccv_state;
	}

	public String getEccv_change() {
		return eccv_change;
	}

	public void setEccv_change(String eccv_change) {
		this.eccv_change = eccv_change;
	}

	public String getEccv_class() {
		return eccv_class;
	}

	public void setEccv_class(String eccv_class) {
		this.eccv_class = eccv_class;
	}

	public String getEccv_visiting_date() {
		return eccv_visiting_date;
	}

	public void setEccv_visiting_date(String eccv_visiting_date) {
		this.eccv_visiting_date = eccv_visiting_date;
	}

	public String getEccv_visiting_hospital() {
		return eccv_visiting_hospital;
	}

	public void setEccv_visiting_hospital(String eccv_visiting_hospital) {
		this.eccv_visiting_hospital = eccv_visiting_hospital;
	}

	public String getEccv_visiting_cause() {
		return eccv_visiting_cause;
	}

	public void setEccv_visiting_cause(String eccv_visiting_cause) {
		this.eccv_visiting_cause = eccv_visiting_cause;
	}

	public String getEccv_claim_class() {
		return eccv_claim_class;
	}

	public void setEccv_claim_class(String eccv_claim_class) {
		this.eccv_claim_class = eccv_claim_class;
	}

	public String getEccv_rejected_total() {
		return eccv_rejected_total;
	}

	public void setEccv_rejected_total(String eccv_rejected_total) {
		this.eccv_rejected_total = eccv_rejected_total;
	}

	public String getEccv_delay_total() {
		return eccv_delay_total;
	}

	public void setEccv_delay_total(String eccv_delay_total) {
		this.eccv_delay_total = eccv_delay_total;
	}

	public String getEccv_claim_total() {
		return eccv_claim_total;
	}

	public void setEccv_claim_total(String eccv_claim_total) {
		this.eccv_claim_total = eccv_claim_total;
	}

	public String getEccv_bih_allowance() {
		return eccv_bih_allowance;
	}

	public void setEccv_bih_allowance(String eccv_bih_allowance) {
		this.eccv_bih_allowance = eccv_bih_allowance;
	}

	public String getEccv_remark() {
		return eccv_remark;
	}

	public void setEccv_remark(String eccv_remark) {
		this.eccv_remark = eccv_remark;
	}

	public String getEccv_rejected_case() {
		return eccv_rejected_case;
	}

	public void setEccv_rejected_case(String eccv_rejected_case) {
		this.eccv_rejected_case = eccv_rejected_case;
	}

	public String getEccv_email_state() {
		return eccv_email_state;
	}

	public void setEccv_email_state(String eccv_email_state) {
		this.eccv_email_state = eccv_email_state;
	}

	public String getEccv_claim_bclass() {
		return eccv_claim_bclass;
	}

	public void setEccv_claim_bclass(String eccv_claim_bclass) {
		this.eccv_claim_bclass = eccv_claim_bclass;
	}

	public List<CI_InsurantClaimVistModel> getPid_list() {
		return pid_list;
	}

	public void setPid_list(List<CI_InsurantClaimVistModel> pid_list) {
		this.pid_list = pid_list;
	}

	public int getEcci_id() {
		return ecci_id;
	}

	public void setEcci_id(int ecci_id) {
		this.ecci_id = ecci_id;
	}

	public int getEcci_viid() {
		return ecci_viid;
	}

	public void setEcci_viid(int ecci_viid) {
		this.ecci_viid = ecci_viid;
	}

	public String getEcci_pid() {
		return ecci_pid;
	}

	public void setEcci_pid(String ecci_pid) {
		this.ecci_pid = ecci_pid;
	}

	public String getEcci_addname() {
		return ecci_addname;
	}

	public void setEcci_addname(String ecci_addname) {
		this.ecci_addname = ecci_addname;
	}

	public String getEcci_addtime() {
		return ecci_addtime;
	}

	public void setEcci_addtime(String ecci_addtime) {
		this.ecci_addtime = ecci_addtime;
	}

	public String getEcci_invoice_money() {
		return ecci_invoice_money;
	}

	public void setEcci_invoice_money(String ecci_invoice_money) {
		this.ecci_invoice_money = ecci_invoice_money;
	}

	public String getEcci_west_medicine() {
		return ecci_west_medicine;
	}

	public void setEcci_west_medicine(String ecci_west_medicine) {
		this.ecci_west_medicine = ecci_west_medicine;
	}

	public String getEcci_chinese_medicine() {
		return ecci_chinese_medicine;
	}

	public void setEcci_chinese_medicine(String ecci_chinese_medicine) {
		this.ecci_chinese_medicine = ecci_chinese_medicine;
	}

	public String getEcci_herbal_medicine() {
		return ecci_herbal_medicine;
	}

	public void setEcci_herbal_medicine(String ecci_herbal_medicine) {
		this.ecci_herbal_medicine = ecci_herbal_medicine;
	}

	public String getEcci_exmine_fee() {
		return ecci_exmine_fee;
	}

	public void setEcci_exmine_fee(String ecci_exmine_fee) {
		this.ecci_exmine_fee = ecci_exmine_fee;
	}

	public String getEcci_bed_fee() {
		return ecci_bed_fee;
	}

	public void setEcci_bed_fee(String ecci_bed_fee) {
		this.ecci_bed_fee = ecci_bed_fee;
	}

	public String getEcci_check_fee() {
		return ecci_check_fee;
	}

	public void setEcci_check_fee(String ecci_check_fee) {
		this.ecci_check_fee = ecci_check_fee;
	}

	public String getEcci_ct_fee() {
		return ecci_ct_fee;
	}

	public void setEcci_ct_fee(String ecci_ct_fee) {
		this.ecci_ct_fee = ecci_ct_fee;
	}

	public String getEcci_prove_fee() {
		return ecci_prove_fee;
	}

	public void setEcci_prove_fee(String ecci_prove_fee) {
		this.ecci_prove_fee = ecci_prove_fee;
	}

	public String getEcci_cure_fee() {
		return ecci_cure_fee;
	}

	public void setEcci_cure_fee(String ecci_cure_fee) {
		this.ecci_cure_fee = ecci_cure_fee;
	}

	public String getEcci_ops_fee() {
		return ecci_ops_fee;
	}

	public void setEcci_ops_fee(String ecci_ops_fee) {
		this.ecci_ops_fee = ecci_ops_fee;
	}

	public String getEcci_other_fee() {
		return ecci_other_fee;
	}

	public void setEcci_other_fee(String ecci_other_fee) {
		this.ecci_other_fee = ecci_other_fee;
	}

	public String getEcci_service_fee() {
		return ecci_service_fee;
	}

	public void setEcci_service_fee(String ecci_service_fee) {
		this.ecci_service_fee = ecci_service_fee;
	}

	public String getEcci_stuff_fee() {
		return ecci_stuff_fee;
	}

	public void setEcci_stuff_fee(String ecci_stuff_fee) {
		this.ecci_stuff_fee = ecci_stuff_fee;
	}

	public String getEcci_register_fee() {
		return ecci_register_fee;
	}

	public void setEcci_register_fee(String ecci_register_fee) {
		this.ecci_register_fee = ecci_register_fee;
	}

	public String getEcci_nurse_fee() {
		return ecci_nurse_fee;
	}

	public void setEcci_nurse_fee(String ecci_nurse_fee) {
		this.ecci_nurse_fee = ecci_nurse_fee;
	}

	public String getEcci_claim_total() {
		return ecci_claim_total;
	}

	public void setEcci_claim_total(String ecci_claim_total) {
		this.ecci_claim_total = ecci_claim_total;
	}

	public String getEcci_state() {
		return ecci_state;
	}

	public void setEcci_state(String ecci_state) {
		this.ecci_state = ecci_state;
	}

	public String getEcci_change() {
		return ecci_change;
	}

	public void setEcci_change(String ecci_change) {
		this.ecci_change = ecci_change;
	}

	public String getEcci_auditing_money() {
		return ecci_auditing_money;
	}

	public void setEcci_auditing_money(String ecci_auditing_money) {
		this.ecci_auditing_money = ecci_auditing_money;
	}

	public String getEcci_rejected_duty() {
		return ecci_rejected_duty;
	}

	public void setEcci_rejected_duty(String ecci_rejected_duty) {
		this.ecci_rejected_duty = ecci_rejected_duty;
	}

	public String getEcci_rejected_pdpaid() {
		return ecci_rejected_pdpaid;
	}

	public void setEcci_rejected_pdpaid(String ecci_rejected_pdpaid) {
		this.ecci_rejected_pdpaid = ecci_rejected_pdpaid;
	}

	public String getEcci_rejected_medicine() {
		return ecci_rejected_medicine;
	}

	public void setEcci_rejected_medicine(String ecci_rejected_medicine) {
		this.ecci_rejected_medicine = ecci_rejected_medicine;
	}

	public String getEcci_rejected_stuff() {
		return ecci_rejected_stuff;
	}

	public void setEcci_rejected_stuff(String ecci_rejected_stuff) {
		this.ecci_rejected_stuff = ecci_rejected_stuff;
	}

	public String getEcci_rejected_check() {
		return ecci_rejected_check;
	}

	public void setEcci_rejected_check(String ecci_rejected_check) {
		this.ecci_rejected_check = ecci_rejected_check;
	}

	public String getEcci_rejected_hospital() {
		return ecci_rejected_hospital;
	}

	public void setEcci_rejected_hospital(String ecci_rejected_hospital) {
		this.ecci_rejected_hospital = ecci_rejected_hospital;
	}

	public String getEcci_rejected_other_fee() {
		return ecci_rejected_other_fee;
	}

	public void setEcci_rejected_other_fee(String ecci_rejected_other_fee) {
		this.ecci_rejected_other_fee = ecci_rejected_other_fee;
	}

	public String getEcci_rejected_no_fee() {
		return ecci_rejected_no_fee;
	}

	public void setEcci_rejected_no_fee(String ecci_rejected_no_fee) {
		this.ecci_rejected_no_fee = ecci_rejected_no_fee;
	}

	public String getEcci_rejected_up_fee() {
		return ecci_rejected_up_fee;
	}

	public void setEcci_rejected_up_fee(String ecci_rejected_up_fee) {
		this.ecci_rejected_up_fee = ecci_rejected_up_fee;
	}

	public String getEcci_delay_case_history() {
		return ecci_delay_case_history;
	}

	public void setEcci_delay_case_history(String ecci_delay_case_history) {
		this.ecci_delay_case_history = ecci_delay_case_history;
	}

	public String getEcci_delay_invoice() {
		return ecci_delay_invoice;
	}

	public void setEcci_delay_invoice(String ecci_delay_invoice) {
		this.ecci_delay_invoice = ecci_delay_invoice;
	}

	public String getEcci_delay_leechdom_list() {
		return ecci_delay_leechdom_list;
	}

	public void setEcci_delay_leechdom_list(String ecci_delay_leechdom_list) {
		this.ecci_delay_leechdom_list = ecci_delay_leechdom_list;
	}

	public String getEcci_delay_paper() {
		return ecci_delay_paper;
	}

	public void setEcci_delay_paper(String ecci_delay_paper) {
		this.ecci_delay_paper = ecci_delay_paper;
	}

	public String getEcci_delay_check_report() {
		return ecci_delay_check_report;
	}

	public void setEcci_delay_check_report(String ecci_delay_check_report) {
		this.ecci_delay_check_report = ecci_delay_check_report;
	}

	public String getEcci_delay_other() {
		return ecci_delay_other;
	}

	public void setEcci_delay_other(String ecci_delay_other) {
		this.ecci_delay_other = ecci_delay_other;
	}

	public String getEcci_rejected_case() {
		return ecci_rejected_case;
	}

	public void setEcci_rejected_case(String ecci_rejected_case) {
		this.ecci_rejected_case = ecci_rejected_case;
	}

	public String getEcci_autime() {
		return ecci_autime;
	}

	public void setEcci_autime(String ecci_autime) {
		this.ecci_autime = ecci_autime;
	}

}
