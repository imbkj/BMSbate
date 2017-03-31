package Model;

import java.math.BigDecimal;

public class EmActyWarehouseModel {
	private Integer wase_id;
	private String wase_name;
	private int wase_totalnum;
	private int wase_nownum;
	private String wase_addname,wase_addtime,wase_modname,wase_modtime;
	private Integer num;
	private Integer wase_prod_id;
	private Integer wase_prty_id;
	private String wase_unit;
	private int use_num;
	private BigDecimal price=BigDecimal.ZERO;
	public Integer getWase_id() {
		return wase_id;
	}
	public void setWase_id(Integer wase_id) {
		this.wase_id = wase_id;
	}
	public String getWase_name() {
		return wase_name;
	}
	public void setWase_name(String wase_name) {
		this.wase_name = wase_name;
	}
	
	
	public int getWase_totalnum() {
		return wase_totalnum;
	}
	public void setWase_totalnum(int wase_totalnum) {
		this.wase_totalnum = wase_totalnum;
	}
	public int getWase_nownum() {
		return wase_nownum;
	}
	public void setWase_nownum(int wase_nownum) {
		this.wase_nownum = wase_nownum;
	}
	public String getWase_addname() {
		return wase_addname;
	}
	public void setWase_addname(String wase_addname) {
		this.wase_addname = wase_addname;
	}
	public String getWase_addtime() {
		return wase_addtime;
	}
	public void setWase_addtime(String wase_addtime) {
		this.wase_addtime = wase_addtime;
	}
	public String getWase_modname() {
		return wase_modname;
	}
	public void setWase_modname(String wase_modname) {
		this.wase_modname = wase_modname;
	}
	public String getWase_modtime() {
		return wase_modtime;
	}
	public void setWase_modtime(String wase_modtime) {
		this.wase_modtime = wase_modtime;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getWase_prod_id() {
		return wase_prod_id;
	}
	public void setWase_prod_id(Integer wase_prod_id) {
		this.wase_prod_id = wase_prod_id;
	}
	public Integer getWase_prty_id() {
		return wase_prty_id;
	}
	public void setWase_prty_id(Integer wase_prty_id) {
		this.wase_prty_id = wase_prty_id;
	}
	public String getWase_unit() {
		return wase_unit;
	}
	public void setWase_unit(String wase_unit) {
		this.wase_unit = wase_unit;
	}
	public int getUse_num() {
		return use_num;
	}
	public void setUse_num(int use_num) {
		this.use_num = use_num;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
}
