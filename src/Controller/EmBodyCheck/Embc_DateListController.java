package Controller.EmBodyCheck;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.ListModelList;

import Model.EmBodyCheckDateListModel;
import Model.LoginModel;
import Util.FileOperate;
import bll.EmBodyCheck.EmBc_DateListSelectBll;

public class Embc_DateListController {
	private EmBc_DateListSelectBll bll = new EmBc_DateListSelectBll();
	private List<EmBodyCheckDateListModel> list = new ListModelList<>();

	private List<LoginModel> clientlist = bll.getClientList();
	private FileOperate fo = new FileOperate();

	@Command
	@NotifyChange("list")
	public void search(@BindingParam("company") String company,
			@BindingParam("name") String name,
			@BindingParam("client") String client) {
		String str = "";
		if (company != null && !company.equals("") && company != "") {
			str = str + " and coba_shortname like '%" + company + "%'";
		}
		if (name != null && !name.equals("") && name != "") {
			str = str + " and emba_name = '" + name + "'";
		}
		if (client != null && !client.equals("") && client != "") {
			str = str + " and coba_client like '" + client + "'";
		}
		list = bll.getEmBodyCheckDateListInfo(str);
	}

	@Command
	public void export() {
		if (list.size() > 0) {
			
			bll.createFile(list);

			
		}
	}

	public List<EmBodyCheckDateListModel> getList() {
		return list;
	}

	public void setList(List<EmBodyCheckDateListModel> list) {
		this.list = list;
	}

	public List<LoginModel> getClientlist() {
		return clientlist;
	}

	public void setClientlist(List<LoginModel> clientlist) {
		this.clientlist = clientlist;
	}

}
