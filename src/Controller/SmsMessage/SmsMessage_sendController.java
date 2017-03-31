package Controller.SmsMessage;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;
import Model.SmsMessageSendModel;
import Util.UserInfo;
import bll.SmsMessage.SmsMessageManagerBll;

public class SmsMessage_sendController extends SelectorComposer<Component> {
	private List<SmsMessageSendModel> smSendList;
	private List<SmsMessageSendModel> smSendPhoneList;
	private List<SmsMessageSendModel> cidList;
	private List<SmsMessageSendModel> gidList;
	private List<SmsMessageSendModel> companyList;
	private List<SmsMessageSendModel> nameList;

	private String phonenum;
	private String content;
	private String cid;
	private String gid;
	private String company;
	private String name;
	private Integer userId = Integer.valueOf(UserInfo.getUserid());

	private SmsMessageManagerBll bll = new SmsMessageManagerBll();

	public SmsMessage_sendController() {
		userId = 226;
		setSmSendList();
		setSmSendPhoneList();
		setCidList();
		setGidList();
		setCompanyList();
		setNameList();
	}

	@Wire
	Combobox cbPhoneNum;
	@Wire
	Textbox tbContent;
	@Wire
	Combobox cbCid;
	@Wire
	Combobox cbCompany;
	@Wire
	Combobox cbGId;
	@Wire
	Combobox cbName;
	@Wire
	Grid gdList;

	@Listen("onClick=#btnSubmit")
	public void searchList() {
		content = tbContent.getValue();
		phonenum = cbPhoneNum.getValue();
		cid = cbCid.getValue();
		company = cbCompany.getValue();
		gid = cbGId.getValue();
		name = cbName.getValue();
		
		setSmSendList();

		gdList.setModel(new ListModelList<SmsMessageSendModel>(smSendList));

	}

	public List<SmsMessageSendModel> getSmSendList() {
		return smSendList;
	}

	public void setSmSendList() {

		this.smSendList = new ListModelList<SmsMessageSendModel>(
				bll.getSendList(userId, cid, gid, company, name, phonenum,
						content));
	}

	public List<SmsMessageSendModel> getSmSendPhoneList() {
		return smSendPhoneList;
	}

	public void setSmSendPhoneList() {

		this.smSendPhoneList = new ListModelList<SmsMessageSendModel>(
				bll.getSendPhoneName(userId));
	}

	public List<SmsMessageSendModel> getCidList() {
		return cidList;
	}

	public void setCidList() {

		this.cidList = new ListModelList<SmsMessageSendModel>(
				bll.getSendDisCid(userId));
	}

	public List<SmsMessageSendModel> getGidList() {
		return gidList;
	}

	public void setGidList() {

		this.gidList = new ListModelList<SmsMessageSendModel>(
				bll.getSendDisGid(userId));
	}

	public List<SmsMessageSendModel> getCompanyList() {
		return companyList;
	}

	public void setCompanyList() {

		this.companyList = new ListModelList<SmsMessageSendModel>(
				bll.getSendDisCompany(userId));
	}

	public List<SmsMessageSendModel> getNameList() {
		return nameList;
	}

	public void setNameList() {

		this.nameList = new ListModelList<SmsMessageSendModel>(
				bll.getSendDisName(userId));
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
