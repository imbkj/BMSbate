package Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class EmFinanceZYTModel {
	private DecimalFormat myformat=new DecimalFormat("0.00");
	private String coba_company;
	private String coba_client;
	private Integer coco_id;
    /// id
    private Integer  id;
             /// zid
    private Integer  zid;
             /// gid
    private Integer  gid;
             /// cid
    private Integer  cid;
             /// scompany
    private String  scompany;
             /// sname
    private String  sname;
             /// scity
    private String  scity;
             /// rname
    private String  rname;
             /// rcity
    private String  rcity;
             /// rcompany
    private String  rcompany;
             /// ownmonth
    private Integer  ownmonth;
             /// emfz_company
    private String  emfz_company;
             /// emfz_name
    private String  emfz_name;
             /// emfz_idcard
    private String  emfz_idcard;
             /// emfz_yltotal
    private BigDecimal  emfz_yltotal = BigDecimal.ZERO;
             /// emfz_syetotal
    private BigDecimal  emfz_syetotal = BigDecimal.ZERO;
             /// emfz_gstotal
    private BigDecimal  emfz_gstotal = BigDecimal.ZERO;
             /// emfz_syutotal
    private BigDecimal  emfz_syutotal = BigDecimal.ZERO;
             /// emfz_jltotal
    private BigDecimal  emfz_jltotal = BigDecimal.ZERO;
             /// emfz_housetotal
    private BigDecimal  emfz_housetotal = BigDecimal.ZERO;
             /// emfz_zhtotal
    private BigDecimal  emfz_zhtotal = BigDecimal.ZERO;
             /// emfz_bjtotal
    private BigDecimal  emfz_bjtotal = BigDecimal.ZERO;
             /// emfz_sbtotal
    private BigDecimal emfz_sbtotal = BigDecimal.ZERO;
    private BigDecimal efsb_ylao= BigDecimal.ZERO;
    private BigDecimal efsb_yliao= BigDecimal.ZERO;
    private BigDecimal efsb_syu= BigDecimal.ZERO;
    private BigDecimal efsb_sye= BigDecimal.ZERO;
    private BigDecimal efsb_gs= BigDecimal.ZERO;
             /// emfz_elsefee
    private BigDecimal  emfz_elsefee = BigDecimal.ZERO;
    private BigDecimal emfz_other=BigDecimal.ZERO;
             /// emfz_sbchange
    private String  emfz_sbchange;
             /// emfz_serverid
    private String  emfz_serverid;
             /// emfz_servername
    private String  emfz_servername;
             /// emfz_serverfee
    private String  emfz_serverfee;
             /// emfz_servertotal
    private BigDecimal  emfz_servertotal;
             /// emfz_serverchange
    private String  emfz_serverchange;
             /// emfz_fee
    private BigDecimal  emfz_fee = BigDecimal.ZERO;
    private BigDecimal  emfz_feex = BigDecimal.ZERO;
    private BigDecimal  emfz_fee2 = BigDecimal.ZERO;
             /// emfz_filefee
    private BigDecimal  emfz_filefee = BigDecimal.ZERO;
             /// emfz_total
    private BigDecimal  emfz_total = BigDecimal.ZERO;
    private BigDecimal emfi_total = BigDecimal.ZERO;
    private BigDecimal emfi_emfz_total = BigDecimal.ZERO;
             /// emfz_remark
    private String  emfz_remark;
             /// emfz_ifinure
    private String  emfz_ifinure;
             /// emfz_addtime
    private String  emfz_addtime;
             /// emfz_ifconfirm
    private String  emfz_ifconfirm;
             /// emfz_confirmtime
    private String  emfz_confirmtime;
             /// emfz_confirmname
    private String  emfz_confirmname;
             /// emfz_city
    private String  emfz_city;
             /// emfz_sbstand
    private String  emfz_sbstand;
             /// emfz_sbstanename
    private String  emfz_sbstanename;
             /// emfz_ylcp
    private BigDecimal  emfz_ylcp;
             /// emfz_ylop
    private BigDecimal  emfz_ylop;
             /// emfz_jlcp
    private BigDecimal  emfz_jlcp;
             /// emfz_jlop
    private BigDecimal  emfz_jlop;
             /// emfz_gscp
    private BigDecimal  emfz_gscp;
             /// emfz_gsop
    private BigDecimal  emfz_gsop;
             /// emfz_syecp
    private BigDecimal  emfz_syecp;
             /// emfz_syeop
    private BigDecimal  emfz_syeop;
             /// emfz_syucp
    private BigDecimal  emfz_syucp;
             /// emfz_syuop
    private BigDecimal  emfz_syuop;
             /// emfz_housecp
    private BigDecimal  emfz_housecp;
             /// emfz_houseop
    private BigDecimal  emfz_houseop;
             /// emfz_zhcp
    private BigDecimal  emfz_zhcp;
             /// emfz_zhop
    private BigDecimal  emfz_zhop;
             /// emfz_bjcp
    private BigDecimal  emfz_bjcp;
             /// emfz_bjop
    private BigDecimal  emfz_bjop;
             /// emfz_ylradix
    private BigDecimal  emfz_ylradix;
             /// emfz_syeradix
    private BigDecimal  emfz_syeradix;
             /// emfz_gsradix
    private BigDecimal  emfz_gsradix;
             /// emfz_syuradix
    private BigDecimal  emfz_syuradix;
             /// emfz_jlradix
    private BigDecimal  emfz_jlradix;
             /// emfz_houseradix
    private BigDecimal  emfz_houseradix;
             /// emfz_zhradix
    private BigDecimal  emfz_zhradix;
             /// emfz_bjradix
    private BigDecimal  emfz_bjradix;
             /// emfz_fpfee
    private BigDecimal  emfz_fpfee;
             /// emfz_fptime
    private String  emfz_fptime;
             /// emfz_fpid
    private String  emfz_fpid;
             /// emfz_filename
    private String  emfz_filename;
             /// 对账确认状态
    private int  emfz_f_confirm;
             /// 对账确认时间
    private String  emfz_f_confirmtime;
             /// 对账确认人
    private String  emfz_f_confirmname;
             /// emfz_zgid
    private String  emfz_zgid;
    private BigDecimal total = BigDecimal.ZERO;;
    private String setup;
    private Integer stateid;
    
	private String name;//委托城市
	private String coab_name;//委托机构
	private Integer cocoid;//合同id
	private Integer cabc_id;//委托机构与城市关联id
	private String emba_name;
	private String emba_idcard;
	private BigDecimal fifztotal;//差额合计
	private BigDecimal fifztotals;//差额合计
	private Integer cfmb_PersonnelConfirm;
	
	 private BigDecimal  emfz_syetotal2 = BigDecimal.ZERO;
     
	 private BigDecimal  emfz_gstotal2 = BigDecimal.ZERO;
     
	 private BigDecimal  emfz_syutotal2 = BigDecimal.ZERO;
    
	 private BigDecimal  emfz_jltotal2 = BigDecimal.ZERO;
	
	private BigDecimal  emfz_yltotal2 = BigDecimal.ZERO;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getZid() {
		return zid;
	}
	public void setZid(Integer zid) {
		this.zid = zid;
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
	public String getScompany() {
		return scompany;
	}
	public void setScompany(String scompany) {
		this.scompany = scompany;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getScity() {
		return scity;
	}
	public void setScity(String scity) {
		this.scity = scity;
	}
	public String getRname() {
		return rname;
	}
	public void setRname(String rname) {
		this.rname = rname;
	}
	public String getRcity() {
		return rcity;
	}
	public void setRcity(String rcity) {
		this.rcity = rcity;
	}
	public String getRcompany() {
		return rcompany;
	}
	public void setRcompany(String rcompany) {
		this.rcompany = rcompany;
	}
	public Integer getOwnmonth() {
		return ownmonth;
	}
	public void setOwnmonth(Integer ownmonth) {
		this.ownmonth = ownmonth;
	}
	public String getEmfz_company() {
		return emfz_company;
	}
	public void setEmfz_company(String emfz_company) {
		this.emfz_company = emfz_company;
	}
	public String getEmfz_name() {
		return emfz_name;
	}
	public void setEmfz_name(String emfz_name) {
		this.emfz_name = emfz_name;
	}
	public String getEmfz_idcard() {
		return emfz_idcard;
	}
	public void setEmfz_idcard(String emfz_idcard) {
		this.emfz_idcard = emfz_idcard;
	}
	public BigDecimal getEmfz_yltotal() {
		return emfz_yltotal;
	}
	public void setEmfz_yltotal(BigDecimal emfz_yltotal) {
		this.emfz_yltotal = emfz_yltotal;
	}
	public BigDecimal getEmfz_syetotal() {
		return emfz_syetotal;
	}
	public void setEmfz_syetotal(BigDecimal emfz_syetotal) {
		this.emfz_syetotal = emfz_syetotal;
	}
	public BigDecimal getEmfz_gstotal() {
		return emfz_gstotal;
	}
	public void setEmfz_gstotal(BigDecimal emfz_gstotal) {
		this.emfz_gstotal = emfz_gstotal;
	}
	public BigDecimal getEmfz_syutotal() {
		return emfz_syutotal;
	}
	public void setEmfz_syutotal(BigDecimal emfz_syutotal) {
		this.emfz_syutotal = emfz_syutotal;
	}
	public BigDecimal getEmfz_jltotal() {
		return emfz_jltotal;
	}
	public void setEmfz_jltotal(BigDecimal emfz_jltotal) {
		this.emfz_jltotal = emfz_jltotal;
	}
	public BigDecimal getEmfz_housetotal() {
		return emfz_housetotal;
	}
	public void setEmfz_housetotal(BigDecimal emfz_housetotal) {
		this.emfz_housetotal = emfz_housetotal;
	}
	public BigDecimal getEmfz_zhtotal() {
		return emfz_zhtotal;
	}
	public void setEmfz_zhtotal(BigDecimal emfz_zhtotal) {
		this.emfz_zhtotal = emfz_zhtotal;
	}
	public BigDecimal getEmfz_bjtotal() {
		return emfz_bjtotal;
	}
	public void setEmfz_bjtotal(BigDecimal emfz_bjtotal) {
		this.emfz_bjtotal = emfz_bjtotal;
	}
	public BigDecimal getEmfz_sbtotal() {
		return emfz_sbtotal;
	}
	public void setEmfz_sbtotal(BigDecimal emfz_sbtotal) {
		this.emfz_sbtotal = emfz_sbtotal;
	}
	public BigDecimal getEmfz_elsefee() {
		return emfz_elsefee;
	}
	public void setEmfz_elsefee(BigDecimal emfz_elsefee) {
		this.emfz_elsefee = emfz_elsefee;
	}
	public String getEmfz_sbchange() {
		return emfz_sbchange;
	}
	public void setEmfz_sbchange(String emfz_sbchange) {
		this.emfz_sbchange = emfz_sbchange;
	}
	public String getEmfz_serverid() {
		return emfz_serverid;
	}
	public void setEmfz_serverid(String emfz_serverid) {
		this.emfz_serverid = emfz_serverid;
	}
	public String getEmfz_servername() {
		return emfz_servername;
	}
	public void setEmfz_servername(String emfz_servername) {
		this.emfz_servername = emfz_servername;
	}
	public String getEmfz_serverfee() {
		return emfz_serverfee;
	}
	public void setEmfz_serverfee(String emfz_serverfee) {
		this.emfz_serverfee = emfz_serverfee;
	}
	public BigDecimal getEmfz_servertotal() {
		return emfz_servertotal;
	}
	public void setEmfz_servertotal(BigDecimal emfz_servertotal) {
		this.emfz_servertotal = emfz_servertotal;
	}
	public String getEmfz_serverchange() {
		return emfz_serverchange;
	}
	public void setEmfz_serverchange(String emfz_serverchange) {
		this.emfz_serverchange = emfz_serverchange;
	}
	public BigDecimal getEmfz_fee() {
		return emfz_fee;
	}
	public void setEmfz_fee(BigDecimal emfz_fee) {
		this.emfz_fee = emfz_fee;
	}
	public BigDecimal getEmfz_filefee() {
		return emfz_filefee;
	}
	public void setEmfz_filefee(BigDecimal emfz_filefee) {
		this.emfz_filefee = emfz_filefee;
	}
	public BigDecimal getEmfz_total() {
		return emfz_total;
	}
	public void setEmfz_total(BigDecimal emfz_total) {
		this.emfz_total = emfz_total;
	}
	public String getEmfz_remark() {
		return emfz_remark;
	}
	public void setEmfz_remark(String emfz_remark) {
		this.emfz_remark = emfz_remark;
	}
	public String getEmfz_ifinure() {
		return emfz_ifinure;
	}
	public void setEmfz_ifinure(String emfz_ifinure) {
		this.emfz_ifinure = emfz_ifinure;
	}
	public String getEmfz_addtime() {
		return emfz_addtime;
	}
	public void setEmfz_addtime(String emfz_addtime) {
		this.emfz_addtime = emfz_addtime;
	}
	public String getEmfz_ifconfirm() {
		return emfz_ifconfirm;
	}
	public void setEmfz_ifconfirm(String emfz_ifconfirm) {
		this.emfz_ifconfirm = emfz_ifconfirm;
	}
	public String getEmfz_confirmtime() {
		return emfz_confirmtime;
	}
	public void setEmfz_confirmtime(String emfz_confirmtime) {
		this.emfz_confirmtime = emfz_confirmtime;
	}
	public String getEmfz_confirmname() {
		return emfz_confirmname;
	}
	public void setEmfz_confirmname(String emfz_confirmname) {
		this.emfz_confirmname = emfz_confirmname;
	}
	public String getEmfz_city() {
		return emfz_city;
	}
	public void setEmfz_city(String emfz_city) {
		this.emfz_city = emfz_city;
	}
	public String getEmfz_sbstand() {
		return emfz_sbstand;
	}
	public void setEmfz_sbstand(String emfz_sbstand) {
		this.emfz_sbstand = emfz_sbstand;
	}
	public String getEmfz_sbstanename() {
		return emfz_sbstanename;
	}
	public void setEmfz_sbstanename(String emfz_sbstanename) {
		this.emfz_sbstanename = emfz_sbstanename;
	}
	public BigDecimal getEmfz_ylcp() {
		return emfz_ylcp;
	}
	public void setEmfz_ylcp(BigDecimal emfz_ylcp) {
		this.emfz_ylcp = emfz_ylcp;
	}
	public BigDecimal getEmfz_ylop() {
		return emfz_ylop;
	}
	public void setEmfz_ylop(BigDecimal emfz_ylop) {
		this.emfz_ylop = emfz_ylop;
	}
	public BigDecimal getEmfz_jlcp() {
		return emfz_jlcp;
	}
	public void setEmfz_jlcp(BigDecimal emfz_jlcp) {
		this.emfz_jlcp = emfz_jlcp;
	}
	public BigDecimal getEmfz_jlop() {
		return emfz_jlop;
	}
	public void setEmfz_jlop(BigDecimal emfz_jlop) {
		this.emfz_jlop = emfz_jlop;
	}
	public BigDecimal getEmfz_gscp() {
		return emfz_gscp;
	}
	public void setEmfz_gscp(BigDecimal emfz_gscp) {
		this.emfz_gscp = emfz_gscp;
	}
	public BigDecimal getEmfz_gsop() {
		return emfz_gsop;
	}
	public void setEmfz_gsop(BigDecimal emfz_gsop) {
		this.emfz_gsop = emfz_gsop;
	}
	public BigDecimal getEmfz_syecp() {
		return emfz_syecp;
	}
	public void setEmfz_syecp(BigDecimal emfz_syecp) {
		this.emfz_syecp = emfz_syecp;
	}
	public BigDecimal getEmfz_syeop() {
		return emfz_syeop;
	}
	public void setEmfz_syeop(BigDecimal emfz_syeop) {
		this.emfz_syeop = emfz_syeop;
	}
	public BigDecimal getEmfz_syucp() {
		return emfz_syucp;
	}
	public void setEmfz_syucp(BigDecimal emfz_syucp) {
		this.emfz_syucp = emfz_syucp;
	}
	public BigDecimal getEmfz_syuop() {
		return emfz_syuop;
	}
	public void setEmfz_syuop(BigDecimal emfz_syuop) {
		this.emfz_syuop = emfz_syuop;
	}
	public BigDecimal getEmfz_housecp() {
		return emfz_housecp;
	}
	public void setEmfz_housecp(BigDecimal emfz_housecp) {
		this.emfz_housecp = emfz_housecp;
	}
	public BigDecimal getEmfz_houseop() {
		return emfz_houseop;
	}
	public void setEmfz_houseop(BigDecimal emfz_houseop) {
		this.emfz_houseop = emfz_houseop;
	}
	public BigDecimal getEmfz_zhcp() {
		return emfz_zhcp;
	}
	public void setEmfz_zhcp(BigDecimal emfz_zhcp) {
		this.emfz_zhcp = emfz_zhcp;
	}
	public BigDecimal getEmfz_zhop() {
		return emfz_zhop;
	}
	public void setEmfz_zhop(BigDecimal emfz_zhop) {
		this.emfz_zhop = emfz_zhop;
	}
	public BigDecimal getEmfz_bjcp() {
		return emfz_bjcp;
	}
	public void setEmfz_bjcp(BigDecimal emfz_bjcp) {
		this.emfz_bjcp = emfz_bjcp;
	}
	public BigDecimal getEmfz_bjop() {
		return emfz_bjop;
	}
	public void setEmfz_bjop(BigDecimal emfz_bjop) {
		this.emfz_bjop = emfz_bjop;
	}
	public BigDecimal getEmfz_ylradix() {
		return emfz_ylradix;
	}
	public void setEmfz_ylradix(BigDecimal emfz_ylradix) {
		this.emfz_ylradix = emfz_ylradix;
	}
	public BigDecimal getEmfz_syeradix() {
		return emfz_syeradix;
	}
	public void setEmfz_syeradix(BigDecimal emfz_syeradix) {
		this.emfz_syeradix = emfz_syeradix;
	}
	public BigDecimal getEmfz_gsradix() {
		return emfz_gsradix;
	}
	public void setEmfz_gsradix(BigDecimal emfz_gsradix) {
		this.emfz_gsradix = emfz_gsradix;
	}
	public BigDecimal getEmfz_syuradix() {
		return emfz_syuradix;
	}
	public void setEmfz_syuradix(BigDecimal emfz_syuradix) {
		this.emfz_syuradix = emfz_syuradix;
	}
	public BigDecimal getEmfz_jlradix() {
		return emfz_jlradix;
	}
	public void setEmfz_jlradix(BigDecimal emfz_jlradix) {
		this.emfz_jlradix = emfz_jlradix;
	}
	public BigDecimal getEmfz_houseradix() {
		return emfz_houseradix;
	}
	public void setEmfz_houseradix(BigDecimal emfz_houseradix) {
		this.emfz_houseradix = emfz_houseradix;
	}
	public BigDecimal getEmfz_zhradix() {
		return emfz_zhradix;
	}
	public void setEmfz_zhradix(BigDecimal emfz_zhradix) {
		this.emfz_zhradix = emfz_zhradix;
	}
	public BigDecimal getEmfz_bjradix() {
		return emfz_bjradix;
	}
	public void setEmfz_bjradix(BigDecimal emfz_bjradix) {
		this.emfz_bjradix = emfz_bjradix;
	}
	public BigDecimal getEmfz_fpfee() {
		return emfz_fpfee;
	}
	public void setEmfz_fpfee(BigDecimal emfz_fpfee) {
		this.emfz_fpfee = emfz_fpfee;
	}
	public String getEmfz_fptime() {
		return emfz_fptime;
	}
	public void setEmfz_fptime(String emfz_fptime) {
		this.emfz_fptime = emfz_fptime;
	}
	public String getEmfz_fpid() {
		return emfz_fpid;
	}
	public void setEmfz_fpid(String emfz_fpid) {
		this.emfz_fpid = emfz_fpid;
	}
	public String getEmfz_filename() {
		return emfz_filename;
	}
	public void setEmfz_filename(String emfz_filename) {
		this.emfz_filename = emfz_filename;
	}
	public int getEmfz_f_confirm() {
		return emfz_f_confirm;
	}
	public void setEmfz_f_confirm(int emfz_f_confirm) {
		this.emfz_f_confirm = emfz_f_confirm;
	}
	public String getEmfz_f_confirmtime() {
		return emfz_f_confirmtime;
	}
	public void setEmfz_f_confirmtime(String emfz_f_confirmtime) {
		this.emfz_f_confirmtime = emfz_f_confirmtime;
	}
	public String getEmfz_f_confirmname() {
		return emfz_f_confirmname;
	}
	public void setEmfz_f_confirmname(String emfz_f_confirmname) {
		this.emfz_f_confirmname = emfz_f_confirmname;
	}
	public String getEmfz_zgid() {
		return emfz_zgid;
	}
	public void setEmfz_zgid(String emfz_zgid) {
		this.emfz_zgid = emfz_zgid;
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
	public Integer getCoco_id() {
		return coco_id;
	}
	public void setCoco_id(Integer coco_id) {
		this.coco_id = coco_id;
	}
	public BigDecimal getEmfi_total() {
		return emfi_total;
	}
	public void setEmfi_total(BigDecimal emfi_total) {
		emfi_total =new BigDecimal(myformat
				.format(emfi_total).toString());
		this.emfi_total = emfi_total;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		total =new BigDecimal(myformat
				.format(total).toString());
		this.total = total;
	}
	public String getSetup() {
		return setup;
	}
	public void setSetup(String setup) {
		this.setup = setup;
	}
	public Integer getStateid() {
		return stateid;
	}
	public void setStateid(Integer stateid) {
		this.stateid = stateid;
	}
	public String getCoab_name() {
		return coab_name;
	}
	public void setCoab_name(String coab_name) {
		this.coab_name = coab_name;
	}
	public Integer getCocoid() {
		return cocoid;
	}
	public void setCocoid(Integer cocoid) {
		this.cocoid = cocoid;
	}
	public Integer getCabc_id() {
		return cabc_id;
	}
	public void setCabc_id(Integer cabc_id) {
		this.cabc_id = cabc_id;
	}
	public String getEmba_name() {
		return emba_name;
	}
	public void setEmba_name(String emba_name) {
		this.emba_name = emba_name;
	}
	public String getEmba_idcard() {
		return emba_idcard;
	}
	public void setEmba_idcard(String emba_idcard) {
		this.emba_idcard = emba_idcard;
	}
	public BigDecimal getFifztotal() {
		return fifztotal;
	}
	public void setFifztotal(BigDecimal fifztotal) {
		this.fifztotal = fifztotal;
	}
	public Integer getCfmb_PersonnelConfirm() {
		return cfmb_PersonnelConfirm;
	}
	public void setCfmb_PersonnelConfirm(Integer cfmb_PersonnelConfirm) {
		this.cfmb_PersonnelConfirm = cfmb_PersonnelConfirm;
	}
	public BigDecimal getFifztotals() {
		return fifztotals;
	}
	public void setFifztotals(BigDecimal fifztotals) {
		this.fifztotals = fifztotals;
	}
	public BigDecimal getEmfz_other() {
		return emfz_other;
	}
	public void setEmfz_other(BigDecimal emfz_other) {
		this.emfz_other = emfz_other;
	}
	public DecimalFormat getMyformat() {
		return myformat;
	}
	public void setMyformat(DecimalFormat myformat) {
		this.myformat = myformat;
	}
	public BigDecimal getEfsb_ylao() {
		return efsb_ylao;
	}
	public void setEfsb_ylao(BigDecimal efsb_ylao) {
		this.efsb_ylao = efsb_ylao;
	}
	public BigDecimal getEfsb_yliao() {
		return efsb_yliao;
	}
	public void setEfsb_yliao(BigDecimal efsb_yliao) {
		this.efsb_yliao = efsb_yliao;
	}
	public BigDecimal getEfsb_syu() {
		return efsb_syu;
	}
	public void setEfsb_syu(BigDecimal efsb_syu) {
		this.efsb_syu = efsb_syu;
	}
	public BigDecimal getEfsb_sye() {
		return efsb_sye;
	}
	public void setEfsb_sye(BigDecimal efsb_sye) {
		this.efsb_sye = efsb_sye;
	}
	public BigDecimal getEfsb_gs() {
		return efsb_gs;
	}
	public void setEfsb_gs(BigDecimal efsb_gs) {
		this.efsb_gs = efsb_gs;
	}
	public BigDecimal getEmfz_syetotal2() {
		return emfz_syetotal2;
	}
	public void setEmfz_syetotal2(BigDecimal emfz_syetotal2) {
		this.emfz_syetotal2 = emfz_syetotal2;
	}
	public BigDecimal getEmfz_gstotal2() {
		return emfz_gstotal2;
	}
	public void setEmfz_gstotal2(BigDecimal emfz_gstotal2) {
		this.emfz_gstotal2 = emfz_gstotal2;
	}
	public BigDecimal getEmfz_syutotal2() {
		return emfz_syutotal2;
	}
	public void setEmfz_syutotal2(BigDecimal emfz_syutotal2) {
		this.emfz_syutotal2 = emfz_syutotal2;
	}
	public BigDecimal getEmfz_jltotal2() {
		return emfz_jltotal2;
	}
	public void setEmfz_jltotal2(BigDecimal emfz_jltotal2) {
		this.emfz_jltotal2 = emfz_jltotal2;
	}
	public BigDecimal getEmfz_yltotal2() {
		return emfz_yltotal2;
	}
	public void setEmfz_yltotal2(BigDecimal emfz_yltotal2) {
		this.emfz_yltotal2 = emfz_yltotal2;
	}
	public BigDecimal getEmfi_emfz_total() {
		return emfi_emfz_total;
	}
	public void setEmfi_emfz_total(BigDecimal emfi_emfz_total) {
		this.emfi_emfz_total = emfi_emfz_total;
	}
	public BigDecimal getEmfz_feex() {
		return emfz_feex;
	}
	public void setEmfz_feex(BigDecimal emfz_feex) {
		this.emfz_feex = emfz_feex;
	}
	public BigDecimal getEmfz_fee2() {
		return emfz_fee2;
	}
	public void setEmfz_fee2(BigDecimal emfz_fee2) {
		this.emfz_fee2 = emfz_fee2;
	}
    
    
}
