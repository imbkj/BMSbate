package Model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class EmCommissionOutChangeModel implements Cloneable {
	// ecoc_id
	private Integer ecoc_id;
	// gid
	private Integer gid;
	// cid
	private Integer cid;
	// SocialInsurance表外键id
	private Integer ecoc_soin_id;
	// EmCommissionOutStandard表外键id
	// 当ecoc_addtype为一次性费用时,此项为CoAgencyBaseCityRel表外键id
	private Integer ecoc_ecos_id;
	// 变动类型:新增,调整,停缴,在职,离职,取消
	private String ecoc_addtype;
	// 状态编码
	private String ecoc_type;
	// ecoc_idcard
	private String ecoc_idcard;
	// ecoc_email
	private String ecoc_email;
	// ecoc_phone
	private String ecoc_phone;
	// ecoc_mobile
	private String ecoc_mobile;
	// 执行日
	private String ecoc_title_date;
	private Date title_date;
	// ecoc_in_date
	private Date ecoc_in_date;
	// ecoc_com_phone
	private String ecoc_com_phone;
	// ecoc_com_principal
	private String ecoc_com_principal;
	// ecoc_com_company
	private String ecoc_com_company;
	// 户籍
	private String ecoc_domicile;
	// 合同签订方
	private String ecoc_compact_jud;
	// 合同起始日期
	private String ecoc_compact_f;
	// 合同结束日期
	private String ecoc_compact_l;
	// 社保基数
	private BigDecimal ecoc_sb_base;
	// 公积金基数
	private BigDecimal ecoc_house_base;
	// 社保费用总和
	private BigDecimal ecoc_sb_sum;
	// 公积金费用总和
	private BigDecimal ecoc_gjj_sum;
	// 福利产品费用总和
	private BigDecimal ecoc_welfare_sum;
	// 服务费
	private BigDecimal ecoc_service_fee;
	// 档案费
	private BigDecimal ecoc_file_fee;
	// 费用总和
	private BigDecimal ecoc_sum;
	// ecoc_stop_date
	private Date ecoc_stop_date;
	// ecoc_stop_cause
	private String ecoc_stop_cause;
	// 取消时间
	private Date ecoc_cancel_date;
	// 取消原因
	private String ecoc_cancel_cause;
	// ecoc_laststate
	private Integer ecoc_laststate;
	// ecoc_state
	private Integer ecoc_state;
	// 客服
	private String ecoc_client;
	// ecoc_addname
	private String ecoc_addname;
	// ecoc_addtime
	private Date ecoc_addtime;
	private String ecoc_addtime1;
	// ecoc_remark
	private String ecoc_remark;
	private String ecoc_remark1;
	// ecoc_tapr_id
	private Integer ecoc_tapr_id;
	// 实际工资
	private BigDecimal ecoc_salary;
	// 社保个人总计
	private BigDecimal ecoc_sb_em_sum;
	// 社保企业总计
	private BigDecimal ecoc_sb_co_sum;
	// 公积金个人总计
	private BigDecimal ecoc_gjj_em_sum;
	// 公积金企业总计
	private BigDecimal ecoc_gjj_co_sum;
	private Integer ecoc_ecou_id;
	private String statename;
	private String ecos_name;
	private String ecoc_name;
	private String fee_title;
	// 取消的ecoc_id
	private Integer ecoc_cancel_id;
	// 补缴收费月份
	private String ecoc_bjmonth;
	// 是否发送系统短信
	private Boolean if_sendmessage;

	// 档案情况
	private String wtss_archives;
	// 社保情况
	private String wtss_shebaoco;
	private String wtss_shebaopayty;
	// 公积金情况
	private String wtss_gjjco;
	private String wtss_gjjpayty;
	// 用工情况
	private String wtss_employment;
	// 劳动合同版
	private String wtss_laborcontractbb;
	// 合同签订方
	private String wtss_laborcontract;
	// 员工性别
	private String emba_sex;
	// 公司性质
	private String coba_setuptype;

	private Integer sial_id;
	private String sial_execdate;
	private String soin_title;
	private Integer soin_id;
	private String emba_name;
	private String emba_idcard;
	private String coba_client;
	
	private String coba_shortname;
	private String coba_company;
	private String coab_name;
	private String city;
	private String type;
	private Integer ecos_cabc_id;
	private String remark;
	private String cali_fax;
	private String coab_company;
	private String addtime_y;
	private String addtime_m;
	private String addtime_d;
	
	private Integer coab_id;

	private boolean updateState = false;

	// 计数
	private Integer c1;
	private Integer c2;
	private Integer c3;
	private Integer c4;
	private Integer c5;
	private Integer c6;
	private Integer c7;
	private Integer c8;
	private String addtime;

	private Integer readstate;// 短信状态
	private String zfeofc_cp; // 住房公积金单位比例
	private String zfeofc_op; // 公积金个人比例
	private String sbownmonth; // 社保起始月
	private String gjjownmonth; // 公积金其实月
	private String ecoc_statestr; //变更状态
 
	
	
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			return null;
		}

	}
 
	public String getEmba_idcard() {
		return emba_idcard;
	}

	public void setEmba_idcard(String emba_idcard) {
		this.emba_idcard = emba_idcard;
	}

	public String getCoba_client() {
		return coba_client;
	}

	public void setCoba_client(String coba_client) {
		this.coba_client = coba_client;
	}

	private List<EmCommissionOutFeeDetailChangeModel> feeList;

	public String getSbownmonth() {
		return sbownmonth;
	}

	public void setSbownmonth(String sbownmonth) {
		this.sbownmonth = sbownmonth;
	}

	public String getGjjownmonth() {
		return gjjownmonth;
	}

	public String getEcoc_statestr() {
		return ecoc_statestr;
	}

	public void setEcoc_statestr(String ecoc_statestr) {
		this.ecoc_statestr = ecoc_statestr;
	}

	public void setGjjownmonth(String gjjownmonth) {
		this.gjjownmonth = gjjownmonth;
	}

	public String getZfeofc_cp() {
		return zfeofc_cp;
	}

	public void setZfeofc_cp(String zfeofc_cp) {
		this.zfeofc_cp = zfeofc_cp;
	}

	public String getZfeofc_op() {
		return zfeofc_op;
	}

	public void setZfeofc_op(String zfeofc_op) {
		this.zfeofc_op = zfeofc_op;
	}

	// 是否可以提交
	private Boolean if_submit;

	public final Integer getC1() {
		return c1;
	}

	public final Integer getC2() {
		return c2;
	}

	public final Integer getC3() {
		return c3;
	}

	public final Integer getC4() {
		return c4;
	}

	public final Integer getC5() {
		return c5;
	}

	public final void setC1(Integer c1) {
		this.c1 = c1;
	}

	public final void setC2(Integer c2) {
		this.c2 = c2;
	}

	public final void setC3(Integer c3) {
		this.c3 = c3;
	}

	public final void setC4(Integer c4) {
		this.c4 = c4;
	}

	public final void setC5(Integer c5) {
		this.c5 = c5;
	}

	public final Integer getC6() {
		return c6;
	}

	public final void setC6(Integer c6) {
		this.c6 = c6;
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

	public Integer getEcoc_id() {
		return ecoc_id;
	}

	public void setEcoc_id(Integer ecoc_id) {
		this.ecoc_id = ecoc_id;
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

	public Integer getEcoc_ecos_id() {
		return ecoc_ecos_id;
	}

	public void setEcoc_ecos_id(Integer ecoc_ecos_id) {
		this.ecoc_ecos_id = ecoc_ecos_id;
	}

	public String getEcoc_addtype() {
		return ecoc_addtype;
	}

	public void setEcoc_addtype(String ecoc_addtype) {
		this.ecoc_addtype = ecoc_addtype;
	}

	public String getEcoc_idcard() {
		return ecoc_idcard;
	}

	public void setEcoc_idcard(String ecoc_idcard) {
		this.ecoc_idcard = ecoc_idcard;
	}

	public String getEcoc_email() {
		return ecoc_email;
	}

	public void setEcoc_email(String ecoc_email) {
		this.ecoc_email = ecoc_email;
	}

	public String getEcoc_phone() {
		return ecoc_phone;
	}

	public void setEcoc_phone(String ecoc_phone) {
		this.ecoc_phone = ecoc_phone;
	}

	public String getEcoc_mobile() {
		return ecoc_mobile;
	}

	public void setEcoc_mobile(String ecoc_mobile) {
		this.ecoc_mobile = ecoc_mobile;
	}

	public String getEcoc_title_date() {
		return ecoc_title_date;
	}

	public void setEcoc_title_date(String ecoc_title_date) {
		this.ecoc_title_date = ecoc_title_date;
	}

	public Date getEcoc_in_date() {
		return ecoc_in_date;
	}

	public void setEcoc_in_date(Date ecoc_in_date) {
		this.ecoc_in_date = ecoc_in_date;
	}

	public String getEcoc_com_phone() {
		return ecoc_com_phone;
	}

	public void setEcoc_com_phone(String ecoc_com_phone) {
		this.ecoc_com_phone = ecoc_com_phone;
	}

	public String getEcoc_com_principal() {
		return ecoc_com_principal;
	}

	public void setEcoc_com_principal(String ecoc_com_principal) {
		this.ecoc_com_principal = ecoc_com_principal;
	}

	public String getEcoc_com_company() {
		return ecoc_com_company;
	}

	public void setEcoc_com_company(String ecoc_com_company) {
		this.ecoc_com_company = ecoc_com_company;
	}

	public String getEcoc_domicile() {
		return ecoc_domicile;
	}

	public void setEcoc_domicile(String ecoc_domicile) {
		this.ecoc_domicile = ecoc_domicile;
	}

	public String getEcoc_compact_jud() {
		return ecoc_compact_jud;
	}

	public void setEcoc_compact_jud(String ecoc_compact_jud) {
		this.ecoc_compact_jud = ecoc_compact_jud;
	}

	public String getEcoc_compact_f() {
		return ecoc_compact_f;
	}

	public void setEcoc_compact_f(String ecoc_compact_f) {
		this.ecoc_compact_f = ecoc_compact_f;
	}

	public String getEcoc_compact_l() {
		return ecoc_compact_l;
	}

	public void setEcoc_compact_l(String ecoc_compact_l) {
		this.ecoc_compact_l = ecoc_compact_l;
	}

	public BigDecimal getEcoc_sb_base() {
		return ecoc_sb_base;
	}

	public void setEcoc_sb_base(BigDecimal ecoc_sb_base) {
		this.ecoc_sb_base = ecoc_sb_base == null ? BigDecimal.ZERO
				: ecoc_sb_base.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getEcoc_house_base() {
		return ecoc_house_base;
	}

	public void setEcoc_house_base(BigDecimal ecoc_house_base) {
		this.ecoc_house_base = ecoc_house_base == null ? BigDecimal.ZERO
				: ecoc_house_base.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getEcoc_sb_sum() {
		return ecoc_sb_sum;
	}

	public void setEcoc_sb_sum(BigDecimal ecoc_sb_sum) {
		this.ecoc_sb_sum = ecoc_sb_sum == null ? BigDecimal.ZERO : ecoc_sb_sum
				.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getEcoc_gjj_sum() {
		return ecoc_gjj_sum;
	}

	public void setEcoc_gjj_sum(BigDecimal ecoc_gjj_sum) {
		this.ecoc_gjj_sum = ecoc_gjj_sum == null ? BigDecimal.ZERO
				: ecoc_gjj_sum.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getEcoc_welfare_sum() {
		return ecoc_welfare_sum;
	}

	public void setEcoc_welfare_sum(BigDecimal ecoc_welfare_sum) {
		this.ecoc_welfare_sum = ecoc_welfare_sum == null ? BigDecimal.ZERO
				: ecoc_welfare_sum.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getEcoc_service_fee() {
		return ecoc_service_fee;
	}

	public void setEcoc_service_fee(BigDecimal ecoc_service_fee) {
		this.ecoc_service_fee = ecoc_service_fee == null ? BigDecimal.ZERO
				: ecoc_service_fee.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getEcoc_file_fee() {
		return ecoc_file_fee;
	}

	public void setEcoc_file_fee(BigDecimal ecoc_file_fee) {
		this.ecoc_file_fee = ecoc_file_fee == null ? BigDecimal.ZERO
				: ecoc_file_fee.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getEcoc_sum() {
		return ecoc_sum;
	}

	public void setEcoc_sum(BigDecimal ecoc_sum) {
		this.ecoc_sum = ecoc_sum == null ? BigDecimal.ZERO : ecoc_sum.setScale(
				2, BigDecimal.ROUND_HALF_UP);
	}

	public Date getEcoc_stop_date() {
		return ecoc_stop_date;
	}

	public void setEcoc_stop_date(Date ecoc_stop_date) {
		this.ecoc_stop_date = ecoc_stop_date;
	}

	public String getEcoc_stop_cause() {
		return ecoc_stop_cause;
	}

	public void setEcoc_stop_cause(String ecoc_stop_cause) {
		this.ecoc_stop_cause = ecoc_stop_cause;
	}

	public String getEcoc_cancel_cause() {
		return ecoc_cancel_cause;
	}

	public void setEcoc_cancel_cause(String ecoc_cancel_cause) {
		this.ecoc_cancel_cause = ecoc_cancel_cause;
	}

	public Integer getEcoc_laststate() {
		return ecoc_laststate;
	}

	public void setEcoc_laststate(Integer ecoc_laststate) {
		this.ecoc_laststate = ecoc_laststate;
	}

	public Integer getEcoc_state() {
		return ecoc_state;
	}

	public void setEcoc_state(Integer ecoc_state) {
		this.ecoc_state = ecoc_state;
	}

	public String getEcoc_client() {
		return ecoc_client;
	}

	public void setEcoc_client(String ecoc_client) {
		this.ecoc_client = ecoc_client;
	}

	public String getEcoc_addname() {
		return ecoc_addname;
	}

	public void setEcoc_addname(String ecoc_addname) {
		this.ecoc_addname = ecoc_addname;
	}

	public Date getEcoc_addtime() {
		return ecoc_addtime;
	}

	public void setEcoc_addtime(Date ecoc_addtime) {
		this.ecoc_addtime = ecoc_addtime;
	}

	public String getEcoc_remark() {
		return ecoc_remark;
	}

	public void setEcoc_remark(String ecoc_remark) {
		this.ecoc_remark = ecoc_remark;
	}

	public Integer getEcoc_tapr_id() {
		return ecoc_tapr_id;
	}

	public void setEcoc_tapr_id(Integer ecoc_tapr_id) {
		this.ecoc_tapr_id = ecoc_tapr_id;
	}

	public BigDecimal getEcoc_salary() {
		return ecoc_salary;
	}

	public void setEcoc_salary(BigDecimal ecoc_salary) {
		this.ecoc_salary = ecoc_salary == null ? BigDecimal.ZERO : ecoc_salary
				.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public Integer getSial_id() {
		return sial_id;
	}

	public void setSial_id(Integer sial_id) {
		this.sial_id = sial_id;
	}

	public String getSoin_title() {
		return soin_title;
	}

	public void setSoin_title(String soin_title) {
		this.soin_title = soin_title;
	}

	public Integer getSoin_id() {
		return soin_id;
	}

	public void setSoin_id(Integer soin_id) {
		this.soin_id = soin_id;
	}

	public BigDecimal getEcoc_sb_em_sum() {
		return ecoc_sb_em_sum;
	}

	public void setEcoc_sb_em_sum(BigDecimal ecoc_sb_em_sum) {
		this.ecoc_sb_em_sum = ecoc_sb_em_sum == null ? BigDecimal.ZERO
				: ecoc_sb_em_sum.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getEcoc_sb_co_sum() {
		return ecoc_sb_co_sum;
	}

	public void setEcoc_sb_co_sum(BigDecimal ecoc_sb_co_sum) {
		this.ecoc_sb_co_sum = ecoc_sb_co_sum == null ? BigDecimal.ZERO
				: ecoc_sb_co_sum.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getEcoc_gjj_em_sum() {
		return ecoc_gjj_em_sum;
	}

	public void setEcoc_gjj_em_sum(BigDecimal ecoc_gjj_em_sum) {
		this.ecoc_gjj_em_sum = ecoc_gjj_em_sum == null ? BigDecimal.ZERO
				: ecoc_gjj_em_sum.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getEcoc_gjj_co_sum() {
		return ecoc_gjj_co_sum;
	}

	public void setEcoc_gjj_co_sum(BigDecimal ecoc_gjj_co_sum) {
		this.ecoc_gjj_co_sum = ecoc_gjj_co_sum == null ? BigDecimal.ZERO
				: ecoc_gjj_co_sum.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public Integer getEcoc_ecou_id() {
		return ecoc_ecou_id;
	}

	public void setEcoc_ecou_id(Integer ecoc_ecou_id) {
		this.ecoc_ecou_id = ecoc_ecou_id;
	}

	public String getEmba_name() {
		return emba_name;
	}

	public void setEmba_name(String emba_name) {
		this.emba_name = emba_name;
	}

	public String getEcoc_addtime1() {
		return ecoc_addtime1;
	}

	public void setEcoc_addtime1(String ecoc_addtime1) {
		this.ecoc_addtime1 = ecoc_addtime1;
	}

	public String getStatename() {
		return statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}

	public String getEcos_name() {
		return ecos_name;
	}

	public void setEcos_name(String ecos_name) {
		this.ecos_name = ecos_name;
	}

	public String getEcoc_remark1() {
		return ecoc_remark1;
	}

	public void setEcoc_remark1(String ecoc_remark1) {
		this.ecoc_remark1 = ecoc_remark1;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getEcoc_soin_id() {
		return ecoc_soin_id;
	}

	public void setEcoc_soin_id(Integer ecoc_soin_id) {
		this.ecoc_soin_id = ecoc_soin_id;
	}

	public Date getTitle_date() {
		return title_date;
	}

	public void setTitle_date(Date title_date) {
		this.title_date = title_date;
	}

	public List<EmCommissionOutFeeDetailChangeModel> getFeeList() {
		return feeList;
	}

	public void setFeeList(List<EmCommissionOutFeeDetailChangeModel> feeList) {
		this.feeList = feeList;
	}

	public String getSial_execdate() {
		return sial_execdate;
	}

	public void setSial_execdate(String sial_execdate) {
		this.sial_execdate = sial_execdate;
	}

	public String getEcoc_name() {
		return ecoc_name;
	}

	public void setEcoc_name(String ecoc_name) {
		this.ecoc_name = ecoc_name;
	}

	public Integer getEcos_cabc_id() {
		return ecos_cabc_id;
	}

	public void setEcos_cabc_id(Integer ecos_cabc_id) {
		this.ecos_cabc_id = ecos_cabc_id;
	}

	public String getEcoc_type() {
		return ecoc_type;
	}

	public void setEcoc_type(String ecoc_type) {
		this.ecoc_type = ecoc_type;
	}

	public Date getEcoc_cancel_date() {
		return ecoc_cancel_date;
	}

	public void setEcoc_cancel_date(Date ecoc_cancel_date) {
		this.ecoc_cancel_date = ecoc_cancel_date;
	}

	public Integer getEcoc_cancel_id() {
		return ecoc_cancel_id;
	}

	public void setEcoc_cancel_id(Integer ecoc_cancel_id) {
		this.ecoc_cancel_id = ecoc_cancel_id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getEcoc_bjmonth() {
		return ecoc_bjmonth;
	}

	public void setEcoc_bjmonth(String ecoc_bjmonth) {
		this.ecoc_bjmonth = ecoc_bjmonth;
	}

	public Boolean getIf_sendmessage() {
		return if_sendmessage;
	}

	public void setIf_sendmessage(Boolean if_sendmessage) {
		this.if_sendmessage = if_sendmessage;
	}

	public Boolean getIf_submit() {
		return if_submit;
	}

	public void setIf_submit(Boolean if_submit) {
		this.if_submit = if_submit;
	}

	public Integer getC7() {
		return c7;
	}

	public void setC7(Integer c7) {
		this.c7 = c7;
	}

	public Integer getC8() {
		return c8;
	}

	public void setC8(Integer c8) {
		this.c8 = c8;
	}

	public String getFee_title() {
		return fee_title;
	}

	public void setFee_title(String fee_title) {
		this.fee_title = fee_title;
	}

	public String getWtss_archives() {
		return wtss_archives;
	}

	public void setWtss_archives(String wtss_archives) {
		this.wtss_archives = wtss_archives;
	}

	public String getWtss_shebaoco() {
		return wtss_shebaoco;
	}

	public void setWtss_shebaoco(String wtss_shebaoco) {
		this.wtss_shebaoco = wtss_shebaoco;
	}

	public String getWtss_gjjco() {
		return wtss_gjjco;
	}

	public void setWtss_gjjco(String wtss_gjjco) {
		this.wtss_gjjco = wtss_gjjco;
	}

	public String getWtss_employment() {
		return wtss_employment;
	}

	public void setWtss_employment(String wtss_employment) {
		this.wtss_employment = wtss_employment;
	}

	public String getWtss_laborcontractbb() {
		return wtss_laborcontractbb;
	}

	public void setWtss_laborcontractbb(String wtss_laborcontractbb) {
		this.wtss_laborcontractbb = wtss_laborcontractbb;
	}

	public String getWtss_laborcontract() {
		return wtss_laborcontract;
	}

	public void setWtss_laborcontract(String wtss_laborcontract) {
		this.wtss_laborcontract = wtss_laborcontract;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getEmba_sex() {
		return emba_sex;
	}

	public void setEmba_sex(String emba_sex) {
		this.emba_sex = emba_sex;
	}

	public String getCoba_setuptype() {
		return coba_setuptype;
	}

	public void setCoba_setuptype(String coba_setuptype) {
		this.coba_setuptype = coba_setuptype;
	}

	public Integer getReadstate() {
		return readstate;
	}

	public void setReadstate(Integer readstate) {
		this.readstate = readstate;
	}

	public String getCali_fax() {
		return cali_fax;
	}

	public void setCali_fax(String cali_fax) {
		this.cali_fax = cali_fax;
	}

	public String getCoab_company() {
		return coab_company;
	}

	public void setCoab_company(String coab_company) {
		this.coab_company = coab_company;
	}

	public boolean isUpdateState() {
		return updateState;
	}

	public void setUpdateState(boolean updateState) {
		this.updateState = updateState;
	}

	public String getAddtime_y() {
		return addtime_y;
	}

	public void setAddtime_y(String addtime_y) {
		this.addtime_y = addtime_y;
	}

	public String getAddtime_m() {
		return addtime_m;
	}

	public void setAddtime_m(String addtime_m) {
		this.addtime_m = addtime_m;
	}

	public String getAddtime_d() {
		return addtime_d;
	}

	public void setAddtime_d(String addtime_d) {
		this.addtime_d = addtime_d;
	}

	public String getWtss_shebaopayty() {
		return wtss_shebaopayty;
	}

	public void setWtss_shebaopayty(String wtss_shebaopayty) {
		this.wtss_shebaopayty = wtss_shebaopayty;
	}

	public String getWtss_gjjpayty() {
		return wtss_gjjpayty;
	}

	public void setWtss_gjjpayty(String wtss_gjjpayty) {
		this.wtss_gjjpayty = wtss_gjjpayty;
	}

	public Integer getCoab_id() {
		return coab_id;
	}

	public void setCoab_id(Integer coab_id) {
		this.coab_id = coab_id;
	}

}
