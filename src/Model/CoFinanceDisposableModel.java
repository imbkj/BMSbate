package Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CoFinanceDisposableModel {
	private final DecimalFormat df = new DecimalFormat("#.00");
	private int cfdi_id;
	private int cfdi_coco_id;
	private String cfdi_cfmb_number;
	private int cid;
	private int ownmonth;
	private int mendOwnmonth;
	private int cfdi_cabc_id;
	private String cfdi_cpac_name;
	private String cfdi_copr_name;
	private String cfdi_Reason;
	private BigDecimal cfdi_Fee;
	private BigDecimal cfdi_Receivable;
	private BigDecimal cfdi_PaidIn;

	public CoFinanceDisposableModel() {
		super();
	}

	public int getCfdi_id() {
		return cfdi_id;
	}

	public void setCfdi_id(int cfdi_id) {
		this.cfdi_id = cfdi_id;
	}

	public int getCfdi_coco_id() {
		return cfdi_coco_id;
	}

	public void setCfdi_coco_id(int cfdi_coco_id) {
		this.cfdi_coco_id = cfdi_coco_id;
	}

	public String getCfdi_cfmb_number() {
		return cfdi_cfmb_number;
	}

	public void setCfdi_cfmb_number(String cfdi_cfmb_number) {
		this.cfdi_cfmb_number = cfdi_cfmb_number;
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

	public int getMendOwnmonth() {
		return mendOwnmonth;
	}

	public void setMendOwnmonth(int mendOwnmonth) {
		this.mendOwnmonth = mendOwnmonth;
	}

	public int getCfdi_cabc_id() {
		return cfdi_cabc_id;
	}

	public void setCfdi_cabc_id(int cfdi_cabc_id) {
		this.cfdi_cabc_id = cfdi_cabc_id;
	}

	public String getCfdi_cpac_name() {
		return cfdi_cpac_name;
	}

	public void setCfdi_cpac_name(String cfdi_cpac_name) {
		this.cfdi_cpac_name = cfdi_cpac_name;
	}

	public String getCfdi_copr_name() {
		return cfdi_copr_name;
	}

	public void setCfdi_copr_name(String cfdi_copr_name) {
		this.cfdi_copr_name = cfdi_copr_name;
	}

	public String getCfdi_Reason() {
		return cfdi_Reason;
	}

	public void setCfdi_Reason(String cfdi_Reason) {
		this.cfdi_Reason = cfdi_Reason;
	}

	public BigDecimal getCfdi_Fee() {
		return cfdi_Fee;
	}

	public void setCfdi_Fee(BigDecimal cfdi_Fee) {
		this.cfdi_Fee = new BigDecimal(df.format(cfdi_Fee));
	}

	public BigDecimal getCfdi_Receivable() {
		return cfdi_Receivable;
	}

	public void setCfdi_Receivable(BigDecimal cfdi_Receivable) {
		this.cfdi_Receivable = new BigDecimal(df.format(cfdi_Receivable));
	}

	public BigDecimal getCfdi_PaidIn() {
		return cfdi_PaidIn;
	}

	public void setCfdi_PaidIn(BigDecimal cfdi_PaidIn) {
		this.cfdi_PaidIn = new BigDecimal(df.format(cfdi_PaidIn));
	}

}
