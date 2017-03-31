package Model;

import java.math.BigDecimal;

public class EmHouseGJJModel {

	// / emhu_id
	private Integer emhu_id;
	// / gid
	private Integer gid;
	// / cid
	private Integer cid;
	// / ownmonth
	private Integer ownmonth;
	// / emhu_State
	private String emhu_state;
	// / emhu_StartMonth
	private String emhu_startmonth;
	// / emhu_CompanyID
	private String emhu_companyid;
	// / emhu_company
	private String emhu_company;
	// / emhu_name
	private String emhu_name;
	// / emhu_idcard
	private String emhu_idcard;
	// / emhu_houseid
	private String emhu_houseid;
	// / emhu_radix
	private double emhu_radix;
	// / emhu_cpp
	private double emhu_cpp;
	// / emhu_opp
	private double emhu_opp;
	// / emhu_cp
	private double emhu_cp;
	// / emhu_op
	private double emhu_op;
	// / emhu_Sum
	private BigDecimal emhu_sum;
	// / emhu_total
	private BigDecimal emhu_total;
	private BigDecimal emhu_total2;
	// / emhu_single
	private Integer emhu_single;
	// / emhu_addtime
	private String emhu_addtime;
	// / emhu_addname
	private String emhu_addname;
	// / emhu_filename
	private String emhu_filename;
	// / emhu_hj
	private String emhu_hj;
	// / emhu_computerid
	private String emhu_computerid;
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

	private Integer num;
	private Integer num2;

	private double cohf_cpp;
	private String cohf_client;

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

	public String getEmhu_state() {
		return emhu_state;
	}

	public void setEmhu_state(String emhu_state) {
		this.emhu_state = emhu_state;
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

	public String getEmhu_houseid() {
		return emhu_houseid;
	}

	public void setEmhu_houseid(String emhu_houseid) {
		this.emhu_houseid = emhu_houseid;
	}

	public double getEmhu_radix() {
		return emhu_radix;
	}

	public void setEmhu_radix(double emhu_radix) {
		this.emhu_radix = emhu_radix;
	}

	public double getEmhu_cpp() {
		return emhu_cpp;
	}

	public void setEmhu_cpp(double emhu_cpp) {
		this.emhu_cpp = emhu_cpp;
	}

	public double getEmhu_opp() {
		return emhu_opp;
	}

	public void setEmhu_opp(double emhu_opp) {
		this.emhu_opp = emhu_opp;
	}

	public double getEmhu_cp() {
		return emhu_cp;
	}

	public void setEmhu_cp(double emhu_cp) {
		this.emhu_cp = emhu_cp;
	}

	public double getEmhu_op() {
		return emhu_op;
	}

	public void setEmhu_op(double emhu_op) {
		this.emhu_op = emhu_op;
	}

	public BigDecimal getEmhu_sum() {
		return emhu_sum;
	}

	public void setEmhu_sum(BigDecimal emhu_sum) {
		this.emhu_sum = emhu_sum;
	}

	public BigDecimal getEmhu_total() {
		return emhu_total;
	}

	public void setEmhu_total(BigDecimal emhu_total) {
		this.emhu_total = emhu_total == null ? null : emhu_total.setScale(2,
				BigDecimal.ROUND_HALF_UP);
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

	public String getEmhu_filename() {
		return emhu_filename;
	}

	public void setEmhu_filename(String emhu_filename) {
		this.emhu_filename = emhu_filename;
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

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public double getCohf_cpp() {
		return cohf_cpp;
	}

	public void setCohf_cpp(double cohf_cpp) {
		this.cohf_cpp = cohf_cpp;
	}

	public BigDecimal getEmhu_total2() {
		return emhu_total2;
	}

	public void setEmhu_total2(BigDecimal emhu_total2) {
		this.emhu_total2 = emhu_total2 == null ? BigDecimal.ZERO : emhu_total2
				.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public Integer getNum2() {
		return num2;
	}

	public void setNum2(Integer num2) {
		this.num2 = num2;
	}

	public String getCohf_client() {
		return cohf_client;
	}

	public void setCohf_client(String cohf_client) {
		this.cohf_client = cohf_client;
	}

}
