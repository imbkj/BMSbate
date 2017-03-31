package Controller.CoFinanceManage;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;

import Model.CoFinanceDisposableModel;
import Model.CoFinanceProductModel;
import Model.EmFinanceCommissionOutCityModel;
import Model.EmFinanceDisposableModel;
import Model.EmFinanceHouseGjjModel;
import Model.EmFinanceProductModel;
import Model.EmFinanceSalaryModel;
import Model.EmFinanceSheBaoModel;
import Model.EmFinanceTaxModel;
import bll.CoFinanceManage.Cfma_BussinessSelAllBll;

public class Cfma_BusinessAllListController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;
	private final int cid = Integer.parseInt(Executions.getCurrent()
			.getParameter("cid").toString());
	private final int ownmonth = Integer.parseInt(Executions.getCurrent()
			.getParameter("ownmonth").toString());

	private Cfma_BussinessSelAllBll bsnsBll;
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

	public Cfma_BusinessAllListController() {
		bsnsBll = new Cfma_BussinessSelAllBll();
		shebaoList = bsnsBll.getShebaoList(cid, ownmonth);
		shebaoListSize = shebaoList.size();
		gjjList = bsnsBll.getGjjList(cid, ownmonth);
		gjjListSize = gjjList.size();
		salaryList = bsnsBll.getSalaryList(cid, ownmonth);
		salaryListSize = salaryList.size();
		emProductList = bsnsBll.getEmProductList(cid, ownmonth);
		emProductListSize = emProductList.size();
		emCommissionOutList = bsnsBll.getEmFinanceCommissionOutList(cid,
				ownmonth);
		emCommissionOutListSize = emCommissionOutList.size();
		emDisposableList = bsnsBll.getEmDisposableList(cid, ownmonth);
		emDisposableListSize = emDisposableList.size();
		emTaxList = bsnsBll.getTaxList(cid, ownmonth);
		emTaxListSize = emTaxList.size();
		coProductList = bsnsBll.getCoProductList(cid, ownmonth);
		coProductListSize = coProductList.size();
		coDisposableList = bsnsBll.getCoDisposableList(cid, ownmonth);
		coDisposableListSize = coDisposableList.size();
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

}
