/*
 * 创建人：张志强
 * 创建时间：2013-10-17
 * 用途：合同打印及导出列表页面Controller
 */
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
import bll.CoCompact.Compact_PrintSelectBll;

public class Compact_PrintSelectController {
	private List cocoBaseList; // 合同基本信息列表
	private List cocoAddnameList; // 添加人列表
	private Compact_PrintSelectBll cocoBll = new Compact_PrintSelectBll();

	// 查询条件
	private String coco_company;
	private String coco_compactid;
	private String coco_addname;
	private String coco_state;

	public Compact_PrintSelectController() {
		// 首页加载显示所有数据
		cocoBaseList = cocoBll.getCoCoBaseAll();
		// 查询中添加人下拉框
		cocoAddnameList = cocoBll.getCoCoAddname(" AND coco_state=2");
	}

	// 查询委托机构基本信息
	@Command("search")
	@NotifyChange("cocoBaseList")
	public void search() {
		cocoBaseList = cocoBll.searchCoCoBase(coco_company, coco_compactid,
				coco_state, coco_addname);
	}
	
	//打印合同
	@Command("compact_add")
	public void compact_add(@BindingParam("coabM") CoCompactModel coabM) {
		Map arg = new HashMap();  
		arg.put("id", coabM.getCoco_tapr_id());
		arg.put("daid", coabM.getCoco_id());
		Window wnd = (Window) Executions.createComponents("Compact_PrintDoc.zul", null, arg);  
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
