package Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class EmFinanceHouseGjjModel {
	private final DecimalFormat df = new DecimalFormat("#.00");
	private int efhg_id;
	private int efhg_efba_id;
	private int efhg_coco_id;
	private String efhg_cfmb_number;
	private int gid;
	private int ownmonth;
	private int efhg_emhu_ownmonth;
	private String idcard;
	private String efhg_emhu_hj;
	private BigDecimal efhg_emhu_radix;
	private BigDecimal efhg_emhu_bonus;
	private BigDecimal efhg_emhu_cpp;
	private BigDecimal efhg_emhu_opp;
	private BigDecimal efhg_emhu_cp;
	private BigDecimal efhg_emhu_op;
	private BigDecimal efhg_emhu_total;
	private BigDecimal efhg_receivable;
	private BigDecimal efhg_paidin;
	private BigDecimal efhg_payout;
	private int efhg_iffirstpaidin;
	private String efhg_addtime;
	private int efhg_finalcheck;
	private String efhg_finalchecktime;
	private int efhg_state;
	private String emba_name;

	public EmFinanceHouseGjjModel() {
		super();
	}

	public int getEfhg_id() {
		return efhg_id;
	}

	public void setEfhg_id(int efhg_id) {
		this.efhg_id = efhg_id;
	}

	public int getEfhg_efba_id() {
		return efhg_efba_id;
	}

	public void setEfhg_efba_id(int efhg_efba_id) {
		this.efhg_efba_id = efhg_efba_id;
	}

	public int getEfhg_coco_id() {
		return efhg_coco_id;
	}

	public void setEfhg_coco_id(int efhg_coco_id) {
		this.efhg_coco_id = efhg_coco_id;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getEfhg_cfmb_number() {
		return efhg_cfmb_number;
	}

	public void setEfhg_cfmb_number(String efhg_cfmb_number) {
		this.efhg_cfmb_number = efhg_cfmb_number;
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

	public int getEfhg_emhu_ownmonth() {
		return efhg_emhu_ownmonth;
	}

	public void setEfhg_emhu_ownmonth(int efhg_emhu_ownmonth) {
		this.efhg_emhu_ownmonth = efhg_emhu_ownmonth;
	}

	public String getEfhg_emhu_hj() {
		return efhg_emhu_hj;
	}

	public void setEfhg_emhu_hj(String efhg_emhu_hj) {
		this.efhg_emhu_hj = efhg_emhu_hj;
	}

	public BigDecimal getEfhg_emhu_radix() {
		return efhg_emhu_radix;
	}

	public void setEfhg_emhu_radix(BigDecimal efhg_emhu_radix) {
		this.efhg_emhu_radix = new BigDecimal(df.format(efhg_emhu_radix));
	}

	public BigDecimal getEfhg_emhu_bonus() {
		return efhg_emhu_bonus;
	}

	public void setEfhg_emhu_bonus(BigDecimal efhg_emhu_bonus) {
		this.efhg_emhu_bonus = new BigDecimal(df.format(efhg_emhu_bonus));
	}

	public BigDecimal getEfhg_emhu_cpp() {
		return efhg_emhu_cpp;
	}

	public void setEfhg_emhu_cpp(BigDecimal efhg_emhu_cpp) {
		this.efhg_emhu_cpp = new BigDecimal(df.format(efhg_emhu_cpp));
	}

	public BigDecimal getEfhg_emhu_opp() {
		return efhg_emhu_opp;
	}

	public void setEfhg_emhu_opp(BigDecimal efhg_emhu_opp) {
		this.efhg_emhu_opp = new BigDecimal(df.format(efhg_emhu_opp));
	}

	public BigDecimal getEfhg_emhu_cp() {
		return efhg_emhu_cp;
	}

	public void setEfhg_emhu_cp(BigDecimal efhg_emhu_cp) {
		this.efhg_emhu_cp = new BigDecimal(df.format(efhg_emhu_cp));
	}

	public BigDecimal getEfhg_emhu_op() {
		return efhg_emhu_op;
	}

	public void setEfhg_emhu_op(BigDecimal efhg_emhu_op) {
		this.efhg_emhu_op = new BigDecimal(df.format(efhg_emhu_op));
	}

	public BigDecimal getEfhg_emhu_total() {
		return efhg_emhu_total;
	}

	public void setEfhg_emhu_total(BigDecimal efhg_emhu_total) {
		this.efhg_emhu_total = new BigDecimal(df.format(efhg_emhu_total));
	}

	public BigDecimal getEfhg_receivable() {
		return efhg_receivable;
	}

	public void setEfhg_receivable(BigDecimal efhg_receivable) {
		this.efhg_receivable = new BigDecimal(df.format(efhg_receivable));
	}

	public BigDecimal getEfhg_paidin() {
		return efhg_paidin;
	}

	public void setEfhg_paidin(BigDecimal efhg_paidin) {
		this.efhg_paidin = new BigDecimal(df.format(efhg_paidin));
	}

	public BigDecimal getEfhg_payout() {
		return efhg_payout;
	}

	public void setEfhg_payout(BigDecimal efhg_payout) {
		this.efhg_payout = new BigDecimal(df.format(efhg_payout));
	}

	public int getEfhg_iffirstpaidin() {
		return efhg_iffirstpaidin;
	}

	public void setEfhg_iffirstpaidin(int efhg_iffirstpaidin) {
		this.efhg_iffirstpaidin = efhg_iffirstpaidin;
	}

	public String getEfhg_addtime() {
		return efhg_addtime;
	}

	public void setEfhg_addtime(String efhg_addtime) {
		this.efhg_addtime = efhg_addtime;
	}

	public int getEfhg_finalcheck() {
		return efhg_finalcheck;
	}

	public void setEfhg_finalcheck(int efhg_finalcheck) {
		this.efhg_finalcheck = efhg_finalcheck;
	}

	public String getEfhg_finalchecktime() {
		return efhg_finalchecktime;
	}

	public void setEfhg_finalchecktime(String efhg_finalchecktime) {
		this.efhg_finalchecktime = efhg_finalchecktime;
	}

	public int getEfhg_state() {
		return efhg_state;
	}

	public void setEfhg_state(int efhg_state) {
		this.efhg_state = efhg_state;
	}

	public String getEmba_name() {
		return emba_name;
	}

	public void setEmba_name(String emba_name) {
		this.emba_name = emba_name;
	}

}
