package Model;
//EmBase_ESIn_CFIn_V视图的model
public class EmBaseESInCFInModel {
	
	private int gid;
	private int cid;
	private String emba_name;
	private int emba_state;
	private String cfin_id;
	private String esda_ba_name;
	private String cfin_name;
	private String coba_company;
	private String coba_shortname;
	private String coba_namespell;
	private String coba_spell;
	private String coba_client;
	private String emba_spell;
	private String esda_bcc_email;
	private String esin_addname;
	private String esin_addtime;
	private String esin_stopmonth;
	private String esin_taxplace;
	private String esin_id;
	private String coba_gzaddname;
	private String coba_gzaudname;
	private String coba_servicestate;

	
	public EmBaseESInCFInModel() {
		super();
	}
	
	


	public EmBaseESInCFInModel(int gid, int cid, String emba_name,
			int emba_state, String cfin_id, String esda_ba_name,
			String cfin_name, String coba_company, String coba_shortname,
			String coba_namespell, String coba_spell, String coba_client,
			String emba_spell, String esda_bcc_email, String esin_addname,
			String esin_addtime, String esin_stopmonth, String esin_taxplace,
			String esin_id, String coba_gzaddname, String coba_gzaudname,
			String coba_servicestate) {
		super();
		this.gid = gid;
		this.cid = cid;
		this.emba_name = emba_name;
		this.emba_state = emba_state;
		this.cfin_id = cfin_id;
		this.esda_ba_name = esda_ba_name;
		this.cfin_name = cfin_name;
		this.coba_company = coba_company;
		this.coba_shortname = coba_shortname;
		this.coba_namespell = coba_namespell;
		this.coba_spell = coba_spell;
		this.coba_client = coba_client;
		this.emba_spell = emba_spell;
		this.esda_bcc_email = esda_bcc_email;
		this.esin_addname = esin_addname;
		this.esin_addtime = esin_addtime;
		this.esin_stopmonth = esin_stopmonth;
		this.esin_taxplace = esin_taxplace;
		this.esin_id = esin_id;
		this.coba_gzaddname = coba_gzaddname;
		this.coba_gzaudname = coba_gzaudname;
		this.coba_servicestate = coba_servicestate;
	}




	public String getCoba_servicestate() {
		return coba_servicestate;
	}


	public void setCoba_servicestate(String coba_servicestate) {
		this.coba_servicestate = coba_servicestate;
	}


	public String getCoba_gzaddname() {
		return coba_gzaddname;
	}


	public void setCoba_gzaddname(String coba_gzaddname) {
		this.coba_gzaddname = coba_gzaddname;
	}


	public String getCoba_gzaudname() {
		return coba_gzaudname;
	}


	public void setCoba_gzaudname(String coba_gzaudname) {
		this.coba_gzaudname = coba_gzaudname;
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
	public String getEmba_name() {
		return emba_name;
	}
	public void setEmba_name(String emba_name) {
		this.emba_name = emba_name;
	}
	public int getEmba_state() {
		return emba_state;
	}
	public void setEmba_state(int emba_state) {
		this.emba_state = emba_state;
	}
	public String getCfin_id() {
		return cfin_id;
	}
	public void setCfin_id(String cfin_id) {
		this.cfin_id = cfin_id;
	}
	public String getEsda_ba_name() {
		return esda_ba_name;
	}
	public void setEsda_ba_name(String esda_ba_name) {
		this.esda_ba_name = esda_ba_name;
	}
	public String getCfin_name() {
		return cfin_name;
	}
	public void setCfin_name(String cfin_name) {
		this.cfin_name = cfin_name;
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
	public String getCoba_namespell() {
		return coba_namespell;
	}
	public void setCoba_namespell(String coba_namespell) {
		this.coba_namespell = coba_namespell;
	}
	public String getCoba_spell() {
		return coba_spell;
	}
	public void setCoba_spell(String coba_spell) {
		this.coba_spell = coba_spell;
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
	public String getEsda_bcc_email() {
		return esda_bcc_email;
	}
	public void setEsda_bcc_email(String esda_bcc_email) {
		this.esda_bcc_email = esda_bcc_email;
	}
	public String getEsin_addname() {
		return esin_addname;
	}
	public void setEsin_addname(String esin_addname) {
		this.esin_addname = esin_addname;
	}
	public String getEsin_addtime() {
		return esin_addtime;
	}
	public void setEsin_addtime(String esin_addtime) {
		this.esin_addtime = esin_addtime;
	}
	public String getEsin_stopmonth() {
		return esin_stopmonth;
	}
	public void setEsin_stopmonth(String esin_stopmonth) {
		this.esin_stopmonth = esin_stopmonth;
	}
	public String getEsin_taxplace() {
		return esin_taxplace;
	}
	public void setEsin_taxplace(String esin_taxplace) {
		this.esin_taxplace = esin_taxplace;
	}
	public String getEsin_id() {
		return esin_id;
	}
	public void setEsin_id(String esin_id) {
		this.esin_id = esin_id;
	}
	
	
}
