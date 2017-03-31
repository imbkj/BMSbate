package Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

public class EmWelfareModel {
	private DecimalFormat myformat = new DecimalFormat("0.00");
	private Integer emwf_id;
	// / cid
	private Integer cid;
	// / gid
	private Integer gid;
	private Integer emwf_embf_id;
	private Integer emwf_gift_id;
	private String productName;
	private String suppName;
	// / emwf_company
	private String emwf_company;
	// / emwf_name
	private String emwf_name;
	// / emwf_idcard
	private String emwf_idcard;

	private String embf_name;
	private String emwf_content;
	// / emwf_paykind
	private String emwf_paykind;
	// / emwf_delivery
	private String emwf_delivery;
	private String emwf_intime;
	// / emwf_charge
	private BigDecimal emwf_charge, prod_discountprice, prod_price;
	private String emwf_unit;
	private String emwf_need;
	private String emwf_family;
	private String emwf_standard;
	private Integer emwf_amount;
	private String emwf_dept;
	private String emwf_client;
	private BigDecimal emwf_truecharge;
	// / emwf_signtime
	private String emwf_signtime;
	// / emwf_signname
	private String emwf_signname;
	private String emwf_signState;
	// / emwf_state
	private Integer emwf_state;
	// / emwf_addtime
	private String emwf_addtime;
	// / emwf_addname
	private String emwf_addname;

	private Integer emwf_tapr_id;
	private String emwf_modtime;

	private String embf_mold;
	private String emwf_sortid;
	private Integer num;
	private String emwf_takeclient;
	private String emwf_linktime;
	private String emwf_statename;
	private String emwf_linkcontent, emwf_sendname, emwf_taketime,
			emwf_sendtime, emwf_signclient, emwf_clientsigntime;
	private List<EmWelfareModel> list;
	private boolean checked;
	private Integer ownmonth;
	private String prod_name;
	private String emba_birth;
	private Integer prod_id;
	private Integer oldnum;
	private BigDecimal allcharge;
	private String emwf_backcase;

	// 联系人信息
	private Integer linkId;
	private String linkman;
	private String mobile;
	private String address;
	private String emwf_remark;
	private Integer emwf_prty_id;
	private Integer emwf_prod_id;
	private Integer emwf_producenum;
	private String emwf_produce;
	private String emwf_producefo;
	private String coba_shortname;
	private Integer emwf_num;
	private String prty_name; 
	private String prod_unit;
	private BigDecimal prod_discount;
	private BigDecimal emwf_price;
	private BigDecimal emwf_preprice;
	private BigDecimal emwf_payprice;
	private String emwf_prepaynum;
	private String emwf_prodcontent;
	private int useh_num;
	private BigDecimal useh_price;
	private BigDecimal emwf_buyprice;
	private int buy_num;

	private String idList;

	public Integer getEmwf_id() {
		return emwf_id;
	}

	public void setEmwf_id(Integer emwf_id) {
		this.emwf_id = emwf_id;
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

	public String getEmwf_company() {
		return emwf_company;
	}

	public void setEmwf_company(String emwf_company) {
		this.emwf_company = emwf_company;
	}

	public String getEmwf_name() {
		return emwf_name;
	}

	public void setEmwf_name(String emwf_name) {
		this.emwf_name = emwf_name;
	}

	public String getEmwf_idcard() {
		return emwf_idcard;
	}

	public void setEmwf_idcard(String emwf_idcard) {
		this.emwf_idcard = emwf_idcard;
	}

	public String getEmwf_paykind() {
		return emwf_paykind;
	}

	public void setEmwf_paykind(String emwf_paykind) {
		this.emwf_paykind = emwf_paykind;
	}

	public String getEmwf_delivery() {
		return emwf_delivery;
	}

	public void setEmwf_delivery(String emwf_delivery) {
		this.emwf_delivery = emwf_delivery;
	}

	public BigDecimal getEmwf_charge() {
		return emwf_charge;
	}

	public void setEmwf_charge(BigDecimal emwf_charge) {
		if (emwf_charge != null) {
			this.emwf_charge = emwf_charge
					.setScale(2, BigDecimal.ROUND_HALF_UP);
		}
	}

	public String getEmwf_signtime() {
		return emwf_signtime;
	}

	public void setEmwf_signtime(String emwf_signtime) {
		this.emwf_signtime = emwf_signtime;
	}

	public String getEmwf_signname() {
		return emwf_signname;
	}

	public void setEmwf_signname(String emwf_signname) {
		this.emwf_signname = emwf_signname;
	}

	public Integer getEmwf_state() {
		return emwf_state;
	}

	public void setEmwf_state(Integer emwf_state) {
		this.emwf_state = emwf_state;
	}

	public String getEmwf_addtime() {
		return emwf_addtime;
	}

	public void setEmwf_addtime(String emwf_addtime) {
		this.emwf_addtime = emwf_addtime;
	}

	public String getEmwf_addname() {
		return emwf_addname;
	}

	public void setEmwf_addname(String emwf_addname) {
		this.emwf_addname = emwf_addname;
	}

	public String getEmwf_content() {
		return emwf_content;
	}

	public void setEmwf_content(String emwf_content) {
		this.emwf_content = emwf_content;
	}

	public String getEmwf_signState() {
		return emwf_signState;
	}

	public void setEmwf_signState(String emwf_signState) {
		this.emwf_signState = emwf_signState;
	}

	public String getEmbf_name() {
		return embf_name;
	}

	public void setEmbf_name(String embf_name) {
		this.embf_name = embf_name;
	}

	public Integer getEmwf_tapr_id() {
		return emwf_tapr_id;
	}

	public void setEmwf_tapr_id(Integer emwf_tapr_id) {
		this.emwf_tapr_id = emwf_tapr_id;
	}

	public Integer getEmwf_embf_id() {
		return emwf_embf_id;
	}

	public void setEmwf_embf_id(Integer emwf_embf_id) {
		this.emwf_embf_id = emwf_embf_id;
	}

	public Integer getEmwf_gift_id() {
		return emwf_gift_id;
	}

	public void setEmwf_gift_id(Integer emwf_gift_id) {
		this.emwf_gift_id = emwf_gift_id;
	}

	public String getEmwf_need() {
		return emwf_need;
	}

	public void setEmwf_need(String emwf_need) {
		this.emwf_need = emwf_need;
	}

	public Integer getEmwf_amount() {
		return emwf_amount;
	}

	public void setEmwf_amount(Integer emwf_amount) {
		this.emwf_amount = emwf_amount;
	}

	public String getEmwf_dept() {
		return emwf_dept;
	}

	public void setEmwf_dept(String emwf_dept) {
		this.emwf_dept = emwf_dept;
	}

	public String getEmwf_client() {
		return emwf_client;
	}

	public void setEmwf_client(String emwf_client) {
		this.emwf_client = emwf_client;
	}

	public BigDecimal getEmwf_truecharge() {
		return emwf_truecharge;
	}

	public void setEmwf_truecharge(BigDecimal emwf_truecharge) {
		this.emwf_truecharge = emwf_truecharge.setScale(2,
				BigDecimal.ROUND_HALF_UP);
		;
	}

	public String getEmwf_intime() {
		return emwf_intime;
	}

	public void setEmwf_intime(String emwf_intime) {
		this.emwf_intime = emwf_intime;
	}

	public String getEmwf_modtime() {
		return emwf_modtime;
	}

	public void setEmwf_modtime(String emwf_modtime) {
		this.emwf_modtime = emwf_modtime;
	}

	public String getEmwf_unit() {
		return emwf_unit;
	}

	public void setEmwf_unit(String emwf_unit) {
		this.emwf_unit = emwf_unit;
	}

	public String getEmbf_mold() {
		return embf_mold;
	}

	public void setEmbf_mold(String embf_mold) {
		this.embf_mold = embf_mold;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getEmwf_sortid() {
		return emwf_sortid;
	}

	public void setEmwf_sortid(String emwf_sortid) {
		this.emwf_sortid = emwf_sortid;
	}

	public String getEmwf_family() {
		return emwf_family;
	}

	public void setEmwf_family(String emwf_family) {
		this.emwf_family = emwf_family;
	}

	public String getEmwf_standard() {
		return emwf_standard;
	}

	public void setEmwf_standard(String emwf_standard) {
		this.emwf_standard = emwf_standard;
	}

	public String getEmwf_takeclient() {
		return emwf_takeclient;
	}

	public void setEmwf_takeclient(String emwf_takeclient) {
		this.emwf_takeclient = emwf_takeclient;
	}

	public String getEmwf_linktime() {
		return emwf_linktime;
	}

	public void setEmwf_linktime(String emwf_linktime) {
		this.emwf_linktime = emwf_linktime;
	}

	public String getEmwf_linkcontent() {
		return emwf_linkcontent;
	}

	public void setEmwf_linkcontent(String emwf_linkcontent) {
		this.emwf_linkcontent = emwf_linkcontent;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSuppName() {
		return suppName;
	}

	public void setSuppName(String suppName) {
		this.suppName = suppName;
	}

	public List<EmWelfareModel> getList() {
		return list;
	}

	public void setList(List<EmWelfareModel> list) {
		this.list = list;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getEmwf_sendname() {
		return emwf_sendname;
	}

	public void setEmwf_sendname(String emwf_sendname) {
		this.emwf_sendname = emwf_sendname;
	}

	public String getEmwf_taketime() {
		return emwf_taketime;
	}

	public void setEmwf_taketime(String emwf_taketime) {
		this.emwf_taketime = emwf_taketime;
	}

	public String getEmwf_sendtime() {
		return emwf_sendtime;
	}

	public void setEmwf_sendtime(String emwf_sendtime) {
		this.emwf_sendtime = emwf_sendtime;
	}

	public String getEmwf_signclient() {
		return emwf_signclient;
	}

	public void setEmwf_signclient(String emwf_signclient) {
		this.emwf_signclient = emwf_signclient;
	}

	public String getEmwf_clientsigntime() {
		return emwf_clientsigntime;
	}

	public void setEmwf_clientsigntime(String emwf_clientsigntime) {
		this.emwf_clientsigntime = emwf_clientsigntime;
	}

	public String getEmwf_statename() {
		return emwf_statename;
	}

	public void setEmwf_statename(String emwf_statename) {
		this.emwf_statename = emwf_statename;
	}

	public BigDecimal getProd_discountprice() {
		return prod_discountprice;
	}

	public void setProd_discountprice(BigDecimal prod_discountprice) {
		if (getNum() != null && getNum() >= 0) {
			setAllcharge(prod_discountprice.multiply(new BigDecimal(getNum())));
		}
		try {
			if (prod_discountprice == null) {
				prod_discountprice = BigDecimal.ZERO;
			}
			prod_discountprice = new BigDecimal(myformat.format(prod_discountprice).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.prod_discountprice = prod_discountprice;
	}

	public Integer getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(Integer ownmonth) {
		this.ownmonth = ownmonth;
	}

	public String getProd_name() {
		return prod_name;
	}

	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}

	public String getEmba_birth() {
		return emba_birth;
	}

	public void setEmba_birth(String emba_birth) {
		this.emba_birth = emba_birth;
	}

	public BigDecimal getProd_price() {
		return prod_price;
	}

	public void setProd_price(BigDecimal prod_price) {
		try {
			if (prod_price == null) {
				prod_price = BigDecimal.ZERO;
			}
			prod_price = new BigDecimal(myformat.format(prod_price).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.prod_price = prod_price;
	}

	public Integer getProd_id() {
		return prod_id;
	}

	public void setProd_id(Integer prod_id) {
		this.prod_id = prod_id;
	}

	public Integer getOldnum() {
		return oldnum;
	}

	public void setOldnum(Integer oldnum) {
		this.oldnum = oldnum;
	}

	public BigDecimal getAllcharge() {
		return allcharge;
	}

	public void setAllcharge(BigDecimal allcharge) {
		this.allcharge = allcharge;
	}

	public String getEmwf_backcase() {
		return emwf_backcase;
	}

	public void setEmwf_backcase(String emwf_backcase) {
		this.emwf_backcase = emwf_backcase;
	}

	public String getIdList() {
		return idList;
	}

	public void setIdList(String idList) {
		this.idList = idList;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getLinkId() {
		return linkId;
	}

	public void setLinkId(Integer linkId) {
		this.linkId = linkId;
	}

	public String getEmwf_remark() {
		return emwf_remark;
	}

	public void setEmwf_remark(String emwf_remark) {
		this.emwf_remark = emwf_remark;
	}
	
	
	public Integer getEmwf_prty_id() {
		return emwf_prty_id;
	}

	public void setEmwf_prty_id(Integer emwf_prty_id) {
		this.emwf_prty_id = emwf_prty_id;
	}

	public Integer getEmwf_prod_id() {
		return emwf_prod_id;
	}

	public void setEmwf_prod_id(Integer emwf_prod_id) {
		this.emwf_prod_id = emwf_prod_id;
	}

	public Integer getEmwf_producenum() {
		return emwf_producenum;
	}

	public void setEmwf_producenum(Integer emwf_producenum) {
		if(emwf_producenum!=null)
			setBuy_num(emwf_producenum);
		this.emwf_producenum = emwf_producenum;
	}

	public String getEmwf_produce() {
		return emwf_produce;
	}

	public void setEmwf_produce(String emwf_produce) {
		this.emwf_produce = emwf_produce;
	}

	public String getEmwf_producefo() {
		return emwf_producefo;
	}

	public void setEmwf_producefo(String emwf_producefo) {
		this.emwf_producefo = emwf_producefo;
	}

	public String getCoba_shortname() {
		return coba_shortname;
	}

	public void setCoba_shortname(String coba_shortname) {
		this.coba_shortname = coba_shortname;
	}

	public Integer getEmwf_num() {
		return emwf_num;
	}

	public void setEmwf_num(Integer emwf_num) {
		this.emwf_num = emwf_num;
	}

	public String getPrty_name() {
		return prty_name;
	}

	public void setPrty_name(String prty_name) {
		this.prty_name = prty_name;
	}

	public String getProd_unit() {
		return prod_unit;
	}

	public void setProd_unit(String prod_unit) {
		this.prod_unit = prod_unit;
	}

	public BigDecimal getProd_discount() {
		return prod_discount;
	}

	public void setProd_discount(BigDecimal prod_discount) {
		this.prod_discount = prod_discount;
	}

	public BigDecimal getEmwf_price() {
		return emwf_price;
	}
	

	public BigDecimal getEmwf_preprice() {
		return emwf_preprice;
	}

	public void setEmwf_preprice(BigDecimal emwf_preprice) {
		this.emwf_preprice = emwf_preprice;
	}

	public BigDecimal getEmwf_payprice() {
		return emwf_payprice;
	}

	public void setEmwf_payprice(BigDecimal emwf_payprice) {
		this.emwf_payprice = emwf_payprice;
	}

	public String getEmwf_prepaynum() {
		return emwf_prepaynum;
	}

	public void setEmwf_prepaynum(String emwf_prepaynum) {
		this.emwf_prepaynum = emwf_prepaynum;
	}
	
	public String getEmwf_prodcontent() {
		return emwf_prodcontent;
	}

	public void setEmwf_prodcontent(String emwf_prodcontent) {
		this.emwf_prodcontent = emwf_prodcontent;
	}

	public void setEmwf_price(BigDecimal emwf_price) {
		try {
			if (emwf_price == null) {
				emwf_price = BigDecimal.ZERO;
			}
			emwf_price = new BigDecimal(myformat.format(emwf_price).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		setEmwf_buyprice(emwf_price);
		this.emwf_price = emwf_price;
	}

	public int getUseh_num() {
		return useh_num;
	}

	public void setUseh_num(int useh_num) {
		this.useh_num = useh_num;
	}

	public BigDecimal getUseh_price() {
		return useh_price;
	}

	public void setUseh_price(BigDecimal useh_price) {
		this.useh_price = useh_price;
	}

	public BigDecimal getEmwf_buyprice() {
		return emwf_buyprice;
	}

	public void setEmwf_buyprice(BigDecimal emwf_buyprice) {
		this.emwf_buyprice = emwf_buyprice;
	}

	public int getBuy_num() {
		return buy_num;
	}

	public void setBuy_num(int buy_num) {
		this.buy_num = buy_num;
	}
	
}
