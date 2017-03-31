package Controller.EmSalary;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import Model.CoSalaryItemAttributeModel;
import Model.CoSalaryItemFormulaModel;
import Model.ConnectionItemInfoModel;
import Util.RegexUtil;
import Util.UserInfo;
import bll.EmSalary.ItemFormula_OperateBll;
import bll.EmSalary.ItemFormula_SelectBll;

public class EmSalary_ItemNameUpdateController {
	private ItemFormula_OperateBll ifOBll = new ItemFormula_OperateBll();

	// 获取用户名
	String username = UserInfo.getUsername();
	String userid = UserInfo.getUserid();

	private int csii_id = ((CoSalaryItemFormulaModel) Executions.getCurrent()
			.getArg().get("csiiM")).getCsii_id();
	private int ownmonth = ((CoSalaryItemFormulaModel) Executions.getCurrent()
			.getArg().get("csiiM")).getOwnmonth();

	private String cfin_id = ((CoSalaryItemFormulaModel) Executions
			.getCurrent().getArg().get("csiiM")).getCfin_id();
	private String item_id = ((CoSalaryItemFormulaModel) Executions
			.getCurrent().getArg().get("csiiM")).getCsii_itemid();

	private int cid = ((CoSalaryItemFormulaModel) Executions.getCurrent()
			.getArg().get("csiiM")).getCid();
	private String old_item_name = ((CoSalaryItemFormulaModel) Executions
			.getCurrent().getArg().get("csiiM")).getCsii_item_name();

	ConnectionItemInfoModel ciin_idM;
	CoSalaryItemAttributeModel csia_idM;

	public EmSalary_ItemNameUpdateController() {

	}

	@Command("submit")
	public void submit(@BindingParam("win") final Window win,
			@BindingParam("csii_item_name") Textbox csii_item_name) {

		if (!"".equals(csii_item_name.getValue())
				&& !RegexUtil.isExists("\\[", csii_item_name.getValue())
				&& !RegexUtil.isExists("\\]", csii_item_name.getValue())) {

			// 调用方法
			String[] message = ifOBll.updateSalaryItemName(csii_id,
					csii_item_name.getValue(), old_item_name, cfin_id,
					ownmonth, username, cid, item_id);

			// 弹出提示并跳转页面
			if (message[0].equals("1")) {
				
				//特殊数据邮件提醒
				ifOBll.chkLM(cid);
				
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
			if ("".equals(csii_item_name.getValue())) {
				// 弹出提示
				Messagebox.show("请输入“项目名称”！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			} else if (RegexUtil.isExists("\\[", csii_item_name.getValue())
					|| RegexUtil.isExists("\\]", csii_item_name.getValue())) {
				// 弹出提示
				Messagebox.show("项目名称不能包含 [   ] 字符！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}

		}

	}

	public ConnectionItemInfoModel getCiin_idM() {
		return ciin_idM;
	}

	public void setCiin_idM(ConnectionItemInfoModel ciin_idM) {
		this.ciin_idM = ciin_idM;
	}

}
