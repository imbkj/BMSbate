package Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 手动添加非标记录model
 * 
 * @author Administrator
 * 
 */
public class CoFinanceManualDisposableModel {

	private final DecimalFormat df = new DecimalFormat("#.00");
	private int cfmd_id;
	private int cid;
	private int gid;
	private int ownmonth; // 非标所属月份
	private String cfmd_cpac_name; // --财务科目名称
	private String cfmd_copr_name; // --福利产品名称
	private String cfmd_Reason; // --收费原因
	private BigDecimal cfmd_Receivable; // --应收费用
	private String cfmd_addname; // --录入人
	private String cfmd_addtime; // --录入时间
	private int cfmd_state; // --状态(1已入账（此状态不可更改）2有效3退回4审核中)
	private int cfmd_tapr_id; // --流程ID
	private int cfmd_coco_id; // 合同编号
	private String emba_name;
	private String coco_compactid;
	private String cfmd_backreason; // 退回原因
	private String coba_company;

	public String getCoba_company() {
		return coba_company;
	}

	public void setCoba_company(String coba_company) {
		this.coba_company = coba_company;
	}

	public String getCfmd_backreason() {
		return cfmd_backreason;
	}

	public void setCfmd_backreason(String cfmd_backreason) {
		this.cfmd_backreason = cfmd_backreason;
	}

	public String getCoco_compactid() {
		return coco_compactid;
	}

	public void setCoco_compactid(String coco_compactid) {
		this.coco_compactid = coco_compactid;
	}

	public String getEmba_name() {
		return emba_name;
	}

	public void setEmba_name(String emba_name) {
		this.emba_name = emba_name;
	}

	public int getCfmd_coco_id() {
		return cfmd_coco_id;
	}

	public void setCfmd_coco_id(int cfmd_coco_id) {
		this.cfmd_coco_id = cfmd_coco_id;
	}

	public int getCfmd_id() {
		return cfmd_id;
	}

	public void setCfmd_id(int cfmd_id) {
		this.cfmd_id = cfmd_id;
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

	public String getCfmd_cpac_name() {
		return cfmd_cpac_name;
	}

	public void setCfmd_cpac_name(String cfmd_cpac_name) {
		this.cfmd_cpac_name = cfmd_cpac_name;
	}

	public String getCfmd_copr_name() {
		return cfmd_copr_name;
	}

	public void setCfmd_copr_name(String cfmd_copr_name) {
		this.cfmd_copr_name = cfmd_copr_name;
	}

	public String getCfmd_Reason() {
		return cfmd_Reason;
	}

	public void setCfmd_Reason(String cfmd_Reason) {
		this.cfmd_Reason = cfmd_Reason;
	}

	public BigDecimal getCfmd_Receivable() {
		return cfmd_Receivable;
	}

	public void setCfmd_Receivable(BigDecimal cfmd_Receivable) {
		try {
			this.cfmd_Receivable = new BigDecimal(df.format(cfmd_Receivable));
		} catch (Exception e) {

		}
	}
	public String getCfmd_addname() {
		return cfmd_addname;
	}

	public void setCfmd_addname(String cfmd_addname) {
		this.cfmd_addname = cfmd_addname;
	}

	public String getCfmd_addtime() {
		return cfmd_addtime;
	}

	public void setCfmd_addtime(String cfmd_addtime) {
		this.cfmd_addtime = cfmd_addtime;
	}

	public int getCfmd_state() {
		return cfmd_state;
	}

	public void setCfmd_state(int cfmd_state) {
		this.cfmd_state = cfmd_state;
	}

	public int getCfmd_tapr_id() {
		return cfmd_tapr_id;
	}

	public void setCfmd_tapr_id(int cfmd_tapr_id) {
		this.cfmd_tapr_id = cfmd_tapr_id;
	}

}
