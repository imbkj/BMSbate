package Model;

import java.util.Date;

public class EmArchiveRemarkModel {
	private Integer eare_id;
	private Integer eare_tid;
	private Integer eare_trid;
	private String eare_content;
	private Date eare_addtime;
	private String eare_addname;
	private Integer eare_state;
	private String eare_remark;
	private String eare_tablename;
	private int gid;

	public EmArchiveRemarkModel() {
		super();
	}

	public EmArchiveRemarkModel(Integer eare_id, Integer eare_tid, Integer eare_trid,
			String eare_content, Date eare_addtime, String eare_addname,
			Integer eare_state, String eare_remark) {
		super();
		this.eare_id = eare_id;
		this.eare_tid = eare_tid;
		this.eare_trid = eare_trid;
		this.eare_content = eare_content;
		this.eare_addtime = eare_addtime;
		this.eare_addname = eare_addname;
		this.eare_state = eare_state;
		this.eare_remark = eare_remark;
	}

	public Integer getEare_id() {
		return eare_id;
	}

	public void setEare_id(Integer eare_id) {
		this.eare_id = eare_id;
	}

	public Integer getEare_tid() {
		return eare_tid;
	}

	public void setEare_tid(Integer eare_tid) {
		this.eare_tid = eare_tid;
	}

	public Integer getEare_trid() {
		return eare_trid;
	}

	public void setEare_trid(Integer eare_trid) {
		this.eare_trid = eare_trid;
	}

	public String getEare_content() {
		return eare_content;
	}

	public void setEare_content(String eare_content) {
		this.eare_content = eare_content;
	}

	public Date getEare_addtime() {
		return eare_addtime;
	}

	public void setEare_addtime(Date eare_addtime) {
		this.eare_addtime = eare_addtime;
	}

	public String getEare_addname() {
		return eare_addname;
	}

	public void setEare_addname(String eare_addname) {
		this.eare_addname = eare_addname;
	}

	public Integer getEare_state() {
		return eare_state;
	}

	public void setEare_state(Integer eare_state) {
		this.eare_state = eare_state;
	}

	public String getEare_remark() {
		return eare_remark;
	}

	public void setEare_remark(String eare_remark) {
		this.eare_remark = eare_remark;
	}

	public String getEare_tablename() {
		return eare_tablename;
	}

	public void setEare_tablename(String eare_tablename) {
		this.eare_tablename = eare_tablename;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}
	
}
