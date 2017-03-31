package Model;

import java.math.BigDecimal;

public class SurveyContentModel {
    /// cont_id
    private Integer  cont_id;
             /// cont_suryid
    private Integer  cont_suryid;
             /// cont_content
    private String  cont_content;
             /// cont_score
    private BigDecimal  cont_score;
             /// cont_order
    private Integer  cont_order;
    
    private Integer cont_state;
    
	public Integer getCont_id() {
		return cont_id;
	}
	public void setCont_id(Integer cont_id) {
		this.cont_id = cont_id;
	}
	public Integer getCont_suryid() {
		return cont_suryid;
	}
	public void setCont_suryid(Integer cont_suryid) {
		this.cont_suryid = cont_suryid;
	}
	public String getCont_content() {
		return cont_content;
	}
	public void setCont_content(String cont_content) {
		this.cont_content = cont_content;
	}
	public BigDecimal getCont_score() {
		return cont_score;
	}
	public void setCont_score(BigDecimal cont_score) {
		this.cont_score = cont_score;
	}
	public Integer getCont_order() {
		return cont_order;
	}
	public void setCont_order(Integer cont_order) {
		this.cont_order = cont_order;
	}
	public Integer getCont_state() {
		return cont_state;
	}
	public void setCont_state(Integer cont_state) {
		this.cont_state = cont_state;
	}
	
}
