package Model;

import java.math.BigDecimal;
import java.util.List;

public class EmBcItemGroupModel {
	// / ebig_id
	private Integer ebig_id;
	// / cid
	private Integer cid;
	// / ebig_name
	private String ebig_name;
	// / ebig_charge
	private BigDecimal ebig_charge;
	// / ebig_discount
	private BigDecimal ebig_discount;
	// / ebig_remark
	private String ebig_remark;
	// / ebig_hospital
	private Integer ebig_hospital;
	// / ebig_state
	private Integer ebig_state;
	// / ebig_addname
	private String ebig_addname;
	// / ebig_addtime
	private String ebig_addtime;
	// / ebig_modname
	private String ebig_modname;
	// / ebig_modtime
	private String ebig_modtime;

	private Boolean Checked;

	// / eigl_id
	private Integer eigl_id;
	// / eigl_ebig_id
	private Integer eigl_ebig_id;
	// / eigl_ebit_id
	private Integer eigl_ebit_id;
	// / eigl_addtime
	private String eigl_addtime;
	// / eigl_addname
	private String eigl_addname;
	// / eigl_modtime
	private String eigl_modtime;
	// / eigl_modname
	private String eigl_modname;
	private String coba_client;// 客服
	private String coba_shortname;// 公司简称
	private String ebcs_hospital;// 机构名称
	private String coba_spell;
	private String emba_name;
	private boolean ischeck;

	private List<EmBodyCheckItemModel> list;

	public Integer getEbig_id() {
		return ebig_id;
	}

	public void setEbig_id(Integer ebig_id) {
		this.ebig_id = ebig_id;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getEbig_name() {
		return ebig_name;
	}

	public void setEbig_name(String ebig_name) {
		this.ebig_name = ebig_name;
	}

	public BigDecimal getEbig_charge() {
		return ebig_charge;
	}

	public void setEbig_charge(BigDecimal ebig_charge) {
		this.ebig_charge = ebig_charge;
	}

	public BigDecimal getEbig_discount() {
		return ebig_discount;
	}

	public void setEbig_discount(BigDecimal ebig_discount) {
		this.ebig_discount = ebig_discount;
	}

	public String getEbig_remark() {
		return ebig_remark;
	}

	public void setEbig_remark(String ebig_remark) {
		this.ebig_remark = ebig_remark;
	}

	public Integer getEbig_hospital() {
		return ebig_hospital;
	}

	public void setEbig_hospital(Integer ebig_hospital) {
		this.ebig_hospital = ebig_hospital;
	}

	public Integer getEbig_state() {
		return ebig_state;
	}

	public void setEbig_state(Integer ebig_state) {
		this.ebig_state = ebig_state;
	}

	public String getEbig_addname() {
		return ebig_addname;
	}

	public void setEbig_addname(String ebig_addname) {
		this.ebig_addname = ebig_addname;
	}

	public String getEbig_addtime() {
		return ebig_addtime;
	}

	public void setEbig_addtime(String ebig_addtime) {
		this.ebig_addtime = ebig_addtime;
	}

	public String getEbig_modname() {
		return ebig_modname;
	}

	public void setEbig_modname(String ebig_modname) {
		this.ebig_modname = ebig_modname;
	}

	public String getEbig_modtime() {
		return ebig_modtime;
	}

	public void setEbig_modtime(String ebig_modtime) {
		this.ebig_modtime = ebig_modtime;
	}

	public Integer getEigl_id() {
		return eigl_id;
	}

	public void setEigl_id(Integer eigl_id) {
		this.eigl_id = eigl_id;
	}

	public Integer getEigl_ebig_id() {
		return eigl_ebig_id;
	}

	public void setEigl_ebig_id(Integer eigl_ebig_id) {
		this.eigl_ebig_id = eigl_ebig_id;
	}

	public Integer getEigl_ebit_id() {
		return eigl_ebit_id;
	}

	public void setEigl_ebit_id(Integer eigl_ebit_id) {
		this.eigl_ebit_id = eigl_ebit_id;
	}

	public String getEigl_addtime() {
		return eigl_addtime;
	}

	public void setEigl_addtime(String eigl_addtime) {
		this.eigl_addtime = eigl_addtime;
	}

	public String getEigl_addname() {
		return eigl_addname;
	}

	public void setEigl_addname(String eigl_addname) {
		this.eigl_addname = eigl_addname;
	}

	public String getEigl_modtime() {
		return eigl_modtime;
	}

	public void setEigl_modtime(String eigl_modtime) {
		this.eigl_modtime = eigl_modtime;
	}

	public String getEigl_modname() {
		return eigl_modname;
	}

	public void setEigl_modname(String eigl_modname) {
		this.eigl_modname = eigl_modname;
	}

	public String getCoba_client() {
		return coba_client;
	}

	public void setCoba_client(String coba_client) {
		this.coba_client = coba_client;
	}

	public String getCoba_shortname() {
		return coba_shortname;
	}

	public void setCoba_shortname(String coba_shortname) {
		this.coba_shortname = coba_shortname;
	}

	public String getEbcs_hospital() {
		return ebcs_hospital;
	}

	public void setEbcs_hospital(String ebcs_hospital) {
		this.ebcs_hospital = ebcs_hospital;
	}

	public String getCoba_spell() {
		return coba_spell;
	}

	public void setCoba_spell(String coba_spell) {
		this.coba_spell = coba_spell;
	}

	public List<EmBodyCheckItemModel> getList() {
		return list;
	}

	public void setList(List<EmBodyCheckItemModel> list) {
		this.list = list;
	}

	public Boolean getChecked() {
		return Checked;
	}

	public void setChecked(Boolean checked) {
		Checked = checked;
	}

	public String getEmba_name() {
		return emba_name;
	}

	public void setEmba_name(String emba_name) {
		this.emba_name = emba_name;
	}

	public boolean isIscheck() {
		return ischeck;
	}

	public void setIscheck(boolean ischeck) {
		this.ischeck = ischeck;
	}
	
	
}
