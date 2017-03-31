package Controller.CoFinanceManage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import bll.CoFinanceManage.Cfma_AgencySelBll;

import Model.CoFinanceAgencyModel;
import Model.CoFinanceAgencyMonthlyBillModel;

public class Cfma_AgencyMainController {
	private final int cfat_id = Integer.parseInt(Executions.getCurrent()
			.getArg().get("cfat_id").toString());
	private CoFinanceAgencyModel cfagModel;
	private List<CoFinanceAgencyMonthlyBillModel> mbList;
	private Cfma_AgencySelBll selBll;

	public Cfma_AgencyMainController() {
		selBll = new Cfma_AgencySelBll();
		cfagModel = selBll.getTotalAccount(cfat_id);
		mbList = selBll.getAgencyMonthlyBill(cfagModel.getCfat_coab_id());
	}

	// 机构收款
	@Command("AgencyCollect")
	public void AgencyCollect(@ContextParam(ContextType.VIEW) Component view) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("coab_id", String.valueOf(cfagModel.getCfat_coab_id()));
		map.put("city", cfagModel.getCity());
		map.put("coab_name", cfagModel.getCoab_name());
		Window window = (Window) Executions.createComponents(
				"Cfma_AgencyCollect.zul", view, map);
		window.doModal();
	}

	// 账单收款
	@Command("billCollect")
	public void billCollect(@BindingParam("cfab_number") String cfab_number,
			@ContextParam(ContextType.VIEW) Component view) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("cfab_number", cfab_number);
		Window window = (Window) Executions.createComponents(
				"Cfma_AgencyBillCollect.zul", view, map);
		window.doModal();
	}

	// 领款分配
	@Command("distribution")
	public void distribution(@BindingParam("cfab_number") String cfab_number,
			@ContextParam(ContextType.VIEW) Component view) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("cfab_number", cfab_number);
		Window window = (Window) Executions.createComponents(
				"Cfma_AgencyCollectDistribution.zul", view, map);
		window.doModal();
	}

	// 账单核销
	@Command("ChargeOff")
	public void ChargeOff(@BindingParam("m") CoFinanceAgencyMonthlyBillModel m,
			@ContextParam(ContextType.VIEW) Component view) {
		Map<String, CoFinanceAgencyMonthlyBillModel> map = new HashMap<String, CoFinanceAgencyMonthlyBillModel>();
		map.put("m", m);
		Window window = (Window) Executions.createComponents(
				"Cfma_AgencyBillChargeOff.zul", view, map);
		window.doModal();
	}

	// 查看机构账单
	@Command("viewBill")
	public void viewBill(@BindingParam("cfab_number") String cfab_number) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("cfab_number", cfab_number);
		Window window = (Window) Executions.createComponents(
				"Cfma_AgencyMonthlyBillView.zul", null, map);
		window.doModal();
	}

	// 查看收款纪录
	@Command("collectLog")
	public void collectLog() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("coab_id", String.valueOf(cfagModel.getCfat_coab_id()));
		Window window = (Window) Executions.createComponents(
				"Cfma_AgencyCollectLog.zul", null, map);
		window.doModal();
	}

	// 子页面刷新cfagModel
	@Command("refreshCfagModel")
	@NotifyChange("cfagModel")
	public void refreshTotalModel() {
		cfagModel = selBll.getTotalAccount(cfat_id);
	}

	// 子页面刷新cfagModel 以及 mbList
	@Command("refreshAll")
	@NotifyChange({ "cfagModel", "mbList" })
	public void refreshWin() {
		cfagModel = selBll.getTotalAccount(cfat_id);
		mbList = selBll.getAgencyMonthlyBill(cfagModel.getCfat_coab_id());
	}

	public CoFinanceAgencyModel getCfagModel() {
		return cfagModel;
	}

	public List<CoFinanceAgencyMonthlyBillModel> getMbList() {
		return mbList;
	}

}
