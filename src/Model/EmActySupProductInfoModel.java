package Model;

import java.math.BigDecimal;

public class EmActySupProductInfoModel {
	// / pro_id
	private Integer prod_id;
	// / pro_supId
	private Integer prod_supid;
	private Integer prod_eaco_id;
	private Integer eaco_id;
	// / pro_Name
	private String prod_name;
	private String prod_discount;
	// / pro_Price
	private BigDecimal prod_price;
	// / pro_DiscountPrice
	private BigDecimal prod_discountprice;
	// / pro_TotalNum
	private Integer prod_totalnum;
	// / pro_Addname
	private String prod_addname;
	// / pro_Addtime
	private String prod_addtime;
	private Integer prod_state;
	private String prod_remark;

	private String supp_name;

	private String prod_modtime;
	private String prod_modname;
	private Boolean bstate; // 根据prod_eaco_id不为空判断产品已经挂到合同

	private Integer embf_id;

	public Integer getProd_id() {
		return prod_id;
	}

	public void setProd_id(Integer prod_id) {
		this.prod_id = prod_id;
	}

	public Integer getProd_supid() {
		return prod_supid;
	}

	public Integer getProd_eaco_id() {
		return prod_eaco_id;
	}

	public void setProd_eaco_id(Integer prod_eaco_id) {
		this.prod_eaco_id = prod_eaco_id;
	}

	public void setProd_supid(Integer prod_supid) {
		this.prod_supid = prod_supid;
	}

	public String getProd_name() {
		return prod_name;
	}

	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}

	public String getProd_discount() {
		return prod_discount;
	}

	public void setProd_discount(String prod_discount) {
		this.prod_discount = prod_discount;
	}

	public BigDecimal getProd_price() {
		return prod_price;
	}

	public void setProd_price(BigDecimal prod_price) {
		this.prod_price = prod_price;
	}

	public BigDecimal getProd_discountprice() {
		return prod_discountprice;
	}

	public void setProd_discountprice(BigDecimal prod_discountprice) {
		this.prod_discountprice = prod_discountprice;
	}

	public Integer getProd_totalnum() {
		return prod_totalnum;
	}

	public void setProd_totalnum(Integer prod_totalnum) {
		this.prod_totalnum = prod_totalnum;
	}

	public String getProd_addname() {
		return prod_addname;
	}

	public void setProd_addname(String prod_addname) {
		this.prod_addname = prod_addname;
	}

	public String getProd_addtime() {
		return prod_addtime;
	}

	public void setProd_addtime(String prod_addtime) {
		this.prod_addtime = prod_addtime;
	}

	public Integer getProd_state() {
		return prod_state;
	}

	public void setProd_state(Integer prod_state) {
		this.prod_state = prod_state;
	}

	public String getProd_remark() {
		return prod_remark;
	}

	public void setProd_remark(String prod_remark) {
		this.prod_remark = prod_remark;
	}

	public String getSupp_name() {
		return supp_name;
	}

	public void setSupp_name(String supp_name) {
		this.supp_name = supp_name;
	}

	public String getProd_modtime() {
		return prod_modtime;
	}

	public void setProd_modtime(String prod_modtime) {
		this.prod_modtime = prod_modtime;
	}

	public String getProd_modname() {
		return prod_modname;
	}

	public void setProd_modname(String prod_modname) {
		this.prod_modname = prod_modname;
	}

	public Boolean isBstate() {
		return bstate;
	}

	public void setBstate(Boolean bstate) {
		this.bstate = bstate;
	}

	public Integer getEmbf_id() {
		return embf_id;
	}

	public void setEmbf_id(Integer embf_id) {
		this.embf_id = embf_id;
	}

	public Integer getEaco_id() {
		return eaco_id;
	}

	public void setEaco_id(Integer eaco_id) {
		this.eaco_id = eaco_id;
	}

}
