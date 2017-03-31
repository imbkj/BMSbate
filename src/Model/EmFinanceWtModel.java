package Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import Util.pinyin4jUtil;

public class EmFinanceWtModel implements Comparable<EmFinanceWtModel> {
	private final DecimalFormat df = new DecimalFormat("#.00");
	private String coba_company, coba_client;
	private Integer ownmonth;
	private Integer gid, cid;
	private String emfi_name;
	private String emfi_idcard;
	private BigDecimal emfi_total = BigDecimal.ZERO;
	private BigDecimal emfz_total = BigDecimal.ZERO;
	private BigDecimal diff_total = BigDecimal.ZERO;
	private BigDecimal emfi_sbtotal = BigDecimal.ZERO;
	private BigDecimal emfz_sbtotal = BigDecimal.ZERO;
	private BigDecimal diff_sbtotal = BigDecimal.ZERO;
	private BigDecimal emfi_yltotal = BigDecimal.ZERO;
	private BigDecimal emfz_yltotal = BigDecimal.ZERO;
	private BigDecimal diff_yltotal = BigDecimal.ZERO;
	private BigDecimal emfi_housetotal = BigDecimal.ZERO;
	private BigDecimal emfz_housetotal = BigDecimal.ZERO;
	private BigDecimal diff_housetotal = BigDecimal.ZERO;
	private BigDecimal emfi_fee = BigDecimal.ZERO;
	private BigDecimal emfz_fee = BigDecimal.ZERO;
	private BigDecimal emfz_feex = BigDecimal.ZERO;
	private BigDecimal emfz_fee2 = BigDecimal.ZERO;
	private BigDecimal diff_fee = BigDecimal.ZERO;
	private BigDecimal emfi_filefee = BigDecimal.ZERO;
	private BigDecimal emfz_filefee = BigDecimal.ZERO;
	private BigDecimal diff_filefee = BigDecimal.ZERO;
	private BigDecimal emfi_elsefee = BigDecimal.ZERO;
	private BigDecimal emfz_elsefee = BigDecimal.ZERO;
	private BigDecimal diff_elsefee = BigDecimal.ZERO;
	private BigDecimal emfi_n_total = BigDecimal.ZERO;
	private BigDecimal emfz_servertotal = BigDecimal.ZERO;

	private BigDecimal emfz_other = BigDecimal.ZERO;
	private BigDecimal fifztotal = BigDecimal.ZERO;// 差额合计
	private BigDecimal fifz_total = BigDecimal.ZERO;// 差额合计正数

	private BigDecimal emfz_syetotal = BigDecimal.ZERO;
	// / emfz_gstotal
	private BigDecimal emfz_gstotal = BigDecimal.ZERO;
	// / emfz_syutotal
	private BigDecimal emfz_syutotal = BigDecimal.ZERO;
	// / emfz_jltotal
	private BigDecimal emfz_jltotal = BigDecimal.ZERO;

	private BigDecimal efsb_ylao = BigDecimal.ZERO;
	private BigDecimal efsb_yliao = BigDecimal.ZERO;
	private BigDecimal efsb_syu = BigDecimal.ZERO;
	private BigDecimal efsb_sye = BigDecimal.ZERO;
	private BigDecimal efsb_gs = BigDecimal.ZERO;

	private Integer emba_state;
	private String spell;

	private pinyin4jUtil util = new pinyin4jUtil();

	public EmFinanceWtModel() {
		super();
	}

	// 系统合计
	public void sumEmfi_total() {
		this.emfi_total = emfi_total.add(emfi_sbtotal).add(emfi_housetotal)
				.add(emfi_fee).add(emfi_filefee).add(emfi_elsefee);
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public String getEmfi_name() {
		return emfi_name;
	}

	public void setEmfi_name(String emfi_name) {
		if (emfi_name.length() > 0) {
			String n = emfi_name.substring(0, 1);
			String s = util.getPinYinHeadChar(n);
			setSpell(s);
		}
		this.emfi_name = emfi_name;
	}

	public String getEmfi_idcard() {
		return emfi_idcard;
	}

	public void setEmfi_idcard(String emfi_idcard) {
		this.emfi_idcard = emfi_idcard;
	}

	public BigDecimal getEmfi_total() {
		return emfi_total;
	}

	public void setEmfi_total(BigDecimal emfi_total) {
		this.emfi_total = new BigDecimal(df.format(emfi_total));
	}

	public BigDecimal getEmfz_total() {
		return emfz_total;
	}

	public void setEmfz_total(BigDecimal emfz_total) {
		this.emfz_total = new BigDecimal(df.format(emfz_total));
	}

	public BigDecimal getDiff_total() {
		return diff_total;
	}

	public void setDiff_total(BigDecimal diff_total) {
		this.diff_total = new BigDecimal(df.format(diff_total));
	}

	public BigDecimal getEmfi_sbtotal() {
		return emfi_sbtotal;
	}

	public void setEmfi_sbtotal(BigDecimal emfi_sbtotal) {
		this.emfi_sbtotal = new BigDecimal(df.format(emfi_sbtotal));
	}

	public BigDecimal getEmfz_sbtotal() {
		return emfz_sbtotal;
	}

	public void setEmfz_sbtotal(BigDecimal emfz_sbtotal) {
		this.emfz_sbtotal = new BigDecimal(df.format(emfz_sbtotal));
	}

	public BigDecimal getDiff_sbtotal() {
		return diff_sbtotal;
	}

	public void setDiff_sbtotal(BigDecimal diff_sbtotal) {
		this.diff_sbtotal = new BigDecimal(df.format(diff_sbtotal));
	}

	public BigDecimal getEmfi_yltotal() {
		return emfi_yltotal;
	}

	public void setEmfi_yltotal(BigDecimal emfi_yltotal) {
		this.emfi_yltotal = new BigDecimal(df.format(emfi_yltotal));
	}

	public BigDecimal getEmfz_yltotal() {
		return emfz_yltotal;
	}

	public void setEmfz_yltotal(BigDecimal emfz_yltotal) {
		this.emfz_yltotal = new BigDecimal(df.format(emfz_yltotal));
	}

	public BigDecimal getDiff_yltotal() {
		return diff_yltotal;
	}

	public void setDiff_yltotal(BigDecimal diff_yltotal) {
		this.diff_yltotal = new BigDecimal(df.format(diff_yltotal));
	}

	public BigDecimal getEmfi_housetotal() {
		return emfi_housetotal;
	}

	public void setEmfi_housetotal(BigDecimal emfi_housetotal) {
		this.emfi_housetotal = new BigDecimal(df.format(emfi_housetotal));
	}

	public BigDecimal getEmfz_housetotal() {
		return emfz_housetotal;
	}

	public void setEmfz_housetotal(BigDecimal emfz_housetotal) {
		this.emfz_housetotal = new BigDecimal(df.format(emfz_housetotal));
	}

	public BigDecimal getDiff_housetotal() {
		return diff_housetotal;
	}

	public void setDiff_housetotal(BigDecimal diff_housetotal) {
		this.diff_housetotal = new BigDecimal(df.format(diff_housetotal));
	}

	public BigDecimal getEmfi_fee() {
		return emfi_fee;
	}

	public void setEmfi_fee(BigDecimal emfi_fee) {
		this.emfi_fee = new BigDecimal(df.format(emfi_fee));
	}

	public BigDecimal getEmfz_fee() {
		return emfz_fee;
	}

	public void setEmfz_fee(BigDecimal emfz_fee) {
		this.emfz_fee = new BigDecimal(df.format(emfz_fee));
	}

	public BigDecimal getDiff_fee() {
		return diff_fee;
	}

	public void setDiff_fee(BigDecimal diff_fee) {
		this.diff_fee = new BigDecimal(df.format(diff_fee));
	}

	public BigDecimal getEmfi_filefee() {
		return emfi_filefee;
	}

	public void setEmfi_filefee(BigDecimal emfi_filefee) {
		this.emfi_filefee = new BigDecimal(df.format(emfi_filefee));
	}

	public BigDecimal getEmfz_filefee() {
		return emfz_filefee;
	}

	public void setEmfz_filefee(BigDecimal emfz_filefee) {
		this.emfz_filefee = new BigDecimal(df.format(emfz_filefee));
	}

	public BigDecimal getDiff_filefee() {
		return diff_filefee;
	}

	public void setDiff_filefee(BigDecimal diff_filefee) {
		this.diff_filefee = new BigDecimal(df.format(diff_filefee));
	}

	public BigDecimal getEmfi_elsefee() {
		return emfi_elsefee;
	}

	public void setEmfi_elsefee(BigDecimal emfi_elsefee) {
		this.emfi_elsefee = new BigDecimal(df.format(emfi_elsefee));
	}

	public BigDecimal getEmfz_elsefee() {
		return emfz_elsefee;
	}

	public void setEmfz_elsefee(BigDecimal emfz_elsefee) {
		this.emfz_elsefee = new BigDecimal(df.format(emfz_elsefee));
	}

	public BigDecimal getDiff_elsefee() {
		return diff_elsefee;
	}

	public void setDiff_elsefee(BigDecimal diff_elsefee) {
		this.diff_elsefee = new BigDecimal(df.format(diff_elsefee));
	}

	public BigDecimal getEmfi_n_total() {
		return emfi_n_total;
	}

	public void setEmfi_n_total(BigDecimal emfi_n_total) {
		this.emfi_n_total = new BigDecimal(df.format(emfi_n_total));
	}

	public String getCoba_company() {
		return coba_company;
	}

	public void setCoba_company(String coba_company) {
		this.coba_company = coba_company;
	}

	public Integer getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(Integer ownmonth) {
		this.ownmonth = ownmonth;
	}

	public BigDecimal getFifztotal() {
		return fifztotal;
	}

	public void setFifztotal(BigDecimal fifztotal) {
		this.fifztotal = fifztotal;
	}

	public String getCoba_client() {
		return coba_client;
	}

	public void setCoba_client(String coba_client) {
		this.coba_client = coba_client;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public Integer getEmba_state() {
		return emba_state;
	}

	public void setEmba_state(Integer emba_state) {
		this.emba_state = emba_state;
	}

	@Override
	public int compareTo(EmFinanceWtModel arg0) {
		if (arg0.getSpell() != null) {
			return this.getSpell().compareTo(arg0.getSpell());
		} else {
			return this.getSpell().compareTo("");
		}

	}

	public String getSpell() {
		return spell;
	}

	public void setSpell(String spell) {
		this.spell = spell;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public BigDecimal getEmfz_other() {
		return emfz_other;
	}

	public void setEmfz_other(BigDecimal emfz_other) {
		this.emfz_other = emfz_other;
	}

	public BigDecimal getEmfz_syetotal() {
		return emfz_syetotal;
	}

	public void setEmfz_syetotal(BigDecimal emfz_syetotal) {
		this.emfz_syetotal = emfz_syetotal;
	}

	public BigDecimal getEmfz_gstotal() {
		return emfz_gstotal;
	}

	public void setEmfz_gstotal(BigDecimal emfz_gstotal) {
		this.emfz_gstotal = emfz_gstotal;
	}

	public BigDecimal getEmfz_syutotal() {
		return emfz_syutotal;
	}

	public void setEmfz_syutotal(BigDecimal emfz_syutotal) {
		this.emfz_syutotal = emfz_syutotal;
	}

	public BigDecimal getEmfz_jltotal() {
		return emfz_jltotal;
	}

	public void setEmfz_jltotal(BigDecimal emfz_jltotal) {
		this.emfz_jltotal = emfz_jltotal;
	}

	public BigDecimal getEfsb_ylao() {
		return efsb_ylao;
	}

	public void setEfsb_ylao(BigDecimal efsb_ylao) {
		this.efsb_ylao = efsb_ylao;
	}

	public BigDecimal getEfsb_yliao() {
		return efsb_yliao;
	}

	public void setEfsb_yliao(BigDecimal efsb_yliao) {
		this.efsb_yliao = efsb_yliao;
	}

	public BigDecimal getEfsb_syu() {
		return efsb_syu;
	}

	public void setEfsb_syu(BigDecimal efsb_syu) {
		this.efsb_syu = efsb_syu;
	}

	public BigDecimal getEfsb_sye() {
		return efsb_sye;
	}

	public void setEfsb_sye(BigDecimal efsb_sye) {
		this.efsb_sye = efsb_sye;
	}

	public BigDecimal getEfsb_gs() {
		return efsb_gs;
	}

	public void setEfsb_gs(BigDecimal efsb_gs) {
		this.efsb_gs = efsb_gs;
	}

	public BigDecimal getFifz_total() {
		return fifz_total;
	}

	public BigDecimal getEmfz_feex() {
		return emfz_feex;
	}

	public void setEmfz_feex(BigDecimal emfz_feex) {
		this.emfz_feex = new BigDecimal(df.format(emfz_feex));
	}

	public BigDecimal getEmfz_fee2() {
		return emfz_fee2;
	}

	public void setEmfz_fee2(BigDecimal emfz_fee2) {
		this.emfz_fee2 = new BigDecimal(df.format(emfz_fee2));
	}

	public void setFifz_total(BigDecimal fifz_total) {
		BigDecimal ze = BigDecimal.ZERO;
		if (fifz_total.compareTo(ze) <= 0) {
			fifz_total = ze.subtract(fifz_total);
		}
		this.fifz_total = fifz_total;
	}

	public BigDecimal getEmfz_servertotal() {
		return emfz_servertotal;
	}

	public void setEmfz_servertotal(BigDecimal emfz_servertotal) {
		this.emfz_servertotal = new BigDecimal(df.format(emfz_servertotal));
	}

}
