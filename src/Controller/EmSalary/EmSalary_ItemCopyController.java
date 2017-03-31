package Controller.EmSalary;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.EmSalary.ItemFormula_OperateBll;
import bll.EmSalary.ItemFormula_SelectBll;

import Controller.systemWindowController;
import Model.CoOwnmonthModel;
import Util.UserInfo;

public class EmSalary_ItemCopyController {
	private List<CoOwnmonthModel> cobaseList;
	private List<CoOwnmonthModel> ownmonthList;
	private ItemFormula_SelectBll ifSBll = new ItemFormula_SelectBll();
	private ItemFormula_OperateBll ifOBll = new ItemFormula_OperateBll();

	// 获取用户名
	String username = UserInfo.getUsername();
	String userid = UserInfo.getUserid();

	private String l_company = "";
	private String cid = String.valueOf(Executions.getCurrent().getArg()
			.get("cid").toString());
	private String ownmonth = String.valueOf(Executions.getCurrent().getArg()
			.get("ownmonth").toString());

	public EmSalary_ItemCopyController() {
		// 初始化被复制公司下拉框
		cobaseList = ifSBll.getCobase("");
		// 初始化公司简称
		l_company = ifSBll.getCobase(" AND a.cid=" + cid).get(0)
				.getLong_company();
	}

	// 获取项目算法组合所属月份
	@Command("selOwnmonth")
	@NotifyChange("ownmonthList")
	public void selOwnmonth(@BindingParam("c_company") Combobox c_company,
			@BindingParam("c_ownmonth") Combobox c_ownmonth) {
		c_ownmonth.setValue("");
		if (!"".equals(c_company.getValue())) {
			String str1 = " AND cid=" + c_company.getSelectedItem().getValue();
			setOwnmonthList(ifSBll.getOwnmonth(str1));
		}
	}

	// 提交事件
	@Command("submit")
	public void submit(@BindingParam("winItemCopy") final Window winItemCopy,
			@BindingParam("c_company") Combobox c_company,
			@BindingParam("c_ownmonth") Combobox c_ownmonth) {
		// 如果c_cid或c_ownmonth数据不完整，c_cid=0,c_ownmonth=0导入默认算法组合数据

		int copy_cid;
		int copy_ownmonth;
		if (!"".equals(c_company.getValue())) {
			copy_cid = Integer.parseInt(c_company.getSelectedItem().getValue()
					.toString());
		} else {
			copy_cid = 0;
		}

		if (!"".equals(c_ownmonth.getValue())) {
			copy_ownmonth = Integer.parseInt(c_ownmonth.getValue().toString());
		} else {
			copy_ownmonth = 0;
		}

		// 调用方法
		String[] message = ifOBll.copySalaryFormulaItem(
				Integer.parseInt(cid.toString()),
				Integer.parseInt(ownmonth.toString()), copy_cid, copy_ownmonth,
				username);

		// 弹出提示并跳转页面
		if (message[0].equals("1")) {
			
			//特殊数据邮件提醒
			ifOBll.chkLM(Integer.parseInt(cid));
			
			EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
				public void onEvent(ClickEvent event) throws Exception {
					if (Messagebox.Button.OK.equals(event.getButton())) {
						// //跳转页面
						winItemCopy.detach();
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

	public List<CoOwnmonthModel> getCobaseList() {
		return cobaseList;
	}

	public void setCobaseList(List<CoOwnmonthModel> cobaseList) {
		this.cobaseList = cobaseList;
	}

	public List<CoOwnmonthModel> getOwnmonthList() {
		return ownmonthList;
	}

	public void setOwnmonthList(List<CoOwnmonthModel> ownmonthList) {
		this.ownmonthList = ownmonthList;
	}

	public String getL_company() {
		return l_company;
	}

	public void setL_company(String l_company) {
		this.l_company = l_company;
	}

}
