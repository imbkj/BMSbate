package Model;

import java.util.Date;

/**
 * @author zmj
 * 数据权限
 */

public class DataPopedomModel {
	
	private int dat_id ;    //自动增长ID
	private int log_id  ;	//login 表ID
	private int cid  ;	
	//公司编号	
	private Boolean dat_selected;  // 查询权限
	private Boolean dat_edited;    //操作权限
	private Boolean dat_delete;

	private String  dat_addname ;  //添加人
	private Date dat_addtime  ;    //添加时间
	private String  log_name;
	private String coba_shortname;
	private String coba_client;
	private Integer cola_id;
	private Integer oldlog_id;
	
	
	public Integer getOldlog_id() {
		return oldlog_id;
	}
	public void setOldlog_id(Integer oldlog_id) {
		this.oldlog_id = oldlog_id;
	}
	public Integer getCola_id() {
		return cola_id;
	}
	public void setCola_id(Integer cola_id) {
		this.cola_id = cola_id;
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
	
	public DataPopedomModel(int dat_id, int log_id, int cid,
			Boolean dat_selected, Boolean dat_edited, Boolean dat_delete,
			String dat_addname, Date dat_addtime, String log_name,
			String coba_shortname, String coba_client) {
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
	}
	
	public DataPopedomModel(int dat_id, int log_id, int cid,int cola_id,
			Boolean dat_selected, Boolean dat_edited, Boolean dat_delete,
			String dat_addname, Date dat_addtime, String log_name,
			String coba_shortname, String coba_client) {
		super();
		this.dat_id = dat_id;
		this.log_id = log_id;
		this.cid=cid;
		this.cola_id = cola_id;
		this.dat_selected = dat_selected;
		this.dat_edited = dat_edited;
		this.dat_delete = dat_delete;
		this.dat_addname = dat_addname;
		this.dat_addtime = dat_addtime;
		this.log_name = log_name;
		this.coba_shortname = coba_shortname;
		this.coba_client = coba_client;
	}
	
	public DataPopedomModel() {
		super();
	}
	
	
}
