package Controller.CoFinanceManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Window;

import bll.CoFinanceManage.Cfma_CollectImportBll;

import Model.CoFinanceCollectImportModel;
import Util.DateStringChange;

public class Cfma_CollectImportListController {
	private List<CoFinanceCollectImportModel> ciList;
	private List<CoFinanceCollectImportModel> winCiList;
	private Cfma_CollectImportBll bll;
	private CoFinanceCollectImportModel recordedModel;
	// 查询参数
	private String transactionNo = "";
	private String company = "";
	private String account = "";
	private String addname = "";
	private String state = "";

	public Cfma_CollectImportListController() {
		bll = new Cfma_CollectImportBll();
		ciList = winCiList = bll.getCollectImportList();
	}

	// 检索
	@Command("search")
	@NotifyChange("winCiList")
	public void search(
			@BindingParam("dbTransactionTime") Datebox dbTransactionTime,
			@BindingParam("dbAddtime") Datebox dbAddtime) {
		try {
			String transactionTime = dbTransactionTime.getValue() != null ? DateStringChange
					.DatetoSting(dbTransactionTime.getValue(), "yyyy-MM-dd")
					: "";
			String addtime = dbAddtime.getValue() != null ? DateStringChange
					.DatetoSting(dbAddtime.getValue(), "yyyy-MM-dd") : "";
			if (!"".equals(transactionNo) || !"".equals(company)
					|| !"".equals(account) || !"".equals(addname)
					|| !"".equals(state) || !"".equals(transactionTime)
					|| !"".equals(addtime)) {
				winCiList = searchData(transactionTime, addtime);
			} else {
				winCiList = ciList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 检索数据处理
	private List<CoFinanceCollectImportModel> searchData(
			String transactionTime, String addtime) {
		List<CoFinanceCollectImportModel> list = new ArrayList<CoFinanceCollectImportModel>();
		try {
			for (CoFinanceCollectImportModel m : ciList) {
				if (!"".equals(transactionNo)) {
					if (!transactionNo.equals(m.getCfci_transactionNo()))
						continue;
				}
				if (!"".equals(company) && m.getCfci_company() != null) {
					if (!m.getCfci_company().contains(company))
						continue;
				}
				if (!"".equals(account)) {
					if (!account.equals(m.getCfci_account()))
						continue;
				}
				if (!"".equals(addname)) {
					if (!addname.equals(m.getCfci_addname()))
						continue;
				}
				if (!"".equals(state)) {
					if (!state.equals(m.getCfci_stateStr()))
						continue;
				}
				if (!"".equals(transactionTime)) {
					if (!transactionTime.equals(m.getCfci_transactionTime()))
						continue;
				}
				if (!"".equals(addtime)) {
					if (!m.getCfci_addtime().contains(addtime))
						continue;
				}
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 导入收款
	@Command("importCollect")
	public void importCollect(@ContextParam(ContextType.VIEW) Component view) {
		Window window = (Window) Executions.createComponents(
				"Cfma_CollectImport.zul", view, null);
		window.doModal();
	}

	// 入账
	@Command("recorded")
	public void recorded(@BindingParam("m") CoFinanceCollectImportModel m,
			@ContextParam(ContextType.VIEW) Component view) {
		this.recordedModel = m;
		Map<String, String> map = new HashMap<String, String>();
		map.put("cfci_id", String.valueOf(m.getCfci_id()));
		Window window = (Window) Executions.createComponents(
				"Cfma_CollectImportRecorded.zul", view, map);
		window.doModal();
	}

	// 查看出错记录
	@Command("importErr")
	public void importErr() {
		Window window = (Window) Executions.createComponents(
				"Cfma_CollectImportErrList.zul", null, null);
		window.doModal();
	}

	// 查看收款纪录
	@Command("collectLog")
	public void collectLog(@BindingParam("m") CoFinanceCollectImportModel m) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			if (m.getCfci_type() == 1) {
				map.put("cid", String.valueOf(m.getCid()));
				map.put("company", String.valueOf(m.getCfci_company()));
				Window window = (Window) Executions.createComponents(
						"Cfma_CollectLog.zul", null, map);
				window.doModal();
			} else if (m.getCfci_type() == 2) {
				map.put("coab_id", String.valueOf(m.getCfci_coab_id()));
				Window window = (Window) Executions.createComponents(
						"Cfma_AgencyCollectLog.zul", null, map);
				window.doModal();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 入账刷新列表
	@Command("recordedToRefreshWin")
	public void recordedToRefreshWin() {
		recordedModel.setCfci_state(1);
		recordedModel.setOpVisible(false);
		BindUtils.postNotifyChange(null, null, recordedModel, "cfci_stateStr");
		BindUtils.postNotifyChange(null, null, recordedModel, "cfci_state");
		BindUtils.postNotifyChange(null, null, recordedModel, "opVisible");
	}

	// 刷新列表
	@Command("refreshWin")
	@NotifyChange("winCiList")
	public void refreshWin() {
		ciList = winCiList = bll.getCollectImportList();
	}

	public List<CoFinanceCollectImportModel> getWinCiList() {
		return winCiList;
	}

	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAddname() {
		return addname;
	}

	public void setAddname(String addname) {
		this.addname = addname;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
