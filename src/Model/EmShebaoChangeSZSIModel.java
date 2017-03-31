package Model;

import java.util.Date;

public class EmShebaoChangeSZSIModel {
	private int escs_id;
	private int gid;
	private int cid;
	private int ownmonth;
	private String escs_company;
	private String escs_name;
	private String escs_change;
	private String escs_content;
	private String escs_changehj;
	private String escs_changename;
	private String escs_changeidcard;
	private String escs_changeylid;
	private String escs_changecid;
	private String escs_changeofficialrank;
	private int escs_s1;
	private int escs_s2;
	private int escs_s3;
	private int escs_s4;
	private int escs_s5;
	private int escs_s6;
	private int escs_s7;
	private String escs_s8;
	private int escs_c1;
	private int escs_c2;
	private int escs_c3;
	private int escs_c4;
	private int escs_c5;
	private int escs_c6;
	private int escs_c7;
	private Date escs_t1;
	private Date escs_t2;
	private Date escs_t3;
	private Date escs_t4;
	private Date escs_t5;
	private Date escs_t6;
	private Date escs_t7;
	private Date escs_t8;
	private Date escs_o1;
	private Date escs_o2;
	private Date escs_o3;
	private Date escs_o4;
	private Date escs_o5;
	private Date escs_o6;
	private Date escs_o7;
	private Date escs_o8;
	private Date escs_addtime;
	private String escs_addname;
	private int escs_ifdeclare;
	private Date escs_declaretime;
	private String escs_declarename;
	private int escs_single;
	private String escs_shebaoid;
	private String escs_remark;
	private int escs_ifbase;
	private int escs_ifsb;
	private String escs_rtime;
	private String escs_rname;
	private String escs_qname;
	private String escs_ttime;
	private String escs_tname;
	private String escs_hname;
	private String escs_flag;
	private String escs_flagname;
	private String computerid;
	private String idcard;
	private int flag_len;
	private int emsc_tapr_id;
	private String coco_shebaoid;
	// 系统短信最新的数据状态
	private String msg_a;
	// 系统短信未读数
	private String msg_w;
	// 系统短信已读数
	private String msg_y;
	
	private String escs_uploadfile;
	private String escs_confirmtime;
	
	private Integer escs_ifnet;
	
	public EmShebaoChangeSZSIModel() {
		super();
	}

	

	public EmShebaoChangeSZSIModel(int escs_id, int gid, int cid, int ownmonth,
			String escs_company, String escs_name, String escs_change,
			String escs_content, String escs_changehj, String escs_changename,
			String escs_changeidcard, String escs_changeylid,
			String escs_changecid, String escs_changeofficialrank, int escs_s1,
			int escs_s2, int escs_s3, int escs_s4, int escs_s5, int escs_s6,
			int escs_s7, String escs_s8, int escs_c1, int escs_c2, int escs_c3,
			int escs_c4, int escs_c5, int escs_c6, int escs_c7, Date escs_t1,
			Date escs_t2, Date escs_t3, Date escs_t4, Date escs_t5,
			Date escs_t6, Date escs_t7, Date escs_t8, Date escs_o1,
			Date escs_o2, Date escs_o3, Date escs_o4, Date escs_o5,
			Date escs_o6, Date escs_o7, Date escs_o8, Date escs_addtime,
			String escs_addname, int escs_ifdeclare, Date escs_declaretime,
			String escs_declarename, int escs_single, String escs_shebaoid,
			String escs_remark, int escs_ifbase, int escs_ifsb,
			String escs_rtime, String escs_rname, String escs_qname,
			String escs_ttime, String escs_tname, String escs_hname,
			String escs_flag, String escs_flagname, String computerid,
			String idcard, int flag_len, int emsc_tapr_id,
			String coco_shebaoid, String msg_a, String msg_w, String msg_y) {
		super();
		this.escs_id = escs_id;
		this.gid = gid;
		this.cid = cid;
		this.ownmonth = ownmonth;
		this.escs_company = escs_company;
		this.escs_name = escs_name;
		this.escs_change = escs_change;
		this.escs_content = escs_content;
		this.escs_changehj = escs_changehj;
		this.escs_changename = escs_changename;
		this.escs_changeidcard = escs_changeidcard;
		this.escs_changeylid = escs_changeylid;
		this.escs_changecid = escs_changecid;
		this.escs_changeofficialrank = escs_changeofficialrank;
		this.escs_s1 = escs_s1;
		this.escs_s2 = escs_s2;
		this.escs_s3 = escs_s3;
		this.escs_s4 = escs_s4;
		this.escs_s5 = escs_s5;
		this.escs_s6 = escs_s6;
		this.escs_s7 = escs_s7;
		this.escs_s8 = escs_s8;
		this.escs_c1 = escs_c1;
		this.escs_c2 = escs_c2;
		this.escs_c3 = escs_c3;
		this.escs_c4 = escs_c4;
		this.escs_c5 = escs_c5;
		this.escs_c6 = escs_c6;
		this.escs_c7 = escs_c7;
		this.escs_t1 = escs_t1;
		this.escs_t2 = escs_t2;
		this.escs_t3 = escs_t3;
		this.escs_t4 = escs_t4;
		this.escs_t5 = escs_t5;
		this.escs_t6 = escs_t6;
		this.escs_t7 = escs_t7;
		this.escs_t8 = escs_t8;
		this.escs_o1 = escs_o1;
		this.escs_o2 = escs_o2;
		this.escs_o3 = escs_o3;
		this.escs_o4 = escs_o4;
		this.escs_o5 = escs_o5;
		this.escs_o6 = escs_o6;
		this.escs_o7 = escs_o7;
		this.escs_o8 = escs_o8;
		this.escs_addtime = escs_addtime;
		this.escs_addname = escs_addname;
		this.escs_ifdeclare = escs_ifdeclare;
		this.escs_declaretime = escs_declaretime;
		this.escs_declarename = escs_declarename;
		this.escs_single = escs_single;
		this.escs_shebaoid = escs_shebaoid;
		this.escs_remark = escs_remark;
		this.escs_ifbase = escs_ifbase;
		this.escs_ifsb = escs_ifsb;
		this.escs_rtime = escs_rtime;
		this.escs_rname = escs_rname;
		this.escs_qname = escs_qname;
		this.escs_ttime = escs_ttime;
		this.escs_tname = escs_tname;
		this.escs_hname = escs_hname;
		this.escs_flag = escs_flag;
		this.escs_flagname = escs_flagname;
		this.computerid = computerid;
		this.idcard = idcard;
		this.flag_len = flag_len;
		this.emsc_tapr_id = emsc_tapr_id;
		this.coco_shebaoid = coco_shebaoid;
		this.msg_a = msg_a;
		this.msg_w = msg_w;
		this.msg_y = msg_y;
	}



	public EmShebaoChangeSZSIModel(int escs_id, int gid, int cid, int ownmonth,
			String escs_company, String escs_name, String escs_change,
			String escs_content, String escs_changehj, String escs_changename,
			String escs_changeidcard, String escs_changeylid,
			String escs_changecid, String escs_changeofficialrank, int escs_s1,
			int escs_s2, int escs_s3, int escs_s4, int escs_s5, int escs_s6,
			int escs_s7, String escs_s8, int escs_c1, int escs_c2, int escs_c3,
			int escs_c4, int escs_c5, int escs_c6, int escs_c7, Date escs_t1,
			Date escs_t2, Date escs_t3, Date escs_t4, Date escs_t5,
			Date escs_t6, Date escs_t7, Date escs_t8, Date escs_o1,
			Date escs_o2, Date escs_o3, Date escs_o4, Date escs_o5,
			Date escs_o6, Date escs_o7, Date escs_o8, Date escs_addtime,
			String escs_addname, int escs_ifdeclare, Date escs_declaretime,
			String escs_declarename, int escs_single, String escs_shebaoid,
			String escs_remark, int escs_ifbase, int escs_ifsb,
			String escs_rtime, String escs_rname, String escs_qname,
			String escs_ttime, String escs_tname, String escs_hname,
			String escs_flag, String escs_flagname) {
		super();
		this.escs_id = escs_id;
		this.gid = gid;
		this.cid = cid;
		this.ownmonth = ownmonth;
		this.escs_company = escs_company;
		this.escs_name = escs_name;
		this.escs_change = escs_change;
		this.escs_content = escs_content;
		this.escs_changehj = escs_changehj;
		this.escs_changename = escs_changename;
		this.escs_changeidcard = escs_changeidcard;
		this.escs_changeylid = escs_changeylid;
		this.escs_changecid = escs_changecid;
		this.escs_changeofficialrank = escs_changeofficialrank;
		this.escs_s1 = escs_s1;
		this.escs_s2 = escs_s2;
		this.escs_s3 = escs_s3;
		this.escs_s4 = escs_s4;
		this.escs_s5 = escs_s5;
		this.escs_s6 = escs_s6;
		this.escs_s7 = escs_s7;
		this.escs_s8 = escs_s8;
		this.escs_c1 = escs_c1;
		this.escs_c2 = escs_c2;
		this.escs_c3 = escs_c3;
		this.escs_c4 = escs_c4;
		this.escs_c5 = escs_c5;
		this.escs_c6 = escs_c6;
		this.escs_c7 = escs_c7;
		this.escs_t1 = escs_t1;
		this.escs_t2 = escs_t2;
		this.escs_t3 = escs_t3;
		this.escs_t4 = escs_t4;
		this.escs_t5 = escs_t5;
		this.escs_t6 = escs_t6;
		this.escs_t7 = escs_t7;
		this.escs_t8 = escs_t8;
		this.escs_o1 = escs_o1;
		this.escs_o2 = escs_o2;
		this.escs_o3 = escs_o3;
		this.escs_o4 = escs_o4;
		this.escs_o5 = escs_o5;
		this.escs_o6 = escs_o6;
		this.escs_o7 = escs_o7;
		this.escs_o8 = escs_o8;
		this.escs_addtime = escs_addtime;
		this.escs_addname = escs_addname;
		this.escs_ifdeclare = escs_ifdeclare;
		this.escs_declaretime = escs_declaretime;
		this.escs_declarename = escs_declarename;
		this.escs_single = escs_single;
		this.escs_shebaoid = escs_shebaoid;
		this.escs_remark = escs_remark;
		this.escs_ifbase = escs_ifbase;
		this.escs_ifsb = escs_ifsb;
		this.escs_rtime = escs_rtime;
		this.escs_rname = escs_rname;
		this.escs_qname = escs_qname;
		this.escs_ttime = escs_ttime;
		this.escs_tname = escs_tname;
		this.escs_hname = escs_hname;
		this.escs_flag = escs_flag;
		this.escs_flagname = escs_flagname;
	}

	
	
	public String getEscs_confirmtime() {
		return escs_confirmtime;
	}



	public void setEscs_confirmtime(String escs_confirmtime) {
		this.escs_confirmtime = escs_confirmtime;
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



	public int getEscs_id() {
		return escs_id;
	}

	public void setEscs_id(int escs_id) {
		this.escs_id = escs_id;
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

	public String getEscs_company() {
		return escs_company;
	}

	public void setEscs_company(String escs_company) {
		this.escs_company = escs_company;
	}

	public String getEscs_name() {
		return escs_name;
	}

	public void setEscs_name(String escs_name) {
		this.escs_name = escs_name;
	}

	public String getEscs_change() {
		return escs_change;
	}

	public void setEscs_change(String escs_change) {
		this.escs_change = escs_change;
	}

	public String getEscs_content() {
		return escs_content;
	}

	public void setEscs_content(String escs_content) {
		this.escs_content = escs_content;
	}

	public String getEscs_changehj() {
		return escs_changehj;
	}

	public void setEscs_changehj(String escs_changehj) {
		this.escs_changehj = escs_changehj;
	}

	public String getEscs_changename() {
		return escs_changename;
	}

	public void setEscs_changename(String escs_changename) {
		this.escs_changename = escs_changename;
	}

	public String getEscs_changeidcard() {
		return escs_changeidcard;
	}

	public void setEscs_changeidcard(String escs_changeidcard) {
		this.escs_changeidcard = escs_changeidcard;
	}

	public String getEscs_changeylid() {
		return escs_changeylid;
	}

	public void setEscs_changeylid(String escs_changeylid) {
		this.escs_changeylid = escs_changeylid;
	}

	public String getEscs_changecid() {
		return escs_changecid;
	}

	public void setEscs_changecid(String escs_changecid) {
		this.escs_changecid = escs_changecid;
	}

	public String getEscs_changeofficialrank() {
		return escs_changeofficialrank;
	}

	public void setEscs_changeofficialrank(String escs_changeofficialrank) {
		this.escs_changeofficialrank = escs_changeofficialrank;
	}

	public int getEscs_s1() {
		return escs_s1;
	}

	public void setEscs_s1(int escs_s1) {
		this.escs_s1 = escs_s1;
	}

	public int getEscs_s2() {
		return escs_s2;
	}

	public void setEscs_s2(int escs_s2) {
		this.escs_s2 = escs_s2;
	}

	public int getEscs_s3() {
		return escs_s3;
	}

	public void setEscs_s3(int escs_s3) {
		this.escs_s3 = escs_s3;
	}

	public int getEscs_s4() {
		return escs_s4;
	}

	public void setEscs_s4(int escs_s4) {
		this.escs_s4 = escs_s4;
	}

	public int getEscs_s5() {
		return escs_s5;
	}

	public void setEscs_s5(int escs_s5) {
		this.escs_s5 = escs_s5;
	}

	public int getEscs_s6() {
		return escs_s6;
	}

	public void setEscs_s6(int escs_s6) {
		this.escs_s6 = escs_s6;
	}

	public int getEscs_s7() {
		return escs_s7;
	}

	public void setEscs_s7(int escs_s7) {
		this.escs_s7 = escs_s7;
	}

	public String getEscs_s8() {
		return escs_s8;
	}

	public void setEscs_s8(String escs_s8) {
		this.escs_s8 = escs_s8;
	}

	public int getEscs_c1() {
		return escs_c1;
	}

	public void setEscs_c1(int escs_c1) {
		this.escs_c1 = escs_c1;
	}

	public int getEscs_c2() {
		return escs_c2;
	}

	public void setEscs_c2(int escs_c2) {
		this.escs_c2 = escs_c2;
	}

	public int getEscs_c3() {
		return escs_c3;
	}

	public void setEscs_c3(int escs_c3) {
		this.escs_c3 = escs_c3;
	}

	public int getEscs_c4() {
		return escs_c4;
	}

	public void setEscs_c4(int escs_c4) {
		this.escs_c4 = escs_c4;
	}

	public int getEscs_c5() {
		return escs_c5;
	}

	public void setEscs_c5(int escs_c5) {
		this.escs_c5 = escs_c5;
	}

	public int getEscs_c6() {
		return escs_c6;
	}

	public void setEscs_c6(int escs_c6) {
		this.escs_c6 = escs_c6;
	}

	public int getEscs_c7() {
		return escs_c7;
	}

	public void setEscs_c7(int escs_c7) {
		this.escs_c7 = escs_c7;
	}

	public Date getEscs_t1() {
		return escs_t1;
	}

	public void setEscs_t1(Date escs_t1) {
		this.escs_t1 = escs_t1;
	}

	public Date getEscs_t2() {
		return escs_t2;
	}

	public void setEscs_t2(Date escs_t2) {
		this.escs_t2 = escs_t2;
	}

	public Date getEscs_t3() {
		return escs_t3;
	}

	public void setEscs_t3(Date escs_t3) {
		this.escs_t3 = escs_t3;
	}

	public Date getEscs_t4() {
		return escs_t4;
	}

	public void setEscs_t4(Date escs_t4) {
		this.escs_t4 = escs_t4;
	}

	public Date getEscs_t5() {
		return escs_t5;
	}

	public void setEscs_t5(Date escs_t5) {
		this.escs_t5 = escs_t5;
	}

	public Date getEscs_t6() {
		return escs_t6;
	}

	public void setEscs_t6(Date escs_t6) {
		this.escs_t6 = escs_t6;
	}

	public Date getEscs_t7() {
		return escs_t7;
	}

	public void setEscs_t7(Date escs_t7) {
		this.escs_t7 = escs_t7;
	}

	public Date getEscs_t8() {
		return escs_t8;
	}

	public void setEscs_t8(Date escs_t8) {
		this.escs_t8 = escs_t8;
	}

	public Date getEscs_o1() {
		return escs_o1;
	}

	public void setEscs_o1(Date escs_o1) {
		this.escs_o1 = escs_o1;
	}

	public Date getEscs_o2() {
		return escs_o2;
	}

	public void setEscs_o2(Date escs_o2) {
		this.escs_o2 = escs_o2;
	}

	public Date getEscs_o3() {
		return escs_o3;
	}

	public void setEscs_o3(Date escs_o3) {
		this.escs_o3 = escs_o3;
	}

	public Date getEscs_o4() {
		return escs_o4;
	}

	public void setEscs_o4(Date escs_o4) {
		this.escs_o4 = escs_o4;
	}

	public Date getEscs_o5() {
		return escs_o5;
	}

	public void setEscs_o5(Date escs_o5) {
		this.escs_o5 = escs_o5;
	}

	public Date getEscs_o6() {
		return escs_o6;
	}

	public void setEscs_o6(Date escs_o6) {
		this.escs_o6 = escs_o6;
	}

	public Date getEscs_o7() {
		return escs_o7;
	}

	public void setEscs_o7(Date escs_o7) {
		this.escs_o7 = escs_o7;
	}

	public Date getEscs_o8() {
		return escs_o8;
	}

	public void setEscs_o8(Date escs_o8) {
		this.escs_o8 = escs_o8;
	}

	public Date getEscs_addtime() {
		return escs_addtime;
	}

	public void setEscs_addtime(Date escs_addtime) {
		this.escs_addtime = escs_addtime;
	}

	public String getEscs_addname() {
		return escs_addname;
	}

	public void setEscs_addname(String escs_addname) {
		this.escs_addname = escs_addname;
	}

	public int getEscs_ifdeclare() {
		return escs_ifdeclare;
	}

	public void setEscs_ifdeclare(int escs_ifdeclare) {
		this.escs_ifdeclare = escs_ifdeclare;
	}

	public Date getEscs_declaretime() {
		return escs_declaretime;
	}

	public void setEscs_declaretime(Date escs_declaretime) {
		this.escs_declaretime = escs_declaretime;
	}

	public String getEscs_declarename() {
		return escs_declarename;
	}

	public void setEscs_declarename(String escs_declarename) {
		this.escs_declarename = escs_declarename;
	}

	public int getEscs_single() {
		return escs_single;
	}

	public void setEscs_single(int escs_single) {
		this.escs_single = escs_single;
	}

	public String getEscs_shebaoid() {
		return escs_shebaoid;
	}

	public void setEscs_shebaoid(String escs_shebaoid) {
		this.escs_shebaoid = escs_shebaoid;
	}

	public String getEscs_remark() {
		return escs_remark;
	}

	public void setEscs_remark(String escs_remark) {
		this.escs_remark = escs_remark;
	}

	public int getEscs_ifbase() {
		return escs_ifbase;
	}

	public void setEscs_ifbase(int escs_ifbase) {
		this.escs_ifbase = escs_ifbase;
	}

	public int getEscs_ifsb() {
		return escs_ifsb;
	}

	public void setEscs_ifsb(int escs_ifsb) {
		this.escs_ifsb = escs_ifsb;
	}

	public String getEscs_rtime() {
		return escs_rtime;
	}

	public void setEscs_rtime(String escs_rtime) {
		this.escs_rtime = escs_rtime;
	}

	public String getEscs_rname() {
		return escs_rname;
	}

	public void setEscs_rname(String escs_rname) {
		this.escs_rname = escs_rname;
	}

	public String getEscs_qname() {
		return escs_qname;
	}

	public void setEscs_qname(String escs_qname) {
		this.escs_qname = escs_qname;
	}

	public String getEscs_ttime() {
		return escs_ttime;
	}

	public void setEscs_ttime(String escs_ttime) {
		this.escs_ttime = escs_ttime;
	}

	public String getEscs_tname() {
		return escs_tname;
	}

	public void setEscs_tname(String escs_tname) {
		this.escs_tname = escs_tname;
	}

	public String getEscs_hname() {
		return escs_hname;
	}

	public void setEscs_hname(String escs_hname) {
		this.escs_hname = escs_hname;
	}

	public String getEscs_flag() {
		return escs_flag;
	}

	public void setEscs_flag(String escs_flag) {
		this.escs_flag = escs_flag;

		try {
			this.flag_len = escs_flag.length();
		} catch (Exception e) {
			this.flag_len = 0;
		}
	}

	public String getEscs_flagname() {
		return escs_flagname;
	}

	public void setEscs_flagname(String escs_flagname) {
		this.escs_flagname = escs_flagname;
	}

	public String getComputerid() {
		return computerid;
	}

	public void setComputerid(String computerid) {
		this.computerid = computerid;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public int getFlag_len() {
		return flag_len;
	}

	public void setFlag_len(int flag_len) {
		this.flag_len = flag_len;
	}

	public int getEmsc_tapr_id() {
		return emsc_tapr_id;
	}

	public void setEmsc_tapr_id(int emsc_tapr_id) {
		this.emsc_tapr_id = emsc_tapr_id;
	}



	public String getEscs_uploadfile() {
		return escs_uploadfile;
	}



	public void setEscs_uploadfile(String escs_uploadfile) {
		this.escs_uploadfile = escs_uploadfile;
	}



	public Integer getEscs_ifnet() {
		return escs_ifnet;
	}



	public void setEscs_ifnet(Integer escs_ifnet) {
		this.escs_ifnet = escs_ifnet;
	}

}
