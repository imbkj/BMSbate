package Model;

import java.math.BigDecimal;

public class EmHouseUpdateModel {
	// / emhu_id
	private Integer emhu_id;
	// / gid
	private Integer gid;
	// / cid
	private Integer cid;
	// / ownmonth
	private Integer ownmonth;
	// / emhu_companyid
	private String emhu_companyid;
	// / emhu_company
	private String emhu_company;
	// / emhu_name
	private String emhu_name;
	// / emhu_idcard
	private String emhu_idcard;
	private String emhu_idcard2;
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
	private String emhu_cpp2;
	// / emhu_opp
	private Double emhu_opp;
	private String emhu_opp2;
	// / emhu_cp
	private Double emhu_cp;
	// / emhu_op
	private Double emhu_op;
	private Double total;
	// / emhu_single
	private Integer emhu_single;
	private String emhu_single2;
	// / emhu_ifstop
	private Integer emhu_ifstop;
	// / emhu_addtime
	private String emhu_addtime;
	// / emhu_addname
	private String emhu_addname;
	// / emhu_remark
	private String emhu_remark;
	// / emhu_startmonth
	private String emhu_startmonth;

	private String coba_shortname;

	private String coba_client;
	private String emba_sex;
	private String emba_email;

	private boolean judge; // 审核
	private boolean confirm; // 确认
	private String reason;
	private String state;
	private String coco_cpp;
	private String cohf_houseid;
	private String jc;

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
		BigDecimal b = new BigDecimal(emhu_cp);
		double d = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		this.emhu_cp = d;
	}

	public Double getEmhu_op() {
		return emhu_op;
	}

	public void setEmhu_op(Double emhu_op) {
		BigDecimal b = new BigDecimal(emhu_op);
		double d = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		this.emhu_op = d;
	}

	public Integer getEmhu_single() {
		return emhu_single;
	}

	public void setEmhu_single(Integer emhu_single) {
		this.emhu_single = emhu_single;
	}

	public Integer getEmhu_ifstop() {
		return emhu_ifstop;
	}

	public void setEmhu_ifstop(Integer emhu_ifstop) {
		this.emhu_ifstop = emhu_ifstop;
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

	public String getEmhu_remark() {
		return emhu_remark;
	}

	public void setEmhu_remark(String emhu_remark) {
		this.emhu_remark = emhu_remark;
	}

	public String getEmhu_startmonth() {
		return emhu_startmonth;
	}

	public void setEmhu_startmonth(String emhu_startmonth) {
		this.emhu_startmonth = emhu_startmonth;
	}

	public String getCoba_shortname() {
		return coba_shortname;
	}

	public void setCoba_shortname(String coba_shortname) {
		this.coba_shortname = coba_shortname;
	}

	public String getCoba_client() {
		return coba_client;
	}

	public void setCoba_client(String coba_client) {
		this.coba_client = coba_client;
	}

	public String getEmhu_cpp2() {
		return emhu_cpp2;
	}

	public void setEmhu_cpp2(String emhu_cpp2) {
		this.emhu_cpp2 = emhu_cpp2;
	}

	public String getEmhu_opp2() {
		return emhu_opp2;
	}

	public void setEmhu_opp2(String emhu_opp2) {
		this.emhu_opp2 = emhu_opp2;
	}

	public boolean isJudge() {
		return judge;
	}

	public void setJudge(boolean judge) {
		this.judge = judge;
	}

	public boolean isConfirm() {
		return confirm;
	}

	public void setConfirm(boolean confirm) {
		this.confirm = confirm;
	}

	public String getEmhu_single2() {
		return emhu_single2;
	}

	public void setEmhu_single2(String emhu_single2) {
		this.emhu_single2 = emhu_single2;
	}

	public String getEmba_sex() {
		return emba_sex;
	}

	public void setEmba_sex(String emba_sex) {
		this.emba_sex = emba_sex;
	}

	public String getEmba_email() {
		return emba_email;
	}

	public void setEmba_email(String emba_email) {
		this.emba_email = emba_email;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCohf_houseid() {
		return cohf_houseid;
	}

	public void setCohf_houseid(String cohf_houseid) {
		this.cohf_houseid = cohf_houseid;
	}

	public String getJc() {
		return jc;
	}

	public void setJc(String jc) {
		this.jc = jc;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getEmhu_idcard2() {
		return emhu_idcard2;
	}

	public void setEmhu_idcard2(String emhu_idcard2) {
		this.emhu_idcard2 = emhu_idcard2;
	}

	public String getCoco_cpp() {
		return coco_cpp;
	}

	public void setCoco_cpp(String coco_cpp) {
		this.coco_cpp = coco_cpp;
	}

}
