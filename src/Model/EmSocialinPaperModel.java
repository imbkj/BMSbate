package Model;

public class EmSocialinPaperModel {

	// espa_id
	private int espa_id;
	// cid
	private int cid;
	// gid
	private int gid;
	// name
	private String name;
	// ownmonth
	private String ownmonth;
	// 办卡状态 新办/补卡
	private String espa_type;
	// 发卡方式
	private String espa_sendtype;
	// espa_idcard
	private String espa_idcard;
	// 连接EmSocialinPaperState表的stateid
	private int espa_state;
	// 付款情况
	private String espa_feestate;
	// 付款方式
	private String espa_feetype;
	// espa_addname
	private String espa_addname;
	// espa_addtime
	private String espa_addtime;
	// espa_tapr_id
	private int espa_tapr_id;
	// espa_finaltime
	private String espa_finaltime;
	// espa_modname
	private String espa_modname;
	// espa_modtime
	private String espa_modtime;
	// espa_filetime
	private String espa_filetime;
	// espa_filetime
	private String espa_backreason;
	// espa_backname
	private String espa_backname;
	private int espa_isback;
	private int espa_isrevoke;

	// Stateid
	private int stateid;
	// Statename
	private String statename;
	// Type
	private int type;
	// Typename
	private String typename;
	// orderid
	private int orderid;

	// epsr_id
	private int epsr_id;
	// epsr_stateid
	private int epsr_stateid;
	// epsr_espa_id
	private int epsr_espa_id;
	// epsr_addname
	private String epsr_addname;
	// epsr_addtime
	private String epsr_addtime;
	// epsr_statetime
	private String epsr_statetime;
	// epsr_state
	private int epsr_state;

	private String coba_shortname;
	private String emba_computerid;
	private String str;

	public int getEspa_id() {
		return espa_id;
	}

	public void setEspa_id(int espa_id) {
		this.espa_id = espa_id;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(String ownmonth) {
		this.ownmonth = ownmonth;
	}

	public String getEspa_type() {
		return espa_type;
	}

	public void setEspa_type(String espa_type) {
		this.espa_type = espa_type;
	}

	public String getEspa_sendtype() {
		return espa_sendtype;
	}

	public void setEspa_sendtype(String espa_sendtype) {
		this.espa_sendtype = espa_sendtype;
	}

	public String getEspa_idcard() {
		return espa_idcard;
	}

	public void setEspa_idcard(String espa_idcard) {
		this.espa_idcard = espa_idcard;
	}

	public int getEspa_state() {
		return espa_state;
	}

	public void setEspa_state(int espa_state) {
		this.espa_state = espa_state;
	}

	public String getEspa_feestate() {
		return espa_feestate;
	}

	public void setEspa_feestate(String espa_feestate) {
		this.espa_feestate = espa_feestate;
	}

	public String getEspa_feetype() {
		return espa_feetype;
	}

	public void setEspa_feetype(String espa_feetype) {
		this.espa_feetype = espa_feetype;
	}

	public String getEspa_addname() {
		return espa_addname;
	}

	public void setEspa_addname(String espa_addname) {
		this.espa_addname = espa_addname;
	}

	public String getEspa_addtime() {
		return espa_addtime;
	}

	public void setEspa_addtime(String espa_addtime) {
		this.espa_addtime = espa_addtime;
	}

	public int getStateid() {
		return stateid;
	}

	public void setStateid(int stateid) {
		this.stateid = stateid;
	}

	public String getStatename() {
		return statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public int getOrderid() {
		return orderid;
	}

	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

	public int getEspa_tapr_id() {
		return espa_tapr_id;
	}

	public void setEspa_tapr_id(int espa_tapr_id) {
		this.espa_tapr_id = espa_tapr_id;
	}

	public String getCoba_shortname() {
		return coba_shortname;
	}

	public void setCoba_shortname(String coba_shortname) {
		this.coba_shortname = coba_shortname;
	}

	public String getEspa_finaltime() {
		return espa_finaltime;
	}

	public void setEspa_finaltime(String espa_finaltime) {
		this.espa_finaltime = espa_finaltime;
	}

	public int getEpsr_id() {
		return epsr_id;
	}

	public void setEpsr_id(int epsr_id) {
		this.epsr_id = epsr_id;
	}

	public int getEpsr_stateid() {
		return epsr_stateid;
	}

	public void setEpsr_stateid(int epsr_stateid) {
		this.epsr_stateid = epsr_stateid;
	}

	public int getEpsr_espa_id() {
		return epsr_espa_id;
	}

	public void setEpsr_espa_id(int epsr_espa_id) {
		this.epsr_espa_id = epsr_espa_id;
	}

	public String getEpsr_addname() {
		return epsr_addname;
	}

	public void setEpsr_addname(String epsr_addname) {
		this.epsr_addname = epsr_addname;
	}

	public String getEpsr_addtime() {
		return epsr_addtime;
	}

	public void setEpsr_addtime(String epsr_addtime) {
		this.epsr_addtime = epsr_addtime;
	}

	public String getEpsr_statetime() {
		return epsr_statetime;
	}

	public void setEpsr_statetime(String epsr_statetime) {
		this.epsr_statetime = epsr_statetime;
	}

	public int getEpsr_state() {
		return epsr_state;
	}

	public void setEpsr_state(int epsr_state) {
		this.epsr_state = epsr_state;
	}

	public String getEmba_computerid() {
		return emba_computerid;
	}

	public void setEmba_computerid(String emba_computerid) {
		this.emba_computerid = emba_computerid;
	}

	public String getEspa_modname() {
		return espa_modname;
	}

	public void setEspa_modname(String espa_modname) {
		this.espa_modname = espa_modname;
	}

	public String getEspa_modtime() {
		return espa_modtime;
	}

	public void setEspa_modtime(String espa_modtime) {
		this.espa_modtime = espa_modtime;
	}

	public String getEspa_filetime() {
		return espa_filetime;
	}

	public void setEspa_filetime(String espa_filetime) {
		this.espa_filetime = espa_filetime;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public String getEspa_backreason() {
		return espa_backreason;
	}

	public void setEspa_backreason(String espa_backreason) {
		this.espa_backreason = espa_backreason;
	}

	public String getEspa_backname() {
		return espa_backname;
	}

	public void setEspa_backname(String espa_backname) {
		this.espa_backname = espa_backname;
	}

	public int getEspa_isback() {
		return espa_isback;
	}

	public void setEspa_isback(int espa_isback) {
		this.espa_isback = espa_isback;
	}

	public int getEspa_isrevoke() {
		return espa_isrevoke;
	}

	public void setEspa_isrevoke(int espa_isrevoke) {
		this.espa_isrevoke = espa_isrevoke;
	}
}
