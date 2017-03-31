package bll.EmCommissionOut;

import java.sql.SQLException;
import java.util.List;

import Model.EmCommissionOutFeeDetailChangeModel;
import Model.EmCommissionOutPayModel;
import dal.EmCommissionOut.EmCommissionOutPayDal;
import dal.EmCommissionOut.EmCommissionOutPay_UnfinishedDal;

public class EmCommissionOutPay_UnfinishedBll {
	// 获取委托未完成列表
	public List<EmCommissionOutPayModel> getwt_unflist(String ownmonth,
			String city, String company) throws SQLException {
		EmCommissionOutPay_UnfinishedDal dal = new EmCommissionOutPay_UnfinishedDal();
		List<EmCommissionOutPayModel> list = dal.getwt_unflist(ownmonth, city,
				company);
		return list;
	}

	// 获取委托未完成列表
	public List<EmCommissionOutPayModel> getwt_kfunflist(String gid,String name,String company,String coba_client)
			throws SQLException {
		EmCommissionOutPay_UnfinishedDal dal = new EmCommissionOutPay_UnfinishedDal();
		List<EmCommissionOutPayModel> list = dal.getwt_kfunflist(gid,name,company,coba_client);
		return list;
	}
	
	// 获取差额列表
		public List<EmCommissionOutPayModel> getwt_wtunflist(String gid,String name,String company,String cabc_id,String ownmonth)
				throws SQLException {
			EmCommissionOutPay_UnfinishedDal dal = new EmCommissionOutPay_UnfinishedDal();
			List<EmCommissionOutPayModel> list = dal.getwt_wtunflist(gid,name,company,cabc_id,ownmonth);
			return list;
		}

	// 获取所属月份
	public List<EmCommissionOutPayModel> getownmonthlist() throws SQLException {
		EmCommissionOutPay_UnfinishedDal dal = new EmCommissionOutPay_UnfinishedDal();
		List<EmCommissionOutPayModel> list = dal.getownmonthlist();
		return list;
	}
	
	// 获取客服
		public List<EmCommissionOutPayModel> getclientlist() throws SQLException {
			EmCommissionOutPay_UnfinishedDal dal = new EmCommissionOutPay_UnfinishedDal();
			List<EmCommissionOutPayModel> list = dal.getclientlist();
			return list;
		}

	// 添加非标
	public int zd_add(String ecop_id,String zd_st,String zd_ownmonth) throws SQLException {
		EmCommissionOutPay_UnfinishedDal dal = new EmCommissionOutPay_UnfinishedDal();
		return dal.zd_add(ecop_id,zd_st,zd_ownmonth);
	}

	// 获取委托未完成列表
	public EmCommissionOutPayModel getsfeelist(String ecod_id) throws SQLException {
		EmCommissionOutPay_UnfinishedDal dal = new EmCommissionOutPay_UnfinishedDal();
		return dal.getsfeelist(ecod_id);
	}
	
	// 显示帐单月份
	public List<EmCommissionOutPayModel> getzdownmonthlist(String ecod_id,String nowmonth) throws SQLException {
		EmCommissionOutPay_UnfinishedDal dal = new EmCommissionOutPay_UnfinishedDal();
		List<EmCommissionOutPayModel> list = dal.getzdownmonthlist(ecod_id,nowmonth);
		return list;
	}
}
