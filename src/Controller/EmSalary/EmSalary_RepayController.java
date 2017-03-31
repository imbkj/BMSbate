package Controller.EmSalary;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import Model.EmSalaryBaseSel_viewModel;
import Util.UserInfo;
import bll.EmSalary.EmSalary_DataOperateBll;
import bll.EmSalary.EmSalary_SalarySelBll;

public class EmSalary_RepayController {
	private EmSalary_SalarySelBll esBll = new EmSalary_SalarySelBll();
	private EmSalary_DataOperateBll eoBll = new EmSalary_DataOperateBll();

	private EmSalaryBaseSel_viewModel esdaM; // 员工工资数据
	private int esda_id = Integer.parseInt(Executions.getCurrent().getArg()
			.get("daid").toString()); // 工资数据id

	// 获取用户名
	String username = UserInfo.getUsername();

	public EmSalary_RepayController() {
		esdaM = esBll.getSalaryEmBase(esda_id);
	}

	@Command("submit")
	public void submit(@BindingParam("win") final Window win) {

		if (Messagebox.show("重发该条数据并生成报盘文件数据?", "INFORMATION", Messagebox.YES
				| Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {

			// 调用方法
			String[] message = eoBll.repay(esdaM, username);

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

		}
	}

	public EmSalaryBaseSel_viewModel getEsdaM() {
		return esdaM;
	}

	public void setEsdaM(EmSalaryBaseSel_viewModel esdaM) {
		this.esdaM = esdaM;
	}

}
