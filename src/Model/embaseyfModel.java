package Model;
import java.text.ParseException;
import java.util.Date;

import Util.DateStringChange;

public class embaseyfModel {
	
	private  String ownmonth;
	private Integer gid;
	private Integer cid;
	private String coba_shortname;
	private String coba_spell;
	private String coba_company;
	private String coba_namespell;
	private String coba_client;
	private String emba_addtime;
	
	private String emba_name;
	private String emba_spell;
	private String emba_idcard;
	private String  emba_mobile;
	
	private Integer emba_tapr_id;
	private String tali_addtime;
	private String cosp_enty_caliname;
	
	private Integer emba_state; //操作状态
	
	private Integer emzt_contactstate; //联系状态
	private Integer emzt_datastate;  //材料状态
	private Integer emzt_contactstateweb; //入职网状态
	private Integer emzt_contactstateyd; //移动端状态
	
	private String emba_statestr;   
	private String emzt_contactstatestr;
	private String emzt_datastatestr;
	private String emzt_contactstatewebstr;
	private String emzt_contactstateydstr;
	
	private String emzt_r_record;
	private String emba_emhb_ownmonth;
	private String emba_emsb_ownmonth;
	
	private String coba_assistant;
	
	
	 
	public embaseyfModel(Date ownmonth, Integer gid, Integer cid,
			String coba_shortname, String coba_spell, String coba_company,
			String coba_namespell, String emba_name, String emba_spell,
			String emba_idcard,String emba_mobile,  Integer emba_tapr_id,
			String tali_addtime, String cosp_enty_caliname, Integer emba_state,
			Integer emzt_contactstate, Integer emzt_datastate,
			Integer emzt_contactstateweb, Integer emzt_contactstateyd,
			String emba_statestr, String emzt_contactstatestr,
			String emzt_datastatestr, String emzt_contactstatewebstr,
			String emzt_contactstateydstr,String coba_client,String emzt_r_record) throws ParseException {
		super();
		this.ownmonth = DateStringChange.Stringtoownmonth(ownmonth);
		this.gid = gid;
		this.cid = cid;
		this.coba_shortname = coba_shortname;
		this.coba_spell = coba_spell;
		this.coba_company = coba_company;
		this.coba_namespell = coba_namespell;
		this.emba_name = emba_name;
		this.emba_spell = emba_spell;
		this.emba_idcard = emba_idcard;
		this.emba_mobile=emba_mobile;
		this.emba_tapr_id = emba_tapr_id;
		this.tali_addtime = tali_addtime;
		this.cosp_enty_caliname = cosp_enty_caliname;
		this.emba_state = emba_state;
		this.emzt_contactstate = emzt_contactstate;
		this.emzt_datastate = emzt_datastate;
		this.emzt_contactstateweb = emzt_contactstateweb;
		this.emzt_contactstateyd = emzt_contactstateyd;
		this.emba_statestr = emba_statestr;
		this.emzt_contactstatestr = emzt_contactstatestr;
		this.emzt_datastatestr = emzt_datastatestr;
		this.emzt_contactstatewebstr = emzt_contactstatewebstr;
		this.emzt_contactstateydstr = emzt_contactstateydstr;
		this.coba_client=coba_client;
		this.emzt_r_record=emzt_r_record;
	}
	
	public embaseyfModel(String ownmonth, Integer gid, Integer cid,
			String coba_shortname, String coba_spell, String coba_company,
			String coba_namespell, String emba_name, String emba_spell,
			String emba_idcard,String emba_mobile,  Integer emba_tapr_id,
			String tali_addtime, String cosp_enty_caliname, Integer emba_state,
			Integer emzt_contactstate, Integer emzt_datastate,
			Integer emzt_contactstateweb, Integer emzt_contactstateyd,
			String emba_statestr, String emzt_contactstatestr,
			String emzt_datastatestr, String emzt_contactstatewebstr,
			String emzt_contactstateydstr,String coba_client,String emzt_r_record,String emba_emhb_ownmonth,String emba_emsb_ownmonth) throws ParseException {
		super();
		this.ownmonth = ownmonth;
		this.gid = gid;
		this.cid = cid;
		this.coba_shortname = coba_shortname;
		this.coba_spell = coba_spell;
		this.coba_company = coba_company;
		this.coba_namespell = coba_namespell;
		this.emba_name = emba_name;
		this.emba_spell = emba_spell;
		this.emba_idcard = emba_idcard;
		this.emba_mobile=emba_mobile;
		this.emba_tapr_id = emba_tapr_id;
		this.tali_addtime = tali_addtime;
		this.cosp_enty_caliname = cosp_enty_caliname;
		this.emba_state = emba_state;
		this.emzt_contactstate = emzt_contactstate;
		this.emzt_datastate = emzt_datastate;
		this.emzt_contactstateweb = emzt_contactstateweb;
		this.emzt_contactstateyd = emzt_contactstateyd;
		this.emba_statestr = emba_statestr;
		this.emzt_contactstatestr = emzt_contactstatestr;
		this.emzt_datastatestr = emzt_datastatestr;
		this.emzt_contactstatewebstr = emzt_contactstatewebstr;
		this.emzt_contactstateydstr = emzt_contactstateydstr;
		this.coba_client=coba_client;
		this.emzt_r_record=emzt_r_record;
		this.emba_emhb_ownmonth=emba_emhb_ownmonth;
		this.emba_emsb_ownmonth=emba_emsb_ownmonth;
	}
	
	public embaseyfModel(String ownmonth, Integer gid, Integer cid,
			String coba_shortname, String coba_spell, String coba_company,
			String coba_namespell, String emba_name, String emba_spell,
			String emba_idcard,String emba_mobile,  Integer emba_tapr_id,
			String tali_addtime, String cosp_enty_caliname, Integer emba_state,
			Integer emzt_contactstate, Integer emzt_datastate,
			Integer emzt_contactstateweb, Integer emzt_contactstateyd,
			String emba_statestr, String emzt_contactstatestr,
			String emzt_datastatestr, String emzt_contactstatewebstr,
			String emzt_contactstateydstr,String coba_client,String emzt_r_record,String emba_emhb_ownmonth,String emba_emsb_ownmonth,String coba_assistant) throws ParseException {
		super();
		this.ownmonth = ownmonth;
		this.gid = gid;
		this.cid = cid;
		this.coba_shortname = coba_shortname;
		this.coba_spell = coba_spell;
		this.coba_company = coba_company;
		this.coba_namespell = coba_namespell;
		this.emba_name = emba_name;
		this.emba_spell = emba_spell;
		this.emba_idcard = emba_idcard;
		this.emba_mobile=emba_mobile;
		this.emba_tapr_id = emba_tapr_id;
		this.tali_addtime = tali_addtime;
		this.cosp_enty_caliname = cosp_enty_caliname;
		this.emba_state = emba_state;
		this.emzt_contactstate = emzt_contactstate;
		this.emzt_datastate = emzt_datastate;
		this.emzt_contactstateweb = emzt_contactstateweb;
		this.emzt_contactstateyd = emzt_contactstateyd;
		this.emba_statestr = emba_statestr;
		this.emzt_contactstatestr = emzt_contactstatestr;
		this.emzt_datastatestr = emzt_datastatestr;
		this.emzt_contactstatewebstr = emzt_contactstatewebstr;
		this.emzt_contactstateydstr = emzt_contactstateydstr;
		this.coba_client=coba_client;
		this.emzt_r_record=emzt_r_record;
		this.emba_emhb_ownmonth=emba_emhb_ownmonth;
		this.emba_emsb_ownmonth=emba_emsb_ownmonth;
		this.coba_assistant=coba_assistant;
	}
	

	public String getEmzt_r_record() {
		return emzt_r_record;
	}


	public void setEmzt_r_record(String emzt_r_record) {
		this.emzt_r_record = emzt_r_record;
	}


	public String getEmba_addtime() {
		return emba_addtime;
	}


	public void setEmba_addtime(String emba_addtime) {
		this.emba_addtime = emba_addtime;
	}


	public String getCoba_client() {
		return coba_client;
	}


	public void setCoba_client(String coba_client) {
		this.coba_client = coba_client;
	}


	public String getEmba_spell() {
		return emba_spell;
	}
	public void setEmba_spell(String emba_spell) {
		this.emba_spell = emba_spell;
	}
	public String getEmba_idcard() {
		return emba_idcard;
	}
	public void setEmba_idcard(String emba_idcard) {
		this.emba_idcard = emba_idcard;
	}
	public String getEmba_mobile() {
		return emba_mobile;
	}
	public void setEmba_mobile(String emba_mobile) {
		this.emba_mobile = emba_mobile;
	}
	public String getOwnmonth() {
		return ownmonth;
	}
	public void setOwnmonth(String ownmonth) {
		this.ownmonth = ownmonth;
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
	public String getCoba_shortname() {
		return coba_shortname;
	}
	public void setCoba_shortname(String coba_shortname) {
		this.coba_shortname = coba_shortname;
	}
	public String getCoba_spell() {
		return coba_spell;
	}
	public void setCoba_spell(String coba_spell) {
		this.coba_spell = coba_spell;
	}
	public String getCoba_company() {
		return coba_company;
	}
	public void setCoba_company(String coba_company) {
		this.coba_company = coba_company;
	}
	public String getCoba_namespell() {
		return coba_namespell;
	}
	public void setCoba_namespell(String coba_namespell) {
		this.coba_namespell = coba_namespell;
	}
	public String getEmba_name() {
		return emba_name;
	}
	public void setEmba_name(String emba_name) {
		this.emba_name = emba_name;
	}
	public Integer getEmba_tapr_id() {
		return emba_tapr_id;
	}
	public void setEmba_tapr_id(Integer emba_tapr_id) {
		this.emba_tapr_id = emba_tapr_id;
	}
	public String getTali_addtime() {
		return tali_addtime;
	}
	public void setTali_addtime(String tali_addtime) {
		this.tali_addtime = tali_addtime;
	}
	public String getCosp_enty_caliname() {
		return cosp_enty_caliname;
	}
	public void setCosp_enty_caliname(String cosp_enty_caliname) {
		this.cosp_enty_caliname = cosp_enty_caliname;
	}
	public Integer getEmba_state() {
		return emba_state;
	}
	public void setEmba_state(Integer emba_state) {
		this.emba_state = emba_state;
	}
	public Integer getEmzt_contactstate() {
		return emzt_contactstate;
	}
	public void setEmzt_contactstate(Integer emzt_contactstate) {
		this.emzt_contactstate = emzt_contactstate;
	}
	public Integer getEmzt_datastate() {
		return emzt_datastate;
	}
	public void setEmzt_datastate(Integer emzt_datastate) {
		this.emzt_datastate = emzt_datastate;
	}
	public Integer getEmzt_contactstateweb() {
		return emzt_contactstateweb;
	}
	public void setEmzt_contactstateweb(Integer emzt_contactstateweb) {
		this.emzt_contactstateweb = emzt_contactstateweb;
	}
	public Integer getEmzt_contactstateyd() {
		return emzt_contactstateyd;
	}
	public void setEmzt_contactstateyd(Integer emzt_contactstateyd) {
		this.emzt_contactstateyd = emzt_contactstateyd;
	}
	public String getEmba_statestr() {
		return emba_statestr;
	}
	public void setEmba_statestr(String emba_statestr) {
		this.emba_statestr = emba_statestr;
	}
	public String getEmzt_contactstatestr() {
		return emzt_contactstatestr;
	}
	public void setEmzt_contactstatestr(String emzt_contactstatestr) {
		this.emzt_contactstatestr = emzt_contactstatestr;
	}
	public String getEmzt_datastatestr() {
		return emzt_datastatestr;
	}
	public void setEmzt_datastatestr(String emzt_datastatestr) {
		this.emzt_datastatestr = emzt_datastatestr;
	}
	public String getEmzt_contactstatewebstr() {
		return emzt_contactstatewebstr;
	}
	public void setEmzt_contactstatewebstr(String emzt_contactstatewebstr) {
		this.emzt_contactstatewebstr = emzt_contactstatewebstr;
	}
	public String getEmzt_contactstateydstr() {
		return emzt_contactstateydstr;
	}
	public void setEmzt_contactstateydstr(String emzt_contactstateydstr) {
		this.emzt_contactstateydstr = emzt_contactstateydstr;
	}

	public String getEmba_emhb_ownmonth() {
		return emba_emhb_ownmonth;
	}

	public void setEmba_emhb_ownmonth(String emba_emhb_ownmonth) {
		this.emba_emhb_ownmonth = emba_emhb_ownmonth;
	}

	public String getEmba_emsb_ownmonth() {
		return emba_emsb_ownmonth;
	}

	public void setEmba_emsb_ownmonth(String emba_emsb_ownmonth) {
		this.emba_emsb_ownmonth = emba_emsb_ownmonth;
	}

	public String getCoba_assistant() {
		return coba_assistant;
	}

	public void setCoba_assistant(String coba_assistant) {
		this.coba_assistant = coba_assistant;
	}
	 
	
	 
 
}
