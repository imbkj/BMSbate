package Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CoFinanceCollectImportModel {
	private final DecimalFormat df = new DecimalFormat("#.00");
	private int cfci_id;
	private int cfci_cfco_id;
	private int cid;
	private int cfci_coab_id;
	private int cfci_type;
	private String cfci_transactionNo;
	private String cfci_company;
	private String cfci_account;
	private BigDecimal cfci_amount;
	private String cfci_usage;
	private String cfci_remark;
	private String cfci_transactionTime;
	private String cfci_addname;
	private String cfci_addtime;
	private int cfci_state;
	private String cfci_stateStr;

	// 页面参数
	private boolean opVisible = true;

	public CoFinanceCollectImportModel() {
		super();
	}

	public int getCfci_id() {
		return cfci_id;
	}

	public void setCfci_id(int cfci_id) {
		this.cfci_id = cfci_id;
	}

	public int getCfci_cfco_id() {
		return cfci_cfco_id;
	}

	public void setCfci_cfco_id(int cfci_cfco_id) {
		this.cfci_cfco_id = cfci_cfco_id;
	}

	public String getCfci_transactionNo() {
		return cfci_transactionNo;
	}

	public void setCfci_transactionNo(String cfci_transactionNo) {
		this.cfci_transactionNo = cfci_transactionNo;
	}

	public String getCfci_company() {
		return cfci_company;
	}

	public void setCfci_company(String cfci_company) {
		this.cfci_company = cfci_company;
	}

	public String getCfci_account() {
		return cfci_account;
	}

	public void setCfci_account(String cfci_account) {
		this.cfci_account = cfci_account;
	}

	public BigDecimal getCfci_amount() {
		return cfci_amount;
	}

	public void setCfci_amount(BigDecimal cfci_amount) {
		this.cfci_amount = new BigDecimal(df.format(cfci_amount));
	}

	public String getCfci_usage() {
		return cfci_usage;
	}

	public void setCfci_usage(String cfci_usage) {
		this.cfci_usage = cfci_usage;
	}

	public String getCfci_remark() {
		return cfci_remark;
	}

	public void setCfci_remark(String cfci_remark) {
		this.cfci_remark = cfci_remark;
	}

	public String getCfci_transactionTime() {
		return cfci_transactionTime;
	}

	public void setCfci_transactionTime(String cfci_transactionTime) {
		this.cfci_transactionTime = cfci_transactionTime;
	}

	public String getCfci_addname() {
		return cfci_addname;
	}

	public void setCfci_addname(String cfci_addname) {
		this.cfci_addname = cfci_addname;
	}

	public String getCfci_addtime() {
		return cfci_addtime;
	}

	public void setCfci_addtime(String cfci_addtime) {
		this.cfci_addtime = cfci_addtime;
	}

	public int getCfci_state() {
		return cfci_state;
	}

	public void setCfci_state(int cfci_state) {
		this.cfci_state = cfci_state;
		switch (cfci_state) {
		case 0:
			this.cfci_stateStr = "未入账";
			break;
		case 1:
			this.cfci_stateStr = "已入账";
			break;
		case 2:
			this.cfci_stateStr = "已删除";
			break;
		}
	}

	public String getCfci_stateStr() {
		return cfci_stateStr;
	}

	public void setCfci_stateStr(String cfci_stateStr) {
		this.cfci_stateStr = cfci_stateStr;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public int getCfci_coab_id() {
		return cfci_coab_id;
	}

	public void setCfci_coab_id(int cfci_coab_id) {
		this.cfci_coab_id = cfci_coab_id;
	}

	public int getCfci_type() {
		return cfci_type;
	}

	public void setCfci_type(int cfci_type) {
		this.cfci_type = cfci_type;
	}

	public boolean isOpVisible() {
		return opVisible;
	}

	public void setOpVisible(boolean opVisible) {
		this.opVisible = opVisible;
	}

}
