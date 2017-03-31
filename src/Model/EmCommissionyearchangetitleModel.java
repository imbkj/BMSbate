package Model;

import java.util.Date;


public class EmCommissionyearchangetitleModel {

	/**
	 * @param args
	 */
private Integer  ecyt_id;
		/// ecyt_monthsart
private String  ecyt_monthsart;
		/// ecyt_monthend
private String  ecyt_monthend;
		/// 0:未确认1：已确认2：采集中3：采集完成
private Integer  ecyt_state;
		/// cabc_id
private Integer  coab_id;
		/// 0:大户1：独立开户
private Integer  ecyt_single;
		/// ecyt_addname
private String  ecyt_addname;
		/// ecyt_addtime
private Date  ecyt_addtime;
		/// ecyt_ylao
private Integer  ecyt_ylao;
		/// ecyt_yliao
private Integer  ecyt_yliao;
		/// ecyt_gshang
private Integer  ecyt_gshang;
		/// ecyt_sye
private Integer  ecyt_sye;
		/// ecyt_gjj
private Integer  ecyt_gjj;
		/// ecyt_bcgjj
private Integer  ecyt_bcgjj;
		/// ecyt_syu
private Integer  ecyt_syu;
		/// ecyt_remark
private String  ecyt_remark;

private Integer ecyt_lzcj;

private String ecyt_lzstatedate;

private String ecyt_lzenddate;

private Date ecyt_ylaotime;

private Date ecyt_yliaotime;
private Date ecyt_gshangtime;
private Date ecyt_syetime;
private Date ecyt_gjjtime;
private Date ecyt_bcgjjtime;
private Date ecyt_syutime;
private Date ecyt_modtime;
private String ecyt_startdate;

private String ecyt_modname;

private String city;
private String jgname;
private String ecyt_singlestr;
private String  ecyt_ylaostr;
/// ecyt_yliao
private String  ecyt_yliaostr;
/// ecyt_gshang
private String  ecyt_gshangstr;
/// ecyt_sye
private String  ecyt_syestr;
/// ecyt_gjj
private String  ecyt_gjjstr;
/// ecyt_bcgjj
private String  ecyt_bcgjjstr;
/// ecyt_syu
private String  ecyt_syustr;

private String  ecyt_statestr;

private String ecyt_lzcjstr;

private Integer taba_tapr_id;//政策关系表ID；总任务单ID






public String getEcyt_startdate() {
	return ecyt_startdate;
}
public void setEcyt_startdate(String ecyt_startdate) {
	this.ecyt_startdate = ecyt_startdate;
}
public Integer getTaba_tapr_id() {
	return taba_tapr_id;
}
public void setTaba_tapr_id(Integer taba_tapr_id) {
	this.taba_tapr_id = taba_tapr_id;
}
public Integer getEcyt_lzcj() {
	return ecyt_lzcj;
}
public void setEcyt_lzcj(Integer ecyt_lzcj) {
	this.ecyt_lzcj = ecyt_lzcj;
}
public String getEcyt_lzstatedate() {
	return ecyt_lzstatedate;
}
public void setEcyt_lzstatedate(String ecyt_lzstatedate) {
	this.ecyt_lzstatedate = ecyt_lzstatedate;
}
public String getEcyt_lzenddate() {
	return ecyt_lzenddate;
}
public void setEcyt_lzenddate(String ecyt_lzenddate) {
	this.ecyt_lzenddate = ecyt_lzenddate;
}
public String getEcyt_lzcjstr() {
	return ecyt_lzcjstr;
}
public void setEcyt_lzcjstr(String ecyt_lzcjstr) {
	this.ecyt_lzcjstr = ecyt_lzcjstr;
}
public String getEcyt_statestr() {
	return ecyt_statestr;
}
public void setEcyt_statestr(String ecyt_statestr) {
	this.ecyt_statestr = ecyt_statestr;
}
public String getEcyt_singlestr() {
	return ecyt_singlestr;
}
public void setEcyt_singlestr(String ecyt_singlestr) {
	this.ecyt_singlestr = ecyt_singlestr;
}
public String getEcyt_ylaostr() {
	return ecyt_ylaostr;
}
public void setEcyt_ylaostr(String ecyt_ylaostr) {
	this.ecyt_ylaostr = ecyt_ylaostr;
}
public String getEcyt_yliaostr() {
	return ecyt_yliaostr;
}
public void setEcyt_yliaostr(String ecyt_yliaostr) {
	this.ecyt_yliaostr = ecyt_yliaostr;
}
public String getEcyt_gshangstr() {
	return ecyt_gshangstr;
}
public void setEcyt_gshangstr(String ecyt_gshangstr) {
	this.ecyt_gshangstr = ecyt_gshangstr;
}
public String getEcyt_syestr() {
	return ecyt_syestr;
}
public void setEcyt_syestr(String ecyt_syestr) {
	this.ecyt_syestr = ecyt_syestr;
}
public String getEcyt_gjjstr() {
	return ecyt_gjjstr;
}
public void setEcyt_gjjstr(String ecyt_gjjstr) {
	this.ecyt_gjjstr = ecyt_gjjstr;
}
public String getEcyt_bcgjjstr() {
	return ecyt_bcgjjstr;
}
public void setEcyt_bcgjjstr(String ecyt_bcgjjstr) {
	this.ecyt_bcgjjstr = ecyt_bcgjjstr;
}
public String getEcyt_syustr() {
	return ecyt_syustr;
}
public void setEcyt_syustr(String ecyt_syustr) {
	this.ecyt_syustr = ecyt_syustr;
}
public String getCity() {
	return city;
}
public void setCity(String city) {
	this.city = city;
}
public String getJgname() {
	return jgname;
}
public void setJgname(String jgname) {
	this.jgname = jgname;
}
public Integer getEcyt_id() {
	return ecyt_id;
}
public void setEcyt_id(Integer ecyt_id) {
	this.ecyt_id = ecyt_id;
}
public String getEcyt_monthsart() {
	return ecyt_monthsart;
}
public void setEcyt_monthsart(String ecyt_monthsart) {
	this.ecyt_monthsart = ecyt_monthsart;
}
public String getEcyt_monthend() {
	return ecyt_monthend;
}
public void setEcyt_monthend(String ecyt_monthend) {
	this.ecyt_monthend = ecyt_monthend;
}
public Integer getEcyt_state() {
	return ecyt_state;
}
public void setEcyt_state(Integer ecyt_state) {
	this.ecyt_state = ecyt_state;
}
public Integer getCoab_id() {
	return coab_id;
}
public void setCoab_id(Integer coab_id) {
	this.coab_id = coab_id;
}
public Integer getEcyt_single() {
	return ecyt_single;
}
public void setEcyt_single(Integer ecyt_single) {
	this.ecyt_single = ecyt_single;
}
public String getEcyt_addname() {
	return ecyt_addname;
}
public void setEcyt_addname(String ecyt_addname) {
	this.ecyt_addname = ecyt_addname;
}

public Integer getEcyt_ylao() {
	return ecyt_ylao;
}
public void setEcyt_ylao(Integer ecyt_ylao) {
	this.ecyt_ylao = ecyt_ylao;
}
public Integer getEcyt_yliao() {
	return ecyt_yliao;
}
public void setEcyt_yliao(Integer ecyt_yliao) {
	this.ecyt_yliao = ecyt_yliao;
}
public Integer getEcyt_gshang() {
	return ecyt_gshang;
}
public void setEcyt_gshang(Integer ecyt_gshang) {
	this.ecyt_gshang = ecyt_gshang;
}
public Integer getEcyt_sye() {
	return ecyt_sye;
}
public void setEcyt_sye(Integer ecyt_sye) {
	this.ecyt_sye = ecyt_sye;
}
public Integer getEcyt_gjj() {
	return ecyt_gjj;
}
public void setEcyt_gjj(Integer ecyt_gjj) {
	this.ecyt_gjj = ecyt_gjj;
}
public Integer getEcyt_bcgjj() {
	return ecyt_bcgjj;
}
public void setEcyt_bcgjj(Integer ecyt_bcgjj) {
	this.ecyt_bcgjj = ecyt_bcgjj;
}
public Integer getEcyt_syu() {
	return ecyt_syu;
}
public void setEcyt_syu(Integer ecyt_syu) {
	this.ecyt_syu = ecyt_syu;
}
public String getEcyt_remark() {
	return ecyt_remark;
}
public void setEcyt_remark(String ecyt_remark) {
	this.ecyt_remark = ecyt_remark;
}






public Date getEcyt_ylaotime() {
	return ecyt_ylaotime;
}
public void setEcyt_ylaotime(Date ecyt_ylaotime) {
	this.ecyt_ylaotime = ecyt_ylaotime;
}
public String getEcyt_modname() {
	return ecyt_modname;
}
public void setEcyt_modname(String ecyt_modname) {
	this.ecyt_modname = ecyt_modname;
}
public EmCommissionyearchangetitleModel() {
	super();
}
public Date getEcyt_addtime() {
	return ecyt_addtime;
}
public void setEcyt_addtime(Date ecyt_addtime) {
	this.ecyt_addtime = ecyt_addtime;
}
public Date getEcyt_yliaotime() {
	return ecyt_yliaotime;
}
public void setEcyt_yliaotime(Date ecyt_yliaotime) {
	this.ecyt_yliaotime = ecyt_yliaotime;
}
public Date getEcyt_gshangtime() {
	return ecyt_gshangtime;
}
public void setEcyt_gshangtime(Date ecyt_gshangtime) {
	this.ecyt_gshangtime = ecyt_gshangtime;
}
public Date getEcyt_syetime() {
	return ecyt_syetime;
}
public void setEcyt_syetime(Date ecyt_syetime) {
	this.ecyt_syetime = ecyt_syetime;
}
public Date getEcyt_gjjtime() {
	return ecyt_gjjtime;
}
public void setEcyt_gjjtime(Date ecyt_gjjtime) {
	this.ecyt_gjjtime = ecyt_gjjtime;
}
public Date getEcyt_bcgjjtime() {
	return ecyt_bcgjjtime;
}
public void setEcyt_bcgjjtime(Date ecyt_bcgjjtime) {
	this.ecyt_bcgjjtime = ecyt_bcgjjtime;
}
public Date getEcyt_syutime() {
	return ecyt_syutime;
}
public void setEcyt_syutime(Date ecyt_syutime) {
	this.ecyt_syutime = ecyt_syutime;
}
public Date getEcyt_modtime() {
	return ecyt_modtime;
}
public void setEcyt_modtime(Date ecyt_modtime) {
	this.ecyt_modtime = ecyt_modtime;
}


}
