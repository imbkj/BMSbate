package Controller.Workflow;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkmax.zul.Chosenbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.North;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import bll.Workflow.WfClassBll;
import bll.Workflow.WfDefinationBll;

import Model.WfClassModel;
import Model.WfDefinationModel;

public class WfDefination_ListController extends SelectorComposer<Component> {
	private ListModelList<WfClassModel> wfClassNameList;// 表头内任务类型列表
	private ListModelList<WfClassModel> wfClassNameList2;// 高级查询内任务类型列表
	private ListModelList<WfDefinationModel> wfDefinationList;
	private ListModelList<WfDefinationModel> wfDefinationNameList;// 表头内任务名称列表
	private ListModelList<WfDefinationModel> wfDefinationNameList2;// 高级查询内任务名称列表
	private ListModelList<WfDefinationModel> wfDefinationCodeList;
	private ListModelList<WfDefinationModel> wfDefinationAddNameList;

	private String wfcl_name;
	private String wfde_code;
	private String wfde_name;
	private Integer wfde_state;
	private String wfde_addname;
	private Date wfde_addtime;
	private String wfde_stateName;

	private WfDefinationModel wfdModel = new WfDefinationModel();

	WfDefinationBll wfdBll = new WfDefinationBll();
	WfClassBll wfcBll = new WfClassBll();

	@Wire
	Chosenbox missionType;
	@Wire
	Textbox codeName;
	@Wire
	Chosenbox missionName;
	@Wire
	Combobox wfdestate;
	@Wire
	Grid gdWfDefination;
	@Wire
	North north;

	// 初始化变量
	@Init
	public void init() {

		this.wfClassNameList = new ListModelList<WfClassModel>(
				wfcBll.getClassNameList());
		this.wfClassNameList2 = new ListModelList<WfClassModel>(
				wfcBll.getClassNameList());
		this.wfDefinationList = new ListModelList<WfDefinationModel>(
				wfdBll.getDefinationList(wfdModel));
		this.wfDefinationNameList = new ListModelList<WfDefinationModel>(
				wfdBll.getDefinationNameList(""));
		this.wfDefinationNameList2 = new ListModelList<WfDefinationModel>(
				wfdBll.getDefinationNameList(""));
		this.wfDefinationCodeList = new ListModelList<WfDefinationModel>(
				wfdBll.getDefinationCodeList(""));
		this.wfDefinationAddNameList = new ListModelList<WfDefinationModel>(
				wfdBll.getDefinationAddNameList(""));

		wfcl_name = "";
		wfde_code = "";
		wfde_name = "";
		wfde_state = null;
		wfde_addname = "";
		wfde_addtime = null;
		wfde_stateName = "";
	}

	// 高级查询
	@Command("SearchList")
	@NotifyChange({ "wfDefinationList", "wfClassList" })
	public void SearchList(@BindingParam("a") Chosenbox missionType,
			@BindingParam("b") Textbox codeName,
			@BindingParam("c") Chosenbox missionName,
			@BindingParam("d") Combobox wfdestate,
			@BindingParam("e") North north) {

		wfde_state = wfdestate.getSelectedIndex() >= 0 ? Integer
				.valueOf(wfdestate.getSelectedItem().getValue().toString())
				: null;
		wfdModel.setWfcl_name(missionType.getSelectedObjects().toString()
				.replace("[", "").replace("]", ""));
		wfdModel.setWfde_code(codeName.getValue());
		wfdModel.setWfde_name(missionName.getSelectedObjects().toString()
				.replace("[", "").replace("]", ""));
		wfdModel.setWfde_state(wfde_state);
		this.wfDefinationList = new ListModelList<WfDefinationModel>(
				wfdBll.getDefinationList(wfdModel));
		north.setOpen(false);

	}

	// 表头查询
	@Command("tbSearch")
	@NotifyChange({ "wfDefinationList", "wfClassList" })
	public void tbSearch() {

		wfdModel.setWfcl_name(wfcl_name);
		wfdModel.setWfde_code(wfde_code);
		wfdModel.setWfde_name(wfde_name);
		wfdModel.setWfde_addname(wfde_addname);
		wfdModel.setWfde_addtime(wfde_addtime);
		wfdModel.setWfde_stateName(wfde_stateName);
		this.wfDefinationList = new ListModelList<WfDefinationModel>(
				wfdBll.getDefinationList(wfdModel));
	}

	//更新任务信息
	@Command("updateMission")
	@NotifyChange({ "wfDefinationList", "wfClassList" })
	public void ModInfo(@BindingParam("id") Integer i) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("id", i);
		Window window = (Window) Executions.createComponents(
				"WfDefination_Mod.zul", null, map);
		window.doModal();

		this.wfClassNameList = new ListModelList<WfClassModel>(
				wfcBll.getClassNameList());
		this.wfClassNameList2 = new ListModelList<WfClassModel>(
				wfcBll.getClassNameList());
		this.wfDefinationList = new ListModelList<WfDefinationModel>(
				wfdBll.getDefinationList(wfdModel));
		this.wfDefinationNameList = new ListModelList<WfDefinationModel>(
				wfdBll.getDefinationNameList(""));
		this.wfDefinationNameList2 = new ListModelList<WfDefinationModel>(
				wfdBll.getDefinationNameList(""));
		this.wfDefinationCodeList = new ListModelList<WfDefinationModel>(
				wfdBll.getDefinationCodeList(""));
		this.wfDefinationAddNameList = new ListModelList<WfDefinationModel>(
				wfdBll.getDefinationAddNameList(""));
	}

	@Command("linkList")
	@NotifyChange("wfDefinationList")
	public void getChildren(@BindingParam("id") String str) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", str);
		Window window = (Window) Executions.createComponents("WfNode_List.zul",
				null, map);
		window.doModal();

	}

	public ListModelList<WfClassModel> getWfClassNameList() {
		return wfClassNameList;
	}

	public void setWfClassNameList(ListModelList<WfClassModel> wfClassNameList) {
		this.wfClassNameList = wfClassNameList;
	}

	public ListModelList<WfDefinationModel> getWfDefinationList() {
		return wfDefinationList;
	}

	public void setWfDefinationList(
			ListModelList<WfDefinationModel> wfDefinationList) {
		this.wfDefinationList = wfDefinationList;
	}

	public ListModelList<WfDefinationModel> getWfDefinationNameList() {
		return wfDefinationNameList;
	}

	public void setWfDefinationNameList(
			ListModelList<WfDefinationModel> wfDefinationNameList) {
		this.wfDefinationNameList = wfDefinationNameList;
	}

	public ListModelList<WfDefinationModel> getWfDefinationCodeList() {
		return wfDefinationCodeList;
	}

	public void setWfDefinationCodeList(
			ListModelList<WfDefinationModel> wfDefinationCodeList) {
		this.wfDefinationCodeList = wfDefinationCodeList;
	}

	public ListModelList<WfDefinationModel> getWfDefinationAddNameList() {
		return wfDefinationAddNameList;
	}

	public void setWfDefinationAddNameList(
			ListModelList<WfDefinationModel> wfDefinationAddNameList) {
		this.wfDefinationAddNameList = wfDefinationAddNameList;
	}

	public String getWfcl_name() {
		return wfcl_name;
	}

	public void setWfcl_name(String wfcl_name) {
		this.wfcl_name = wfcl_name;
	}

	public String getWfde_code() {
		return wfde_code;
	}

	public void setWfde_code(String wfde_code) {
		this.wfde_code = wfde_code;
	}

	public String getWfde_name() {
		return wfde_name;
	}

	public void setWfde_name(String wfde_name) {
		this.wfde_name = wfde_name;
	}

	public Integer getWfde_state() {
		return wfde_state;
	}

	public void setWfde_state(Integer wfde_state) {
		this.wfde_state = wfde_state;
	}

	public String getWfde_addname() {
		return wfde_addname;
	}

	public void setWfde_addname(String wfde_addname) {
		this.wfde_addname = wfde_addname;
	}

	public Date getWfde_addtime() {
		return wfde_addtime;
	}

	public void setWfde_addtime(Date wfde_addtime) {
		this.wfde_addtime = wfde_addtime;
	}

	public ListModelList<WfClassModel> getWfClassNameList2() {
		return wfClassNameList2;
	}

	public void setWfClassNameList2(ListModelList<WfClassModel> wfClassNameList2) {
		this.wfClassNameList2 = wfClassNameList2;
	}

	public ListModelList<WfDefinationModel> getWfDefinationNameList2() {
		return wfDefinationNameList2;
	}

	public void setWfDefinationNameList2(
			ListModelList<WfDefinationModel> wfDefinationNameList2) {
		this.wfDefinationNameList2 = wfDefinationNameList2;
	}

	public String getWfde_stateName() {
		return wfde_stateName;
	}

	public void setWfde_stateName(String wfde_stateName) {
		this.wfde_stateName = wfde_stateName;
	}


}
