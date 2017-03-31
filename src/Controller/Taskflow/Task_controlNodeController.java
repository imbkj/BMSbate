package Controller.Taskflow;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.Taskflow.TaskProcess_ViewBll;
import bll.Taskflow.Task_controlBll;

import Model.TaskProcessViewModel;
import Model.WfNodeModel;
import Util.UserInfo;

public class Task_controlNodeController {
	private final int tali_id = Integer.parseInt(Executions.getCurrent()
			.getArg().get("tali_id").toString());
	private final String tali_name = Executions.getCurrent().getArg()
			.get("tali_name").toString();
	private List<TaskProcessViewModel> listView;
	private Task_controlBll bll;
	private List<WfNodeModel> nodeList;
	private int appointid = 0;
	private String appointcon = "";
	private String remark = "";

	public Task_controlNodeController() {
		TaskProcess_ViewBll viewbll = new TaskProcess_ViewBll();
		listView = viewbll.getViewList(tali_id);
		bll = new Task_controlBll();
		nodeList = bll.getNode(tali_id);
	}

	@Command
	public void ctrlNode(@BindingParam("m") WfNodeModel m,
			@BindingParam("win") Window win) {
		try {
			if (appointid < -2) {
				Messagebox.show("指定执行ID,输入有误。", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
			} else if (m.getNowNode() > 0) {
				Messagebox.show("节点未调整,无需提交。", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
			} else {
				if (Messagebox.show("确认需要调整流程吗？", "操作提示", Messagebox.YES
						| Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
					int i = bll.ctrlNode(tali_id, m, appointid, appointcon,
							remark, UserInfo.getUsername());
					if (i == 1) {
						Messagebox.show("操作成功。", "操作提示", Messagebox.OK,
								Messagebox.INFORMATION);
						win.detach();
					} else {
						Messagebox.show("操作失败。", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("操作出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public List<TaskProcessViewModel> getListView() {
		return listView;
	}

	public List<WfNodeModel> getNodeList() {
		return nodeList;
	}

	public int getAppointid() {
		return appointid;
	}

	public void setAppointid(int appointid) {
		this.appointid = appointid;
	}

	public String getAppointcon() {
		return appointcon;
	}

	public void setAppointcon(String appointcon) {
		this.appointcon = appointcon;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getTali_id() {
		return tali_id;
	}

	public String getTali_name() {
		return tali_name;
	}

}
