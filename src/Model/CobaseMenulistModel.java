package Model;

public class CobaseMenulistModel {
    /// come_id
    private Integer  come_id;
             /// 业务中心父菜单id
    private Integer  come_pid;
             /// 业务中心菜单名称
    private String  come_menuname;
             /// 业务中心菜单url
    private String  come_menuurl;
             /// come_wfbu_id
    private String  come_wfbu_id;
             /// 排序
    private Integer  come_order;
             /// 1、正在使用；0、取消
    private Integer  come_state;
             /// come_addname
    private String  come_addname;
             /// come_addtime
    private String  come_addtime;
	public Integer getCome_id() {
		return come_id;
	}
	public void setCome_id(Integer come_id) {
		this.come_id = come_id;
	}
	public Integer getCome_pid() {
		return come_pid;
	}
	public void setCome_pid(Integer come_pid) {
		this.come_pid = come_pid;
	}
	public String getCome_menuname() {
		return come_menuname;
	}
	public void setCome_menuname(String come_menuname) {
		this.come_menuname = come_menuname;
	}
	public String getCome_menuurl() {
		return come_menuurl;
	}
	public void setCome_menuurl(String come_menuurl) {
		this.come_menuurl = come_menuurl;
	}
	public String getCome_wfbu_id() {
		return come_wfbu_id;
	}
	public void setCome_wfbu_id(String come_wfbu_id) {
		this.come_wfbu_id = come_wfbu_id;
	}
	public Integer getCome_order() {
		return come_order;
	}
	public void setCome_order(Integer come_order) {
		this.come_order = come_order;
	}
	public Integer getCome_state() {
		return come_state;
	}
	public void setCome_state(Integer come_state) {
		this.come_state = come_state;
	}
	public String getCome_addname() {
		return come_addname;
	}
	public void setCome_addname(String come_addname) {
		this.come_addname = come_addname;
	}
	public String getCome_addtime() {
		return come_addtime;
	}
	public void setCome_addtime(String come_addtime) {
		this.come_addtime = come_addtime;
	}
    
}
