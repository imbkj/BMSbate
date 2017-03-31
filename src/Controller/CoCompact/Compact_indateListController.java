package Controller.CoCompact;

import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;

import bll.CoCompact.cocompact_indateBll;

import Model.CoCompactModel;
import Util.UserInfo;

public class Compact_indateListController {

	private String title = "";
	private List<CoCompactModel> list = new ListModelList<>();
	private List<CoCompactModel> clientlist = new ListModelList<>();
	private cocompact_indateBll bll = new cocompact_indateBll();
	private String client = "";
	private String company = "";

	public Compact_indateListController() {
		if (Executions.getCurrent().getArg().get("p1") != null) {
			title = Executions.getCurrent().getArg().get("p1").toString();
		}
		if (title.equals("合同已过期")) {
			list = bll.getlist(-1, null, null, UserInfo.getUserid());
			clientlist=bll.getclientlist(-1, UserInfo.getUserid());
		} else if (title.equals("合同15日内到期")) {
			list = bll.getlist(15, null, null, UserInfo.getUserid());
			clientlist=bll.getclientlist(-1, UserInfo.getUserid());
		} else if (title.equals("合同20日内到期")) {
			list = bll.getlist(20, null, null, UserInfo.getUserid());
			clientlist=bll.getclientlist(-1, UserInfo.getUserid());
		} else if (title.equals("合同50日内到期")) {
			list = bll.getlist(50, null, null, UserInfo.getUserid());
			clientlist=bll.getclientlist(-1, UserInfo.getUserid());
		}
	}

	@Command
	@NotifyChange("list")
	public void search(){
		if (title.equals("合同已过期")) {
			list = bll.getlist(-1, client, company, UserInfo.getUserid());
			
		} else if (title.equals("合同15日内到期")) {
			list = bll.getlist(15, client, company, UserInfo.getUserid());
			
		} else if (title.equals("合同20日内到期")) {
			list = bll.getlist(20, client, company, UserInfo.getUserid());
			
		} else if (title.equals("合同50日内到期")) {
			list = bll.getlist(50, client, company, UserInfo.getUserid());
			
		}
	}
	
	public List<CoCompactModel> getList() {
		return list;
	}

	public void setList(List<CoCompactModel> list) {
		this.list = list;
	}

	public List<CoCompactModel> getClientlist() {
		return clientlist;
	}

	public void setClientlist(List<CoCompactModel> clientlist) {
		this.clientlist = clientlist;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

}
