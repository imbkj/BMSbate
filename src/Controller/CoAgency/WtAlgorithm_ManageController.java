package Controller.CoAgency;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import bll.CoAgency.BaseInfo_SelBll;
import bll.SocialInsurance.Algorithm_ListBll;

import Model.CoAgencyBaseCityRelModel;
import Model.SocialInsuranceAlgorithmViewModel;
import Model.SocialInsuranceChangeModel;

public class WtAlgorithm_ManageController {
	private int cabc_id = Integer.parseInt(Executions.getCurrent()
			.getParameter("cabc_id").toString());
	private List<SocialInsuranceAlgorithmViewModel> hisSiList;
	private List<SocialInsuranceAlgorithmViewModel> nowSiList;
	private Algorithm_ListBll bll;
	private CoAgencyBaseCityRelModel coabModel;
	private boolean changeVis;
	private List<SocialInsuranceChangeModel> changeList;

	public WtAlgorithm_ManageController() {
		bll = new Algorithm_ListBll();
		BaseInfo_SelBll selbll = new BaseInfo_SelBll();
		coabModel = selbll.getCoabModel(cabc_id);
		nowSiList = bll.getSiAlgorithmSel(cabc_id, 1);
		hisSiList = bll.getSiAlgorithmSel(cabc_id, 2);
		changeList = bll.getSiAlgorithmChangeList(cabc_id);
		changeVis = changeList.size() > 0 ? true : false;
	}

	// 查看在册数据
	@Command("regData")
	public void regData(@BindingParam("sial_id") String sial_id) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("sial_id", sial_id);
		Window window;
		if ("深圳".equals(coabModel.getCity())
				&& "深圳中智经济技术合作有限公司".equals(coabModel.getCoab_name())) {
			window = (Window) Executions.createComponents(
					"/SocialInsurance/Algorithm_LocalRegisteredData.zul", null,
					map);
		} else {
			window = (Window) Executions.createComponents(
					"/SocialInsurance/Algorithm_RegisteredData.zul", null, map);
		}
		window.doModal();
	}

	// 新增算法
	@Command("addAlg")
	@NotifyChange({ "nowSiList", "hisSiList" })
	public void addAlg() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("cabc_id", String.valueOf(cabc_id));
		Window window = (Window) Executions.createComponents(
				"/SocialInsurance/Algorithm_Add.zul", null, map);
		window.doModal();
		refreshWin();
	}

	// 禁止算法
	@Command("stopAlg")
	@NotifyChange({ "nowSiList", "hisSiList" })
	public void stopAlg(@BindingParam("sial_id") String sial_id) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("sial_id", sial_id);
		Window window = (Window) Executions.createComponents(
				"/SocialInsurance/Algorithm_Stop.zul", null, map);
		window.doModal();
		refreshWin();
	}

	// 更新算法
	@Command("upAlg")
	@NotifyChange({ "nowSiList", "hisSiList" })
	public void upAlg(@BindingParam("sial_id") String sial_id) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("sial_id", sial_id);
		Window window = (Window) Executions.createComponents(
				"/SocialInsurance/Algorithm_Update.zul", null, map);
		window.doModal();
		refreshWin();
	}

	// 查看算法
	@Command("searAlg")
	public void searAlg(@BindingParam("sial_id") String sial_id) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("sial_id", sial_id);
		Window window = (Window) Executions.createComponents(
				"/SocialInsurance/Algorithm_Search.zul", null, map);
		window.doModal();
	}

	// 查看待审核算法
	@Command("searAlgChange")
	public void searAlgChange(@BindingParam("sich_id") String sich_id) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("daid", sich_id);
		map.put("submitVis", "false");
		Window window = (Window) Executions.createComponents(
				"/SocialInsurance/Algorithm_Examined.zul", null, map);
		window.doModal();
	}

	// 刷新页面
	public void refreshWin() {
		nowSiList = bll.getSiAlgorithmSel(cabc_id, 1);
		hisSiList = bll.getSiAlgorithmSel(cabc_id, 2);
		changeList = bll.getSiAlgorithmChangeList(cabc_id);
		changeVis = changeList.size() > 0 ? true : false;
		BindUtils.postNotifyChange(null, null, this, "nowSiList");
		BindUtils.postNotifyChange(null, null, this, "hisSiList");
		BindUtils.postNotifyChange(null, null, this, "changeList");
		BindUtils.postNotifyChange(null, null, this, "changeVis");
	}

	public List<SocialInsuranceAlgorithmViewModel> getHisSiList() {
		return hisSiList;
	}

	public List<SocialInsuranceAlgorithmViewModel> getNowSiList() {
		return nowSiList;
	}

	public boolean isChangeVis() {
		return changeVis;
	}

	public List<SocialInsuranceChangeModel> getChangeList() {
		return changeList;
	}

}
