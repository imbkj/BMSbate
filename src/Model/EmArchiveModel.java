package Model;

import java.math.BigDecimal;
import java.util.Date;

public class EmArchiveModel {

	private Integer emar_id;
	private Integer cid;
	private Integer gid;
	private String emar_company;
	private String emar_name;
	private String emar_sex;
	private String emar_idcard;
	private String emar_link;
	private String emar_xs;
	private String emar_fid;
	private String emar_fid2;
	private String emar_archivetype;
	private String emar_archivearea;
	private String emar_surrogateid;
	private String emar_surrogatecardid;
	private String emar_transferorderid;
	private String emar_archivesource;
	private String emar_archiveplace;
	private String emar_promisesdate;
	private String emar_prodate;
	private String emar_firstdeadline;
	private String emar_continuedeadline;
	private String cddate;
	private String emar_client;
	private String emar_grouptype;
	private String emar_censusregister;
	private Integer emar_crbelongs;
	private String emar_specialty;
	private String emar_degree;
	private String emar_caste;
	private String emar_casteassessdate;
	private String emar_party;
	private String emar_partydate;
	private Integer emar_partybelongs;
	private String emar_marrystate;
	private String emar_fertilitystate;
	private String emar_school;
	private String emar_gradate;
	private String emar_workdate;
	private String emar_szresume;
	private Integer emar_colhj;
	private String emar_colhjname;
	private String emar_wtmode;
	private String emar_inciicdate;
	private String emar_archivefolddate;
	private String emar_archivefoldreason;
	private String emar_archivefoldmode;
	private String emar_archiveclass;
	private String emar_peoplefoldmode;
	private String emar_archiveremovedate;
	private String emar_archiveremovereason;
	private String emar_archiveremovermode;
	private String emar_archiveremoveplace;
	private String emar_rsetup;
	private String emar_rdate;
	private String emar_address;
	private String emar_rspaykind;
	private String emar_hjpaykind;
	private String emar_rsinvoice;
	private String emar_hjinvoice;
	private String emar_remark;
	private String emar_addtime;
	private String emar_addname;
	private String emar_modtime;
	private String emar_modname;
	private Integer emar_specialdata;
	private Integer emar_state;
	private String state;// 中文状态，emar_state转换
	private Integer num;
	private boolean checked;
	private Integer eare_id;
	private BigDecimal fpay;
	private BigDecimal hpay;
	private BigDecimal zpay;
	private String continueDate;
	private Date cd1;
	private Date cd2;
	private String emba_indate;
	private Integer emar_eada_id;
	private Integer emba_state;
	public EmArchiveModel() {
		super();
	}

	public EmArchiveModel(Integer emar_id, Integer cid, Integer gid,
			String emar_company, String emar_name, String emar_sex,
			String emar_idcard, String emar_link, String emar_xs,
			String emar_fid, String emar_archivetype, String emar_archivearea,
			String emar_surrogateid, String emar_surrogatecardid,
			String emar_transferorderid, String emar_archivesource,
			String emar_archiveplace, String emar_promisesdate,
			String emar_prodate, String emar_firstdeadline,
			String emar_continuedeadline, String emar_client,
			String emar_grouptype, String emar_censusregister,
			Integer emar_crbelongs, String emar_specialty, String emar_degree,
			String emar_caste, String emar_casteassessdate, String emar_party,
			String emar_partydate, Integer emar_partybelongs,
			String emar_marrystate, String emar_fertilitystate,
			String emar_school, String emar_gradate, String emar_workdate,
			String emar_szresume, Integer emar_colhj, String emar_wtmode,
			String emar_inciicdate, String emar_archivefolddate,
			String emar_archivefoldreason, String emar_archivefoldmode,
			String emar_archiveclass, String emar_peoplefoldmode,
			String emar_archiveremovedate, String emar_archiveremovereason,
			String emar_archiveremovermode, String emar_archiveremoveplace,
			String emar_rsetup, String emar_rdate, String emar_address,
			String emar_rspaykind, String emar_hjpaykind,
			String emar_rsinvoice, String emar_hjinvoice, String emar_remark,
			String emar_addtime, String emar_addname, String emar_modtime,
			String emar_modname, Integer emar_specialdata, Integer emar_state,
			String state, Integer num) {
		super();
		this.emar_id = emar_id;
		this.cid = cid;
		this.gid = gid;
		this.emar_company = emar_company;
		this.emar_name = emar_name;
		this.emar_sex = emar_sex;
		this.emar_idcard = emar_idcard;
		this.emar_link = emar_link;
		this.emar_xs = emar_xs;
		this.emar_fid = emar_fid;
		this.emar_archivetype = emar_archivetype;
		this.emar_archivearea = emar_archivearea;
		this.emar_surrogateid = emar_surrogateid;
		this.emar_surrogatecardid = emar_surrogatecardid;
		this.emar_transferorderid = emar_transferorderid;
		this.emar_archivesource = emar_archivesource;
		this.emar_archiveplace = emar_archiveplace;
		this.emar_promisesdate = emar_promisesdate;
		this.emar_prodate = emar_prodate;
		this.emar_firstdeadline = emar_firstdeadline;
		this.emar_continuedeadline = emar_continuedeadline;
		this.emar_client = emar_client;
		this.emar_grouptype = emar_grouptype;
		this.emar_censusregister = emar_censusregister;
		this.emar_crbelongs = emar_crbelongs;
		this.emar_specialty = emar_specialty;
		this.emar_degree = emar_degree;
		this.emar_caste = emar_caste;
		this.emar_casteassessdate = emar_casteassessdate;
		this.emar_party = emar_party;
		this.emar_partydate = emar_partydate;
		this.emar_partybelongs = emar_partybelongs;
		this.emar_marrystate = emar_marrystate;
		this.emar_fertilitystate = emar_fertilitystate;
		this.emar_school = emar_school;
		this.emar_gradate = emar_gradate;
		this.emar_workdate = emar_workdate;
		this.emar_szresume = emar_szresume;
		this.emar_colhj = emar_colhj;
		this.emar_wtmode = emar_wtmode;
		this.emar_inciicdate = emar_inciicdate;
		this.emar_archivefolddate = emar_archivefolddate;
		this.emar_archivefoldreason = emar_archivefoldreason;
		this.emar_archivefoldmode = emar_archivefoldmode;
		this.emar_archiveclass = emar_archiveclass;
		this.emar_peoplefoldmode = emar_peoplefoldmode;
		this.emar_archiveremovedate = emar_archiveremovedate;
		this.emar_archiveremovereason = emar_archiveremovereason;
		this.emar_archiveremovermode = emar_archiveremovermode;
		this.emar_archiveremoveplace = emar_archiveremoveplace;
		this.emar_rsetup = emar_rsetup;
		this.emar_rdate = emar_rdate;
		this.emar_address = emar_address;
		this.emar_rspaykind = emar_rspaykind;
		this.emar_hjpaykind = emar_hjpaykind;
		this.emar_rsinvoice = emar_rsinvoice;
		this.emar_hjinvoice = emar_hjinvoice;
		this.emar_remark = emar_remark;
		this.emar_addtime = emar_addtime;
		this.emar_addname = emar_addname;
		this.emar_modtime = emar_modtime;
		this.emar_modname = emar_modname;
		this.emar_specialdata = emar_specialdata;
		this.emar_state = emar_state;
		this.state = state;
		this.num = num;
	}

	
	public Integer getEmba_state() {
		return emba_state;
	}

	public void setEmba_state(Integer emba_state) {
		this.emba_state = emba_state;
	}

	public Integer getEmar_id() {
		return emar_id;
	}

	public void setEmar_id(Integer emar_id) {
		this.emar_id = emar_id;
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

	public String getEmar_company() {
		return emar_company;
	}

	public void setEmar_company(String emar_company) {
		this.emar_company = emar_company;
	}

	public String getEmar_name() {
		return emar_name;
	}

	public void setEmar_name(String emar_name) {
		this.emar_name = emar_name;
	}

	public String getEmar_sex() {
		return emar_sex;
	}

	public void setEmar_sex(String emar_sex) {
		this.emar_sex = emar_sex;
	}

	public String getEmar_idcard() {
		return emar_idcard;
	}

	public void setEmar_idcard(String emar_idcard) {
		this.emar_idcard = emar_idcard;
	}

	public String getEmar_link() {
		return emar_link;
	}

	public void setEmar_link(String emar_link) {
		this.emar_link = emar_link;
	}

	public String getEmar_xs() {
		return emar_xs;
	}

	public void setEmar_xs(String emar_xs) {
		this.emar_xs = emar_xs;
	}

	public String getEmar_fid() {
		return emar_fid;
	}

	public void setEmar_fid(String emar_fid) {
		this.emar_fid = emar_fid;
	}

	public String getEmar_archivetype() {
		return emar_archivetype;
	}

	public void setEmar_archivetype(String emar_archivetype) {
		this.emar_archivetype = emar_archivetype;
	}

	public String getEmar_archivearea() {
		return emar_archivearea;
	}

	public void setEmar_archivearea(String emar_archivearea) {
		this.emar_archivearea = emar_archivearea;
	}

	public String getEmar_surrogateid() {
		return emar_surrogateid;
	}

	public void setEmar_surrogateid(String emar_surrogateid) {
		this.emar_surrogateid = emar_surrogateid;
	}

	public String getEmar_surrogatecardid() {
		return emar_surrogatecardid;
	}

	public void setEmar_surrogatecardid(String emar_surrogatecardid) {
		this.emar_surrogatecardid = emar_surrogatecardid;
	}

	public String getEmar_transferorderid() {
		return emar_transferorderid;
	}

	public void setEmar_transferorderid(String emar_transferorderid) {
		this.emar_transferorderid = emar_transferorderid;
	}

	public String getEmar_archivesource() {
		return emar_archivesource;
	}

	public void setEmar_archivesource(String emar_archivesource) {
		this.emar_archivesource = emar_archivesource;
	}

	public String getEmar_archiveplace() {
		return emar_archiveplace;
	}

	public void setEmar_archiveplace(String emar_archiveplace) {
		this.emar_archiveplace = emar_archiveplace;
	}

	public String getEmar_promisesdate() {
		return emar_promisesdate;
	}

	public void setEmar_promisesdate(String emar_promisesdate) {
		this.emar_promisesdate = emar_promisesdate;
	}

	public String getEmar_prodate() {
		return emar_prodate;
	}

	public void setEmar_prodate(String emar_prodate) {
		this.emar_prodate = emar_prodate;
	}

	public String getEmar_firstdeadline() {
		return emar_firstdeadline;
	}

	public void setEmar_firstdeadline(String emar_firstdeadline) {
		this.emar_firstdeadline = emar_firstdeadline;
	}

	public String getEmar_continuedeadline() {
		return emar_continuedeadline;
	}

	public void setEmar_continuedeadline(String emar_continuedeadline) {
		this.emar_continuedeadline = emar_continuedeadline;
	}

	public String getEmar_client() {
		return emar_client;
	}

	public void setEmar_client(String emar_client) {
		this.emar_client = emar_client;
	}

	public String getEmar_grouptype() {
		return emar_grouptype;
	}

	public void setEmar_grouptype(String emar_grouptype) {
		this.emar_grouptype = emar_grouptype;
	}

	public String getEmar_censusregister() {
		return emar_censusregister;
	}

	public void setEmar_censusregister(String emar_censusregister) {
		this.emar_censusregister = emar_censusregister;
	}

	public Integer getEmar_crbelongs() {
		return emar_crbelongs;
	}

	public void setEmar_crbelongs(Integer emar_crbelongs) {
		this.emar_crbelongs = emar_crbelongs;
	}

	public String getEmar_specialty() {
		return emar_specialty;
	}

	public void setEmar_specialty(String emar_specialty) {
		this.emar_specialty = emar_specialty;
	}

	public String getEmar_degree() {
		return emar_degree;
	}

	public void setEmar_degree(String emar_degree) {
		this.emar_degree = emar_degree;
	}

	public String getEmar_caste() {
		return emar_caste;
	}

	public void setEmar_caste(String emar_caste) {
		this.emar_caste = emar_caste;
	}

	public String getEmar_casteassessdate() {
		return emar_casteassessdate;
	}

	public void setEmar_casteassessdate(String emar_casteassessdate) {
		this.emar_casteassessdate = emar_casteassessdate;
	}

	public String getEmar_party() {
		return emar_party;
	}

	public void setEmar_party(String emar_party) {
		this.emar_party = emar_party;
	}

	public String getEmar_partydate() {
		return emar_partydate;
	}

	public void setEmar_partydate(String emar_partydate) {
		this.emar_partydate = emar_partydate;
	}

	public Integer getEmar_partybelongs() {
		return emar_partybelongs;
	}

	public void setEmar_partybelongs(Integer emar_partybelongs) {
		this.emar_partybelongs = emar_partybelongs;
	}

	public String getEmar_marrystate() {
		return emar_marrystate;
	}

	public void setEmar_marrystate(String emar_marrystate) {
		this.emar_marrystate = emar_marrystate;
	}

	public String getEmar_fertilitystate() {
		return emar_fertilitystate;
	}

	public void setEmar_fertilitystate(String emar_fertilitystate) {
		this.emar_fertilitystate = emar_fertilitystate;
	}

	public String getEmar_school() {
		return emar_school;
	}

	public void setEmar_school(String emar_school) {
		this.emar_school = emar_school;
	}

	public String getEmar_gradate() {
		return emar_gradate;
	}

	public void setEmar_gradate(String emar_gradate) {
		this.emar_gradate = emar_gradate;
	}

	public String getEmar_workdate() {
		return emar_workdate;
	}

	public void setEmar_workdate(String emar_workdate) {
		this.emar_workdate = emar_workdate;
	}

	public String getEmar_szresume() {
		return emar_szresume;
	}

	public void setEmar_szresume(String emar_szresume) {
		this.emar_szresume = emar_szresume;
	}

	public Integer getEmar_colhj() {
		return emar_colhj;
	}

	public void setEmar_colhj(Integer emar_colhj) {
		this.emar_colhj = emar_colhj;
	}

	public String getEmar_wtmode() {
		return emar_wtmode;
	}

	public void setEmar_wtmode(String emar_wtmode) {
		this.emar_wtmode = emar_wtmode;
	}

	public String getEmar_inciicdate() {
		return emar_inciicdate;
	}

	public void setEmar_inciicdate(String emar_inciicdate) {
		this.emar_inciicdate = emar_inciicdate;
	}

	public String getEmar_archivefolddate() {
		return emar_archivefolddate;
	}

	public void setEmar_archivefolddate(String emar_archivefolddate) {
		this.emar_archivefolddate = emar_archivefolddate;
	}

	public String getEmar_archivefoldreason() {
		return emar_archivefoldreason;
	}

	public void setEmar_archivefoldreason(String emar_archivefoldreason) {
		this.emar_archivefoldreason = emar_archivefoldreason;
	}

	public String getEmar_archivefoldmode() {
		return emar_archivefoldmode;
	}

	public void setEmar_archivefoldmode(String emar_archivefoldmode) {
		this.emar_archivefoldmode = emar_archivefoldmode;
	}

	public String getEmar_archiveclass() {
		return emar_archiveclass;
	}

	public void setEmar_archiveclass(String emar_archiveclass) {
		this.emar_archiveclass = emar_archiveclass;
	}

	public String getEmar_peoplefoldmode() {
		return emar_peoplefoldmode;
	}

	public void setEmar_peoplefoldmode(String emar_peoplefoldmode) {
		this.emar_peoplefoldmode = emar_peoplefoldmode;
	}

	public String getEmar_archiveremovedate() {
		return emar_archiveremovedate;
	}

	public void setEmar_archiveremovedate(String emar_archiveremovedate) {
		this.emar_archiveremovedate = emar_archiveremovedate;
	}

	public String getEmar_archiveremovereason() {
		return emar_archiveremovereason;
	}

	public void setEmar_archiveremovereason(String emar_archiveremovereason) {
		this.emar_archiveremovereason = emar_archiveremovereason;
	}

	public String getEmar_archiveremovermode() {
		return emar_archiveremovermode;
	}

	public void setEmar_archiveremovermode(String emar_archiveremovermode) {
		this.emar_archiveremovermode = emar_archiveremovermode;
	}

	public String getEmar_archiveremoveplace() {
		return emar_archiveremoveplace;
	}

	public void setEmar_archiveremoveplace(String emar_archiveremoveplace) {
		this.emar_archiveremoveplace = emar_archiveremoveplace;
	}

	public String getEmar_rsetup() {
		return emar_rsetup;
	}

	public void setEmar_rsetup(String emar_rsetup) {
		this.emar_rsetup = emar_rsetup;
	}

	public String getEmar_rdate() {
		return emar_rdate;
	}

	public void setEmar_rdate(String emar_rdate) {
		this.emar_rdate = emar_rdate;
	}

	public String getEmar_address() {
		return emar_address;
	}

	public void setEmar_address(String emar_address) {
		this.emar_address = emar_address;
	}

	public String getEmar_rspaykind() {
		return emar_rspaykind;
	}

	public void setEmar_rspaykind(String emar_rspaykind) {
		this.emar_rspaykind = emar_rspaykind;
	}

	public String getEmar_hjpaykind() {
		return emar_hjpaykind;
	}

	public void setEmar_hjpaykind(String emar_hjpaykind) {
		this.emar_hjpaykind = emar_hjpaykind;
	}

	public String getEmar_rsinvoice() {
		return emar_rsinvoice;
	}

	public void setEmar_rsinvoice(String emar_rsinvoice) {
		this.emar_rsinvoice = emar_rsinvoice;
	}

	public String getEmar_hjinvoice() {
		return emar_hjinvoice;
	}

	public void setEmar_hjinvoice(String emar_hjinvoice) {
		this.emar_hjinvoice = emar_hjinvoice;
	}

	public String getEmar_remark() {
		return emar_remark;
	}

	public void setEmar_remark(String emar_remark) {
		this.emar_remark = emar_remark;
	}

	public String getEmar_addtime() {
		return emar_addtime;
	}

	public void setEmar_addtime(String emar_addtime) {
		this.emar_addtime = emar_addtime;
	}

	public String getEmar_addname() {
		return emar_addname;
	}

	public void setEmar_addname(String emar_addname) {
		this.emar_addname = emar_addname;
	}

	public String getEmar_modtime() {
		return emar_modtime;
	}

	public void setEmar_modtime(String emar_modtime) {
		this.emar_modtime = emar_modtime;
	}

	public String getEmar_modname() {
		return emar_modname;
	}

	public void setEmar_modname(String emar_modname) {
		this.emar_modname = emar_modname;
	}

	public Integer getEmar_specialdata() {
		return emar_specialdata;
	}

	public void setEmar_specialdata(Integer emar_specialdata) {
		this.emar_specialdata = emar_specialdata;
	}

	public Integer getEmar_state() {
		return emar_state;
	}

	public void setEmar_state(Integer emar_state) {
		this.emar_state = emar_state;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getEmar_colhjname() {
		return emar_colhjname;
	}

	public void setEmar_colhjname(String emar_colhjname) {
		this.emar_colhjname = emar_colhjname;
	}

	public String getEmar_fid2() {
		return emar_fid2;
	}

	public void setEmar_fid2(String emar_fid2) {
		this.emar_fid2 = emar_fid2;
	}

	public String getCddate() {
		return cddate;
	}

	public void setCddate(String cddate) {
		this.cddate = cddate;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Integer getEare_id() {
		return eare_id;
	}

	public void setEare_id(Integer eare_id) {
		this.eare_id = eare_id;
	}

	public BigDecimal getFpay() {
		return fpay;
	}

	public void setFpay(BigDecimal fpay) {
		this.fpay = fpay;
	}

	public BigDecimal getHpay() {
		return hpay;
	}

	public void setHpay(BigDecimal hpay) {
		this.hpay = hpay;
	}

	public BigDecimal getZpay() {
		return zpay;
	}

	public void setZpay(BigDecimal zpay) {
		this.zpay = zpay;
	}

	public String getContinueDate() {
		return continueDate;
	}

	public void setContinueDate(String continueDate) {
		this.continueDate = continueDate;
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

	public String getEmba_indate() {
		return emba_indate;
	}

	public void setEmba_indate(String emba_indate) {
		this.emba_indate = emba_indate;
	}

	public Integer getEmar_eada_id() {
		return emar_eada_id;
	}

	public void setEmar_eada_id(Integer emar_eada_id) {
		this.emar_eada_id = emar_eada_id;
	}
	
}
