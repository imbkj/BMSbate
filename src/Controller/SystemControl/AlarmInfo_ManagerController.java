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

import bll.SystemControl.AlarmInfoBll;

import Model.AlarmClassModel;
import Model.AlarmInfoModel;

public class AlarmInfo_ManagerController extends SelectorComposer<Component> {

	private ListModelList<AlarmInfoModel> amlist;
	private ListModelList<AlarmInfoModel> nameList;

	AlarmInfoBll bll = new AlarmInfoBll();

	private final Integer alcl_id = Integer.parseInt(Executions.getCurrent()
			.getArg().get("id").toString());

	private String alarmName;
	private String alarmState;
	private String alarmContent;
	private String warning;

	// 初始化
	@Init
	public void init() throws SQLException {
		this.amlist = new ListModelList<AlarmInfoModel>(
				bll.getAlarmInfoList(alcl_id));
		this.nameList = new ListModelList<AlarmInfoModel>(
				bll.getAlarmInfoDisNameByAlclId(alcl_id));

	}

	//更新项目信息列表
	@Command("updateAlarmInfo")
	@NotifyChange({ "amlist", "nameList" })
	public void ModAlarmInfo(@BindingParam("id") Integer id) {
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("id", id);
		Window window = (Window) Executions.createComponents(
				"AlarmInfo_Mod.zul", null, map);
		window.doModal();
		
		this.amlist = new ListModelList<AlarmInfoModel>(
				bll.getAlarmInfoList(alcl_id));
		this.nameList = new ListModelList<AlarmInfoModel>(
				bll.getAlarmInfoDisNameByAlclId(alcl_id));
				
	}

	//查询项目信息
	@Command("SearchInfo")
	@NotifyChange({ "amlist", "nameList" })
	public void SearchInfo(@BindingParam("a") Combobox cbItem,
			@BindingParam("b") Combobox cbState,
			@BindingParam("c") Textbox tbContent,
			@BindingParam("d") Combobox cbw, @BindingParam("e") North north) {

		alarmName = cbItem.getSelectedIndex() >= 0 ? cbItem.getSelectedItem()
				.getValue().toString() : "";

		alarmState = cbState.getSelectedIndex() >= 0 ? cbState
				.getSelectedItem().getValue().toString() : "";

		warning = cbw.getSelectedIndex() >= 0 ? cbw.getSelectedItem()
				.getValue().toString() : "";

		alarmContent = tbContent.getValue().toString();

		this.amlist = new ListModelList<AlarmInfoModel>(
				bll.getAlarmInfoListByParam(alcl_id, alarmName, alarmContent,
						warning, alarmState));

		north.setOpen(false);
	}

	//重置项目信息列表
	@Command("resetInfo")
	@NotifyChange({ "amlist", "nameList" })
	public void resetInfo(@BindingParam("a") Combobox cbItem,
			@BindingParam("b") Combobox cbState,
			@BindingParam("c") Textbox tbContent,
			@BindingParam("d") Combobox cbw) {
		
		this.amlist = new ListModelList<AlarmInfoModel>(
				bll.getAlarmInfoList(alcl_id));
		this.nameList = new ListModelList<AlarmInfoModel>(
				bll.getAlarmInfoDisNameByAlclId(alcl_id));
	}

	public ListModelList<AlarmInfoModel> getAmlist() {
		return amlist;
	}

	public void setAmlist(ListModelList<AlarmInfoModel> amlist) {
		this.amlist = amlist;
	}

	public ListModelList<AlarmInfoModel> getNameList() {
		return nameList;
	}

	public void setNameList(ListModelList<AlarmInfoModel> nameList) {
		this.nameList = nameList;
	}

	public String getAlarmName() {
		return alarmName;
	}

	public void setAlarmName(String alarmName) {
		this.alarmName = alarmName;
	}

	public String getAlarmState() {
		return alarmState;
	}

	public void setAlarmState(String alarmState) {
		this.alarmState = alarmState;
	}

	public String getAlarmContent() {
		return alarmContent;
	}

	public void setAlarmContent(String alarmContent) {
		this.alarmContent = alarmContent;
	}

	public String getWarning() {
		return warning;
	}

	public void setWarning(String warning) {
		this.warning = warning;
	}

	public Integer getAlcl_id() {
		return alcl_id;
	}
}
