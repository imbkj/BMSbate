package Model;

import java.math.BigDecimal;
import java.util.Date;

public class EmCommissionYearChangemModel {

	/**
	 * @param args
	 */
	private Integer  ecyc_id;
private Integer ecyt_id; //父表ID
		/// 雇员编号
private Integer  gid;
		/// ecyc_city
private String  ecyc_city;
		/// 养老基数
private BigDecimal  ecyc_yl_base;
		/// 失业基数
private BigDecimal  ecyc_sye_base;
		/// 工伤基数
private BigDecimal  ecyc_gs_base;
		/// 医疗基数
private BigDecimal  ecyc_jl_base;
		/// 生育基数
private BigDecimal  ecyc_syu_base;
		/// 住房基数
private BigDecimal  ecyc_house_pay;
		/// 住房企业比例
private String  ecyc_house_cp;
		/// 住房个人比例
private String  ecyc_house_op;
		/// ecyc_house_hj
private BigDecimal  ecyc_house_hj;
		/// ecyc_fact_date
private Date  ecyc_fact_date;
		/// 状态
private Integer  ecyc_state;
		/// 变更状态
private Integer  ecyc_change;
		/// 添加人
private String  ecyc_addname;
		/// 添加时间
private Date  ecyc_addtime;
		/// ecyc_sb_base
private BigDecimal  ecyc_sb_base;
		/// ecyc_house_base
private BigDecimal  ecyc_house_base;
		/// ecyc_excel
private Integer  ecyc_excel;
		/// ecyc_excel_date
private Date  ecyc_excel_date;
		/// ecyc_excel_name
private String  ecyc_excel_name;
		/// ecyc_excel_file
private String  ecyc_excel_file;
		/// ecyc_remark
private String  ecyc_remark;
		/// ecyc_bc_base
private BigDecimal  ecyc_bc_base;
		/// ecyc_bc_cp
private String  ecyc_bc_cp;
		/// ecyc_bc_op
private String  ecyc_bc_op;
		/// ecyc_bc_total
private BigDecimal  ecyc_bc_total;
		/// ecyc_yl_date
private Date  ecyc_yl_date;
		/// ecyc_sye_date
private Date  ecyc_sye_date;
		/// ecyc_gs_date
private Date  ecyc_gs_date;
		/// ecyc_jl_date
private Date  ecyc_jl_date;
		/// ecyc_syu_date
private Date  ecyc_syu_date;
		/// ecyc_house_date
private Date  ecyc_house_date;
		/// ecyc_bc_date
private Date  ecyc_bc_date;

private Integer cid;

private String coba_shortname;

private String coba_company;

private String emba_wt;

private String emba_name;
private String emba_idcard;

//机构名
private String coab_name;
//城市
private String city;

private String coba_client;

//
private String  ecyc_statestr;

 
private BigDecimal ecyc_ycohouse_base;
private BigDecimal ecyc_yemhouse_base;
private String ecyc_yhouseop;
private String ecyc_yhousecp;
private int coab_id ;




 
public int getCoab_id() {
	return coab_id;
}
public void setCoab_id(int coab_id) {
	this.coab_id = coab_id;
}
public BigDecimal getEcyc_ycohouse_base() {
	return ecyc_ycohouse_base;
}
public void setEcyc_ycohouse_base(BigDecimal ecyc_ycohouse_base) {
	this.ecyc_ycohouse_base = ecyc_ycohouse_base;
}
public BigDecimal getEcyc_yemhouse_base() {
	return ecyc_yemhouse_base;
}
public void setEcyc_yemhouse_base(BigDecimal ecyc_yemhouse_base) {
	this.ecyc_yemhouse_base = ecyc_yemhouse_base;
}
public String getEcyc_yhouseop() {
	return ecyc_yhouseop;
}
public void setEcyc_yhouseop(String ecyc_yhouseop) {
	this.ecyc_yhouseop = ecyc_yhouseop;
}
public String getEcyc_yhousecp() {
	return ecyc_yhousecp;
}
public void setEcyc_yhousecp(String ecyc_yhousecp) {
	this.ecyc_yhousecp = ecyc_yhousecp;
}
public Integer getEcyt_id() {
	return ecyt_id;
}
public void setEcyt_id(Integer ecyt_id) {
	this.ecyt_id = ecyt_id;
}
public String getEmba_idcard() {
	return emba_idcard;
}
public void setEmba_idcard(String emba_idcard) {
	this.emba_idcard = emba_idcard;
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
public String getEcyc_statestr() {
	return ecyc_statestr;
}
public void setEcyc_statestr(String ecyc_statestr) {
	this.ecyc_statestr = ecyc_statestr;
}
public Integer getEcyc_id() {
	return ecyc_id;
}
public void setEcyc_id(Integer ecyc_id) {
	this.ecyc_id = ecyc_id;
}
public Integer getGid() {
	return gid;
}
public void setGid(Integer gid) {
	this.gid = gid;
}
public String getEcyc_city() {
	return ecyc_city;
}
public void setEcyc_city(String ecyc_city) {
	this.ecyc_city = ecyc_city;
}
public BigDecimal getEcyc_yl_base() {
	return ecyc_yl_base;
}
public void setEcyc_yl_base(BigDecimal ecyc_yl_base) {
	this.ecyc_yl_base = ecyc_yl_base;
}
public BigDecimal getEcyc_sye_base() {
	return ecyc_sye_base;
}
public void setEcyc_sye_base(BigDecimal ecyc_sye_base) {
	this.ecyc_sye_base = ecyc_sye_base;
}
public BigDecimal getEcyc_gs_base() {
	return ecyc_gs_base;
}
public void setEcyc_gs_base(BigDecimal ecyc_gs_base) {
	this.ecyc_gs_base = ecyc_gs_base;
}
public BigDecimal getEcyc_jl_base() {
	return ecyc_jl_base;
}
public void setEcyc_jl_base(BigDecimal ecyc_jl_base) {
	this.ecyc_jl_base = ecyc_jl_base;
}
public BigDecimal getEcyc_syu_base() {
	return ecyc_syu_base;
}
public void setEcyc_syu_base(BigDecimal ecyc_syu_base) {
	this.ecyc_syu_base = ecyc_syu_base;
}
public BigDecimal getEcyc_house_pay() {
	return ecyc_house_pay;
}
public void setEcyc_house_pay(BigDecimal ecyc_house_pay) {
	this.ecyc_house_pay = ecyc_house_pay;
}
public String getEcyc_house_cp() {
	return ecyc_house_cp;
}
public void setEcyc_house_cp(String ecyc_house_cp) {
	this.ecyc_house_cp = ecyc_house_cp;
}
public String getEcyc_house_op() {
	return ecyc_house_op;
}
public void setEcyc_house_op(String ecyc_house_op) {
	this.ecyc_house_op = ecyc_house_op;
}
public BigDecimal getEcyc_house_hj() {
	return ecyc_house_hj;
}
public void setEcyc_house_hj(BigDecimal ecyc_house_hj) {
	this.ecyc_house_hj = ecyc_house_hj;
}
public Date getEcyc_fact_date() {
	return ecyc_fact_date;
}
public void setEcyc_fact_date(Date ecyc_fact_date) {
	this.ecyc_fact_date = ecyc_fact_date;
}
public Integer getEcyc_state() {
	return ecyc_state;
}
public void setEcyc_state(Integer ecyc_state) {
	this.ecyc_state = ecyc_state;
}
public Integer getEcyc_change() {
	return ecyc_change;
}
public void setEcyc_change(Integer ecyc_change) {
	this.ecyc_change = ecyc_change;
}
public String getEcyc_addname() {
	return ecyc_addname;
}
public void setEcyc_addname(String ecyc_addname) {
	this.ecyc_addname = ecyc_addname;
}
public Date getEcyc_addtime() {
	return ecyc_addtime;
}
public void setEcyc_addtime(Date ecyc_addtime) {
	this.ecyc_addtime = ecyc_addtime;
}
public BigDecimal getEcyc_sb_base() {
	return ecyc_sb_base;
}
public void setEcyc_sb_base(BigDecimal ecyc_sb_base) {
	this.ecyc_sb_base = ecyc_sb_base;
}
public BigDecimal getEcyc_house_base() {
	return ecyc_house_base;
}
public void setEcyc_house_base(BigDecimal ecyc_house_base) {
	this.ecyc_house_base = ecyc_house_base;
}
public Integer getEcyc_excel() {
	return ecyc_excel;
}
public void setEcyc_excel(Integer ecyc_excel) {
	this.ecyc_excel = ecyc_excel;
}
public Date getEcyc_excel_date() {
	return ecyc_excel_date;
}
public void setEcyc_excel_date(Date ecyc_excel_date) {
	this.ecyc_excel_date = ecyc_excel_date;
}
public String getEcyc_excel_name() {
	return ecyc_excel_name;
}
public void setEcyc_excel_name(String ecyc_excel_name) {
	this.ecyc_excel_name = ecyc_excel_name;
}
public String getEcyc_excel_file() {
	return ecyc_excel_file;
}
public void setEcyc_excel_file(String ecyc_excel_file) {
	this.ecyc_excel_file = ecyc_excel_file;
}
public String getEcyc_remark() {
	return ecyc_remark;
}
public void setEcyc_remark(String ecyc_remark) {
	this.ecyc_remark = ecyc_remark;
}
public BigDecimal getEcyc_bc_base() {
	return ecyc_bc_base;
}
public void setEcyc_bc_base(BigDecimal ecyc_bc_base) {
	this.ecyc_bc_base = ecyc_bc_base;
}
public String getEcyc_bc_cp() {
	return ecyc_bc_cp;
}
public void setEcyc_bc_cp(String ecyc_bc_cp) {
	this.ecyc_bc_cp = ecyc_bc_cp;
}
public String getEcyc_bc_op() {
	return ecyc_bc_op;
}
public void setEcyc_bc_op(String ecyc_bc_op) {
	this.ecyc_bc_op = ecyc_bc_op;
}
public BigDecimal getEcyc_bc_total() {
	return ecyc_bc_total;
}
public void setEcyc_bc_total(BigDecimal ecyc_bc_total) {
	this.ecyc_bc_total = ecyc_bc_total;
}
public Date getEcyc_yl_date() {
	return ecyc_yl_date;
}
public void setEcyc_yl_date(Date ecyc_yl_date) {
	this.ecyc_yl_date = ecyc_yl_date;
}
public Date getEcyc_sye_date() {
	return ecyc_sye_date;
}
public void setEcyc_sye_date(Date ecyc_sye_date) {
	this.ecyc_sye_date = ecyc_sye_date;
}
public Date getEcyc_gs_date() {
	return ecyc_gs_date;
}
public void setEcyc_gs_date(Date ecyc_gs_date) {
	this.ecyc_gs_date = ecyc_gs_date;
}
public Date getEcyc_jl_date() {
	return ecyc_jl_date;
}
public void setEcyc_jl_date(Date ecyc_jl_date) {
	this.ecyc_jl_date = ecyc_jl_date;
}
public Date getEcyc_syu_date() {
	return ecyc_syu_date;
}
public void setEcyc_syu_date(Date ecyc_syu_date) {
	this.ecyc_syu_date = ecyc_syu_date;
}
public Date getEcyc_house_date() {
	return ecyc_house_date;
}
public void setEcyc_house_date(Date ecyc_house_date) {
	this.ecyc_house_date = ecyc_house_date;
}
public Date getEcyc_bc_date() {
	return ecyc_bc_date;
}
public void setEcyc_bc_date(Date ecyc_bc_date) {
	this.ecyc_bc_date = ecyc_bc_date;
}
public Integer getCid() {
	return cid;
}
public void setCid(Integer cid) {
	this.cid = cid;
}
public String getCoba_shortname() {
	return coba_shortname;
}
public void setCoba_shortname(String coba_shortname) {
	this.coba_shortname = coba_shortname;
}
public String getEmba_wt() {
	return emba_wt;
}
public void setEmba_wt(String emba_wt) {
	this.emba_wt = emba_wt;
}
public String getEmba_name() {
	return emba_name;
}
public void setEmba_name(String emba_name) {
	this.emba_name = emba_name;
}
public String getCoab_name() {
	return coab_name;
}
public void setCoab_name(String coab_name) {
	this.coab_name = coab_name;
}
public String getCity() {
	return city;
}
public void setCity(String city) {
	this.city = city;
}
public EmCommissionYearChangemModel() {
	super();
}



}
