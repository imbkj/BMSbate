package Model;

import java.math.BigDecimal;

public class SurveyHistoryContentInfoModel {
    /// hicn_id
   private Integer  hicn_id;
            /// hicn_titleid
   private Integer  hicn_titleid;
            /// hicn_content
   private String  hicn_content;
            /// hicn_score
   private BigDecimal  hicn_score;
            /// hicn_order
   private Integer  hicn_order;
            /// hicn_state
   private Integer  hicn_state;
   private Integer num;
   
   //***********关系表的字段***********//
   private String relt_txtcon;
   private Integer cid;
   private Integer relt_titlid;
   private Integer relt_contid;
   private Integer relt_id;
   private String coba_shortname;
public Integer getHicn_id() {
	return hicn_id;
}
public void setHicn_id(Integer hicn_id) {
	this.hicn_id = hicn_id;
}
public Integer getHicn_titleid() {
	return hicn_titleid;
}
public void setHicn_titleid(Integer hicn_titleid) {
	this.hicn_titleid = hicn_titleid;
}
public String getHicn_content() {
	return hicn_content;
}
public void setHicn_content(String hicn_content) {
	this.hicn_content = hicn_content;
}
public BigDecimal getHicn_score() {
	return hicn_score;
}
public void setHicn_score(BigDecimal hicn_score) {
	this.hicn_score = hicn_score;
}
public Integer getHicn_order() {
	return hicn_order;
}
public void setHicn_order(Integer hicn_order) {
	this.hicn_order = hicn_order;
}
public Integer getHicn_state() {
	return hicn_state;
}
public void setHicn_state(Integer hicn_state) {
	this.hicn_state = hicn_state;
}
public Integer getNum() {
	return num;
}
public void setNum(Integer num) {
	this.num = num;
}
public String getRelt_txtcon() {
	return relt_txtcon;
}
public void setRelt_txtcon(String relt_txtcon) {
	this.relt_txtcon = relt_txtcon;
}
public Integer getCid() {
	return cid;
}
public void setCid(Integer cid) {
	this.cid = cid;
}
public Integer getRelt_titlid() {
	return relt_titlid;
}
public void setRelt_titlid(Integer relt_titlid) {
	this.relt_titlid = relt_titlid;
}
public Integer getRelt_contid() {
	return relt_contid;
}
public void setRelt_contid(Integer relt_contid) {
	this.relt_contid = relt_contid;
}
public Integer getRelt_id() {
	return relt_id;
}
public void setRelt_id(Integer relt_id) {
	this.relt_id = relt_id;
}
public String getCoba_shortname() {
	return coba_shortname;
}
public void setCoba_shortname(String coba_shortname) {
	this.coba_shortname = coba_shortname;
}
   
   
}
