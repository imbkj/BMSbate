package Model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class EmCommissionOutFeeDetailModel {

	// eofd_id
	private Integer eofd_id;
	// eofd_ecou_id
	private Integer eofd_ecou_id;
	// SocialInsuranceClass表外键id(为空则为福利项目)
	private Integer eofd_sicl_id;
	// eofd_em_base
	private BigDecimal eofd_em_base;
	// eofd_co_base
	private BigDecimal eofd_co_base;
	// 企业比例
	private String eofd_cp;
	// 个人比例
	private String eofd_op;
	// eofd_em_sum
	private BigDecimal eofd_em_sum;
	// eofd_co_sum
	private BigDecimal eofd_co_sum;
	// eofd_month_sum
	private BigDecimal eofd_month_sum;
	// 缴费起始日
	private String eofd_start_date;
	// 个人实际缴费起始日
	private String eofd_em_fstart_date;
	// 企业实际缴费起始日
	private String eofd_co_fstart_date;
	// 项目名称
	private String eofd_name;
	// 项目内容
	private String eofd_content;
	private Date eofd_addtime;
	private Integer eofd_state;
	// EmCommissionOutCoOfferListRel外键id
	private Integer eofd_ecop_id;
	// 停缴日
	private String eofd_stop_date;
	// 个人实际停缴日
	private String eofd_em_fstop_date;
	// 企业实际停缴日
	private String eofd_co_fstop_date;

	private String sicl_name;
	private String sicl_class;
	private List<String> cpList;
	private List<String> opList;
	private Date tempDate;
	private boolean isdate;

	public Integer getEofd_id() {
		return eofd_id;
	}

	public void setEofd_id(Integer eofd_id) {
		this.eofd_id = eofd_id;
	}

	public Integer getEofd_ecou_id() {
		return eofd_ecou_id;
	}

	public void setEofd_ecou_id(Integer eofd_ecou_id) {
		this.eofd_ecou_id = eofd_ecou_id;
	}

	public Integer getEofd_sicl_id() {
		return eofd_sicl_id;
	}

	public void setEofd_sicl_id(Integer eofd_sicl_id) {
		this.eofd_sicl_id = eofd_sicl_id;
	}

	public BigDecimal getEofd_em_base() {
		return eofd_em_base;
	}

	public void setEofd_em_base(BigDecimal eofd_em_base) {
		this.eofd_em_base = eofd_em_base == null ? new BigDecimal(0)
				: eofd_em_base.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getEofd_co_base() {
		return eofd_co_base;
	}

	public void setEofd_co_base(BigDecimal eofd_co_base) {
		this.eofd_co_base = eofd_co_base == null ? new BigDecimal(0)
				: eofd_co_base.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public String getEofd_cp() {
		return eofd_cp;
	}

	public void setEofd_cp(String eofd_cp) {
		this.eofd_cp = eofd_cp;
	}

	public String getEofd_op() {
		return eofd_op;
	}

	public void setEofd_op(String eofd_op) {
		this.eofd_op = eofd_op;
	}

	public BigDecimal getEofd_em_sum() {
		return eofd_em_sum;
	}

	public void setEofd_em_sum(BigDecimal eofd_em_sum) {
		this.eofd_em_sum = eofd_em_sum;
	}

	public BigDecimal getEofd_co_sum() {
		return eofd_co_sum;
	}

	public void setEofd_co_sum(BigDecimal eofd_co_sum) {
		this.eofd_co_sum = eofd_co_sum;
	}

	public BigDecimal getEofd_month_sum() {
		return eofd_month_sum;
	}

	public void setEofd_month_sum(BigDecimal eofd_month_sum) {
		this.eofd_month_sum = eofd_month_sum == null ? new BigDecimal(0)
				: eofd_month_sum.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public String getEofd_start_date() {
		return eofd_start_date;
	}

	public void setEofd_start_date(String eofd_start_date) {
		this.eofd_start_date = eofd_start_date;
	}

	public String getSicl_name() {
		return sicl_name;
	}

	public void setSicl_name(String sicl_name) {
		this.sicl_name = sicl_name;
	}

	public List<String> getCpList() {
		return cpList;
	}

	public void setCpList(List<String> cpList) {
		this.cpList = cpList;
	}

	public List<String> getOpList() {
		return opList;
	}

	public void setOpList(List<String> opList) {
		this.opList = opList;
	}

	public String getSicl_class() {
		return sicl_class;
	}

	public void setSicl_class(String sicl_class) {
		this.sicl_class = sicl_class;
	}

	public String getEofd_name() {
		return eofd_name;
	}

	public void setEofd_name(String eofd_name) {
		this.eofd_name = eofd_name;
	}

	public String getEofd_content() {
		return eofd_content;
	}

	public void setEofd_content(String eofd_content) {
		this.eofd_content = eofd_content;
	}

	public Date getTempDate() {
		return tempDate;
	}

	public void setTempDate(Date tempDate) {
		this.tempDate = tempDate;
	}

	public Date getEofd_addtime() {
		return eofd_addtime;
	}

	public void setEofd_addtime(Date eofd_addtime) {
		this.eofd_addtime = eofd_addtime;
	}

	public Integer getEofd_state() {
		return eofd_state;
	}

	public void setEofd_state(Integer eofd_state) {
		this.eofd_state = eofd_state;
	}

	public Integer getEofd_ecop_id() {
		return eofd_ecop_id;
	}

	public void setEofd_ecop_id(Integer eofd_ecop_id) {
		this.eofd_ecop_id = eofd_ecop_id;
	}

	public boolean isIsdate() {
		return isdate;
	}

	public void setIsdate(boolean isdate) {
		this.isdate = isdate;
	}

	public String getEofd_stop_date() {
		return eofd_stop_date;
	}

	public void setEofd_stop_date(String eofd_stop_date) {
		this.eofd_stop_date = eofd_stop_date;
	}

	public String getEofd_co_fstart_date() {
		return eofd_co_fstart_date;
	}

	public void setEofd_co_fstart_date(String eofd_co_fstart_date) {
		this.eofd_co_fstart_date = eofd_co_fstart_date;
	}

	public String getEofd_em_fstart_date() {
		return eofd_em_fstart_date;
	}

	public void setEofd_em_fstart_date(String eofd_em_fstart_date) {
		this.eofd_em_fstart_date = eofd_em_fstart_date;
	}

	public String getEofd_co_fstop_date() {
		return eofd_co_fstop_date;
	}

	public void setEofd_co_fstop_date(String eofd_co_fstop_date) {
		this.eofd_co_fstop_date = eofd_co_fstop_date;
	}

	public String getEofd_em_fstop_date() {
		return eofd_em_fstop_date;
	}

	public void setEofd_em_fstop_date(String eofd_em_fstop_date) {
		this.eofd_em_fstop_date = eofd_em_fstop_date;
	}
}
