package Model;

public class CoFinanceVATModel implements Cloneable {
	private Integer cfva_id;
	private Integer cid;
	private String cfva_number;
	private String cfva_company;
	private String cfva_reg_add;// 注册地址
	private String cfva_title;// 发条抬头
	private String cfva_tel;// 电话
	private String cfva_taxpayers;// 增值税一般纳税人
	private String cfva_number1;// 纳税人识别号（15位数字）
	private String cfva_number2;// 三证合一号码(18位字符)
	private String cfva_bank_acc;// 银行账号
	private String cfva_bank;// 银行
	private String cfva_contact;// 发票联系人
	private String cfva_contact_tel;// 发票联系人电话
	private String cfva_vat_add;// 发票接收地址
	private String cfva_remark;// 备注
	private String cfva_addname;//
	private String cfva_addtime;//

	private Boolean cfva_only;
	private Boolean cfva_simple;

	private Boolean cfva_sp;
	private Boolean cfva_confirm;

	public CoFinanceVATModel() {
		super();
	}

	public Integer getCfva_id() {
		return cfva_id;
	}

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			return null;
		}

	}

	public void setCfva_id(Integer cfva_id) {
		this.cfva_id = cfva_id;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getCfva_company() {
		return cfva_company;
	}

	public void setCfva_company(String cfva_company) {
		this.cfva_company = cfva_company;
	}

	public String getCfva_reg_add() {
		return cfva_reg_add;
	}

	public void setCfva_reg_add(String cfva_reg_add) {
		this.cfva_reg_add = cfva_reg_add;
	}

	public String getCfva_title() {
		return cfva_title;
	}

	public void setCfva_title(String cfva_title) {
		this.cfva_title = cfva_title;
	}

	public String getCfva_tel() {
		return cfva_tel;
	}

	public void setCfva_tel(String cfva_tel) {
		this.cfva_tel = cfva_tel;
	}

	public String getCfva_taxpayers() {
		return cfva_taxpayers;
	}

	public void setCfva_taxpayers(String cfva_taxpayers) {
		this.cfva_taxpayers = cfva_taxpayers;
	}

	public String getCfva_number1() {
		return cfva_number1;
	}

	public void setCfva_number1(String cfva_number1) {
		this.cfva_number1 = cfva_number1;
	}

	public String getCfva_number2() {
		return cfva_number2;
	}

	public void setCfva_number2(String cfva_number2) {
		this.cfva_number2 = cfva_number2;
	}

	public String getCfva_bank_acc() {
		return cfva_bank_acc;
	}

	public void setCfva_bank_acc(String cfva_bank_acc) {
		this.cfva_bank_acc = cfva_bank_acc;
	}

	public String getCfva_bank() {
		return cfva_bank;
	}

	public void setCfva_bank(String cfva_bank) {
		this.cfva_bank = cfva_bank;
	}

	public String getCfva_contact() {
		return cfva_contact;
	}

	public void setCfva_contact(String cfva_contact) {
		this.cfva_contact = cfva_contact;
	}

	public String getCfva_contact_tel() {
		return cfva_contact_tel;
	}

	public void setCfva_contact_tel(String cfva_contact_tel) {
		this.cfva_contact_tel = cfva_contact_tel;
	}

	public String getCfva_vat_add() {
		return cfva_vat_add;
	}

	public void setCfva_vat_add(String cfva_vat_add) {
		this.cfva_vat_add = cfva_vat_add;
	}

	public String getCfva_remark() {
		return cfva_remark;
	}

	public void setCfva_remark(String cfva_remark) {
		this.cfva_remark = cfva_remark;
	}

	public String getCfva_addname() {
		return cfva_addname;
	}

	public void setCfva_addname(String cfva_addname) {
		this.cfva_addname = cfva_addname;
	}

	public String getCfva_addtime() {
		return cfva_addtime;
	}

	public void setCfva_addtime(String cfva_addtime) {
		this.cfva_addtime = cfva_addtime;
	}

	public String getCfva_number() {
		return cfva_number;
	}

	public void setCfva_number(String cfva_number) {
		this.cfva_number = cfva_number;
	}

	public Boolean getCfva_only() {
		return cfva_only;
	}

	public void setCfva_only(Boolean cfva_only) {
		this.cfva_only = cfva_only;
	}

	public Boolean getCfva_simple() {
		return cfva_simple;
	}

	public void setCfva_simple(Boolean cfva_simple) {
		this.cfva_simple = cfva_simple;
	}

	public Boolean getCfva_sp() {
		return cfva_sp;
	}

	public void setCfva_sp(Boolean cfva_sp) {
		this.cfva_sp = cfva_sp;
	}

	public Boolean getCfva_confirm() {
		return cfva_confirm;
	}

	public void setCfva_confirm(Boolean cfva_confirm) {
		this.cfva_confirm = cfva_confirm;
	}

}
