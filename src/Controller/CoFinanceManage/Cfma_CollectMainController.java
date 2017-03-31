package Controller.CoFinanceManage;

import java.math.BigDecimal;
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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoFinanceMonthlyBillModel;
import Model.CoFinanceTotalAccountModel;
import Util.RegexUtil;
import Util.UserInfo;
import bll.CoFinanceManage.Cfma_CollectOperateBll;
import bll.CoFinanceManage.Cfma_SelBll;

public class Cfma_CollectMainController {
	private final int cid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("cid").toString());

	private Cfma_SelBll bll;
	private CoFinanceTotalAccountModel totalModel;
	private List<CoFinanceMonthlyBillModel> billList;
	private List<CoFinanceMonthlyBillModel> winbillList;
	private String billNo;

	public Cfma_CollectMainController() {
		billNo = "";
		bll = new Cfma_SelBll();
		totalModel = bll.getCompanyTotalAccount(cid);
		billList = bll.getCompanyAllBill(totalModel.getCfta_id());
		winbillList = billList;
	}

	// 查看账单
	@Command("viewBill")
	public void viewBill(@BindingParam("billNo") String billNo) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("billNo", billNo);
		Window window = (Window) Executions.createComponents(
				"/CoFinanceManage/Cfma_MonthlyBillView.zul", null, map);
		window.doModal();
	}

	// 公司收款
	@Command("companyCollect")
	public void companyCollect(@ContextParam(ContextType.VIEW) Component view) {
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("cid", String.valueOf(totalModel.getCid()));
//		map.put("company", totalModel.getCoba_company());
//		Window window = (Window) Executions.createComponents(
//				"Cfma_CollectToCompany.zul", view, map);
//		window.doModal();
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("cid", String.valueOf(cid));
		map.put("company", totalModel.getCoba_company());
		Window window = (Window) Executions.createComponents(
				"/CoFinanceManage/Cfma_CollectLog.zul", view, map);
		window.doModal();
	}

	// 新增发票
	@Command
	public void createInvoice() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("cid", String.valueOf(totalModel.getCid()));
		map.put("company", totalModel.getCoba_company());
		Window window = (Window) Executions.createComponents(
				"/CoFinanceManage/CoInvoice/InvoiceAdd.zul", null, map);
		window.doModal();
	}

	// 账单收款
	@Command("billCollect")
	public void billCollect(@BindingParam("m") CoFinanceMonthlyBillModel m,
			@ContextParam(ContextType.VIEW) Component view) {
		if (m.getCfmb_FinanceConfirm() == 1
				|| m.getCfmb_PersonnelConfirm() == 1) {
			if (m.getCfmb_WriteOffs() == 1) {
				Messagebox.show("该账单已销账。", "操作提示", Messagebox.OK,
						Messagebox.NONE);
			} else if (m.getCfmb_WriteOffs() == 2) {
				Messagebox.show("该账单已结转。", "操作提示", Messagebox.OK,
						Messagebox.NONE);
			} else {
				Map<String, String> map = new HashMap<String, String>();
				map.put("billNo", m.getCfmb_number());
				Window window = (Window) Executions.createComponents(
						"/CoFinanceManage/Cfma_CollectToBill.zul", view, map);
				window.doModal();
			}
		} else {
			Messagebox.show("该账单应收未确认，无法录入收款。", "操作提示", Messagebox.OK,
					Messagebox.NONE);
		}
	}

	// 领款分配
	@Command("distribution")
	public void distribution(@BindingParam("m") CoFinanceMonthlyBillModel m,
			@ContextParam(ContextType.VIEW) Component view) {
		if (m.getCfmb_FinanceConfirm() == 1
				|| m.getCfmb_PersonnelConfirm() == 1) {
			if (m.getCfmb_WriteOffs() == 1) {
				Messagebox.show("该账单已销账。", "操作提示", Messagebox.OK,
						Messagebox.NONE);
			} else if (m.getCfmb_WriteOffs() == 2) {
				Messagebox.show("该账单已结转。", "操作提示", Messagebox.OK,
						Messagebox.NONE);
			} else {
				Map<String, String> map = new HashMap<String, String>();
				map.put("billNo", m.getCfmb_number());
				map.put("coba_client", totalModel.getCoba_client());
				Integer a=m.getOwnmonth();
				map.put("owmno",a.toString());
				Window window = (Window) Executions.createComponents(
						"/CoFinanceManage/Cfma_CollectDistribution.zul", view, map);
				window.doModal();
			}
		} else {
			Messagebox.show("该账单应收未确认，无法领款。", "操作提示", Messagebox.OK,
					Messagebox.NONE);
		}
	}

	// 销账
	@Command("WriteOffs")
	public void WriteOffs(@BindingParam("m") CoFinanceMonthlyBillModel m) {
		try {
			if (m.getImbalance().compareTo(BigDecimal.ZERO) == 0) {
				Cfma_CollectOperateBll opbll = new Cfma_CollectOperateBll();
				int i = opbll.BillWriteOffs(m.getCfmb_number(),
						UserInfo.getUsername());
				if (i == 1) {
					billList = bll.getCompanyAllBill(totalModel.getCfta_id());
					selBill();
					BindUtils.postNotifyChange(null, null, this, "winbillList");
					Messagebox.show("销账成功。", "操作提示", Messagebox.OK,
							Messagebox.NONE);
				} else {
					Messagebox.show("销账失败。", "操作提示", Messagebox.OK,
							Messagebox.NONE);
				}
			} else {
				Messagebox.show("该账单仍有差额，无法销账。", "操作提示", Messagebox.OK,
						Messagebox.NONE);
			}
		} catch (Exception e) {
			Messagebox.show("销账出错。", "操作提示", Messagebox.OK, Messagebox.NONE);
		}
	}

	// 结转
	@Command("CarryForward")
	public void CarryForward(@BindingParam("m") CoFinanceMonthlyBillModel m,
			@ContextParam(ContextType.VIEW) Component view) {
		try {
			if (m.getCfmb_PersonnelConfirm() == 0) {
				Messagebox.show("该账单人事应收未确认，无法结转。", "操作提示", Messagebox.OK,
						Messagebox.NONE);
			} else if (m.getCfmb_FinanceConfirm() == 0) {
				Messagebox.show("该账单财务应收未确认，无法结转。", "操作提示", Messagebox.OK,
						Messagebox.NONE);
			} else if (m.getImbalance().compareTo(BigDecimal.ZERO) != -1) {
				Messagebox.show("该账单无差额，无需结转可直接销账处理。", "操作提示", Messagebox.OK,
						Messagebox.NONE);
			} else {
				Map<String, String> map = new HashMap<String, String>();
				map.put("billNo", m.getCfmb_number());
				Window window = (Window) Executions.createComponents(
						"Cfma_BillCarryForward.zul", view, map);
				window.doModal();
			}
		} catch (Exception e) {

		}
	}

	// 查看收款纪录
	@Command("collectLog")
	public void collectLog() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("cid", String.valueOf(cid));
		map.put("company", totalModel.getCoba_company());
		Window window = (Window) Executions.createComponents(
				"Cfma_CollectLog.zul", null, map);
		window.doModal();
	}

	// 账单查询
	@Command("selBill")
	@NotifyChange("winbillList")
	public void selBill() {
		if ("".equals(billNo) || billNo == null)
			winbillList = billList;
		else
			searchBill();
	}

	// 账单检索
	private void searchBill() {
		List<CoFinanceMonthlyBillModel> list = new ArrayList<CoFinanceMonthlyBillModel>();
		for (CoFinanceMonthlyBillModel m : billList) {
			if (RegexUtil.isExists(billNo, m.getCfmb_number())) {
				list.add(m);
			}
		}
		winbillList = list;
	}

	// 子页面刷新totalModel
	@Command("refreshTotalModel")
	@NotifyChange({ "totalModel", "winbillList" })
	public void refreshTotalModel() {
		totalModel = bll.getCompanyTotalAccount(cid);
		selBill();
	}

	// 子页面刷新totalModel 以及 billList
	@Command("refreshWin")
	@NotifyChange({ "totalModel", "winbillList" })
	public void refreshWin() {
		totalModel = bll.getCompanyTotalAccount(cid);
		billList = bll.getCompanyAllBill(totalModel.getCfta_id());
		selBill();
	}

	public CoFinanceTotalAccountModel getTotalModel() {
		return totalModel;
	}

	public List<CoFinanceMonthlyBillModel> getWinbillList() {
		return winbillList;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

}
