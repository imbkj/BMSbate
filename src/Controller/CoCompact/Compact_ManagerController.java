package Controller.CoCompact;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import Model.CoCompactModel;
import bll.CoCompact.BaseInfo_SelectListBll;

public class Compact_ManagerController {

	private List cocoBaseList; // 合同基本信息列表
	private List cocoAddnameList; // 添加人列表
	private BaseInfo_SelectListBll cocoBll = new BaseInfo_SelectListBll();

	// 查询条件
	private String coco_company;
	private String coco_compactid;
	private String coco_addname;
	private String coco_state;

	public Compact_ManagerController() {
		// 首页加载显示所有数据
		cocoBaseList = cocoBll.getCoCoBaseAll();
		// 查询中添加人下拉框
		cocoAddnameList = cocoBll.getCoCoAddname("");
	}

	// 查询公司合同基本信息
	@Command("search")
	@NotifyChange("cocoBaseList")
	public void search() {
		cocoBaseList = cocoBll.searchCoCoBase(coco_company, coco_compactid,
				coco_state, coco_addname,"","");
	}

	@Command
	@NotifyChange("cocoBaseList")
	public void quotationadd(@BindingParam("a") CoCompactModel cocoM) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cid", cocoM.getCid());
		map.put("coco_id", cocoM.getCoco_id());
		map.put("colacompany", cocoM.getCoba_shortname());

		Window window = (Window) Executions.createComponents(
				"/CoQuotation/Coquotation_Add.zul", null, map);
		window.doModal();
		cocoBaseList = cocoBll.getCoCoBaseAll();
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
		cocoBll.getCoCoBaseAll();
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

	// 上传补充协议
	@Command
	public void co_upchange(@BindingParam("a") CoCompactModel cocoM) {
		// 专递cocoM
		Map map = new HashMap();
		map.put("cocoM", cocoM);
		Window window = (Window) Executions.createComponents(
				"CoCompactSA/Compact_BcUpload.zul", null, map);
		window.doModal();
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
