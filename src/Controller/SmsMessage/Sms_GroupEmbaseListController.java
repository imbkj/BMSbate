package Controller.SmsMessage;

import java.util.ArrayList;
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

import dal.SysMessage.Message_SelectDal;

import bll.SmsMessage.SmsGroupBll;

import Model.EmbaseModel;

public class Sms_GroupEmbaseListController {
	private List<EmbaseModel> list = new ArrayList<EmbaseModel>();
	private SmsGroupBll bll = new SmsGroupBll();
	private EmbaseModel m = new EmbaseModel();
	private List<String> loginlist = bll.getLoginList();
	private String state;

	public Sms_GroupEmbaseListController() {
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
			if (gd.getCell(i, 10) != null) {
				Cell cell = (Cell) gd.getCell(i, 10);
				if (cell.getChildren().size() > 0) {
					Checkbox cb = (Checkbox) cell.getChildren().get(0);
					cb.setChecked(ck.isChecked());
				}
			}
		}
	}

	// 弹出发短信
	@Command
	public void sendsms(@BindingParam("gd") Grid gd) {
		List<EmbaseModel> checklist = new ArrayList<EmbaseModel>();
		for (int i = 0; i < gd.getPageSize(); i++) {
			if (gd.getCell(i, 10) != null) {
				Cell cell = (Cell) gd.getCell(i, 10);
				if (cell.getChildren().size() > 0) {
					Checkbox ck = (Checkbox) cell.getChildren().get(0);
					if (ck.isChecked()) {
						EmbaseModel m = ck.getValue();
						checklist.add(m);
					}
				}
			}
		}
		Map map = new HashMap<>();
		map.put("list", checklist);
		Window window = (Window) Executions.createComponents(
				"/SmsMessage/Sms_GroupSend.zul", null, map);
		window.doModal();
	}

	// 弹出发短信
	@Command
	public void sendemail(@BindingParam("gd") Grid gd) {
		List<EmbaseModel> checklist = new ArrayList<EmbaseModel>();
		for (int i = 0; i < gd.getPageSize(); i++) {
			if (gd.getCell(i, 10) != null) {
				Cell cell = (Cell) gd.getCell(i, 10);
				if (cell.getChildren().size() > 0) {
					Checkbox ck = (Checkbox) cell.getChildren().get(0);
					if (ck.isChecked()) {
						EmbaseModel m = ck.getValue();
						checklist.add(m);
					}
				}
			}
		}
		Map map = new HashMap<>();
		map.put("list", checklist);
		Window window = (Window) Executions.createComponents(
				"/SmsMessage/Sms_GroupSendEmail.zul", null, map);
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}
