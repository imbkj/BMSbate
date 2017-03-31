package Controller.EmSalary;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import Model.CoSalaryItemFormulaModel;
import Util.UserInfo;
import bll.EmSalary.ItemFormula_OperateBll;

public class EmSalary_ItemIfZeroController {
	private ItemFormula_OperateBll ifOBll = new ItemFormula_OperateBll();

	// 获取用户名
	String username = UserInfo.getUsername();
	String userid = UserInfo.getUserid();

	private int csii_id = ((CoSalaryItemFormulaModel) Executions.getCurrent()
			.getArg().get("csiiM")).getCsii_id();

	public EmSalary_ItemIfZeroController() {
		super();
	}

	@Command("submit")
	public void submit(@BindingParam("win") final Window win,
			@BindingParam("csii_ifzero") Radiogroup csii_ifzero) {

		if (!"".equals(csii_ifzero.getSelectedItem().getValue())) {

			// 调用方法
			String[] message = ifOBll.changeIfZero(
					csii_id,
					Integer.parseInt(csii_ifzero.getSelectedItem().getValue()
							.toString()));

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
			} else {
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}

		} else {
			if ("".equals(csii_ifzero.getSelectedItem().getValue())) {
				// 弹出提示
				Messagebox.show("请选择“是否清零”！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}

		}

	}
}
