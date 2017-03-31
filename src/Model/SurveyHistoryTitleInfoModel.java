package Model;

import java.util.List;

public class SurveyHistoryTitleInfoModel {
    /// hitl_id
   private Integer  hitl_id;
            /// hitl_title
   private String  hitl_title;
            /// hitl_type
   private String  hitl_type;
            /// hiti_answertype
   private String  hiti_answertype;
            /// hiti_order
   private Integer  hiti_order;
            /// hiti_ownyear
   private Integer  hiti_ownyear;
            /// hiti_state
   private Integer  hiti_state;
   private String answertype;
   private String relt_txtcon;
   private List<SurveyHistoryContentInfoModel> list;
public Integer getHitl_id() {
	return hitl_id;
}
public void setHitl_id(Integer hitl_id) {
	this.hitl_id = hitl_id;
}
public String getHitl_title() {
	return hitl_title;
}
public void setHitl_title(String hitl_title) {
	this.hitl_title = hitl_title;
}
public String getHitl_type() {
	return hitl_type;
}
public void setHitl_type(String hitl_type) {
	this.hitl_type = hitl_type;
}
public String getHiti_answertype() {
	return hiti_answertype;
}
public void setHiti_answertype(String hiti_answertype) {
	this.hiti_answertype = hiti_answertype;
}
public Integer getHiti_order() {
	return hiti_order;
}
public void setHiti_order(Integer hiti_order) {
	this.hiti_order = hiti_order;
}
public Integer getHiti_ownyear() {
	return hiti_ownyear;
}
public void setHiti_ownyear(Integer hiti_ownyear) {
	this.hiti_ownyear = hiti_ownyear;
}
public Integer getHiti_state() {
	return hiti_state;
}
public void setHiti_state(Integer hiti_state) {
	this.hiti_state = hiti_state;
}
public String getAnswertype() {
	return answertype;
}
public void setAnswertype(String answertype) {
	this.answertype = answertype;
}
public String getRelt_txtcon() {
	return relt_txtcon;
}
public void setRelt_txtcon(String relt_txtcon) {
	this.relt_txtcon = relt_txtcon;
}
public List<SurveyHistoryContentInfoModel> getList() {
	return list;
}
public void setList(List<SurveyHistoryContentInfoModel> list) {
	this.list = list;
}
   
   
}
