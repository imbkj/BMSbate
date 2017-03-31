package Model;

import java.math.BigDecimal;

public class EmTaxInfoModel {
	private BigDecimal zero = BigDecimal.ZERO;
	private int etin_id;
	private int cid;
	private int gid;
	private int ownmonth;
	private int esda_id;
	private String etin_name;
	private String etin_idcard;
	private String etin_nationality;
	private int etin_tax_class;
	private BigDecimal etin_tax_base = zero;
	private BigDecimal etin_tax = zero;
	private int etin_state;
	private String etin_file_num;
	private String etin_declare_date;
	private String etin_success_date;
	private String etin_remark;
	private String etin_addname;
	private String etin_addtime;
	private String tax_class;
	private String state;

	private String idcardclass;
	private String esin_taxplace;
	private String nationality;
	private String birth;
	private String sex;
	private String gtstate;
	private String company;
	private String manageraddress;
	private int ifNew;// 是否新增(本月)

	private BigDecimal etin_tax1 = zero; // 个人所得税汇总
	private BigDecimal etin_tax2 = zero; // 年终奖金税汇总
	private BigDecimal etin_tax3 = zero; // 股票税汇总
	private BigDecimal etin_tax4 = zero; // 离职补偿金税汇总
	private BigDecimal etin_tax5 = zero; // 劳务报酬个人所得税汇总
	private BigDecimal etin_tax6 = zero; // 支付模块个人所得税汇总
	private BigDecimal coga_t6 = zero; // 个税实际收款
	private BigDecimal balance = zero; // 相差对比

	private String client;
	private int y_cou;// 应分配人数
	private int s_cou;// 实际分配人数

	private String chk_cgli;// 判断是否有选择个税项目
	private String coab_shortname;// 受托机构简称
	private int gz_ownmonth;// 工资劳务费月份

	private String esin_nexttaxplace;// 提前录入的个税申报地
	private int esin_nexttaxplace_smonth;// 提前录入的个税申报地生效月份
	private int taxplace_smonth;// 提前录入的个税申报地生效月份

	private String mobile;// 联系电话

	public EmTaxInfoModel() {
		super();
	}

	public EmTaxInfoModel(BigDecimal zero, int etin_id, int cid, int gid,
			int ownmonth, int esda_id, String etin_name, String etin_idcard,
			String etin_nationality, int etin_tax_class,
			BigDecimal etin_tax_base, BigDecimal etin_tax, int etin_state,
			String etin_file_num, String etin_declare_date,
			String etin_success_date, String etin_remark, String etin_addname,
			String etin_addtime, String tax_class, String state,
			String idcardclass, String esin_taxplace, String nationality,
			String birth, String sex, String gtstate, String company,
			String manageraddress, int ifNew, BigDecimal etin_tax1,
			BigDecimal etin_tax2, BigDecimal etin_tax3, BigDecimal etin_tax4,
			BigDecimal etin_tax5, BigDecimal coga_t6, BigDecimal balance) {
		super();
		this.zero = zero;
		this.etin_id = etin_id;
		this.cid = cid;
		this.gid = gid;
		this.ownmonth = ownmonth;
		this.esda_id = esda_id;
		this.etin_name = etin_name;
		this.etin_idcard = etin_idcard;
		this.etin_nationality = etin_nationality;
		this.etin_tax_class = etin_tax_class;
		this.etin_tax_base = etin_tax_base;
		this.etin_tax = etin_tax;
		this.etin_state = etin_state;
		this.etin_file_num = etin_file_num;
		this.etin_declare_date = etin_declare_date;
		this.etin_success_date = etin_success_date;
		this.etin_remark = etin_remark;
		this.etin_addname = etin_addname;
		this.etin_addtime = etin_addtime;
		this.tax_class = tax_class;
		this.state = state;
		this.idcardclass = idcardclass;
		this.esin_taxplace = esin_taxplace;
		this.nationality = nationality;
		this.birth = birth;
		this.sex = sex;
		this.gtstate = gtstate;
		this.company = company;
		this.manageraddress = manageraddress;
		this.ifNew = ifNew;
		this.etin_tax1 = etin_tax1;
		this.etin_tax2 = etin_tax2;
		this.etin_tax3 = etin_tax3;
		this.etin_tax4 = etin_tax4;
		this.etin_tax5 = etin_tax5;
		this.coga_t6 = coga_t6;
		this.balance = balance;
	}

	public String getChk_cgli() {
		return chk_cgli;
	}

	public void setChk_cgli(String chk_cgli) {
		this.chk_cgli = chk_cgli;
	}

	public BigDecimal getZero() {
		return zero;
	}

	public void setZero(BigDecimal zero) {
		this.zero = zero;
	}

	public int getEtin_id() {
		return etin_id;
	}

	public void setEtin_id(int etin_id) {
		this.etin_id = etin_id;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public int getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(int ownmonth) {
		this.ownmonth = ownmonth;
	}

	public int getEsda_id() {
		return esda_id;
	}

	public void setEsda_id(int esda_id) {
		this.esda_id = esda_id;
	}

	public String getEtin_name() {
		return etin_name;
	}

	public void setEtin_name(String etin_name) {
		this.etin_name = etin_name;
	}

	public String getEtin_idcard() {
		return etin_idcard;
	}

	public void setEtin_idcard(String etin_idcard) {
		this.etin_idcard = etin_idcard;
	}

	public String getEtin_nationality() {
		return etin_nationality;
	}

	public void setEtin_nationality(String etin_nationality) {
		this.etin_nationality = etin_nationality;
	}

	public int getEtin_tax_class() {
		return etin_tax_class;
	}

	public void setEtin_tax_class(int etin_tax_class) {
		this.etin_tax_class = etin_tax_class;
	}

	public BigDecimal getEtin_tax_base() {
		return etin_tax_base;
	}

	public void setEtin_tax_base(BigDecimal etin_tax_base) {
		this.etin_tax_base = etin_tax_base;
	}

	public BigDecimal getEtin_tax() {
		return etin_tax;
	}

	public void setEtin_tax(BigDecimal etin_tax) {
		this.etin_tax = etin_tax;
	}

	public int getEtin_state() {
		return etin_state;
	}

	public void setEtin_state(int etin_state) {
		this.etin_state = etin_state;
	}

	public String getEtin_file_num() {
		return etin_file_num;
	}

	public void setEtin_file_num(String etin_file_num) {
		this.etin_file_num = etin_file_num;
	}

	public String getEtin_declare_date() {
		return etin_declare_date;
	}

	public void setEtin_declare_date(String etin_declare_date) {
		this.etin_declare_date = etin_declare_date;
	}

	public String getEtin_success_date() {
		return etin_success_date;
	}

	public void setEtin_success_date(String etin_success_date) {
		this.etin_success_date = etin_success_date;
	}

	public String getEtin_remark() {
		return etin_remark;
	}

	public void setEtin_remark(String etin_remark) {
		this.etin_remark = etin_remark;
	}

	public String getEtin_addname() {
		return etin_addname;
	}

	public void setEtin_addname(String etin_addname) {
		this.etin_addname = etin_addname;
	}

	public String getEtin_addtime() {
		return etin_addtime;
	}

	public void setEtin_addtime(String etin_addtime) {
		this.etin_addtime = etin_addtime;
	}

	public String getTax_class() {
		return tax_class;
	}

	public void setTax_class(String tax_class) {
		this.tax_class = tax_class;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIdcardclass() {
		return idcardclass;
	}

	public void setIdcardclass(String idcardclass) {
		this.idcardclass = idcardclass;
	}

	public String getEsin_taxplace() {
		return esin_taxplace;
	}

	public void setEsin_taxplace(String esin_taxplace) {
		this.esin_taxplace = esin_taxplace;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getGtstate() {
		return gtstate;
	}

	public void setGtstate(String gtstate) {
		this.gtstate = gtstate;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getManageraddress() {
		return manageraddress;
	}

	public void setManageraddress(String manageraddress) {
		this.manageraddress = manageraddress;
	}

	public int getIfNew() {
		return ifNew;
	}

	public void setIfNew(int ifNew) {
		this.ifNew = ifNew;
	}

	public BigDecimal getEtin_tax1() {
		return etin_tax1;
	}

	public void setEtin_tax1(BigDecimal etin_tax1) {
		this.etin_tax1 = etin_tax1;
	}

	public BigDecimal getEtin_tax2() {
		return etin_tax2;
	}

	public void setEtin_tax2(BigDecimal etin_tax2) {
		this.etin_tax2 = etin_tax2;
	}

	public BigDecimal getEtin_tax3() {
		return etin_tax3;
	}

	public void setEtin_tax3(BigDecimal etin_tax3) {
		this.etin_tax3 = etin_tax3;
	}

	public BigDecimal getEtin_tax4() {
		return etin_tax4;
	}

	public void setEtin_tax4(BigDecimal etin_tax4) {
		this.etin_tax4 = etin_tax4;
	}

	public BigDecimal getEtin_tax5() {
		return etin_tax5;
	}

	public void setEtin_tax5(BigDecimal etin_tax5) {
		this.etin_tax5 = etin_tax5;
	}

	public BigDecimal getCoga_t6() {
		return coga_t6;
	}

	public void setCoga_t6(BigDecimal coga_t6) {
		this.coga_t6 = coga_t6;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public int getY_cou() {
		return y_cou;
	}

	public void setY_cou(int y_cou) {
		this.y_cou = y_cou;
	}

	public int getS_cou() {
		return s_cou;
	}

	public void setS_cou(int s_cou) {
		this.s_cou = s_cou;
	}

	public String getCoab_shortname() {
		return coab_shortname;
	}

	public void setCoab_shortname(String coab_shortname) {
		this.coab_shortname = coab_shortname;
	}

	public int getGz_ownmonth() {
		return gz_ownmonth;
	}

	public void setGz_ownmonth(int gz_ownmonth) {
		this.gz_ownmonth = gz_ownmonth;
	}

	public String getEsin_nexttaxplace() {
		return esin_nexttaxplace;
	}

	public void setEsin_nexttaxplace(String esin_nexttaxplace) {
		this.esin_nexttaxplace = esin_nexttaxplace;
	}

	public int getEsin_nexttaxplace_smonth() {
		return esin_nexttaxplace_smonth;
	}

	public void setEsin_nexttaxplace_smonth(int esin_nexttaxplace_smonth) {
		this.esin_nexttaxplace_smonth = esin_nexttaxplace_smonth;
	}

	public int getTaxplace_smonth() {
		return taxplace_smonth;
	}

	public void setTaxplace_smonth(int taxplace_smonth) {
		this.taxplace_smonth = taxplace_smonth;
	}

	public BigDecimal getEtin_tax6() {
		return etin_tax6;
	}

	public void setEtin_tax6(BigDecimal etin_tax6) {
		this.etin_tax6 = etin_tax6;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
