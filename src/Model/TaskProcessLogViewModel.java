package Model;

public class TaskProcessLogViewModel {
	private Integer tapl_id;
	private Integer tapr_id;
	private Integer tali_id;
	private Integer tacl_id;
	private String tacl_name;
	private String tali_name;
	private String wfno_name;
	private String tapl_content;
	private String tapl_remark;
	private String tapl_username;
	private String tapl_addtime;
	private Integer tapr_state;
	private String tapr_statename;
	private Integer wfno_step;
	private Integer tapl_datatableid;
	private Integer wfno_ifrevoke;
	private String wfno_runprocedure;
	private Integer tapl_state;
	private Integer tali_urgentState;
	private Integer tapr_urgentState;
	private String tapr_endname;
	private String tapr_endtime;
	private String tapr_remark;

	public Integer getTapr_state() {
		return tapr_state;
	}

	public void setTapr_state(Integer tapr_state) {
		this.tapr_state = tapr_state;
		switch (tapr_state) {
		case 0:
			tapr_statename = "无效";
			break;
		case 1:
			tapr_statename = "正在执行";
			break;
		case 2:
			tapr_statename = "已通过";
			break;
		case 3:
			tapr_statename = "退回";
			break;
		case 4:
			tapr_statename = "被撤回";
			break;
		case 5:
			tapr_statename = "终止";
			break;
		case 6:
			tapr_statename = "挂起";
			break;
		default:
			tapr_statename = "未知";
			break;
		}
	}

	public Integer getTapl_id() {
		return tapl_id;
	}

	public void setTapl_id(Integer tapl_id) {
		this.tapl_id = tapl_id;
	}

	public Integer getTapr_id() {
		return tapr_id;
	}

	public void setTapr_id(Integer tapr_id) {
		this.tapr_id = tapr_id;
	}

	public Integer getTali_id() {
		return tali_id;
	}

	public void setTali_id(Integer tali_id) {
		this.tali_id = tali_id;
	}

	public Integer getTacl_id() {
		return tacl_id;
	}

	public void setTacl_id(Integer tacl_id) {
		this.tacl_id = tacl_id;
	}

	public String getTacl_name() {
		return tacl_name;
	}

	public void setTacl_name(String tacl_name) {
		this.tacl_name = tacl_name;
	}

	public String getTali_name() {
		return tali_name;
	}

	public void setTali_name(String tali_name) {
		this.tali_name = tali_name;
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

	public String getTapr_statename() {
		return tapr_statename;
	}

	public void setTapr_statename(String tapr_statename) {
		this.tapr_statename = tapr_statename;
	}

	public String getWfno_name() {
		return wfno_name;
	}

	public void setWfno_name(String wfno_name) {
		this.wfno_name = wfno_name;
	}

	public Integer getWfno_step() {
		return wfno_step;
	}

	public void setWfno_step(Integer wfno_step) {
		this.wfno_step = wfno_step;
	}

	public Integer getTapl_datatableid() {
		return tapl_datatableid;
	}

	public void setTapl_datatableid(Integer tapl_datatableid) {
		this.tapl_datatableid = tapl_datatableid;
	}

	public Integer getWfno_ifrevoke() {
		return wfno_ifrevoke;
	}

	public void setWfno_ifrevoke(Integer wfno_ifrevoke) {
		this.wfno_ifrevoke = wfno_ifrevoke;
	}

	public String getWfno_runprocedure() {
		return wfno_runprocedure;
	}

	public void setWfno_runprocedure(String wfno_runprocedure) {
		this.wfno_runprocedure = wfno_runprocedure;
	}

	public Integer getTapl_state() {
		return tapl_state;
	}

	public void setTapl_state(Integer tapl_state) {
		this.tapl_state = tapl_state;
	}

	public Integer getTali_urgentState() {
		return tali_urgentState;
	}

	public void setTali_urgentState(Integer tali_urgentState) {
		this.tali_urgentState = tali_urgentState;
	}

	public Integer getTapr_urgentState() {
		return tapr_urgentState;
	}

	public void setTapr_urgentState(Integer tapr_urgentState) {
		this.tapr_urgentState = tapr_urgentState;
	}

	public String getTapr_endname() {
		return tapr_endname;
	}

	public void setTapr_endname(String tapr_endname) {
		this.tapr_endname = tapr_endname;
	}

	public String getTapr_endtime() {
		return tapr_endtime;
	}

	public void setTapr_endtime(String tapr_endtime) {
		this.tapr_endtime = tapr_endtime;
	}

	public String getTapr_remark() {
		return tapr_remark;
	}

	public void setTapr_remark(String tapr_remark) {
		this.tapr_remark = tapr_remark;
	}

}
