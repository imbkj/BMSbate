package Model;

public class PubEmailModel {

	private int id;
	private String puem_title;
	private String puem_content;
	private String puem_email;
	private String puem_replyto;
	private String puem_emailcc;
	private int puem_state;
	private String puem_sendtime;
	private String puem_truesendtime;
	private String puem_addname;
	private String puem_addtime;
	private int puem_ifHTML;
	private int puem_iffile;   //是否有文件
	private String fileurl;  //文件路径

	private String server_host;// 发送服务器
	private String server_port;// 发送端口
	private String server_username;// 用户名
	private String server_password;//密码
	private String server_f_address;//发件地址
	
	public int getPuem_iffile() {
		return puem_iffile;
	}

	public void setPuem_iffile(int puem_iffile) {
		this.puem_iffile = puem_iffile;
	}

	public String getFileurl() {
		return fileurl;
	}

	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPuem_title() {
		return puem_title;
	}

	public void setPuem_title(String puem_title) {
		this.puem_title = puem_title;
	}

	public String getPuem_content() {
		return puem_content;
	}

	public void setPuem_content(String puem_content) {
		this.puem_content = puem_content;
	}

	public String getPuem_email() {
		return puem_email;
	}

	public void setPuem_email(String puem_email) {
		this.puem_email = puem_email;
	}

	public String getPuem_replyto() {
		return puem_replyto;
	}

	public void setPuem_replyto(String puem_replyto) {
		this.puem_replyto = puem_replyto;
	}

	public String getPuem_emailcc() {
		return puem_emailcc;
	}

	public void setPuem_emailcc(String puem_emailcc) {
		this.puem_emailcc = puem_emailcc;
	}

	public int getPuem_state() {
		return puem_state;
	}

	public void setPuem_state(int puem_state) {
		this.puem_state = puem_state;
	}

	public String getPuem_sendtime() {
		return puem_sendtime;
	}

	public void setPuem_sendtime(String puem_sendtime) {
		this.puem_sendtime = puem_sendtime;
	}

	public String getPuem_truesendtime() {
		return puem_truesendtime;
	}

	public void setPuem_truesendtime(String puem_truesendtime) {
		this.puem_truesendtime = puem_truesendtime;
	}

	public String getPuem_addname() {
		return puem_addname;
	}

	public void setPuem_addname(String puem_addname) {
		this.puem_addname = puem_addname;
	}

	public String getPuem_addtime() {
		return puem_addtime;
	}

	public void setPuem_addtime(String puem_addtime) {
		this.puem_addtime = puem_addtime;
	}

	public int getPuem_ifHTML() {
		return puem_ifHTML;
	}

	public void setPuem_ifHTML(int puem_ifHTML) {
		this.puem_ifHTML = puem_ifHTML;
	}

	public String getServer_host() {
		return server_host;
	}

	public void setServer_host(String server_host) {
		this.server_host = server_host;
	}

	public String getServer_port() {
		return server_port;
	}

	public void setServer_port(String server_port) {
		this.server_port = server_port;
	}

	public String getServer_username() {
		return server_username;
	}

	public void setServer_username(String server_username) {
		this.server_username = server_username;
	}

	public String getServer_password() {
		return server_password;
	}

	public void setServer_password(String server_password) {
		this.server_password = server_password;
	}

	public String getServer_f_address() {
		return server_f_address;
	}

	public void setServer_f_address(String server_f_address) {
		this.server_f_address = server_f_address;
	}
	
}
