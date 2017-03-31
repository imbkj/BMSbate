package bll.EmPay;

import java.sql.SQLException;
import java.util.List;

import Model.EmPay_ListModel;
import dal.EmPay.EmPay_ListDal;

public class EmPay_ListBll {
	// 获取未审核数据
	public List<EmPay_ListModel> empay_autlist(String castsort)
			throws SQLException {
		String str1 = "";
		if (!castsort.equals("全部") && !castsort.equals("")) {
			str1 = " and d.ecic_title='" + castsort + "'";
		}
		EmPay_ListDal dal = new EmPay_ListDal();
		List<EmPay_ListModel> list = dal.getEmPay_Autlist(str1);
		return list;
	}

	// 获取已审核数据
	public List<EmPay_ListModel> empay_autpaylist(String castsort)
			throws SQLException {
		String str1 = "";
		if (!castsort.equals("全部") && !castsort.equals("")) {
			str1 = " and d.ecic_title='" + castsort + "'";
		}
		EmPay_ListDal dal = new EmPay_ListDal();
		List<EmPay_ListModel> list = dal.getEmPay_Autpaylist(str1);
		return list;
	}

	// 获取已审核数据
	public List<EmPay_ListModel> empay_mgautlist(String castsort)
			throws SQLException {
		String str1 = "";
		if (!castsort.equals("全部") && !castsort.equals("")) {
			str1 = " and d.ecic_title='" + castsort + "'";
		}
		EmPay_ListDal dal = new EmPay_ListDal();
		List<EmPay_ListModel> list = dal.getEmpay_Mgautlist(str1);
		return list;
	}

	// 获取数据
	public List<EmPay_ListModel> empay_list(String castsort)
			throws SQLException {
		String str1 = "";
		if (!castsort.equals("全部") && !castsort.equals("")) {
			str1 = " and d.ecic_title='" + castsort + "'";
		}
		EmPay_ListDal dal = new EmPay_ListDal();
		List<EmPay_ListModel> list = dal.getEmpay_List(str1);
		return list;
	}
}
