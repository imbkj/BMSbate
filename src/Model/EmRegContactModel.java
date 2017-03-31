package Model;

public class EmRegContactModel {
	// / cont_id
	private Integer cont_id;
	// / cont_erin_id
	private Integer cont_erin_id;
	// / cont_type
	private String cont_type;
	// / cont_content
	private String cont_content;
	// / cont_addname
	private String cont_addname;
	// / cont_addtime
	private String cont_addtime;
	private String cont_remark;
	private String cont_tablename;
	
	private int gid;

	public Integer getCont_id() {
		return cont_id;
	}

	public void setCont_id(Integer cont_id) {
		this.cont_id = cont_id;
	}

	public Integer getCont_erin_id() {
		return cont_erin_id;
	}

	public void setCont_erin_id(Integer cont_erin_id) {
		this.cont_erin_id = cont_erin_id;
	}

	public String getCont_type() {
		return cont_type;
	}

	public void setCont_type(String cont_type) {
		this.cont_type = cont_type;
	}

	public String getCont_content() {
		return cont_content;
	}

	public void setCont_content(String cont_content) {
		this.cont_content = cont_content;
	}

	public String getCont_addname() {
		return cont_addname;
	}

	public void setCont_addname(String cont_addname) {
		this.cont_addname = cont_addname;
	}

	public String getCont_addtime() {
		return cont_addtime;
	}

	public void setCont_addtime(String cont_addtime) {
		this.cont_addtime = cont_addtime;
	}

	public String getCont_remark() {
		return cont_remark;
	}

	public void setCont_remark(String cont_remark) {
		this.cont_remark = cont_remark;
	}

	public String getCont_tablename() {
		return cont_tablename;
	}

	public void setCont_tablename(String cont_tablename) {
		this.cont_tablename = cont_tablename;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}
	
}
