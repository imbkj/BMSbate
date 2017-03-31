package Model;

import java.util.Date;

public class PubCallModel {

	private int id;
	private int gid;
	private String content;
	private String client;
	private Date addtime;
	private int State;
	private int pc_class;
	private Date remindtime;
	private String readname;
	private Date readtime;
	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	public int getState() {
		return State;
	}
	public void setState(int state) {
		State = state;
	}
	public int getPc_class() {
		return pc_class;
	}
	public void setPc_class(int pc_class) {
		this.pc_class = pc_class;
	}
	public Date getRemindtime() {
		return remindtime;
	}
	public void setRemindtime(Date remindtime) {
		this.remindtime = remindtime;
	}
	public String getReadname() {
		return readname;
	}
	public void setReadname(String readname) {
		this.readname = readname;
	}
	public Date getReadtime() {
		return readtime;
	}
	public void setReadtime(Date readtime) {
		this.readtime = readtime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
