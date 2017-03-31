package Model;

public class EmZYTFeedbackModel {
	private int id;
	private String ezfb_billmonth;
	private int ezfb_zytid;
	private int ezfb_listid;
	private String ezfb_gid;
	private String ezfb_name;
	private String ezfb_cid;
	private String ezfb_company;
	private String ezfb_idcardclass;
	private String ezfb_idcard;
	private String ezfb_class;
	private String ezfb_hbstate;
	private String ezfb_hbstart;
	private String ezfb_hbstop;
	private String ezfb_hbclass;
	private String ezfb_ylstate;
	private String ezfb_yl_cradix;
	private String ezfb_yl_oradix;
	private String ezfb_ylstart;
	private String ezfb_ylstop;
	private String ezfb_ylcity;
	private String ezfb_housestate;
	private String ezfb_house_cradix;
	private String ezfb_house_oradix;
	private String ezfb_housecpp;
	private String ezfb_houseopp;
	private String ezfb_housestart;
	private String ezfb_housestop;
	private String ezfb_housecity;
	private String ezfb_compactstate;
	private String ezfb_compactstart;
	private String ezfb_compactstop;
	private String ezfb_feestate;
	private String ezfb_feestart;
	private String ezfb_feestop;
	private String ezfb_filestate;
	private String ezfb_filestart;
	private String ezfb_filestop;
	private String ezfb_elsestate;
	private String ezfb_elsestart;
	private String ezfb_elsestop;
	private String ezfb_cremark;
	private String ezfb_cremark_class;
	private String ezfb_cremark_content;
	private String ezfb_zytgid;
	private String ezfb_zytcid;
	private String ezfb_ct_gid;
	private String ezfb_ct_cid;
	private String ezfb_city;
	private String ezfb_submittime;
	private String ezfb_confirmtime;
	private String ezfb_ylop_pm;
	private String ezfb_ylcp_pm;
	private String ezfb_jlop_pm;
	private String ezfb_jlcp_pm;
	private String ezfb_gsop_pm;
	private String ezfb_gscp_pm;
	private String ezfb_syuop_pm;
	private String ezfb_syucp_pm;
	private String ezfb_syeop_pm;
	private String ezfb_syecp_pm;
	private String ezfb_houseop_pm;
	private String ezfb_housecp_pm;
	private String ezfb_bchouseop_pm;
	private String ezfb_bchousecp_pm;
	private String ezfb_feetotal;
	private String ezfb_addname;
	private String ezfb_addtime;
	private String ezfb_addfilename;
	private String ezfb_outname;
	private String ezfb_outtime;
	private String ezfb_outfilename;
	private String ezfb_gsstate;
	private String ezfb_gs_cradix;
	private String ezfb_gs_oradix;
	private String ezfb_gsstart;
	private String ezfb_gsstop;
	private String ezfb_gscity;
	private String ezfb_syustate;
	private String ezfb_syu_cradix;
	private String ezfb_syu_oradix;
	private String ezfb_syustart;
	private String ezfb_syustop;
	private String ezfb_syucity;
	private String ezfb_bchousestate;
	private String ezfb_bchouse_cradix;
	private String ezfb_bchouse_oradix;
	private String ezfb_bchousecpp;
	private String ezfb_bchouseopp;
	private String ezfb_bchousestart;
	private String ezfb_bchousestop;
	private String ezfb_bchousecity;
	private String ezfb_sb_remark;
	private String ezfb_house_remark;
	private String ezfb_hbstartBMS;
	private String ezfb_hbstopBMS;
	private String ezfb_ylstartBMS;
	private String ezfb_ylstopBMS;
	private String ezfb_housestartBMS;
	private String ezfb_housestopBMS;
	private String ezfb_compactstartBMS;
	private String ezfb_compactstopBMS;
	private String ezfb_feestartBMS;
	private String ezfb_feestopBMS;
	private String ezfb_filestartBMS;
	private String ezfb_filestopBMS;
	private String ezfb_elsestartBMS;
	private String ezfb_elsestopBMS;
	private String ezfb_ylradixBMS;
	private String ezfb_jlradixBMS;
	private String ezfb_houseradixBMS;
	
	//更新失败原因
	private String ezfb_yl_failed;
	private String ezfb_jl_failed;
	private String ezfb_house_failed;
	
	private String ezfb_wtremark;
	
	private String ezfb_qz_fee;
	private String ezfb_qz_housecp;
	private String ezfb_qz_houseop;
	private String ezfb_qz_sbcp;
	private String ezfb_qz_sbop;
	private String ezfb_qz_file	;
	private String ezfb_qz_product	;
	private String ezfb_wt_agency;
	
	public EmZYTFeedbackModel() {
		super();
	}
	
	
	
	public EmZYTFeedbackModel(int id, String ezfb_billmonth, int ezfb_zytid,
			int ezfb_listid, String ezfb_gid, String ezfb_name,
			String ezfb_cid, String ezfb_company, String ezfb_idcardclass,
			String ezfb_idcard, String ezfb_class, String ezfb_hbstate,
			String ezfb_hbstart, String ezfb_hbstop, String ezfb_hbclass,
			String ezfb_ylstate, String ezfb_yl_cradix, String ezfb_yl_oradix,
			String ezfb_ylstart, String ezfb_ylstop, String ezfb_ylcity,
			String ezfb_housestate, String ezfb_house_cradix,
			String ezfb_house_oradix, String ezfb_housecpp,
			String ezfb_houseopp, String ezfb_housestart,
			String ezfb_housestop, String ezfb_housecity,
			String ezfb_compactstate, String ezfb_compactstart,
			String ezfb_compactstop, String ezfb_feestate,
			String ezfb_feestart, String ezfb_feestop, String ezfb_filestate,
			String ezfb_filestart, String ezfb_filestop, String ezfb_elsestate,
			String ezfb_elsestart, String ezfb_elsestop, String ezfb_cremark,
			String ezfb_cremark_class, String ezfb_cremark_content,
			String ezfb_zytgid, String ezfb_zytcid, String ezfb_ct_gid,
			String ezfb_ct_cid, String ezfb_city, String ezfb_submittime,
			String ezfb_confirmtime, String ezfb_ylop_pm, String ezfb_ylcp_pm,
			String ezfb_jlop_pm, String ezfb_jlcp_pm, String ezfb_gsop_pm,
			String ezfb_gscp_pm, String ezfb_syuop_pm, String ezfb_syucp_pm,
			String ezfb_syeop_pm, String ezfb_syecp_pm, String ezfb_houseop_pm,
			String ezfb_housecp_pm, String ezfb_bchouseop_pm,
			String ezfb_bchousecp_pm, String ezfb_feetotal,
			String ezfb_addname, String ezfb_addtime, String ezfb_addfilename,
			String ezfb_outname, String ezfb_outtime, String ezfb_outfilename,
			String ezfb_gsstate, String ezfb_gs_cradix, String ezfb_gs_oradix,
			String ezfb_gsstart, String ezfb_gsstop, String ezfb_gscity,
			String ezfb_syustate, String ezfb_syu_cradix,
			String ezfb_syu_oradix, String ezfb_syustart, String ezfb_syustop,
			String ezfb_syucity, String ezfb_bchousestate,
			String ezfb_bchouse_cradix, String ezfb_bchouse_oradix,
			String ezfb_bchousecpp, String ezfb_bchouseopp,
			String ezfb_bchousestart, String ezfb_bchousestop,
			String ezfb_bchousecity, String ezfb_sb_remark,
			String ezfb_house_remark, String ezfb_hbstartBMS,
			String ezfb_hbstopBMS, String ezfb_ylstartBMS,
			String ezfb_ylstopBMS, String ezfb_housestartBMS,
			String ezfb_housestopBMS, String ezfb_compactstartBMS,
			String ezfb_compactstopBMS, String ezfb_feestartBMS,
			String ezfb_feestopBMS, String ezfb_filestartBMS,
			String ezfb_filestopBMS, String ezfb_elsestartBMS,
			String ezfb_elsestopBMS, String ezfb_ylradixBMS,
			String ezfb_jlradixBMS, String ezfb_houseradixBMS) {
		super();
		this.id = id;
		this.ezfb_billmonth = ezfb_billmonth;
		this.ezfb_zytid = ezfb_zytid;
		this.ezfb_listid = ezfb_listid;
		this.ezfb_gid = ezfb_gid;
		this.ezfb_name = ezfb_name;
		this.ezfb_cid = ezfb_cid;
		this.ezfb_company = ezfb_company;
		this.ezfb_idcardclass = ezfb_idcardclass;
		this.ezfb_idcard = ezfb_idcard;
		this.ezfb_class = ezfb_class;
		this.ezfb_hbstate = ezfb_hbstate;
		this.ezfb_hbstart = ezfb_hbstart;
		this.ezfb_hbstop = ezfb_hbstop;
		this.ezfb_hbclass = ezfb_hbclass;
		this.ezfb_ylstate = ezfb_ylstate;
		this.ezfb_yl_cradix = ezfb_yl_cradix;
		this.ezfb_yl_oradix = ezfb_yl_oradix;
		this.ezfb_ylstart = ezfb_ylstart;
		this.ezfb_ylstop = ezfb_ylstop;
		this.ezfb_ylcity = ezfb_ylcity;
		this.ezfb_housestate = ezfb_housestate;
		this.ezfb_house_cradix = ezfb_house_cradix;
		this.ezfb_house_oradix = ezfb_house_oradix;
		this.ezfb_housecpp = ezfb_housecpp;
		this.ezfb_houseopp = ezfb_houseopp;
		this.ezfb_housestart = ezfb_housestart;
		this.ezfb_housestop = ezfb_housestop;
		this.ezfb_housecity = ezfb_housecity;
		this.ezfb_compactstate = ezfb_compactstate;
		this.ezfb_compactstart = ezfb_compactstart;
		this.ezfb_compactstop = ezfb_compactstop;
		this.ezfb_feestate = ezfb_feestate;
		this.ezfb_feestart = ezfb_feestart;
		this.ezfb_feestop = ezfb_feestop;
		this.ezfb_filestate = ezfb_filestate;
		this.ezfb_filestart = ezfb_filestart;
		this.ezfb_filestop = ezfb_filestop;
		this.ezfb_elsestate = ezfb_elsestate;
		this.ezfb_elsestart = ezfb_elsestart;
		this.ezfb_elsestop = ezfb_elsestop;
		this.ezfb_cremark = ezfb_cremark;
		this.ezfb_cremark_class = ezfb_cremark_class;
		this.ezfb_cremark_content = ezfb_cremark_content;
		this.ezfb_zytgid = ezfb_zytgid;
		this.ezfb_zytcid = ezfb_zytcid;
		this.ezfb_ct_gid = ezfb_ct_gid;
		this.ezfb_ct_cid = ezfb_ct_cid;
		this.ezfb_city = ezfb_city;
		this.ezfb_submittime = ezfb_submittime;
		this.ezfb_confirmtime = ezfb_confirmtime;
		this.ezfb_ylop_pm = ezfb_ylop_pm;
		this.ezfb_ylcp_pm = ezfb_ylcp_pm;
		this.ezfb_jlop_pm = ezfb_jlop_pm;
		this.ezfb_jlcp_pm = ezfb_jlcp_pm;
		this.ezfb_gsop_pm = ezfb_gsop_pm;
		this.ezfb_gscp_pm = ezfb_gscp_pm;
		this.ezfb_syuop_pm = ezfb_syuop_pm;
		this.ezfb_syucp_pm = ezfb_syucp_pm;
		this.ezfb_syeop_pm = ezfb_syeop_pm;
		this.ezfb_syecp_pm = ezfb_syecp_pm;
		this.ezfb_houseop_pm = ezfb_houseop_pm;
		this.ezfb_housecp_pm = ezfb_housecp_pm;
		this.ezfb_bchouseop_pm = ezfb_bchouseop_pm;
		this.ezfb_bchousecp_pm = ezfb_bchousecp_pm;
		this.ezfb_feetotal = ezfb_feetotal;
		this.ezfb_addname = ezfb_addname;
		this.ezfb_addtime = ezfb_addtime;
		this.ezfb_addfilename = ezfb_addfilename;
		this.ezfb_outname = ezfb_outname;
		this.ezfb_outtime = ezfb_outtime;
		this.ezfb_outfilename = ezfb_outfilename;
		this.ezfb_gsstate = ezfb_gsstate;
		this.ezfb_gs_cradix = ezfb_gs_cradix;
		this.ezfb_gs_oradix = ezfb_gs_oradix;
		this.ezfb_gsstart = ezfb_gsstart;
		this.ezfb_gsstop = ezfb_gsstop;
		this.ezfb_gscity = ezfb_gscity;
		this.ezfb_syustate = ezfb_syustate;
		this.ezfb_syu_cradix = ezfb_syu_cradix;
		this.ezfb_syu_oradix = ezfb_syu_oradix;
		this.ezfb_syustart = ezfb_syustart;
		this.ezfb_syustop = ezfb_syustop;
		this.ezfb_syucity = ezfb_syucity;
		this.ezfb_bchousestate = ezfb_bchousestate;
		this.ezfb_bchouse_cradix = ezfb_bchouse_cradix;
		this.ezfb_bchouse_oradix = ezfb_bchouse_oradix;
		this.ezfb_bchousecpp = ezfb_bchousecpp;
		this.ezfb_bchouseopp = ezfb_bchouseopp;
		this.ezfb_bchousestart = ezfb_bchousestart;
		this.ezfb_bchousestop = ezfb_bchousestop;
		this.ezfb_bchousecity = ezfb_bchousecity;
		this.ezfb_sb_remark = ezfb_sb_remark;
		this.ezfb_house_remark = ezfb_house_remark;
		this.ezfb_hbstartBMS = ezfb_hbstartBMS;
		this.ezfb_hbstopBMS = ezfb_hbstopBMS;
		this.ezfb_ylstartBMS = ezfb_ylstartBMS;
		this.ezfb_ylstopBMS = ezfb_ylstopBMS;
		this.ezfb_housestartBMS = ezfb_housestartBMS;
		this.ezfb_housestopBMS = ezfb_housestopBMS;
		this.ezfb_compactstartBMS = ezfb_compactstartBMS;
		this.ezfb_compactstopBMS = ezfb_compactstopBMS;
		this.ezfb_feestartBMS = ezfb_feestartBMS;
		this.ezfb_feestopBMS = ezfb_feestopBMS;
		this.ezfb_filestartBMS = ezfb_filestartBMS;
		this.ezfb_filestopBMS = ezfb_filestopBMS;
		this.ezfb_elsestartBMS = ezfb_elsestartBMS;
		this.ezfb_elsestopBMS = ezfb_elsestopBMS;
		this.ezfb_ylradixBMS = ezfb_ylradixBMS;
		this.ezfb_jlradixBMS = ezfb_jlradixBMS;
		this.ezfb_houseradixBMS = ezfb_houseradixBMS;
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEzfb_billmonth() {
		return ezfb_billmonth;
	}
	public void setEzfb_billmonth(String ezfb_billmonth) {
		this.ezfb_billmonth = ezfb_billmonth;
	}
	public int getEzfb_zytid() {
		return ezfb_zytid;
	}
	public void setEzfb_zytid(int ezfb_zytid) {
		this.ezfb_zytid = ezfb_zytid;
	}
	public int getEzfb_listid() {
		return ezfb_listid;
	}
	public void setEzfb_listid(int ezfb_listid) {
		this.ezfb_listid = ezfb_listid;
	}
	public String getEzfb_gid() {
		return ezfb_gid;
	}
	public void setEzfb_gid(String ezfb_gid) {
		this.ezfb_gid = ezfb_gid;
	}
	public String getEzfb_name() {
		return ezfb_name;
	}
	public void setEzfb_name(String ezfb_name) {
		this.ezfb_name = ezfb_name;
	}
	public String getEzfb_cid() {
		return ezfb_cid;
	}
	public void setEzfb_cid(String ezfb_cid) {
		this.ezfb_cid = ezfb_cid;
	}
	public String getEzfb_company() {
		return ezfb_company;
	}
	public void setEzfb_company(String ezfb_company) {
		this.ezfb_company = ezfb_company;
	}
	public String getEzfb_idcardclass() {
		return ezfb_idcardclass;
	}
	public void setEzfb_idcardclass(String ezfb_idcardclass) {
		this.ezfb_idcardclass = ezfb_idcardclass;
	}
	public String getEzfb_idcard() {
		return ezfb_idcard;
	}
	public void setEzfb_idcard(String ezfb_idcard) {
		this.ezfb_idcard = ezfb_idcard;
	}
	public String getEzfb_class() {
		return ezfb_class;
	}
	public void setEzfb_class(String ezfb_class) {
		this.ezfb_class = ezfb_class;
	}
	public String getEzfb_hbstate() {
		return ezfb_hbstate;
	}
	public void setEzfb_hbstate(String ezfb_hbstate) {
		this.ezfb_hbstate = ezfb_hbstate;
	}
	public String getEzfb_hbstart() {
		return ezfb_hbstart;
	}
	public void setEzfb_hbstart(String ezfb_hbstart) {
		this.ezfb_hbstart = ezfb_hbstart;
	}
	public String getEzfb_hbstop() {
		return ezfb_hbstop;
	}
	public void setEzfb_hbstop(String ezfb_hbstop) {
		this.ezfb_hbstop = ezfb_hbstop;
	}
	public String getEzfb_hbclass() {
		return ezfb_hbclass;
	}
	public void setEzfb_hbclass(String ezfb_hbclass) {
		this.ezfb_hbclass = ezfb_hbclass;
	}
	public String getEzfb_ylstate() {
		return ezfb_ylstate;
	}
	public void setEzfb_ylstate(String ezfb_ylstate) {
		this.ezfb_ylstate = ezfb_ylstate;
	}
	public String getEzfb_yl_cradix() {
		return ezfb_yl_cradix;
	}
	public void setEzfb_yl_cradix(String ezfb_yl_cradix) {
		this.ezfb_yl_cradix = ezfb_yl_cradix;
	}
	public String getEzfb_yl_oradix() {
		return ezfb_yl_oradix;
	}
	public void setEzfb_yl_oradix(String ezfb_yl_oradix) {
		this.ezfb_yl_oradix = ezfb_yl_oradix;
	}
	public String getEzfb_ylstart() {
		return ezfb_ylstart;
	}
	public void setEzfb_ylstart(String ezfb_ylstart) {
		this.ezfb_ylstart = ezfb_ylstart;
	}
	public String getEzfb_ylstop() {
		return ezfb_ylstop;
	}
	public void setEzfb_ylstop(String ezfb_ylstop) {
		this.ezfb_ylstop = ezfb_ylstop;
	}
	public String getEzfb_ylcity() {
		return ezfb_ylcity;
	}
	public void setEzfb_ylcity(String ezfb_ylcity) {
		this.ezfb_ylcity = ezfb_ylcity;
	}
	public String getEzfb_housestate() {
		return ezfb_housestate;
	}
	public void setEzfb_housestate(String ezfb_housestate) {
		this.ezfb_housestate = ezfb_housestate;
	}
	public String getEzfb_house_cradix() {
		return ezfb_house_cradix;
	}
	public void setEzfb_house_cradix(String ezfb_house_cradix) {
		this.ezfb_house_cradix = ezfb_house_cradix;
	}
	public String getEzfb_house_oradix() {
		return ezfb_house_oradix;
	}
	public void setEzfb_house_oradix(String ezfb_house_oradix) {
		this.ezfb_house_oradix = ezfb_house_oradix;
	}
	public String getEzfb_housecpp() {
		return ezfb_housecpp;
	}
	public void setEzfb_housecpp(String ezfb_housecpp) {
		this.ezfb_housecpp = ezfb_housecpp;
	}
	public String getEzfb_houseopp() {
		return ezfb_houseopp;
	}
	public void setEzfb_houseopp(String ezfb_houseopp) {
		this.ezfb_houseopp = ezfb_houseopp;
	}
	public String getEzfb_housestart() {
		return ezfb_housestart;
	}
	public void setEzfb_housestart(String ezfb_housestart) {
		this.ezfb_housestart = ezfb_housestart;
	}
	public String getEzfb_housestop() {
		return ezfb_housestop;
	}
	public void setEzfb_housestop(String ezfb_housestop) {
		this.ezfb_housestop = ezfb_housestop;
	}
	public String getEzfb_housecity() {
		return ezfb_housecity;
	}
	public void setEzfb_housecity(String ezfb_housecity) {
		this.ezfb_housecity = ezfb_housecity;
	}
	public String getEzfb_compactstate() {
		return ezfb_compactstate;
	}
	public void setEzfb_compactstate(String ezfb_compactstate) {
		this.ezfb_compactstate = ezfb_compactstate;
	}
	public String getEzfb_compactstart() {
		return ezfb_compactstart;
	}
	public void setEzfb_compactstart(String ezfb_compactstart) {
		this.ezfb_compactstart = ezfb_compactstart;
	}
	public String getEzfb_compactstop() {
		return ezfb_compactstop;
	}
	public void setEzfb_compactstop(String ezfb_compactstop) {
		this.ezfb_compactstop = ezfb_compactstop;
	}
	public String getEzfb_feestate() {
		return ezfb_feestate;
	}
	public void setEzfb_feestate(String ezfb_feestate) {
		this.ezfb_feestate = ezfb_feestate;
	}
	public String getEzfb_feestart() {
		return ezfb_feestart;
	}
	public void setEzfb_feestart(String ezfb_feestart) {
		this.ezfb_feestart = ezfb_feestart;
	}
	public String getEzfb_feestop() {
		return ezfb_feestop;
	}
	public void setEzfb_feestop(String ezfb_feestop) {
		this.ezfb_feestop = ezfb_feestop;
	}
	public String getEzfb_filestate() {
		return ezfb_filestate;
	}
	public void setEzfb_filestate(String ezfb_filestate) {
		this.ezfb_filestate = ezfb_filestate;
	}
	public String getEzfb_filestart() {
		return ezfb_filestart;
	}
	public void setEzfb_filestart(String ezfb_filestart) {
		this.ezfb_filestart = ezfb_filestart;
	}
	public String getEzfb_filestop() {
		return ezfb_filestop;
	}
	public void setEzfb_filestop(String ezfb_filestop) {
		this.ezfb_filestop = ezfb_filestop;
	}
	public String getEzfb_elsestate() {
		return ezfb_elsestate;
	}
	public void setEzfb_elsestate(String ezfb_elsestate) {
		this.ezfb_elsestate = ezfb_elsestate;
	}
	public String getEzfb_elsestart() {
		return ezfb_elsestart;
	}
	public void setEzfb_elsestart(String ezfb_elsestart) {
		this.ezfb_elsestart = ezfb_elsestart;
	}
	public String getEzfb_elsestop() {
		return ezfb_elsestop;
	}
	public void setEzfb_elsestop(String ezfb_elsestop) {
		this.ezfb_elsestop = ezfb_elsestop;
	}
	public String getEzfb_cremark() {
		return ezfb_cremark;
	}
	public void setEzfb_cremark(String ezfb_cremark) {
		this.ezfb_cremark = ezfb_cremark;
	}
	public String getEzfb_cremark_class() {
		return ezfb_cremark_class;
	}
	public void setEzfb_cremark_class(String ezfb_cremark_class) {
		this.ezfb_cremark_class = ezfb_cremark_class;
	}
	public String getEzfb_cremark_content() {
		return ezfb_cremark_content;
	}
	public void setEzfb_cremark_content(String ezfb_cremark_content) {
		this.ezfb_cremark_content = ezfb_cremark_content;
	}
	public String getEzfb_zytgid() {
		return ezfb_zytgid;
	}
	public void setEzfb_zytgid(String ezfb_zytgid) {
		this.ezfb_zytgid = ezfb_zytgid;
	}
	public String getEzfb_zytcid() {
		return ezfb_zytcid;
	}
	public void setEzfb_zytcid(String ezfb_zytcid) {
		this.ezfb_zytcid = ezfb_zytcid;
	}
	public String getEzfb_ct_gid() {
		return ezfb_ct_gid;
	}
	public void setEzfb_ct_gid(String ezfb_ct_gid) {
		this.ezfb_ct_gid = ezfb_ct_gid;
	}
	public String getEzfb_ct_cid() {
		return ezfb_ct_cid;
	}
	public void setEzfb_ct_cid(String ezfb_ct_cid) {
		this.ezfb_ct_cid = ezfb_ct_cid;
	}
	public String getEzfb_city() {
		return ezfb_city;
	}
	public void setEzfb_city(String ezfb_city) {
		this.ezfb_city = ezfb_city;
	}
	public String getEzfb_submittime() {
		return ezfb_submittime;
	}
	public void setEzfb_submittime(String ezfb_submittime) {
		this.ezfb_submittime = ezfb_submittime;
	}
	public String getEzfb_confirmtime() {
		return ezfb_confirmtime;
	}
	public void setEzfb_confirmtime(String ezfb_confirmtime) {
		this.ezfb_confirmtime = ezfb_confirmtime;
	}
	public String getEzfb_ylop_pm() {
		return ezfb_ylop_pm;
	}
	public void setEzfb_ylop_pm(String ezfb_ylop_pm) {
		this.ezfb_ylop_pm = ezfb_ylop_pm;
	}
	public String getEzfb_ylcp_pm() {
		return ezfb_ylcp_pm;
	}
	public void setEzfb_ylcp_pm(String ezfb_ylcp_pm) {
		this.ezfb_ylcp_pm = ezfb_ylcp_pm;
	}
	public String getEzfb_jlop_pm() {
		return ezfb_jlop_pm;
	}
	public void setEzfb_jlop_pm(String ezfb_jlop_pm) {
		this.ezfb_jlop_pm = ezfb_jlop_pm;
	}
	public String getEzfb_jlcp_pm() {
		return ezfb_jlcp_pm;
	}
	public void setEzfb_jlcp_pm(String ezfb_jlcp_pm) {
		this.ezfb_jlcp_pm = ezfb_jlcp_pm;
	}
	public String getEzfb_gsop_pm() {
		return ezfb_gsop_pm;
	}
	public void setEzfb_gsop_pm(String ezfb_gsop_pm) {
		this.ezfb_gsop_pm = ezfb_gsop_pm;
	}
	public String getEzfb_gscp_pm() {
		return ezfb_gscp_pm;
	}
	public void setEzfb_gscp_pm(String ezfb_gscp_pm) {
		this.ezfb_gscp_pm = ezfb_gscp_pm;
	}
	public String getEzfb_syuop_pm() {
		return ezfb_syuop_pm;
	}
	public void setEzfb_syuop_pm(String ezfb_syuop_pm) {
		this.ezfb_syuop_pm = ezfb_syuop_pm;
	}
	public String getEzfb_syucp_pm() {
		return ezfb_syucp_pm;
	}
	public void setEzfb_syucp_pm(String ezfb_syucp_pm) {
		this.ezfb_syucp_pm = ezfb_syucp_pm;
	}
	public String getEzfb_syeop_pm() {
		return ezfb_syeop_pm;
	}
	public void setEzfb_syeop_pm(String ezfb_syeop_pm) {
		this.ezfb_syeop_pm = ezfb_syeop_pm;
	}
	public String getEzfb_syecp_pm() {
		return ezfb_syecp_pm;
	}
	public void setEzfb_syecp_pm(String ezfb_syecp_pm) {
		this.ezfb_syecp_pm = ezfb_syecp_pm;
	}
	public String getEzfb_houseop_pm() {
		return ezfb_houseop_pm;
	}
	public void setEzfb_houseop_pm(String ezfb_houseop_pm) {
		this.ezfb_houseop_pm = ezfb_houseop_pm;
	}
	public String getEzfb_housecp_pm() {
		return ezfb_housecp_pm;
	}
	public void setEzfb_housecp_pm(String ezfb_housecp_pm) {
		this.ezfb_housecp_pm = ezfb_housecp_pm;
	}
	public String getEzfb_bchouseop_pm() {
		return ezfb_bchouseop_pm;
	}
	public void setEzfb_bchouseop_pm(String ezfb_bchouseop_pm) {
		this.ezfb_bchouseop_pm = ezfb_bchouseop_pm;
	}
	public String getEzfb_bchousecp_pm() {
		return ezfb_bchousecp_pm;
	}
	public void setEzfb_bchousecp_pm(String ezfb_bchousecp_pm) {
		this.ezfb_bchousecp_pm = ezfb_bchousecp_pm;
	}
	public String getEzfb_feetotal() {
		return ezfb_feetotal;
	}
	public void setEzfb_feetotal(String ezfb_feetotal) {
		this.ezfb_feetotal = ezfb_feetotal;
	}
	public String getEzfb_addname() {
		return ezfb_addname;
	}
	public void setEzfb_addname(String ezfb_addname) {
		this.ezfb_addname = ezfb_addname;
	}
	public String getEzfb_addtime() {
		return ezfb_addtime;
	}
	public void setEzfb_addtime(String ezfb_addtime) {
		this.ezfb_addtime = ezfb_addtime;
	}
	public String getEzfb_addfilename() {
		return ezfb_addfilename;
	}
	public void setEzfb_addfilename(String ezfb_addfilename) {
		this.ezfb_addfilename = ezfb_addfilename;
	}
	public String getEzfb_outname() {
		return ezfb_outname;
	}
	public void setEzfb_outname(String ezfb_outname) {
		this.ezfb_outname = ezfb_outname;
	}
	public String getEzfb_outtime() {
		return ezfb_outtime;
	}
	public void setEzfb_outtime(String ezfb_outtime) {
		this.ezfb_outtime = ezfb_outtime;
	}
	public String getEzfb_outfilename() {
		return ezfb_outfilename;
	}
	public void setEzfb_outfilename(String ezfb_outfilename) {
		this.ezfb_outfilename = ezfb_outfilename;
	}
	public String getEzfb_gsstate() {
		return ezfb_gsstate;
	}
	public void setEzfb_gsstate(String ezfb_gsstate) {
		this.ezfb_gsstate = ezfb_gsstate;
	}
	public String getEzfb_gs_cradix() {
		return ezfb_gs_cradix;
	}
	public void setEzfb_gs_cradix(String ezfb_gs_cradix) {
		this.ezfb_gs_cradix = ezfb_gs_cradix;
	}
	public String getEzfb_gs_oradix() {
		return ezfb_gs_oradix;
	}
	public void setEzfb_gs_oradix(String ezfb_gs_oradix) {
		this.ezfb_gs_oradix = ezfb_gs_oradix;
	}
	public String getEzfb_gsstart() {
		return ezfb_gsstart;
	}
	public void setEzfb_gsstart(String ezfb_gsstart) {
		this.ezfb_gsstart = ezfb_gsstart;
	}
	public String getEzfb_gsstop() {
		return ezfb_gsstop;
	}
	public void setEzfb_gsstop(String ezfb_gsstop) {
		this.ezfb_gsstop = ezfb_gsstop;
	}
	public String getEzfb_gscity() {
		return ezfb_gscity;
	}
	public void setEzfb_gscity(String ezfb_gscity) {
		this.ezfb_gscity = ezfb_gscity;
	}
	public String getEzfb_syustate() {
		return ezfb_syustate;
	}
	public void setEzfb_syustate(String ezfb_syustate) {
		this.ezfb_syustate = ezfb_syustate;
	}
	public String getEzfb_syu_cradix() {
		return ezfb_syu_cradix;
	}
	public void setEzfb_syu_cradix(String ezfb_syu_cradix) {
		this.ezfb_syu_cradix = ezfb_syu_cradix;
	}
	public String getEzfb_syu_oradix() {
		return ezfb_syu_oradix;
	}
	public void setEzfb_syu_oradix(String ezfb_syu_oradix) {
		this.ezfb_syu_oradix = ezfb_syu_oradix;
	}
	public String getEzfb_syustart() {
		return ezfb_syustart;
	}
	public void setEzfb_syustart(String ezfb_syustart) {
		this.ezfb_syustart = ezfb_syustart;
	}
	public String getEzfb_syustop() {
		return ezfb_syustop;
	}
	public void setEzfb_syustop(String ezfb_syustop) {
		this.ezfb_syustop = ezfb_syustop;
	}
	public String getEzfb_syucity() {
		return ezfb_syucity;
	}
	public void setEzfb_syucity(String ezfb_syucity) {
		this.ezfb_syucity = ezfb_syucity;
	}
	public String getEzfb_bchousestate() {
		return ezfb_bchousestate;
	}
	public void setEzfb_bchousestate(String ezfb_bchousestate) {
		this.ezfb_bchousestate = ezfb_bchousestate;
	}
	public String getEzfb_bchouse_cradix() {
		return ezfb_bchouse_cradix;
	}
	public void setEzfb_bchouse_cradix(String ezfb_bchouse_cradix) {
		this.ezfb_bchouse_cradix = ezfb_bchouse_cradix;
	}
	public String getEzfb_bchouse_oradix() {
		return ezfb_bchouse_oradix;
	}
	public void setEzfb_bchouse_oradix(String ezfb_bchouse_oradix) {
		this.ezfb_bchouse_oradix = ezfb_bchouse_oradix;
	}
	public String getEzfb_bchousecpp() {
		return ezfb_bchousecpp;
	}
	public void setEzfb_bchousecpp(String ezfb_bchousecpp) {
		this.ezfb_bchousecpp = ezfb_bchousecpp;
	}
	public String getEzfb_bchouseopp() {
		return ezfb_bchouseopp;
	}
	public void setEzfb_bchouseopp(String ezfb_bchouseopp) {
		this.ezfb_bchouseopp = ezfb_bchouseopp;
	}
	public String getEzfb_bchousestart() {
		return ezfb_bchousestart;
	}
	public void setEzfb_bchousestart(String ezfb_bchousestart) {
		this.ezfb_bchousestart = ezfb_bchousestart;
	}
	public String getEzfb_bchousestop() {
		return ezfb_bchousestop;
	}
	public void setEzfb_bchousestop(String ezfb_bchousestop) {
		this.ezfb_bchousestop = ezfb_bchousestop;
	}
	public String getEzfb_bchousecity() {
		return ezfb_bchousecity;
	}
	public void setEzfb_bchousecity(String ezfb_bchousecity) {
		this.ezfb_bchousecity = ezfb_bchousecity;
	}
	public String getEzfb_sb_remark() {
		return ezfb_sb_remark;
	}
	public void setEzfb_sb_remark(String ezfb_sb_remark) {
		this.ezfb_sb_remark = ezfb_sb_remark;
	}
	public String getEzfb_house_remark() {
		return ezfb_house_remark;
	}
	public void setEzfb_house_remark(String ezfb_house_remark) {
		this.ezfb_house_remark = ezfb_house_remark;
	}
	public String getEzfb_hbstartBMS() {
		return ezfb_hbstartBMS;
	}
	public void setEzfb_hbstartBMS(String ezfb_hbstartBMS) {
		this.ezfb_hbstartBMS = ezfb_hbstartBMS;
	}
	public String getEzfb_hbstopBMS() {
		return ezfb_hbstopBMS;
	}
	public void setEzfb_hbstopBMS(String ezfb_hbstopBMS) {
		this.ezfb_hbstopBMS = ezfb_hbstopBMS;
	}
	public String getEzfb_ylstartBMS() {
		return ezfb_ylstartBMS;
	}
	public void setEzfb_ylstartBMS(String ezfb_ylstartBMS) {
		this.ezfb_ylstartBMS = ezfb_ylstartBMS;
	}
	public String getEzfb_ylstopBMS() {
		return ezfb_ylstopBMS;
	}
	public void setEzfb_ylstopBMS(String ezfb_ylstopBMS) {
		this.ezfb_ylstopBMS = ezfb_ylstopBMS;
	}
	public String getEzfb_housestartBMS() {
		return ezfb_housestartBMS;
	}
	public void setEzfb_housestartBMS(String ezfb_housestartBMS) {
		this.ezfb_housestartBMS = ezfb_housestartBMS;
	}
	public String getEzfb_housestopBMS() {
		return ezfb_housestopBMS;
	}
	public void setEzfb_housestopBMS(String ezfb_housestopBMS) {
		this.ezfb_housestopBMS = ezfb_housestopBMS;
	}
	public String getEzfb_compactstartBMS() {
		return ezfb_compactstartBMS;
	}
	public void setEzfb_compactstartBMS(String ezfb_compactstartBMS) {
		this.ezfb_compactstartBMS = ezfb_compactstartBMS;
	}
	public String getEzfb_compactstopBMS() {
		return ezfb_compactstopBMS;
	}
	public void setEzfb_compactstopBMS(String ezfb_compactstopBMS) {
		this.ezfb_compactstopBMS = ezfb_compactstopBMS;
	}
	public String getEzfb_feestartBMS() {
		return ezfb_feestartBMS;
	}
	public void setEzfb_feestartBMS(String ezfb_feestartBMS) {
		this.ezfb_feestartBMS = ezfb_feestartBMS;
	}
	public String getEzfb_feestopBMS() {
		return ezfb_feestopBMS;
	}
	public void setEzfb_feestopBMS(String ezfb_feestopBMS) {
		this.ezfb_feestopBMS = ezfb_feestopBMS;
	}
	public String getEzfb_filestartBMS() {
		return ezfb_filestartBMS;
	}
	public void setEzfb_filestartBMS(String ezfb_filestartBMS) {
		this.ezfb_filestartBMS = ezfb_filestartBMS;
	}
	public String getEzfb_filestopBMS() {
		return ezfb_filestopBMS;
	}
	public void setEzfb_filestopBMS(String ezfb_filestopBMS) {
		this.ezfb_filestopBMS = ezfb_filestopBMS;
	}
	public String getEzfb_elsestartBMS() {
		return ezfb_elsestartBMS;
	}
	public void setEzfb_elsestartBMS(String ezfb_elsestartBMS) {
		this.ezfb_elsestartBMS = ezfb_elsestartBMS;
	}
	public String getEzfb_elsestopBMS() {
		return ezfb_elsestopBMS;
	}
	public void setEzfb_elsestopBMS(String ezfb_elsestopBMS) {
		this.ezfb_elsestopBMS = ezfb_elsestopBMS;
	}
	public String getEzfb_ylradixBMS() {
		return ezfb_ylradixBMS;
	}
	public void setEzfb_ylradixBMS(String ezfb_ylradixBMS) {
		this.ezfb_ylradixBMS = ezfb_ylradixBMS;
	}
	public String getEzfb_jlradixBMS() {
		return ezfb_jlradixBMS;
	}
	public void setEzfb_jlradixBMS(String ezfb_jlradixBMS) {
		this.ezfb_jlradixBMS = ezfb_jlradixBMS;
	}
	public String getEzfb_houseradixBMS() {
		return ezfb_houseradixBMS;
	}
	public void setEzfb_houseradixBMS(String ezfb_houseradixBMS) {
		this.ezfb_houseradixBMS = ezfb_houseradixBMS;
	}



	public String getEzfb_yl_failed() {
		return ezfb_yl_failed;
	}



	public void setEzfb_yl_failed(String ezfb_yl_failed) {
		this.ezfb_yl_failed = ezfb_yl_failed;
	}



	public String getEzfb_jl_failed() {
		return ezfb_jl_failed;
	}



	public void setEzfb_jl_failed(String ezfb_jl_failed) {
		this.ezfb_jl_failed = ezfb_jl_failed;
	}



	public String getEzfb_house_failed() {
		return ezfb_house_failed;
	}



	public void setEzfb_house_failed(String ezfb_house_failed) {
		this.ezfb_house_failed = ezfb_house_failed;
	}



	public String getEzfb_wtremark() {
		return ezfb_wtremark;
	}



	public void setEzfb_wtremark(String ezfb_wtremark) {
		this.ezfb_wtremark = ezfb_wtremark;
	}



	public String getEzfb_qz_fee() {
		return ezfb_qz_fee;
	}



	public void setEzfb_qz_fee(String ezfb_qz_fee) {
		this.ezfb_qz_fee = ezfb_qz_fee;
	}



	public String getEzfb_qz_housecp() {
		return ezfb_qz_housecp;
	}



	public void setEzfb_qz_housecp(String ezfb_qz_housecp) {
		this.ezfb_qz_housecp = ezfb_qz_housecp;
	}



	public String getEzfb_qz_houseop() {
		return ezfb_qz_houseop;
	}



	public void setEzfb_qz_houseop(String ezfb_qz_houseop) {
		this.ezfb_qz_houseop = ezfb_qz_houseop;
	}



	public String getEzfb_qz_sbcp() {
		return ezfb_qz_sbcp;
	}



	public void setEzfb_qz_sbcp(String ezfb_qz_sbcp) {
		this.ezfb_qz_sbcp = ezfb_qz_sbcp;
	}



	public String getEzfb_qz_sbop() {
		return ezfb_qz_sbop;
	}



	public void setEzfb_qz_sbop(String ezfb_qz_sbop) {
		this.ezfb_qz_sbop = ezfb_qz_sbop;
	}



	public String getEzfb_qz_file() {
		return ezfb_qz_file;
	}



	public void setEzfb_qz_file(String ezfb_qz_file) {
		this.ezfb_qz_file = ezfb_qz_file;
	}



	public String getEzfb_qz_product() {
		return ezfb_qz_product;
	}



	public void setEzfb_qz_product(String ezfb_qz_product) {
		this.ezfb_qz_product = ezfb_qz_product;
	}



	public String getEzfb_wt_agency() {
		return ezfb_wt_agency;
	}



	public void setEzfb_wt_agency(String ezfb_wt_agency) {
		this.ezfb_wt_agency = ezfb_wt_agency;
	}
	
	
}
