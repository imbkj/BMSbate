package Model;

import java.math.BigDecimal;

public class EmCommissionOutChangeEntrustModel {
	
private Integer cid;
private Integer gid;
private Integer ecyc_cityid;
private BigDecimal ecyc_sb_base;
private String ecyc_house_cp;
private BigDecimal ecyc_house_base;
private String coba_company;
private String emba_name;
private String emba_idcard;
private String coba_client;
private String city;
private String ecyc_state;
private String ecyt_startdate;

java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");

public String getCity() {
	return city;
}
public void setCity(String city) {
	this.city = city;
}

public String getEcyt_startdate() {
	return ecyt_startdate;
}
public void setEcyt_startdate(String ecyt_startdate) {
	this.ecyt_startdate = ecyt_startdate;
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
public Integer getEcyc_cityid() {
	return ecyc_cityid;
}
public void setEcyc_cityid(Integer ecyc_cityid) {
	this.ecyc_cityid = ecyc_cityid;
}

public BigDecimal getEcyc_sb_base() {
	return ecyc_sb_base;
}
public void setEcyc_sb_base(BigDecimal ecyc_sb_base) {
	this.ecyc_sb_base =ecyc_sb_base.setScale(2, BigDecimal.ROUND_HALF_UP);
}
public String getEcyc_house_cp() {
	return ecyc_house_cp;
}
public void setEcyc_house_cp(String ecyc_house_cp) {
	this.ecyc_house_cp = ecyc_house_cp;
}
public BigDecimal getEcyc_house_base() {
	return ecyc_house_base;
}
public void setEcyc_house_base(BigDecimal ecyc_house_base) {
	this.ecyc_house_base = ecyc_house_base.setScale(2, BigDecimal.ROUND_HALF_UP);
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
public String getEmba_idcard() {
	return emba_idcard;
}
public void setEmba_idcard(String emba_idcard) {
	this.emba_idcard = emba_idcard;
}

public String getEcyc_state() {
	return ecyc_state;
}
public void setEcyc_state(String ecyc_state) {
	this.ecyc_state = ecyc_state;
}
public String getCoba_client() {
	return coba_client;
}
public void setCoba_client(String coba_client) {
	this.coba_client = coba_client;
}

}
