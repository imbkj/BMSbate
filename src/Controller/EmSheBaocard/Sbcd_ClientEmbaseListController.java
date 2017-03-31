package Controller.EmSheBaocard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import Model.EmShebaoCardInfoModel;
import Model.EmbaseModel;
import bll.EmSheBaocard.EmShebaoCardInfoSelectBll;

public class Sbcd_ClientEmbaseListController {
	private EmShebaoCardInfoSelectBll bll = new EmShebaoCardInfoSelectBll();
	private List<EmbaseModel> list = bll
			.getEmbaseList(" and emba_state=1 and emsc_computerid is not null");
	private List<String> clientlist = bll.getClientList();
	private EmbaseModel model = new EmbaseModel();

	// 查询
	@Command
	@NotifyChange("list")
	public void search() {
		list = bll.getEmbaseList(model);
	}

	@Command
	public void openzul(@BindingParam("model") final EmbaseModel model) {
		List<EmShebaoCardInfoModel> list = bll
				.getEmShebaoCardInfoList("and a.gid=" + model.getGid());
		if (list.size() > 0) {
			Messagebox.show("该员工已有社保卡信息，是否继续新增?", "操作提示", new Messagebox.Button[] {
					Messagebox.Button.OK, Messagebox.Button.CANCEL },
					Messagebox.QUESTION,
					new EventListener<Messagebox.ClickEvent>() {
						@Override
						public void onEvent(ClickEvent event) throws Exception {
							Map map = new HashMap<>();
							map.put("gid", model.getGid() + "");
							Window window = (Window) Executions.createComponents(
									"Sbcd_ClientAdd.zul", null, map);
							window.doModal();
						}
			});
		}
		else
		{
			Map map = new HashMap<>();
			map.put("gid", model.getGid() + "");
			Window window = (Window) Executions.createComponents(
					"Sbcd_ClientAdd.zul", null, map);
			window.doModal();
		}
		
	}

	public List<EmbaseModel> getList() {
		return list;
	}

	public void setList(List<EmbaseModel> list) {
		this.list = list;
	}

	public List<String> getClientlist() {
		return clientlist;
	}

	public void setClientlist(List<String> clientlist) {
		this.clientlist = clientlist;
	}

	public EmbaseModel getModel() {
		return model;
	}

	public void setModel(EmbaseModel model) {
		this.model = model;
	}
}
