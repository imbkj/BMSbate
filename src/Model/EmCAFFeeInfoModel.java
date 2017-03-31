package Model;

import java.math.BigDecimal;
import java.util.Date;

import Util.DateStringChange;

public class EmCAFFeeInfoModel {
	private BigDecimal zero = BigDecimal.ZERO;
	private int ecfi_id;
	private int gid;
	private int cid;
	private int ownmonth;
	private int ecfi_caf_id;
	private String ecfi_class;
	private String ecfi_payment_kind;
	private String ecfi_payment_state;
	private String ecfi_rec_date;
	private String ecfi_cl_number;
	private int ecfi_csd_xls;
	private int ecfi_fee;
	private String ecfi_wd_applicant;
	private String ecfi_wd_loan_date;
	private String ecfi_ri_date;
	private String ecfi_csd_applicant;
	private String ecfi_csd_loan_date;
	private String ecfi_csd_clearing_date;
	private String ecfi_clearing_people;
	private int ecfi_if_sic;
	private String ecfi_state;
	private int ecfi_if_return;
	private int ecfi_if_refundment;
	private String ecfi_refundment_date;
	private String ecfi_refundment_people;
	private String ecfi_remark;
	private String ecfi_addname;
	private String ecfi_addtime;
	private String ecfi_cddate;
	private String ecfi_sdate;
	private String ecfi_rs_invoice;
	private int ecfi_loanstate;
	private String ecfi_paystate;
	private String ecfi_rshj_id;
	private int ecfi_tapr_id;
	private String ecfi_modtime;
	private String ecfi_modname;

	private String name;
	private String idcard;
	private String spell;
	private String shortname;
	private String shortspell;
	private String client;
	private String loanstate;
	private String cddate;
	private String sdate;
	private String rspay;

	private BigDecimal emap_file = zero;
	private BigDecimal emap_hj = zero;
	private BigDecimal emap_op = zero;
	private String emap_fpay;
	private String emap_hpay;
	private String emap_finvoice;
	private String emap_hinvoice;

	private Integer cou;

	public EmCAFFeeInfoModel() {
		super();
	}

	public EmCAFFeeInfoModel(BigDecimal zero, int ecfi_id, int gid, int cid,
			int ownmonth, int ecfi_caf_id, String ecfi_class,
			String ecfi_payment_kind, String ecfi_payment_state,
			String ecfi_rec_date, String ecfi_cl_number, int ecfi_csd_xls,
			int ecfi_fee, String ecfi_wd_applicant, String ecfi_wd_loan_date,
			String ecfi_ri_date, String ecfi_csd_applicant,
			String ecfi_csd_loan_date, String ecfi_csd_clearing_date,
			String ecfi_clearing_people, int ecfi_if_sic, String ecfi_state,
			int ecfi_if_return, int ecfi_if_refundment,
			String ecfi_refundment_date, String ecfi_refundment_people,
			String ecfi_remark, String ecfi_addname, String ecfi_addtime,
			String ecfi_cddate, String ecfi_sdate, String ecfi_rs_invoice,
			int ecfi_loanstate, String ecfi_paystate, String ecfi_rshj_id,
			int ecfi_tapr_id, String name, String idcard, String spell,
			String shortname, String shortspell, String client,
			String loanstate, String cddate, String sdate, String rspay,
			BigDecimal emap_file, BigDecimal emap_hj, BigDecimal emap_op,
			String emap_fpay, String emap_hpay, String emap_finvoice,
			String emap_hinvoice) {
		super();
		this.zero = zero;
		this.ecfi_id = ecfi_id;
		this.gid = gid;
		this.cid = cid;
		this.ownmonth = ownmonth;
		this.ecfi_caf_id = ecfi_caf_id;
		this.ecfi_class = ecfi_class;
		this.ecfi_payment_kind = ecfi_payment_kind;
		this.ecfi_payment_state = ecfi_payment_state;
		this.ecfi_rec_date = ecfi_rec_date;
		this.ecfi_cl_number = ecfi_cl_number;
		this.ecfi_csd_xls = ecfi_csd_xls;
		this.ecfi_fee = ecfi_fee;
		this.ecfi_wd_applicant = ecfi_wd_applicant;
		this.ecfi_wd_loan_date = ecfi_wd_loan_date;
		this.ecfi_ri_date = ecfi_ri_date;
		this.ecfi_csd_applicant = ecfi_csd_applicant;
		this.ecfi_csd_loan_date = ecfi_csd_loan_date;
		this.ecfi_csd_clearing_date = ecfi_csd_clearing_date;
		this.ecfi_clearing_people = ecfi_clearing_people;
		this.ecfi_if_sic = ecfi_if_sic;
		this.ecfi_state = ecfi_state;
		this.ecfi_if_return = ecfi_if_return;
		this.ecfi_if_refundment = ecfi_if_refundment;
		this.ecfi_refundment_date = ecfi_refundment_date;
		this.ecfi_refundment_people = ecfi_refundment_people;
		this.ecfi_remark = ecfi_remark;
		this.ecfi_addname = ecfi_addname;
		this.ecfi_addtime = ecfi_addtime;
		this.ecfi_cddate = ecfi_cddate;
		this.ecfi_sdate = ecfi_sdate;
		this.ecfi_rs_invoice = ecfi_rs_invoice;
		this.ecfi_loanstate = ecfi_loanstate;
		this.ecfi_paystate = ecfi_paystate;
		this.ecfi_rshj_id = ecfi_rshj_id;
		this.ecfi_tapr_id = ecfi_tapr_id;
		this.name = name;
		this.idcard = idcard;
		this.spell = spell;
		this.shortname = shortname;
		this.shortspell = shortspell;
		this.client = client;
		this.loanstate = loanstate;
		this.cddate = cddate;
		this.sdate = sdate;
		this.rspay = rspay;
		this.emap_file = emap_file;
		this.emap_hj = emap_hj;
		this.emap_op = emap_op;
		this.emap_fpay = emap_fpay;
		this.emap_hpay = emap_hpay;
		this.emap_finvoice = emap_finvoice;
		this.emap_hinvoice = emap_hinvoice;
	}

	public BigDecimal getZero() {
		return zero;
	}

	public void setZero(BigDecimal zero) {
		this.zero = zero;
	}

	public BigDecimal getEmap_file() {
		return emap_file;
	}

	public void setEmap_file(BigDecimal emap_file) {
		this.emap_file = emap_file.setScale(0,BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getEmap_hj() {
		return emap_hj;
	}

	public void setEmap_hj(BigDecimal emap_hj) {
		this.emap_hj = emap_hj.setScale(0,BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getEmap_op() {
		return emap_op;
	}

	public void setEmap_op(BigDecimal emap_op) {
		this.emap_op = emap_op.setScale(0,BigDecimal.ROUND_HALF_UP);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getSpell() {
		return spell;
	}

	public void setSpell(String spell) {
		this.spell = spell;
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

	public String getLoanstate() {
		return loanstate;
	}

	public void setLoanstate(String loanstate) {
		this.loanstate = loanstate;
	}

	public int getEcfi_id() {
		return ecfi_id;
	}

	public void setEcfi_id(int ecfi_id) {
		this.ecfi_id = ecfi_id;
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

	public int getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(int ownmonth) {
		this.ownmonth = ownmonth;
	}

	public int getEcfi_caf_id() {
		return ecfi_caf_id;
	}

	public void setEcfi_caf_id(int ecfi_caf_id) {
		this.ecfi_caf_id = ecfi_caf_id;
	}

	public String getEcfi_class() {
		return ecfi_class;
	}

	public void setEcfi_class(String ecfi_class) {
		this.ecfi_class = ecfi_class;
	}

	public String getEcfi_payment_kind() {
		return ecfi_payment_kind;
	}

	public void setEcfi_payment_kind(String ecfi_payment_kind) {
		this.ecfi_payment_kind = ecfi_payment_kind;
	}

	public String getEcfi_payment_state() {
		return ecfi_payment_state;
	}

	public void setEcfi_payment_state(String ecfi_payment_state) {
		this.ecfi_payment_state = ecfi_payment_state;
	}

	public String getEcfi_rec_date() {
		return changeSDate(ecfi_rec_date);
	}

	public void setEcfi_rec_date(String ecfi_rec_date) {
		this.ecfi_rec_date = ecfi_rec_date;
	}

	public String getEcfi_cl_number() {
		return ecfi_cl_number;
	}

	public void setEcfi_cl_number(String ecfi_cl_number) {
		this.ecfi_cl_number = ecfi_cl_number;
	}

	public int getEcfi_csd_xls() {
		return ecfi_csd_xls;
	}

	public void setEcfi_csd_xls(int ecfi_csd_xls) {
		this.ecfi_csd_xls = ecfi_csd_xls;
	}

	public int getEcfi_fee() {
		return ecfi_fee;
	}

	public void setEcfi_fee(int ecfi_fee) {
		this.ecfi_fee = ecfi_fee;
	}

	public String getEcfi_wd_applicant() {
		return ecfi_wd_applicant;
	}

	public void setEcfi_wd_applicant(String ecfi_wd_applicant) {
		this.ecfi_wd_applicant = ecfi_wd_applicant;
	}

	public String getEcfi_wd_loan_date() {
		return changeSDate(ecfi_wd_loan_date);
	}

	public void setEcfi_wd_loan_date(String ecfi_wd_loan_date) {
		this.ecfi_wd_loan_date = ecfi_wd_loan_date;
	}

	public String getEcfi_ri_date() {
		return changeSDate(ecfi_ri_date);
	}

	public void setEcfi_ri_date(String ecfi_ri_date) {
		this.ecfi_ri_date = ecfi_ri_date;
	}

	public String getEcfi_csd_applicant() {
		return ecfi_csd_applicant;
	}

	public void setEcfi_csd_applicant(String ecfi_csd_applicant) {
		this.ecfi_csd_applicant = ecfi_csd_applicant;
	}

	public String getEcfi_csd_loan_date() {
		return changeSDate(ecfi_csd_loan_date);
	}

	public void setEcfi_csd_loan_date(String ecfi_csd_loan_date) {
		this.ecfi_csd_loan_date = ecfi_csd_loan_date;
	}

	public String getEcfi_csd_clearing_date() {
		return changeSDate(ecfi_csd_clearing_date);
	}

	public void setEcfi_csd_clearing_date(String ecfi_csd_clearing_date) {
		this.ecfi_csd_clearing_date = ecfi_csd_clearing_date;
	}

	public String getEcfi_clearing_people() {
		return ecfi_clearing_people;
	}

	public void setEcfi_clearing_people(String ecfi_clearing_people) {
		this.ecfi_clearing_people = ecfi_clearing_people;
	}

	public int getEcfi_if_sic() {
		return ecfi_if_sic;
	}

	public void setEcfi_if_sic(int ecfi_if_sic) {
		this.ecfi_if_sic = ecfi_if_sic;
	}

	public String getEcfi_state() {
		return ecfi_state;
	}

	public void setEcfi_state(String ecfi_state) {
		this.ecfi_state = ecfi_state;
	}

	public int getEcfi_if_return() {
		return ecfi_if_return;
	}

	public void setEcfi_if_return(int ecfi_if_return) {
		this.ecfi_if_return = ecfi_if_return;
	}

	public int getEcfi_if_refundment() {
		return ecfi_if_refundment;
	}

	public void setEcfi_if_refundment(int ecfi_if_refundment) {
		this.ecfi_if_refundment = ecfi_if_refundment;
	}

	public String getEcfi_refundment_date() {
		return changeSDate(ecfi_refundment_date);
	}

	public void setEcfi_refundment_date(String ecfi_refundment_date) {
		this.ecfi_refundment_date = ecfi_refundment_date;
	}

	public String getEcfi_refundment_people() {
		return ecfi_refundment_people;
	}

	public void setEcfi_refundment_people(String ecfi_refundment_people) {
		this.ecfi_refundment_people = ecfi_refundment_people;
	}

	public String getEcfi_remark() {
		return ecfi_remark;
	}

	public void setEcfi_remark(String ecfi_remark) {
		this.ecfi_remark = ecfi_remark;
	}

	public String getEcfi_addname() {
		return ecfi_addname;
	}

	public void setEcfi_addname(String ecfi_addname) {
		this.ecfi_addname = ecfi_addname;
	}

	public String getEcfi_addtime() {
		return ecfi_addtime;
	}

	public void setEcfi_addtime(String ecfi_addtime) {
		this.ecfi_addtime = ecfi_addtime;
	}

	public String getEcfi_cddate() {
		return changeSDate(ecfi_cddate);
	}

	public void setEcfi_cddate(String ecfi_cddate) {
		this.ecfi_cddate = ecfi_cddate;
	}

	public String getEcfi_sdate() {
		return changeSDate(ecfi_sdate);
	}

	public void setEcfi_sdate(String ecfi_sdate) {
		this.ecfi_sdate = ecfi_sdate;
	}

	public String getEcfi_rs_invoice() {
		return ecfi_rs_invoice;
	}

	public void setEcfi_rs_invoice(String ecfi_rs_invoice) {
		this.ecfi_rs_invoice = ecfi_rs_invoice;
	}

	public int getEcfi_loanstate() {
		return ecfi_loanstate;
	}

	public void setEcfi_loanstate(int ecfi_loanstate) {
		this.ecfi_loanstate = ecfi_loanstate;
	}

	public String getEcfi_paystate() {
		return ecfi_paystate;
	}

	public void setEcfi_paystate(String ecfi_paystate) {
		this.ecfi_paystate = ecfi_paystate;
	}

	public String getEcfi_rshj_id() {
		return ecfi_rshj_id;
	}

	public void setEcfi_rshj_id(String ecfi_rshj_id) {
		this.ecfi_rshj_id = ecfi_rshj_id;
	}

	public int getEcfi_tapr_id() {
		return ecfi_tapr_id;
	}

	public void setEcfi_tapr_id(int ecfi_tapr_id) {
		this.ecfi_tapr_id = ecfi_tapr_id;
	}

	public String getCddate() {
		return changeSDate(cddate);
	}

	public void setCddate(String cddate) {
		this.cddate = cddate;
	}

	public String getSdate() {
		return changeSDate(sdate);
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public String getRspay() {
		return rspay;
	}

	public void setRspay(String rspay) {
		this.rspay = rspay;
	}

	public Integer getCou() {
		return cou;
	}

	public void setCou(Integer cou) {
		this.cou = cou;
	}

	public String getEmap_finvoice() {
		return emap_finvoice;
	}

	public void setEmap_finvoice(String emap_finvoice) {
		this.emap_finvoice = emap_finvoice;
	}

	public String getEmap_hinvoice() {
		return emap_hinvoice;
	}

	public void setEmap_hinvoice(String emap_hinvoice) {
		this.emap_hinvoice = emap_hinvoice;
	}

	public String getEcfi_modtime() {
		return ecfi_modtime;
	}

	public void setEcfi_modtime(String ecfi_modtime) {
		this.ecfi_modtime = ecfi_modtime;
	}

	public String getEcfi_modname() {
		return ecfi_modname;
	}

	public void setEcfi_modname(String ecfi_modname) {
		this.ecfi_modname = ecfi_modname;
	}

	public String getEmap_fpay() {
		return emap_fpay;
	}

	public void setEmap_fpay(String emap_fpay) {
		this.emap_fpay = emap_fpay;
	}

	public String getEmap_hpay() {
		return emap_hpay;
	}

	public void setEmap_hpay(String emap_hpay) {
		this.emap_hpay = emap_hpay;
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
