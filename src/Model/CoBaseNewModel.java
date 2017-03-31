package Model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
/**
 * 
 * @author suhognyaun 
 * 2016-05-20
 */
public class CoBaseNewModel {
	private int coba_id;
	private Integer cid;
	private String coba_shortname;
	private String coba_client;
	private String coba_spell;
	private String coba_company;
	private String coba_shortspell;
	private String coba_namespell;
	private String coba_englishname;
	private String coba_country;
	private String coba_setuptype; // 机构性质(公司类型)
	private String coba_servicearea;
	private int coba_servicestate;
	private String coba_stopreason;
	private String coba_stoptime;
	private String coba_stopremark;
	private String coba_stopname;
	private String coba_industytype;// 所属行业
	private String coba_clientsize;
	private String coba_compacttype;
	private String coba_vip;
	private String coba_address;
	private String coba_area;
	private String coba_companymanager;
	private String coba_manageraddress;
	private String coba_postcode;
	private String coba_website;
	private String coba_clientsource;
	private String coba_escrowarea;
	private String coba_escrowcompany;
	private String coba_manager;
	private String coba_clientmanager;
	private String coba_hzqsr;
	private String coba_remark;
	private String coba_regagent;
	private String coba_reg_fund;
	private String coba_certificate;
	private String coba_bregdeadline;
	private String coba_bregid;
	private String coba_regexpires;
	private String coba_regtime;
	private String coba_reglimit;
	private String coba_organdeadline;
	private String coba_orregtime;
	private String coba_organcode;
	private String coba_ntbank;
	private String coba_ntaccount;
	private String coba_ntid;
	private String coba_ntregtime;
	private String coba_ntlimit;
	private String coba_ntdeadline;
	private String coba_ltregid;
	private String coba_ltregtime;
	private String coba_ltlimit;
	private String coba_ltstate;
	private String coba_ltid;
	private String coba_ltdeadline;
	private String coba_ltbank;
	private String coba_ltaccount;
	private String coba_gtstate;
	private String coba_gtbank;
	private String coba_gtacc;
	private String coba_gtto;
	private String coba_gtdeadline;
	private String coba_sistate;
	private String coba_siiilimit;
	private String coba_sicoding;
	private String coba_sibank;
	private String coba_siaccount;
	private String coba_sihospital;
	private String coba_sihosphone;
	private String coba_sihosadd;
	private String coba_dept;
	private String coba_regremark;
	private String coba_addtime;
	private String coba_addname;
	private String coba_modtime;
	private String coba_modname;
	private String coba_shebaodeclare;
	private String coba_ufid;
	private String coba_ufid2;
	private String coba_ufclass;
	private String coba_clientclass;
	private String coba_invoicerule;
	private String coba_invoiceaddress;
	private String coba_wtco;
	private String coba_regdataList;
	private String coba_assistant;
	private String coba_zytcid;
	private String coba_developer;
	private int coba_gzautoemail;
	private int coba_sign;
	private String coba_invoice;
	private String coba_taxmonth;
	private String coba_gzaddname;
	private String coba_gzaudname;
	private int coba_gzemailtype;
	private String coba_emfi_paydate;
	private String coba_emfi_backdate;
	private String coba_gz_paydate;
	private String coba_emailgz_paydate;
	private String coba_papergz_paydate;
	private String coba_emfics_deldate;
	private String coba_emfics_paydate;
	private String coba_emfics_backdate;
	private String coba_zytwtcid;
	private int coba_gzautoemaildays;
	private Integer peopnum;// 公司的员工数
	private String coconum;// 公司合同数
	private Integer pronum;// 服务约束数
	private int log_id;
	private int cou; // 计算
	private boolean fuzzy;
	private String coba_kind;
	private String company;
	private List<CoFinanceCollectModel> cfcms;
	private CollectAmountNewModel amount;
	private String msg_a;
	private String coba_ifhasbribery;// 是否签订反贿赂协议
	private String coba_fpremark;
	private String coba_regaccount;
	private String coba_regaccountpwd;
	private Date makedate;
	private String makenumber, zaiyao;
	private int group_count, group_firstrow, group_order;
	private String cfmb_remark;
	private String cfta_addtime;
	private Integer ownmonth;
	private String cfmb_number;
	private String outstate;
	private String cohf_houseid;
	private String cfss_cfso_id;
	private BigDecimal total;
	private Boolean checked;
	private String  cfss_type;
	private String cfss_fpstate;
	
	public CoBaseNewModel() {
		super();
	}

	public CoBaseNewModel(int coba_id, int cid, String coba_shortname,
			String coba_client, String coba_spell, String coba_company,
			String coba_shortspell, String coba_namespell,
			String coba_englishname, String coba_country,
			String coba_setuptype, String coba_servicearea,
			int coba_servicestate, String coba_stopreason,
			String coba_stoptime, String coba_stopremark, String coba_stopname,
			String coba_industytype, String coba_clientsize,
			String coba_compacttype, String coba_vip, String coba_address,
			String coba_area, String coba_companymanager,
			String coba_manageraddress, String coba_postcode,
			String coba_website, String coba_clientsource,
			String coba_escrowarea, String coba_escrowcompany,
			String coba_manager, String coba_clientmanager, String coba_hzqsr,
			String coba_remark, String coba_regagent, String coba_reg_fund,
			String coba_certificate, String coba_bregdeadline,
			String coba_bregid, String coba_regexpires, String coba_regtime,
			String coba_reglimit, String coba_organdeadline,
			String coba_orregtime, String coba_organcode, String coba_ntbank,
			String coba_ntaccount, String coba_ntid, String coba_ntregtime,
			String coba_ntlimit, String coba_ntdeadline, String coba_ltregid,
			String coba_ltregtime, String coba_ltlimit, String coba_ltstate,
			String coba_ltid, String coba_ltdeadline, String coba_ltbank,
			String coba_ltaccount, String coba_gtstate, String coba_gtbank,
			String coba_gtacc, String coba_gtto, String coba_gtdeadline,
			String coba_sistate, String coba_siiilimit, String coba_sicoding,
			String coba_sibank, String coba_siaccount, String coba_sihospital,
			String coba_sihosphone, String coba_sihosadd, String coba_dept,
			String coba_regremark, String coba_addtime, String coba_addname,
			String coba_modtime, String coba_modname,
			String coba_shebaodeclare, String coba_ufid, String coba_ufid2,
			String coba_ufclass, String coba_invoicerule,
			String coba_invoiceaddress, String coba_wtco,
			String coba_regdataList, String coba_assistant, String coba_zytcid,
			String coba_developer, int coba_gzautoemail, int coba_sign,
			String coba_invoice, String coba_taxmonth, String coba_gzaddname,
			String coba_gzaudname, int coba_gzemailtype,
			String coba_emfi_paydate, String coba_emfi_backdate,
			String coba_gz_paydate, String coba_emailgz_paydate,
			String coba_papergz_paydate, String coba_emfics_deldate,
			String coba_emfics_paydate, String coba_emfics_backdate,
			String coba_zytwtcid, int coba_gzautoemaildays) {
		super();
		this.coba_id = coba_id;
		this.cid = cid;
		this.coba_shortname = coba_shortname;
		this.coba_client = coba_client;
		this.coba_spell = coba_spell;
		this.coba_company = coba_company;
		this.coba_shortspell = coba_shortspell;
		this.coba_namespell = coba_namespell;
		this.coba_englishname = coba_englishname;
		this.coba_country = coba_country;
		this.coba_setuptype = coba_setuptype;
		this.coba_servicearea = coba_servicearea;
		this.coba_servicestate = coba_servicestate;
		this.coba_stopreason = coba_stopreason;
		this.coba_stoptime = coba_stoptime;
		this.coba_stopremark = coba_stopremark;
		this.coba_stopname = coba_stopname;
		this.coba_industytype = coba_industytype;
		this.coba_clientsize = coba_clientsize;
		this.coba_compacttype = coba_compacttype;
		this.coba_vip = coba_vip;
		this.coba_address = coba_address;
		this.coba_area = coba_area;
		this.coba_companymanager = coba_companymanager;
		this.coba_manageraddress = coba_manageraddress;
		this.coba_postcode = coba_postcode;
		this.coba_website = coba_website;
		this.coba_clientsource = coba_clientsource;
		this.coba_escrowarea = coba_escrowarea;
		this.coba_escrowcompany = coba_escrowcompany;
		this.coba_manager = coba_manager;
		this.coba_clientmanager = coba_clientmanager;
		this.coba_hzqsr = coba_hzqsr;
		this.coba_remark = coba_remark;
		this.coba_regagent = coba_regagent;
		this.coba_reg_fund = coba_reg_fund;
		this.coba_certificate = coba_certificate;
		this.coba_bregdeadline = coba_bregdeadline;
		this.coba_bregid = coba_bregid;
		this.coba_regexpires = coba_regexpires;
		this.coba_regtime = coba_regtime;
		this.coba_reglimit = coba_reglimit;
		this.coba_organdeadline = coba_organdeadline;
		this.coba_orregtime = coba_orregtime;
		this.coba_organcode = coba_organcode;
		this.coba_ntbank = coba_ntbank;
		this.coba_ntaccount = coba_ntaccount;
		this.coba_ntid = coba_ntid;
		this.coba_ntregtime = coba_ntregtime;
		this.coba_ntlimit = coba_ntlimit;
		this.coba_ntdeadline = coba_ntdeadline;
		this.coba_ltregid = coba_ltregid;
		this.coba_ltregtime = coba_ltregtime;
		this.coba_ltlimit = coba_ltlimit;
		this.coba_ltstate = coba_ltstate;
		this.coba_ltid = coba_ltid;
		this.coba_ltdeadline = coba_ltdeadline;
		this.coba_ltbank = coba_ltbank;
		this.coba_ltaccount = coba_ltaccount;
		this.coba_gtstate = coba_gtstate;
		this.coba_gtbank = coba_gtbank;
		this.coba_gtacc = coba_gtacc;
		this.coba_gtto = coba_gtto;
		this.coba_gtdeadline = coba_gtdeadline;
		this.coba_sistate = coba_sistate;
		this.coba_siiilimit = coba_siiilimit;
		this.coba_sicoding = coba_sicoding;
		this.coba_sibank = coba_sibank;
		this.coba_siaccount = coba_siaccount;
		this.coba_sihospital = coba_sihospital;
		this.coba_sihosphone = coba_sihosphone;
		this.coba_sihosadd = coba_sihosadd;
		this.coba_dept = coba_dept;
		this.coba_regremark = coba_regremark;
		this.coba_addtime = coba_addtime;
		this.coba_addname = coba_addname;
		this.coba_modtime = coba_modtime;
		this.coba_modname = coba_modname;
		this.coba_shebaodeclare = coba_shebaodeclare;
		this.coba_ufid = coba_ufid;
		this.coba_ufid2 = coba_ufid2;
		this.coba_ufclass = coba_ufclass;
		this.coba_invoicerule = coba_invoicerule;
		this.coba_invoiceaddress = coba_invoiceaddress;
		this.coba_wtco = coba_wtco;
		this.coba_regdataList = coba_regdataList;
		this.coba_assistant = coba_assistant;
		this.coba_zytcid = coba_zytcid;
		this.coba_developer = coba_developer;
		this.coba_gzautoemail = coba_gzautoemail;
		this.coba_sign = coba_sign;
		this.coba_invoice = coba_invoice;
		this.coba_taxmonth = coba_taxmonth;
		this.coba_gzaddname = coba_gzaddname;
		this.coba_gzaudname = coba_gzaudname;
		this.coba_gzemailtype = coba_gzemailtype;
		this.coba_emfi_paydate = coba_emfi_paydate;
		this.coba_emfi_backdate = coba_emfi_backdate;
		this.coba_gz_paydate = coba_gz_paydate;
		this.coba_emailgz_paydate = coba_emailgz_paydate;
		this.coba_papergz_paydate = coba_papergz_paydate;
		this.coba_emfics_deldate = coba_emfics_deldate;
		this.coba_emfics_paydate = coba_emfics_paydate;
		this.coba_emfics_backdate = coba_emfics_backdate;
		this.coba_zytwtcid = coba_zytwtcid;
		this.coba_gzautoemaildays = coba_gzautoemaildays;
	}

	public CollectAmountNewModel getAmount() {
		return amount;
	}

	// 设置收款
	public void setAmount(List<CoFinanceCollectNewModel> amounts) {
		CollectAmountNewModel a = new CollectAmountNewModel();
		for (int i = 0; i < amounts.size(); i++) {
			if (a.getFileManageFee() != null
					& amounts.get(i).getFileManageFee() != null) {
				a.setFileManageFee(a.getFileManageFee().add(
						amounts.get(i).getFileManageFee()));
			} else {
				a.setFileManageFee(amounts.get(i).getFileManageFee());
			}
			if (a.getServiceFee() != null
					& amounts.get(i).getServiceFee() != null) {
				a.setServiceFee(a.getServiceFee().add(
						amounts.get(i).getServiceFee()));
			} else {
				a.setServiceFee(amounts.get(i).getServiceFee());
			}
			if (a.getSheBaoFee() != null
					& amounts.get(i).getSheBaoFee() != null) {
				a.setSheBaoFee(a.getSheBaoFee().add(
						amounts.get(i).getSheBaoFee()));
			} else {
				a.setSheBaoFee(amounts.get(i).getSheBaoFee());
			}
			if (a.getHouseGjj() != null & amounts.get(i).getHouseGjj() != null) {
				a.setHouseGjj(a.getHouseGjj().add(amounts.get(i).getHouseGjj()));
			} else {
				a.setHouseGjj(amounts.get(i).getHouseGjj());
			}
			if (a.getActivityFee() != null
					& amounts.get(i).getActivityFee() != null) {
				a.setActivityFee(a.getActivityFee().add(
						amounts.get(i).getActivityFee()));
			} else {
				a.setActivityFee(amounts.get(i).getActivityFee());
			}
			if (a.getBodyTestFee() != null
					& amounts.get(i).getBodyTestFee() != null) {
				a.setBodyTestFee(a.getBodyTestFee().add(
						amounts.get(i).getBodyTestFee()));
			} else {
				a.setBodyTestFee(amounts.get(i).getBodyTestFee());
			}
			if (a.getBusinessProtectFee() != null
					& amounts.get(i).getBusinessProtectFee() != null) {
				a.setBusinessProtectFee(a.getBusinessProtectFee().add(
						amounts.get(i).getBusinessProtectFee()));
			} else {
				a.setBusinessProtectFee(amounts.get(i).getBusinessProtectFee());
			}
			if (a.getBookFee() != null) {
				a.setBookFee(a.getBookFee().add(amounts.get(i).getBookFee()));
			} else {
				a.setBookFee(amounts.get(i).getBookFee());
			}
			if (a.getSalaryOfAfterTax() != null
					& amounts.get(i).getSalaryOfAfterTax() != null) {
				a.setSalaryOfAfterTax(a.getSalaryOfAfterTax().add(
						amounts.get(i).getSalaryOfAfterTax()));
			} else {
				a.setSalaryOfAfterTax(amounts.get(i).getSalaryOfAfterTax());
			}
			if (a.getoMoveFee() != null & amounts.get(i).getoMoveFee() != null) {
				a.setOMoveFee(a.getoMoveFee().add(amounts.get(i).getoMoveFee()));
			} else {
				a.setOMoveFee(amounts.get(i).getoMoveFee());
			}
			if (a.getoMoveFee() != null & amounts.get(i).getoMoveFee() != null) {
				a.setOMoveFee(a.getoMoveFee().add(amounts.get(i).getoMoveFee()));
			} else {
				a.setOMoveFee(amounts.get(i).getoMoveFee());
			}
			if (a.getHouseRestore() != null
					& amounts.get(i).getHouseRestore() != null) {
				a.setHouseRestore(a.getHouseRestore().add(
						amounts.get(i).getHouseRestore()));
			} else {
				a.setHouseRestore(amounts.get(i).getHouseRestore());
			}
			if (a.getFinanServiceFee() != null
					& amounts.get(i).getFinanServiceFee() != null) {
				a.setFinanServiceFee(a.getFinanServiceFee().add(
						amounts.get(i).getFinanServiceFee()));
			} else {
				a.setFinanServiceFee(amounts.get(i).getFinanServiceFee());
			}
			if (a.getBusinessServiceFee() != null
					& amounts.get(i).getBusinessServiceFee() != null) {
				a.setBusinessServiceFee(a.getBusinessServiceFee().add(
						amounts.get(i).getBusinessServiceFee()));
			} else {
				a.setBusinessServiceFee(amounts.get(i).getBusinessServiceFee());
			}
			if (a.getRecruitServiceFee() != null
					& amounts.get(i).getRecruitServiceFee() != null) {
				a.setRecruitServiceFee(a.getRecruitServiceFee().add(
						amounts.get(i).getRecruitServiceFee()));
			} else {
				a.setRecruitServiceFee(amounts.get(i).getRecruitServiceFee());
			}
			if (a.getDeformityFee() != null
					& amounts.get(i).getDeformityFee() != null) {
				a.setDeformityFee(a.getDeformityFee().add(
						amounts.get(i).getDeformityFee()));
			} else {
				a.setDeformityFee(amounts.get(i).getDeformityFee());
			}
			if (a.getResidencePermitFee() != null
					& amounts.get(i).getResidencePermitFee() != null) {
				a.setResidencePermitFee(a.getResidencePermitFee().add(
						amounts.get(i).getResidencePermitFee()));
			} else {
				a.setResidencePermitFee(amounts.get(i).getResidencePermitFee());
			}
			if (a.getLasscFee() != null & amounts.get(i).getLasscFee() != null) {
				a.setLasscFee(a.getLasscFee().add(amounts.get(i).getLasscFee()));
			} else {
				a.setLasscFee(amounts.get(i).getLasscFee());
			}
			if (a.getOther() != null & amounts.get(i).getOther() != null) {
				a.setOther(a.getOther().add(amounts.get(i).getOther()));
			} else {
				a.setOther(amounts.get(i).getOther());
			}
			if (a.getCfmb_TotalPaidIn() != null
					& amounts.get(i).getCfmb_TotalPaidIn() != null) {
				a.setCfmb_TotalPaidIn(a.getCfmb_TotalPaidIn().add(
						amounts.get(i).getCfmb_TotalPaidIn()));
			} else {
				a.setCfmb_TotalPaidIn(amounts.get(i).getCfmb_TotalPaidIn());
			}
			
			
			a.setOwnmonth(amounts.get(i).getOwnmonth());
		}
		this.amount = a;
	}

	public String getMsg_a() {
		return msg_a;
	}

	public void setMsg_a(String msg_a) {
		this.msg_a = msg_a;
	}

	public List<CoFinanceCollectModel> getCfcms() {
		return cfcms;
	}

	public void setCfcms(List<CoFinanceCollectModel> cfcms) {
		this.cfcms = cfcms;
	}

	public int getCoba_id() {
		return coba_id;
	}

	public void setCoba_id(int coba_id) {
		this.coba_id = coba_id;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getCoba_shortname() {
		return coba_shortname;
	}

	public void setCoba_shortname(String coba_shortname) {
		this.coba_shortname = coba_shortname;
	}

	public String getCoba_client() {
		return coba_client;
	}

	public void setCoba_client(String coba_client) {
		this.coba_client = coba_client;
	}

	public String getCoba_spell() {
		return coba_spell;
	}

	public void setCoba_spell(String coba_spell) {
		this.coba_spell = coba_spell;
	}

	public String getCoba_company() {
		return coba_company;
	}

	public void setCoba_company(String coba_company) {
		this.coba_company = coba_company;
	}

	public String getCoba_shortspell() {
		return coba_shortspell;
	}

	public void setCoba_shortspell(String coba_shortspell) {
		this.coba_shortspell = coba_shortspell;
	}

	public String getCoba_namespell() {
		return coba_namespell;
	}

	public void setCoba_namespell(String coba_namespell) {
		this.coba_namespell = coba_namespell;
	}

	public String getCoba_englishname() {
		return coba_englishname;
	}

	public void setCoba_englishname(String coba_englishname) {
		this.coba_englishname = coba_englishname;
	}

	public String getCoba_country() {
		return coba_country;
	}

	public void setCoba_country(String coba_country) {
		this.coba_country = coba_country;
	}

	public String getCoba_setuptype() {
		return coba_setuptype;
	}

	public void setCoba_setuptype(String coba_setuptype) {
		this.coba_setuptype = coba_setuptype;
	}

	public String getCoba_servicearea() {
		return coba_servicearea;
	}

	public void setCoba_servicearea(String coba_servicearea) {
		this.coba_servicearea = coba_servicearea;
	}

	public int getCoba_servicestate() {
		return coba_servicestate;
	}

	public void setCoba_servicestate(int coba_servicestate) {
		this.coba_servicestate = coba_servicestate;
	}

	public String getCoba_stopreason() {
		return coba_stopreason;
	}

	public void setCoba_stopreason(String coba_stopreason) {
		this.coba_stopreason = coba_stopreason;
	}

	public String getCoba_stoptime() {
		return coba_stoptime;
	}

	public void setCoba_stoptime(String coba_stoptime) {
		this.coba_stoptime = coba_stoptime;
	}

	public String getCoba_stopremark() {
		return coba_stopremark;
	}

	public void setCoba_stopremark(String coba_stopremark) {
		this.coba_stopremark = coba_stopremark;
	}

	public String getCoba_stopname() {
		return coba_stopname;
	}

	public void setCoba_stopname(String coba_stopname) {
		this.coba_stopname = coba_stopname;
	}

	public String getCoba_industytype() {
		return coba_industytype;
	}

	public void setCoba_industytype(String coba_industytype) {
		this.coba_industytype = coba_industytype;
	}

	public String getCoba_clientsize() {
		return coba_clientsize;
	}

	public void setCoba_clientsize(String coba_clientsize) {
		this.coba_clientsize = coba_clientsize;
	}

	public String getCoba_compacttype() {
		return coba_compacttype;
	}

	public void setCoba_compacttype(String coba_compacttype) {
		this.coba_compacttype = coba_compacttype;
	}

	public String getCoba_vip() {
		return coba_vip;
	}

	public void setCoba_vip(String coba_vip) {
		this.coba_vip = coba_vip;
	}

	public String getCoba_address() {
		return coba_address;
	}

	public void setCoba_address(String coba_address) {
		this.coba_address = coba_address;
	}

	public String getCoba_area() {
		return coba_area;
	}

	public void setCoba_area(String coba_area) {
		this.coba_area = coba_area;
	}

	public String getCoba_companymanager() {
		return coba_companymanager;
	}

	public void setCoba_companymanager(String coba_companymanager) {
		this.coba_companymanager = coba_companymanager;
	}

	public String getCoba_manageraddress() {
		return coba_manageraddress;
	}

	public void setCoba_manageraddress(String coba_manageraddress) {
		this.coba_manageraddress = coba_manageraddress;
	}

	public String getCoba_postcode() {
		return coba_postcode;
	}

	public void setCoba_postcode(String coba_postcode) {
		this.coba_postcode = coba_postcode;
	}

	public String getCoba_website() {
		return coba_website;
	}

	public void setCoba_website(String coba_website) {
		this.coba_website = coba_website;
	}

	public String getCoba_clientsource() {
		return coba_clientsource;
	}

	public void setCoba_clientsource(String coba_clientsource) {
		this.coba_clientsource = coba_clientsource;
	}

	public String getCoba_escrowarea() {
		return coba_escrowarea;
	}

	public void setCoba_escrowarea(String coba_escrowarea) {
		this.coba_escrowarea = coba_escrowarea;
	}

	public String getCoba_escrowcompany() {
		return coba_escrowcompany;
	}

	public void setCoba_escrowcompany(String coba_escrowcompany) {
		this.coba_escrowcompany = coba_escrowcompany;
	}

	public String getCoba_manager() {
		return coba_manager;
	}

	public void setCoba_manager(String coba_manager) {
		this.coba_manager = coba_manager;
	}

	public String getCoba_clientmanager() {
		return coba_clientmanager;
	}

	public void setCoba_clientmanager(String coba_clientmanager) {
		this.coba_clientmanager = coba_clientmanager;
	}

	public String getCoba_hzqsr() {
		return coba_hzqsr;
	}

	public void setCoba_hzqsr(String coba_hzqsr) {
		this.coba_hzqsr = coba_hzqsr;
	}

	public String getCoba_remark() {
		return coba_remark;
	}

	public void setCoba_remark(String coba_remark) {
		this.coba_remark = coba_remark;
	}

	public String getCoba_regagent() {
		return coba_regagent;
	}

	public void setCoba_regagent(String coba_regagent) {
		this.coba_regagent = coba_regagent;
	}

	public String getCoba_reg_fund() {
		return coba_reg_fund;
	}

	public void setCoba_reg_fund(String coba_reg_fund) {
		this.coba_reg_fund = coba_reg_fund;
	}

	public String getCoba_certificate() {
		return coba_certificate;
	}

	public void setCoba_certificate(String coba_certificate) {
		this.coba_certificate = coba_certificate;
	}

	public String getCoba_bregdeadline() {
		return coba_bregdeadline;
	}

	public void setCoba_bregdeadline(String coba_bregdeadline) {
		this.coba_bregdeadline = coba_bregdeadline;
	}

	public String getCoba_bregid() {
		return coba_bregid;
	}

	public void setCoba_bregid(String coba_bregid) {
		this.coba_bregid = coba_bregid;
	}

	public String getCoba_regexpires() {
		return coba_regexpires;
	}

	public void setCoba_regexpires(String coba_regexpires) {
		this.coba_regexpires = coba_regexpires;
	}

	public String getCoba_regtime() {
		return coba_regtime;
	}

	public void setCoba_regtime(String coba_regtime) {
		this.coba_regtime = coba_regtime;
	}

	public String getCoba_reglimit() {
		return coba_reglimit;
	}

	public void setCoba_reglimit(String coba_reglimit) {
		this.coba_reglimit = coba_reglimit;
	}

	public String getCoba_organdeadline() {
		return coba_organdeadline;
	}

	public void setCoba_organdeadline(String coba_organdeadline) {
		this.coba_organdeadline = coba_organdeadline;
	}

	public String getCoba_orregtime() {
		return coba_orregtime;
	}

	public void setCoba_orregtime(String coba_orregtime) {
		this.coba_orregtime = coba_orregtime;
	}

	public String getCoba_organcode() {
		return coba_organcode;
	}

	public void setCoba_organcode(String coba_organcode) {
		this.coba_organcode = coba_organcode;
	}

	public String getCoba_ntbank() {
		return coba_ntbank;
	}

	public void setCoba_ntbank(String coba_ntbank) {
		this.coba_ntbank = coba_ntbank;
	}

	public String getCoba_ntaccount() {
		return coba_ntaccount;
	}

	public void setCoba_ntaccount(String coba_ntaccount) {
		this.coba_ntaccount = coba_ntaccount;
	}

	public String getCoba_ntid() {
		return coba_ntid;
	}

	public void setCoba_ntid(String coba_ntid) {
		this.coba_ntid = coba_ntid;
	}

	public String getCoba_ntregtime() {
		return coba_ntregtime;
	}

	public void setCoba_ntregtime(String coba_ntregtime) {
		this.coba_ntregtime = coba_ntregtime;
	}

	public String getCoba_ntlimit() {
		return coba_ntlimit;
	}

	public void setCoba_ntlimit(String coba_ntlimit) {
		this.coba_ntlimit = coba_ntlimit;
	}

	public String getCoba_ntdeadline() {
		return coba_ntdeadline;
	}

	public void setCoba_ntdeadline(String coba_ntdeadline) {
		this.coba_ntdeadline = coba_ntdeadline;
	}

	public String getCoba_ltregid() {
		return coba_ltregid;
	}

	public void setCoba_ltregid(String coba_ltregid) {
		this.coba_ltregid = coba_ltregid;
	}

	public String getCoba_ltregtime() {
		return coba_ltregtime;
	}

	public void setCoba_ltregtime(String coba_ltregtime) {
		this.coba_ltregtime = coba_ltregtime;
	}

	public String getCoba_ltlimit() {
		return coba_ltlimit;
	}

	public void setCoba_ltlimit(String coba_ltlimit) {
		this.coba_ltlimit = coba_ltlimit;
	}

	public String getCoba_ltstate() {
		return coba_ltstate;
	}

	public void setCoba_ltstate(String coba_ltstate) {
		this.coba_ltstate = coba_ltstate;
	}

	public String getCoba_ltid() {
		return coba_ltid;
	}

	public void setCoba_ltid(String coba_ltid) {
		this.coba_ltid = coba_ltid;
	}

	public String getCoba_ltdeadline() {
		return coba_ltdeadline;
	}

	public void setCoba_ltdeadline(String coba_ltdeadline) {
		this.coba_ltdeadline = coba_ltdeadline;
	}

	public String getCoba_ltbank() {
		return coba_ltbank;
	}

	public void setCoba_ltbank(String coba_ltbank) {
		this.coba_ltbank = coba_ltbank;
	}

	public String getCoba_ltaccount() {
		return coba_ltaccount;
	}

	public void setCoba_ltaccount(String coba_ltaccount) {
		this.coba_ltaccount = coba_ltaccount;
	}

	public String getCoba_gtstate() {
		return coba_gtstate;
	}

	public void setCoba_gtstate(String coba_gtstate) {
		this.coba_gtstate = coba_gtstate;
	}

	public String getCoba_gtbank() {
		return coba_gtbank;
	}

	public void setCoba_gtbank(String coba_gtbank) {
		this.coba_gtbank = coba_gtbank;
	}

	public String getCoba_gtacc() {
		return coba_gtacc;
	}

	public void setCoba_gtacc(String coba_gtacc) {
		this.coba_gtacc = coba_gtacc;
	}

	public String getCoba_gtto() {
		return coba_gtto;
	}

	public void setCoba_gtto(String coba_gtto) {
		this.coba_gtto = coba_gtto;
	}

	public String getCoba_gtdeadline() {
		return coba_gtdeadline;
	}

	public void setCoba_gtdeadline(String coba_gtdeadline) {
		this.coba_gtdeadline = coba_gtdeadline;
	}

	public String getCoba_sistate() {
		return coba_sistate;
	}

	public void setCoba_sistate(String coba_sistate) {
		this.coba_sistate = coba_sistate;
	}

	public String getCoba_siiilimit() {
		return coba_siiilimit;
	}

	public void setCoba_siiilimit(String coba_siiilimit) {
		this.coba_siiilimit = coba_siiilimit;
	}

	public String getCoba_sicoding() {
		return coba_sicoding;
	}

	public void setCoba_sicoding(String coba_sicoding) {
		this.coba_sicoding = coba_sicoding;
	}

	public String getCoba_sibank() {
		return coba_sibank;
	}

	public void setCoba_sibank(String coba_sibank) {
		this.coba_sibank = coba_sibank;
	}

	public String getCoba_siaccount() {
		return coba_siaccount;
	}

	public void setCoba_siaccount(String coba_siaccount) {
		this.coba_siaccount = coba_siaccount;
	}

	public String getCoba_sihospital() {
		return coba_sihospital;
	}

	public void setCoba_sihospital(String coba_sihospital) {
		this.coba_sihospital = coba_sihospital;
	}

	public String getCoba_sihosphone() {
		return coba_sihosphone;
	}

	public void setCoba_sihosphone(String coba_sihosphone) {
		this.coba_sihosphone = coba_sihosphone;
	}

	public String getCoba_sihosadd() {
		return coba_sihosadd;
	}

	public void setCoba_sihosadd(String coba_sihosadd) {
		this.coba_sihosadd = coba_sihosadd;
	}

	public String getCoba_dept() {
		return coba_dept;
	}

	public void setCoba_dept(String coba_dept) {
		this.coba_dept = coba_dept;
	}

	public String getCoba_regremark() {
		return coba_regremark;
	}

	public void setCoba_regremark(String coba_regremark) {
		this.coba_regremark = coba_regremark;
	}

	public String getCoba_addtime() {
		return coba_addtime;
	}

	public void setCoba_addtime(String coba_addtime) {
		this.coba_addtime = coba_addtime;
	}

	public String getCoba_addname() {
		return coba_addname;
	}

	public void setCoba_addname(String coba_addname) {
		this.coba_addname = coba_addname;
	}

	public String getCoba_modtime() {
		return coba_modtime;
	}

	public void setCoba_modtime(String coba_modtime) {
		this.coba_modtime = coba_modtime;
	}

	public String getCoba_modname() {
		return coba_modname;
	}

	public void setCoba_modname(String coba_modname) {
		this.coba_modname = coba_modname;
	}

	public String getCoba_shebaodeclare() {
		return coba_shebaodeclare;
	}

	public void setCoba_shebaodeclare(String coba_shebaodeclare) {
		this.coba_shebaodeclare = coba_shebaodeclare;
	}

	public String getCoba_ufid() {
		return coba_ufid;
	}

	public void setCoba_ufid(String coba_ufid) {
		this.coba_ufid = coba_ufid;
	}

	public String getCoba_ufid2() {
		return coba_ufid2;
	}

	public void setCoba_ufid2(String coba_ufid2) {
		this.coba_ufid2 = coba_ufid2;
	}

	public String getCoba_ufclass() {
		return coba_ufclass;
	}

	public void setCoba_ufclass(String coba_ufclass) {
		if (coba_ufclass != null && coba_ufclass.equals("224105")) {
			setCoba_clientclass("AF");
		} else if (coba_ufclass != null && coba_ufclass.equals("224106")) {
			setCoba_clientclass("FS");
		} else {
			setCoba_clientclass("");
		}
		this.coba_ufclass = coba_ufclass;
	}

	public String getCoba_invoicerule() {
		return coba_invoicerule;
	}

	public void setCoba_invoicerule(String coba_invoicerule) {
		this.coba_invoicerule = coba_invoicerule;
	}

	public String getCoba_invoiceaddress() {
		return coba_invoiceaddress;
	}

	public void setCoba_invoiceaddress(String coba_invoiceaddress) {
		this.coba_invoiceaddress = coba_invoiceaddress;
	}

	public String getCoba_wtco() {
		return coba_wtco;
	}

	public void setCoba_wtco(String coba_wtco) {
		this.coba_wtco = coba_wtco;
	}

	public String getCoba_regdataList() {
		return coba_regdataList;
	}

	public void setCoba_regdataList(String coba_regdataList) {
		this.coba_regdataList = coba_regdataList;
	}

	public String getCoba_assistant() {
		return coba_assistant;
	}

	public void setCoba_assistant(String coba_assistant) {
		this.coba_assistant = coba_assistant;
	}

	public String getCoba_zytcid() {
		return coba_zytcid;
	}

	public void setCoba_zytcid(String coba_zytcid) {
		this.coba_zytcid = coba_zytcid;
	}

	public String getCoba_developer() {
		return coba_developer;
	}

	public void setCoba_developer(String coba_developer) {
		this.coba_developer = coba_developer;
	}

	public int getCoba_gzautoemail() {
		return coba_gzautoemail;
	}

	public void setCoba_gzautoemail(int coba_gzautoemail) {
		this.coba_gzautoemail = coba_gzautoemail;
	}

	public int getCoba_sign() {
		return coba_sign;
	}

	public void setCoba_sign(int coba_sign) {
		this.coba_sign = coba_sign;
	}

	public String getCoba_invoice() {
		return coba_invoice;
	}

	public void setCoba_invoice(String coba_invoice) {
		this.coba_invoice = coba_invoice;
	}

	public String getCoba_taxmonth() {
		return coba_taxmonth;
	}

	public void setCoba_taxmonth(String coba_taxmonth) {
		this.coba_taxmonth = coba_taxmonth;
	}

	public String getCoba_gzaddname() {
		return coba_gzaddname;
	}

	public void setCoba_gzaddname(String coba_gzaddname) {
		this.coba_gzaddname = coba_gzaddname;
	}

	public String getCoba_gzaudname() {
		return coba_gzaudname;
	}

	public String getCoba_fpremark() {
		return coba_fpremark;
	}

	public void setCoba_fpremark(String coba_fpremark) {
		this.coba_fpremark = coba_fpremark;
	}

	public void setCoba_gzaudname(String coba_gzaudname) {
		this.coba_gzaudname = coba_gzaudname;
	}

	public int getCoba_gzemailtype() {
		return coba_gzemailtype;
	}

	public void setCoba_gzemailtype(int coba_gzemailtype) {
		this.coba_gzemailtype = coba_gzemailtype;
	}

	public String getCoba_emfi_paydate() {
		return coba_emfi_paydate;
	}

	public void setCoba_emfi_paydate(String coba_emfi_paydate) {
		this.coba_emfi_paydate = coba_emfi_paydate;
	}

	public String getCoba_emfi_backdate() {
		return coba_emfi_backdate;
	}

	public void setCoba_emfi_backdate(String coba_emfi_backdate) {
		this.coba_emfi_backdate = coba_emfi_backdate;
	}

	public String getCoba_gz_paydate() {
		return coba_gz_paydate;
	}

	public void setCoba_gz_paydate(String coba_gz_paydate) {
		this.coba_gz_paydate = coba_gz_paydate;
	}

	public String getCoba_emailgz_paydate() {
		return coba_emailgz_paydate;
	}

	public void setCoba_emailgz_paydate(String coba_emailgz_paydate) {
		this.coba_emailgz_paydate = coba_emailgz_paydate;
	}

	public String getCoba_papergz_paydate() {
		return coba_papergz_paydate;
	}

	public void setCoba_papergz_paydate(String coba_papergz_paydate) {
		this.coba_papergz_paydate = coba_papergz_paydate;
	}

	public String getCoba_emfics_deldate() {
		return coba_emfics_deldate;
	}

	public void setCoba_emfics_deldate(String coba_emfics_deldate) {
		this.coba_emfics_deldate = coba_emfics_deldate;
	}

	public String getCoba_emfics_paydate() {
		return coba_emfics_paydate;
	}

	public void setCoba_emfics_paydate(String coba_emfics_paydate) {
		this.coba_emfics_paydate = coba_emfics_paydate;
	}

	public String getCoba_emfics_backdate() {
		return coba_emfics_backdate;
	}

	public void setCoba_emfics_backdate(String coba_emfics_backdate) {
		this.coba_emfics_backdate = coba_emfics_backdate;
	}

	public String getCoba_zytwtcid() {
		return coba_zytwtcid;
	}

	public void setCoba_zytwtcid(String coba_zytwtcid) {
		this.coba_zytwtcid = coba_zytwtcid;
	}

	public int getCoba_gzautoemaildays() {
		return coba_gzautoemaildays;
	}

	public void setCoba_gzautoemaildays(int coba_gzautoemaildays) {
		this.coba_gzautoemaildays = coba_gzautoemaildays;
	}

	public Integer getPeopnum() {
		return peopnum;
	}

	public void setPeopnum(Integer peopnum) {
		this.peopnum = peopnum;
	}

	public Integer getPronum() {
		return pronum;
	}

	public void setPronum(Integer pronum) {
		this.pronum = pronum;
	}

	public String getCoconum() {
		return coconum;
	}

	public void setCoconum(String coconum) {
		this.coconum = coconum;
	}

	public int getLog_id() {
		return log_id;
	}

	public void setLog_id(int log_id) {
		this.log_id = log_id;
	}

	public int getCou() {
		return cou;
	}

	public void setCou(int cou) {
		this.cou = cou;
	}

	public boolean isFuzzy() {
		return fuzzy;
	}

	public void setFuzzy(boolean fuzzy) {
		this.fuzzy = fuzzy;
	}

	public String getCoba_kind() {
		return coba_kind;
	}

	public void setCoba_kind(String coba_kind) {
		this.coba_kind = coba_kind;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCoba_ifhasbribery() {
		return coba_ifhasbribery;
	}

	public void setCoba_ifhasbribery(String coba_ifhasbribery) {
		this.coba_ifhasbribery = coba_ifhasbribery;
	}

	public String getCoba_regaccount() {
		return coba_regaccount;
	}

	public void setCoba_regaccount(String coba_regaccount) {
		this.coba_regaccount = coba_regaccount;
	}

	public String getCoba_regaccountpwd() {
		return coba_regaccountpwd;
	}

	public void setCoba_regaccountpwd(String coba_regaccountpwd) {
		this.coba_regaccountpwd = coba_regaccountpwd;
	}

	public void setAmount(CollectAmountNewModel amount) {
		this.amount = amount;
	}

	public Date getMakedate() {
		return makedate;
	}

	public void setMakedate(Date makedate) {
		this.makedate = makedate;
	}

	public String getMakenumber() {
		return makenumber;
	}

	public void setMakenumber(String makenumber) {
		this.makenumber = makenumber;
	}

	public String getZaiyao() {
		return zaiyao;
	}

	public void setZaiyao(String zaiyao) {
		this.zaiyao = zaiyao;
	}

	public String getCoba_clientclass() {
		return coba_clientclass;
	}

	public void setCoba_clientclass(String coba_clientclass) {
		this.coba_clientclass = coba_clientclass;
	}

	public int getGroup_count() {
		return group_count;
	}

	public void setGroup_count(int group_count) {
		this.group_count = group_count;
	}

	public int getGroup_firstrow() {
		return group_firstrow;
	}

	public void setGroup_firstrow(int group_firstrow) {
		this.group_firstrow = group_firstrow;
	}

	public int getGroup_order() {
		return group_order;
	}

	public void setGroup_order(int group_order) {
		this.group_order = group_order;
	}

	public String getCfmb_remark() {
		return cfmb_remark;
	}

	public void setCfmb_remark(String cfmb_remark) {
		this.cfmb_remark = cfmb_remark;
	}

	public String getCfta_addtime() {
		return cfta_addtime;
	}

	public void setCfta_addtime(String cfta_addtime) {
		this.cfta_addtime = cfta_addtime;
	}

	public Integer getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(Integer ownmonth) {
		this.ownmonth = ownmonth;
	}

	public String getCfmb_number() {
		return cfmb_number;
	}

	public void setCfmb_number(String cfmb_number) {
		this.cfmb_number = cfmb_number;
	}

	public String getOutstate() {
		return outstate;
	}

	public void setOutstate(String outstate) {
		this.outstate = outstate;
	}

	public String getCohf_houseid() {
		return cohf_houseid;
	}

	public void setCohf_houseid(String cohf_houseid) {
		this.cohf_houseid = cohf_houseid;
	}

	public String getCfss_cfso_id() {
		return cfss_cfso_id;
	}

	public void setCfss_cfso_id(String cfss_cfso_id) {
		this.cfss_cfso_id = cfss_cfso_id;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public String getCfss_type() {
		return cfss_type;
	}

	public void setCfss_type(String cfss_type) {
		this.cfss_type = cfss_type;
	}

	public String getCfss_fpstate() {
		return cfss_fpstate;
	}

	public void setCfss_fpstate(String cfss_fpstate) {
		this.cfss_fpstate = cfss_fpstate;
	}
	
	

}
