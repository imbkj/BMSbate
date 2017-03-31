package Controller.SystemControl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.North;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import bll.SystemControl.AlarmClassBll;

import Model.AlarmClassModel;

public class AlarmClass_ManagerController extends SelectorComposer<Component> {
	private ListModelList<AlarmClassModel> addnamelist;
	private ListModelList<AlarmClassModel> amlist;

	private String alarmName;
	private String addName;
	private String Alarmstate;

	private AlarmClassModel am = new AlarmClassModel();
	AlarmClassBll bll = new AlarmClassBll();

	public AlarmClass_ManagerController() {
	}

	// 初始化
	@Init
	public void init() throws SQLException {

		this.addnamelist = new ListModelList<AlarmClassModel>(
				bll.getAlarmAddNameList());
		this.amlist = new ListModelList<AlarmClassModel>(bll.getAlarmClassList(
				"%", "%", "%"));
	}

	// 高级查询
	@Command("SearchList")
	@NotifyChange("amlist")
	public void SearchList(@BindingParam("a") Textbox tb,
			@BindingParam("b") Combobox cbAddName,
			@BindingParam("c") Combobox cbState, @BindingParam("d") North north) {
		alarmName = tb.getValue().toString().equals("") ? "%" : tb.getValue()
				.toString() + "%";
		addName = cbAddName.getValue().toString().equals("") ? "%" : cbAddName
				.getValue().toString().equals("全部") ? "%" : cbAddName
				.getValue().toString() + "%";
		if (cbState.getSelectedIndex() >= 0) {
			if (cbState.getSelectedItem().getValue().toString().equals("")) {
				Alarmstate = "%";
			} else {
				Alarmstate = cbState.getSelectedItem().getValue().toString();
			}
		} else {
			Alarmstate = "%";
		}
		this.amlist = new ListModelList<AlarmClassModel>(bll.getAlarmClassList(
				alarmName, addName, Alarmstate));

		north.setOpen(false);
	}

	// 更新项目类型列表
	@Command("updateAlarmClass")
	@NotifyChange("amlist")
	public void SearchList(@BindingParam("id") Integer id) {

		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("id", id);
		Window window = (Window) Executions.createComponents(
				"AlarmClass_Mod.zul", null, map);
		window.doModal();

		this.amlist = new ListModelList<AlarmClassModel>(
				bll.getAlarmClassById(id));
	}

	// 打开预警项目明细页面
	@Command("linkInfo")
	public void linkUrl(@BindingParam("id") Integer id) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("id", id);
		Window window = (Window) Executions.createComponents(
				"AlarmInfo_Manager.zul", null, map);
		window.doModal();
	}

	public ListModelList<AlarmClassModel> getAddnamelist() {
		return addnamelist;
	}

	public void setAddnamelist(ListModelList<AlarmClassModel> addnamelist) {
		this.addnamelist = addnamelist;
	}

	public ListModelList<AlarmClassModel> getAmlist() {
		return amlist;
	}

	public void setAmlist(ListModelList<AlarmClassModel> amlist) {
		this.amlist = amlist;
	}

	public String getAddName() {
		return addName;
	}

	public void setAddName(String addName) {
		this.addName = addName;
	}

	public AlarmClassModel getAm() {
		return am;
	}

	public void setAm(AlarmClassModel am) {
		this.am = am;
	}

}
