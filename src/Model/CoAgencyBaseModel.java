package Model;

import java.math.BigDecimal;

public class CoAgencyBaseModel {

	java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
	private int coab_id;
	private String coab_name;
	private String coab_shortname;
	private String coab_namespell;
	private String coab_province;
	private String coab_city;
	private String coab_setuptype;
	private String coab_source;
	private String coab_capital;
	private String coab_regaddress;
	private String coab_companymanager;
	private String coab_business;
	private String coab_remark;
	private String coab_addname;
	private String coab_addtime;
	private int coab_state;
	private int coas_id;
	private int type;
	private int serCount;
	private String name;
	private Integer cabc_id;
	private CoAgencyBaseServiceModel cabsModel;
	private CoAgencyBaseCityRelModel crModel;
	private int cabc_ifdefault;
	private String coas_client;

	private BigDecimal cabc_fee;

	public CoAgencyBaseModel() {

	}

	// 拼接机构名称（委托机构新增）

	public BigDecimal getCabc_fee() {
		return cabc_fee;
	}

	public void setCabc_fee(BigDecimal cabc_fee) {
		this.cabc_fee = new BigDecimal(df.format(cabc_fee));
	}

	public int getCoab_id() {
		return coab_id;
	}

	public void setCoab_id(int coab_id) {
		this.coab_id = coab_id;
	}

	public String getCoab_name() {
		return coab_name;
	}

	public void setCoab_name(String coab_name) {
		this.coab_name = coab_name;
	}

	public String getCoab_namespell() {
		return coab_namespell;
	}

	public void setCoab_namespell(String coab_namespell) {
		this.coab_namespell = coab_namespell;
	}

	public String getCoab_province() {
		return coab_province;
	}

	public void setCoab_province(String coab_province) {
		this.coab_province = coab_province;
	}

	public String getCoab_city() {
		return coab_city;
	}

	public void setCoab_city(String coab_city) {
		this.coab_city = coab_city;
	}

	public String getCoab_setuptype() {
		return coab_setuptype;
	}

	public void setCoab_setuptype(String coab_setuptype) {
		this.coab_setuptype = coab_setuptype;
	}

	public String getCoab_source() {
		return coab_source;
	}

	public void setCoab_source(String coab_source) {
		this.coab_source = coab_source;
	}

	public String getCoab_capital() {
		return coab_capital;
	}

	public void setCoab_capital(String coab_capital) {
		this.coab_capital = coab_capital;
	}

	public String getCoab_regaddress() {
		return coab_regaddress;
	}

	public void setCoab_regaddress(String coab_regaddress) {
		this.coab_regaddress = coab_regaddress;
	}

	public String getCoab_companymanager() {
		return coab_companymanager;
	}

	public void setCoab_companymanager(String coab_companymanager) {
		this.coab_companymanager = coab_companymanager;
	}

	public String getCoab_business() {
		return coab_business;
	}

	public void setCoab_business(String coab_business) {
		this.coab_business = coab_business;
	}

	public String getCoab_remark() {
		return coab_remark;
	}

	public void setCoab_remark(String coab_remark) {
		this.coab_remark = coab_remark;
	}

	public String getCoab_addname() {
		return coab_addname;
	}

	public void setCoab_addname(String coab_addname) {
		this.coab_addname = coab_addname;
	}

	public String getCoab_addtime() {
		return coab_addtime;
	}

	public void setCoab_addtime(String coab_addtime) {
		this.coab_addtime = coab_addtime;
	}

	public int getCoab_state() {
		return coab_state;
	}

	public void setCoab_state(int coab_state) {
		this.coab_state = coab_state;
	}

	public int getCoas_id() {
		return coas_id;
	}

	public void setCoas_id(int coas_id) {
		this.coas_id = coas_id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public CoAgencyBaseServiceModel getCabsModel() {
		return cabsModel;
	}

	public void setCabsModel(CoAgencyBaseServiceModel cabsModel) {
		this.cabsModel = cabsModel;
	}

	public int getSerCount() {
		return serCount;
	}

	public void setSerCount(int serCount) {
		this.serCount = serCount;
	}

	public CoAgencyBaseCityRelModel getCrModel() {
		return crModel;
	}

	public void setCrModel(CoAgencyBaseCityRelModel crModel) {
		this.crModel = crModel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCabc_id() {
		return cabc_id;
	}

	public void setCabc_id(Integer cabc_id) {
		this.cabc_id = cabc_id;
	}

	public int getCabc_ifdefault() {
		return cabc_ifdefault;
	}

	public void setCabc_ifdefault(int cabc_ifdefault) {
		this.cabc_ifdefault = cabc_ifdefault;
	}

	public String getCoas_client() {
		return coas_client;
	}

	public void setCoas_client(String coas_client) {
		this.coas_client = coas_client;
	}

	public String getCoab_shortname() {
		return coab_shortname;
	}

	public void setCoab_shortname(String coab_shortname) {
		this.coab_shortname = coab_shortname;
	}

}
