package Model;

import java.math.BigDecimal;

public class EmGatheringModel {
    /// Emgt_id
    private Integer  emgt_id;
             /// gid
    private Integer  gid;
             /// cid
    private Integer  cid;
             /// ownmonth
    private Integer ownmonth;
             /// Emgt_type
    private String  emgt_type;
             /// Emgt_paytype
    private String  emgt_paytype;
             /// Emgt_fee
    private BigDecimal emgt_fee=new BigDecimal(0.00);
             /// Emgt_remark
    private String  emgt_remark;
             /// emgt_addtime
    private String  emgt_addtime;
             /// emgt_addname
    private String  emgt_addname;
    private String coba_shortname;
    private String coba_company;
    private String emba_name;
	public Integer getEmgt_id() {
		return emgt_id;
	}
	public void setEmgt_id(Integer emgt_id) {
		this.emgt_id = emgt_id;
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
	public Integer getOwnmonth() {
		return ownmonth;
	}
	public void setOwnmonth(Integer ownmonth) {
		this.ownmonth = ownmonth;
	}
	public String getEmgt_type() {
		return emgt_type;
	}
	public void setEmgt_type(String emgt_type) {
		this.emgt_type = emgt_type;
	}
	public String getEmgt_paytype() {
		return emgt_paytype;
	}
	public void setEmgt_paytype(String emgt_paytype) {
		this.emgt_paytype = emgt_paytype;
	}
	public BigDecimal getEmgt_fee() {
		return emgt_fee;
	}
	public void setEmgt_fee(BigDecimal emgt_fee) {
		this.emgt_fee = emgt_fee;
	}
	public String getEmgt_remark() {
		return emgt_remark;
	}
	public void setEmgt_remark(String emgt_remark) {
		this.emgt_remark = emgt_remark;
	}
	public String getEmgt_addtime() {
		return emgt_addtime;
	}
	public void setEmgt_addtime(String emgt_addtime) {
		this.emgt_addtime = emgt_addtime;
	}
	public String getEmgt_addname() {
		return emgt_addname;
	}
	public void setEmgt_addname(String emgt_addname) {
		this.emgt_addname = emgt_addname;
	}
	public String getCoba_shortname() {
		return coba_shortname;
	}
	public void setCoba_shortname(String coba_shortname) {
		this.coba_shortname = coba_shortname;
	}
	public String getCoba_company() {
		return coba_company;
	}
	public void setCoba_company(String coba_company) {
		this.coba_company = coba_company;
	}
	public String getEmba_name() {
		return emba_name;
	}
	public void setEmba_name(String emba_name) {
		this.emba_name = emba_name;
	}
    
}
