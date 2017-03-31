package Model;

import java.math.BigDecimal;
import java.text.NumberFormat;

import Util.DateStringChange;

public class EmZYTModel {
	private BigDecimal zero = new BigDecimal(0);
	private int id;
	private int ownmonth;
	private int gid;
	private int cid;
	private String emzt_zytid;
	private String emzt_zgid;
	private String emzt_zcid;
	private String emzt_class;
	private int emzt_state;
	private int emzt_ifsame;
	private String emzt_uptime;
	private String emzt_scompany;
	private String emzt_sname;
	private String emzt_scity;
	private String emzt_rname;
	private String emzt_rcity;
	private String emzt_rcompany;
	private String emzt_company;
	private String emzt_client;
	private String emzt_name;
	private String emzt_idcard;
	private String emzt_mobile;
	private BigDecimal emzt_yltotal = zero;
	private BigDecimal emzt_syetotal = zero;
	private BigDecimal emzt_gstotal = zero;
	private BigDecimal emzt_syutotal = zero;
	private BigDecimal emzt_jltotal = zero;
	private BigDecimal emzt_housetotal = zero;
	private BigDecimal emzt_zhtotal = zero;
	private BigDecimal emzt_bjtotal = zero;
	private BigDecimal emzt_sbtotal = zero;
	private BigDecimal emzt_elsefee = zero;
	private String emzt_sbchange;
	private String emzt_serverid;
	private String emzt_servername;
	private String emzt_serverfee;
	private BigDecimal emzt_servertotal = zero;
	private String emzt_serverchange;
	private BigDecimal emzt_fee = zero;
	private BigDecimal emzt_filefee = zero;
	private BigDecimal emzt_total = zero;
	private BigDecimal emzt_sbbj = zero;
	private BigDecimal emzt_housebj = zero;
	private BigDecimal emzt_elsebj = zero;
	private String emzt_remark;
	private String emzt_ifinure;
	private String emzt_addtime;
	private String emzt_addname;
	private String emzt_ifconfirm;
	private String emzt_confirmtime;
	private String emzt_confirmname;
	private String emzt_city;
	private String emzt_sbstand;
	private String emzt_sbstanename;
	private BigDecimal emzt_ylcp = zero;
	private BigDecimal emzt_ylop = zero;
	private BigDecimal emzt_jlcp = zero;
	private BigDecimal emzt_jlop = zero;
	private BigDecimal emzt_gscp = zero;
	private BigDecimal emzt_gsop = zero;
	private BigDecimal emzt_syecp = zero;
	private BigDecimal emzt_syeop = zero;
	private BigDecimal emzt_syucp = zero;
	private BigDecimal emzt_syuop = zero;
	private BigDecimal emzt_housecp = zero;
	private BigDecimal emzt_houseop = zero;
	private BigDecimal emzt_zhcp = zero;
	private BigDecimal emzt_zhop = zero;
	private String emzt_ylradix;
	private String emzt_syeradix;
	private String emzt_gsradix;
	private String emzt_syuradix;
	private String emzt_jlradix;
	private String emzt_houseradix;
	private String emzt_zhradix;
	private String emzt_bjradix;
	private BigDecimal emzt_flfee = zero;
	private String emzt_flcontent;
	private String emzt_filename;
	private int emzt_f_confirm;
	private String emzt_f_confirmtime;
	private String emzt_f_confirmname;
	private String emzt_ylcpp;
	private String emzt_ylopp;
	private String emzt_jlcpp;
	private String emzt_jlopp;
	private String emzt_gscpp;
	private String emzt_gsopp;
	private String emzt_syecpp;
	private String emzt_syeopp;
	private String emzt_syucpp;
	private String emzt_syuopp;
	private String emzt_housecpp;
	private String emzt_houseopp;
	private String emzt_sbsingle;
	private String emzt_housesingle;
	private String emzt_ylstart;
	private String emzt_ylstartBMS;
	private String emzt_ylstop;
	private String emzt_ylstopBMS;
	private String emzt_jlstart;
	private String emzt_jlstartBMS;
	private String emzt_jlstop;
	private String emzt_jlstopBMS;
	private String emzt_gsstart;
	private String emzt_gsstop;
	private String emzt_syestart;
	private String emzt_syestop;
	private String emzt_syustart;
	private String emzt_syustop;
	private String emzt_housestart;
	private String emzt_housestop;
	private String emzt_housestartBMS;
	private String emzt_housestopBMS;
	private String emzt_sbtitle;
	private String emzt_housetitle;
	private String emzt_compactstart;
	private String emzt_compactstop;
	private String emzt_outdate;
	private String emzt_flag;
	private String emzt_email;
	private String emzt_spell;
	private String emzt_r_record;
	private String emzt_declaretime;
	private String emzt_declarename;
	private String emzt_phone;
	private String emzt_idcardclass;
	private String emzt_bchousestart;
	private String emzt_bchousestop;
	private String emzt_bchouseradix;
	private String emzt_flstart;
	private String emzt_flstop;
	private String emzt_flfeeinfo;
	private String emzt_elsefeestart;
	private String emzt_elsefeestop;
	private String emzt_feestart;
	private String emzt_feestop;
	private String emzt_filefeestart;
	private String emzt_filefeestop;
	private BigDecimal emzt_managefee = zero;
	private String emzt_cityremark;
	private String emzt_agreement;
	private String emzt_iffile;
	private String emzt_rdate;
	private String emzt_ifsingle;
	private String emzt_ifunlimited;
	private int emzt_outstate;
	private String emzt_ylradixBMS;
	private String emzt_jlradixBMS;
	private String emzt_houseradixBMS;
	private String emzt_od_name;
	private String emzt_od_time;
	private String emzt_oc_name;
	private String emzt_oc_time;
	private String emzt_filesingle;
	private String emzt_rsingle;
	private String emzt_outfilename;
	private String emzt_indate;
	private String emzt_t_name;
	private String emzt_t_idcard;
	private String emzt_hjadd;
	private String emzt_education;
	private String emzt_folk;
	private String emzt_hand;
	private String emzt_ifshebao;
	private String emzt_computerid;
	private String emzt_ifsbcard;
	private String emzt_ifhouse;
	private String emzt_houseid;
	private String emzt_marital;
	private String emzt_m_name;
	private String emzt_m_idcard;
	private String emzt_fileplace;
	private String emzt_ofileplace;
	private String emzt_ifda;
	private String emzt_ifowed;
	private int emzt_fileendmonth;
	private String emzt_ifrc;
	private String emzt_iffileservice;
	private String emzt_iffilechange;
	private String emzt_nifc_reason;
	private String emzt_ifhouseseal;
	private int emzt_contactstate;
	private int emzt_datastate;
	private String emzt_contacttype;
	private int emzt_yl_outstate;
	private String emzt_ylod_name;
	private String emzt_ylod_time;
	private String emzt_yloc_name;
	private String emzt_yloc_time;
	private int emzt_jl_outstate;
	private String emzt_jlod_name;
	private String emzt_jlod_time;
	private String emzt_jloc_name;
	private String emzt_jloc_time;
	private int emzt_house_outstate;
	private String emzt_houseod_name;
	private String emzt_houseod_time;
	private String emzt_houseoc_name;
	private String emzt_houseoc_time;
	private String emzt_sbc_notice;
	private String emzt_data_notice;
	private String emzt_wtgid;
	private String emzt_title;
	private String emzt_adtype;
	private String emzt_tel;
	private String emzt_wtcid;
	private String emzt_outreason;
	private String emzt_sex;
	private String emzt_sb_state;
	private String emzt_house_state;
	private String emzt_iffeefile;
	private String state;
	private String outstate;
	private int emzt_tapr_id;
	private String contactstate;
	private String datastate;
	private int emba_id;
	private String state_color;
	private String outstate_color;
	private String house_cpp;
	private String house_opp;
	private BigDecimal emfi_total = zero;
	private BigDecimal balance = zero;
	private String emzt_err;
	private int emzt_chkstate;
	private String chkstate;

	private boolean emzt_ischecked;

	public EmZYTModel() {
		super();
	}

	public EmZYTModel(BigDecimal zero, int id, int ownmonth, int gid, int cid,
			String emzt_zytid, String emzt_zgid, String emzt_zcid,
			String emzt_class, int emzt_state, int emzt_ifsame,
			String emzt_uptime, String emzt_scompany, String emzt_sname,
			String emzt_scity, String emzt_rname, String emzt_rcity,
			String emzt_rcompany, String emzt_company, String emzt_client,
			String emzt_name, String emzt_idcard, String emzt_mobile,
			BigDecimal emzt_yltotal, BigDecimal emzt_syetotal,
			BigDecimal emzt_gstotal, BigDecimal emzt_syutotal,
			BigDecimal emzt_jltotal, BigDecimal emzt_housetotal,
			BigDecimal emzt_zhtotal, BigDecimal emzt_bjtotal,
			BigDecimal emzt_sbtotal, BigDecimal emzt_elsefee,
			String emzt_sbchange, String emzt_serverid, String emzt_servername,
			String emzt_serverfee, BigDecimal emzt_servertotal,
			String emzt_serverchange, BigDecimal emzt_fee,
			BigDecimal emzt_filefee, BigDecimal emzt_total,
			BigDecimal emzt_sbbj, BigDecimal emzt_housebj,
			BigDecimal emzt_elsebj, String emzt_remark, String emzt_ifinure,
			String emzt_addtime, String emzt_addname, String emzt_ifconfirm,
			String emzt_confirmtime, String emzt_confirmname, String emzt_city,
			String emzt_sbstand, String emzt_sbstanename, BigDecimal emzt_ylcp,
			BigDecimal emzt_ylop, BigDecimal emzt_jlcp, BigDecimal emzt_jlop,
			BigDecimal emzt_gscp, BigDecimal emzt_gsop, BigDecimal emzt_syecp,
			BigDecimal emzt_syeop, BigDecimal emzt_syucp,
			BigDecimal emzt_syuop, BigDecimal emzt_housecp,
			BigDecimal emzt_houseop, BigDecimal emzt_zhcp,
			BigDecimal emzt_zhop, String emzt_ylradix, String emzt_syeradix,
			String emzt_gsradix, String emzt_syuradix, String emzt_jlradix,
			String emzt_houseradix, String emzt_zhradix, String emzt_bjradix,
			BigDecimal emzt_flfee, String emzt_flcontent, String emzt_filename,
			int emzt_f_confirm, String emzt_f_confirmtime,
			String emzt_f_confirmname, String emzt_ylcpp, String emzt_ylopp,
			String emzt_jlcpp, String emzt_jlopp, String emzt_gscpp,
			String emzt_gsopp, String emzt_syecpp, String emzt_syeopp,
			String emzt_syucpp, String emzt_syuopp, String emzt_housecpp,
			String emzt_houseopp, String emzt_sbsingle,
			String emzt_housesingle, String emzt_ylstart,
			String emzt_ylstartBMS, String emzt_ylstop, String emzt_ylstopBMS,
			String emzt_jlstart, String emzt_jlstartBMS, String emzt_jlstop,
			String emzt_jlstopBMS, String emzt_gsstart, String emzt_gsstop,
			String emzt_syestart, String emzt_syestop, String emzt_syustart,
			String emzt_syustop, String emzt_housestart, String emzt_housestop,
			String emzt_housestartBMS, String emzt_housestopBMS,
			String emzt_sbtitle, String emzt_housetitle,
			String emzt_compactstart, String emzt_compactstop,
			String emzt_outdate, String emzt_flag, String emzt_email,
			String emzt_spell, String emzt_r_record, String emzt_declaretime,
			String emzt_declarename, String emzt_phone,
			String emzt_idcardclass, String emzt_bchousestart,
			String emzt_bchousestop, String emzt_bchouseradix,
			String emzt_flstart, String emzt_flstop, String emzt_flfeeinfo,
			String emzt_elsefeestart, String emzt_elsefeestop,
			String emzt_feestart, String emzt_feestop,
			String emzt_filefeestart, String emzt_filefeestop,
			BigDecimal emzt_managefee, String emzt_cityremark,
			String emzt_agreement, String emzt_iffile, String emzt_rdate,
			String emzt_ifsingle, String emzt_ifunlimited, int emzt_outstate,
			String emzt_ylradixBMS, String emzt_jlradixBMS,
			String emzt_houseradixBMS, String emzt_od_name,
			String emzt_od_time, String emzt_oc_name, String emzt_oc_time,
			String emzt_filesingle, String emzt_rsingle,
			String emzt_outfilename, String emzt_indate, String emzt_t_name,
			String emzt_t_idcard, String emzt_hjadd, String emzt_education,
			String emzt_folk, String emzt_hand, String emzt_ifshebao,
			String emzt_computerid, String emzt_ifsbcard, String emzt_ifhouse,
			String emzt_houseid, String emzt_marital, String emzt_m_name,
			String emzt_m_idcard, String emzt_fileplace,
			String emzt_ofileplace, String emzt_ifda, String emzt_ifowed,
			int emzt_fileendmonth, String emzt_ifrc, String emzt_iffileservice,
			String emzt_iffilechange, String emzt_nifc_reason,
			String emzt_ifhouseseal, int emzt_contactstate, int emzt_datastate,
			String emzt_contacttype, int emzt_yl_outstate,
			String emzt_ylod_name, String emzt_ylod_time,
			String emzt_yloc_name, String emzt_yloc_time, int emzt_jl_outstate,
			String emzt_jlod_name, String emzt_jlod_time,
			String emzt_jloc_name, String emzt_jloc_time,
			int emzt_house_outstate, String emzt_houseod_name,
			String emzt_houseod_time, String emzt_houseoc_name,
			String emzt_houseoc_time, String emzt_sbc_notice,
			String emzt_data_notice, String emzt_wtgid, String emzt_title,
			String emzt_adtype, String emzt_tel, String emzt_wtcid,
			String emzt_outreason, String emzt_sex, String emzt_sb_state,
			String emzt_house_state, String emzt_iffeefile, String state,
			String outstate, int emzt_tapr_id, String contactstate,
			String datastate, int emba_id) {
		super();
		this.zero = zero;
		this.id = id;
		this.ownmonth = ownmonth;
		this.gid = gid;
		this.cid = cid;
		this.emzt_zytid = emzt_zytid;
		this.emzt_zgid = emzt_zgid;
		this.emzt_zcid = emzt_zcid;
		this.emzt_class = emzt_class;
		this.emzt_state = emzt_state;
		this.emzt_ifsame = emzt_ifsame;
		this.emzt_uptime = emzt_uptime;
		this.emzt_scompany = emzt_scompany;
		this.emzt_sname = emzt_sname;
		this.emzt_scity = emzt_scity;
		this.emzt_rname = emzt_rname;
		this.emzt_rcity = emzt_rcity;
		this.emzt_rcompany = emzt_rcompany;
		this.emzt_company = emzt_company;
		this.emzt_client = emzt_client;
		this.emzt_name = emzt_name;
		this.emzt_idcard = emzt_idcard;
		this.emzt_mobile = emzt_mobile;
		this.emzt_yltotal = emzt_yltotal;
		this.emzt_syetotal = emzt_syetotal;
		this.emzt_gstotal = emzt_gstotal;
		this.emzt_syutotal = emzt_syutotal;
		this.emzt_jltotal = emzt_jltotal;
		this.emzt_housetotal = emzt_housetotal;
		this.emzt_zhtotal = emzt_zhtotal;
		this.emzt_bjtotal = emzt_bjtotal;
		this.emzt_sbtotal = emzt_sbtotal;
		this.emzt_elsefee = emzt_elsefee;
		this.emzt_sbchange = emzt_sbchange;
		this.emzt_serverid = emzt_serverid;
		this.emzt_servername = emzt_servername;
		this.emzt_serverfee = emzt_serverfee;
		this.emzt_servertotal = emzt_servertotal;
		this.emzt_serverchange = emzt_serverchange;
		this.emzt_fee = emzt_fee;
		this.emzt_filefee = emzt_filefee;
		this.emzt_total = emzt_total;
		this.emzt_sbbj = emzt_sbbj;
		this.emzt_housebj = emzt_housebj;
		this.emzt_elsebj = emzt_elsebj;
		this.emzt_remark = emzt_remark;
		this.emzt_ifinure = emzt_ifinure;
		this.emzt_addtime = emzt_addtime;
		this.emzt_addname = emzt_addname;
		this.emzt_ifconfirm = emzt_ifconfirm;
		this.emzt_confirmtime = emzt_confirmtime;
		this.emzt_confirmname = emzt_confirmname;
		this.emzt_city = emzt_city;
		this.emzt_sbstand = emzt_sbstand;
		this.emzt_sbstanename = emzt_sbstanename;
		this.emzt_ylcp = emzt_ylcp;
		this.emzt_ylop = emzt_ylop;
		this.emzt_jlcp = emzt_jlcp;
		this.emzt_jlop = emzt_jlop;
		this.emzt_gscp = emzt_gscp;
		this.emzt_gsop = emzt_gsop;
		this.emzt_syecp = emzt_syecp;
		this.emzt_syeop = emzt_syeop;
		this.emzt_syucp = emzt_syucp;
		this.emzt_syuop = emzt_syuop;
		this.emzt_housecp = emzt_housecp;
		this.emzt_houseop = emzt_houseop;
		this.emzt_zhcp = emzt_zhcp;
		this.emzt_zhop = emzt_zhop;
		this.emzt_ylradix = emzt_ylradix;
		this.emzt_syeradix = emzt_syeradix;
		this.emzt_gsradix = emzt_gsradix;
		this.emzt_syuradix = emzt_syuradix;
		this.emzt_jlradix = emzt_jlradix;
		this.emzt_houseradix = emzt_houseradix;
		this.emzt_zhradix = emzt_zhradix;
		this.emzt_bjradix = emzt_bjradix;
		this.emzt_flfee = emzt_flfee;
		this.emzt_flcontent = emzt_flcontent;
		this.emzt_filename = emzt_filename;
		this.emzt_f_confirm = emzt_f_confirm;
		this.emzt_f_confirmtime = emzt_f_confirmtime;
		this.emzt_f_confirmname = emzt_f_confirmname;
		this.emzt_ylcpp = emzt_ylcpp;
		this.emzt_ylopp = emzt_ylopp;
		this.emzt_jlcpp = emzt_jlcpp;
		this.emzt_jlopp = emzt_jlopp;
		this.emzt_gscpp = emzt_gscpp;
		this.emzt_gsopp = emzt_gsopp;
		this.emzt_syecpp = emzt_syecpp;
		this.emzt_syeopp = emzt_syeopp;
		this.emzt_syucpp = emzt_syucpp;
		this.emzt_syuopp = emzt_syuopp;
		this.emzt_housecpp = emzt_housecpp;
		this.emzt_houseopp = emzt_houseopp;
		this.emzt_sbsingle = emzt_sbsingle;
		this.emzt_housesingle = emzt_housesingle;
		this.emzt_ylstart = emzt_ylstart;
		this.emzt_ylstartBMS = emzt_ylstartBMS;
		this.emzt_ylstop = emzt_ylstop;
		this.emzt_ylstopBMS = emzt_ylstopBMS;
		this.emzt_jlstart = emzt_jlstart;
		this.emzt_jlstartBMS = emzt_jlstartBMS;
		this.emzt_jlstop = emzt_jlstop;
		this.emzt_jlstopBMS = emzt_jlstopBMS;
		this.emzt_gsstart = emzt_gsstart;
		this.emzt_gsstop = emzt_gsstop;
		this.emzt_syestart = emzt_syestart;
		this.emzt_syestop = emzt_syestop;
		this.emzt_syustart = emzt_syustart;
		this.emzt_syustop = emzt_syustop;
		this.emzt_housestart = emzt_housestart;
		this.emzt_housestop = emzt_housestop;
		this.emzt_housestartBMS = emzt_housestartBMS;
		this.emzt_housestopBMS = emzt_housestopBMS;
		this.emzt_sbtitle = emzt_sbtitle;
		this.emzt_housetitle = emzt_housetitle;
		this.emzt_compactstart = emzt_compactstart;
		this.emzt_compactstop = emzt_compactstop;
		this.emzt_outdate = emzt_outdate;
		this.emzt_flag = emzt_flag;
		this.emzt_email = emzt_email;
		this.emzt_spell = emzt_spell;
		this.emzt_r_record = emzt_r_record;
		this.emzt_declaretime = emzt_declaretime;
		this.emzt_declarename = emzt_declarename;
		this.emzt_phone = emzt_phone;
		this.emzt_idcardclass = emzt_idcardclass;
		this.emzt_bchousestart = emzt_bchousestart;
		this.emzt_bchousestop = emzt_bchousestop;
		this.emzt_bchouseradix = emzt_bchouseradix;
		this.emzt_flstart = emzt_flstart;
		this.emzt_flstop = emzt_flstop;
		this.emzt_flfeeinfo = emzt_flfeeinfo;
		this.emzt_elsefeestart = emzt_elsefeestart;
		this.emzt_elsefeestop = emzt_elsefeestop;
		this.emzt_feestart = emzt_feestart;
		this.emzt_feestop = emzt_feestop;
		this.emzt_filefeestart = emzt_filefeestart;
		this.emzt_filefeestop = emzt_filefeestop;
		this.emzt_managefee = emzt_managefee;
		this.emzt_cityremark = emzt_cityremark;
		this.emzt_agreement = emzt_agreement;
		this.emzt_iffile = emzt_iffile;
		this.emzt_rdate = emzt_rdate;
		this.emzt_ifsingle = emzt_ifsingle;
		this.emzt_ifunlimited = emzt_ifunlimited;
		this.emzt_outstate = emzt_outstate;
		this.emzt_ylradixBMS = emzt_ylradixBMS;
		this.emzt_jlradixBMS = emzt_jlradixBMS;
		this.emzt_houseradixBMS = emzt_houseradixBMS;
		this.emzt_od_name = emzt_od_name;
		this.emzt_od_time = emzt_od_time;
		this.emzt_oc_name = emzt_oc_name;
		this.emzt_oc_time = emzt_oc_time;
		this.emzt_filesingle = emzt_filesingle;
		this.emzt_rsingle = emzt_rsingle;
		this.emzt_outfilename = emzt_outfilename;
		this.emzt_indate = emzt_indate;
		this.emzt_t_name = emzt_t_name;
		this.emzt_t_idcard = emzt_t_idcard;
		this.emzt_hjadd = emzt_hjadd;
		this.emzt_education = emzt_education;
		this.emzt_folk = emzt_folk;
		this.emzt_hand = emzt_hand;
		this.emzt_ifshebao = emzt_ifshebao;
		this.emzt_computerid = emzt_computerid;
		this.emzt_ifsbcard = emzt_ifsbcard;
		this.emzt_ifhouse = emzt_ifhouse;
		this.emzt_houseid = emzt_houseid;
		this.emzt_marital = emzt_marital;
		this.emzt_m_name = emzt_m_name;
		this.emzt_m_idcard = emzt_m_idcard;
		this.emzt_fileplace = emzt_fileplace;
		this.emzt_ofileplace = emzt_ofileplace;
		this.emzt_ifda = emzt_ifda;
		this.emzt_ifowed = emzt_ifowed;
		this.emzt_fileendmonth = emzt_fileendmonth;
		this.emzt_ifrc = emzt_ifrc;
		this.emzt_iffileservice = emzt_iffileservice;
		this.emzt_iffilechange = emzt_iffilechange;
		this.emzt_nifc_reason = emzt_nifc_reason;
		this.emzt_ifhouseseal = emzt_ifhouseseal;
		this.emzt_contactstate = emzt_contactstate;
		this.emzt_datastate = emzt_datastate;
		this.emzt_contacttype = emzt_contacttype;
		this.emzt_yl_outstate = emzt_yl_outstate;
		this.emzt_ylod_name = emzt_ylod_name;
		this.emzt_ylod_time = emzt_ylod_time;
		this.emzt_yloc_name = emzt_yloc_name;
		this.emzt_yloc_time = emzt_yloc_time;
		this.emzt_jl_outstate = emzt_jl_outstate;
		this.emzt_jlod_name = emzt_jlod_name;
		this.emzt_jlod_time = emzt_jlod_time;
		this.emzt_jloc_name = emzt_jloc_name;
		this.emzt_jloc_time = emzt_jloc_time;
		this.emzt_house_outstate = emzt_house_outstate;
		this.emzt_houseod_name = emzt_houseod_name;
		this.emzt_houseod_time = emzt_houseod_time;
		this.emzt_houseoc_name = emzt_houseoc_name;
		this.emzt_houseoc_time = emzt_houseoc_time;
		this.emzt_sbc_notice = emzt_sbc_notice;
		this.emzt_data_notice = emzt_data_notice;
		this.emzt_wtgid = emzt_wtgid;
		this.emzt_title = emzt_title;
		this.emzt_adtype = emzt_adtype;
		this.emzt_tel = emzt_tel;
		this.emzt_wtcid = emzt_wtcid;
		this.emzt_outreason = emzt_outreason;
		this.emzt_sex = emzt_sex;
		this.emzt_sb_state = emzt_sb_state;
		this.emzt_house_state = emzt_house_state;
		this.emzt_iffeefile = emzt_iffeefile;
		this.state = state;
		this.outstate = outstate;
		this.emzt_tapr_id = emzt_tapr_id;
		this.contactstate = contactstate;
		this.datastate = datastate;
		this.emba_id = emba_id;
	}

	public int getEmba_id() {
		return emba_id;
	}

	public void setEmba_id(int emba_id) {
		this.emba_id = emba_id;
	}

	public String getContactstate() {
		return contactstate;
	}

	public void setContactstate(String contactstate) {
		this.contactstate = contactstate;
	}

	public String getDatastate() {
		return datastate;
	}

	public void setDatastate(String datastate) {
		this.datastate = datastate;
	}

	public int getEmzt_tapr_id() {
		return emzt_tapr_id;
	}

	public void setEmzt_tapr_id(int emzt_tapr_id) {
		this.emzt_tapr_id = emzt_tapr_id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getOutstate() {
		return outstate;
	}

	public void setOutstate(String outstate) {
		this.outstate = outstate;
	}

	public BigDecimal getZero() {
		return zero;
	}

	public void setZero(BigDecimal zero) {
		this.zero = zero;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(int ownmonth) {
		this.ownmonth = ownmonth;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getEmzt_zytid() {
		return emzt_zytid;
	}

	public void setEmzt_zytid(String emzt_zytid) {
		this.emzt_zytid = emzt_zytid;
	}

	public String getEmzt_zgid() {
		return emzt_zgid;
	}

	public void setEmzt_zgid(String emzt_zgid) {
		this.emzt_zgid = emzt_zgid;
	}

	public String getEmzt_zcid() {
		return emzt_zcid;
	}

	public void setEmzt_zcid(String emzt_zcid) {
		this.emzt_zcid = emzt_zcid;
	}

	public String getEmzt_class() {
		return emzt_class;
	}

	public void setEmzt_class(String emzt_class) {
		this.emzt_class = emzt_class;
	}

	public int getEmzt_state() {
		return emzt_state;
	}

	public void setEmzt_state(int emzt_state) {
		this.emzt_state = emzt_state;
	}

	public int getEmzt_ifsame() {
		return emzt_ifsame;
	}

	public void setEmzt_ifsame(int emzt_ifsame) {
		this.emzt_ifsame = emzt_ifsame;
	}

	public String getEmzt_uptime() {
		return emzt_uptime;
	}

	public void setEmzt_uptime(String emzt_uptime) {
		try {
			if (emzt_uptime.length() > 8) {
				this.emzt_uptime = DateStringChange.DatetoSting(
						DateStringChange
								.StringtoDate(emzt_uptime, "yyyy-mm-dd"),
						"yyyy-mm-dd");
			} else {
				this.emzt_uptime = emzt_uptime;
			}
		} catch (Exception e) {
			this.emzt_uptime = "";
		}
	}

	public String getEmzt_scompany() {
		return emzt_scompany;
	}

	public void setEmzt_scompany(String emzt_scompany) {
		this.emzt_scompany = emzt_scompany;
	}

	public String getEmzt_sname() {
		return emzt_sname;
	}

	public void setEmzt_sname(String emzt_sname) {
		this.emzt_sname = emzt_sname;
	}

	public String getEmzt_scity() {
		return emzt_scity;
	}

	public void setEmzt_scity(String emzt_scity) {
		this.emzt_scity = emzt_scity;
	}

	public String getEmzt_rname() {
		return emzt_rname;
	}

	public void setEmzt_rname(String emzt_rname) {
		this.emzt_rname = emzt_rname;
	}

	public String getEmzt_rcity() {
		return emzt_rcity;
	}

	public void setEmzt_rcity(String emzt_rcity) {
		this.emzt_rcity = emzt_rcity;
	}

	public String getEmzt_rcompany() {
		return emzt_rcompany;
	}

	public void setEmzt_rcompany(String emzt_rcompany) {
		this.emzt_rcompany = emzt_rcompany;
	}

	public String getEmzt_company() {
		return emzt_company;
	}

	public void setEmzt_company(String emzt_company) {
		this.emzt_company = emzt_company;
	}

	public String getEmzt_client() {
		return emzt_client;
	}

	public void setEmzt_client(String emzt_client) {
		this.emzt_client = emzt_client;
	}

	public String getEmzt_name() {
		return emzt_name;
	}

	public void setEmzt_name(String emzt_name) {
		this.emzt_name = emzt_name;
	}

	public String getEmzt_idcard() {
		return emzt_idcard;
	}

	public void setEmzt_idcard(String emzt_idcard) {
		this.emzt_idcard = emzt_idcard;
	}

	public String getEmzt_mobile() {
		return emzt_mobile;
	}

	public void setEmzt_mobile(String emzt_mobile) {
		this.emzt_mobile = emzt_mobile;
	}

	public BigDecimal getEmzt_yltotal() {
		return emzt_yltotal;
	}

	public void setEmzt_yltotal(BigDecimal emzt_yltotal) {
		this.emzt_yltotal = emzt_yltotal == null ? null : emzt_yltotal
				.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getEmzt_syetotal() {
		return emzt_syetotal;
	}

	public void setEmzt_syetotal(BigDecimal emzt_syetotal) {
		this.emzt_syetotal = emzt_syetotal == null ? null : emzt_syetotal
				.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getEmzt_gstotal() {
		return emzt_gstotal;
	}

	public void setEmzt_gstotal(BigDecimal emzt_gstotal) {
		this.emzt_gstotal = emzt_gstotal == null ? null : emzt_gstotal
				.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getEmzt_syutotal() {
		return emzt_syutotal;
	}

	public void setEmzt_syutotal(BigDecimal emzt_syutotal) {
		this.emzt_syutotal = emzt_syutotal == null ? null : emzt_syutotal
				.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getEmzt_jltotal() {
		return emzt_jltotal;
	}

	public void setEmzt_jltotal(BigDecimal emzt_jltotal) {
		this.emzt_jltotal = emzt_jltotal == null ? null : emzt_jltotal
				.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getEmzt_housetotal() {
		return emzt_housetotal;
	}

	public void setEmzt_housetotal(BigDecimal emzt_housetotal) {
		this.emzt_housetotal = emzt_housetotal == null ? null : emzt_housetotal
				.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getEmzt_zhtotal() {
		return emzt_zhtotal;
	}

	public void setEmzt_zhtotal(BigDecimal emzt_zhtotal) {
		this.emzt_zhtotal = emzt_zhtotal == null ? null : emzt_zhtotal
				.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getEmzt_bjtotal() {
		return emzt_bjtotal;
	}

	public void setEmzt_bjtotal(BigDecimal emzt_bjtotal) {
		this.emzt_bjtotal = emzt_bjtotal == null ? null : emzt_bjtotal
				.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getEmzt_sbtotal() {
		return emzt_sbtotal;
	}

	public void setEmzt_sbtotal(BigDecimal emzt_sbtotal) {
		this.emzt_sbtotal = emzt_sbtotal == null ? null : emzt_sbtotal
				.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getEmzt_elsefee() {
		return emzt_elsefee;
	}

	public void setEmzt_elsefee(BigDecimal emzt_elsefee) {
		this.emzt_elsefee = emzt_elsefee == null ? null : emzt_elsefee
				.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public String getEmzt_sbchange() {
		return emzt_sbchange;
	}

	public void setEmzt_sbchange(String emzt_sbchange) {
		this.emzt_sbchange = emzt_sbchange;
	}

	public String getEmzt_serverid() {
		return emzt_serverid;
	}

	public void setEmzt_serverid(String emzt_serverid) {
		this.emzt_serverid = emzt_serverid;
	}

	public String getEmzt_servername() {
		return emzt_servername;
	}

	public void setEmzt_servername(String emzt_servername) {
		this.emzt_servername = emzt_servername;
	}

	public String getEmzt_serverfee() {
		return emzt_serverfee;
	}

	public void setEmzt_serverfee(String emzt_serverfee) {
		this.emzt_serverfee = emzt_serverfee;
	}

	public BigDecimal getEmzt_servertotal() {
		return emzt_servertotal;
	}

	public void setEmzt_servertotal(BigDecimal emzt_servertotal) {
		this.emzt_servertotal = emzt_servertotal == null ? null
				: emzt_servertotal.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public String getEmzt_serverchange() {
		return emzt_serverchange;
	}

	public void setEmzt_serverchange(String emzt_serverchange) {
		this.emzt_serverchange = emzt_serverchange;
	}

	public BigDecimal getEmzt_fee() {
		return emzt_fee;
	}

	public void setEmzt_fee(BigDecimal emzt_fee) {
		this.emzt_fee = emzt_fee == null ? null : emzt_fee.setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getEmzt_filefee() {
		return emzt_filefee;
	}

	public void setEmzt_filefee(BigDecimal emzt_filefee) {
		this.emzt_filefee = emzt_filefee == null ? null : emzt_filefee
				.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getEmzt_total() {
		return emzt_total;
	}

	public void setEmzt_total(BigDecimal emzt_total) {
		this.emzt_total = emzt_total == null ? null : emzt_total.setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getEmzt_sbbj() {
		return emzt_sbbj;
	}

	public void setEmzt_sbbj(BigDecimal emzt_sbbj) {
		this.emzt_sbbj = emzt_sbbj == null ? null : emzt_sbbj.setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getEmzt_housebj() {
		return emzt_housebj;
	}

	public void setEmzt_housebj(BigDecimal emzt_housebj) {
		this.emzt_housebj = emzt_housebj == null ? null : emzt_housebj
				.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getEmzt_elsebj() {
		return emzt_elsebj;
	}

	public void setEmzt_elsebj(BigDecimal emzt_elsebj) {
		this.emzt_elsebj = emzt_elsebj == null ? null : emzt_elsebj.setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	public String getEmzt_remark() {
		return emzt_remark;
	}

	public void setEmzt_remark(String emzt_remark) {
		this.emzt_remark = emzt_remark;
	}

	public String getEmzt_ifinure() {
		return emzt_ifinure;
	}

	public void setEmzt_ifinure(String emzt_ifinure) {
		this.emzt_ifinure = emzt_ifinure;
	}

	public String getEmzt_addtime() {
		return emzt_addtime;
	}

	public void setEmzt_addtime(String emzt_addtime) {
		this.emzt_addtime = emzt_addtime;
	}

	public String getEmzt_addname() {
		return emzt_addname;
	}

	public void setEmzt_addname(String emzt_addname) {
		this.emzt_addname = emzt_addname;
	}

	public String getEmzt_ifconfirm() {
		return emzt_ifconfirm;
	}

	public void setEmzt_ifconfirm(String emzt_ifconfirm) {
		this.emzt_ifconfirm = emzt_ifconfirm;
	}

	public String getEmzt_confirmtime() {
		return emzt_confirmtime;
	}

	public void setEmzt_confirmtime(String emzt_confirmtime) {
		this.emzt_confirmtime = emzt_confirmtime;
	}

	public String getEmzt_confirmname() {
		return emzt_confirmname;
	}

	public void setEmzt_confirmname(String emzt_confirmname) {
		this.emzt_confirmname = emzt_confirmname;
	}

	public String getEmzt_city() {
		return emzt_city;
	}

	public void setEmzt_city(String emzt_city) {
		this.emzt_city = emzt_city;
	}

	public String getEmzt_sbstand() {
		return emzt_sbstand;
	}

	public void setEmzt_sbstand(String emzt_sbstand) {
		this.emzt_sbstand = emzt_sbstand;
	}

	public String getEmzt_sbstanename() {
		return emzt_sbstanename;
	}

	public void setEmzt_sbstanename(String emzt_sbstanename) {
		this.emzt_sbstanename = emzt_sbstanename;
	}

	public BigDecimal getEmzt_ylcp() {
		return emzt_ylcp;
	}

	public void setEmzt_ylcp(BigDecimal emzt_ylcp) {
		this.emzt_ylcp = emzt_ylcp == null ? null : emzt_ylcp.setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getEmzt_ylop() {
		return emzt_ylop;
	}

	public void setEmzt_ylop(BigDecimal emzt_ylop) {
		this.emzt_ylop = emzt_ylop == null ? null : emzt_ylop.setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getEmzt_jlcp() {
		return emzt_jlcp;
	}

	public void setEmzt_jlcp(BigDecimal emzt_jlcp) {
		this.emzt_jlcp = emzt_jlcp == null ? null : emzt_jlcp.setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getEmzt_jlop() {
		return emzt_jlop;
	}

	public void setEmzt_jlop(BigDecimal emzt_jlop) {
		this.emzt_jlop = emzt_jlop == null ? null : emzt_jlop.setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getEmzt_gscp() {
		return emzt_gscp;
	}

	public void setEmzt_gscp(BigDecimal emzt_gscp) {
		this.emzt_gscp = emzt_gscp == null ? null : emzt_gscp.setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getEmzt_gsop() {
		return emzt_gsop;
	}

	public void setEmzt_gsop(BigDecimal emzt_gsop) {
		this.emzt_gsop = emzt_gsop == null ? null : emzt_gsop.setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getEmzt_syecp() {
		return emzt_syecp;
	}

	public void setEmzt_syecp(BigDecimal emzt_syecp) {
		this.emzt_syecp = emzt_syecp == null ? null : emzt_syecp.setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getEmzt_syeop() {
		return emzt_syeop;
	}

	public void setEmzt_syeop(BigDecimal emzt_syeop) {
		this.emzt_syeop = emzt_syeop == null ? null : emzt_syeop.setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getEmzt_syucp() {
		return emzt_syucp;
	}

	public void setEmzt_syucp(BigDecimal emzt_syucp) {
		this.emzt_syucp = emzt_syucp == null ? null : emzt_syucp.setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getEmzt_syuop() {
		return emzt_syuop;
	}

	public void setEmzt_syuop(BigDecimal emzt_syuop) {
		this.emzt_syuop = emzt_syuop == null ? null : emzt_syuop.setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getEmzt_housecp() {
		return emzt_housecp;
	}

	public void setEmzt_housecp(BigDecimal emzt_housecp) {
		this.emzt_housecp = emzt_housecp == null ? null : emzt_housecp
				.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getEmzt_houseop() {
		return emzt_houseop;
	}

	public void setEmzt_houseop(BigDecimal emzt_houseop) {
		this.emzt_houseop = emzt_houseop == null ? null : emzt_houseop
				.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getEmzt_zhcp() {
		return emzt_zhcp;
	}

	public void setEmzt_zhcp(BigDecimal emzt_zhcp) {
		this.emzt_zhcp = emzt_zhcp == null ? null : emzt_zhcp.setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getEmzt_zhop() {
		return emzt_zhop;
	}

	public void setEmzt_zhop(BigDecimal emzt_zhop) {
		this.emzt_zhop = emzt_zhop == null ? null : emzt_zhop.setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	public String getEmzt_ylradix() {
		return emzt_ylradix;
	}

	public void setEmzt_ylradix(String emzt_ylradix) {
		this.emzt_ylradix = emzt_ylradix;
	}

	public String getEmzt_syeradix() {
		return emzt_syeradix;
	}

	public void setEmzt_syeradix(String emzt_syeradix) {
		this.emzt_syeradix = emzt_syeradix;
	}

	public String getEmzt_gsradix() {
		return emzt_gsradix;
	}

	public void setEmzt_gsradix(String emzt_gsradix) {
		this.emzt_gsradix = emzt_gsradix;
	}

	public String getEmzt_syuradix() {
		return emzt_syuradix;
	}

	public void setEmzt_syuradix(String emzt_syuradix) {
		this.emzt_syuradix = emzt_syuradix;
	}

	public String getEmzt_jlradix() {
		return emzt_jlradix;
	}

	public void setEmzt_jlradix(String emzt_jlradix) {
		this.emzt_jlradix = emzt_jlradix;
	}

	public String getEmzt_houseradix() {
		return emzt_houseradix;
	}

	public void setEmzt_houseradix(String emzt_houseradix) {
		this.emzt_houseradix = emzt_houseradix;
	}

	public String getEmzt_zhradix() {
		return emzt_zhradix;
	}

	public void setEmzt_zhradix(String emzt_zhradix) {
		this.emzt_zhradix = emzt_zhradix;
	}

	public String getEmzt_bjradix() {
		return emzt_bjradix;
	}

	public void setEmzt_bjradix(String emzt_bjradix) {
		this.emzt_bjradix = emzt_bjradix;
	}

	public BigDecimal getEmzt_flfee() {
		return emzt_flfee;
	}

	public void setEmzt_flfee(BigDecimal emzt_flfee) {
		this.emzt_flfee = emzt_flfee == null ? null : emzt_flfee.setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	public String getEmzt_flcontent() {
		return emzt_flcontent;
	}

	public void setEmzt_flcontent(String emzt_flcontent) {
		this.emzt_flcontent = emzt_flcontent;
	}

	public String getEmzt_filename() {
		return emzt_filename;
	}

	public void setEmzt_filename(String emzt_filename) {
		this.emzt_filename = emzt_filename;
	}

	public int getEmzt_f_confirm() {
		return emzt_f_confirm;
	}

	public void setEmzt_f_confirm(int emzt_f_confirm) {
		this.emzt_f_confirm = emzt_f_confirm;
	}

	public String getEmzt_f_confirmtime() {
		return emzt_f_confirmtime;
	}

	public void setEmzt_f_confirmtime(String emzt_f_confirmtime) {
		this.emzt_f_confirmtime = emzt_f_confirmtime;
	}

	public String getEmzt_f_confirmname() {
		return emzt_f_confirmname;
	}

	public void setEmzt_f_confirmname(String emzt_f_confirmname) {
		this.emzt_f_confirmname = emzt_f_confirmname;
	}

	public String getEmzt_ylcpp() {
		return emzt_ylcpp;
	}

	public void setEmzt_ylcpp(String emzt_ylcpp) {
		this.emzt_ylcpp = changePercent(emzt_ylcpp);
	}

	public String getEmzt_ylopp() {
		return emzt_ylopp;
	}

	public void setEmzt_ylopp(String emzt_ylopp) {
		this.emzt_ylopp = changePercent(emzt_ylopp);
	}

	public String getEmzt_jlcpp() {
		return emzt_jlcpp;
	}

	public void setEmzt_jlcpp(String emzt_jlcpp) {
		this.emzt_jlcpp = changePercent(emzt_jlcpp);
	}

	public String getEmzt_jlopp() {
		return emzt_jlopp;
	}

	public void setEmzt_jlopp(String emzt_jlopp) {
		this.emzt_jlopp = changePercent(emzt_jlopp);
	}

	public String getEmzt_gscpp() {
		return emzt_gscpp;
	}

	public void setEmzt_gscpp(String emzt_gscpp) {
		this.emzt_gscpp = changePercent(emzt_gscpp);
	}

	public String getEmzt_gsopp() {
		return emzt_gsopp;
	}

	public void setEmzt_gsopp(String emzt_gsopp) {
		this.emzt_gsopp = changePercent(emzt_gsopp);
	}

	public String getEmzt_syecpp() {
		return emzt_syecpp;
	}

	public void setEmzt_syecpp(String emzt_syecpp) {
		this.emzt_syecpp = changePercent(emzt_syecpp);
	}

	public String getEmzt_syeopp() {
		return emzt_syeopp;
	}

	public void setEmzt_syeopp(String emzt_syeopp) {
		this.emzt_syeopp = changePercent(emzt_syeopp);
	}

	public String getEmzt_syucpp() {
		return emzt_syucpp;
	}

	public void setEmzt_syucpp(String emzt_syucpp) {
		this.emzt_syucpp = changePercent(emzt_syucpp);
	}

	public String getEmzt_syuopp() {
		return emzt_syuopp;
	}

	public void setEmzt_syuopp(String emzt_syuopp) {
		this.emzt_syuopp = changePercent(emzt_syuopp);
	}

	public String getEmzt_housecpp() {
		return emzt_housecpp;
	}

	public void setEmzt_housecpp(String emzt_housecpp) {
		this.emzt_housecpp = changePercent(emzt_housecpp);
	}

	public String getEmzt_houseopp() {
		return emzt_houseopp;
	}

	public void setEmzt_houseopp(String emzt_houseopp) {
		this.emzt_houseopp = changePercent(emzt_houseopp);
	}

	public String getEmzt_sbsingle() {
		return emzt_sbsingle;
	}

	public void setEmzt_sbsingle(String emzt_sbsingle) {
		this.emzt_sbsingle = emzt_sbsingle;
	}

	public String getEmzt_housesingle() {
		return emzt_housesingle;
	}

	public void setEmzt_housesingle(String emzt_housesingle) {
		this.emzt_housesingle = emzt_housesingle;
	}

	public String getEmzt_ylstart() {
		return emzt_ylstart;
	}

	public void setEmzt_ylstart(String emzt_ylstart) {
		try {
			if (emzt_ylstart.length() > 8) {
				this.emzt_ylstart = DateStringChange.DatetoSting(
						DateStringChange.StringtoDate(emzt_ylstart,
								"yyyy-mm-dd"), "yyyy-mm-dd");
			} else {
				this.emzt_ylstart = emzt_ylstart;
			}

		} catch (Exception e) {
			this.emzt_ylstart = "";
		}
	}

	public String getEmzt_ylstartBMS() {
		return emzt_ylstartBMS;
	}

	public void setEmzt_ylstartBMS(String emzt_ylstartBMS) {
		this.emzt_ylstartBMS = emzt_ylstartBMS;
	}

	public String getEmzt_ylstop() {
		return emzt_ylstop;
	}

	public void setEmzt_ylstop(String emzt_ylstop) {
		this.emzt_ylstop = emzt_ylstop;
	}

	public String getEmzt_ylstopBMS() {
		return emzt_ylstopBMS;
	}

	public void setEmzt_ylstopBMS(String emzt_ylstopBMS) {
		this.emzt_ylstopBMS = emzt_ylstopBMS;
	}

	public String getEmzt_jlstart() {
		return emzt_jlstart;
	}

	public void setEmzt_jlstart(String emzt_jlstart) {

		try {
			if (emzt_jlstart.length() > 8) {
				this.emzt_jlstart = DateStringChange.DatetoSting(
						DateStringChange.StringtoDate(emzt_jlstart,
								"yyyy-mm-dd"), "yyyy-mm-dd");
			} else {
				this.emzt_jlstart = emzt_jlstart;
			}

		} catch (Exception e) {
			this.emzt_jlstart = "";
		}
	}

	public String getEmzt_jlstartBMS() {
		return emzt_jlstartBMS;
	}

	public void setEmzt_jlstartBMS(String emzt_jlstartBMS) {
		this.emzt_jlstartBMS = emzt_jlstartBMS;
	}

	public String getEmzt_jlstop() {
		return emzt_jlstop;
	}

	public void setEmzt_jlstop(String emzt_jlstop) {
		this.emzt_jlstop = emzt_jlstop;
	}

	public String getEmzt_jlstopBMS() {
		return emzt_jlstopBMS;
	}

	public void setEmzt_jlstopBMS(String emzt_jlstopBMS) {
		this.emzt_jlstopBMS = emzt_jlstopBMS;
	}

	public String getEmzt_gsstart() {
		return emzt_gsstart;
	}

	public void setEmzt_gsstart(String emzt_gsstart) {

		try {
			if (emzt_gsstart.length() > 8) {
				this.emzt_gsstart = DateStringChange.DatetoSting(
						DateStringChange.StringtoDate(emzt_gsstart,
								"yyyy-mm-dd"), "yyyy-mm-dd");
			} else {
				this.emzt_gsstart = emzt_gsstart;
			}

		} catch (Exception e) {
			this.emzt_gsstart = "";
		}
	}

	public String getEmzt_gsstop() {
		return emzt_gsstop;
	}

	public void setEmzt_gsstop(String emzt_gsstop) {
		this.emzt_gsstop = emzt_gsstop;
	}

	public String getEmzt_syestart() {
		return emzt_syestart;
	}

	public void setEmzt_syestart(String emzt_syestart) {

		try {
			if (emzt_syestart.length() > 8) {
				this.emzt_syestart = DateStringChange.DatetoSting(
						DateStringChange.StringtoDate(emzt_syestart,
								"yyyy-mm-dd"), "yyyy-mm-dd");
			} else {
				this.emzt_syestart = emzt_syestart;
			}

		} catch (Exception e) {
			this.emzt_syestart = "";
		}
	}

	public String getEmzt_syestop() {
		return emzt_syestop;
	}

	public void setEmzt_syestop(String emzt_syestop) {
		this.emzt_syestop = emzt_syestop;
	}

	public String getEmzt_syustart() {
		return emzt_syustart;
	}

	public void setEmzt_syustart(String emzt_syustart) {

		try {
			if (emzt_syustart.length() > 8) {
				this.emzt_syustart = DateStringChange.DatetoSting(
						DateStringChange.StringtoDate(emzt_syustart,
								"yyyy-mm-dd"), "yyyy-mm-dd");
			} else {
				this.emzt_syustart = emzt_syustart;
			}

		} catch (Exception e) {
			this.emzt_syustart = "";
		}
	}

	public String getEmzt_syustop() {
		return emzt_syustop;
	}

	public void setEmzt_syustop(String emzt_syustop) {
		this.emzt_syustop = emzt_syustop;
	}

	public String getEmzt_housestart() {
		return emzt_housestart;
	}

	public void setEmzt_housestart(String emzt_housestart) {

		try {
			if (emzt_housestart.length() > 8) {
				this.emzt_housestart = DateStringChange.DatetoSting(
						DateStringChange.StringtoDate(emzt_housestart,
								"yyyy-mm-dd"), "yyyy-mm-dd");
			} else {
				this.emzt_housestart = emzt_housestart;
			}

		} catch (Exception e) {
			this.emzt_housestart = "";
		}
	}

	public String getEmzt_housestop() {
		return emzt_housestop;
	}

	public void setEmzt_housestop(String emzt_housestop) {
		this.emzt_housestop = emzt_housestop;
	}

	public String getEmzt_housestartBMS() {
		return emzt_housestartBMS;
	}

	public void setEmzt_housestartBMS(String emzt_housestartBMS) {
		this.emzt_housestartBMS = emzt_housestartBMS;
	}

	public String getEmzt_housestopBMS() {
		return emzt_housestopBMS;
	}

	public void setEmzt_housestopBMS(String emzt_housestopBMS) {
		this.emzt_housestopBMS = emzt_housestopBMS;
	}

	public String getEmzt_sbtitle() {
		return emzt_sbtitle;
	}

	public void setEmzt_sbtitle(String emzt_sbtitle) {
		this.emzt_sbtitle = emzt_sbtitle;
	}

	public String getEmzt_housetitle() {
		return emzt_housetitle;
	}

	public void setEmzt_housetitle(String emzt_housetitle) {
		this.emzt_housetitle = emzt_housetitle;
	}

	public String getEmzt_compactstart() {
		return emzt_compactstart;
	}

	public void setEmzt_compactstart(String emzt_compactstart) {
		try {
			if (emzt_compactstart.length() > 8) {
				this.emzt_compactstart = DateStringChange.DatetoSting(
						DateStringChange.StringtoDate(emzt_compactstart,
								"yyyy-mm-dd"), "yyyy-mm-dd");
			} else {
				this.emzt_compactstart = emzt_compactstart;
			}

		} catch (Exception e) {
			this.emzt_compactstart = "";
		}
	}

	public String getEmzt_compactstop() {
		return emzt_compactstop;
	}

	public void setEmzt_compactstop(String emzt_compactstop) {
		try {
			if (emzt_compactstop.length() > 8) {
				this.emzt_compactstop = DateStringChange.DatetoSting(
						DateStringChange.StringtoDate(emzt_compactstop,
								"yyyy-mm-dd"), "yyyy-mm-dd");
			} else {
				this.emzt_compactstop = emzt_compactstop;
			}

		} catch (Exception e) {
			this.emzt_compactstop = "";
		}
	}

	public String getEmzt_outdate() {
		return emzt_outdate;
	}

	public void setEmzt_outdate(String emzt_outdate) {
		try {
			if (emzt_outdate.length() > 8) {
				this.emzt_outdate = DateStringChange.DatetoSting(
						DateStringChange.StringtoDate(emzt_outdate,
								"yyyy-mm-dd"), "yyyy-mm-dd");
			} else {
				this.emzt_outdate = emzt_outdate;
			}

		} catch (Exception e) {
			this.emzt_outdate = "";
		}
	}

	public String getEmzt_flag() {
		return emzt_flag;
	}

	public void setEmzt_flag(String emzt_flag) {
		this.emzt_flag = emzt_flag;
	}

	public String getEmzt_email() {
		return emzt_email;
	}

	public void setEmzt_email(String emzt_email) {
		this.emzt_email = emzt_email;
	}

	public String getEmzt_spell() {
		return emzt_spell;
	}

	public void setEmzt_spell(String emzt_spell) {
		this.emzt_spell = emzt_spell;
	}

	public String getEmzt_r_record() {
		return emzt_r_record;
	}

	public void setEmzt_r_record(String emzt_r_record) {
		this.emzt_r_record = emzt_r_record;
	}

	public String getEmzt_declaretime() {
		return emzt_declaretime;
	}

	public void setEmzt_declaretime(String emzt_declaretime) {
		this.emzt_declaretime = emzt_declaretime;
	}

	public String getEmzt_declarename() {
		return emzt_declarename;
	}

	public void setEmzt_declarename(String emzt_declarename) {
		this.emzt_declarename = emzt_declarename;
	}

	public String getEmzt_phone() {
		return emzt_phone;
	}

	public void setEmzt_phone(String emzt_phone) {
		this.emzt_phone = emzt_phone;
	}

	public String getEmzt_idcardclass() {
		return emzt_idcardclass;
	}

	public void setEmzt_idcardclass(String emzt_idcardclass) {
		this.emzt_idcardclass = emzt_idcardclass;
	}

	public String getEmzt_bchousestart() {
		return emzt_bchousestart;
	}

	public void setEmzt_bchousestart(String emzt_bchousestart) {
		this.emzt_bchousestart = emzt_bchousestart;
	}

	public String getEmzt_bchousestop() {
		return emzt_bchousestop;
	}

	public void setEmzt_bchousestop(String emzt_bchousestop) {
		this.emzt_bchousestop = emzt_bchousestop;
	}

	public String getEmzt_bchouseradix() {
		return emzt_bchouseradix;
	}

	public void setEmzt_bchouseradix(String emzt_bchouseradix) {
		this.emzt_bchouseradix = emzt_bchouseradix;
	}

	public String getEmzt_flstart() {
		return emzt_flstart;
	}

	public void setEmzt_flstart(String emzt_flstart) {
		this.emzt_flstart = emzt_flstart;
	}

	public String getEmzt_flstop() {
		return emzt_flstop;
	}

	public void setEmzt_flstop(String emzt_flstop) {
		this.emzt_flstop = emzt_flstop;
	}

	public String getEmzt_flfeeinfo() {
		return emzt_flfeeinfo;
	}

	public void setEmzt_flfeeinfo(String emzt_flfeeinfo) {
		this.emzt_flfeeinfo = emzt_flfeeinfo;
	}

	public String getEmzt_elsefeestart() {
		return emzt_elsefeestart;
	}

	public void setEmzt_elsefeestart(String emzt_elsefeestart) {
		this.emzt_elsefeestart = emzt_elsefeestart;
	}

	public String getEmzt_elsefeestop() {
		return emzt_elsefeestop;
	}

	public void setEmzt_elsefeestop(String emzt_elsefeestop) {
		this.emzt_elsefeestop = emzt_elsefeestop;
	}

	public String getEmzt_feestart() {
		return emzt_feestart;
	}

	public void setEmzt_feestart(String emzt_feestart) {
		this.emzt_feestart = emzt_feestart;
	}

	public String getEmzt_feestop() {
		return emzt_feestop;
	}

	public void setEmzt_feestop(String emzt_feestop) {
		this.emzt_feestop = emzt_feestop;
	}

	public String getEmzt_filefeestart() {
		return emzt_filefeestart;
	}

	public void setEmzt_filefeestart(String emzt_filefeestart) {
		this.emzt_filefeestart = emzt_filefeestart;
	}

	public String getEmzt_filefeestop() {
		return emzt_filefeestop;
	}

	public void setEmzt_filefeestop(String emzt_filefeestop) {
		this.emzt_filefeestop = emzt_filefeestop;
	}

	public BigDecimal getEmzt_managefee() {
		return emzt_managefee;
	}

	public void setEmzt_managefee(BigDecimal emzt_managefee) {
		this.emzt_managefee = emzt_managefee;
	}

	public String getEmzt_cityremark() {
		return emzt_cityremark;
	}

	public void setEmzt_cityremark(String emzt_cityremark) {
		this.emzt_cityremark = emzt_cityremark;
	}

	public String getEmzt_agreement() {
		return emzt_agreement;
	}

	public void setEmzt_agreement(String emzt_agreement) {
		this.emzt_agreement = emzt_agreement;
	}

	public String getEmzt_iffile() {
		return emzt_iffile;
	}

	public void setEmzt_iffile(String emzt_iffile) {
		this.emzt_iffile = emzt_iffile;
	}

	public String getEmzt_rdate() {
		return emzt_rdate;
	}

	public void setEmzt_rdate(String emzt_rdate) {
		this.emzt_rdate = emzt_rdate;
	}

	public String getEmzt_ifsingle() {
		return emzt_ifsingle;
	}

	public void setEmzt_ifsingle(String emzt_ifsingle) {
		this.emzt_ifsingle = emzt_ifsingle;
	}

	public String getEmzt_ifunlimited() {
		return emzt_ifunlimited;
	}

	public void setEmzt_ifunlimited(String emzt_ifunlimited) {
		this.emzt_ifunlimited = emzt_ifunlimited;
	}

	public int getEmzt_outstate() {
		return emzt_outstate;
	}

	public void setEmzt_outstate(int emzt_outstate) {
		this.emzt_outstate = emzt_outstate;
	}

	public String getEmzt_ylradixBMS() {
		return emzt_ylradixBMS;
	}

	public void setEmzt_ylradixBMS(String emzt_ylradixBMS) {
		this.emzt_ylradixBMS = emzt_ylradixBMS;
	}

	public String getEmzt_jlradixBMS() {
		return emzt_jlradixBMS;
	}

	public void setEmzt_jlradixBMS(String emzt_jlradixBMS) {
		this.emzt_jlradixBMS = emzt_jlradixBMS;
	}

	public String getEmzt_houseradixBMS() {
		return emzt_houseradixBMS;
	}

	public void setEmzt_houseradixBMS(String emzt_houseradixBMS) {
		this.emzt_houseradixBMS = emzt_houseradixBMS;
	}

	public String getEmzt_od_name() {
		return emzt_od_name;
	}

	public void setEmzt_od_name(String emzt_od_name) {
		this.emzt_od_name = emzt_od_name;
	}

	public String getEmzt_od_time() {
		return emzt_od_time;
	}

	public void setEmzt_od_time(String emzt_od_time) {
		this.emzt_od_time = emzt_od_time;
	}

	public String getEmzt_oc_name() {
		return emzt_oc_name;
	}

	public void setEmzt_oc_name(String emzt_oc_name) {
		this.emzt_oc_name = emzt_oc_name;
	}

	public String getEmzt_oc_time() {
		return emzt_oc_time;
	}

	public void setEmzt_oc_time(String emzt_oc_time) {
		this.emzt_oc_time = emzt_oc_time;
	}

	public String getEmzt_filesingle() {
		return emzt_filesingle;
	}

	public void setEmzt_filesingle(String emzt_filesingle) {
		this.emzt_filesingle = emzt_filesingle;
	}

	public String getEmzt_rsingle() {
		return emzt_rsingle;
	}

	public void setEmzt_rsingle(String emzt_rsingle) {
		this.emzt_rsingle = emzt_rsingle;
	}

	public String getEmzt_outfilename() {
		return emzt_outfilename;
	}

	public void setEmzt_outfilename(String emzt_outfilename) {
		this.emzt_outfilename = emzt_outfilename;
	}

	public String getEmzt_indate() {
		return emzt_indate;
	}

	public void setEmzt_indate(String emzt_indate) {
		this.emzt_indate = emzt_indate;
	}

	public String getEmzt_t_name() {
		return emzt_t_name;
	}

	public void setEmzt_t_name(String emzt_t_name) {
		this.emzt_t_name = emzt_t_name;
	}

	public String getEmzt_t_idcard() {
		return emzt_t_idcard;
	}

	public void setEmzt_t_idcard(String emzt_t_idcard) {
		this.emzt_t_idcard = emzt_t_idcard;
	}

	public String getEmzt_hjadd() {
		return emzt_hjadd;
	}

	public void setEmzt_hjadd(String emzt_hjadd) {
		this.emzt_hjadd = emzt_hjadd;
	}

	public String getEmzt_education() {
		return emzt_education;
	}

	public void setEmzt_education(String emzt_education) {
		this.emzt_education = emzt_education;
	}

	public String getEmzt_folk() {
		return emzt_folk;
	}

	public void setEmzt_folk(String emzt_folk) {
		this.emzt_folk = emzt_folk;
	}

	public String getEmzt_hand() {
		return emzt_hand;
	}

	public void setEmzt_hand(String emzt_hand) {
		this.emzt_hand = emzt_hand;
	}

	public String getEmzt_ifshebao() {
		return emzt_ifshebao;
	}

	public void setEmzt_ifshebao(String emzt_ifshebao) {
		this.emzt_ifshebao = emzt_ifshebao;
	}

	public String getEmzt_computerid() {
		return emzt_computerid;
	}

	public void setEmzt_computerid(String emzt_computerid) {
		this.emzt_computerid = emzt_computerid;
	}

	public String getEmzt_ifsbcard() {
		return emzt_ifsbcard;
	}

	public void setEmzt_ifsbcard(String emzt_ifsbcard) {
		this.emzt_ifsbcard = emzt_ifsbcard;
	}

	public String getEmzt_ifhouse() {
		return emzt_ifhouse;
	}

	public void setEmzt_ifhouse(String emzt_ifhouse) {
		this.emzt_ifhouse = emzt_ifhouse;
	}

	public String getEmzt_houseid() {
		return emzt_houseid;
	}

	public void setEmzt_houseid(String emzt_houseid) {
		this.emzt_houseid = emzt_houseid;
	}

	public String getEmzt_marital() {
		return emzt_marital;
	}

	public void setEmzt_marital(String emzt_marital) {
		this.emzt_marital = emzt_marital;
	}

	public String getEmzt_m_name() {
		return emzt_m_name;
	}

	public void setEmzt_m_name(String emzt_m_name) {
		this.emzt_m_name = emzt_m_name;
	}

	public String getEmzt_m_idcard() {
		return emzt_m_idcard;
	}

	public void setEmzt_m_idcard(String emzt_m_idcard) {
		this.emzt_m_idcard = emzt_m_idcard;
	}

	public String getEmzt_fileplace() {
		return emzt_fileplace;
	}

	public void setEmzt_fileplace(String emzt_fileplace) {
		this.emzt_fileplace = emzt_fileplace;
	}

	public String getEmzt_ofileplace() {
		return emzt_ofileplace;
	}

	public void setEmzt_ofileplace(String emzt_ofileplace) {
		this.emzt_ofileplace = emzt_ofileplace;
	}

	public String getEmzt_ifda() {
		return emzt_ifda;
	}

	public void setEmzt_ifda(String emzt_ifda) {
		this.emzt_ifda = emzt_ifda;
	}

	public String getEmzt_ifowed() {
		return emzt_ifowed;
	}

	public void setEmzt_ifowed(String emzt_ifowed) {
		this.emzt_ifowed = emzt_ifowed;
	}

	public int getEmzt_fileendmonth() {
		return emzt_fileendmonth;
	}

	public void setEmzt_fileendmonth(int emzt_fileendmonth) {
		this.emzt_fileendmonth = emzt_fileendmonth;
	}

	public String getEmzt_ifrc() {
		return emzt_ifrc;
	}

	public void setEmzt_ifrc(String emzt_ifrc) {
		this.emzt_ifrc = emzt_ifrc;
	}

	public String getEmzt_iffileservice() {
		return emzt_iffileservice;
	}

	public void setEmzt_iffileservice(String emzt_iffileservice) {
		this.emzt_iffileservice = emzt_iffileservice;
	}

	public String getEmzt_iffilechange() {
		return emzt_iffilechange;
	}

	public void setEmzt_iffilechange(String emzt_iffilechange) {
		this.emzt_iffilechange = emzt_iffilechange;
	}

	public String getEmzt_nifc_reason() {
		return emzt_nifc_reason;
	}

	public void setEmzt_nifc_reason(String emzt_nifc_reason) {
		this.emzt_nifc_reason = emzt_nifc_reason;
	}

	public String getEmzt_ifhouseseal() {
		return emzt_ifhouseseal;
	}

	public void setEmzt_ifhouseseal(String emzt_ifhouseseal) {
		this.emzt_ifhouseseal = emzt_ifhouseseal;
	}

	public int getEmzt_contactstate() {
		return emzt_contactstate;
	}

	public void setEmzt_contactstate(int emzt_contactstate) {
		this.emzt_contactstate = emzt_contactstate;
	}

	public int getEmzt_datastate() {
		return emzt_datastate;
	}

	public void setEmzt_datastate(int emzt_datastate) {
		this.emzt_datastate = emzt_datastate;
	}

	public String getEmzt_contacttype() {
		return emzt_contacttype;
	}

	public void setEmzt_contacttype(String emzt_contacttype) {
		this.emzt_contacttype = emzt_contacttype;
	}

	public int getEmzt_yl_outstate() {
		return emzt_yl_outstate;
	}

	public void setEmzt_yl_outstate(int emzt_yl_outstate) {
		this.emzt_yl_outstate = emzt_yl_outstate;
	}

	public String getEmzt_ylod_name() {
		return emzt_ylod_name;
	}

	public void setEmzt_ylod_name(String emzt_ylod_name) {
		this.emzt_ylod_name = emzt_ylod_name;
	}

	public String getEmzt_ylod_time() {
		return emzt_ylod_time;
	}

	public void setEmzt_ylod_time(String emzt_ylod_time) {
		this.emzt_ylod_time = emzt_ylod_time;
	}

	public String getEmzt_yloc_name() {
		return emzt_yloc_name;
	}

	public void setEmzt_yloc_name(String emzt_yloc_name) {
		this.emzt_yloc_name = emzt_yloc_name;
	}

	public String getEmzt_yloc_time() {
		return emzt_yloc_time;
	}

	public void setEmzt_yloc_time(String emzt_yloc_time) {
		this.emzt_yloc_time = emzt_yloc_time;
	}

	public int getEmzt_jl_outstate() {
		return emzt_jl_outstate;
	}

	public void setEmzt_jl_outstate(int emzt_jl_outstate) {
		this.emzt_jl_outstate = emzt_jl_outstate;
	}

	public String getEmzt_jlod_name() {
		return emzt_jlod_name;
	}

	public void setEmzt_jlod_name(String emzt_jlod_name) {
		this.emzt_jlod_name = emzt_jlod_name;
	}

	public String getEmzt_jlod_time() {
		return emzt_jlod_time;
	}

	public void setEmzt_jlod_time(String emzt_jlod_time) {
		this.emzt_jlod_time = emzt_jlod_time;
	}

	public String getEmzt_jloc_name() {
		return emzt_jloc_name;
	}

	public void setEmzt_jloc_name(String emzt_jloc_name) {
		this.emzt_jloc_name = emzt_jloc_name;
	}

	public String getEmzt_jloc_time() {
		return emzt_jloc_time;
	}

	public void setEmzt_jloc_time(String emzt_jloc_time) {
		this.emzt_jloc_time = emzt_jloc_time;
	}

	public int getEmzt_house_outstate() {
		return emzt_house_outstate;
	}

	public void setEmzt_house_outstate(int emzt_house_outstate) {
		this.emzt_house_outstate = emzt_house_outstate;
	}

	public String getEmzt_houseod_name() {
		return emzt_houseod_name;
	}

	public void setEmzt_houseod_name(String emzt_houseod_name) {
		this.emzt_houseod_name = emzt_houseod_name;
	}

	public String getEmzt_houseod_time() {
		return emzt_houseod_time;
	}

	public void setEmzt_houseod_time(String emzt_houseod_time) {
		this.emzt_houseod_time = emzt_houseod_time;
	}

	public String getEmzt_houseoc_name() {
		return emzt_houseoc_name;
	}

	public void setEmzt_houseoc_name(String emzt_houseoc_name) {
		this.emzt_houseoc_name = emzt_houseoc_name;
	}

	public String getEmzt_houseoc_time() {
		return emzt_houseoc_time;
	}

	public void setEmzt_houseoc_time(String emzt_houseoc_time) {
		this.emzt_houseoc_time = emzt_houseoc_time;
	}

	public String getEmzt_sbc_notice() {
		return emzt_sbc_notice;
	}

	public void setEmzt_sbc_notice(String emzt_sbc_notice) {
		this.emzt_sbc_notice = emzt_sbc_notice;
	}

	public String getEmzt_data_notice() {
		return emzt_data_notice;
	}

	public void setEmzt_data_notice(String emzt_data_notice) {
		this.emzt_data_notice = emzt_data_notice;
	}

	public String getEmzt_wtgid() {
		return emzt_wtgid;
	}

	public void setEmzt_wtgid(String emzt_wtgid) {
		this.emzt_wtgid = emzt_wtgid;
	}

	public String getEmzt_title() {
		return emzt_title;
	}

	public void setEmzt_title(String emzt_title) {
		this.emzt_title = emzt_title;
	}

	public String getEmzt_adtype() {
		return emzt_adtype;
	}

	public void setEmzt_adtype(String emzt_adtype) {
		this.emzt_adtype = emzt_adtype;
	}

	public String getEmzt_tel() {
		return emzt_tel;
	}

	public void setEmzt_tel(String emzt_tel) {
		this.emzt_tel = emzt_tel;
	}

	public String getEmzt_wtcid() {
		return emzt_wtcid;
	}

	public void setEmzt_wtcid(String emzt_wtcid) {
		this.emzt_wtcid = emzt_wtcid;
	}

	public String getEmzt_outreason() {
		return emzt_outreason;
	}

	public void setEmzt_outreason(String emzt_outreason) {
		this.emzt_outreason = emzt_outreason;
	}

	public String getEmzt_sex() {
		return emzt_sex;
	}

	public void setEmzt_sex(String emzt_sex) {
		this.emzt_sex = emzt_sex;
	}

	public String getEmzt_sb_state() {
		return emzt_sb_state;
	}

	public void setEmzt_sb_state(String emzt_sb_state) {
		this.emzt_sb_state = emzt_sb_state;
	}

	public String getEmzt_house_state() {
		return emzt_house_state;
	}

	public void setEmzt_house_state(String emzt_house_state) {
		this.emzt_house_state = emzt_house_state;
	}

	public String getEmzt_iffeefile() {
		return emzt_iffeefile;
	}

	public void setEmzt_iffeefile(String emzt_iffeefile) {
		this.emzt_iffeefile = emzt_iffeefile;
	}

	// 小数转百分比
	public String changePercent(String num) {
		String percent;

		try {
			NumberFormat nt = NumberFormat.getPercentInstance();
			nt.setMinimumFractionDigits(2);
			percent = nt.format(Double.parseDouble(num));
		} catch (Exception e) {
			percent = "";
		}

		return percent;
	}

	public String getState_color() {
		return state_color;
	}

	public void setState_color(String state_color) {
		this.state_color = state_color;
	}

	public String getOutstate_color() {
		return outstate_color;
	}

	public void setOutstate_color(String outstate_color) {
		this.outstate_color = outstate_color;
	}

	public String getHouse_cpp() {
		return house_cpp;
	}

	public void setHouse_cpp(String house_cpp) {
		this.house_cpp = house_cpp;
	}

	public String getHouse_opp() {
		return house_opp;
	}

	public void setHouse_opp(String house_opp) {
		this.house_opp = house_opp;
	}

	public BigDecimal getEmfi_total() {
		return emfi_total;
	}

	public void setEmfi_total(BigDecimal emfi_total) {
		this.emfi_total = emfi_total == null ? null : emfi_total.setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance == null ? null : balance.setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	public String getEmzt_err() {
		return emzt_err;
	}

	public void setEmzt_err(String emzt_err) {
		this.emzt_err = emzt_err;
	}

	public int getEmzt_chkstate() {
		return emzt_chkstate;
	}

	public void setEmzt_chkstate(int emzt_chkstate) {
		this.emzt_chkstate = emzt_chkstate;
	}

	public String getChkstate() {
		return chkstate;
	}

	public void setChkstate(String chkstate) {
		this.chkstate = chkstate;
	}

	public boolean isEmzt_ischecked() {
		return emzt_ischecked;
	}

	public void setEmzt_ischecked(boolean emzt_ischecked) {
		this.emzt_ischecked = emzt_ischecked;
	}

}
