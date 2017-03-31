package Model;

public class EmbaseContractDetailModel {
	// 公司编号
	private int cid;
	// 员工编号
	private int gid;
	//文件表主键
	private int puof_id;
	// 公司名
	private String coba_company;
	// 员工姓名
	private String emba_name;
	// 身份证
	private String emba_idcard;
	// 客服
	private String coba_client;
	// 劳动合同终止时间
	private String ebcc_end_date;
	// 添加人
	private String puof_addname;
	// 添加时间
	private String puof_addtime;
	// 劳动合同状态
	private String ebcc_change;
	// 审核状态
	private String puof_state;
	// 审核人
	private String puof_editname;
	// 审核时间
	private String puof_edittime;
	// 劳动合同详细
	private String puof_url;

	
	public int getPuof_id() {
		return puof_id;
	}

	public void setPuof_id(int puof_id) {
		this.puof_id = puof_id;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public String getCoba_company() {
		return coba_company;
	}

	public void setCoba_company(String coba_company) {
		this.coba_company = coba_company;
	}

	public String getEmba_name() {
		return emba_name;
	}

	public void setEmba_name(String emba_name) {
		this.emba_name = emba_name;
	}

	public String getEmba_idcard() {
		return emba_idcard;
	}

	public void setEmba_idcard(String emba_idcard) {
		this.emba_idcard = emba_idcard;
	}

	public String getCoba_client() {
		return coba_client;
	}

	public void setCoba_client(String coba_client) {
		this.coba_client = coba_client;
	}

	public String getEbcc_end_date() {
		return ebcc_end_date;
	}

	public void setEbcc_end_date(String ebcc_end_date) {
		this.ebcc_end_date = ebcc_end_date;
	}

	public String getPuof_addname() {
		return puof_addname;
	}

	public void setPuof_addname(String puof_addname) {
		this.puof_addname = puof_addname;
	}

	public String getPuof_addtime() {
		return puof_addtime;
	}

	public void setPuof_addtime(String puof_addtime) {
		this.puof_addtime = puof_addtime;
	}

	public String getEbcc_change() {
		return ebcc_change;
	}

	public void setEbcc_change(String ebcc_change) {
		this.ebcc_change = ebcc_change;
	}

	public String getPuof_state() {
		return puof_state;
	}

	public void setPuof_state(String puof_state) {
		this.puof_state = puof_state;
	}

	public String getPuof_editname() {
		return puof_editname;
	}

	public void setPuof_editname(String puof_editname) {
		this.puof_editname = puof_editname;
	}

	public String getPuof_edittime() {
		return puof_edittime;
	}

	public void setPuof_edittime(String puof_edittime) {
		this.puof_edittime = puof_edittime;
	}

	public String getPuof_url() {
		return puof_url;
	}

	public void setPuof_url(String puof_url) {
		this.puof_url = puof_url;
	}

}
