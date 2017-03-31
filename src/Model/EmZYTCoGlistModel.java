package Model;

public class EmZYTCoGlistModel {
	private int ezcg_id;
	private int emzt_id;
	private int ezcg_coli_id;
	private int ezcg_state;
	private String ezcg_addname;
	private String ezcg_addtime;
	
	public EmZYTCoGlistModel() {
		super();
	}
	public EmZYTCoGlistModel(int ezcg_id, int emzt_id, int ezcg_coli_id,
			int ezcg_state, String ezcg_addname, String ezcg_addtime) {
		super();
		this.ezcg_id = ezcg_id;
		this.emzt_id = emzt_id;
		this.ezcg_coli_id = ezcg_coli_id;
		this.ezcg_state = ezcg_state;
		this.ezcg_addname = ezcg_addname;
		this.ezcg_addtime = ezcg_addtime;
	}
	public int getEzcg_id() {
		return ezcg_id;
	}
	public void setEzcg_id(int ezcg_id) {
		this.ezcg_id = ezcg_id;
	}
	public int getEmzt_id() {
		return emzt_id;
	}
	public void setEmzt_id(int emzt_id) {
		this.emzt_id = emzt_id;
	}
	public int getEzcg_coli_id() {
		return ezcg_coli_id;
	}
	public void setEzcg_coli_id(int ezcg_coli_id) {
		this.ezcg_coli_id = ezcg_coli_id;
	}
	public int getEzcg_state() {
		return ezcg_state;
	}
	public void setEzcg_state(int ezcg_state) {
		this.ezcg_state = ezcg_state;
	}
	public String getEzcg_addname() {
		return ezcg_addname;
	}
	public void setEzcg_addname(String ezcg_addname) {
		this.ezcg_addname = ezcg_addname;
	}
	public String getEzcg_addtime() {
		return ezcg_addtime;
	}
	public void setEzcg_addtime(String ezcg_addtime) {
		this.ezcg_addtime = ezcg_addtime;
	}

	
}
