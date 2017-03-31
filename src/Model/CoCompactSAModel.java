/*
 * 创建人：林少斌
 * 创建时间：2013-10-16
 * 用途：公司合同补充协议视图CoCompactSA_CoCo_CoBase_V
 */
package Model;

public class CoCompactSAModel {
	private int ccsa_id;
	private int ccsa_coco_id;
	private int cid;
	private String ccsa_type;
	private String ccsa_said;
	private String ccsa_signdate;
	private String ccsa_stopdate;
	private String ccsa_stopreason;
	private String ccsa_stoptype;
	private String ccsa_inuredate;
	private String ccsa_indate;
	private String ccsa_delay;
	private String ccsa_auditingdate;
	private String ccsa_printdate;
	private String ccsa_returndate;
	private String ccsa_signplace;
	private String ccsa_filedate;
	private String ccsa_fileid;
	private String ccsa_chs_copies;
	private String ccsa_en_copies;
	private int ccsa_state;
	private String state;		//中文
	private String ccsa_remark;
	private String ccsa_addname;
	private String ccsa_addtime;
	private String coba_company;
	private String coba_shortname;
	private String coco_compactid;
	private int ccsa_tapr_id;
	
	
	
	public CoCompactSAModel() {
		super();
	}

	

	public CoCompactSAModel(int ccsa_id, int ccsa_coco_id, int cid,
			String ccsa_type, String ccsa_said, String ccsa_signdate,
			String ccsa_stopdate, String ccsa_stopreason, String ccsa_stoptype,
			String ccsa_inuredate, String ccsa_indate, String ccsa_delay,
			String ccsa_auditingdate, String ccsa_printdate,
			String ccsa_returndate, String ccsa_signplace,
			String ccsa_filedate, String ccsa_fileid, String ccsa_chs_copies,
			String ccsa_en_copies, int ccsa_state, String state,
			String ccsa_remark, String ccsa_addname, String ccsa_addtime,
			String coba_company, String coba_shortname, String coco_compactid,
			int ccsa_tapr_id) {
		super();
		this.ccsa_id = ccsa_id;
		this.ccsa_coco_id = ccsa_coco_id;
		this.cid = cid;
		this.ccsa_type = ccsa_type;
		this.ccsa_said = ccsa_said;
		this.ccsa_signdate = ccsa_signdate;
		this.ccsa_stopdate = ccsa_stopdate;
		this.ccsa_stopreason = ccsa_stopreason;
		this.ccsa_stoptype = ccsa_stoptype;
		this.ccsa_inuredate = ccsa_inuredate;
		this.ccsa_indate = ccsa_indate;
		this.ccsa_delay = ccsa_delay;
		this.ccsa_auditingdate = ccsa_auditingdate;
		this.ccsa_printdate = ccsa_printdate;
		this.ccsa_returndate = ccsa_returndate;
		this.ccsa_signplace = ccsa_signplace;
		this.ccsa_filedate = ccsa_filedate;
		this.ccsa_fileid = ccsa_fileid;
		this.ccsa_chs_copies = ccsa_chs_copies;
		this.ccsa_en_copies = ccsa_en_copies;
		this.ccsa_state = ccsa_state;
		this.state = state;
		this.ccsa_remark = ccsa_remark;
		this.ccsa_addname = ccsa_addname;
		this.ccsa_addtime = ccsa_addtime;
		this.coba_company = coba_company;
		this.coba_shortname = coba_shortname;
		this.coco_compactid = coco_compactid;
		this.ccsa_tapr_id = ccsa_tapr_id;
	}



	public CoCompactSAModel(String ccsa_addname) {
		super();
		this.ccsa_addname = ccsa_addname;
	}
	
	


	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
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



	public String getCoco_compactid() {
		return coco_compactid;
	}



	public void setCoco_compactid(String coco_compactid) {
		this.coco_compactid = coco_compactid;
	}



	public int getCcsa_id() {
		return ccsa_id;
	}
	public void setCcsa_id(int ccsa_id) {
		this.ccsa_id = ccsa_id;
	}
	public int getCcsa_coco_id() {
		return ccsa_coco_id;
	}
	public void setCcsa_coco_id(int ccsa_coco_id) {
		this.ccsa_coco_id = ccsa_coco_id;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getCcsa_type() {
		return ccsa_type;
	}
	public void setCcsa_type(String ccsa_type) {
		this.ccsa_type = ccsa_type;
	}
	public String getCcsa_said() {
		return ccsa_said;
	}
	public void setCcsa_said(String ccsa_said) {
		this.ccsa_said = ccsa_said;
	}
	public String getCcsa_signdate() {
		return ccsa_signdate;
	}
	public void setCcsa_signdate(String ccsa_signdate) {
		this.ccsa_signdate = ccsa_signdate;
	}
	public String getCcsa_stopdate() {
		return ccsa_stopdate;
	}
	public void setCcsa_stopdate(String ccsa_stopdate) {
		this.ccsa_stopdate = ccsa_stopdate;
	}
	public String getCcsa_stopreason() {
		return ccsa_stopreason;
	}
	public void setCcsa_stopreason(String ccsa_stopreason) {
		this.ccsa_stopreason = ccsa_stopreason;
	}
	public String getCcsa_stoptype() {
		return ccsa_stoptype;
	}
	public void setCcsa_stoptype(String ccsa_stoptype) {
		this.ccsa_stoptype = ccsa_stoptype;
	}
	public String getCcsa_inuredate() {
		return ccsa_inuredate;
	}
	public void setCcsa_inuredate(String ccsa_inuredate) {
		this.ccsa_inuredate = ccsa_inuredate;
	}
	public String getCcsa_indate() {
		return ccsa_indate;
	}
	public void setCcsa_indate(String ccsa_indate) {
		this.ccsa_indate = ccsa_indate;
	}
	public String getCcsa_delay() {
		return ccsa_delay;
	}
	public void setCcsa_delay(String ccsa_delay) {
		this.ccsa_delay = ccsa_delay;
	}
	public String getCcsa_auditingdate() {
		return ccsa_auditingdate;
	}
	public void setCcsa_auditingdate(String ccsa_auditingdate) {
		this.ccsa_auditingdate = ccsa_auditingdate;
	}
	public String getCcsa_printdate() {
		return ccsa_printdate;
	}
	public void setCcsa_printdate(String ccsa_printdate) {
		this.ccsa_printdate = ccsa_printdate;
	}
	public String getCcsa_returndate() {
		return ccsa_returndate;
	}
	public void setCcsa_returndate(String ccsa_returndate) {
		this.ccsa_returndate = ccsa_returndate;
	}
	public String getCcsa_signplace() {
		return ccsa_signplace;
	}
	public void setCcsa_signplace(String ccsa_signplace) {
		this.ccsa_signplace = ccsa_signplace;
	}
	public String getCcsa_filedate() {
		return ccsa_filedate;
	}
	public void setCcsa_filedate(String ccsa_filedate) {
		this.ccsa_filedate = ccsa_filedate;
	}
	public String getCcsa_fileid() {
		return ccsa_fileid;
	}
	public void setCcsa_fileid(String ccsa_fileid) {
		this.ccsa_fileid = ccsa_fileid;
	}
	public String getCcsa_chs_copies() {
		return ccsa_chs_copies;
	}
	public void setCcsa_chs_copies(String ccsa_chs_copies) {
		this.ccsa_chs_copies = ccsa_chs_copies;
	}
	public String getCcsa_en_copies() {
		return ccsa_en_copies;
	}
	public void setCcsa_en_copies(String ccsa_en_copies) {
		this.ccsa_en_copies = ccsa_en_copies;
	}
	public int getCcsa_state() {
		return ccsa_state;
	}
	public void setCcsa_state(int ccsa_state) {
		this.ccsa_state = ccsa_state;
	}
	public String getCcsa_remark() {
		return ccsa_remark;
	}
	public void setCcsa_remark(String ccsa_remark) {
		this.ccsa_remark = ccsa_remark;
	}
	public String getCcsa_addname() {
		return ccsa_addname;
	}
	public void setCcsa_addname(String ccsa_addname) {
		this.ccsa_addname = ccsa_addname;
	}
	public String getCcsa_addtime() {
		return ccsa_addtime;
	}
	public void setCcsa_addtime(String ccsa_addtime) {
		this.ccsa_addtime = ccsa_addtime;
	}
	public int getCcsa_tapr_id() {
		return ccsa_tapr_id;
	}
	public void setCcsa_tapr_id(int ccsa_tapr_id) {
		this.ccsa_tapr_id = ccsa_tapr_id;
	}
	
	
}
