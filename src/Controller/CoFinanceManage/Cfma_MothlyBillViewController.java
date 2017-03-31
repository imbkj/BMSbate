package Controller.CoFinanceManage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import bll.CoFinanceManage.Cfma_BillSelBll;

import Model.CoFinanceMonthlyBillModel;
import Model.CoFinanceMonthlyBillSortAccountModel;
import Util.FileOperate;

public class Cfma_MothlyBillViewController {
	private final String billNo = Executions.getCurrent().getArg()
			.get("billNo").toString();
	private CoFinanceMonthlyBillModel billModel;
	private List<CoFinanceMonthlyBillSortAccountModel> sortList;
	private Cfma_BillSelBll bll;
	private String listSrc;

	public Cfma_MothlyBillViewController() {
		bll = new Cfma_BillSelBll();
		billModel = bll.getBillModel(billNo);
		sortList = bll.getMonthlyBillSortAccount(billNo);
		listSrc = "../CoFinanceManage/Cfma_BusinessList.zul?cid=" + billModel.getCid()
				+ "&ownmonth=" + billModel.getOwnmonth() + "&billNo=" + billNo;
	}

	@Command("report")
	public void report() {
		if (bll.existsBillReport(billNo)
				&& FileOperate.existFile("CoFinanceManage/File/billReport/",
						billNo + ".xls")) {
			FileOperate.download("CoFinanceManage/File/billReport/" + billNo
					+ ".xls");
		} else {
			BindUtils.postGlobalCommand(null, null, "createReport", null);
		}
	}

	@Command("chart")
	public void chart() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sortList", sortList);
		map.put("billModel", billModel);
		Window window = (Window) Executions.createComponents("../CoFinanceManage/ChartTest.zul",
				null, map);
		window.doModal();
	}

	public String getListSrc() {
		return listSrc;
	}

	public List<CoFinanceMonthlyBillSortAccountModel> getSortList() {
		return sortList;
	}

	public CoFinanceMonthlyBillModel getBillModel() {
		return billModel;
	}

}
