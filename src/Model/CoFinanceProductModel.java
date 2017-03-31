package Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CoFinanceProductModel {
	private final DecimalFormat df = new DecimalFormat("#.00");
	private int cfpr_id;
	private String cfpr_cfmb_number;
	private int cid;
	private int ownmonth;
	private int cfpr_startownmonth;
	private int cfpr_coco_id;
	private int cfpr_cabc_id;
	private String cfpr_cpac_name;
	private String cfpr_copr_name;
	private int cfpr_amount;
	private BigDecimal cfpr_Fee;
	private BigDecimal cfpr_Receivable;
	private BigDecimal cfpr_PaidIn;
	private BigDecimal efpr_PayOut;

	public CoFinanceProductModel() {
		super();
	}

	public int getCfpr_id() {
		return cfpr_id;
	}

	public void setCfpr_id(int cfpr_id) {
		this.cfpr_id = cfpr_id;
	}

	public String getCfpr_cfmb_number() {
		return cfpr_cfmb_number;
	}

	public void setCfpr_cfmb_number(String cfpr_cfmb_number) {
		this.cfpr_cfmb_number = cfpr_cfmb_number;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public int getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(int ownmonth) {
		this.ownmonth = ownmonth;
	}

	public int getCfpr_startownmonth() {
		return cfpr_startownmonth;
	}

	public void setCfpr_startownmonth(int cfpr_startownmonth) {
		this.cfpr_startownmonth = cfpr_startownmonth;
	}

	public int getCfpr_coco_id() {
		return cfpr_coco_id;
	}

	public void setCfpr_coco_id(int cfpr_coco_id) {
		this.cfpr_coco_id = cfpr_coco_id;
	}

	public int getCfpr_cabc_id() {
		return cfpr_cabc_id;
	}

	public void setCfpr_cabc_id(int cfpr_cabc_id) {
		this.cfpr_cabc_id = cfpr_cabc_id;
	}

	public String getCfpr_cpac_name() {
		return cfpr_cpac_name;
	}

	public void setCfpr_cpac_name(String cfpr_cpac_name) {
		this.cfpr_cpac_name = cfpr_cpac_name;
	}

	public String getCfpr_copr_name() {
		return cfpr_copr_name;
	}

	public void setCfpr_copr_name(String cfpr_copr_name) {
		this.cfpr_copr_name = cfpr_copr_name;
	}

	public int getCfpr_amount() {
		return cfpr_amount;
	}

	public void setCfpr_amount(int cfpr_amount) {
		this.cfpr_amount = cfpr_amount;
	}

	public BigDecimal getCfpr_Fee() {
		return cfpr_Fee;
	}

	public void setCfpr_Fee(BigDecimal cfpr_Fee) {
		this.cfpr_Fee = new BigDecimal(df.format(cfpr_Fee));
	}

	public BigDecimal getCfpr_Receivable() {
		return cfpr_Receivable;
	}

	public void setCfpr_Receivable(BigDecimal cfpr_Receivable) {
		this.cfpr_Receivable = new BigDecimal(df.format(cfpr_Receivable));
	}

	public BigDecimal getCfpr_PaidIn() {
		return cfpr_PaidIn;
	}

	public void setCfpr_PaidIn(BigDecimal cfpr_PaidIn) {
		this.cfpr_PaidIn = cfpr_PaidIn;
	}

	public BigDecimal getEfpr_PayOut() {
		return efpr_PayOut;
	}

	public void setEfpr_PayOut(BigDecimal efpr_PayOut) {
		this.efpr_PayOut = new BigDecimal(df.format(efpr_PayOut));
	}

}
