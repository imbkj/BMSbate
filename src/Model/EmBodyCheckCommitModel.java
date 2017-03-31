package Model;

import java.math.BigDecimal;
import java.util.List;

public class EmBodyCheckCommitModel {
	private Integer cid;
	private Integer ebcc_id;
	private String coba_company;
	private String coba_shortname;
	private String emba_name;
	private String emba_sex;
	private String emba_marital;
	private String emba_idcard;
	private String emba_indate;
	private Integer ebcc_hospital;
	private String hospital;
	private Integer ebcc_address;
	private String address;
	private String ebcc_bookdate;
	private Integer ebcc_groupid;
	private Integer ebcc_itemid;
	private String items;
	private Integer ebcc_ownmonth;
	private Integer gid;
	private String remark;
	private String coba_client;
	private String ebcc_addtime;
	private BigDecimal charge;
	private String embc_iteminfo;
	private String ebcc_modtime;
	private String ebcc_modname;
	private Integer ebcc_state;
	private Integer ebcc_deleteState;
	private boolean checked;
	private Integer month;
	private Integer wk;

	private boolean lock;

	private List<EmBcItemGroupModel> list;

	public List<EmBcItemGroupModel> getList() {
		return list;
	}

	public void setList(List<EmBcItemGroupModel> list) {
		this.list = list;
	}

	public String getCoba_shortname() {
		return coba_shortname;
	}

	public void setCoba_shortname(String coba_shortname) {
		this.coba_shortname = coba_shortname;
	}

	public String getEmba_name() {
		return emba_name;
	}

	public void setEmba_name(String emba_name) {
		this.emba_name = emba_name;
	}

	public String getEmba_sex() {
		return emba_sex;
	}

	public void setEmba_sex(String emba_sex) {
		this.emba_sex = emba_sex;
	}

	public String getEmba_marital() {
		return emba_marital;
	}

	public void setEmba_marital(String emba_marital) {
		this.emba_marital = emba_marital;
	}

	public String getEmba_idcard() {
		return emba_idcard;
	}

	public void setEmba_idcard(String emba_idcard) {
		this.emba_idcard = emba_idcard;
	}

	public String getEmba_indate() {
		return emba_indate;
	}

	public void setEmba_indate(String emba_indate) {
		this.emba_indate = emba_indate;
	}

	public Integer getEbcc_hospital() {
		return ebcc_hospital;
	}

	public void setEbcc_hospital(Integer ebcc_hospital) {
		this.ebcc_hospital = ebcc_hospital;
	}

	public String getEbcc_bookdate() {
		return ebcc_bookdate;
	}

	public void setEbcc_bookdate(String ebcc_bookdate) {
		this.ebcc_bookdate = ebcc_bookdate;
	}

	public Integer getEbcc_ownmonth() {
		return ebcc_ownmonth;
	}

	public void setEbcc_ownmonth(Integer ebcc_ownmonth) {
		this.ebcc_ownmonth = ebcc_ownmonth;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCoba_client() {
		return coba_client;
	}

	public void setCoba_client(String coba_client) {
		this.coba_client = coba_client;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public Integer getEbcc_id() {
		return ebcc_id;
	}

	public void setEbcc_id(Integer ebcc_id) {
		this.ebcc_id = ebcc_id;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public BigDecimal getCharge() {
		return charge;
	}

	public void setCharge(BigDecimal charge) {
		this.charge = charge;
	}

	public String getEmbc_iteminfo() {
		return embc_iteminfo;
	}

	public void setEmbc_iteminfo(String embc_iteminfo) {
		this.embc_iteminfo = embc_iteminfo;
	}

	public String getHospital() {
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	public Integer getEbcc_address() {
		return ebcc_address;
	}

	public void setEbcc_address(Integer ebcc_address) {
		this.ebcc_address = ebcc_address;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getEbcc_groupid() {
		return ebcc_groupid;
	}

	public void setEbcc_groupid(Integer ebcc_groupid) {
		this.ebcc_groupid = ebcc_groupid;
	}

	public Integer getEbcc_itemid() {
		return ebcc_itemid;
	}

	public void setEbcc_itemid(Integer ebcc_itemid) {
		this.ebcc_itemid = ebcc_itemid;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public String getEbcc_addtime() {
		return ebcc_addtime;
	}

	public void setEbcc_addtime(String ebcc_addtime) {
		this.ebcc_addtime = ebcc_addtime;
	}

	public String getEbcc_modtime() {
		return ebcc_modtime;
	}

	public void setEbcc_modtime(String ebcc_modtime) {
		this.ebcc_modtime = ebcc_modtime;
	}

	public String getEbcc_modname() {
		return ebcc_modname;
	}

	public void setEbcc_modname(String ebcc_modname) {
		this.ebcc_modname = ebcc_modname;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Integer getEbcc_state() {
		return ebcc_state;
	}

	public void setEbcc_state(Integer ebcc_state) {
		this.ebcc_state = ebcc_state;
	}

	public Integer getEbcc_deleteState() {
		return ebcc_deleteState;
	}

	public void setEbcc_deleteState(Integer ebcc_deleteState) {
		this.ebcc_deleteState = ebcc_deleteState;
	}

	public String getCoba_company() {
		return coba_company;
	}

	public void setCoba_company(String coba_company) {
		this.coba_company = coba_company;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getWk() {
		return wk;
	}

	public void setWk(Integer wk) {
		this.wk = wk;
	}

	public boolean isLock() {
		return lock;
	}

	public void setLock(boolean lock) {
		this.lock = lock;
	}

}
