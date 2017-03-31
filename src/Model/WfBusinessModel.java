package Model;

public class WfBusinessModel {
	private int wfbu_id;
	private String wfbu_name;
	private String wfbu_remark;
	private String wfbu_addname;
	private String wfbu_addtime;
	private int wfbu_state;
	private String wfbu_statestr;

	public WfBusinessModel() {
		super();
	}

	public WfBusinessModel(int wfbu_id, String wfbu_name, String wfbu_remark,
			String wfbu_addname, String wfbu_addtime, int wfbu_state) {
		super();
		this.wfbu_id = wfbu_id;
		this.wfbu_name = wfbu_name;
		this.wfbu_remark = wfbu_remark;
		this.wfbu_addname = wfbu_addname;
		this.wfbu_addtime = wfbu_addtime;
		this.wfbu_state = wfbu_state;

		switch (wfbu_state) {
		case 1:
			this.wfbu_statestr = "有效";
			break;
		case 0:
			this.wfbu_statestr = "无效";
			break;
		default:
			this.wfbu_statestr = "";
			break;
		}
	}

	public int getWfbu_id() {
		return wfbu_id;
	}

	public void setWfbu_id(int wfbu_id) {
		this.wfbu_id = wfbu_id;
	}

	public String getWfbu_name() {
		return wfbu_name;
	}

	public void setWfbu_name(String wfbu_name) {
		this.wfbu_name = wfbu_name;
	}

	public String getWfbu_remark() {
		return wfbu_remark;
	}

	public void setWfbu_remark(String wfbu_remark) {
		this.wfbu_remark = wfbu_remark;
	}

	public String getWfbu_addname() {
		return wfbu_addname;
	}

	public void setWfbu_addname(String wfbu_addname) {
		this.wfbu_addname = wfbu_addname;
	}

	public String getWfbu_addtime() {
		return wfbu_addtime;
	}

	public void setWfbu_addtime(String wfbu_addtime) {
		this.wfbu_addtime = wfbu_addtime;
	}

	public int getWfbu_state() {
		return wfbu_state;
	}

	public void setWfbu_state(int wfbu_state) {
		this.wfbu_state = wfbu_state;
	}

	public String getWfbu_statestr() {
		return wfbu_statestr;
	}

	public void setWfbu_statestr(String wfbu_statestr) {
		this.wfbu_statestr = wfbu_statestr;
	}

}
