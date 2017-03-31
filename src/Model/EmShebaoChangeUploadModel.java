package Model;

public class EmShebaoChangeUploadModel {
	private int emsu_id;
	private int gid;
	private int ownmonth;
	private String emsu_name;
	private int emsu_type;
	private String emsu_idcard;
	private String emsu_computerid;
	private int emsu_old_radix;
	private int emsu_radix;
	private String emsu_stopreason;
	private String emsu_uploadfilename;
	private String emsu_addname;
	private String emsu_addtime;
	private int emsu_state;
	private String emsu_stateStr;
	private String emsu_err;
	private String emsu_typeStr;

	private int cid;
	private String coba_shortname;
	private String coba_client;
	private boolean check = false;
	private EmShebaoUpdateModel euModel;

	private String emsu_hj;
	private String emsu_Folk;
	private String emsu_Worker;
	private String emsu_Hand;
	private String emsu_YL;
	private String emsu_GS;
	private String emsu_Sye;
	private String emsu_Syu;
	private String emsu_YLType;
	private Integer emsu_formulaid;
	private String emsu_formula;
	private String mobile;
	private String esiu_idcard;

	public EmShebaoChangeUploadModel() {
		super();
	}

	public int getEmsu_state() {
		return emsu_state;
	}

	public void setEmsu_state(int emsu_state) {
		this.emsu_state = emsu_state;
		switch (emsu_state) {
		case 0:
			this.emsu_stateStr = "未提交";
			break;
		case 1:
			this.emsu_stateStr = "已提交";
			break;
		default:
			break;
		}
	}

	public int getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(int ownmonth) {
		this.ownmonth = ownmonth;
	}

	public String getEmsu_stateStr() {
		return emsu_stateStr;
	}

	public void setEmsu_stateStr(String emsu_stateStr) {
		this.emsu_stateStr = emsu_stateStr;
	}

	public String getEmsu_err() {
		return emsu_err;
	}

	public void setEmsu_err(String emsu_err) {
		this.emsu_err = emsu_err;
	}

	public int getEmsu_id() {
		return emsu_id;
	}

	public void setEmsu_id(int emsu_id) {
		this.emsu_id = emsu_id;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public String getEmsu_name() {
		return emsu_name;
	}

	public void setEmsu_name(String emsu_name) {
		this.emsu_name = emsu_name;
	}

	public int getEmsu_type() {
		return emsu_type;
	}

	public void setEmsu_type(int emsu_type) {
		this.emsu_type = emsu_type;
		switch (emsu_type) {
		case 1:
			this.emsu_typeStr = "新增";
			break;
		case 2:
			this.emsu_typeStr = "调入";
			break;
		case 3:
			this.emsu_typeStr = "修改工资";
			break;
		case 4:
			this.emsu_typeStr = "外籍人新增";
			break;
		case 5:
			this.emsu_typeStr = "外籍人调入";
			break;
		case 6:
			this.emsu_typeStr = "独立户接管";
			break;
		default:
			break;
		}
	}

	public String getEmsu_idcard() {
		return emsu_idcard;
	}

	public void setEmsu_idcard(String emsu_idcard) {
		this.emsu_idcard = emsu_idcard;
	}

	public String getEmsu_computerid() {
		return emsu_computerid;
	}

	public void setEmsu_computerid(String emsu_computerid) {
		this.emsu_computerid = emsu_computerid;
	}

	public int getEmsu_radix() {
		return emsu_radix;
	}

	public void setEmsu_radix(int emsu_radix) {
		this.emsu_radix = emsu_radix;
	}

	public String getEmsu_stopreason() {
		return emsu_stopreason;
	}

	public void setEmsu_stopreason(String emsu_stopreason) {
		this.emsu_stopreason = emsu_stopreason;
	}

	public String getEmsu_uploadfilename() {
		return emsu_uploadfilename;
	}

	public void setEmsu_uploadfilename(String emsu_uploadfilename) {
		this.emsu_uploadfilename = emsu_uploadfilename;
	}

	public String getEmsu_addname() {
		return emsu_addname;
	}

	public void setEmsu_addname(String emsu_addname) {
		this.emsu_addname = emsu_addname;
	}

	public String getEmsu_addtime() {
		return emsu_addtime;
	}

	public void setEmsu_addtime(String emsu_addtime) {
		this.emsu_addtime = emsu_addtime;
	}

	public int getEmsu_old_radix() {
		return emsu_old_radix;
	}

	public void setEmsu_old_radix(int emsu_old_radix) {
		this.emsu_old_radix = emsu_old_radix;
	}

	public String getEmsu_typeStr() {
		return emsu_typeStr;
	}

	public void setEmsu_typeStr(String emsu_typeStr) {
		this.emsu_typeStr = emsu_typeStr;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getCoba_shortname() {
		return coba_shortname;
	}

	public void setCoba_shortname(String coba_shortname) {
		this.coba_shortname = coba_shortname;
	}

	public String getCoba_client() {
		return coba_client;
	}

	public void setCoba_client(String coba_client) {
		this.coba_client = coba_client;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public EmShebaoUpdateModel getEuModel() {
		return euModel;
	}

	public void setEuModel(EmShebaoUpdateModel euModel) {
		this.euModel = euModel;
	}

	public String getEmsu_hj() {
		return emsu_hj;
	}

	public void setEmsu_hj(String emsu_hj) {
		this.emsu_hj = emsu_hj;
	}

	public String getEmsu_Folk() {
		return emsu_Folk;
	}

	public void setEmsu_Folk(String emsu_Folk) {
		this.emsu_Folk = emsu_Folk;
	}

	public String getEmsu_Worker() {
		return emsu_Worker;
	}

	public void setEmsu_Worker(String emsu_Worker) {
		this.emsu_Worker = emsu_Worker;
	}

	public String getEmsu_Hand() {
		return emsu_Hand;
	}

	public void setEmsu_Hand(String emsu_Hand) {
		this.emsu_Hand = emsu_Hand;
	}

	public String getEmsu_YL() {
		return emsu_YL;
	}

	public void setEmsu_YL(String emsu_YL) {
		this.emsu_YL = emsu_YL;
	}

	public String getEmsu_GS() {
		return emsu_GS;
	}

	public void setEmsu_GS(String emsu_GS) {
		this.emsu_GS = emsu_GS;
	}

	public String getEmsu_Sye() {
		return emsu_Sye;
	}

	public void setEmsu_Sye(String emsu_Sye) {
		this.emsu_Sye = emsu_Sye;
	}

	public String getEmsu_Syu() {
		return emsu_Syu;
	}

	public void setEmsu_Syu(String emsu_Syu) {
		this.emsu_Syu = emsu_Syu;
	}

	public String getEmsu_YLType() {
		return emsu_YLType;
	}

	public void setEmsu_YLType(String emsu_YLType) {
		this.emsu_YLType = emsu_YLType;
	}

	public Integer getEmsu_formulaid() {
		return emsu_formulaid;
	}

	public void setEmsu_formulaid(Integer emsu_formulaid) {
		this.emsu_formulaid = emsu_formulaid;
	}

	public String getEmsu_formula() {
		return emsu_formula;
	}

	public void setEmsu_formula(String emsu_formula) {
		this.emsu_formula = emsu_formula;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEsiu_idcard() {
		return esiu_idcard;
	}

	public void setEsiu_idcard(String esiu_idcard) {
		this.esiu_idcard = esiu_idcard;
	}

}
