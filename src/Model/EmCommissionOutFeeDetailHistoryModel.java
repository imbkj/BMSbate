package Model;

import java.math.BigDecimal;
import java.util.Date;

public class EmCommissionOutFeeDetailHistoryModel {
	// eofh_id
	private Integer eofh_id;
	// eofh_ecoh_id
	private Integer eofh_ecoh_id;
	// SocialInsuranceClass表外键id(为0则为福利项目)
	private Integer eofh_sicl_id;
	// EmCommissionOutCoOfferListRel外键id
	private Integer eofh_ecop_id;
	// 项目名称
	private String eofh_name;
	// 项目内容
	private String eofh_content;
	// eofh_em_base
	private BigDecimal eofh_em_base;
	// eofh_co_base
	private BigDecimal eofh_co_base;
	// eofh_cp
	private String eofh_cp;
	// eofh_op
	private String eofh_op;
	// eofh_em_sum
	private BigDecimal eofh_em_sum;
	// eofh_co_sum
	private BigDecimal eofh_co_sum;
	// eofh_month_sum
	private BigDecimal eofh_month_sum;
	// 缴费起始日
	private String eofh_start_date;
	// 个人实际缴费起始日
	private String eofh_em_fstart_date;
	// 企业实际起始日
	private String eofh_co_fstart_date;
	// 停缴日
	private String eofh_stop_date;
	// 个人实际停缴日
	private String eofh_em_fstop_date;
	// 企业实际停缴日
	private String eofh_co_fstop_date;
	// eofh_addtime
	private Date eofh_addtime;
	// eofh_state
	private Integer eofh_state;

	// 外地账单
	private Integer epfd_id;
	private Integer epfd_ecpu_id;

	// 五险一金台账表
	private Integer efcd_id;
	private Integer efch_id;
	private Integer efcd_efco_id;
	private Integer efch_efco_id;
	private Integer efcd_eofd_sicl_id;

	// 福利台账表
	private Integer efcp_id;
	private Integer efph_id;
	private Integer efcp_efco_id;
	private Integer efph_efco_id;
	private Integer efcp_eofd_ecop_id;

	// 实付
	private BigDecimal epfd_month_sum;
	// 应收
	private BigDecimal efcd_eofd_month_sum;

	// 差额(应付-应收)
	private BigDecimal yf_ys_diff;
	// 差额(应付-实付)
	private BigDecimal yf_sf_diff;
	
	private String sicl_class;
	
	private Date tempDate;

	
	
	public Date getTempDate() {
		return tempDate;
	}

	public void setTempDate(Date tempDate) {
		this.tempDate = tempDate;
	}

	public String getSicl_class() {
		return sicl_class;
	}

	public void setSicl_class(String sicl_class) {
		this.sicl_class = sicl_class;
	}

	public final Integer getEpfd_id() {
		return epfd_id;
	}

	public final Integer getEpfd_ecpu_id() {
		return epfd_ecpu_id;
	}

	public final Integer getEfcd_id() {
		return efcd_id;
	}

	public final Integer getEfch_id() {
		return efch_id;
	}

	public final Integer getEfcd_efco_id() {
		return efcd_efco_id;
	}

	public final Integer getEfch_efco_id() {
		return efch_efco_id;
	}

	public final Integer getEfcd_eofd_sicl_id() {
		return efcd_eofd_sicl_id;
	}

	public final Integer getEfcp_id() {
		return efcp_id;
	}

	public final Integer getEfph_id() {
		return efph_id;
	}

	public final Integer getEfcp_efco_id() {
		return efcp_efco_id;
	}

	public final Integer getEfph_efco_id() {
		return efph_efco_id;
	}

	public final Integer getEfcp_eofd_ecop_id() {
		return efcp_eofd_ecop_id;
	}

	public final BigDecimal getEpfd_month_sum() {
		return epfd_month_sum;
	}

	public final BigDecimal getEfcd_eofd_month_sum() {
		return efcd_eofd_month_sum;
	}

	public final BigDecimal getYf_ys_diff() {
		return yf_ys_diff;
	}

	public final BigDecimal getYf_sf_diff() {
		return yf_sf_diff;
	}

	public final void setEpfd_id(Integer epfd_id) {
		this.epfd_id = epfd_id;
	}

	public final void setEpfd_ecpu_id(Integer epfd_ecpu_id) {
		this.epfd_ecpu_id = epfd_ecpu_id;
	}

	public final void setEfcd_id(Integer efcd_id) {
		this.efcd_id = efcd_id;
	}

	public final void setEfch_id(Integer efch_id) {
		this.efch_id = efch_id;
	}

	public final void setEfcd_efco_id(Integer efcd_efco_id) {
		this.efcd_efco_id = efcd_efco_id;
	}

	public final void setEfch_efco_id(Integer efch_efco_id) {
		this.efch_efco_id = efch_efco_id;
	}

	public final void setEfcd_eofd_sicl_id(Integer efcd_eofd_sicl_id) {
		this.efcd_eofd_sicl_id = efcd_eofd_sicl_id;
	}

	public final void setEfcp_id(Integer efcp_id) {
		this.efcp_id = efcp_id;
	}

	public final void setEfph_id(Integer efph_id) {
		this.efph_id = efph_id;
	}

	public final void setEfcp_efco_id(Integer efcp_efco_id) {
		this.efcp_efco_id = efcp_efco_id;
	}

	public final void setEfph_efco_id(Integer efph_efco_id) {
		this.efph_efco_id = efph_efco_id;
	}

	public final void setEfcp_eofd_ecop_id(Integer efcp_eofd_ecop_id) {
		this.efcp_eofd_ecop_id = efcp_eofd_ecop_id;
	}

	public final void setEpfd_month_sum(BigDecimal epfd_month_sum) {
		this.epfd_month_sum = epfd_month_sum == null ? null : epfd_month_sum
				.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setEfcd_eofd_month_sum(BigDecimal efcd_eofd_month_sum) {
		this.efcd_eofd_month_sum = efcd_eofd_month_sum == null ? null
				: efcd_eofd_month_sum.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setYf_ys_diff(BigDecimal yf_ys_diff) {
		this.yf_ys_diff = yf_ys_diff == null ? null : yf_ys_diff.setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	public final void setYf_sf_diff(BigDecimal yf_sf_diff) {
		this.yf_sf_diff = yf_sf_diff == null ? null : yf_sf_diff.setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	public final Integer getEofh_id() {
		return eofh_id;
	}

	public final void setEofh_id(Integer eofh_id) {
		this.eofh_id = eofh_id;
	}

	public final Integer getEofh_ecoh_id() {
		return eofh_ecoh_id;
	}

	public final void setEofh_ecoh_id(Integer eofh_ecoh_id) {
		this.eofh_ecoh_id = eofh_ecoh_id;
	}

	public final Integer getEofh_sicl_id() {
		return eofh_sicl_id;
	}

	public final void setEofh_sicl_id(Integer eofh_sicl_id) {
		this.eofh_sicl_id = eofh_sicl_id;
	}

	public final Integer getEofh_ecop_id() {
		return eofh_ecop_id;
	}

	public final void setEofh_ecop_id(Integer eofh_ecop_id) {
		this.eofh_ecop_id = eofh_ecop_id;
	}

	public final String getEofh_name() {
		return eofh_name;
	}

	public final void setEofh_name(String eofh_name) {
		this.eofh_name = eofh_name;
	}

	public final String getEofh_content() {
		return eofh_content;
	}

	public final void setEofh_content(String eofh_content) {
		this.eofh_content = eofh_content;
	}

	public final BigDecimal getEofh_em_base() {
		return eofh_em_base;
	}

	public final void setEofh_em_base(BigDecimal eofh_em_base) {
		this.eofh_em_base = eofh_em_base == null ? null : eofh_em_base
				.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final BigDecimal getEofh_co_base() {
		return eofh_co_base;
	}

	public final void setEofh_co_base(BigDecimal eofh_co_base) {
		this.eofh_co_base = eofh_co_base == null ? null : eofh_co_base
				.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final String getEofh_cp() {
		return eofh_cp;
	}

	public final void setEofh_cp(String eofh_cp) {
		this.eofh_cp = eofh_cp;
	}

	public final String getEofh_op() {
		return eofh_op;
	}

	public final void setEofh_op(String eofh_op) {
		this.eofh_op = eofh_op;
	}

	public final BigDecimal getEofh_em_sum() {
		return eofh_em_sum;
	}

	public final void setEofh_em_sum(BigDecimal eofh_em_sum) {
		this.eofh_em_sum = eofh_em_sum == null ? null : eofh_em_sum.setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	public final BigDecimal getEofh_co_sum() {
		return eofh_co_sum;
	}

	public final void setEofh_co_sum(BigDecimal eofh_co_sum) {
		this.eofh_co_sum = eofh_co_sum == null ? null : eofh_co_sum.setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	public final BigDecimal getEofh_month_sum() {
		return eofh_month_sum;
	}

	public final void setEofh_month_sum(BigDecimal eofh_month_sum) {
		this.eofh_month_sum = eofh_month_sum == null ? null : eofh_month_sum
				.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final String getEofh_start_date() {
		return eofh_start_date;
	}

	public final void setEofh_start_date(String eofh_start_date) {
		this.eofh_start_date = eofh_start_date;
	}

	public final String getEofh_em_fstart_date() {
		return eofh_em_fstart_date;
	}

	public final void setEofh_em_fstart_date(String eofh_em_fstart_date) {
		this.eofh_em_fstart_date = eofh_em_fstart_date;
	}

	public final String getEofh_co_fstart_date() {
		return eofh_co_fstart_date;
	}

	public final void setEofh_co_fstart_date(String eofh_co_fstart_date) {
		this.eofh_co_fstart_date = eofh_co_fstart_date;
	}

	public final String getEofh_stop_date() {
		return eofh_stop_date;
	}

	public final void setEofh_stop_date(String eofh_stop_date) {
		this.eofh_stop_date = eofh_stop_date;
	}

	public final String getEofh_em_fstop_date() {
		return eofh_em_fstop_date;
	}

	public final void setEofh_em_fstop_date(String eofh_em_fstop_date) {
		this.eofh_em_fstop_date = eofh_em_fstop_date;
	}

	public final String getEofh_co_fstop_date() {
		return eofh_co_fstop_date;
	}

	public final void setEofh_co_fstop_date(String eofh_co_fstop_date) {
		this.eofh_co_fstop_date = eofh_co_fstop_date;
	}

	public final Date getEofh_addtime() {
		return eofh_addtime;
	}

	public final void setEofh_addtime(Date eofh_addtime) {
		this.eofh_addtime = eofh_addtime;
	}

	public final Integer getEofh_state() {
		return eofh_state;
	}

	public final void setEofh_state(Integer eofh_state) {
		this.eofh_state = eofh_state;
	}
}
