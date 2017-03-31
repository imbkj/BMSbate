package Controller.EmFinanceManage;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import Model.CoBaseModel;
import Model.SystLogModel;
import bll.EmFinanceManage.Finance_CFMLogBll;

public class Finance_CFMLogController {

	private List<SystLogModel> slist = new ListModelList<>();
	private List<CoBaseModel> clientList = new ListModelList<>();
	private List<CoBaseModel> developerList = new ListModelList<>();
	private String company;
	private String client;
	private String developer;
	private Date date1;
	private Date date2;

	private Finance_CFMLogBll bll = new Finance_CFMLogBll();

	public Finance_CFMLogController() {
		date1 = new Date();
		date2 = new Date();
		slist = bll.getLogList(null, null, null, date1, date2);
		clientList = bll.getclient();
		developerList = bll.getdeveloper();
	}

	@Command
	@NotifyChange("slist")
	public void search() {
		slist = bll.getLogList(company, client, developer, date1, date2);
	}

	@Command
	public void info(@BindingParam("a") SystLogModel model) {
		Map map = new HashMap<>();
		map.put("cid", model.getCid());
		map.put("addtime", model.getAddtime());
		Window window = (Window) Executions.createComponents(
				"Finance_CFMLogInfo.zul", null, map);
		window.doModal();
	}

	public List<SystLogModel> getSlist() {
		return slist;
	}

	public void setSlist(List<SystLogModel> slist) {
		this.slist = slist;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getDeveloper() {
		return developer;
	}

	public void setDeveloper(String developer) {
		this.developer = developer;
	}

	public Date getDate1() {
		return date1;
	}

	public void setDate1(Date date1) {
		this.date1 = date1;
	}

	public Date getDate2() {
		return date2;
	}

	public void setDate2(Date date2) {
		this.date2 = date2;
	}

	public List<CoBaseModel> getClientList() {
		return clientList;
	}

	public void setClientList(List<CoBaseModel> clientList) {
		this.clientList = clientList;
	}

	public List<CoBaseModel> getDeveloperList() {
		return developerList;
	}

	public void setDeveloperList(List<CoBaseModel> developerList) {
		this.developerList = developerList;
	}

}
