package Controller.Taskflow;

import impl.WorkflowCore.Core.WfFlowControlImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import Model.LoginModel;
import Model.TaskProcessLogViewModel;
import Model.TaskProcessModel;
import Model.TaskProcessViewModel;
import bll.Taskflow.TaskProcess_ViewBll;
import bll.Taskflow.Task_controlBll;

public class Task_InfoController {
	private List<TaskProcessViewModel> listView;
	private List<TaskProcessLogViewModel> listTP;
	private List<TaskProcessLogViewModel> listLog;
	private List<LoginModel> userList;
	private boolean canOp;
	private Integer id = Integer.parseInt(Executions.getCurrent().getArg()
			.get("id").toString());
	private TaskProcess_ViewBll bll;
	private WfFlowControlImpl impl;
	private Task_controlBll ctrlBll;
	private TaskProcessModel nowModel;
	private String btnLabel;
	private boolean btnDis;

	public Task_InfoController() {
		bll = new TaskProcess_ViewBll();
		listView = bll.getViewList(id);
		listTP = bll.geTaskProcessLogViewByTaliId(id);
		listLog = bll.geTaskProcessLogById(id);

		ctrlBll = new Task_controlBll();
		impl = new WfFlowControlImpl();
		nowModel = ctrlBll.getNowProcess(id);
		if (nowModel != null) {
			canOp = true;
			userList = impl.searchUserName(nowModel.getTapr_id());
			try {
				if (listTP.get(0).getTali_urgentState() == 0) {
					btnLabel = "催促办理";
					btnDis = false;
				} else {
					btnLabel = "已催办";
					btnDis = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			canOp = false;
		}
	}

	// 催促处理
	@Command("urge")
	public void urge(@ContextParam(ContextType.VIEW) Component view) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("tapr_id", String.valueOf(nowModel.getTapr_id()));
		Window window = (Window) Executions.createComponents("Task_UrgeOp.zul",
				view, map);
		window.doModal();
	}

	// 刷新列表
	@Command("refreshWin")
	public void refreshWin() {
		listLog = bll.geTaskProcessLogById(id);
		BindUtils.postNotifyChange(null, null, this, "listLog");
		btnLabel = "已催办";
		btnDis = true;
		BindUtils.postNotifyChange(null, null, this, "btnLabel");
		BindUtils.postNotifyChange(null, null, this, "btnDis");
	}

	public List<TaskProcessViewModel> getListView() {
		return listView;
	}

	public void setListView(List<TaskProcessViewModel> listView) {
		this.listView = listView;
	}

	public List<TaskProcessLogViewModel> getListTP() {
		return listTP;
	}

	public void setListTP(List<TaskProcessLogViewModel> listTP) {
		this.listTP = listTP;
	}

	public List<TaskProcessLogViewModel> getListLog() {
		return listLog;
	}

	public List<LoginModel> getUserList() {
		return userList;
	}

	public boolean isCanOp() {
		return canOp;
	}

	public String getBtnLabel() {
		return btnLabel;
	}

	public boolean isBtnDis() {
		return btnDis;
	}

}
