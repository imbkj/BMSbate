package Model;

import java.math.BigDecimal;

public class EmBodyCheckListModel {
	// / ebcl_id
	private Integer ebcl_id;
	// / 员工体检基本信息表EmBodyCheck的id
	private Integer ebcl_embc_id;
	// / 机构表EmBcSetup的id
	private Integer ebcl_hospital;
	// / 地址表id
	private Integer ebcl_area;
	// / ebcl_itemgroup
	private String ebcl_itemgroup;
	// / ebcl_items
	private String ebcl_items;
	// / ebcl_itemnums
	private String ebcl_itemnums;
	// / ebcl_charge
	private BigDecimal ebcl_charge;
	// / ebcl_discount
	private BigDecimal ebcl_discount;
	// / 预约时间
	private String ebcl_bookdate;
	// / 联系医院时间
	private String ebcl_linkdate;
	// / 领取体检单
	private String ebcl_drawformdate;
	// / 签收体检单
	private String ebcl_showformdate;
	// / ebcl_showformpeople
	private String ebcl_showformpeople;
	// / 安排体检时间
	private String ebcl_plandate;
	// / 报销时间
	private String ebcl_wodate;
	// / 0、已取消；1、待确认； 2、待申报； 3、体检中； 4、已体检； 5、已收报告； 6、已报销； 7、已退回； 8、报销处理；
	// 9、重新预约；10、预约中；
	private Integer ebcl_state;
	// / 0:单次体检;1:入职体检;2:年度体检;
	private Integer ebcl_type;
	// / 报销
	private Integer ebcl_bx;
	// / 体检变动信息(0:原始记录;1:时间变动;2:项目变动;3:1&2)
	private Integer ebcl_change;
	// / 0:待确认;1:已申报;2:退回;
	private Integer ebcl_commits;
	// / 1:特殊数据
	private Integer ebcl_special;
	// / 客服填写备注
	private String ebcl_remark;
	// / 福利退回备注
	private String ebcl_remark2;
	// / ebcl_exportnum
	private String ebcl_exportnum;
	// / 导出医院名单文件名
	private String ebcl_filename;
	// / ebcl_addtime
	private String ebcl_addtime;
	// / ebcl_addname
	private String ebcl_addname;
	// / ebcl_modtime
	private String ebcl_modtime;
	// / ebcl_modname
	private String ebcl_modname;
	// / 预约时间模式(1:定期/2:不定期)
	private Integer ebcl_bookmode;

	private String ebcl_completeDate;
	private String ebcl_clientshowdate;
	private String ebcl_showclient;
	// / 核收报告时间
	private String ebcl_showreportdate;
	// / 核收报告人
	private String ebcl_showreportpeople;
	// / 结算费用
	private BigDecimal ebcl_finalcharge;
	// / 结算时间
	private String ebcl_balancedate;
	private String ebcl_drawreportdate;
	
	private String ebcl_clientbackcase;
	private String ebcl_clientbackname;
	private String ebcl_clientbacktime;

	public Integer getEbcl_id() {
		return ebcl_id;
	}

	public void setEbcl_id(Integer ebcl_id) {
		this.ebcl_id = ebcl_id;
	}

	public Integer getEbcl_embc_id() {
		return ebcl_embc_id;
	}

	public void setEbcl_embc_id(Integer ebcl_embc_id) {
		this.ebcl_embc_id = ebcl_embc_id;
	}

	public Integer getEbcl_hospital() {
		return ebcl_hospital;
	}

	public void setEbcl_hospital(Integer ebcl_hospital) {
		this.ebcl_hospital = ebcl_hospital;
	}

	public Integer getEbcl_area() {
		return ebcl_area;
	}

	public void setEbcl_area(Integer ebcl_area) {
		this.ebcl_area = ebcl_area;
	}

	public String getEbcl_itemgroup() {
		return ebcl_itemgroup;
	}

	public void setEbcl_itemgroup(String ebcl_itemgroup) {
		this.ebcl_itemgroup = ebcl_itemgroup;
	}

	public String getEbcl_items() {
		return ebcl_items;
	}

	public void setEbcl_items(String ebcl_items) {
		this.ebcl_items = ebcl_items;
	}

	public String getEbcl_itemnums() {
		return ebcl_itemnums;
	}

	public void setEbcl_itemnums(String ebcl_itemnums) {
		this.ebcl_itemnums = ebcl_itemnums;
	}

	public BigDecimal getEbcl_charge() {
		return ebcl_charge;
	}

	public void setEbcl_charge(BigDecimal ebcl_charge) {
		this.ebcl_charge = ebcl_charge;
	}

	public BigDecimal getEbcl_discount() {
		return ebcl_discount;
	}

	public void setEbcl_discount(BigDecimal ebcl_discount) {
		this.ebcl_discount = ebcl_discount;
	}

	public String getEbcl_bookdate() {
		return ebcl_bookdate;
	}

	public void setEbcl_bookdate(String ebcl_bookdate) {
		this.ebcl_bookdate = ebcl_bookdate;
	}

	public String getEbcl_linkdate() {
		return ebcl_linkdate;
	}

	public void setEbcl_linkdate(String ebcl_linkdate) {
		this.ebcl_linkdate = ebcl_linkdate;
	}

	public String getEbcl_drawformdate() {
		return ebcl_drawformdate;
	}

	public void setEbcl_drawformdate(String ebcl_drawformdate) {
		this.ebcl_drawformdate = ebcl_drawformdate;
	}

	public String getEbcl_showformdate() {
		return ebcl_showformdate;
	}

	public void setEbcl_showformdate(String ebcl_showformdate) {
		this.ebcl_showformdate = ebcl_showformdate;
	}

	public String getEbcl_showformpeople() {
		return ebcl_showformpeople;
	}

	public void setEbcl_showformpeople(String ebcl_showformpeople) {
		this.ebcl_showformpeople = ebcl_showformpeople;
	}

	public String getEbcl_plandate() {
		return ebcl_plandate;
	}

	public void setEbcl_plandate(String ebcl_plandate) {
		this.ebcl_plandate = ebcl_plandate;
	}

	public String getEbcl_wodate() {
		return ebcl_wodate;
	}

	public void setEbcl_wodate(String ebcl_wodate) {
		this.ebcl_wodate = ebcl_wodate;
	}

	public Integer getEbcl_state() {
		return ebcl_state;
	}

	public void setEbcl_state(Integer ebcl_state) {
		this.ebcl_state = ebcl_state;
	}

	public Integer getEbcl_type() {
		return ebcl_type;
	}

	public void setEbcl_type(Integer ebcl_type) {
		this.ebcl_type = ebcl_type;
	}

	public Integer getEbcl_bx() {
		return ebcl_bx;
	}

	public void setEbcl_bx(Integer ebcl_bx) {
		this.ebcl_bx = ebcl_bx;
	}

	public Integer getEbcl_change() {
		return ebcl_change;
	}

	public void setEbcl_change(Integer ebcl_change) {
		this.ebcl_change = ebcl_change;
	}

	public Integer getEbcl_commits() {
		return ebcl_commits;
	}

	public void setEbcl_commits(Integer ebcl_commits) {
		this.ebcl_commits = ebcl_commits;
	}

	public Integer getEbcl_special() {
		return ebcl_special;
	}

	public void setEbcl_special(Integer ebcl_special) {
		this.ebcl_special = ebcl_special;
	}

	public String getEbcl_remark() {
		return ebcl_remark;
	}

	public void setEbcl_remark(String ebcl_remark) {
		this.ebcl_remark = ebcl_remark;
	}

	public String getEbcl_remark2() {
		return ebcl_remark2;
	}

	public void setEbcl_remark2(String ebcl_remark2) {
		this.ebcl_remark2 = ebcl_remark2;
	}

	public String getEbcl_exportnum() {
		return ebcl_exportnum;
	}

	public void setEbcl_exportnum(String ebcl_exportnum) {
		this.ebcl_exportnum = ebcl_exportnum;
	}

	public String getEbcl_filename() {
		return ebcl_filename;
	}

	public void setEbcl_filename(String ebcl_filename) {
		this.ebcl_filename = ebcl_filename;
	}

	public String getEbcl_addtime() {
		return ebcl_addtime;
	}

	public void setEbcl_addtime(String ebcl_addtime) {
		this.ebcl_addtime = ebcl_addtime;
	}

	public String getEbcl_addname() {
		return ebcl_addname;
	}

	public void setEbcl_addname(String ebcl_addname) {
		this.ebcl_addname = ebcl_addname;
	}

	public String getEbcl_modtime() {
		return ebcl_modtime;
	}

	public void setEbcl_modtime(String ebcl_modtime) {
		this.ebcl_modtime = ebcl_modtime;
	}

	public String getEbcl_modname() {
		return ebcl_modname;
	}

	public void setEbcl_modname(String ebcl_modname) {
		this.ebcl_modname = ebcl_modname;
	}

	public Integer getEbcl_bookmode() {
		return ebcl_bookmode;
	}

	public void setEbcl_bookmode(Integer ebcl_bookmode) {
		this.ebcl_bookmode = ebcl_bookmode;
	}

	public String getEbcl_completeDate() {
		return ebcl_completeDate;
	}

	public void setEbcl_completeDate(String ebcl_completeDate) {
		this.ebcl_completeDate = ebcl_completeDate;
	}

	public String getEbcl_clientshowdate() {
		return ebcl_clientshowdate;
	}

	public void setEbcl_clientshowdate(String ebcl_clientshowdate) {
		this.ebcl_clientshowdate = ebcl_clientshowdate;
	}

	public String getEbcl_showclient() {
		return ebcl_showclient;
	}

	public void setEbcl_showclient(String ebcl_showclient) {
		this.ebcl_showclient = ebcl_showclient;
	}

	public String getEbcl_showreportdate() {
		return ebcl_showreportdate;
	}

	public void setEbcl_showreportdate(String ebcl_showreportdate) {
		this.ebcl_showreportdate = ebcl_showreportdate;
	}

	public String getEbcl_showreportpeople() {
		return ebcl_showreportpeople;
	}

	public void setEbcl_showreportpeople(String ebcl_showreportpeople) {
		this.ebcl_showreportpeople = ebcl_showreportpeople;
	}

	public BigDecimal getEbcl_finalcharge() {
		return ebcl_finalcharge;
	}

	public void setEbcl_finalcharge(BigDecimal ebcl_finalcharge) {
		this.ebcl_finalcharge = ebcl_finalcharge;
	}

	public String getEbcl_balancedate() {
		return ebcl_balancedate;
	}

	public void setEbcl_balancedate(String ebcl_balancedate) {
		this.ebcl_balancedate = ebcl_balancedate;
	}

	public String getEbcl_drawreportdate() {
		return ebcl_drawreportdate;
	}

	public void setEbcl_drawreportdate(String ebcl_drawreportdate) {
		this.ebcl_drawreportdate = ebcl_drawreportdate;
	}

	public String getEbcl_clientbackcase() {
		return ebcl_clientbackcase;
	}

	public void setEbcl_clientbackcase(String ebcl_clientbackcase) {
		this.ebcl_clientbackcase = ebcl_clientbackcase;
	}

	public String getEbcl_clientbackname() {
		return ebcl_clientbackname;
	}

	public void setEbcl_clientbackname(String ebcl_clientbackname) {
		this.ebcl_clientbackname = ebcl_clientbackname;
	}

	public String getEbcl_clientbacktime() {
		return ebcl_clientbacktime;
	}

	public void setEbcl_clientbacktime(String ebcl_clientbacktime) {
		this.ebcl_clientbacktime = ebcl_clientbacktime;
	}
	
}
