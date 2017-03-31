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
import bll.EmSalary.ItemFormula_SelectBll;

public class EmSalary_RepaySendController {
	private EmSalary_SalarySelBll esBll = new EmSalary_SalarySelBll();
	private ItemFormula_SelectBll isBll = new ItemFormula_SelectBll();
	private EmSalary_DataOperateBll eoBll = new EmSalary_DataOperateBll();

	private EmSalaryBaseSel_viewModel esdaM; // 员工工资数据
	private String rp_reason; // 重发原因
	private int esda_id = Integer.parseInt(Executions.getCurrent().getArg()
			.get("daid").toString()); // 工资数据id

	// 获取用户名
	String username = UserInfo.getUsername();

	public EmSalary_RepaySendController() {
		esdaM = esBll.getSalaryEmBase(esda_id);
		rp_reason = isBll.tRepayReason(Integer.parseInt(esdaM
				.getEsda_rp_reason().toString()));
	}

	@Command("submit")
	public void submit(@BindingParam("win") final Window win) {
		// 调用方法
		String[] message = eoBll.repaySend(esdaM, username);
		
		//节点权限分配给客服

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
			Messagebox
					.show(message[1], "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public String getRp_reason() {
		return rp_reason;
	}

	public void setRp_reason(String rp_reason) {
		this.rp_reason = rp_reason;
	}

	public EmSalaryBaseSel_viewModel getEsdaM() {
		return esdaM;
	}

	public void setEsdaM(EmSalaryBaseSel_viewModel esdaM) {
		this.esdaM = esdaM;
	}

}
