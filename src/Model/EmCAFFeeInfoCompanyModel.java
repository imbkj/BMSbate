package Model;

import java.math.BigDecimal;

import Util.DateStringChange;

public class EmCAFFeeInfoCompanyModel {
	private int cid;
	private String shortname;
	private String shortspell;
	private String client;
	private int all_fee;
	private int yf_fee;
	private int wf_fee;
	private int wd_loan_fee;
	private int wd_clear_fee;
	private int csd_loan_fee;
	private int csd_clear_fee;

	private int per_fee;
	private int esp_fee;
	private int wd_per_fee;
	private int wd_esp_fee;
	private int wd_all_fee;
	private int all_wd_bfee;

	private String ecfi_rec_date;

	public EmCAFFeeInfoCompanyModel() {
		super();
	}

	public EmCAFFeeInfoCompanyModel(int cid, String shortname,
			String shortspell, String client, int all_fee, int yf_fee,
			int wf_fee, int wd_loan_fee, int wd_clear_fee, int csd_loan_fee,
			int csd_clear_fee, int per_fee, int esp_fee, int wd_per_fee,
			int wd_esp_fee, int wd_all_fee, int all_wd_bfee,
			String ecfi_rec_date) {
		super();
		this.cid = cid;
		this.shortname = shortname;
		this.shortspell = shortspell;
		this.client = client;
		this.all_fee = all_fee;
		this.yf_fee = yf_fee;
		this.wf_fee = wf_fee;
		this.wd_loan_fee = wd_loan_fee;
		this.wd_clear_fee = wd_clear_fee;
		this.csd_loan_fee = csd_loan_fee;
		this.csd_clear_fee = csd_clear_fee;
		this.per_fee = per_fee;
		this.esp_fee = esp_fee;
		this.wd_per_fee = wd_per_fee;
		this.wd_esp_fee = wd_esp_fee;
		this.wd_all_fee = wd_all_fee;
		this.all_wd_bfee = all_wd_bfee;
		this.ecfi_rec_date = ecfi_rec_date;
	}

	public String getEcfi_rec_date() {
		return changeSDate(ecfi_rec_date);
	}

	public void setEcfi_rec_date(String ecfi_rec_date) {
		this.ecfi_rec_date = ecfi_rec_date;
	}

	public int getPer_fee() {
		return per_fee;
	}

	public void setPer_fee(int per_fee) {
		this.per_fee = per_fee;
	}

	public int getEsp_fee() {
		return esp_fee;
	}

	public void setEsp_fee(int esp_fee) {
		this.esp_fee = esp_fee;
	}

	public int getWd_per_fee() {
		return wd_per_fee;
	}

	public void setWd_per_fee(int wd_per_fee) {
		this.wd_per_fee = wd_per_fee;
	}

	public int getWd_esp_fee() {
		return wd_esp_fee;
	}

	public void setWd_esp_fee(int wd_esp_fee) {
		this.wd_esp_fee = wd_esp_fee;
	}

	public int getWd_all_fee() {
		return wd_all_fee;
	}

	public void setWd_all_fee(int wd_all_fee) {
		this.wd_all_fee = wd_all_fee;
	}

	public int getAll_wd_bfee() {
		return all_wd_bfee;
	}

	public void setAll_wd_bfee(int all_wd_bfee) {
		this.all_wd_bfee = all_wd_bfee;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String getShortspell() {
		return shortspell;
	}

	public void setShortspell(String shortspell) {
		this.shortspell = shortspell;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public int getAll_fee() {
		return all_fee;
	}

	public void setAll_fee(int all_fee) {
		this.all_fee = all_fee;
	}

	public int getYf_fee() {
		return yf_fee;
	}

	public void setYf_fee(int yf_fee) {
		this.yf_fee = yf_fee;
	}

	public int getWf_fee() {
		return wf_fee;
	}

	public void setWf_fee(int wf_fee) {
		this.wf_fee = wf_fee;
	}

	public int getWd_loan_fee() {
		return wd_loan_fee;
	}

	public void setWd_loan_fee(int wd_loan_fee) {
		this.wd_loan_fee = wd_loan_fee;
	}

	public int getWd_clear_fee() {
		return wd_clear_fee;
	}

	public void setWd_clear_fee(int wd_clear_fee) {
		this.wd_clear_fee = wd_clear_fee;
	}

	public int getCsd_loan_fee() {
		return csd_loan_fee;
	}

	public void setCsd_loan_fee(int csd_loan_fee) {
		this.csd_loan_fee = csd_loan_fee;
	}

	public int getCsd_clear_fee() {
		return csd_clear_fee;
	}

	public void setCsd_clear_fee(int csd_clear_fee) {
		this.csd_clear_fee = csd_clear_fee;
	}

	// 去除日期格式后面的时间部分
	public String changeSDate(String date) {
		if (!"".equals(date) && date != null && !date.equals("NULL")
				&& !date.equals("null")) {
			return DateStringChange.DatetoSting(
					DateStringChange.StringtoDate(date, "yyyy-MM-dd"),
					"yyyy-MM-dd");
		} else {
			return date;
		}
	}
}
