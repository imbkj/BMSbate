package Model;

import java.util.Date;

public class KnowledgeBaseModel {
	private int id;
	private int pid;
	private String name;
	private String url;
	private String adminUrl;
	private int level;
	private String target;
	private Date addtime;
	private Date updatetime;
	private String addname;
	public KnowledgeBaseModel(int id, int pid, String name, String url,
			String adminUrl, int level, String target, Date addtime,
			Date updatetime, String addname) {
		super();
		this.id = id;
		this.pid = pid;
		this.name = name;
		this.url = url;
		this.adminUrl = adminUrl;
		this.level = level;
		this.target = target;
		this.addtime = addtime;
		this.updatetime = updatetime;
		this.addname = addname;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAdminUrl() {
		return adminUrl;
	}
	public void setAdminUrl(String adminUrl) {
		this.adminUrl = adminUrl;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public String getAddname() {
		return addname;
	}
	public void setAddname(String addname) {
		this.addname = addname;
	}
	
	public KnowledgeBaseModel() {
		super();
	}
}
