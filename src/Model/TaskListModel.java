package Model;

import java.util.Date;
import java.util.Map;

import Util.DateStringChange;

public class TaskListModel {
	private Integer wfcl_id;
	private String wfcl_name;
	private Integer wfde_id;
	private String wfde_name;
	private Integer tacl_id;
	private Integer tali_tacl_id;
	private String tacl_name;
	private Integer tali_id;
	private String tali_name;
	private Date tali_addtime;
	private String tali_addtimeStr;
	private String tali_addname;
	private Integer tali_state;
	private String wfno_name;
	private Integer tapr_id;
	private String state;
	private String taskname;
	private Map<String, String> searchConMap;

	public TaskListModel() {

	}

	public TaskListModel(String taskname, String state, String tali_name,
			Integer tali_id) {
		super();
		this.taskname = taskname;
		this.state = state;
		this.tali_name = tali_name;
		this.tali_id = tali_id;
	}

	public TaskListModel(String taskname, String state, String tali_name) {
		super();
		this.taskname = taskname;
		this.state = state;
		this.tali_name = tali_name;
	}

	public String getTaskname() {
		return taskname;
	}

	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTali_name() {
		return tali_name;
	}

	public void setTali_name(String tali_name) {
		this.tali_name = tali_name;
	}

	public Integer getTali_id() {
		return tali_id;
	}

	public void setTali_id(Integer tali_id) {
		this.tali_id = tali_id;
	}

	public Date getTali_addtime() {
		return tali_addtime;
	}

	public void setTali_addtime(Date tali_addtime) {
		this.tali_addtime = tali_addtime;
	}

	public String getTali_addtimeStr() {
		return tali_addtimeStr;
	}

	public void setTali_addtimeStr(String tali_addtimeStr) {
		this.tali_addtimeStr = tali_addtimeStr;
		this.tali_addtime = DateStringChange.StringtoDate(tali_addtimeStr,
				"yyyy-MM-dd HH:mm:ss");
	}

	public Integer getTali_state() {
		return tali_state;
	}

	public void setTali_state(Integer tali_state) {
		this.tali_state = tali_state;
		switch (tali_state) {
		case 1:
			this.state = "处理中";
			break;
		case 2:
			this.state = "已完成";
			break;
		case 3:
			this.state = "已终止";
			break;
		}
	}

	public String getTacl_name() {
		return tacl_name;
	}

	public void setTacl_name(String tacl_name) {
		this.tacl_name = tacl_name;
	}

	public String getWfno_name() {
		return wfno_name;
	}

	public void setWfno_name(String wfno_name) {
		this.wfno_name = wfno_name;
	}

	public Integer getTapr_id() {
		return tapr_id;
	}

	public void setTapr_id(Integer tapr_id) {
		this.tapr_id = tapr_id;
	}

	public Integer getWfcl_id() {
		return wfcl_id;
	}

	public void setWfcl_id(Integer wfcl_id) {
		this.wfcl_id = wfcl_id;
	}

	public String getWfcl_name() {
		return wfcl_name;
	}

	public void setWfcl_name(String wfcl_name) {
		this.wfcl_name = wfcl_name;
	}

	public Integer getWfde_id() {
		return wfde_id;
	}

	public void setWfde_id(Integer wfde_id) {
		this.wfde_id = wfde_id;
	}

	public String getWfde_name() {
		return wfde_name;
	}

	public void setWfde_name(String wfde_name) {
		this.wfde_name = wfde_name;
	}

	public Integer getTacl_id() {
		return tacl_id;
	}

	public void setTacl_id(Integer tacl_id) {
		this.tacl_id = tacl_id;
	}

	public String getTali_addname() {
		return tali_addname;
	}

	public void setTali_addname(String tali_addname) {
		this.tali_addname = tali_addname;
	}

	public Map<String, String> getSearchConMap() {
		return searchConMap;
	}

	public void setSearchConMap(Map<String, String> searchConMap) {
		this.searchConMap = searchConMap;
	}

	public Integer getTali_tacl_id() {
		return tali_tacl_id;
	}

	public void setTali_tacl_id(Integer tali_tacl_id) {
		this.tali_tacl_id = tali_tacl_id;
	}

}
