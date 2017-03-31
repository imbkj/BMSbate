package Model;

import java.util.Comparator;

public class EmbaseBusinessCenterModel implements
		Comparator<EmbaseBusinessCenterModel>, Cloneable {

	// / emce_id
	private Integer emce_id;
	// / 业务中心父菜单id
	private Integer emce_pid;
	// / 业务中心菜单名称
	private String emce_menuname;
	// / 业务中心菜单url
	private String emce_menuurl;
	// / 排序
	private Integer emce_order;
	// / 1、正在使用；0、取消
	private Integer emce_state;
	private String emce_addname;
	private String emce_addtime;
	private Integer emce_must;
	private Integer emce_onboard;
	private Integer emce_entry;
	private Integer emce_isforeigner;
	private Integer gid;
	private Integer emceid;
	private Boolean sel;
	private Boolean del;

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			return null;
		}

	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public Integer getEmce_id() {
		return emce_id;
	}

	public void setEmce_id(Integer emce_id) {
		this.emce_id = emce_id;
	}

	public Integer getEmce_pid() {
		return emce_pid;
	}

	public void setEmce_pid(Integer emce_pid) {
		this.emce_pid = emce_pid;
	}

	public String getEmce_menuname() {
		return emce_menuname;
	}

	public void setEmce_menuname(String emce_menuname) {
		this.emce_menuname = emce_menuname;
	}

	public String getEmce_menuurl() {
		return emce_menuurl;
	}

	public void setEmce_menuurl(String emce_menuurl) {
		this.emce_menuurl = emce_menuurl;
	}

	public Integer getEmce_order() {
		return emce_order;
	}

	public void setEmce_order(Integer emce_order) {
		this.emce_order = emce_order;
	}

	public Integer getEmce_state() {
		return emce_state;
	}

	public void setEmce_state(Integer emce_state) {
		this.emce_state = emce_state;
	}

	public String getEmce_addname() {
		return emce_addname;
	}

	public void setEmce_addname(String emce_addname) {
		this.emce_addname = emce_addname;
	}

	public String getEmce_addtime() {
		return emce_addtime;
	}

	public void setEmce_addtime(String emce_addtime) {
		this.emce_addtime = emce_addtime;
	}

	public Integer getEmceid() {
		return emceid;
	}

	public void setEmceid(Integer emceid) {
		this.emceid = emceid;
	}

	public Integer getEmce_must() {
		return emce_must;
	}

	public void setEmce_must(Integer emce_must) {
		this.emce_must = emce_must;
	}

	public Integer getEmce_onboard() {
		return emce_onboard;
	}

	public void setEmce_onboard(Integer emce_onboard) {
		this.emce_onboard = emce_onboard;
	}

	public Integer getEmce_entry() {
		return emce_entry;
	}

	public void setEmce_entry(Integer emce_entry) {
		this.emce_entry = emce_entry;
	}

	public Integer getEmce_isforeigner() {
		return emce_isforeigner;
	}

	public void setEmce_isforeigner(Integer emce_isforeigner) {
		this.emce_isforeigner = emce_isforeigner;
	}

	public Boolean getDel() {
		return del;
	}

	public void setDel(Boolean del) {
		this.del = del;
	}

	public Boolean getSel() {
		return sel;
	}

	public void setSel(Boolean sel) {
		this.sel = sel;
	}

	@Override
	public int compare(EmbaseBusinessCenterModel o1,
			EmbaseBusinessCenterModel o2) {
		// TODO Auto-generated method stub
		Integer i = o1.getEmce_pid().compareTo(o2.getEmce_pid());
		if (i.equals(0)) {
			Integer i2 = o1.getEmce_order().compareTo(o2.getEmce_order());
			i = i2;
		}

		return i;
	}

}
