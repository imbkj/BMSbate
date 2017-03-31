package bll.BusinessIntelligence;

import java.util.List;

import Model.CoFinanceMonthlyBillModel;
import Model.CoFinanceMonthlyBillSortAccountModel;
import dal.BusinessIntelligence.CoFinanceDal;

public class CoFinanceBll {
	private CoFinanceDal dal;

	public CoFinanceBll() {
		dal = new CoFinanceDal();
	}

	// 获取台账财务科目统计
	public List<CoFinanceMonthlyBillSortAccountModel> getMonthlySortAccount(
			int ownmonth) {
		return dal.getMonthlySortAccount(ownmonth);
	}

	// 获取每月的应收实收数据
	public List<CoFinanceMonthlyBillModel> getMonthBillTotle() {
		return dal.getMonthBillTotle();
	}
}
