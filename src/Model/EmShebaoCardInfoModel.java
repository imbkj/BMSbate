package Model;

public class EmShebaoCardInfoModel {
	// / sbcd_id
	private Integer sbcd_id;
	// / gid
	private Integer gid;
	// / cid
	private Integer cid;
	// / sbcd_company
	private String sbcd_company;
	// / sbcd_name
	private String sbcd_name;
	// / sbcd_sex
	private String sbcd_sex;
	// / sbcd_birthday
	private String sbcd_birthday;
	// / 民族表id
	private String sbcd_folk;
	// / sbcd_mobile
	private String sbcd_mobile;
	// / 社保电脑号
	private String sbcd_computerid;
	// / sbcd_idcardclass
	private String sbcd_idcardclass;
	// / 证件有效期起始日
	private String sbcd_idcardstartdate;
	// / 证件有效期到期日
	private String sbcd_idcardenddate;
	// / 联系地址省：统一默认为440000
	private String sbcd_province;
	// / 联系地址市：统一默认为440300
	private String sbcd_city;
	// / 详细联系地址：不许重复省和市，默认公司地址
	private String sbcd_address;
	// / 照片回执码
	private String sbcd_photonum;
	// / 户籍所在省：省户籍行政区划代码；关联PubProvince表
	private String sbcd_hjprovince;
	// / 户籍所在市：关联PubProCity表
	private String sbcd_hjcity;
	// / 发证机关
	private String sbcd_agencies;
	// / 职业：关联PubCodeConversion表的pucl_id36
	private String sbcd_job;
	// / 文化程度：关联PubCodeConversion表pucl_id的36
	private String sbcd_education;
	// / 婚姻状况：默认09，关联PubCodeConversion表pucl_id的36
	private String sbcd_marry;
	// / 户口性质，关联PubCodeConversion表pucl_id的36
	private String sbcd_hj;
	// / sbcd_remark
	private String sbcd_remark;
	// / 状态id，关联EmShebaoCardState表
	private Integer sbcd_stateid;
	// / sbcd_tarpid
	private Integer sbcd_tarpid;
	private String sbcd_shortname;
	private String sbcd_addname;
	private String sbcd_addtime;
	private String sbcd_content;
	private Integer ownmonth;
	private Integer sbcd_bankdocid;
	private Integer gidnum;
	private String coba_client;
	private Integer sbcd_lbk, sbcd_IDC;
	private String sbcd_handintime;
	// / 制卡银行
	private String sbcd_makeband;
	// / 福利核收人
	private String sbcd_flacceptname;
	// / 福利核收时间
	private String sbcd_flaccpettime;
	// / 递交银行人
	private String sbcd_tobankname;
	// / 递交银行时间
	private String sbcd_tobanktime;
	// / 福利领卡人
	private String sbcd_fltakename;
	// / 福利领卡时间
	private String sbcd_fltaketime;
	// / 雇员服务中心领卡人
	private String sbcd_centertakename;
	// / 雇员服务中心领卡时间
	private String sbcd_centertaketime;
	// / 客服领卡人
	private String sbcd_clienttakename;
	// / 客服领卡时间
	private String sbcd_clienttaketime;
	// / 领卡员工/邮件接收员工
	private String sbcd_staffname;
	// / 员工领卡时间/邮寄时间
	private String sbcd_stafftime;
	// / 中智户或者委托户
	private String sbcd_single;
	// / 社保单位名称
	private String sbcd_companyname;
	// / 社保单位编码
	private String sbcd_sbnumber;
	private String sbcd_idcard;

	private String cdst_statename;// 状态名称
	private Integer cdst_state;
	private Integer cdst_id;// 状态Id

	private String sbcd_upbankname;// 办卡银行

	private String sbcd_taketype;// 领卡方式

	private String sbcd_backcase;// 退回原因
	private String sbcd_backname;// 退回人
	private String sbcd_backtime;// 退回时间
	private String sbcd_bank;
	private Integer msgnum, msgsnum, picnum;
	private String sbcd_printtime, sbcd_printname;
	private String sbcd_centerdataname,sbcd_centerdatatime;
	private String cosp_card_caliname;
	private String cosp_bsal_caliname;
	private String sbcd_stampname;
	private String sbcd_stamptime;
	private Integer cdst_order;
	private String sbcd_centermaketime;
	private String sbcd_centermakename;
	private Boolean cosp=false;
	private String coba_shortname;
	private String cosp_card_data_caliname;
	private String sbcd_cancelcase;
	public Integer getSbcd_id() {
		return sbcd_id;
	}

	public void setSbcd_id(Integer sbcd_id) {
		this.sbcd_id = sbcd_id;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getSbcd_company() {
		return sbcd_company;
	}

	public void setSbcd_company(String sbcd_company) {
		this.sbcd_company = sbcd_company;
	}

	public String getSbcd_name() {
		return sbcd_name;
	}

	public void setSbcd_name(String sbcd_name) {
		this.sbcd_name = sbcd_name;
	}

	public String getSbcd_sex() {
		return sbcd_sex;
	}

	public void setSbcd_sex(String sbcd_sex) {
		this.sbcd_sex = sbcd_sex;
	}

	public String getSbcd_birthday() {
		return sbcd_birthday;
	}

	public void setSbcd_birthday(String sbcd_birthday) {
		this.sbcd_birthday = sbcd_birthday;
	}

	public String getSbcd_folk() {
		return sbcd_folk;
	}

	public void setSbcd_folk(String sbcd_folk) {
		this.sbcd_folk = sbcd_folk;
	}

	public String getSbcd_mobile() {
		return sbcd_mobile;
	}

	public void setSbcd_mobile(String sbcd_mobile) {
		this.sbcd_mobile = sbcd_mobile;
	}

	public String getSbcd_computerid() {
		return sbcd_computerid;
	}

	public void setSbcd_computerid(String sbcd_computerid) {
		this.sbcd_computerid = sbcd_computerid;
	}

	public String getSbcd_idcardclass() {
		return sbcd_idcardclass;
	}

	public void setSbcd_idcardclass(String sbcd_idcardclass) {
		this.sbcd_idcardclass = sbcd_idcardclass;
	}

	public String getSbcd_idcardstartdate() {
		return sbcd_idcardstartdate;
	}

	public void setSbcd_idcardstartdate(String sbcd_idcardstartdate) {
		this.sbcd_idcardstartdate = sbcd_idcardstartdate;
	}

	public String getSbcd_idcardenddate() {
		return sbcd_idcardenddate;
	}

	public void setSbcd_idcardenddate(String sbcd_idcardenddate) {
		this.sbcd_idcardenddate = sbcd_idcardenddate;
	}

	public String getSbcd_province() {
		return sbcd_province;
	}

	public void setSbcd_province(String sbcd_province) {
		this.sbcd_province = sbcd_province;
	}

	public String getSbcd_city() {
		return sbcd_city;
	}

	public void setSbcd_city(String sbcd_city) {
		this.sbcd_city = sbcd_city;
	}

	public String getSbcd_address() {
		return sbcd_address;
	}

	public void setSbcd_address(String sbcd_address) {
		this.sbcd_address = sbcd_address;
	}

	public String getSbcd_photonum() {
		return sbcd_photonum;
	}

	public void setSbcd_photonum(String sbcd_photonum) {
		this.sbcd_photonum = sbcd_photonum;
	}

	public String getSbcd_hjprovince() {
		return sbcd_hjprovince;
	}

	public void setSbcd_hjprovince(String sbcd_hjprovince) {
		this.sbcd_hjprovince = sbcd_hjprovince;
	}

	public String getSbcd_hjcity() {
		return sbcd_hjcity;
	}

	public void setSbcd_hjcity(String sbcd_hjcity) {
		this.sbcd_hjcity = sbcd_hjcity;
	}

	public String getSbcd_agencies() {
		return sbcd_agencies;
	}

	public void setSbcd_agencies(String sbcd_agencies) {
		this.sbcd_agencies = sbcd_agencies;
	}

	public String getSbcd_job() {
		return sbcd_job;
	}

	public void setSbcd_job(String sbcd_job) {
		this.sbcd_job = sbcd_job;
	}

	public String getSbcd_education() {
		return sbcd_education;
	}

	public void setSbcd_education(String sbcd_education) {
		this.sbcd_education = sbcd_education;
	}

	public String getSbcd_marry() {
		return sbcd_marry;
	}

	public void setSbcd_marry(String sbcd_marry) {
		this.sbcd_marry = sbcd_marry;
	}

	public String getSbcd_hj() {
		return sbcd_hj;
	}

	public void setSbcd_hj(String sbcd_hj) {
		this.sbcd_hj = sbcd_hj;
	}

	public String getSbcd_remark() {
		return sbcd_remark;
	}

	public void setSbcd_remark(String sbcd_remark) {
		this.sbcd_remark = sbcd_remark;
	}

	public Integer getSbcd_stateid() {
		return sbcd_stateid;
	}

	public void setSbcd_stateid(Integer sbcd_stateid) {
		this.sbcd_stateid = sbcd_stateid;
	}

	public Integer getSbcd_tarpid() {
		return sbcd_tarpid;
	}

	public void setSbcd_tarpid(Integer sbcd_tarpid) {
		this.sbcd_tarpid = sbcd_tarpid;
	}

	public String getSbcd_shortname() {
		return sbcd_shortname;
	}

	public void setSbcd_shortname(String sbcd_shortname) {
		this.sbcd_shortname = sbcd_shortname;
	}

	public String getSbcd_addname() {
		return sbcd_addname;
	}

	public void setSbcd_addname(String sbcd_addname) {
		this.sbcd_addname = sbcd_addname;
	}

	public String getSbcd_addtime() {
		return sbcd_addtime;
	}

	public void setSbcd_addtime(String sbcd_addtime) {
		this.sbcd_addtime = sbcd_addtime;
	}

	public String getSbcd_content() {
		return sbcd_content;
	}

	public void setSbcd_content(String sbcd_content) {
		this.sbcd_content = sbcd_content;
	}

	public Integer getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(Integer ownmonth) {
		this.ownmonth = ownmonth;
	}

	public String getSbcd_makeband() {
		return sbcd_makeband;
	}

	public void setSbcd_makeband(String sbcd_makeband) {
		this.sbcd_makeband = sbcd_makeband;
	}

	public String getSbcd_flacceptname() {
		return sbcd_flacceptname;
	}

	public void setSbcd_flacceptname(String sbcd_flacceptname) {
		this.sbcd_flacceptname = sbcd_flacceptname;
	}

	public String getSbcd_flaccpettime() {
		return sbcd_flaccpettime;
	}

	public void setSbcd_flaccpettime(String sbcd_flaccpettime) {
		this.sbcd_flaccpettime = sbcd_flaccpettime;
	}

	public String getSbcd_tobankname() {
		return sbcd_tobankname;
	}

	public void setSbcd_tobankname(String sbcd_tobankname) {
		this.sbcd_tobankname = sbcd_tobankname;
	}

	public String getSbcd_tobanktime() {
		return sbcd_tobanktime;
	}

	public void setSbcd_tobanktime(String sbcd_tobanktime) {
		this.sbcd_tobanktime = sbcd_tobanktime;
	}

	public String getSbcd_fltakename() {
		return sbcd_fltakename;
	}

	public void setSbcd_fltakename(String sbcd_fltakename) {
		this.sbcd_fltakename = sbcd_fltakename;
	}

	public String getSbcd_fltaketime() {
		return sbcd_fltaketime;
	}

	public void setSbcd_fltaketime(String sbcd_fltaketime) {
		this.sbcd_fltaketime = sbcd_fltaketime;
	}

	public String getSbcd_centertakename() {
		return sbcd_centertakename;
	}

	public void setSbcd_centertakename(String sbcd_centertakename) {
		this.sbcd_centertakename = sbcd_centertakename;
	}

	public String getSbcd_centertaketime() {
		return sbcd_centertaketime;
	}

	public void setSbcd_centertaketime(String sbcd_centertaketime) {
		this.sbcd_centertaketime = sbcd_centertaketime;
	}

	public String getSbcd_clienttakename() {
		return sbcd_clienttakename;
	}

	public void setSbcd_clienttakename(String sbcd_clienttakename) {
		this.sbcd_clienttakename = sbcd_clienttakename;
	}

	public String getSbcd_clienttaketime() {
		return sbcd_clienttaketime;
	}

	public void setSbcd_clienttaketime(String sbcd_clienttaketime) {
		this.sbcd_clienttaketime = sbcd_clienttaketime;
	}

	public String getSbcd_staffname() {
		return sbcd_staffname;
	}

	public void setSbcd_staffname(String sbcd_staffname) {
		this.sbcd_staffname = sbcd_staffname;
	}

	public String getSbcd_stafftime() {
		return sbcd_stafftime;
	}

	public void setSbcd_stafftime(String sbcd_stafftime) {
		this.sbcd_stafftime = sbcd_stafftime;
	}

	public String getSbcd_single() {
		return sbcd_single;
	}

	public void setSbcd_single(String sbcd_single) {
		this.sbcd_single = sbcd_single;
	}

	public String getSbcd_companyname() {
		return sbcd_companyname;
	}

	public void setSbcd_companyname(String sbcd_companyname) {
		this.sbcd_companyname = sbcd_companyname;
	}

	public String getSbcd_sbnumber() {
		return sbcd_sbnumber;
	}

	public void setSbcd_sbnumber(String sbcd_sbnumber) {
		this.sbcd_sbnumber = sbcd_sbnumber;
	}

	public String getCdst_statename() {
		return cdst_statename;
	}

	public void setCdst_statename(String cdst_statename) {
		this.cdst_statename = cdst_statename;
	}

	public Integer getCdst_state() {
		return cdst_state;
	}

	public void setCdst_state(Integer cdst_state) {
		this.cdst_state = cdst_state;
	}

	public Integer getCdst_id() {
		return cdst_id;
	}

	public void setCdst_id(Integer cdst_id) {
		this.cdst_id = cdst_id;
	}

	public String getSbcd_idcard() {
		return sbcd_idcard;
	}

	public void setSbcd_idcard(String sbcd_idcard) {
		this.sbcd_idcard = sbcd_idcard;
	}

	public String getSbcd_upbankname() {
		return sbcd_upbankname;
	}

	public void setSbcd_upbankname(String sbcd_upbankname) {
		this.sbcd_upbankname = sbcd_upbankname;
	}

	public String getSbcd_taketype() {
		return sbcd_taketype;
	}

	public void setSbcd_taketype(String sbcd_taketype) {
		this.sbcd_taketype = sbcd_taketype;
	}

	public String getSbcd_backcase() {
		return sbcd_backcase;
	}

	public void setSbcd_backcase(String sbcd_backcase) {
		this.sbcd_backcase = sbcd_backcase;
	}

	public String getSbcd_backname() {
		return sbcd_backname;
	}

	public void setSbcd_backname(String sbcd_backname) {
		this.sbcd_backname = sbcd_backname;
	}

	public String getSbcd_backtime() {
		return sbcd_backtime;
	}

	public void setSbcd_backtime(String sbcd_backtime) {
		this.sbcd_backtime = sbcd_backtime;
	}

	public Integer getSbcd_bankdocid() {
		return sbcd_bankdocid;
	}

	public void setSbcd_bankdocid(Integer sbcd_bankdocid) {
		this.sbcd_bankdocid = sbcd_bankdocid;
	}

	public String getSbcd_bank() {
		return sbcd_bank;
	}

	public void setSbcd_bank(String sbcd_bank) {
		this.sbcd_bank = sbcd_bank;
	}

	public Integer getGidnum() {
		return gidnum;
	}

	public void setGidnum(Integer gidnum) {
		this.gidnum = gidnum;
	}

	public String getCoba_client() {
		return coba_client;
	}

	public void setCoba_client(String coba_client) {
		this.coba_client = coba_client;
	}

	public Integer getMsgnum() {
		return msgnum;
	}

	public void setMsgnum(Integer msgnum) {
		this.msgnum = msgnum;
	}

	public Integer getMsgsnum() {
		return msgsnum;
	}

	public void setMsgsnum(Integer msgsnum) {
		this.msgsnum = msgsnum;
	}

	public Integer getSbcd_lbk() {
		return sbcd_lbk;
	}

	public void setSbcd_lbk(Integer sbcd_lbk) {
		this.sbcd_lbk = sbcd_lbk;
	}

	public Integer getSbcd_IDC() {
		return sbcd_IDC;
	}

	public void setSbcd_IDC(Integer sbcd_IDC) {
		this.sbcd_IDC = sbcd_IDC;
	}

	public String getSbcd_handintime() {
		return sbcd_handintime;
	}

	public void setSbcd_handintime(String sbcd_handintime) {
		this.sbcd_handintime = sbcd_handintime;
	}

	public Integer getPicnum() {
		return picnum;
	}

	public void setPicnum(Integer picnum) {
		this.picnum = picnum;
	}

	public String getSbcd_printtime() {
		return sbcd_printtime;
	}

	public void setSbcd_printtime(String sbcd_printtime) {
		this.sbcd_printtime = sbcd_printtime;
	}

	public String getSbcd_printname() {
		return sbcd_printname;
	}

	public void setSbcd_printname(String sbcd_printname) {
		this.sbcd_printname = sbcd_printname;
	}

	public String getSbcd_centerdataname() {
		return sbcd_centerdataname;
	}

	public void setSbcd_centerdataname(String sbcd_centerdataname) {
		this.sbcd_centerdataname = sbcd_centerdataname;
	}

	public String getSbcd_centerdatatime() {
		return sbcd_centerdatatime;
	}

	public void setSbcd_centerdatatime(String sbcd_centerdatatime) {
		this.sbcd_centerdatatime = sbcd_centerdatatime;
	}

	public String getCosp_card_caliname() {
		return cosp_card_caliname;
	}

	public void setCosp_card_caliname(String cosp_card_caliname) {
		if(cosp_card_caliname!=null&&cosp_card_caliname.contains("指定联系人"))
		{
			setCosp(true);
		}
		else
		{
			setCosp(false);
		}
		this.cosp_card_caliname = cosp_card_caliname;
	}

	public String getCosp_bsal_caliname() {
		return cosp_bsal_caliname;
	}

	public void setCosp_bsal_caliname(String cosp_bsal_caliname) {
		this.cosp_bsal_caliname = cosp_bsal_caliname;
	}

	public String getSbcd_stampname() {
		return sbcd_stampname;
	}

	public void setSbcd_stampname(String sbcd_stampname) {
		this.sbcd_stampname = sbcd_stampname;
	}

	public String getSbcd_stamptime() {
		return sbcd_stamptime;
	}

	public void setSbcd_stamptime(String sbcd_stamptime) {
		this.sbcd_stamptime = sbcd_stamptime;
	}

	public Integer getCdst_order() {
		return cdst_order;
	}

	public void setCdst_order(Integer cdst_order) {
		this.cdst_order = cdst_order;
	}

	public String getSbcd_centermaketime() {
		return sbcd_centermaketime;
	}

	public void setSbcd_centermaketime(String sbcd_centermaketime) {
		this.sbcd_centermaketime = sbcd_centermaketime;
	}

	public String getSbcd_centermakename() {
		return sbcd_centermakename;
	}

	public void setSbcd_centermakename(String sbcd_centermakename) {
		this.sbcd_centermakename = sbcd_centermakename;
	}

	public Boolean getCosp() {
		return cosp;
	}

	public void setCosp(Boolean cosp) {
		this.cosp = cosp;
	}

	public String getCoba_shortname() {
		return coba_shortname;
	}

	public void setCoba_shortname(String coba_shortname) {
		this.coba_shortname = coba_shortname;
	}

	public String getCosp_card_data_caliname() {
		return cosp_card_data_caliname;
	}

	public void setCosp_card_data_caliname(String cosp_card_data_caliname) {
		this.cosp_card_data_caliname = cosp_card_data_caliname;
	}

	public String getSbcd_cancelcase() {
		return sbcd_cancelcase;
	}

	public void setSbcd_cancelcase(String sbcd_cancelcase) {
		this.sbcd_cancelcase = sbcd_cancelcase;
	}
	
}
