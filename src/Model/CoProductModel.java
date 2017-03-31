package Model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CoProductModel {

	private Integer copr_id;
	private String copr_name;
	private String copr_content;
	private String copr_content1;
	private Integer copr_state;
	private String copr_stopname;
	private Date copr_stoptime;
	private Integer copr_sort;
	private Integer copr_d1;
	private Integer copr_d2;
	private Integer copr_copc_id;
	private Integer copr_cpac_id;
	private Date copr_addtime;
	private String copr_addname;
	private String cpst_name;
	private String city;
	private Integer cpfc_id;
	private BigDecimal fee;
	private Integer row;
	private String copc_name;
	private String cpac_name;
	private String state;
	private Integer cpfr_cpfc_id;
	private String cpfc_name;
	private Integer cpfr_lock;
	private boolean cpfr_lock1;
	private String lock;
	private Integer copr_emce_id;
	private String emce_menuname;
	private String copr_table;
	private Integer id;
	private String name;
	private Integer coab_id;
	private String coab_name;
	private Integer copr_embe_id;
	private Integer cpfrcount;
	private boolean ifU;
	private Integer copr_cabc_id;
	private String copr_type;
	private Integer copr_laststate;
	private String copr_remark;
	private Integer copr_tapr_id;
	private String statename;
	private Boolean ifChecked;
	private Integer cpcr_cpct_id;
	private List<EmbaseBusinessCenterModel> businessList;

	public Integer getCopr_id() {
		return copr_id;
	}

	public void setCopr_id(Integer copr_id) {
		this.copr_id = copr_id;
	}

	public String getCopr_name() {
		return copr_name;
	}

	public void setCopr_name(String copr_name) {
		this.copr_name = copr_name;
	}

	public String getCopr_content() {
		return copr_content;
	}

	public void setCopr_content(String copr_content) {
		this.copr_content = copr_content;
	}

	public String getCopr_content1() {
		return copr_content1;
	}

	public void setCopr_content1(String copr_content1) {
		this.copr_content1 = copr_content1;
	}

	public Integer getCopr_state() {
		return copr_state;
	}

	public void setCopr_state(Integer copr_state) {
		this.copr_state = copr_state;
	}

	public String getCopr_stopname() {
		return copr_stopname;
	}

	public void setCopr_stopname(String copr_stopname) {
		this.copr_stopname = copr_stopname;
	}

	public Date getCopr_stoptime() {
		return copr_stoptime;
	}

	public void setCopr_stoptime(Date copr_stoptime) {
		this.copr_stoptime = copr_stoptime;
	}

	public Integer getCopr_sort() {
		return copr_sort;
	}

	public void setCopr_sort(Integer copr_sort) {
		this.copr_sort = copr_sort;
	}

	public Integer getCopr_d1() {
		return copr_d1;
	}

	public void setCopr_d1(Integer copr_d1) {
		this.copr_d1 = copr_d1;
	}

	public Integer getCopr_d2() {
		return copr_d2;
	}

	public void setCopr_d2(Integer copr_d2) {
		this.copr_d2 = copr_d2;
	}

	public Integer getCopr_copc_id() {
		return copr_copc_id;
	}

	public void setCopr_copc_id(Integer copr_copc_id) {
		this.copr_copc_id = copr_copc_id;
	}

	public Integer getCopr_cpac_id() {
		return copr_cpac_id;
	}

	public void setCopr_cpac_id(Integer copr_cpac_id) {
		this.copr_cpac_id = copr_cpac_id;
	}

	public Date getCopr_addtime() {
		return copr_addtime;
	}

	public void setCopr_addtime(Date copr_addtime) {
		this.copr_addtime = copr_addtime;
	}

	public String getCopr_addname() {
		return copr_addname;
	}

	public void setCopr_addname(String copr_addname) {
		this.copr_addname = copr_addname;
	}

	public String getCpst_name() {
		return cpst_name;
	}

	public void setCpst_name(String cpst_name) {
		this.cpst_name = cpst_name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getCpfc_id() {
		return cpfc_id;
	}

	public void setCpfc_id(Integer cpfc_id) {
		this.cpfc_id = cpfc_id;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee == null ? BigDecimal.ZERO : fee.setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	public Integer getRow() {
		return row;
	}

	public void setRow(Integer row) {
		this.row = row;
	}

	public String getCopc_name() {
		return copc_name;
	}

	public void setCopc_name(String copc_name) {
		this.copc_name = copc_name;
	}

	public String getCpac_name() {
		return cpac_name;
	}

	public void setCpac_name(String cpac_name) {
		this.cpac_name = cpac_name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getCpfr_cpfc_id() {
		return cpfr_cpfc_id;
	}

	public void setCpfr_cpfc_id(Integer cpfr_cpfc_id) {
		this.cpfr_cpfc_id = cpfr_cpfc_id;
	}

	public String getCpfc_name() {
		return cpfc_name;
	}

	public void setCpfc_name(String cpfc_name) {
		this.cpfc_name = cpfc_name;
	}

	public Integer getCpfr_lock() {
		return cpfr_lock;
	}

	public void setCpfr_lock(Integer cpfr_lock) {
		this.cpfr_lock = cpfr_lock;
	}

	public String getLock() {
		return lock;
	}

	public void setLock(String lock) {
		this.lock = lock;
	}

	public Integer getCopr_emce_id() {
		return copr_emce_id;
	}

	public void setCopr_emce_id(Integer copr_emce_id) {
		this.copr_emce_id = copr_emce_id;
	}

	public String getEmce_menuname() {
		return emce_menuname;
	}

	public void setEmce_menuname(String emce_menuname) {
		this.emce_menuname = emce_menuname;
	}

	public String getCopr_table() {
		return copr_table;
	}

	public void setCopr_table(String copr_table) {
		this.copr_table = copr_table;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCoab_name() {
		return coab_name;
	}

	public void setCoab_name(String coab_name) {
		this.coab_name = coab_name;
	}

	public Integer getCopr_embe_id() {
		return copr_embe_id;
	}

	public void setCopr_embe_id(Integer copr_embe_id) {
		this.copr_embe_id = copr_embe_id;
	}

	public Integer getCpfrcount() {
		return cpfrcount;
	}

	public void setCpfrcount(Integer cpfrcount) {
		this.cpfrcount = cpfrcount;
	}

	public boolean isCpfr_lock1() {
		return cpfr_lock1;
	}

	public void setCpfr_lock1(boolean cpfr_lock1) {
		this.cpfr_lock1 = cpfr_lock1;
	}

	public boolean isIfU() {
		return ifU;
	}

	public void setIfU(boolean ifU) {
		this.ifU = ifU;
	}

	public Integer getCopr_cabc_id() {
		return copr_cabc_id;
	}

	public void setCopr_cabc_id(Integer copr_cabc_id) {
		this.copr_cabc_id = copr_cabc_id;
	}

	public String getCopr_type() {
		return copr_type;
	}

	public void setCopr_type(String copr_type) {
		this.copr_type = copr_type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCopr_laststate() {
		return copr_laststate;
	}

	public void setCopr_laststate(Integer copr_laststate) {
		this.copr_laststate = copr_laststate;
	}

	public String getCopr_remark() {
		return copr_remark;
	}

	public void setCopr_remark(String copr_remark) {
		this.copr_remark = copr_remark;
	}

	public Integer getCopr_tapr_id() {
		return copr_tapr_id;
	}

	public void setCopr_tapr_id(Integer copr_tapr_id) {
		this.copr_tapr_id = copr_tapr_id;
	}

	public String getStatename() {
		return statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}

	public Integer getCoab_id() {
		return coab_id;
	}

	public void setCoab_id(Integer coab_id) {
		this.coab_id = coab_id;
	}

	public Boolean getIfChecked() {
		return ifChecked;
	}

	public void setIfChecked(Boolean ifChecked) {
		this.ifChecked = ifChecked;
	}

	public Integer getCpcr_cpct_id() {
		return cpcr_cpct_id;
	}

	public void setCpcr_cpct_id(Integer cpcr_cpct_id) {
		this.cpcr_cpct_id = cpcr_cpct_id;
	}

	public List<EmbaseBusinessCenterModel> getBusinessList() {
		return businessList;
	}

	public void setBusinessList(List<EmbaseBusinessCenterModel> businessList) {
		this.businessList = businessList;
	}

}
