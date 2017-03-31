package Model;

import java.math.BigDecimal;

public class EmHouseUploadModel {
	// / hsup_id
	private Integer hsup_id;
	// / ownmonth
	private Integer ownmonth;
	// / gid
	private Integer gid;
	// / cid
	private Integer cid;
	// / hsup_name
	private String hsup_name;
	// / hsup_type
	private Integer hsup_type;
	// / hsup_idcard
	private String hsup_idcard;
	// / hsup_companyid
	private String hsup_companyid;
	// / hsup_houseid
	private String hsup_houseid;
	// / hsup_radix
	private double hsup_radix;
	// / hsup_trueradix
	private double hsup_trueradix;
	// / hsup_hj
	private String hsup_hj;
	// / hsup_cpp
	private Double hsup_cpp;
	// / hsup_opp
	private Double hsup_opp;
	// / hsup_title
	private String hsup_title;
	// / hsup_degree
	private String hsup_degree;
	// / hsup_wifename
	private String hsup_wifename;
	// / hsup_wifeidcard
	private String hsup_wifeidcard;
	// / hsup_change
	private String hsup_change;
	// / hsup_mobile
	private String hsup_mobile;
	// / hsup_single
	private Integer hsup_single;
	// / hsup_ifprogress
	private Integer hsup_ifprogress;
	// / hsup_remark
	private String hsup_remark;
	private String hsup_errormsg;
	private Double hsup_op, hsup_cp;

	private String coba_company;

	private String coba_client;
	private String hsup_addname;
	private String hsup_addtime;

	private Integer hsup_state;

	private String hsup_excompanyid;
	private String hsup_excompany;

	private boolean checked;

	public Integer getHsup_id() {
		return hsup_id;
	}

	public void setHsup_id(Integer hsup_id) {
		this.hsup_id = hsup_id;
	}

	public Integer getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(Integer ownmonth) {
		this.ownmonth = ownmonth;
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

	public String getHsup_name() {
		return hsup_name;
	}

	public void setHsup_name(String hsup_name) {
		this.hsup_name = hsup_name;
	}

	public Integer getHsup_type() {
		return hsup_type;
	}

	public void setHsup_type(Integer hsup_type) {
		this.hsup_type = hsup_type;
	}

	public String getHsup_idcard() {
		return hsup_idcard;
	}

	public void setHsup_idcard(String hsup_idcard) {
		this.hsup_idcard = hsup_idcard;
	}

	public String getHsup_companyid() {
		return hsup_companyid;
	}

	public void setHsup_companyid(String hsup_companyid) {
		this.hsup_companyid = hsup_companyid;
	}

	public String getHsup_houseid() {
		return hsup_houseid;
	}

	public void setHsup_houseid(String hsup_houseid) {
		this.hsup_houseid = hsup_houseid;
	}

	public String getHsup_hj() {
		return hsup_hj;
	}

	public void setHsup_hj(String hsup_hj) {
		this.hsup_hj = hsup_hj;
	}

	public Double getHsup_cpp() {
		return hsup_cpp;
	}

	public void setHsup_cpp(Double hsup_cpp) {
		this.hsup_cpp = hsup_cpp;
	}

	public Double getHsup_opp() {
		return hsup_opp;
	}

	public void setHsup_opp(Double hsup_opp) {
		this.hsup_opp = hsup_opp;
	}

	public String getHsup_title() {
		return hsup_title;
	}

	public void setHsup_title(String hsup_title) {
		this.hsup_title = hsup_title;
	}

	public String getHsup_degree() {
		return hsup_degree;
	}

	public void setHsup_degree(String hsup_degree) {
		this.hsup_degree = hsup_degree;
	}

	public String getHsup_wifename() {
		return hsup_wifename;
	}

	public void setHsup_wifename(String hsup_wifename) {
		this.hsup_wifename = hsup_wifename;
	}

	public String getHsup_wifeidcard() {
		return hsup_wifeidcard;
	}

	public void setHsup_wifeidcard(String hsup_wifeidcard) {
		this.hsup_wifeidcard = hsup_wifeidcard;
	}

	public String getHsup_change() {
		return hsup_change;
	}

	public void setHsup_change(String hsup_change) {
		this.hsup_change = hsup_change;
	}

	public String getHsup_mobile() {
		return hsup_mobile;
	}

	public void setHsup_mobile(String hsup_mobile) {
		this.hsup_mobile = hsup_mobile;
	}

	public Integer getHsup_single() {
		return hsup_single;
	}

	public void setHsup_single(Integer hsup_single) {
		this.hsup_single = hsup_single;
	}

	public Integer getHsup_ifprogress() {
		return hsup_ifprogress;
	}

	public void setHsup_ifprogress(Integer hsup_ifprogress) {
		this.hsup_ifprogress = hsup_ifprogress;
	}

	public String getHsup_remark() {
		return hsup_remark;
	}

	public void setHsup_remark(String hsup_remark) {
		this.hsup_remark = hsup_remark;
	}

	public String getHsup_errormsg() {
		return hsup_errormsg;
	}

	public void setHsup_errormsg(String hsup_errormsg) {
		this.hsup_errormsg = hsup_errormsg;
	}

	public Double getHsup_op() {
		return hsup_op;
	}

	public void setHsup_op(Double hsup_op) {
		this.hsup_op = hsup_op;
	}

	public Double getHsup_cp() {
		return hsup_cp;
	}

	public void setHsup_cp(Double hsup_cp) {
		this.hsup_cp = hsup_cp;
	}

	public String getCoba_company() {
		return coba_company;
	}

	public void setCoba_company(String coba_company) {
		this.coba_company = coba_company;
	}

	public String getCoba_client() {
		return coba_client;
	}

	public void setCoba_client(String coba_client) {
		this.coba_client = coba_client;
	}

	public String getHsup_addname() {
		return hsup_addname;
	}

	public void setHsup_addname(String hsup_addname) {
		this.hsup_addname = hsup_addname;
	}

	public String getHsup_addtime() {
		return hsup_addtime;
	}

	public void setHsup_addtime(String hsup_addtime) {
		this.hsup_addtime = hsup_addtime;
	}

	public Integer getHsup_state() {
		return hsup_state;
	}

	public void setHsup_state(Integer hsup_state) {
		this.hsup_state = hsup_state;
	}

	public String getHsup_excompanyid() {
		return hsup_excompanyid;
	}

	public void setHsup_excompanyid(String hsup_excompanyid) {
		this.hsup_excompanyid = hsup_excompanyid;
	}

	public String getHsup_excompany() {
		return hsup_excompany;
	}

	public void setHsup_excompany(String hsup_excompany) {
		this.hsup_excompany = hsup_excompany;
	}

	public double getHsup_radix() {
		return hsup_radix;
	}

	public void setHsup_radix(double hsup_radix) {
		this.hsup_radix = hsup_radix;
	}

	public double getHsup_trueradix() {
		return hsup_trueradix;
	}

	public void setHsup_trueradix(double hsup_trueradix) {
		this.hsup_trueradix = hsup_trueradix;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}
