package Controller.EmBodyCheck;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import Model.EmBcItemGroupModel;
import Model.EmBcSetupModel;
import bll.CoLatencyClient.CoLatencyClient_AddBll;
import bll.EmBodyCheck.EmbcItem_SelectBll;
import bll.EmBodyCheck.Embc_SetupSellectBll;

public class EmBc_ItemGroupController {
	private String str = "";
	EmbcItem_SelectBll bll = new EmbcItem_SelectBll();
	// 获取项目组合信息
	private List<EmBcItemGroupModel> grouplist = new ListModelList<>();

	Embc_SetupSellectBll setupbll = new Embc_SetupSellectBll();
	// 获取机构信息
	private List<EmBcSetupModel> setuplist = setupbll.getEmBcSetupname("");

	CoLatencyClient_AddBll clientbll = new CoLatencyClient_AddBll();
	// 获取客服信息
	private List<String> clientlist = clientbll.getLoginInfo();

	Integer id = 0;

	public EmBc_ItemGroupController() {
		if (Executions.getCurrent().getArg().get("id")!=null) {
			id = Integer.valueOf(Executions.getCurrent().getArg().get("id")
					.toString());
		}
		
		String sql = "";
		if (id > 0) {
			sql = " and a.ebig_id=" + id;
		}
		grouplist=bll.getEmBcItemGroupInfo(sql);
	}

	@Command
	@NotifyChange("grouplist")
	public void search(@BindingParam("setup") String setup,
			@BindingParam("groupname") String groupname,
			@BindingParam("coba_name") String coba_name,
			@BindingParam("client") String client) {
		str = "";
		if (setup != null && !setup.equals("") && setup != "") {
			str = str + " and ebcs_hospital like '%" + setup + "%'";
		}
		if (groupname != null && !groupname.equals("") && groupname != "") {
			str = str + " and ebig_name like '%" + groupname + "%'";
		}
		if (coba_name != null && !coba_name.equals("") && coba_name != "") {
			str = str + " and coba_shortname like '%" + coba_name + "%'";
		}
		if (client != null && !client.equals("") && client != "") {
			str = str + " and coba_client= '" + client + "'";
		}
		grouplist = bll.getEmBcItemGroupInfo(str);
	}

	@Command
	@NotifyChange("grouplist")
	public void openZUL(@BindingParam("model") EmBcItemGroupModel model,
			@BindingParam("url") String url) {
		Map map = new HashMap<>();
		map.put("model", model);
		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
		grouplist = bll.getEmBcItemGroupInfo(str);
	}

	public List<EmBcItemGroupModel> getGrouplist() {
		return grouplist;
	}

	public void setGrouplist(List<EmBcItemGroupModel> grouplist) {
		this.grouplist = grouplist;
	}

	public List<EmBcSetupModel> getSetuplist() {
		return setuplist;
	}

	public void setSetuplist(List<EmBcSetupModel> setuplist) {
		this.setuplist = setuplist;
	}

	public List<String> getClientlist() {
		return clientlist;
	}

	public void setClientlist(List<String> clientlist) {
		this.clientlist = clientlist;
	}

}
