package bll.CoSocialInsurance;

import java.util.List;

import Model.CoShebaoPayAmountModel;
import Model.CoShebaoQueryBillsModel;
import dal.CoSocialInsurance.CoShebao_QueryBillsDal;

public class CoShebao_QueryBillsBll {

	CoShebao_QueryBillsDal dal = new CoShebao_QueryBillsDal();

	/**
	 * 添加社保到账情况
	 * 
	 * @return
	 */
	public int addQueryBills(CoShebaoPayAmountModel m) {
		return dal.addQueryBills(m);
	}

	/**
	 * 更新社保缴存表
	 */
	public void updatePayAmount(CoShebaoPayAmountModel m) {
		dal.updatePayAmount(m);
	}

	/**
	 * 添加社保缴存
	 */
	public void addPayAmount(CoShebaoPayAmountModel m) {
		dal.addPayAmount(m);
	}

	/**
	 * 查询社保缴存是否存在
	 * 
	 * @return
	 */
	public CoShebaoPayAmountModel getPayAmount(CoShebaoPayAmountModel m) {

		return dal.getPayAmount(m);
	}

	/**
	 * 社保到账详情查询
	 * 
	 * @param cspaid
	 * @return
	 */
	public List<CoShebaoQueryBillsModel> getQueryBillsList(int cspaid) {

		return dal.getQueryBillsList(cspaid);
	}

	/**
	 * 查询社保到账记录集合
	 * 
	 * @return
	 */
	public List<CoShebaoPayAmountModel> getQueryBillsList() {
		List<CoShebaoPayAmountModel> l = dal.getPayAmountList();

		return l;
	}
}
