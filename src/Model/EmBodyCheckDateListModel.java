package Model;

import java.util.Date;

public class EmBodyCheckDateListModel {
	// / ebdl_id
	private int ebdl_id;
	// / cid
	private int cid;
	// / gid
	private int gid;
	// / ebdl_hospital
	private int ebdl_hospital;
	// / ebdl_address
	private int ebdl_address;
	// / 体检时间
	private String ebdl_bctime;
	// / ebdl_items
	private String ebdl_items;
	// / ebdl_addtime
	private String ebdl_addtime;
	// / ebdl_addname
	private String ebdl_addname;
	// / ebdl_modtime
	private String ebdl_modtime;
	// / 0:待确认;1:已确认
	private int ebdl_state;
	private String coba_shortname;
	private String emba_name;
	private String emba_sex;
	private String emba_idcard;
	private String emba_indate;
	private String coba_client;
	private String bctime;
	private Date dd;

	public int getEbdl_id() {
		return ebdl_id;
	}

	public void setEbdl_id(int ebdl_id) {
		this.ebdl_id = ebdl_id;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public int getEbdl_hospital() {
		return ebdl_hospital;
	}

	public void setEbdl_hospital(int ebdl_hospital) {
		this.ebdl_hospital = ebdl_hospital;
	}

	public int getEbdl_address() {
		return ebdl_address;
	}

	public void setEbdl_address(int ebdl_address) {
		this.ebdl_address = ebdl_address;
	}

	public String getEbdl_bctime() {
		return ebdl_bctime;
	}

	public void setEbdl_bctime(String ebdl_bctime) {
		this.ebdl_bctime = ebdl_bctime;
	}

	public String getEbdl_items() {
		return ebdl_items;
	}

	public void setEbdl_items(String ebdl_items) {
		this.ebdl_items = ebdl_items;
	}

	public String getEbdl_addtime() {
		return ebdl_addtime;
	}

	public void setEbdl_addtime(String ebdl_addtime) {
		this.ebdl_addtime = ebdl_addtime;
	}

	public String getEbdl_addname() {
		return ebdl_addname;
	}

	public void setEbdl_addname(String ebdl_addname) {
		this.ebdl_addname = ebdl_addname;
	}

	public String getEbdl_modtime() {
		return ebdl_modtime;
	}

	public void setEbdl_modtime(String ebdl_modtime) {
		this.ebdl_modtime = ebdl_modtime;
	}

	public int getEbdl_state() {
		return ebdl_state;
	}

	public void setEbdl_state(int ebdl_state) {
		this.ebdl_state = ebdl_state;
	}

	public String getCoba_shortname() {
		return coba_shortname;
	}

	public void setCoba_shortname(String coba_shortname) {
		this.coba_shortname = coba_shortname;
	}

	public String getEmba_name() {
		return emba_name;
	}

	public void setEmba_name(String emba_name) {
		this.emba_name = emba_name;
	}

	public String getEmba_sex() {
		return emba_sex;
	}

	public void setEmba_sex(String emba_sex) {
		this.emba_sex = emba_sex;
	}

	public String getEmba_idcard() {
		return emba_idcard;
	}

	public void setEmba_idcard(String emba_idcard) {
		this.emba_idcard = emba_idcard;
	}

	public String getEmba_indate() {
		return emba_indate;
	}

	public void setEmba_indate(String emba_indate) {
		this.emba_indate = emba_indate;
	}

	public String getCoba_client() {
		return coba_client;
	}

	public void setCoba_client(String coba_client) {
		this.coba_client = coba_client;
	}

	public String getBctime() {
		return bctime;
	}

	public void setBctime(String bctime) {
		this.bctime = bctime;
	}

	public Date getDd() {
		return dd;
	}

	public void setDd(Date dd) {
		this.dd = dd;
	}

}
