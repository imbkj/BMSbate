package Controller.Taskflow;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Util.UserInfo;
import bll.Taskflow.Task_controlBll;

public class Task_StopTaskController {
	private final int tali_id = Integer.parseInt(Executions.getCurrent()
			.getArg().get("tali_id").toString());
	private final String tali_name = Executions.getCurrent().getArg()
			.get("tali_name").toString();
	private Task_controlBll bll;
	private String remark = "";

	public Task_StopTaskController() {

	}

	@Command
	public void stopTask(@BindingParam("win") Window win) {
		try {
			if (Messagebox.show("确认需要终止编号为：" + tali_id + "的任务单吗？", "操作提示",
					Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
				bll = new Task_controlBll();
				int i = bll.stopTask(tali_id, remark, UserInfo.getUsername());
				if (i == 1) {
					Messagebox.show("操作成功，请自行刷新列表。", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else if (i == -1) {
					Messagebox.show("该步骤已处理,无法继续操作。", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
				} else if (i == -2) {
					Messagebox.show("该步骤不允许终止。", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
				} else {
					Messagebox.show("操作失败。", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("操作出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
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
