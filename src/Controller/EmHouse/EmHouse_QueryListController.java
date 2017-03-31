package Controller.EmHouse;

import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import bll.EmHouse.EmHouse_QueryListBll;

import Model.EmHouseChangeModel;
import Util.UserInfo;

public class EmHouse_QueryListController {
	private List<EmHouseChangeModel> eclist = new ListModelList<>();
	private EmHouse_QueryListBll bll = new EmHouse_QueryListBll();
	private String deptid = UserInfo.getDepID();

	private EmHouseChangeModel ecm = (EmHouseChangeModel) Executions
			.getCurrent().getArg().get("ecm");

	private Window win = (Window) Path.getComponent("/winSearch");

	public EmHouse_QueryListController() {

		setEclist(ecm);

	}

	@Command("search")
	@NotifyChange("eclist")
	public void search() {
		Textbox tbCompany = (Textbox) win.getFellow("company");
		Textbox tbEmp = (Textbox) win.getFellow("emp");
		if (!tbCompany.getValue().equals("") || !tbEmp.getValue().equals("")) {
			EmHouseChangeModel em = new EmHouseChangeModel();
			em.setEmhc_company(tbCompany.getValue());
			em.setEmhc_name(tbEmp.getValue());
			setEclist(em);
		} else {
			Messagebox
					.show("请输入搜索内容!", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public List<EmHouseChangeModel> getEclist() {
		return eclist;
	}

	public void setEclist(EmHouseChangeModel em) {
		this.eclist = bll.getList(em);
	}
}
