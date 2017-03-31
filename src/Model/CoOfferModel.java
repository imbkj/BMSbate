package Model;

import java.math.BigDecimal;
import java.util.Date;

public class CoOfferModel {

	private Integer coof_id;
	private Integer coof_id1;
	private String coof_name;
	private String coof_name1;
	private Integer coof_cpct_id;
	private String coof_quotemode;
	private Date coof_quotetime;
	private Integer coof_state;
	private String coof_addname;
	private Date coof_addtime;
	private String addtime;
	private String coof_modname;
	private Date coof_modtime;
	private String coof_remark;
	private Integer coof_tali_id;
	private Integer coof_register;
	private Integer coof_cola_id;
	private Integer coof_coco_id;
	private BigDecimal fee;
	private String coli_fee;
	private Integer row;
	private Integer coof_min;
	private Integer coof_max;
	private String coof_gm;
	private BigDecimal coof_sum;
	private String coof_sumInfo;
	private Integer cid;
	private String cpct_shortname;
	private String cpct_name;
	private String cpct_name1;
	private String coof_tooltip;
	private String coof_compacttype;
	private Integer gid;
	private String emba_name;
	private Integer coli_id;
	private String coli_name;
	private Integer cgli_id;
	private String cgli_startdate;
	private String cgli_startdate2;
	private String cgli_stopdate;
	private String city;
	private String copc_name;
	private Integer copc_id;
	private String coco_compactid;
	private String coco_shebao;
	private String coco_house;
	private Integer coof_tarp_id;
	private String coof_auditing_name;
	private String coof_auditing_time;
	private String coof_backcase;
	private String coof_backname;
	private String coof_backtime;
	private String coof_auditaddname;
	private String coof_auditaddtime;
	private boolean checked;

	public String getCoof_name() {
		return coof_name;
	}

	public void setCoof_name(String coof_name) {
		this.coof_name = coof_name;
	}

	public String getCoof_quotemode() {
		return coof_quotemode;
	}

	public void setCoof_quotemode(String coof_quotemode) {
		this.coof_quotemode = coof_quotemode;
	}

	public Date getCoof_quotetime() {
		return coof_quotetime;
	}

	public void setCoof_quotetime(Date coof_quotetime) {
		this.coof_quotetime = coof_quotetime;
	}

	public Integer getCoof_state() {
		return coof_state;
	}

	public void setCoof_state(Integer coof_state) {
		this.coof_state = coof_state;
	}

	public String getCoof_addname() {
		return coof_addname;
	}

	public void setCoof_addname(String coof_addname) {
		this.coof_addname = coof_addname;
	}

	public Date getCoof_addtime() {
		return coof_addtime;
	}

	public void setCoof_addtime(Date coof_addtime) {
		this.coof_addtime = coof_addtime;
	}

	public String getCoof_modname() {
		return coof_modname;
	}

	public void setCoof_modname(String coof_modname) {
		this.coof_modname = coof_modname;
	}

	public Date getCoof_modtime() {
		return coof_modtime;
	}

	public void setCoof_modtime(Date coof_modtime) {
		this.coof_modtime = coof_modtime;
	}

	public String getCoof_remark() {
		return coof_remark;
	}

	public void setCoof_remark(String coof_remark) {
		this.coof_remark = coof_remark;
	}

	public Integer getCoof_tali_id() {
		return coof_tali_id;
	}

	public void setCoof_tali_id(Integer coof_tali_id) {
		this.coof_tali_id = coof_tali_id;
	}

	public Integer getCoof_register() {
		return coof_register;
	}

	public void setCoof_register(Integer coof_register) {
		this.coof_register = coof_register;
	}

	public Integer getCoof_cola_id() {
		return coof_cola_id;
	}

	public void setCoof_cola_id(Integer coof_cola_id) {
		this.coof_cola_id = coof_cola_id;
	}

	public Integer getRow() {
		return row;
	}

	public void setRow(Integer row) {
		this.row = row;
	}

	public Integer getCoof_id() {
		return coof_id;
	}

	public void setCoof_id(Integer coof_id) {
		this.coof_id = coof_id;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee == null ? BigDecimal.ZERO : fee.setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	public Integer getCoof_min() {
		return coof_min;
	}

	public void setCoof_min(Integer coof_min) {
		this.coof_min = coof_min;
	}

	public Integer getCoof_max() {
		return coof_max;
	}

	public void setCoof_max(Integer coof_max) {
		this.coof_max = coof_max;
	}

	public String getCoof_gm() {
		return coof_gm;
	}

	public void setCoof_gm(String coof_gm) {
		this.coof_gm = coof_gm;
	}

	public Integer getCoof_coco_id() {
		return coof_coco_id;
	}

	public void setCoof_coco_id(Integer coof_coco_id) {
		this.coof_coco_id = coof_coco_id;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public BigDecimal getCoof_sum() {
		return coof_sum;
	}

	public void setCoof_sum(BigDecimal coof_sum) {
		this.coof_sum = coof_sum == null ? BigDecimal.ZERO : coof_sum.setScale(
				2, BigDecimal.ROUND_HALF_UP);
	}

	public String getCoof_name1() {
		return coof_name1;
	}

	public void setCoof_name1(String coof_name1) {
		this.coof_name1 = coof_name1;
	}

	public Integer getCoof_cpct_id() {
		return coof_cpct_id;
	}

	public void setCoof_cpct_id(Integer coof_cpct_id) {
		this.coof_cpct_id = coof_cpct_id;
	}

	public String getCpct_shortname() {
		return cpct_shortname;
	}

	public void setCpct_shortname(String cpct_shortname) {
		this.cpct_shortname = cpct_shortname;
	}

	public String getCpct_name() {
		return cpct_name;
	}

	public void setCpct_name(String cpct_name) {
		this.cpct_name = cpct_name;
	}

	public String getCpct_name1() {
		return cpct_name1;
	}

	public void setCpct_name1(String cpct_name1) {
		this.cpct_name1 = cpct_name1;
	}

	public Integer getCoof_id1() {
		return coof_id1;
	}

	public void setCoof_id1(Integer coof_id1) {
		this.coof_id1 = coof_id1;
	}

	public String getCoof_tooltip() {
		return coof_tooltip;
	}

	public void setCoof_tooltip(String coof_tooltip) {
		this.coof_tooltip = coof_tooltip;
	}

	public String getCoof_compacttype() {
		return coof_compacttype;
	}

	public void setCoof_compacttype(String coof_compacttype) {
		this.coof_compacttype = coof_compacttype;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public String getEmba_name() {
		return emba_name;
	}

	public void setEmba_name(String emba_name) {
		this.emba_name = emba_name;
	}

	public Integer getColi_id() {
		return coli_id;
	}

	public void setColi_id(Integer coli_id) {
		this.coli_id = coli_id;
	}

	public String getColi_name() {
		return coli_name;
	}

	public void setColi_name(String coli_name) {
		this.coli_name = coli_name;
	}

	public Integer getCgli_id() {
		return cgli_id;
	}

	public void setCgli_id(Integer cgli_id) {
		this.cgli_id = cgli_id;
	}

	public String getCgli_startdate() {
		return cgli_startdate;
	}

	public void setCgli_startdate(String cgli_startdate) {
		this.cgli_startdate = cgli_startdate;
	}

	public String getCgli_startdate2() {
		return cgli_startdate2;
	}

	public void setCgli_startdate2(String cgli_startdate2) {
		this.cgli_startdate2 = cgli_startdate2;
	}

	public String getCgli_stopdate() {
		return cgli_stopdate;
	}

	public void setCgli_stopdate(String cgli_stopdate) {
		this.cgli_stopdate = cgli_stopdate;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCopc_name() {
		return copc_name;
	}

	public void setCopc_name(String copc_name) {
		this.copc_name = copc_name;
	}

	public Integer getCopc_id() {
		return copc_id;
	}

	public void setCopc_id(Integer copc_id) {
		this.copc_id = copc_id;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getCoco_compactid() {
		return coco_compactid;
	}

	public void setCoco_compactid(String coco_compactid) {
		this.coco_compactid = coco_compactid;
	}

	public String getCoco_shebao() {
		return coco_shebao;
	}

	public void setCoco_shebao(String coco_shebao) {
		this.coco_shebao = coco_shebao;
	}

	public String getCoco_house() {
		return coco_house;
	}

	public void setCoco_house(String coco_house) {
		this.coco_house = coco_house;
	}

	public String getCoof_sumInfo() {
		return coof_sumInfo;
	}

	public void setCoof_sumInfo(String coof_sumInfo) {
		this.coof_sumInfo = coof_sumInfo;
	}

	public Integer getCoof_tarp_id() {
		return coof_tarp_id;
	}

	public void setCoof_tarp_id(Integer coof_tarp_id) {
		this.coof_tarp_id = coof_tarp_id;
	}

	public String getCoof_auditing_name() {
		return coof_auditing_name;
	}

	public void setCoof_auditing_name(String coof_auditing_name) {
		this.coof_auditing_name = coof_auditing_name;
	}

	public String getCoof_auditing_time() {
		return coof_auditing_time;
	}

	public void setCoof_auditing_time(String coof_auditing_time) {
		this.coof_auditing_time = coof_auditing_time;
	}

	public String getCoof_backcase() {
		return coof_backcase;
	}

	public void setCoof_backcase(String coof_backcase) {
		this.coof_backcase = coof_backcase;
	}

	public String getCoof_backname() {
		return coof_backname;
	}

	public void setCoof_backname(String coof_backname) {
		this.coof_backname = coof_backname;
	}

	public String getCoof_backtime() {
		return coof_backtime;
	}

	public void setCoof_backtime(String coof_backtime) {
		this.coof_backtime = coof_backtime;
	}

	public String getCoof_auditaddname() {
		return coof_auditaddname;
	}

	public void setCoof_auditaddname(String coof_auditaddname) {
		this.coof_auditaddname = coof_auditaddname;
	}

	public String getCoof_auditaddtime() {
		return coof_auditaddtime;
	}

	public void setCoof_auditaddtime(String coof_auditaddtime) {
		this.coof_auditaddtime = coof_auditaddtime;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getColi_fee() {
		return coli_fee;
	}

	public void setColi_fee(String coli_fee) {
		this.coli_fee = coli_fee;
	}

}
