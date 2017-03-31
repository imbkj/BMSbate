package Model;

import java.math.BigDecimal;

public class EmFinanceValueAddTaxModel {
	private Integer efvt_id;
	private Integer ownmonth;
	private Integer efvt_efba_id;
	private Integer cid;
	private Integer gid;
	private Integer efvt_coco_id;
	private String efvt_cfmb_number;
	private BigDecimal efvt_Receivable;
	private Integer efvt_PaidIn;
	private Integer efvt_PayOut;
	private Integer efvt_IfFirstPaidIn;
	private String efvt_addtime;
	private Integer efvt_FinalCheck;
	private String efvt_FinalCheckTime;
	private Integer efvt_state;

	private String emba_name;
	private Integer types;
	private boolean write;

	public Integer getEfvt_id() {
		return efvt_id;
	}

	public void setEfvt_id(Integer efvt_id) {
		this.efvt_id = efvt_id;
	}

	public Integer getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(Integer ownmonth) {
		this.ownmonth = ownmonth;
	}

	public Integer getEfvt_efba_id() {
		return efvt_efba_id;
	}

	public void setEfvt_efba_id(Integer efvt_efba_id) {
		this.efvt_efba_id = efvt_efba_id;
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

	public Integer getEfct_coco_id() {
		return efvt_coco_id;
	}

	public void setEfct_coco_id(Integer efvt_coco_id) {
		this.efvt_coco_id = efvt_coco_id;
	}

	public String getEfvt_cfmb_number() {
		return efvt_cfmb_number;
	}

	public void setEfvt_cfmb_number(String efvt_cfmb_number) {
		this.efvt_cfmb_number = efvt_cfmb_number;
	}

	public BigDecimal getEfvt_Receivable() {
		return efvt_Receivable;
	}

	public void setEfvt_Receivable(BigDecimal efvt_Receivable) {
		this.efvt_Receivable = efvt_Receivable;
	}

	public Integer getEfvt_PaidIn() {
		return efvt_PaidIn;
	}

	public void setEfvt_PaidIn(Integer efvt_PaidIn) {
		this.efvt_PaidIn = efvt_PaidIn;
	}

	public Integer getEfvt_PayOut() {
		return efvt_PayOut;
	}

	public void setEfvt_PayOut(Integer efvt_PayOut) {
		this.efvt_PayOut = efvt_PayOut;
	}

	public Integer getEfvt_IfFirstPaidIn() {
		return efvt_IfFirstPaidIn;
	}

	public void setEfvt_IfFirstPaidIn(Integer efvt_IfFirstPaidIn) {
		this.efvt_IfFirstPaidIn = efvt_IfFirstPaidIn;
	}

	public String getEfvt_addtime() {
		return efvt_addtime;
	}

	public void setEfvt_addtime(String efvt_addtime) {
		this.efvt_addtime = efvt_addtime;
	}

	public Integer getEfvt_FinalCheck() {
		return efvt_FinalCheck;
	}

	public void setEfvt_FinalCheck(Integer efvt_FinalCheck) {
		this.efvt_FinalCheck = efvt_FinalCheck;
	}

	public String getEfvt_FinalCheckTime() {
		return efvt_FinalCheckTime;
	}

	public void setEfvt_FinalCheckTime(String efvt_FinalCheckTime) {
		this.efvt_FinalCheckTime = efvt_FinalCheckTime;
	}

	public Integer getEfvt_state() {
		return efvt_state;
	}

	public void setEfvt_state(Integer efvt_state) {
		this.efvt_state = efvt_state;
	}

	public String getEmba_name() {
		return emba_name;
	}

	public void setEmba_name(String emba_name) {
		this.emba_name = emba_name;
	}

	public Integer getTypes() {
		return types;
	}

	public void setTypes(Integer types) {
		this.types = types;
	}

	public boolean isWrite() {
		return write;
	}

	public void setWrite(boolean write) {
		this.write = write;
	}

}
