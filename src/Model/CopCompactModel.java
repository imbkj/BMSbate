package Model;

import java.util.Date;

public class CopCompactModel {
	// cpct_id
	private Integer cpct_id;
	// cpct_shortname
	private String cpct_shortname;
	// cpct_name
	private String cpct_name;
	// cpct_state
	private Integer cpct_state;
	// cpct_addname
	private String cpct_addname;
	// cpct_addtime
	private Date cpct_addtime;

	// cpcr_id
	private Integer cpcr_id;
	// cpcr_copr_id
	private Integer cpcr_copr_id;
	// cpcr_cpct_id
	private Integer cpcr_cpct_id;
	// cpcr_state
	private Integer cpcr_state;
	// cpcr_addname
	private String cpcr_addname;
	// cpcr_addtime
	private Date cpcr_addtime;

	// Copr_id
	private Integer copr_id;
	// copr_name
	private String copr_name;
	// copr_content
	private String copr_content;
	// 1.待确定科目 2.已生效 0.未生效
	private Integer copr_state;
	// copr_stopname
	private String copr_stopname;
	// copr_stoptime
	private Date copr_stoptime;
	// copr_sort
	private Integer copr_sort;
	// copr_d1
	private Integer copr_d1;
	// copr_d2
	private Integer copr_d2;
	// copr_copc_id
	private Integer copr_copc_id;
	// copr_cpac_id
	private Integer copr_cpac_id;
	// copr_addtime
	private Date copr_addtime;
	// copr_addname
	private String copr_addname;
	// copr_type
	private String copr_type;
	// copr_cabc_id
	private Integer copr_cabc_id;
	// copr_cmce_id
	private Integer copr_cmce_id;
	// 入职时产品与业务关联ID
	private Integer copr_emce_id;
	// copr_table
	private String copr_table;
	// 员工活动关联ID
	private Integer copr_embe_id;
	// copr_tapr_id
	private Integer copr_tapr_id;
	// copr_laststate
	private Integer copr_laststate;
	// copr_statetype
	private String copr_statetype;
	private String copc_name;
	private String cpac_name;
	private String city;
	private String coab_name;

	private String statename;
	private Boolean ifChecked;

	public final Integer getCopr_id() {
		return copr_id;
	}

	public final String getCopr_content() {
		return copr_content;
	}

	public final Integer getCopr_state() {
		return copr_state;
	}

	public final String getCopr_stopname() {
		return copr_stopname;
	}

	public final Date getCopr_stoptime() {
		return copr_stoptime;
	}

	public final Integer getCopr_sort() {
		return copr_sort;
	}

	public final Integer getCopr_d1() {
		return copr_d1;
	}

	public final Integer getCopr_d2() {
		return copr_d2;
	}

	public final Integer getCopr_copc_id() {
		return copr_copc_id;
	}

	public final Integer getCopr_cpac_id() {
		return copr_cpac_id;
	}

	public final Date getCopr_addtime() {
		return copr_addtime;
	}

	public final String getCopr_addname() {
		return copr_addname;
	}

	public final String getCopr_type() {
		return copr_type;
	}

	public final Integer getCopr_cabc_id() {
		return copr_cabc_id;
	}

	public final Integer getCopr_cmce_id() {
		return copr_cmce_id;
	}

	public final Integer getCopr_emce_id() {
		return copr_emce_id;
	}

	public final String getCopr_table() {
		return copr_table;
	}

	public final Integer getCopr_embe_id() {
		return copr_embe_id;
	}

	public final Integer getCopr_tapr_id() {
		return copr_tapr_id;
	}

	public final Integer getCopr_laststate() {
		return copr_laststate;
	}

	public final String getCopr_statetype() {
		return copr_statetype;
	}

	public final void setCopr_id(Integer copr_id) {
		this.copr_id = copr_id;
	}

	public final void setCopr_content(String copr_content) {
		this.copr_content = copr_content;
	}

	public final void setCopr_state(Integer copr_state) {
		this.copr_state = copr_state;
	}

	public final void setCopr_stopname(String copr_stopname) {
		this.copr_stopname = copr_stopname;
	}

	public final void setCopr_stoptime(Date copr_stoptime) {
		this.copr_stoptime = copr_stoptime;
	}

	public final void setCopr_sort(Integer copr_sort) {
		this.copr_sort = copr_sort;
	}

	public final void setCopr_d1(Integer copr_d1) {
		this.copr_d1 = copr_d1;
	}

	public final void setCopr_d2(Integer copr_d2) {
		this.copr_d2 = copr_d2;
	}

	public final void setCopr_copc_id(Integer copr_copc_id) {
		this.copr_copc_id = copr_copc_id;
	}

	public final void setCopr_cpac_id(Integer copr_cpac_id) {
		this.copr_cpac_id = copr_cpac_id;
	}

	public final void setCopr_addtime(Date copr_addtime) {
		this.copr_addtime = copr_addtime;
	}

	public final void setCopr_addname(String copr_addname) {
		this.copr_addname = copr_addname;
	}

	public final void setCopr_type(String copr_type) {
		this.copr_type = copr_type;
	}

	public final void setCopr_cabc_id(Integer copr_cabc_id) {
		this.copr_cabc_id = copr_cabc_id;
	}

	public final void setCopr_cmce_id(Integer copr_cmce_id) {
		this.copr_cmce_id = copr_cmce_id;
	}

	public final void setCopr_emce_id(Integer copr_emce_id) {
		this.copr_emce_id = copr_emce_id;
	}

	public final void setCopr_table(String copr_table) {
		this.copr_table = copr_table;
	}

	public final void setCopr_embe_id(Integer copr_embe_id) {
		this.copr_embe_id = copr_embe_id;
	}

	public final void setCopr_tapr_id(Integer copr_tapr_id) {
		this.copr_tapr_id = copr_tapr_id;
	}

	public final void setCopr_laststate(Integer copr_laststate) {
		this.copr_laststate = copr_laststate;
	}

	public final void setCopr_statetype(String copr_statetype) {
		this.copr_statetype = copr_statetype;
	}

	public final Integer getCpct_id() {
		return cpct_id;
	}

	public final String getCpct_shortname() {
		return cpct_shortname;
	}

	public final String getCpct_name() {
		return cpct_name;
	}

	public final Integer getCpct_state() {
		return cpct_state;
	}

	public final String getCpct_addname() {
		return cpct_addname;
	}

	public final Date getCpct_addtime() {
		return cpct_addtime;
	}

	public final Integer getCpcr_id() {
		return cpcr_id;
	}

	public final Integer getCpcr_copr_id() {
		return cpcr_copr_id;
	}

	public final Integer getCpcr_cpct_id() {
		return cpcr_cpct_id;
	}

	public final Integer getCpcr_state() {
		return cpcr_state;
	}

	public final String getCpcr_addname() {
		return cpcr_addname;
	}

	public final Date getCpcr_addtime() {
		return cpcr_addtime;
	}

	public final void setCpct_id(Integer cpct_id) {
		this.cpct_id = cpct_id;
	}

	public final void setCpct_shortname(String cpct_shortname) {
		this.cpct_shortname = cpct_shortname;
	}

	public final void setCpct_name(String cpct_name) {
		this.cpct_name = cpct_name;
	}

	public final void setCpct_state(Integer cpct_state) {
		this.cpct_state = cpct_state;
	}

	public final void setCpct_addname(String cpct_addname) {
		this.cpct_addname = cpct_addname;
	}

	public final void setCpct_addtime(Date cpct_addtime) {
		this.cpct_addtime = cpct_addtime;
	}

	public final void setCpcr_id(Integer cpcr_id) {
		this.cpcr_id = cpcr_id;
	}

	public final void setCpcr_copr_id(Integer cpcr_copr_id) {
		this.cpcr_copr_id = cpcr_copr_id;
	}

	public final void setCpcr_cpct_id(Integer cpcr_cpct_id) {
		this.cpcr_cpct_id = cpcr_cpct_id;
	}

	public final void setCpcr_state(Integer cpcr_state) {
		this.cpcr_state = cpcr_state;
	}

	public final void setCpcr_addname(String cpcr_addname) {
		this.cpcr_addname = cpcr_addname;
	}

	public final void setCpcr_addtime(Date cpcr_addtime) {
		this.cpcr_addtime = cpcr_addtime;
	}

	public String getCopr_name() {
		return copr_name;
	}

	public void setCopr_name(String copr_name) {
		this.copr_name = copr_name;
	}

	public String getStatename() {
		return statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}

	public Boolean getIfChecked() {
		return ifChecked;
	}

	public void setIfChecked(Boolean ifChecked) {
		this.ifChecked = ifChecked;
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
}
