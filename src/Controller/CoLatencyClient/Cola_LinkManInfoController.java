package Controller.CoLatencyClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import Model.CoAgencyLinkmanModel;
import Model.CoLatencyClientModel;
import bll.CoLatencyClient.CoLatencyClient_AddBll;

public class Cola_LinkManInfoController {
	private CoLatencyClientModel model = (CoLatencyClientModel) Executions
			.getCurrent().getArg().get("model");
	private CoLatencyClient_AddBll bll = new CoLatencyClient_AddBll();
	private List<CoAgencyLinkmanModel> list = bll.getLinkmanForAg(model
			.getCola_id());

	@Command
	@NotifyChange("list")
	public void add() {
		Map map = new HashMap<>();
		map.put("model", model);
		Window window = (Window) Executions.createComponents(
				"../CoLatencyClient/ColaClientLinkManAdd.zul", null, map);
		window.doModal();
		list = bll.getLinkmanForAg(model.getCola_id());
	}

	// 弹出更新联系人信息页面
	@Command
	@NotifyChange("list")
	public void linkUpdate(
			@BindingParam("model") final CoAgencyLinkmanModel ml) {
		Map map = new HashMap();
		map.put("linkmodel", ml);
		Window window = (Window) Executions.createComponents(
				"../CoLatencyClient/ColaClientLinkManUpdate.zul", null, map);
		window.doModal();
		list = bll.getLinkmanForAg(model.getCola_id());
	}

	public List<CoAgencyLinkmanModel> getList() {
		return list;
	}

	public void setList(List<CoAgencyLinkmanModel> list) {
		this.list = list;
	}

}
