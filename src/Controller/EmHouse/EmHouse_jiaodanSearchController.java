package Controller.EmHouse;

import impl.WorkflowCore.WfOperateImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

import Model.EmHouseChangeGJJModel;
import Model.LoginModel;
import Util.DateStringChange;
import Util.UserInfo;
import bll.EmHouse.EmHouseChangeGjjConfirmImpl;
import bll.EmHouse.EmHouse_ChangeGjjBll;
import bll.EmHouse.EmHouse_EditBll;

/**
 * 公积金交单查询
 * 
 * @author Administrator
 * 
 */

public class EmHouse_jiaodanSearchController {

	private EmHouse_ChangeGjjBll bll = new EmHouse_ChangeGjjBll();
	List<EmHouseChangeGJJModel> list = new ArrayList<EmHouseChangeGJJModel>();
	private List<EmHouseChangeGJJModel> ownmonthlist = new ListModelList<>();
	private EmHouseChangeGJJModel ecgm = new EmHouseChangeGJJModel();

	private Window win = (Window) Path.getComponent("/winGj");

	public EmHouse_jiaodanSearchController() {
		ecgm.setOwnmonth(Integer.valueOf(DateStringChange
				.Datestringnow("yyyyMM")));
		ownmonthlist = bll.getOwnmonthList();
		list = bll.getEmHouse_ChangeGjj(null, null, null, ecgm.getOwnmonth(),
				0, null);

	}

	@Command
	public void stateInfo(@BindingParam("a") Combobox cb) {
		if (cb.getSelectedItem() != null) {
			System.out.println(cb.getSelectedItem().getValue());
			if (!cb.getSelectedItem().getValue().equals("")) {
				ecgm.setEhcg_ifdeclare(Integer.valueOf(cb.getSelectedItem()
						.getValue().toString()));
			} else {
				ecgm.setEhcg_ifdeclare(null);
			}

		}
	}

	// 公积金交单申报信息查询
	@Command
	@NotifyChange("list")
	public void search() {

		list = bll.getEmHouse_ChangeGjj(null,ecgm.getEhcg_company(),
				ecgm.getEhcg_name(), ecgm.getOwnmonth(),
				ecgm.getEhcg_ifdeclare(),null);
	}

	// 全选
	@Command("checkall")
	@NotifyChange("list")
	public void checkall() {
		Checkbox ck = (Checkbox) win.getFellow("cka");
		Grid gd = (Grid) win.getFellow("gg");
		Integer n = gd.getActivePage() * gd.getPageSize();
		Integer s = (list.size() / gd.getPageCount()) < gd.getPageSize() ? (list
				.size() / gd.getPageCount()) : gd.getPageSize();
		for (int i = n; i < s; i++) {
			if (list.get(i).getEhcg_ifdeclare().equals(0)
					|| list.get(i).getEhcg_ifdeclare().equals(2)) {
				list.get(i).setChecked(ck.isChecked());
			}
		}
	}

	public List<EmHouseChangeGJJModel> getList() {
		return list;
	}

	public void setList(List<EmHouseChangeGJJModel> list) {
		this.list = list;
	}

	public List<EmHouseChangeGJJModel> getOwnmonthlist() {
		return ownmonthlist;
	}

	public void setOwnmonthlist(List<EmHouseChangeGJJModel> ownmonthlist) {
		this.ownmonthlist = ownmonthlist;
	}

	public EmHouseChangeGJJModel getEcgm() {
		return ecgm;
	}

	public void setEcgm(EmHouseChangeGJJModel ecgm) {
		this.ecgm = ecgm;
	}

}
