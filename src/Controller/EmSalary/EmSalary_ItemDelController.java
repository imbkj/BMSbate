package Controller.EmSalary;

import org.zkoss.bind.annotation.BindingParam;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import Model.CoSalaryItemFormulaModel;

import Util.UserInfo;
import bll.EmSalary.ItemFormula_OperateBll;
import bll.EmSalary.ItemFormula_SelectBll;

public class EmSalary_ItemDelController {
	private String ciin_name = "";
	private ItemFormula_SelectBll ifSBll = new ItemFormula_SelectBll();
	private ItemFormula_OperateBll ifOBll = new ItemFormula_OperateBll();

	// 获取用户名
	String username = UserInfo.getUsername();
	String userid = UserInfo.getUserid();

	private int csii_id = ((CoSalaryItemFormulaModel) Executions.getCurrent()
			.getArg().get("csiiM")).getCsii_id();
	private int ownmonth = ((CoSalaryItemFormulaModel) Executions.getCurrent()
			.getArg().get("csiiM")).getOwnmonth();
	private int ciin_id = ((CoSalaryItemFormulaModel) Executions.getCurrent()
			.getArg().get("csiiM")).getCiin_id();
	private int cid = ((CoSalaryItemFormulaModel) Executions.getCurrent()
			.getArg().get("csiiM")).getCid();
	private String cfin_id = ((CoSalaryItemFormulaModel) Executions
			.getCurrent().getArg().get("csiiM")).getCfin_id();

	public EmSalary_ItemDelController() {
		if (ciin_id != 0) {
			ciin_name = ifSBll
					.getConnectionItem(
							" AND ciin_id=" + String.valueOf(ciin_id)).get(0)
					.getCiin_name();
		} else {
			ciin_name = "无";
		}
	}

	@Command("submit")
	public void submit(@BindingParam("winItemDel") final Window winItemDel) {

		// 调用方法
		String[] message = ifOBll.delSalaryItems(csii_id, ownmonth, username);

		// 调用算法组合方法更新顺序和算法内容
		ifOBll.createFormula(cid, ownmonth, Integer.parseInt(cfin_id));

		if (message[0].equals("1")) {
			
			//特殊数据邮件提醒
			ifOBll.chkLM(cid);
			
			EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
				public void onEvent(ClickEvent event) throws Exception {
					if (Messagebox.Button.OK.equals(event.getButton())) {
						// //跳转页面
						winItemDel.detach();
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

	public String getCiin_name() {
		return ciin_name;
	}

	public void setCiin_name(String ciin_name) {
		this.ciin_name = ciin_name;
	}

}
