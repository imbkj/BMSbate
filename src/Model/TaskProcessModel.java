package Model;

public class TaskProcessModel {
	private int tapr_id;
	private int inttapr_tali_id;
	private int tapr_wfno_id;
	private int tapr_lastid;
	private int tapr_dataid;
	private String tapr_starname;
	private String tapr_starttime;
	private Integer tapr_appointid;
	private String tapr_appointcon;
	private int tapr_appointname;
	private String tapr_remark;
	private String tapr_endname;
	private String tapr_endtime;
	private String tapr_state;
	private int wfno_step;

	public TaskProcessModel() {
		super();
	}

	public TaskProcessModel(int tapr_id, int inttapr_tali_id, int tapr_wfno_id,
			int tapr_lastid, int tapr_dataid, String tapr_starname,
			String tapr_starttime, int tapr_appointname, String tapr_remark,
			String tapr_endname, String tapr_endtime, String tapr_state) {
		super();
		this.tapr_id = tapr_id;
		this.inttapr_tali_id = inttapr_tali_id;
		this.tapr_wfno_id = tapr_wfno_id;
		this.tapr_lastid = tapr_lastid;
		this.tapr_dataid = tapr_dataid;
		this.tapr_starname = tapr_starname;
		this.tapr_starttime = tapr_starttime;
		this.tapr_appointname = tapr_appointname;
		this.tapr_remark = tapr_remark;
		this.tapr_endname = tapr_endname;
		this.tapr_endtime = tapr_endtime;
		this.tapr_state = tapr_state;
	}

	public int getTapr_id() {
		return tapr_id;
	}

	public void setTapr_id(int tapr_id) {
		this.tapr_id = tapr_id;
	}

	public int getInttapr_tali_id() {
		return inttapr_tali_id;
	}

	public void setInttapr_tali_id(int inttapr_tali_id) {
		this.inttapr_tali_id = inttapr_tali_id;
	}

	public int getTapr_wfno_id() {
		return tapr_wfno_id;
	}

	public void setTapr_wfno_id(int tapr_wfno_id) {
		this.tapr_wfno_id = tapr_wfno_id;
	}

	public int getTapr_lastid() {
		return tapr_lastid;
	}

	public void setTapr_lastid(int tapr_lastid) {
		this.tapr_lastid = tapr_lastid;
	}

	public int getTapr_dataid() {
		return tapr_dataid;
	}

	public void setTapr_dataid(int tapr_dataid) {
		this.tapr_dataid = tapr_dataid;
	}

	public String getTapr_starname() {
		return tapr_starname;
	}

	public void setTapr_starname(String tapr_starname) {
		this.tapr_starname = tapr_starname;
	}

	public String getTapr_starttime() {
		return tapr_starttime;
	}

	public void setTapr_starttime(String tapr_starttime) {
		this.tapr_starttime = tapr_starttime;
	}

	public int getTapr_appointname() {
		return tapr_appointname;
	}

	public void setTapr_appointname(int tapr_appointname) {
		this.tapr_appointname = tapr_appointname;
	}

	public String getTapr_remark() {
		return tapr_remark;
	}

	public void setTapr_remark(String tapr_remark) {
		this.tapr_remark = tapr_remark;
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

	public String getTapr_state() {
		return tapr_state;
	}

	public void setTapr_state(String tapr_state) {
		this.tapr_state = tapr_state;
	}

	public int getWfno_step() {
		return wfno_step;
	}

	public void setWfno_step(int wfno_step) {
		this.wfno_step = wfno_step;
	}

	public Integer getTapr_appointid() {
		return tapr_appointid;
	}

	public void setTapr_appointid(Integer tapr_appointid) {
		this.tapr_appointid = tapr_appointid;
	}

	public String getTapr_appointcon() {
		return tapr_appointcon;
	}

	public void setTapr_appointcon(String tapr_appointcon) {
		this.tapr_appointcon = tapr_appointcon;
	}

}
