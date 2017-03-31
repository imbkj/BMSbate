package Model;

public class EmSheBaoChangeModel {
	private Integer id;
	// / CID
	private Integer cid;
	// / GID
	private int gid;
	// / Ownmonth
	private int ownmonth;
	// / emsc_Formula
	private String emsc_formula;
	// / emsc_Company
	private String emsc_company;
	// / emsc_Shortname
	private String emsc_shortname;
	// / emsc_Name
	private String emsc_name;
	// / emsc_Computerid
	private String emsc_computerid;
	// / emsc_IDcard
	private String emsc_idcard;
	// / emsc_HJ
	private String emsc_hj;
	// / emsc_Radix
	private int emsc_radix;
	// / emsc_Folk
	private String emsc_folk;
	// / emsc_Mobile
	private String emsc_mobile;
	// / emsc_Worker
	private String emsc_worker;
	// / emsc_OfficialRank
	private String emsc_officialrank;
	// / emsc_Hand
	private String emsc_hand;
	// / emsc_YL
	private String emsc_yl;
	// / emsc_GS
	private String emsc_gs;
	// / emsc_Sye
	private String emsc_sye;
	// / emsc_Syu
	private String emsc_syu;
	// / emsc_YLType
	private String emsc_yltype;
	// / emsc_House
	private String emsc_house;
	// / emsc_Single
	private int emsc_single;
	// / emsc_Client
	private String emsc_client;
	// / emsc_Remark
	private String emsc_remark;
	// / emsc_Change
	private String emsc_change;
	// / emsc_Content
	private String emsc_content;
	// / emsc_DeclareTime
	private String emsc_declaretime;
	// / emsc_DeclareName
	private String emsc_declarename;
	// / emsc_AddTime
	private String emsc_addtime;
	// / emsc_AddName
	private String emsc_addname;
	// / emsc_ChargeMan
	private String emsc_chargeman;
	// / emsc_ExcelFile
	private String emsc_excelfile;
	// / emsc_IfFifteen
	private String emsc_iffifteen;
	// / emsc_IfDeclare
	private String emsc_ifdeclare;
	// / emsc_IfInure
	private String emsc_ifinure;
	// / emsc_IfModify
	private String emsc_ifmodify;
	// / emsc_Ifsame
	private String emsc_ifsame;
	// / emsc_IfMSG
	private String emsc_ifmsg;
	// / emsc_Flag
	private String emsc_flag;
	// / emsc_FlagName
	private String emsc_flagname;
	// / emsc_Ifwrong
	private int emsc_ifwrong;
	// / emsc_ConfirmTime
	private String emsc_confirmtime;
	// / emsc_tid
	private String emsc_tid;
	// / 停交原因
	private String emsc_stopreason;
	// 社保独立开户单位编码
	private String coco_shebaoid;
	// 系统短信最新的数据状态
	private String msg_a;
	// 系统短信未读数
	private String msg_w;
	// 系统短信已读数
	private String msg_y;
	// 当月或下月补交数
	private String bj_cou;
	private int flag_len;
	private int emsc_tapr_id;
	private String emba_birth;
	private String emba_sex;
	private int changetype;
	private int emsc_ifdeclareInt;
	private String emsc_singleStr;
	private Integer readstate;//短信状态

	public EmSheBaoChangeModel() {
		super();
	}

	public EmSheBaoChangeModel(Integer id, Integer cid, int gid, int ownmonth,
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
			String emsc_flag, String emsc_flagname, int emsc_ifwrong,
			String emsc_confirmtime, String emsc_tid, String emsc_stopreason,
			String coco_shebaoid, String msg_a, String msg_w, String msg_y,
			String bj_cou, int flag_len, int emsc_tapr_id, String emba_birth,
			String emba_sex) {
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
		this.emsc_ifwrong = emsc_ifwrong;
		this.emsc_confirmtime = emsc_confirmtime;
		this.emsc_tid = emsc_tid;
		this.emsc_stopreason = emsc_stopreason;
		this.coco_shebaoid = coco_shebaoid;
		this.msg_a = msg_a;
		this.msg_w = msg_w;
		this.msg_y = msg_y;
		this.bj_cou = bj_cou;
		this.flag_len = flag_len;
		this.emsc_tapr_id = emsc_tapr_id;
		this.emba_birth = emba_birth;
		this.emba_sex = emba_sex;
	}

	public String getEmba_sex() {
		return emba_sex;
	}

	public void setEmba_sex(String emba_sex) {
		this.emba_sex = emba_sex;
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

	public int getFlag_len() {
		return flag_len;
	}

	public void setFlag_len(int flag_len) {
		this.flag_len = flag_len;
	}

	public String getCoco_shebaoid() {
		return coco_shebaoid;
	}

	public void setCoco_shebaoid(String coco_shebaoid) {
		this.coco_shebaoid = coco_shebaoid;
	}

	public String getMsg_a() {
		return msg_a;
	}

	public void setMsg_a(String msg_a) {
		this.msg_a = msg_a;
	}

	public String getMsg_w() {
		return msg_w;
	}

	public void setMsg_w(String msg_w) {
		this.msg_w = msg_w;
	}

	public String getMsg_y() {
		return msg_y;
	}

	public void setMsg_y(String msg_y) {
		this.msg_y = msg_y;
	}

	public String getBj_cou() {
		return bj_cou;
	}

	public void setBj_cou(String bj_cou) {
		this.bj_cou = bj_cou;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
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

	public String sbmodel() {
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
		switch (emsc_single) {
		case 0:
			this.emsc_singleStr = "中智开户";
			break;
		case 1:
			this.emsc_singleStr = "独立开户";
			break;
		case 2:
			this.emsc_singleStr = "中智开户(委托)";
			break;
		case 3:
			this.emsc_singleStr = "中智开户(外包)";
			break;
		case 4:
			this.emsc_singleStr = "中智开户(派遣)";
			break;
		}
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

	public int getEmsc_ifwrong() {
		return emsc_ifwrong;
	}

	public void setEmsc_ifwrong(int emsc_ifwrong) {
		this.emsc_ifwrong = emsc_ifwrong;
	}

	public String getEmsc_confirmtime() {
		return emsc_confirmtime;
	}

	public void setEmsc_confirmtime(String emsc_confirmtime) {
		this.emsc_confirmtime = emsc_confirmtime;
	}

	public String getEmsc_tid() {
		return emsc_tid;
	}

	public void setEmsc_tid(String emsc_tid) {
		this.emsc_tid = emsc_tid;
	}

	public String getEmsc_stopreason() {
		return emsc_stopreason;
	}

	public void setEmsc_stopreason(String emsc_stopreason) {
		this.emsc_stopreason = emsc_stopreason;
	}

	public int getChangetype() {
		return changetype;
	}

	public void setChangetype(int changtype) {
		this.changetype = changtype;
	}

	public int getEmsc_ifdeclareInt() {
		return emsc_ifdeclareInt;
	}

	public void setEmsc_ifdeclareInt(int emsc_ifdeclareInt) {
		this.emsc_ifdeclareInt = emsc_ifdeclareInt;
		switch (emsc_ifdeclareInt) {
		case 0:
			this.emsc_ifdeclare = "未申报";
			break;
		case 1:
			this.emsc_ifdeclare = "已申报";
			break;
		case 2:
			this.emsc_ifdeclare = "申报中";
			break;
		case 3:
			this.emsc_ifdeclare = "退回";
			break;
		case 4:
			this.emsc_ifdeclare = "待确认";
			break;
		case 5:
			this.emsc_ifdeclare = "审核中";
			break;
		case 6:
			this.emsc_ifdeclare = "已交单";
			break;
		case 7:
			this.emsc_ifdeclare = "中心待核收";
			break;
		case 8:
			this.emsc_ifdeclare = "待申报";
			break;
		case 11:
			this.emsc_ifdeclare = "已传决定书";
			break;
		case 12:
			this.emsc_ifdeclare = "已确认收款";
			break;
		case 13:
			this.emsc_ifdeclare = "已完结";
			break;
		}
	}

	public String getEmsc_singleStr() {
		return emsc_singleStr;
	}

	public void setEmsc_singleStr(String emsc_singleStr) {
		this.emsc_singleStr = emsc_singleStr;
	}

	public Integer getReadstate() {
		return readstate;
	}

	public void setReadstate(Integer readstate) {
		this.readstate = readstate;
	}

	public String getEmsc_computerid() {
		return emsc_computerid;
	}
	
}
