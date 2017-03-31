package Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class EmFinanceSheBaoModel {
	private final DecimalFormat df = new DecimalFormat("#.00");
	private int efsb_id;
	private int efsb_efba_id;
	private int efsb_coco_id;
	private String efsb_cfmb_number;
	private int gid;
	private String idcard;
	private int ownmonth;
	private int efsb_esiu_ownmonth;
	private BigDecimal efsb_esiu_radix;
	private String efsb_esiu_hj;
	private BigDecimal efsb_esiu_ylcp;
	private BigDecimal efsb_esiu_ylop;
	private BigDecimal efsb_esiu_jlcp;
	private BigDecimal efsb_esiu_jlop;
	private BigDecimal efsb_esiu_syucp;
	private BigDecimal efsb_esiu_syuop;
	private BigDecimal efsb_esiu_syecp;
	private BigDecimal efsb_esiu_syeop;
	private BigDecimal efsb_esiu_gscp;
	private BigDecimal efsb_esiu_gsop;
	private BigDecimal efsb_esiu_totalcp;
	private BigDecimal efsb_esiu_totalop;
	private BigDecimal efsb_totalsum;
	private BigDecimal efsb_receivable;
	private BigDecimal efsb_paidin;
	private BigDecimal efsb_payout;
	private int efsb_iffirstpaidin;
	private String efsb_addtime;
	private int efsb_finalcheck;
	private String efsb_finalchecktime;
	private int efsb_state;
	private String emba_name;

	public EmFinanceSheBaoModel() {
		super();
	}

	public int getEfsb_id() {
		return efsb_id;
	}

	public void setEfsb_id(int efsb_id) {
		this.efsb_id = efsb_id;
	}

	public int getEfsb_efba_id() {
		return efsb_efba_id;
	}

	public void setEfsb_efba_id(int efsb_efba_id) {
		this.efsb_efba_id = efsb_efba_id;
	}

	public int getEfsb_coco_id() {
		return efsb_coco_id;
	}

	public void setEfsb_coco_id(int efsb_coco_id) {
		this.efsb_coco_id = efsb_coco_id;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getEfsb_cfmb_number() {
		return efsb_cfmb_number;
	}

	public void setEfsb_cfmb_number(String efsb_cfmb_number) {
		this.efsb_cfmb_number = efsb_cfmb_number;
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

	public int getEfsb_esiu_ownmonth() {
		return efsb_esiu_ownmonth;
	}

	public void setEfsb_esiu_ownmonth(int efsb_esiu_ownmonth) {
		this.efsb_esiu_ownmonth = efsb_esiu_ownmonth;
	}

	public BigDecimal getEfsb_esiu_radix() {
		return efsb_esiu_radix;
	}

	public void setEfsb_esiu_radix(BigDecimal efsb_esiu_radix) {
		this.efsb_esiu_radix = new BigDecimal(df.format(efsb_esiu_radix));
	}

	public String getEfsb_esiu_hj() {
		return efsb_esiu_hj;
	}

	public void setEfsb_esiu_hj(String efsb_esiu_hj) {
		this.efsb_esiu_hj = efsb_esiu_hj;
	}

	public BigDecimal getEfsb_esiu_ylcp() {
		return efsb_esiu_ylcp;
	}

	public void setEfsb_esiu_ylcp(BigDecimal efsb_esiu_ylcp) {
		this.efsb_esiu_ylcp = new BigDecimal(df.format(efsb_esiu_ylcp));
	}

	public BigDecimal getEfsb_esiu_ylop() {
		return efsb_esiu_ylop;
	}

	public void setEfsb_esiu_ylop(BigDecimal efsb_esiu_ylop) {
		this.efsb_esiu_ylop = new BigDecimal(df.format(efsb_esiu_ylop));
	}

	public BigDecimal getEfsb_esiu_jlcp() {
		return efsb_esiu_jlcp;
	}

	public void setEfsb_esiu_jlcp(BigDecimal efsb_esiu_jlcp) {
		this.efsb_esiu_jlcp = new BigDecimal(df.format(efsb_esiu_jlcp));
	}

	public BigDecimal getEfsb_esiu_jlop() {
		return efsb_esiu_jlop;
	}

	public void setEfsb_esiu_jlop(BigDecimal efsb_esiu_jlop) {
		this.efsb_esiu_jlop = new BigDecimal(df.format(efsb_esiu_jlop));
	}

	public BigDecimal getEfsb_esiu_syucp() {
		return efsb_esiu_syucp;
	}

	public void setEfsb_esiu_syucp(BigDecimal efsb_esiu_syucp) {
		this.efsb_esiu_syucp = new BigDecimal(df.format(efsb_esiu_syucp));
	}

	public BigDecimal getEfsb_esiu_syuop() {
		return efsb_esiu_syuop;
	}

	public void setEfsb_esiu_syuop(BigDecimal efsb_esiu_syuop) {
		this.efsb_esiu_syuop = new BigDecimal(df.format(efsb_esiu_syuop));
	}

	public BigDecimal getEfsb_esiu_syecp() {
		return efsb_esiu_syecp;
	}

	public void setEfsb_esiu_syecp(BigDecimal efsb_esiu_syecp) {
		this.efsb_esiu_syecp = new BigDecimal(df.format(efsb_esiu_syecp));
	}

	public BigDecimal getEfsb_esiu_syeop() {
		return efsb_esiu_syeop;
	}

	public void setEfsb_esiu_syeop(BigDecimal efsb_esiu_syeop) {
		this.efsb_esiu_syeop = new BigDecimal(df.format(efsb_esiu_syeop));
	}

	public BigDecimal getEfsb_esiu_gscp() {
		return efsb_esiu_gscp;
	}

	public void setEfsb_esiu_gscp(BigDecimal efsb_esiu_gscp) {
		this.efsb_esiu_gscp = new BigDecimal(df.format(efsb_esiu_gscp));
	}

	public BigDecimal getEfsb_esiu_gsop() {
		return efsb_esiu_gsop;
	}

	public void setEfsb_esiu_gsop(BigDecimal efsb_esiu_gsop) {
		this.efsb_esiu_gsop = new BigDecimal(df.format(efsb_esiu_gsop));
	}

	public BigDecimal getEfsb_esiu_totalcp() {
		return efsb_esiu_totalcp;
	}

	public void setEfsb_esiu_totalcp(BigDecimal efsb_esiu_totalcp) {
		this.efsb_esiu_totalcp = new BigDecimal(df.format(efsb_esiu_totalcp));
	}

	public BigDecimal getEfsb_esiu_totalop() {
		return efsb_esiu_totalop;
	}

	public void setEfsb_esiu_totalop(BigDecimal efsb_esiu_totalop) {
		this.efsb_esiu_totalop = new BigDecimal(df.format(efsb_esiu_totalop));
	}

	public BigDecimal getEfsb_totalsum() {
		return efsb_totalsum;
	}

	public void setEfsb_totalsum(BigDecimal efsb_totalsum) {
		this.efsb_totalsum = new BigDecimal(df.format(efsb_totalsum));
	}

	public BigDecimal getEfsb_receivable() {
		return efsb_receivable;
	}

	public void setEfsb_receivable(BigDecimal efsb_receivable) {
		this.efsb_receivable = new BigDecimal(df.format(efsb_receivable));
	}

	public BigDecimal getEfsb_paidin() {
		return efsb_paidin;
	}

	public void setEfsb_paidin(BigDecimal efsb_paidin) {
		this.efsb_paidin = new BigDecimal(df.format(efsb_paidin));
	}

	public BigDecimal getEfsb_payout() {
		return efsb_payout;
	}

	public void setEfsb_payout(BigDecimal efsb_payout) {
		this.efsb_payout = new BigDecimal(df.format(efsb_payout));
	}

	public int getEfsb_iffirstpaidin() {
		return efsb_iffirstpaidin;
	}

	public void setEfsb_iffirstpaidin(int efsb_iffirstpaidin) {
		this.efsb_iffirstpaidin = efsb_iffirstpaidin;
	}

	public String getEfsb_addtime() {
		return efsb_addtime;
	}

	public void setEfsb_addtime(String efsb_addtime) {
		this.efsb_addtime = efsb_addtime;
	}

	public int getEfsb_finalcheck() {
		return efsb_finalcheck;
	}

	public void setEfsb_finalcheck(int efsb_finalcheck) {
		this.efsb_finalcheck = efsb_finalcheck;
	}

	public String getEfsb_finalchecktime() {
		return efsb_finalchecktime;
	}

	public void setEfsb_finalchecktime(String efsb_finalchecktime) {
		this.efsb_finalchecktime = efsb_finalchecktime;
	}

	public int getEfsb_state() {
		return efsb_state;
	}

	public void setEfsb_state(int efsb_state) {
		this.efsb_state = efsb_state;
	}

	public String getEmba_name() {
		return emba_name;
	}

	public void setEmba_name(String emba_name) {
		this.emba_name = emba_name;
	}

}
