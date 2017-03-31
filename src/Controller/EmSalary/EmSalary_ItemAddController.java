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

import bll.EmSalary.ItemFormula_OperateBll;
import bll.EmSalary.ItemFormula_SelectBll;

import Model.CoSalaryItemAttributeModel;
import Model.ConnectionItemInfoModel;
import Util.UserInfo;
import Util.RegexUtil;

public class EmSalary_ItemAddController {
	private List<ConnectionItemInfoModel> conItemList;
	private List<CoSalaryItemAttributeModel> itemAttributeList;
	private ItemFormula_SelectBll ifSBll = new ItemFormula_SelectBll();
	private ItemFormula_OperateBll ifOBll = new ItemFormula_OperateBll();

	// 获取用户名
	String username = UserInfo.getUsername();
	String userid = UserInfo.getUserid();

	private String cid = String.valueOf(Executions.getCurrent().getArg()
			.get("cid").toString());
	private String ownmonth = String.valueOf(Executions.getCurrent().getArg()
			.get("ownmonth").toString());
	private String item_id = String.valueOf(Executions.getCurrent().getArg()
			.get("item_id").toString());
	private String cfin_id = String.valueOf(Executions.getCurrent().getArg()
			.get("cfin_id").toString());

	public EmSalary_ItemAddController() {
		// 获取关联项目下拉框
		conItemList = ifSBll.getConnectionItem("");
		// 获取项目属性下拉框
		itemAttributeList = ifSBll.getItemAttribute("");
	}

	@Command("submit")
	public void submit(@BindingParam("winItemAdd") final Window winItemAdd,
			@BindingParam("csii_item_name") Textbox csii_item_name,
			@BindingParam("csii_fd_state") Radiogroup csii_fd_state,
			@BindingParam("ciin_id") Combobox ciin_id,
			@BindingParam("csii_ifzero") Radiogroup csii_ifzero,
			@BindingParam("csia_id") Combobox csia_id) {

		int i_ciin_id = 0;
		if (ciin_id.getSelectedItem() != null) {
			i_ciin_id = Integer.parseInt(ciin_id.getSelectedItem().getValue()
					.toString());
		}

		int i_csia_id = 0;
		if (csia_id.getSelectedItem() != null) {
			i_csia_id = Integer.parseInt(csia_id.getSelectedItem().getValue()
					.toString());
		}

		if (!"".equals(csii_item_name.getValue())
				&& !RegexUtil.isExists("\\[", csii_item_name.getValue())
				&& !RegexUtil.isExists("\\]", csii_item_name.getValue())
				&& !"".equals(csii_fd_state.getSelectedItem().getValue())
				&& !"".equals(csii_ifzero.getSelectedItem().getValue())) {

			// 调用方法
			String[] message = ifOBll.addSalaryItems(
					Integer.parseInt(cid),
					item_id,
					csii_item_name.getValue(),
					i_csia_id,
					cfin_id,
					Integer.parseInt(csii_fd_state.getSelectedItem().getValue()
							.toString()),
					i_ciin_id,
					Integer.parseInt(csii_ifzero.getSelectedItem().getValue()
							.toString()), username,ownmonth);

			// 调用算法组合方法更新顺序和算法内容
			ifOBll.createFormula(Integer.parseInt(cid),
					Integer.parseInt(ownmonth), Integer.parseInt(cfin_id));

			// 弹出提示并跳转页面
			if (message[0].equals("1")) {
				//特殊数据邮件提醒
				ifOBll.chkLM(Integer.parseInt(cid));
				
				EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.OK.equals(event.getButton())) {
							// //跳转页面
							winItemAdd.detach();
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
			} else if ("".equals(csii_fd_state.getSelectedItem().getValue())) {
				// 弹出提示
				Messagebox.show("请选择“显示状态”！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			} else if ("".equals(csii_ifzero.getSelectedItem().getValue())) {
				// 弹出提示
				Messagebox.show("请选择“是否清零”！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}

		}

	}

	public List<ConnectionItemInfoModel> getConItemList() {
		return conItemList;
	}

	public void setConItemList(List<ConnectionItemInfoModel> conItemList) {
		this.conItemList = conItemList;
	}

	public List<CoSalaryItemAttributeModel> getItemAttributeList() {
		return itemAttributeList;
	}

	public void setItemAttributeList(
			List<CoSalaryItemAttributeModel> itemAttributeList) {
		this.itemAttributeList = itemAttributeList;
	}

}
