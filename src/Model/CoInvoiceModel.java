package Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CoInvoiceModel {

	private Integer coin_id;
	private Integer coin_cfco_id;
	private String coin_invoiceid;
	private String coin_receiptid;
	private String coin_codeid;
	private BigDecimal coin_total;
	private String coin_title;
	private String coin_idate;
	private Date coin_iDate2;
	private String coin_itype;
	private String coin_iprint;
	private String coin_remark;
	private String coin_addtime;
	private String coin_addname;
	private Integer coin_state;
	private String coin_modtime;
	private String coin_modname;
	private int ownmonth;

	private String coba_company;
	private Integer cid;
	private String rule;
	private String industytype;
	private List<CoInvoiceInfoModel> list;
	private List<CoInvoiceRelationModel> list2;
	private String receivename;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public String getReceivename() {
		return receivename;
	}

	public void setReceivename(String receivename) {
		this.receivename = receivename;
	}

	public int getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(int ownmonth) {
		this.ownmonth = ownmonth;
	}

	public Integer getCoin_id() {
		return coin_id;
	}

	public void setCoin_id(Integer coin_id) {
		this.coin_id = coin_id;
	}

	public Integer getCoin_cfco_id() {
		return coin_cfco_id;
	}

	public void setCoin_cfco_id(Integer coin_cfco_id) {
		this.coin_cfco_id = coin_cfco_id;
	}

	public String getCoin_invoiceid() {
		return coin_invoiceid;
	}

	public void setCoin_invoiceid(String coin_invoiceid) {
		this.coin_invoiceid = coin_invoiceid;
	}

	public String getCoin_receiptid() {
		return coin_receiptid;
	}

	public void setCoin_receiptid(String coin_receiptid) {
		this.coin_receiptid = coin_receiptid;
	}

	public String getCoin_codeid() {
		return coin_codeid;
	}

	public void setCoin_codeid(String coin_codeid) {
		this.coin_codeid = coin_codeid;
	}

	public BigDecimal getCoin_total() {
		return coin_total;
	}

	public void setCoin_total(BigDecimal coin_total) {
		this.coin_total = coin_total.setScale(2,BigDecimal.ROUND_HALF_UP);
	}

	public String getCoin_title() {
		return coin_title;
	}

	public void setCoin_title(String coin_title) {
		
		this.coin_title = coin_title;
	}

	public String getCoin_idate() {
		return coin_idate;
	}

	public void setCoin_idate(String coin_idate) {
		this.coin_idate = coin_idate;
	}

	public String getCoin_itype() {
		return coin_itype;
	}

	public void setCoin_itype(String coin_itype) {
		this.coin_itype = coin_itype;
	}

	public String getCoin_iprint() {
		return coin_iprint;
	}

	public void setCoin_iprint(String coin_iprint) {
		this.coin_iprint = coin_iprint;
	}

	public String getCoin_remark() {
		return coin_remark;
	}

	public void setCoin_remark(String coin_remark) {
		this.coin_remark = coin_remark;
	}

	public String getCoin_addtime() {
		return coin_addtime;
	}

	public void setCoin_addtime(String coin_addtime) {
		this.coin_addtime = coin_addtime;
	}

	public String getCoin_addname() {
		return coin_addname;
	}

	public void setCoin_addname(String coin_addname) {
		this.coin_addname = coin_addname;
	}

	public Integer getCoin_state() {
		return coin_state;
	}

	public void setCoin_state(Integer coin_state) {
		this.coin_state = coin_state;
	}

	public String getCoba_company() {
		return coba_company;
	}

	public void setCoba_company(String coba_company) {
		this.coba_company = coba_company;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getIndustytype() {
		return industytype;
	}

	public void setIndustytype(String industytype) {
		this.industytype = industytype;
	}

	public Date getCoin_iDate2() {
		return coin_iDate2;
	}

	public void setCoin_iDate2(Date coin_iDate2) {
		this.coin_iDate2 = coin_iDate2;
	}

	public List<CoInvoiceInfoModel> getList() {
		return list;
	}

	public void setList(List<CoInvoiceInfoModel> list) {
		this.list = list;
	}

	public List<CoInvoiceRelationModel> getList2() {
		return list2;
	}

	public void setList2(List<CoInvoiceRelationModel> list2) {
		this.list2 = list2;
	}

	public String getCoin_modtime() {
		return coin_modtime;
	}

	public void setCoin_modtime(String coin_modtime) {
		this.coin_modtime = coin_modtime;
	}

	public String getCoin_modname() {
		return coin_modname;
	}

	public void setCoin_modname(String coin_modname) {
		this.coin_modname = coin_modname;
	}

}
