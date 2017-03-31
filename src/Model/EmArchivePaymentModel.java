package Model;

import java.math.BigDecimal;
import java.util.Date;

public class EmArchivePaymentModel {
	// / emap_id
	private Integer emap_id;
	// / ownmonth
	private Integer ownmonth;
	// / cid
	private Integer cid;
	// / gid
	private Integer gid;
	// / emap_comspell
	private String emap_comspell;
	// / emap_spell
	private String emap_spell;
	// / emap_emar_id
	private Integer emap_emar_id;
	// / emap_company
	private String emap_company;
	// / emap_name
	private String emap_name;
	// / emap_type
	private String emap_type;
	// / emap_area
	private String emap_area;
	// / emap_fid
	private String emap_fid;
	private String emap_fid2;

	private String emap_archivetype;
	// / emap_xs
	private String emap_xs;
	// / emap_idcard
	private String emap_idcard;
	// / emap_fileplace
	private String emap_fileplace;
	// / emap_sdate
	private String emap_sdate;
	// / emap_cdate
	private String emap_cdate;
	// / emap_file
	private BigDecimal emap_file;
	// / emap_hj
	private BigDecimal emap_hj;
	// / emap_op
	private BigDecimal emap_op;
	// / emap_fpay
	private String emap_fpay;

	private String emap_finvoice;
	// / emap_hpay
	private String emap_hpay;
	private String emap_hinvoice;
	// / emap_idlist
	private String emap_idlist;
	// / emap_lname
	private String emap_lname;
	// / emap_ltime
	private String emap_ltime;
	// / emap_lstate
	private Integer emap_lstate;
	// / emap_itime
	private String emap_itime;
	// / emap_client
	private String emap_client;
	// / emap_remark
	private String emap_remark;

	private Integer emap_colhj;
	// / emap_state
	private Integer emap_state;
	// / emap_addname
	private String emap_addname;
	// / emap_addtime
	private String emap_addtime;
	// / emap_modname
	private String emap_modname;
	// / emap_modtime
	private String emap_modtime;
	// / emap_idtime
	private String emap_idtime;
	// / emap_idname
	private String emap_idname;

	private String lstatename;

	private String cddate;

	private Integer eare_trid;
	private String intime;
	private String outtime;

	private Date cd1;
	private Date cd2;
	private Integer datetype;
	private boolean checked;
	private BigDecimal total;
	private boolean message;
	private boolean readState;
	private Integer symr_readstate;
	public Integer getEmap_id() {
		return emap_id;
	}

	public void setEmap_id(Integer emap_id) {
		this.emap_id = emap_id;
	}

	public Integer getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(Integer ownmonth) {
		this.ownmonth = ownmonth;
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

	public String getEmap_comspell() {
		return emap_comspell;
	}

	public void setEmap_comspell(String emap_comspell) {
		this.emap_comspell = emap_comspell;
	}

	public String getEmap_spell() {
		return emap_spell;
	}

	public void setEmap_spell(String emap_spell) {
		this.emap_spell = emap_spell;
	}

	public Integer getEmap_emar_id() {
		return emap_emar_id;
	}

	public void setEmap_emar_id(Integer emap_emar_id) {
		this.emap_emar_id = emap_emar_id;
	}

	public String getEmap_company() {
		return emap_company;
	}

	public void setEmap_company(String emap_company) {
		this.emap_company = emap_company;
	}

	public String getEmap_name() {
		return emap_name;
	}

	public void setEmap_name(String emap_name) {
		this.emap_name = emap_name;
	}

	public String getEmap_type() {
		return emap_type;
	}

	public void setEmap_type(String emap_type) {
		this.emap_type = emap_type;
	}

	public String getEmap_area() {
		return emap_area;
	}

	public void setEmap_area(String emap_area) {
		this.emap_area = emap_area;
	}

	public String getEmap_fid() {
		return emap_fid;
	}

	public void setEmap_fid(String emap_fid) {
		this.emap_fid = emap_fid;
	}

	public String getEmap_xs() {
		return emap_xs;
	}

	public void setEmap_xs(String emap_xs) {
		this.emap_xs = emap_xs;
	}

	public String getEmap_idcard() {
		return emap_idcard;
	}

	public void setEmap_idcard(String emap_idcard) {
		this.emap_idcard = emap_idcard;
	}

	public String getEmap_fileplace() {
		return emap_fileplace;
	}

	public void setEmap_fileplace(String emap_fileplace) {
		this.emap_fileplace = emap_fileplace;
	}

	public String getEmap_sdate() {
		return emap_sdate;
	}

	public void setEmap_sdate(String emap_sdate) {
		this.emap_sdate = emap_sdate;
	}

	public String getEmap_cdate() {
		return emap_cdate;
	}

	public void setEmap_cdate(String emap_cdate) {
		this.emap_cdate = emap_cdate;
	}

	public BigDecimal getEmap_file() {

		return emap_file.setScale(0, BigDecimal.ROUND_HALF_UP);
	}

	public void setEmap_file(BigDecimal emap_file) {
		this.emap_file = emap_file;
	}

	public BigDecimal getEmap_hj() {
		return emap_hj.setScale(0, BigDecimal.ROUND_HALF_UP);
	}

	public void setEmap_hj(BigDecimal emap_hj) {
		this.emap_hj = emap_hj;
	}

	public BigDecimal getEmap_op() {
		return emap_op.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setEmap_op(BigDecimal emap_op) {
		this.emap_op = emap_op;
	}

	public String getEmap_fpay() {
		return emap_fpay;
	}

	public void setEmap_fpay(String emap_fpay) {
		this.emap_fpay = emap_fpay;
	}

	public String getEmap_hpay() {
		return emap_hpay;
	}

	public void setEmap_hpay(String emap_hpay) {
		this.emap_hpay = emap_hpay;
	}

	public String getEmap_idlist() {
		return emap_idlist;
	}

	public void setEmap_idlist(String emap_idlist) {
		this.emap_idlist = emap_idlist;
	}

	public String getEmap_lname() {
		return emap_lname;
	}

	public void setEmap_lname(String emap_lname) {
		this.emap_lname = emap_lname;
	}

	public String getEmap_ltime() {
		return emap_ltime;
	}

	public void setEmap_ltime(String emap_ltime) {
		this.emap_ltime = emap_ltime;
	}

	public Integer getEmap_lstate() {
		return emap_lstate;
	}

	public void setEmap_lstate(Integer emap_lstate) {
		this.emap_lstate = emap_lstate;
	}

	public String getEmap_itime() {
		return emap_itime;
	}

	public void setEmap_itime(String emap_itime) {
		this.emap_itime = emap_itime;
	}

	public String getEmap_client() {
		return emap_client;
	}

	public void setEmap_client(String emap_client) {
		this.emap_client = emap_client;
	}

	public String getEmap_remark() {
		return emap_remark;
	}

	public void setEmap_remark(String emap_remark) {
		this.emap_remark = emap_remark;
	}

	public Integer getEmap_state() {
		return emap_state;
	}

	public void setEmap_state(Integer emap_state) {
		this.emap_state = emap_state;
	}

	public String getEmap_addname() {
		return emap_addname;
	}

	public void setEmap_addname(String emap_addname) {
		this.emap_addname = emap_addname;
	}

	public String getEmap_addtime() {
		return emap_addtime;
	}

	public void setEmap_addtime(String emap_addtime) {
		this.emap_addtime = emap_addtime;
	}

	public String getEmap_modname() {
		return emap_modname;
	}

	public void setEmap_modname(String emap_modname) {
		this.emap_modname = emap_modname;
	}

	public String getEmap_modtime() {
		return emap_modtime;
	}

	public void setEmap_modtime(String emap_modtime) {
		this.emap_modtime = emap_modtime;
	}

	public String getEmap_idtime() {
		return emap_idtime;
	}

	public void setEmap_idtime(String emap_idtime) {
		this.emap_idtime = emap_idtime;
	}

	public String getEmap_idname() {
		return emap_idname;
	}

	public void setEmap_idname(String emap_idname) {
		this.emap_idname = emap_idname;
	}

	public String getLstatename() {
		return lstatename;
	}

	public void setLstatename(String lstatename) {
		this.lstatename = lstatename;
	}

	public String getCddate() {
		return cddate;
	}

	public void setCddate(String cddate) {
		this.cddate = cddate;
	}

	public Integer getEare_trid() {
		return eare_trid;
	}

	public void setEare_trid(Integer eare_trid) {
		this.eare_trid = eare_trid;
	}

	public String getEmap_fid2() {
		return emap_fid2;
	}

	public void setEmap_fid2(String emap_fid2) {
		this.emap_fid2 = emap_fid2;
	}

	public Integer getEmap_colhj() {
		return emap_colhj;
	}

	public void setEmap_colhj(Integer emap_colhj) {
		this.emap_colhj = emap_colhj;
	}

	public Date getCd1() {
		return cd1;
	}

	public void setCd1(Date cd1) {
		this.cd1 = cd1;
	}

	public Date getCd2() {
		return cd2;
	}

	public void setCd2(Date cd2) {
		this.cd2 = cd2;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getEmap_archivetype() {
		return emap_archivetype;
	}

	public void setEmap_archivetype(String emap_archivetype) {
		this.emap_archivetype = emap_archivetype;
	}

	public String getEmap_finvoice() {
		return emap_finvoice;
	}

	public void setEmap_finvoice(String emap_finvoice) {
		this.emap_finvoice = emap_finvoice;
	}

	public String getEmap_hinvoice() {
		return emap_hinvoice;
	}

	public void setEmap_hinvoice(String emap_hinvoice) {
		this.emap_hinvoice = emap_hinvoice;
	}

	public String getIntime() {
		return intime;
	}

	public void setIntime(String intime) {
		this.intime = intime;
	}

	public Integer getDatetype() {
		return datetype;
	}

	public void setDatetype(Integer datetype) {
		this.datetype = datetype;
	}

	public String getOuttime() {
		return outtime;
	}

	public void setOuttime(String outtime) {
		this.outtime = outtime;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public boolean isMessage() {
		return message;
	}

	public void setMessage(boolean message) {
		this.message = message;
	}

	public boolean isReadState() {
		return readState;
	}

	public void setReadState(boolean readState) {
		this.readState = readState;
	}

	public Integer getSymr_readstate() {
		return symr_readstate;
	}

	public void setSymr_readstate(Integer symr_readstate) {
		this.symr_readstate = symr_readstate;
	}
	
}
