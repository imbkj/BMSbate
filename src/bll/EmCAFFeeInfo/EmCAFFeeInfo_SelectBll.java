package bll.EmCAFFeeInfo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dal.EmCAFFeeInfo.EmCAFFeeInfo_SelectDal;

import Model.EmCAFFeeInfoCompanyModel;
import Model.EmCAFFeeInfoModel;
import Model.HandoverPeopleModel;
import Util.DateStringChange;

public class EmCAFFeeInfo_SelectBll {

	// 获取证件及档案费用信息
	public List<EmCAFFeeInfoModel> getECFIInfoList(String str, String orderBy)
			throws SQLException {
		EmCAFFeeInfo_SelectDal dal = new EmCAFFeeInfo_SelectDal();
		List<EmCAFFeeInfoModel> list = dal.getECFIInfoList(str, orderBy);
		return list;
	}

	// 获取证件及档案费用信息(根据id)
	public EmCAFFeeInfoModel getECFIInfoById(Integer ecfi_id)
			throws SQLException {
		EmCAFFeeInfo_SelectDal dal = new EmCAFFeeInfo_SelectDal();
		EmCAFFeeInfoModel m = dal.getECFIInfoById(ecfi_id);
		return m;
	}

	// 获取证件及档案费用信息
	public List<HandoverPeopleModel> getHP(String str) throws SQLException {
		EmCAFFeeInfo_SelectDal dal = new EmCAFFeeInfo_SelectDal();
		List<HandoverPeopleModel> list = dal.getHP(str);
		return list;
	}

	// 去除日期格式后面的时间部分
	public Date changeSDate(String date) {
		Date f_date = null;
		if (!"".equals(date) && date != null && !date.equals("NULL")
				&& !date.equals("null")) {
			f_date = DateStringChange.StringtoDate(date, "yyyy-MM-dd");
		}
		return f_date;
	}

	// 统计公司汇总数据
	public List<EmCAFFeeInfoCompanyModel> getECFICompanyList(String str) {
		EmCAFFeeInfo_SelectDal dal = new EmCAFFeeInfo_SelectDal();
		List<EmCAFFeeInfoModel> list = dal.getECFIInfoList(str, "");
		List<EmCAFFeeInfoCompanyModel> clist = new ArrayList<EmCAFFeeInfoCompanyModel>();
		Map<Integer, EmCAFFeeInfoCompanyModel> map = new HashMap<Integer, EmCAFFeeInfoCompanyModel>();
		EmCAFFeeInfoCompanyModel cm;

		try {
			for (EmCAFFeeInfoModel m : list) {
				if (map.containsKey(m.getCid())) {// 按cid匹配
					cm = (EmCAFFeeInfoCompanyModel) map.get(m.getCid());
				} else {
					cm = new EmCAFFeeInfoCompanyModel();
					cm.setCid(m.getCid());
					cm.setShortname(m.getShortname());
					cm.setShortspell(m.getShortspell());
					cm.setClient(m.getClient());
					cm.setAll_fee(0);
					cm.setYf_fee(0);
					cm.setWf_fee(0);
					cm.setWd_loan_fee(0);
					cm.setWd_clear_fee(0);
					cm.setCsd_loan_fee(0);
					cm.setCsd_clear_fee(0);
					map.put(m.getCid(), cm);
				}

				// 赋值到model
				cm.setAll_fee(cm.getAll_fee() + m.getEcfi_fee());
				if ("已付".equals(m.getEcfi_payment_state())) {
					cm.setYf_fee(cm.getYf_fee() + m.getEcfi_fee());
				}
				if ("未付".equals(m.getEcfi_payment_state())) {
					cm.setWf_fee(cm.getWf_fee() + m.getEcfi_fee());
				}
				if ((m.getEcfi_wd_loan_date() != null
						|| !"".equals(m.getEcfi_wd_loan_date()) || !"null"
							.equals(m.getEcfi_wd_loan_date()))
						&& (m.getEcfi_ri_date() == null
								|| "".equals(m.getEcfi_ri_date()) || "null"
									.equals(m.getEcfi_ri_date()))) {
					cm.setWd_loan_fee(cm.getWd_loan_fee() + m.getEcfi_fee());
				}
				if ((m.getEcfi_wd_loan_date() != null
						|| !"".equals(m.getEcfi_wd_loan_date()) || !"null"
							.equals(m.getEcfi_wd_loan_date()))
						&& (m.getEcfi_ri_date() != null
								|| !"".equals(m.getEcfi_ri_date()) || !"null"
									.equals(m.getEcfi_ri_date()))) {
					cm.setWd_clear_fee(cm.getWd_clear_fee() + m.getEcfi_fee());
				}
				if ((m.getEcfi_csd_loan_date() != null
						|| !"".equals(m.getEcfi_csd_loan_date()) || !"null"
							.equals(m.getEcfi_csd_loan_date()))
						&& (m.getEcfi_csd_clearing_date() == null
								|| "".equals(m.getEcfi_csd_clearing_date()) || "null"
									.equals(m.getEcfi_csd_clearing_date()))) {
					cm.setCsd_loan_fee(cm.getCsd_loan_fee() + m.getEcfi_fee());
				}
				if ((m.getEcfi_csd_loan_date() != null
						|| !"".equals(m.getEcfi_csd_loan_date()) || !"null"
							.equals(m.getEcfi_csd_loan_date()))
						&& (m.getEcfi_csd_clearing_date() != null
								|| !"".equals(m.getEcfi_csd_clearing_date()) || !"null"
									.equals(m.getEcfi_csd_clearing_date()))) {
					cm.setCsd_clear_fee(cm.getCsd_clear_fee() + m.getEcfi_fee());
				}

			}

			// map转list
			Collection<EmCAFFeeInfoCompanyModel> collection = map.values();
			for (EmCAFFeeInfoCompanyModel m : collection) {
				clist.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return clist;
	}

	// 获取证件及档案费用信息
	public List<EmCAFFeeInfoCompanyModel> getECFIInfoGAllFee(String str)
			throws SQLException {
		EmCAFFeeInfo_SelectDal dal = new EmCAFFeeInfo_SelectDal();
		List<EmCAFFeeInfoCompanyModel> list = dal.getECFIInfoGAllFee(str);
		return list;
	}
}
