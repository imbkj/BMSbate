package Model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.zkoss.zul.Decimalbox;

public class EmbaseModel implements Cloneable {
	// / emba_id
	private Integer emba_id;
	// / gid
	private Integer gid;
	// / cid
	private Integer cid;
	// / emba_type
	private Integer emba_type;
	// / emba_name
	private String emba_name;
	// / emba_englishname
	private String emba_englishname;
	// / emba_spell
	private String emba_spell;
	// / emba_pinyin
	private String emba_pinyin;
	// / emba_sex
	private String emba_sex;
	// / emba_idcard
	private String emba_idcard;
	// / emba_idcardclass
	private String emba_idcardclass;
	// / emba_hjadd
	private String emba_hjadd;
	// / emba_native
	private String emba_native;
	// / emba_hjtype
	private String emba_hjtype;
	// / emba_Nationality
	private String emba_nationality;
	// / emba_folk
	private String emba_folk;
	// / emba_birth
	private String emba_birth;
	// / emba_marital
	private String emba_marital;
	// / emba_phone
	private String emba_phone;
	// / emba_mobile
	private String emba_mobile;
	// / emba_epname
	private String emba_epname;
	// / emba_epmobile
	private String emba_epmobile;
	// / emba_address
	private String emba_address;
	// / emba_email
	private String emba_email;
	// / emba_privateemail
	private String emba_privateemail;
	// / emba_party
	private String emba_party;
	// / emba_status
	private String emba_status;
	// / emba_degree
	private String emba_degree;
	// / emba_education
	private String emba_education;
	// / emba_school
	private String emba_school;
	// / emba_specialty
	private String emba_specialty;
	// / emba_graduation
	private String emba_graduation;
	// / emba_fileplace
	private String emba_fileplace;
	// / emba_fileinclass
	private Integer emba_fileinclass;
	// / emba_filereason
	private String emba_filereason;
	// / emba_filedebts
	private Integer emba_filedebts;
	// / emba_filedebtsmonth
	private String emba_filedebtsmonth;
	// / emba_filehj
	private Integer emba_filehj;
	// / emba_computerid
	private String emba_computerid;
	// / emba_hand
	private String emba_hand;
	// / emba_sbcard
	private String emba_sbcard;
	// / emba_houseid
	private String emba_houseid;
	// / emba_excompanyid
	private String emba_excompanyid;
	// / emba_excompanystate
	private Integer emba_excompanystate;
	// / emba_excompany
	private String emba_excompany;
	// / emba_title
	private String emba_title;
	// / emba_wifename
	private String emba_wifename;
	// / emba_wifeidcard
	private String emba_wifeidcard;
	// / emba_gz_email
	private String emba_gz_email;
	// / emba_gz_cemail
	private String emba_gz_cemail;
	// / emba_gz_account
	private String emba_gz_account;
	// / emba_gz_bank
	private String emba_gz_bank;
	// 报销开户银行
	private String emba_gz_bxbank;
	// 报销银行账号
	private String emba_gz_bxaccount;
	// / emba_house_account
	private String emba_house_account;
	// / emba_house_bank
	private String emba_house_bank;
	// / emba_writeoff_account
	private String emba_writeoff_account;
	// / emba_writeoff_bank
	private String emba_writeoff_bank;
	// / emba_housecode
	private String emba_housecode;
	// / emba_housetime
	private String emba_housetime;
	// / emba_housetype
	private String emba_housetype;
	// / emba_houseclass
	private String emba_houseclass;
	// / emba_skilllevel
	private String emba_skilllevel;
	// / emba_worktime
	private String emba_worktime;
	// / emba_sztime
	private String emba_sztime;
	// / emba_hjtime
	private String emba_hjtime;
	// / emba_regtype
	private String emba_regtype;
	// / emba_compactlimit
	private String emba_compactlimit;
	// / emba_compactstart
	private String emba_compactstart;
	// / emba_compactend
	private String emba_compactend;
	// / emba_companystart
	private String emba_companystart;
	// / emba_station
	private String emba_station;
	// / emba_birthcontrol
	private String emba_birthcontrol;
	// / emba_photoNum
	private String emba_photonum;
	// / emba_sy_account
	private String emba_sy_account;
	// / emba_sy_bank
	private String emba_sy_bank;
	// / emba_sbemail
	private String emba_sbemail;
	// / emba_sbname1
	private String emba_sbname1;
	// / emba_sbname2
	private String emba_sbname2;
	// / emba_sbname3
	private String emba_sbname3;
	// / emba_sbname4
	private String emba_sbname4;
	// / emba_sbage1
	private String emba_sbage1;
	// / emba_sbage2
	private String emba_sbage2;
	// / emba_sbage3
	private String emba_sbage3;
	// / emba_sbage4
	private String emba_sbage4;
	// / emba_sbidcard1
	private String emba_sbidcard1;
	// / emba_sbidcard2
	private String emba_sbidcard2;
	// / emba_sbidcard3
	private String emba_sbidcard3;
	// / emba_sbidcard4
	private String emba_sbidcard4;
	// / emba_sbbirth1
	private String emba_sbbirth1;
	// / emba_sbbirth2
	private String emba_sbbirth2;
	// / emba_sbbirth3
	private String emba_sbbirth3;
	// / emba_sbbirth4
	private String emba_sbbirth4;
	private Date emba_sbbirth1_d;
	private Date emba_sbbirth2_d;
	private Date emba_sbbirth3_d;
	private Date emba_sbbirth4_d;
	// / emba_sbrelation1
	private String emba_sbrelation1;
	// / emba_sbrelation2
	private String emba_sbrelation2;
	// / emba_sbrelation3
	private String emba_sbrelation3;
	// / emba_sbrelation4
	private String emba_sbrelation4;
	// / emba_hospital
	private String emba_hospital;
	// / emba_bcaddress
	private String emba_bcaddress;
	// / emba_bctime
	private String emba_bctime;
	// / emba_worktime1
	private String emba_worktime1;
	// / emba_worktime2
	private String emba_worktime2;
	// / emba_worktime3
	private String emba_worktime3;
	// / emba_worktime4
	private String emba_worktime4;
	// / emba_worktime5
	private String emba_worktime5;
	// / emba_worktime6
	private String emba_worktime6;
	// / emba_workcompany1
	private String emba_workcompany1;
	// / emba_workcompany2
	private String emba_workcompany2;
	// / emba_workcompany3
	private String emba_workcompany3;
	// / emba_workcompany4
	private String emba_workcompany4;
	// / emba_workcompany5
	private String emba_workcompany5;
	// / emba_workcompany6
	private String emba_workcompany6;
	// / emba_workjob1
	private String emba_workjob1;
	// / emba_workjob2
	private String emba_workjob2;
	// / emba_workjob3
	private String emba_workjob3;
	// / emba_workjob4
	private String emba_workjob4;
	// / emba_workjob5
	private String emba_workjob5;
	// / emba_workjob6
	private String emba_workjob6;
	// / emba_f1
	private String emba_f1;
	// / emba_f2
	private String emba_f2;
	// / emba_f3
	private String emba_f3;
	// / emba_f4
	private String emba_f4;
	// / emba_f5
	private String emba_f5;
	// / emba_f6
	private String emba_f6;
	// / emba_fa1
	private String emba_fa1;
	// / emba_fa2
	private String emba_fa2;
	// / emba_fa3
	private String emba_fa3;
	// / emba_fa4
	private String emba_fa4;
	// / emba_fa5
	private String emba_fa5;
	// / emba_fa6
	private String emba_fa6;
	// / emba_fn1
	private String emba_fn1;
	// / emba_fn2
	private String emba_fn2;
	// / emba_fn3
	private String emba_fn3;
	// / emba_fn4
	private String emba_fn4;
	// / emba_fn5
	private String emba_fn5;
	// / emba_fn6
	private String emba_fn6;
	// / emba_fp1
	private String emba_fp1;
	// / emba_fp2
	private String emba_fp2;
	// / emba_fp3
	private String emba_fp3;
	// / emba_fp4
	private String emba_fp4;
	// / emba_fp5
	private String emba_fp5;
	// / emba_fp6
	private String emba_fp6;
	// emba_fag1
	private String emba_fag1;
	// emba_fag2
	private String emba_fag2;
	// emba_fag3
	private String emba_fag3;
	// emba_fag4
	private String emba_fag4;
	// emba_fag5
	private String emba_fag5;
	// emba_fag6
	private String emba_fag6;
	// emba_fw1
	private String emba_fw1;
	// emba_fw2
	private String emba_fw2;
	// emba_fw3
	private String emba_fw3;
	// emba_fw4
	private String emba_fw4;
	// emba_fw5
	private String emba_fw5;
	// emba_fw6
	private String emba_fw6;
	// emba_fr1
	private String emba_fr1;
	// emba_fr2
	private String emba_fr2;
	// emba_fr3
	private String emba_fr3;
	// emba_fr4
	private String emba_fr4;
	// emba_fr5
	private String emba_fr5;
	// emba_fr6
	private String emba_fr6;
	// / emba_state
	private Integer emba_state;
	// / emba_remark
	private String emba_remark;
	// / emba_wt
	private String emba_wt;
	// / emba_indate
	private String emba_indate;
	// / emba_outdate
	private String emba_outdate;
	// / emba_outreason
	private String emba_outreason;
	// / emba_addtime
	private String emba_addtime;
	// / emba_addname
	private String emba_addname;
	// / emba_zytid
	private String emba_zytid;
	// / emba_zytwtgid
	private String emba_zytwtgid;
	// / emba_csid
	private Integer emba_csid;
	// / emba_cpid
	private Integer emba_cpid;
	// / emba_number
	private String emba_number;

	private String coba_name;

	private String coba_company;

	private String coba_shortname;
	private String coba_assistant;

	private String coba_client;
	// / sein_shebao
	private String sein_shebao;
	// / sein_gjj
	private String sein_gjj;
	// / sein_shangbao
	private String sein_shangbao;
	// / sein_wt
	private String sein_wt;
	// / sein_shebaob
	private String sein_shebaob;
	// / sein_gjjb
	private String sein_gjjb;
	// / sein_da
	private String sein_da;
	// / sein_zj
	private String sein_zj;
	// / sein_ldyg
	private String sein_ldyg;
	// / sein_xc
	private String sein_xc;

	private String emba_statestr;
	private Integer emba_tapr_id;

	private Integer emar_id;
	private String archive;
	private String statename;
	private Integer emar_state;
	private String emhu_houseid;
	private Integer emba_age;
	private Integer num;
	// 是否有电脑号
	private String emba_ifcomputerid;
	private Boolean vis_computerid;
	private Boolean vis_hand;
	// 办理社保卡是否通知员工
	private Boolean emba_sbcard_notice;
	private Boolean vis_sbcard_notice;
	// 服务地区
	private String emba_service_place;
	// 是否有公积金账号
	private String emba_ifhouse;
	private String search_computerid_url;

	// 社保公积金缴交地
	private String emba_sb_place;
	private String emba_house_place;
	private String emba_sy_place;

	// 社保基数
	private BigDecimal emba_sb_radix;
	// 社保补缴所属月
	private String emba_emsb_ownmonth;
	// 社保补缴台帐月
	private String emba_emsb_feeownmonth;
	private String emba_emsb_m1;
	private Integer emba_emsb_r1;
	private String emba_emsb_m2;
	private Integer emba_emsb_r2;
	private String emba_emsb_m3;
	private Integer emba_emsb_r3;

	// 社保医疗类型
	private String emba_formula;
	// 社保户籍
	private String emba_sb_hj;
	// 公积金基数
	private BigDecimal emba_house_radix;
	private BigDecimal emba_emhb_radix;
	// 公积金补缴所属月
	private String emba_emhb_ownmonth;
	// 公积金补缴台帐月
	private String emba_emhb_feeownmonth;
	private String emba_emhb_startdate;
	private String emba_emhb_stopdate;
	private String emba_emhb_reason;
	private BigDecimal emba_emhb_total;
	// 公积金比例
	private Double emba_house_cpp;

	private String emba_modname;
	private String emba_modtime;
	private String emba_emsb_foreigner;
	private String emsc_computerid;// 社保电脑号
	private String coba_address;
	private int emsc_Single;
	private int empic;// 员工图像状态
	private int mobile;// 员工短信

	private String ebco_incept_date;// 合同起始时间
	private String ebco_maturity_date;// 合同结束时间
	private String ebco_change;// 合同性质

	private String emba_gjjuname;
	private String emba_gjjidcard;
	private String emba_sbuname;
	private String emba_sbidcard;

	private String Emzt_iffileservice;// 是否有档案服务

	private boolean checked;

	private Integer emba_form;

	private String emba_ba_name;
	private BigDecimal emba_fee;
	private String emba_class;
	private String emba_payclass;
	private String emba_paytype;
	private String emba_paymenttype;

	private String coli_name;
	private String coli_remark;
	private String coli_standard;
	private String coli_flpaykind;
	private BigDecimal colm_fee;
	private Integer embf_id;
	private String dept;

	private Integer sbcd_tarpid;

	private String emba_costcenter;// 成本中心
	private String esiu_hj;

	private String esiu_computerid, esiu_yl, esiu_yltype, esiu_gs, esiu_sye,
			esiu_syu, esiu_addtime, ebco_probation_incept,
			ebco_probation_mdate, ebco_working_station;
	private Integer esiu_radix;
	private BigDecimal emhu_bonus;
	private Double emhu_opp, emhu_cpp, emhu_radix;

	private String emba_excelfile;
	private String emba_err;
	private String emba_statebatchstr;
	private String emba_batchtype;
	private Integer sbcd_id;
	private Date ownmonth;
	private Date ownmonthend;

	private boolean top;
	private Integer topNum;

	// 数据迁移使用的判断
	private boolean rs = false;
	private boolean cs = false;
	private boolean da = false;
	private boolean dh = false;
	private boolean hk = false;
	private boolean other = false;
	private boolean sw = false;
	private boolean sy = false;
	private boolean tj = false;
	private boolean fl = false;
	private boolean zp = false;
	private boolean check = false;

	private Integer emba_emsb_jlbj1;
	private Integer emba_emsb_jlbj2;
	private Integer emba_emsb_jlbj3;

	private boolean chk_jlbj1 = false;
	private boolean chk_jlbj2 = false;
	private boolean chk_jlbj3 = false;

	private List<CoglistModel> cglist;

	
	
	public String getCoba_assistant() {
		return coba_assistant;
	}

	public void setCoba_assistant(String coba_assistant) {
		this.coba_assistant = coba_assistant;
	}

	public boolean isCheck() {
		return check;
	}

	public boolean getCheck() {
		return check;
	}

	public Integer getSbcd_tarpid() {
		return sbcd_tarpid;
	}

	public void setSbcd_tarpid(Integer sbcd_tarpid) {
		this.sbcd_tarpid = sbcd_tarpid;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public String getEmba_batchtype() {
		return emba_batchtype;
	}

	public void setEmba_batchtype(String emba_batchtype) {
		this.emba_batchtype = emba_batchtype;
	}

	public String getEmba_statebatchstr() {
		return emba_statebatchstr;
	}

	public void setEmba_statebatchstr(String emba_statebatchstr) {
		this.emba_statebatchstr = emba_statebatchstr;
	}

	public String getEmba_excelfile() {
		return emba_excelfile;
	}

	public void setEmba_excelfile(String emba_excelfile) {
		this.emba_excelfile = emba_excelfile;
	}

	public String getEmba_err() {
		return emba_err;
	}

	public void setEmba_err(String emba_err) {
		this.emba_err = emba_err;
	}

	public String getEmba_gjjuname() {
		return emba_gjjuname;
	}

	public void setEmba_gjjuname(String emba_gjjuname) {
		this.emba_gjjuname = emba_gjjuname;
	}

	public Integer getEmba_form() {
		return emba_form;
	}

	public void setEmba_form(Integer emba_form) {
		this.emba_form = emba_form;
	}

	public String getEmba_gjjidcard() {
		return emba_gjjidcard;
	}

	public void setEmba_gjjidcard(String emba_gjjidcard) {
		this.emba_gjjidcard = emba_gjjidcard;
	}

	public String getEmba_sbuname() {
		return emba_sbuname;
	}

	public void setEmba_sbuname(String emba_sbuname) {
		this.emba_sbuname = emba_sbuname;
	}

	public String getEmba_sbidcard() {
		return emba_sbidcard;
	}

	public void setEmba_sbidcard(String emba_sbidcard) {
		this.emba_sbidcard = emba_sbidcard;
	}

	public String getCoba_name() {
		return coba_name;
	}

	public void setCoba_name(String coba_name) {
		this.coba_name = coba_name;
	}

	public String getCoba_company() {
		return coba_company;
	}

	public void setCoba_company(String coba_company) {
		this.coba_company = coba_company;
	}

	public String getCoba_client() {
		return coba_client;
	}

	public void setCoba_client(String coba_client) {
		this.coba_client = coba_client;
	}

	public String getSein_shebao() {
		return sein_shebao;
	}

	public void setSein_shebao(String sein_shebao) {
		this.sein_shebao = sein_shebao;
	}

	public String getSein_gjj() {
		return sein_gjj;
	}

	public void setSein_gjj(String sein_gjj) {
		this.sein_gjj = sein_gjj;
	}

	public String getSein_shangbao() {
		return sein_shangbao;
	}

	public void setSein_shangbao(String sein_shangbao) {
		this.sein_shangbao = sein_shangbao;
	}

	public String getSein_wt() {
		return sein_wt;
	}

	public void setSein_wt(String sein_wt) {
		this.sein_wt = sein_wt;
	}

	public String getSein_shebaob() {
		return sein_shebaob;
	}

	public void setSein_shebaob(String sein_shebaob) {
		this.sein_shebaob = sein_shebaob;
	}

	public String getSein_gjjb() {
		return sein_gjjb;
	}

	public void setSein_gjjb(String sein_gjjb) {
		this.sein_gjjb = sein_gjjb;
	}

	public String getSein_da() {
		return sein_da;
	}

	public void setSein_da(String sein_da) {
		this.sein_da = sein_da;
	}

	public String getSein_zj() {
		return sein_zj;
	}

	public void setSein_zj(String sein_zj) {
		this.sein_zj = sein_zj;
	}

	public String getSein_ldyg() {
		return sein_ldyg;
	}

	public void setSein_ldyg(String sein_ldyg) {
		this.sein_ldyg = sein_ldyg;
	}

	public String getSein_xc() {
		return sein_xc;
	}

	public void setSein_xc(String sein_xc) {
		this.sein_xc = sein_xc;
	}

	public String getEmba_statestr() {
		return emba_statestr;
	}

	public void setEmba_statestr(String emba_statestr) {
		this.emba_statestr = emba_statestr;
	}

	public Integer getEmba_id() {
		return emba_id;
	}

	public void setEmba_id(Integer emba_id) {
		this.emba_id = emba_id;
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

	public Integer getEmba_type() {
		return emba_type;
	}

	public void setEmba_type(Integer emba_type) {
		this.emba_type = emba_type;
	}

	public String getEmba_name() {
		return emba_name;
	}

	public void setEmba_name(String emba_name) {
		this.emba_name = emba_name;
	}

	public String getEmba_englishname() {
		return emba_englishname;
	}

	public void setEmba_englishname(String emba_englishname) {
		this.emba_englishname = emba_englishname;
	}

	public String getEmba_spell() {
		return emba_spell;
	}

	public void setEmba_spell(String emba_spell) {
		this.emba_spell = emba_spell;
	}

	public String getEmba_pinyin() {
		return emba_pinyin;
	}

	public void setEmba_pinyin(String emba_pinyin) {
		this.emba_pinyin = emba_pinyin;
	}

	public String getEmba_sex() {
		return emba_sex;
	}

	public void setEmba_sex(String emba_sex) {
		this.emba_sex = emba_sex;
	}

	public String getEmba_idcard() {
		return emba_idcard;
	}

	public void setEmba_idcard(String emba_idcard) {
		this.emba_idcard = emba_idcard;
	}

	public String getEmba_idcardclass() {
		return emba_idcardclass;
	}

	public void setEmba_idcardclass(String emba_idcardclass) {
		this.emba_idcardclass = emba_idcardclass;
	}

	public String getEmba_hjadd() {
		return emba_hjadd;
	}

	public void setEmba_hjadd(String emba_hjadd) {
		this.emba_hjadd = emba_hjadd;
	}

	public String getEmba_native() {
		return emba_native;
	}

	public void setEmba_native(String emba_native) {
		this.emba_native = emba_native;
	}

	public String getEmba_hjtype() {
		return emba_hjtype;
	}

	public void setEmba_hjtype(String emba_hjtype) {
		this.emba_hjtype = emba_hjtype;
	}

	public String getEmba_nationality() {
		return emba_nationality;
	}

	public void setEmba_nationality(String emba_nationality) {
		this.emba_nationality = emba_nationality;
	}

	public String getEmba_folk() {
		return emba_folk;
	}

	public void setEmba_folk(String emba_folk) {
		this.emba_folk = emba_folk;
	}

	public String getEmba_birth() {
		return emba_birth;
	}

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			return null;
		}

	}

	public void setEmba_birth(String emba_birth) {
		this.emba_birth = emba_birth;
	}

	public String getEmba_marital() {
		return emba_marital;
	}

	public void setEmba_marital(String emba_marital) {
		this.emba_marital = emba_marital;
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

	public String getEmba_address() {
		return emba_address;
	}

	public void setEmba_address(String emba_address) {
		this.emba_address = emba_address;
	}

	public String getEmba_email() {
		return emba_email;
	}

	public void setEmba_email(String emba_email) {
		this.emba_email = emba_email;
	}

	public String getEmba_privateemail() {
		return emba_privateemail;
	}

	public void setEmba_privateemail(String emba_privateemail) {
		this.emba_privateemail = emba_privateemail;
	}

	public String getEmba_party() {
		return emba_party;
	}

	public void setEmba_party(String emba_party) {
		this.emba_party = emba_party;
	}

	public String getEmba_status() {
		return emba_status;
	}

	public void setEmba_status(String emba_status) {
		this.emba_status = emba_status;
	}

	public String getEmba_degree() {
		return emba_degree;
	}

	public void setEmba_degree(String emba_degree) {
		this.emba_degree = emba_degree;
	}

	public String getEmba_education() {
		return emba_education;
	}

	public void setEmba_education(String emba_education) {
		this.emba_education = emba_education;
	}

	public String getEmba_school() {
		return emba_school;
	}

	public void setEmba_school(String emba_school) {
		this.emba_school = emba_school;
	}

	public String getEmba_specialty() {
		return emba_specialty;
	}

	public void setEmba_specialty(String emba_specialty) {
		this.emba_specialty = emba_specialty;
	}

	public String getEmba_graduation() {
		return emba_graduation;
	}

	public void setEmba_graduation(String emba_graduation) {
		this.emba_graduation = emba_graduation;
	}

	public String getEmba_fileplace() {
		return emba_fileplace;
	}

	public void setEmba_fileplace(String emba_fileplace) {
		this.emba_fileplace = emba_fileplace;
	}

	public Integer getEmba_fileinclass() {
		return emba_fileinclass;
	}

	public void setEmba_fileinclass(Integer emba_fileinclass) {
		this.emba_fileinclass = emba_fileinclass;
	}

	public String getEmba_filereason() {
		return emba_filereason;
	}

	public void setEmba_filereason(String emba_filereason) {
		this.emba_filereason = emba_filereason;
	}

	public Integer getEmba_filedebts() {
		return emba_filedebts;
	}

	public void setEmba_filedebts(Integer emba_filedebts) {
		this.emba_filedebts = emba_filedebts;
	}

	public String getEmba_filedebtsmonth() {
		return emba_filedebtsmonth;
	}

	public void setEmba_filedebtsmonth(String emba_filedebtsmonth) {
		this.emba_filedebtsmonth = emba_filedebtsmonth;
	}

	public Integer getEmba_filehj() {
		return emba_filehj;
	}

	public void setEmba_filehj(Integer emba_filehj) {
		this.emba_filehj = emba_filehj;
	}

	public String getEmba_computerid() {
		return emba_computerid;
	}

	public void setEmba_computerid(String emba_computerid) {
		this.emba_computerid = emba_computerid;
	}

	public String getEmba_hand() {
		return emba_hand;
	}

	public void setEmba_hand(String emba_hand) {
		this.emba_hand = emba_hand;
	}

	public String getEmba_houseid() {
		return emba_houseid;
	}

	public void setEmba_houseid(String emba_houseid) {
		this.emba_houseid = emba_houseid;
	}

	public String getEmba_excompanyid() {
		return emba_excompanyid;
	}

	public void setEmba_excompanyid(String emba_excompanyid) {
		this.emba_excompanyid = emba_excompanyid;
	}

	public Integer getEmba_excompanystate() {
		return emba_excompanystate;
	}

	public void setEmba_excompanystate(Integer emba_excompanystate) {
		this.emba_excompanystate = emba_excompanystate;
	}

	public String getEmba_excompany() {
		return emba_excompany;
	}

	public void setEmba_excompany(String emba_excompany) {
		this.emba_excompany = emba_excompany;
	}

	public String getEmba_title() {
		return emba_title;
	}

	public void setEmba_title(String emba_title) {
		this.emba_title = emba_title;
	}

	public String getEmba_wifename() {
		return emba_wifename;
	}

	public void setEmba_wifename(String emba_wifename) {
		this.emba_wifename = emba_wifename;
	}

	public String getEmba_wifeidcard() {
		return emba_wifeidcard;
	}

	public void setEmba_wifeidcard(String emba_wifeidcard) {
		this.emba_wifeidcard = emba_wifeidcard;
	}

	public String getEmba_gz_email() {
		return emba_gz_email;
	}

	public void setEmba_gz_email(String emba_gz_email) {
		this.emba_gz_email = emba_gz_email;
	}

	public String getEmba_gz_account() {
		return emba_gz_account;
	}

	public void setEmba_gz_account(String emba_gz_account) {
		this.emba_gz_account = emba_gz_account;
	}

	public String getEmba_gz_bank() {
		return emba_gz_bank;
	}

	public void setEmba_gz_bank(String emba_gz_bank) {
		this.emba_gz_bank = emba_gz_bank;
	}

	public String getEmba_house_account() {
		return emba_house_account;
	}

	public void setEmba_house_account(String emba_house_account) {
		this.emba_house_account = emba_house_account;
	}

	public String getEmba_house_bank() {
		return emba_house_bank;
	}

	public void setEmba_house_bank(String emba_house_bank) {
		this.emba_house_bank = emba_house_bank;
	}

	public String getEmba_writeoff_account() {
		return emba_writeoff_account;
	}

	public void setEmba_writeoff_account(String emba_writeoff_account) {
		this.emba_writeoff_account = emba_writeoff_account;
	}

	public String getEmba_writeoff_bank() {
		return emba_writeoff_bank;
	}

	public void setEmba_writeoff_bank(String emba_writeoff_bank) {
		this.emba_writeoff_bank = emba_writeoff_bank;
	}

	public String getEmba_housecode() {
		return emba_housecode;
	}

	public void setEmba_housecode(String emba_housecode) {
		this.emba_housecode = emba_housecode;
	}

	public String getEmba_housetime() {
		return emba_housetime;
	}

	public void setEmba_housetime(String emba_housetime) {
		this.emba_housetime = emba_housetime;
	}

	public String getEmba_housetype() {
		return emba_housetype;
	}

	public void setEmba_housetype(String emba_housetype) {
		this.emba_housetype = emba_housetype;
	}

	public String getEmba_houseclass() {
		return emba_houseclass;
	}

	public void setEmba_houseclass(String emba_houseclass) {
		this.emba_houseclass = emba_houseclass;
	}

	public String getEmba_skilllevel() {
		return emba_skilllevel;
	}

	public void setEmba_skilllevel(String emba_skilllevel) {
		this.emba_skilllevel = emba_skilllevel;
	}

	public String getEmba_worktime() {
		return emba_worktime;
	}

	public void setEmba_worktime(String emba_worktime) {
		this.emba_worktime = emba_worktime;
	}

	public String getEmba_sztime() {
		return emba_sztime;
	}

	public void setEmba_sztime(String emba_sztime) {
		this.emba_sztime = emba_sztime;
	}

	public String getEmba_hjtime() {
		return emba_hjtime;
	}

	public void setEmba_hjtime(String emba_hjtime) {
		this.emba_hjtime = emba_hjtime;
	}

	public String getEmba_regtype() {
		return emba_regtype;
	}

	public void setEmba_regtype(String emba_regtype) {
		this.emba_regtype = emba_regtype;
	}

	public String getEmba_compactlimit() {
		return emba_compactlimit;
	}

	public void setEmba_compactlimit(String emba_compactlimit) {
		this.emba_compactlimit = emba_compactlimit;
	}

	public String getEmba_compactstart() {
		return emba_compactstart;
	}

	public void setEmba_compactstart(String emba_compactstart) {
		this.emba_compactstart = emba_compactstart;
	}

	public String getEmba_compactend() {
		return emba_compactend;
	}

	public void setEmba_compactend(String emba_compactend) {
		this.emba_compactend = emba_compactend;
	}

	public String getEmba_companystart() {
		return emba_companystart;
	}

	public void setEmba_companystart(String emba_companystart) {
		this.emba_companystart = emba_companystart;
	}

	public String getEmba_station() {
		return emba_station;
	}

	public void setEmba_station(String emba_station) {
		this.emba_station = emba_station;
	}

	public String getEmba_birthcontrol() {
		return emba_birthcontrol;
	}

	public void setEmba_birthcontrol(String emba_birthcontrol) {
		this.emba_birthcontrol = emba_birthcontrol;
	}

	public String getEmba_photonum() {
		return emba_photonum;
	}

	public void setEmba_photonum(String emba_photonum) {
		this.emba_photonum = emba_photonum;
	}

	public String getEmba_sy_account() {
		return emba_sy_account;
	}

	public void setEmba_sy_account(String emba_sy_account) {
		this.emba_sy_account = emba_sy_account;
	}

	public String getEmba_sy_bank() {
		return emba_sy_bank;
	}

	public void setEmba_sy_bank(String emba_sy_bank) {
		this.emba_sy_bank = emba_sy_bank;
	}

	public String getEmba_sbemail() {
		return emba_sbemail;
	}

	public void setEmba_sbemail(String emba_sbemail) {
		this.emba_sbemail = emba_sbemail;
	}

	public String getEmba_sbname1() {
		return emba_sbname1;
	}

	public void setEmba_sbname1(String emba_sbname1) {
		this.emba_sbname1 = emba_sbname1;
	}

	public String getEmba_sbname2() {
		return emba_sbname2;
	}

	public void setEmba_sbname2(String emba_sbname2) {
		this.emba_sbname2 = emba_sbname2;
	}

	public String getEmba_sbname3() {
		return emba_sbname3;
	}

	public void setEmba_sbname3(String emba_sbname3) {
		this.emba_sbname3 = emba_sbname3;
	}

	public String getEmba_sbname4() {
		return emba_sbname4;
	}

	public void setEmba_sbname4(String emba_sbname4) {
		this.emba_sbname4 = emba_sbname4;
	}

	public String getEmba_sbage1() {
		return emba_sbage1;
	}

	public void setEmba_sbage1(String emba_sbage1) {
		this.emba_sbage1 = emba_sbage1;
	}

	public String getEmba_sbage2() {
		return emba_sbage2;
	}

	public void setEmba_sbage2(String emba_sbage2) {
		this.emba_sbage2 = emba_sbage2;
	}

	public String getEmba_sbage3() {
		return emba_sbage3;
	}

	public void setEmba_sbage3(String emba_sbage3) {
		this.emba_sbage3 = emba_sbage3;
	}

	public String getEmba_sbage4() {
		return emba_sbage4;
	}

	public void setEmba_sbage4(String emba_sbage4) {
		this.emba_sbage4 = emba_sbage4;
	}

	public String getEmba_sbidcard1() {
		return emba_sbidcard1;
	}

	public void setEmba_sbidcard1(String emba_sbidcard1) {
		this.emba_sbidcard1 = emba_sbidcard1;
	}

	public String getEmba_sbidcard2() {
		return emba_sbidcard2;
	}

	public void setEmba_sbidcard2(String emba_sbidcard2) {
		this.emba_sbidcard2 = emba_sbidcard2;
	}

	public String getEmba_sbidcard3() {
		return emba_sbidcard3;
	}

	public void setEmba_sbidcard3(String emba_sbidcard3) {
		this.emba_sbidcard3 = emba_sbidcard3;
	}

	public String getEmba_sbidcard4() {
		return emba_sbidcard4;
	}

	public void setEmba_sbidcard4(String emba_sbidcard4) {
		this.emba_sbidcard4 = emba_sbidcard4;
	}

	public String getEmba_sbbirth1() {
		return emba_sbbirth1;
	}

	public void setEmba_sbbirth1(String emba_sbbirth1) {
		this.emba_sbbirth1 = emba_sbbirth1;
	}

	public String getEmba_sbbirth2() {
		return emba_sbbirth2;
	}

	public void setEmba_sbbirth2(String emba_sbbirth2) {
		this.emba_sbbirth2 = emba_sbbirth2;
	}

	public String getEmba_sbbirth3() {
		return emba_sbbirth3;
	}

	public void setEmba_sbbirth3(String emba_sbbirth3) {
		this.emba_sbbirth3 = emba_sbbirth3;
	}

	public String getEmba_sbbirth4() {
		return emba_sbbirth4;
	}

	public void setEmba_sbbirth4(String emba_sbbirth4) {
		this.emba_sbbirth4 = emba_sbbirth4;
	}

	public String getEmba_sbrelation1() {
		return emba_sbrelation1;
	}

	public void setEmba_sbrelation1(String emba_sbrelation1) {
		this.emba_sbrelation1 = emba_sbrelation1;
	}

	public String getEmba_sbrelation2() {
		return emba_sbrelation2;
	}

	public void setEmba_sbrelation2(String emba_sbrelation2) {
		this.emba_sbrelation2 = emba_sbrelation2;
	}

	public String getEmba_sbrelation3() {
		return emba_sbrelation3;
	}

	public void setEmba_sbrelation3(String emba_sbrelation3) {
		this.emba_sbrelation3 = emba_sbrelation3;
	}

	public String getEmba_sbrelation4() {
		return emba_sbrelation4;
	}

	public void setEmba_sbrelation4(String emba_sbrelation4) {
		this.emba_sbrelation4 = emba_sbrelation4;
	}

	public String getEmba_hospital() {
		return emba_hospital;
	}

	public void setEmba_hospital(String emba_hospital) {
		this.emba_hospital = emba_hospital;
	}

	public String getEmba_bcaddress() {
		return emba_bcaddress;
	}

	public void setEmba_bcaddress(String emba_bcaddress) {
		this.emba_bcaddress = emba_bcaddress;
	}

	public String getEmba_bctime() {
		return emba_bctime;
	}

	public void setEmba_bctime(String emba_bctime) {
		this.emba_bctime = emba_bctime;
	}

	public String getEmba_worktime1() {
		return emba_worktime1;
	}

	public void setEmba_worktime1(String emba_worktime1) {
		this.emba_worktime1 = emba_worktime1;
	}

	public String getEmba_worktime2() {
		return emba_worktime2;
	}

	public void setEmba_worktime2(String emba_worktime2) {
		this.emba_worktime2 = emba_worktime2;
	}

	public String getEmba_worktime3() {
		return emba_worktime3;
	}

	public void setEmba_worktime3(String emba_worktime3) {
		this.emba_worktime3 = emba_worktime3;
	}

	public String getEmba_worktime4() {
		return emba_worktime4;
	}

	public void setEmba_worktime4(String emba_worktime4) {
		this.emba_worktime4 = emba_worktime4;
	}

	public String getEmba_worktime5() {
		return emba_worktime5;
	}

	public void setEmba_worktime5(String emba_worktime5) {
		this.emba_worktime5 = emba_worktime5;
	}

	public String getEmba_worktime6() {
		return emba_worktime6;
	}

	public void setEmba_worktime6(String emba_worktime6) {
		this.emba_worktime6 = emba_worktime6;
	}

	public String getEmba_workcompany1() {
		return emba_workcompany1;
	}

	public void setEmba_workcompany1(String emba_workcompany1) {
		this.emba_workcompany1 = emba_workcompany1;
	}

	public String getEmba_workcompany2() {
		return emba_workcompany2;
	}

	public void setEmba_workcompany2(String emba_workcompany2) {
		this.emba_workcompany2 = emba_workcompany2;
	}

	public String getEmba_workcompany3() {
		return emba_workcompany3;
	}

	public void setEmba_workcompany3(String emba_workcompany3) {
		this.emba_workcompany3 = emba_workcompany3;
	}

	public String getEmba_workcompany4() {
		return emba_workcompany4;
	}

	public void setEmba_workcompany4(String emba_workcompany4) {
		this.emba_workcompany4 = emba_workcompany4;
	}

	public String getEmba_workcompany5() {
		return emba_workcompany5;
	}

	public void setEmba_workcompany5(String emba_workcompany5) {
		this.emba_workcompany5 = emba_workcompany5;
	}

	public String getEmba_workcompany6() {
		return emba_workcompany6;
	}

	public void setEmba_workcompany6(String emba_workcompany6) {
		this.emba_workcompany6 = emba_workcompany6;
	}

	public String getEmba_workjob1() {
		return emba_workjob1;
	}

	public void setEmba_workjob1(String emba_workjob1) {
		this.emba_workjob1 = emba_workjob1;
	}

	public String getEmba_workjob2() {
		return emba_workjob2;
	}

	public void setEmba_workjob2(String emba_workjob2) {
		this.emba_workjob2 = emba_workjob2;
	}

	public String getEmba_workjob3() {
		return emba_workjob3;
	}

	public void setEmba_workjob3(String emba_workjob3) {
		this.emba_workjob3 = emba_workjob3;
	}

	public String getEmba_workjob4() {
		return emba_workjob4;
	}

	public void setEmba_workjob4(String emba_workjob4) {
		this.emba_workjob4 = emba_workjob4;
	}

	public String getEmba_workjob5() {
		return emba_workjob5;
	}

	public void setEmba_workjob5(String emba_workjob5) {
		this.emba_workjob5 = emba_workjob5;
	}

	public String getEmba_workjob6() {
		return emba_workjob6;
	}

	public void setEmba_workjob6(String emba_workjob6) {
		this.emba_workjob6 = emba_workjob6;
	}

	public String getEmba_f1() {
		return emba_f1;
	}

	public void setEmba_f1(String emba_f1) {
		this.emba_f1 = emba_f1;
	}

	public String getEmba_f2() {
		return emba_f2;
	}

	public void setEmba_f2(String emba_f2) {
		this.emba_f2 = emba_f2;
	}

	public String getEmba_f3() {
		return emba_f3;
	}

	public void setEmba_f3(String emba_f3) {
		this.emba_f3 = emba_f3;
	}

	public String getEmba_f4() {
		return emba_f4;
	}

	public void setEmba_f4(String emba_f4) {
		this.emba_f4 = emba_f4;
	}

	public String getEmba_f5() {
		return emba_f5;
	}

	public void setEmba_f5(String emba_f5) {
		this.emba_f5 = emba_f5;
	}

	public String getEmba_f6() {
		return emba_f6;
	}

	public void setEmba_f6(String emba_f6) {
		this.emba_f6 = emba_f6;
	}

	public String getEmba_fa1() {
		return emba_fa1;
	}

	public void setEmba_fa1(String emba_fa1) {
		this.emba_fa1 = emba_fa1;
	}

	public String getEmba_fa2() {
		return emba_fa2;
	}

	public void setEmba_fa2(String emba_fa2) {
		this.emba_fa2 = emba_fa2;
	}

	public String getEmba_fa3() {
		return emba_fa3;
	}

	public void setEmba_fa3(String emba_fa3) {
		this.emba_fa3 = emba_fa3;
	}

	public String getEmba_fa4() {
		return emba_fa4;
	}

	public void setEmba_fa4(String emba_fa4) {
		this.emba_fa4 = emba_fa4;
	}

	public String getEmba_fa5() {
		return emba_fa5;
	}

	public void setEmba_fa5(String emba_fa5) {
		this.emba_fa5 = emba_fa5;
	}

	public String getEmba_fa6() {
		return emba_fa6;
	}

	public void setEmba_fa6(String emba_fa6) {
		this.emba_fa6 = emba_fa6;
	}

	public String getEmba_fn1() {
		return emba_fn1;
	}

	public void setEmba_fn1(String emba_fn1) {
		this.emba_fn1 = emba_fn1;
	}

	public String getEmba_fn2() {
		return emba_fn2;
	}

	public void setEmba_fn2(String emba_fn2) {
		this.emba_fn2 = emba_fn2;
	}

	public String getEmba_fn3() {
		return emba_fn3;
	}

	public void setEmba_fn3(String emba_fn3) {
		this.emba_fn3 = emba_fn3;
	}

	public String getEmba_fn4() {
		return emba_fn4;
	}

	public void setEmba_fn4(String emba_fn4) {
		this.emba_fn4 = emba_fn4;
	}

	public String getEmba_fn5() {
		return emba_fn5;
	}

	public void setEmba_fn5(String emba_fn5) {
		this.emba_fn5 = emba_fn5;
	}

	public String getEmba_fn6() {
		return emba_fn6;
	}

	public void setEmba_fn6(String emba_fn6) {
		this.emba_fn6 = emba_fn6;
	}

	public String getEmba_fp1() {
		return emba_fp1;
	}

	public void setEmba_fp1(String emba_fp1) {
		this.emba_fp1 = emba_fp1;
	}

	public String getEmba_fp2() {
		return emba_fp2;
	}

	public void setEmba_fp2(String emba_fp2) {
		this.emba_fp2 = emba_fp2;
	}

	public String getEmba_fp3() {
		return emba_fp3;
	}

	public void setEmba_fp3(String emba_fp3) {
		this.emba_fp3 = emba_fp3;
	}

	public String getEmba_fp4() {
		return emba_fp4;
	}

	public void setEmba_fp4(String emba_fp4) {
		this.emba_fp4 = emba_fp4;
	}

	public String getEmba_fp5() {
		return emba_fp5;
	}

	public void setEmba_fp5(String emba_fp5) {
		this.emba_fp5 = emba_fp5;
	}

	public String getEmba_fp6() {
		return emba_fp6;
	}

	public void setEmba_fp6(String emba_fp6) {
		this.emba_fp6 = emba_fp6;
	}

	public Integer getEmba_state() {
		return emba_state;
	}

	public void setEmba_state(Integer emba_state) {
		this.emba_state = emba_state;
	}

	public String getEmba_remark() {
		return emba_remark;
	}

	public void setEmba_remark(String emba_remark) {
		this.emba_remark = emba_remark;
	}

	public String getEmba_wt() {
		return emba_wt;
	}

	public void setEmba_wt(String emba_wt) {
		this.emba_wt = emba_wt;
	}

	public String getEmba_indate() {
		return emba_indate;
	}

	public void setEmba_indate(String emba_indate) {
		this.emba_indate = emba_indate;
	}

	public String getEmba_outdate() {
		return emba_outdate;
	}

	public void setEmba_outdate(String emba_outdate) {
		this.emba_outdate = emba_outdate;
	}

	public String getEmba_outreason() {
		return emba_outreason;
	}

	public void setEmba_outreason(String emba_outreason) {
		this.emba_outreason = emba_outreason;
	}

	public String getEmba_addtime() {
		return emba_addtime;
	}

	public void setEmba_addtime(String emba_addtime) {
		this.emba_addtime = emba_addtime;
	}

	public String getEmba_addname() {
		return emba_addname;
	}

	public void setEmba_addname(String emba_addname) {
		this.emba_addname = emba_addname;
	}

	public String getEmba_zytid() {
		return emba_zytid;
	}

	public void setEmba_zytid(String emba_zytid) {
		this.emba_zytid = emba_zytid;
	}

	public String getEmba_zytwtgid() {
		return emba_zytwtgid;
	}

	public void setEmba_zytwtgid(String emba_zytwtgid) {
		this.emba_zytwtgid = emba_zytwtgid;
	}

	public Integer getEmba_csid() {
		return emba_csid;
	}

	public void setEmba_csid(Integer emba_csid) {
		this.emba_csid = emba_csid;
	}

	public Integer getEmba_cpid() {
		return emba_cpid;
	}

	public void setEmba_cpid(Integer emba_cpid) {
		this.emba_cpid = emba_cpid;
	}

	public String getEmba_number() {
		return emba_number;
	}

	public void setEmba_number(String emba_number) {
		this.emba_number = emba_number;
	}

	public String getEmba_sbcard() {
		return emba_sbcard;
	}

	public void setEmba_sbcard(String emba_sbcard) {
		this.emba_sbcard = emba_sbcard;
	}

	public String getEmba_fag1() {
		return emba_fag1;
	}

	public void setEmba_fag1(String emba_fag1) {
		this.emba_fag1 = emba_fag1;
	}

	public String getEmba_fag2() {
		return emba_fag2;
	}

	public void setEmba_fag2(String emba_fag2) {
		this.emba_fag2 = emba_fag2;
	}

	public String getEmba_fag3() {
		return emba_fag3;
	}

	public void setEmba_fag3(String emba_fag3) {
		this.emba_fag3 = emba_fag3;
	}

	public String getEmba_fag4() {
		return emba_fag4;
	}

	public void setEmba_fag4(String emba_fag4) {
		this.emba_fag4 = emba_fag4;
	}

	public String getEmba_fag5() {
		return emba_fag5;
	}

	public void setEmba_fag5(String emba_fag5) {
		this.emba_fag5 = emba_fag5;
	}

	public String getEmba_fag6() {
		return emba_fag6;
	}

	public void setEmba_fag6(String emba_fag6) {
		this.emba_fag6 = emba_fag6;
	}

	public String getEmba_fw1() {
		return emba_fw1;
	}

	public void setEmba_fw1(String emba_fw1) {
		this.emba_fw1 = emba_fw1;
	}

	public String getEmba_fw2() {
		return emba_fw2;
	}

	public void setEmba_fw2(String emba_fw2) {
		this.emba_fw2 = emba_fw2;
	}

	public String getEmba_fw3() {
		return emba_fw3;
	}

	public void setEmba_fw3(String emba_fw3) {
		this.emba_fw3 = emba_fw3;
	}

	public String getEmba_fw4() {
		return emba_fw4;
	}

	public void setEmba_fw4(String emba_fw4) {
		this.emba_fw4 = emba_fw4;
	}

	public String getEmba_fw5() {
		return emba_fw5;
	}

	public void setEmba_fw5(String emba_fw5) {
		this.emba_fw5 = emba_fw5;
	}

	public String getEmba_fw6() {
		return emba_fw6;
	}

	public void setEmba_fw6(String emba_fw6) {
		this.emba_fw6 = emba_fw6;
	}

	public String getEmba_fr1() {
		return emba_fr1;
	}

	public void setEmba_fr1(String emba_fr1) {
		this.emba_fr1 = emba_fr1;
	}

	public String getEmba_fr2() {
		return emba_fr2;
	}

	public void setEmba_fr2(String emba_fr2) {
		this.emba_fr2 = emba_fr2;
	}

	public String getEmba_fr3() {
		return emba_fr3;
	}

	public void setEmba_fr3(String emba_fr3) {
		this.emba_fr3 = emba_fr3;
	}

	public String getEmba_fr4() {
		return emba_fr4;
	}

	public void setEmba_fr4(String emba_fr4) {
		this.emba_fr4 = emba_fr4;
	}

	public String getEmba_fr5() {
		return emba_fr5;
	}

	public void setEmba_fr5(String emba_fr5) {
		this.emba_fr5 = emba_fr5;
	}

	public String getEmba_fr6() {
		return emba_fr6;
	}

	public void setEmba_fr6(String emba_fr6) {
		this.emba_fr6 = emba_fr6;
	}

	public Integer getEmba_tapr_id() {
		return emba_tapr_id;
	}

	public void setEmba_tapr_id(Integer emba_tapr_id) {
		this.emba_tapr_id = emba_tapr_id;
	}

	public Integer getEmar_id() {
		return emar_id;
	}

	public void setEmar_id(Integer emar_id) {
		this.emar_id = emar_id;
	}

	public String getArchive() {
		return archive;
	}

	public void setArchive(String archive) {
		this.archive = archive;
	}

	public String getStatename() {
		return statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}

	public Integer getEmar_state() {
		return emar_state;
	}

	public void setEmar_state(Integer emar_state) {
		this.emar_state = emar_state;
	}

	public int getEmpic() {
		return empic;
	}

	public void setEmpic(int empic) {
		this.empic = empic;
	}

	public int getMobile() {
		return mobile;
	}

	public void setMobile(int mobile) {
		this.mobile = mobile;
	}

	public EmbaseModel() {
		super();
	}

	public EmbaseModel(Integer emba_id, Integer gid, Integer cid,
			String emba_name, String emba_spell, String emba_pinyin,
			String emba_sex, String emba_idcard, String emba_idcardclass,
			String emba_mobile, String emba_email, Integer emba_state,
			String emba_wt, String coba_name, String coba_client,
			String sein_shebao, String sein_gjj, String sein_shangbao,
			String sein_wt, String sein_shebaob, String sein_gjjb,
			String sein_da, String sein_zj, String sein_ldyg, String sein_xc,
			String emba_statestr, Integer empic, Integer mobile) {
		super();
		this.emba_id = emba_id;
		this.gid = gid;
		this.cid = cid;
		this.emba_name = emba_name;
		this.emba_spell = emba_spell;
		this.emba_pinyin = emba_pinyin;
		this.emba_sex = emba_sex;
		this.emba_idcard = emba_idcard;
		this.emba_idcardclass = emba_idcardclass;
		this.emba_mobile = emba_mobile;
		this.emba_email = emba_email;
		this.emba_state = emba_state;
		this.emba_wt = emba_wt;
		this.coba_name = coba_name;
		this.coba_client = coba_client;
		this.sein_shebao = sein_shebao;
		this.sein_gjj = sein_gjj;
		this.sein_shangbao = sein_shangbao;
		this.sein_wt = sein_wt;
		this.sein_shebaob = sein_shebaob;
		this.sein_gjjb = sein_gjjb;
		this.sein_da = sein_da;
		this.sein_zj = sein_zj;
		this.sein_ldyg = sein_ldyg;
		this.sein_xc = sein_xc;
		this.emba_statestr = emba_statestr;
		this.empic = empic;
		this.mobile = mobile;
	}

	public String getEmba_gz_bxbank() {
		return emba_gz_bxbank;
	}

	public void setEmba_gz_bxbank(String emba_gz_bxbank) {
		this.emba_gz_bxbank = emba_gz_bxbank;
	}

	public String getEmba_gz_bxaccount() {
		return emba_gz_bxaccount;
	}

	public void setEmba_gz_bxaccount(String emba_gz_bxaccount) {
		this.emba_gz_bxaccount = emba_gz_bxaccount;
	}

	public String getEmba_gz_cemail() {
		return emba_gz_cemail;
	}

	public void setEmba_gz_cemail(String emba_gz_cemail) {
		this.emba_gz_cemail = emba_gz_cemail;
	}

	public String getCoba_shortname() {
		return coba_shortname;
	}

	public void setCoba_shortname(String coba_shortname) {
		this.coba_shortname = coba_shortname;
	}

	public String getEmhu_houseid() {
		return emhu_houseid;
	}

	public void setEmhu_houseid(String emhu_houseid) {
		this.emhu_houseid = emhu_houseid;
	}

	public Integer getEmba_age() {
		return emba_age;
	}

	public void setEmba_age(Integer emba_age) {
		this.emba_age = emba_age;
	}

	public boolean getChecked() {
		return checked;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getEmba_ifcomputerid() {
		return emba_ifcomputerid;
	}

	public void setEmba_ifcomputerid(String emba_ifcomputerid) {
		this.emba_ifcomputerid = emba_ifcomputerid;
	}

	public Boolean getVis_computerid() {
		return vis_computerid;
	}

	public void setVis_computerid(Boolean vis_computerid) {
		this.vis_computerid = vis_computerid;
	}

	public Boolean getVis_hand() {
		return vis_hand;
	}

	public void setVis_hand(Boolean vis_hand) {
		this.vis_hand = vis_hand;
	}

	public Boolean getEmba_sbcard_notice() {
		return emba_sbcard_notice;
	}

	public void setEmba_sbcard_notice(Boolean emba_sbcard_notice) {
		this.emba_sbcard_notice = emba_sbcard_notice;
	}

	public Boolean getVis_sbcard_notice() {
		return vis_sbcard_notice;
	}

	public void setVis_sbcard_notice(Boolean vis_sbcard_notice) {
		this.vis_sbcard_notice = vis_sbcard_notice;
	}

	public String getEmba_service_place() {
		return emba_service_place;
	}

	public void setEmba_service_place(String emba_service_place) {
		this.emba_service_place = emba_service_place;
	}

	public String getEmba_ifhouse() {
		return emba_ifhouse;
	}

	public void setEmba_ifhouse(String emba_ifhouse) {
		this.emba_ifhouse = emba_ifhouse;
	}

	public String getSearch_computerid_url() {
		return search_computerid_url;
	}

	public void setSearch_computerid_url(String search_computerid_url) {
		this.search_computerid_url = search_computerid_url;
	}

	public String getEmba_sb_place() {
		return emba_sb_place;
	}

	public void setEmba_sb_place(String emba_sb_place) {
		this.emba_sb_place = emba_sb_place;
	}

	public String getEmba_house_place() {
		return emba_house_place;
	}

	public void setEmba_house_place(String emba_house_place) {
		this.emba_house_place = emba_house_place;
	}

	public BigDecimal getEmba_sb_radix() {
		return emba_sb_radix;
	}

	public void setEmba_sb_radix(BigDecimal emba_sb_radix) {
		this.emba_sb_radix = emba_sb_radix;
	}

	public String getEmba_formula() {
		return emba_formula;
	}

	public void setEmba_formula(String emba_formula) {
		this.emba_formula = emba_formula;
	}

	public String getEmba_sb_hj() {
		return emba_sb_hj;
	}

	public void setEmba_sb_hj(String emba_sb_hj) {
		this.emba_sb_hj = emba_sb_hj;
	}

	public BigDecimal getEmba_house_radix() {
		return emba_house_radix;
	}

	public void setEmba_house_radix(BigDecimal emba_house_radix) {
		this.emba_house_radix = emba_house_radix;
	}

	public Double getEmba_house_cpp() {
		return emba_house_cpp;
	}

	public void setEmba_house_cpp(Double emba_house_cpp) {
		this.emba_house_cpp = emba_house_cpp;
	}

	public String getEmba_modname() {
		return emba_modname;
	}

	public void setEmba_modname(String emba_modname) {
		this.emba_modname = emba_modname;
	}

	public String getEmba_modtime() {
		return emba_modtime;
	}

	public void setEmba_modtime(String emba_modtime) {
		this.emba_modtime = emba_modtime;
	}

	public String getEmba_emsb_ownmonth() {
		return emba_emsb_ownmonth;
	}

	public void setEmba_emsb_ownmonth(String emba_emsb_ownmonth) {
		this.emba_emsb_ownmonth = emba_emsb_ownmonth;
	}

	public String getEmba_emsb_m1() {
		return emba_emsb_m1;
	}

	public void setEmba_emsb_m1(String emba_emsb_m1) {
		this.emba_emsb_m1 = emba_emsb_m1;
	}

	public Integer getEmba_emsb_r1() {
		return emba_emsb_r1;
	}

	public void setEmba_emsb_r1(Integer emba_emsb_r1) {
		this.emba_emsb_r1 = emba_emsb_r1;
	}

	public String getEmba_emsb_m2() {
		return emba_emsb_m2;
	}

	public void setEmba_emsb_m2(String emba_emsb_m2) {
		this.emba_emsb_m2 = emba_emsb_m2;
	}

	public Integer getEmba_emsb_r2() {
		return emba_emsb_r2;
	}

	public void setEmba_emsb_r2(Integer emba_emsb_r2) {
		this.emba_emsb_r2 = emba_emsb_r2;
	}

	public String getEmba_emsb_m3() {
		return emba_emsb_m3;
	}

	public void setEmba_emsb_m3(String emba_emsb_m3) {
		this.emba_emsb_m3 = emba_emsb_m3;
	}

	public Integer getEmba_emsb_r3() {
		return emba_emsb_r3;
	}

	public void setEmba_emsb_r3(Integer emba_emsb_r3) {
		this.emba_emsb_r3 = emba_emsb_r3;
	}

	public BigDecimal getEmba_emhb_radix() {
		return emba_emhb_radix;
	}

	public void setEmba_emhb_radix(BigDecimal emba_emhb_radix) {
		this.emba_emhb_radix = emba_emhb_radix;
	}

	public String getEmba_emhb_ownmonth() {
		return emba_emhb_ownmonth;
	}

	public void setEmba_emhb_ownmonth(String emba_emhb_ownmonth) {
		this.emba_emhb_ownmonth = emba_emhb_ownmonth;
	}

	public String getEmba_emhb_startdate() {
		return emba_emhb_startdate;
	}

	public void setEmba_emhb_startdate(String emba_emhb_startdate) {
		this.emba_emhb_startdate = emba_emhb_startdate;
	}

	public String getEmba_emhb_stopdate() {
		return emba_emhb_stopdate;
	}

	public void setEmba_emhb_stopdate(String emba_emhb_stopdate) {
		this.emba_emhb_stopdate = emba_emhb_stopdate;
	}

	public String getEmba_emhb_reason() {
		return emba_emhb_reason;
	}

	public void setEmba_emhb_reason(String emba_emhb_reason) {
		this.emba_emhb_reason = emba_emhb_reason;
	}

	public BigDecimal getEmba_emhb_total() {
		return emba_emhb_total;
	}

	public void setEmba_emhb_total(BigDecimal emba_emhb_total) {
		this.emba_emhb_total = emba_emhb_total;
	}

	public String getEmba_emsb_feeownmonth() {
		return emba_emsb_feeownmonth;
	}

	public void setEmba_emsb_feeownmonth(String emba_emsb_feeownmonth) {
		this.emba_emsb_feeownmonth = emba_emsb_feeownmonth;
	}

	public String getEmba_emhb_feeownmonth() {
		return emba_emhb_feeownmonth;
	}

	public void setEmba_emhb_feeownmonth(String emba_emhb_feeownmonth) {
		this.emba_emhb_feeownmonth = emba_emhb_feeownmonth;
	}

	public String getEmba_emsb_foreigner() {
		return emba_emsb_foreigner;
	}

	public void setEmba_emsb_foreigner(String emba_emsb_foreigner) {
		this.emba_emsb_foreigner = emba_emsb_foreigner;
	}

	public String getEmsc_computerid() {
		return emsc_computerid;
	}

	public void setEmsc_computerid(String emsc_computerid) {
		this.emsc_computerid = emsc_computerid;
	}

	public String getCoba_address() {
		return coba_address;
	}

	public void setCoba_address(String coba_address) {
		this.coba_address = coba_address;
	}

	public int getEmsc_Single() {
		return emsc_Single;
	}

	public void setEmsc_Single(int emsc_Single) {
		this.emsc_Single = emsc_Single;
	}

	public String getEbco_incept_date() {
		return ebco_incept_date;
	}

	public void setEbco_incept_date(String ebco_incept_date) {
		this.ebco_incept_date = ebco_incept_date;
	}

	public String getEbco_maturity_date() {
		return ebco_maturity_date;
	}

	public void setEbco_maturity_date(String ebco_maturity_date) {
		this.ebco_maturity_date = ebco_maturity_date;
	}

	public String getEbco_change() {
		return ebco_change;
	}

	public void setEbco_change(String ebco_change) {
		this.ebco_change = ebco_change;
	}

	public String getEmzt_iffileservice() {
		return Emzt_iffileservice;
	}

	public void setEmzt_iffileservice(String emzt_iffileservice) {
		Emzt_iffileservice = emzt_iffileservice;
	}

	public String getColi_name() {
		return coli_name;
	}

	public void setColi_name(String coli_name) {
		this.coli_name = coli_name;
	}

	public Integer getEmbf_id() {
		return embf_id;
	}

	public void setEmbf_id(Integer embf_id) {
		this.embf_id = embf_id;
	}

	public String getColi_remark() {
		return coli_remark;
	}

	public void setColi_remark(String coli_remark) {
		this.coli_remark = coli_remark;
	}

	public BigDecimal getColm_fee() {
		return colm_fee;
	}

	public void setColm_fee(BigDecimal colm_fee) {
		this.colm_fee = colm_fee;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getColi_standard() {
		return coli_standard;
	}

	public void setColi_standard(String coli_standard) {
		this.coli_standard = coli_standard;
	}

	public String getColi_flpaykind() {
		return coli_flpaykind;
	}

	public void setColi_flpaykind(String coli_flpaykind) {
		this.coli_flpaykind = coli_flpaykind;
	}

	public String getEmba_costcenter() {
		return emba_costcenter;
	}

	public void setEmba_costcenter(String emba_costcenter) {
		this.emba_costcenter = emba_costcenter;
	}

	public String getEsiu_hj() {
		return esiu_hj;
	}

	public void setEsiu_hj(String esiu_hj) {
		this.esiu_hj = esiu_hj;
	}

	public boolean isRs() {
		return rs;
	}

	public boolean getRs() {
		return rs;
	}

	public void setRs(boolean rs) {
		this.rs = rs;
	}

	public boolean isCs() {
		return cs;
	}

	public boolean getCs() {
		return cs;
	}

	public void setCs(boolean cs) {
		this.cs = cs;
	}

	public boolean isDa() {
		return da;
	}

	public boolean getDa() {
		return da;
	}

	public void setDa(boolean da) {
		this.da = da;
	}

	public boolean isDh() {
		return dh;
	}

	public boolean getDh() {
		return dh;
	}

	public void setDh(boolean dh) {
		this.dh = dh;
	}

	public boolean isHk() {
		return hk;
	}

	public boolean getHk() {
		return hk;
	}

	public void setHk(boolean hk) {
		this.hk = hk;
	}

	public boolean isOther() {
		return other;
	}

	public boolean getOther() {
		return other;
	}

	public void setOther(boolean other) {
		this.other = other;
	}

	public boolean isSw() {
		return sw;
	}

	public boolean getSw() {
		return sw;
	}

	public void setSw(boolean sw) {
		this.sw = sw;
	}

	public boolean isSy() {
		return sy;
	}

	public boolean getSy() {
		return sy;
	}

	public void setSy(boolean sy) {
		this.sy = sy;
	}

	public boolean isTj() {
		return tj;
	}

	public boolean getTj() {
		return tj;
	}

	public void setTj(boolean tj) {
		this.tj = tj;
	}

	public boolean isFl() {
		return fl;
	}

	public boolean getFl() {
		return fl;
	}

	public void setFl(boolean fl) {
		this.fl = fl;
	}

	public boolean isZp() {
		return zp;
	}

	public boolean getZp() {
		return zp;
	}

	public void setZp(boolean zp) {
		this.zp = zp;
	}

	public String getEsiu_computerid() {
		return esiu_computerid;
	}

	public void setEsiu_computerid(String esiu_computerid) {
		this.esiu_computerid = esiu_computerid;
	}

	public String getEsiu_yl() {
		return esiu_yl;
	}

	public void setEsiu_yl(String esiu_yl) {
		this.esiu_yl = esiu_yl;
	}

	public String getEsiu_yltype() {
		return esiu_yltype;
	}

	public void setEsiu_yltype(String esiu_yltype) {
		this.esiu_yltype = esiu_yltype;
	}

	public String getEsiu_gs() {
		return esiu_gs;
	}

	public void setEsiu_gs(String esiu_gs) {
		this.esiu_gs = esiu_gs;
	}

	public String getEsiu_sye() {
		return esiu_sye;
	}

	public void setEsiu_sye(String esiu_sye) {
		this.esiu_sye = esiu_sye;
	}

	public String getEsiu_syu() {
		return esiu_syu;
	}

	public void setEsiu_syu(String esiu_syu) {
		this.esiu_syu = esiu_syu;
	}

	public String getEsiu_addtime() {
		return esiu_addtime;
	}

	public void setEsiu_addtime(String esiu_addtime) {
		this.esiu_addtime = esiu_addtime;
	}

	public String getEbco_probation_incept() {
		return ebco_probation_incept;
	}

	public void setEbco_probation_incept(String ebco_probation_incept) {
		this.ebco_probation_incept = ebco_probation_incept;
	}

	public String getEbco_probation_mdate() {
		return ebco_probation_mdate;
	}

	public void setEbco_probation_mdate(String ebco_probation_mdate) {
		this.ebco_probation_mdate = ebco_probation_mdate;
	}

	public String getEbco_working_station() {
		return ebco_working_station;
	}

	public void setEbco_working_station(String ebco_working_station) {
		this.ebco_working_station = ebco_working_station;
	}

	public Integer getEsiu_radix() {
		return esiu_radix;
	}

	public void setEsiu_radix(Integer esiu_radix) {
		this.esiu_radix = esiu_radix;
	}

	public BigDecimal getEmhu_bonus() {
		return emhu_bonus;
	}

	public void setEmhu_bonus(BigDecimal emhu_bonus) {
		this.emhu_bonus = emhu_bonus;
	}

	public Double getEmhu_opp() {
		return emhu_opp;
	}

	public void setEmhu_opp(Double emhu_opp) {
		this.emhu_opp = emhu_opp;
	}

	public Double getEmhu_cpp() {
		return emhu_cpp;
	}

	public void setEmhu_cpp(Double emhu_cpp) {
		this.emhu_cpp = emhu_cpp;
	}

	public Double getEmhu_radix() {
		return emhu_radix;
	}

	public void setEmhu_radix(Double emhu_radix) {
		this.emhu_radix = emhu_radix;
	}

	public Integer getSbcd_id() {
		return sbcd_id;
	}

	public void setSbcd_id(Integer sbcd_id) {
		this.sbcd_id = sbcd_id;
	}

	public Date getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(Date ownmonth) {
		this.ownmonth = ownmonth;
	}

	public String getEmba_ba_name() {
		return emba_ba_name;
	}

	public void setEmba_ba_name(String emba_ba_name) {
		this.emba_ba_name = emba_ba_name;
	}

	public BigDecimal getEmba_fee() {
		return emba_fee;
	}

	public void setEmba_fee(BigDecimal emba_fee) {
		this.emba_fee = emba_fee;
	}

	public String getEmba_class() {
		return emba_class;
	}

	public void setEmba_class(String emba_class) {
		this.emba_class = emba_class;
	}

	public String getEmba_payclass() {
		return emba_payclass;
	}

	public void setEmba_payclass(String emba_payclass) {
		this.emba_payclass = emba_payclass;
	}

	public String getEmba_paytype() {
		return emba_paytype;
	}

	public void setEmba_paytype(String emba_paytype) {
		this.emba_paytype = emba_paytype;
	}

	public boolean isTop() {
		return top;
	}

	public boolean getTop() {
		return top;
	}

	public void setTop(boolean top) {
		this.top = top;
	}

	public Integer getTopNum() {
		return topNum;
	}

	public void setTopNum(Integer topNum) {
		this.topNum = topNum;
	}

	public String getEmba_paymenttype() {
		return emba_paymenttype;
	}

	public void setEmba_paymenttype(String emba_paymenttype) {
		this.emba_paymenttype = emba_paymenttype;
	}

	public Date getOwnmonthend() {
		return ownmonthend;
	}

	public void setOwnmonthend(Date ownmonthend) {
		this.ownmonthend = ownmonthend;
	}

	public Date getEmba_sbbirth1_d() {
		return emba_sbbirth1_d;
	}

	public void setEmba_sbbirth1_d(Date emba_sbbirth1_d) {
		this.emba_sbbirth1_d = emba_sbbirth1_d;
	}

	public Date getEmba_sbbirth2_d() {
		return emba_sbbirth2_d;
	}

	public void setEmba_sbbirth2_d(Date emba_sbbirth2_d) {
		this.emba_sbbirth2_d = emba_sbbirth2_d;
	}

	public Date getEmba_sbbirth3_d() {
		return emba_sbbirth3_d;
	}

	public void setEmba_sbbirth3_d(Date emba_sbbirth3_d) {
		this.emba_sbbirth3_d = emba_sbbirth3_d;
	}

	public Date getEmba_sbbirth4_d() {
		return emba_sbbirth4_d;
	}

	public void setEmba_sbbirth4_d(Date emba_sbbirth4_d) {
		this.emba_sbbirth4_d = emba_sbbirth4_d;
	}

	public String getEmba_sy_place() {
		return emba_sy_place;
	}

	public void setEmba_sy_place(String emba_sy_place) {
		this.emba_sy_place = emba_sy_place;
	}

	public Integer getEmba_emsb_jlbj1() {
		return emba_emsb_jlbj1;
	}

	public void setEmba_emsb_jlbj1(Integer emba_emsb_jlbj1) {
		this.emba_emsb_jlbj1 = emba_emsb_jlbj1;

		if (emba_emsb_jlbj1 == null || emba_emsb_jlbj1 == 0) {
			this.chk_jlbj1 = false;
		} else if (emba_emsb_jlbj1 == 1) {
			this.chk_jlbj1 = true;
		}
	}

	public Integer getEmba_emsb_jlbj2() {
		return emba_emsb_jlbj2;
	}

	public void setEmba_emsb_jlbj2(Integer emba_emsb_jlbj2) {
		this.emba_emsb_jlbj2 = emba_emsb_jlbj2;

		if (emba_emsb_jlbj2 == null || emba_emsb_jlbj2 == 0) {
			this.chk_jlbj2 = false;
		} else if (emba_emsb_jlbj2 == 1) {
			this.chk_jlbj2 = true;
		}
	}

	public Integer getEmba_emsb_jlbj3() {
		return emba_emsb_jlbj3;
	}

	public void setEmba_emsb_jlbj3(Integer emba_emsb_jlbj3) {
		this.emba_emsb_jlbj3 = emba_emsb_jlbj3;

		if (emba_emsb_jlbj3 == null || emba_emsb_jlbj3 == 0) {
			this.chk_jlbj3 = false;
		} else if (emba_emsb_jlbj3 == 1) {
			this.chk_jlbj3 = true;
		}
	}

	public boolean isChk_jlbj1() {
		return chk_jlbj1;
	}

	public boolean getChk_jlbj1() {
		return chk_jlbj1;
	}

	public void setChk_jlbj1(boolean chk_jlbj1) {
		this.chk_jlbj1 = chk_jlbj1;
	}

	public boolean isChk_jlbj2() {
		return chk_jlbj2;
	}

	public boolean getChk_jlbj2() {
		return chk_jlbj2;
	}

	public void setChk_jlbj2(boolean chk_jlbj2) {
		this.chk_jlbj2 = chk_jlbj2;
	}

	public boolean isChk_jlbj3() {
		return chk_jlbj3;
	}

	public boolean getChk_jlbj3() {
		return chk_jlbj3;
	}

	public void setChk_jlbj3(boolean chk_jlbj3) {
		this.chk_jlbj3 = chk_jlbj3;
	}

	public List<CoglistModel> getCglist() {
		return cglist;
	}

	public void setCglist(List<CoglistModel> cglist) {
		this.cglist = cglist;
	}

}
