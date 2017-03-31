package Model;

public class WfNodeModel {
	private int wfno_id;
	private int wfno_wfde_id;
	private String wfno_name;
	private int wfno_step;
	private int wfno_maxday;
	private String wfno_selectuser;
	private int wfno_runtype;
	private String wfno_runprocedure;
	private String wfno_url;
	private int wfno_ifview;
	private int wfno_ifskip;
	private int wfno_ifreturn;
	private int wfno_ifstop;
	private int wfno_ifrevoke;
	private int wfno_ifhavechild;
	private String wfno_remark;
	private String wfno_addname;
	private String wfno_addtime;
	private String wfcl_updatename;
	private String wfcl_updatetime;
	private int wfno_state;
	private int nowNode;

	public WfNodeModel() {
		super();
	}

	public WfNodeModel(int wfno_id, int wfno_wfde_id, String wfno_name,
			int wfno_step, int wfno_maxday, String wfno_selectuser,
			int wfno_runtype, String wfno_runprocedure, String wfno_url,
			int wfno_ifview, int wfno_ifskip, int wfno_ifreturn,
			int wfno_ifstop, int wfno_ifrevoke, int wfno_ifhavechild,
			String wfno_remark, String wfno_addname, String wfno_addtime,
			String wfcl_updatename, String wfcl_updatetime, int wfno_state) {
		super();
		this.wfno_id = wfno_id;
		this.wfno_wfde_id = wfno_wfde_id;
		this.wfno_name = wfno_name;
		this.wfno_step = wfno_step;
		this.wfno_maxday = wfno_maxday;
		this.wfno_selectuser = wfno_selectuser;
		this.wfno_runtype = wfno_runtype;
		this.wfno_runprocedure = wfno_runprocedure;
		this.wfno_url = wfno_url;
		this.wfno_ifview = wfno_ifview;
		this.wfno_ifskip = wfno_ifskip;
		this.wfno_ifreturn = wfno_ifreturn;
		this.wfno_ifstop = wfno_ifstop;
		this.wfno_ifrevoke = wfno_ifrevoke;
		this.wfno_ifhavechild = wfno_ifhavechild;
		this.wfno_remark = wfno_remark;
		this.wfno_addname = wfno_addname;
		this.wfno_addtime = wfno_addtime;
		this.wfcl_updatename = wfcl_updatename;
		this.wfcl_updatetime = wfcl_updatetime;
		this.wfno_state = wfno_state;
	}

	public int getWfno_id() {
		return wfno_id;
	}

	public void setWfno_id(int wfno_id) {
		this.wfno_id = wfno_id;
	}

	public int getWfno_wfde_id() {
		return wfno_wfde_id;
	}

	public void setWfno_wfde_id(int wfno_wfde_id) {
		this.wfno_wfde_id = wfno_wfde_id;
	}

	public String getWfno_name() {
		return wfno_name;
	}

	public void setWfno_name(String wfno_name) {
		this.wfno_name = wfno_name;
	}

	public int getWfno_step() {
		return wfno_step;
	}

	public void setWfno_step(int wfno_step) {
		this.wfno_step = wfno_step;
	}

	public int getWfno_maxday() {
		return wfno_maxday;
	}

	public void setWfno_maxday(int wfno_maxday) {
		this.wfno_maxday = wfno_maxday;
	}

	public String getWfno_selectuser() {
		return wfno_selectuser;
	}

	public void setWfno_selectuser(String wfno_selectuser) {
		this.wfno_selectuser = wfno_selectuser;
	}

	public int getWfno_runtype() {
		return wfno_runtype;
	}

	public void setWfno_runtype(int wfno_runtype) {
		this.wfno_runtype = wfno_runtype;
	}

	public String getWfno_runprocedure() {
		return wfno_runprocedure;
	}

	public void setWfno_runprocedure(String wfno_runprocedure) {
		this.wfno_runprocedure = wfno_runprocedure;
	}

	public String getWfno_url() {
		return wfno_url;
	}

	public void setWfno_url(String wfno_url) {
		this.wfno_url = wfno_url;
	}

	public int getWfno_ifview() {
		return wfno_ifview;
	}

	public void setWfno_ifview(int wfno_ifview) {
		this.wfno_ifview = wfno_ifview;
	}

	public int getWfno_ifskip() {
		return wfno_ifskip;
	}

	public void setWfno_ifskip(int wfno_ifskip) {
		this.wfno_ifskip = wfno_ifskip;
	}

	public int getWfno_ifreturn() {
		return wfno_ifreturn;
	}

	public void setWfno_ifreturn(int wfno_ifreturn) {
		this.wfno_ifreturn = wfno_ifreturn;
	}

	public int getWfno_ifstop() {
		return wfno_ifstop;
	}

	public void setWfno_ifstop(int wfno_ifstop) {
		this.wfno_ifstop = wfno_ifstop;
	}

	public int getWfno_ifrevoke() {
		return wfno_ifrevoke;
	}

	public void setWfno_ifrevoke(int wfno_ifrevoke) {
		this.wfno_ifrevoke = wfno_ifrevoke;
	}

	public int getWfno_ifhavechild() {
		return wfno_ifhavechild;
	}

	public void setWfno_ifhavechild(int wfno_ifhavechild) {
		this.wfno_ifhavechild = wfno_ifhavechild;
	}

	public String getWfno_remark() {
		return wfno_remark;
	}

	public void setWfno_remark(String wfno_remark) {
		this.wfno_remark = wfno_remark;
	}

	public String getWfno_addname() {
		return wfno_addname;
	}

	public void setWfno_addname(String wfno_addname) {
		this.wfno_addname = wfno_addname;
	}

	public String getWfno_addtime() {
		return wfno_addtime;
	}

	public void setWfno_addtime(String wfno_addtime) {
		this.wfno_addtime = wfno_addtime;
	}

	public String getWfcl_updatename() {
		return wfcl_updatename;
	}

	public void setWfcl_updatename(String wfcl_updatename) {
		this.wfcl_updatename = wfcl_updatename;
	}

	public String getWfcl_updatetime() {
		return wfcl_updatetime;
	}

	public void setWfcl_updatetime(String wfcl_updatetime) {
		this.wfcl_updatetime = wfcl_updatetime;
	}

	public int getWfno_state() {
		return wfno_state;
	}

	public void setWfno_state(int wfno_state) {
		this.wfno_state = wfno_state;
	}

	public int getNowNode() {
		return nowNode;
	}

	public void setNowNode(int nowNode) {
		this.nowNode = nowNode;
	}

}
