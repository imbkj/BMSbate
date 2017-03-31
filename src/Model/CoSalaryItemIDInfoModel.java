package Model;

public class CoSalaryItemIDInfoModel {
	private int csii_id;
	private String csii_itemid;
	private int cid;
	private int ownmonth;
	private int csii_edit_state;
	private String csii_addname;
	private String csii_addtime;
	private int csii_tapr_id;
	
	
	
	public CoSalaryItemIDInfoModel() {
		super();
	}
	
	
	public CoSalaryItemIDInfoModel(int csii_id, String csii_itemid, int cid,
			int ownmonth, int csii_edit_state, String csii_addname,
			String csii_addtime, int csii_tapr_id) {
		super();
		this.csii_id = csii_id;
		this.csii_itemid = csii_itemid;
		this.cid = cid;
		this.ownmonth = ownmonth;
		this.csii_edit_state = csii_edit_state;
		this.csii_addname = csii_addname;
		this.csii_addtime = csii_addtime;
		this.csii_tapr_id = csii_tapr_id;
	}


	public int getCsii_tapr_id() {
		return csii_tapr_id;
	}
	public void setCsii_tapr_id(int csii_tapr_id) {
		this.csii_tapr_id = csii_tapr_id;
	}
	public int getCsii_id() {
		return csii_id;
	}
	public void setCsii_id(int csii_id) {
		this.csii_id = csii_id;
	}
	public String getCsii_itemid() {
		return csii_itemid;
	}
	public void setCsii_itemid(String csii_itemid) {
		this.csii_itemid = csii_itemid;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public int getOwnmonth() {
		return ownmonth;
	}
	public void setOwnmonth(int ownmonth) {
		this.ownmonth = ownmonth;
	}
	public int getCsii_edit_state() {
		return csii_edit_state;
	}
	public void setCsii_edit_state(int csii_edit_state) {
		this.csii_edit_state = csii_edit_state;
	}
	public String getCsii_addname() {
		return csii_addname;
	}
	public void setCsii_addname(String csii_addname) {
		this.csii_addname = csii_addname;
	}
	public String getCsii_addtime() {
		return csii_addtime;
	}
	public void setCsii_addtime(String csii_addtime) {
		this.csii_addtime = csii_addtime;
	}
	
	
}
