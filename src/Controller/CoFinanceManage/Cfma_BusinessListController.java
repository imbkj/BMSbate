package Controller.CoFinanceManage;

import java.util.List;

import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;

import Model.CoFinanceDisposableModel;
import Model.CoFinanceProductModel;
import Model.EmFinanceCommissionOutCityModel;
import Model.EmFinanceDisposableModel;
import Model.EmFinanceHouseGjjModel;
import Model.EmFinanceProductModel;
import Model.EmFinanceSalaryModel;
import Model.EmFinanceSheBaoModel;
import Model.EmFinanceTaxModel;
import Model.EmFinanceValueAddTaxModel;
import Util.FileOperate;
import bll.CoFinanceManage.Cfma_BussinessSelBll;
import bll.CoFinanceManage.Cfma_CreateBillReportNew;

public class Cfma_BusinessListController {
	private final int cid = Integer.parseInt(Executions.getCurrent()
			.getParameter("cid").toString());
	private final int ownmonth = Integer.parseInt(Executions.getCurrent()
			.getParameter("ownmonth").toString());
	private final String billNo = Executions.getCurrent()
			.getParameter("billNo").toString();

	private Cfma_BussinessSelBll bsnsBll;
	// 社保
	private List<EmFinanceSheBaoModel> shebaoList;
	private int shebaoListSize;
	// 公积金
	private List<EmFinanceHouseGjjModel> gjjList;
	private int gjjListSize;
	// 工资
	private List<EmFinanceSalaryModel> salaryList;
	private int salaryListSize;

	// 公司福利
	private List<CoFinanceProductModel> coProductList;
	private int coProductListSize;
	// 员工福利
	private List<EmFinanceProductModel> emProductList;
	private int emProductListSize;

	// 委托出
	private List<EmFinanceCommissionOutCityModel> emCommissionOutList;
	private int emCommissionOutListSize;
	// 公司非标
	private List<CoFinanceDisposableModel> coDisposableList;
	private int coDisposableListSize;
	// 员工非标
	private List<EmFinanceDisposableModel> emDisposableList;
	private int emDisposableListSize;

	// 个税
	private List<EmFinanceTaxModel> emTaxList;
	private int emTaxListSize;
	
	//增值税金
	private List<EmFinanceValueAddTaxModel> vatList;
	private int vatListSize;

	public Cfma_BusinessListController() {
		bsnsBll = new Cfma_BussinessSelBll();
		shebaoList = bsnsBll.getShebaoList(cid, ownmonth, billNo);
		shebaoListSize = shebaoList.size();
		gjjList = bsnsBll.getGjjList(cid, ownmonth, billNo);
		gjjListSize = gjjList.size();
		salaryList = bsnsBll.getSalaryList(cid, ownmonth, billNo);
		salaryListSize = salaryList.size();
		emProductList = bsnsBll.getEmProductList(cid, ownmonth, billNo);
		emProductListSize = emProductList.size();
		emCommissionOutList = bsnsBll.getEmFinanceCommissionOutList(cid,
				ownmonth, billNo);
		emCommissionOutListSize = emCommissionOutList.size();
		emDisposableList = bsnsBll.getEmDisposableList(cid, ownmonth, billNo);
		emDisposableListSize = emDisposableList.size();
		emTaxList = bsnsBll.getTaxList(cid, ownmonth, billNo);
		emTaxListSize = emTaxList.size();
		coProductList = bsnsBll.getCoProductList(cid, ownmonth, billNo);
		coProductListSize = coProductList.size();
		coDisposableList = bsnsBll.getCoDisposableList(cid, ownmonth, billNo);
		coDisposableListSize = coDisposableList.size();
		vatList=bsnsBll.getvatList(cid, ownmonth, billNo);
		vatListSize=vatList.size();
		
	}

	// 生成付款通知
	@GlobalCommand
	public void createReport() {
		if ("0".equals(billNo)) {
			return;
		}
		Cfma_CreateBillReportNew cb = new Cfma_CreateBillReportNew();
		if (cb.createReport(billNo, shebaoList, gjjList, salaryList,
				emProductList, emCommissionOutList, emDisposableList,
				emTaxList, coProductList, coDisposableList,vatList)) {
			FileOperate.download("CoFinanceManage/File/billReport/" + billNo
					+ ".xls");
		} else {
			Messagebox.show("付款通知生成出错。", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	public List<EmFinanceSheBaoModel> getShebaoList() {
		return shebaoList;
	}

	public int getShebaoListSize() {
		return shebaoListSize;
	}

	public List<EmFinanceHouseGjjModel> getGjjList() {
		return gjjList;
	}

	public int getGjjListSize() {
		return gjjListSize;
	}

	public List<EmFinanceSalaryModel> getSalaryList() {
		return salaryList;
	}

	public int getSalaryListSize() {
		return salaryListSize;
	}

	public List<EmFinanceProductModel> getEmProductList() {
		return emProductList;
	}

	public int getEmProductListSize() {
		return emProductListSize;
	}

	public List<EmFinanceCommissionOutCityModel> getEmCommissionOutList() {
		return emCommissionOutList;
	}

	public int getEmCommissionOutListSize() {
		return emCommissionOutListSize;
	}

	public List<EmFinanceDisposableModel> getEmDisposableList() {
		return emDisposableList;
	}

	public int getEmDisposableListSize() {
		return emDisposableListSize;
	}

	public List<EmFinanceTaxModel> getEmTaxList() {
		return emTaxList;
	}

	public int getEmTaxListSize() {
		return emTaxListSize;
	}

	public List<CoFinanceProductModel> getCoProductList() {
		return coProductList;
	}

	public int getCoProductListSize() {
		return coProductListSize;
	}

	public List<CoFinanceDisposableModel> getCoDisposableList() {
		return coDisposableList;
	}

	public int getCoDisposableListSize() {
		return coDisposableListSize;
	}

	public List<EmFinanceValueAddTaxModel> getVatList() {
		return vatList;
	}

	public void setVatList(List<EmFinanceValueAddTaxModel> vatList) {
		this.vatList = vatList;
	}

	public int getVatListSize() {
		return vatListSize;
	}

	public void setVatListSize(int vatListSize) {
		this.vatListSize = vatListSize;
	}
	
	
}
