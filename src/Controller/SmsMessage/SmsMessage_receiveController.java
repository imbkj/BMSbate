package Controller.SmsMessage;

import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;

import bll.SmsMessage.SmsMessageManagerBll;
import Model.SmsMessageReceiveModel;
import Util.UserInfo;

public class SmsMessage_receiveController extends SelectorComposer<Component> {
	private List<SmsMessageReceiveModel> smrList;
	private List<SmsMessageReceiveModel> smrPhoneList;
	private List<SmsMessageReceiveModel> cidList;
	private List<SmsMessageReceiveModel> gidList;
	private List<SmsMessageReceiveModel> companyList;
	private List<SmsMessageReceiveModel> nameList;

	private String phonenum;
	private String content;
	private String cid;
	private String gid;
	private String company;
	private String name;
	private Integer userId = Integer.valueOf(UserInfo.getUserid());

	private SmsMessageManagerBll bll = new SmsMessageManagerBll();

	public SmsMessage_receiveController() {
		// userId = 198;
		
		
		setSmrPhoneList();
		setCidList();
		setGidList();
		setCompanyList();
		setNameList();
		userId = null;
		setSmrList();
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
		
		setSmrList();

		gdList.setModel(new ListModelList<SmsMessageReceiveModel>(smrList));

	}
	

	public List<SmsMessageReceiveModel> getSmrList() {
		return smrList;
	}

	public void setSmrList() {

		this.smrList = new ListModelList<SmsMessageReceiveModel>(
				bll.getRecList(userId, cid, gid, company, name,
						phonenum, content));
	}

	public List<SmsMessageReceiveModel> getSmrPhoneList() {
		return smrPhoneList;
	}

	public void setSmrPhoneList() {

		this.smrPhoneList = new ListModelList<SmsMessageReceiveModel>(
				bll.getDisPhoneName(userId));
	}

	public List<SmsMessageReceiveModel> getCidList() {
		return cidList;
	}

	public void setCidList() {

		this.cidList = new ListModelList<SmsMessageReceiveModel>(
				bll.getDisCid(userId));
	}

	public List<SmsMessageReceiveModel> getGidList() {
		return gidList;
	}

	public void setGidList() {

		this.gidList = new ListModelList<SmsMessageReceiveModel>(
				bll.getDisGid(userId));
	}

	public List<SmsMessageReceiveModel> getCompanyList() {
		return companyList;
	}

	public void setCompanyList() {

		this.companyList = new ListModelList<SmsMessageReceiveModel>(
				bll.getDisCompany(userId));
	}

	public List<SmsMessageReceiveModel> getNameList() {
		return nameList;
	}

	public void setNameList() {

		this.nameList = new ListModelList<SmsMessageReceiveModel>(
				bll.getDisName(userId));
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
