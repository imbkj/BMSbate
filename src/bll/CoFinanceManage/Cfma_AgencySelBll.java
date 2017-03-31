package bll.CoFinanceManage;

import java.util.List;

import Model.CoAgencyBaseModel;
import Model.CoFinanceAgencyCollectModel;
import Model.CoFinanceAgencyModel;
import Model.CoFinanceAgencyMonthlyBillModel;
import Model.CoFinanceMonthlyBillModel;
import dal.CoFinanceManage.Cfma_AgencySelDal;

public class Cfma_AgencySelBll {
	private Cfma_AgencySelDal dal = new Cfma_AgencySelDal();

	// 获取机构总账表数据
	public List<CoFinanceAgencyModel> getTotalAccount() {
		return dal.getTotalAccount();
	}

	// 根据主键获取机构总账表数据
	public CoFinanceAgencyModel getTotalAccount(int cfat_id) {
		return dal.getTotalAccount(cfat_id);
	}

	// 根据cabc_id获取委托入机构每月帐单
	public List<CoFinanceAgencyMonthlyBillModel> getAgencyMonthlyBill(
			int cabc_id) {
		return dal.getAgencyMonthlyBill(cabc_id);
	}

	// 根据主键获取委托入机构每月帐单
	public CoFinanceAgencyMonthlyBillModel getAgencyMonthlyBillModel(
			String cfab_number) {
		return dal.getAgencyMonthlyBillModel(cfab_number);
	}

	// 根据cabc_id获取收款纪录
	public List<CoFinanceAgencyCollectModel> getAgencyCollectList(int cabc_id) {
		return dal.getAgencyCollectList(cabc_id);
	}

	// 按机构账单查询公司账单列表
	public List<CoFinanceMonthlyBillModel> getCoFinanceMonthlyBillList(
			int cabc_id, int ownmonth) {
		return dal.getCoFinanceMonthlyBillList(cabc_id, ownmonth);
	}

	// 根据coab_id获取收款纪录
	public CoAgencyBaseModel getAgency(int coab_id) {
		return dal.getAgency(coab_id);
	}
}
