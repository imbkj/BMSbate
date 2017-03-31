package Controller.CoAgency;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import bll.CoAgency.BaseInfo_SelBll;

import Model.CoAgencyLinkmanModel;

public class LinkMan_ManageController {
	private int coab_id = Integer.parseInt(Executions.getCurrent().getArg()
			.get("coab_id").toString());
	private int cabl_type = Integer.parseInt(Executions.getCurrent().getArg()
			.get("cabl_type").toString());
	private String coab_name = Executions.getCurrent().getArg()
			.get("coab_name").toString();

	private List<CoAgencyLinkmanModel> linkList;
	private BaseInfo_SelBll selBll;

	public LinkMan_ManageController() {
		selBll = new BaseInfo_SelBll();
		linkList = selBll.getCoAgencyLinkmanList(coab_id, cabl_type);
	}

	// 弹出新增联系人页面
	@Command
	public void openLinkMan_Add() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("coab_id", coab_id);
		map.put("cabl_type", cabl_type);
		map.put("cabc_id", 0);
		Window window = (Window) Executions.createComponents("LinkMan_Add.zul",
				null, map);
		window.doModal();
		refreshWin();
	}

	// 弹出查看联系人信息页面
	@Command("selLink")
	public void selLink(@BindingParam("m") CoAgencyLinkmanModel m) {
		// 专递Model
		Map<String, CoAgencyLinkmanModel> map = new HashMap<String, CoAgencyLinkmanModel>();
		map.put("caliM", m);
		Window window = (Window) Executions.createComponents(
				"LinkMan_Detail.zul", null, map);
		window.doModal();
	}

	// 弹出修改联系人信息页面
	@Command("upLink")
	public void upLink(@BindingParam("m") CoAgencyLinkmanModel m) {
		// 专递Model
		Map<String, CoAgencyLinkmanModel> map = new HashMap<String, CoAgencyLinkmanModel>();
		map.put("caliM", m);
		Window window = (Window) Executions.createComponents(
				"LinkMan_Update.zul", null, map);
		window.doModal();
		refreshWin();
	}

	// 弹出删除联系人信息页面
	@Command("delLink")
	public void delLink(@BindingParam("cali_id") int cali_id) {
		// 专递参数
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("cali_id", cali_id);
		map.put("coab_id", coab_id);
		map.put("cabc_id", 0);
		Window window = (Window) Executions.createComponents(
				"LinkMan_Delete.zul", null, map);
		window.doModal();
		refreshWin();
	}

	// 刷新列表
	@Command("refreshWin")
	public void refreshWin() {
		linkList = selBll.getCoAgencyLinkmanList(coab_id, cabl_type);
		BindUtils.postNotifyChange(null, null, this, "linkList");
	}

	public List<CoAgencyLinkmanModel> getLinkList() {
		return linkList;
	}

	public int getCoab_id() {
		return coab_id;
	}

	public String getCoab_name() {
		return coab_name;
	}

}
