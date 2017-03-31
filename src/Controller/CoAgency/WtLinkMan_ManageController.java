package Controller.CoAgency;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import Model.CoAgencyBaseModel;
import Model.CoAgencyLinkmanModel;
import bll.CoAgency.BaseInfo_SelBll;

public class WtLinkMan_ManageController {
	private int cabc_id = Integer.parseInt(Executions.getCurrent()
			.getParameter("cabc_id").toString());
	private List<CoAgencyLinkmanModel> linkList;
	private BaseInfo_SelBll selBll;
	private CoAgencyBaseModel coabModel;

	public WtLinkMan_ManageController() {
		selBll = new BaseInfo_SelBll();
		linkList = selBll.getWtCoAgencyLinkmanList(cabc_id);
		coabModel = selBll.getCoAgencyBaseModelByCabcId(cabc_id);
	}

	// 弹出新增联系人页面
	@Command
	public void openLinkMan_Add() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("coab_id", coabModel.getCoab_id());
		map.put("cabl_type", 1);
		map.put("cabc_id", cabc_id);
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
		map.put("coab_id", coabModel.getCoab_id());
		map.put("cabc_id", cabc_id);
		Window window = (Window) Executions.createComponents(
				"LinkMan_Delete.zul", null, map);
		window.doModal();
		refreshWin();
	}

	// 刷新列表
	@Command("refreshWin")
	public void refreshWin() {
		linkList = selBll.getWtCoAgencyLinkmanList(cabc_id);
		BindUtils.postNotifyChange(null, null, this, "linkList");
	}

	public List<CoAgencyLinkmanModel> getLinkList() {
		return linkList;
	}

}
