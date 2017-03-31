package Controller.Taskflow;

import impl.WorkflowCore.Core.WfFlowControlImpl;

import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;

import bll.Taskflow.TaskProcess_ViewBll;
import bll.Taskflow.Task_controlBll;
import bll.Workflow.WfNode_DisUserBll;

import Model.LoginModel;
import Model.TaskProcessModel;
import Model.TaskProcessViewModel;

public class Task_SelOpNameController {
	private final int tali_id = Integer.parseInt(Executions.getCurrent()
			.getArg().get("tali_id").toString());
	private final String tali_name = Executions.getCurrent().getArg()
			.get("tali_name").toString();
	private List<TaskProcessViewModel> listView;
	private List<LoginModel> userList;
	private List<LoginModel> selUserList;
	private Task_controlBll bll;
	private TaskProcessModel nowModel;

	public Task_SelOpNameController() {
		try {
			TaskProcess_ViewBll viewbll = new TaskProcess_ViewBll();
			listView = viewbll.getViewList(tali_id);
			bll = new Task_controlBll();
			nowModel = bll.getNowProcess(tali_id);
			WfFlowControlImpl impl = new WfFlowControlImpl();
			userList = impl.searchUserName(nowModel.getTapr_id());
			WfNode_DisUserBll userBll = new WfNode_DisUserBll();
			selUserList = userBll.getUserList(nowModel.getTapr_wfno_id());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 设置操作人
	@Command
	public void setOpName(@BindingParam("name") String name) {
		try {
			if (name != null && !"".equals(name)) {
				int i = bll.setOpName(nowModel.getTapr_id(), name);
				if (i == 1) {
					WfFlowControlImpl impl = new WfFlowControlImpl();
					userList = impl.searchUserName(nowModel.getTapr_id());
					BindUtils.postNotifyChange(null, null, this, "userList");
					Messagebox.show("操作成功。", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
				} else {
					Messagebox.show("操作失败。", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				Messagebox.show("请选择操作人。", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("操作出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public int getTali_id() {
		return tali_id;
	}

	public String getTali_name() {
		return tali_name;
	}

	public List<LoginModel> getUserList() {
		return userList;
	}

	public List<TaskProcessViewModel> getListView() {
		return listView;
	}

	public List<LoginModel> getSelUserList() {
		return selUserList;
	}

}
