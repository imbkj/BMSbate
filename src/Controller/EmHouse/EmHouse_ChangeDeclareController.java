package Controller.EmHouse;

import impl.WorkflowCore.WfOperateImpl;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

import bll.EmHouse.EmHouseChangeGjjConfirmImpl;
import bll.EmHouse.EmHouse_ChangeGjjBll;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.EmHouseChangeGJJModel;
import Util.DateStringChange;
import Util.UserInfo;

public class EmHouse_ChangeDeclareController {
	private EmHouseChangeGJJModel model = (EmHouseChangeGJJModel) Executions
			.getCurrent().getArg().get("model");

	private List<EmHouseChangeGJJModel> list = new ListModelList<>();

	EmHouse_ChangeGjjBll bll = new EmHouse_ChangeGjjBll();

	public EmHouse_ChangeDeclareController() {
		setList(model.getEhcg_id());
		if (list.size() > 0) {
			for (EmHouseChangeGJJModel em : list) {
				if (em.getEhcg_content() != null) {
					model.setEhcg_content(model.getEhcg_content() + ","
							+ em.getEhcg_content());
				}

			}

		}
	}

	@Command
	public void summit(@BindingParam("win") final Window win,
			@BindingParam("grid") final Grid grid) {

		Messagebox.show("确认变更数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							int i = 0;
							// 提交材料
							DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
							docOC.UpsubmitDoc(grid, model.getEhcg_id()
									.toString());
							if (model.getEhcg_tapr_id() != null) {

								// 提交数据
								WfBusinessService wfbs = new EmHouseChangeGjjConfirmImpl();
								WfOperateService wfs = new WfOperateImpl(wfbs);
								if (model.getEhcg_ifdeclare().equals(0)) {
									model.setEhcg_ifdeclare(2);
								} else if (model.getEhcg_ifdeclare().equals(2)) {
									model.setEhcg_ifdeclare(1);
									model.setEhcg_declareName(UserInfo
											.getUsername());
									model.setEhcg_declareTime(DateStringChange
											.Datestringnow("yyyy-MM-dd"));
								}

								Object[] obj = { "申报数据", model };
								String[] message = wfs.PassToNext(obj,
										model.getEhcg_tapr_id(),
										UserInfo.getUsername(), "",
										model.getCid(), "");
								if (message[0].equals("1")) {
									i=1;
								}
							}else {
								
							}
							if (i>0) {

								Messagebox.show("提交成功", "提示", Messagebox.OK,
										Messagebox.INFORMATION);
								win.detach();
							} else {
								Messagebox.show("操作失败", "提示", Messagebox.OK,
										Messagebox.ERROR);
							}
						}

					}
				});
	}

	public EmHouseChangeGJJModel getModel() {
		return model;
	}

	public void setModel(EmHouseChangeGJJModel model) {
		this.model = model;
	}

	public List<EmHouseChangeGJJModel> getList() {
		return list;
	}

	public void setList(Integer id) {
		this.list = bll.getGjjReList(id);
	}

}
