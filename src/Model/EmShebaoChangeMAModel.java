package Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class EmShebaoChangeMAModel {
	private final DecimalFormat df = new DecimalFormat("#.00");
	private Integer id;
	private Integer cid;
	private Integer gid;
	private Integer ownmonth;
	private String escm_name;
	private String escm_company;
	private String escm_shortname;
	private String escm_computerid;
	private String escm_idcard;
	private String escm_hj;
	private String escm_sex;
	private Integer escm_age;
	private Integer escm_single;// 社保开户状态
	private String escm_single_str;
	private Integer escm_ifdeclare;// 申报状态
	private String escm_ifdeclare_str;
	private String escm_declaretime;// 申报时间
	private String escm_declarename;// 申报人
	private String escm_addtime;// 添加时间
	private String escm_addname;// 添加人
	private Integer escm_easylabour = 0;// 是否顺产
	private boolean chkeasylabour;

	private Integer escm_easylabourmb;// 顺产胎数
	private String escm_easylabourmb_str;// 顺产胎数
	private Integer escm_dystocia = 0;// 是否难产
	private boolean chkdystocia;

	private Integer escm_dystociatype;// 难产类型
	private String escm_dystociatype_str;
	private Integer escm_dystociamb;// 难产胎数
	private String escm_dystociamb_str;// 难产胎数

	private Integer escm_abortion_fm = 0;// 是否怀孕满4个月流产
	private boolean chkabortion_fm;

	private Integer escm_abortion_nfm = 0;// 是否怀孕未满4个月流产
	private boolean chkabortion_nfm;

	private Integer escm_setiud = 0;// 放置宫内节育器
	private boolean chksetiud;

	private Integer escm_getiud = 0;// 取出宫内节育器
	private boolean chkgetiud;

	private Integer escm_tuballigation = 0;// 输卵管结扎手术
	private boolean chktuballigation;

	private Integer escm_tubalreversal = 0;// 输卵管复通手术
	private boolean chktubalreversal;

	private Integer escm_vasoligation = 0;// 输精管结扎
	private boolean chkvasoligation;

	private Integer escm_vasostomy = 0;// 输精管复通
	private boolean chkvasostomy;

	private String escm_endoffp;// 妊娠/计划生育结束日期

	private String escm_mobile;// 联系电话
	private Integer escm_ifpay;// 垫款情况
	private String escm_ifpay_str;//
	private String escm_bank;// 银行
	private String escm_bankacc;// 银行账户
	private Integer escm_ifagree;// 是否同意
	private String escm_ifagree_str;
	private String escm_confirmtime;// 确认时间
	private String escm_remark;// 备注
	private Integer escm_ifdata;// 是否有材料
	// 系统短信总数
	private String msg_a;
	private String coco_shebaoid;
	private String escm_client;
	private String escm_flag;
	private String escm_reason;
	private String escm_batchnum;// 批次号
	private String escm_af_filename;// 申请表
	private String escm_bf_filename;// 批量表
	private String escm_def_filename;//决定书
	private String escm_def_filetime;//决定书上传时间

	private BigDecimal escm_fee;//金额
	
	private String escm_fd_ctime;// 财务确认收款时间
	private String escm_fd_cname;// 财务确认收款操作人
	
	private String escm_csd_ftime;// 客服完结时间
	private String escm_csd_fname;// 客服完结操作人
	
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

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public String getEscm_name() {
		return escm_name;
	}

	public void setEscm_name(String escm_name) {
		this.escm_name = escm_name;
	}

	public String getEscm_company() {
		return escm_company;
	}

	public void setEscm_company(String escm_company) {
		this.escm_company = escm_company;
	}

	public String getEscm_shortname() {
		return escm_shortname;
	}

	public void setEscm_shortname(String escm_shortname) {
		this.escm_shortname = escm_shortname;
	}

	public String getEscm_computerid() {
		return escm_computerid;
	}

	public void setEscm_computerid(String escm_computerid) {
		this.escm_computerid = escm_computerid;
	}

	public String getEscm_idcard() {
		return escm_idcard;
	}

	public void setEscm_idcard(String escm_idcard) {
		this.escm_idcard = escm_idcard;
	}

	public String getEscm_hj() {
		return escm_hj;
	}

	public void setEscm_hj(String escm_hj) {
		this.escm_hj = escm_hj;
	}

	public String getEscm_sex() {
		return escm_sex;
	}

	public void setEscm_sex(String escm_sex) {
		this.escm_sex = escm_sex;
	}

	public Integer getEscm_age() {
		return escm_age;
	}

	public void setEscm_age(Integer escm_age) {
		this.escm_age = escm_age;
	}

	public Integer getEscm_single() {
		return escm_single;
	}

	public void setEscm_single(Integer escm_single) {
		this.escm_single = escm_single;
	}

	public String getEscm_single_str() {
		return escm_single_str;
	}

	public void setEscm_single_str(String escm_single_str) {
		this.escm_single_str = escm_single_str;
	}

	public Integer getEscm_ifdeclare() {
		return escm_ifdeclare;
	}

	public void setEscm_ifdeclare(Integer escm_ifdeclare) {
		this.escm_ifdeclare = escm_ifdeclare;
	}

	public String getEscm_ifdeclare_str() {
		return escm_ifdeclare_str;
	}

	public void setEscm_ifdeclare_str(String escm_ifdeclare_str) {
		this.escm_ifdeclare_str = escm_ifdeclare_str;
	}

	public String getEscm_declaretime() {
		return escm_declaretime;
	}

	public void setEscm_declaretime(String escm_declaretime) {
		this.escm_declaretime = escm_declaretime;
	}

	public String getEscm_declarename() {
		return escm_declarename;
	}

	public void setEscm_declarename(String escm_declarename) {
		this.escm_declarename = escm_declarename;
	}

	public String getEscm_addtime() {
		return escm_addtime;
	}

	public void setEscm_addtime(String escm_addtime) {
		this.escm_addtime = escm_addtime;
	}

	public String getEscm_addname() {
		return escm_addname;
	}

	public void setEscm_addname(String escm_addname) {
		this.escm_addname = escm_addname;
	}

	public Integer getEscm_easylabour() {
		return escm_easylabour;
	}

	public void setEscm_easylabour(Integer escm_easylabour) {
		this.escm_easylabour = escm_easylabour;
		if (escm_easylabour == 1) {
			chkeasylabour = true;
		} else {
			chkeasylabour = false;
		}

	}

	public Integer getEscm_easylabourmb() {
		return escm_easylabourmb;
	}

	public void setEscm_easylabourmb(Integer escm_easylabourmb) {
		this.escm_easylabourmb = escm_easylabourmb;
		if (escm_easylabourmb == 1) {
			escm_easylabourmb_str = "单";
		} else if (escm_easylabourmb == 2) {
			escm_easylabourmb_str = "双";
		} else if (escm_easylabourmb == 0) {
			escm_easylabourmb_str = "";
		} else {
			escm_easylabourmb_str = String.valueOf(escm_easylabourmb);
		}
	}

	public Integer getEscm_dystocia() {
		return escm_dystocia;
	}

	public void setEscm_dystocia(Integer escm_dystocia) {
		this.escm_dystocia = escm_dystocia;
		if (escm_dystocia == 1) {
			chkdystocia = true;
		} else {
			chkdystocia = false;
		}
	}

	public Integer getEscm_dystociatype() {
		return escm_dystociatype;
	}

	public void setEscm_dystociatype(Integer escm_dystociatype) {
		this.escm_dystociatype = escm_dystociatype;
		if (escm_dystociatype == 1) {
			escm_dystociatype_str = "剖宫产";
		} else if (escm_dystociatype == 2) {
			escm_dystociatype_str = "胎头吸引";
		} else if (escm_dystociatype == 3) {
			escm_dystociatype_str = "胎头旋转";
		} else if (escm_dystociatype == 4) {
			escm_dystociatype_str = "产钳助产";
		} else if (escm_dystociatype == 5) {
			escm_dystociatype_str = "臀位助产";
		} else {
			escm_dystociatype_str = "";
		}
	}

	public String getEscm_dystociatype_str() {
		return escm_dystociatype_str;
	}

	public void setEscm_dystociatype_str(String escm_dystociatype_str) {
		this.escm_dystociatype_str = escm_dystociatype_str;
	}

	public Integer getEscm_dystociamb() {
		return escm_dystociamb;
	}

	public void setEscm_dystociamb(Integer escm_dystociamb) {
		this.escm_dystociamb = escm_dystociamb;
		if (escm_dystociamb == 1) {
			escm_dystociamb_str = "单";
		} else if (escm_dystociamb == 2) {
			escm_dystociamb_str = "双";
		} else if (escm_dystociamb == 0) {
			escm_dystociamb_str = "";
		} else {
			escm_dystociamb_str = String.valueOf(escm_dystociamb);
		}
	}

	public Integer getEscm_abortion_fm() {
		return escm_abortion_fm;
	}

	public void setEscm_abortion_fm(Integer escm_abortion_fm) {
		this.escm_abortion_fm = escm_abortion_fm;
		if (escm_abortion_fm == 1) {
			chkabortion_fm = true;
		} else {
			chkabortion_fm = false;
		}
	}

	public Integer getEscm_abortion_nfm() {
		return escm_abortion_nfm;
	}

	public void setEscm_abortion_nfm(Integer escm_abortion_nfm) {
		this.escm_abortion_nfm = escm_abortion_nfm;
		if (escm_abortion_nfm == 1) {
			chkabortion_nfm = true;
		} else {
			chkabortion_nfm = false;
		}
	}

	public Integer getEscm_setiud() {
		return escm_setiud;
	}

	public void setEscm_setiud(Integer escm_setiud) {
		this.escm_setiud = escm_setiud;
		if (escm_setiud == 1) {
			chksetiud = true;
		} else {
			chksetiud = false;
		}
	}

	public Integer getEscm_getiud() {
		return escm_getiud;
	}

	public void setEscm_getiud(Integer escm_getiud) {
		this.escm_getiud = escm_getiud;
		if (escm_getiud == 1) {
			chkgetiud = true;
		} else {
			chkgetiud = false;
		}
	}

	public Integer getEscm_tuballigation() {
		return escm_tuballigation;
	}

	public void setEscm_tuballigation(Integer escm_tuballigation) {
		this.escm_tuballigation = escm_tuballigation;
		if (escm_tuballigation == 1) {
			chktuballigation = true;
		} else {
			chktuballigation = false;
		}
	}

	public Integer getEscm_tubalreversal() {
		return escm_tubalreversal;
	}

	public void setEscm_tubalreversal(Integer escm_tubalreversal) {
		this.escm_tubalreversal = escm_tubalreversal;
		if (escm_tubalreversal == 1) {
			chktubalreversal = true;
		} else {
			chktubalreversal = false;
		}
	}

	public Integer getEscm_vasoligation() {
		return escm_vasoligation;
	}

	public void setEscm_vasoligation(Integer escm_vasoligation) {
		this.escm_vasoligation = escm_vasoligation;
		if (escm_vasoligation == 1) {
			chkvasoligation = true;
		} else {
			chkvasoligation = false;
		}
	}

	public Integer getEscm_vasostomy() {
		return escm_vasostomy;
	}

	public void setEscm_vasostomy(Integer escm_vasostomy) {
		this.escm_vasostomy = escm_vasostomy;
		if (escm_vasostomy == 1) {
			chkvasostomy = true;
		} else {
			chkvasostomy = false;
		}
	}

	public String getEscm_endoffp() {
		return escm_endoffp;
	}

	public void setEscm_endoffp(String escm_endoffp) {
		this.escm_endoffp = escm_endoffp;
	}

	public String getEscm_mobile() {
		return escm_mobile;
	}

	public void setEscm_mobile(String escm_mobile) {
		this.escm_mobile = escm_mobile;
	}

	public Integer getEscm_ifpay() {
		return escm_ifpay;
	}

	public void setEscm_ifpay(Integer escm_ifpay) {
		this.escm_ifpay = escm_ifpay;
		if (escm_ifpay == 1) {
			escm_ifpay_str = "已支付";
		} else {
			escm_ifpay_str = "未支付";
		}
	}

	public String getEscm_ifpay_str() {
		return escm_ifpay_str;
	}

	public void setEscm_ifpay_str(String escm_ifpay_str) {
		this.escm_ifpay_str = escm_ifpay_str;
	}

	public String getEscm_bank() {
		return escm_bank;
	}

	public void setEscm_bank(String escm_bank) {
		this.escm_bank = escm_bank;
	}

	public String getEscm_bankacc() {
		return escm_bankacc;
	}

	public void setEscm_bankacc(String escm_bankacc) {
		this.escm_bankacc = escm_bankacc;
	}

	public Integer getEscm_ifagree() {
		return escm_ifagree;
	}

	public void setEscm_ifagree(Integer escm_ifagree) {
		this.escm_ifagree = escm_ifagree;
		if (escm_ifagree == 1) {
			escm_ifagree_str = "是";
		} else {
			escm_ifagree_str = "否";
		}
	}

	public String getEscm_ifagree_str() {
		return escm_ifagree_str;
	}

	public void setEscm_ifagree_str(String escm_ifagree_str) {
		this.escm_ifagree_str = escm_ifagree_str;
	}

	public boolean isChkeasylabour() {
		return chkeasylabour;
	}

	public void setChkeasylabour(boolean chkeasylabour) {
		this.chkeasylabour = chkeasylabour;
		if (chkeasylabour) {
			escm_easylabour = 1;
		} else {
			escm_easylabour = 0;
		}
	}

	public boolean isChkdystocia() {
		return chkdystocia;
	}

	public void setChkdystocia(boolean chkdystocia) {
		this.chkdystocia = chkdystocia;
		if (chkdystocia) {
			escm_dystocia = 1;
		} else {
			escm_dystocia = 0;
		}
	}

	public boolean isChkabortion_fm() {
		return chkabortion_fm;
	}

	public void setChkabortion_fm(boolean chkabortion_fm) {
		this.chkabortion_fm = chkabortion_fm;
		if (chkabortion_fm) {
			escm_abortion_fm = 1;
		} else {
			escm_abortion_fm = 0;
		}
	}

	public boolean isChkabortion_nfm() {
		return chkabortion_nfm;
	}

	public void setChkabortion_nfm(boolean chkabortion_nfm) {
		this.chkabortion_nfm = chkabortion_nfm;
		if (chkabortion_nfm) {
			escm_abortion_nfm = 1;
		} else {
			escm_abortion_nfm = 0;
		}
	}

	public boolean isChksetiud() {
		return chksetiud;
	}

	public void setChksetiud(boolean chksetiud) {
		this.chksetiud = chksetiud;
		if (chksetiud) {
			escm_setiud = 1;
		} else {
			escm_setiud = 0;
		}
	}

	public boolean isChkgetiud() {
		return chkgetiud;
	}

	public void setChkgetiud(boolean chkgetiud) {
		this.chkgetiud = chkgetiud;
		if (chkgetiud) {
			escm_getiud = 1;
		} else {
			escm_getiud = 0;
		}
	}

	public boolean isChktuballigation() {
		return chktuballigation;
	}

	public void setChktuballigation(boolean chktuballigation) {
		this.chktuballigation = chktuballigation;
		if (chktuballigation) {
			escm_tuballigation = 1;
		} else {
			escm_tuballigation = 0;
		}
	}

	public boolean isChktubalreversal() {
		return chktubalreversal;
	}

	public void setChktubalreversal(boolean chktubalreversal) {
		this.chktubalreversal = chktubalreversal;
		if (chktubalreversal) {
			escm_tubalreversal = 1;
		} else {
			escm_tubalreversal = 0;
		}
	}

	public boolean isChkvasoligation() {
		return chkvasoligation;
	}

	public void setChkvasoligation(boolean chkvasoligation) {
		this.chkvasoligation = chkvasoligation;
		if (chkvasoligation) {
			escm_vasoligation = 1;
		} else {
			escm_vasoligation = 0;
		}
	}

	public boolean isChkvasostomy() {
		return chkvasostomy;
	}

	public void setChkvasostomy(boolean chkvasostomy) {
		this.chkvasostomy = chkvasostomy;
		if (chkvasostomy) {
			escm_vasostomy = 1;
		} else {
			escm_vasostomy = 0;
		}
	}

	public Integer getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(Integer ownmonth) {
		this.ownmonth = ownmonth;
	}

	public String getEscm_confirmtime() {
		return escm_confirmtime;
	}

	public void setEscm_confirmtime(String escm_confirmtime) {
		this.escm_confirmtime = escm_confirmtime;
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

	public String getEscm_client() {
		return escm_client;
	}

	public void setEscm_client(String escm_client) {
		this.escm_client = escm_client;
	}

	public String getEscm_flag() {
		return escm_flag;
	}

	public void setEscm_flag(String escm_flag) {
		this.escm_flag = escm_flag;
	}

	public String getEscm_reason() {
		return escm_reason;
	}

	public void setEscm_reason(String escm_reason) {
		this.escm_reason = escm_reason;
	}

	public String getEscm_remark() {
		return escm_remark;
	}

	public void setEscm_remark(String escm_remark) {
		this.escm_remark = escm_remark;
	}

	public String getEscm_easylabourmb_str() {
		return escm_easylabourmb_str;
	}

	public void setEscm_easylabourmb_str(String escm_easylabourmb_str) {
		this.escm_easylabourmb_str = escm_easylabourmb_str;
	}

	public String getEscm_dystociamb_str() {
		return escm_dystociamb_str;
	}

	public void setEscm_dystociamb_str(String escm_dystociamb_str) {
		this.escm_dystociamb_str = escm_dystociamb_str;
	}

	public Integer getEscm_ifdata() {
		return escm_ifdata;
	}

	public void setEscm_ifdata(Integer escm_ifdata) {
		this.escm_ifdata = escm_ifdata;
	}

	public String getEscm_batchnum() {
		return escm_batchnum;
	}

	public void setEscm_batchnum(String escm_batchnum) {
		this.escm_batchnum = escm_batchnum;
	}

	public String getEscm_af_filename() {
		return escm_af_filename;
	}

	public void setEscm_af_filename(String escm_af_filename) {
		this.escm_af_filename = escm_af_filename;
	}

	public String getEscm_bf_filename() {
		return escm_bf_filename;
	}

	public void setEscm_bf_filename(String escm_bf_filename) {
		this.escm_bf_filename = escm_bf_filename;
	}

	public String getEscm_def_filename() {
		return escm_def_filename;
	}

	public void setEscm_def_filename(String escm_def_filename) {
		this.escm_def_filename = escm_def_filename;
	}

	public BigDecimal getEscm_fee() {
		return escm_fee;
	}

	public void setEscm_fee(BigDecimal escm_fee) {
		this.escm_fee = new BigDecimal(df.format(escm_fee));
	}

	public String getEscm_fd_ctime() {
		return escm_fd_ctime;
	}

	public void setEscm_fd_ctime(String escm_fd_ctime) {
		this.escm_fd_ctime = escm_fd_ctime;
	}

	public String getEscm_fd_cname() {
		return escm_fd_cname;
	}

	public void setEscm_fd_cname(String escm_fd_cname) {
		this.escm_fd_cname = escm_fd_cname;
	}

	public String getEscm_csd_ftime() {
		return escm_csd_ftime;
	}

	public void setEscm_csd_ftime(String escm_csd_ftime) {
		this.escm_csd_ftime = escm_csd_ftime;
	}

	public String getEscm_csd_fname() {
		return escm_csd_fname;
	}

	public void setEscm_csd_fname(String escm_csd_fname) {
		this.escm_csd_fname = escm_csd_fname;
	}

	public String getEscm_def_filetime() {
		return escm_def_filetime;
	}

	public void setEscm_def_filetime(String escm_def_filetime) {
		this.escm_def_filetime = escm_def_filetime;
	}


}
