package Model;

import java.util.Comparator;
import java.util.Date;

public class EmHouseUploadErrModel implements Comparator<EmHouseUploadErrModel> {
	private Integer ehle_id;
	private String ehle_houseid;
	private String ehle_idcard;
	private String ehle_change;
	private String ehle_errMessage;
	private Integer ehle_state;
	private Date ehle_addtime;
	private String addtime;
	private String ehle_addname;

	private Integer emhc_id;
	private Integer ownmonth;
	private Integer cid;
	private Integer gid;
	private String company;
	private String name;
	private String companyid;
	private String houseid;
	private Double radix;
	private Double cpp;
	private String change;
	private String declareName;
	private String addname;
	private String addtime2;
	private Boolean checked;
	private Integer emhc_ifprogress;
	private Integer emhc_tapr_id;

	private Integer logId;

	public Integer getEhle_id() {
		return ehle_id;
	}

	public void setEhle_id(Integer ehle_id) {
		this.ehle_id = ehle_id;
	}

	public String getEhle_houseid() {
		return ehle_houseid;
	}

	public void setEhle_houseid(String ehle_houseid) {
		this.ehle_houseid = ehle_houseid;
	}

	public String getEhle_idcard() {
		return ehle_idcard;
	}

	public void setEhle_idcard(String ehle_idcard) {
		this.ehle_idcard = ehle_idcard;
	}

	public Integer getEhle_state() {
		return ehle_state;
	}

	public void setEhle_state(Integer ehle_state) {
		this.ehle_state = ehle_state;
	}

	public Date getEhle_addtime() {
		return ehle_addtime;
	}

	public void setEhle_addtime(Date ehle_addtime) {
		this.ehle_addtime = ehle_addtime;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getEhle_addname() {
		return ehle_addname;
	}

	public void setEhle_addname(String ehle_addname) {
		this.ehle_addname = ehle_addname;
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

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getHouseid() {
		return houseid;
	}

	public void setHouseid(String houseid) {
		this.houseid = houseid;
	}

	public Double getRadix() {
		return radix;
	}

	public void setRadix(Double radix) {
		this.radix = radix;
	}

	public Double getCpp() {
		return cpp;
	}

	public void setCpp(Double cpp) {
		this.cpp = cpp;
	}

	public String getChange() {
		return change;
	}

	public void setChange(String change) {
		this.change = change;
	}

	public String getDeclareName() {
		return declareName;
	}

	public void setDeclareName(String declareName) {
		this.declareName = declareName;
	}

	public String getAddname() {
		return addname;
	}

	public void setAddname(String addname) {
		this.addname = addname;
	}

	public String getAddtime2() {
		return addtime2;
	}

	public void setAddtime2(String addtime2) {
		this.addtime2 = addtime2;
	}

	public String getEhle_change() {
		return ehle_change;
	}

	public void setEhle_change(String ehle_change) {
		this.ehle_change = ehle_change;
	}

	public Integer getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(Integer ownmonth) {
		this.ownmonth = ownmonth;
	}

	public String getEhle_errMessage() {
		return ehle_errMessage;
	}

	public void setEhle_errMessage(String ehle_errMessage) {
		this.ehle_errMessage = ehle_errMessage;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Integer getEmhc_id() {
		return emhc_id;
	}

	public void setEmhc_id(Integer emhc_id) {
		this.emhc_id = emhc_id;
	}

	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public Integer getEmhc_ifprogress() {
		return emhc_ifprogress;
	}

	public void setEmhc_ifprogress(Integer emhc_ifprogress) {
		this.emhc_ifprogress = emhc_ifprogress;
	}

	public Integer getEmhc_tapr_id() {
		return emhc_tapr_id;
	}

	public void setEmhc_tapr_id(Integer emhc_tapr_id) {
		this.emhc_tapr_id = emhc_tapr_id;
	}

	@Override
	public int compare(EmHouseUploadErrModel o1, EmHouseUploadErrModel o2) {
		// TODO Auto-generated method stub
		Integer i = o1.getCompany().compareTo(o2.getCompany());
		if (i.equals(0)) {
			Integer i2 = o1.getName().compareTo(o2.getName());
			i = i2;
		}

		return i;
	}

}
