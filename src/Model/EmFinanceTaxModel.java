package Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class EmFinanceTaxModel {
	private final DecimalFormat df = new DecimalFormat("#.00");
	private int efta_id;
	private int efta_efba_id;
	private int efta_coco_id;
	private String efta_cfmb_number;
	private int gid;
	private String emba_name;
	private int ownmonth;
	private int efta_etin_id;
	private int efta_tax_class;
	private String efta_tax_classStr;
	private BigDecimal efta_tax_base;
	private BigDecimal efta_tax;
	private BigDecimal efta_receivable;
	private BigDecimal efta_paidin;
	private BigDecimal efta_payout;
	private int efta_iffirstpaidin;
	private String efta_addtime;
	private int efta_finalcheck;
	private String efta_finalchecktime;
	private int efta_state;

	public EmFinanceTaxModel() {
		super();
	}

	public int getEfta_id() {
		return efta_id;
	}

	public void setEfta_id(int efta_id) {
		this.efta_id = efta_id;
	}

	public int getEfta_efba_id() {
		return efta_efba_id;
	}

	public void setEfta_efba_id(int efta_efba_id) {
		this.efta_efba_id = efta_efba_id;
	}

	public int getEfta_coco_id() {
		return efta_coco_id;
	}

	public void setEfta_coco_id(int efta_coco_id) {
		this.efta_coco_id = efta_coco_id;
	}

	public String getEfta_cfmb_number() {
		return efta_cfmb_number;
	}

	public void setEfta_cfmb_number(String efta_cfmb_number) {
		this.efta_cfmb_number = efta_cfmb_number;
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

	public int getEfta_etin_id() {
		return efta_etin_id;
	}

	public void setEfta_etin_id(int efta_etin_id) {
		this.efta_etin_id = efta_etin_id;
	}

	public int getEfta_tax_class() {
		return efta_tax_class;
	}

	public void setEfta_tax_class(int efta_tax_class) {
		this.efta_tax_class = efta_tax_class;
		switch (efta_tax_class) {
		case 1:
			this.efta_tax_classStr = "个人所得税";
			break;
		case 2:
			this.efta_tax_classStr = "年终奖金税";
			break;
		case 3:
			this.efta_tax_classStr = "股票税";
			break;
		case 4:
			this.efta_tax_classStr = "职补偿金税";
			break;
		case 5:
			this.efta_tax_classStr = "劳务报酬个人所得税";
			break;
		}
	}

	public BigDecimal getEfta_tax_base() {
		return efta_tax_base;
	}

	public void setEfta_tax_base(BigDecimal efta_tax_base) {
		this.efta_tax_base = new BigDecimal(df.format(efta_tax_base));
	}

	public BigDecimal getEfta_tax() {
		return efta_tax;
	}

	public void setEfta_tax(BigDecimal efta_tax) {
		this.efta_tax = new BigDecimal(df.format(efta_tax));
	}

	public BigDecimal getEfta_receivable() {
		return efta_receivable;
	}

	public void setEfta_receivable(BigDecimal efta_receivable) {
		this.efta_receivable = new BigDecimal(df.format(efta_receivable));
	}

	public BigDecimal getEfta_paidin() {
		return efta_paidin;
	}

	public void setEfta_paidin(BigDecimal efta_paidin) {
		this.efta_paidin = new BigDecimal(df.format(efta_paidin));
	}

	public BigDecimal getEfta_payout() {
		return efta_payout;
	}

	public void setEfta_payout(BigDecimal efta_payout) {
		this.efta_payout = new BigDecimal(df.format(efta_payout));
	}

	public int getEfta_iffirstpaidin() {
		return efta_iffirstpaidin;
	}

	public void setEfta_iffirstpaidin(int efta_iffirstpaidin) {
		this.efta_iffirstpaidin = efta_iffirstpaidin;
	}

	public String getEfta_addtime() {
		return efta_addtime;
	}

	public void setEfta_addtime(String efta_addtime) {
		this.efta_addtime = efta_addtime;
	}

	public int getEfta_finalcheck() {
		return efta_finalcheck;
	}

	public void setEfta_finalcheck(int efta_finalcheck) {
		this.efta_finalcheck = efta_finalcheck;
	}

	public String getEfta_finalchecktime() {
		return efta_finalchecktime;
	}

	public void setEfta_finalchecktime(String efta_finalchecktime) {
		this.efta_finalchecktime = efta_finalchecktime;
	}

	public int getEfta_state() {
		return efta_state;
	}

	public void setEfta_state(int efta_state) {
		this.efta_state = efta_state;
	}

	public String getEmba_name() {
		return emba_name;
	}

	public void setEmba_name(String emba_name) {
		this.emba_name = emba_name;
	}

	public String getEfta_tax_classStr() {
		return efta_tax_classStr;
	}

	public void setEfta_tax_classStr(String efta_tax_classStr) {
		this.efta_tax_classStr = efta_tax_classStr;
	}

}
