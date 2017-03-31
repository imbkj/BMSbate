package bll.EmPay;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import Model.EmPay_BaseListModel;
import dal.EmPay.EmPay_BaseListDal;

public class EmPay_BaseListBll {
	// 获取员工数据
	public List<EmPay_BaseListModel> empay_baselist(String id)
			throws SQLException {
		EmPay_BaseListDal dal = new EmPay_BaseListDal();
		List<EmPay_BaseListModel> list = dal.getEmPay_Baselist(id);
		return list;
	}
	
	// 获取公司数据
		public List<EmPay_BaseListModel> copay_baselist(String ownmonth,String paynum,String stype)
				throws SQLException {
			EmPay_BaseListDal dal = new EmPay_BaseListDal();
			List<EmPay_BaseListModel> list = dal.getCoPay_Baselist(ownmonth,paynum,stype);
			return list;
		}

	// 获取员工详细数据
	public List<EmPay_BaseListModel> empay_feelist(String emsp_id, String gid,
			String ownmonth, String stype) throws SQLException {

		EmPay_BaseListDal dal = new EmPay_BaseListDal();
		List<EmPay_BaseListModel> list = dal.getEmPay_Feelist(emsp_id, gid,
				ownmonth, stype);
		return list;
	}
	
	// 获取公司详细数据
		public List<EmPay_BaseListModel> copay_feelist(String cid,
				String ownmonth,String paynum, String stype) throws SQLException {

			EmPay_BaseListDal dal = new EmPay_BaseListDal();
			List<EmPay_BaseListModel> list = dal.getCoPay_Feelist(cid,ownmonth, paynum,stype);
			return list;
		}

	// 审核支付通知
	public List<EmPay_BaseListModel> empay_autok(String ownmonth, String paynum)
			throws SQLException {

		EmPay_BaseListDal dal = new EmPay_BaseListDal();
		List<EmPay_BaseListModel> list = dal.getEmPay_Autok(ownmonth, paynum);
		return list;
	}

	// 审核支付通知
	public List<EmPay_BaseListModel> empay_mgautok(String ownmonth, String paynum)
			throws SQLException {

		EmPay_BaseListDal dal = new EmPay_BaseListDal();
		List<EmPay_BaseListModel> list = dal.getEmPay_Mgautok(ownmonth, paynum);
		return list;
	}

	// 添加支付明细
	public List<EmPay_BaseListModel> empay_payok(String em1, String em2,
			String em3, String em4, String em5, String em6, String em7,
			String ownmonth, String paynum) throws SQLException {

		EmPay_BaseListDal dal = new EmPay_BaseListDal();
		List<EmPay_BaseListModel> list = dal.getEmPay_Payok(em1, em2, em3, em4,
				em5, em6, em7, ownmonth, paynum);
		return list;
	}

	// Date类型转换String
	public String DatetoSting(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String Date = sdf.format(d);
		return Date;
	}
}
