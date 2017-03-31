package Model;

import java.math.BigDecimal;
import java.util.Date;

public class EmShebaoBJModel {
	private int id;
	private int gid;
	private int cid;
	private int ownmonth;
	private String emsb_name;
	private String emsb_company;
	private String emsb_idcard;
	private int emsb_single;
	private int emsb_feeownmonth;
	private String emsb_computerid;
	private String emsb_hj;
	private String emsb_m1;
	private int emsb_r1;
	private String emsb_m2;
	private int emsb_r2;
	private String emsb_m3;
	private int emsb_r3;
	private BigDecimal emsb_totalcp;
	private BigDecimal emsb_totalop;
	private int emsb_ifdeclare;
	private String emsb_ifdeclarestr;
	private String emsb_declaretime;
	private String emsb_declarename;
	private String emsb_addtime;
	private String emsb_addname;
	private String emsb_remark;
	private String emsb_flag;
	private String emsb_flagname;
	private String emsb_excelfile;
	private String emsb_printtime;
	private Date emsb_dptime;
	private BigDecimal emsb_overdue;
	private String emsb_overduedate;
	private int emsb_startmonth;
	private int emsb_stopmonth;
	private int emsb_radix;
	private int emsb_computeridlength;
	private int emsb_tapr_id;
	private String emsb_reason;
	// 社保独立开户单位编码
	private String coco_shebaoid;
	// 系统短信总数
	private int msg_a;
	// 系统短信未读数
	private int msg_w;
	// 系统短信已读数
	private int msg_y;
	// 外籍人社保变更数
	private int cfCou;
	// 社保变更数
	private int ecCou;
	// 客服
	private String coba_client;
	// 算法名称
	private String soin_title;
	
	private BigDecimal emsb_total;
	private BigDecimal essb_zh;
	private BigDecimal essb_gj;
	private String emsb_uploadfile;
	private String emsb_confirmtime;
	private String emsb_yltype;
	private boolean chk_jlbj;
	
	private String type;
	
	public EmShebaoBJModel() {
		super();
	}

	public EmShebaoBJModel(int id, int gid, int cid, int ownmonth,
			String emsb_name, String emsb_company, String emsb_idcard,
			int emsb_single, int emsb_feeownmonth, String emsb_computerid,
			String emsb_hj, String emsb_m1, int emsb_r1, String emsb_m2,
			int emsb_r2, String emsb_m3, int emsb_r3, BigDecimal emsb_totalcp,
			BigDecimal emsb_totalop, int emsb_ifdeclare,
			String emsb_declaretime, String emsb_declarename,
			String emsb_addtime, String emsb_addname, String emsb_remark,
			String emsb_flag, String emsb_flagname, String emsb_excelfile,
			String emsb_printtime, Date emsb_dptime, BigDecimal emsb_overdue,
			String emsb_overduedate, int emsb_startmonth, int emsb_stopmonth,
			int emsb_radix) {
		super();
		this.id = id;
		this.gid = gid;
		this.cid = cid;
		this.ownmonth = ownmonth;
		this.emsb_name = emsb_name;
		this.emsb_company = emsb_company;
		this.emsb_idcard = emsb_idcard;
		this.emsb_single = emsb_single;
		this.emsb_feeownmonth = emsb_feeownmonth;
		this.emsb_computerid = emsb_computerid;
		this.emsb_hj = emsb_hj;
		this.emsb_m1 = emsb_m1;
		this.emsb_r1 = emsb_r1;
		this.emsb_m2 = emsb_m2;
		this.emsb_r2 = emsb_r2;
		this.emsb_m3 = emsb_m3;
		this.emsb_r3 = emsb_r3;
		this.emsb_totalcp = emsb_totalcp;
		this.emsb_totalop = emsb_totalop;
		this.emsb_ifdeclare = emsb_ifdeclare;
		this.emsb_declaretime = emsb_declaretime;
		this.emsb_declarename = emsb_declarename;
		this.emsb_addtime = emsb_addtime;
		this.emsb_addname = emsb_addname;
		this.emsb_remark = emsb_remark;
		this.emsb_flag = emsb_flag;
		this.emsb_flagname = emsb_flagname;
		this.emsb_excelfile = emsb_excelfile;
		this.emsb_printtime = emsb_printtime;
		this.emsb_dptime = emsb_dptime;
		this.emsb_overdue = emsb_overdue;
		this.emsb_overduedate = emsb_overduedate;
		this.emsb_startmonth = emsb_startmonth;
		this.emsb_stopmonth = emsb_stopmonth;
		this.emsb_radix = emsb_radix;
	}
	
	

	public EmShebaoBJModel(int id, int gid, int cid, int ownmonth,
			String emsb_name, String emsb_company, String emsb_idcard,
			int emsb_single, int emsb_feeownmonth, String emsb_computerid,
			String emsb_hj, String emsb_m1, int emsb_r1, String emsb_m2,
			int emsb_r2, String emsb_m3, int emsb_r3, BigDecimal emsb_totalcp,
			BigDecimal emsb_totalop, int emsb_ifdeclare,
			String emsb_ifdeclarestr, String emsb_declaretime,
			String emsb_declarename, String emsb_addtime, String emsb_addname,
			String emsb_remark, String emsb_flag, String emsb_flagname,
			String emsb_excelfile, String emsb_printtime, Date emsb_dptime,
			BigDecimal emsb_overdue, String emsb_overduedate,
			int emsb_startmonth, int emsb_stopmonth, int emsb_radix,
			int emsb_computeridlength, int emsb_tapr_id, String emsb_reason,
			String coco_shebaoid, int msg_a, int msg_w, int msg_y, int cfCou,
			int ecCou, String coba_client, String soin_title,
			BigDecimal emsb_total) {
		super();
		this.id = id;
		this.gid = gid;
		this.cid = cid;
		this.ownmonth = ownmonth;
		this.emsb_name = emsb_name;
		this.emsb_company = emsb_company;
		this.emsb_idcard = emsb_idcard;
		this.emsb_single = emsb_single;
		this.emsb_feeownmonth = emsb_feeownmonth;
		this.emsb_computerid = emsb_computerid;
		this.emsb_hj = emsb_hj;
		this.emsb_m1 = emsb_m1;
		this.emsb_r1 = emsb_r1;
		this.emsb_m2 = emsb_m2;
		this.emsb_r2 = emsb_r2;
		this.emsb_m3 = emsb_m3;
		this.emsb_r3 = emsb_r3;
		this.emsb_totalcp = emsb_totalcp;
		this.emsb_totalop = emsb_totalop;
		this.emsb_ifdeclare = emsb_ifdeclare;
		this.emsb_ifdeclarestr = emsb_ifdeclarestr;
		this.emsb_declaretime = emsb_declaretime;
		this.emsb_declarename = emsb_declarename;
		this.emsb_addtime = emsb_addtime;
		this.emsb_addname = emsb_addname;
		this.emsb_remark = emsb_remark;
		this.emsb_flag = emsb_flag;
		this.emsb_flagname = emsb_flagname;
		this.emsb_excelfile = emsb_excelfile;
		this.emsb_printtime = emsb_printtime;
		this.emsb_dptime = emsb_dptime;
		this.emsb_overdue = emsb_overdue;
		this.emsb_overduedate = emsb_overduedate;
		this.emsb_startmonth = emsb_startmonth;
		this.emsb_stopmonth = emsb_stopmonth;
		this.emsb_radix = emsb_radix;
		this.emsb_computeridlength = emsb_computeridlength;
		this.emsb_tapr_id = emsb_tapr_id;
		this.emsb_reason = emsb_reason;
		this.coco_shebaoid = coco_shebaoid;
		this.msg_a = msg_a;
		this.msg_w = msg_w;
		this.msg_y = msg_y;
		this.cfCou = cfCou;
		this.ecCou = ecCou;
		this.coba_client = coba_client;
		this.soin_title = soin_title;
		this.emsb_total = emsb_total;
	}

	public String getEmsb_confirmtime() {
		return emsb_confirmtime;
	}

	public void setEmsb_confirmtime(String emsb_confirmtime) {
		this.emsb_confirmtime = emsb_confirmtime;
	}

	public BigDecimal getEmsb_total() {
		return emsb_total;
	}

	public void setEmsb_total(BigDecimal emsb_total) {
		this.emsb_total = emsb_total;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public int getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(int ownmonth) {
		this.ownmonth = ownmonth;
	}

	public String getEmsb_name() {
		return emsb_name;
	}

	public void setEmsb_name(String emsb_name) {
		this.emsb_name = emsb_name;
	}

	public String getEmsb_company() {
		return emsb_company;
	}

	public void setEmsb_company(String emsb_company) {
		this.emsb_company = emsb_company;
	}

	public String getEmsb_idcard() {
		return emsb_idcard;
	}

	public void setEmsb_idcard(String emsb_idcard) {
		this.emsb_idcard = emsb_idcard;
	}

	public int getEmsb_single() {
		return emsb_single;
	}

	public void setEmsb_single(int emsb_single) {
		this.emsb_single = emsb_single;
	}

	public int getEmsb_feeownmonth() {
		return emsb_feeownmonth;
	}

	public void setEmsb_feeownmonth(int emsb_feeownmonth) {
		this.emsb_feeownmonth = emsb_feeownmonth;
	}

	public String getEmsb_computerid() {
		return emsb_computerid;
	}

	public void setEmsb_computerid(String emsb_computerid) {
		this.emsb_computerid = emsb_computerid;
		try {
			this.emsb_computeridlength = emsb_computerid.length();
		} catch (Exception e) {
			this.emsb_computeridlength = 0;
		}
	}

	public String getEmsb_hj() {
		return emsb_hj;
	}

	public void setEmsb_hj(String emsb_hj) {
		this.emsb_hj = emsb_hj;
	}

	public String getEmsb_m1() {
		return emsb_m1;
	}

	public void setEmsb_m1(String emsb_m1) {
		this.emsb_m1 = emsb_m1;
	}

	public int getEmsb_r1() {
		return emsb_r1;
	}

	public void setEmsb_r1(int emsb_r1) {
		this.emsb_r1 = emsb_r1;
	}

	public String getEmsb_m2() {
		return emsb_m2;
	}

	public void setEmsb_m2(String emsb_m2) {
		this.emsb_m2 = emsb_m2;
	}

	public int getEmsb_r2() {
		return emsb_r2;
	}

	public void setEmsb_r2(int emsb_r2) {
		this.emsb_r2 = emsb_r2;
	}

	public String getEmsb_m3() {
		return emsb_m3;
	}

	public void setEmsb_m3(String emsb_m3) {
		this.emsb_m3 = emsb_m3;
	}

	public int getEmsb_r3() {
		return emsb_r3;
	}

	public void setEmsb_r3(int emsb_r3) {
		this.emsb_r3 = emsb_r3;
	}

	public BigDecimal getEmsb_totalcp() {
		return emsb_totalcp;
	}

	public void setEmsb_totalcp(BigDecimal emsb_totalcp) {
		this.emsb_totalcp = emsb_totalcp;
	}

	public BigDecimal getEmsb_totalop() {
		return emsb_totalop;
	}

	public void setEmsb_totalop(BigDecimal emsb_totalop) {
		this.emsb_totalop = emsb_totalop;
	}

	public int getEmsb_ifdeclare() {
		return emsb_ifdeclare;
	}

	public void setEmsb_ifdeclare(int emsb_ifdeclare) {
		this.emsb_ifdeclare = emsb_ifdeclare;
		switch (emsb_ifdeclare) {
		case 0:
			emsb_ifdeclarestr = "未申报";
			break;
		case 1:
			emsb_ifdeclarestr = "已申报";
			break;
		case 2:
			emsb_ifdeclarestr = "已打单";
			break;
		case 3:
			emsb_ifdeclarestr = "退回";
			break;
		case 4:
			emsb_ifdeclarestr = "待确认";
			break;
		case 6:
			emsb_ifdeclarestr = "已交单";
			break;
		}
	}

	public String getEmsb_declaretime() {
		return emsb_declaretime;
	}

	public void setEmsb_declaretime(String emsb_declaretime) {
		this.emsb_declaretime = emsb_declaretime;
	}

	public String getEmsb_declarename() {
		return emsb_declarename;
	}

	public void setEmsb_declarename(String emsb_declarename) {
		this.emsb_declarename = emsb_declarename;
	}

	public String getEmsb_addtime() {
		return emsb_addtime;
	}

	public void setEmsb_addtime(String emsb_addtime) {
		this.emsb_addtime = emsb_addtime;
	}

	public String getEmsb_addname() {
		return emsb_addname;
	}

	public void setEmsb_addname(String emsb_addname) {
		this.emsb_addname = emsb_addname;
	}

	public String getEmsb_remark() {
		return emsb_remark;
	}

	public void setEmsb_remark(String emsb_remark) {
		this.emsb_remark = emsb_remark;
	}

	public String getEmsb_flag() {
		return emsb_flag;
	}

	public void setEmsb_flag(String emsb_flag) {
		this.emsb_flag = emsb_flag;
	}

	public String getEmsb_flagname() {
		return emsb_flagname;
	}

	public void setEmsb_flagname(String emsb_flagname) {
		this.emsb_flagname = emsb_flagname;
	}

	public String getEmsb_excelfile() {
		return emsb_excelfile;
	}

	public void setEmsb_excelfile(String emsb_excelfile) {
		this.emsb_excelfile = emsb_excelfile;
	}

	public String getEmsb_printtime() {
		return emsb_printtime;
	}

	public void setEmsb_printtime(String emsb_printtime) {
		this.emsb_printtime = emsb_printtime;
	}

	public Date getEmsb_dptime() {
		return emsb_dptime;
	}

	public void setEmsb_dptime(Date emsb_dptime) {
		this.emsb_dptime = emsb_dptime;
	}

	public BigDecimal getEmsb_overdue() {
		return emsb_overdue;
	}

	public void setEmsb_overdue(BigDecimal emsb_overdue) {
		this.emsb_overdue = emsb_overdue;
	}

	public String getEmsb_overduedate() {
		return emsb_overduedate;
	}

	public void setEmsb_overduedate(String emsb_overduedate) {
		this.emsb_overduedate = emsb_overduedate;
	}

	public int getEmsb_startmonth() {
		return emsb_startmonth;
	}

	public void setEmsb_startmonth(int emsb_startmonth) {
		this.emsb_startmonth = emsb_startmonth;
	}

	public int getEmsb_stopmonth() {
		return emsb_stopmonth;
	}

	public void setEmsb_stopmonth(int emsb_stopmonth) {
		this.emsb_stopmonth = emsb_stopmonth;
	}

	public int getEmsb_radix() {
		return emsb_radix;
	}

	public void setEmsb_radix(int emsb_radix) {
		this.emsb_radix = emsb_radix;
	}

	public int getMsg_a() {
		return msg_a;
	}

	public void setMsg_a(int msg_a) {
		this.msg_a = msg_a;
	}

	public int getMsg_w() {
		return msg_w;
	}

	public void setMsg_w(int msg_w) {
		this.msg_w = msg_w;
	}

	public int getMsg_y() {
		return msg_y;
	}

	public void setMsg_y(int msg_y) {
		this.msg_y = msg_y;
	}

	public int getCfCou() {
		return cfCou;
	}

	public void setCfCou(int cfCou) {
		this.cfCou = cfCou;
	}

	public int getEcCou() {
		return ecCou;
	}

	public void setEcCou(int ecCou) {
		this.ecCou = ecCou;
	}

	public String getCoco_shebaoid() {
		return coco_shebaoid;
	}

	public void setCoco_shebaoid(String coco_shebaoid) {
		this.coco_shebaoid = coco_shebaoid;
	}

	public int getEmsb_computeridlength() {
		return emsb_computeridlength;
	}

	public void setEmsb_computeridlength(int emsb_computeridlength) {
		this.emsb_computeridlength = emsb_computeridlength;
	}

	public int getEmsb_tapr_id() {
		return emsb_tapr_id;
	}

	public void setEmsb_tapr_id(int emsb_tapr_id) {
		this.emsb_tapr_id = emsb_tapr_id;
	}

	public String getEmsb_ifdeclarestr() {
		return emsb_ifdeclarestr;
	}

	public void setEmsb_ifdeclarestr(String emsb_ifdeclarestr) {
		this.emsb_ifdeclarestr = emsb_ifdeclarestr;
	}

	public String getCoba_client() {
		return coba_client;
	}

	public void setCoba_client(String coba_client) {
		this.coba_client = coba_client;
	}

	public String getEmsb_reason() {
		return emsb_reason;
	}

	public void setEmsb_reason(String emsb_reason) {
		this.emsb_reason = emsb_reason;
	}

	public String getSoin_title() {
		return soin_title;
	}

	public void setSoin_title(String soin_title) {
		this.soin_title = soin_title;
	}

	public BigDecimal getEssb_zh() {
		return essb_zh;
	}

	public void setEssb_zh(BigDecimal essb_zh) {
		this.essb_zh = essb_zh;
	}

	public BigDecimal getEssb_gj() {
		return essb_gj;
	}

	public void setEssb_gj(BigDecimal essb_gj) {
		this.essb_gj = essb_gj;
	}

	public String getEmsb_uploadfile() {
		return emsb_uploadfile;
	}

	public void setEmsb_uploadfile(String emsb_uploadfile) {
		this.emsb_uploadfile = emsb_uploadfile;
	}

	public String getEmsb_yltype() {
		return emsb_yltype;
	}

	public void setEmsb_yltype(String emsb_yltype) {
		this.emsb_yltype = emsb_yltype;
	}

	public boolean isChk_jlbj() {
		return chk_jlbj;
	}

	public void setChk_jlbj(boolean chk_jlbj) {
		this.chk_jlbj = chk_jlbj;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
