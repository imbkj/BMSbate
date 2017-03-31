package Model;

public class CoFormulaInfoModel {
	private int cfin_id;
	private int cid;
	private String csii_itemid;
	private String cfin_name;
	private int cfin_state;
	private int cfin_delete;
	private String cfin_remark;
	private String cfin_addname;
	private String cfin_addtime;
	
	public CoFormulaInfoModel() {
		super();
	}
	
	

	public CoFormulaInfoModel(int cfin_id, int cid, String csii_itemid,
			String cfin_name, int cfin_state, int cfin_delete,
			String cfin_remark, String cfin_addname, String cfin_addtime) {
		super();
		this.cfin_id = cfin_id;
		this.cid = cid;
		this.csii_itemid = csii_itemid;
		this.cfin_name = cfin_name;
		this.cfin_state = cfin_state;
		this.cfin_delete = cfin_delete;
		this.cfin_remark = cfin_remark;
		this.cfin_addname = cfin_addname;
		this.cfin_addtime = cfin_addtime;
	}



	public int getCfin_id() {
		return cfin_id;
	}

	public void setCfin_id(int cfin_id) {
		this.cfin_id = cfin_id;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getCsii_itemid() {
		return csii_itemid;
	}

	public void setCsii_itemid(String csii_itemid) {
		this.csii_itemid = csii_itemid;
	}

	public String getCfin_name() {
		return cfin_name;
	}

	public void setCfin_name(String cfin_name) {
		this.cfin_name = cfin_name;
	}

	public int getCfin_state() {
		return cfin_state;
	}

	public void setCfin_state(int cfin_state) {
		this.cfin_state = cfin_state;
	}

	public int getCfin_delete() {
		return cfin_delete;
	}

	public void setCfin_delete(int cfin_delete) {
		this.cfin_delete = cfin_delete;
	}

	public String getCfin_remark() {
		return cfin_remark;
	}

	public void setCfin_remark(String cfin_remark) {
		this.cfin_remark = cfin_remark;
	}

	public String getCfin_addname() {
		return cfin_addname;
	}

	public void setCfin_addname(String cfin_addname) {
		this.cfin_addname = cfin_addname;
	}

	public String getCfin_addtime() {
		return cfin_addtime;
	}

	public void setCfin_addtime(String cfin_addtime) {
		this.cfin_addtime = cfin_addtime;
	}
	
	
}
