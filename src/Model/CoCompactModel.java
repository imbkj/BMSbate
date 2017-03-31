/*
 * 创建人：林少斌
 * 创建时间：2013-10-16
 * 用途：公司合同视图CoCompact_CoLa_CoBase_V
 */
package Model;

public class CoCompactModel {
	private Integer coco_id;
	private String cid;
	private Integer cid2;
	private String coco_cola_id;
	private String coco_compacttype;
	private String coco_compactid;
	private String coco_servicearea;
	private String coco_signdate;
	private String coco_stopdate;
	private String coco_stopreason;
	private String coco_stoptype;
	private String coco_inuredate;
	private String coco_indate;
	private String coco_delay;
	private String coco_remark;
	private Integer coco_state;
	private String coco_addtime;
	private String coco_addname;
	private String company;
	private String client;
	private String coba_shortname;
	private String coco_returndate;
	private String coco_signplace;
	private String coco_money;
	private String coco_invoice;
	private String coco_filedate;
	private String coco_fileid;
	private String coco_printdate;
	private String state;
	private String coco_auditingdate;
	private String coof_fee;
	private Integer coco_tapr_id;
	private String coco_class;
	private String coco_chs_copies;
	private String coco_en_copies;
	private Integer coco_wttype;
	private String coba_shortspell;

	private Integer dataState;// 1:有效(大于3)

	// 社保账户类型
	private String coco_shebao;
	// 公积金账户类型
	private String coco_house;
	// 公积金公司比例
	private String coco_cpp;
	// 公积金个人比例
	private String coco_opp;
	// 工资所属期
	private String coco_gzmonth;
	// 个税所属期
	private String coco_gsmonth;
	// 社保支付方式 0:空 1:客户存款 2:中智转款
	private Integer coco_sbfee;
	// 公积金支付方式 0:空 1:客户存款 2:中智转款
	private Integer coco_housefee;
	// 社保个人支付方式 0:空 1:客户存款 2:中智转款
	private Integer coco_sbperfee;
	// 个税支付方式 0:空 1:客户扣缴 2:中智扣缴
	private Integer coco_gsfee;
	// 是否有代发工资
	private Integer coco_gzpay;

	private String coco_shebaoAcc;
	private String coco_shebaobank;
	private String coco_Injury;
	private String coco_shebaoID;
	private String coco_houseid;
	private String cabc_id;
	private String city;
	private String agency;

	private String coco_houseacc;
	private String coco_housebank;
	private String coco_modtime;
	private String coco_modname;
	private Integer chfc_id;

	private Integer coli_id;
	private boolean checked;

	private String coco_compactclass;

	// 公积金个人支付方式 0:空 1:客户存款 2:中智转款
	private Integer coco_houseperfee;
	// 工资款支付方式 0:空 1:客户发放 2:中智发放
	private Integer coco_gzperfee;
	// 工资是否只计算不发放
	private Integer coco_ifgzpay;
	// 个税账户类型
	private String coco_gs;

	// 社保失业下浮比例
	private String coco_sb_sye;

	private Boolean sbInure;
	private Boolean houseInure;

	private Integer coco_paydate;// 每月付款日

	private double coco_fw_p;// 服务类比例
	private double coco_fl_p;// 福利类比例
	private double coco_dk_p;// 代扣代缴类比例
	
	private Integer coco_autst;//

	public CoCompactModel() {
		super();
	}

	public CoCompactModel(String coco_addname) {
		super();
		this.coco_addname = coco_addname;
	}

	public CoCompactModel(Integer coco_id, String cid, String coco_cola_id,
			String coco_compacttype, String coco_compactid,
			String coco_servicearea, String coco_signdate,
			String coco_stopdate, String coco_stopreason, String coco_stoptype,
			String coco_inuredate, String coco_indate, String coco_delay,
			String coco_remark, Integer coco_state, String coco_addtime,
			String coco_addname, String company, String coba_shortname,
			String coco_returndate, String coco_signplace, String coco_money,
			String coco_invoice, String coco_filedate, String coco_fileid,
			String coco_printdate, String state, String coco_auditingdate,
			String coof_fee) {
		super();
		this.coco_id = coco_id;
		this.cid = cid;
		this.coco_cola_id = coco_cola_id;
		this.coco_compacttype = coco_compacttype;
		this.coco_compactid = coco_compactid;
		this.coco_servicearea = coco_servicearea;
		this.coco_signdate = coco_signdate;
		this.coco_stopdate = coco_stopdate;
		this.coco_stopreason = coco_stopreason;
		this.coco_stoptype = coco_stoptype;
		this.coco_inuredate = coco_inuredate;
		this.coco_indate = coco_indate;
		this.coco_delay = coco_delay;
		this.coco_remark = coco_remark;
		this.coco_state = coco_state;
		this.coco_addtime = coco_addtime;
		this.coco_addname = coco_addname;
		this.company = company;
		this.coba_shortname = coba_shortname;
		this.coco_returndate = coco_returndate;
		this.coco_signplace = coco_signplace;
		this.coco_money = coco_money;
		this.coco_invoice = coco_invoice;
		this.coco_filedate = coco_filedate;
		this.coco_fileid = coco_fileid;
		this.coco_printdate = coco_printdate;
		this.state = state;
		this.coco_auditingdate = coco_auditingdate;
		this.coof_fee = coof_fee;
	}

	public CoCompactModel(Integer coco_id, String cid, String coco_cola_id,
			String coco_compacttype, String coco_compactid,
			String coco_servicearea, String coco_signdate,
			String coco_stopdate, String coco_stopreason, String coco_stoptype,
			String coco_inuredate, String coco_indate, String coco_delay,
			String coco_remark, Integer coco_state, String coco_addtime,
			String coco_addname, String company, String coba_shortname,
			String coco_returndate, String coco_signplace, String coco_money,
			String coco_invoice, String coco_filedate, String coco_fileid,
			String coco_printdate, String state, String coco_auditingdate,
			String coof_fee, Integer coco_tapr_id, String coco_class,
			String coco_chs_copies, String coco_en_copies) {
		super();
		this.coco_id = coco_id;
		this.cid = cid;
		this.coco_cola_id = coco_cola_id;
		this.coco_compacttype = coco_compacttype;
		this.coco_compactid = coco_compactid;
		this.coco_servicearea = coco_servicearea;
		this.coco_signdate = coco_signdate;
		this.coco_stopdate = coco_stopdate;
		this.coco_stopreason = coco_stopreason;
		this.coco_stoptype = coco_stoptype;
		this.coco_inuredate = coco_inuredate;
		this.coco_indate = coco_indate;
		this.coco_delay = coco_delay;
		this.coco_remark = coco_remark;
		this.coco_state = coco_state;
		this.coco_addtime = coco_addtime;
		this.coco_addname = coco_addname;
		this.company = company;
		this.coba_shortname = coba_shortname;
		this.coco_returndate = coco_returndate;
		this.coco_signplace = coco_signplace;
		this.coco_money = coco_money;
		this.coco_invoice = coco_invoice;
		this.coco_filedate = coco_filedate;
		this.coco_fileid = coco_fileid;
		this.coco_printdate = coco_printdate;
		this.state = state;
		this.coco_auditingdate = coco_auditingdate;
		this.coof_fee = coof_fee;
		this.coco_tapr_id = coco_tapr_id;
		this.coco_class = coco_class;
		this.coco_chs_copies = coco_chs_copies;
		this.coco_en_copies = coco_en_copies;
		if (coof_fee != null) {
			this.coof_fee = coof_fee.substring(0, coof_fee.length() - 2);
		}

	}

	public CoCompactModel(String cid, Integer coco_wttype,
			String coco_compacttype, String coco_class, String coco_inuredate,
			String coco_indate, String coco_delay, String coco_signdate,
			String coco_money, String coco_invoice, String coco_compactid,
			String coco_remark, String coco_addname, String coco_shebao,
			String coco_house, String coco_cpp, String coco_gzmonth,
			String coco_gsmonth, Integer coco_sbfee, Integer coco_housefee,
			Integer coco_sbperfee, Integer coco_gsfee, Integer coco_gzpay) {
		super();
		this.cid = cid;
		this.coco_compacttype = coco_compacttype;
		this.coco_compactid = coco_compactid;
		this.coco_signdate = coco_signdate;
		this.coco_inuredate = coco_inuredate;
		this.coco_indate = coco_indate;
		this.coco_delay = coco_delay;
		this.coco_remark = coco_remark;
		this.coco_addname = coco_addname;
		this.coco_money = coco_money;
		this.coco_invoice = coco_invoice;
		this.coco_class = coco_class;
		this.coco_shebao = coco_shebao;
		this.coco_house = coco_house;
		this.coco_cpp = coco_cpp;
		this.coco_gzmonth = coco_gzmonth;
		this.coco_gsmonth = coco_gsmonth;
		this.coco_sbfee = coco_sbfee;
		this.coco_housefee = coco_housefee;
		this.coco_sbperfee = coco_sbperfee;
		this.coco_gsfee = coco_gsfee;
		this.coco_gzpay = coco_gzpay;
		this.coco_wttype = coco_wttype;
	}

	public CoCompactModel(String cid, Integer coco_wttype,
			String coco_compacttype, String coco_class, String coco_inuredate,
			String coco_indate, String coco_delay, String coco_signdate,
			String coco_money, String coco_invoice, String coco_compactid,
			String city, String agency, String coco_remark,
			String coco_addname, String coco_shebao, String coco_house,
			String coco_cpp, String coco_gzmonth, String coco_gsmonth,
			Integer coco_sbfee, Integer coco_housefee, Integer coco_sbperfee,
			Integer coco_gsfee, Integer coco_gzpay, String coco_fileid) {
		super();
		this.cid = cid;
		this.coco_compacttype = coco_compacttype;
		this.coco_compactid = coco_compactid;
		this.coco_signdate = coco_signdate;
		this.coco_inuredate = coco_inuredate;
		this.coco_indate = coco_indate;
		this.coco_delay = coco_delay;
		this.coco_remark = coco_remark;
		this.coco_addname = coco_addname;
		this.coco_money = coco_money;
		this.coco_invoice = coco_invoice;
		this.coco_class = coco_class;
		this.coco_shebao = coco_shebao;
		this.coco_house = coco_house;
		this.coco_cpp = coco_cpp;
		this.coco_gzmonth = coco_gzmonth;
		this.coco_gsmonth = coco_gsmonth;
		this.coco_sbfee = coco_sbfee;
		this.coco_housefee = coco_housefee;
		this.coco_sbperfee = coco_sbperfee;
		this.coco_gsfee = coco_gsfee;
		this.coco_gzpay = coco_gzpay;
		this.coco_wttype = coco_wttype;
		this.coco_gzpay = coco_gzpay;
		this.coco_wttype = coco_wttype;
		this.city = city;
		this.agency = agency;
		this.coco_fileid = coco_fileid;
	}

	public CoCompactModel(Integer coco_id, String cid, Integer cid2,
			String coco_cola_id, String coco_compacttype,
			String coco_compactid, String coco_servicearea,
			String coco_signdate, String coco_stopdate, String coco_stopreason,
			String coco_stoptype, String coco_inuredate, String coco_indate,
			String coco_delay, String coco_remark, Integer coco_state,
			String coco_addtime, String coco_addname, String company,
			String coba_shortname, String coco_returndate,
			String coco_signplace, String coco_money, String coco_invoice,
			String coco_filedate, String coco_fileid, String coco_printdate,
			String state, String coco_auditingdate, String coof_fee,
			Integer coco_tapr_id, String coco_class, String coco_chs_copies,
			String coco_en_copies, Integer coco_wttype, String coba_shortspell,
			String coco_shebao, String coco_house, String coco_cpp,
			String coco_opp, String coco_gzmonth, String coco_gsmonth,
			Integer coco_sbfee, Integer coco_housefee, Integer coco_sbperfee,
			Integer coco_gsfee, Integer coco_gzpay, String coco_shebaoAcc,
			String coco_shebaobank, String coco_Injury, String coco_shebaoID,
			String coco_houseid) {
		super();
		this.coco_id = coco_id;
		this.cid = cid;
		this.cid2 = cid2;
		this.coco_cola_id = coco_cola_id;
		this.coco_compacttype = coco_compacttype;
		this.coco_compactid = coco_compactid;
		this.coco_servicearea = coco_servicearea;
		this.coco_signdate = coco_signdate;
		this.coco_stopdate = coco_stopdate;
		this.coco_stopreason = coco_stopreason;
		this.coco_stoptype = coco_stoptype;
		this.coco_inuredate = coco_inuredate;
		this.coco_indate = coco_indate;
		this.coco_delay = coco_delay;
		this.coco_remark = coco_remark;
		this.coco_state = coco_state;
		this.coco_addtime = coco_addtime;
		this.coco_addname = coco_addname;
		this.company = company;
		this.coba_shortname = coba_shortname;
		this.coco_returndate = coco_returndate;
		this.coco_signplace = coco_signplace;
		this.coco_money = coco_money;
		this.coco_invoice = coco_invoice;
		this.coco_filedate = coco_filedate;
		this.coco_fileid = coco_fileid;
		this.coco_printdate = coco_printdate;
		this.state = state;
		this.coco_auditingdate = coco_auditingdate;
		this.coof_fee = coof_fee;
		this.coco_tapr_id = coco_tapr_id;
		this.coco_class = coco_class;
		this.coco_chs_copies = coco_chs_copies;
		this.coco_en_copies = coco_en_copies;
		this.coco_wttype = coco_wttype;
		this.coba_shortspell = coba_shortspell;
		this.coco_shebao = coco_shebao;
		this.coco_house = coco_house;
		this.coco_cpp = coco_cpp;
		this.coco_opp = coco_opp;
		this.coco_gzmonth = coco_gzmonth;
		this.coco_gsmonth = coco_gsmonth;
		this.coco_sbfee = coco_sbfee;
		this.coco_housefee = coco_housefee;
		this.coco_sbperfee = coco_sbperfee;
		this.coco_gsfee = coco_gsfee;
		this.coco_gzpay = coco_gzpay;
		this.coco_shebaoAcc = coco_shebaoAcc;
		this.coco_shebaobank = coco_shebaobank;
		this.coco_Injury = coco_Injury;
		this.coco_shebaoID = coco_shebaoID;
		this.coco_houseid = coco_houseid;
	}

	public CoCompactModel(Integer coco_id, String cid, Integer cid2,
			String coco_cola_id, String coco_compacttype,
			String coco_compactid, String coco_servicearea,
			String coco_signdate, String coco_stopdate, String coco_stopreason,
			String coco_stoptype, String coco_inuredate, String coco_indate,
			String coco_delay, String coco_remark, Integer coco_state,
			String coco_addtime, String coco_addname, String company,
			String coba_shortname, String coco_returndate,
			String coco_signplace, String coco_money, String coco_invoice,
			String coco_filedate, String coco_fileid, String coco_printdate,
			String state, String coco_auditingdate, String coof_fee,
			Integer coco_tapr_id, String coco_class, String coco_chs_copies,
			String coco_en_copies, Integer coco_wttype, String coba_shortspell,
			String coco_shebao, String coco_house, String coco_cpp,
			String coco_opp, String coco_gzmonth, String coco_gsmonth,
			Integer coco_sbfee, Integer coco_housefee, Integer coco_sbperfee,
			Integer coco_gsfee, Integer coco_gzpay, String coco_shebaoAcc,
			String coco_shebaobank, String coco_Injury, String coco_shebaoID,
			String coco_houseid, String cabc_id) {
		super();
		this.coco_id = coco_id;
		this.cid = cid;
		this.cid2 = cid2;
		this.coco_cola_id = coco_cola_id;
		this.coco_compacttype = coco_compacttype;
		this.coco_compactid = coco_compactid;
		this.coco_servicearea = coco_servicearea;
		this.coco_signdate = coco_signdate;
		this.coco_stopdate = coco_stopdate;
		this.coco_stopreason = coco_stopreason;
		this.coco_stoptype = coco_stoptype;
		this.coco_inuredate = coco_inuredate;
		this.coco_indate = coco_indate;
		this.coco_delay = coco_delay;
		this.coco_remark = coco_remark;
		this.coco_state = coco_state;
		this.coco_addtime = coco_addtime;
		this.coco_addname = coco_addname;
		this.company = company;
		this.coba_shortname = coba_shortname;
		this.coco_returndate = coco_returndate;
		this.coco_signplace = coco_signplace;
		this.coco_money = coco_money;
		this.coco_invoice = coco_invoice;
		this.coco_filedate = coco_filedate;
		this.coco_fileid = coco_fileid;
		this.coco_printdate = coco_printdate;
		this.state = state;
		this.coco_auditingdate = coco_auditingdate;
		this.coof_fee = coof_fee;
		this.coco_tapr_id = coco_tapr_id;
		this.coco_class = coco_class;
		this.coco_chs_copies = coco_chs_copies;
		this.coco_en_copies = coco_en_copies;
		this.coco_wttype = coco_wttype;
		this.coba_shortspell = coba_shortspell;
		this.coco_shebao = coco_shebao;
		this.coco_house = coco_house;
		this.coco_cpp = coco_cpp;
		this.coco_opp = coco_opp;
		this.coco_gzmonth = coco_gzmonth;
		this.coco_gsmonth = coco_gsmonth;
		this.coco_sbfee = coco_sbfee;
		this.coco_housefee = coco_housefee;
		this.coco_sbperfee = coco_sbperfee;
		this.coco_gsfee = coco_gsfee;
		this.coco_gzpay = coco_gzpay;
		this.coco_shebaoAcc = coco_shebaoAcc;
		this.coco_shebaobank = coco_shebaobank;
		this.coco_Injury = coco_Injury;
		this.coco_shebaoID = coco_shebaoID;
		this.coco_houseid = coco_houseid;
		this.cabc_id = cabc_id;
	}

	public CoCompactModel(Integer coco_id, String cid, String coco_cola_id,
			String coco_compacttype, String coco_compactid,
			String coco_servicearea, String coco_signdate,
			String coco_stopdate, String coco_stopreason, String coco_stoptype,
			String coco_inuredate, String coco_indate, String coco_delay,
			String coco_remark, Integer coco_state, String coco_addtime,
			String coco_addname, String company, String coba_shortname,
			String coco_returndate, String coco_signplace, String coco_money,
			String coco_invoice, String coco_filedate, String coco_fileid,
			String coco_printdate, String state, String coco_auditingdate,
			String coof_fee, Integer coco_tapr_id, String coco_class,
			String coco_chs_copies, String coco_en_copies, String coco_shebao,
			String coco_house, String coco_opp) {
		super();
		this.coco_id = coco_id;
		this.cid = cid;
		this.coco_cola_id = coco_cola_id;
		this.coco_compacttype = coco_compacttype;
		this.coco_compactid = coco_compactid;
		this.coco_servicearea = coco_servicearea;
		this.coco_signdate = coco_signdate;
		this.coco_stopdate = coco_stopdate;
		this.coco_stopreason = coco_stopreason;
		this.coco_stoptype = coco_stoptype;
		this.coco_inuredate = coco_inuredate;
		this.coco_indate = coco_indate;
		this.coco_delay = coco_delay;
		this.coco_remark = coco_remark;
		this.coco_state = coco_state;
		this.coco_addtime = coco_addtime;
		this.coco_addname = coco_addname;
		this.company = company;
		this.coba_shortname = coba_shortname;
		this.coco_returndate = coco_returndate;
		this.coco_signplace = coco_signplace;
		this.coco_money = coco_money;
		this.coco_invoice = coco_invoice;
		this.coco_filedate = coco_filedate;
		this.coco_fileid = coco_fileid;
		this.coco_printdate = coco_printdate;
		this.state = state;
		this.coco_auditingdate = coco_auditingdate;
		this.coof_fee = coof_fee;
		this.coco_tapr_id = coco_tapr_id;
		this.coco_class = coco_class;
		this.coco_chs_copies = coco_chs_copies;
		this.coco_en_copies = coco_en_copies;
		if (coof_fee != null) {
			this.coof_fee = coof_fee.substring(0, coof_fee.length() - 2);
		}
		this.coco_shebao = coco_shebao;
		this.coco_house = coco_house;
		this.coco_opp = coco_opp;

	}

	public String getCoco_sb_sye() {
		return coco_sb_sye;
	}

	public void setCoco_sb_sye(String coco_sb_sye) {
		this.coco_sb_sye = coco_sb_sye;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getCabc_id() {
		return cabc_id;
	}

	public void setCabc_id(String cabc_id) {
		this.cabc_id = cabc_id;
	}

	public String getCoco_shebaoAcc() {
		return coco_shebaoAcc;
	}

	public void setCoco_shebaoAcc(String coco_shebaoAcc) {
		this.coco_shebaoAcc = coco_shebaoAcc;
	}

	public String getCoco_shebaobank() {
		return coco_shebaobank;
	}

	public void setCoco_shebaobank(String coco_shebaobank) {
		this.coco_shebaobank = coco_shebaobank;
	}

	public String getCoco_Injury() {
		return coco_Injury;
	}

	public void setCoco_Injury(String coco_Injury) {
		this.coco_Injury = coco_Injury;
	}

	public String getCoco_shebaoID() {
		return coco_shebaoID;
	}

	public void setCoco_shebaoID(String coco_shebaoID) {
		this.coco_shebaoID = coco_shebaoID;
	}

	public String getCoco_houseid() {
		return coco_houseid;
	}

	public void setCoco_houseid(String coco_houseid) {
		this.coco_houseid = coco_houseid;
	}

	public String getCoba_shortspell() {
		return coba_shortspell;
	}

	public void setCoba_shortspell(String coba_shortspell) {
		this.coba_shortspell = coba_shortspell;
	}

	public String getCoco_class() {
		return coco_class;
	}

	public void setCoco_class(String coco_class) {
		this.coco_class = coco_class;
	}

	public String getCoco_chs_copies() {
		return coco_chs_copies;
	}

	public void setCoco_chs_copies(String coco_chs_copies) {
		this.coco_chs_copies = coco_chs_copies;
	}

	public String getCoco_en_copies() {
		return coco_en_copies;
	}

	public void setCoco_en_copies(String coco_en_copies) {
		this.coco_en_copies = coco_en_copies;
	}

	public Integer getCoco_tapr_id() {
		return coco_tapr_id;
	}

	public void setCoco_tapr_id(Integer coco_tapr_id) {
		this.coco_tapr_id = coco_tapr_id;
	}

	public String getCoof_fee() {
		return coof_fee;
	}

	public void setCoof_fee(String coof_fee) {
		this.coof_fee = coof_fee;
	}

	public String getCoco_auditingdate() {
		return coco_auditingdate;
	}

	public void setCoco_auditingdate(String coco_auditingdate) {
		this.coco_auditingdate = coco_auditingdate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getCoco_id() {
		return coco_id;
	}

	public void setCoco_id(Integer coco_id) {
		this.coco_id = coco_id;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public Integer getCid2() {
		return cid2;
	}

	public void setCid2(Integer cid2) {
		this.cid2 = cid2;
	}

	public String getCoco_cola_id() {
		return coco_cola_id;
	}

	public void setCoco_cola_id(String coco_cola_id) {
		this.coco_cola_id = coco_cola_id;
	}

	public String getCoco_compacttype() {
		return coco_compacttype;
	}

	public void setCoco_compacttype(String coco_compacttype) {
		this.coco_compacttype = coco_compacttype;
	}

	public String getCoco_compactid() {
		return coco_compactid;
	}

	public void setCoco_compactid(String coco_compactid) {
		this.coco_compactid = coco_compactid;
	}

	public String getCoco_servicearea() {
		return coco_servicearea;
	}

	public void setCoco_servicearea(String coco_servicearea) {
		this.coco_servicearea = coco_servicearea;
	}

	public String getCoco_signdate() {
		return coco_signdate;
	}

	public void setCoco_signdate(String coco_signdate) {
		this.coco_signdate = coco_signdate;
	}

	public String getCoco_stopdate() {
		return coco_stopdate;
	}

	public void setCoco_stopdate(String coco_stopdate) {
		this.coco_stopdate = coco_stopdate;
	}

	public String getCoco_stopreason() {
		return coco_stopreason;
	}

	public void setCoco_stopreason(String coco_stopreason) {
		this.coco_stopreason = coco_stopreason;
	}

	public String getCoco_stoptype() {
		return coco_stoptype;
	}

	public void setCoco_stoptype(String coco_stoptype) {
		this.coco_stoptype = coco_stoptype;
	}

	public String getCoco_inuredate() {
		return coco_inuredate;
	}

	public void setCoco_inuredate(String coco_inuredate) {
		this.coco_inuredate = coco_inuredate;
	}

	public String getCoco_indate() {
		return coco_indate;
	}

	public void setCoco_indate(String coco_indate) {
		this.coco_indate = coco_indate;
	}

	public String getCoco_delay() {
		return coco_delay;
	}

	public void setCoco_delay(String coco_delay) {
		this.coco_delay = coco_delay;
	}

	public String getCoco_remark() {
		return coco_remark;
	}

	public void setCoco_remark(String coco_remark) {
		this.coco_remark = coco_remark;
	}

	public Integer getCoco_state() {
		return coco_state;
	}

	public void setCoco_state(Integer coco_state) {
		this.coco_state = coco_state;
	}

	public String getCoco_addtime() {
		return coco_addtime;
	}

	public void setCoco_addtime(String coco_addtime) {
		this.coco_addtime = coco_addtime;
	}

	public String getCoco_addname() {
		return coco_addname;
	}

	public void setCoco_addname(String coco_addname) {
		this.coco_addname = coco_addname;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCoba_shortname() {
		return coba_shortname;
	}

	public void setCoba_shortname(String coba_shortname) {
		this.coba_shortname = coba_shortname;
	}

	public String getCoco_returndate() {
		return coco_returndate;
	}

	public void setCoco_returndate(String coco_returndate) {
		this.coco_returndate = coco_returndate;
	}

	public String getCoco_signplace() {
		return coco_signplace;
	}

	public void setCoco_signplace(String coco_signplace) {
		this.coco_signplace = coco_signplace;
	}

	public String getCoco_money() {
		return coco_money;
	}

	public void setCoco_money(String coco_money) {
		this.coco_money = coco_money;
	}

	public String getCoco_invoice() {
		return coco_invoice;
	}

	public void setCoco_invoice(String coco_invoice) {
		this.coco_invoice = coco_invoice;
	}

	public String getCoco_filedate() {
		return coco_filedate;
	}

	public void setCoco_filedate(String coco_filedate) {
		this.coco_filedate = coco_filedate;
	}

	public String getCoco_fileid() {
		return coco_fileid;
	}

	public void setCoco_fileid(String coco_fileid) {
		this.coco_fileid = coco_fileid;
	}

	public String getCoco_printdate() {
		return coco_printdate;
	}

	public void setCoco_printdate(String coco_printdate) {
		this.coco_printdate = coco_printdate;
	}

	public String getCoco_shebao() {
		return coco_shebao;
	}

	public void setCoco_shebao(String coco_shebao) {
		this.coco_shebao = coco_shebao;
	}

	public String getCoco_house() {
		return coco_house;
	}

	public void setCoco_house(String coco_house) {
		this.coco_house = coco_house;
	}

	public String getCoco_cpp() {
		return coco_cpp;
	}

	public void setCoco_cpp(String coco_cpp) {
		this.coco_cpp = coco_cpp;
	}

	public String getCoco_opp() {
		return coco_opp;
	}

	public void setCoco_opp(String cooc_opp) {
		this.coco_opp = cooc_opp;
	}

	public String getCoco_gzmonth() {
		return coco_gzmonth;
	}

	public void setCoco_gzmonth(String coco_gzmonth) {
		this.coco_gzmonth = coco_gzmonth;
	}

	public String getCoco_gsmonth() {
		return coco_gsmonth;
	}

	public void setCoco_gsmonth(String coco_gsmonth) {
		this.coco_gsmonth = coco_gsmonth;
	}

	public Integer getCoco_sbfee() {
		return coco_sbfee;
	}

	public void setCoco_sbfee(Integer coco_sbfee) {
		this.coco_sbfee = coco_sbfee;
	}

	public Integer getCoco_housefee() {
		return coco_housefee;
	}

	public void setCoco_housefee(Integer coco_housefee) {
		this.coco_housefee = coco_housefee;
	}

	public Integer getCoco_sbperfee() {
		return coco_sbperfee;
	}

	public void setCoco_sbperfee(Integer coco_sbperfee) {
		this.coco_sbperfee = coco_sbperfee;
	}

	public Integer getCoco_gsfee() {
		return coco_gsfee;
	}

	public void setCoco_gsfee(Integer coco_gsfee) {
		this.coco_gsfee = coco_gsfee;
	}

	public Integer getCoco_gzpay() {
		return coco_gzpay;
	}

	public void setCoco_gzpay(Integer coco_gzpay) {
		this.coco_gzpay = coco_gzpay;
	}

	public Integer getCoco_wttype() {
		return coco_wttype;
	}

	public void setCoco_wttype(Integer coco_wttype) {
		this.coco_wttype = coco_wttype;
	}

	public Integer getColi_id() {
		return coli_id;
	}

	public void setColi_id(Integer coli_id) {
		this.coli_id = coli_id;
	}

	public Integer getDataState() {
		return dataState;
	}

	public void setDataState(Integer dataState) {
		this.dataState = dataState;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getCoco_houseacc() {
		return coco_houseacc;
	}

	public void setCoco_houseacc(String coco_houseacc) {
		this.coco_houseacc = coco_houseacc;
	}

	public String getCoco_housebank() {
		return coco_housebank;
	}

	public void setCoco_housebank(String coco_housebank) {
		this.coco_housebank = coco_housebank;
	}

	public String getCoco_modtime() {
		return coco_modtime;
	}

	public void setCoco_modtime(String coco_modtime) {
		this.coco_modtime = coco_modtime;
	}

	public String getCoco_modname() {
		return coco_modname;
	}

	public void setCoco_modname(String coco_modname) {
		this.coco_modname = coco_modname;
	}

	public Integer getChfc_id() {
		return chfc_id;
	}

	public void setChfc_id(Integer chfc_id) {
		this.chfc_id = chfc_id;
	}

	public Integer getCoco_houseperfee() {
		return coco_houseperfee;
	}

	public void setCoco_houseperfee(Integer coco_houseperfee) {
		this.coco_houseperfee = coco_houseperfee;
	}

	public Integer getCoco_gzperfee() {
		return coco_gzperfee;
	}

	public void setCoco_gzperfee(Integer coco_gzperfee) {
		this.coco_gzperfee = coco_gzperfee;
	}

	public Integer getCoco_ifgzpay() {
		return coco_ifgzpay;
	}

	public void setCoco_ifgzpay(Integer coco_ifgzpay) {
		this.coco_ifgzpay = coco_ifgzpay;
	}

	public String getCoco_gs() {
		return coco_gs;
	}

	public void setCoco_gs(String coco_gs) {
		this.coco_gs = coco_gs;
	}

	public String getCoco_compactclass() {
		return coco_compactclass;
	}

	public void setCoco_compactclass(String coco_compactclass) {
		this.coco_compactclass = coco_compactclass;
	}

	public Boolean getSbInure() {
		return sbInure;
	}

	public void setSbInure(Boolean sbInure) {
		this.sbInure = sbInure;
	}

	public Boolean getHouseInure() {
		return houseInure;
	}

	public void setHouseInure(Boolean houseInure) {
		this.houseInure = houseInure;
	}

	public Integer getCoco_paydate() {
		return coco_paydate;
	}

	public void setCoco_paydate(Integer coco_paydate) {
		this.coco_paydate = coco_paydate;
	}

	public double getCoco_fw_p() {
		return coco_fw_p;
	}

	public void setCoco_fw_p(double coco_fw_p) {
		this.coco_fw_p = coco_fw_p;
	}

	public double getCoco_fl_p() {
		return coco_fl_p;
	}

	public void setCoco_fl_p(double coco_fl_p) {
		this.coco_fl_p = coco_fl_p;
	}

	public double getCoco_dk_p() {
		return coco_dk_p;
	}

	public void setCoco_dk_p(double coco_dk_p) {
		this.coco_dk_p = coco_dk_p;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public Integer getCoco_autst() {
		return coco_autst;
	}

	public void setCoco_autst(Integer coco_autst) {
		this.coco_autst = coco_autst;
	}

}
