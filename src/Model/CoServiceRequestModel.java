package Model;

public class CoServiceRequestModel {
	   /// 公司服务要求表
    private Integer  csqe_id;
             /// cid
    private Integer  cid;
             /// 潜在客户信息表id
    private Integer  csqe_cola_id;
             /// coco_id
    private Integer  coco_id;
             /// 发票寄送日
    private Integer  csqe_isenddate;
             /// 社会保险缴纳情况 1：非深户一档医保；2：非深户二档医保；3：委托外地缴纳
    private Integer  csqe_sbtype;
             /// 社保类型备注
    private String  csqe_sbtype_remark;
             /// 各种证件的办理和费用收取 1：个人付；2：随付款；3：公司付
    private Integer  csqe_cardpay;
             /// 各种证件的办理和费用收取 备注
    private String  csqe_cardpay_remark;
             /// 上门服务
    private Integer  csqe_dtdservice;
             /// 上门服务 备注
    private String  csqe_dtdservice_remark;
             /// 委托外地
    private Integer  csqe_wt;
             /// 委托外地 备注
    private String  csqe_wt_remark;
             /// 外籍人服务
    private Integer  csqe_fservice;
             /// 外籍人服务 备注
    private String  csqe_fservice_remark;
             /// 其他
    private String  csqe_other;
             /// 服务中需注意的事项
    private String  csqe_ispa;
             /// 工作中曾需解决过的事情
    private String  csqe_ws;
             /// 待办事宜
    private String  csqe_todo;
             /// 工资个税付款日
    private Integer  csqe_gz_paydate;
             /// 公积金是否有超标员工 1：有；0：无；2：不确定；
    private Integer  csqe_houseover;
             /// 公积金是否有超标员工 备注
    private String  csqe_houseover_remark;
             /// 考勤计算约定 1：需要计算考勤；2：无需计算考勤；3：审核客户计算；
    private Integer  csqe_actype;
             /// 考勤计算约定 备注
    private String  csqe_actype_remark;
             /// 报表汇总约定 1：需要按部门汇总；2：不需要；3：不确定；4：需要特殊报表；
    private Integer  csqe_report;
             /// 报表汇总约定 备注
    private String  csqe_report_remark;
             /// 个税计算约定 1：我司计算明细扣缴；2：客户提供明细扣缴；3：仅发放税后金额；
    private Integer  csqe_taxctype;
             /// 个税计算约定 备注
    private String  csqe_taxctype_remark;
             /// 个税申报约定 1：委托我司大户申报；2：委托我司客户独立户申报；3：不委托；
    private Integer  csqe_taxdetype;
             /// 个税申报约定 备注
    private String  csqe_taxdetype_remark;
             /// 个税转账约定 1：客户独立户扣个税无需我司代转；2：客户独立户扣个税需要我司代转；3：中智大户扣缴；4：无个税服务；
    private Integer  csqe_taxpay;
             /// 个税转账约定 备注
    private String  csqe_taxpay_remark;
             /// 委托外地申报约定 1：有；2：不确定；0：无；
    private Integer  csqe_taxwt;
             /// 委托外地申报约定 委托地
    private String  csqe_taxwt_place;
             /// 个税申报时间 1：工资发放当月申报；2：工资发放次月申报；
    private Integer  csqe_taxde_date;
             /// 工资单形式 1：无需工资单；2：E-mail工资单；3：密封工资单；4：网上中智工资单；
    private Integer  csqe_gzpayroll_type;
             /// csqe_gzpayroll_people
    private String  csqe_gzpayroll_people;
             /// 密封工资单是否需要底单 1：是；0：否；
    private Integer  csqe_gzpayroll_b;
             /// csqe_addname
    private String  csqe_addname;
             /// csqe_addtime
    private String  csqe_addtime;
             /// 社保公积金扣缴约定
    private String  csqe_sbhousetype;
             /// 社保公积金扣缴约定 备注
    private String  csqe_sbhouse_remark;
             /// 社保公积金转账约定
    private String  csqe_sbhouse_trans;
             /// 社保公积金转账约定 备注
    private String  csqe_sbhouse_trans_renark;
    /// 有无住房公积金缴纳
    private String  csqe_house;
             /// 有无住房公积金缴纳 备注
    private String  csqe_house_remark;
             /// 有无劳动就业登记手续办理
    private String  csqe_regist;
             /// 有无劳动就业登记手续办理 备注
    private String  csqe_regist_remark,coba_shortname;
    private String sbtype,cardpay,dtdservice,wt,fservice,paydate,houseover,actype,emfics_backdate,papergz_paydate,
    report,taxctype,taxdetype,taxpay,taxwt,taxde_date,gzpayroll_type,gzpayroll_b,emfi_backdate,coba_emfi_senddatestr,isenddate;
    
    private Integer coba_emfi_backdate;//每月付款时间
    private Integer coba_emfi_senddate;//每月发付款通知时间
    
    private  Integer coba_emfics_backdate;//工资个税付款日
    private Integer coba_papergz_paydate;//纸工资单发送时间约定
    private String csqe_salaryleader,csqe_salaryassort,csqe_salaryduty;
    
    private Integer csqe_ifhassalary,csqe_ifhasrenshi;
    private String csqe_auditname;
    private int csqe_salary,csqe_request;
    
    private Integer coba_xctzzzsj; //薪酬台账明细制作时间
     
	private Integer coba_fktzffsj; //薪酬付款通知发放时间：
    private Integer coba_xchksj; //薪酬回款时间
    
    private String coba_xctzzzsjstr; //薪酬台账明细制作时间
    private String coba_fktzffsjstr; //薪酬付款通知发放时间：
    private String coba_xchksjstr; //薪酬回款时间
    private Integer csqe_firstmonthservernumber;
    private String csqe_salaryconfirmenddate;
    private Integer csqe_forecastservernumber;
    
    private String csqe_gzmonth;//发放当月/次月工资
    
	public String getCoba_xctzzzsjstr() {
		return coba_xctzzzsjstr;
	}
	public void setCoba_xctzzzsjstr(String coba_xctzzzsjstr) {
		this.coba_xctzzzsjstr = coba_xctzzzsjstr;
	}
	public String getCoba_fktzffsjstr() {
		return coba_fktzffsjstr;
	}
	public void setCoba_fktzffsjstr(String coba_fktzffsjstr) {
		this.coba_fktzffsjstr = coba_fktzffsjstr;
	}
	public String getCoba_xchksjstr() {
		return coba_xchksjstr;
	}
	public void setCoba_xchksjstr(String coba_xchksjstr) {
		this.coba_xchksjstr = coba_xchksjstr;
	}
	public Integer getCoba_xctzzzsj(String str) {
	 
		
		Integer d = null;
		if (str != null && !str.equals("")) {
			d = Integer.parseInt(str.replace("日", "").trim());
		}
		return d;
	}
	
	public void setCoba_xctzzzsj(Integer coba_xctzzzsj) {
		this.coba_xctzzzsj = coba_xctzzzsj;
	}
	
	public Integer getCoba_fktzffsj(String str) {
		Integer d = null;
		if (str != null && !str.equals("")) {
			d = Integer.parseInt(str.replace("日", "").trim());
		}
		return d;
	}
	 
	public void setCoba_fktzffsj(Integer coba_fktzffsj) {
		this.coba_fktzffsj = coba_fktzffsj;
	}
	public Integer getCoba_xchksj(String str) {
	 
		Integer d = null;
		if (str != null && !str.equals("")) {
			d = Integer.parseInt(str.replace("日", "").trim());
		}
		return d;
	}
	 
	public void setCoba_xchksj(Integer coba_xchksj) {
		this.coba_xchksj = coba_xchksj;
	}
	public String getCoba_emfi_senddatestr() {
		return coba_emfi_senddatestr;
	}
	public void setCoba_emfi_senddatestr(String coba_emfi_senddatestr) {
		this.coba_emfi_senddatestr = coba_emfi_senddatestr;
	}
	public Integer getCoba_emfi_senddate() {
		return coba_emfi_senddate;
	}
	public void setCoba_emfi_senddate(Integer coba_emfi_senddate) {
		this.coba_emfi_senddate = coba_emfi_senddate;
	}
	public Integer getCsqe_id() {
		return csqe_id;
	}
	public void setCsqe_id(Integer csqe_id) {
		this.csqe_id = csqe_id;
	}
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	
	public Integer getCoco_id() {
		return coco_id;
	}
	public void setCoco_id(Integer coco_id) {
		this.coco_id = coco_id;
	}
	public Integer getCsqe_isenddate() {
		return csqe_isenddate;
	}
	public void setCsqe_isenddate(Integer csqe_isenddate) {
		this.csqe_isenddate = csqe_isenddate;
	}
	public Integer getCsqe_sbtype() {
		return csqe_sbtype;
	}
	public void setCsqe_sbtype(Integer csqe_sbtype) {
		this.csqe_sbtype = csqe_sbtype;
	}
	public String getCsqe_sbtype_remark() {
		return csqe_sbtype_remark;
	}
	public void setCsqe_sbtype_remark(String csqe_sbtype_remark) {
		this.csqe_sbtype_remark = csqe_sbtype_remark;
	}
	public Integer getCsqe_cardpay() {
		return csqe_cardpay;
	}
	public void setCsqe_cardpay(Integer csqe_cardpay) {
		this.csqe_cardpay = csqe_cardpay;
	}
	public String getCsqe_cardpay_remark() {
		return csqe_cardpay_remark;
	}
	public void setCsqe_cardpay_remark(String csqe_cardpay_remark) {
		this.csqe_cardpay_remark = csqe_cardpay_remark;
	}
	public Integer getCsqe_dtdservice() {
		return csqe_dtdservice;
	}
	public void setCsqe_dtdservice(Integer csqe_dtdservice) {
		this.csqe_dtdservice = csqe_dtdservice;
	}
	public String getCsqe_dtdservice_remark() {
		return csqe_dtdservice_remark;
	}
	public void setCsqe_dtdservice_remark(String csqe_dtdservice_remark) {
		this.csqe_dtdservice_remark = csqe_dtdservice_remark;
	}
	public Integer getCsqe_wt() {
		return csqe_wt;
	}
	public void setCsqe_wt(Integer csqe_wt) {
		this.csqe_wt = csqe_wt;
	}
	public String getCsqe_wt_remark() {
		return csqe_wt_remark;
	}
	public void setCsqe_wt_remark(String csqe_wt_remark) {
		this.csqe_wt_remark = csqe_wt_remark;
	}
	public Integer getCsqe_fservice() {
		return csqe_fservice;
	}
	public void setCsqe_fservice(Integer csqe_fservice) {
		this.csqe_fservice = csqe_fservice;
	}
	public String getCsqe_fservice_remark() {
		return csqe_fservice_remark;
	}
	public void setCsqe_fservice_remark(String csqe_fservice_remark) {
		this.csqe_fservice_remark = csqe_fservice_remark;
	}
	public String getCsqe_other() {
		return csqe_other;
	}
	public void setCsqe_other(String csqe_other) {
		this.csqe_other = csqe_other;
	}
	public String getCsqe_ispa() {
		return csqe_ispa;
	}
	public void setCsqe_ispa(String csqe_ispa) {
		this.csqe_ispa = csqe_ispa;
	}
	public String getCsqe_ws() {
		return csqe_ws;
	}
	public void setCsqe_ws(String csqe_ws) {
		this.csqe_ws = csqe_ws;
	}
	public String getCsqe_todo() {
		return csqe_todo;
	}
	public void setCsqe_todo(String csqe_todo) {
		this.csqe_todo = csqe_todo;
	}
	public Integer getCsqe_gz_paydate() {
		return csqe_gz_paydate;
	}
	public void setCsqe_gz_paydate(Integer csqe_gz_paydate) {
		this.csqe_gz_paydate = csqe_gz_paydate;
	}
	public Integer getCsqe_houseover() {
		return csqe_houseover;
	}
	public void setCsqe_houseover(Integer csqe_houseover) {
		this.csqe_houseover = csqe_houseover;
	}
	public String getCsqe_houseover_remark() {
		return csqe_houseover_remark;
	}
	public void setCsqe_houseover_remark(String csqe_houseover_remark) {
		this.csqe_houseover_remark = csqe_houseover_remark;
	}
	public Integer getCsqe_actype() {
		return csqe_actype;
	}
	public void setCsqe_actype(Integer csqe_actype) {
		this.csqe_actype = csqe_actype;
	}
	public String getCsqe_actype_remark() {
		return csqe_actype_remark;
	}
	public void setCsqe_actype_remark(String csqe_actype_remark) {
		this.csqe_actype_remark = csqe_actype_remark;
	}
	public Integer getCsqe_report() {
		return csqe_report;
	}
	public void setCsqe_report(Integer csqe_report) {
		this.csqe_report = csqe_report;
	}
	public String getCsqe_report_remark() {
		return csqe_report_remark;
	}
	public void setCsqe_report_remark(String csqe_report_remark) {
		this.csqe_report_remark = csqe_report_remark;
	}
	public Integer getCsqe_taxctype() {
		return csqe_taxctype;
	}
	public void setCsqe_taxctype(Integer csqe_taxctype) {
		this.csqe_taxctype = csqe_taxctype;
	}
	public String getCsqe_taxctype_remark() {
		return csqe_taxctype_remark;
	}
	public void setCsqe_taxctype_remark(String csqe_taxctype_remark) {
		this.csqe_taxctype_remark = csqe_taxctype_remark;
	}
	public Integer getCsqe_taxdetype() {
		return csqe_taxdetype;
	}
	public void setCsqe_taxdetype(Integer csqe_taxdetype) {
		this.csqe_taxdetype = csqe_taxdetype;
	}
	public String getCsqe_taxdetype_remark() {
		return csqe_taxdetype_remark;
	}
	public void setCsqe_taxdetype_remark(String csqe_taxdetype_remark) {
		this.csqe_taxdetype_remark = csqe_taxdetype_remark;
	}
	public Integer getCsqe_taxpay() {
		return csqe_taxpay;
	}
	public void setCsqe_taxpay(Integer csqe_taxpay) {
		this.csqe_taxpay = csqe_taxpay;
	}
	public String getCsqe_taxpay_remark() {
		return csqe_taxpay_remark;
	}
	public void setCsqe_taxpay_remark(String csqe_taxpay_remark) {
		this.csqe_taxpay_remark = csqe_taxpay_remark;
	}
	public Integer getCsqe_taxwt() {
		return csqe_taxwt;
	}
	public void setCsqe_taxwt(Integer csqe_taxwt) {
		this.csqe_taxwt = csqe_taxwt;
	}
	public String getCsqe_taxwt_place() {
		return csqe_taxwt_place;
	}
	public void setCsqe_taxwt_place(String csqe_taxwt_place) {
		this.csqe_taxwt_place = csqe_taxwt_place;
	}
	public Integer getCsqe_taxde_date() {
		return csqe_taxde_date;
	}
	public void setCsqe_taxde_date(Integer csqe_taxde_date) {
		this.csqe_taxde_date = csqe_taxde_date;
	}
	public Integer getCsqe_gzpayroll_type() {
		return csqe_gzpayroll_type;
	}
	public void setCsqe_gzpayroll_type(Integer csqe_gzpayroll_type) {
		this.csqe_gzpayroll_type = csqe_gzpayroll_type;
	}
	public String getCsqe_gzpayroll_people() {
		return csqe_gzpayroll_people;
	}
	public void setCsqe_gzpayroll_people(String csqe_gzpayroll_people) {
		this.csqe_gzpayroll_people = csqe_gzpayroll_people;
	}
	public Integer getCsqe_gzpayroll_b() {
		return csqe_gzpayroll_b;
	}
	public void setCsqe_gzpayroll_b(Integer csqe_gzpayroll_b) {
		this.csqe_gzpayroll_b = csqe_gzpayroll_b;
	}
	public String getCsqe_addname() {
		return csqe_addname;
	}
	public void setCsqe_addname(String csqe_addname) {
		this.csqe_addname = csqe_addname;
	}
	public String getCsqe_addtime() {
		return csqe_addtime;
	}
	public void setCsqe_addtime(String csqe_addtime) {
		this.csqe_addtime = csqe_addtime;
	}
	public String getCsqe_sbhousetype() {
		return csqe_sbhousetype;
	}
	public void setCsqe_sbhousetype(String csqe_sbhousetype) {
		this.csqe_sbhousetype = csqe_sbhousetype;
	}
	public Integer getCsqe_cola_id() {
		return csqe_cola_id;
	}
	public void setCsqe_cola_id(Integer csqe_cola_id) {
		this.csqe_cola_id = csqe_cola_id;
	}
	
	public String getCsqe_sbhouse_remark() {
		return csqe_sbhouse_remark;
	}
	public void setCsqe_sbhouse_remark(String csqe_sbhouse_remark) {
		this.csqe_sbhouse_remark = csqe_sbhouse_remark;
	}
	public String getCsqe_sbhouse_trans() {
		return csqe_sbhouse_trans;
	}
	public void setCsqe_sbhouse_trans(String csqe_sbhouse_trans) {
		this.csqe_sbhouse_trans = csqe_sbhouse_trans;
	}
	public String getCsqe_sbhouse_trans_renark() {
		return csqe_sbhouse_trans_renark;
	}
	public void setCsqe_sbhouse_trans_renark(String csqe_sbhouse_trans_renark) {
		this.csqe_sbhouse_trans_renark = csqe_sbhouse_trans_renark;
	}
	public String getCsqe_house() {
		return csqe_house;
	}
	public void setCsqe_house(String csqe_house) {
		this.csqe_house = csqe_house;
	}
	public String getCsqe_house_remark() {
		return csqe_house_remark;
	}
	public void setCsqe_house_remark(String csqe_house_remark) {
		this.csqe_house_remark = csqe_house_remark;
	}
	public String getCsqe_regist() {
		return csqe_regist;
	}
	public void setCsqe_regist(String csqe_regist) {
		this.csqe_regist = csqe_regist;
	}
	public String getCsqe_regist_remark() {
		return csqe_regist_remark;
	}
	public void setCsqe_regist_remark(String csqe_regist_remark) {
		this.csqe_regist_remark = csqe_regist_remark;
	}
	public String getSbtype() {
		return sbtype;
	}
	public void setSbtype(String sbtype) {
		this.sbtype = sbtype;
	}
	public String getCardpay() {
		return cardpay;
	}
	public void setCardpay(String cardpay) {
		this.cardpay = cardpay;
	}
	public String getDtdservice() {
		return dtdservice;
	}
	public void setDtdservice(String dtdservice) {
		this.dtdservice = dtdservice;
	}
	public String getWt() {
		return wt;
	}
	public void setWt(String wt) {
		this.wt = wt;
	}
	public String getFservice() {
		return fservice;
	}
	public void setFservice(String fservice) {
		this.fservice = fservice;
	}
	public String getPaydate() {
		return paydate;
	}
	public void setPaydate(String paydate) {
		this.paydate = paydate;
	}
	public String getHouseover() {
		return houseover;
	}
	public void setHouseover(String houseover) {
		this.houseover = houseover;
	}
	public String getActype() {
		return actype;
	}
	public void setActype(String actype) {
		this.actype = actype;
	}
	public String getReport() {
		return report;
	}
	public void setReport(String report) {
		this.report = report;
	}
	public String getTaxctype() {
		return taxctype;
	}
	public void setTaxctype(String taxctype) {
		this.taxctype = taxctype;
	}
	public String getTaxdetype() {
		return taxdetype;
	}
	public void setTaxdetype(String taxdetype) {
		this.taxdetype = taxdetype;
	}
	public String getTaxpay() {
		return taxpay;
	}
	public void setTaxpay(String taxpay) {
		this.taxpay = taxpay;
	}
	public String getTaxwt() {
		return taxwt;
	}
	public void setTaxwt(String taxwt) {
		this.taxwt = taxwt;
	}
	public String getTaxde_date() {
		return taxde_date;
	}
	public void setTaxde_date(String taxde_date) {
		this.taxde_date = taxde_date;
	}
	public String getGzpayroll_type() {
		return gzpayroll_type;
	}
	public void setGzpayroll_type(String gzpayroll_type) {
		this.gzpayroll_type = gzpayroll_type;
	}
	public String getGzpayroll_b() {
		return gzpayroll_b;
	}
	public void setGzpayroll_b(String gzpayroll_b) {
		this.gzpayroll_b = gzpayroll_b;
	}
	public String getEmfi_backdate() {
		return emfi_backdate;
	}
	public void setEmfi_backdate(String emfi_backdate) {
		this.emfi_backdate = emfi_backdate;
	}
	public String getEmfics_backdate() {
		return emfics_backdate;
	}
	public void setEmfics_backdate(String emfics_backdate) {
		this.emfics_backdate = emfics_backdate;
	}
	public String getPapergz_paydate() {
		return papergz_paydate;
	}
	public void setPapergz_paydate(String papergz_paydate) {
		this.papergz_paydate = papergz_paydate;
	}
	public String getCoba_shortname() {
		return coba_shortname;
	}
	public void setCoba_shortname(String coba_shortname) {
		this.coba_shortname = coba_shortname;
	}
	public String getIsenddate() {
		return isenddate;
	}
	public void setIsenddate(String isenddate) {
		this.isenddate = isenddate;
	}
	public Integer getCoba_emfi_backdate() {
		return coba_emfi_backdate;
	}
	public void setCoba_emfi_backdate(Integer coba_emfi_backdate) {
		this.coba_emfi_backdate = coba_emfi_backdate;
	}
	public Integer getCoba_emfics_backdate() {
		return coba_emfics_backdate;
	}
	public void setCoba_emfics_backdate(Integer coba_emfics_backdate) {
		this.coba_emfics_backdate = coba_emfics_backdate;
	}
	public Integer getCoba_papergz_paydate() {
		return coba_papergz_paydate;
	}
	public void setCoba_papergz_paydate(Integer coba_papergz_paydate) {
		this.coba_papergz_paydate = coba_papergz_paydate;
	}
	public String getCsqe_salaryleader() {
		return csqe_salaryleader;
	}
	public void setCsqe_salaryleader(String csqe_salaryleader) {
		this.csqe_salaryleader = csqe_salaryleader;
	}
	public String getCsqe_salaryassort() {
		return csqe_salaryassort;
	}
	public void setCsqe_salaryassort(String csqe_salaryassort) {
		this.csqe_salaryassort = csqe_salaryassort;
	}
	public String getCsqe_salaryduty() {
		return csqe_salaryduty;
	}
	public void setCsqe_salaryduty(String csqe_salaryduty) {
		this.csqe_salaryduty = csqe_salaryduty;
	}
	public Integer getCsqe_ifhassalary() {
		return csqe_ifhassalary;
	}
	public void setCsqe_ifhassalary(Integer csqe_ifhassalary) {
		this.csqe_ifhassalary = csqe_ifhassalary;
	}
	public Integer getCsqe_ifhasrenshi() {
		return csqe_ifhasrenshi;
	}
	public void setCsqe_ifhasrenshi(Integer csqe_ifhasrenshi) {
		this.csqe_ifhasrenshi = csqe_ifhasrenshi;
	}
	public String getCsqe_auditname() {
		return csqe_auditname;
	}
	public void setCsqe_auditname(String csqe_auditname) {
		this.csqe_auditname = csqe_auditname;
	}
	public int getCsqe_salary() {
		return csqe_salary;
	}
	public void setCsqe_salary(int csqe_salary) {
		this.csqe_salary = csqe_salary;
	}
	public int getCsqe_request() {
		return csqe_request;
	}
	public void setCsqe_request(int csqe_request) {
		this.csqe_request = csqe_request;
	}
	public Integer getCsqe_firstmonthservernumber() {
		return csqe_firstmonthservernumber;
	}
	public void setCsqe_firstmonthservernumber(Integer csqe_firstmonthservernumber) {
		if(csqe_firstmonthservernumber==null)
			csqe_firstmonthservernumber=0;
		this.csqe_firstmonthservernumber = csqe_firstmonthservernumber;
	}
	public String getCsqe_salaryconfirmenddate() {
		return csqe_salaryconfirmenddate;
	}
	public void setCsqe_salaryconfirmenddate(String csqe_salaryconfirmenddate) {
		this.csqe_salaryconfirmenddate = csqe_salaryconfirmenddate;
	}
	public Integer getCsqe_forecastservernumber() {
		return csqe_forecastservernumber;
	}
	public void setCsqe_forecastservernumber(Integer csqe_forecastservernumber) {
		if(csqe_forecastservernumber==null)
			csqe_forecastservernumber=0;
		this.csqe_forecastservernumber = csqe_forecastservernumber;
	}
	public String getCsqe_gzmonth() {
		return csqe_gzmonth;
	}
	public void setCsqe_gzmonth(String csqe_gzmonth) {
		this.csqe_gzmonth = csqe_gzmonth;
	}	
    
}
