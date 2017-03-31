package Model;

import java.math.BigDecimal;

public class EmSalaryInfoModel {

	private int esda_id;
	private int cid;
	private String name;
	private int ownmonth;
	private int csii_sequence;
	private String gzdm;
	private String gzmc;
	private BigDecimal gzje;

	private int gid;
	private String emba_nationality;
	private String esin_taxplace;
	private String esin_salaryplace;
	private String esin_hpro;
	private String esin_addname;

	private int cfin_id;
	private String esda_ba_name;
	private String esda_bcc_email;

	private int esin_id;
	private int esin_tapr_id;
	private String company;

	private String esin_cost_Type;
	private int esin_cost_id;
	private String esin_attachpassword;
	private String coba_company;
	private String emba_name;
	private String esin_cosm_typeurl;
	private String esin_cosm_model;

	private String emba_gz_account;
	private String emba_gz_bank;
	private String emba_writeoff_bank;
	private String emba_writeoff_account;
	private String emba_gz_email;
	private String emba_gz_cemail;
	private String emba_email;

	private Integer esin_nexttaxplace_smonth;// 提前录入的个税申报地生效月份

	public EmSalaryInfoModel() {
		super();
	}

	public EmSalaryInfoModel(int esda_id, int cid, String name, int ownmonth,
			int csii_sequence, String gzdm, String gzmc, BigDecimal gzje) {
		super();
		this.esda_id = esda_id;
		this.cid = cid;
		this.name = name;
		this.ownmonth = ownmonth;
		this.csii_sequence = csii_sequence;
		this.gzdm = gzdm;
		this.gzmc = gzmc;
		this.gzje = gzje;
	}

	public EmSalaryInfoModel(int cid, int csii_sequence, String gzdm,
			String gzmc) {
		super();
		this.cid = cid;
		this.csii_sequence = csii_sequence;
		this.gzdm = gzdm;
		this.gzmc = gzmc;
	}

	public EmSalaryInfoModel(int esda_id, int cid, String name, int ownmonth,
			int csii_sequence, String gzdm, String gzmc, BigDecimal gzje,
			int gid, String emba_nationality, String esin_taxplace,
			String esin_salaryplace, String esin_hpro, String esin_addname,
			int cfin_id, String esda_ba_name, String esda_bcc_email,
			int esin_id, int esin_tapr_id, String company) {
		super();
		this.esda_id = esda_id;
		this.cid = cid;
		this.name = name;
		this.ownmonth = ownmonth;
		this.csii_sequence = csii_sequence;
		this.gzdm = gzdm;
		this.gzmc = gzmc;
		this.gzje = gzje;
		this.gid = gid;
		this.emba_nationality = emba_nationality;
		this.esin_taxplace = esin_taxplace;
		this.esin_salaryplace = esin_salaryplace;
		this.esin_hpro = esin_hpro;
		this.esin_addname = esin_addname;
		this.cfin_id = cfin_id;
		this.esda_ba_name = esda_ba_name;
		this.esda_bcc_email = esda_bcc_email;
		this.esin_id = esin_id;
		this.esin_tapr_id = esin_tapr_id;
		this.company = company;
	}

	public String getEmba_gz_email() {
		return emba_gz_email;
	}

	public void setEmba_gz_email(String emba_gz_email) {
		this.emba_gz_email = emba_gz_email;
	}

	public String getEmba_gz_cemail() {
		return emba_gz_cemail;
	}

	public void setEmba_gz_cemail(String emba_gz_cemail) {
		this.emba_gz_cemail = emba_gz_cemail;
	}

	public String getEsin_cosm_typeurl() {
		return esin_cosm_typeurl;
	}

	public void setEsin_cosm_typeurl(String esin_cosm_typeurl) {
		this.esin_cosm_typeurl = esin_cosm_typeurl;
	}

	public String getCoba_company() {
		return coba_company;
	}

	public void setCoba_company(String coba_company) {
		this.coba_company = coba_company;
	}

	public String getEmba_name() {
		return emba_name;
	}

	public void setEmba_name(String emba_name) {
		this.emba_name = emba_name;
	}

	public String getEsin_cost_Type() {
		return esin_cost_Type;
	}

	public void setEsin_cost_Type(String esin_cost_Type) {
		this.esin_cost_Type = esin_cost_Type;
	}

	public String getEsin_cosm_model() {
		return esin_cosm_model;
	}

	public void setEsin_cosm_model(String esin_cosm_model) {
		this.esin_cosm_model = esin_cosm_model;
	}

	public int getEsin_cost_id() {
		return esin_cost_id;
	}

	public void setEsin_cost_id(int esin_cost_id) {
		this.esin_cost_id = esin_cost_id;
	}

	public String getEsin_attachpassword() {
		return esin_attachpassword;
	}

	public void setEsin_attachpassword(String esin_attachpassword) {
		this.esin_attachpassword = esin_attachpassword;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public int getEsin_id() {
		return esin_id;
	}

	public void setEsin_id(int esin_id) {
		this.esin_id = esin_id;
	}

	public int getEsin_tapr_id() {
		return esin_tapr_id;
	}

	public void setEsin_tapr_id(int esin_tapr_id) {
		this.esin_tapr_id = esin_tapr_id;
	}

	public String getEsda_bcc_email() {
		return esda_bcc_email;
	}

	public void setEsda_bcc_email(String esda_bcc_email) {
		this.esda_bcc_email = esda_bcc_email;
	}

	public String getEsda_ba_name() {
		return esda_ba_name;
	}

	public void setEsda_ba_name(String esda_ba_name) {
		this.esda_ba_name = esda_ba_name;
	}

	public int getCfin_id() {
		return cfin_id;
	}

	public void setCfin_id(int cfin_id) {
		this.cfin_id = cfin_id;
	}

	public int getEsda_id() {
		return esda_id;
	}

	public void setEsda_id(int esda_id) {
		this.esda_id = esda_id;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(int ownmonth) {
		this.ownmonth = ownmonth;
	}

	public int getCsii_sequence() {
		return csii_sequence;
	}

	public void setCsii_sequence(int csii_sequence) {
		this.csii_sequence = csii_sequence;
	}

	public String getGzdm() {
		return gzdm;
	}

	public void setGzdm(String gzdm) {
		this.gzdm = gzdm;
	}

	public String getGzmc() {
		return gzmc;
	}

	public void setGzmc(String gzmc) {
		this.gzmc = gzmc;
	}

	public BigDecimal getGzje() {
		return gzje;
	}

	public void setGzje(BigDecimal gzje) {
		this.gzje = gzje;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public String getEmba_nationality() {
		return emba_nationality;
	}

	public void setEmba_nationality(String emba_nationality) {
		this.emba_nationality = emba_nationality;
	}

	public String getEsin_taxplace() {
		return esin_taxplace;
	}

	public void setEsin_taxplace(String esin_taxplace) {
		this.esin_taxplace = esin_taxplace;
	}

	public String getEsin_salaryplace() {
		return esin_salaryplace;
	}

	public void setEsin_salaryplace(String esin_salaryplace) {
		this.esin_salaryplace = esin_salaryplace;
	}

	public String getEsin_hpro() {
		return esin_hpro;
	}

	public void setEsin_hpro(String esin_hpro) {
		this.esin_hpro = esin_hpro;
	}

	public String getEsin_addname() {
		return esin_addname;
	}

	public void setEsin_addname(String esin_addname) {
		this.esin_addname = esin_addname;
	}

	public String getEmba_gz_account() {
		return emba_gz_account;
	}

	public void setEmba_gz_account(String emba_gz_account) {
		this.emba_gz_account = emba_gz_account;
	}

	public String getEmba_gz_bank() {
		return emba_gz_bank;
	}

	public void setEmba_gz_bank(String emba_gz_bank) {
		this.emba_gz_bank = emba_gz_bank;
	}

	public String getEmba_writeoff_bank() {
		return emba_writeoff_bank;
	}

	public void setEmba_writeoff_bank(String emba_writeoff_bank) {
		this.emba_writeoff_bank = emba_writeoff_bank;
	}

	public String getEmba_writeoff_account() {
		return emba_writeoff_account;
	}

	public void setEmba_writeoff_account(String emba_writeoff_account) {
		this.emba_writeoff_account = emba_writeoff_account;
	}

	public String getEmba_email() {
		return emba_email;
	}

	public void setEmba_email(String emba_email) {
		this.emba_email = emba_email;
	}

	public Integer getEsin_nexttaxplace_smonth() {
		return esin_nexttaxplace_smonth;
	}

	public void setEsin_nexttaxplace_smonth(Integer esin_nexttaxplace_smonth) {
		this.esin_nexttaxplace_smonth = esin_nexttaxplace_smonth;
	}

}
