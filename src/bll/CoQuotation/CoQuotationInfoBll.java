package bll.CoQuotation;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import dal.CoQuotation.CoQuotationInfoDal;

import Model.CoOLModeModel;
import Model.CoOfferListModel;

public class CoQuotationInfoBll {

	// 根据报价单id获取详情列表(单个报价单)
	public List<CoOfferListModel> getCoOfferlistList(int coof_id)
			throws Exception {
		List<CoOfferListModel> list = new ArrayList<CoOfferListModel>();
		CoQuotationInfoDal dal = new CoQuotationInfoDal();
		list = dal.getCoOfferlistList(coof_id);
		return list;
	}

	// 根据报价单id获取详情列表(多个报价单)
	public List<CoOfferListModel> getCoOfferlistList(String coof_ids)
			throws Exception {
		List<CoOfferListModel> list = new ArrayList<CoOfferListModel>();
		CoQuotationInfoDal dal = new CoQuotationInfoDal();
		list = dal.getCoOfferlistList(coof_ids);
		return list;
	}

	// 根据报价单id获取合同类型(单个报价单)
	public List<CoOfferListModel> getCompactClass(int coof_id) throws Exception {
		List<CoOfferListModel> list = new ArrayList<CoOfferListModel>();
		CoQuotationInfoDal dal = new CoQuotationInfoDal();
		list = dal.getCompactClass(coof_id);
		return list;
	}

	// 根据报价单id获取合同类型(多个报价单)
	public List<CoOfferListModel> getCompactClass(String coof_ids) throws Exception {
		List<CoOfferListModel> list = new ArrayList<CoOfferListModel>();
		CoQuotationInfoDal dal = new CoQuotationInfoDal();
		list = dal.getCompactClass(coof_ids);
		return list;
	}

	// 根据报价单ID查询项目信息
	public List<CoOfferListModel> getcoofferListInfo(Integer coofId) {
		List<CoOfferListModel> list = new ListModelList<>();
		CoQuotationInfoDal dal = new CoQuotationInfoDal();
		list = dal.getCoofferList(coofId);
		if (list.size() > 0) {
			for (CoOfferListModel m : list) {
				List<CoOLModeModel> l = new ListModelList<>();
				l = dal.getcoolModelInfo(m.getColi_id());
				m.setColi_remark(m.getColi_remark() == null ? "" : m
						.getColi_remark());
				if (l.size() > 1) {
					for (CoOLModeModel m2 : l) {
						if (m2.getColm_startmonth() != null
								&& m2.getColm_stopmonth() != null) {
							if (m2.getColm_startmonth().equals(0)
									&& m2.getColm_stopmonth().equals(0)) {

								m.setColi_remark(m.getColi_remark().equals("") ? "在职即享受"
										: ",在职即享受");
								break;
							} else if (m2.getColm_startmonth().equals(0)
									&& m2.getColm_stopmonth() > 0) {
								m.setColi_remark(m.getColi_remark().equals("") ? ("在职，收款满"
										+ m2.getColm_stopmonth()
										+ "个月以下,发放标准:"
										+ m2.getColm_fee() + "元.")
										: (m.getColi_remark() + ";在职，收款满"
												+ m2.getColm_stopmonth()
												+ "个月以下,发放标准:"
												+ m2.getColm_fee() + "元."));

							} else if (m2.getColm_startmonth() > 0
									&& m2.getColm_stopmonth().equals(0)) {
								m.setColi_remark(m.getColi_remark().equals("") ? ("在职，收款满"
										+ m2.getColm_startmonth()
										+ "个月以上,发放标准:" + m2.getColm_fee() + "元.")
										: (m.getColi_remark() + ";在职，收款满"
												+ m2.getColm_startmonth()
												+ "个月以上,发放标准:"
												+ m2.getColm_fee() + "元."));

							}
						}
					}
				} else if (l.size() > 0) {
					for (CoOLModeModel m2 : l) {
						if (m2.getColm_startmonth() != null
								&& m2.getColm_stopmonth() != null) {
							if (m2.getColm_startmonth().equals(0)
									&& m2.getColm_stopmonth().equals(0)) {
								m.setColi_remark(m.getColi_remark().equals("") ? "在职即享受"
										: ",在职即享受");
								break;
							} else if (m2.getColm_startmonth() > 0) {
								m.setColi_remark(m.getColi_remark().equals("") ? ("在职,收款满:"
										+ m2.getColm_startmonth() + "月即可享受.")
										: (";在职,发放标准:" + m2.getColm_fee() + "元."));
								break;
							} else {
								m.setColi_remark(m.getColi_remark().equals("") ? ("在职,发放标准:"
										+ m2.getColm_fee() + "元.")
										: (";在职,发放标准:" + m2.getColm_fee() + "元."));
								break;
							}
						}
					}
				}
			}
		}

		return list;
	}

	// 根据合同id获取报价单信息
	public List<CoOfferListModel> getCoOfferlist(String coli_coco_id) {
		List<CoOfferListModel> list = new ArrayList<CoOfferListModel>();
		CoQuotationInfoDal dal = new CoQuotationInfoDal();
		list = dal.getCoOfferlistInfo(coli_coco_id);
		return list;
	}
}
