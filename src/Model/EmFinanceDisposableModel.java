package Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class EmFinanceDisposableModel {
	private final DecimalFormat df = new DecimalFormat("#.00");
	private int efdi_id;
	private int efdi_efba_id;
	private String efdi_cfmb_number;
	private int gid;
	private int ownmonth;
	private int mendOwnmonth;
	private int efdi_coco_id;
	private int efdi_cabc_id;
	private String efdi_cpac_name;
	private String efdi_copr_name;
	private String efdi_Reason;
	private BigDecimal efdi_Receivable;
	private BigDecimal efdi_PaidIn;
	private int efdi_IfFirstPaidIn;
	private String efdi_addname;
	private String efdi_addtime;
	private int efdi_state;
	private String emba_name;

	public EmFinanceDisposableModel() {
		super();
	}

	public int getEfdi_id() {
		return efdi_id;
	}

	public void setEfdi_id(int efdi_id) {
		this.efdi_id = efdi_id;
	}

	public int getEfdi_efba_id() {
		return efdi_efba_id;
	}

	public void setEfdi_efba_id(int efdi_efba_id) {
		this.efdi_efba_id = efdi_efba_id;
	}

	public String getEfdi_cfmb_number() {
		return efdi_cfmb_number;
	}

	public void setEfdi_cfmb_number(String efdi_cfmb_number) {
		this.efdi_cfmb_number = efdi_cfmb_number;
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

	public int getMendOwnmonth() {
		return mendOwnmonth;
	}

	public void setMendOwnmonth(int mendOwnmonth) {
		this.mendOwnmonth = mendOwnmonth;
	}

	public int getEfdi_coco_id() {
		return efdi_coco_id;
	}

	public void setEfdi_coco_id(int efdi_coco_id) {
		this.efdi_coco_id = efdi_coco_id;
	}

	public int getEfdi_cabc_id() {
		return efdi_cabc_id;
	}

	public void setEfdi_cabc_id(int efdi_cabc_id) {
		this.efdi_cabc_id = efdi_cabc_id;
	}

	public String getEfdi_cpac_name() {
		return efdi_cpac_name;
	}

	public void setEfdi_cpac_name(String efdi_cpac_name) {
		this.efdi_cpac_name = efdi_cpac_name;
	}

	public String getEfdi_copr_name() {
		return efdi_copr_name;
	}

	public void setEfdi_copr_name(String efdi_copr_name) {
		this.efdi_copr_name = efdi_copr_name;
	}

	public String getEfdi_Reason() {
		return efdi_Reason;
	}

	public void setEfdi_Reason(String efdi_Reason) {
		this.efdi_Reason = efdi_Reason;
	}

	public BigDecimal getEfdi_Receivable() {
		return efdi_Receivable;
	}

	public void setEfdi_Receivable(BigDecimal efdi_Receivable) {
		this.efdi_Receivable = new BigDecimal(df.format(efdi_Receivable));
	}

	public BigDecimal getEfdi_PaidIn() {
		return efdi_PaidIn;
	}

	public void setEfdi_PaidIn(BigDecimal efdi_PaidIn) {
		this.efdi_PaidIn = new BigDecimal(df.format(efdi_PaidIn));
	}

	public int getEfdi_IfFirstPaidIn() {
		return efdi_IfFirstPaidIn;
	}

	public void setEfdi_IfFirstPaidIn(int efdi_IfFirstPaidIn) {
		this.efdi_IfFirstPaidIn = efdi_IfFirstPaidIn;
	}

	public String getEfdi_addname() {
		return efdi_addname;
	}

	public void setEfdi_addname(String efdi_addname) {
		this.efdi_addname = efdi_addname;
	}

	public String getEfdi_addtime() {
		return efdi_addtime;
	}

	public void setEfdi_addtime(String efdi_addtime) {
		this.efdi_addtime = efdi_addtime;
	}

	public int getEfdi_state() {
		return efdi_state;
	}

	public void setEfdi_state(int efdi_state) {
		this.efdi_state = efdi_state;
	}

	public String getEmba_name() {
		return emba_name;
	}

	public void setEmba_name(String emba_name) {
		this.emba_name = emba_name;
	}

}
