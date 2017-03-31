package Model;

import java.util.Date;

public class DataPopedomChangeModel {
	
	private int dat_id ;    //自动增长ID
	private int log_id  ;	//login 表ID
	private int cid  ;		//公司编号	
	private boolean dat_selected;  // 查询权限
	private boolean dat_edited;    //操作权限
	private boolean dat_delete;
	private String  dat_addname ;  //添加人
	private Date dat_addtime  ;    //添加时间
	private String  log_name;
	private String coba_shortname;
	private String coba_client;		
	private  String Dat_auditingname;	//审核人
	private Date Dat_auditingtime;		//审核时间
	private int Dat_state;		 //生效状态
	private int type;
	private int pwr;
	
	public DataPopedomChangeModel(int dat_id, int log_id, int cid,
			boolean dat_selected, boolean dat_edited, boolean dat_delete,
			String dat_addname, Date dat_addtime, String log_name,
			String coba_shortname, String coba_client, String dat_auditingname,
			Date dat_auditingtime, int dat_state) {
		super();
		this.dat_id = dat_id;
		this.log_id = log_id;
		this.cid = cid;
		this.dat_selected = dat_selected;
		this.dat_edited = dat_edited;
		this.dat_delete = dat_delete;
		this.dat_addname = dat_addname;
		this.dat_addtime = dat_addtime;
		this.log_name = log_name;
		this.coba_shortname = coba_shortname;
		this.coba_client = coba_client;
		Dat_auditingname = dat_auditingname;
		Dat_auditingtime = dat_auditingtime;
		Dat_state = dat_state;
	
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getPwr() {
		return pwr;
	}
	public void setPwr(int pwr) {
		this.pwr = pwr;
	}
	
	public DataPopedomChangeModel() {
		super();
	}
	public int getDat_id() {
		return dat_id;
	}
	public void setDat_id(int dat_id) {
		this.dat_id = dat_id;
	}
	public int getLog_id() {
		return log_id;
	}
	public void setLog_id(int log_id) {
		this.log_id = log_id;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}

	public boolean isDat_selected() {
		return dat_selected;
	}
	public void setDat_selected(boolean dat_selected) {
		this.dat_selected = dat_selected;
	}
	public boolean isDat_edited() {
		return dat_edited;
	}
	public void setDat_edited(boolean dat_edited) {
		this.dat_edited = dat_edited;
	}
	public boolean isDat_delete() {
		return dat_delete;
	}
	public void setDat_delete(boolean dat_delete) {
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
	public String getLog_name() {
		return log_name;
	}
	public void setLog_name(String log_name) {
		this.log_name = log_name;
	}
	public String getCoba_shortname() {
		return coba_shortname;
	}
	public void setCoba_shortname(String coba_shortname) {
		this.coba_shortname = coba_shortname;
	}
	public String getCoba_client() {
		return coba_client;
	}
	public void setCoba_client(String coba_client) {
		this.coba_client = coba_client;
	}
	public String getDat_auditingname() {
		return Dat_auditingname;
	}
	public void setDat_auditingname(String dat_auditingname) {
		Dat_auditingname = dat_auditingname;
	}
	public Date getDat_auditingtime() {
		return Dat_auditingtime;
	}
	public void setDat_auditingtime(Date dat_auditingtime) {
		Dat_auditingtime = dat_auditingtime;
	}
	public int getDat_state() {
		return Dat_state;
	}
	public void setDat_state(int dat_state) {
		Dat_state = dat_state;
	}

}
