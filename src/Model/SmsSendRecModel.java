package Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SmsSendRecModel {
	private Integer smsIndex;
	private String phoneNumber;
	private String smsContent;
	private Date smsTime;
	private String smsUser;
	private Integer gid;
	private Integer newFlag;
	private Integer ntype;

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

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public Integer getNewFlag() {
		return newFlag;
	}

	public void setNewFlag(Integer newFlag) {
		this.newFlag = newFlag;
	}

	public Integer getNtype() {
		return ntype;
	}

	public void setNtype(Integer ntype) {
		this.ntype = ntype;
	}

}
