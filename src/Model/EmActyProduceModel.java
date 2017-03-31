package Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

public class EmActyProduceModel {
	private DecimalFormat myformat=new DecimalFormat("0.00");
    /// prod_id
    private Integer  prod_id;
             /// prod_name
    private String  prod_name;
             /// prod_state
    private Integer  prod_state;
             /// prod_unit
    private String  prod_unit;
    private String prod_addname;
    private String prod_addtime;
    
    private BigDecimal prod_price;
    private BigDecimal prod_discount;
    private String prod_pricetype;
    private int prod_supp_id;
    private EmActyProducetypeModel pm;
    
    private List<EmActyProducetypeModel> ptypeList;
    
	public Integer getProd_id() {
		return prod_id;
	}
	public void setProd_id(Integer prod_id) {
		this.prod_id = prod_id;
	}
	public String getProd_name() {
		return prod_name;
	}
	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}
	public Integer getProd_state() {
		return prod_state;
	}
	public void setProd_state(Integer prod_state) {
		this.prod_state = prod_state;
	}
	public String getProd_unit() {
		return prod_unit;
	}
	public void setProd_unit(String prod_unit) {
		this.prod_unit = prod_unit;
	}
	public List<EmActyProducetypeModel> getPtypeList() {
		return ptypeList;
	}
	public void setPtypeList(List<EmActyProducetypeModel> ptypeList) {
		this.ptypeList = ptypeList;
	}
	public BigDecimal getProd_price() {
		return prod_price;
	}
	public void setProd_price(BigDecimal prod_price) {
		this.prod_price = prod_price;
	}
	public BigDecimal getProd_discount() {
		return prod_discount;
	}
	public void setProd_discount(BigDecimal prod_discount) {
		if(prod_discount!=null)
		prod_discount =new BigDecimal(myformat
				.format(prod_discount).toString());
		this.prod_discount = prod_discount;
	}
	public String getProd_pricetype() {
		return prod_pricetype;
	}
	public void setProd_pricetype(String prod_pricetype) {
		this.prod_pricetype = prod_pricetype;
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
	public int getProd_supp_id() {
		return prod_supp_id;
	}
	public void setProd_supp_id(int prod_supp_id) {
		this.prod_supp_id = prod_supp_id;
	}
	public EmActyProducetypeModel getPm() {
		return pm;
	}
	public void setPm(EmActyProducetypeModel pm) {
		this.pm = pm;
	}
    
    
}
