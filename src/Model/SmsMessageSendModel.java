package Model;

import java.util.Date;

public class SmsMessageSendModel {
	private Integer smsIndex;
	private String phoneNumber;
	private String smsContent;
	private Date smsTime;
	private String smsUser;
	private Integer status;
	private Integer newFlag;
	private Integer userDefineNo;
	private Integer sentSetIndex;
	private Integer gid;
	private Integer dggid;
	private Integer cid;
	private String coba_company;
	private String emba_name;
	private String coba_namespell;
	private String emba_spell;

	public Integer getSmsIndex() {
		return smsIndex;
	}

	public void setSmsIndex(Integer smsIndex) {
		this.smsIndex = smsIndex;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public Date getSmsTime() {
		return smsTime;
	}

	public void setSmsTime(Date smsTime) {
		this.smsTime = smsTime;
	}

	public String getSmsUser() {
		return smsUser;
	}

	public void setSmsUser(String smsUser) {
		this.smsUser = smsUser;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getNewFlag() {
		return newFlag;
	}

	public void setNewFlag(Integer newFlag) {
		this.newFlag = newFlag;
	}

	public Integer getUserDefineNo() {
		return userDefineNo;
	}

	public void setUserDefineNo(Integer userDefineNo) {
		this.userDefineNo = userDefineNo;
	}

	public Integer getSentSetIndex() {
		return sentSetIndex;
	}

	public void setSentSetIndex(Integer sentSetIndex) {
		this.sentSetIndex = sentSetIndex;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public Integer getDggid() {
		return dggid;
	}

	public void setDggid(Integer dggid) {
		this.dggid = dggid;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getCoba_company() {
		return coba_company;
	}

	public void setCoba_company(String coba_company) {
		this.coba_company = coba_company;
	}

	public String getEmba_name() {
		return emba_name;
	}

	public void setEmba_name(String emba_name) {
		this.emba_name = emba_name;
	}

	public String getCoba_namespell() {
		return coba_namespell;
	}

	public void setCoba_namespell(String coba_namespell) {
		this.coba_namespell = coba_namespell;
	}

	public String getEmba_spell() {
		return emba_spell;
	}

	public void setEmba_spell(String emba_spell) {
		this.emba_spell = emba_spell;
	}

}
