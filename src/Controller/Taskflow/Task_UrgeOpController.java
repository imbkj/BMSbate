package Controller.Taskflow;

import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.Taskflow.Task_controlBll;

import Util.UserInfo;

public class Task_UrgeOpController {
	private int tapr_id = Integer.parseInt(Executions.getCurrent().getArg()
			.get("tapr_id").toString());
	private String reason;

	public Task_UrgeOpController() {

	}

	@Command("addUrge")
	public void addUrge(@BindingParam("win") Window win,
			@ContextParam(ContextType.VIEW) Component view) {
		try {
			if (reason != null && !"".equals(reason.trim())) {
				Task_controlBll bll = new Task_controlBll();
				int i = bll.urgeOp(tapr_id, reason, UserInfo.getUsername());
				if (i == 1) {
					Messagebox.show("催促处理，操作成功。", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
					Binder bind = (Binder) view.getParent().getAttribute(
							"binder");
					bind.postCommand("refreshWin", null);
					win.detach();
				} else if (i == -1) {
					Messagebox.show("该步骤已处理，无法操作。", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
				} else {
					Messagebox.show("催促处理，操作失败。", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
				}
			} else {
				Messagebox.show("请填写催促原因。", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("催促处理，操作出错。", "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
		}
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
