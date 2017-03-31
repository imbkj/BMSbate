package Controller.EmPay;

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
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmbaseModel;
import bll.EmPay.EmPa_SelectBll;
import bll.SmsMessage.SmsGroupBll;

public class EmPa_EmbaseListController {
	private EmPa_SelectBll bll = new EmPa_SelectBll();
	private SmsGroupBll smsbll = new SmsGroupBll();
	private List<EmbaseModel> list = new ListModelList<>();
	private List<EmbaseModel> checklist = new ListModelList<>();
	private List<String> loginlist = smsbll.getLoginList();
	private EmbaseModel m = new EmbaseModel();

	private boolean allcheck = false;

	public EmPa_EmbaseListController() {
		list = bll.getEmbaseList("");
	}

	@Command
	@NotifyChange("list")
	public void search() {
		list = bll.getEmbaseInfoList(m);
	}

	// 全选
	@Command
	public void checkall(@BindingParam("gd") Grid gd,
			@BindingParam("ck") Checkbox ck) {
		for (int i = 0; i < gd.getPageSize(); i++) {
			if (gd.getCell(i, 8) != null) {
				Cell cell = (Cell) gd.getCell(i, 8);
				if (cell.getChildren().size() > 0) {
					Checkbox cb = (Checkbox) cell.getChildren().get(0);
					cb.setChecked(ck.isChecked());
					EmbaseModel m = cb.getValue();
					m.setCheck(ck.isChecked());
					check(m);
				}
			}
		}
	}

	@Command
	@NotifyChange("checklist")
	public void check(@BindingParam("a") EmbaseModel m) {
		
		if (m.isCheck()) {
			if (checklist.size()>0) {
				Integer size = checklist.size();
				boolean b = false;
				for (int i = 0; i <size ; i++) {
					if (checklist.get(i).equals(m)) {
						b=true;
					}
				}
				if (!b) {
					checklist.add(m);
				}
			}else {
				checklist.add(m);
			}
			
			
		}else {
			checklist.remove(m);
		}
	}

	// 删除已选人员
	@Command
	@NotifyChange("checklist")
	public void del(@BindingParam("a") EmbaseModel m) {
		checklist.remove(m);
	}

	

	// 批量新增支付
	@Command
	public void batchAddPay() {
		
		if (checklist.size() <= 0) {
			Messagebox.show("请选择数据", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		Map map = new HashMap<>();
		map.put("l", checklist);
		Window window = (Window) Executions.createComponents(
				"EmPa_BatchAdd.zul", null, map);
		window.doModal();
	}

	public List<EmbaseModel> getList() {
		return list;
	}

	public void setList(List<EmbaseModel> list) {
		this.list = list;
	}

	public EmbaseModel getM() {
		return m;
	}

	public void setM(EmbaseModel m) {
		this.m = m;
	}

	public List<String> getLoginlist() {
		return loginlist;
	}

	public void setLoginlist(List<String> loginlist) {
		this.loginlist = loginlist;
	}

	public List<EmbaseModel> getChecklist() {
		return checklist;
	}

	public void setChecklist(List<EmbaseModel> checklist) {
		this.checklist = checklist;
	}

	public boolean isAllcheck() {
		return allcheck;
	}

	public void setAllcheck(boolean allcheck) {
		this.allcheck = allcheck;
	}

}
