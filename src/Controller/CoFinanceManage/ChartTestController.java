package Controller.CoFinanceManage;

import java.math.BigDecimal;
import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.CategoryModel;
import org.zkoss.zul.PieModel;
import org.zkoss.zul.SimpleCategoryModel;
import org.zkoss.zul.SimplePieModel;

import Model.CoFinanceMonthlyBillModel;
import Model.CoFinanceMonthlyBillSortAccountModel;
import Util.ChartEngine;
import Util.ChartPieEngine;

public class ChartTestController {
	@SuppressWarnings("unchecked")
	private final List<CoFinanceMonthlyBillSortAccountModel> sortList = (List<CoFinanceMonthlyBillSortAccountModel>) Executions
			.getCurrent().getArg().get("sortList");
	private final CoFinanceMonthlyBillModel billModel = (CoFinanceMonthlyBillModel) Executions
			.getCurrent().getArg().get("billModel");
	private PieModel pieModel;
	private ChartPieEngine pieEngine;

	private CategoryModel barModel;
	private ChartEngine barEngine;

	public ChartTestController() {
		setPieData();
		setBarData();
	}

	private void setPieData() {
		pieModel = new SimplePieModel();
		pieEngine = new Util.ChartPieEngine();
		pieEngine.setExplode(true);
		for (CoFinanceMonthlyBillSortAccountModel m : sortList) {
			if (m.getCfsa_Receivable().compareTo(BigDecimal.ZERO) != 0)
				pieModel.setValue(m.getCfsa_cpac_name(), m.getCfsa_Receivable());
		}
	}

	private void setBarData() {
		barModel = new SimpleCategoryModel();
		barEngine = new Util.ChartEngine();
		for (CoFinanceMonthlyBillSortAccountModel m : sortList) {
			if (m.getCfsa_Receivable().compareTo(BigDecimal.ZERO) != 0)
				barModel.setValue(billModel.getCfmb_number(),
						m.getCfsa_cpac_name(), m.getCfsa_Receivable());
		}
	}

	public PieModel getPieModel() {
		return pieModel;
	}

	public ChartPieEngine getPieEngine() {
		return pieEngine;
	}

	public CategoryModel getBarModel() {
		return barModel;
	}

	public ChartEngine getBarEngine() {
		return barEngine;
	}

}
