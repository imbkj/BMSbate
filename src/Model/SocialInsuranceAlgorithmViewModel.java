package Model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class SocialInsuranceAlgorithmViewModel {
	private String city;
	private String coab_name;
	private int soin_id;
	private int soin_cabc_id;
	private String soin_title;
	private String soin_addname;
	private String soin_addtime;
	private String soin_delname;
	private String soin_deltime;
	private String soin_delreason;
	private String soin_state;
	private int sial_id;
	private Date sial_execdate;
	private String sial_execdatestr;
	private String sial_standard;
	private int sial_sb_standard;
	private int sial_gjj_standard;
	private String sial_sb_standardstr;
	private String sial_gjj_standardstr;
	private BigDecimal sial_avg_salary;
	private BigDecimal sial_lowest_salary;
	private String sial_avg_salarystr;
	private String sial_lowest_salarystr;
	private String sial_city_remark;
	private String sial_sb_remark;
	private String sial_gjj_remark;
	private String sial_cb_remark;
	private String sial_addname;
	private String gjj_proportion;
	private int sich_change_type;
	private int sich_sial_id;
	private SocialInsuranceAlgorithmViewModel om;
	private List<SocialInsuranceClassInfoViewModel> classInfoList;

	public SocialInsuranceAlgorithmViewModel() {
		super();
	}

	// 在用
	public SocialInsuranceAlgorithmViewModel(String city, String coab_name,
			int soin_id, int soin_cabc_id, String soin_title, int sial_id,
			Date sial_execdate, String sial_standard, int sial_sb_standard,
			int sial_gjj_standard, BigDecimal sial_avg_salary,
			BigDecimal sial_lowest_salary, String sial_city_remark,
			String sial_sb_remark, String sial_gjj_remark, String sial_cb_remark) {
		super();
		this.city = city;
		this.coab_name = coab_name;
		this.soin_id = soin_id;
		this.soin_cabc_id = soin_cabc_id;
		this.soin_title = soin_title;
		this.sial_id = sial_id;
		this.sial_execdate = sial_execdate;
		this.sial_standard = sial_standard;
		this.sial_sb_standard = sial_sb_standard;
		this.sial_gjj_standard = sial_gjj_standard;
		this.sial_avg_salary = sial_avg_salary;
		this.sial_lowest_salary = sial_lowest_salary;
		this.sial_city_remark = sial_city_remark;
		this.sial_sb_remark = sial_sb_remark;
		this.sial_gjj_remark = sial_gjj_remark;
		this.sial_cb_remark = sial_cb_remark;
	}

	public SocialInsuranceAlgorithmViewModel(String city, String coab_name,
			int soin_id, int soin_cabc_id, String soin_title,
			String soin_addname, String soin_addtime, String soin_delname,
			String soin_deltime, String soin_delreason, String soin_state,
			int sial_id, Date sial_execdate, String sial_execdatestr,
			String sial_standard, int sial_sb_standard, int sial_gjj_standard,
			BigDecimal sial_avg_salary, BigDecimal sial_lowest_salary,
			String sial_city_remark, String sial_sb_remark,
			String sial_gjj_remark, String sial_cb_remark, String sial_addname) {
		super();
		this.city = city;
		this.coab_name = coab_name;
		this.soin_id = soin_id;
		this.soin_cabc_id = soin_cabc_id;
		this.soin_title = soin_title;
		this.soin_addname = soin_addname;
		this.soin_addtime = soin_addtime;
		this.soin_delname = soin_delname;
		this.soin_deltime = soin_deltime;
		this.soin_delreason = soin_delreason;
		this.soin_state = soin_state;
		this.sial_id = sial_id;
		this.sial_execdate = sial_execdate;
		this.sial_execdatestr = sial_execdatestr;
		this.sial_standard = sial_standard;
		this.sial_sb_standard = sial_sb_standard;
		this.sial_gjj_standard = sial_gjj_standard;
		this.sial_avg_salary = sial_avg_salary;
		this.sial_lowest_salary = sial_lowest_salary;
		this.sial_city_remark = sial_city_remark;
		this.sial_sb_remark = sial_sb_remark;
		this.sial_gjj_remark = sial_gjj_remark;
		this.sial_cb_remark = sial_cb_remark;
		this.sial_addname = sial_addname;
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

	public int getSoin_id() {
		return soin_id;
	}

	public void setSoin_id(int soin_id) {
		this.soin_id = soin_id;
	}

	public int getSoin_cabc_id() {
		return soin_cabc_id;
	}

	public void setSoin_cabc_id(int soin_cabc_id) {
		this.soin_cabc_id = soin_cabc_id;
	}

	public String getSoin_title() {
		return soin_title;
	}

	public void setSoin_title(String soin_title) {
		this.soin_title = soin_title;
	}

	public String getSoin_addname() {
		return soin_addname;
	}

	public void setSoin_addname(String soin_addname) {
		this.soin_addname = soin_addname;
	}

	public String getSoin_addtime() {
		return soin_addtime;
	}

	public void setSoin_addtime(String soin_addtime) {
		this.soin_addtime = soin_addtime;
	}

	public String getSoin_delname() {
		return soin_delname;
	}

	public void setSoin_delname(String soin_delname) {
		this.soin_delname = soin_delname;
	}

	public String getSoin_deltime() {
		return soin_deltime;
	}

	public void setSoin_deltime(String soin_deltime) {
		this.soin_deltime = soin_deltime;
	}

	public String getSoin_delreason() {
		return soin_delreason;
	}

	public void setSoin_delreason(String soin_delreason) {
		this.soin_delreason = soin_delreason;
	}

	public String getSoin_state() {
		return soin_state;
	}

	public void setSoin_state(String soin_state) {
		this.soin_state = soin_state;
	}

	public int getSial_id() {
		return sial_id;
	}

	public void setSial_id(int sial_id) {
		this.sial_id = sial_id;
	}

	public Date getSial_execdate() {
		return sial_execdate;
	}

	public void setSial_execdate(Date sial_execdate) {
		this.sial_execdate = sial_execdate;
	}

	public String getSial_execdatestr() {
		return sial_execdatestr;
	}

	public void setSial_execdatestr(String sial_execdatestr) {
		this.sial_execdatestr = sial_execdatestr;
	}

	public String getSial_standard() {
		return sial_standard;
	}

	public void setSial_standard(String sial_standard) {
		this.sial_standard = sial_standard;
	}

	public int getSial_sb_standard() {
		return sial_sb_standard;
	}

	public void setSial_sb_standard(int sial_sb_standard) {
		this.sial_sb_standard = sial_sb_standard;
	}

	public int getSial_gjj_standard() {
		return sial_gjj_standard;
	}

	public void setSial_gjj_standard(int sial_gjj_standard) {
		this.sial_gjj_standard = sial_gjj_standard;
	}

	public BigDecimal getSial_avg_salary() {
		return sial_avg_salary;
	}

	public void setSial_avg_salary(BigDecimal sial_avg_salary) {
		this.sial_avg_salary = sial_avg_salary;
	}

	public BigDecimal getSial_lowest_salary() {
		return sial_lowest_salary;
	}

	public void setSial_lowest_salary(BigDecimal sial_lowest_salary) {
		this.sial_lowest_salary = sial_lowest_salary;
	}

	public String getSial_city_remark() {
		return sial_city_remark;
	}

	public void setSial_city_remark(String sial_city_remark) {
		this.sial_city_remark = sial_city_remark;
	}

	public String getSial_sb_remark() {
		return sial_sb_remark;
	}

	public void setSial_sb_remark(String sial_sb_remark) {
		this.sial_sb_remark = sial_sb_remark;
	}

	public String getSial_gjj_remark() {
		return sial_gjj_remark;
	}

	public void setSial_gjj_remark(String sial_gjj_remark) {
		this.sial_gjj_remark = sial_gjj_remark;
	}

	public String getSial_cb_remark() {
		return sial_cb_remark;
	}

	public void setSial_cb_remark(String sial_cb_remark) {
		this.sial_cb_remark = sial_cb_remark;
	}

	public String getSial_addname() {
		return sial_addname;
	}

	public void setSial_addname(String sial_addname) {
		this.sial_addname = sial_addname;
	}

	public String getSial_sb_standardstr() {
		return sial_sb_standardstr;
	}

	public void setSial_sb_standardstr(String sial_sb_standardstr) {
		this.sial_sb_standardstr = sial_sb_standardstr;
	}

	public String getSial_gjj_standardstr() {
		return sial_gjj_standardstr;
	}

	public void setSial_gjj_standardstr(String sial_gjj_standardstr) {
		this.sial_gjj_standardstr = sial_gjj_standardstr;
	}

	public String getSial_avg_salarystr() {
		return sial_avg_salarystr;
	}

	public void setSial_avg_salarystr(String sial_avg_salarystr) {
		this.sial_avg_salarystr = sial_avg_salarystr;
	}

	public String getSial_lowest_salarystr() {
		return sial_lowest_salarystr;
	}

	public void setSial_lowest_salarystr(String sial_lowest_salarystr) {
		this.sial_lowest_salarystr = sial_lowest_salarystr;
	}

	public String getGjj_proportion() {
		return gjj_proportion;
	}

	public void setGjj_proportion(String gjj_proportion) {
		this.gjj_proportion = gjj_proportion;
	}

	public int getSich_change_type() {
		return sich_change_type;
	}

	public void setSich_change_type(int sich_change_type) {
		this.sich_change_type = sich_change_type;
	}

	public SocialInsuranceAlgorithmViewModel getOm() {
		return om;
	}

	public void setOm(SocialInsuranceAlgorithmViewModel om) {
		this.om = om;
	}

	public int getSich_sial_id() {
		return sich_sial_id;
	}

	public void setSich_sial_id(int sich_sial_id) {
		this.sich_sial_id = sich_sial_id;
	}

	public List<SocialInsuranceClassInfoViewModel> getClassInfoList() {
		return classInfoList;
	}

	public void setClassInfoList(
			List<SocialInsuranceClassInfoViewModel> classInfoList) {
		this.classInfoList = classInfoList;
	}

}
