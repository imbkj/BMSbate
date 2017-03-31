package Model;

public class loginroleModel {
	private Integer log_id;
	private String log_name;
	private Integer lgp_id;
	private Integer rol_id;
	private String rol_name;
	private String rolId;// 使用in语法

	private Integer dep_id;
	private String dep_name;
	private Integer log_inure;
	private String lognameSpell;
	private String rolnameSpell;
	private String depnameSpell;
	private String log_email;

	private Integer type;// 判断是否根据页面限制查询部门

	public Integer getLog_id() {
		return log_id;
	}

	public void setLog_id(Integer log_id) {
		this.log_id = log_id;
	}

	public String getLog_name() {
		return log_name;
	}

	public void setLog_name(String log_name) {
		this.log_name = log_name;
	}

	public Integer getLgp_id() {
		return lgp_id;
	}

	public void setLgp_id(Integer lgp_id) {
		this.lgp_id = lgp_id;
	}

	public Integer getRol_id() {
		return rol_id;
	}

	public void setRol_id(Integer rol_id) {
		this.rol_id = rol_id;
	}

	public String getRol_name() {
		return rol_name;
	}

	public void setRol_name(String rol_name) {
		this.rol_name = rol_name;
	}

	public Integer getDep_id() {
		return dep_id;
	}

	public void setDep_id(Integer dep_id) {
		this.dep_id = dep_id;
	}

	public String getDep_name() {
		return dep_name;
	}

	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
	}

	public Integer getLog_inure() {
		return log_inure;
	}

	public void setLog_inure(Integer log_inure) {
		this.log_inure = log_inure;
	}

	public String getLognameSpell() {
		return lognameSpell;
	}

	public void setLognameSpell(String lognameSpell) {
		this.lognameSpell = lognameSpell;
	}

	public String getRolnameSpell() {
		return rolnameSpell;
	}

	public void setRolnameSpell(String rolnameSpell) {
		this.rolnameSpell = rolnameSpell;
	}

	public String getDepnameSpell() {
		return depnameSpell;
	}

	public void setDepnameSpell(String depnameSpell) {
		this.depnameSpell = depnameSpell;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getRolId() {
		return rolId;
	}

	public void setRolId(String rolId) {
		this.rolId = rolId;
	}

	public String getLog_email() {
		return log_email;
	}

	public void setLog_email(String log_email) {
		this.log_email = log_email;
	}

}
