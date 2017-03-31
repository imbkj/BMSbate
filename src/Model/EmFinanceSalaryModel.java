package Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

public class EmFinanceSalaryModel {
	private final DecimalFormat df = new DecimalFormat("#.00");
	private int efsa_id;
	private int efsa_efba_id;
	private int efsa_coco_id;
	private String efsa_cfmb_number;
	private int gid;
	private int ownmonth;
	private int efsa_esda_id;
	private BigDecimal efsa_esda_pay;
	private BigDecimal efsa_esda_tax;
	private BigDecimal efsa_esda_db;
	private BigDecimal efsa_esda_db_tax;
	private BigDecimal efsa_receivable;
	private BigDecimal efsa_paidin;
	private BigDecimal efsa_payout;
	private int efsa_iffirstpaidin;
	private String efsa_addtime;
	private int efsa_finalcheck;
	private String efsa_finalchecktime;
	private int efsa_state;
	private String emba_name;
	private EmSalaryDataModel emsdModel;

	public EmFinanceSalaryModel() {
		super();
	}

	public int getEfsa_id() {
		return efsa_id;
	}

	public void setEfsa_id(int efsa_id) {
		this.efsa_id = efsa_id;
	}

	public int getEfsa_efba_id() {
		return efsa_efba_id;
	}

	public void setEfsa_efba_id(int efsa_efba_id) {
		this.efsa_efba_id = efsa_efba_id;
	}

	public int getEfsa_coco_id() {
		return efsa_coco_id;
	}

	public void setEfsa_coco_id(int efsa_coco_id) {
		this.efsa_coco_id = efsa_coco_id;
	}

	public String getEfsa_cfmb_number() {
		return efsa_cfmb_number;
	}

	public void setEfsa_cfmb_number(String efsa_cfmb_number) {
		this.efsa_cfmb_number = efsa_cfmb_number;
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

	public int getEfsa_esda_id() {
		return efsa_esda_id;
	}

	public void setEfsa_esda_id(int efsa_esda_id) {
		this.efsa_esda_id = efsa_esda_id;
	}

	public BigDecimal getEfsa_esda_pay() {
		return efsa_esda_pay;
	}

	public void setEfsa_esda_pay(BigDecimal efsa_esda_pay) {
		this.efsa_esda_pay = new BigDecimal(df.format(efsa_esda_pay));
	}

	public BigDecimal getEfsa_esda_tax() {
		return efsa_esda_tax;
	}

	public void setEfsa_esda_tax(BigDecimal efsa_esda_tax) {
		this.efsa_esda_tax = new BigDecimal(df.format(efsa_esda_tax));
	}

	public BigDecimal getEfsa_esda_db() {
		return efsa_esda_db;
	}

	public void setEfsa_esda_db(BigDecimal efsa_esda_db) {
		this.efsa_esda_db = new BigDecimal(df.format(efsa_esda_db));
	}

	public BigDecimal getEfsa_esda_db_tax() {
		return efsa_esda_db_tax;
	}

	public void setEfsa_esda_db_tax(BigDecimal efsa_esda_db_tax) {
		this.efsa_esda_db_tax = new BigDecimal(df.format(efsa_esda_db_tax));
	}

	public BigDecimal getEfsa_receivable() {
		return efsa_receivable;
	}

	public void setEfsa_receivable(BigDecimal efsa_receivable) {
		this.efsa_receivable = new BigDecimal(df.format(efsa_receivable));
	}

	public BigDecimal getEfsa_paidin() {
		return efsa_paidin;
	}

	public void setEfsa_paidin(BigDecimal efsa_paidin) {
		this.efsa_paidin = new BigDecimal(df.format(efsa_paidin));
	}

	public BigDecimal getEfsa_payout() {
		return efsa_payout;
	}

	public void setEfsa_payout(BigDecimal efsa_payout) {
		this.efsa_payout = new BigDecimal(df.format(efsa_payout));
	}

	public int getEfsa_iffirstpaidin() {
		return efsa_iffirstpaidin;
	}

	public void setEfsa_iffirstpaidin(int efsa_iffirstpaidin) {
		this.efsa_iffirstpaidin = efsa_iffirstpaidin;
	}

	public String getEfsa_addtime() {
		return efsa_addtime;
	}

	public void setEfsa_addtime(String efsa_addtime) {
		this.efsa_addtime = efsa_addtime;
	}

	public int getEfsa_finalcheck() {
		return efsa_finalcheck;
	}

	public void setEfsa_finalcheck(int efsa_finalcheck) {
		this.efsa_finalcheck = efsa_finalcheck;
	}

	public String getEfsa_finalchecktime() {
		return efsa_finalchecktime;
	}

	public void setEfsa_finalchecktime(String efsa_finalchecktime) {
		this.efsa_finalchecktime = efsa_finalchecktime;
	}

	public int getEfsa_state() {
		return efsa_state;
	}

	public void setEfsa_state(int efsa_state) {
		this.efsa_state = efsa_state;
	}

	public String getEmba_name() {
		return emba_name;
	}

	public void setEmba_name(String emba_name) {
		this.emba_name = emba_name;
	}

	public EmSalaryDataModel getEmsdModel() {
		return emsdModel;
	}

	public void setEmsdModel(EmSalaryDataModel emsdModel) {
		this.emsdModel = emsdModel;
	}

}
