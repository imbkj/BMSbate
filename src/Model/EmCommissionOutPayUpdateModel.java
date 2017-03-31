package Model;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Util.plyUtil;

public class EmCommissionOutPayUpdateModel {
	// ecpu_id
	private Integer ecpu_id;
	// 公司编号
	private Integer cid;
	// 员工编号
	private Integer gid;
	// 公司名称
	private String ecpu_company;
	// 员工姓名
	private String ecpu_name;
	// 委托机构关系表id
	private Integer ecpu_cabc_id;
	// 身份证号
	private String ecpu_idcard;
	// 户籍
	private String ecpu_hk;
	// 月份
	private Integer ownmonth;
	// 补缴月份
	private Integer bjownmonth;
	// 类型(正常、补缴)
	private String ecpu_change;
	// 利息
	private BigDecimal ecpu_lx;
	// 社保基数
	private BigDecimal ecpu_base;
	// 社保公司合计
	private BigDecimal ecpu_sb_co_total;
	// 社保个人合计
	private BigDecimal ecpu_sb_em_total;
	// 养老企业合计
	private BigDecimal ecpu_yl_co_total;
	// 养老个人合计
	private BigDecimal ecpu_yl_em_total;
	// 失业企业合计
	private BigDecimal ecpu_shy_em_total;
	// 失业个人合计
	private BigDecimal ecpu_shy_co_total;
	// 医疗企业合计
	private BigDecimal ecpu_yil_co_total;
	// 医疗个人合计
	private BigDecimal ecpu_yil_em_total;
	// 补充医疗企业合计
	private BigDecimal ecpu_bchyil_co_total;
	// 补充医疗个人合计
	private BigDecimal ecpu_bchyil_em_total;
	// 生育企业合计
	private BigDecimal ecpu_shyu_co_total;
	// 生育个人合计
	private BigDecimal ecpu_shyu_em_total;
	// 工伤企业合计
	private BigDecimal ecpu_gsh_co_total;
	// 工伤个人合计
	private BigDecimal ecpu_gsh_em_total;
	// 住房企业合计
	private BigDecimal ecpu_gjj_co_total;
	// 住房个人合计
	private BigDecimal ecpu_gjj_em_total;
	// 补充住房企业合计
	private BigDecimal ecpu_bchgjj_co_total;
	// 补充住房个人合计
	private BigDecimal ecpu_bchgjj_em_total;
	// 社保合计
	private BigDecimal ecpu_sb_total;
	// 住房合计
	private BigDecimal ecpu_gjj_total;
	//补充住房
	private BigDecimal ecpu_bcgjj_total;
	// 其他金额
	private BigDecimal ecpu_other_total;
	// 福利合计
	private BigDecimal ecpu_welfare_total;
	// 总计
	private BigDecimal ecpu_total;
	// ecpu_state
	private Integer ecpu_state;
	// ecpu_addtime
	private Date ecpu_addtime;
	// ecpu_addname
	private String ecpu_addname;
	// ecpu_remark
	private String ecpu_remark;
	// 客服
	private String ecpu_client;

	private String city;
	private Integer ppc_id;
	private String coab_name;

	private List<Object> objsList = new ListModelList<>();

	private List<EmCommissionOutPayUpdateFeeDetailModel> feeList = new ListModelList<>();

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

	public final BigDecimal getEcpu_base() {
		return ecpu_base;
	}

	public final BigDecimal getEcpu_sb_co_total() {
		return ecpu_sb_co_total;
	}

	public final BigDecimal getEcpu_sb_em_total() {
		return ecpu_sb_em_total;
	}

	public final BigDecimal getEcpu_yl_co_total() {
		return ecpu_yl_co_total;
	}

	public final BigDecimal getEcpu_yl_em_total() {
		return ecpu_yl_em_total;
	}

	public final BigDecimal getEcpu_shy_em_total() {
		return ecpu_shy_em_total;
	}

	public final BigDecimal getEcpu_shy_co_total() {
		return ecpu_shy_co_total;
	}

	public final BigDecimal getEcpu_yil_co_total() {
		return ecpu_yil_co_total;
	}

	public final BigDecimal getEcpu_yil_em_total() {
		return ecpu_yil_em_total;
	}

	public final BigDecimal getEcpu_bchyil_co_total() {
		return ecpu_bchyil_co_total;
	}

	public final BigDecimal getEcpu_bchyil_em_total() {
		return ecpu_bchyil_em_total;
	}

	public final BigDecimal getEcpu_shyu_co_total() {
		return ecpu_shyu_co_total;
	}

	public final BigDecimal getEcpu_shyu_em_total() {
		return ecpu_shyu_em_total;
	}

	public final BigDecimal getEcpu_gsh_co_total() {
		return ecpu_gsh_co_total;
	}

	public final BigDecimal getEcpu_gsh_em_total() {
		return ecpu_gsh_em_total;
	}

	public final BigDecimal getEcpu_gjj_co_total() {
		return ecpu_gjj_co_total;
	}

	public final BigDecimal getEcpu_gjj_em_total() {
		return ecpu_gjj_em_total;
	}

	public final BigDecimal getEcpu_bchgjj_co_total() {
		return ecpu_bchgjj_co_total;
	}

	public final BigDecimal getEcpu_bchgjj_em_total() {
		return ecpu_bchgjj_em_total;
	}

	public final void setEcpu_base(BigDecimal ecpu_base) {
		this.ecpu_base = ecpu_base == null ? null : ecpu_base.setScale(2,
				BigDecimal.ROUND_HALF_UP);
		;
	}

	public final void setEcpu_sb_co_total(BigDecimal ecpu_sb_co_total) {
		this.ecpu_sb_co_total = ecpu_sb_co_total == null ? null
				: ecpu_sb_co_total.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setEcpu_sb_em_total(BigDecimal ecpu_sb_em_total) {
		this.ecpu_sb_em_total = ecpu_sb_em_total == null ? null
				: ecpu_sb_em_total.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setEcpu_yl_co_total(BigDecimal ecpu_yl_co_total) {
		this.ecpu_yl_co_total = ecpu_yl_co_total == null ? null
				: ecpu_yl_co_total.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setEcpu_yl_em_total(BigDecimal ecpu_yl_em_total) {
		this.ecpu_yl_em_total = ecpu_yl_em_total == null ? null
				: ecpu_yl_em_total.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setEcpu_shy_em_total(BigDecimal ecpu_shy_em_total) {
		this.ecpu_shy_em_total = ecpu_shy_em_total == null ? null
				: ecpu_shy_em_total.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setEcpu_shy_co_total(BigDecimal ecpu_shy_co_total) {
		this.ecpu_shy_co_total = ecpu_shy_co_total == null ? null
				: ecpu_shy_co_total.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setEcpu_yil_co_total(BigDecimal ecpu_yil_co_total) {
		this.ecpu_yil_co_total = ecpu_yil_co_total == null ? null
				: ecpu_yil_co_total.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setEcpu_yil_em_total(BigDecimal ecpu_yil_em_total) {
		this.ecpu_yil_em_total = ecpu_yil_em_total == null ? null
				: ecpu_yil_em_total.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setEcpu_bchyil_co_total(BigDecimal ecpu_bchyil_co_total) {
		this.ecpu_bchyil_co_total = ecpu_bchyil_co_total == null ? null
				: ecpu_bchyil_co_total.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setEcpu_bchyil_em_total(BigDecimal ecpu_bchyil_em_total) {
		this.ecpu_bchyil_em_total = ecpu_bchyil_em_total == null ? null
				: ecpu_bchyil_em_total.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setEcpu_shyu_co_total(BigDecimal ecpu_shyu_co_total) {
		this.ecpu_shyu_co_total = ecpu_shyu_co_total == null ? null
				: ecpu_shyu_co_total.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setEcpu_shyu_em_total(BigDecimal ecpu_shyu_em_total) {
		this.ecpu_shyu_em_total = ecpu_shyu_em_total == null ? null
				: ecpu_shyu_em_total.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setEcpu_gsh_co_total(BigDecimal ecpu_gsh_co_total) {
		this.ecpu_gsh_co_total = ecpu_gsh_co_total == null ? null
				: ecpu_gsh_co_total.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setEcpu_gsh_em_total(BigDecimal ecpu_gsh_em_total) {
		this.ecpu_gsh_em_total = ecpu_gsh_em_total == null ? null
				: ecpu_gsh_em_total.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setEcpu_gjj_co_total(BigDecimal ecpu_gjj_co_total) {
		this.ecpu_gjj_co_total = ecpu_gjj_co_total == null ? null
				: ecpu_gjj_co_total.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setEcpu_gjj_em_total(BigDecimal ecpu_gjj_em_total) {
		this.ecpu_gjj_em_total = ecpu_gjj_em_total == null ? null
				: ecpu_gjj_em_total.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setEcpu_bchgjj_co_total(BigDecimal ecpu_bchgjj_co_total) {
		this.ecpu_bchgjj_co_total = ecpu_bchgjj_co_total == null ? null
				: ecpu_bchgjj_co_total.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final void setEcpu_bchgjj_em_total(BigDecimal ecpu_bchgjj_em_total) {
		this.ecpu_bchgjj_em_total = ecpu_bchgjj_em_total == null ? null
				: ecpu_bchgjj_em_total.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public final Integer getEcpu_id() {
		return ecpu_id;
	}

	public final String getEcpu_company() {
		return ecpu_company;
	}

	public final String getEcpu_name() {
		return ecpu_name;
	}

	public final String getEcpu_idcard() {
		return ecpu_idcard;
	}

	public final String getEcpu_hk() {
		return ecpu_hk;
	}

	public final Integer getOwnmonth() {
		return ownmonth;
	}

	public final String getEcpu_change() {
		return ecpu_change;
	}

	public final BigDecimal getEcpu_total() {
		return ecpu_total;
	}

	public final Integer getEcpu_state() {
		return ecpu_state;
	}

	public final Date getEcpu_addtime() {
		return ecpu_addtime;
	}

	public final String getEcpu_addname() {
		return ecpu_addname;
	}

	public final String getEcpu_remark() {
		return ecpu_remark;
	}

	public final void setEcpu_id(Integer ecpu_id) {
		this.ecpu_id = ecpu_id;
	}

	public final void setEcpu_company(String ecpu_company) {
		this.ecpu_company = ecpu_company;
	}

	public final void setEcpu_name(String ecpu_name) {
		this.ecpu_name = ecpu_name;
	}

	public final void setEcpu_idcard(String ecpu_idcard) {
		this.ecpu_idcard = ecpu_idcard;
	}

	public final void setEcpu_hk(String ecpu_hk) {
		this.ecpu_hk = ecpu_hk;
	}

	public final void setOwnmonth(Integer ownmonth) {
		this.ownmonth = ownmonth;
	}

	public final void setEcpu_change(String ecpu_change) {
		this.ecpu_change = ecpu_change;
	}

	public final void setEcpu_total(BigDecimal ecpu_total) {
		this.ecpu_total = ecpu_total == null ? null : ecpu_total.setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	public final void setEcpu_state(Integer ecpu_state) {
		this.ecpu_state = ecpu_state;
	}

	public final void setEcpu_addtime(Date ecpu_addtime) {
		this.ecpu_addtime = ecpu_addtime;
	}

	public final void setEcpu_addname(String ecpu_addname) {
		this.ecpu_addname = ecpu_addname;
	}

	public final void setEcpu_remark(String ecpu_remark) {
		this.ecpu_remark = ecpu_remark;
	}

	public List<EmCommissionOutPayUpdateFeeDetailModel> getFeeList() {
		return feeList;
	}

	public void setFeeList(List<EmCommissionOutPayUpdateFeeDetailModel> feeList) {
		this.feeList = feeList;
	}

	public Integer getEcpu_cabc_id() {
		return ecpu_cabc_id;
	}

	public void setEcpu_cabc_id(Integer ecpu_cabc_id) {
		this.ecpu_cabc_id = ecpu_cabc_id;
	}

	public BigDecimal getEcpu_lx() {
		return ecpu_lx;
	}

	public void setEcpu_lx(BigDecimal ecpu_lx) {
		this.ecpu_lx = ecpu_lx == null ? null : ecpu_lx.setScale(2,
				BigDecimal.ROUND_HALF_UP);
		;
	}

	public BigDecimal getEcpu_sb_total() {
		return ecpu_sb_total;
	}

	public void setEcpu_sb_total(BigDecimal ecpu_sb_total) {
		this.ecpu_sb_total = ecpu_sb_total == null ? null : ecpu_sb_total
				.setScale(2, BigDecimal.ROUND_HALF_UP);
		;
	}

	public BigDecimal getEcpu_gjj_total() {
		return ecpu_gjj_total;
	}

	public void setEcpu_gjj_total(BigDecimal ecpu_gjj_total) {
		this.ecpu_gjj_total = ecpu_gjj_total == null ? null : ecpu_gjj_total
				.setScale(2, BigDecimal.ROUND_HALF_UP);
		;
	}
	
	public BigDecimal getEcpu_bcgjj_total() {
		return ecpu_bcgjj_total;
	}

	public void setEcpu_bcgjj_total(BigDecimal ecpu_bcgjj_total) {
		this.ecpu_bcgjj_total = ecpu_bcgjj_total == null ? null : ecpu_bcgjj_total
				.setScale(2, BigDecimal.ROUND_HALF_UP);
		;
	}

	public BigDecimal getEcpu_other_total() {
		return ecpu_other_total;
	}

	public void setEcpu_other_total(BigDecimal ecpu_other_total) {
		this.ecpu_other_total = ecpu_other_total == null ? null
				: ecpu_other_total.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getEcpu_welfare_total() {
		return ecpu_welfare_total;
	}

	public void setEcpu_welfare_total(BigDecimal ecpu_welfare_total) {
		this.ecpu_welfare_total = ecpu_welfare_total == null ? null
				: ecpu_welfare_total.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public String getEcpu_client() {
		return ecpu_client;
	}

	public void setEcpu_client(String ecpu_client) {
		this.ecpu_client = ecpu_client;
	}

	public List<Object> getObjsList() {
		return objsList;
	}

	public void setObjsList(List<Object> objsList) {
		this.objsList = objsList;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCoab_name() {
		return coab_name;
	}

	public void setCoab_name(String coab_name) {
		this.coab_name = coab_name;
	}

	public Integer getPpc_id() {
		return ppc_id;
	}

	public void setPpc_id(Integer ppc_id) {
		this.ppc_id = ppc_id;
	}

	public Integer getBjownmonth() {
		return bjownmonth;
	}

	public void setBjownmonth(Integer bjownmonth) {
		this.bjownmonth = bjownmonth;
	}
}
