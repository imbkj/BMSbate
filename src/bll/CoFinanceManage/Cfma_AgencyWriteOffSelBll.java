package bll.CoFinanceManage;

import java.util.List;

import dal.CoFinanceManage.Cfma_AgencyWriteOffSelDal;

import Model.CoAgencyBaseModel;
import Model.CoFinanceAgencyMonthlyBillModel;
import Model.CoFinanceAgencyWriteOffInfoModel;
import Model.CoFinanceAgencyWriteOffModel;

public class Cfma_AgencyWriteOffSelBll {
	private Cfma_AgencyWriteOffSelDal dal;

	public Cfma_AgencyWriteOffSelBll() {
		dal = new Cfma_AgencyWriteOffSelDal();
	}

	// 获取冲销机构列表
	public List<CoAgencyBaseModel> getAgencyList() {
		return dal.getAgencyList();
	}

	// 获取委托出冲销账单
	public List<CoFinanceAgencyMonthlyBillModel> getWtWriteOffBill(int coab_id) {
		return dal.getWtWriteOffBill(coab_id);
	}

	// 获取委托入冲销账单
	public List<CoFinanceAgencyMonthlyBillModel> getStWriteOffBill(int coab_id) {
		return dal.getStWriteOffBill(coab_id);
	}

	// 获取冲销记录
	public List<CoFinanceAgencyWriteOffModel> getWriteOffList(int coab_id) {
		return dal.getWriteOffList(coab_id);
	}

	// 获取冲销明细记录
	public List<CoFinanceAgencyWriteOffInfoModel> getWriteOffInfoList(
			int cawo_id, int type) {
		return dal.getWriteOffInfoList(cawo_id, type);
	}
}
