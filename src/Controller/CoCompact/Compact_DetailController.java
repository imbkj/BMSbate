package Controller.CoCompact;

import impl.CoCompactOperateComparatorImpl;

import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import Model.CoCompactModel;
import Model.CoCompactOperateGroupingModel;
import Model.CoOfferListModel;
import bll.CoCompact.CoCompact_OperateBll;
import bll.CoCompact.Compact_DetailBll;
import bll.CoQuotation.CoQuotationInfoBll;

public class Compact_DetailController {
	private List cocoBaseList; // 合同基本信息列表
	private List cocoAddnameList; // 添加人列表
	private Compact_DetailBll cocoBll = new Compact_DetailBll();
	private CoCompact_OperateBll cocoBll1 = new CoCompact_OperateBll();
	private List<CoOfferListModel> coofferinfoList;
	private CoCompactOperateGroupingModel coofferInfoGroupList;
	private boolean showGroup = false;
	int coco_id = ((CoCompactModel) Executions.getCurrent().getArg()
			.get("cocoM")).getCoco_id();

	@Wire
	private Textbox compact_cid;

	// 查询条件
	private String coco_company;
	private String coco_compactid;
	private String coco_addname;
	private String coco_state;

	public Compact_DetailController() throws Exception {
		// 首页加载显示所有数据
		cocoBaseList = cocoBll.getCoCoBaseAll();
		setCoofferinfoList(new ListModelList<CoOfferListModel>(
				new CoQuotationInfoBll().getCoOfferlist(coco_id + "")));// 报价单详情
	}

	// 弹出产品费用调整页面
	@Command("co_change")
	public void co_change(@BindingParam("a") CoOfferListModel cocoM) {
		// 专递cocoM
		Map map = new HashMap();
		map.put("cocoM", cocoM);
		Window window = (Window) Executions.createComponents(
				"../CoCompact/Compact_CoChange.zul", null, map);
		window.doModal();
	}

	// 弹出产品终止页面
	@Command("co_del")
	public void co_del(@BindingParam("a") CoOfferListModel cocoM) {
		// 专递cocoM
		Map map = new HashMap();
		map.put("cocoM", cocoM);
		Window window = (Window) Executions.createComponents(
				"../CoCompact/Compact_CoStop.zul", null, map);
		window.doModal();
	}

	// 弹出公司合同详细页面
	@Command("openCoCompact_Detail")
	public void openCoCompact_Detail(@BindingParam("cocoM") CoCompactModel cocoM) {
		// 专递cocoM
		System.out.print(cocoM.getCoco_id());
		Map map = new HashMap();
		map.put("cocoM", cocoM);
		Window window = (Window) Executions.createComponents(
				"../CoCompact/Compact_Detail.zul", null, map);
		window.doModal();
	}
	
	// 上传补充协议
		@Command
		public void co_upchange(@BindingParam("a") CoOfferListModel cocoM) {
			// 专递cocoM
			Map map = new HashMap();
			map.put("cocoM", cocoM);
			Window window = (Window) Executions.createComponents(
					"../CoCompact/CoCompactSA/Compact_BcUpload.zul", null, map);
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

	public List<CoOfferListModel> getCoofferinfoList() {
		return coofferinfoList;
	}

	public void setCoofferinfoList(List<CoOfferListModel> coofferinfoList) {
		this.coofferinfoList = coofferinfoList;
	}

	public CoCompactOperateGroupingModel getCoofferInfoGroupList() {
		return coofferInfoGroupList;
	}

	public void setCoofferInfoGroupList(
			CoCompactOperateGroupingModel coofferInfoGroupList) {
		this.coofferInfoGroupList = coofferInfoGroupList;
	}

	public boolean isShowGroup() {
		return showGroup;
	}

	public void setShowGroup(boolean showGroup) {
		this.showGroup = showGroup;
	}
}
