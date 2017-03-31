package Model;

import java.util.Date;

public class KnowledgeBase_ContentModel {
	private int kbco_ID;
	private int kbco_classid;
	private String kbco_title;
	private String kbco_content;
	private Date kbco_addtime;
	private Date kbco_updatetime;
	private String kbco_remark;
	private String kbco_addname;
	private String kbco_classname;
	private String kbco_fileurl;
	public KnowledgeBase_ContentModel(int kbco_ID, int kbco_classid,
			String kbco_title, String kbco_content, Date kbco_addtime,
			Date kbco_updatetime, String kbco_remark, String kbco_addname,String kbco_classname,String kbco_fileurl) {
		super();
		this.kbco_ID = kbco_ID;
		this.kbco_classid = kbco_classid;
		this.kbco_title = kbco_title;
		this.kbco_content = kbco_content;
		this.kbco_addtime = kbco_addtime;
		this.kbco_updatetime = kbco_updatetime;
		this.kbco_remark = kbco_remark;
		this.kbco_addname = kbco_addname;
		this.kbco_classname=kbco_classname;
		this.kbco_fileurl=kbco_fileurl;
	}
	public KnowledgeBase_ContentModel() {
		super();
	}
	public int getKbco_ID() {
		return kbco_ID;
	}
	public void setKbco_ID(int kbco_ID) {
		this.kbco_ID = kbco_ID;
	}
	public int getKbco_classid() {
		return kbco_classid;
	}
	public void setKbco_classid(int kbco_classid) {
		this.kbco_classid = kbco_classid;
	}
	public String getKbco_title() {
		return kbco_title;
	}
	public void setKbco_title(String kbco_title) {
		this.kbco_title = kbco_title;
	}
	public String getKbco_content() {
		return kbco_content;
	}
	public void setKbco_content(String kbco_content) {
		this.kbco_content = kbco_content;
	}
	public Date getKbco_addtime() {
		return kbco_addtime;
	}
	public void setKbco_addtime(Date kbco_addtime) {
		this.kbco_addtime = kbco_addtime;
	}
	public Date getKbco_updatetime() {
		return kbco_updatetime;
	}
	public void setKbco_updatetime(Date kbco_updatetime) {
		this.kbco_updatetime = kbco_updatetime;
	}
	public String getKbco_remark() {
		return kbco_remark;
	}
	public void setKbco_remark(String kbco_remark) {
		this.kbco_remark = kbco_remark;
	}
	public String getKbco_addname() {
		return kbco_addname;
	}
	public void setKbco_addname(String kbco_addname) {
		this.kbco_addname = kbco_addname;
	}
	public String getKbco_classname() {
		return kbco_classname;
	}
	public void setKbco_classname(String kbco_classname) {
		this.kbco_classname = kbco_classname;
	}
	public String getKbco_fileurl() {
		return kbco_fileurl;
	}
	public void setKbco_fileurl(String kbco_fileurl) {
		this.kbco_fileurl = kbco_fileurl;
	}
	
}
