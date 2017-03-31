package Controller.EmSalary;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Window;

import Model.EmTXTFileInfoModel;
import Util.UserInfo;
import bll.EmSalary.ItemFormula_OperateBll;

public class EmSalary_TXTDelController {
	private ItemFormula_OperateBll ifOBll = new ItemFormula_OperateBll();

	// 获取用户名
	String username = UserInfo.getUsername();
	String userid = UserInfo.getUserid();

	private int etfi_id = ((EmTXTFileInfoModel) Executions.getCurrent()
			.getArg().get("etfiM")).getEtfi_id();

	@Command("submit")
	public void submit(@BindingParam("winTXTDel") final Window winTXTDel) {
		// 调用方法
		String[] message = ifOBll.delTXT(etfi_id, username);

		if (message[0].equals("0")) {
			EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
				public void onEvent(ClickEvent event) throws Exception {
					if (Messagebox.Button.OK.equals(event.getButton())) {
						// //跳转页面
						winTXTDel.detach();
					}
				}
			};
			// 弹出提示
			Messagebox.show(message[1], "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.INFORMATION, clickListener);
		} else {
			// 弹出提示
			Messagebox
					.show(message[1], "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
}
