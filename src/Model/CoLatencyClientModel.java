package Model;

import java.util.Date;

public class CoLatencyClientModel {
	private Integer cola_id;
	private Integer cid;
	private String cola_company;
	private String cola_spell;
	private String cola_companytype;
	private String cola_website;
	private Integer cola_sign;
	private String cola_address;
	private String cola_clientarea;
	private String cola_trade;
	private String cola_clientsize;
	private String cola_clientsource;
	private String cola_servicecontent;
	private String cola_remark;
	private Date cola_addtime;
	private String cola_addname;
	private Integer cola_successlevel;
	private String cola_successname; // 数据库没有该字段的
	private Integer cola_ownyear;
	private Integer coba_LTS;
	private Date cola_modifydate;
	private String cola_modifyname;
	private Integer cola_state;
	private Integer quonum;
	private String cola_follower;  //跟进人
	private boolean detailVisible;
	
	private Integer dat_id ;    //自动增长ID
	private Integer log_id  ;	//login 表ID
	private Boolean dat_selected;  // 查询权限
	private Boolean dat_edited;    //操作权限
	private Boolean dat_delete;
	private String  dat_addname ;  //添加人
	private Date dat_addtime  ;    //添加时间
	private Integer num;
	private String cola_ownpeople;
	
	private String cola_realsize;//客户实际规模
	private String cola_kind;//企业性质
	private int colanum;
	private String companytype;
	private String coba_ifhasbribery;
	private String cola_servicessource; //原有服务供应商

	public CoLatencyClientModel(Integer cola_id, Integer cid, String cola_company,
			String cola_spell, String cola_companytype, String cola_website,
			Integer cola_sign, String cola_address, String cola_clientarea,
			String cola_trade, String cola_clientsize,
			String cola_clientsource, String cola_servicecontent,
			String cola_remark, Date cola_addtime, String cola_addname,
			Integer cola_successlevel, Integer cola_ownyear, Integer coba_LTS,
			Date cola_modifydate, String cola_modifyname, Integer cola_state,String cola_kind) {
		super();
		this.cola_id = cola_id;
		this.cid = cid;
		this.cola_company = cola_company;
		this.cola_spell = cola_spell;
		this.cola_companytype = cola_companytype;
		this.cola_website = cola_website;
		this.cola_sign = cola_sign;
		this.cola_address = cola_address;
		this.cola_clientarea = cola_clientarea;
		this.cola_trade = cola_trade;
		this.cola_clientsize = cola_clientsize;
		this.cola_clientsource = cola_clientsource;
		this.cola_servicecontent = cola_servicecontent;
		this.cola_remark = cola_remark;
		this.cola_addtime = cola_addtime;
		this.cola_addname = cola_addname;
		this.cola_successlevel = cola_successlevel;
		this.cola_ownyear = cola_ownyear;
		this.coba_LTS = coba_LTS;
		this.cola_modifydate = cola_modifydate;
		this.cola_modifyname = cola_modifyname;
		this.cola_state = cola_state;
		this.cola_kind=cola_kind;
	}

	public CoLatencyClientModel(Integer cola_id, Integer cid, String cola_company,
			String cola_spell, String cola_companytype, String cola_website,
			Integer cola_sign, String cola_address, String cola_clientarea,
			String cola_trade, String cola_clientsize,
			String cola_clientsource, String cola_servicecontent,
			String cola_remark, Date cola_addtime, String cola_addname,
			Integer cola_successlevel, Integer cola_ownyear, Integer coba_LTS,
			Date cola_modifydate, String cola_modifyname, Integer cola_state,
			String cola_successname,String cola_follower,Integer num,
			String cola_realsize,String cola_kind,int colanum) {
		super();
		this.cola_id = cola_id;
		this.cid = cid;
		this.cola_company = cola_company;
		this.cola_spell = cola_spell;
		this.cola_companytype = cola_companytype;
		this.cola_website = cola_website;
		this.cola_sign = cola_sign;
		this.cola_address = cola_address;
		this.cola_clientarea = cola_clientarea;
		this.cola_trade = cola_trade;
		this.cola_clientsize = cola_clientsize;
		this.cola_clientsource = cola_clientsource;
		this.cola_servicecontent = cola_servicecontent;
		this.cola_remark = cola_remark;
		this.cola_addtime = cola_addtime;
		this.cola_addname = cola_addname;
		this.cola_successlevel = cola_successlevel;
		this.cola_ownyear = cola_ownyear;
		this.coba_LTS = coba_LTS;
		this.cola_modifydate = cola_modifydate;
		this.cola_modifyname = cola_modifyname;
		this.cola_state = cola_state;
		this.cola_successname = cola_successname;
		this.cola_follower=cola_follower;
		this.num=num;
		this.cola_realsize=cola_realsize;
		this.cola_kind=cola_kind;
		this.colanum=colanum;
		
	}
	
	public CoLatencyClientModel(Integer cola_id, Integer cid, String cola_company,
			String cola_spell, String cola_companytype, String cola_website,
			Integer cola_sign, String cola_address, String cola_clientarea,
			String cola_trade, String cola_clientsize,
			String cola_clientsource, String cola_servicecontent,
			String cola_remark, Date cola_addtime, String cola_addname,
			Integer cola_successlevel, Integer cola_ownyear, Integer coba_LTS,
			Date cola_modifydate, String cola_modifyname, Integer cola_state,
			String cola_successname,String cola_follower,Integer num,
			String cola_realsize,String cola_kind) {
		super();
		this.cola_id = cola_id;
		this.cid = cid;
		this.cola_company = cola_company;
		this.cola_spell = cola_spell;
		this.cola_companytype = cola_companytype;
		this.cola_website = cola_website;
		this.cola_sign = cola_sign;
		this.cola_address = cola_address;
		this.cola_clientarea = cola_clientarea;
		this.cola_trade = cola_trade;
		this.cola_clientsize = cola_clientsize;
		this.cola_clientsource = cola_clientsource;
		this.cola_servicecontent = cola_servicecontent;
		this.cola_remark = cola_remark;
		this.cola_addtime = cola_addtime;
		this.cola_addname = cola_addname;
		this.cola_successlevel = cola_successlevel;
		this.cola_ownyear = cola_ownyear;
		this.coba_LTS = coba_LTS;
		this.cola_modifydate = cola_modifydate;
		this.cola_modifyname = cola_modifyname;
		this.cola_state = cola_state;
		this.cola_successname = cola_successname;
		this.cola_follower=cola_follower;
		this.num=num;
		this.cola_realsize=cola_realsize;
		this.cola_kind=cola_kind;
	}

	public CoLatencyClientModel(Integer cola_id, Integer cid, String cola_company,
			String cola_spell, String cola_companytype, String cola_website,
			int cola_sign, String cola_address, String cola_clientarea,
			String cola_trade, String cola_clientsize,
			String cola_clientsource, String cola_servicecontent,
			String cola_remark, Date cola_addtime, String cola_addname,
			int cola_successlevel, Integer cola_ownyear, Integer coba_LTS,
			Date cola_modifydate, String cola_modifyname, Integer cola_state,
			String cola_successname,String cola_follower, Integer quonum, 
			boolean detailVisible,String cola_kind,String cola_realsize) {
		super();
		this.cola_id = cola_id;
		this.cid = cid;
		this.cola_company = cola_company;
		this.cola_spell = cola_spell;
		this.cola_companytype = cola_companytype;
		this.cola_website = cola_website;
		this.cola_sign = cola_sign;
		this.cola_address = cola_address;
		this.cola_clientarea = cola_clientarea;
		this.cola_trade = cola_trade;
		this.cola_clientsize = cola_clientsize;
		this.cola_clientsource = cola_clientsource;
		this.cola_servicecontent = cola_servicecontent;
		this.cola_remark = cola_remark;
		this.cola_addtime = cola_addtime;
		this.cola_addname = cola_addname;
		this.cola_successlevel = cola_successlevel;
		this.cola_ownyear = cola_ownyear;
		this.coba_LTS = coba_LTS;
		this.cola_modifydate = cola_modifydate;
		this.cola_modifyname = cola_modifyname;
		this.cola_state = cola_state;
		this.cola_successname = cola_successname;
		this.quonum = quonum;
		this.detailVisible = detailVisible;
		this.cola_kind=cola_kind;
		this.cola_follower=cola_follower;
		this.cola_realsize=cola_realsize;
	}

	
	
	
	
	public CoLatencyClientModel(Integer cola_id, Integer cid, String cola_company,
			String cola_spell, String cola_companytype, String cola_website,
			int cola_sign, String cola_address, String cola_clientarea,
			String cola_trade, String cola_clientsize,
			String cola_clientsource, String cola_servicecontent,
			String cola_remark, Date cola_addtime, String cola_addname,
			int cola_successlevel, Integer cola_ownyear, Integer coba_LTS,
			Date cola_modifydate, String cola_modifyname, Integer cola_state,
			String cola_successname,String cola_follower, Integer quonum, 
			boolean detailVisible,String cola_kind,String cola_realsize,String cola_servicessource) {
		super();
		this.cola_id = cola_id;
		this.cid = cid;
		this.cola_company = cola_company;
		this.cola_spell = cola_spell;
		this.cola_companytype = cola_companytype;
		this.cola_website = cola_website;
		this.cola_sign = cola_sign;
		this.cola_address = cola_address;
		this.cola_clientarea = cola_clientarea;
		this.cola_trade = cola_trade;
		this.cola_clientsize = cola_clientsize;
		this.cola_clientsource = cola_clientsource;
		this.cola_servicecontent = cola_servicecontent;
		this.cola_remark = cola_remark;
		this.cola_addtime = cola_addtime;
		this.cola_addname = cola_addname;
		this.cola_successlevel = cola_successlevel;
		this.cola_ownyear = cola_ownyear;
		this.coba_LTS = coba_LTS;
		this.cola_modifydate = cola_modifydate;
		this.cola_modifyname = cola_modifyname;
		this.cola_state = cola_state;
		this.cola_successname = cola_successname;
		this.quonum = quonum;
		this.detailVisible = detailVisible;
		this.cola_kind=cola_kind;
		this.cola_follower=cola_follower;
		this.cola_realsize=cola_realsize;
		this.cola_servicessource=cola_servicessource;
	}

	
	public CoLatencyClientModel(Integer cola_id, Integer cid, String cola_company,
			String cola_spell, String cola_companytype, String cola_website,
			Integer cola_sign, String cola_address, String cola_clientarea,
			String cola_trade, String cola_clientsize,
			String cola_clientsource, String cola_servicecontent,
			String cola_remark, Date cola_addtime, String cola_addname,
			Integer cola_successlevel, String cola_successname, Integer cola_ownyear,
			Integer coba_LTS, Date cola_modifydate, String cola_modifyname,
			Integer cola_state, Integer quonum, String cola_follower, Integer dat_id,
			Integer log_id, Boolean dat_selected, Boolean dat_edited,
			Boolean dat_delete, String dat_addname, Date dat_addtime,String cola_kind) {
		super();
		this.cola_id = cola_id;
		this.cid = cid;
		this.cola_company = cola_company;
		this.cola_spell = cola_spell;
		this.cola_companytype = cola_companytype;
		this.cola_website = cola_website;
		this.cola_sign = cola_sign;
		this.cola_address = cola_address;
		this.cola_clientarea = cola_clientarea;
		this.cola_trade = cola_trade;
		this.cola_clientsize = cola_clientsize;
		this.cola_clientsource = cola_clientsource;
		this.cola_servicecontent = cola_servicecontent;
		this.cola_remark = cola_remark;
		this.cola_addtime = cola_addtime;
		this.cola_addname = cola_addname;
		this.cola_successlevel = cola_successlevel;
		this.cola_successname = cola_successname;
		this.cola_ownyear = cola_ownyear;
		this.coba_LTS = coba_LTS;
		this.cola_modifydate = cola_modifydate;
		this.cola_modifyname = cola_modifyname;
		this.cola_state = cola_state;
		this.quonum = quonum;
		this.cola_follower = cola_follower;
		this.dat_id = dat_id;
		this.log_id = log_id;
		this.dat_selected = dat_selected;
		this.dat_edited = dat_edited;
		this.dat_delete = dat_delete;
		this.dat_addname = dat_addname;
		this.dat_addtime = dat_addtime;
		this.cola_kind=cola_kind;
	}
	
	public CoLatencyClientModel(Integer cola_id, Integer cid, String cola_company,
			String cola_spell, String cola_companytype, String cola_website,
			Integer cola_sign, String cola_address, String cola_clientarea,
			String cola_trade, String cola_clientsize,
			String cola_clientsource, String cola_servicecontent,
			String cola_remark, Date cola_addtime, String cola_addname,
			Integer cola_successlevel, Integer cola_ownyear, Integer coba_LTS,
			Date cola_modifydate, String cola_modifyname, Integer cola_state,
			String cola_successname,String cola_follower,Integer dat_id,
			Integer log_id, Boolean dat_selected, Boolean dat_edited,
			Boolean dat_delete, String dat_addname, Date dat_addtime,String cola_kind) {
		super();
		this.cola_id = cola_id;
		this.cid = cid;
		this.cola_company = cola_company;
		this.cola_spell = cola_spell;
		this.cola_companytype = cola_companytype;
		this.cola_website = cola_website;
		this.cola_sign = cola_sign;
		this.cola_address = cola_address;
		this.cola_clientarea = cola_clientarea;
		this.cola_trade = cola_trade;
		this.cola_clientsize = cola_clientsize;
		this.cola_clientsource = cola_clientsource;
		this.cola_servicecontent = cola_servicecontent;
		this.cola_remark = cola_remark;
		this.cola_addtime = cola_addtime;
		this.cola_addname = cola_addname;
		this.cola_successlevel = cola_successlevel;
		this.cola_ownyear = cola_ownyear;
		this.coba_LTS = coba_LTS;
		this.cola_modifydate = cola_modifydate;
		this.cola_modifyname = cola_modifyname;
		this.cola_state = cola_state;
		this.cola_successname = cola_successname;
		this.cola_follower=cola_follower;
		
		this.dat_id = dat_id;
		this.log_id = log_id;
		this.dat_selected = dat_selected;
		this.dat_edited = dat_edited;
		this.dat_delete = dat_delete;
		this.dat_addname = dat_addname;
		this.dat_addtime = dat_addtime;
		this.cola_kind=cola_kind;
	}

	public Integer getDat_id() {
		return dat_id;
	}

	public void setDat_id(Integer dat_id) {
		this.dat_id = dat_id;
	}

	public Integer getLog_id() {
		return log_id;
	}

	public void setLog_id(Integer log_id) {
		this.log_id = log_id;
	}

	public String getCola_servicessource() {
		return cola_servicessource;
	}

	public void setCola_servicessource(String cola_servicessource) {
		this.cola_servicessource = cola_servicessource;
	}

	public Boolean getDat_selected() {
		return dat_selected;
	}

	public void setDat_selected(Boolean dat_selected) {
		this.dat_selected = dat_selected;
	}

	public Boolean getDat_edited() {
		return dat_edited;
	}

	public void setDat_edited(Boolean dat_edited) {
		this.dat_edited = dat_edited;
	}

	public Boolean getDat_delete() {
		return dat_delete;
	}

	public void setDat_delete(Boolean dat_delete) {
		this.dat_delete = dat_delete;
	}

	public String getDat_addname() {
		return dat_addname;
	}

	public void setDat_addname(String dat_addname) {
		this.dat_addname = dat_addname;
	}

	public Date getDat_addtime() {
		return dat_addtime;
	}

	public void setDat_addtime(Date dat_addtime) {
		this.dat_addtime = dat_addtime;
	}

	public CoLatencyClientModel() {
	}

	public Integer getCola_id() {
		return cola_id;
	}

	public void setCola_id(Integer cola_id) {
		this.cola_id = cola_id;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getCola_company() {
		return cola_company;
	}

	public void setCola_company(String cola_company) {
		this.cola_company = cola_company;
	}

	public String getCola_spell() {
		return cola_spell;
	}

	public void setCola_spell(String cola_spell) {
		this.cola_spell = cola_spell;
	}

	public String getCola_companytype() {
		return cola_companytype;
	}

	public void setCola_companytype(String cola_companytype) {
		this.cola_companytype = cola_companytype;
	}

	public String getCola_website() {
		return cola_website;
	}

	public void setCola_website(String cola_website) {
		this.cola_website = cola_website;
	}

	public Integer getCola_sign() {
		return cola_sign;
	}

	public void setCola_sign(Integer cola_sign) {
		this.cola_sign = cola_sign;
	}

	public String getCola_address() {
		return cola_address;
	}

	public void setCola_address(String cola_address) {
		this.cola_address = cola_address;
	}

	public String getCola_clientarea() {
		return cola_clientarea;
	}

	public void setCola_clientarea(String cola_clientarea) {
		this.cola_clientarea = cola_clientarea;
	}

	public String getCola_trade() {
		return cola_trade;
	}

	public void setCola_trade(String cola_trade) {
		this.cola_trade = cola_trade;
	}

	public String getCola_clientsize() {
		return cola_clientsize;
	}

	public void setCola_clientsize(String cola_clientsize) {
		this.cola_clientsize = cola_clientsize;
	}

	public String getCola_clientsource() {
		return cola_clientsource;
	}

	public void setCola_clientsource(String cola_clientsource) {
		this.cola_clientsource = cola_clientsource;
	}

	public String getCola_servicecontent() {
		return cola_servicecontent;
	}

	public void setCola_servicecontent(String cola_servicecontent) {
		this.cola_servicecontent = cola_servicecontent;
	}

	public String getCola_remark() {
		return cola_remark;
	}

	public void setCola_remark(String cola_remark) {
		this.cola_remark = cola_remark;
	}

	public Date getCola_addtime() {
		return cola_addtime;
	}

	public void setCola_addtime(Date cola_addtime) {
		this.cola_addtime = cola_addtime;
	}

	public String getCola_addname() {
		return cola_addname;
	}

	public void setCola_addname(String cola_addname) {
		this.cola_addname = cola_addname;
	}

	public Integer getCola_successlevel() {
		return cola_successlevel;
	}

	public void setCola_successlevel(Integer cola_successlevel) {
		this.cola_successlevel = cola_successlevel;
	}

	public Integer getCola_ownyear() {
		return cola_ownyear;
	}

	public void setCola_ownyear(Integer cola_ownyear) {
		this.cola_ownyear = cola_ownyear;
	}

	public Integer getCoba_LTS() {
		return coba_LTS;
	}

	public void setCoba_LTS(Integer coba_LTS) {
		this.coba_LTS = coba_LTS;
	}

	public Date getCola_modifydate() {
		return cola_modifydate;
	}

	public void setCola_modifydate(Date cola_modifydate) {
		this.cola_modifydate = cola_modifydate;
	}

	public String getCola_modifyname() {
		return cola_modifyname;
	}

	public void setCola_modifyname(String cola_modifyname) {
		this.cola_modifyname = cola_modifyname;
	}

	public Integer getCola_state() {
		return cola_state;
	}

	public void setCola_state(Integer cola_state) {
		this.cola_state = cola_state;
	}

	public String getCola_successname() {
		return cola_successname;
	}

	public void setCola_successname(String cola_successname) {
		this.cola_successname = cola_successname;
	}

	public Integer getQuonum() {
		return quonum;
	}

	public void setQuonum(Integer quonum) {
		this.quonum = quonum;
	}

	public String getCola_follower() {
		return cola_follower;
	}

	public void setCola_follower(String cola_follower) {
		this.cola_follower = cola_follower;
	}

	public boolean isDetailVisible() {
		return detailVisible;
	}

	public void setDetailVisible(boolean detailVisible) {
		this.detailVisible = detailVisible;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getCola_realsize() {
		return cola_realsize;
	}

	public void setCola_realsize(String cola_realsize) {
		this.cola_realsize = cola_realsize;
	}

	public String getCola_ownpeople() {
		return cola_ownpeople;
	}

	public void setCola_ownpeople(String cola_ownpeople) {
		this.cola_ownpeople = cola_ownpeople;
	}

	public String getCola_kind() {
		return cola_kind;
	}

	public void setCola_kind(String cola_kind) {
		this.cola_kind = cola_kind;
	}

	public int getColanum() {
		return colanum;
	}

	public void setColanum(int colanum) {
		this.colanum = colanum;
	}

	public String getCompanytype() {
		return companytype;
	}

	public void setCompanytype(String companytype) {
		this.companytype = companytype;
	}

	public String getCoba_ifhasbribery() {
		return coba_ifhasbribery;
	}

	public void setCoba_ifhasbribery(String coba_ifhasbribery) {
		this.coba_ifhasbribery = coba_ifhasbribery;
	}
	
}
