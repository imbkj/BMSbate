package Model;

public class EmSalaryBaseSel_viewModel {
	private int esda_id;
	private int ownmonth;
	private int cid;
	private String coba_shortname;
	private String emba_name;
	private int gid;
	private String esda_nationality;
	private String esda_bank_account;
	private String esda_bank;
	private String esda_ba_name;
	private int esda_usage_type;
	private int cfin_id;
	private String csii_itemid;
	private String cfin_name;
	private String esda_remark;
	private String esda_fd_remark;
	private int esda_payment_state;
	private String esda_payment_statestr;
	private String esda_usage_typestr;
	private String esda_rp_reason;
	private int esda_history_state;
	private int esda_tapr_id;

	public EmSalaryBaseSel_viewModel() {
		super();
	}

	
	public EmSalaryBaseSel_viewModel(int esda_id, int ownmonth, int cid,
			String coba_shortname, String emba_name, int gid,
			String esda_nationality, String esda_bank_account,
			String esda_bank, String esda_ba_name, int esda_usage_type,
			int cfin_id, String csii_itemid, String cfin_name,
			String esda_remark, String esda_fd_remark, int esda_payment_state,
			String esda_rp_reason,int esda_history_state,int esda_tapr_id) {
		super();
		this.esda_id = esda_id;
		this.ownmonth = ownmonth;
		this.cid = cid;
		this.coba_shortname = coba_shortname;
		this.emba_name = emba_name;
		this.gid = gid;
		this.esda_nationality = esda_nationality;
		this.esda_bank_account = esda_bank_account;
		this.esda_bank = esda_bank;
		this.esda_ba_name = esda_ba_name;
		this.esda_usage_type = esda_usage_type;
		this.cfin_id = cfin_id;
		this.csii_itemid = csii_itemid;
		this.cfin_name = cfin_name;
		this.esda_remark = esda_remark;
		this.esda_fd_remark = esda_fd_remark;
		this.esda_payment_state = esda_payment_state;
		this.esda_rp_reason = esda_rp_reason;
		this.esda_history_state=esda_history_state;
		this.esda_tapr_id=esda_tapr_id;

		switch (esda_usage_type) {
		case 0:
			this.esda_usage_typestr = "工资";
			break;
		case 1:
			this.esda_usage_typestr = "报销";
			break;
		case 2:
			this.esda_usage_typestr = "住房返还";
			break;

		default:
			this.esda_usage_typestr = "";
			break;
		}

		switch (esda_payment_state) {
		case 0:
			this.esda_payment_statestr = "未审核";
			break;
		case 1:
			this.esda_payment_statestr = "已审核";
			break;
		case 2:
			this.esda_payment_statestr = "已发放";
			break;
		case 3:
			this.esda_payment_statestr = "待确认";
			break;
		default:
			this.esda_payment_statestr = "";
			break;
		}
	}



	public EmSalaryBaseSel_viewModel(int esda_id, int ownmonth, int cid,
			String coba_shortname, String emba_name, int gid,
			String esda_nationality, String esda_bank_account,
			String esda_bank, String esda_ba_name, int esda_usage_type,
			int cfin_id, String csii_itemid, String cfin_name,
			String esda_remark, String esda_fd_remark, int esda_payment_state) {
		super();
		this.esda_id = esda_id;
		this.ownmonth = ownmonth;
		this.cid = cid;
		this.coba_shortname = coba_shortname;
		this.emba_name = emba_name;
		this.gid = gid;
		this.esda_nationality = esda_nationality;
		this.esda_bank_account = esda_bank_account;
		this.esda_bank = esda_bank;
		this.esda_ba_name = esda_ba_name;
		this.esda_usage_type = esda_usage_type;
		this.cfin_id = cfin_id;
		this.csii_itemid = csii_itemid;
		this.cfin_name = cfin_name;
		this.esda_remark = esda_remark;
		this.esda_fd_remark = esda_fd_remark;
		this.esda_payment_state = esda_payment_state;

		switch (esda_usage_type) {
		case 0:
			this.esda_usage_typestr = "工资";
			break;
		case 1:
			this.esda_usage_typestr = "报销";
			break;
		case 2:
			this.esda_usage_typestr = "住房返还";
			break;

		default:
			this.esda_usage_typestr = "";
			break;
		}

		switch (esda_payment_state) {
		case 0:
			this.esda_payment_statestr = "未审核";
			break;
		case 1:
			this.esda_payment_statestr = "已审核";
			break;
		case 2:
			this.esda_payment_statestr = "已发放";
			break;
		case 3:
			this.esda_payment_statestr = "待确认";
			break;
		default:
			this.esda_payment_statestr = "";
			break;
		}
	}
	
	

	public EmSalaryBaseSel_viewModel(int esda_id, String emba_name,
			int esda_usage_type) {
		super();
		this.esda_id = esda_id;
		this.emba_name = emba_name;
		this.esda_usage_type = esda_usage_type;
		
		switch (esda_usage_type) {
		case 0:
			this.esda_usage_typestr = "工资";
			break;
		case 1:
			this.esda_usage_typestr = "报销";
			break;
		case 2:
			this.esda_usage_typestr = "住房返还";
			break;

		default:
			this.esda_usage_typestr = "";
			break;
		}
	}

	
	public int getEsda_tapr_id() {
		return esda_tapr_id;
	}

	public void setEsda_tapr_id(int esda_tapr_id) {
		this.esda_tapr_id = esda_tapr_id;
	}

	public int getEsda_history_state() {
		return esda_history_state;
	}

	public void setEsda_history_state(int esda_history_state) {
		this.esda_history_state = esda_history_state;
	}


	public String getEsda_rp_reason() {
		return esda_rp_reason;
	}

	public void setEsda_rp_reason(String esda_rp_reason) {
		this.esda_rp_reason = esda_rp_reason;
	}

	public int getEsda_id() {
		return esda_id;
	}

	public void setEsda_id(int esda_id) {
		this.esda_id = esda_id;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getEmba_name() {
		return emba_name;
	}

	public void setEmba_name(String emba_name) {
		this.emba_name = emba_name;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public String getEsda_nationality() {
		return esda_nationality;
	}

	public void setEsda_nationality(String esda_nationality) {
		this.esda_nationality = esda_nationality;
	}

	public String getEsda_bank_account() {
		return esda_bank_account;
	}

	public void setEsda_bank_account(String esda_bank_account) {
		this.esda_bank_account = esda_bank_account;
	}

	public String getEsda_bank() {
		return esda_bank;
	}

	public void setEsda_bank(String esda_bank) {
		this.esda_bank = esda_bank;
	}

	public String getEsda_ba_name() {
		return esda_ba_name;
	}

	public void setEsda_ba_name(String esda_ba_name) {
		this.esda_ba_name = esda_ba_name;
	}

	public int getEsda_usage_type() {
		return esda_usage_type;
	}

	public void setEsda_usage_type(int esda_usage_type) {
		this.esda_usage_type = esda_usage_type;
		switch (esda_usage_type) {
		case 0:
			this.esda_usage_typestr = "工资";
			break;
		case 1:
			this.esda_usage_typestr = "报销";
			break;
		case 2:
			this.esda_usage_typestr = "住房返还";
			break;

		default:
			this.esda_usage_typestr = "";
			break;
		}
	}

	public int getCfin_id() {
		return cfin_id;
	}

	public void setCfin_id(int cfin_id) {
		this.cfin_id = cfin_id;
	}

	public String getCsii_itemid() {
		return csii_itemid;
	}

	public void setCsii_itemid(String csii_itemid) {
		this.csii_itemid = csii_itemid;
	}

	public String getEsda_remark() {
		return esda_remark;
	}

	public void setEsda_remark(String esda_remark) {
		this.esda_remark = esda_remark;
	}

	public String getEsda_fd_remark() {
		return esda_fd_remark;
	}

	public void setEsda_fd_remark(String esda_fd_remark) {
		this.esda_fd_remark = esda_fd_remark;
	}

	public String getEsda_usage_typestr() {
		return esda_usage_typestr;
	}

	public void setEsda_usage_typestr(String esda_usage_typestr) {
		this.esda_usage_typestr = esda_usage_typestr;
	}

	public String getCoba_shortname() {
		return coba_shortname;
	}

	public void setCoba_shortname(String coba_shortname) {
		this.coba_shortname = coba_shortname;
	}

	public int getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(int ownmonth) {
		this.ownmonth = ownmonth;
	}

	public int getEsda_payment_state() {
		return esda_payment_state;
	}

	public void setEsda_payment_state(int esda_payment_state) {
		this.esda_payment_state = esda_payment_state;
	}

	public String getEsda_payment_statestr() {
		return esda_payment_statestr;
	}

	public void setEsda_payment_statestr(String esda_payment_statestr) {
		this.esda_payment_statestr = esda_payment_statestr;
	}

	public String getCfin_name() {
		return cfin_name;
	}

	public void setCfin_name(String cfin_name) {
		this.cfin_name = cfin_name;
	}

}
