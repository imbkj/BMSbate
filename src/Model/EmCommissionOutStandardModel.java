package Model;

import java.math.BigDecimal;
import java.util.Date;

public class EmCommissionOutStandardModel {
	// ecos_id
	private Integer ecos_id;
	// cid
	private Integer cid;
	// ownmonth
	private String ownmonth;
	// 标准名称
	private String ecos_name;
	// 地区机构id
	private Integer ecos_cabc_id;
	// 服务费
	private BigDecimal ecos_service_fee;
	// 档案费
	private BigDecimal ecos_archvie_fee;
	// 档案费支付方式
	private String ecos_archvie_feetype;
	// 社保账户类型
	private String ecos_shebao_zhtype;
	// 社保费用支付方式
	private String ecos_shebao_feetype;
	// 公积金账户类型
	private String ecos_gjj_zhtype;
	// 公积金费用支付方式
	private String ecos_gjj_feetype;
	// 备注
	private String ecos_remark;
	// ecos_laststate
	private Integer ecos_laststate;
	// ecos_state
	private Integer ecos_state;
	// 使用状态 0:历史数据 1:使用中数据
	private Integer ecos_usestate;
	// 历史数据主键id
	private Integer ecos_history_id;
	// ecos_addname
	private String ecos_addname;
	// ecos_addtime
	private Date ecos_addtime;
	private String ecos_addtime1;
	// ecos_tapr_id
	private Integer ecos_tapr_id;
	
	private String wtss_laborcontract;
	
	private String wtss_archives;

	// id
	private Integer id;
	// stateid
	private Integer stateid;
	// statename
	private String statename;
	// operate
	private String operate;
	// type
	private Integer type;
	// typename
	private String typename;
	// state
	private Integer state;
	
	private String fee_title;

	// eosr_id
	private Integer eosr_id;
	// eosr_stateid
	private Integer eosr_stateid;
	// eosr_daid
	private Integer eosr_daid;
	// eosr_addname
	private String eosr_addname;
	// eosr_addtime
	private Date eosr_addtime;
	// eosr_statetime
	private Date eosr_statetime;
	// eosr_remark
	private String eosr_remark;

	// ecop_id
	private Integer ecop_id;
	// 委托标准表id
	private Integer ecop_ecos_id;
	// 产品类型id
	private Integer ecop_coli_id;
	// ecop_state
	private Integer ecop_state;
	
	private Integer wtot_feeid;

	private String coba_shortname;
	private String coba_company;
	private String city;
	private Integer ppc_id;
	private Integer cabc_id;
	private String coli_name;
	private String coli_content;
	private BigDecimal coli_fee;
	// 服务费收入
	private BigDecimal service_cost;
	private String coab_name;

	private Integer count;

	public Integer getEcos_id() {
		return ecos_id;
	}

	public void setEcos_id(Integer ecos_id) {
		this.ecos_id = ecos_id;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(String ownmonth) {
		this.ownmonth = ownmonth;
	}

	public String getEcos_name() {
		return ecos_name;
	}

	public void setEcos_name(String ecos_name) {
		this.ecos_name = ecos_name;
	}

	public Integer getEcos_cabc_id() {
		return ecos_cabc_id;
	}

	public void setEcos_cabc_id(Integer ecos_cabc_id) {
		this.ecos_cabc_id = ecos_cabc_id;
	}

	public BigDecimal getEcos_service_fee() {
		return ecos_service_fee;
	}

	public void setEcos_service_fee(BigDecimal ecos_service_fee) {
		this.ecos_service_fee = ecos_service_fee == null ? new BigDecimal(0)
				: ecos_service_fee.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getEcos_archvie_fee() {
		return ecos_archvie_fee;
	}

	public void setEcos_archvie_fee(BigDecimal ecos_archvie_fee) {
		this.ecos_archvie_fee = ecos_archvie_fee == null ? new BigDecimal(0)
				: ecos_archvie_fee.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public String getEcos_shebao_zhtype() {
		return ecos_shebao_zhtype;
	}

	public void setEcos_shebao_zhtype(String ecos_shebao_zhtype) {
		this.ecos_shebao_zhtype = ecos_shebao_zhtype;
	}

	public String getEcos_shebao_feetype() {
		return ecos_shebao_feetype;
	}

	public void setEcos_shebao_feetype(String ecos_shebao_feetype) {
		this.ecos_shebao_feetype = ecos_shebao_feetype;
	}

	public String getEcos_gjj_zhtype() {
		return ecos_gjj_zhtype;
	}

	public void setEcos_gjj_zhtype(String ecos_gjj_zhtype) {
		this.ecos_gjj_zhtype = ecos_gjj_zhtype;
	}

	public String getEcos_gjj_feetype() {
		return ecos_gjj_feetype;
	}

	public void setEcos_gjj_feetype(String ecos_gjj_feetype) {
		this.ecos_gjj_feetype = ecos_gjj_feetype;
	}

	public String getEcos_remark() {
		return ecos_remark;
	}

	public void setEcos_remark(String ecos_remark) {
		this.ecos_remark = ecos_remark;
	}

	public Integer getEcos_laststate() {
		return ecos_laststate;
	}

	public void setEcos_laststate(Integer ecos_laststate) {
		this.ecos_laststate = ecos_laststate;
	}

	public Integer getEcos_state() {
		return ecos_state;
	}

	public void setEcos_state(Integer ecos_state) {
		this.ecos_state = ecos_state;
	}

	public Integer getEcos_usestate() {
		return ecos_usestate;
	}

	public void setEcos_usestate(Integer ecos_usestate) {
		this.ecos_usestate = ecos_usestate;
	}

	public Integer getEcos_history_id() {
		return ecos_history_id;
	}

	public void setEcos_history_id(Integer ecos_history_id) {
		this.ecos_history_id = ecos_history_id;
	}

	public String getEcos_addname() {
		return ecos_addname;
	}

	public void setEcos_addname(String ecos_addname) {
		this.ecos_addname = ecos_addname;
	}

	public Date getEcos_addtime() {
		return ecos_addtime;
	}

	public void setEcos_addtime(Date ecos_addtime) {
		this.ecos_addtime = ecos_addtime;
	}

	public Integer getEcos_tapr_id() {
		return ecos_tapr_id;
	}

	public void setEcos_tapr_id(Integer ecos_tapr_id) {
		this.ecos_tapr_id = ecos_tapr_id;
	}

	public Integer getEcop_id() {
		return ecop_id;
	}

	public void setEcop_id(Integer ecop_id) {
		this.ecop_id = ecop_id;
	}

	public Integer getEcop_ecos_id() {
		return ecop_ecos_id;
	}

	public void setEcop_ecos_id(Integer ecop_ecos_id) {
		this.ecop_ecos_id = ecop_ecos_id;
	}

	public Integer getEcop_state() {
		return ecop_state;
	}

	public void setEcop_state(Integer ecop_state) {
		this.ecop_state = ecop_state;
	}

	public String getCoba_shortname() {
		return coba_shortname;
	}

	public void setCoba_shortname(String coba_shortname) {
		this.coba_shortname = coba_shortname;
	}

	public String getCoba_company() {
		return coba_company;
	}

	public void setCoba_company(String coba_company) {
		this.coba_company = coba_company;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStateid() {
		return stateid;
	}

	public void setStateid(Integer stateid) {
		this.stateid = stateid;
	}

	public String getStatename() {
		return statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getEosr_id() {
		return eosr_id;
	}

	public void setEosr_id(Integer eosr_id) {
		this.eosr_id = eosr_id;
	}

	public Integer getEosr_stateid() {
		return eosr_stateid;
	}

	public void setEosr_stateid(Integer eosr_stateid) {
		this.eosr_stateid = eosr_stateid;
	}

	public Integer getEosr_daid() {
		return eosr_daid;
	}

	public void setEosr_daid(Integer eosr_daid) {
		this.eosr_daid = eosr_daid;
	}

	public String getEosr_addname() {
		return eosr_addname;
	}

	public void setEosr_addname(String eosr_addname) {
		this.eosr_addname = eosr_addname;
	}

	public Date getEosr_addtime() {
		return eosr_addtime;
	}

	public void setEosr_addtime(Date eosr_addtime) {
		this.eosr_addtime = eosr_addtime;
	}

	public Date getEosr_statetime() {
		return eosr_statetime;
	}

	public void setEosr_statetime(Date eosr_statetime) {
		this.eosr_statetime = eosr_statetime;
	}

	public String getEosr_remark() {
		return eosr_remark;
	}

	public void setEosr_remark(String eosr_remark) {
		this.eosr_remark = eosr_remark;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getEcop_coli_id() {
		return ecop_coli_id;
	}

	public void setEcop_coli_id(Integer ecop_coli_id) {
		this.ecop_coli_id = ecop_coli_id;
	}

	public Integer getCabc_id() {
		return cabc_id;
	}

	public void setCabc_id(Integer cabc_id) {
		this.cabc_id = cabc_id;
	}

	public BigDecimal getService_cost() {
		return service_cost == null ? new BigDecimal(0) : service_cost
				.setScale(2);
	}

	public void setService_cost(BigDecimal service_cost) {
		this.service_cost = service_cost == null ? new BigDecimal(0)
				: service_cost.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public Integer getPpc_id() {
		return ppc_id;
	}

	public void setPpc_id(Integer ppc_id) {
		this.ppc_id = ppc_id;
	}

	public String getCoab_name() {
		return coab_name;
	}

	public void setCoab_name(String coab_name) {
		this.coab_name = coab_name;
	}

	public String getEcos_addtime1() {
		return ecos_addtime1;
	}

	public void setEcos_addtime1(String ecos_addtime1) {
		this.ecos_addtime1 = ecos_addtime1;
	}

	public String getColi_name() {
		return coli_name;
	}

	public void setColi_name(String coli_name) {
		this.coli_name = coli_name;
	}

	public String getColi_content() {
		return coli_content;
	}

	public void setColi_content(String coli_content) {
		this.coli_content = coli_content;
	}

	public BigDecimal getColi_fee() {
		return coli_fee;
	}

	public void setColi_fee(BigDecimal coli_fee) {
		this.coli_fee = coli_fee;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getEcos_archvie_feetype() {
		return ecos_archvie_feetype;
	}

	public void setEcos_archvie_feetype(String ecos_archvie_feetype) {
		this.ecos_archvie_feetype = ecos_archvie_feetype;
	}

	public String getFee_title() {
		return fee_title;
	}

	public void setFee_title(String fee_title) {
		this.fee_title = fee_title;
	}

	public Integer getWtot_feeid() {
		return wtot_feeid;
	}

	public void setWtot_feeid(Integer wtot_feeid) {
		this.wtot_feeid = wtot_feeid;
	}

	public String getWtss_laborcontract() {
		return wtss_laborcontract;
	}

	public void setWtss_laborcontract(String wtss_laborcontract) {
		this.wtss_laborcontract = wtss_laborcontract;
	}

	public String getWtss_archives() {
		return wtss_archives;
	}

	public void setWtss_archives(String wtss_archives) {
		this.wtss_archives = wtss_archives;
	}
	
	
}
