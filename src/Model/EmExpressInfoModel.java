package Model;

import java.math.BigDecimal;
import java.util.Date;

public class EmExpressInfoModel {
	// / expr_id
	private Integer expr_id;
	// / expr_exct_id
	private Integer expr_exct_id;
	// / gid
	private Integer gid;
	// / cid
	private Integer cid;
	// / expr_content
	private String expr_content;
	// / expr_company
	private String expr_company;
	// / expr_rank
	private String expr_rank;
	// / expr_class
	private String expr_class;
	// / expr_acceptname
	private String expr_acceptname;
	// / expr_accepttime
	private Date expr_accepttime;
	// / expr_state
	private Integer expr_state;
	// / expr_addname
	private String expr_addname;
	// / expr_addtime
	private Date expr_addtime;
	// / expr_sendname
	private String expr_sendname;
	// / expr_sendtime
	private String expr_sendtime;
	// / expr_waynumber
	private String expr_waynumber;
	// / expr_fee
	private BigDecimal expr_fee;
	// / expr_modname
	private String expr_modname;
	// / expr_modtime
	private Date expr_modtime;

	private String ecprstate;

	private Integer expr_tarpid;

	private Integer num;

	private String expr_companySpell;

	// / exct_id
	private Integer exct_id;
	// / exct_type
	private String exct_type;
	// / exct_receivename
	private String exct_receivename;
	// / exct_address
	private String exct_address;
	// / exct_code
	private String exct_code;
	// / exct_mobile
	private String exct_mobile;
	// / exct_state
	private Integer exct_state;
	private String exct_addname;
	private Date exct_addtime;
	private String exct_phone;

	private String expr_operattime, expr_operatecontent,expr_remark;

	private String coba_shortname, emba_name;

	public Integer getExpr_id() {
		return expr_id;
	}

	public void setExpr_id(Integer expr_id) {
		this.expr_id = expr_id;
	}

	public Integer getExpr_exct_id() {
		return expr_exct_id;
	}

	public void setExpr_exct_id(Integer expr_exct_id) {
		this.expr_exct_id = expr_exct_id;
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

	public String getExpr_content() {
		return expr_content;
	}

	public void setExpr_content(String expr_content) {
		this.expr_content = expr_content;
	}

	public String getExpr_company() {
		return expr_company;
	}

	public void setExpr_company(String expr_company) {
		if (expr_company.contains("中通")) {
			setExpr_companySpell("zhongtong");
		} else if (expr_company.contains("顺丰")) {
			setExpr_companySpell("shunfeng");
		} else if (expr_company.contains("申通")) {
			setExpr_companySpell("shentong");
		} else if (expr_company.contains("圆通")) {
			setExpr_companySpell("yuantong");
		} else if (expr_company.contains("韵达")) {
			setExpr_companySpell("yunda");
		} else if (expr_company.contains("天天")) {
			setExpr_companySpell("tiantian");
		}
		else if (expr_company.contains("EMS")) {
			setExpr_companySpell("ems");
		} else if (expr_company.contains("汇通")) {
			setExpr_companySpell("huitong");
		}
		this.expr_company = expr_company;
	}

	public String getExpr_rank() {
		return expr_rank;
	}

	public void setExpr_rank(String expr_rank) {
		this.expr_rank = expr_rank;
	}

	public String getExpr_class() {
		return expr_class;
	}

	public void setExpr_class(String expr_class) {
		this.expr_class = expr_class;
	}

	public String getExpr_acceptname() {
		return expr_acceptname;
	}

	public void setExpr_acceptname(String expr_acceptname) {
		this.expr_acceptname = expr_acceptname;
	}

	public Date getExpr_accepttime() {
		return expr_accepttime;
	}

	public void setExpr_accepttime(Date expr_accepttime) {
		this.expr_accepttime = expr_accepttime;
	}

	public Integer getExpr_state() {
		return expr_state;
	}

	public void setExpr_state(Integer expr_state) {
		this.expr_state = expr_state;
	}

	public String getExpr_addname() {
		return expr_addname;
	}

	public void setExpr_addname(String expr_addname) {
		this.expr_addname = expr_addname;
	}

	public Date getExpr_addtime() {
		return expr_addtime;
	}

	public void setExpr_addtime(Date expr_addtime) {
		this.expr_addtime = expr_addtime;
	}

	public String getExpr_sendname() {
		return expr_sendname;
	}

	public void setExpr_sendname(String expr_sendname) {
		this.expr_sendname = expr_sendname;
	}

	public String getExpr_sendtime() {
		return expr_sendtime;
	}

	public void setExpr_sendtime(String expr_sendtime) {
		this.expr_sendtime = expr_sendtime;
	}

	public String getExpr_waynumber() {
		return expr_waynumber;
	}

	public void setExpr_waynumber(String expr_waynumber) {
		this.expr_waynumber = expr_waynumber;
	}

	public BigDecimal getExpr_fee() {
		return expr_fee;
	}

	public void setExpr_fee(BigDecimal expr_fee) {
		this.expr_fee = expr_fee;
	}

	public String getExpr_modname() {
		return expr_modname;
	}

	public void setExpr_modname(String expr_modname) {
		this.expr_modname = expr_modname;
	}

	public Date getExpr_modtime() {
		return expr_modtime;
	}

	public void setExpr_modtime(Date expr_modtime) {
		this.expr_modtime = expr_modtime;
	}

	public Integer getExct_id() {
		return exct_id;
	}

	public void setExct_id(Integer exct_id) {
		this.exct_id = exct_id;
	}

	public String getExct_type() {
		return exct_type;
	}

	public void setExct_type(String exct_type) {
		this.exct_type = exct_type;
	}

	public String getExct_receivename() {
		return exct_receivename;
	}

	public void setExct_receivename(String exct_receivename) {
		this.exct_receivename = exct_receivename;
	}

	public String getExct_address() {
		return exct_address;
	}

	public void setExct_address(String exct_address) {
		this.exct_address = exct_address;
	}

	public String getExct_code() {
		return exct_code;
	}

	public void setExct_code(String exct_code) {
		this.exct_code = exct_code;
	}

	public String getExct_mobile() {
		return exct_mobile;
	}

	public void setExct_mobile(String exct_mobile) {
		this.exct_mobile = exct_mobile;
	}

	public Integer getExct_state() {
		return exct_state;
	}

	public void setExct_state(Integer exct_state) {
		this.exct_state = exct_state;
	}

	public String getEcprstate() {
		return ecprstate;
	}

	public void setEcprstate(String ecprstate) {
		this.ecprstate = ecprstate;
	}

	public Integer getExpr_tarpid() {
		return expr_tarpid;
	}

	public void setExpr_tarpid(Integer expr_tarpid) {
		this.expr_tarpid = expr_tarpid;
	}

	public String getExct_addname() {
		return exct_addname;
	}

	public void setExct_addname(String exct_addname) {
		this.exct_addname = exct_addname;
	}

	public Date getExct_addtime() {
		return exct_addtime;
	}

	public void setExct_addtime(Date exct_addtime) {
		this.exct_addtime = exct_addtime;
	}

	public String getExct_phone() {
		return exct_phone;
	}

	public void setExct_phone(String exct_phone) {
		this.exct_phone = exct_phone;
	}

	public String getCoba_shortname() {
		return coba_shortname;
	}

	public void setCoba_shortname(String coba_shortname) {
		this.coba_shortname = coba_shortname;
	}

	public String getEmba_name() {
		return emba_name;
	}

	public void setEmba_name(String emba_name) {
		this.emba_name = emba_name;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getExpr_operattime() {
		return expr_operattime;
	}

	public void setExpr_operattime(String expr_operattime) {
		this.expr_operattime = expr_operattime;
	}

	public String getExpr_operatecontent() {
		return expr_operatecontent;
	}

	public void setExpr_operatecontent(String expr_operatecontent) {
		this.expr_operatecontent = expr_operatecontent;
	}

	public String getExpr_companySpell() {
		return expr_companySpell;
	}

	public void setExpr_companySpell(String expr_companySpell) {
		this.expr_companySpell = expr_companySpell;
	}

	public String getExpr_remark() {
		return expr_remark;
	}

	public void setExpr_remark(String expr_remark) {
		this.expr_remark = expr_remark;
	}
	
}
