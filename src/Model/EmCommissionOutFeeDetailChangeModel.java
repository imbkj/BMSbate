package Model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Util.RegexUtil;

public class EmCommissionOutFeeDetailChangeModel {
	// eofc_id
	private Integer eofc_id;
	// eofc_ecoc_id
	private Integer eofc_ecoc_id;
	// eofc_ecou_id
	private Integer eofc_eofd_id;
	// SocialInsuranceClass表外键id(为空则为福利项目)
	private Integer eofc_sicl_id;
	// eofc_em_base
	private BigDecimal eofc_em_base;
	// eofc_co_base
	private BigDecimal eofc_co_base;
	// eofc_cp
	private String eofc_cp;
	// eofc_op
	private String eofc_op;
	// eofc_em_sum
	private BigDecimal eofc_em_sum;
	// eofc_co_sum
	private BigDecimal eofc_co_sum;
	// eofc_month_sum
	private BigDecimal eofc_month_sum;
	// 起始日
	private String eofc_start_date;
	// 个人实际缴费起始日
	private String eofc_em_fstart_date;
	// 企业实际缴费起始日
	private String eofc_co_fstart_date;
	// 项目名称
	private String eofc_name;
	// 项目内容
	private String eofc_content;
	private Date eofc_addtime;
	private Integer eofc_state;
	// EmCommissionOutCoOfferListRel外键id
	private Integer eofc_ecop_id;
	// 停缴日
	private String eofc_stop_date;
	// 个人实际停缴日
	private String eofc_em_fstop_date;
	// 企业实际停缴日
	private String eofc_co_fstop_date;

	private String sicl_name;
	private String sicl_class;
	private List<String> cpList;
	private List<String> opList;
	private Date tempDate;
	private Date tempDate1;
	private boolean ischange;
	private boolean isdate;

	// 基数上限
	private BigDecimal siai_basic_u;
	// 基数下限
	private BigDecimal siai_basic_d;
	// 最高缴存额
	private BigDecimal siai_deposit_u;
	// 最低缴存额
	private BigDecimal siai_deposit_d;
	// 比例
	private String siai_proportion;

	/**
	 * 比例处理
	 * 
	 */
	public List<String> CppHandle(String cpp) {
		List<String> list = new ListModelList<>();
		if (cpp != null && !cpp.isEmpty()) {
			if (RegexUtil.isExists(",", cpp)) {
				String[] strings = cpp.split(",");
				for (String str : strings) {
					list.add(str);
				}
			} else if (RegexUtil.isExists("-", cpp)) {
				String[] strs = cpp.split("-");
				Integer step;
				String[] strs1 = strs[0].split("\\.");
				String[] strs2 = strs[1].split("\\.");
				if (strs1[1].length() > 1 || strs2[1].length() > 1) {
					step = 1;
				} else {
					step = 10;
				}
				for (Double i = Double.parseDouble(strs[0]) * 100; i <= Double
						.parseDouble(strs[1]) * 100; i = i + step) {
					list.add((i / 100) + "");
				}
			} else {
				list.add(cpp);
			}
		} else {
			list.add("");
		}
		return list;
	}

	public void initcpList(String str) {
		cpList = CppHandle(str);
		eofc_cp ="0";
	}

	public void initopList(String str) {
		opList = CppHandle(str);
		eofc_op ="0";
	}

	public final BigDecimal getSiai_basic_u() {
		return siai_basic_u;
	}

	public final BigDecimal getSiai_basic_d() {
		return siai_basic_d;
	}

	public final BigDecimal getSiai_deposit_u() {
		return siai_deposit_u;
	}

	public final BigDecimal getSiai_deposit_d() {
		return siai_deposit_d;
	}

	public final String getSiai_proportion() {
		return siai_proportion;
	}

	public final void setSiai_basic_u(BigDecimal siai_basic_u) {
		this.siai_basic_u = siai_basic_u == null ? BigDecimal.ZERO
				: siai_basic_u.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setSiai_basic_d(BigDecimal siai_basic_d) {
		this.siai_basic_d = siai_basic_d == null ? BigDecimal.ZERO
				: siai_basic_d.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setSiai_deposit_u(BigDecimal siai_deposit_u) {
		this.siai_deposit_u = siai_deposit_u == null ? BigDecimal.ZERO
				: siai_deposit_u.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setSiai_deposit_d(BigDecimal siai_deposit_d) {
		this.siai_deposit_d = siai_deposit_d == null ? BigDecimal.ZERO
				: siai_deposit_d.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setSiai_proportion(String siai_proportion) {
		this.siai_proportion = siai_proportion;
	}

	public final Integer getEofc_id() {
		return eofc_id;
	}

	public final Integer getEofc_ecoc_id() {
		return eofc_ecoc_id;
	}

	public final Integer getEofc_sicl_id() {
		return eofc_sicl_id;
	}

	public final BigDecimal getEofc_em_base() {
		return eofc_em_base;
	}

	public final BigDecimal getEofc_co_base() {
		return eofc_co_base;
	}

	public final String getEofc_cp() {
		return eofc_cp;
	}

	public final String getEofc_op() {
		return eofc_op;
	}

	public final BigDecimal getEofc_em_sum() {
		return eofc_em_sum;
	}

	public final BigDecimal getEofc_co_sum() {
		return eofc_co_sum;
	}

	public final BigDecimal getEofc_month_sum() {
		return eofc_month_sum;
	}

	public final String getEofc_name() {
		return eofc_name;
	}

	public final String getEofc_content() {
		return eofc_content;
	}

	public final Date getEofc_addtime() {
		return eofc_addtime;
	}

	public final Integer getEofc_state() {
		return eofc_state;
	}

	public final String getSicl_name() {
		return sicl_name;
	}

	public final String getSicl_class() {
		return sicl_class;
	}

	public final List<String> getCpList() {
		return cpList;
	}

	public final List<String> getOpList() {
		return opList;
	}

	public final Date getTempDate() {
		return tempDate;
	}

	public final void setEofc_id(Integer eofc_id) {
		this.eofc_id = eofc_id;
	}

	public final void setEofc_ecoc_id(Integer eofc_ecoc_id) {
		this.eofc_ecoc_id = eofc_ecoc_id;
	}

	public final void setEofc_sicl_id(Integer eofc_sicl_id) {
		this.eofc_sicl_id = eofc_sicl_id;
	}

	public final void setEofc_em_base(BigDecimal eofc_em_base) {
		this.eofc_em_base = eofc_em_base == null ? new BigDecimal(0)
				: eofc_em_base.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setEofc_co_base(BigDecimal eofc_co_base) {
		this.eofc_co_base = eofc_co_base == null ? new BigDecimal(0)
				: eofc_co_base.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setEofc_cp(String eofc_cp) {
		this.eofc_cp = eofc_cp;
	}

	public final void setEofc_op(String eofc_op) {
		this.eofc_op = eofc_op;
	}

	public final void setEofc_em_sum(BigDecimal eofc_em_sum) {
		this.eofc_em_sum = eofc_em_sum;
	}

	public final void setEofc_co_sum(BigDecimal eofc_co_sum) {
		this.eofc_co_sum = eofc_co_sum;
	}

	public final void setEofc_month_sum(BigDecimal eofc_month_sum) {
		this.eofc_month_sum = eofc_month_sum == null ? new BigDecimal(0)
				: eofc_month_sum.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setEofc_name(String eofc_name) {
		this.eofc_name = eofc_name;
	}

	public final void setEofc_content(String eofc_content) {
		this.eofc_content = eofc_content;
	}

	public final void setEofc_addtime(Date eofc_addtime) {
		this.eofc_addtime = eofc_addtime;
	}

	public final void setEofc_state(Integer eofc_state) {
		this.eofc_state = eofc_state;
	}

	public final void setSicl_name(String sicl_name) {
		this.sicl_name = sicl_name;
	}

	public final void setSicl_class(String sicl_class) {
		this.sicl_class = sicl_class;
	}

	public final void setCpList(List<String> cpList) {
		this.cpList = cpList;
	}

	public final void setOpList(List<String> opList) {
		this.opList = opList;
	}

	public final void setTempDate(Date tempDate) {
		this.tempDate = tempDate;
	}

	public Integer getEofc_eofd_id() {
		return eofc_eofd_id;
	}

	public void setEofc_eofd_id(Integer eofc_eofd_id) {
		this.eofc_eofd_id = eofc_eofd_id;
	}

	public boolean isIschange() {
		return ischange;
	}

	public void setIschange(boolean ischange) {
		this.ischange = ischange;
	}

	public boolean isIsdate() {
		return isdate;
	}

	public void setIsdate(boolean isdate) {
		this.isdate = isdate;
	}

	public Integer getEofc_ecop_id() {
		return eofc_ecop_id;
	}

	public void setEofc_ecop_id(Integer eofc_ecop_id) {
		this.eofc_ecop_id = eofc_ecop_id;
	}

	public String getEofc_em_fstart_date() {
		return eofc_em_fstart_date;
	}

	public void setEofc_em_fstart_date(String eofc_em_fstart_date) {
		this.eofc_em_fstart_date = eofc_em_fstart_date;
	}

	public String getEofc_em_fstop_date() {
		return eofc_em_fstop_date;
	}

	public void setEofc_em_fstop_date(String eofc_em_fstop_date) {
		this.eofc_em_fstop_date = eofc_em_fstop_date;
	}

	public String getEofc_co_fstop_date() {
		return eofc_co_fstop_date;
	}

	public void setEofc_co_fstop_date(String eofc_co_fstop_date) {
		this.eofc_co_fstop_date = eofc_co_fstop_date;
	}

	public String getEofc_co_fstart_date() {
		return eofc_co_fstart_date;
	}

	public void setEofc_co_fstart_date(String eofc_co_fstart_date) {
		this.eofc_co_fstart_date = eofc_co_fstart_date;
	}

	public String getEofc_start_date() {
		return eofc_start_date;
	}

	public void setEofc_start_date(String eofc_start_date) {
		this.eofc_start_date = eofc_start_date;
	}

	public String getEofc_stop_date() {
		return eofc_stop_date;
	}

	public void setEofc_stop_date(String eofc_stop_date) {
		this.eofc_stop_date = eofc_stop_date;
	}

	public Date getTempDate1() {
		return tempDate1;
	}

	public void setTempDate1(Date tempDate1) {
		this.tempDate1 = tempDate1;
	}
}
