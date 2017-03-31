package Model;

import java.math.BigDecimal;
import java.util.Date;

public class EmbaseGDModel {

	private Integer id;
	private Integer dataId;
	private Integer cid;
	private Integer gid;
	private Integer typeId;// 数据类型
	private String type;// 数据类型
	private Integer ownmonth;// 所属月份
	private String company;// 公司全称
	private String shortname;// 公司简称
	private String name;// 姓名
	private String client;// 客服
	private String linktype;// 联系状态
	private String declaretype;// 操作状态
	private String datatype;// 材料状态
	private String remark;// 备注
	private String addtime;// 任务单提交时间
	private Date addtime2;
	private String addname;// 任务单提交人
	private String ifSp; // 多条数据特殊标识
	private boolean checked;// 是否选中数据
	private boolean ifLink; // 是否联系
	private boolean ifdeclare;// 数据是否可以操作
	private Integer declareState; // 业务数据状态
	private String declareName; // 业务数据状态
	private String idList;// 操作数据ID数组
	private Integer taprId;// 任务单ID

	private String cohf_company;// 补缴清册公司名称,独立户名称

	private Integer emgd_id;
	private String emgd_modtime;
	private String emgd_modname;

	private String emba_mobile;

	private String embc_spell;
	private String embc_sex;
	private String embc_idcard;
	private String embc_marry;
	private Integer ebcl_type;
	private Integer ebcl_state;
	private Integer ebcl_id;
	private Integer embc_id;
	private String hospital;
	private String address;
	private String bookdate;
	private Date bookdate2;

	private String ifAddress2;
	private Boolean ifAddress;
	private String ifbookdate2;
	private Boolean ifbookdate;
	private String ifFile2;
	private Boolean ifFile;
	private String cosp_bcrp_bclinkname;
	private String cosp_bcrp_caliname;
	private String cosp_sbhs_caliname;
	private String contactstate;
	private String clstate;
	private String emgd_addname;
	private String emgd_addtime;

	private String companyid;
	private String houseid;
	private String single;
	private Integer hsingle;

	private Integer tsday;
	private String emba_statestr;
	private String excelState;
	private String cohf_ispwd;
	private String change;
	private Integer ifprogress;
	private String progress;
	private String idcard;
	private String computerid;
	private String degree;
	private String title;
	private Double radix;
	private String hj;
	private String mobile;
	private String marry;
	private String wifename;
	private String wifeidcard;
	private BigDecimal total;
	private Integer startMonth;
	private Integer stopMonth;
	private String bjreason;
	private String cosp_sing_caliname;
	private boolean remarkFlag;
	private boolean remarkFlag2;

	private Integer bj_cou;
	private Integer ifjlbj;

	private Integer n;

	private String assistant;
	
	public String getEmba_statestr() {
		return emba_statestr;
	}

	public void setEmba_statestr(String emba_statestr) {
		this.emba_statestr = emba_statestr;
	}

	public String getEmgd_addname() {
		return emgd_addname;
	}

	public void setEmgd_addname(String emgd_addname) {
		this.emgd_addname = emgd_addname;
	}

	public String getEmgd_addtime() {
		return emgd_addtime;
	}

	public void setEmgd_addtime(String emgd_addtime) {
		this.emgd_addtime = emgd_addtime;
	}

	public String getContactstate() {
		return contactstate;
	}

	public void setContactstate(String contactstate) {
		this.contactstate = contactstate;
	}

	public String getClstate() {
		return clstate;
	}

	public void setClstate(String clstate) {
		this.clstate = clstate;
	}

	public Integer getEmgd_id() {
		return emgd_id;
	}

	public void setEmgd_id(Integer emgd_id) {
		this.emgd_id = emgd_id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDataId() {
		return dataId;
	}

	public void setDataId(Integer dataId) {
		this.dataId = dataId;
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

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(Integer ownmonth) {
		this.ownmonth = ownmonth;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getLinktype() {
		return linktype;
	}

	public void setLinktype(String linktype) {
		this.linktype = linktype;
	}

	public String getDeclaretype() {
		return declaretype;
	}

	public void setDeclaretype(String declaretype) {
		this.declaretype = declaretype;
	}

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getIfSp() {
		return ifSp;
	}

	public void setIfSp(String ifSp) {
		this.ifSp = ifSp;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isIfLink() {
		return ifLink;
	}

	public void setIfLink(boolean ifLink) {
		this.ifLink = ifLink;
	}

	public String getIdList() {
		return idList;
	}

	public void setIdList(String idList) {
		this.idList = idList;
	}

	public boolean isIfdeclare() {
		return ifdeclare;
	}

	public void setIfdeclare(boolean ifdeclare) {
		this.ifdeclare = ifdeclare;
	}

	public Integer getDeclareState() {
		return declareState;
	}

	public void setDeclareState(Integer declareState) {
		this.declareState = declareState;
	}

	public String getDeclareName() {
		return declareName;
	}

	public void setDeclareName(String declareName) {
		this.declareName = declareName;
	}

	public String getEmba_mobile() {
		return emba_mobile;
	}

	public void setEmba_mobile(String emba_mobile) {
		this.emba_mobile = emba_mobile;
	}

	public String getEmgd_modtime() {
		return emgd_modtime;
	}

	public void setEmgd_modtime(String emgd_modtime) {
		this.emgd_modtime = emgd_modtime;
	}

	public String getEmgd_modname() {
		return emgd_modname;
	}

	public void setEmgd_modname(String emgd_modname) {
		this.emgd_modname = emgd_modname;
	}

	public String getAddname() {
		return addname;
	}

	public void setAddname(String addname) {
		this.addname = addname;
	}

	public Integer getTaprId() {
		return taprId;
	}

	public void setTaprId(Integer taprId) {
		this.taprId = taprId;
	}

	public String getEmbc_idcard() {
		return embc_idcard;
	}

	public void setEmbc_idcard(String embc_idcard) {
		this.embc_idcard = embc_idcard;
	}

	public Integer getEbcl_type() {
		return ebcl_type;
	}

	public void setEbcl_type(Integer ebcl_type) {
		this.ebcl_type = ebcl_type;
	}

	public String getHospital() {
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBookdate() {
		return bookdate;
	}

	public void setBookdate(String bookdate) {
		this.bookdate = bookdate;
	}

	public String getEmbc_sex() {
		return embc_sex;
	}

	public void setEmbc_sex(String embc_sex) {
		this.embc_sex = embc_sex;
	}

	public String getEmbc_spell() {
		return embc_spell;
	}

	public void setEmbc_spell(String embc_spell) {
		this.embc_spell = embc_spell;
	}

	public Boolean getIfbookdate() {
		return ifbookdate;
	}

	public void setIfbookdate(Boolean ifbookdate) {
		this.ifbookdate = ifbookdate;
	}

	public Boolean getIfFile() {
		return ifFile;
	}

	public void setIfFile(Boolean ifFile) {
		this.ifFile = ifFile;
	}

	public Date getAddtime2() {
		return addtime2;
	}

	public void setAddtime2(Date addtime2) {
		this.addtime2 = addtime2;
	}

	public Date getBookdate2() {
		return bookdate2;
	}

	public void setBookdate2(Date bookdate2) {
		this.bookdate2 = bookdate2;
	}

	public String getIfAddress2() {
		return ifAddress2;
	}

	public void setIfAddress2(String ifAddress2) {
		this.ifAddress2 = ifAddress2;
	}

	public Boolean getIfAddress() {
		return ifAddress;
	}

	public void setIfAddress(Boolean ifAddress) {
		this.ifAddress = ifAddress;
	}

	public String getIfbookdate2() {
		return ifbookdate2;
	}

	public void setIfbookdate2(String ifbookdate2) {
		this.ifbookdate2 = ifbookdate2;
	}

	public String getIfFile2() {
		return ifFile2;
	}

	public void setIfFile2(String ifFile2) {
		this.ifFile2 = ifFile2;
	}

	public Integer getEbcl_state() {
		return ebcl_state;
	}

	public void setEbcl_state(Integer ebcl_state) {
		this.ebcl_state = ebcl_state;
	}

	public String getCosp_bcrp_bclinkname() {
		return cosp_bcrp_bclinkname;
	}

	public void setCosp_bcrp_bclinkname(String cosp_bcrp_bclinkname) {
		this.cosp_bcrp_bclinkname = cosp_bcrp_bclinkname;
	}

	public String getCosp_bcrp_caliname() {
		return cosp_bcrp_caliname;
	}

	public void setCosp_bcrp_caliname(String cosp_bcrp_caliname) {
		this.cosp_bcrp_caliname = cosp_bcrp_caliname;
	}

	public Integer getEbcl_id() {
		return ebcl_id;
	}

	public void setEbcl_id(Integer ebcl_id) {
		this.ebcl_id = ebcl_id;
	}

	public String getCosp_sbhs_caliname() {
		return cosp_sbhs_caliname;
	}

	public void setCosp_sbhs_caliname(String cosp_sbhs_caliname) {
		this.cosp_sbhs_caliname = cosp_sbhs_caliname;
	}

	public Integer getEmbc_id() {
		return embc_id;
	}

	public void setEmbc_id(Integer embc_id) {
		this.embc_id = embc_id;
	}

	public String getSingle() {
		return single;
	}

	public void setSingle(String single) {
		this.single = single;
	}

	public Integer getTsday() {
		return tsday;
	}

	public void setTsday(Integer tsday) {
		this.tsday = tsday;
	}

	public Integer getHsingle() {
		return hsingle;
	}

	public void setHsingle(Integer hsingle) {
		this.hsingle = hsingle;
	}

	public String getExcelState() {
		return excelState;
	}

	public void setExcelState(String excelState) {
		this.excelState = excelState;
	}

	public String getCohf_ispwd() {
		return cohf_ispwd;
	}

	public void setCohf_ispwd(String cohf_ispwd) {
		this.cohf_ispwd = cohf_ispwd;
	}

	public String getChange() {
		return change;
	}

	public void setChange(String change) {
		this.change = change;
	}

	public Integer getIfprogress() {
		return ifprogress;
	}

	public void setIfprogress(Integer ifprogress) {
		this.ifprogress = ifprogress;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getComputerid() {
		return computerid;
	}

	public void setComputerid(String computerid) {
		this.computerid = computerid;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Double getRadix() {
		return radix;
	}

	public void setRadix(Double radix) {
		this.radix = radix;
	}

	public String getHj() {
		return hj;
	}

	public void setHj(String hj) {
		this.hj = hj;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMarry() {
		return marry;
	}

	public void setMarry(String marry) {
		this.marry = marry;
	}

	public String getWifename() {
		return wifename;
	}

	public void setWifename(String wifename) {
		this.wifename = wifename;
	}

	public String getWifeidcard() {
		return wifeidcard;
	}

	public void setWifeidcard(String wifeidcard) {
		this.wifeidcard = wifeidcard;
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

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Integer getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(Integer startMonth) {
		this.startMonth = startMonth;
	}

	public Integer getStopMonth() {
		return stopMonth;
	}

	public void setStopMonth(Integer stopMonth) {
		this.stopMonth = stopMonth;
	}

	public String getBjreason() {
		return bjreason;
	}

	public void setBjreason(String bjreason) {
		this.bjreason = bjreason;
	}

	public String getCosp_sing_caliname() {
		return cosp_sing_caliname;
	}

	public void setCosp_sing_caliname(String cosp_sing_caliname) {
		this.cosp_sing_caliname = cosp_sing_caliname;
	}

	public boolean isRemarkFlag() {
		if (this.remark == null || this.remark.equals("")) {
			remarkFlag = false;
		} else {
			remarkFlag = true;
		}
		return remarkFlag;
	}

	public void setRemarkFlag(boolean remarkFlag) {
		this.remarkFlag = remarkFlag;

	}

	public boolean isRemarkFlag2() {
		if (this.remark == null || this.remark.equals("")) {
			remarkFlag2 = true;
		} else {
			remarkFlag2 = false;
		}
		return remarkFlag2;
	}

	public void setRemarkFlag2(boolean remarkFlag2) {
		this.remarkFlag2 = remarkFlag2;
	}

	public Integer getBj_cou() {
		return bj_cou;
	}

	public void setBj_cou(Integer bj_cou) {
		this.bj_cou = bj_cou;
	}

	public Integer getIfjlbj() {
		return ifjlbj;
	}

	public void setIfjlbj(Integer ifjlbj) {
		this.ifjlbj = ifjlbj;
	}

	public String getCohf_company() {
		return cohf_company;
	}

	public void setCohf_company(String cohf_company) {
		this.cohf_company = cohf_company;
	}

	public Integer getN() {
		return n;
	}

	public void setN(Integer n) {
		this.n = n;
	}

	public String getEmbc_marry() {
		return embc_marry;
	}

	public void setEmbc_marry(String embc_marry) {
		this.embc_marry = embc_marry;
	}

	public String getAssistant() {
		return assistant;
	}

	public void setAssistant(String assistant) {
		this.assistant = assistant;
	}

}
