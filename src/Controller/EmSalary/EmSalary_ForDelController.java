package Controller.EmSalary;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.EmSalary.ItemFormula_OperateBll;
import Model.CoFormulaInfoModel;
import Util.UserInfo;

public class EmSalary_ForDelController {
	private ItemFormula_OperateBll ifOBll = new ItemFormula_OperateBll();
	// 获取用户名
	String username = UserInfo.getUsername();
	String userid = UserInfo.getUserid();

	private int cfin_id = ((CoFormulaInfoModel) Executions.getCurrent()
			.getArg().get("cfinM")).getCfin_id();

	// 删除
	@Command("submit")
	public void submit(@BindingParam("winForDel") final Window winForDel) {
		// 调用方法
		String[] message = ifOBll.delFormulaInfo(cfin_id, username);

		// 弹出提示并跳转页面
		if (message[0].equals("0")) {
			EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
				public void onEvent(ClickEvent event) throws Exception {
					if (Messagebox.Button.OK.equals(event.getButton())) {
						// //跳转页面
						winForDel.detach();
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
