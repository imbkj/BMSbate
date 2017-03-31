package bll.CoHousingFund;

import java.util.List;

import Model.CoHousingFundPayAmountModel;
import Model.CoHousingFundPaymentModel;
import Model.CoShebaoPayAmountModel;
import dal.CoHousingFund.CoHousingFund_QueryBillsDal;

public class CoHousingFund_QueryBillsBll {
	CoHousingFund_QueryBillsDal dal = new CoHousingFund_QueryBillsDal();

	/**
	 * 补缴详情列表
	 * 
	 * @return
	 */
	public List<CoHousingFundPaymentModel> getPaymentBillsList(int cfpaid) {
		return dal.getPaymentBillsList(cfpaid);
	}

	/**
	 * 添加公积金补缴到账状况
	 * 
	 * @return
	 */
	public int addPayment(CoHousingFundPayAmountModel m) {
		return dal.addPayment(m);
	}

	/**
	 * 添加公积金缴存 (从补缴添加)
	 */
	public void updatePaymentAmount(CoHousingFundPayAmountModel m) {
		dal.updatePaymentAmount(m);
	}

	/**
	 * 更新公积金缴存表(从补缴添加)
	 */
	public void addPaymentAmount(CoHousingFundPayAmountModel m) {
		dal.addPaymentAmount(m);
	}

	/**
	 * 添加公积金到账情况
	 * 
	 * @return
	 */
	public int addQueryBills(CoHousingFundPayAmountModel m) {
		return dal.addQueryBills(m);
	}

	/**
	 * 添加公积金缴存(从缴存添加)
	 */
	public void addPayAmount(CoHousingFundPayAmountModel m) {
		dal.addPayAmount(m);
	}

	/**
	 * 更新公积金缴存表(从缴存更新)
	 */
	public void updatePayAmount(CoHousingFundPayAmountModel m) {
		dal.updatePayAmount(m);
	}

	/**
	 * 查询公积金缴存是否存在
	 * 
	 * @return
	 */
	public CoHousingFundPayAmountModel getPayAmount(
			CoHousingFundPayAmountModel m) {

		return dal.getPayAmount(m);
	}

	/**
	 * 根据cfpaid查询到账详情
	 * 
	 * @param cfpaid
	 * @return
	 */
	public List<CoHousingFundPaymentModel> getBillsList(int cfpaid) {

		return dal.getBillsList(cfpaid);
	}

	/**
	 * 查询公积金到账记录
	 * 
	 * @return
	 */
	public List<CoHousingFundPayAmountModel> getQueryBillsList() {

		List<CoHousingFundPayAmountModel> l = dal.getQueryBillsList();
		for (int i = 0; i < l.size(); i++) {

			if (l.get(i).getCofp_isaccount() == null) {
				l.get(i).setCofp_isaccount("未到帐");
			}
			if (l.get(i).getCqbc_isaccount() == null) {
				l.get(i).setCqbc_isaccount("未到帐");
			}
		}
		return l;
	}

}
