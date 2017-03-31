package Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CoAgencyBaseCityRelViewModel {
	
	private final DecimalFormat df = new DecimalFormat("#.00");
	private String province;
	private String city;
	private int cabc_id;
	private int cabc_coab_id;
	private int cabc_ppc_id;
	private String cabc_addname;
	private String cabc_addtime;
	private int cabc_state;
	private int cabc_ifdefault;
	private int coab_id;
	private String coab_name;
	private String coab_namespell;
	private String coab_province;
	private String coab_city;
	private String coab_setuptype;
	private String coab_source;
	private String coab_capital;
	private String coab_regaddress;
	private String coab_companymanager;
	private String coab_coaddress;
	private String coab_zipcode;
	private String coab_business;
	private int coab_sendinvoice;
	private int coab_payday;
	private String coab_paymon;
	private String coab_accountbank;
	private String coab_accountnum;
	private String coab_hz;
	private String coab_remark;
	private String coab_client;
	private String coab_addname;
	private String coab_addtime;
	private int coab_state;
	private BigDecimal cabc_fee;

	public CoAgencyBaseCityRelViewModel() {
		super();
	}

	public CoAgencyBaseCityRelViewModel(int cabc_id, int cabc_coab_id,
			String coab_name, String coab_namespell, String coab_setuptype,
			String province, String city) {
		super();
		this.cabc_id = cabc_id;
		this.cabc_coab_id = cabc_coab_id;
		this.coab_name = coab_name;
		this.coab_namespell = coab_namespell;
		this.coab_setuptype = coab_setuptype;
		this.province = province;
		this.city = city;
	}

	
	
	public BigDecimal getCabc_fee() {
		return cabc_fee;
	}

	public void setCabc_fee(BigDecimal cabc_fee) {
		this.cabc_fee = new BigDecimal(df.format(cabc_fee));
	}

	public int getCabc_id() {
		return cabc_id;
	}

	public void setCabc_id(int cabc_id) {
		this.cabc_id = cabc_id;
	}

	public int getCabc_coab_id() {
		return cabc_coab_id;
	}

	public void setCabc_coab_id(int cabc_coab_id) {
		this.cabc_coab_id = cabc_coab_id;
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

	public String getCoab_setuptype() {
		return coab_setuptype;
	}

	public void setCoab_setuptype(String coab_setuptype) {
		this.coab_setuptype = coab_setuptype;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getCabc_ppc_id() {
		return cabc_ppc_id;
	}

	public void setCabc_ppc_id(int cabc_ppc_id) {
		this.cabc_ppc_id = cabc_ppc_id;
	}

	public String getCabc_addname() {
		return cabc_addname;
	}

	public void setCabc_addname(String cabc_addname) {
		this.cabc_addname = cabc_addname;
	}

	public String getCabc_addtime() {
		return cabc_addtime;
	}

	public void setCabc_addtime(String cabc_addtime) {
		this.cabc_addtime = cabc_addtime;
	}

	public int getCabc_state() {
		return cabc_state;
	}

	public void setCabc_state(int cabc_state) {
		this.cabc_state = cabc_state;
	}

	public int getCabc_ifdefault() {
		return cabc_ifdefault;
	}

	public void setCabc_ifdefault(int cabc_ifdefault) {
		this.cabc_ifdefault = cabc_ifdefault;
	}

	public int getCoab_id() {
		return coab_id;
	}

	public void setCoab_id(int coab_id) {
		this.coab_id = coab_id;
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

	public String getCoab_coaddress() {
		return coab_coaddress;
	}

	public void setCoab_coaddress(String coab_coaddress) {
		this.coab_coaddress = coab_coaddress;
	}

	public String getCoab_zipcode() {
		return coab_zipcode;
	}

	public void setCoab_zipcode(String coab_zipcode) {
		this.coab_zipcode = coab_zipcode;
	}

	public String getCoab_business() {
		return coab_business;
	}

	public void setCoab_business(String coab_business) {
		this.coab_business = coab_business;
	}

	public int getCoab_sendinvoice() {
		return coab_sendinvoice;
	}

	public void setCoab_sendinvoice(int coab_sendinvoice) {
		this.coab_sendinvoice = coab_sendinvoice;
	}

	public int getCoab_payday() {
		return coab_payday;
	}

	public void setCoab_payday(int coab_payday) {
		this.coab_payday = coab_payday;
	}

	public String getCoab_paymon() {
		return coab_paymon;
	}

	public void setCoab_paymon(String coab_paymon) {
		this.coab_paymon = coab_paymon;
	}

	public String getCoab_accountbank() {
		return coab_accountbank;
	}

	public void setCoab_accountbank(String coab_accountbank) {
		this.coab_accountbank = coab_accountbank;
	}

	public String getCoab_accountnum() {
		return coab_accountnum;
	}

	public void setCoab_accountnum(String coab_accountnum) {
		this.coab_accountnum = coab_accountnum;
	}

	public String getCoab_hz() {
		return coab_hz;
	}

	public void setCoab_hz(String coab_hz) {
		this.coab_hz = coab_hz;
	}

	public String getCoab_remark() {
		return coab_remark;
	}

	public void setCoab_remark(String coab_remark) {
		this.coab_remark = coab_remark;
	}

	public String getCoab_client() {
		return coab_client;
	}

	public void setCoab_client(String coab_client) {
		this.coab_client = coab_client;
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

}
