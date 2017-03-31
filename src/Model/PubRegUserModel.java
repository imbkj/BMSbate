package Model;
/*
 * 创建人：张志强
 * 创建时间：2013-9-12
 * 用途：Login表基本信息
 */
public class PubRegUserModel {
	private String log_spell;//姓名拼音简写
	private String log_ip;//IP地址
	private String log_name;//姓名
	private int log_teamleader;//领导ID
	private int dep_id;//所属部门
	private String log_tel;//座机号
	private int log_inure;//审核状态
	private String log_sex;//性别
	private String log_pws;//密码
	private String log_email;//邮箱地址
	private String log_mobile;//手机号码
	private String addtime;//添加时间
	private String addname;//添加人
	private String log_intime;//入职时间
	public String getLog_spell() {
		return log_spell;
	}
	public void setLog_spell(String log_spell) {
		this.log_spell = log_spell;
	}
	public String getLog_ip() {
		return log_ip;
	}
	public void setLog_ip(String log_ip) {
		this.log_ip = log_ip;
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
	public int getDep_id() {
		return dep_id;
	}
	public void setDep_id(int dep_id) {
		this.dep_id = dep_id;
	}
	public String getLog_tel() {
		return log_tel;
	}
	public void setLog_tel(String log_tel) {
		this.log_tel = log_tel;
	}
	public int getLog_inure() {
		return log_inure;
	}
	public void setLog_inure(int log_inure) {
		this.log_inure = log_inure;
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
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getAddname() {
		return addname;
	}
	public void setAddname(String addname) {
		this.addname = addname;
	}
	public String getLog_intime() {
		return log_intime;
	}
	public void setLog_intime(String log_intime) {
		this.log_intime = log_intime;
	}
	public PubRegUserModel(String log_spell, String log_ip, String log_name,
			int log_teamleader, int dep_id, String log_tel, int log_inure,
			String log_sex, String log_pws, String log_email,
			String log_mobile, String addtime, String addname, String log_intime) {
		super();
		this.log_spell = log_spell;
		this.log_ip = log_ip;
		this.log_name = log_name;
		this.log_teamleader = log_teamleader;
		this.dep_id = dep_id;
		this.log_tel = log_tel;
		this.log_inure = log_inure;
		this.log_sex = log_sex;
		this.log_pws = log_pws;
		this.log_email = log_email;
		this.log_mobile = log_mobile;
		this.addtime = addtime;
		this.addname = addname;
		this.log_intime = log_intime;
	}
}
