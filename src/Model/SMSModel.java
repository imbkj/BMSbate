package Model;

public class SMSModel {
	private String mobile;
	private String idcard;
	private String sendname;
	private String content;
	private String sendtime;
	private String sms_class;
	private String sms_content;
	private String sms_id;
	private String sms_state;
	private Integer email_flag;
	private String email;
	private Integer gid;
	private String title;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSendname() {
		return sendname;
	}

	public void setSendname(String sendname) {
		this.sendname = sendname;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSendtime() {
		return sendtime;
	}

	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}

	public String getSms_class() {
		return sms_class;
	}

	public void setSms_class(String sms_class) {
		this.sms_class = sms_class;
	}

	public String getSms_content() {
		return sms_content;
	}

	public void setSms_content(String sms_content) {
		this.sms_content = sms_content;
	}

	public String getSms_id() {
		return sms_id;
	}

	public void setSms_id(String sms_id) {
		this.sms_id = sms_id;
	}

	public String getSms_state() {
		return sms_state;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public Integer getEmail_flag() {
		return email_flag;
	}

	public void setEmail_flag(Integer email_flag) {
		this.email_flag = email_flag;
	}
	
	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public void setSms_state(int sms_state) {
		if (sms_state==2)
		{
			this.sms_state="已发送";
		}
		else
		{
			this.sms_state="发送中";
		}
		
	}
	
}
