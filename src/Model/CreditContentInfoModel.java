package Model;

import java.util.Date;

public class CreditContentInfoModel {
	private int crst_id;
	private int crst_crcr_id;
	private String crst_name;
	private String crst_addname;
	private Date crst_addtime;
	private int crst_state;
	private String  crst_ifstate; //是否有效。不是CreditContentInfo的字段，crst_state为1时表示有效，0时表示无效
	public CreditContentInfoModel(int crst_id, int crst_crcr_id,
			String crst_name, String crst_addname, Date crst_addtime,
			int crst_state,String crst_ifstate) {
		super();
		this.crst_id = crst_id;
		this.crst_crcr_id = crst_crcr_id;
		this.crst_name = crst_name;
		this.crst_addname = crst_addname;
		this.crst_addtime = crst_addtime;
		this.crst_state = crst_state;
		this.crst_ifstate=crst_ifstate;
	}
	public CreditContentInfoModel(){}
	public int getCrst_id() {
		return crst_id;
	}
	public void setCrst_id(int crst_id) {
		this.crst_id = crst_id;
	}
	public int getCrst_crcr_id() {
		return crst_crcr_id;
	}
	public void setCrst_crcr_id(int crst_crcr_id) {
		this.crst_crcr_id = crst_crcr_id;
	}
	public String getCrst_name() {
		return crst_name;
	}
	public void setCrst_name(String crst_name) {
		this.crst_name = crst_name;
	}
	public String getCrst_addname() {
		return crst_addname;
	}
	public void setCrst_addname(String crst_addname) {
		this.crst_addname = crst_addname;
	}
	public Date getCrst_addtime() {
		return crst_addtime;
	}
	public void setCrst_addtime(Date crst_addtime) {
		this.crst_addtime = crst_addtime;
	}
	public int getCrst_state() {
		return crst_state;
	}
	public void setCrst_state(int crst_state) {
		this.crst_state = crst_state;
	}
	public String getCrst_ifstate() {
		return crst_ifstate;
	}
	public void setCrst_ifstate(String crst_ifstate) {
		this.crst_ifstate = crst_ifstate;
	}
}
