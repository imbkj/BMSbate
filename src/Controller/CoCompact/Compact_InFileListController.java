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

public class Compact_InFileListController {
	private List cocoBaseList; // 合同基本信息列表
	private List cocoAddnameList; // 添加人列表
	private BaseInfo_SelectListBll cocoBll = new BaseInfo_SelectListBll();

	// 查询条件
	private String coco_company;
	private String coco_compactid;
	private String coco_addname;

	public Compact_InFileListController() {
		// 首页加载显示已签回的数据
		cocoBaseList = cocoBll.searchCoCoBaseList(coco_company, coco_compactid,
				"4", coco_addname,"","");
		// 查询中添加人下拉框
		cocoAddnameList = cocoBll.getCoCoAddname(" AND coco_state=4");
	}

	// 查询公司合同基本信息
	@Command("search")
	@NotifyChange("cocoBaseList")
	public void search() {
		cocoBaseList = cocoBll.searchCoCoBaseList(coco_company, coco_compactid,
				"4", coco_addname,"","");
	}
	
	//弹出公司合同归档页面
	@Command("openCompact_InFile")
	@NotifyChange("cocoBaseList")
	public void openCompact_InFile(@BindingParam("cocoM") CoCompactModel cocoM){
		//专递cocoM
		Map map = new HashMap();
		map.put("id",cocoM.getCoco_tapr_id());
		map.put("daid",cocoM.getCoco_id());
		Window window = (Window)Executions.createComponents("Compact_InFile.zul",null, map);
		window.doModal();
		cocoBaseList = cocoBll.searchCoCoBaseList(coco_company, coco_compactid,
				"4", coco_addname,"","");
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

}
