package Model;

public class TaskProcessLogModel {
	private int tapl_id;
	private int tapl_tapr_id;
	private String tapl_datatable;
	private int tapl_datatableid;
	private String tapl_content;
	private String tapl_remark;
	private String tapl_username;
	private String tapl_addtime;
	private String tapl_state;

	public TaskProcessLogModel() {
		super();
	}

	public TaskProcessLogModel(int tapl_id, int tapl_tapr_id,
			String tapl_datatable, int tapl_datatableid, String tapl_content,
			String tapl_remark, String tapl_username, String tapl_addtime,
			String tapl_state) {
		super();
		this.tapl_id = tapl_id;
		this.tapl_tapr_id = tapl_tapr_id;
		this.tapl_datatable = tapl_datatable;
		this.tapl_datatableid = tapl_datatableid;
		this.tapl_content = tapl_content;
		this.tapl_remark = tapl_remark;
		this.tapl_username = tapl_username;
		this.tapl_addtime = tapl_addtime;
		this.tapl_state = tapl_state;
	}

	public int getTapl_id() {
		return tapl_id;
	}

	public void setTapl_id(int tapl_id) {
		this.tapl_id = tapl_id;
	}

	public int getTapl_tapr_id() {
		return tapl_tapr_id;
	}

	public void setTapl_tapr_id(int tapl_tapr_id) {
		this.tapl_tapr_id = tapl_tapr_id;
	}

	public String getTapl_datatable() {
		return tapl_datatable;
	}

	public void setTapl_datatable(String tapl_datatable) {
		this.tapl_datatable = tapl_datatable;
	}

	public int getTapl_datatableid() {
		return tapl_datatableid;
	}

	public void setTapl_datatableid(int tapl_datatableid) {
		this.tapl_datatableid = tapl_datatableid;
	}

	public String getTapl_content() {
		return tapl_content;
	}

	public void setTapl_content(String tapl_content) {
		this.tapl_content = tapl_content;
	}

	public String getTapl_remark() {
		return tapl_remark;
	}

	public void setTapl_remark(String tapl_remark) {
		this.tapl_remark = tapl_remark;
	}

	public String getTapl_username() {
		return tapl_username;
	}

	public void setTapl_username(String tapl_username) {
		this.tapl_username = tapl_username;
	}

	public String getTapl_addtime() {
		return tapl_addtime;
	}

	public void setTapl_addtime(String tapl_addtime) {
		this.tapl_addtime = tapl_addtime;
	}

	public String getTapl_state() {
		return tapl_state;
	}

	public void setTapl_state(String tapl_state) {
		this.tapl_state = tapl_state;
	}

}
