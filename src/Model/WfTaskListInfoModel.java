package Model;

import java.util.Map;

public class WfTaskListInfoModel {
	// 任务单类型字段
	int tacl_id;
	String tacl_name;
	// 任务单字段
	int tali_id;
	String tali_name;
	String tali_addname;
	String tali_addtime;
	String tali_searchCon;
	// 任务流程字段
	int tapr_id;
	// 任务单搜索条件Map
	Map<String, String> searchConMap;
	private int tali_urgentState;
	private int tapr_urgentState;
	private int tapr_state;
	private int tapr_lastState;

	public WfTaskListInfoModel() {
		super();
	}

	public WfTaskListInfoModel(int tacl_id, String tacl_name, int tali_id,
			String tali_name, String tali_addname, String tali_addtime,
			int tapr_id) {
		super();
		this.tacl_id = tacl_id;
		this.tacl_name = tacl_name;
		this.tali_id = tali_id;
		this.tali_name = tali_name;
		this.tali_addname = tali_addname;
		this.tali_addtime = tali_addtime;
		this.tapr_id = tapr_id;
	}

	public int getTacl_id() {
		return tacl_id;
	}

	public void setTacl_id(int tacl_id) {
		this.tacl_id = tacl_id;
	}

	public String getTacl_name() {
		return tacl_name;
	}

	public void setTacl_name(String tacl_name) {
		this.tacl_name = tacl_name;
	}

	public int getTali_id() {
		return tali_id;
	}

	public void setTali_id(int tali_id) {
		this.tali_id = tali_id;
	}

	public String getTali_name() {
		return tali_name;
	}

	public void setTali_name(String tali_name) {
		this.tali_name = tali_name;
	}

	public String getTali_addname() {
		return tali_addname;
	}

	public void setTali_addname(String tali_addname) {
		this.tali_addname = tali_addname;
	}

	public String getTali_addtime() {
		return tali_addtime;
	}

	public void setTali_addtime(String tali_addtime) {
		this.tali_addtime = tali_addtime;
	}

	public int getTapr_id() {
		return tapr_id;
	}

	public void setTapr_id(int tapr_id) {
		this.tapr_id = tapr_id;
	}

	public Map<String, String> getSearchConMap() {
		return searchConMap;
	}

	public void setSearchConMap(Map<String, String> searchConMap) {
		this.searchConMap = searchConMap;
	}

	public int getTali_urgentState() {
		return tali_urgentState;
	}

	public void setTali_urgentState(int tali_urgentState) {
		this.tali_urgentState = tali_urgentState;
	}

	public int getTapr_urgentState() {
		return tapr_urgentState;
	}

	public void setTapr_urgentState(int tapr_urgentState) {
		this.tapr_urgentState = tapr_urgentState;
	}

	public int getTapr_state() {
		return tapr_state;
	}

	public void setTapr_state(int tapr_state) {
		this.tapr_state = tapr_state;
	}

	public int getTapr_lastState() {
		return tapr_lastState;
	}

	public void setTapr_lastState(int tapr_lastState) {
		this.tapr_lastState = tapr_lastState;
	}

	public String getTali_searchCon() {
		return tali_searchCon;
	}

	public void setTali_searchCon(String tali_searchCon) {
		this.tali_searchCon = tali_searchCon;
	}

}
