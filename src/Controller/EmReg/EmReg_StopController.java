package Controller.EmReg;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.EmReg.EmReg_ListBll;
import bll.EmReg.EmReg_OperateBll;

import Model.EmRegistrationInfoModel;

public class EmReg_StopController {
	private EmRegistrationInfoModel erm = new EmRegistrationInfoModel();
	Integer daid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("daid").toString());

	public EmReg_StopController() {
		try {
			EmReg_ListBll bll = new EmReg_ListBll();

			setErm(bll.getEmRegInfo(daid, ""));

		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 提交事件
	@Command("submit")
	public void submit(@BindingParam("win") final Window win) {

		EmReg_OperateBll bll = new EmReg_OperateBll();

		// 调用方法
		String[] message = bll.erinStop(erm);
		// 弹出提示并跳转页面
		if (message[0].equals("1")) {
			EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
				public void onEvent(ClickEvent event) throws Exception {
					if (Messagebox.Button.OK.equals(event.getButton())) {
						// //跳转页面
						win.detach();
					}
				}
			};
			// 弹出提示
			Messagebox.show(message[1], "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.INFORMATION, clickListener);
		}

	}

	public EmRegistrationInfoModel getErm() {
		return erm;
	}

	public void setErm(EmRegistrationInfoModel erm) {
		this.erm = erm;
	}

}
