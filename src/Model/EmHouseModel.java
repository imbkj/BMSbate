package Model;

import java.math.BigDecimal;

import Conn.dbconn;

public class EmHouseModel {
	// / emhu_id
	private Integer emhu_id;
	// / gid
	private Integer gid;
	// / cid
	private Integer cid;
	// / ownmonth
	private Integer ownmonth;
	// / emhu_startmonth
	private String emhu_startmonth;
	// / emhu_companyid
	private String emhu_companyid;
	// / emhu_company
	private String emhu_company;
	// / emhu_name
	private String emhu_name;
	// / emhu_idcard
	private String emhu_idcard;
	// / emhu_idcardclass
	private String emhu_idcardclass;
	// / emhu_hj
	private String emhu_hj;
	// / emhu_computerid
	private String emhu_computerid;
	// / emhu_houseid
	private String emhu_houseid;
	// / emhu_mobile
	private String emhu_mobile;
	// / emhu_title
	private String emhu_title;
	// / emhu_wifename
	private String emhu_wifename;
	// / emhu_wifeidcard
	private String emhu_wifeidcard;
	// / emhu_degree
	private String emhu_degree;
	// / emhu_radix
	private Double emhu_radix;
	// / emhu_bonus
	private BigDecimal emhu_bonus;
	// / emhu_cpp
	private Double emhu_cpp;
	// / emhu_opp
	private Double emhu_opp;
	// / emhu_cp
	private Double emhu_cp;
	// / emhu_op
	private Double emhu_op;
	// / emhu_total
	private BigDecimal emhu_total;
	// / emhu_single
	private Integer emhu_single;
	// / emhu_addtime
	private String emhu_addtime;
	// / emhu_addname
	private String emhu_addname;
	// / emhu_ifstop
	private Integer emhu_ifstop;
	private String remark;

	public Integer getEmhu_id() {
		return emhu_id;
	}

	public void setEmhu_id(Integer emhu_id) {
		this.emhu_id = emhu_id;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public Integer getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(Integer ownmonth) {
		this.ownmonth = ownmonth;
	}

	public String getEmhu_startmonth() {
		return emhu_startmonth;
	}

	public void setEmhu_startmonth(String emhu_startmonth) {
		this.emhu_startmonth = emhu_startmonth;
	}

	public String getEmhu_companyid() {
		return emhu_companyid;
	}

	public void setEmhu_companyid(String emhu_companyid) {
		this.emhu_companyid = emhu_companyid;
	}

	public String getEmhu_company() {
		return emhu_company;
	}

	public void setEmhu_company(String emhu_company) {
		this.emhu_company = emhu_company;
	}

	public String getEmhu_name() {
		return emhu_name;
	}

	public void setEmhu_name(String emhu_name) {
		this.emhu_name = emhu_name;
	}

	public String getEmhu_idcard() {
		return emhu_idcard;
	}

	public void setEmhu_idcard(String emhu_idcard) {
		this.emhu_idcard = emhu_idcard;
	}

	public String getEmhu_idcardclass() {
		return emhu_idcardclass;
	}

	public void setEmhu_idcardclass(String emhu_idcardclass) {
		this.emhu_idcardclass = emhu_idcardclass;
	}

	public String getEmhu_hj() {
		return emhu_hj;
	}

	public void setEmhu_hj(String emhu_hj) {
		this.emhu_hj = emhu_hj;
	}

	public String getEmhu_computerid() {
		return emhu_computerid;
	}

	public void setEmhu_computerid(String emhu_computerid) {
		this.emhu_computerid = emhu_computerid;
	}

	public String getEmhu_houseid() {
		return emhu_houseid;
	}

	public void setEmhu_houseid(String emhu_houseid) {
		this.emhu_houseid = emhu_houseid;
	}

	public String getEmhu_mobile() {
		return emhu_mobile;
	}

	public void setEmhu_mobile(String emhu_mobile) {
		this.emhu_mobile = emhu_mobile;
	}

	public String getEmhu_title() {
		return emhu_title;
	}

	public void setEmhu_title(String emhu_title) {
		this.emhu_title = emhu_title;
	}

	public String getEmhu_wifename() {
		return emhu_wifename;
	}

	public void setEmhu_wifename(String emhu_wifename) {
		this.emhu_wifename = emhu_wifename;
	}

	public String getEmhu_wifeidcard() {
		return emhu_wifeidcard;
	}

	public void setEmhu_wifeidcard(String emhu_wifeidcard) {
		this.emhu_wifeidcard = emhu_wifeidcard;
	}

	public String getEmhu_degree() {
		return emhu_degree;
	}

	public void setEmhu_degree(String emhu_degree) {
		this.emhu_degree = emhu_degree;
	}

	public Double getEmhu_radix() {
		return emhu_radix;
	}

	public void setEmhu_radix(Double emhu_radix) {
		this.emhu_radix = emhu_radix;
	}

	public BigDecimal getEmhu_bonus() {
		return emhu_bonus;
	}

	public void setEmhu_bonus(BigDecimal emhu_bonus) {
		this.emhu_bonus = emhu_bonus;
	}

	public Double getEmhu_cpp() {
		return emhu_cpp;
	}

	public void setEmhu_cpp(Double emhu_cpp) {
		this.emhu_cpp = emhu_cpp;
	}

	public Double getEmhu_opp() {
		return emhu_opp;
	}

	public void setEmhu_opp(Double emhu_opp) {
		this.emhu_opp = emhu_opp;
	}

	public Double getEmhu_cp() {
		return emhu_cp;
	}

	public void setEmhu_cp(Double emhu_cp) {
		BigDecimal bd = new BigDecimal(emhu_cp);
		bd=bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		this.emhu_cp = bd.doubleValue();
	}

	public Double getEmhu_op() {
		return emhu_op;
	}

	public void setEmhu_op(Double emhu_op) {
		BigDecimal bd = new BigDecimal(emhu_op);
		bd=bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		this.emhu_op = bd.doubleValue();
	}

	public BigDecimal getEmhu_total() {
		return emhu_total;
	}

	public void setEmhu_total(BigDecimal emhu_total) {
		emhu_total=emhu_total.setScale(2, BigDecimal.ROUND_HALF_UP);
		this.emhu_total = emhu_total;
	}

	public Integer getEmhu_single() {
		return emhu_single;
	}

	public void setEmhu_single(Integer emhu_single) {
		this.emhu_single = emhu_single;
	}

	public String getEmhu_addtime() {
		return emhu_addtime;
	}

	public void setEmhu_addtime(String emhu_addtime) {
		this.emhu_addtime = emhu_addtime;
	}

	public String getEmhu_addname() {
		return emhu_addname;
	}

	public void setEmhu_addname(String emhu_addname) {
		this.emhu_addname = emhu_addname;
	}

	public Integer getEmhu_ifstop() {
		return emhu_ifstop;
	}

	public void setEmhu_ifstop(Integer emhu_ifstop) {
		this.emhu_ifstop = emhu_ifstop;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
