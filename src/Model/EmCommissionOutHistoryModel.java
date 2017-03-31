package Model;

import java.math.BigDecimal;
import java.util.Date;

public class EmCommissionOutHistoryModel {
	// ecoh_id
	private Integer ecoh_id;
	// gid
	private Integer gid;
	// cid
	private Integer cid;
	// ecoh_ecou_id
	private Integer ecoh_ecou_id;
	// SocialInsurance表外键id
	private Integer ecoh_soin_id;
	// EmCommissionOutHistoryStandard表外键id,当ecoc_addtype为一次性费用时,此项为CoAgencyBaseCityRel表外键id
	private Integer ecoh_ecos_id;
	// 变动类型:新增,调整,停缴,在职,离职,取消
	private String ecoh_addtype;
	// ecoh_idcard
	private String ecoh_idcard;
	// ecoh_email
	private String ecoh_email;
	// ecoh_phone
	private String ecoh_phone;
	// ecoh_mobile
	private String ecoh_mobile;
	// ecoh_title_date
	private String ecoh_title_date;
	// ecoh_in_date
	private Date ecoh_in_date;
	// ecoh_com_phone
	private String ecoh_com_phone;
	// ecoh_com_principal
	private String ecoh_com_principal;
	// ecoh_com_company
	private String ecoh_com_company;
	// 户籍
	private String ecoh_domicile;
	// 合同签订方
	private String ecoh_compact_jud;
	// 合同起始日期
	private String ecoh_compact_f;
	// 合同结束日期
	private String ecoh_compact_l;
	// 实际工资
	private BigDecimal ecoh_salary;
	// 社保基数
	private BigDecimal ecoh_sb_base;
	// 公积金基数
	private BigDecimal ecoh_house_base;
	// 社保个人总计
	private BigDecimal ecoh_sb_em_sum;
	// 社保企业总计
	private BigDecimal ecoh_sb_co_sum;
	// 社保费用总和
	private BigDecimal ecoh_sb_sum;
	// 公积金个人总计
	private BigDecimal ecoh_gjj_em_sum;
	// 公积金企业总计
	private BigDecimal ecoh_gjj_co_sum;
	// 公积金费用总和
	private BigDecimal ecoh_gjj_sum;
	// 福利产品费用总和
	private BigDecimal ecoh_welfare_sum;
	// 服务费
	private BigDecimal ecoh_service_fee;
	// 档案费
	private BigDecimal ecoh_file_fee;
	// 费用总和
	private BigDecimal ecoh_sum;
	// ecoh_stop_date
	private Date ecoh_stop_date;
	// ecoh_stop_cause
	private String ecoh_stop_cause;
	// 取消原因
	private String ecoh_cancel_cause;
	// 0.离职 1.在职 2.取消 3.被退回
	private Integer ecoh_state;
	// 客服
	private String ecoh_client;
	// ecoh_addname
	private String ecoh_addname;
	// ecoh_addtime
	private Date ecoh_addtime;
	// ecoh_remark
	private String ecoh_remark;
	// ownmonth
	private Integer ownmonth;
	private Integer bjownmonth;

	// 应付人数
	private Integer yf_count;
	// 应收人数
	private Integer ys_count;
	// 实付人数
	private Integer sf_count;
	// 应付金额
	private BigDecimal yf_sum;
	// 应收金额
	private BigDecimal ys_sum;
	// 实付金额
	private BigDecimal sf_sum;
	// 应付-应收
	private BigDecimal yf_ys_diff;
	// 应付-实付
	private BigDecimal yf_sf_diff;

	private Integer cabc_id;
	private Integer cabc_ppc_id;
	private String city;
	private String coab_name;
	private String company;
	private String emba_name;
	private Integer ecpu_id;

	// efco_id
	private Integer efco_id;
	// efch_id
	private Integer efch_id;
	// efco_efba_id
	private Integer efco_efba_id;
	// efco_coco_id
	private Integer efco_coco_id;
	// efco_cfmb_number
	private String efco_cfmb_number;
	// efco_cabc_id
	private Integer efco_cabc_id;
	// efco_soin_id
	private Integer efco_soin_id;
	// efco_ecou_id
	private Integer efco_ecou_id;
	// efco_ecos_shebao_feetype
	private Integer efco_ecos_shebao_feetype;
	// efco_ecos_gjj_feetype
	private Integer efco_ecos_gjj_feetype;
	// efco_ecou_addtype
	private String efco_ecou_addtype;
	// efco_ecou_title_date
	private String efco_ecou_title_date;
	// efco_ecou_sb_sum
	private BigDecimal efco_ecou_sb_sum;
	// efco_ecou_gjj_sum
	private BigDecimal efco_ecou_gjj_sum;
	// efco_ecou_cb_sum
	private BigDecimal efco_ecou_cb_sum;
	// efco_ecou_welfare_sum
	private BigDecimal efco_ecou_welfare_sum;
	// efco_ecou_service_fee
	private BigDecimal efco_ecou_service_fee;
	// efco_ecou_file_fee
	private BigDecimal efco_ecou_file_fee;
	// efco_Receivable
	private BigDecimal efco_receivable;
	// efco_PaidIn
	private BigDecimal efco_paidin;
	// efco_PayOut
	private BigDecimal efco_payout;
	// efco_IfFirstPaidIn
	private Integer efco_iffirstpaidin;
	// efco_addtime
	private Date efco_addtime;
	// efco_FinalCheck
	private Integer efco_finalcheck;
	// efco_FinalCheckTime
	private Date efco_finalchecktime;
	// efco_state
	private Integer efco_state;

	// 社保合计
	private BigDecimal ecpu_sb_total;
	// 住房合计
	private BigDecimal ecpu_gjj_total;
	// 其他金额
	private BigDecimal ecpu_other_total;
	// 福利合计
	private BigDecimal ecpu_welfare_total;
	// 总计
	private BigDecimal ecpu_total;

	// 社保应付总计 - 社保应收总计
	private BigDecimal yf_ys_sbsum_diff;
	// 社保应付总计 - 社保实付总计
	private BigDecimal yf_sf_sbsum_diff;
	// 公积金应付总计 - 公积金应收总计
	private BigDecimal yf_ys_gjjsum_diff;
	// 公积金应付总计 - 公积金实付总计
	private BigDecimal yf_sf_gjjsum_diff;
	// 福利应付总计 - 福利应收总计
	private BigDecimal yf_ys_welsum_diff;
	// 福利应付总计 - 福利实付总计
	private BigDecimal yf_sf_welsum_diff;
	// 应付总计 - 应收总计
	private BigDecimal yf_ys_sum_diff;
	// 应付总计 - 实付总计
	private BigDecimal yf_sf_sum_diff;

	public final BigDecimal getEcpu_sb_total() {
		return ecpu_sb_total;
	}

	public final void setEcpu_sb_total(BigDecimal ecpu_sb_total) {
		this.ecpu_sb_total = ecpu_sb_total == null ? null : ecpu_sb_total
				.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final BigDecimal getEcpu_gjj_total() {
		return ecpu_gjj_total;
	}

	public final void setEcpu_gjj_total(BigDecimal ecpu_gjj_total) {
		this.ecpu_gjj_total = ecpu_gjj_total == null ? null : ecpu_gjj_total
				.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final BigDecimal getEcpu_other_total() {
		return ecpu_other_total;
	}

	public final void setEcpu_other_total(BigDecimal ecpu_other_total) {
		this.ecpu_other_total = ecpu_other_total == null ? null
				: ecpu_other_total.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final BigDecimal getEcpu_welfare_total() {
		return ecpu_welfare_total;
	}

	public final void setEcpu_welfare_total(BigDecimal ecpu_welfare_total) {
		this.ecpu_welfare_total = ecpu_welfare_total == null ? null
				: ecpu_welfare_total.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final BigDecimal getEcpu_total() {
		return ecpu_total;
	}

	public final void setEcpu_total(BigDecimal ecpu_total) {
		this.ecpu_total = ecpu_total == null ? null : ecpu_total.setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	public final BigDecimal getYf_ys_sbsum_diff() {
		return yf_ys_sbsum_diff;
	}

	public final BigDecimal getYf_sf_sbsum_diff() {
		return yf_sf_sbsum_diff;
	}

	public final BigDecimal getYf_ys_gjjsum_diff() {
		return yf_ys_gjjsum_diff;
	}

	public final BigDecimal getYf_sf_gjjsum_diff() {
		return yf_sf_gjjsum_diff;
	}

	public final BigDecimal getYf_ys_welsum_diff() {
		return yf_ys_welsum_diff;
	}

	public final BigDecimal getYf_sf_welsum_diff() {
		return yf_sf_welsum_diff;
	}

	public final BigDecimal getYf_ys_sum_diff() {
		return yf_ys_sum_diff;
	}

	public final BigDecimal getYf_sf_sum_diff() {
		return yf_sf_sum_diff;
	}

	public final void setYf_ys_sbsum_diff(BigDecimal yf_ys_sbsum_diff) {
		this.yf_ys_sbsum_diff = yf_ys_sbsum_diff == null ? null
				: yf_ys_sbsum_diff.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setYf_sf_sbsum_diff(BigDecimal yf_sf_sbsum_diff) {
		this.yf_sf_sbsum_diff = yf_sf_sbsum_diff == null ? null
				: yf_sf_sbsum_diff.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setYf_ys_gjjsum_diff(BigDecimal yf_ys_gjjsum_diff) {
		this.yf_ys_gjjsum_diff = yf_ys_gjjsum_diff == null ? null
				: yf_ys_gjjsum_diff.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setYf_sf_gjjsum_diff(BigDecimal ys_sf_gjjsum_diff) {
		this.yf_sf_gjjsum_diff = yf_sf_gjjsum_diff == null ? null
				: yf_sf_gjjsum_diff.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setYf_ys_welsum_diff(BigDecimal yf_ys_welsum_diff) {
		this.yf_ys_welsum_diff = yf_ys_welsum_diff == null ? null
				: yf_ys_welsum_diff.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setYf_sf_welsum_diff(BigDecimal yf_sf_welsum_diff) {
		this.yf_sf_welsum_diff = yf_sf_welsum_diff == null ? null
				: yf_sf_welsum_diff.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setYf_ys_sum_diff(BigDecimal yf_ys_sum_diff) {
		this.yf_ys_sum_diff = yf_ys_sum_diff == null ? null : yf_ys_sum_diff
				.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setYf_sf_sum_diff(BigDecimal yf_sf_sum_diff) {
		this.yf_sf_sum_diff = yf_sf_sum_diff == null ? null : yf_sf_sum_diff
				.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final Integer getEfco_id() {
		return efco_id;
	}

	public final void setEfco_id(Integer efco_id) {
		this.efco_id = efco_id;
	}

	public final Integer getEfco_efba_id() {
		return efco_efba_id;
	}

	public final void setEfco_efba_id(Integer efco_efba_id) {
		this.efco_efba_id = efco_efba_id;
	}

	public final Integer getEfco_coco_id() {
		return efco_coco_id;
	}

	public final void setEfco_coco_id(Integer efco_coco_id) {
		this.efco_coco_id = efco_coco_id;
	}

	public final String getEfco_cfmb_number() {
		return efco_cfmb_number;
	}

	public final void setEfco_cfmb_number(String efco_cfmb_number) {
		this.efco_cfmb_number = efco_cfmb_number;
	}

	public final Integer getEfco_cabc_id() {
		return efco_cabc_id;
	}

	public final void setEfco_cabc_id(Integer efco_cabc_id) {
		this.efco_cabc_id = efco_cabc_id;
	}

	public final Integer getEfco_soin_id() {
		return efco_soin_id;
	}

	public final void setEfco_soin_id(Integer efco_soin_id) {
		this.efco_soin_id = efco_soin_id;
	}

	public final Integer getEfco_ecou_id() {
		return efco_ecou_id;
	}

	public final void setEfco_ecou_id(Integer efco_ecou_id) {
		this.efco_ecou_id = efco_ecou_id;
	}

	public final Integer getEfco_ecos_shebao_feetype() {
		return efco_ecos_shebao_feetype;
	}

	public final void setEfco_ecos_shebao_feetype(
			Integer efco_ecos_shebao_feetype) {
		this.efco_ecos_shebao_feetype = efco_ecos_shebao_feetype;
	}

	public final Integer getEfco_ecos_gjj_feetype() {
		return efco_ecos_gjj_feetype;
	}

	public final void setEfco_ecos_gjj_feetype(Integer efco_ecos_gjj_feetype) {
		this.efco_ecos_gjj_feetype = efco_ecos_gjj_feetype;
	}

	public final String getEfco_ecou_addtype() {
		return efco_ecou_addtype;
	}

	public final void setEfco_ecou_addtype(String efco_ecou_addtype) {
		this.efco_ecou_addtype = efco_ecou_addtype;
	}

	public final String getEfco_ecou_title_date() {
		return efco_ecou_title_date;
	}

	public final void setEfco_ecou_title_date(String efco_ecou_title_date) {
		this.efco_ecou_title_date = efco_ecou_title_date;
	}

	public final BigDecimal getEfco_ecou_sb_sum() {
		return efco_ecou_sb_sum;
	}

	public final void setEfco_ecou_sb_sum(BigDecimal efco_ecou_sb_sum) {
		this.efco_ecou_sb_sum = efco_ecou_sb_sum == null ? null
				: efco_ecou_sb_sum.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final BigDecimal getEfco_ecou_gjj_sum() {
		return efco_ecou_gjj_sum;
	}

	public final void setEfco_ecou_gjj_sum(BigDecimal efco_ecou_gjj_sum) {
		this.efco_ecou_gjj_sum = efco_ecou_gjj_sum == null ? null
				: efco_ecou_gjj_sum.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final BigDecimal getEfco_ecou_cb_sum() {
		return efco_ecou_cb_sum;
	}

	public final void setEfco_ecou_cb_sum(BigDecimal efco_ecou_cb_sum) {
		this.efco_ecou_cb_sum = efco_ecou_cb_sum == null ? null
				: efco_ecou_cb_sum.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final BigDecimal getEfco_ecou_welfare_sum() {
		return efco_ecou_welfare_sum;
	}

	public final void setEfco_ecou_welfare_sum(BigDecimal efco_ecou_welfare_sum) {
		this.efco_ecou_welfare_sum = efco_ecou_welfare_sum == null ? null
				: efco_ecou_welfare_sum.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final BigDecimal getEfco_ecou_service_fee() {
		return efco_ecou_service_fee;
	}

	public final void setEfco_ecou_service_fee(BigDecimal efco_ecou_service_fee) {
		this.efco_ecou_service_fee = efco_ecou_service_fee == null ? null
				: efco_ecou_service_fee.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final BigDecimal getEfco_ecou_file_fee() {
		return efco_ecou_file_fee;
	}

	public final void setEfco_ecou_file_fee(BigDecimal efco_ecou_file_fee) {
		this.efco_ecou_file_fee = efco_ecou_file_fee == null ? null
				: efco_ecou_file_fee.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final BigDecimal getEfco_receivable() {
		return efco_receivable;
	}

	public final void setEfco_receivable(BigDecimal efco_receivable) {
		this.efco_receivable = efco_receivable == null ? null : efco_receivable
				.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final BigDecimal getEfco_paidin() {
		return efco_paidin;
	}

	public final void setEfco_paidin(BigDecimal efco_paidin) {
		this.efco_paidin = efco_paidin == null ? null : efco_paidin.setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	public final BigDecimal getEfco_payout() {
		return efco_payout;
	}

	public final void setEfco_payout(BigDecimal efco_payout) {
		this.efco_payout = efco_payout == null ? null : efco_payout.setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	public final Integer getEfco_iffirstpaidin() {
		return efco_iffirstpaidin;
	}

	public final void setEfco_iffirstpaidin(Integer efco_iffirstpaidin) {
		this.efco_iffirstpaidin = efco_iffirstpaidin;
	}

	public final Date getEfco_addtime() {
		return efco_addtime;
	}

	public final void setEfco_addtime(Date efco_addtime) {
		this.efco_addtime = efco_addtime;
	}

	public final Integer getEfco_finalcheck() {
		return efco_finalcheck;
	}

	public final void setEfco_finalcheck(Integer efco_finalcheck) {
		this.efco_finalcheck = efco_finalcheck;
	}

	public final Date getEfco_finalchecktime() {
		return efco_finalchecktime;
	}

	public final void setEfco_finalchecktime(Date efco_finalchecktime) {
		this.efco_finalchecktime = efco_finalchecktime;
	}

	public final Integer getEfco_state() {
		return efco_state;
	}

	public final void setEfco_state(Integer efco_state) {
		this.efco_state = efco_state;
	}

	public final Integer getCabc_id() {
		return cabc_id;
	}

	public final Integer getCabc_ppc_id() {
		return cabc_ppc_id;
	}

	public final String getCity() {
		return city;
	}

	public final String getCoab_name() {
		return coab_name;
	}

	public final void setCabc_id(Integer cabc_id) {
		this.cabc_id = cabc_id;
	}

	public final void setCabc_ppc_id(Integer cabc_ppc_id) {
		this.cabc_ppc_id = cabc_ppc_id;
	}

	public final void setCity(String city) {
		this.city = city;
	}

	public final void setCoab_name(String coab_name) {
		this.coab_name = coab_name;
	}

	private BigDecimal zero() {
		return BigDecimal.ZERO;
	}

	public final Integer getEcoh_id() {
		return ecoh_id;
	}

	public final Integer getGid() {
		return gid;
	}

	public final Integer getCid() {
		return cid;
	}

	public final Integer getEcoh_ecou_id() {
		return ecoh_ecou_id;
	}

	public final Integer getEcoh_soin_id() {
		return ecoh_soin_id;
	}

	public final Integer getEcoh_ecos_id() {
		return ecoh_ecos_id;
	}

	public final String getEcoh_addtype() {
		return ecoh_addtype;
	}

	public final String getEcoh_idcard() {
		return ecoh_idcard;
	}

	public final String getEcoh_email() {
		return ecoh_email;
	}

	public final String getEcoh_phone() {
		return ecoh_phone;
	}

	public final String getEcoh_mobile() {
		return ecoh_mobile;
	}

	public final String getEcoh_title_date() {
		return ecoh_title_date;
	}

	public final Date getEcoh_in_date() {
		return ecoh_in_date;
	}

	public final String getEcoh_com_phone() {
		return ecoh_com_phone;
	}

	public final String getEcoh_com_principal() {
		return ecoh_com_principal;
	}

	public final String getEcoh_com_company() {
		return ecoh_com_company;
	}

	public final String getEcoh_domicile() {
		return ecoh_domicile;
	}

	public final String getEcoh_compact_jud() {
		return ecoh_compact_jud;
	}

	public final String getEcoh_compact_f() {
		return ecoh_compact_f;
	}

	public final String getEcoh_compact_l() {
		return ecoh_compact_l;
	}

	public final BigDecimal getEcoh_salary() {
		return ecoh_salary;
	}

	public final BigDecimal getEcoh_sb_base() {
		return ecoh_sb_base;
	}

	public final BigDecimal getEcoh_house_base() {
		return ecoh_house_base;
	}

	public final BigDecimal getEcoh_sb_em_sum() {
		return ecoh_sb_em_sum;
	}

	public final BigDecimal getEcoh_sb_co_sum() {
		return ecoh_sb_co_sum;
	}

	public final BigDecimal getEcoh_sb_sum() {
		return ecoh_sb_sum;
	}

	public final BigDecimal getEcoh_gjj_em_sum() {
		return ecoh_gjj_em_sum;
	}

	public final BigDecimal getEcoh_gjj_co_sum() {
		return ecoh_gjj_co_sum;
	}

	public final BigDecimal getEcoh_gjj_sum() {
		return ecoh_gjj_sum;
	}

	public final BigDecimal getEcoh_welfare_sum() {
		return ecoh_welfare_sum;
	}

	public final BigDecimal getEcoh_service_fee() {
		return ecoh_service_fee;
	}

	public final BigDecimal getEcoh_file_fee() {
		return ecoh_file_fee;
	}

	public final BigDecimal getEcoh_sum() {
		return ecoh_sum;
	}

	public final Date getEcoh_stop_date() {
		return ecoh_stop_date;
	}

	public final String getEcoh_stop_cause() {
		return ecoh_stop_cause;
	}

	public final String getEcoh_cancel_cause() {
		return ecoh_cancel_cause;
	}

	public final Integer getEcoh_state() {
		return ecoh_state;
	}

	public final String getEcoh_client() {
		return ecoh_client;
	}

	public final String getEcoh_addname() {
		return ecoh_addname;
	}

	public final Date getEcoh_addtime() {
		return ecoh_addtime;
	}

	public final String getEcoh_remark() {
		return ecoh_remark;
	}

	public final Integer getOwnmonth() {
		return ownmonth;
	}

	public final Integer getYf_count() {
		return yf_count;
	}

	public final Integer getYs_count() {
		return ys_count;
	}

	public final Integer getSf_count() {
		return sf_count;
	}

	public final BigDecimal getYf_sum() {
		return yf_sum;
	}

	public final BigDecimal getYs_sum() {
		return ys_sum;
	}

	public final BigDecimal getSf_sum() {
		return sf_sum;
	}

	public final BigDecimal getYf_ys_diff() {
		return yf_ys_diff;
	}

	public final BigDecimal getYf_sf_diff() {
		return yf_sf_diff;
	}

	public final void setEcoh_id(Integer ecoh_id) {
		this.ecoh_id = ecoh_id;
	}

	public final void setGid(Integer gid) {
		this.gid = gid;
	}

	public final void setCid(Integer cid) {
		this.cid = cid;
	}

	public final void setEcoh_ecou_id(Integer ecoh_ecou_id) {
		this.ecoh_ecou_id = ecoh_ecou_id;
	}

	public final void setEcoh_soin_id(Integer ecoh_soin_id) {
		this.ecoh_soin_id = ecoh_soin_id;
	}

	public final void setEcoh_ecos_id(Integer ecoh_ecos_id) {
		this.ecoh_ecos_id = ecoh_ecos_id;
	}

	public final void setEcoh_addtype(String ecoh_addtype) {
		this.ecoh_addtype = ecoh_addtype;
	}

	public final void setEcoh_idcard(String ecoh_idcard) {
		this.ecoh_idcard = ecoh_idcard;
	}

	public final void setEcoh_email(String ecoh_email) {
		this.ecoh_email = ecoh_email;
	}

	public final void setEcoh_phone(String ecoh_phone) {
		this.ecoh_phone = ecoh_phone;
	}

	public final void setEcoh_mobile(String ecoh_mobile) {
		this.ecoh_mobile = ecoh_mobile;
	}

	public final void setEcoh_title_date(String ecoh_title_date) {
		this.ecoh_title_date = ecoh_title_date;
	}

	public final void setEcoh_in_date(Date ecoh_in_date) {
		this.ecoh_in_date = ecoh_in_date;
	}

	public final void setEcoh_com_phone(String ecoh_com_phone) {
		this.ecoh_com_phone = ecoh_com_phone;
	}

	public final void setEcoh_com_principal(String ecoh_com_principal) {
		this.ecoh_com_principal = ecoh_com_principal;
	}

	public final void setEcoh_com_company(String ecoh_com_company) {
		this.ecoh_com_company = ecoh_com_company;
	}

	public final void setEcoh_domicile(String ecoh_domicile) {
		this.ecoh_domicile = ecoh_domicile;
	}

	public final void setEcoh_compact_jud(String ecoh_compact_jud) {
		this.ecoh_compact_jud = ecoh_compact_jud;
	}

	public final void setEcoh_compact_f(String ecoh_compact_f) {
		this.ecoh_compact_f = ecoh_compact_f;
	}

	public final void setEcoh_compact_l(String ecoh_compact_l) {
		this.ecoh_compact_l = ecoh_compact_l;
	}

	public final void setEcoh_salary(BigDecimal ecoh_salary) {
		this.ecoh_salary = ecoh_salary == null ? zero() : ecoh_salary.setScale(
				2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setEcoh_sb_base(BigDecimal ecoh_sb_base) {
		this.ecoh_sb_base = ecoh_sb_base == null ? zero() : ecoh_sb_base
				.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setEcoh_house_base(BigDecimal ecoh_house_base) {
		this.ecoh_house_base = ecoh_house_base == null ? zero()
				: ecoh_house_base.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setEcoh_sb_em_sum(BigDecimal ecoh_sb_em_sum) {
		this.ecoh_sb_em_sum = ecoh_sb_em_sum == null ? zero() : ecoh_sb_em_sum
				.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setEcoh_sb_co_sum(BigDecimal ecoh_sb_co_sum) {
		this.ecoh_sb_co_sum = ecoh_sb_co_sum == null ? zero() : ecoh_sb_co_sum
				.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setEcoh_sb_sum(BigDecimal ecoh_sb_sum) {
		this.ecoh_sb_sum = ecoh_sb_sum == null ? zero() : ecoh_sb_sum.setScale(
				2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setEcoh_gjj_em_sum(BigDecimal ecoh_gjj_em_sum) {
		this.ecoh_gjj_em_sum = ecoh_gjj_em_sum == null ? zero()
				: ecoh_gjj_em_sum.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setEcoh_gjj_co_sum(BigDecimal ecoh_gjj_co_sum) {
		this.ecoh_gjj_co_sum = ecoh_gjj_co_sum == null ? zero()
				: ecoh_gjj_co_sum.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setEcoh_gjj_sum(BigDecimal ecoh_gjj_sum) {
		this.ecoh_gjj_sum = ecoh_gjj_sum == null ? zero() : ecoh_gjj_sum
				.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setEcoh_welfare_sum(BigDecimal ecoh_welfare_sum) {
		this.ecoh_welfare_sum = ecoh_welfare_sum == null ? zero()
				: ecoh_welfare_sum.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setEcoh_service_fee(BigDecimal ecoh_service_fee) {
		this.ecoh_service_fee = ecoh_service_fee == null ? zero()
				: ecoh_service_fee.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setEcoh_file_fee(BigDecimal ecoh_file_fee) {
		this.ecoh_file_fee = ecoh_file_fee == null ? zero() : ecoh_file_fee
				.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setEcoh_sum(BigDecimal ecoh_sum) {
		this.ecoh_sum = ecoh_sum == null ? zero() : ecoh_sum.setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	public final void setEcoh_stop_date(Date ecoh_stop_date) {
		this.ecoh_stop_date = ecoh_stop_date;
	}

	public final void setEcoh_stop_cause(String ecoh_stop_cause) {
		this.ecoh_stop_cause = ecoh_stop_cause;
	}

	public final void setEcoh_cancel_cause(String ecoh_cancel_cause) {
		this.ecoh_cancel_cause = ecoh_cancel_cause;
	}

	public final void setEcoh_state(Integer ecoh_state) {
		this.ecoh_state = ecoh_state;
	}

	public final void setEcoh_client(String ecoh_client) {
		this.ecoh_client = ecoh_client;
	}

	public final void setEcoh_addname(String ecoh_addname) {
		this.ecoh_addname = ecoh_addname;
	}

	public final void setEcoh_addtime(Date ecoh_addtime) {
		this.ecoh_addtime = ecoh_addtime;
	}

	public final void setEcoh_remark(String ecoh_remark) {
		this.ecoh_remark = ecoh_remark;
	}

	public final void setOwnmonth(Integer ownmonth) {
		this.ownmonth = ownmonth;
	}

	public final void setYf_count(Integer yf_count) {
		this.yf_count = yf_count;
	}

	public final void setYs_count(Integer ys_count) {
		this.ys_count = ys_count;
	}

	public final void setSf_count(Integer sf_count) {
		this.sf_count = sf_count;
	}

	public final void setYf_sum(BigDecimal yf_sum) {
		this.yf_sum = yf_sum == null ? null : yf_sum.setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	public final void setYs_sum(BigDecimal ys_sum) {
		this.ys_sum = ys_sum == null ? null : ys_sum.setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	public final void setSf_sum(BigDecimal sf_sum) {
		this.sf_sum = sf_sum == null ? null : sf_sum.setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	public final void setYf_ys_diff(BigDecimal yf_ys_diff) {
		this.yf_ys_diff = yf_ys_diff == null ? null : yf_ys_diff.setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	public final void setYf_sf_diff(BigDecimal yf_sf_diff) {
		this.yf_sf_diff = yf_sf_diff == null ? null : yf_sf_diff.setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getEmba_name() {
		return emba_name;
	}

	public void setEmba_name(String emba_name) {
		this.emba_name = emba_name;
	}

	public Integer getBjownmonth() {
		return bjownmonth;
	}

	public void setBjownmonth(Integer bjownmonth) {
		this.bjownmonth = bjownmonth;
	}

	public Integer getEfch_id() {
		return efch_id;
	}

	public void setEfch_id(Integer efch_id) {
		this.efch_id = efch_id;
	}

	public Integer getEcpu_id() {
		return ecpu_id;
	}

	public void setEcpu_id(Integer ecpu_id) {
		this.ecpu_id = ecpu_id;
	}
}
