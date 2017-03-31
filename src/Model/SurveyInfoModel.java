package Model;

import java.util.List;

public class SurveyInfoModel {
    /// sury_id
    private Integer  sury_id;
             /// sury_title
    private String  sury_title;
             /// sury_order
    private Integer  sury_order;
             /// sury_state
    private Integer  sury_state;
    private String sury_type;
    private String sury_answertype;
    private List<SurveyContentModel> list;
    private String answertype;
    
	public Integer getSury_id() {
		return sury_id;
	}
	public void setSury_id(Integer sury_id) {
		this.sury_id = sury_id;
	}
	public String getSury_title() {
		return sury_title;
	}
	public void setSury_title(String sury_title) {
		this.sury_title = sury_title;
	}
	public Integer getSury_order() {
		return sury_order;
	}
	public void setSury_order(Integer sury_order) {
		this.sury_order = sury_order;
	}
	public Integer getSury_state() {
		return sury_state;
	}
	public void setSury_state(Integer sury_state) {
		this.sury_state = sury_state;
	}
	public List<SurveyContentModel> getList() {
		return list;
	}
	public void setList(List<SurveyContentModel> list) {
		this.list = list;
	}
	public String getSury_type() {
		return sury_type;
	}
	public void setSury_type(String sury_type) {
		this.sury_type = sury_type;
	}
	public String getSury_answertype() {
		return sury_answertype;
	}
	public void setSury_answertype(String sury_answertype) {
		this.sury_answertype = sury_answertype;
	}
	public String getAnswertype() {
		return answertype;
	}
	public void setAnswertype(String answertype) {
		this.answertype = answertype;
	}
	
}
