package Model;

import java.util.Date;

public class SysMessageModel {

	private int syme_id;
	private String syme_title;
	private String syme_content;
	private String syme_addname;
	private String syme_addtime;
	private int syme_state;
	private Integer syme_log_id;
	private int syme_reply_id;
	private String syme_url;
	private String syme_para;

	private Integer symr_id;
	private int symr_syme_id;
	private Integer symr_log_id;
	private String symr_name;
	private Integer symr_readstate;
	private Date symr_readtime;
	private int symr_replystate;
	private Date symr_replytime;
	private int symr_state;
	

	private int pmte_id;
	private String pmte_model;
	private String pmte_class;
	private String pmte_content;
	private String pmte_addname;
	private String pmte_addtime;
	private String pmte_type;
	private int pmte_state;

	private int row;
	private int recicount;
	private String dep_name;
	
	private Integer gid;
	private Integer cid;
	private Integer smwr_id;
	private String smwr_table;
	private Integer smwr_tid;
	private String smwr_type;
	
	private Integer syme_readstate;//发件人阅读状态
	private Integer syme_replystate;//发件人回复状态
	
	private Integer email;
	private String emailtitle;
	private String statename;
	private String[][] fileurl;
	
	private String email_address;
	private String replyEmailAddress;
	
	public int getSyme_id() {
		return syme_id;
	}

	public void setSyme_id(int syme_id) {
		this.syme_id = syme_id;
	}

	public String getSyme_content() {
		return syme_content;
	}

	public void setSyme_content(String syme_content) {
		this.syme_content = syme_content;
	}

	public String getSyme_addname() {
		return syme_addname;
	}

	public void setSyme_addname(String syme_addname) {
		this.syme_addname = syme_addname;
	}

	public String getSyme_addtime() {
		return syme_addtime;
	}

	public void setSyme_addtime(String syme_addtime) {
		this.syme_addtime = syme_addtime;
	}

	public Integer getSymr_id() {
		return symr_id;
	}

	public void setSymr_id(Integer symr_id) {
		this.symr_id = symr_id;
	}

	public int getSymr_syme_id() {
		return symr_syme_id;
	}

	public void setSymr_syme_id(int symr_syme_id) {
		this.symr_syme_id = symr_syme_id;
	}

	public Integer getSymr_log_id() {
		return symr_log_id;
	}

	public void setSymr_log_id(Integer symr_log_id) {
		this.symr_log_id = symr_log_id;
	}

	public String getSymr_name() {
		return symr_name;
	}

	public void setSymr_name(String symr_name) {
		this.symr_name = symr_name;
	}

	public Integer getSymr_readstate() {
		return symr_readstate;
	}

	public void setSymr_readstate(Integer symr_readstate) {
		this.symr_readstate = symr_readstate;
	}

	public Date getSymr_readtime() {
		return symr_readtime;
	}

	public void setSymr_readtime(Date symr_readtime) {
		this.symr_readtime = symr_readtime;
	}

	public int getSymr_replystate() {
		return symr_replystate;
	}

	public void setSymr_replystate(int symr_replystate) {
		this.symr_replystate = symr_replystate;
	}

	public Date getSymr_replytime() {
		return symr_replytime;
	}

	public void setSymr_replytime(Date symr_replytime) {
		this.symr_replytime = symr_replytime;
	}

	public int getSymr_state() {
		return symr_state;
	}

	public void setSymr_state(int symr_state) {
		this.symr_state = symr_state;
	}

	public int getPmte_id() {
		return pmte_id;
	}

	public void setPmte_id(int pmte_id) {
		this.pmte_id = pmte_id;
	}

	public String getPmte_model() {
		return pmte_model;
	}

	public void setPmte_model(String pmte_model) {
		this.pmte_model = pmte_model;
	}

	public String getPmte_class() {
		return pmte_class;
	}

	public void setPmte_class(String pmte_class) {
		this.pmte_class = pmte_class;
	}

	public String getPmte_content() {
		return pmte_content;
	}

	public void setPmte_content(String pmte_content) {
		this.pmte_content = pmte_content;
	}

	public String getPmte_addname() {
		return pmte_addname;
	}

	public void setPmte_addname(String pmte_addname) {
		this.pmte_addname = pmte_addname;
	}

	public String getPmte_addtime() {
		return pmte_addtime;
	}

	public void setPmte_addtime(String pmte_addtime) {
		this.pmte_addtime = pmte_addtime;
	}

	public int getPmte_state() {
		return pmte_state;
	}

	public void setPmte_state(int pmte_state) {
		this.pmte_state = pmte_state;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getSyme_state() {
		return syme_state;
	}

	public void setSyme_state(int syme_state) {
		this.syme_state = syme_state;
	}

	public String getDep_name() {
		return dep_name;
	}

	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
	}

	public Integer getSyme_log_id() {
		return syme_log_id;
	}

	public void setSyme_log_id(Integer syme_log_id) {
		this.syme_log_id = syme_log_id;
	}

	public int getSyme_reply_id() {
		return syme_reply_id;
	}

	public void setSyme_reply_id(int syme_reply_id) {
		this.syme_reply_id = syme_reply_id;
	}

	public int getRecicount() {
		return recicount;
	}

	public void setRecicount(int recicount) {
		this.recicount = recicount;
	}

	public String getSyme_title() {
		return syme_title;
	}

	public void setSyme_title(String syme_title) {
		this.syme_title = syme_title;
	}

	public String getPmte_type() {
		return pmte_type;
	}

	public void setPmte_type(String pmte_type) {
		this.pmte_type = pmte_type;
	}

	public String getSyme_url() {
		return syme_url;
	}

	public void setSyme_url(String syme_url) {
		this.syme_url = syme_url;
	}

	public String getSyme_para() {
		return syme_para;
	}

	public void setSyme_para(String syme_para) {
		this.syme_para = syme_para;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public Integer getSmwr_id() {
		return smwr_id;
	}

	public void setSmwr_id(Integer smwr_id) {
		this.smwr_id = smwr_id;
	}

	public String getSmwr_table() {
		return smwr_table;
	}

	public void setSmwr_table(String smwr_table) {
		this.smwr_table = smwr_table;
	}

	
	public Integer getSmwr_tid() {
		return smwr_tid;
	}

	public void setSmwr_tid(Integer smwr_tid) {
		this.smwr_tid = smwr_tid;
	}

	public String getSmwr_type() {
		return smwr_type;
	}

	public void setSmwr_type(String smwr_type) {
		this.smwr_type = smwr_type;
	}

	public Integer getSyme_readstate() {
		return syme_readstate;
	}

	public void setSyme_readstate(Integer syme_readstate) {
		this.syme_readstate = syme_readstate;
	}

	public Integer getSyme_replystate() {
		return syme_replystate;
	}

	public void setSyme_replystate(Integer syme_replystate) {
		this.syme_replystate = syme_replystate;
	}

	public Integer getEmail() {
		return email;
	}

	public void setEmail(Integer email) {
		this.email = email;
	}

	public String getEmailtitle() {
		return emailtitle;
	}

	public void setEmailtitle(String emailtitle) {
		this.emailtitle = emailtitle;
	}

	public String getStatename() {
		return statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}

	public String[][] getFileurl() {
		return fileurl;
	}

	public void setFileurl(String[][] fileurl) {
		this.fileurl = fileurl;
	}

	public String getEmail_address() {
		return email_address;
	}

	public void setEmail_address(String email_address) {
		this.email_address = email_address;
	}

	public String getReplyEmailAddress() {
		return replyEmailAddress;
	}

	public void setReplyEmailAddress(String replyEmailAddress) {
		this.replyEmailAddress = replyEmailAddress;
	}
	
}
