package bll.CoFinanceManage;

import java.util.List;

import dal.CoFinanceManage.Cfma_BillSelDal;
import Model.CoFinanceCollectModel;
import Model.CoFinanceMonthlyBillModel;
import Model.CoFinanceMonthlyBillSortAccountModel;

public class Cfma_BillSelBll {
	private Cfma_BillSelDal dal;

	public Cfma_BillSelBll() {
		dal = new Cfma_BillSelDal();
	}

	
	//根据cid查询账单
	public List<CoFinanceCollectModel> getNumberByCid(int cid){
		
		return dal.getNumberByCid(cid);
	}
	// 根据账单号获取账单信息
	public CoFinanceMonthlyBillModel getBillModel(String billNo) {
		return dal.getBillModel(billNo);
	}

	// 根据账单号获取账单财务科目
	public List<CoFinanceMonthlyBillSortAccountModel> getMonthlyBillSortAccount(
			String billNo) {
		return dal.getMonthlyBillSortAccount(billNo);
	}

	// 查询账单是否已生成付款通知
	public boolean existsBillReport(String billNo) {
		return dal.existsBillReport(billNo);
	}
}
