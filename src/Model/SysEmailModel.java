package Model;

import java.util.List;

public class SysEmailModel {
	private int pfil_id;
	private String pfil_title;
	private String pfil_content;
	private String pfil_addname;
	private String pfil_addtime;
	private String pfil_email;
	private String pfil_remark;
	private List<SysEmailFileModel> flist;
	public int getPfil_id() {
		return pfil_id;
	}
	public void setPfil_id(int pfil_id) {
		this.pfil_id = pfil_id;
	}
	public String getPfil_title() {
		return pfil_title;
	}
	public void setPfil_title(String pfil_title) {
		this.pfil_title = pfil_title;
	}
	public String getPfil_content() {
		return pfil_content;
	}
	public void setPfil_content(String pfil_content) {
		this.pfil_content = pfil_content;
	}
	public String getPfil_addname() {
		return pfil_addname;
	}
	public void setPfil_addname(String pfil_addname) {
		this.pfil_addname = pfil_addname;
	}
	public String getPfil_addtime() {
		return pfil_addtime;
	}
	public void setPfil_addtime(String pfil_addtime) {
		this.pfil_addtime = pfil_addtime;
	}
	public List<SysEmailFileModel> getFlist() {
		return flist;
	}
	public void setFlist(List<SysEmailFileModel> flist) {
		this.flist = flist;
	}
	public String getPfil_email() {
		return pfil_email;
	}
	public void setPfil_email(String pfil_email) {
		this.pfil_email = pfil_email;
	}
	public String getPfil_remark() {
		return pfil_remark;
	}
	public void setPfil_remark(String pfil_remark) {
		this.pfil_remark = pfil_remark;
	}
	
	
}
