package Model;

public class EmShebaoChangeForeignerModel {

	private int id;
	private int cid;
	private int gid;
	private int ownmonth;

	private String emsc_formula;
	private String emsc_company;
	private String emsc_shortname;
	private String emsc_name;
	private String emsc_computerid;
	private String emsc_idcard;
	private String emsc_hj;
	private int emsc_radix;
	private String emsc_folk;
	private String emsc_mobile;
	private String emsc_worker;
	private String emsc_officialrank;
	private String emsc_hand;
	private String emsc_yl;
	private String emsc_gs;
	private String emsc_sye;
	private String emsc_syu;
	private String emsc_yltype;
	private String emsc_house;
	private int emsc_single;
	private String emsc_client;
	private String emsc_remark;
	private String emsc_change;
	private String emsc_content;
	private String emsc_declaretime;
	private String emsc_declarename;
	private String emsc_addtime;
	private String emsc_addname;
	private String emsc_chargeman;
	private String emsc_excelfile;
	private String emsc_iffifteen;
	private String emsc_ifdeclare;
	private String emsc_ifinure;
	private String emsc_ifmodify;
	private String emsc_ifsame;
	private String emsc_ifmsg;
	private String emsc_flag;
	private String emsc_flagname;
	private int emsc_tapr_id;
	private int flag_len;
	private String emba_birth;
	private String emba_sex;
	private String coco_shebaoid;
	private String emsc_reason;
	private EmShebaoUpdateModel emshebaoupdateModel;
	// 系统短信总数
	private String msg_a;
	private String emsc_confirmtime;

	public EmShebaoChangeForeignerModel() {
		super();
	}

	public EmShebaoChangeForeignerModel(int id, int cid, int gid, int ownmonth,
			String emsc_formula, String emsc_company, String emsc_shortname,
			String emsc_name, String emsc_computerid, String emsc_idcard,
			String emsc_hj, int emsc_radix, String emsc_folk,
			String emsc_mobile, String emsc_worker, String emsc_officialrank,
			String emsc_hand, String emsc_yl, String emsc_gs, String emsc_sye,
			String emsc_syu, String emsc_yltype, String emsc_house,
			int emsc_single, String emsc_client, String emsc_remark,
			String emsc_change, String emsc_content, String emsc_declaretime,
			String emsc_declarename, String emsc_addtime, String emsc_addname,
			String emsc_chargeman, String emsc_excelfile,
			String emsc_iffifteen, String emsc_ifdeclare, String emsc_ifinure,
			String emsc_ifmodify, String emsc_ifsame, String emsc_ifmsg,
			String emsc_flag, String emsc_flagname, int emsc_tapr_id,
			int flag_len, String emba_birth, String emba_sex,
			String coco_shebaoid, String emsc_reason,
			EmShebaoUpdateModel emshebaoupdateModel, String msg_a) {
		super();
		this.id = id;
		this.cid = cid;
		this.gid = gid;
		this.ownmonth = ownmonth;
		this.emsc_formula = emsc_formula;
		this.emsc_company = emsc_company;
		this.emsc_shortname = emsc_shortname;
		this.emsc_name = emsc_name;
		this.emsc_computerid = emsc_computerid;
		this.emsc_idcard = emsc_idcard;
		this.emsc_hj = emsc_hj;
		this.emsc_radix = emsc_radix;
		this.emsc_folk = emsc_folk;
		this.emsc_mobile = emsc_mobile;
		this.emsc_worker = emsc_worker;
		this.emsc_officialrank = emsc_officialrank;
		this.emsc_hand = emsc_hand;
		this.emsc_yl = emsc_yl;
		this.emsc_gs = emsc_gs;
		this.emsc_sye = emsc_sye;
		this.emsc_syu = emsc_syu;
		this.emsc_yltype = emsc_yltype;
		this.emsc_house = emsc_house;
		this.emsc_single = emsc_single;
		this.emsc_client = emsc_client;
		this.emsc_remark = emsc_remark;
		this.emsc_change = emsc_change;
		this.emsc_content = emsc_content;
		this.emsc_declaretime = emsc_declaretime;
		this.emsc_declarename = emsc_declarename;
		this.emsc_addtime = emsc_addtime;
		this.emsc_addname = emsc_addname;
		this.emsc_chargeman = emsc_chargeman;
		this.emsc_excelfile = emsc_excelfile;
		this.emsc_iffifteen = emsc_iffifteen;
		this.emsc_ifdeclare = emsc_ifdeclare;
		this.emsc_ifinure = emsc_ifinure;
		this.emsc_ifmodify = emsc_ifmodify;
		this.emsc_ifsame = emsc_ifsame;
		this.emsc_ifmsg = emsc_ifmsg;
		this.emsc_flag = emsc_flag;
		this.emsc_flagname = emsc_flagname;
		this.emsc_tapr_id = emsc_tapr_id;
		this.flag_len = flag_len;
		this.emba_birth = emba_birth;
		this.emba_sex = emba_sex;
		this.coco_shebaoid = coco_shebaoid;
		this.emsc_reason = emsc_reason;
		this.emshebaoupdateModel = emshebaoupdateModel;
		this.msg_a = msg_a;
	}

	public String getEmsc_confirmtime() {
		return emsc_confirmtime;
	}

	public void setEmsc_confirmtime(String emsc_confirmtime) {
		this.emsc_confirmtime = emsc_confirmtime;
	}

	public String getMsg_a() {
		return msg_a;
	}

	public void setMsg_a(String msg_a) {
		this.msg_a = msg_a;
	}

	public String getCoco_shebaoid() {
		return coco_shebaoid;
	}

	public void setCoco_shebaoid(String coco_shebaoid) {
		this.coco_shebaoid = coco_shebaoid;
	}

	public int getFlag_len() {
		return flag_len;
	}

	public void setFlag_len(int flag_len) {
		this.flag_len = flag_len;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(int ownmonth) {
		this.ownmonth = ownmonth;
	}

	public String getEmsc_formula() {
		return emsc_formula;
	}

	public void setEmsc_formula(String emsc_formula) {
		this.emsc_formula = emsc_formula;
	}

	public String getEmsc_company() {
		return emsc_company;
	}

	public void setEmsc_company(String emsc_company) {
		this.emsc_company = emsc_company;
	}

	public String getEmsc_shortname() {
		return emsc_shortname;
	}

	public void setEmsc_shortname(String emsc_shortname) {
		this.emsc_shortname = emsc_shortname;
	}

	public String getEmsc_name() {
		return emsc_name;
	}

	public void setEmsc_name(String emsc_name) {
		this.emsc_name = emsc_name;
	}

	public String getEmsc_computerid() {
		return emsc_computerid;
	}

	public void setEmsc_computerid(String emsc_computerid) {
		this.emsc_computerid = emsc_computerid;
	}

	public String getEmsc_idcard() {
		return emsc_idcard;
	}

	public void setEmsc_idcard(String emsc_idcard) {
		this.emsc_idcard = emsc_idcard;
	}

	public String getEmsc_hj() {
		return emsc_hj;
	}

	public void setEmsc_hj(String emsc_hj) {
		this.emsc_hj = emsc_hj;
	}

	public int getEmsc_radix() {
		return emsc_radix;
	}

	public void setEmsc_radix(int emsc_radix) {
		this.emsc_radix = emsc_radix;
	}

	public String getEmsc_folk() {
		return emsc_folk;
	}

	public void setEmsc_folk(String emsc_folk) {
		this.emsc_folk = emsc_folk;
	}

	public String getEmsc_mobile() {
		return emsc_mobile;
	}

	public void setEmsc_mobile(String emsc_mobile) {
		this.emsc_mobile = emsc_mobile;
	}

	public String getEmsc_worker() {
		return emsc_worker;
	}

	public void setEmsc_worker(String emsc_worker) {
		this.emsc_worker = emsc_worker;
	}

	public String getEmsc_officialrank() {
		return emsc_officialrank;
	}

	public void setEmsc_officialrank(String emsc_officialrank) {
		this.emsc_officialrank = emsc_officialrank;
	}

	public String getEmsc_hand() {
		return emsc_hand;
	}

	public void setEmsc_hand(String emsc_hand) {
		this.emsc_hand = emsc_hand;
	}

	public String getEmsc_yl() {
		return emsc_yl;
	}

	public void setEmsc_yl(String emsc_yl) {
		this.emsc_yl = emsc_yl;
	}

	public String getEmsc_gs() {
		return emsc_gs;
	}

	public void setEmsc_gs(String emsc_gs) {
		this.emsc_gs = emsc_gs;
	}

	public String getEmsc_sye() {
		return emsc_sye;
	}

	public void setEmsc_sye(String emsc_sye) {
		this.emsc_sye = emsc_sye;
	}

	public String getEmsc_syu() {
		return emsc_syu;
	}

	public void setEmsc_syu(String emsc_syu) {
		this.emsc_syu = emsc_syu;
	}

	public String getEmsc_yltype() {
		return emsc_yltype;
	}

	public void setEmsc_yltype(String emsc_yltype) {
		this.emsc_yltype = emsc_yltype;
	}

	public String getEmsc_house() {
		return emsc_house;
	}

	public void setEmsc_house(String emsc_house) {
		this.emsc_house = emsc_house;
	}

	public int getEmsc_single() {
		return emsc_single;
	}

	public void setEmsc_single(int emsc_single) {
		this.emsc_single = emsc_single;
	}

	public String getEmsc_client() {
		return emsc_client;
	}

	public void setEmsc_client(String emsc_client) {
		this.emsc_client = emsc_client;
	}

	public String getEmsc_remark() {
		return emsc_remark;
	}

	public void setEmsc_remark(String emsc_remark) {
		this.emsc_remark = emsc_remark;
	}

	public String getEmsc_change() {
		return emsc_change;
	}

	public void setEmsc_change(String emsc_change) {
		this.emsc_change = emsc_change;
	}

	public String getEmsc_content() {
		return emsc_content;
	}

	public void setEmsc_content(String emsc_content) {
		this.emsc_content = emsc_content;
	}

	public String getEmsc_declaretime() {
		return emsc_declaretime;
	}

	public void setEmsc_declaretime(String emsc_declaretime) {
		this.emsc_declaretime = emsc_declaretime;
	}

	public String getEmsc_declarename() {
		return emsc_declarename;
	}

	public void setEmsc_declarename(String emsc_declarename) {
		this.emsc_declarename = emsc_declarename;
	}

	public String getEmsc_addtime() {
		return emsc_addtime;
	}

	public void setEmsc_addtime(String emsc_addtime) {
		this.emsc_addtime = emsc_addtime;
	}

	public String getEmsc_addname() {
		return emsc_addname;
	}

	public void setEmsc_addname(String emsc_addname) {
		this.emsc_addname = emsc_addname;
	}

	public String getEmsc_chargeman() {
		return emsc_chargeman;
	}

	public void setEmsc_chargeman(String emsc_chargeman) {
		this.emsc_chargeman = emsc_chargeman;
	}

	public String getEmsc_excelfile() {
		return emsc_excelfile;
	}

	public void setEmsc_excelfile(String emsc_excelfile) {
		this.emsc_excelfile = emsc_excelfile;
	}

	public String getEmsc_iffifteen() {
		return emsc_iffifteen;
	}

	public void setEmsc_iffifteen(String emsc_iffifteen) {
		this.emsc_iffifteen = emsc_iffifteen;
	}

	public String getEmsc_ifdeclare() {
		return emsc_ifdeclare;
	}

	public void setEmsc_ifdeclare(String emsc_ifdeclare) {
		this.emsc_ifdeclare = emsc_ifdeclare;
	}

	public String getEmsc_ifinure() {
		return emsc_ifinure;
	}

	public void setEmsc_ifinure(String emsc_ifinure) {
		this.emsc_ifinure = emsc_ifinure;
	}

	public String getEmsc_ifmodify() {
		return emsc_ifmodify;
	}

	public void setEmsc_ifmodify(String emsc_ifmodify) {
		this.emsc_ifmodify = emsc_ifmodify;
	}

	public String getEmsc_ifsame() {
		return emsc_ifsame;
	}

	public void setEmsc_ifsame(String emsc_ifsame) {
		this.emsc_ifsame = emsc_ifsame;
	}

	public String getEmsc_ifmsg() {
		return emsc_ifmsg;
	}

	public void setEmsc_ifmsg(String emsc_ifmsg) {
		this.emsc_ifmsg = emsc_ifmsg;
	}

	public String getEmsc_flag() {
		return emsc_flag;
	}

	public void setEmsc_flag(String emsc_flag) {
		this.emsc_flag = emsc_flag;

		try {
			this.flag_len = emsc_flag.length();
		} catch (Exception e) {
			this.flag_len = 0;
		}
	}

	public String getEmsc_flagname() {
		return emsc_flagname;
	}

	public void setEmsc_flagname(String emsc_flagname) {
		this.emsc_flagname = emsc_flagname;
	}

	public int getEmsc_tapr_id() {
		return emsc_tapr_id;
	}

	public void setEmsc_tapr_id(int emsc_tapr_id) {
		this.emsc_tapr_id = emsc_tapr_id;
	}

	public String getEmba_birth() {
		return emba_birth;
	}

	public void setEmba_birth(String emba_birth) {
		this.emba_birth = emba_birth;
	}

	public String getEmba_sex() {
		return emba_sex;
	}

	public void setEmba_sex(String emba_sex) {
		this.emba_sex = emba_sex;
	}

	public EmShebaoUpdateModel getEmshebaoupdateModel() {
		return emshebaoupdateModel;
	}

	public void setEmshebaoupdateModel(EmShebaoUpdateModel emshebaoupdateModel) {
		this.emshebaoupdateModel = emshebaoupdateModel;
	}

	public String getEmsc_reason() {
		return emsc_reason;
	}

	public void setEmsc_reason(String emsc_reason) {
		this.emsc_reason = emsc_reason;
	}

}
