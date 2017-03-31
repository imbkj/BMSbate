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
import Util.UserInfo;
import bll.CoCompact.BaseInfo_SelectListBll;

public class BaseInfo_SelectListController {

	private List cocoBaseList; // 合同基本信息列表
	private List cocoAddnameList; // 添加人列表
	private BaseInfo_SelectListBll cocoBll = new BaseInfo_SelectListBll();

	// 查询条件
	private String coco_company;
	private String coco_compactid;
	private String coco_addname;
	private String coco_state;
	private String coco_client;
	private String coco_type;

	public BaseInfo_SelectListController() {
		coco_addname=UserInfo.getUsername();
		// 首页加载显示所有数据
		cocoBaseList = cocoBll.searchCoCoBaseList(coco_company, coco_compactid,
				coco_state, coco_addname,coco_client,coco_type);
		// 查询中添加人下拉框
		cocoAddnameList = cocoBll.getCoCoAddname("");
	}

	// 查询公司合同基本信息
	@Command("search")
	@NotifyChange("cocoBaseList")
	public void search() {
		cocoBaseList = cocoBll.searchCoCoBaseList(coco_company, coco_compactid,
				coco_state, coco_addname,coco_client,coco_type);
	}
	
	//弹出公司合同详细页面
	@Command("openCoCompact_Detail")
	public void openCoCompact_Detail(@BindingParam("cocoM") CoCompactModel cocoM){
		//专递cocoM
		Map map = new HashMap();
		map.put("cocoM",cocoM);
		Window window = (Window)Executions.createComponents("Compact_Detail.zul",null, map);
		window.doModal();
	}
	
	//弹出公司合同文档页面
		@Command("openCoCompact_word")
		public void openCoCompact_word(@BindingParam("cocoM") CoCompactModel cocoM){
			//专递cocoM
			Map map = new HashMap();
			map.put("id",cocoM.getCoco_id());
			Window window = (Window) Executions.createComponents(
					"/CoCompact/Compact_CheckDoc.zul", null, map);
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

	public String getCoco_client() {
		return coco_client;
	}

	public void setCoco_client(String coco_client) {
		this.coco_client = coco_client;
	}

	public String getCoco_type() {
		return coco_type;
	}

	public void setCoco_type(String coco_type) {
		this.coco_type = coco_type;
	}

}
