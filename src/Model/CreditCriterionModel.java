package Model;

import java.util.Date;

public class CreditCriterionModel {
	private int crcr_id;
	private String crcr_type;
	private String crcr_content;
	private int crcr_state;
	private String crcr_addname;
	private Date crcr_addtime;
	private String crcr_ifstate; //是否有效。不是CreditCriterion的字段，crcr_state为1时表示有效，0时表示无效
	
	public CreditCriterionModel(int crcr_id, String crcr_type,
			String crcr_content, int crcr_state, String crcr_addname,
			Date crcr_addtime,String crcr_ifstate) {
		super();
		this.crcr_id = crcr_id;
		this.crcr_type = crcr_type;
		this.crcr_content = crcr_content;
		this.crcr_state = crcr_state;
		this.crcr_addname = crcr_addname;
		this.crcr_addtime = crcr_addtime;
		this.crcr_ifstate=crcr_ifstate;
	}
	public CreditCriterionModel()
	{}
	public int getCrcr_id() {
		return crcr_id;
	}
	public void setCrcr_id(int crcr_id) {
		this.crcr_id = crcr_id;
	}
	public String getCrcr_type() {
		return crcr_type;
	}
	public void setCrcr_type(String crcr_type) {
		this.crcr_type = crcr_type;
	}
	public String getCrcr_content() {
		return crcr_content;
	}
	public void setCrcr_content(String crcr_content) {
		this.crcr_content = crcr_content;
	}
	public int getCrcr_state() {
		return crcr_state;
	}
	public void setCrcr_state(int crcr_state) {
		this.crcr_state = crcr_state;
	}
	public String getCrcr_addname() {
		return crcr_addname;
	}
	public void setCrcr_addname(String crcr_addname) {
		this.crcr_addname = crcr_addname;
	}
	public Date getCrcr_addtime() {
		return crcr_addtime;
	}
	public void setCrcr_addtime(Date crcr_addtime) {
		this.crcr_addtime = crcr_addtime;
	}
	public String getCrcr_ifstate() {
		return crcr_ifstate;
	}
	public void setCrcr_ifstate(String crcr_ifstate) {
		this.crcr_ifstate = crcr_ifstate;
	}
}
