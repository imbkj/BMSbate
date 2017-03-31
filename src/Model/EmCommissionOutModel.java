package Model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class EmCommissionOutModel {
	// ecou_id
	private Integer ecou_id;
	// gid
	private Integer gid;
	// cid
	private Integer cid;
	// SocialInsurance表外键id
	private Integer ecou_soin_id;
	// EmCommissionOutStandard表外键id
	// 当ecoc_addtype为一次性费用时,此项为CoAgencyBaseCityRel表外键id
	private Integer ecou_ecos_id;
	// 变动类型:新增,调整,停缴,在职,离职,取消
	private String ecou_addtype;
	// ecou_idcard
	private String ecou_idcard;
	// ecou_email
	private String ecou_email;
	// ecou_phone
	private String ecou_phone;
	// ecou_mobile
	private String ecou_mobile;
	// 执行日
	private String ecou_title_date;
	private Date title_date;
	// ecou_in_date
	private Date ecou_in_date;
	// ecou_com_phone
	private String ecou_com_phone;
	// ecou_com_principal
	private String ecou_com_principal;
	// ecou_com_company
	private String ecou_com_company;
	// 户籍
	private String ecou_domicile;
	// 合同签订方
	private String ecou_compact_jud;
	// 合同起始日期
	private String ecou_compact_f;
	// 合同结束日期
	private String ecou_compact_l;
	// 社保基数
	private BigDecimal ecou_sb_base;
	// 公积金基数
	private BigDecimal ecou_house_base;
	// 社保费用总和
	private BigDecimal ecou_sb_sum;
	// 公积金费用总和
	private BigDecimal ecou_gjj_sum;
	// 福利产品费用总和
	private BigDecimal ecou_welfare_sum;
	// 服务费
	private BigDecimal ecou_service_fee;

	private BigDecimal wtot_fee;
	// 档案费
	private BigDecimal ecou_file_fee;
	// 费用总和
	private BigDecimal ecou_sum;
	// ecou_stop_date
	private Date ecou_stop_date;
	// ecou_stop_cause
	private String ecou_stop_cause;
	// 取消原因
	private String ecou_cancel_cause;
	// 0.离职 1.在职
	private Integer ecou_state;
	// 客服
	private String ecou_client;
	// ecou_addname
	private String ecou_addname;
	// ecou_addtime
	private Date ecou_addtime;
	private String ecou_addtime1;
	// ecou_remark
	private String ecou_remark;
	// 实际工资
	private BigDecimal ecou_salary;
	// 社保个人总计
	private BigDecimal ecou_sb_em_sum;
	// 社保企业总计
	private BigDecimal ecou_sb_co_sum;
	// 公积金个人总计
	private BigDecimal ecou_gjj_em_sum;
	// 公积金企业总计
	private BigDecimal ecou_gjj_co_sum;
	// 补缴所属在册ecou_id
	private Integer ecou_bj_id;

	// 判断修改权限
	private Integer ecou_edit_st;

	java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");

	private Integer sial_id;
	private String soin_title;
	private Integer soin_id;
	private String sial_execdate;
	private String emba_name;
	private String coba_shortname;
	private String coba_company;
	private String coab_name;
	private String city;
	private String type;
	private Integer ecos_cabc_id;
	private String statename;
	private String ecos_name;
	private String coab_company;

	private List<EmCommissionOutFeeDetailModel> feeList;

	public final String getSial_execdate() {
		return sial_execdate;
	}

	public BigDecimal getWtot_fee() {
		return wtot_fee;
	}

	public void setWtot_fee(BigDecimal wtot_fee) {
		this.wtot_fee = new BigDecimal(df.format(wtot_fee));
	}

	public final String getEmba_name() {
		return emba_name;
	}

	public final String getCoba_shortname() {
		return coba_shortname;
	}

	public final String getCoba_company() {
		return coba_company;
	}

	public final String getCoab_name() {
		return coab_name;
	}

	public final String getCity() {
		return city;
	}

	public final String getType() {
		return type;
	}

	public final Integer getEcos_cabc_id() {
		return ecos_cabc_id;
	}

	public final void setSial_execdate(String sial_execdate) {
		this.sial_execdate = sial_execdate;
	}

	public final void setEmba_name(String emba_name) {
		this.emba_name = emba_name;
	}

	public final void setCoba_shortname(String coba_shortname) {
		this.coba_shortname = coba_shortname;
	}

	public final void setCoba_company(String coba_company) {
		this.coba_company = coba_company;
	}

	public final void setCoab_name(String coab_name) {
		this.coab_name = coab_name;
	}

	public final void setCity(String city) {
		this.city = city;
	}

	public final void setType(String type) {
		this.type = type;
	}

	public final void setEcos_cabc_id(Integer ecos_cabc_id) {
		this.ecos_cabc_id = ecos_cabc_id;
	}

	public Integer getEcou_id() {
		return ecou_id;
	}

	public void setEcou_id(Integer ecou_id) {
		this.ecou_id = ecou_id;
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

	public Integer getEcou_ecos_id() {
		return ecou_ecos_id;
	}

	public void setEcou_ecos_id(Integer ecou_ecos_id) {
		this.ecou_ecos_id = ecou_ecos_id;
	}

	public String getEcou_addtype() {
		return ecou_addtype;
	}

	public void setEcou_addtype(String ecou_addtype) {
		this.ecou_addtype = ecou_addtype;
	}

	public String getEcou_idcard() {
		return ecou_idcard;
	}

	public void setEcou_idcard(String ecou_idcard) {
		this.ecou_idcard = ecou_idcard;
	}

	public String getEcou_email() {
		return ecou_email;
	}

	public void setEcou_email(String ecou_email) {
		this.ecou_email = ecou_email;
	}

	public String getEcou_phone() {
		return ecou_phone;
	}

	public void setEcou_phone(String ecou_phone) {
		this.ecou_phone = ecou_phone;
	}

	public String getEcou_mobile() {
		return ecou_mobile;
	}

	public void setEcou_mobile(String ecou_mobile) {
		this.ecou_mobile = ecou_mobile;
	}

	public String getEcou_title_date() {
		return ecou_title_date;
	}

	public void setEcou_title_date(String ecou_title_date) {
		this.ecou_title_date = ecou_title_date;
	}

	public Date getEcou_in_date() {
		return ecou_in_date;
	}

	public void setEcou_in_date(Date ecou_in_date) {
		this.ecou_in_date = ecou_in_date;
	}

	public String getEcou_com_phone() {
		return ecou_com_phone;
	}

	public void setEcou_com_phone(String ecou_com_phone) {
		this.ecou_com_phone = ecou_com_phone;
	}

	public String getEcou_com_principal() {
		return ecou_com_principal;
	}

	public void setEcou_com_principal(String ecou_com_principal) {
		this.ecou_com_principal = ecou_com_principal;
	}

	public String getEcou_com_company() {
		return ecou_com_company;
	}

	public void setEcou_com_company(String ecou_com_company) {
		this.ecou_com_company = ecou_com_company;
	}

	public String getEcou_domicile() {
		return ecou_domicile;
	}

	public void setEcou_domicile(String ecou_domicile) {
		this.ecou_domicile = ecou_domicile;
	}

	public String getEcou_compact_jud() {
		return ecou_compact_jud;
	}

	public void setEcou_compact_jud(String ecou_compact_jud) {
		this.ecou_compact_jud = ecou_compact_jud;
	}

	public String getEcou_compact_f() {
		return ecou_compact_f;
	}

	public void setEcou_compact_f(String ecou_compact_f) {
		this.ecou_compact_f = ecou_compact_f;
	}

	public String getEcou_compact_l() {
		return ecou_compact_l;
	}

	public void setEcou_compact_l(String ecou_compact_l) {
		this.ecou_compact_l = ecou_compact_l;
	}

	public BigDecimal getEcou_sb_base() {
		return ecou_sb_base;
	}

	public void setEcou_sb_base(BigDecimal ecou_sb_base) {
		if (ecou_sb_base != null) {
			this.ecou_sb_base = ecou_sb_base.setScale(2,
					BigDecimal.ROUND_HALF_UP);
		} else {
			this.ecou_sb_base = ecou_sb_base;
		}
	}

	public BigDecimal getEcou_house_base() {
		return ecou_house_base;
	}

	public void setEcou_house_base(BigDecimal ecou_house_base) {
		if (ecou_house_base != null) {
			this.ecou_house_base = ecou_house_base.setScale(2,
					BigDecimal.ROUND_HALF_UP);
		} else {
			this.ecou_house_base = ecou_house_base;
		}

	}

	public BigDecimal getEcou_sb_sum() {
		return ecou_sb_sum;
	}

	public void setEcou_sb_sum(BigDecimal ecou_sb_sum) {
		if (ecou_sb_sum != null) {
			this.ecou_sb_sum = ecou_sb_sum
					.setScale(2, BigDecimal.ROUND_HALF_UP);
		} else {
			this.ecou_sb_sum = ecou_sb_sum;
		}

	}

	public BigDecimal getEcou_gjj_sum() {
		return ecou_gjj_sum;
	}

	public void setEcou_gjj_sum(BigDecimal ecou_gjj_sum) {
		if (ecou_gjj_sum != null) {
			this.ecou_gjj_sum = ecou_gjj_sum.setScale(2,
					BigDecimal.ROUND_HALF_UP);
		} else {
			this.ecou_gjj_sum = ecou_gjj_sum;
		}

	}

	public BigDecimal getEcou_welfare_sum() {
		return ecou_welfare_sum;
	}

	public void setEcou_welfare_sum(BigDecimal ecou_welfare_sum) {
		if (ecou_welfare_sum != null) {
			this.ecou_welfare_sum = ecou_welfare_sum.setScale(2,
					BigDecimal.ROUND_HALF_UP);
		} else {
			this.ecou_welfare_sum = ecou_welfare_sum;
		}

	}

	public BigDecimal getEcou_service_fee() {
		return ecou_service_fee;
	}

	public void setEcou_service_fee(BigDecimal ecou_service_fee) {
		if (ecou_service_fee != null) {
			this.ecou_service_fee = ecou_service_fee.setScale(2,
					BigDecimal.ROUND_HALF_UP);
		} else {
			this.ecou_service_fee = ecou_service_fee;
		}
	}

	public BigDecimal getEcou_file_fee() {
		return ecou_file_fee;
	}

	public void setEcou_file_fee(BigDecimal ecou_file_fee) {
		if (ecou_file_fee != null) {
			this.ecou_file_fee = ecou_file_fee.setScale(2,
					BigDecimal.ROUND_HALF_UP);
		} else {
			this.ecou_file_fee = ecou_file_fee;
		}

	}

	public BigDecimal getEcou_sum() {
		return ecou_sum;
	}

	public void setEcou_sum(BigDecimal ecou_sum) {
		if (ecou_sum != null) {
			this.ecou_sum = ecou_sum.setScale(2, BigDecimal.ROUND_HALF_UP);
		} else {
			this.ecou_sum = ecou_sum;
		}

	}

	public Date getEcou_stop_date() {
		return ecou_stop_date;
	}

	public void setEcou_stop_date(Date ecou_stop_date) {
		this.ecou_stop_date = ecou_stop_date;
	}

	public String getEcou_stop_cause() {
		return ecou_stop_cause;
	}

	public void setEcou_stop_cause(String ecou_stop_cause) {
		this.ecou_stop_cause = ecou_stop_cause;
	}

	public String getEcou_cancel_cause() {
		return ecou_cancel_cause;
	}

	public void setEcou_cancel_cause(String ecou_cancel_cause) {
		this.ecou_cancel_cause = ecou_cancel_cause;
	}

	public Integer getEcou_state() {
		return ecou_state;
	}

	public void setEcou_state(Integer ecou_state) {
		this.ecou_state = ecou_state;
	}

	public String getEcou_client() {
		return ecou_client;
	}

	public void setEcou_client(String ecou_client) {
		this.ecou_client = ecou_client;
	}

	public String getEcou_addname() {
		return ecou_addname;
	}

	public void setEcou_addname(String ecou_addname) {
		this.ecou_addname = ecou_addname;
	}

	public Date getEcou_addtime() {
		return ecou_addtime;
	}

	public void setEcou_addtime(Date ecou_addtime) {
		this.ecou_addtime = ecou_addtime;
	}

	public String getEcou_remark() {
		return ecou_remark;
	}

	public void setEcou_remark(String ecou_remark) {
		this.ecou_remark = ecou_remark;
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

	public BigDecimal getEcou_salary() {
		return ecou_salary;
	}

	public void setEcou_salary(BigDecimal ecou_salary) {
		if (ecou_salary != null) {
			this.ecou_salary = ecou_salary
					.setScale(2, BigDecimal.ROUND_HALF_UP);
		} else {
			this.ecou_salary = ecou_salary;
		}

	}

	public Integer getSoin_id() {
		return soin_id;
	}

	public void setSoin_id(Integer soin_id) {
		this.soin_id = soin_id;
	}

	public BigDecimal getEcou_sb_em_sum() {
		return ecou_sb_em_sum;
	}

	public void setEcou_sb_em_sum(BigDecimal ecou_sb_em_sum) {
		if (ecou_sb_em_sum != null) {
			this.ecou_sb_em_sum = ecou_sb_em_sum.setScale(2,
					BigDecimal.ROUND_HALF_UP);
		} else {
			this.ecou_sb_em_sum = ecou_sb_em_sum;
		}

	}

	public BigDecimal getEcou_sb_co_sum() {
		return ecou_sb_co_sum;
	}

	public void setEcou_sb_co_sum(BigDecimal ecou_sb_co_sum) {
		if (ecou_sb_co_sum != null) {
			this.ecou_sb_co_sum = ecou_sb_co_sum.setScale(2,
					BigDecimal.ROUND_HALF_UP);
		} else {
			this.ecou_sb_co_sum = ecou_sb_co_sum;
		}
	}

	public BigDecimal getEcou_gjj_em_sum() {
		return ecou_gjj_em_sum;
	}

	public void setEcou_gjj_em_sum(BigDecimal ecou_gjj_em_sum) {
		if (ecou_gjj_em_sum != null) {
			this.ecou_gjj_em_sum = ecou_gjj_em_sum.setScale(2,
					BigDecimal.ROUND_HALF_UP);
		} else {
			this.ecou_gjj_em_sum = ecou_gjj_em_sum;
		}
	}

	public BigDecimal getEcou_gjj_co_sum() {
		return ecou_gjj_co_sum;
	}

	public void setEcou_gjj_co_sum(BigDecimal ecou_gjj_co_sum) {
		if (ecou_gjj_co_sum != null) {

			this.ecou_gjj_co_sum = ecou_gjj_co_sum.setScale(2,
					BigDecimal.ROUND_HALF_UP);
		} else {
			this.ecou_gjj_co_sum = ecou_gjj_co_sum;
		}
	}

	public Integer getEcou_soin_id() {
		return ecou_soin_id;
	}

	public void setEcou_soin_id(Integer ecou_soin_id) {
		this.ecou_soin_id = ecou_soin_id;
	}

	public Date getTitle_date() {
		return title_date;
	}

	public void setTitle_date(Date title_date) {
		this.title_date = title_date;
	}

	public List<EmCommissionOutFeeDetailModel> getFeeList() {
		return feeList;
	}

	public void setFeeList(List<EmCommissionOutFeeDetailModel> feeList) {
		this.feeList = feeList;
	}

	public String getEcou_addtime1() {
		return ecou_addtime1;
	}

	public void setEcou_addtime1(String ecou_addtime1) {
		this.ecou_addtime1 = ecou_addtime1;
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

	public Integer getEcou_bj_id() {
		return ecou_bj_id;
	}

	public void setEcou_bj_id(Integer ecou_bj_id) {
		this.ecou_bj_id = ecou_bj_id;
	}

	public String getCoab_company() {
		return coab_company;
	}

	public void setCoab_company(String coab_company) {
		this.coab_company = coab_company;
	}

	public Integer getEcou_edit_st() {
		return ecou_edit_st;
	}

	public void setEcou_edit_st(Integer ecou_edit_st) {
		this.ecou_edit_st = ecou_edit_st;
	}

}
