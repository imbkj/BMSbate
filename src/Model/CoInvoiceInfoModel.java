package Model;

import java.math.BigDecimal;

public class CoInvoiceInfoModel {
	private Integer coii_id;
	private Integer coii_coin_id;
	private Integer coii_owmonth;
	private Integer coii_owmonth2;
	private String coii_content;
	private BigDecimal coii_fee;
	private String coii_feetype;
	private String coii_addtime;
	private String coii_addname;
	private Integer coii_state;

	public Integer getCoii_id() {
		return coii_id;
	}

	public void setCoii_id(Integer coii_id) {
		this.coii_id = coii_id;
	}

	public Integer getCoii_coin_id() {
		return coii_coin_id;
	}

	public void setCoii_coin_id(Integer coii_coin_id) {
		this.coii_coin_id = coii_coin_id;
	}

	public Integer getCoii_owmonth() {
		return coii_owmonth;
	}

	public void setCoii_owmonth(Integer coii_owmonth) {
		this.coii_owmonth = coii_owmonth;
	}

	public String getCoii_content() {
		return coii_content;
	}

	public void setCoii_content(String coii_content) {
		this.coii_content = coii_content;
	}

	public BigDecimal getCoii_fee() {
		return coii_fee;
	}

	public void setCoii_fee(BigDecimal coii_fee) {
		if (coii_fee!=null) {
			coii_fee=coii_fee.setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		this.coii_fee = coii_fee;
	}

	public String getCoii_feetype() {
		return coii_feetype;
	}

	public void setCoii_feetype(String coii_feetype) {
		this.coii_feetype = coii_feetype;
	}

	public String getCoii_addtime() {
		return coii_addtime;
	}

	public void setCoii_addtime(String coii_addtime) {
		this.coii_addtime = coii_addtime;
	}

	public String getCoii_addname() {
		return coii_addname;
	}

	public void setCoii_addname(String coii_addname) {
		this.coii_addname = coii_addname;
	}

	public Integer getCoii_state() {
		return coii_state;
	}

	public void setCoii_state(Integer coii_state) {
		this.coii_state = coii_state;
	}

	public Integer getCoii_owmonth2() {
		return coii_owmonth2;
	}

	public void setCoii_owmonth2(Integer coii_owmonth2) {
		this.coii_owmonth2 = coii_owmonth2;
	}

}
