package Controller.CoCompact;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoBaseModel;
import Model.CoCompactCppAduitModel;
import Model.CoCompactModel;
import Model.CoCompactSAModel;
import bll.CoCompact.BaseInfo_SelectListBll;
import bll.CoCompact.CoCompact_OperateBll;
import bll.CoCompact.Compact_houseCppBll;

public class Compact_ManagerbjdaddController {

	private List<CoCompactModel> cocoBaseList; // 合同基本信息列表
	private List cocoAddnameList; // 添加人列表
	private BaseInfo_SelectListBll cocoBll = new BaseInfo_SelectListBll();
	private CoBaseModel cbm = (CoBaseModel) Executions.getCurrent().getArg()
			.get("model");
	// 查询条件
	private String coco_company;
	private String coco_compactid;
	private String coco_addname;
	private String coco_state;

	public Compact_ManagerbjdaddController() {

		coco_company = cbm.getCid().toString();
		coco_state = "inure";
		// 首页加载显示所有数据
		cocoBaseList = cocoBll.searchCoCoBaseList(coco_company, coco_compactid,
				coco_state, coco_addname, "", "");

		// 查询中添加人下拉框
		cocoAddnameList = cocoBll.getCoCoAddname("");

	}

	// 查询公司合同基本信息
	@Command("search")
	// @NotifyChange("cocoBaseList")
	public void search() {
		cocoBaseList = cocoBll.searchCoCoBaseList(coco_company, coco_compactid,
				coco_state, coco_addname, "", "");
	}

	@Command
	@NotifyChange("cocoBaseList")
	public void quotationadd(@BindingParam("a") CoCompactModel cocoM) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cid", cocoM.getCid());
		map.put("coco_id", cocoM.getCoco_id());
		map.put("colacompany", cocoM.getCoba_shortname());

		Window window = (Window) Executions.createComponents(
				"/CoQuotation/CoQuotation_Addywzx.zul", null, map);
		window.doModal();
		// cocoBaseList = cocoBll.getCoCoBaseAll();
		search();
	}

	@Command
	@NotifyChange("cocoBaseList")
	public void itemAdd(@BindingParam("a") CoCompactModel cocoM) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cid", cocoM.getCid());
		map.put("coco_id", cocoM.getCoco_id());
		map.put("company", cocoM.getCompany());
		Window window = (Window) Executions.createComponents(
				"/CoQuotation/Coquotation_ItemAdd.zul", null, map);
		window.doModal();
		search();
		// cocoBll.getCoCoBaseAll();
	}

	// 弹出公司合同详细页面
	@Command
	public void query(@BindingParam("a") CoCompactModel cocoM) {
		// 专递cocoM
		Map map = new HashMap();
		// map.put("cocoM", cocoM);
		// Window window = (Window)
		// Executions.createComponents("Compact_Detail.zul", null, map);
		map.put("coco_id", String.valueOf(cocoM.getCoco_id()));
		Window window = (Window) Executions.createComponents(
				"../CoBase/CoCompact_Info.zul", null, map);
		window.doModal();
	}

	@Command
	public void editAdd(@BindingParam("a") CoCompactModel cocoM) {
		// 专递cocoM
		Map map = new HashMap();
		map.put("cocoM", cocoM);
		Window window = (Window) Executions.createComponents(
				"../CoCompact/Compact_Detail.zul", null, map);
		window.doModal();
	}

	@Command
	public void compactinfo(@BindingParam("a") CoCompactModel cocoM) {
		// 专递cocoM
		Map map = new HashMap();
		map.put("coco_id", cocoM.getCoco_id());
		Window window = (Window) Executions.createComponents(
				"../CoBase/CoCompact_Info.zul", null, map);
		window.doModal();
	}

	@Command
	public void upPaydate(@BindingParam("a") CoCompactModel cocoM) {
		// 专递cocoM
		Map map = new HashMap();
		map.put("coco_id", cocoM.getCoco_id());
		Window window = (Window) Executions.createComponents(
				"../CoCompact/CoCompact_UpPaydate.zul", null, map);
		window.doModal();
	}

	@Command
	public void compactcheck(@BindingParam("a") CoCompactModel cocoM) {
		// 专递cocoM
		Map map = new HashMap();
		map.put("id", cocoM.getCoco_id());
		Window window = (Window) Executions.createComponents(
				"/CoCompact/Compact_CheckDoc.zul", null, map);
		window.doModal();
	}

	@Command
	@NotifyChange("cocoBaseList")
	public void modCpp(@BindingParam("a") CoCompactModel cocoM) {
		Compact_houseCppBll bll = new Compact_houseCppBll();
		List<CoCompactCppAduitModel> list = new ListModelList<>();
		CoCompactCppAduitModel m = new CoCompactCppAduitModel();
		m.setCid(Integer.valueOf(cocoM.getCid()));
		m.setCoca_state(1);
		m.setCoca_aduit(0);
		list = bll.search(m);
		if (list.size() > 0) {
			Messagebox.show("当前有未审核比例变更数据.", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		} else {

			Map map = new HashMap();
			map.put("id", cocoM.getCoco_id());
			Window window = (Window) Executions.createComponents(
					"../CoCompact/Compact_houseCppMod.zul", null, map);
			window.doModal();
			cocoBaseList = cocoBll.searchCoCoBaseList(coco_company,
					coco_compactid, coco_state, coco_addname, "", "");
		}
	}

	// 上传补充协议
	@Command
	public void co_upchange(@BindingParam("a") CoCompactModel cocoM) {
		// 专递cocoM
		Map map = new HashMap();
		map.put("cocoM", cocoM);
		Window window = (Window) Executions.createComponents(
				"../CoCompact/CoCompactSA/Compact_BcUpload.zul", null, map);
		window.doModal();
	}

	// 查看合同
	@Command("openCompactSA_InFile")
	public void openCompactSA_InFile(@BindingParam("ccsaM") CoCompactModel ccsaM) {
		Map arg = new HashMap();
		arg.put("id", ccsaM.getCoco_id());
		Window wnd = (Window) Executions.createComponents(
				"../CoCompact/CoCompactSA/Compact_SACheckDoc.zul", null, arg);
		wnd.doModal();
	}

	public List getCocoBaseList() {
		return cocoBaseList;
	}

	public void setCocoBaseList(List cocoBaseList) {
		this.cocoBaseList = cocoBaseList;
	}

	public List getCocoAddnameList() {
		return cocoAddnameList;
	}

	public void setCocoAddnameList(List cocoAddnameList) {
		this.cocoAddnameList = cocoAddnameList;
	}

	public String getCoco_company() {
		return coco_company;
	}

	public void setCoco_company(String coco_company) {
		this.coco_company = coco_company;
	}

	public String getCoco_compactid() {
		return coco_compactid;
	}

	public void setCoco_compactid(String coco_compactid) {
		this.coco_compactid = coco_compactid;
	}

	public String getCoco_addname() {
		return coco_addname;
	}

	public void setCoco_addname(String coco_addname) {
		this.coco_addname = coco_addname;
	}

	public String getCoco_state() {
		return coco_state;
	}

	public void setCoco_state(String coco_state) {
		this.coco_state = coco_state;
	}

}
