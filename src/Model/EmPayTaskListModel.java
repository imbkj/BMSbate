package Model;

import java.util.Date;

public class EmPayTaskListModel implements Cloneable {
	// / patk_id
	private Integer patk_id;
	// / patk_number
	private String patk_number;
	// / patk_addtime
	private Date patk_addtime;
	// / patk_tapr_id
	private Integer patk_tapr_id;
	// / patk_state
	private Integer patk_state;
	// / patk_addname
	private String patk_addname;
	private String patk_prestep;

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			return null;
		}

	}

	public Integer getPatk_id() {
		return patk_id;
	}

	public void setPatk_id(Integer patk_id) {
		this.patk_id = patk_id;
	}

	public String getPatk_number() {
		return patk_number;
	}

	public void setPatk_number(String patk_number) {
		this.patk_number = patk_number;
	}

	public Date getPatk_addtime() {
		return patk_addtime;
	}

	public void setPatk_addtime(Date patk_addtime) {
		this.patk_addtime = patk_addtime;
	}

	public Integer getPatk_tapr_id() {
		return patk_tapr_id;
	}

	public void setPatk_tapr_id(Integer patk_tapr_id) {
		this.patk_tapr_id = patk_tapr_id;
	}

	public Integer getPatk_state() {
		return patk_state;
	}

	public void setPatk_state(Integer patk_state) {
		this.patk_state = patk_state;
	}

	public String getPatk_addname() {
		return patk_addname;
	}

	public void setPatk_addname(String patk_addname) {
		this.patk_addname = patk_addname;
	}

	public String getPatk_prestep() {
		return patk_prestep;
	}

	public void setPatk_prestep(String patk_prestep) {
		this.patk_prestep = patk_prestep;
	}
}
