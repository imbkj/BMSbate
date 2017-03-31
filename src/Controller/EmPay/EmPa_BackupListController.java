package Controller.EmPay;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmPayModel;
import Util.DateStringChange;
import bll.EmPay.EmPa_SelectBll;
import bll.SmsMessage.SmsGroupBll;

public class EmPa_BackupListController {
	private final EmPa_SelectBll bll = new EmPa_SelectBll();
	private final SmsGroupBll smsbll = new SmsGroupBll();
	private List<EmPayModel> list = new ArrayList<EmPayModel>();
	private EmPayModel m = new EmPayModel();
	private List<String> loginlist = smsbll.getLoginList();
	private String sql=" and empa_state in(1,7)";

	public EmPa_BackupListController() {
		list = bll.getEmPayList(sql);
	}

	@Command
	@NotifyChange("list")
	public void search(@BindingParam("ownmonth") Date ownmonth) {
		if (ownmonth != null) {
			try {
				m.setOwnmonth(Integer.parseInt(DateStringChange
						.Stringtoownmonth(ownmonth)));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		list = bll.getEmPayInfoList(m);
	}

	// 打开批量修改页面——部门经理审批
	@Command
	@NotifyChange("list")
	public void batchUpdate(@BindingParam("gd") Grid gd) {
		List<EmPayModel> clist = new ArrayList<EmPayModel>();
		for (int i = 0; i < gd.getPageSize(); i++) {
			if (gd.getCell(i, 17) != null) {
				Cell cell = (Cell) gd.getCell(i, 17);
				if (cell.getChildren().size() > 0) {
					Checkbox ck = (Checkbox) cell.getChildren().get(0);
					if (ck.isChecked()) {
						EmPayModel model = ck.getValue();
						clist.add(model);
					}
				}
			}
		}
		if (clist.size() > 0) {
			Map map = new HashMap<>();
			map.put("list", clist);
			Window window = (Window) Executions.createComponents(
					"EmPa_DMBatchApproval.zul", null, map);
			window.doModal();
			list = bll.getEmPayList(sql);
		} else {
			Messagebox.show("请选择数据", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 打开单个修改页面——部门经理审批
	@Command
	@NotifyChange("list")
	public void singleUpdate(@BindingParam("model") EmPayModel model) {
		Map map = new HashMap<>();
		map.put("model", model);
		Window window = (Window) Executions.createComponents(
				"EmPa_DMApproval.zul", null, map);
		window.doModal();
		list = bll.getEmPayList(sql);
	}

	// 部门经理退回
	@Command
	@NotifyChange("list")
	public void singleBack(@BindingParam("model") EmPayModel model) {
		Map map = new HashMap<>();
		map.put("model", model);
		Window window = (Window) Executions.createComponents(
				"EmPa_DMBack.zul", null, map);
		window.doModal();
		list = bll.getEmPayList(sql);
	}

	// 全选
	@Command
	public void checkall(@BindingParam("gd") Grid gd,
			@BindingParam("ck") Checkbox ck) {
		for (int i = 0; i < gd.getPageSize(); i++) {
			if (gd.getCell(i, 17) != null) {
				Cell cell = (Cell) gd.getCell(i, 17);
				if (cell.getChildren().size() > 0) {
					Checkbox cb = (Checkbox) cell.getChildren().get(0);
					cb.setChecked(ck.isChecked());
				}
			}
		}
	}

	public List<EmPayModel> getList() {
		return list;
	}

	public void setList(List<EmPayModel> list) {
		this.list = list;
	}

	public EmPayModel getM() {
		return m;
	}

	public void setM(EmPayModel m) {
		this.m = m;
	}

	public List<String> getLoginlist() {
		return loginlist;
	}

	public void setLoginlist(List<String> loginlist) {
		this.loginlist = loginlist;
	}
}
