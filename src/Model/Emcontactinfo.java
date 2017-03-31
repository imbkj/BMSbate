package Model;

public class Emcontactinfo implements Cloneable {
	private int id;
	private int ownmonth;
	private int gid;
	private String emzt_t_name;
	private String emzt_t_idcard;
	private String emzt_hjadd;
	private String emzt_education;
	private String emzt_folk;
	private String emzt_email;
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
	private String emzt_addtime;
	private String emzt_state;
	private String emzt_r_record;
	private String emzt_emba_idcard;
	private String emzt_contactstateweb;
	
	public Emcontactinfo() {
		super();
	}
	
	
	public Object clone()
	{
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
		   return null;
		}
		
	}

	
	
	public String getEmzt_contactstateweb() {
		return emzt_contactstateweb;
	}


	public void setEmzt_contactstateweb(String emzt_contactstateweb) {
		this.emzt_contactstateweb = emzt_contactstateweb;
	}


	public String getEmzt_emba_idcard() {
		return emzt_emba_idcard;
	}


	public void setEmzt_emba_idcard(String emzt_emba_idcard) {
		this.emzt_emba_idcard = emzt_emba_idcard;
	}


	public String getEmzt_r_record() {
		return emzt_r_record;
	}


	public void setEmzt_r_record(String emzt_r_record) {
		this.emzt_r_record = emzt_r_record;
	}


	public String getEmzt_email() {
		return emzt_email;
	}


	public void setEmzt_email(String emzt_email) {
		this.emzt_email = emzt_email;
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
	public String getEmzt_addtime() {
		return emzt_addtime;
	}
	public void setEmzt_addtime(String emzt_addtime) {
		this.emzt_addtime = emzt_addtime;
	}
	public String getEmzt_state() {
		return emzt_state;
	}
	public void setEmzt_state(String emzt_state) {
		this.emzt_state = emzt_state;
	}
	
	

}
