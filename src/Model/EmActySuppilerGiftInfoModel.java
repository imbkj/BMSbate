package Model;

import java.math.BigDecimal;

public class EmActySuppilerGiftInfoModel {

	// / gift_id
	private Integer gift_id;
	// / 供应商id
	private Integer gift_supid;
	// / gift_name
	private String gift_name;
	// / 品牌
	private String gift_brand;
	// / gift_color
	private String gift_color;
	// / 生产地
	private String gift_production;
	// / 类型
	private String gift_class;
	// / 进货数量
	private Integer gift_totalnum;
	// / 现在剩余数量
	private Integer gift_nownum;
	// / 购买礼品申请状态：0：未审核；1：已审核；2、购买中；3、已购买
	private Integer gift_state;
	// / gift_Addname
	private String gift_addname;
	// / gift_addtime
	private String gift_addtime;
	// / gift_remark
	private String gift_remark;
	// / 进货地址
	private String gift_inaddress;
	// / 原价
	private BigDecimal gift_price;
	// / 折扣价
	private BigDecimal gift_nowprice;
	// / 总价
	private BigDecimal gift_totalprice;
	// / 审核人
	private String gift_auditname;
	// / 审核时间
	private String gift_audittime;
	// / 购买人
	private String gift_buyname;
	// / 购买时间
	private String gift_buytime;
	// / 入库人
	private String gift_inname;
	// / 入库时间
	private String gift_intime;
	// / 任务单id
	private Integer gift_tarpid;
	// 状态
	private String statename;
	// 供应商
	private String supp_name;
	private Integer countnum, bnum;
	// / ownmonth
	private Integer ownmonth;
	// / gift_allprice
	private BigDecimal gift_allprice;
	// / 有效期
	private String gift_validdate;
	// / 发票日期
	private String gift_invoicedate, gift_invoiceupdate, gift_invoicename,gift_realinmoney,gift_realinname,gift_realintime;
	// / 发票号码
	private String gift_invoicenumber;
	// / 付款日期
	private String gift_paydate;
	private String gift_sortid;// 批量号
	private String gift_payname;
	private String gift_type;//福利类型
	private String gift_realpay;//实际付款
	private String gift_paytype;
	private BigDecimal gift_prepay;//预付款金额
	private String operatecon;
	private Integer gift_userhousenum;
	private Integer tostep;
	private String gift_projectname;
	
	public Integer getGift_id() {
		return gift_id;
	}

	public void setGift_id(Integer gift_id) {
		this.gift_id = gift_id;
	}

	public Integer getGift_supid() {
		return gift_supid;
	}

	public void setGift_supid(Integer gift_supid) {
		this.gift_supid = gift_supid;
	}

	public String getGift_name() {
		return gift_name;
	}

	public void setGift_name(String gift_name) {
		this.gift_name = gift_name;
	}

	public String getGift_brand() {
		return gift_brand;
	}

	public void setGift_brand(String gift_brand) {
		this.gift_brand = gift_brand;
	}

	public String getGift_color() {
		return gift_color;
	}

	public void setGift_color(String gift_color) {
		this.gift_color = gift_color;
	}

	public String getGift_production() {
		return gift_production;
	}

	public void setGift_production(String gift_production) {
		this.gift_production = gift_production;
	}

	public String getGift_class() {
		return gift_class;
	}

	public void setGift_class(String gift_class) {
		this.gift_class = gift_class;
	}

	public Integer getGift_totalnum() {
		return gift_totalnum;
	}

	public void setGift_totalnum(Integer gift_totalnum) {
		if(gift_totalnum==null)
		{
			gift_totalnum=0;
		}
		this.gift_totalnum = gift_totalnum;
	}

	public Integer getGift_nownum() {
		return gift_nownum;
	}

	public void setGift_nownum(Integer gift_nownum) {
		this.gift_nownum = gift_nownum;
	}

	public Integer getGift_state() {
		return gift_state;
	}

	public void setGift_state(Integer gift_state) {
		this.gift_state = gift_state;
	}

	public String getGift_addname() {
		return gift_addname;
	}

	public void setGift_addname(String gift_addname) {
		this.gift_addname = gift_addname;
	}

	public String getGift_addtime() {
		return gift_addtime;
	}

	public void setGift_addtime(String gift_addtime) {
		this.gift_addtime = gift_addtime;
	}

	public String getGift_remark() {
		return gift_remark;
	}

	public void setGift_remark(String gift_remark) {
		this.gift_remark = gift_remark;
	}

	public String getGift_inaddress() {
		return gift_inaddress;
	}

	public void setGift_inaddress(String gift_inaddress) {
		this.gift_inaddress = gift_inaddress;
	}

	public BigDecimal getGift_price() {
		return gift_price;
	}

	public void setGift_price(BigDecimal gift_price) {
		this.gift_price = gift_price;
	}

	public BigDecimal getGift_nowprice() {
		return gift_nowprice;
	}

	public void setGift_nowprice(BigDecimal gift_nowprice) {
		this.gift_nowprice = gift_nowprice;
	}

	public BigDecimal getGift_totalprice() {
		return gift_totalprice;
	}

	public void setGift_totalprice(BigDecimal gift_totalprice) {
		this.gift_totalprice = gift_totalprice;
	}

	public String getGift_auditname() {
		return gift_auditname;
	}

	public void setGift_auditname(String gift_auditname) {
		this.gift_auditname = gift_auditname;
	}

	public String getGift_audittime() {
		return gift_audittime;
	}

	public void setGift_audittime(String gift_audittime) {
		this.gift_audittime = gift_audittime;
	}

	public String getGift_buyname() {
		return gift_buyname;
	}

	public void setGift_buyname(String gift_buyname) {
		this.gift_buyname = gift_buyname;
	}

	public String getGift_buytime() {
		return gift_buytime;
	}

	public void setGift_buytime(String gift_buytime) {
		this.gift_buytime = gift_buytime;
	}

	public String getGift_inname() {
		return gift_inname;
	}

	public void setGift_inname(String gift_inname) {
		this.gift_inname = gift_inname;
	}

	public String getGift_intime() {
		return gift_intime;
	}

	public void setGift_intime(String gift_intime) {
		this.gift_intime = gift_intime;
	}

	public Integer getGift_tarpid() {
		return gift_tarpid;
	}

	public void setGift_tarpid(Integer gift_tarpid) {
		this.gift_tarpid = gift_tarpid;
	}

	public String getStatename() {
		return statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}

	public String getSupp_name() {
		return supp_name;
	}

	public void setSupp_name(String supp_name) {
		this.supp_name = supp_name;
	}

	public Integer getCountnum() {
		return countnum;
	}

	public void setCountnum(Integer countnum) {
		this.countnum = countnum;
	}

	public Integer getBnum() {
		return bnum;
	}

	public void setBnum(Integer bnum) {
		this.bnum = bnum;
	}

	public Integer getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(Integer ownmonth) {
		this.ownmonth = ownmonth;
	}

	public BigDecimal getGift_allprice() {
		return gift_allprice;
	}

	public void setGift_allprice(BigDecimal gift_allprice) {
		this.gift_allprice = gift_allprice;
	}

	public String getGift_validdate() {
		return gift_validdate;
	}

	public void setGift_validdate(String gift_validdate) {
		this.gift_validdate = gift_validdate;
	}

	public String getGift_invoicedate() {
		return gift_invoicedate;
	}

	public void setGift_invoicedate(String gift_invoicedate) {
		this.gift_invoicedate = gift_invoicedate;
	}



	public String getGift_invoiceupdate() {
		return gift_invoiceupdate;
	}

	public void setGift_invoiceupdate(String gift_invoiceupdate) {
		this.gift_invoiceupdate = gift_invoiceupdate;
	}

	public String getGift_invoicename() {
		return gift_invoicename;
	}

	public void setGift_invoicename(String gift_invoicename) {
		this.gift_invoicename = gift_invoicename;
	}

	public String getGift_invoicenumber() {
		return gift_invoicenumber;
	}

	public void setGift_invoicenumber(String gift_invoicenumber) {
		this.gift_invoicenumber = gift_invoicenumber;
	}

	public String getGift_paydate() {
		return gift_paydate;
	}

	public void setGift_paydate(String gift_paydate) {
		this.gift_paydate = gift_paydate;
	}

	public String getGift_sortid() {
		return gift_sortid;
	}

	public void setGift_sortid(String gift_sortid) {
		this.gift_sortid = gift_sortid;
	}

	public String getGift_payname() {
		return gift_payname;
	}

	public void setGift_payname(String gift_payname) {
		this.gift_payname = gift_payname;
	}

	public String getGift_type() {
		return gift_type;
	}

	public void setGift_type(String gift_type) {
		this.gift_type = gift_type;
	}

	public String getGift_realpay() {
		return gift_realpay;
	}

	public void setGift_realpay(String gift_realpay) {
		this.gift_realpay = gift_realpay;
	}

	public String getGift_realinmoney() {
		return gift_realinmoney;
	}

	public void setGift_realinmoney(String gift_realinmoney) {
		this.gift_realinmoney = gift_realinmoney;
	}

	public String getGift_realinname() {
		return gift_realinname;
	}

	public void setGift_realinname(String gift_realinname) {
		this.gift_realinname = gift_realinname;
	}

	public String getGift_realintime() {
		return gift_realintime;
	}

	public void setGift_realintime(String gift_realintime) {
		this.gift_realintime = gift_realintime;
	}

	public String getGift_paytype() {
		return gift_paytype;
	}

	public void setGift_paytype(String gift_paytype) {
		this.gift_paytype = gift_paytype;
	}

	public BigDecimal getGift_prepay() {
		return gift_prepay;
	}

	public void setGift_prepay(BigDecimal gift_prepay) {
		this.gift_prepay = gift_prepay;
	}

	public String getOperatecon() {
		return operatecon;
	}

	public void setOperatecon(String operatecon) {
		this.operatecon = operatecon;
	}

	public Integer getGift_userhousenum() {
		return gift_userhousenum;
	}

	public void setGift_userhousenum(Integer gift_userhousenum) {
		if(gift_userhousenum==null)
		{
			gift_userhousenum=0;
		}
		this.gift_userhousenum = gift_userhousenum;
	}

	public Integer getTostep() {
		return tostep;
	}

	public void setTostep(Integer tostep) {
		this.tostep = tostep;
	}

	public String getGift_projectname() {
		return gift_projectname;
	}

	public void setGift_projectname(String gift_projectname) {
		this.gift_projectname = gift_projectname;
	}
		
}
