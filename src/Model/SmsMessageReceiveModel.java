package Model;

import java.util.Date;

public class SmsMessageReceiveModel {
	private Integer smsIndex;
	private Date smsTime;
	private String sendNumber;
	private String receiver;
	private String smsContent;
	private Integer newFlag;
	private String lstyle;
	private String recvSetIndex;
	private Integer gid;
	private Integer smsstate;
	private Date remindTime;
	private Integer dggid;
	private Integer cid;
	private String coba_company;
	private String coba_shortname;
	private String emba_name;
	private String coba_namespell;
	private String emba_spell;

	public Integer getSmsIndex() {
		return smsIndex;
	}

	public void setSmsIndex(Integer smsIndex) {
		this.smsIndex = smsIndex;
	}

	public String getCoba_shortname() {
		return coba_shortname;
	}

	public void setCoba_shortname(String coba_shortname) {
		this.coba_shortname = coba_shortname;
	}

	public Date getSmsTime() {
		return smsTime;
	}

	public void setSmsTime(Date smsTime) {
		this.smsTime = smsTime;
	}

	public String getSendNumber() {
		return sendNumber;
	}

	public void setSendNumber(String sendNumber) {
		this.sendNumber = sendNumber;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public Integer getNewFlag() {
		return newFlag;
	}

	public void setNewFlag(Integer newFlag) {
		this.newFlag = newFlag;
	}

	public String getRecvSetIndex() {
		return recvSetIndex;
	}

	public void setRecvSetIndex(String recvSetIndex) {
		this.recvSetIndex = recvSetIndex;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public Integer getSmsstate() {
		return smsstate;
	}

	public void setSmsstate(Integer smsstate) {
		this.smsstate = smsstate;
	}

	public Date getRemindTime() {
		return remindTime;
	}

	public void setRemindTime(Date remindTime) {
		this.remindTime = remindTime;
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

	public String getLstyle() {
		return lstyle;
	}

	public void setLstyle(String lstyle) {
		this.lstyle = lstyle;
	}

}
