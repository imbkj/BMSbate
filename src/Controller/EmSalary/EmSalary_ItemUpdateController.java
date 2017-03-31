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

public class EmSalary_ItemUpdateController {
	private List<ConnectionItemInfoModel> conItemList;
	private List<CoSalaryItemAttributeModel> itemAttributeList;
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
	private int csia_id = ((CoSalaryItemFormulaModel) Executions.getCurrent()
			.getArg().get("csiiM")).getCsia_id();
	private String cfin_id = ((CoSalaryItemFormulaModel) Executions
			.getCurrent().getArg().get("csiiM")).getCfin_id();
	private String item_id = ((CoSalaryItemFormulaModel) Executions
			.getCurrent().getArg().get("csiiM")).getCsii_itemid();

	private int cid = ((CoSalaryItemFormulaModel) Executions.getCurrent()
			.getArg().get("csiiM")).getCid();

	ConnectionItemInfoModel ciin_idM;
	CoSalaryItemAttributeModel csia_idM;

	public EmSalary_ItemUpdateController() {
		// 获取关联项目下拉框
		conItemList = ifSBll.getConnectionItem("");
		// 获取项目属性下拉框
		itemAttributeList = ifSBll.getItemAttribute("");

		for (ConnectionItemInfoModel m : conItemList) {
			if (m.getCiin_id() == ciin_id) {
				ciin_idM = m;
				break;
			}
		}
		for (CoSalaryItemAttributeModel m : itemAttributeList) {
			if (m.getCsia_id() == csia_id) {
				csia_idM = m;
				break;
			}
		}
	}

	@Command("submit")
	public void submit(
			@BindingParam("winItemUpdate") final Window winItemUpdate,
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
				&& !"".equals(csii_ifzero.getSelectedItem().getValue())
				&& csia_id.getSelectedItem() != null) {

			// 调用方法
			String[] message = ifOBll.updateSalaryItems(
					csii_id,
					csii_item_name.getValue(),
					i_csia_id,
					cfin_id,
					Integer.parseInt(csii_fd_state.getSelectedItem().getValue()
							.toString()),
					i_ciin_id,
					Integer.parseInt(csii_ifzero.getSelectedItem().getValue()
							.toString()), ownmonth, username,cid,item_id);

			// 调用算法组合方法更新顺序和算法内容
			ifOBll.createFormula(cid, ownmonth, Integer.parseInt(cfin_id));

			// 弹出提示并跳转页面
			if (message[0].equals("1")) {
				
				//特殊数据邮件提醒
				ifOBll.chkLM(cid);
				
				EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.OK.equals(event.getButton())) {
							// //跳转页面
							winItemUpdate.detach();
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

	public ConnectionItemInfoModel getCiin_idM() {
		return ciin_idM;
	}

	public void setCiin_idM(ConnectionItemInfoModel ciin_idM) {
		this.ciin_idM = ciin_idM;
	}

	public List<CoSalaryItemAttributeModel> getItemAttributeList() {
		return itemAttributeList;
	}

	public void setItemAttributeList(
			List<CoSalaryItemAttributeModel> itemAttributeList) {
		this.itemAttributeList = itemAttributeList;
	}

	public CoSalaryItemAttributeModel getCsia_idM() {
		return csia_idM;
	}

	public void setCsia_idM(CoSalaryItemAttributeModel csia_idM) {
		this.csia_idM = csia_idM;
	}

}
