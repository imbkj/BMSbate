package Model;

import java.util.Date;

public class WfDefinationModel {
	private Integer wfde_id;
	private Integer wfde_wfcl_id;
	private String wfde_code;
	private String wfde_name;
	private String wfde_remark;
	private String wfde_addname;
	private Date wfde_addtime;
	private String wfde_updatename;
	private Date wfde_updatetime;
	private Integer wfde_state;
	private String wfde_stateName;
	private String wfcl_name;
	
	public Integer getWfde_id() {
		return wfde_id;
	}
	public void setWfde_id(Integer wfde_id) {
		this.wfde_id = wfde_id;
	}
	public Integer getWfde_wfcl_id() {
		return wfde_wfcl_id;
	}
	public void setWfde_wfcl_id(Integer wfde_wfcl_id) {
		this.wfde_wfcl_id = wfde_wfcl_id;
	}
	public String getWfde_code() {
		return wfde_code;
	}
	public void setWfde_code(String wfde_code) {
		this.wfde_code = wfde_code;
	}
	public String getWfde_name() {
		return wfde_name;
	}
	public void setWfde_name(String wfde_name) {
		this.wfde_name = wfde_name;
	}
	public String getWfde_remark() {
		return wfde_remark;
	}
	public void setWfde_remark(String wfde_remark) {
		this.wfde_remark = wfde_remark;
	}
	public String getWfde_addname() {
		return wfde_addname;
	}
	public void setWfde_addname(String wfde_addname) {
		this.wfde_addname = wfde_addname;
	}
	
	public Date getWfde_addtime() {
		return wfde_addtime;
	}
	public void setWfde_addtime(Date wfde_addtime) {
		this.wfde_addtime = wfde_addtime;
	}
	public String getWfde_updatename() {
		return wfde_updatename;
	}
	public void setWfde_updatename(String wfde_updatename) {
		this.wfde_updatename = wfde_updatename;
	}
	public Date getWfde_updatetime() {
		return wfde_updatetime;
	}
	public void setWfde_updatetime(Date wfde_updatetime) {
		this.wfde_updatetime = wfde_updatetime;
	}
	public Integer getWfde_state() {
		return wfde_state;
	}
	public void setWfde_state(Integer wfde_state) {
		this.wfde_state = wfde_state;
	}
	public String getWfcl_name() {
		return wfcl_name;
	}
	public void setWfcl_name(String wfcl_name) {
		this.wfcl_name = wfcl_name;
	}
	public String getWfde_stateName() {
		return wfde_stateName;
	}
	public void setWfde_stateName(String wfde_stateName) {
		this.wfde_stateName = wfde_stateName;
	}

}
