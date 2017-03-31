package Controller.EmSalary;

import java.util.List;
import java.util.Set;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.EmSalary.ItemFormula_OperateBll;
import bll.EmSalary.ItemFormula_SelectBll;

import Model.CoSalaryItemFormulaModel;
import Util.RegexUtil;

public class EmSalary_ItemChangeDisController {
	private List<CoSalaryItemFormulaModel> itemFormulaList;
	private ItemFormula_SelectBll ifSBll = new ItemFormula_SelectBll();
	private ItemFormula_OperateBll ifOBll = new ItemFormula_OperateBll();

	private String cid = String.valueOf(Executions.getCurrent().getArg()
			.get("cid").toString());
	private String ownmonth = String.valueOf(Executions.getCurrent().getArg()
			.get("ownmonth").toString());
	private String cfin_id = String.valueOf(Executions.getCurrent().getArg()
			.get("cfin_id").toString());

	public EmSalary_ItemChangeDisController() {
		// 获取工资项目列表
		itemFormulaList = ifSBll.getItemFormulaCSIF(cid, ownmonth, "",
				" AND csii_fd_state<>2 AND cfin_id=" + cfin_id);
	}

	// 查询
	@Command("search")
	@NotifyChange("itemFormulaList")
	public void search(@BindingParam("fd_state") Combobox fd_state) {
		if (fd_state.getSelectedItem() != null) {
			String sql = "";
			if ("显示".equals(fd_state.getSelectedItem().getLabel())) {
				sql = sql + " AND csii_fd_state=1";
			} else if ("隐藏".equals(fd_state.getSelectedItem().getLabel())) {
				sql = sql + " AND csii_fd_state=0";
			}
			itemFormulaList = ifSBll.getItemFormulaCSIF(cid, ownmonth, "",
					" AND csii_fd_state<>2 AND cfin_id=" + cfin_id + sql);
		}
	}

	// // 全选
	// @Command
	// public void allCheck(@BindingParam("grid") Grid grid,
	// @BindingParam("all") Checkbox allc) {
	// boolean isCheck = allc.isChecked();
	// for (int i = 0; i <= itemFormulaList.size(); i++) {
	// Checkbox c = (Checkbox) grid.getCell(i, 0).getChildren().get(0);
	// c.setChecked(isCheck);
	// }
	//
	// }

	// 提交事件
	@Command("submit")
	public void submit(@BindingParam("winItemDis") final Window winItemDis,
			@BindingParam("itemList") Listbox itemList,
			@BindingParam("state") int state) {
		Set<Listitem> listCheck = itemList.getSelectedItems();
		try {
			if (!listCheck.isEmpty()) {
				Integer csii_id = 0;
				int chk_i = 0;
				int chk_j = 0;

				for (Listitem c : listCheck) {
					chk_i = chk_i + 1;
					// 调用方法
					String[] message = ifOBll.changeSalaryItemsDis(
							((CoSalaryItemFormulaModel) c.getValue())
									.getCsii_id(), state);
					if (message[0].equals("0")) {
						chk_j = chk_j + 1;
					}

				}

				//特殊数据邮件提醒
				ifOBll.chkLM(Integer.parseInt(cid));
				
				// 弹出提示并跳转页面
				if (chk_j == chk_j) {
					EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
						public void onEvent(ClickEvent event) throws Exception {
							if (Messagebox.Button.OK.equals(event.getButton())) {
								// //跳转页面
								winItemDis.detach();
							}
						}
					};
					// 弹出提示
					Messagebox.show("操作成功", "操作提示",
							new Messagebox.Button[] { Messagebox.Button.OK },
							Messagebox.INFORMATION, clickListener);
				} else {
					// 弹出提示
					Messagebox.show("操作失败", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}

			} else {
				if (listCheck.isEmpty()) {
					// 弹出提示
					Messagebox.show("请选择工资项目！", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}

			}
		} catch (Exception e) {
			Messagebox.show("全选操作时请将数据加载完整", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	public List<CoSalaryItemFormulaModel> getItemFormulaList() {
		return itemFormulaList;
	}

	public void setItemFormulaList(
			List<CoSalaryItemFormulaModel> itemFormulaList) {
		this.itemFormulaList = itemFormulaList;
	}

	public String getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(String ownmonth) {
		this.ownmonth = ownmonth;
	}

}
