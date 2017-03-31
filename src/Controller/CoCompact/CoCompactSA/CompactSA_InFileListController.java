package Controller.CoCompact.CoCompactSA;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import bll.CoCompact.CoCompactSA.CoCompactSA_SelectBll;
import Model.CoCompactSAModel;
import Model.EmBaseCompactListModel;

public class CompactSA_InFileListController {
	private List ccsaBaseList; // 基本信息列表
	private List ccsaAddnameList; // 添加人列表
	private CoCompactSA_SelectBll ccsaBll = new CoCompactSA_SelectBll();

	// 查询条件
	private String coba_company;
	private String coco_compactid;
	private String ccsa_addname;
	private String cid;

	public CompactSA_InFileListController() {
		cid = Executions.getCurrent().getArg().get("cid").toString();
		// 首页加载显示已签回的数据
		ccsaBaseList = ccsaBll.searchCCSABase(cid, coco_compactid, "3",
				ccsa_addname);
		// 查询中添加人下拉框
		ccsaAddnameList = ccsaBll.getCCSAAddname(" AND ccsa_state=3");

	}

	// 查询公司合同基本信息
	@Command("search")
	@NotifyChange("cocoBaseList")
	public void search() {
		ccsaBaseList = ccsaBll.searchCCSABase(coba_company, coco_compactid,
				"3", ccsa_addname);
	}

	// 弹出公司合同归档页面
	@Command("openCompactSA_InFile1")
	@NotifyChange("ccsaBaseList")
	public void openCompactSA_InFile1(
			@BindingParam("ccsaM") CoCompactSAModel ccsaM) {
		// 专递ccsaM
		Map map = new HashMap();
		map.put("id", ccsaM.getCcsa_tapr_id());
		map.put("daid", ccsaM.getCcsa_id());
		Window window = (Window) Executions.createComponents(
				"CompactSA_InFile.zul", null, map);
		window.doModal();
		ccsaBaseList = ccsaBll.searchCCSABase(coba_company, coco_compactid,
				"3", ccsa_addname);
	}

	// 查看合同
	@Command("openCompactSA_InFile")
	public void openCompactSA_InFile(
			@BindingParam("ccsaM") CoCompactSAModel ccsaM) {
		Map arg = new HashMap();
		arg.put("id", ccsaM.getCcsa_id());
		Window wnd = (Window) Executions.createComponents(
				"../CoCompact/CoCompactSA/Compact_SACheckDoc.zul", null, arg);
		wnd.doModal();
	}

	public List getCcsaBaseList() {
		return ccsaBaseList;
	}

	public void setCcsaBaseList(List ccsaBaseList) {
		this.ccsaBaseList = ccsaBaseList;
	}

	public List getCcsaAddnameList() {
		return ccsaAddnameList;
	}

	public void setCcsaAddnameList(List ccsaAddnameList) {
		this.ccsaAddnameList = ccsaAddnameList;
	}

	public String getCoba_company() {
		return coba_company;
	}

	public void setCoba_company(String coba_company) {
		this.coba_company = coba_company;
	}

	public String getCcsa_addname() {
		return ccsa_addname;
	}

	public void setCcsa_addname(String ccsa_addname) {
		this.ccsa_addname = ccsa_addname;
	}

	public String getCoco_compactid() {
		return coco_compactid;
	}

	public void setCoco_compactid(String coco_compactid) {
		this.coco_compactid = coco_compactid;
	}

}
