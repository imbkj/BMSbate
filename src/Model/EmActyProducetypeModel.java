package Model;

import java.math.BigDecimal;
import java.util.Date;

public class EmActyProducetypeModel {
	// / prty_id
	private Integer prty_id;
	// / prty_prod_id
	private Integer prty_prod_id;
	// / prty_name
	private String prty_name;
	// / prty_discount
	private BigDecimal prty_discount;
	// / prty_unit
	private String prty_unit;
	// / prty_state
	private Integer prty_state;
	// / prty_addname
	private String prty_addname;
	// / prty_addtime
	private Date prty_addtime;
	
	private BigDecimal prty_price;
	private String prty_pricetype;

	public Integer getPrty_id() {
		return prty_id;
	}

	public void setPrty_id(Integer prty_id) {
		this.prty_id = prty_id;
	}

	public Integer getPrty_prod_id() {
		return prty_prod_id;
	}

	public void setPrty_prod_id(Integer prty_prod_id) {
		this.prty_prod_id = prty_prod_id;
	}

	public String getPrty_name() {
		return prty_name;
	}

	public void setPrty_name(String prty_name) {
		this.prty_name = prty_name;
	}

	public BigDecimal getPrty_discount() {
		return prty_discount;
	}

	public void setPrty_discount(BigDecimal prty_discount) {
		this.prty_discount = prty_discount;
	}

	public String getPrty_unit() {
		return prty_unit;
	}

	public void setPrty_unit(String prty_unit) {
		this.prty_unit = prty_unit;
	}

	public Integer getPrty_state() {
		return prty_state;
	}

	public void setPrty_state(Integer prty_state) {
		this.prty_state = prty_state;
	}

	public String getPrty_addname() {
		return prty_addname;
	}

	public void setPrty_addname(String prty_addname) {
		this.prty_addname = prty_addname;
	}

	public Date getPrty_addtime() {
		return prty_addtime;
	}

	public void setPrty_addtime(Date prty_addtime) {
		this.prty_addtime = prty_addtime;
	}

	public BigDecimal getPrty_price() {
		return prty_price;
	}

	public void setPrty_price(BigDecimal prty_price) {
		this.prty_price = prty_price;
	}

	public String getPrty_pricetype() {
		return prty_pricetype;
	}

	public void setPrty_pricetype(String prty_pricetype) {
		this.prty_pricetype = prty_pricetype;
	}
	
}
