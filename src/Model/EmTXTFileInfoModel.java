package Model;

import java.math.BigDecimal;

public class EmTXTFileInfoModel {
	private int etfi_id;
	private int gid;
	private int cid;
	private int esda_id;
	private String etfi_payment_batch;
	private String etfi_o_bank;
	private String etfi_bank;
	private String etfi_ba_name;
	private String etfi_bank_account;
	private int etfi_usage_type;
	private String etfi_remit_ba;
	private String etfi_remit_company;
	private String etfi_code;
	private BigDecimal etfi_pay;
	private String etfi_txt_date;
	private String etfi_txt_people;
	private String etfi_txt_batch;
	private String etfi_same_ban;
	private String etfi_same_ba;
	private int etfi_state;
	private String ownmonth;
	private String esda_if_bms;
	private BigDecimal esda_write_off;
	private String emba_name;
	private String coba_shortname;
	private String state;
	private String usage_type;
	private String etype;
	public EmTXTFileInfoModel() {
		super();
	}

	public EmTXTFileInfoModel(int etfi_id, int gid, int cid, int esda_id,
			String etfi_payment_batch, String etfi_o_bank, String etfi_bank,
			String etfi_ba_name, String etfi_bank_account, int etfi_usage_type,
			String etfi_remit_ba, String etfi_remit_company, String etfi_code,
			BigDecimal etfi_pay, String etfi_txt_date, String etfi_txt_people,
			String etfi_txt_batch, String etfi_same_ban, String etfi_same_ba,
			int etfi_state, String ownmonth, String esda_if_bms,
			BigDecimal esda_write_off, String emba_name, String coba_shortname,
			String state, String usage_type) {
		super();
		this.etfi_id = etfi_id;
		this.gid = gid;
		this.cid = cid;
		this.esda_id = esda_id;
		this.etfi_payment_batch = etfi_payment_batch;
		this.etfi_o_bank = etfi_o_bank;
		this.etfi_bank = etfi_bank;
		this.etfi_ba_name = etfi_ba_name;
		this.etfi_bank_account = etfi_bank_account;
		this.etfi_usage_type = etfi_usage_type;
		this.etfi_remit_ba = etfi_remit_ba;
		this.etfi_remit_company = etfi_remit_company;
		this.etfi_code = etfi_code;
		this.etfi_pay = etfi_pay;
		this.etfi_txt_date = etfi_txt_date;
		this.etfi_txt_people = etfi_txt_people;
		this.etfi_txt_batch = etfi_txt_batch;
		this.etfi_same_ban = etfi_same_ban;
		this.etfi_same_ba = etfi_same_ba;
		this.etfi_state = etfi_state;
		this.ownmonth = ownmonth;
		this.esda_if_bms = esda_if_bms;
		this.esda_write_off = esda_write_off;
		this.emba_name = emba_name;
		this.coba_shortname = coba_shortname;
		this.state = state;
		this.usage_type = usage_type;
	}

	public int getEtfi_usage_type() {
		return etfi_usage_type;
	}

	public void setEtfi_usage_type(int etfi_usage_type) {
		this.etfi_usage_type = etfi_usage_type;
	}

	public String getUsage_type() {
		return usage_type;
	}

	public void setUsage_type(String usage_type) {
		this.usage_type = usage_type;
	}

	public int getEtfi_id() {
		return etfi_id;
	}

	public void setEtfi_id(int etfi_id) {
		this.etfi_id = etfi_id;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public int getEsda_id() {
		return esda_id;
	}

	public void setEsda_id(int esda_id) {
		this.esda_id = esda_id;
	}

	public String getEtfi_payment_batch() {
		return etfi_payment_batch;
	}

	public void setEtfi_payment_batch(String etfi_payment_batch) {
		this.etfi_payment_batch = etfi_payment_batch;
	}

	public String getEtfi_o_bank() {
		return etfi_o_bank;
	}

	public void setEtfi_o_bank(String etfi_o_bank) {
		this.etfi_o_bank = etfi_o_bank;
	}

	public String getEtfi_bank() {
		return etfi_bank;
	}

	public void setEtfi_bank(String etfi_bank) {
		this.etfi_bank = etfi_bank;
	}

	public String getEtfi_ba_name() {
		return etfi_ba_name;
	}

	public void setEtfi_ba_name(String etfi_ba_name) {
		this.etfi_ba_name = etfi_ba_name;
	}

	public String getEtfi_bank_account() {
		return etfi_bank_account;
	}

	public void setEtfi_bank_account(String etfi_bank_account) {
		this.etfi_bank_account = etfi_bank_account;
	}

	public String getEtfi_remit_ba() {
		return etfi_remit_ba;
	}

	public void setEtfi_remit_ba(String etfi_remit_ba) {
		this.etfi_remit_ba = etfi_remit_ba;
	}

	public String getEtfi_remit_company() {
		return etfi_remit_company;
	}

	public void setEtfi_remit_company(String etfi_remit_company) {
		this.etfi_remit_company = etfi_remit_company;
	}

	public String getEtfi_code() {
		return etfi_code;
	}

	public void setEtfi_code(String etfi_code) {
		this.etfi_code = etfi_code;
	}

	public BigDecimal getEtfi_pay() {
		return etfi_pay.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setEtfi_pay(BigDecimal etfi_pay) {
		this.etfi_pay = etfi_pay;
	}

	public String getEtfi_txt_date() {
		return etfi_txt_date;
	}

	public void setEtfi_txt_date(String etfi_txt_date) {
		this.etfi_txt_date = etfi_txt_date;
	}

	public String getEtfi_txt_people() {
		return etfi_txt_people;
	}

	public void setEtfi_txt_people(String etfi_txt_people) {
		this.etfi_txt_people = etfi_txt_people;
	}

	public String getEtfi_txt_batch() {
		return etfi_txt_batch;
	}

	public void setEtfi_txt_batch(String etfi_txt_batch) {
		this.etfi_txt_batch = etfi_txt_batch;
	}

	public String getEtfi_same_ban() {
		return etfi_same_ban;
	}

	public void setEtfi_same_ban(String etfi_same_ban) {
		this.etfi_same_ban = etfi_same_ban;
	}

	public String getEtfi_same_ba() {
		return etfi_same_ba;
	}

	public void setEtfi_same_ba(String etfi_same_ba) {
		this.etfi_same_ba = etfi_same_ba;
	}

	public int getEtfi_state() {
		return etfi_state;
	}

	public void setEtfi_state(int etfi_state) {
		this.etfi_state = etfi_state;
	}

	public String getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(String ownmonth) {
		this.ownmonth = ownmonth;
	}

	public String getEsda_if_bms() {
		return esda_if_bms;
	}

	public void setEsda_if_bms(String esda_if_bms) {
		this.esda_if_bms = esda_if_bms;
	}

	public BigDecimal getEsda_write_off() {
		return esda_write_off.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setEsda_write_off(BigDecimal esda_write_off) {
		this.esda_write_off = esda_write_off;
	}

	public String getEmba_name() {
		return emba_name;
	}

	public void setEmba_name(String emba_name) {
		this.emba_name = emba_name;
	}

	public String getCoba_shortname() {
		return coba_shortname;
	}

	public void setCoba_shortname(String coba_shortname) {
		this.coba_shortname = coba_shortname;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getEtype() {
		return etype;
	}

	public void setEtype(String etype) {
		this.etype = etype;
	}

}
