package Model;

import java.math.BigDecimal;

public class EmHouseChangeModel {
	// / emhc_id
	private Integer emhc_id;
	private Integer emhu_id;
	// / gid
	private Integer gid;
	// / cid
	private Integer cid;
	// / ownmonth
	private Integer ownmonth;
	// / emhc_startmonth
	private String emhc_startmonth;
	// / emhc_companyid
	private String emhc_companyid;
	// / emhc_excompany
	private String emhc_excompany;
	// / emhc_excompanyid
	private String emhc_excompanyid;
	// / emhc_company
	private String emhc_company;
	// / emhc_shortname
	private String emhc_shortname;
	// / emhc_name
	private String emhc_name;
	// / emhc_idcard
	private String emhc_idcard;
	// / emhc_idcardclass
	private String emhc_idcardclass;
	// / emhc_hj
	private String emhc_hj;
	// / emhc_computerid
	private String emhc_computerid;
	// / emhc_houseid
	private String emhc_houseid;
	// / emhc_mobile
	private String emhc_mobile;
	// / emhc_title
	private String emhc_title;
	// / emhc_wifename
	private String emhc_wifename;
	// / emhc_wifeidcard
	private String emhc_wifeidcard;
	// / emhc_degree
	private String emhc_degree;
	// / emhc_change
	private String emhc_change;
	// / emhc_radix
	private Double emhc_radix;
	// / emhc_trueradix
	private Double emhc_trueradix;
	// / emhc_bonus
	private BigDecimal emhc_bonus;
	// / emhc_cpp
	private Double emhc_cpp;
	private String emhc_cpp2;
	// / emhc_opp
	private Double emhc_opp;
	private String emhc_opp2;

	private Double cohf_cpp;
	// / emhc_single
	private Integer emhc_single;
	private String emhc_single2;
	// / emhc_ifsame
	private Integer emhc_ifsame;
	// / emhc_ifmodify
	private Integer emhc_ifmodify;
	// / emhc_iffifteen
	private Integer emhc_iffifteen;
	// / emhc_ifdeclare
	private Integer emhc_ifdeclare;
	private String emhc_ifdeclare2;
	private String emhc_ifdeclare3;
	// / emhc_ifprogress
	private Integer emhc_ifprogress;
	private String emhc_ifprogress2;
	// / emhc_ifwrong
	private Integer emhc_ifwrong;
	// / emhc_ifmsg
	private Integer emhc_ifmsg;
	// / emhc_declaretime
	private String emhc_declaretime;
	// / emhc_declarename
	private String emhc_declarename;
	// / emhc_addtime
	private String emhc_addtime;
	// / emhc_addname
	private String emhc_addname;
	// / emhc_remark
	private String emhc_remark;
	// / emhc_ConfirmTime
	private String emhc_confirmtime;
	// / emhc_flag
	private String emhc_flag;
	// / emhc_flagname
	private String emhc_flagname;
	// / emhc_excelfile
	private String emhc_excelfile;
	// / emhc_client
	private String emhc_client;
	// / emhc_content
	private String emhc_content;
	// / emhc_tid
	private Integer emhc_tid;
	private Boolean tidchecked;

	private Integer emhc_tapr_id;
	private String emhc_confirmname;

	private Integer num;

	private String emhc_statename;

	private String emhc_progressname;
	private String coba_company;
	private String coba_shortname;

	private String cohf_houseid;

	private boolean emhc_ischecked;

	private String account;
	private Integer bjid;

	private Integer deptId;
	private String dep_name;
	private Integer rolId;
	private Integer tid;

	private boolean judge; // 审核
	private Boolean confirm; // 确认
	// 数据状态
	// 1:未申报/未确认;2:未申报/未确认/退回/核查失败;3:未申报/申报中;
	// 4:待审核;5:未申报/申报中/已申报/核查失败;6:未申报/申报中/已申报/退回/核查失败;
	// 7:申报中/已申报
	private Integer dataState;
	private Integer singleState; // 账户状态,0:中智+中智委托,1独立户
	private String idState; // 拼接SQL语句时,采用IN(),拼接表ID
	private boolean sameInfo;// 同时查询员工编号/身份证/公积金号
	private boolean addnew;
	private String addtimeArea;
	private String declaretimeArea;
	private Integer readstate;// 短信状态
	private Integer pageSize;
	private boolean message;
	private boolean readState;
	private boolean error;
	private String emhc_filename;
	private Integer emhc_type;
	private Integer hsup_id;
	private String tapr_appointcon; // 流程退回权限人
	private String tapr_starname;// 任务操作人
	private String coba_client;

	private String errorMsg;

	private Integer nowmonth;// 入职时判断是否有存在NOWMONTH月份以后的数据

	private String stopmonth;

	private String hsup_batchnumber;

	private String lastday;
	private String tsday;
	private String jc;

	private Boolean alarm;
	private Integer smwr_tid;
	private Integer symr_readstate;

	private Double emhc_hradix;
	private Integer tapr_state;
	private String addname;

	public Integer getEmhc_id() {
		return emhc_id;
	}

	public void setEmhc_id(Integer emhc_id) {
		this.emhc_id = emhc_id;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public Integer getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(Integer ownmonth) {
		this.ownmonth = ownmonth;
	}

	public String getEmhc_startmonth() {
		return emhc_startmonth;
	}

	public void setEmhc_startmonth(String emhc_startmonth) {
		this.emhc_startmonth = emhc_startmonth;
	}

	public String getEmhc_companyid() {
		return emhc_companyid;
	}

	public void setEmhc_companyid(String emhc_companyid) {
		this.emhc_companyid = emhc_companyid;
	}

	public String getEmhc_excompany() {
		return emhc_excompany;
	}

	public void setEmhc_excompany(String emhc_excompany) {
		this.emhc_excompany = emhc_excompany;
	}

	public String getEmhc_excompanyid() {
		return emhc_excompanyid;
	}

	public void setEmhc_excompanyid(String emhc_excompanyid) {
		this.emhc_excompanyid = emhc_excompanyid;
	}

	public String getEmhc_company() {
		return emhc_company;
	}

	public void setEmhc_company(String emhc_company) {
		this.emhc_company = emhc_company;
	}

	public String getEmhc_shortname() {
		return emhc_shortname;
	}

	public void setEmhc_shortname(String emhc_shortname) {
		this.emhc_shortname = emhc_shortname;
	}

	public String getEmhc_name() {
		return emhc_name;
	}

	public void setEmhc_name(String emhc_name) {
		this.emhc_name = emhc_name;
	}

	public String getEmhc_idcard() {
		return emhc_idcard;
	}

	public void setEmhc_idcard(String emhc_idcard) {
		this.emhc_idcard = emhc_idcard;
	}

	public String getEmhc_idcardclass() {
		return emhc_idcardclass;
	}

	public void setEmhc_idcardclass(String emhc_idcardclass) {
		this.emhc_idcardclass = emhc_idcardclass;
	}

	public String getEmhc_hj() {
		return emhc_hj;
	}

	public void setEmhc_hj(String emhc_hj) {
		this.emhc_hj = emhc_hj;
	}

	public String getEmhc_computerid() {
		return emhc_computerid;
	}

	public void setEmhc_computerid(String emhc_computerid) {
		this.emhc_computerid = emhc_computerid;
	}

	public String getEmhc_houseid() {
		return emhc_houseid;
	}

	public void setEmhc_houseid(String emhc_houseid) {
		this.emhc_houseid = emhc_houseid;
	}

	public String getEmhc_mobile() {
		return emhc_mobile;
	}

	public void setEmhc_mobile(String emhc_mobile) {
		this.emhc_mobile = emhc_mobile;
	}

	public String getEmhc_title() {
		return emhc_title;
	}

	public void setEmhc_title(String emhc_title) {
		this.emhc_title = emhc_title;
	}

	public String getEmhc_wifename() {
		return emhc_wifename;
	}

	public void setEmhc_wifename(String emhc_wifename) {
		this.emhc_wifename = emhc_wifename;
	}

	public String getEmhc_wifeidcard() {
		return emhc_wifeidcard;
	}

	public void setEmhc_wifeidcard(String emhc_wifeidcard) {
		this.emhc_wifeidcard = emhc_wifeidcard;
	}

	public String getEmhc_degree() {
		return emhc_degree;
	}

	public void setEmhc_degree(String emhc_degree) {
		this.emhc_degree = emhc_degree;
	}

	public String getEmhc_change() {
		return emhc_change;
	}

	public void setEmhc_change(String emhc_change) {
		this.emhc_change = emhc_change;
	}

	public Double getEmhc_radix() {
		return emhc_radix;
	}

	public void setEmhc_radix(Double emhc_radix) {
		this.emhc_radix = emhc_radix;
	}

	public Double getEmhc_trueradix() {
		return emhc_trueradix;
	}

	public void setEmhc_trueradix(Double emhc_trueradix) {
		this.emhc_trueradix = emhc_trueradix;
	}

	public BigDecimal getEmhc_bonus() {
		return emhc_bonus;
	}

	public void setEmhc_bonus(BigDecimal emhc_bonus) {
		this.emhc_bonus = emhc_bonus;
	}

	public Double getEmhc_cpp() {
		return emhc_cpp;
	}

	public void setEmhc_cpp(Double emhc_cpp) {
		this.emhc_cpp = emhc_cpp;
	}

	public Double getEmhc_opp() {
		return emhc_opp;
	}

	public void setEmhc_opp(Double emhc_opp) {
		this.emhc_opp = emhc_opp;
	}

	public Integer getEmhc_single() {
		return emhc_single;
	}

	public void setEmhc_single(Integer emhc_single) {
		this.emhc_single = emhc_single;
	}

	public Integer getEmhc_ifsame() {
		return emhc_ifsame;
	}

	public void setEmhc_ifsame(Integer emhc_ifsame) {
		this.emhc_ifsame = emhc_ifsame;
	}

	public Integer getEmhc_ifmodify() {
		return emhc_ifmodify;
	}

	public void setEmhc_ifmodify(Integer emhc_ifmodify) {
		this.emhc_ifmodify = emhc_ifmodify;
	}

	public Integer getEmhc_iffifteen() {
		return emhc_iffifteen;
	}

	public void setEmhc_iffifteen(Integer emhc_iffifteen) {
		this.emhc_iffifteen = emhc_iffifteen;
	}

	public Integer getEmhc_ifdeclare() {
		return emhc_ifdeclare;
	}

	public void setEmhc_ifdeclare(Integer emhc_ifdeclare) {
		this.emhc_ifdeclare = emhc_ifdeclare;
	}

	public Integer getEmhc_ifprogress() {
		return emhc_ifprogress;
	}

	public void setEmhc_ifprogress(Integer emhc_ifprogress) {
		this.emhc_ifprogress = emhc_ifprogress;
	}

	public Integer getEmhc_ifwrong() {
		return emhc_ifwrong;
	}

	public void setEmhc_ifwrong(Integer emhc_ifwrong) {
		this.emhc_ifwrong = emhc_ifwrong;
	}

	public Integer getEmhc_ifmsg() {
		return emhc_ifmsg;
	}

	public void setEmhc_ifmsg(Integer emhc_ifmsg) {
		this.emhc_ifmsg = emhc_ifmsg;
	}

	public String getEmhc_declaretime() {
		return emhc_declaretime;
	}

	public void setEmhc_declaretime(String emhc_declaretime) {
		this.emhc_declaretime = emhc_declaretime;
	}

	public String getEmhc_declarename() {
		return emhc_declarename;
	}

	public void setEmhc_declarename(String emhc_declarename) {
		this.emhc_declarename = emhc_declarename;
	}

	public String getEmhc_addtime() {
		return emhc_addtime;
	}

	public void setEmhc_addtime(String emhc_addtime) {
		this.emhc_addtime = emhc_addtime;
	}

	public String getEmhc_addname() {
		return emhc_addname;
	}

	public void setEmhc_addname(String emhc_addname) {
		this.emhc_addname = emhc_addname;
	}

	public String getEmhc_remark() {
		return emhc_remark;
	}

	public void setEmhc_remark(String emhc_remark) {
		this.emhc_remark = emhc_remark;
	}

	public String getEmhc_confirmtime() {
		return emhc_confirmtime;
	}

	public void setEmhc_confirmtime(String emhc_confirmtime) {
		this.emhc_confirmtime = emhc_confirmtime;
	}

	public String getEmhc_flag() {
		return emhc_flag;
	}

	public void setEmhc_flag(String emhc_flag) {
		this.emhc_flag = emhc_flag;
	}

	public String getEmhc_flagname() {
		return emhc_flagname;
	}

	public void setEmhc_flagname(String emhc_flagname) {
		this.emhc_flagname = emhc_flagname;
	}

	public String getEmhc_excelfile() {
		return emhc_excelfile;
	}

	public void setEmhc_excelfile(String emhc_excelfile) {
		this.emhc_excelfile = emhc_excelfile;
	}

	public String getEmhc_client() {
		return emhc_client;
	}

	public void setEmhc_client(String emhc_client) {
		this.emhc_client = emhc_client;
	}

	public String getEmhc_content() {
		return emhc_content;
	}

	public void setEmhc_content(String emhc_content) {
		this.emhc_content = emhc_content;
	}

	public Integer getEmhc_tid() {
		return emhc_tid;
	}

	public void setEmhc_tid(Integer emhc_tid) {
		this.emhc_tid = emhc_tid;
	}

	public Integer getEmhc_tapr_id() {
		return emhc_tapr_id;
	}

	public void setEmhc_tapr_id(Integer emhc_tapr_id) {
		this.emhc_tapr_id = emhc_tapr_id;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getEmhc_statename() {
		return emhc_statename;
	}

	public void setEmhc_statename(String emhc_statename) {
		this.emhc_statename = emhc_statename;
	}

	public String getEmhc_progressname() {
		return emhc_progressname;
	}

	public void setEmhc_progressname(String emhc_progressname) {
		this.emhc_progressname = emhc_progressname;
	}

	public String getCoba_shortname() {
		return coba_shortname;
	}

	public void setCoba_shortname(String coba_shortname) {
		this.coba_shortname = coba_shortname;
	}

	public String getCohf_houseid() {
		return cohf_houseid;
	}

	public void setCohf_houseid(String cohf_houseid) {
		this.cohf_houseid = cohf_houseid;
	}

	public boolean isEmhc_ischecked() {
		return emhc_ischecked;
	}

	public void setEmhc_ischecked(boolean emhc_ischecked) {
		this.emhc_ischecked = emhc_ischecked;
	}

	public Integer getBjid() {
		return bjid;
	}

	public void setBjid(Integer bjid) {
		this.bjid = bjid;
	}

	public String getEmhc_ifdeclare2() {
		return emhc_ifdeclare2;
	}

	public void setEmhc_ifdeclare2(String emhc_ifdeclare2) {
		this.emhc_ifdeclare2 = emhc_ifdeclare2;
	}

	public String getEmhc_ifprogress2() {
		return emhc_ifprogress2;
	}

	public void setEmhc_ifprogress2(String emhc_ifprogress2) {
		this.emhc_ifprogress2 = emhc_ifprogress2;
	}

	public String getEmhc_cpp2() {
		return emhc_cpp2;
	}

	public void setEmhc_cpp2(String emhc_cpp2) {
		this.emhc_cpp2 = emhc_cpp2;
	}

	public String getEmhc_opp2() {
		return emhc_opp2;
	}

	public void setEmhc_opp2(String emhc_opp2) {
		this.emhc_opp2 = emhc_opp2;
	}

	public String getEmhc_single2() {
		return emhc_single2;
	}

	public void setEmhc_single2(String emhc_single2) {
		this.emhc_single2 = emhc_single2;
	}

	public boolean isJudge() {
		return judge;
	}

	public void setJudge(boolean judge) {
		this.judge = judge;
	}

	public Boolean isConfirm() {
		return confirm;
	}

	public void setConfirm(Boolean confirm) {
		this.confirm = confirm;
	}

	public Integer getDataState() {
		return dataState;
	}

	public void setDataState(Integer dataState) {
		this.dataState = dataState;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public Integer getRolId() {
		return rolId;
	}

	public void setRolId(Integer rolId) {
		this.rolId = rolId;
	}

	public String getDep_name() {
		return dep_name;
	}

	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
	}

	public String getIdState() {
		return idState;
	}

	public void setIdState(String idState) {
		this.idState = idState;
	}

	public boolean isSameInfo() {
		return sameInfo;
	}

	public void setSameInfo(boolean sameInfo) {
		this.sameInfo = sameInfo;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAddtimeArea() {
		return addtimeArea;
	}

	public void setAddtimeArea(String addtimeArea) {
		this.addtimeArea = addtimeArea;
	}

	public String getDeclaretimeArea() {
		return declaretimeArea;
	}

	public void setDeclaretimeArea(String declaretimeArea) {
		this.declaretimeArea = declaretimeArea;
	}

	public Integer getSingleState() {
		return singleState;
	}

	public void setSingleState(Integer singleState) {
		this.singleState = singleState;
	}

	public Integer getReadstate() {
		return readstate;
	}

	public void setReadstate(Integer readstate) {
		this.readstate = readstate;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public boolean isMessage() {
		return message;
	}

	public void setMessage(boolean message) {
		this.message = message;
	}

	public boolean getMessage() {
		return message;
	}

	public boolean isReadState() {
		return readState;
	}

	public void setReadState(boolean readState) {
		this.readState = readState;
	}

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public Integer getNowmonth() {
		return nowmonth;
	}

	public void setNowmonth(Integer nowmonth) {
		this.nowmonth = nowmonth;
	}

	public String getCoba_company() {
		return coba_company;
	}

	public void setCoba_company(String coba_company) {
		this.coba_company = coba_company;
	}

	public boolean isAddnew() {
		return addnew;
	}

	public void setAddnew(boolean addnew) {
		this.addnew = addnew;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public boolean getError() {
		return error;
	}

	public Integer getEmhu_id() {
		return emhu_id;
	}

	public void setEmhu_id(Integer emhu_id) {
		this.emhu_id = emhu_id;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getEmhc_filename() {
		return emhc_filename;
	}

	public void setEmhc_filename(String emhc_filename) {
		this.emhc_filename = emhc_filename;
	}

	public Double getCohf_cpp() {
		return cohf_cpp;
	}

	public void setCohf_cpp(Double cohf_cpp) {
		this.cohf_cpp = cohf_cpp;
	}

	public Integer getEmhc_type() {
		return emhc_type;
	}

	public void setEmhc_type(Integer emhc_type) {
		this.emhc_type = emhc_type;
	}

	public Integer getHsup_id() {
		return hsup_id;
	}

	public void setHsup_id(Integer hsup_id) {
		this.hsup_id = hsup_id;
	}

	public String getTapr_appointcon() {
		return tapr_appointcon;
	}

	public void setTapr_appointcon(String tapr_appointcon) {
		this.tapr_appointcon = tapr_appointcon;
	}

	public Boolean getConfirm() {
		return confirm;
	}

	public String getCoba_client() {
		return coba_client;
	}

	public void setCoba_client(String coba_client) {
		this.coba_client = coba_client;
	}

	public String getTapr_starname() {
		return tapr_starname;
	}

	public void setTapr_starname(String tapr_starname) {
		this.tapr_starname = tapr_starname;
	}

	public String getEmhc_confirmname() {
		return emhc_confirmname;
	}

	public void setEmhc_confirmname(String emhc_confirmname) {
		this.emhc_confirmname = emhc_confirmname;
	}

	public String getStopmonth() {
		return stopmonth;
	}

	public void setStopmonth(String stopmonth) {
		this.stopmonth = stopmonth;
	}

	public String getHsup_batchnumber() {
		return hsup_batchnumber;
	}

	public void setHsup_batchnumber(String hsup_batchnumber) {
		this.hsup_batchnumber = hsup_batchnumber;
	}

	public String getLastday() {
		return lastday;
	}

	public void setLastday(String lastday) {
		this.lastday = lastday;
	}

	public Boolean getAlarm() {
		return alarm;
	}

	public void setAlarm(Boolean alarm) {
		this.alarm = alarm;
	}

	public Integer getSmwr_tid() {
		return smwr_tid;
	}

	public void setSmwr_tid(Integer smwr_tid) {
		this.smwr_tid = smwr_tid;
	}

	public Integer getSymr_readstate() {
		return symr_readstate;
	}

	public void setSymr_readstate(Integer symr_readstate) {
		this.symr_readstate = symr_readstate;
	}

	public String getEmhc_ifdeclare3() {
		return emhc_ifdeclare3;
	}

	public void setEmhc_ifdeclare3(String emhc_ifdeclare3) {
		this.emhc_ifdeclare3 = emhc_ifdeclare3;
	}

	public String getTsday() {
		return tsday;
	}

	public void setTsday(String tsday) {
		this.tsday = tsday;
	}

	public Double getEmhc_hradix() {
		return emhc_hradix;
	}

	public void setEmhc_hradix(Double emhc_hradix) {
		this.emhc_hradix = emhc_hradix;
	}

	public Boolean getTidchecked() {
		return tidchecked;
	}

	public void setTidchecked(Boolean tidchecked) {
		this.tidchecked = tidchecked;
	}

	public String getJc() {
		return jc;
	}

	public void setJc(String jc) {
		this.jc = jc;
	}

	public Integer getTapr_state() {
		return tapr_state;
	}

	public void setTapr_state(Integer tapr_state) {
		this.tapr_state = tapr_state;
	}

	public String getAddname() {
		return addname;
	}

	public void setAddname(String addname) {
		this.addname = addname;
	}

}