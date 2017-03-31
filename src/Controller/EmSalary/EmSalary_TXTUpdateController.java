package Controller.EmSalary;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import Model.EmTXTFileInfoModel;
import Util.UserInfo;
import bll.EmSalary.ItemFormula_OperateBll;

public class EmSalary_TXTUpdateController {
	private ItemFormula_OperateBll ifOBll = new ItemFormula_OperateBll();

	// 获取用户名
	String username = UserInfo.getUsername();
	String userid = UserInfo.getUserid();

	private int etfi_id = ((EmTXTFileInfoModel) Executions.getCurrent()
			.getArg().get("etfiM")).getEtfi_id();

	@Command("submit")
	public void submit(@BindingParam("winTXTUpdate") final Window winTXTUpdate,
			@BindingParam("etfi_ba_name") Textbox etfi_ba_name) {
		if (!"".equals(etfi_ba_name.getValue())) {
			// 调用方法
			String[] message = ifOBll.updateTXTBN(etfi_id,
					etfi_ba_name.getValue(), username);

			if (message[0].equals("0")) {
				EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.OK.equals(event.getButton())) {
							// //跳转页面
							winTXTUpdate.detach();
						}
					}
				};
				// 弹出提示
				Messagebox.show(message[1], "操作提示",
						new Messagebox.Button[] { Messagebox.Button.OK },
						Messagebox.INFORMATION, clickListener);
			} else {
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			if ("".equals(etfi_ba_name.getValue())) {
				// 弹出提示
				Messagebox.show("请输入“银行账户名”！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}
}
