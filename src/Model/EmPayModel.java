package Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmPayModel implements Cloneable {
	private DecimalFormat myformat = new DecimalFormat("0.00");
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
	// / id
	private Integer id;
	// / gid
	private Integer gid;
	// / cid
	private Integer cid;
	// / ownmonth
	private Integer ownmonth;
	private Integer ownmonthend;
	private Date ownmonth2;
	private Date ownmonthend2;
	// / empa_name
	private String empa_name;
	private String name;
	// / empa_fee
	private BigDecimal empa_fee;// 税前
	private BigDecimal empa_aftertax;// 税后实付
	private BigDecimal empa_tax;// 个人所得税

	private BigDecimal empa_feetotal;// 税前总金额
	private BigDecimal empa_aftertaxtotal;// 税后实付总金额
	private BigDecimal empa_taxtotal;// 个人所得税总金额
	// / empa_class
	private String empa_class;
	// / empa_account
	private String empa_account;
	// / empa_bank
	private String empa_bank;
	// / 0
	private Integer empa_ifpay;
	// / empa_remark
	private String empa_remark;
	// / 支付方式
	private String empa_paytype;
	// / 支付形式
	private String empa_payclass;
	// / 银行账户名
	private String empa_ba_name;
	// / 是否需要经过总经理审批
	private Integer empa_ifpassmanager;
	// / 部门经理审批人
	private String empa_depmanager_checkname;
	// / 部门经理审批时间
	private String empa_depmanager_checkdate;
	// / 财务审批人
	private String empa_finance_checkname;
	// / 财务审批时间
	private String empa_finance_checkdate;
	// / 总经理审批人
	private String empa_manager_checkname;
	// / 总经理审批时间
	private String empa_manager_checkdate;
	// / 出纳审批人
	private String empa_cashier_checkname;
	// / 出纳审批时间
	private String empa_cashier_checkdate;
	// / empa_addtime
	private String empa_addtime;
	// / empa_addname
	private String empa_addname;
	// / 状态
	private Integer empa_state;

	private String coba_company;
	private String coba_shortname;
	private String coba_client;
	private String emba_name;
	private String emba_idcard;
	private int emba_state;
	private String state_name;
	private String ApprovalType;
	private String state;
	private String empa_paytime;
	private String remark;
	private String empa_number;
	private Date addtime;
	private int empa_ifclientManager;
	private String empa_paymenttype;
	private Integer dep_id;
	private Integer dep_name;

	private String coba_ufclass;
	private String coba_ufid;

	private String empa_invoiceauditname;
	private String empa_invoiceaudittime;
	private Integer num;

	private boolean change = false;
	private boolean assCheck = false;
	private Integer checkState;
	private boolean checked;
	private Integer patkId;
	private Integer taprId;

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			return null;
		}

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getEmpa_name() {
		return empa_name;
	}

	public void setEmpa_name(String empa_name) {
		this.empa_name = empa_name;
	}

	public BigDecimal getEmpa_fee() {
		return empa_fee;
	}

	public void setEmpa_fee(BigDecimal empa_fee) {
		try {
			if (empa_fee == null) {
				empa_fee = BigDecimal.ZERO;
			}
			empa_fee = new BigDecimal(myformat.format(empa_fee).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.empa_fee = empa_fee;
	}

	public String getEmpa_class() {
		return empa_class;
	}

	public void setEmpa_class(String empa_class) {
		this.empa_class = empa_class;
	}

	public String getEmpa_account() {
		return empa_account;
	}

	public void setEmpa_account(String empa_account) {
		this.empa_account = empa_account;
	}

	public String getEmpa_bank() {
		return empa_bank;
	}

	public void setEmpa_bank(String empa_bank) {
		this.empa_bank = empa_bank;
	}

	public Integer getEmpa_ifpay() {
		return empa_ifpay;
	}

	public void setEmpa_ifpay(Integer empa_ifpay) {
		this.empa_ifpay = empa_ifpay;
	}

	public String getEmpa_remark() {
		return empa_remark;
	}

	public void setEmpa_remark(String empa_remark) {
		this.empa_remark = empa_remark;
	}

	public String getEmpa_paytype() {
		return empa_paytype;
	}

	public void setEmpa_paytype(String empa_paytype) {
		this.empa_paytype = empa_paytype;
	}

	public String getEmpa_payclass() {
		return empa_payclass;
	}

	public void setEmpa_payclass(String empa_payclass) {
		this.empa_payclass = empa_payclass;
	}

	public String getEmpa_ba_name() {
		return empa_ba_name;
	}

	public void setEmpa_ba_name(String empa_ba_name) {
		this.empa_ba_name = empa_ba_name;
	}

	public Integer getEmpa_ifpassmanager() {
		return empa_ifpassmanager;
	}

	public void setEmpa_ifpassmanager(Integer empa_ifpassmanager) {
		this.empa_ifpassmanager = empa_ifpassmanager;
	}

	public String getEmpa_depmanager_checkname() {
		return empa_depmanager_checkname;
	}

	public void setEmpa_depmanager_checkname(String empa_depmanager_checkname) {
		this.empa_depmanager_checkname = empa_depmanager_checkname;
	}

	public String getEmpa_depmanager_checkdate() {
		return empa_depmanager_checkdate;
	}

	public void setEmpa_depmanager_checkdate(String empa_depmanager_checkdate) {
		this.empa_depmanager_checkdate = empa_depmanager_checkdate;
	}

	public String getEmpa_finance_checkname() {
		return empa_finance_checkname;
	}

	public void setEmpa_finance_checkname(String empa_finance_checkname) {
		this.empa_finance_checkname = empa_finance_checkname;
	}

	public String getEmpa_finance_checkdate() {
		return empa_finance_checkdate;
	}

	public void setEmpa_finance_checkdate(String empa_finance_checkdate) {
		this.empa_finance_checkdate = empa_finance_checkdate;
	}

	public String getEmpa_manager_checkname() {
		return empa_manager_checkname;
	}

	public void setEmpa_manager_checkname(String empa_manager_checkname) {
		this.empa_manager_checkname = empa_manager_checkname;
	}

	public String getEmpa_manager_checkdate() {
		return empa_manager_checkdate;
	}

	public void setEmpa_manager_checkdate(String empa_manager_checkdate) {
		this.empa_manager_checkdate = empa_manager_checkdate;
	}

	public String getEmpa_cashier_checkname() {
		return empa_cashier_checkname;
	}

	public void setEmpa_cashier_checkname(String empa_cashier_checkname) {
		this.empa_cashier_checkname = empa_cashier_checkname;
	}

	public String getEmpa_cashier_checkdate() {
		return empa_cashier_checkdate;
	}

	public void setEmpa_cashier_checkdate(String empa_cashier_checkdate) {
		this.empa_cashier_checkdate = empa_cashier_checkdate;
	}

	public String getEmpa_addtime() {
		return empa_addtime;
	}

	public void setEmpa_addtime(String empa_addtime) {
		this.empa_addtime = empa_addtime;
	}

	public String getEmpa_addname() {
		return empa_addname;
	}

	public void setEmpa_addname(String empa_addname) {
		this.empa_addname = empa_addname;
	}

	public Integer getEmpa_state() {
		return empa_state;
	}

	public void setEmpa_state(Integer empa_state) {
		this.empa_state = empa_state;
	}

	public String getCoba_company() {
		return coba_company;
	}

	public void setCoba_company(String coba_company) {
		this.coba_company = coba_company;
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

	public int getEmba_state() {
		return emba_state;
	}

	public void setEmba_state(int emba_state) {
		this.emba_state = emba_state;
	}

	public String getState_name() {
		return state_name;
	}

	public void setState_name(String state_name) {
		this.state_name = state_name;
	}

	public String getApprovalType() {
		return ApprovalType;
	}

	public void setApprovalType(String approvalType) {
		ApprovalType = approvalType;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getEmpa_paytime() {
		return empa_paytime;
	}

	public void setEmpa_paytime(String empa_paytime) {
		this.empa_paytime = empa_paytime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getEmpa_aftertax() {
		return empa_aftertax;
	}

	public void setEmpa_aftertax(BigDecimal empa_aftertax) {
		try {
			if (empa_aftertax == null) {
				empa_aftertax = BigDecimal.ZERO;
			}
			empa_aftertax = new BigDecimal(myformat.format(empa_aftertax)
					.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.empa_aftertax = empa_aftertax;
	}

	public BigDecimal getEmpa_tax() {
		return empa_tax;
	}

	public void setEmpa_tax(BigDecimal empa_tax) {
		try {
			if (empa_tax == null) {
				empa_tax = BigDecimal.ZERO;
			}
			empa_tax = new BigDecimal(myformat.format(empa_tax).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.empa_tax = empa_tax;
	}

	public String getEmpa_number() {
		return empa_number;
	}

	public void setEmpa_number(String empa_number) {
		this.empa_number = empa_number;
	}

	public BigDecimal getEmpa_feetotal() {
		return empa_feetotal;
	}

	public void setEmpa_feetotal(BigDecimal empa_feetotal) {
		try {
			if (empa_feetotal == null) {
				empa_feetotal = BigDecimal.ZERO;
			}
			empa_feetotal = new BigDecimal(myformat.format(empa_feetotal)
					.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.empa_feetotal = empa_feetotal;
	}

	public BigDecimal getEmpa_aftertaxtotal() {
		return empa_aftertaxtotal;
	}

	public void setEmpa_aftertaxtotal(BigDecimal empa_aftertaxtotal) {
		try {
			if (empa_aftertaxtotal == null) {
				empa_aftertaxtotal = BigDecimal.ZERO;
			}
			empa_aftertaxtotal = new BigDecimal(myformat.format(
					empa_aftertaxtotal).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.empa_aftertaxtotal = empa_aftertaxtotal;
	}

	public BigDecimal getEmpa_taxtotal() {
		return empa_taxtotal;
	}

	public void setEmpa_taxtotal(BigDecimal empa_taxtotal) {
		try {
			if (empa_taxtotal == null) {
				empa_taxtotal = BigDecimal.ZERO;
			}
			empa_taxtotal = new BigDecimal(myformat.format(empa_taxtotal)
					.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.empa_taxtotal = empa_taxtotal;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public int getEmpa_ifclientManager() {
		return empa_ifclientManager;
	}

	public void setEmpa_ifclientManager(int empa_ifclientManager) {
		this.empa_ifclientManager = empa_ifclientManager;
	}

	public String getEmpa_paymenttype() {
		return empa_paymenttype;
	}

	public void setEmpa_paymenttype(String empa_paymenttype) {
		this.empa_paymenttype = empa_paymenttype;
	}

	public Integer getOwnmonthend() {
		return ownmonthend;
	}

	public void setOwnmonthend(Integer ownmonthend) {
		this.ownmonthend = ownmonthend;
	}

	public String getCoba_ufclass() {
		return coba_ufclass;
	}

	public void setCoba_ufclass(String coba_ufclass) {
		this.coba_ufclass = coba_ufclass;
	}

	public String getCoba_ufid() {
		return coba_ufid;
	}

	public void setCoba_ufid(String coba_ufid) {
		this.coba_ufid = coba_ufid;
	}

	public Integer getDep_id() {
		return dep_id;
	}

	public void setDep_id(Integer dep_id) {
		this.dep_id = dep_id;
	}

	public String getEmpa_invoiceauditname() {
		return empa_invoiceauditname;
	}

	public void setEmpa_invoiceauditname(String empa_invoiceauditname) {
		this.empa_invoiceauditname = empa_invoiceauditname;
	}

	public String getEmpa_invoiceaudittime() {
		return empa_invoiceaudittime;
	}

	public void setEmpa_invoiceaudittime(String empa_invoiceaudittime) {
		this.empa_invoiceaudittime = empa_invoiceaudittime;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getOwnmonth2() {
		if (ownmonth2 == null && ownmonth != null) {
			try {
				ownmonth2 = sdf.parse(ownmonth.toString());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ownmonth2;
	}

	public void setOwnmonth2(Date ownmonth2) {

		this.ownmonth2 = ownmonth2;
	}

	public Date getOwnmonthend2() {
		if (ownmonthend2 == null && ownmonthend != null) {
			try {
				ownmonthend2 = sdf.parse(ownmonthend.toString());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ownmonthend2;
	}

	public void setOwnmonthend2(Date ownmonthend2) {

		this.ownmonthend2 = ownmonthend2;
	}

	public boolean isChange() {
		return change;
	}

	public void setChange(boolean change) {
		this.change = change;
	}

	public boolean isAssCheck() {
		return assCheck;
	}

	public void setAssCheck(boolean assCheck) {
		this.assCheck = assCheck;
	}

	public Integer getCheckState() {
		return checkState;
	}

	public void setCheckState(Integer checkState) {
		this.checkState = checkState;
	}

	public Integer getPatkId() {
		return patkId;
	}

	public void setPatkId(Integer patkId) {
		this.patkId = patkId;
	}

	public Integer getTaprId() {
		return taprId;
	}

	public void setTaprId(Integer taprId) {
		this.taprId = taprId;
	}

	public Integer getDep_name() {
		return dep_name;
	}

	public void setDep_name(Integer dep_name) {
		this.dep_name = dep_name;
	}

}
