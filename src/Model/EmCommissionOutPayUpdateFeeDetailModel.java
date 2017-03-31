package Model;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Util.plyUtil;

public class EmCommissionOutPayUpdateFeeDetailModel {
	// epfd_id
	private Integer epfd_id;
	// epfd_ecpu_id
	private Integer epfd_ecpu_id;
	// SocialInsuranceClass表外键id(为0则为福利项目)
	private Integer epfd_sicl_id;
	// EmCommissionOutCoOfferListRel外键id
	private Integer epfd_ecop_id;
	// 项目名称
	private String epfd_name;
	// 项目内容
	private String epfd_content;
	// epfd_base
	private BigDecimal epfd_base;
	// epfd_cp
	private String epfd_cp;
	// epfd_op
	private String epfd_op;
	// epfd_em_sum
	private BigDecimal epfd_em_sum;
	// epfd_co_sum
	private BigDecimal epfd_co_sum;
	// epfd_month_sum
	private BigDecimal epfd_month_sum;
	// 缴费起始日
	private String epfd_start_date;
	// 个人实际缴费起始日
	private String epfd_em_fstart_date;
	// 企业实际起始日
	private String epfd_co_fstart_date;
	// 停缴日
	private String epfd_stop_date;
	// 个人实际停缴日
	private String epfd_em_fstop_date;
	// 企业实际停缴日
	private String epfd_co_fstop_date;
	// epfd_addtime
	private Date epfd_addtime;
	// epfd_state
	private Integer epfd_state;

	private List<Object> objsList = new ListModelList<>();

	// 调用Set方法
	public void setField(String fieldname, Object value, Class<?> class1) {
		try {
			Method method = this.getClass().getMethod(
					plyUtil.setMethod(fieldname), class1);
			method.invoke(this, value);
		} catch (Exception e) {
			System.out.println(e.toString() + ":" + fieldname);
		}
	}

	// 调用get方法
	public Object getField(String fieldname) {
		Object obj = null;
		try {
			Method method = this.getClass().getMethod(
					plyUtil.getMethod(fieldname));
			obj = method.invoke(this);
		} catch (Exception e) {
			System.out.println(e.toString() + ":" + fieldname);
		}
		return obj;
	}

	public final Integer getEpfd_id() {
		return epfd_id;
	}

	public final Integer getEpfd_ecpu_id() {
		return epfd_ecpu_id;
	}

	public final Integer getEpfd_sicl_id() {
		return epfd_sicl_id;
	}

	public final Integer getEpfd_ecop_id() {
		return epfd_ecop_id;
	}

	public final String getEpfd_name() {
		return epfd_name;
	}

	public final String getEpfd_content() {
		return epfd_content;
	}

	public final String getEpfd_cp() {
		return epfd_cp;
	}

	public final String getEpfd_op() {
		return epfd_op;
	}

	public final BigDecimal getEpfd_em_sum() {
		return epfd_em_sum;
	}

	public final BigDecimal getEpfd_co_sum() {
		return epfd_co_sum;
	}

	public final BigDecimal getEpfd_month_sum() {
		return epfd_month_sum;
	}

	public final String getEpfd_start_date() {
		return epfd_start_date;
	}

	public final String getEpfd_em_fstart_date() {
		return epfd_em_fstart_date;
	}

	public final String getEpfd_co_fstart_date() {
		return epfd_co_fstart_date;
	}

	public final String getEpfd_stop_date() {
		return epfd_stop_date;
	}

	public final String getEpfd_em_fstop_date() {
		return epfd_em_fstop_date;
	}

	public final String getEpfd_co_fstop_date() {
		return epfd_co_fstop_date;
	}

	public final Date getEpfd_addtime() {
		return epfd_addtime;
	}

	public final Integer getEpfd_state() {
		return epfd_state;
	}

	public final void setEpfd_id(Integer epfd_id) {
		this.epfd_id = epfd_id;
	}

	public final void setEpfd_ecpu_id(Integer epfd_ecpu_id) {
		this.epfd_ecpu_id = epfd_ecpu_id;
	}

	public final void setEpfd_sicl_id(Integer epfd_sicl_id) {
		this.epfd_sicl_id = epfd_sicl_id;
	}

	public final void setEpfd_ecop_id(Integer epfd_ecop_id) {
		this.epfd_ecop_id = epfd_ecop_id;
	}

	public final void setEpfd_name(String epfd_name) {
		this.epfd_name = epfd_name;
	}

	public final void setEpfd_content(String epfd_content) {
		this.epfd_content = epfd_content;
	}

	public final void setEpfd_cp(String epfd_cp) {
		this.epfd_cp = epfd_cp;
	}

	public final void setEpfd_op(String epfd_op) {
		this.epfd_op = epfd_op;
	}

	public final void setEpfd_em_sum(BigDecimal epfd_em_sum) {
		this.epfd_em_sum = epfd_em_sum == null ? null : epfd_em_sum.setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	public final void setEpfd_co_sum(BigDecimal epfd_co_sum) {
		this.epfd_co_sum = epfd_co_sum == null ? null : epfd_co_sum.setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	public final void setEpfd_month_sum(BigDecimal epfd_month_sum) {
		this.epfd_month_sum = epfd_month_sum == null ? null : epfd_month_sum
				.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setEpfd_start_date(String epfd_start_date) {
		this.epfd_start_date = epfd_start_date;
	}

	public final void setEpfd_em_fstart_date(String epfd_em_fstart_date) {
		this.epfd_em_fstart_date = epfd_em_fstart_date;
	}

	public final void setEpfd_co_fstart_date(String epfd_co_fstart_date) {
		this.epfd_co_fstart_date = epfd_co_fstart_date;
	}

	public final void setEpfd_stop_date(String epfd_stop_date) {
		this.epfd_stop_date = epfd_stop_date;
	}

	public final void setEpfd_em_fstop_date(String epfd_em_fstop_date) {
		this.epfd_em_fstop_date = epfd_em_fstop_date;
	}

	public final void setEpfd_co_fstop_date(String epfd_co_fstop_date) {
		this.epfd_co_fstop_date = epfd_co_fstop_date;
	}

	public final void setEpfd_addtime(Date epfd_addtime) {
		this.epfd_addtime = epfd_addtime;
	}

	public final void setEpfd_state(Integer epfd_state) {
		this.epfd_state = epfd_state;
	}

	public BigDecimal getEpfd_base() {
		return epfd_base;
	}

	public void setEpfd_base(BigDecimal epfd_base) {
		this.epfd_base = epfd_base == null ? null : epfd_base.setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	public List<Object> getObjsList() {
		return objsList;
	}

	public void setObjsList(List<Object> objsList) {
		this.objsList = objsList;
	}
}
