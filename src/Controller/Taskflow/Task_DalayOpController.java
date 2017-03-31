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

public class Task_DalayOpController {
	private int tapr_id = Integer.parseInt(Executions.getCurrent().getArg()
			.get("tapr_id").toString());
	private String tali_name = Executions.getCurrent().getArg()
			.get("tali_name").toString();
	private String wfno_name = Executions.getCurrent().getArg()
			.get("wfno_name").toString();
	private String username;
	private String reason;

	public Task_DalayOpController() {
		username = UserInfo.getUsername();
	}

	@Command("addDalay")
	public void addDalay(@BindingParam("win") Window win,
			@ContextParam(ContextType.VIEW) Component view) {
		try {
			if (reason != null && !"".equals(reason.trim())) {
				Task_controlBll bll = new Task_controlBll();
				int i = bll.dalayOp(tapr_id, reason, username);
				if (i == 1) {
					Messagebox.show("暂缓处理，操作成功。", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
					Binder bind = (Binder) view.getParent().getAttribute(
							"binder");
					bind.postCommand("refreshWin", null);
					win.detach();
				} else if (i == -1) {
					Messagebox.show("该步骤已处理，无法操作。", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
				} else {
					Messagebox.show("暂缓处理，操作失败。", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
				}
			} else {
				Messagebox.show("请填写暂缓原因。", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("暂缓处理，操作出错。", "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
		}
	}

	public String getTali_name() {
		return tali_name;
	}

	public String getWfno_name() {
		return wfno_name;
	}

	public String getUsername() {
		return username;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
