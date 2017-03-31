package Model;

import java.util.List;

/*
 * 创建人：张志强
 * 创建时间：2013-9-12
 * 用途：Login表基本信息
 */
public class LoginModel implements Comparable<LoginModel>,Cloneable {
	private int log_id;// login表ID
	private String log_name;// 姓名
	private int log_teamleader;// 领导ID
	private String log_tel;// 座机号
	private String log_sex;// 性别
	private String log_pws;// 密码
	private String log_email;// 邮箱地址
	private String log_mobile;// 手机号码
	private String log_intime;//入职时间
	private String log_leader;//上级领导
	private int dep_id;// 所属部门
	private String log_spell;		//拼音简称
	private String dep_name;
	private int log_pid;
	private int role_id;
	private String rol_name;
	private String log_state;
	private Integer log_inure;
	private List<LoginModel> loginlist;
	public LoginModel(int log_id, String log_name, int log_teamleader,
			String log_tel, String log_sex, String log_pws, String log_email,
			String log_mobile, String log_intime, int dep_id) {
		super();
		this.log_id = log_id;
		this.log_name = log_name;
		this.log_teamleader = log_teamleader;
		this.log_tel = log_tel;
		this.log_sex = log_sex;
		this.log_pws = log_pws;
		this.log_email = log_email;
		this.log_mobile = log_mobile;
		this.log_intime = log_intime;
		this.dep_id = dep_id;
	}
	
	public LoginModel(int log_id, String log_name, int log_teamleader,
			String log_tel, String log_sex, String log_pws, String log_email,
			String log_mobile, String log_intime, int dep_id,int role_id) {
		super();
		this.log_id = log_id;
		this.log_name = log_name;
		this.log_teamleader = log_teamleader;
		this.log_tel = log_tel;
		this.log_sex = log_sex;
		this.log_pws = log_pws;
		this.log_email = log_email;
		this.log_mobile = log_mobile;
		this.log_intime = log_intime;
		this.dep_id = dep_id;
		this.role_id = role_id;
	}
	
	public LoginModel(int log_id, String log_name) {
		super();
		this.log_id = log_id;
		this.log_name = log_name;
	}
	public LoginModel() {
		super();
	}
	
	
	
	public LoginModel(int log_id, String log_name, int log_teamleader,
			String log_tel, String log_sex, String log_pws, String log_email,
			String log_mobile, String log_intime, int dep_id, String log_spell,
			String dep_name) {
		super();
		this.log_id = log_id;
		this.log_name = log_name;
		this.log_teamleader = log_teamleader;
		this.log_tel = log_tel;
		this.log_sex = log_sex;
		this.log_pws = log_pws;
		this.log_email = log_email;
		this.log_mobile = log_mobile;
		this.log_intime = log_intime;
		this.dep_id = dep_id;
		this.log_spell = log_spell;
		this.dep_name = dep_name;
	}
	
	
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			return null;
		}

	}
	
	public int getLog_id() {
		return log_id;
	}
	public void setLog_id(int log_id) {
		this.log_id = log_id;
	}
	public String getLog_name() {
		return log_name;
	}
	public void setLog_name(String log_name) {
		this.log_name = log_name;
	}
	public int getLog_teamleader() {
		return log_teamleader;
	}
	public void setLog_teamleader(int log_teamleader) {
		this.log_teamleader = log_teamleader;
	}
	public String getLog_tel() {
		return log_tel;
	}
	public void setLog_tel(String log_tel) {
		this.log_tel = log_tel;
	}
	public String getLog_sex() {
		return log_sex;
	}
	public void setLog_sex(String log_sex) {
		this.log_sex = log_sex;
	}
	public String getLog_pws() {
		return log_pws;
	}
	public void setLog_pws(String log_pws) {
		this.log_pws = log_pws;
	}
	public String getLog_email() {
		return log_email;
	}
	public void setLog_email(String log_email) {
		this.log_email = log_email;
	}
	public String getLog_mobile() {
		return log_mobile;
	}
	public void setLog_mobile(String log_mobile) {
		this.log_mobile = log_mobile;
	}
	public String getLog_intime() {
		return log_intime;
	}
	public void setLog_intime(String log_intime) {
		this.log_intime = log_intime;
	}
	public int getDep_id() {
		return dep_id;
	}
	public void setDep_id(int dep_id) {
		this.dep_id = dep_id;
	}
	public String getDep_name() {
		return dep_name;
	}
	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
	}
	public String getLog_spell() {
		return log_spell;
	}
	public void setLog_spell(String log_spell) {
		this.log_spell = log_spell;
	}
	public List<LoginModel> getLoginlist() {
		return loginlist;
	}
	public void setLoginlist(List<LoginModel> loginlist) {
		this.loginlist = loginlist;
	}
	public int getLog_pid() {
		return log_pid;
	}
	public void setLog_pid(int log_pid) {
		this.log_pid = log_pid;
	}

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	public String getRol_name() {
		return rol_name;
	}

	public void setRol_name(String rol_name) {
		this.rol_name = rol_name;
	}

	public String getLog_leader() {
		return log_leader;
	}

	public void setLog_leader(String log_leader) {
		this.log_leader = log_leader;
	}

	public String getLog_state() {
		return log_state;
	}

	public void setLog_state(String log_state) {
		this.log_state = log_state;
	}

	public Integer getLog_inure() {
		return log_inure;
	}

	public void setLog_inure(Integer log_inure) {
		this.log_inure = log_inure;
	}

	@Override
	public int compareTo(LoginModel o) {
		// TODO Auto-generated method stub
		
		return this.log_name.compareTo(o.log_name);
	}
	
	
}
