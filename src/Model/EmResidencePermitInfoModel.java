package Model;

public class EmResidencePermitInfoModel implements Cloneable{
	// erpi_id
	private Integer erpi_id;
	// gid
	private Integer gid;
	// cid
	private Integer cid;
	// ownmonth
	private String ownmonth;
	// erin_id
	private Integer erin_id;
	// 是否填写数码照相图像号
	private boolean erpi_w_photo_number;
	// 是否填写职业技能等级
	private boolean erpi_w_skill_level;
	// 是否填写来深日期
	private boolean erpi_w_a_sz_date;
	// 是否填写入住日期
	private boolean erpi_w_r_date;
	// 是否填写住所类别
	private boolean erpi_w_house_class;
	// 是否填写居住方式
	private boolean erpi_w_r_mode;
	// erpi_t_kind
	private String erpi_t_kind;
	// 交接人
	private String erpi_handover_people;
	// 是否无需录入信息
	private Integer erpi_dw_entering;
	// 付款性质
	private String erpi_payment_kind;
	// erpi_payment_state
	private String erpi_payment_state;
	// 费用
	private Integer erpi_fee;
	// 收费状态
	private Integer erpi_fee_state;
	// 是否需发票
	private Integer erpi_if_invoice;
	// 申办类型
	private Integer erpi_app_type;
	// 申领居住证条件
	private Integer erpi_app_con;
	// 发证对象
	private Integer erpi_send_target;
	// erpi_invoice_date
	private String erpi_invoice_date;
	// erpi_cl_number
	private String erpi_cl_number;
	// erpi_wd_applicant
	private String erpi_wd_applicant;
	// erpi_wd_loan_date
	private String erpi_wd_loan_date;
	// erpi_ri_date
	private String erpi_ri_date;
	// erpi_csd_applicant
	private String erpi_csd_applicant;
	// erpi_csd_loan_date
	private String erpi_csd_loan_date;
	// erpi_csd_clearing_date
	private String erpi_csd_clearing_date;
	// erpi_return_people
	private String erpi_return_people;
	// erpi_return_date
	private String erpi_return_date;
	// erpi_return_reason
	private String erpi_return_reason;
	// erpi_laststate
	private Integer erpi_laststate;
	// erpi_state
	private Integer erpi_state;
	// erpi_remark
	private String erpi_remark;
	// erpi_addname
	private String erpi_addname;
	// erpi_addtime
	private String erpi_addtime;
	// erpi_reg_state
	private Integer erpi_reg_state;
	// erpi_tzl_state
	private Integer erpi_tzl_state;
	// erpi_tapr_id
	private Integer erpi_tapr_id;
	private String fee_state;
	private String app_type;
	private String app_con;
	
	private String feestate;//居住证费用状态
	private String coba_client;
	
	private String erpi_ifedit;
	
	public Object clone()
	{
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
		   return null;
		}
		
	}
	
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
	// spanstate
	private Integer spanstate;

	// epsr_id
	private Integer epsr_id;
	// epsr_erpi_id
	private Integer epsr_erpi_id;
	// epsr_stateid
	private Integer epsr_stateid;
	// epsr_addname
	private String epsr_addname;
	// epsr_addtime
	private String epsr_addtime;
	// epsr_statetime
	private String epsr_statetime;
	// epsr_remark
	private String epsr_remark;

	private String coba_shortname;
	private String coba_company;
	private String emba_name;
	// 账户类型(0:中智户 1:独立户)
	private Integer zhtype;
	private String ifemreg;//是否办理就业登记
	private String emba_idcard;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
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

	public Integer getSpanstate() {
		return spanstate;
	}

	public void setSpanstate(Integer spanstate) {
		this.spanstate = spanstate;
	}

	public Integer getEpsr_id() {
		return epsr_id;
	}

	public void setEpsr_id(Integer epsr_id) {
		this.epsr_id = epsr_id;
	}

	public Integer getEpsr_stateid() {
		return epsr_stateid;
	}

	public void setEpsr_stateid(Integer epsr_stateid) {
		this.epsr_stateid = epsr_stateid;
	}

	public String getEpsr_addname() {
		return epsr_addname;
	}

	public void setEpsr_addname(String epsr_addname) {
		this.epsr_addname = epsr_addname;
	}

	public String getEpsr_addtime() {
		return epsr_addtime;
	}

	public void setEpsr_addtime(String epsr_addtime) {
		this.epsr_addtime = epsr_addtime;
	}

	public String getEpsr_statetime() {
		return epsr_statetime;
	}

	public void setEpsr_statetime(String epsr_statetime) {
		this.epsr_statetime = epsr_statetime;
	}

	public String getEpsr_remark() {
		return epsr_remark;
	}

	public void setEpsr_remark(String epsr_remark) {
		this.epsr_remark = epsr_remark;
	}

	public Integer getErpi_id() {
		return erpi_id;
	}

	public void setErpi_id(Integer erpi_id) {
		this.erpi_id = erpi_id;
	}

	public Integer emba_name() {
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

	public Integer getErin_id() {
		return erin_id;
	}

	public void setErin_id(Integer erin_id) {
		this.erin_id = erin_id;
	}

	public boolean isErpi_w_photo_number() {
		return erpi_w_photo_number;
	}

	public void setErpi_w_photo_number(boolean erpi_w_photo_number) {
		this.erpi_w_photo_number = erpi_w_photo_number;
	}

	public boolean isErpi_w_skill_level() {
		return erpi_w_skill_level;
	}

	public void setErpi_w_skill_level(boolean erpi_w_skill_level) {
		this.erpi_w_skill_level = erpi_w_skill_level;
	}

	public boolean isErpi_w_a_sz_date() {
		return erpi_w_a_sz_date;
	}

	public void setErpi_w_a_sz_date(boolean erpi_w_a_sz_date) {
		this.erpi_w_a_sz_date = erpi_w_a_sz_date;
	}

	public boolean isErpi_w_r_date() {
		return erpi_w_r_date;
	}

	public void setErpi_w_r_date(boolean erpi_w_r_date) {
		this.erpi_w_r_date = erpi_w_r_date;
	}

	public boolean isErpi_w_house_class() {
		return erpi_w_house_class;
	}

	public void setErpi_w_house_class(boolean erpi_w_house_class) {
		this.erpi_w_house_class = erpi_w_house_class;
	}

	public boolean isErpi_w_r_mode() {
		return erpi_w_r_mode;
	}

	public void setErpi_w_r_mode(boolean erpi_w_r_mode) {
		this.erpi_w_r_mode = erpi_w_r_mode;
	}

	public String getErpi_t_kind() {
		return erpi_t_kind;
	}

	public void setErpi_t_kind(String erpi_t_kind) {
		this.erpi_t_kind = erpi_t_kind;
	}

	public String getErpi_handover_people() {
		return erpi_handover_people;
	}

	public void setErpi_handover_people(String erpi_handover_people) {
		this.erpi_handover_people = erpi_handover_people;
	}

	public Integer getErpi_dw_entering() {
		return erpi_dw_entering;
	}

	public void setErpi_dw_entering(Integer erpi_dw_entering) {
		this.erpi_dw_entering = erpi_dw_entering;
	}

	public String getErpi_payment_kind() {
		return erpi_payment_kind;
	}

	public void setErpi_payment_kind(String erpi_payment_kind) {
		this.erpi_payment_kind = erpi_payment_kind;
	}

	public String getErpi_payment_state() {
		return erpi_payment_state;
	}

	public void setErpi_payment_state(String erpi_payment_state) {
		this.erpi_payment_state = erpi_payment_state;
	}

	public Integer getErpi_fee() {
		return erpi_fee;
	}

	public void setErpi_fee(Integer erpi_fee) {
		this.erpi_fee = erpi_fee;
	}

	public Integer getErpi_fee_state() {
		return erpi_fee_state;
	}

	public void setErpi_fee_state(Integer erpi_fee_state) {
		this.erpi_fee_state = erpi_fee_state;
	}

	public Integer getErpi_if_invoice() {
		return erpi_if_invoice;
	}

	public void setErpi_if_invoice(Integer erpi_if_invoice) {
		this.erpi_if_invoice = erpi_if_invoice;
	}

	public Integer getErpi_app_type() {
		return erpi_app_type;
	}

	public void setErpi_app_type(Integer erpi_app_type) {
		this.erpi_app_type = erpi_app_type;
	}

	public Integer getErpi_app_con() {
		return erpi_app_con;
	}

	public void setErpi_app_con(Integer erpi_app_con) {
		this.erpi_app_con = erpi_app_con;
	}

	public Integer getErpi_send_target() {
		return erpi_send_target;
	}

	public void setErpi_send_target(Integer erpi_send_target) {
		this.erpi_send_target = erpi_send_target;
	}

	public String getErpi_invoice_date() {
		return erpi_invoice_date;
	}

	public void setErpi_invoice_date(String erpi_invoice_date) {
		this.erpi_invoice_date = erpi_invoice_date;
	}

	public String getErpi_cl_number() {
		return erpi_cl_number;
	}

	public void setErpi_cl_number(String erpi_cl_number) {
		this.erpi_cl_number = erpi_cl_number;
	}

	public String getErpi_wd_applicant() {
		return erpi_wd_applicant;
	}

	public void setErpi_wd_applicant(String erpi_wd_applicant) {
		this.erpi_wd_applicant = erpi_wd_applicant;
	}

	public String getErpi_wd_loan_date() {
		return erpi_wd_loan_date;
	}

	public void setErpi_wd_loan_date(String erpi_wd_loan_date) {
		this.erpi_wd_loan_date = erpi_wd_loan_date;
	}

	public String getErpi_ri_date() {
		return erpi_ri_date;
	}

	public void setErpi_ri_date(String erpi_ri_date) {
		this.erpi_ri_date = erpi_ri_date;
	}

	public String getErpi_csd_applicant() {
		return erpi_csd_applicant;
	}

	public void setErpi_csd_applicant(String erpi_csd_applicant) {
		this.erpi_csd_applicant = erpi_csd_applicant;
	}

	public String getErpi_csd_loan_date() {
		return erpi_csd_loan_date;
	}

	public void setErpi_csd_loan_date(String erpi_csd_loan_date) {
		this.erpi_csd_loan_date = erpi_csd_loan_date;
	}

	public String getErpi_csd_clearing_date() {
		return erpi_csd_clearing_date;
	}

	public void setErpi_csd_clearing_date(String erpi_csd_clearing_date) {
		this.erpi_csd_clearing_date = erpi_csd_clearing_date;
	}

	public String getErpi_return_people() {
		return erpi_return_people;
	}

	public void setErpi_return_people(String erpi_return_people) {
		this.erpi_return_people = erpi_return_people;
	}

	public String getErpi_return_date() {
		return erpi_return_date;
	}

	public void setErpi_return_date(String erpi_return_date) {
		this.erpi_return_date = erpi_return_date;
	}

	public String getErpi_return_reason() {
		return erpi_return_reason;
	}

	public void setErpi_return_reason(String erpi_return_reason) {
		this.erpi_return_reason = erpi_return_reason;
	}

	public Integer getErpi_laststate() {
		return erpi_laststate;
	}

	public void setErpi_laststate(Integer erpi_laststate) {
		this.erpi_laststate = erpi_laststate;
	}

	public Integer getErpi_state() {
		return erpi_state;
	}

	public void setErpi_state(Integer erpi_state) {
		this.erpi_state = erpi_state;
	}

	public String getErpi_remark() {
		return erpi_remark;
	}

	public void setErpi_remark(String erpi_remark) {
		this.erpi_remark = erpi_remark;
	}

	public String getErpi_addname() {
		return erpi_addname;
	}

	public void setErpi_addname(String erpi_addname) {
		this.erpi_addname = erpi_addname;
	}

	public String getErpi_addtime() {
		return erpi_addtime;
	}

	public void setErpi_addtime(String erpi_addtime) {
		this.erpi_addtime = erpi_addtime;
	}

	public Integer getErpi_reg_state() {
		return erpi_reg_state;
	}

	public void setErpi_reg_state(Integer erpi_reg_state) {
		this.erpi_reg_state = erpi_reg_state;
	}

	public Integer getErpi_tzl_state() {
		return erpi_tzl_state;
	}

	public void setErpi_tzl_state(Integer erpi_tzl_state) {
		this.erpi_tzl_state = erpi_tzl_state;
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

	public Integer getErpi_tapr_id() {
		return erpi_tapr_id;
	}

	public void setErpi_tapr_id(Integer erpi_tapr_id) {
		this.erpi_tapr_id = erpi_tapr_id;
	}

	public String getCoba_company() {
		return coba_company;
	}

	public void setCoba_company(String coba_company) {
		this.coba_company = coba_company;
	}

	public Integer getZhtype() {
		return zhtype;
	}

	public void setZhtype(Integer zhtype) {
		this.zhtype = zhtype;
	}

	public Integer getEpsr_erpi_id() {
		return epsr_erpi_id;
	}

	public void setEpsr_erpi_id(Integer epsr_erpi_id) {
		this.epsr_erpi_id = epsr_erpi_id;
	}

	public String getFee_state() {
		return fee_state;
	}

	public void setFee_state(String fee_state) {
		this.fee_state = fee_state;
	}

	public String getApp_type() {
		return app_type;
	}

	public void setApp_type(String app_type) {
		this.app_type = app_type;
	}

	public String getApp_con() {
		return app_con;
	}

	public void setApp_con(String app_con) {
		this.app_con = app_con;
	}

	public String getFeestate() {
		return feestate;
	}

	public void setFeestate(String feestate) {
		this.feestate = feestate;
	}

	public String getCoba_client() {
		return coba_client;
	}

	public void setCoba_client(String coba_client) {
		this.coba_client = coba_client;
	}

	public String getIfemreg() {
		return ifemreg;
	}

	public void setIfemreg(String ifemreg) {
		this.ifemreg = ifemreg;
	}

	public String getEmba_idcard() {
		return emba_idcard;
	}

	public void setEmba_idcard(String emba_idcard) {
		this.emba_idcard = emba_idcard;
	}

	public Integer getGid() {
		return gid;
	}

	public String getErpi_ifedit() {
		return erpi_ifedit;
	}

	public void setErpi_ifedit(String erpi_ifedit) {
		this.erpi_ifedit = erpi_ifedit;
	}
	
}
