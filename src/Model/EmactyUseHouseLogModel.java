package Model;

import java.math.BigDecimal;
import java.util.Date;

public class EmactyUseHouseLogModel {
    /// useh_id
    private Integer  useh_id;
             /// useh_sortid
    private String  useh_sortid;
             /// useh_prod_id
    private Integer  useh_prod_id;
             /// useh_prty_id
    private Integer  useh_prty_id;
             /// useh_num
    private Integer  useh_num;
             /// useh_addname
    private String  useh_addname;
             /// useh_addtime
    private Date  useh_addtime;
    
    private BigDecimal useh_price;
    
    private int useh_wase_id;
    
	public Integer getUseh_id() {
		return useh_id;
	}
	public void setUseh_id(Integer useh_id) {
		this.useh_id = useh_id;
	}
	public String getUseh_sortid() {
		return useh_sortid;
	}
	public void setUseh_sortid(String useh_sortid) {
		this.useh_sortid = useh_sortid;
	}
	public Integer getUseh_prod_id() {
		return useh_prod_id;
	}
	public void setUseh_prod_id(Integer useh_prod_id) {
		this.useh_prod_id = useh_prod_id;
	}
	public Integer getUseh_prty_id() {
		return useh_prty_id;
	}
	public void setUseh_prty_id(Integer useh_prty_id) {
		this.useh_prty_id = useh_prty_id;
	}
	public Integer getUseh_num() {
		return useh_num;
	}
	public void setUseh_num(Integer useh_num) {
		this.useh_num = useh_num;
	}
	public String getUseh_addname() {
		return useh_addname;
	}
	public void setUseh_addname(String useh_addname) {
		this.useh_addname = useh_addname;
	}
	public Date getUseh_addtime() {
		return useh_addtime;
	}
	public void setUseh_addtime(Date useh_addtime) {
		this.useh_addtime = useh_addtime;
	}
	public BigDecimal getUseh_price() {
		return useh_price;
	}
	public void setUseh_price(BigDecimal useh_price) {
		this.useh_price = useh_price;
	}
	public int getUseh_wase_id() {
		return useh_wase_id;
	}
	public void setUseh_wase_id(int useh_wase_id) {
		this.useh_wase_id = useh_wase_id;
	}
    
    
}
