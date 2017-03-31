package Controller.EmSalary;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.EmSalary.EmSalary_SalarySelBll;
import bll.EmSalary.ItemFormula_OperateBll;
import Model.EmSalaryBaseSel_viewModel;

public class EmSalary_EmbaseUpdateController {
	private final int esda_id = Integer.parseInt(Executions.getCurrent()
			.getArg().get("esda_id").toString());
	private EmSalaryBaseSel_viewModel baseModel;
	private ItemFormula_OperateBll ifOBll = new ItemFormula_OperateBll();

	public EmSalary_EmbaseUpdateController() {
		EmSalary_SalarySelBll bll = new EmSalary_SalarySelBll();
		baseModel = bll.getSalaryEmBase(esda_id);
	}

	@Command("submit")
	public void submit(@BindingParam("win") final Window win) {

		if (!"".equals(baseModel.getEsda_ba_name())) {
			// 调用方法
			String[] message = ifOBll.updateESDABN(baseModel);

			if (message[0].equals("0")) {
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
			if ("".equals(baseModel.getEsda_ba_name())) {
				// 弹出提示
				Messagebox.show("请输入“银行账户名”！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	public EmSalaryBaseSel_viewModel getBaseModel() {
		return baseModel;
	}

	public void setBaseModel(EmSalaryBaseSel_viewModel baseModel) {
		this.baseModel = baseModel;
	}

}
