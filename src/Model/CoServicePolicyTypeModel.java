package Model;

import java.util.List;

public class CoServicePolicyTypeModel {
	 /// id
	  private Integer note_id;
	           /// note_type
	  private String  note_type;
	           /// note_state
	  private Integer  note_state;
	           /// note_addname
	  private String  note_addname;
	           /// note_addtime
	  private String  note_addtime;
	           /// note_modname
	  private String  note_modname;
	           /// note_modtime
	  private String  note_modtime;
	           /// 1、政府政策；2、机构政策
	  private Integer  note_flag;
	  private Integer note_order;//排序
	  
	  private List<CoServicePolicyTitleModel> titlelist;
	public Integer getNote_id() {
		return note_id;
	}
	public void setNote_id(Integer note_id) {
		this.note_id = note_id;
	}
	public String getNote_type() {
		return note_type;
	}
	public void setNote_type(String note_type) {
		this.note_type = note_type;
	}
	public Integer getNote_state() {
		return note_state;
	}
	public void setNote_state(Integer note_state) {
		this.note_state = note_state;
	}
	public String getNote_addname() {
		return note_addname;
	}
	public void setNote_addname(String note_addname) {
		this.note_addname = note_addname;
	}
	public String getNote_addtime() {
		return note_addtime;
	}
	public void setNote_addtime(String note_addtime) {
		this.note_addtime = note_addtime;
	}
	public String getNote_modname() {
		return note_modname;
	}
	public void setNote_modname(String note_modname) {
		this.note_modname = note_modname;
	}
	public String getNote_modtime() {
		return note_modtime;
	}
	public void setNote_modtime(String note_modtime) {
		this.note_modtime = note_modtime;
	}
	public Integer getNote_flag() {
		return note_flag;
	}
	public void setNote_flag(Integer note_flag) {
		this.note_flag = note_flag;
	}
	public List<CoServicePolicyTitleModel> getTitlelist() {
		return titlelist;
	}
	public void setTitlelist(List<CoServicePolicyTitleModel> titlelist) {
		this.titlelist = titlelist;
	}
	public Integer getNote_order() {
		return note_order;
	}
	public void setNote_order(Integer note_order) {
		this.note_order = note_order;
	}
	
}
