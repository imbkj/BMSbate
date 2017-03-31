package Model;

import java.math.BigDecimal;
import java.util.Date;

import Util.MonthListUtil;

public class GatherInfoModel {
	private Integer cid;
	private String coba_company;
	private String coba_shortname;
	private String makebilldate;
	private String proofclassword;//凭证类别字
	private String proofnumber="";//凭证号
	private String prooffdnum="2";//附单据数
	private String proofsummary;//摘要
	private String subjectnumber;//科目编码
	private BigDecimal borrowmoney=BigDecimal.ZERO;//金额借方
	private BigDecimal lendmoney;//金额贷方
	private BigDecimal cfsa_paidin;//实收金额
	private String number;//数量
	private String exchange;//外币
	private String exchangerate;//汇率
	private String makename;//制单人姓名
	private String ticketnumber;//票号
	private String ticketdate;//票号发生日期
	private String depnumber;//部门编码
	private String clientnumber;//客户编码
	private String projectnumber;//项目编码
	private Date makedate;
	private String cfsa_cfmb_number;//账单号
	private Integer ownmonth;
	private String coba_ufid;
	private String cfsa_cpac_name;
	private String coco_gzmonth;
	private String coba_ufclass;
	private Integer cpac_id;//科目表（CoPAccount）ID
	private String cpac_af,cpac_fs,cpac_affs,cpac_fsaf;
	private String cpac_name;//当前登陆人
	private Integer cfsa_id;
	private String coba_clientclass;
	private String cw_id="";
	private String cfss_cfso_id;
	private String cfss_type;
	private BigDecimal sumpin;
	
	
	
	
	public BigDecimal getSumpin() {
		return sumpin;
	}
	public void setSumpin(BigDecimal sumpin) {
		sumpin=sumpin.setScale(2, BigDecimal.ROUND_HALF_UP);
		this.sumpin = sumpin;
	}
	public String getCfss_type() {
		return cfss_type;
	}
	public void setCfss_type(String cfss_type) {
		this.cfss_type = cfss_type;
	}
	public String getMakebilldate() {
		return makebilldate;
	}
	public void setMakebilldate(String makebilldate) {
		this.makebilldate = makebilldate;
	}
	
	public String getCpac_fsaf() {
		return cpac_fsaf;
	}
	public void setCpac_fsaf(String cpac_fsaf) {
		this.cpac_fsaf = cpac_fsaf;
	}
	public String getProofclassword() {
		return proofclassword;
	}
	public void setProofclassword(String proofclassword) {
		this.proofclassword = proofclassword;
	}
	public String getProofnumber() {
		return proofnumber;
	}
	public void setProofnumber(String proofnumber) {
		if(proofnumber==null)
		{
			proofnumber="";
		}
		this.proofnumber = proofnumber;
	}
	public String getProoffdnum() {
		return prooffdnum;
	}
	
 
	public String getCpac_affs() {
		return cpac_affs;
	}
	public void setCpac_affs(String cpac_affs) {
		this.cpac_affs = cpac_affs;
	}
	public void setProoffdnum(String prooffdnum) {
		this.prooffdnum = prooffdnum;
	}
	public String getProofsummary() {
		return proofsummary;
	}
	public void setProofsummary(String proofsummary) {
		this.proofsummary = proofsummary;
	}
	public String getSubjectnumber() {
		return subjectnumber;
	}
	public void setSubjectnumber(String subjectnumber) {
		this.subjectnumber = subjectnumber;
	}
	
	public BigDecimal getBorrowmoney() {
		return borrowmoney;
	}
	public void setBorrowmoney(BigDecimal borrowmoney) {
		borrowmoney=borrowmoney.setScale(2, BigDecimal.ROUND_HALF_UP);
		this.borrowmoney = borrowmoney;
	}
	public BigDecimal getLendmoney() {
		return lendmoney;
	}
	public void setLendmoney(BigDecimal lendmoney) {
		lendmoney=lendmoney.setScale(2, BigDecimal.ROUND_HALF_UP);
		this.lendmoney = lendmoney;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getExchange() {
		return exchange;
	}
	public void setExchange(String exchange) {
		this.exchange = exchange;
	}
	public String getExchangerate() {
		return exchangerate;
	}
	public void setExchangerate(String exchangerate) {
		this.exchangerate = exchangerate;
	}
	public String getMakename() {
		return makename;
	}
	public void setMakename(String makename) {
		this.makename = makename;
	}
	public String getTicketnumber() {
		return ticketnumber;
	}
	public void setTicketnumber(String ticketnumber) {
		this.ticketnumber = ticketnumber;
	}
	public String getTicketdate() {
		return ticketdate;
	}
	public void setTicketdate(String ticketdate) {
		this.ticketdate = ticketdate;
	}
	public String getDepnumber() {
		return depnumber;
	}
	public void setDepnumber(String depnumber) {
		this.depnumber = depnumber;
	}
	public String getClientnumber() {
		return clientnumber;
	}
	public void setClientnumber(String clientnumber) {
		this.clientnumber = clientnumber;
	}
	public String getProjectnumber() {
		return projectnumber;
	}
	public void setProjectnumber(String projectnumber) {
		this.projectnumber = projectnumber;
	}
	public Date getMakedate() {
		return makedate;
	}
	public void setMakedate(Date makedate) {
		this.makedate = makedate;
	}
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public String getCoba_company() {
		return coba_company;
	}
	public void setCoba_company(String coba_company) {
		this.coba_company = coba_company;
	}
	public String getCfsa_cfmb_number() {
		return cfsa_cfmb_number;
	}
	public void setCfsa_cfmb_number(String cfsa_cfmb_number) {
		this.cfsa_cfmb_number = cfsa_cfmb_number;
	}
	public Integer getOwnmonth() {
		return ownmonth;
	}
	public void setOwnmonth(Integer ownmonth) {
		String month="";
		if(ownmonth!=null)
		{
			String ownmonthstr=ownmonth.toString();
			if(coco_gzmonth!=null)
			{
				MonthListUtil util=new MonthListUtil();
				if(coco_gzmonth.equals("上月"))//所属月份减一个月
				{
					ownmonthstr=util.getLastMonth(ownmonthstr);
				}
				else if(coco_gzmonth.equals("次月"))//所属月份加一个月
				{
					ownmonthstr=util.getNextMonth(ownmonthstr);
				}
			}
			if(ownmonthstr.length()>=6)
			{
				month=ownmonthstr.substring(4, 6);
			}
		}
		String cfsa_cpacname=cfsa_cpac_name;
		setProofsummary("收"+coba_shortname+month+"月"+cfsa_cpacname);
		this.ownmonth = ownmonth;
	}
	public String getCoba_shortname() {
		return coba_shortname;
	}
	public void setCoba_shortname(String coba_shortname) {
		this.coba_shortname = coba_shortname;
	}
	public String getCoba_ufid() {
		return coba_ufid;
	}
	public void setCoba_ufid(String coba_ufid) {
		this.coba_ufid = coba_ufid;
	}
	public String getCfsa_cpac_name() {
		return cfsa_cpac_name;
	}
	public void setCfsa_cpac_name(String cfsa_cpac_name) {
		this.cfsa_cpac_name = cfsa_cpac_name;
	}
	public String getCoco_gzmonth() {
		return coco_gzmonth;
	}
	public void setCoco_gzmonth(String coco_gzmonth) {
		this.coco_gzmonth = coco_gzmonth;
	}
	public String getCoba_ufclass() {
		return coba_ufclass;
	}
	public void setCoba_ufclass(String coba_ufclass) {
		
		if(cfss_type.equals("派遣"))
		{
					
			if(coba_ufclass!=null)
			{
				if(coba_ufclass.equals("224105"))//AF客户
				{
					if(cpac_id!=null&&(cpac_id==2||cpac_id==11||cpac_id==12||cpac_id==13))
					{
						setProoffdnum("2");
					}
					else
					{
						setProoffdnum("1");
					}
					setBorrowmoney(new BigDecimal("0"));
					setLendmoney(cfsa_paidin);
					setSubjectnumber(cpac_af);
					setCoba_clientclass("AF");
				}
				
				
				else   //FS客户
				{
					
					setBorrowmoney(new BigDecimal("0"));
					setLendmoney(cfsa_paidin);
					setSubjectnumber(cpac_fsaf);
					setCoba_clientclass("FS");
				}

			}
			
		}
		else if (cfss_type.equals("非派遣"))
		{
			if(coba_ufclass.equals("224105"))//AF客户
			{
				setBorrowmoney(new BigDecimal("0"));
				setLendmoney(cfsa_paidin);
				setSubjectnumber(cpac_affs);
				//setSubjectnumber(cpac_fs);
				setProoffdnum("2");
				setCoba_clientclass("AF");
			} else if (coba_ufclass.equals("224106"))//FS客户
			{
			
				setBorrowmoney(new BigDecimal("0"));
				setLendmoney(cfsa_paidin);
				//setSubjectnumber(cpac_af);
				setSubjectnumber(cpac_fs);
				setProoffdnum("2");
				setCoba_clientclass("FS");
			}
			
		
		}
		else
		{
			setBorrowmoney(new BigDecimal("0"));
			setLendmoney(cfsa_paidin);
			setSubjectnumber(cpac_af);
			setSubjectnumber(cpac_fs);
			setProoffdnum("2");
			setCoba_clientclass("FS");
		}
		
		
		
	
		this.coba_ufclass = coba_ufclass;
	}
	public Integer getCpac_id() {
		return cpac_id;
	}
	public void setCpac_id(Integer cpac_id) {
		this.cpac_id = cpac_id;
	}
	public String getCpac_af() {
		return cpac_af;
	}
	public void setCpac_af(String cpac_af) {
		this.cpac_af = cpac_af;
	}
	public String getCpac_fs() {
		return cpac_fs;
	}
	public void setCpac_fs(String cpac_fs) {
		this.cpac_fs = cpac_fs;
	}
	public BigDecimal getCfsa_paidin() {
		return cfsa_paidin;
	}
	public void setCfsa_paidin(BigDecimal cfsa_paidin) {
		cfsa_paidin=cfsa_paidin.setScale(2, BigDecimal.ROUND_HALF_UP);
		this.cfsa_paidin = cfsa_paidin;
	}
	public String getCpac_name() {
		return cpac_name;
	}
	public void setCpac_name(String cpac_name) {
		this.cpac_name = cpac_name;
	}
	public Integer getCfsa_id() {
		return cfsa_id;
	}
	public void setCfsa_id(Integer cfsa_id) {
		this.cfsa_id = cfsa_id;
	}
	public String getCoba_clientclass() {
		return coba_clientclass;
	}
	public void setCoba_clientclass(String coba_clientclass) {
		this.coba_clientclass = coba_clientclass;
	}
	public String getCw_id() {
		return cw_id;
	}
	public void setCw_id(String cw_id) {
		this.cw_id = cw_id;
	}
	public String getCfss_cfso_id() {
		return cfss_cfso_id;
	}
	public void setCfss_cfso_id(String cfss_cfso_id) {
		this.cfss_cfso_id = cfss_cfso_id;
	}
	
	
}
