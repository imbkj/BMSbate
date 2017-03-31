package Controller.CoCompact;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;

import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.event.EventListener;

import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import org.zkoss.zul.Messagebox.ClickEvent;

import bll.CoCompact.Compact_DetailBll;

public class Compact_CoStopqgController {
	private Compact_DetailBll cocoBll = new Compact_DetailBll();

	// 产品终止
	@Command("co_stopok")
	@NotifyChange("picList")
	public void co_stopok(@BindingParam("w1") final Window w1,
			@BindingParam("cocoid") Label coid,
			@BindingParam("coli_id") Label coli_id,
			@BindingParam("coli_fee") Label coli_fee,
			@BindingParam("st_date") Datebox st_date) throws Exception {

		if (st_date.getValue() != null) {
			String inn_date = "";
			
			inn_date = cocoBll.DatetoSting(st_date.getValue());

			String message = cocoBll.stopco(coli_id.getValue(),
					coli_fee.getValue(), inn_date);

			// 弹出提示并跳转页面
			if (message.equals("0")) {
				EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.OK.equals(event.getButton())) {
							// Executions.sendRedirect("Compact_SginList.zul");
							// //跳转页面
							w1.detach();
						}
					}
				};
				// 弹出提示
				Messagebox.show("操作成功！", "操作提示",
						new Messagebox.Button[] { Messagebox.Button.OK },
						Messagebox.INFORMATION, clickListener);
			} else {
				// 弹出提示
				Messagebox.show("操作失败！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			// 弹出提示
			Messagebox
					.show("请选择终止时间！", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}

	}

}
