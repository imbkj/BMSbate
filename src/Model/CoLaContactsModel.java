package Model;

import java.util.Date;

public class CoLaContactsModel {
	private int clco_id;
	private String clco_content;
	private Date clco_linktime;
	private Date clco_addtime;
	private String clco_addname;
	private int clco_state;
	private int clco_calo_id;
	public CoLaContactsModel() {
		super();
	}
	public CoLaContactsModel(int clco_id, String clco_content,
			Date clco_linktime, Date clco_addtime, String clco_addname,
			int clco_state,int clco_calo_id) {
		super();
		this.clco_id = clco_id;
		this.clco_content = clco_content;
		this.clco_linktime = clco_linktime;
		this.clco_addtime = clco_addtime;
		this.clco_addname = clco_addname;
		this.clco_state = clco_state;
		this.clco_calo_id=clco_calo_id;
	}
	public int getClco_id() {
		return clco_id;
	}
	public void setClco_id(int clco_id) {
		this.clco_id = clco_id;
	}
	public String getClco_content() {
		return clco_content;
	}
	public void setClco_content(String clco_content) {
		this.clco_content = clco_content;
	}
	public Date getClco_linktime() {
		return clco_linktime;
	}
	public void setClco_linktime(Date clco_linktime) {
		this.clco_linktime = clco_linktime;
	}
	public Date getClco_addtime() {
		return clco_addtime;
	}
	public void setClco_addtime(Date clco_addtime) {
		this.clco_addtime = clco_addtime;
	}
	public String getClco_addname() {
		return clco_addname;
	}
	public void setClco_addname(String clco_addname) {
		this.clco_addname = clco_addname;
	}
	public int getClco_state() {
		return clco_state;
	}
	public void setClco_state(int clco_state) {
		this.clco_state = clco_state;
	}
	public int getClco_calo_id() {
		return clco_calo_id;
	}
	public void setClco_calo_id(int clco_calo_id) {
		this.clco_calo_id = clco_calo_id;
	}
}
