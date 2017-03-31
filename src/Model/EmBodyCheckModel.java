package Model;

import java.math.BigDecimal;

public class EmBodyCheckModel {
	// / embc_id
	private Integer embc_id;
	// / CID
	private Integer cid;
	// / GID
	private Integer gid;
	private String emba_idcard, coba_clientmanager, coba_client,
			coba_shortname, emba_sex, ebcl_itemgroup;
	// / embc_bcid
	private String embc_bcid;
	// / embc_company
	private String embc_company;
	private String coba_company;
	// / embc_shortname
	private String embc_shortname;
	// / embc_spell
	private String embc_spell;
	// / embc_name
	private String embc_name;
	// / embc_idcard
	private String embc_idcard;
	// / embc_sex
	private String embc_sex;
	// / embc_age
	private Integer embc_age;
	// / embc_marry
	private String embc_marry;
	// / embc_remark
	private String embc_remark;
	// / embc_client
	private String embc_client;
	// / embc_drawreportdate
	private String embc_drawreportdate;
	// / embc_showreportdate
	private String embc_showreportdate;
	// / embc_showreportpeople
	private String embc_showreportpeople;
	// / embc_finalcharge
	private BigDecimal embc_finalcharge;
	// / embc_balancedate
	private String embc_balancedate;
	// / embc_addtime
	private String embc_addtime;
	// / embc_addname
	private String embc_addname;
	// / embc_state
	private Integer embc_state;
	// / 新增数据时使用(绑定唯一标识)
	private String embc_markid;
	// / embc_modname
	private String embc_modname;
	// / embc_modtime
	private String embc_modtime;

	private EmBodyCheckListModel model = new EmBodyCheckListModel();

	private String ebcl_items;
	private String ebcl_bookdate;
	private String bookdate;// 安排体检时间与预约时间共用
	private Integer ebcl_type;// 体检类型的代号
	private String ebcl_typename;// 体检类型的中文
	private String ebcs_hospital;// 机构名称
	private String embc_statebname;
	private Integer embc_tapr_id;
	private String ebsa_address;// 体检地址
	private Integer ebcl_bookmode;
	private String ebcl_bookmode2; // 体检类型
	private String ebcl_itemnums;
	private Integer ebcl_id;
	private BigDecimal ebcl_charge;
	private String ebcl_plandate;
	private String ebcl_linkdate;
	private String ebcl_drawformdate;
	private String ebcl_showformdate;
	private String ebcl_showformpeople;
	private String emcl_drawreportdate;
	private String ebcl_completedate;
	private String ebcl_showreportdate;
	private String ebcl_showreportpeople;
	private String ebcl_drawreportdate;
	private Integer ebcl_state;
	private String stateName;
	private Integer ebcl_area;
	private String ebcl_backcase;
	private String ebcl_backname, ebcl_backdate;
	private String ebcl_remark;
	private String ebcl_addname, ebcl_addtime;
	private boolean checked;
	private String ocon;
	private String ebcl_balancedate;
	private String ebcl_enddate;
	private BigDecimal ebcl_discount;
	private String ebcl_bcid;
	private BigDecimal ebcl_finalcharge;
	private String ebcl_clientshowdate;
	private String ebcl_showclient;
	private Integer ebcl_hospital;
	private String embc_mobile;
	private String ebcs_tips;
	private String coba_manager;
	private Integer symr_readstate;
	private String ebcl_itemsstr;
	private String ebcl_clientbackcase;
	private String ebcl_clientbackname;
	private String ebcl_clientbacktime;
	private Integer mnum;
	private String cosp_bcrp_postfee;
	private String cosp_bcrp_careinfo;
	private String ebsa_doc;
	private String empType;

	public Integer getEmbc_id() {
		return embc_id;
	}

	public void setEmbc_id(Integer embc_id) {
		this.embc_id = embc_id;
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

	public String getEmbc_bcid() {
		return embc_bcid;
	}

	public void setEmbc_bcid(String embc_bcid) {
		this.embc_bcid = embc_bcid;
	}

	public String getEmbc_company() {
		return embc_company;
	}

	public void setEmbc_company(String embc_company) {
		this.embc_company = embc_company;
	}

	public String getEmbc_shortname() {
		return embc_shortname;
	}

	public void setEmbc_shortname(String embc_shortname) {
		this.embc_shortname = embc_shortname;
	}

	public String getEmbc_spell() {
		return embc_spell;
	}

	public void setEmbc_spell(String embc_spell) {
		this.embc_spell = embc_spell;
	}

	public String getEmbc_name() {
		return embc_name;
	}

	public void setEmbc_name(String embc_name) {
		this.embc_name = embc_name;
	}

	public String getEmbc_idcard() {
		return embc_idcard;
	}

	public void setEmbc_idcard(String embc_idcard) {
		this.embc_idcard = embc_idcard;
	}

	public String getEmbc_sex() {
		return embc_sex;
	}

	public void setEmbc_sex(String embc_sex) {
		this.embc_sex = embc_sex;
	}

	public Integer getEmbc_age() {
		return embc_age;
	}

	public void setEmbc_age(Integer embc_age) {
		this.embc_age = embc_age;
	}

	public String getEmbc_marry() {
		return embc_marry;
	}

	public void setEmbc_marry(String embc_marry) {
		if (embc_marry == null || embc_marry.equals("null")) {
			embc_marry = "";
		}
		this.embc_marry = embc_marry;
	}

	public String getEmbc_remark() {
		return embc_remark;
	}

	public void setEmbc_remark(String embc_remark) {
		this.embc_remark = embc_remark;
	}

	public String getEmbc_client() {
		return embc_client;
	}

	public void setEmbc_client(String embc_client) {
		this.embc_client = embc_client;
	}

	public String getEmbc_drawreportdate() {
		return embc_drawreportdate;
	}

	public void setEmbc_drawreportdate(String embc_drawreportdate) {
		this.embc_drawreportdate = embc_drawreportdate;
	}

	public String getEmbc_showreportdate() {
		return embc_showreportdate;
	}

	public void setEmbc_showreportdate(String embc_showreportdate) {
		this.embc_showreportdate = embc_showreportdate;
	}

	public String getEmbc_showreportpeople() {
		return embc_showreportpeople;
	}

	public void setEmbc_showreportpeople(String embc_showreportpeople) {
		this.embc_showreportpeople = embc_showreportpeople;
	}

	public BigDecimal getEmbc_finalcharge() {
		return embc_finalcharge;
	}

	public void setEmbc_finalcharge(BigDecimal embc_finalcharge) {
		this.embc_finalcharge = embc_finalcharge;
	}

	public String getEmbc_balancedate() {
		return embc_balancedate;
	}

	public void setEmbc_balancedate(String embc_balancedate) {
		this.embc_balancedate = embc_balancedate;
	}

	public String getEmbc_addtime() {
		return embc_addtime;
	}

	public void setEmbc_addtime(String embc_addtime) {
		this.embc_addtime = embc_addtime;
	}

	public String getEmbc_addname() {
		return embc_addname;
	}

	public void setEmbc_addname(String embc_addname) {
		this.embc_addname = embc_addname;
	}

	public Integer getEmbc_state() {
		return embc_state;
	}

	public void setEmbc_state(Integer embc_state) {
		this.embc_state = embc_state;
	}

	public String getEmbc_markid() {
		return embc_markid;
	}

	public void setEmbc_markid(String embc_markid) {
		this.embc_markid = embc_markid;
	}

	public String getEmbc_modname() {
		return embc_modname;
	}

	public void setEmbc_modname(String embc_modname) {
		this.embc_modname = embc_modname;
	}

	public String getEmbc_modtime() {
		return embc_modtime;
	}

	public void setEmbc_modtime(String embc_modtime) {
		this.embc_modtime = embc_modtime;
	}

	public String getEbcl_items() {
		return ebcl_items;
	}

	public BigDecimal getEbcl_discount() {
		return ebcl_discount;
	}

	public void setEbcl_discount(BigDecimal ebcl_discount) {
		this.ebcl_discount = ebcl_discount;
	}

	public void setEbcl_items(String ebcl_items) {
		if (ebcl_items.contains(",")) {
			this.ebcl_items = "自选项目";
		} else {
			this.ebcl_items = ebcl_items;
		}
	}

	public String getEbcl_bookdate() {
		return ebcl_bookdate;
	}

	public void setEbcl_bookdate(String ebcl_bookdate) {
		this.ebcl_bookdate = ebcl_bookdate;
	}

	public Integer getEbcl_type() {
		return ebcl_type;
	}

	public void setEbcl_type(Integer ebcl_type) {
		this.ebcl_type = ebcl_type;
	}

	public String getEbcl_typename() {
		return ebcl_typename;
	}

	public void setEbcl_typename(String ebcl_typename) {
		this.ebcl_typename = ebcl_typename;
	}

	public String getEbcs_hospital() {
		return ebcs_hospital;
	}

	public void setEbcs_hospital(String ebcs_hospital) {
		this.ebcs_hospital = ebcs_hospital;
	}

	public String getEmbc_statebname() {
		return embc_statebname;
	}

	public void setEmbc_statebname(String embc_statebname) {
		this.embc_statebname = embc_statebname;
	}

	public Integer getEmbc_tapr_id() {
		return embc_tapr_id;
	}

	public void setEmbc_tapr_id(Integer embc_tapr_id) {
		this.embc_tapr_id = embc_tapr_id;
	}

	public String getEbsa_address() {
		return ebsa_address;
	}

	public void setEbsa_address(String ebsa_address) {
		this.ebsa_address = ebsa_address;
	}

	public Integer getEbcl_bookmode() {
		return ebcl_bookmode;
	}

	public void setEbcl_bookmode(Integer ebcl_bookmode) {
		this.ebcl_bookmode = ebcl_bookmode;
	}

	public String getEbcl_itemnums() {
		return ebcl_itemnums;
	}

	public void setEbcl_itemnums(String ebcl_itemnums) {
		this.ebcl_itemnums = ebcl_itemnums;
	}

	public Integer getEbcl_id() {
		return ebcl_id;
	}

	public void setEbcl_id(Integer ebcl_id) {
		this.ebcl_id = ebcl_id;
	}

	public BigDecimal getEbcl_charge() {
		return ebcl_charge;
	}

	public void setEbcl_charge(BigDecimal ebcl_charge) {
		this.ebcl_charge = ebcl_charge;
	}

	public String getEbcl_plandate() {
		return ebcl_plandate;
	}

	public void setEbcl_plandate(String ebcl_plandate) {
		this.ebcl_plandate = ebcl_plandate;
	}

	public String getEbcl_linkdate() {
		return ebcl_linkdate;
	}

	public void setEbcl_linkdate(String ebcl_linkdate) {
		this.ebcl_linkdate = ebcl_linkdate;
	}

	public String getEbcl_drawformdate() {
		return ebcl_drawformdate;
	}

	public void setEbcl_drawformdate(String ebcl_drawformdate) {
		this.ebcl_drawformdate = ebcl_drawformdate;
	}

	public String getEbcl_showformdate() {
		return ebcl_showformdate;
	}

	public void setEbcl_showformdate(String ebcl_showformdate) {
		this.ebcl_showformdate = ebcl_showformdate;
	}

	public String getEbcl_showformpeople() {
		return ebcl_showformpeople;
	}

	public void setEbcl_showformpeople(String ebcl_showformpeople) {
		this.ebcl_showformpeople = ebcl_showformpeople;
	}

	public String getEbcl_completedate() {
		return ebcl_completedate;
	}

	public void setEbcl_completedate(String ebcl_completedate) {
		this.ebcl_completedate = ebcl_completedate;
	}

	public String getEbcl_showreportdate() {
		return ebcl_showreportdate;
	}

	public void setEbcl_showreportdate(String ebcl_showreportdate) {
		this.ebcl_showreportdate = ebcl_showreportdate;
	}

	public String getEbcl_showreportpeople() {
		return ebcl_showreportpeople;
	}

	public void setEbcl_showreportpeople(String ebcl_showreportpeople) {
		this.ebcl_showreportpeople = ebcl_showreportpeople;
	}

	public String getEmcl_drawreportdate() {
		return emcl_drawreportdate;
	}

	public void setEmcl_drawreportdate(String emcl_drawreportdate) {
		this.emcl_drawreportdate = emcl_drawreportdate;
	}

	public String getEbcl_bookmode2() {
		return ebcl_bookmode2;
	}

	public void setEbcl_bookmode2(String ebcl_bookmode2) {
		this.ebcl_bookmode2 = ebcl_bookmode2;
	}

	public String getBookdate() {
		return bookdate;
	}

	public void setBookdate(String bookdate) {
		this.bookdate = bookdate;
	}

	public Integer getEbcl_state() {
		return ebcl_state;
	}

	public void setEbcl_state(Integer ebcl_state) {
		this.ebcl_state = ebcl_state;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Integer getEbcl_area() {
		return ebcl_area;
	}

	public String getEbcl_bcid() {
		return ebcl_bcid;
	}

	public void setEbcl_bcid(String ebcl_bcid) {
		this.ebcl_bcid = ebcl_bcid;
	}

	public BigDecimal getEbcl_finalcharge() {
		return ebcl_finalcharge;
	}

	public void setEbcl_finalcharge(BigDecimal ebcl_finalcharge) {
		this.ebcl_finalcharge = ebcl_finalcharge;
	}

	public void setEbcl_area(Integer ebcl_area) {
		this.ebcl_area = ebcl_area;
	}

	public String getEbcl_backcase() {
		return ebcl_backcase;
	}

	public void setEbcl_backcase(String ebcl_backcase) {
		this.ebcl_backcase = ebcl_backcase;
	}

	public String getEbcl_backname() {
		return ebcl_backname;
	}

	public void setEbcl_backname(String ebcl_backname) {
		this.ebcl_backname = ebcl_backname;
	}

	public String getEbcl_backdate() {
		return ebcl_backdate;
	}

	public void setEbcl_backdate(String ebcl_backdate) {
		this.ebcl_backdate = ebcl_backdate;
	}

	public String getEbcl_remark() {
		return ebcl_remark;
	}

	public void setEbcl_remark(String ebcl_remark) {
		this.ebcl_remark = ebcl_remark;
	}

	public String getOcon() {
		return ocon;
	}

	public void setOcon(String ocon) {
		this.ocon = ocon;
	}

	public String getEbcl_addname() {
		return ebcl_addname;
	}

	public void setEbcl_addname(String ebcl_addname) {
		this.ebcl_addname = ebcl_addname;
	}

	public String getEbcl_addtime() {
		return ebcl_addtime;
	}

	public void setEbcl_addtime(String ebcl_addtime) {
		this.ebcl_addtime = ebcl_addtime;
	}

	public String getEbcl_balancedate() {
		return ebcl_balancedate;
	}

	public void setEbcl_balancedate(String ebcl_balancedate) {
		this.ebcl_balancedate = ebcl_balancedate;
	}

	public String getEbcl_enddate() {
		return ebcl_enddate;
	}

	public void setEbcl_enddate(String ebcl_enddate) {
		this.ebcl_enddate = ebcl_enddate;
	}

	public String getEmba_idcard() {
		return emba_idcard;
	}

	public void setEmba_idcard(String emba_idcard) {
		this.emba_idcard = emba_idcard;
	}

	public String getCoba_clientmanager() {
		return coba_clientmanager;
	}

	public void setCoba_clientmanager(String coba_clientmanager) {
		this.coba_clientmanager = coba_clientmanager;
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

	public String getEmba_sex() {
		return emba_sex;
	}

	public void setEmba_sex(String emba_sex) {
		this.emba_sex = emba_sex;
	}

	public String getEbcl_itemgroup() {
		return ebcl_itemgroup;
	}

	public void setEbcl_itemgroup(String ebcl_itemgroup) {
		this.ebcl_itemgroup = ebcl_itemgroup;
	}

	public String getEbcl_drawreportdate() {
		return ebcl_drawreportdate;
	}

	public void setEbcl_drawreportdate(String ebcl_drawreportdate) {
		this.ebcl_drawreportdate = ebcl_drawreportdate;
	}

	public EmBodyCheckListModel getModel() {
		return model;
	}

	public void setModel(EmBodyCheckListModel model) {
		this.model = model;
	}

	public String getEbcl_clientshowdate() {
		return ebcl_clientshowdate;
	}

	public void setEbcl_clientshowdate(String ebcl_clientshowdate) {
		this.ebcl_clientshowdate = ebcl_clientshowdate;
	}

	public String getEbcl_showclient() {
		return ebcl_showclient;
	}

	public void setEbcl_showclient(String ebcl_showclient) {
		this.ebcl_showclient = ebcl_showclient;
	}

	public Integer getEbcl_hospital() {
		return ebcl_hospital;
	}

	public void setEbcl_hospital(Integer ebcl_hospital) {
		this.ebcl_hospital = ebcl_hospital;
	}

	public String getEmbc_mobile() {
		return embc_mobile;
	}

	public void setEmbc_mobile(String embc_mobile) {
		this.embc_mobile = embc_mobile;
	}

	public String getEbcs_tips() {
		return ebcs_tips;
	}

	public void setEbcs_tips(String ebcs_tips) {
		this.ebcs_tips = ebcs_tips;
	}

	public String getCoba_manager() {
		return coba_manager;
	}

	public void setCoba_manager(String coba_manager) {
		this.coba_manager = coba_manager;
	}

	public Integer getSymr_readstate() {
		return symr_readstate;
	}

	public void setSymr_readstate(Integer symr_readstate) {
		this.symr_readstate = symr_readstate;
	}

	public String getEbcl_itemsstr() {
		return ebcl_itemsstr;
	}

	public void setEbcl_itemsstr(String ebcl_itemsstr) {
		this.ebcl_itemsstr = ebcl_itemsstr;
	}

	public String getEbcl_clientbackcase() {
		return ebcl_clientbackcase;
	}

	public void setEbcl_clientbackcase(String ebcl_clientbackcase) {
		this.ebcl_clientbackcase = ebcl_clientbackcase;
	}

	public String getEbcl_clientbackname() {
		return ebcl_clientbackname;
	}

	public void setEbcl_clientbackname(String ebcl_clientbackname) {
		this.ebcl_clientbackname = ebcl_clientbackname;
	}

	public String getEbcl_clientbacktime() {
		return ebcl_clientbacktime;
	}

	public void setEbcl_clientbacktime(String ebcl_clientbacktime) {
		this.ebcl_clientbacktime = ebcl_clientbacktime;
	}

	public Integer getMnum() {
		return mnum;
	}

	public void setMnum(Integer mnum) {
		this.mnum = mnum;
	}

	public String getCosp_bcrp_postfee() {
		return cosp_bcrp_postfee;
	}

	public void setCosp_bcrp_postfee(String cosp_bcrp_postfee) {
		this.cosp_bcrp_postfee = cosp_bcrp_postfee;
	}

	public String getCosp_bcrp_careinfo() {
		return cosp_bcrp_careinfo;
	}

	public void setCosp_bcrp_careinfo(String cosp_bcrp_careinfo) {
		this.cosp_bcrp_careinfo = cosp_bcrp_careinfo;
	}

	public String getEbsa_doc() {
		return ebsa_doc;
	}

	public void setEbsa_doc(String ebsa_doc) {
		this.ebsa_doc = ebsa_doc;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getCoba_company() {
		return coba_company;
	}

	public void setCoba_company(String coba_company) {
		this.coba_company = coba_company;
	}

	public String getEmpType() {
		return empType;
	}

	public void setEmpType(String empType) {
		this.empType = empType;
	}

}
