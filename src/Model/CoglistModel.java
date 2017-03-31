package Model;

import java.math.BigDecimal;
import java.util.Date;

public class CoglistModel {
	// / cgli_id
	private Integer cgli_id;
	// / gid
	private Integer gid;
	private String emba_name;
	private String emba_idcard;
	// / cid
	private Integer cid;
	// / cgli_coli_id
	private Integer cgli_coli_id;
	// / cgli_startdate
	private Integer cgli_startdate;
	private Date startDate;
	// / cgli_stopdate
	private Integer cgli_stopdate;
	private Date stopDate;
	// / cgli_startdate2
	private Integer cgli_startdate2;
	private Date startDate2;
	// / cgli_state
	private Integer cgli_state;
	// / cgli_addname
	private String cgli_addname;
	// / cgli_addtime
	private String cgli_addtime;
	// / cgli_modname
	private String cgli_modname;
	// / cgli_modtime
	private String cgli_modtime;
	// / cgli_outmoney
	private BigDecimal cgli_outmoney;

	private String coco_compacttype;
	private String coco_shebao;

	private String coco_house;
	private Integer gjjlastday;
	private Integer gjjzzlastday;
	private Integer gjjOnAir;

	private Integer coco_housefee;

	private String coco_houseid;

	private String coco_cpp;

	private String coco_opp;

	private Integer cgli_tapr_id;

	private Integer cgli_group_id;

	private Integer cgli_group_count;

	private Integer coli_coof_id;
	private Integer coli_copr_id;
	private String coli_pclass;
	private String city;
	private BigDecimal coli_fee2;
	private Integer coli_group_id;
	private Integer coli_group_count;
	private String coli_cpfc_name;
	private Integer coli_parid;
	private Integer copc_id;

	private String coco_compactid;
	private Integer coof_id;
	private String coof_name;
	private String coli_name;
	private String copc_name;
	private boolean checked;
	private Integer dataState; // 数据状态,1:stopdate为空;2:所属月份在起始和终止收费之间(包含已停)

	private String coli_flpaykind;
	private String coli_rspaykind;
	private String coli_rsinvoice;
	private String coli_hjpaykind;
	private String coli_hjinvoice;

	private String jc;// 公积金缴存银行

	private Integer sort;

	public Integer getCgli_id() {
		return cgli_id;
	}

	public void setCgli_id(Integer cgli_id) {
		this.cgli_id = cgli_id;
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

	public Integer getCgli_coli_id() {
		return cgli_coli_id;
	}

	public void setCgli_coli_id(Integer cgli_coli_id) {
		this.cgli_coli_id = cgli_coli_id;
	}

	public Integer getCgli_startdate() {
		return cgli_startdate;
	}

	public void setCgli_startdate(Integer cgli_startdate) {
		this.cgli_startdate = cgli_startdate;
	}

	public Integer getCgli_stopdate() {
		return cgli_stopdate;
	}

	public void setCgli_stopdate(Integer cgli_stopdate) {
		this.cgli_stopdate = cgli_stopdate;
	}

	public Integer getCgli_startdate2() {
		return cgli_startdate2;
	}

	public void setCgli_startdate2(Integer cgli_startdate2) {
		this.cgli_startdate2 = cgli_startdate2;
	}

	public Integer getCgli_state() {
		return cgli_state;
	}

	public void setCgli_state(Integer cgli_state) {
		this.cgli_state = cgli_state;
	}

	public String getCgli_addname() {
		return cgli_addname;
	}

	public void setCgli_addname(String cgli_addname) {
		this.cgli_addname = cgli_addname;
	}

	public String getCgli_addtime() {
		return cgli_addtime;
	}

	public void setCgli_addtime(String cgli_addtime) {
		this.cgli_addtime = cgli_addtime;
	}

	public String getCgli_modname() {
		return cgli_modname;
	}

	public void setCgli_modname(String cgli_modname) {
		this.cgli_modname = cgli_modname;
	}

	public String getCgli_modtime() {
		return cgli_modtime;
	}

	public void setCgli_modtime(String cgli_modtime) {
		this.cgli_modtime = cgli_modtime;
	}

	public BigDecimal getCgli_outmoney() {
		return cgli_outmoney;
	}

	public void setCgli_outmoney(BigDecimal cgli_outmoney) {
		this.cgli_outmoney = cgli_outmoney;
	}

	public String getCoco_house() {
		return coco_house;
	}

	public void setCoco_house(String coco_house) {
		this.coco_house = coco_house;
	}

	public Integer getCoco_housefee() {
		return coco_housefee;
	}

	public void setCoco_housefee(Integer coco_housefee) {
		this.coco_housefee = coco_housefee;
	}

	public String getCoco_houseid() {
		return coco_houseid;
	}

	public void setCoco_houseid(String coco_houseid) {
		this.coco_houseid = coco_houseid;
	}

	public String getCoco_cpp() {
		return coco_cpp;
	}

	public void setCoco_cpp(String coco_cpp) {
		this.coco_cpp = coco_cpp;
	}

	public String getCoco_opp() {
		return coco_opp;
	}

	public void setCoco_opp(String coco_opp) {
		this.coco_opp = coco_opp;
	}

	public Integer getCgli_tapr_id() {
		return cgli_tapr_id;
	}

	public void setCgli_tapr_id(Integer cgli_tapr_id) {
		this.cgli_tapr_id = cgli_tapr_id;
	}

	public Integer getCgli_group_id() {
		return cgli_group_id;
	}

	public void setCgli_group_id(Integer cgli_group_id) {
		this.cgli_group_id = cgli_group_id;
	}

	public Integer getCgli_group_count() {
		return cgli_group_count;
	}

	public void setCgli_group_count(Integer cgli_group_count) {
		this.cgli_group_count = cgli_group_count;
	}

	public Integer getColi_coof_id() {
		return coli_coof_id;
	}

	public void setColi_coof_id(Integer coli_coof_id) {
		this.coli_coof_id = coli_coof_id;
	}

	public String getColi_name() {
		return coli_name;
	}

	public void setColi_name(String coli_name) {
		this.coli_name = coli_name;
	}

	public String getCoco_shebao() {
		return coco_shebao;
	}

	public void setCoco_shebao(String coco_shebao) {
		this.coco_shebao = coco_shebao;
	}

	public String getCoco_compactid() {
		return coco_compactid;
	}

	public void setCoco_compactid(String coco_compactid) {
		this.coco_compactid = coco_compactid;
	}

	public String getCoof_name() {
		return coof_name;
	}

	public void setCoof_name(String coof_name) {
		this.coof_name = coof_name;
	}

	public String getCopc_name() {
		return copc_name;
	}

	public void setCopc_name(String copc_name) {
		this.copc_name = copc_name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStopDate() {
		return stopDate;
	}

	public void setStopDate(Date stopDate) {
		this.stopDate = stopDate;
	}

	public Date getStartDate2() {
		return startDate2;
	}

	public void setStartDate2(Date startDate2) {
		this.startDate2 = startDate2;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getEmba_name() {
		return emba_name;
	}

	public void setEmba_name(String emba_name) {
		this.emba_name = emba_name;
	}

	public Integer getDataState() {
		return dataState;
	}

	public void setDataState(Integer dataState) {
		this.dataState = dataState;
	}

	public String getColi_flpaykind() {
		return coli_flpaykind;
	}

	public void setColi_flpaykind(String coli_flpaykind) {
		this.coli_flpaykind = coli_flpaykind;
	}

	public String getColi_rspaykind() {
		return coli_rspaykind;
	}

	public void setColi_rspaykind(String coli_rspaykind) {
		this.coli_rspaykind = coli_rspaykind;
	}

	public String getColi_rsinvoice() {
		return coli_rsinvoice;
	}

	public void setColi_rsinvoice(String coli_rsinvoice) {
		this.coli_rsinvoice = coli_rsinvoice;
	}

	public String getColi_hjpaykind() {
		return coli_hjpaykind;
	}

	public void setColi_hjpaykind(String coli_hjpaykind) {
		this.coli_hjpaykind = coli_hjpaykind;
	}

	public String getColi_hjinvoice() {
		return coli_hjinvoice;
	}

	public void setColi_hjinvoice(String coli_hjinvoice) {
		this.coli_hjinvoice = coli_hjinvoice;
	}

	public Integer getCoof_id() {
		return coof_id;
	}

	public void setCoof_id(Integer coof_id) {
		this.coof_id = coof_id;
	}

	public Integer getColi_copr_id() {
		return coli_copr_id;
	}

	public void setColi_copr_id(Integer coli_copr_id) {
		this.coli_copr_id = coli_copr_id;
	}

	public String getColi_pclass() {
		return coli_pclass;
	}

	public void setColi_pclass(String coli_pclass) {
		this.coli_pclass = coli_pclass;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public BigDecimal getColi_fee2() {
		return coli_fee2;
	}

	public void setColi_fee2(BigDecimal coli_fee2) {
		this.coli_fee2 = coli_fee2;
	}

	public String getCoco_compacttype() {
		return coco_compacttype;
	}

	public void setCoco_compacttype(String coco_compacttype) {
		this.coco_compacttype = coco_compacttype;
	}

	public Integer getColi_group_id() {
		return coli_group_id;
	}

	public void setColi_group_id(Integer coli_group_id) {
		this.coli_group_id = coli_group_id;
	}

	public Integer getColi_group_count() {
		return coli_group_count;
	}

	public void setColi_group_count(Integer coli_group_count) {
		this.coli_group_count = coli_group_count;
	}

	public String getColi_cpfc_name() {
		return coli_cpfc_name;
	}

	public void setColi_cpfc_name(String coli_cpfc_name) {
		this.coli_cpfc_name = coli_cpfc_name;
	}

	public Integer getColi_parid() {
		return coli_parid;
	}

	public void setColi_parid(Integer coli_parid) {
		this.coli_parid = coli_parid;
	}

	public Integer getCopc_id() {
		return copc_id;
	}

	public void setCopc_id(Integer copc_id) {
		this.copc_id = copc_id;
	}

	public String getEmba_idcard() {
		return emba_idcard;
	}

	public void setEmba_idcard(String emba_idcard) {
		this.emba_idcard = emba_idcard;
	}

	public Integer getGjjlastday() {
		return gjjlastday;
	}

	public void setGjjlastday(Integer gjjlastday) {
		this.gjjlastday = gjjlastday;
	}

	public Integer getGjjOnAir() {
		return gjjOnAir;
	}

	public void setGjjOnAir(Integer gjjOnAir) {
		this.gjjOnAir = gjjOnAir;
	}

	public Integer getGjjzzlastday() {
		return gjjzzlastday;
	}

	public void setGjjzzlastday(Integer gjjzzlastday) {
		this.gjjzzlastday = gjjzzlastday;
	}

	public String getJc() {
		return jc;
	}

	public void setJc(String jc) {
		this.jc = jc;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
