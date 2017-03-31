package Model;

public class EmRegistrationInfoModel implements Cloneable{

	// erin_id
	private Integer erin_id;
	// gid
	private Integer gid;
	// cid
	private Integer cid;
	// ownmonth
	private String ownmonth;
	// 办理性质
	private String erin_t_kind;
	// 身份证号
	private String erin_idcard;
	// 民族编码
	private String erin_folkcode;
	// 民族
	private String erin_folk;
	// 文化程度编码
	private String erin_educationcode;
	// 文化程度
	private String erin_education;
	// 婚姻情况编码
	private String erin_maritalcode;
	// 婚姻情况
	private String erin_marital;
	// 政治面貌编码
	private String erin_partycode;
	// 政治面貌
	private String erin_party;
	// 户籍地址
	private String erin_hjadd;
	// 现居住地
	private String erin_nowadd;
	// 职称
	private String erin_title;
	// 职称编码
	private String erin_titlecode;
	// 职业技能等级编码
	private String erin_skilllevelcode;
	// 社保电脑号
	private String erin_computerid;
	// 参加工作时间
	private String erin_worktime;
	// 是否添加居住证转换
	private Integer erin_if_resident_con;
	// 交接人
	private String erin_handover_people;
	// 曾用名
	private String erin_former_name;
	// 相片图像号
	private String erin_photo_number;
	// 户籍类型编码
	private String erin_hjtypecode;
	// 户籍类型
	private String erin_hjtype;
	// 工种
	private Integer erin_wcin_code;
	// 就业类型
	private Integer erin_oe_type;
	private String oe_type;
	// 合同性质
	private String erin_compact_kind;
	// 无固定劳动期限
	private Integer erin_if_unlimited;
	// 本单位工作起始日期
	private String erin_unit_b_date;
	// 合同年限
	private Integer erin_compact_ylimit;
	// 劳动合同开始日期
	private String erin_compact_s_date;
	// 劳动合同结束日期
	private String erin_compact_e_date;
	// 户口进入深圳方式
	private Integer erin_hj_in_sz_way;
	private String hj_in_sz_way;
	// 入深方式情况注明
	private String erin_in_sz_remark;
	// 户口进入深圳日期
	private String erin_in_sz_date;
	// 月均工资
	private String erin_salary;
	// 是否兼职
	private Integer erin_is_parttime;
	// 身份证完整住址
	private String erin_idcard_address;
	// 居住房屋地址编码
	private String erin_address_number;
	// 来深时间
	private String erin_come_sz_date;
	// 来深居住事由
	private Integer erin_come_sz_reason;
	private String come_sz_reason;
	// 住所类别
	private Integer erin_house_class;
	private String house_class;
	// 服务处所
	private String erin_s_place;
	// 居住方式
	private Integer erin_house_mode;
	private String house_mode;
	// 入住时间
	private String erin_r_date;
	// 档案存放机构
	private String erin_file_place;
	// 归档人
	private String erin_in_file_people;
	// 领取人
	private String erin_get_people;
	// 终止状态
	private Integer erin_stop_state;
	// 终止原因
	private String erin_stop_reason;
	// 终止日期
	private String erin_stop_date;
	// 终止申请人
	private String erin_stop_people;
	// 退回人
	private String erin_back_people;
	// 退回时间
	private String erin_back_date;
	// 退回原因
	private String erin_back_reason;
	// 上个状态
	private Integer erin_laststate;
	// 状态
	private Integer erin_state;
	// 退材料状态
	private Integer erin_tzl_state;
	// erin_remark
	private String erin_remark;
	// erin_addname
	private String erin_addname;
	// erin_addtime
	private String erin_addtime;
	// erin_reg_state
	private Integer erin_reg_state;
	// erin_tapr_id
	private Integer erin_tapr_id;
	// 是否深户
	private String is_sh;
	// 账户类型(0:中智户 1:独立户)
	private Integer zhtype;
	// 就业类型编码
	private String erin_regtypecode;
	// 本人固定电话
	private String erin_phone;
	// 本人手机
	private String erin_mobile;
	// 紧急联系人姓名
	private String erin_epname;
	// 紧急联系人固定电话
	private String erin_epphone;
	// 紧急联系人手机
	private String erin_epmobile;
	// 广东省流动人口避孕节育情况报告单号
	private String erin_birthcontrol;
	// 职业技能等级
	private String erin_skilllevel;
	// 工种
	private String erin_wcin;

	// id
	private Integer id;
	// stateid
	private Integer stateid;
	// statename
	private String statename;
	// operate
	private String operate;
	// type
	private Integer type;
	// typename
	private String typename;
	// orderid
	private Integer orderid;
	// state
	private Integer state;
	private Integer spanstate;

	// ersr_id
	private Integer ersr_id;
	// ersr_erin_id
	private Integer ersr_erin_id;
	// ersr_stateid
	private Integer ersr_stateid;
	// ersr_addname
	private String ersr_addname;
	// ersr_addtime
	private String ersr_addtime;
	// ersr_statetime
	private String ersr_statetime;
	// ersr_remark
	private String ersr_remark;

	private String coba_company;
	private String coba_shortname;
	private String emba_name;
	private String emba_sex;
	private String emba_birth;
	private Integer cori_sz_puzu_id;
	private Integer cori_wd_puzu_id;
	private String emba_phone;
	private String emba_mobile;
	private String emba_epname;
	private String emba_epmobile;
	private String emba_birthcontrol;

	private String stop_statename;
	
	private String erin_p_stop_date;
	private String coba_client;
	
	private String erin_getdata_date;
	private String erin_dw_entering;
	private Integer erin_ifbackdata;
	
	private String erin_signname;
	private String erin_signtime;
	private String erin_databackname;
	private String erin_databacktime;
	private String erin_signcenter;
	private String erin_signcentertime;
	private String erin_datakeeptype;
	
	public Object clone()
	{
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
		   return null;
		}
		
	}
	
	public String getCoba_company() {
		return coba_company;
	}

	public void setCoba_company(String coba_company) {
		this.coba_company = coba_company;
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

	public String getEmba_birth() {
		return emba_birth;
	}

	public void setEmba_birth(String emba_birth) {
		this.emba_birth = emba_birth;
	}

	public Integer getErin_id() {
		return erin_id;
	}

	public void setErin_id(Integer erin_id) {
		this.erin_id = erin_id;
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

	public String getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(String ownmonth) {
		this.ownmonth = ownmonth;
	}

	public String getErin_t_kind() {
		return erin_t_kind;
	}

	public void setErin_t_kind(String erin_t_kind) {
		this.erin_t_kind = erin_t_kind;
	}

	public String getErin_handover_people() {
		return erin_handover_people;
	}

	public void setErin_handover_people(String erin_handover_people) {
		this.erin_handover_people = erin_handover_people;
	}

	public String getErin_former_name() {
		return erin_former_name;
	}

	public void setErin_former_name(String erin_former_name) {
		this.erin_former_name = erin_former_name;
	}

	public String getErin_photo_number() {
		return erin_photo_number;
	}

	public void setErin_photo_number(String erin_photo_number) {
		this.erin_photo_number = erin_photo_number;
	}

	public String getErin_hjtype() {
		return erin_hjtype;
	}

	public void setErin_hjtype(String erin_hjtype) {
		this.erin_hjtype = erin_hjtype;
	}

	public Integer getErin_wcin_code() {
		return erin_wcin_code;
	}

	public void setErin_wcin_code(Integer erin_wcin_code) {
		this.erin_wcin_code = erin_wcin_code;
	}

	public Integer getErin_oe_type() {
		return erin_oe_type;
	}

	public void setErin_oe_type(Integer erin_oe_type) {
		this.erin_oe_type = erin_oe_type;
	}

	public String getErin_compact_kind() {
		return erin_compact_kind;
	}

	public void setErin_compact_kind(String erin_compact_kind) {
		this.erin_compact_kind = erin_compact_kind;
	}

	public Integer getErin_if_unlimited() {
		return erin_if_unlimited;
	}

	public void setErin_if_unlimited(Integer erin_if_unlimited) {
		this.erin_if_unlimited = erin_if_unlimited;
	}

	public Integer getErin_compact_ylimit() {
		return erin_compact_ylimit;
	}

	public void setErin_compact_ylimit(Integer erin_compact_ylimit) {
		this.erin_compact_ylimit = erin_compact_ylimit;
	}

	public String getErin_compact_s_date() {
		return erin_compact_s_date;
	}

	public void setErin_compact_s_date(String erin_compact_s_date) {
		this.erin_compact_s_date = erin_compact_s_date;
	}

	public String getErin_compact_e_date() {
		return erin_compact_e_date;
	}

	public void setErin_compact_e_date(String erin_compact_e_date) {
		this.erin_compact_e_date = erin_compact_e_date;
	}

	public Integer getErin_hj_in_sz_way() {
		return erin_hj_in_sz_way;
	}

	public void setErin_hj_in_sz_way(Integer erin_hj_in_sz_way) {
		this.erin_hj_in_sz_way = erin_hj_in_sz_way;
	}

	public String getErin_in_sz_remark() {
		return erin_in_sz_remark;
	}

	public void setErin_in_sz_remark(String erin_in_sz_remark) {
		this.erin_in_sz_remark = erin_in_sz_remark;
	}

	public String getErin_salary() {
		return erin_salary;
	}

	public void setErin_salary(String erin_salary) {
		this.erin_salary = erin_salary;
	}

	public Integer getErin_is_parttime() {
		return erin_is_parttime;
	}

	public void setErin_is_parttime(Integer erin_is_parttime) {
		this.erin_is_parttime = erin_is_parttime;
	}

	public String getErin_idcard_address() {
		return erin_idcard_address;
	}

	public void setErin_idcard_address(String erin_idcard_address) {
		this.erin_idcard_address = erin_idcard_address;
	}

	public String getErin_address_number() {
		return erin_address_number;
	}

	public void setErin_address_number(String erin_address_number) {
		this.erin_address_number = erin_address_number;
	}

	public String getErin_come_sz_date() {
		return erin_come_sz_date;
	}

	public void setErin_come_sz_date(String erin_come_sz_date) {
		this.erin_come_sz_date = erin_come_sz_date;
	}

	public Integer getErin_come_sz_reason() {
		return erin_come_sz_reason;
	}

	public void setErin_come_sz_reason(Integer erin_come_sz_reason) {
		this.erin_come_sz_reason = erin_come_sz_reason;
	}

	public Integer getErin_house_class() {
		return erin_house_class;
	}

	public void setErin_house_class(Integer erin_house_class) {
		this.erin_house_class = erin_house_class;
	}

	public String getErin_s_place() {
		return erin_s_place;
	}

	public void setErin_s_place(String erin_s_place) {
		this.erin_s_place = erin_s_place;
	}

	public Integer getErin_house_mode() {
		return erin_house_mode;
	}

	public void setErin_house_mode(Integer erin_house_mode) {
		this.erin_house_mode = erin_house_mode;
	}

	public String getErin_r_date() {
		return erin_r_date;
	}

	public void setErin_r_date(String erin_r_date) {
		this.erin_r_date = erin_r_date;
	}

	public String getErin_file_place() {
		return erin_file_place;
	}

	public void setErin_file_place(String erin_file_place) {
		this.erin_file_place = erin_file_place;
	}

	public String getErin_in_file_people() {
		return erin_in_file_people;
	}

	public void setErin_in_file_people(String erin_in_file_people) {
		this.erin_in_file_people = erin_in_file_people;
	}

	public String getErin_get_people() {
		return erin_get_people;
	}

	public void setErin_get_people(String erin_get_people) {
		this.erin_get_people = erin_get_people;
	}

	public Integer getErin_stop_state() {
		return erin_stop_state;
	}

	public void setErin_stop_state(Integer erin_stop_state) {
		this.erin_stop_state = erin_stop_state;
	}

	public String getErin_stop_reason() {
		return erin_stop_reason;
	}

	public void setErin_stop_reason(String erin_stop_reason) {
		this.erin_stop_reason = erin_stop_reason;
	}

	public String getErin_stop_date() {
		return erin_stop_date;
	}

	public void setErin_stop_date(String erin_stop_date) {
		this.erin_stop_date = erin_stop_date;
	}

	public String getErin_stop_people() {
		return erin_stop_people;
	}

	public void setErin_stop_people(String erin_stop_people) {
		this.erin_stop_people = erin_stop_people;
	}

	public String getErin_back_people() {
		return erin_back_people;
	}

	public void setErin_back_people(String erin_back_people) {
		this.erin_back_people = erin_back_people;
	}

	public String getErin_back_date() {
		return erin_back_date;
	}

	public void setErin_back_date(String erin_back_date) {
		this.erin_back_date = erin_back_date;
	}

	public String getErin_back_reason() {
		return erin_back_reason;
	}

	public void setErin_back_reason(String erin_back_reason) {
		this.erin_back_reason = erin_back_reason;
	}

	public Integer getErin_state() {
		return erin_state;
	}

	public void setErin_state(Integer erin_state) {
		this.erin_state = erin_state;
	}

	public String getErin_remark() {
		return erin_remark;
	}

	public void setErin_remark(String erin_remark) {
		this.erin_remark = erin_remark;
	}

	public String getErin_addname() {
		return erin_addname;
	}

	public void setErin_addname(String erin_addname) {
		this.erin_addname = erin_addname;
	}

	public String getErin_addtime() {
		return erin_addtime;
	}

	public void setErin_addtime(String erin_addtime) {
		this.erin_addtime = erin_addtime;
	}

	public Integer getStateid() {
		return stateid;
	}

	public void setStateid(Integer stateid) {
		this.stateid = stateid;
	}

	public String getStatename() {
		return statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getErsr_id() {
		return ersr_id;
	}

	public void setErsr_id(Integer ersr_id) {
		this.ersr_id = ersr_id;
	}

	public Integer getErsr_erin_id() {
		return ersr_erin_id;
	}

	public void setErsr_erin_id(Integer ersr_erin_id) {
		this.ersr_erin_id = ersr_erin_id;
	}

	public Integer getErsr_stateid() {
		return ersr_stateid;
	}

	public void setErsr_stateid(Integer ersr_stateid) {
		this.ersr_stateid = ersr_stateid;
	}

	public String getErsr_addname() {
		return ersr_addname;
	}

	public void setErsr_addname(String ersr_addname) {
		this.ersr_addname = ersr_addname;
	}

	public String getErsr_addtime() {
		return ersr_addtime;
	}

	public void setErsr_addtime(String ersr_addtime) {
		this.ersr_addtime = ersr_addtime;
	}

	public String getErsr_statetime() {
		return ersr_statetime;
	}

	public void setErsr_statetime(String ersr_statetime) {
		this.ersr_statetime = ersr_statetime;
	}

	public Integer getErin_laststate() {
		return erin_laststate;
	}

	public void setErin_laststate(Integer erin_laststate) {
		this.erin_laststate = erin_laststate;
	}

	public String getErin_idcard() {
		return erin_idcard;
	}

	public void setErin_idcard(String erin_idcard) {
		this.erin_idcard = erin_idcard;
	}

	public String getErin_folk() {
		return erin_folk;
	}

	public void setErin_folk(String erin_folk) {
		this.erin_folk = erin_folk;
	}

	public String getErin_educationcode() {
		return erin_educationcode;
	}

	public void setErin_educationcode(String erin_educationcode) {
		this.erin_educationcode = erin_educationcode;
	}

	public String getErin_education() {
		return erin_education;
	}

	public void setErin_education(String erin_education) {
		this.erin_education = erin_education;
	}

	public String getErin_marital() {
		return erin_marital;
	}

	public void setErin_marital(String erin_marital) {
		this.erin_marital = erin_marital;
	}

	public String getErin_party() {
		return erin_party;
	}

	public void setErin_party(String erin_party) {
		this.erin_party = erin_party;
	}

	public String getErin_hjadd() {
		return erin_hjadd;
	}

	public void setErin_hjadd(String erin_hjadd) {
		this.erin_hjadd = erin_hjadd;
	}

	public String getErin_nowadd() {
		return erin_nowadd;
	}

	public void setErin_nowadd(String erin_nowadd) {
		this.erin_nowadd = erin_nowadd;
	}

	public String getErin_title() {
		return erin_title;
	}

	public void setErin_title(String erin_title) {
		this.erin_title = erin_title;
	}

	public String getErin_titlecode() {
		return erin_titlecode;
	}

	public void setErin_titlecode(String erin_titlecode) {
		this.erin_titlecode = erin_titlecode;
	}

	public String getErin_computerid() {
		return erin_computerid;
	}

	public void setErin_computerid(String erin_computerid) {
		this.erin_computerid = erin_computerid;
	}

	public String getErin_worktime() {
		return erin_worktime;
	}

	public void setErin_worktime(String erin_worktime) {
		this.erin_worktime = erin_worktime;
	}

	public Integer getErin_if_resident_con() {
		return erin_if_resident_con;
	}

	public void setErin_if_resident_con(Integer erin_if_resident_con) {
		this.erin_if_resident_con = erin_if_resident_con;
	}

	public String getErin_hjtypecode() {
		return erin_hjtypecode;
	}

	public void setErin_hjtypecode(String erin_hjtypecode) {
		this.erin_hjtypecode = erin_hjtypecode;
	}

	public Integer getErin_tzl_state() {
		return erin_tzl_state;
	}

	public void setErin_tzl_state(Integer erin_tzl_state) {
		this.erin_tzl_state = erin_tzl_state;
	}

	public Integer getCori_sz_puzu_id() {
		return cori_sz_puzu_id;
	}

	public void setCori_sz_puzu_id(Integer cori_sz_puzu_id) {
		this.cori_sz_puzu_id = cori_sz_puzu_id;
	}

	public Integer getCori_wd_puzu_id() {
		return cori_wd_puzu_id;
	}

	public void setCori_wd_puzu_id(Integer cori_wd_puzu_id) {
		this.cori_wd_puzu_id = cori_wd_puzu_id;
	}

	public Integer getErin_reg_state() {
		return erin_reg_state;
	}

	public void setErin_reg_state(Integer erin_reg_state) {
		this.erin_reg_state = erin_reg_state;
	}

	public Integer getErin_tapr_id() {
		return erin_tapr_id;
	}

	public void setErin_tapr_id(Integer erin_tapr_id) {
		this.erin_tapr_id = erin_tapr_id;
	}

	public String getIs_sh() {
		return is_sh;
	}

	public void setIs_sh(String is_sh) {
		this.is_sh = is_sh;
	}

	public Integer getZhtype() {
		return zhtype;
	}

	public void setZhtype(Integer zhtype) {
		this.zhtype = zhtype;
	}

	public String getErin_folkcode() {
		return erin_folkcode;
	}

	public void setErin_folkcode(String erin_folkcode) {
		this.erin_folkcode = erin_folkcode;
	}

	public String getErin_partycode() {
		return erin_partycode;
	}

	public void setErin_partycode(String erin_partycode) {
		this.erin_partycode = erin_partycode;
	}

	public String getErin_maritalcode() {
		return erin_maritalcode;
	}

	public void setErin_maritalcode(String erin_maritalcode) {
		this.erin_maritalcode = erin_maritalcode;
	}

	public String getErin_skilllevelcode() {
		return erin_skilllevelcode;
	}

	public void setErin_skilllevelcode(String erin_skilllevelcode) {
		this.erin_skilllevelcode = erin_skilllevelcode;
	}

	public String getErin_regtypecode() {
		return erin_regtypecode;
	}

	public void setErin_regtypecode(String erin_regtypecode) {
		this.erin_regtypecode = erin_regtypecode;
	}

	public String getEmba_phone() {
		return emba_phone;
	}

	public void setEmba_phone(String emba_phone) {
		this.emba_phone = emba_phone;
	}

	public String getEmba_mobile() {
		return emba_mobile;
	}

	public void setEmba_mobile(String emba_mobile) {
		this.emba_mobile = emba_mobile;
	}

	public String getEmba_epname() {
		return emba_epname;
	}

	public void setEmba_epname(String emba_epname) {
		this.emba_epname = emba_epname;
	}

	public String getEmba_epmobile() {
		return emba_epmobile;
	}

	public void setEmba_epmobile(String emba_epmobile) {
		this.emba_epmobile = emba_epmobile;
	}

	public String getEmba_birthcontrol() {
		return emba_birthcontrol;
	}

	public void setEmba_birthcontrol(String emba_birthcontrol) {
		this.emba_birthcontrol = emba_birthcontrol;
	}

	public String getErin_unit_b_date() {
		return erin_unit_b_date;
	}

	public void setErin_unit_b_date(String erin_unit_b_date) {
		this.erin_unit_b_date = erin_unit_b_date;
	}

	public String getErin_phone() {
		return erin_phone;
	}

	public void setErin_phone(String erin_phone) {
		this.erin_phone = erin_phone;
	}

	public String getErin_mobile() {
		return erin_mobile;
	}

	public void setErin_mobile(String erin_mobile) {
		this.erin_mobile = erin_mobile;
	}

	public String getErin_epname() {
		return erin_epname;
	}

	public void setErin_epname(String erin_epname) {
		this.erin_epname = erin_epname;
	}

	public String getErin_epphone() {
		return erin_epphone;
	}

	public void setErin_epphone(String erin_epphone) {
		this.erin_epphone = erin_epphone;
	}

	public String getErin_epmobile() {
		return erin_epmobile;
	}

	public void setErin_epmobile(String erin_epmobile) {
		this.erin_epmobile = erin_epmobile;
	}

	public String getErin_birthcontrol() {
		return erin_birthcontrol;
	}

	public void setErin_birthcontrol(String erin_birthcontrol) {
		this.erin_birthcontrol = erin_birthcontrol;
	}

	public String getErin_skilllevel() {
		return erin_skilllevel;
	}

	public void setErin_skilllevel(String erin_skilllevel) {
		this.erin_skilllevel = erin_skilllevel;
	}

	public String getErin_wcin() {
		return erin_wcin;
	}

	public void setErin_wcin(String erin_wcin) {
		this.erin_wcin = erin_wcin;
	}

	public String getCome_sz_reason() {
		return come_sz_reason;
	}

	public void setCome_sz_reason(String come_sz_reason) {
		this.come_sz_reason = come_sz_reason;
	}

	public String getHouse_class() {
		return house_class;
	}

	public void setHouse_class(String house_class) {
		this.house_class = house_class;
	}

	public String getHouse_mode() {
		return house_mode;
	}

	public void setHouse_mode(String house_mode) {
		this.house_mode = house_mode;
	}

	public String getErsr_remark() {
		return ersr_remark;
	}

	public void setErsr_remark(String ersr_remark) {
		this.ersr_remark = ersr_remark;
	}

	public String getErin_in_sz_date() {
		return erin_in_sz_date;
	}

	public void setErin_in_sz_date(String erin_in_sz_date) {
		this.erin_in_sz_date = erin_in_sz_date;
	}

	public String getHj_in_sz_way() {
		return hj_in_sz_way;
	}

	public void setHj_in_sz_way(String hj_in_sz_way) {
		this.hj_in_sz_way = hj_in_sz_way;
	}

	public String getOe_type() {
		return oe_type;
	}

	public void setOe_type(String oe_type) {
		this.oe_type = oe_type;
	}

	public Integer getSpanstate() {
		return spanstate;
	}

	public void setSpanstate(Integer spanstate) {
		this.spanstate = spanstate;
	}

	public String getStop_statename() {
		return stop_statename;
	}

	public void setStop_statename(String stop_statename) {
		this.stop_statename = stop_statename;
	}

	public String getErin_p_stop_date() {
		return erin_p_stop_date;
	}

	public void setErin_p_stop_date(String erin_p_stop_date) {
		this.erin_p_stop_date = erin_p_stop_date;
	}

	public String getCoba_client() {
		return coba_client;
	}

	public void setCoba_client(String coba_client) {
		this.coba_client = coba_client;
	}

	public String getErin_getdata_date() {
		return erin_getdata_date;
	}

	public void setErin_getdata_date(String erin_getdata_date) {
		this.erin_getdata_date = erin_getdata_date;
	}

	public String getErin_dw_entering() {
		return erin_dw_entering;
	}

	public void setErin_dw_entering(String erin_dw_entering) {
		this.erin_dw_entering = erin_dw_entering;
	}

	public Integer getErin_ifbackdata() {
		return erin_ifbackdata;
	}

	public void setErin_ifbackdata(Integer erin_ifbackdata) {
		this.erin_ifbackdata = erin_ifbackdata;
	}

	public String getErin_signname() {
		return erin_signname;
	}

	public void setErin_signname(String erin_signname) {
		this.erin_signname = erin_signname;
	}

	public String getErin_signtime() {
		return erin_signtime;
	}

	public void setErin_signtime(String erin_signtime) {
		this.erin_signtime = erin_signtime;
	}

	public String getErin_databackname() {
		return erin_databackname;
	}

	public void setErin_databackname(String erin_databackname) {
		this.erin_databackname = erin_databackname;
	}

	public String getErin_databacktime() {
		return erin_databacktime;
	}

	public void setErin_databacktime(String erin_databacktime) {
		this.erin_databacktime = erin_databacktime;
	}

	public String getErin_signcenter() {
		return erin_signcenter;
	}

	public void setErin_signcenter(String erin_signcenter) {
		this.erin_signcenter = erin_signcenter;
	}

	public String getErin_signcentertime() {
		return erin_signcentertime;
	}

	public void setErin_signcentertime(String erin_signcentertime) {
		this.erin_signcentertime = erin_signcentertime;
	}

	public String getErin_datakeeptype() {
		return erin_datakeeptype;
	}

	public void setErin_datakeeptype(String erin_datakeeptype) {
		this.erin_datakeeptype = erin_datakeeptype;
	}
	
}
