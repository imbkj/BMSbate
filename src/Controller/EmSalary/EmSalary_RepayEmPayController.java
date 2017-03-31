package Controller.EmSalary;

import impl.MessageImpl;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import service.MessageService;
import dal.LoginDal;

import Model.EmPayModel;
import Model.EmSalaryBaseSel_viewModel;
import Model.SysMessageModel;
import Util.UserInfo;
import bll.EmPay.EmPa_SelectBll;
import bll.EmSalary.EmSalary_DataOperateBll;
import bll.EmSalary.EmSalary_SalarySelBll;
import bll.EmSalary.ItemFormula_SelectBll;

public class EmSalary_RepayEmPayController {
	private ItemFormula_SelectBll isBll = new ItemFormula_SelectBll();
	private EmSalary_DataOperateBll eoBll = new EmSalary_DataOperateBll();

	private List reasonList; // 重发原因
	private Integer empa_id = Integer.parseInt(Executions.getCurrent().getArg()
			.get("empa_id").toString()); // 工资数据id

	private final EmPa_SelectBll bll = new EmPa_SelectBll();
	private EmPayModel empaM = null;

	public EmSalary_RepayEmPayController() {
		reasonList = isBll.getRepayReason();

		empaM = new EmPayModel();
		empaM = bll.getEmPayList(" and a.id=" + empa_id).get(0);

	}

	@Command("submit")
	public void submit(@BindingParam("win") final Window win,
			@BindingParam("repay_reason") Combobox repay_reason) {

		if (repay_reason.getSelectedItem() != null) {

			// 调用方法
			String[] message = eoBll.repayEmPay(empa_id,Integer.parseInt(repay_reason.getSelectedItem().getValue()
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
			// 弹出提示
			Messagebox
					.show("请选择重发原因！", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public List getReasonList() {
		return reasonList;
	}

	public void setReasonList(List reasonList) {
		this.reasonList = reasonList;
	}

	public EmPayModel getEmpaM() {
		return empaM;
	}

	public void setEmpaM(EmPayModel empaM) {
		this.empaM = empaM;
	}

}
