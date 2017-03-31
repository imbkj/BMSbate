package Controller.EmSalary;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import Model.CoFormulaInfoModel;
import Util.UserInfo;
import bll.EmSalary.ItemFormula_OperateBll;
import bll.EmSalary.ItemFormula_SelectBll;

public class EmSalary_ForAddController {
	private ItemFormula_OperateBll ifOBll = new ItemFormula_OperateBll();
	private ItemFormula_SelectBll ifSBll = new ItemFormula_SelectBll();
	private List<CoFormulaInfoModel> formulaList;

	private String csii_itemid;
	private String cid = String.valueOf(Executions.getCurrent().getArg()
			.get("cid").toString());
	// 获取用户名
	String username = UserInfo.getUsername();
	String userid = UserInfo.getUserid();

	public EmSalary_ForAddController() {

		csii_itemid = ifSBll.getCoSalaryItemID(" AND cid=" + cid).get(0)
				.getCsii_itemid();

		// 获取算法下拉框
		formulaList = ifSBll.getFormulaInfo(" AND csii_itemid='" + csii_itemid
				+ "'");

	}

	@Command("submit")
	public void submit(@BindingParam("winForAdd") final Window winForAdd,
			@BindingParam("cfin_name") Textbox cfin_name,
			@BindingParam("cfin_remark") Textbox cfin_remark,
			@BindingParam("cb_cfin_id") Combobox cb_cfin_id) {
		
		if (!"".equals(cfin_name.getValue())) {
			//被复制算法id
			int c_cfin_id=0;
			
			if (cb_cfin_id.getSelectedItem() != null) {
				c_cfin_id=Integer.parseInt(cb_cfin_id.getSelectedItem().getValue().toString());
			}
			
			// 调用方法
			String[] message = ifOBll.addFormulaInfo(Integer.parseInt(cid),
					cfin_name.getValue(), cfin_remark.getValue(), c_cfin_id, username);

			// 弹出提示并跳转页面
			if (message[0].equals("0")) {
				EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.OK.equals(event.getButton())) {
							// //跳转页面
							winForAdd.detach();
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
			if ("".equals(cfin_name.getValue())) {
				// 弹出提示
				Messagebox.show("请输入“算法名称”！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	public List<CoFormulaInfoModel> getFormulaList() {
		return formulaList;
	}

	public void setFormulaList(List<CoFormulaInfoModel> formulaList) {
		this.formulaList = formulaList;
	}

}
